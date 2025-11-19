package uts.sdk.modules.wjKdxf;

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.media.AudioManager
import android.media.AudioDeviceInfo
import android.os.Build
import com.iflytek.sparkchain.core.SparkChain
import com.iflytek.sparkchain.core.SparkChainConfig
import com.iflytek.sparkchain.core.asr.ASR
import com.iflytek.sparkchain.core.asr.ASR.ASRError
import com.iflytek.sparkchain.core.asr.ASR.ASRResult
import com.iflytek.sparkchain.core.asr.AsrCallbacks
import java.io.FileInputStream
import io.dcloud.uts.console

import kotlinx.coroutines.*

class distinguish {
    private var _Context: Context? = null
    private var isAuth: Boolean? = null
    private val cacheInfo = ""
    private val language = "zh_cn"
    private var path = ""
    private var isdws = false
    private var mAsr: ASR? = null
    private var isrun = false
	private var onCallback: ((String) -> Unit)? = null
	
	private var recordingJob: Job? = null
    var mAsrCallbacks: AsrCallbacks = object : AsrCallbacks {
        override fun onResult(asrResult: ASRResult, o: Any) {
            //以下信息需要开发者根据自身需求，如无必要，可不需要解析执行。
            val begin = asrResult.begin //识别结果所处音频的起始点
            val end = asrResult.end //识别结果所处音频的结束点
            val status = asrResult.status //结果数据状态，0：识别的第一块结果,1：识别中间结果,2：识别最后一块结果
            val result = asrResult.bestMatchText //识别结果
            val sid = asrResult.sid //sid
            val vads = asrResult.vads
            val transcriptions = asrResult.transcriptions
            var vad_begin = -1
            var vad_end = -1
            var word: String? = null
            for (vad in vads) {
                vad_begin = vad.begin
                vad_end = vad.end //VAD结果
                showInfo("vad={begin:$vad_begin,end:$vad_end}")
            }
            for (transcription in transcriptions) {
                val segments = transcription.segments
                for (segment in segments) {
                    word = segment.text //分词结果
                    //                    Log.d(TAG,"word={word:"+word+"}");
                }
            }
            val info = "result={begin:$begin,end:$end,status:$status,result:$result,sid:$sid}"
            /****************************此段为为了UI展示结果，开发者可根据自己需求改动 */
            showInfo(status.toString())
			if (status == 0) {
                showInfo(cacheInfo + result)
            } else if (status == 2) {
                // 识别结束
                // showInfo(result + "66666666")
				onCallback?.invoke(result)
                isrun = false
            } else {
                showInfo("未知")
            }
        }

        override fun onError(asrError: ASRError, o: Any) {
            val code = asrError.code
            val msg = asrError.errMsg
            val sid = asrError.sid
            val info = "error={code:$code,msg:$msg,sid:$sid}"
            showInfo("识别出错!错误码：$code,错误信息：$msg,sid:$sid\n")
            isrun = false
        }
    }

    fun init(context: Context?, _path: String, _onCallback: (String) -> Unit) {
        path = _path
        _Context = context
		onCallback = _onCallback
		
		
				
				
        // 初始化SDK，Appid等信息在清单中配置
        val sparkChainConfig = SparkChainConfig.builder()
        sparkChainConfig.appID("b33c2208")
            .apiKey("c893b4ddd72a17edbb6033c636e0c26a")
            .apiSecret("MTU4MGNjMDU3ZTRkODYxYmNhYjIwYmIz") //应用申请的appid三元组
            //                .uid(getAndroidId())
            .logLevel(666)
        val ret = SparkChain.getInst().init(_Context, sparkChainConfig)
        val result: String
        if (ret == 0) {
            result = "SDK初始化成功,请选择相应的功能点击体验。"
            isAuth = true
            initASR()
        } else {
            result = "SDK初始化失败,错误码:$ret"
            isAuth = false
        }
        showInfo(result)
    }

