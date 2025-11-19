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
import uts.sdk.modules.limeAudioPlayer.createInnerAudioContext
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenComponentsUPlayMp3UPlayMp3 : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var src: String by `$props`
    open var id: Number by `$props`
    open var tplType: Number by `$props`
    open var isAuto: Boolean by `$props`
    open var uuid: Number by `$props`
    open var isPlayNum: Boolean by `$props`
    open var maxPlayNum: Number by `$props`
    open var disabled: Boolean by `$props`
    open var isNotIn: Boolean by `$props`
    open var playVoice: () -> Unit
        get() {
            return unref(this.`$exposed`["playVoice"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "playVoice", value)
        }
    open var endPlay: () -> Unit
        get() {
            return unref(this.`$exposed`["endPlay"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "endPlay", value)
        }
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsUPlayMp3UPlayMp3, _arg1: SetupContext) -> Any? = fun(__props, ref1): Any? {
            var __expose = ref1.expose
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsUPlayMp3UPlayMp3
            val _cache = __ins.renderCache
            val ctx = createInnerAudioContext()
            val isPlay = ref(false)
            val progressPercent = ref(0)
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            fun gen_emitStatus_fn(isPlaying: Boolean) {
                emit("statusChange", isPlaying)
            }
            val emitStatus = ::gen_emitStatus_fn
            val maxPlayNumSave = ref(0)
            watchEffect(fun(){
                ucsShare.setState("isPlay", true)
                isPlay.value = false
                ctx.src = props.src
            }
            )
            ctx.onCanplay(fun(_){
                if (props.isAuto) {
                    ucsShare.setState("isPlay", false)
                    emitStatus(true)
                    ctx.play()
                }
            }
            )
            ctx.onEnded(fun(_res){
                isPlay.value = false
                ucsShare.setState("isPlay", true)
                maxPlayNumSave.value++
                emit("playEnd")
                emitStatus(false)
            }
            )
            fun gen_endPlay_fn() {
                ucsShare.setState("isPlay", true)
                ctx.pause()
                isPlay.value = false
                emitStatus(false)
            }
            val endPlay = ::gen_endPlay_fn
            fun gen_playVoice_fn(): Unit {
                if (props.src == "") {
                    return
                }
                if (props.id != 0) {
                    var MaxMap = uni_getStorageSync("maxMap")
                    if (MaxMap != "") {
                        var map = JSON.parse<UTSJSONObject>(MaxMap as String)
                        var mapNum = map?.getNumber(props.id.toString(10))
                        if (mapNum != null) {
                            if (mapNum < props.maxPlayNum) {
                                uni_showToast(ShowToastOptions(title = "\u8FD8\u53EF\u64AD\u653E" + (props.maxPlayNum - mapNum - 1) + "\u6B21", icon = "none"))
                            }
                            if (mapNum >= props.maxPlayNum) {
                                uni_showToast(ShowToastOptions(title = "\u8FD8\u53EF\u64AD\u653E" + (props.maxPlayNum - mapNum) + "\u6B21", icon = "none"))
                                return
                            }
                        }
                    }
                }
                if (props.isPlayNum && props.id == 0) {
                    if (props.maxPlayNum <= 0) {
                        uni_showToast(ShowToastOptions(title = "\u8FD8\u53EF\u64AD\u653E0\u6B21", icon = "none"))
                        return
                    }
                }
                if (props.isPlayNum && !props.isNotIn && props.id == 0) {
                    if (maxPlayNumSave.value < props.maxPlayNum) {
                        uni_showToast(ShowToastOptions(title = "\u8FD8\u53EF\u64AD\u653E" + (props.maxPlayNum - maxPlayNumSave.value - 1) + "\u6B21", icon = "none"))
                    }
                    if (maxPlayNumSave.value >= props.maxPlayNum) {
                        uni_showToast(ShowToastOptions(title = "\u8FD8\u53EF\u64AD\u653E" + (props.maxPlayNum - maxPlayNumSave.value) + "\u6B21", icon = "none"))
                        return
                    }
                }
                if (props.isNotIn && props.id == 0) {
                    uni_showToast(ShowToastOptions(title = "\u8FD8\u53EF\u64AD\u653E" + (props.maxPlayNum - 1) + "\u6B21", icon = "none"))
                }
                isPlay.value = true
                ctx.play()
                emitStatus(true)
                var MaxMap2 = uni_getStorageSync("maxMap")
                if (MaxMap2 != "") {
                    var map = JSON.parse<UTSJSONObject>(MaxMap2 as String ?: "{}") ?: UTSJSONObject()
                    var mapNum = map.getNumber(props.id.toString(10))
                    if (mapNum == null) {
                        (map as UTSJSONObject)[props.id.toString(10)] = 1
                        uni_showToast(ShowToastOptions(title = "\u8FD8\u53EF\u64AD\u653E" + (props.maxPlayNum - 1) + "\u6B21", icon = "none"))
                    } else {
                        (map as UTSJSONObject)[props.id.toString(10)] = mapNum + 1
                    }
                    uni_setStorageSync("maxMap", JSON.stringify(map))
                } else {
                    var map = UTSJSONObject()
                    map[props.id.toString(10)] = 1
                    uni_showToast(ShowToastOptions(title = "\u8FD8\u53EF\u64AD\u653E" + (props.maxPlayNum - 1) + "\u6B21", icon = "none"))
                    uni_setStorageSync("maxMap", JSON.stringify(map))
                }
                ucsShare.setState("isPlay", false)
            }
            val playVoice = ::gen_playVoice_fn
            fun gen_playVoiceFn_fn() {
                emit("click")
                if (props.disabled) {
                    return
                }
                if (isPlay.value || ucsShare.getState("isPlay") == false) {
                    uni_showToast(ShowToastOptions(title = "\u6B63\u5728\u64AD\u653E\u542C\u529B\u97F3\u9891\uFF0C\u8BF7\u7A0D\u540E", icon = "none"))
                    return
                }
                if (ucsShare.getState("isPlay") == false) {
                    return
                }
                playVoice()
            }
            val playVoiceFn = ::gen_playVoiceFn_fn
            onUnmounted(fun(){
                ucsShare.setState("isPlay", true)
                ctx.src = ""
                ctx.destroy()
            }
            )
            ctx.onTimeUpdate(fun(_){})
            __expose(_uM("playVoice" to playVoice, "endPlay" to endPlay))
            return fun(): Any? {
                return _cE("view", _uM("style" to _nS(_uM("align-items" to "center")), "onClick" to playVoiceFn), _uA(
                    if (_ctx.tplType == 1) {
                        _cE(Fragment, _uM("key" to 0), _uA(
                            _cE("image", _uM("style" to _nS(_uM("width" to "32.23rpx", "height" to "32.23rpx")), "src" to if (unref(isPlay)) {
                                "/static/ico/play_new_active.png"
                            } else {
                                "/static/ico/play_new.png"
                            }, "mode" to ""), null, 12, _uA(
                                "src"
                            )),
                            _cE("text", _uM("class" to "operation_text"), "播放听力")
                        ), 64)
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (_ctx.tplType == 2) {
                        _cE(Fragment, _uM("key" to 1), _uA(
                            _cE("image", _uM("style" to _nS(_uM("width" to "32.23rpx", "height" to "32.23rpx")), "src" to if (unref(isPlay)) {
                                "/static/ico/play_new_active.png"
                            } else {
                                "/static/ico/play_new.png"
                            }, "mode" to ""), null, 12, _uA(
                                "src"
                            )),
                            _cE("text", _uM("class" to "operation_text", "style" to _nS(_uM("color" to "#C9D4FF"))), "播放听力", 4)
                        ), 64)
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (_ctx.tplType == 3) {
                        _cE("view", _uM("key" to 2, "class" to "play-btn_box"), _uA(
                            _cE("image", _uM("src" to "/static/ico/speaker.png", "class" to "play-btn_icon", "mode" to "")),
                            _cE("view", _uM("class" to "play-btn"), _uA(
                                _cE("text", _uM("class" to "play-btn_text"), "播放听力")
                            ))
                        ))
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (_ctx.tplType == 4) {
                        _cE("view", _uM("key" to 3, "style" to _nS(_uM("position" to "relative", "width" to "116rpx", "height" to "29rpx"))), _uA(
                            _cE("image", _uM("src" to "/static/ico/play4.png", "style" to _nS(_uM("width" to "100%", "height" to "100%", "position" to "absolute")), "mode" to ""), null, 4),
                            _cE("view", _uM("style" to _nS(_uM("position" to "relative", "height" to "100%", "width" to "100%", "flex-direction" to "row"))), _uA(
                                _cE("text", _uM("style" to _nS(_uM("flex" to "1", "margin-left" to "30rpx", "text-align" to "center", "color" to "#3A58EB", "font-size" to "14rpx", "line-height" to "29rpx"))), "播放听力", 4)
                            ), 4)
                        ), 4)
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (_ctx.tplType == 5) {
                        _cE("view", _uM("key" to 4, "style" to _nS(_uM("position" to "relative", "width" to "21.09rpx", "height" to "18.16rpx"))), _uA(
                            _cE("image", _uM("src" to "/static/ico/horn.png", "style" to _nS(_uM("width" to "100%", "height" to "100%", "position" to "absolute")), "mode" to ""), null, 4)
                        ), 4)
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (_ctx.tplType == 6) {
                        _cE("view", _uM("key" to 5, "class" to "tpl-type6-btn"), _uA(
                            _cE("image", _uM("class" to "play-icon", "src" to "/static/ico/speaker2.png", "mode" to "")),
                            _cE("view", null, _uA(
                                _cE("text", _uM("class" to "text"), "播放听力")
                            ))
                        ))
                    } else {
                        _cC("v-if", true)
                    }
                ), 4)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("operation_text" to _pS(_uM("fontSize" to "14rpx", "marginTop" to "10rpx")), "play-btn_box" to _pS(_uM("width" to "116rpx", "height" to "48rpx", "position" to "relative", "alignItems" to "flex-end", "justifyContent" to "flex-end")), "play-btn_icon" to _uM(".play-btn_box " to _uM("width" to "42.77rpx", "height" to "42.77rpx", "position" to "absolute", "left" to 0, "bottom" to 0, "zIndex" to 2)), "play-btn" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "position" to "relative", "width" to "116rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "33rpx", "borderTopRightRadius" to "33rpx", "borderBottomRightRadius" to "33rpx", "borderBottomLeftRadius" to "33rpx")), "play-btn_text" to _uM(".play-btn " to _uM("fontFamily" to "Arial, Arial", "fontWeight" to "400", "fontSize" to "14rpx", "color" to "#3A58EB", "paddingLeft" to "18rpx")), "tpl-type6-btn" to _pS(_uM("width" to "116rpx", "height" to "29rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to "999rpx", "borderTopRightRadius" to "999rpx", "borderBottomRightRadius" to "999rpx", "borderBottomLeftRadius" to "999rpx")), "play-icon" to _uM(".tpl-type6-btn " to _uM("width" to "29rpx", "height" to "29rpx", "marginRight" to "4rpx")), "text" to _uM(".tpl-type6-btn " to _uM("fontSize" to "14rpx", "color" to "#3A58EB")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("playEnd" to null, "click" to null, "statusChange" to null)
        var props = _nP(_uM("src" to _uM("type" to "String", "default" to fun(): String {
            return ""
        }
        ), "id" to _uM("type" to "Number", "default" to fun(): Number {
            return 0
        }
        ), "tplType" to _uM("type" to "Number", "default" to fun(): Number {
            return 1
        }
        ), "isAuto" to _uM("type" to "Boolean", "default" to fun(): Boolean {
            return false
        }
        ), "uuid" to _uM("type" to "Number", "default" to fun(): Number {
            return 0
        }
        ), "isPlayNum" to _uM("type" to "Boolean", "default" to fun(): Boolean {
            return false
        }
        ), "maxPlayNum" to _uM("type" to "Number", "default" to fun(): Number {
            return 0
        }
        ), "disabled" to _uM("type" to "Boolean", "default" to fun(): Boolean {
            return false
        }
        ), "isNotIn" to _uM("type" to "Boolean", "default" to fun(): Boolean {
            return false
        }
        )))
        var propsNeedCastKeys = _uA(
            "src",
            "id",
            "tplType",
            "isAuto",
            "uuid",
            "isPlayNum",
            "maxPlayNum",
            "disabled",
            "isNotIn"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
