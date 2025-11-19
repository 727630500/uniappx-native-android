@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uni.UNIF47E312
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
import io.dcloud.uniapp.extapi.getSystemInfoSync as uni_getSystemInfoSync
import uts.sdk.modules.wjKdxf.newEvaluating
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.uploadFile as uni_uploadFile
open class GenComponentsRecognitionRecognition : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var language: String by `$props`
    open var category: String by `$props`
    open var subject: String by `$props`
    open var seconds: Number by `$props`
    open var intIsShowStat: Boolean by `$props`
    open var scale: Number by `$props`
    open var isDisable: Boolean by `$props`
    open var startRecord: () -> Unit
        get() {
            return unref(this.`$exposed`["startRecord"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "startRecord", value)
        }
    open var endRecord: () -> Unit
        get() {
            return unref(this.`$exposed`["endRecord"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "endRecord", value)
        }
    open var isStart: Boolean
        get() {
            return unref(this.`$exposed`["isStart"]) as Boolean
        }
        set(value) {
            setRefValue(this.`$exposed`, "isStart", value)
        }
    open var startRecord2: () -> Unit
        get() {
            return unref(this.`$exposed`["startRecord2"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "startRecord2", value)
        }
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsRecognitionRecognition, _arg1: SetupContext) -> Any? = fun(__props, ref1): Any? {
            var __expose = ref1.expose
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsRecognitionRecognition
            val _cache = __ins.renderCache
            val loading = ref(false)
            val isEnd = ref(false)
            val props = __props
            val sourceNum = ref(0)
            val isShowStat = ref(true)
            val timer = ref(0)
            val dWidth = computed(fun(): String {
                var screenWidth = uni_getSystemInfoSync().screenWidth
                var rpx: Number = 82
                return ((screenWidth * rpx) / 750).toString(10)
            }
            )
            fun gen_evalStat_fn(max: Number): String {
                if (sourceNum.value >= max) {
                    return "/static/ico/armmar_stat.png"
                }
                return "/static/ico/armmar_stat_fail.png"
            }
            val evalStat = ::gen_evalStat_fn
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val isStart = ref(false)
            val rec_box = ref<UniElement?>(null)
            var _crLuyin = newEvaluating()
            fun gen_normalizePath_fn(p: String): String {
                if (p == null || p == "") {
                    return p
                }
                return if (p.startsWith("file://")) {
                    p.replace("file://", "")
                } else {
                    p
                }
            }
            val normalizePath = ::gen_normalizePath_fn
            fun gen_uploadRecording_fn(localPath: String): UTSPromise<String> {
                return UTSPromise(fun(resolve, reject){
                    val realPath = normalizePath(localPath)
                    if (realPath == null || realPath == "") {
                        reject(object : UTSJSONObject() {
                            var message = "本地路径为空"
                        })
                        return
                    }
                    uni_uploadFile(UploadFileOptions(url = getUrl("/api/general/upload"), filePath = realPath, name = "file", formData = UTSJSONObject(), header = getHeader(), success = fun(res) {
                        try {
                            val responseData = JSON.parse(res.data) as UTSJSONObject
                            resolve(responseData["data"] as String)
                        }
                         catch (e: Throwable) {
                            reject(e)
                        }
                    }
                    , fail = fun(err) {
                        reject(err)
                    }
                    ))
                }
                )
            }
            val uploadRecording = ::gen_uploadRecording_fn
            _crLuyin.onResult(fun(result: UTSJSONObject){
                ucsShare.setState("isPlay", true)
                isEnd.value = true
                rec_box.value?.style?.setProperty("height", "0%")
                loading.value = false
                if (UTSJSONObject.keys(result).length == 0) {
                    uni_showToast(ShowToastOptions(title = "识别错误，请联系管理员", icon = "error"))
                    return
                }
                var _outPath = result.getString("outPath")
                var _xml_result = result.getJSON("xml_result")
                if (_outPath != null && _xml_result != null) {
                    var _data = _xml_result
                    if (_data != null) {
                        var _source = _data.getString("total_score") ?: "0"
                        var _sourceNum = parseInt(_source)
                        sourceNum.value = _sourceNum
                        isShowStat.value = true
                        loading.value = true
                        uploadRecording(_outPath).then(fun(remoteUrl: String){
                            loading.value = false
                            emit("success", recognitionSuccessType(path = remoteUrl, result = _data))
                        }
                        )
                    }
                }
            }
            )
            _crLuyin.onVolume(fun(result: Int){
                if (!isEnd.value) {
                    rec_box.value?.style?.setProperty("height", "" + result + "%")
                } else {
                    rec_box.value?.style?.setProperty("height", "0%")
                }
            }
            )
            watchEffect(fun(){
                var _language = props.language
                var _subject = props.subject
                var _category = props.category
                var _sc = props.seconds
                if (_language != null && _subject != null && _category != null) {
                    _crLuyin.init(object : UTSJSONObject() {
                        var language = _language
                        var category = _category
                    })
                    _crLuyin.setEvaText(_subject)
                }
                if (_sc > 0) {
                }
                isShowStat.value = false
            }
            )
            fun gen_startRecord_fn() {
                if (props.subject == "") {
                    return
                }
                if (ucsShare.getState("debug") as Boolean != true) {
                    if (ucsShare.getState("isPlay") == false) {
                        return
                    }
                }
                ucsShare.setState("isPlay", false)
                if (props.seconds > 0) {
                }
                isEnd.value = false
                isStart.value = true
                uni_showToast(ShowToastOptions(title = "再次点击结束", icon = "none"))
                isShowStat.value = false
                if (ucsShare.getState("debug") as Boolean) {
                    return
                }
                setTimeout(fun(){
                    _crLuyin.start()
                }
                , 0)
            }
            val startRecord = ::gen_startRecord_fn
            fun gen_startRecord2_fn() {
                if (props.subject == "") {
                    return
                }
                if (ucsShare.getState("debug") as Boolean != true) {
                    if (ucsShare.getState("isPlay") == false) {
                        return
                    }
                }
                ucsShare.setState("isPlay", false)
                if (isStart.value == true) {
                    return
                }
                if (props.seconds > 0) {
                }
                isEnd.value = false
                isShowStat.value = false
                setTimeout(fun(){
                    _crLuyin.start()
                }
                , 0)
                isStart.value = true
            }
            val startRecord2 = ::gen_startRecord2_fn
            val endRecord = fun(){
                if (!isEnd.value) {
                    loading.value = true
                }
                isEnd.value = false
                isStart.value = false
                if (ucsShare.getState("debug") as Boolean) {
                    emit("success", recognitionSuccessType(path = "", result = UTSJSONObject()))
                    loading.value = false
                    isStart.value = false
                    ucsShare.setState("isPlay", true)
                    return
                }
                setTimeout(fun(){
                    _crLuyin.stop()
                }
                , 0)
                rec_box.value?.style?.setProperty("height", "0%")
            }
            val modelVal = ref(0)
            watch(modelVal, fun(kVal: Number){
                if (kVal == 100) {
                    endRecord()
                }
            }
            )
            val switchFn = fun(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w@{
                        if (props.isDisable) {
                            return@w
                        }
                        if (!getLy()) {
                            try {
                                await(jurisdictionLy())
                                uni_showToast(ShowToastOptions(title = "请再次点击按钮开始录音"))
                            }
                             catch (err: Throwable) {
                                uni_showToast(ShowToastOptions(title = "没有麦克风权限,请手动开"))
                            }
                            return@w
                        }
                        if (isStart.value) {
                            endRecord()
                        } else {
                            startRecord()
                        }
                })
            }
            __expose(_uM("startRecord" to startRecord, "endRecord" to endRecord, "isStart" to isStart, "startRecord2" to startRecord2))
            return fun(): Any? {
                val _component_l_circle = resolveEasyComponent("l-circle", GenUniModulesLimeCircleComponentsLCircleLCircleClass)
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_l_loading = resolveEasyComponent("l-loading", GenUniModulesLimeLoadingComponentsLLoadingLLoadingClass)
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                return _cE(Fragment, null, _uA(
                    _cE("view", _uM("style" to _nS(_uA(
                        _uM("height" to "135.94rpx", "width" to "120rpx", "position" to "relative", "justify-content" to "flex-end", "align-items" to "center"),
                        "transform:scale(" + _ctx.scale + ")"
                    ))), _uA(
                        if (isTrue(unref(isShowStat) && props.intIsShowStat)) {
                            _cE("text", _uM("key" to 0, "style" to _nS(_uM("color" to "#FA9600", "font-size" to "17.58rpx"))), _tD(unref(sourceNum)) + "分 ", 5)
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(isShowStat) && props.intIsShowStat)) {
                            _cE("view", _uM("key" to 1, "style" to _nS(_uM("flex-direction" to "row", "position" to "relative", "width" to "100%", "height" to "51rpx", "margin-bottom" to "-24.86rpx"))), _uA(
                                _cE("image", _uM("src" to evalStat(20), "mode" to "", "class" to "armmar armmar1"), null, 8, _uA(
                                    "src"
                                )),
                                _cE("image", _uM("src" to evalStat(40), "mode" to "", "class" to "armmar armmar2"), null, 8, _uA(
                                    "src"
                                )),
                                _cE("image", _uM("src" to evalStat(60), "mode" to "", "class" to "armmar armmar3"), null, 8, _uA(
                                    "src"
                                )),
                                _cE("image", _uM("src" to evalStat(80), "mode" to "", "class" to "armmar armmar4"), null, 8, _uA(
                                    "src"
                                )),
                                _cE("image", _uM("src" to evalStat(100), "mode" to "", "class" to "armmar armmar5"), null, 8, _uA(
                                    "src"
                                ))
                            ), 4)
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        _cE("view", _uM("style" to _nS("width: " + (parseInt(unref(dWidth)) + 1) + "px;height: " + (parseInt(unref(dWidth)) + 1) + "px;;position: relative;")), _uA(
                            if (isTrue(!props.isDisable)) {
                                _cE(Fragment, _uM("key" to 0), _uA(
                                    if (isTrue(unref(isStart))) {
                                        _cV(_component_l_circle, _uM("key" to 0, "current" to unref(modelVal), "onUpdate:current" to fun(`$event`: Number){
                                            trySetRefValue(modelVal, `$event`)
                                        }, "duration" to if (unref(isStart)) {
                                            props.seconds * 1000
                                        } else {
                                            0
                                        }, "percent" to 100, "size" to unref(dWidth)), null, 8, _uA(
                                            "current",
                                            "duration",
                                            "size"
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    _cV(_component_BackgroundImage, _uM("style" to _nS("position: absolute;top:2px;left: 2px;width: " + (parseInt(unref(dWidth)) - 4) + "px;height: " + (parseInt(unref(dWidth)) - 4) + "px;"), "src" to "/static/ico/recording_bg.png", "bgStyle" to ("width: " + (parseInt(unref(dWidth)) - 4) + "px;height: " + (parseInt(unref(dWidth)) - 4) + "px;")), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("view", _uM("class" to "rec_box"), _uA(
                                                _cE("image", _uM("style" to _nS(_uM("width" to "40.43rpx", "height" to "50.71rpx", "z-index" to "99")), "src" to "/static/ico/recording2.png", "mode" to "", "onClick" to switchFn), null, 12, _uA(
                                                    "src"
                                                )),
                                                _cE("view", _uM("class" to "rec_bg", "ref_key" to "rec_box", "ref" to rec_box), null, 512)
                                            ))
                                        )
                                    }), "_" to 1), 8, _uA(
                                        "style",
                                        "bgStyle"
                                    ))
                                ), 64)
                            } else {
                                _cE("view", _uM("key" to 1), _uA(
                                    _cE("image", _uM("src" to "/static/ico/dis.png", "mode" to "", "style" to _nS("width: " + (parseInt(unref(dWidth)) + 1) + "px;height: " + (parseInt(unref(dWidth)) + 1) + "px;;")), null, 4)
                                ))
                            }
                        ), 4)
                    ), 4),
                    if (isTrue(unref(loading))) {
                        _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cE("view", _uM("style" to _nS(_uM("position" to "relative", "left" to "50%", "top" to "50%", "transform" to "translate(-50%,-50%)", "justify-content" to "center", "align-items" to "center"))), _uA(
                                    _cV(_component_l_loading, _uM("color" to "#AEC3FC", "vertical" to true, "textSize" to "20rpx", "textColor" to "#fff", "size" to "40rpx", "text" to "识别中…"))
                                ), 4)
                            )
                        }), "_" to 1))
                    } else {
                        _cC("v-if", true)
                    }
                ), 64)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("rec_box" to _pS(_uM("position" to "absolute", "top" to 0, "left" to 0, "width" to "100%", "height" to "100%", "alignItems" to "center", "justifyContent" to "center", "borderTopLeftRadius" to "90rpx", "borderTopRightRadius" to "90rpx", "borderBottomRightRadius" to "90rpx", "borderBottomLeftRadius" to "90rpx")), "rec_bg" to _pS(_uM("backgroundImage" to "linear-gradient(to bottom, #D4E2FF, #43F4BC)", "position" to "absolute", "bottom" to 0, "left" to 0, "width" to "100%", "height" to "0%", "transitionProperty" to "width", "transitionDuration" to "1s")), "armmar" to _uM("" to _uM("width" to "17.58rpx", "height" to "17.58rpx"), ".armmar1" to _uM("position" to "absolute", "left" to "2rpx", "bottom" to "2rpx", "transform" to "rotate(20deg)"), ".armmar2" to _uM("position" to "absolute", "left" to "24rpx", "bottom" to "24rpx", "transform" to "rotate(30deg)"), ".armmar3" to _uM("position" to "absolute", "left" to "51.21rpx", "top" to 0), ".armmar4" to _uM("position" to "absolute", "right" to "24rpx", "bottom" to "24rpx", "transform" to "rotate(-30deg)"), ".armmar5" to _uM("position" to "absolute", "right" to "2rpx", "bottom" to "2rpx", "transform" to "rotate(-20deg)")), "@TRANSITION" to _uM("rec_bg" to _uM("property" to "width", "duration" to "1s")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("success" to null)
        var props = _nP(_uM("language" to _uM("type" to "String", "required" to true, "default" to ""), "category" to _uM("type" to "String", "required" to true, "default" to ""), "subject" to _uM("type" to "String", "required" to true, "default" to ""), "seconds" to _uM("type" to "Number", "required" to true, "default" to 5), "intIsShowStat" to _uM("type" to "Boolean", "required" to true, "default" to true), "scale" to _uM("type" to "Number", "required" to true, "default" to 1), "isDisable" to _uM("type" to "Boolean", "required" to true, "default" to false)))
        var propsNeedCastKeys = _uA(
            "language",
            "category",
            "subject",
            "seconds",
            "intIsShowStat",
            "scale",
            "isDisable"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
