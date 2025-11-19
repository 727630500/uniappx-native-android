@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uts.sdk.modules.wjKdxf
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import io.dcloud.uniapp.*
import io.dcloud.uniapp.extapi.*
import io.dcloud.uniapp.framework.*
import io.dcloud.uniapp.runtime.*
import io.dcloud.uniapp.vue.*
import io.dcloud.uniapp.vue.shared.*
import io.dcloud.uts.*
import io.dcloud.uts.Map
import io.dcloud.uts.Set
import io.dcloud.uts.UTSAndroid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import uts.sdk.modules.wjKdxf.XunFeiPingCe
import uts.sdk.modules.wjKdxf.audioRecorder
import uts.sdk.modules.wjKdxf.distinguish
import uts.sdk.modules.wjKdxf.pcmPlayer
typealias MyApiErrorCode = Number
val MyAPIErrors: Map<MyApiErrorCode, String> = Map(_uA(
    _uA(
        9010001,
        "custom error mseeage1"
    ),
    _uA(
        9010002,
        "custom error mseeage2"
    )
))
open class dRecorderManager {
    private var sampleRate = 8000 as Int
    private var channelConfig = AudioFormat.CHANNEL_IN_MONO
    private var audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private var isRecording = true
    private var bufferSize = AudioRecord.getMinBufferSize(this.sampleRate, this.channelConfig, this.audioFormat)
    private var timer: Number = 0
    private var _audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC, this.sampleRate, this.channelConfig, this.audioFormat, this.bufferSize)
    constructor(){}
    open fun startRecorder(callback: (res: String) -> Unit, path: String) {
        val _distinguish = distinguish()
        _distinguish.init(UTSAndroid.getAppContext(), path, fun(_res: String) {
            callback(_res)
        }
        )
        _distinguish.runAsr()
    }
}
fun createRecorderManager(): dRecorderManager {
    return dRecorderManager()
}
open class crluyin {
    private var sampleRate: audioRecorder
    constructor(){
        this.sampleRate = audioRecorder()
    }
    open fun _init(path: String) {
        this.sampleRate.init(path)
    }
    open fun start() {
        this.sampleRate.startRecording()
    }
    open fun stop() {
        this.sampleRate.stopRecording()
    }
}
fun newCrluyin(): crluyin {
    return crluyin()
}
open class layPcmAudio {
    private var sampleRate: pcmPlayer
    constructor(){
        this.sampleRate = pcmPlayer()
    }
    open fun play(path: String) {
        this.sampleRate.playPcmAudio(path)
    }
    open fun stop() {
        this.sampleRate.stop()
    }
}
fun newPlayPcmAudio(): layPcmAudio {
    return layPcmAudio()
}
open class evaluating {
    private var xunFeiPingCe: XunFeiPingCe
    constructor(){
        this.xunFeiPingCe = XunFeiPingCe()
    }
    open fun init(setting: UTSJSONObject = UTSJSONObject()) {
        var languageMap: UTSJSONObject = object : UTSJSONObject() {
            var 英语 = "en_us"
            var 汉语 = "zh_cn"
        }
        var categoryMap: UTSJSONObject = object : UTSJSONObject() {
            var 单字 = "read_syllable"
            var 词语 = "read_word"
            var 句子 = "read_sentence"
            var 文章 = "read_chapter"
        }
        var _language = "zh_cn"
        var ___language = setting.getString("language")
        if (___language != null) {
            var _val = languageMap.getString(___language)
            if (_val != null) {
                _language = _val
            }
        }
        var _category = "read_sentence"
        var ___category = setting.getString("category")
        if (___category != null) {
            var _val = categoryMap.getString(___category)
            if (_val != null) {
                _category = _val
            }
        }
        this.xunFeiPingCe.init(UTSAndroid.getAppContext(), _language, _category, "72aafa2f")
    }
    open fun start() {
        this.xunFeiPingCe.start()
    }
    open fun stop() {
        this.xunFeiPingCe.stop()
    }
    open fun setEvaText(str: String?) {
        if (str != null) {
            this.xunFeiPingCe.setEvaText(str)
        }
    }
    open fun clearAudioFiles() {
        this.xunFeiPingCe.clearAudioFiles()
    }
    open fun getParse() {
        var _parse = this.xunFeiPingCe.parse
        console.log(_parse)
    }
    open fun onResult(callback: (res: UTSJSONObject) -> Unit) {
        this.xunFeiPingCe.onResult(fun(_res: String) {
            if (_res == "") {
                callback(UTSJSONObject())
                return
            }
            var returnData: UTSJSONObject = UTSJSONObject()
            var _result = JSON.parse(_res) as UTSJSONObject
            var _outPath = _result.getString("outPath")
            var _xml_result = _result.getJSON("xml_result")
            var _category = _result.getString("type")
            returnData.set("outPath", _outPath ?: "")
            if (_xml_result != null) {
                var ret0 = UTSJSONObject.keys(_xml_result)
                var _data = _xml_result.getJSON(ret0[0])
                if (_data != null) {
                    var ret1 = UTSJSONObject.keys(_data)
                    var _data2 = _data.getJSON("rec_paper")
                    if (_data2 != null) {
                        var ret2 = UTSJSONObject.keys(_data2)
                        var __data = _data2.getJSON(ret2[0])
                        if (__data != null) {
                            returnData.set("xml_result", __data)
                        }
                    }
                }
            }
            callback(returnData)
        }
        )
    }
    open fun onVolume(callback: (res: Int) -> Unit) {
        this.xunFeiPingCe.onVolume(fun(_res: Int) {
            if (_res == 30) {
                callback(100)
                return
            }
            if (_res == 0) {
                callback(0)
                return
            }
            callback((_res * 3.33).toInt())
        }
        )
    }
}
fun newEvaluating(): evaluating {
    return evaluating()
}
