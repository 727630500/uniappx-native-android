package uts.sdk.modules.wjKdxf;

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import com.iflytek.cloud.EvaluatorListener
import com.iflytek.cloud.EvaluatorResult
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechError
import com.iflytek.cloud.SpeechEvaluator
import com.iflytek.cloud.SpeechUtility

import org.json.JSONArray
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.io.File
import java.util.UUID
import io.dcloud.uts.console

class XunFeiPingCe {
    // 评测语种
    private var language: String? = null

    // 评测题型
    private var category: String? = null

    // 结果等级
    private var result_level: String? = null
    private var mIse: SpeechEvaluator? = null
    private var _contnt: Context? = null

    //测评结果分析
    private var mLastResult: String? = null

    // 测评状态，是否结束
    private var isEnd: Boolean? = null

    //需要测评的文本
    private var evaluationText: String? = null
    private var outPath: String? = null
	
	// 定义结束回调
	private var onResultback: ((String) -> Unit)? = null
	// 说话中的回调
	private var onVolumeback: ((Int) -> Unit)? = null
	

    // 评测监听接口
    private val mEvaluatorListener: EvaluatorListener = object : EvaluatorListener {
        override fun onResult(result: EvaluatorResult, isLast: Boolean) {
            if (isLast) {
                val builder = StringBuilder()
                builder.append(result.resultString)
                if (!TextUtils.isEmpty(builder)) {
                    mLastResult = builder.toString()
                }
                isEnd = true
                showTip("评测结束")
				showTip(parse)
				onResultback?.invoke(parse)
            }
        }

        override fun onError(error: SpeechError) {
            isEnd = true
            if (error != null) {
                showTip("error:" + error.errorCode + "," + error.errorDescription)
            } else {
				showTip("识别错误")
            }
			
			onResultback?.invoke(parse)
        }

        override fun onEvent(i: Int, i1: Int, i2: Int, bundle: Bundle) {}
        override fun onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
			showTip("用户可以开始语音输入")
        }