    var count = 0
    fun runAsr() {
        if (isrun) {
            showInfo("正在识别中，请勿重复开启。\n")
            return
        }
        if (mAsr == null) {
            initASR()
        }
        isdws = false
        mAsr!!.language(language) //语种，zh_cn:中文，en_us:英文。其他语种参见集成文档
        mAsr!!.domain("iat") //应用领域,iat:日常用语。其他领域参见集成文档
        mAsr!!.accent("mandarin") //方言，mandarin:普通话。方言仅当language为中文时才会生效。其他方言参见集成文档。
        mAsr!!.vinfo(true) //返回子句结果对应的起始和结束的端点帧偏移值。
        if ("zh_cn" == language) {
            mAsr!!.dwa("wpgs") //动态修正
            isdws = true
        }
        
        // 检测并配置耳机麦克风
        configureHeadsetMicrophone()
        
        count++
        isrun = true
        // val ret = mAsr!!.start(count.toString() + "") //入参为用户自定义标识，用户关联onResult结果。
        // //带有AudioAttributes的start示例如下，开发者根据自身需求二选一即可。
        // //AudioAttributes attr = new AudioAttributes();
        // //attr.setSampleRate(16000);
        // //attr.setEncoding("raw");
        // //attr.setChannels(1);
        // //attr.setBitdepth(16);
        // //int ret = asr.start(attr,count+"");

        
		// 使用默认配置
		val ret = mAsr!!.start(count.toString())

        if (ret == 0) {
            write()
        } else {
            showInfo("识别开启失败，错误码:$ret\n")
        }
    }

    private fun initASR() {
        if (mAsr == null) {
            mAsr = ASR()
            mAsr!!.registerCallbacks(mAsrCallbacks)
        }
    }

    fun write() {
		recordingJob = CoroutineScope(Dispatchers.IO).launch {
		    val filePath = path
		    showInfo("识别音频路径:$filePath\n")
		    showInfo("正在识别中，请稍等...\n")
		    try {
		        val fs = FileInputStream(filePath)
		        val buffer = ByteArray(1280)
		        var len = 0
		        while (-1 != fs.read(buffer).also { len = it }) {
		            if (!isrun) {
		                break
		            }
		            if (len > 0) {
		                mAsr!!.write(buffer.clone())
		                Thread.sleep(40)
		            }
		        }
		        fs.close()
		        Thread.sleep(10)
		        mAsr!!.stop(false) //音频输入完毕后要调用stop通知云端和SDK，音频输入结束。true:立即结束，false:等云端最后一包下发后结束
		        isrun = false
		    } catch (e: Exception) {
		        showInfo("出错啦")
		        e.printStackTrace()
		    }
		}
		
		
        
    }

    private fun stopAsr() {
        if (isrun) {
            mAsr!!.stop(true) //取消打断。true:立即结束，false:等云端最后一包下发后结束
            showInfo("已退出识别\n")
			recordingJob?.cancel()
            isrun = false
        }
    }

    /**
     * 检测耳机连接状态并配置使用耳机麦克风或默认麦克风
     */
   private fun configureHeadsetMicrophone() {
        _Context?.let { context ->
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            
            if (isHeadsetConnected(audioManager)) {
                // 设置音频路由到耳机
                audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
                audioManager.isSpeakerphoneOn = false
                showInfo("已配置使用耳机麦克风进行录音")
            } else {
                // 没有耳机时，切换到默认麦克风
                audioManager.mode = AudioManager.MODE_NORMAL
                audioManager.isSpeakerphoneOn = false
                // 确保音频路由到默认设备
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    audioManager.clearCommunicationDevice()
                }
                showInfo("已配置使用默认麦克风进行录音")
            }
        }
    }
    
    /**
     * 检测是否有耳机连接（包括有麦克风的耳机）
     */
    private fun isHeadsetConnected(audioManager: AudioManager): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6.0 及以上版本使用 AudioDeviceInfo
            val devices = audioManager.getDevices(AudioManager.GET_DEVICES_INPUTS)
            for (device in devices) {
                if (device.type == AudioDeviceInfo.TYPE_WIRED_HEADSET ||
                    device.type == AudioDeviceInfo.TYPE_BLUETOOTH_SCO) {
                    return true
                }
            }
            false
        } else {
            // Android 6.0 以下版本使用已废弃的方法
            @Suppress("DEPRECATION")
            audioManager.isWiredHeadsetOn || audioManager.isBluetoothScoOn
        }
    }

    private fun showInfo(text: String) {
        // Log.d(ContentValues.TAG, text)
		
		console.log(text)
    }
}