        override fun onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
			showTip("已经进入识别过程，不再接受语音输入")
        }

        override fun onVolumeChanged(volume: Int, data: ByteArray) {
            // showTip("当前音量：$volume")
			// showTip("返回音频数据：$data.size")
			
			onVolumeback?.invoke(volume)
        } 
		//        @Override
        //        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        //            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
        //            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
        //            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
        //            //		Log.d(TAG, "session id =" + sid);
        //            //	}
        //        }
    }

    // 设置评测试题
    fun setEvaText(_evaluationText: String) {
        evaluationText = _evaluationText
        var text: String? = ""
        if ("en_us" == language) {
            when (category) {
                "read_word", "read_sentence", "read_chapter" -> text = evaluationText
                "simple_expression", "picture_talk", "retell", "topic", "read_choice", "oral_translation" -> text =
                    String(
                        Base64.decode(evaluationText, Base64.DEFAULT)
                    )
            }
        } else {
            // 中文评测
            when (category) {
                "read_syllable", "read_chapter", "read_word", "read_sentence" -> text =
                    evaluationText
            }
        }
        mLastResult = null
    }
	
	fun onResult(_onCallback: (String) -> Unit) {
	    onResultback = _onCallback
	}
	fun onVolume(_onCallback: (Int) -> Unit) {
	    onVolumeback = _onCallback
	}
	
	

    fun init(startContext: Context?, _language: String?, _category: String?,_appid:String?) {

		if (mIse == null) {
		    val param = StringBuffer()
		    param.append("appid=$_appid")
		    param.append(",")
		    SpeechUtility.createUtility(startContext, param.toString())
		}
        


        isEnd = true
        _contnt = startContext
        // 设定语言
        language = _language
        //评测结果等级
        result_level = "complete"
        /*
           评测题型，可选值：
            read_syllable（单字，汉语专有）、read_word（词语）、
            read_sentence（句子）、read_chapter（篇章）
          */
        category = _category
		if (mIse == null) {
		    mIse = SpeechEvaluator.createEvaluator(_contnt, null)
		}
        //        evaluationText = "我们今天一起走";
        // setEvaText("我们今天一起走")
        showTip("初始化$language,$category,$_appid")
        //        start();
    }

    fun start() {
        if (!isEnd!!) {
            showTip("测评未结束")
            return
        }
        if (mIse == null) {
            return
        }
        showTip("开始")
        mLastResult = null
        setParams()
        val ret = mIse!!.startEvaluating(evaluationText, null, mEvaluatorListener)
        isEnd = false
        //以下方法为通过直接写入音频的方式进行评测业务
        /*if (ret != ErrorCode.SUCCESS) {
					showTip("识别失败,错误码：" + ret);
				} else {
					showTip(getString(R.string.text_begin_ise));
					byte[] audioData = FucUtil.readAudioFile(IseDemo.this,"isetest.wav");
					if(audioData != null) {
						//防止写入音频过早导致失败
						try{
							new Thread().sleep(100);
						}catch (InterruptedException e) {
							Log.d(TAG,"InterruptedException :"+e);
						}
						mIse.writeAudio(audioData,0,audioData.length);
						mIse.stopEvaluating();
					}
				}*/
    }

    fun stop() {
        if (mIse!!.isEvaluating) {
            showTip("评测已停止，等待结果中...")
            isEnd = true
            mIse!!.stopEvaluating()
        }
    }

    val parse: String
        get() {
            if (!TextUtils.isEmpty(mLastResult)) {
                // 拦截不支持的解析类型
                if ("complete" == result_level) {
                    if ("simple_expression" == category || "read_choice" == category || "topic" == category || "retell" == category || "picture_talk" == category || "oral_translation" == category) {
                        showTip("不支持解析该类型")
                        return ""
                    }
                }
                var result: String
                result = ""
                var json: JSONObject? = null
                try {
                    json = convert(mLastResult)
					json.put("outPath", outPath)
					json.put("type", category)
                    val jsonString = json.toString() // 缩进2空格格式化
                    // showTip(jsonString)
                    result = jsonString
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
                return result
            }
            return ""
        }

    private fun setParams() {
        // 设定录音文件，需要回调出去
        // outPath = _contnt!!.getExternalFilesDir("msc")!!.absolutePath + "/ise.wav"
        val randomFileName = "audio_${UUID.randomUUID()}.wav"
        outPath = _contnt!!.getExternalFilesDir("msc")!!.absolutePath + "/" + randomFileName
        val vad_bos = "100000"
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        val vad_eos = "100000"
        // 语音输入超时时间，即用户最多可以连续说多长时间；
//        String speech_timeout = pref.getString(SpeechConstant.KEY_SPEECH_TIMEOUT, "-1");
        val speech_timeout = "-1"

        // 设置流式版本所需参数 : ent sub plev
        if ("zh_cn" == language) {
            mIse!!.setParameter("ent", "cn_vip")
        }
        if ("en_us" == language) {
            mIse!!.setParameter("ent", "en_vip")
        }
        mIse!!.setParameter(SpeechConstant.SUBJECT, "ise")
        mIse!!.setParameter("plev", "0")

        // 设置评分百分制 使用 ise_unite  rst  extra_ability 参数
        mIse!!.setParameter("ise_unite", "1")
        mIse!!.setParameter("rst", "entirety")
        mIse!!.setParameter("extra_ability", "syll_phone_err_msg;pitch;multi_dimension")
        mIse!!.setParameter(SpeechConstant.LANGUAGE, language)
        mIse!!.setParameter(SpeechConstant.ISE_CATEGORY, category)
        mIse!!.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8")
        mIse!!.setParameter(SpeechConstant.VAD_BOS, vad_bos)
        mIse!!.setParameter(SpeechConstant.VAD_EOS, vad_eos)
        mIse!!.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, speech_timeout)
        mIse!!.setParameter(SpeechConstant.RESULT_LEVEL, result_level)
        mIse!!.setParameter(SpeechConstant.AUDIO_FORMAT_AUE, "opus")
        // 设置音频保存路径，保存音频格式支持pcm、wav，
        mIse!!.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
        mIse!!.setParameter(SpeechConstant.ISE_AUDIO_PATH, outPath)
        //通过writeaudio方式直接写入音频时才需要此设置
        //mIse.setParameter(SpeechConstant.AUDIO_SOURCE,"-1");
    }

    private fun showTip(str: String?) {
        // Log.d(TAG, "showTip: $str")

        // console.log(str)
    }

     /**
     * 清理音频文件夹下的所有音频文件
     * 用于避免占用过多用户存储空间
     */
    fun clearAudioFiles() {
        try {
            val audioDir = _contnt?.getExternalFilesDir("msc")
            audioDir?.let { dir ->
                if (dir.exists() && dir.isDirectory) {
                    val audioFiles = dir.listFiles { file ->
                        file.isFile && (file.name.endsWith(".wav") || 
                                       file.name.endsWith(".mp3") || 
                                       file.name.endsWith(".m4a") ||
                                       file.name.endsWith(".aac"))
                    }
                    audioFiles?.forEach { file ->
                        if (file.delete()) {
                            console.log("已删除音频文件: ${file.name}")
                        } else {
                            console.log("删除音频文件失败: ${file.name}")
                        }
                    }
                    console.log("音频文件清理完成，共删除 ${audioFiles?.size ?: 0} 个文件")
                }
            }
        } catch (e: Exception) {
            console.log("清理音频文件时发生错误: ${e.message}")
        }
    }

    @Throws(Exception::class)
    private fun convert(xmlString: String?): JSONObject {
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        val parser = factory.newPullParser()
        parser.setInput(StringReader(xmlString))
        parser.nextTag() // 移动到根元素的START_TAG
        val result = parseNode(parser)
        // 将根元素包装为JSONObject
        val rootJson = JSONObject()
        rootJson.put(parser.name, result)
        return rootJson
    }

    @Throws(Exception::class)
    private fun parseNode(parser: XmlPullParser): Any {
        val jsonObject = JSONObject()
        val attrCount = parser.attributeCount

        // 处理属性
        for (i in 0 until attrCount) {
            val attrName = parser.getAttributeName(i)
            val attrValue = parser.getAttributeValue(i)
            //            jsonObject.put("@" + attrName, attrValue);
            jsonObject.put(attrName, attrValue)
        }
        val childMap: MutableMap<String, Any> = HashMap()
        val content = StringBuilder()
        var eventType: Int
        while (parser.next().also { eventType = it } != XmlPullParser.END_TAG) {
            if (eventType == XmlPullParser.TEXT) {
                val text = parser.text.trim { it <= ' ' }
                if (!text.isEmpty()) {
                    content.append(text)
                }
            } else if (eventType == XmlPullParser.START_TAG) {
                val childName = parser.name
                val child = parseNode(parser) // 递归解析子节点

                // 处理同名子节点转换为数组
                if (childMap.containsKey(childName)) {
                    val existing = childMap[childName]
                    if (existing is JSONArray) {
                        existing.put(child)
                    } else {
                        val array = JSONArray()
                        array.put(existing)
                        array.put(child)
                        childMap[childName] = array
                    }
                } else {
                    childMap[childName] = child
                }
            }
        }

        // 添加子节点到当前JSON对象
        for ((key, value) in childMap) {
            jsonObject.put(key, value)
        }

        // 处理文本内容
        val textContent = content.toString().trim { it <= ' ' }
        if (!textContent.isEmpty()) {
            jsonObject.put("#text", textContent)
        }

        // 简化结构：仅有文本且无属性时返回纯文本
        return if (attrCount == 0 && childMap.isEmpty() && jsonObject.has("#text")) {
            jsonObject.optString("#text")
        } else jsonObject
    }

    companion object {
        private const val TAG = "GeSHi"
        private const val PREFER_NAME = "ise_settings"
    }
}
