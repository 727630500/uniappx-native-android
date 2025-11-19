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
open class GenComponentsUPlayPcmUPlayPcm : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var src: String by `$props`
    open var tplType: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsUPlayPcmUPlayPcm) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsUPlayPcmUPlayPcm
            val _cache = __ins.renderCache
            val ctx = createInnerAudioContext()
            val isPlay = ref(false)
            val props = __props
            watchEffect(fun(){
                ctx.src = props.src
                ctx.onCanplay(fun(_){})
                ctx.onEnded(fun(_res){
                    isPlay.value = false
                    ucsShare.setState("isPlay", true)
                }
                )
            }
            )
            val playVoice = fun(){
                if (ucsShare.getState("isPlay") == false) {
                    return
                }
                if (props.src == "") {
                    return
                }
                isPlay.value = true
                ctx.play()
                ucsShare.setState("isPlay", false)
            }
            onUnmounted(fun(){
                ctx.src = ""
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("style" to _nS(_uM("align-items" to "center")), "onClick" to playVoice), _uA(
                    if (_ctx.tplType == 1) {
                        _cE(Fragment, _uM("key" to 0), _uA(
                            _cE("image", _uM("style" to _nS(_uM("width" to "32.23rpx", "height" to "32.23rpx")), "src" to if (unref(isPlay)) {
                                "/static/ico/recording_play_new_active.png"
                            } else {
                                "/static/ico/recording_play_new.png"
                            }, "mode" to ""), null, 12, _uA(
                                "src"
                            )),
                            _cE("text", _uM("class" to "operation_text"), "播放录音")
                        ), 64)
                    } else {
                        _cE(Fragment, _uM("key" to 1), _uA(
                            _cE("image", _uM("style" to _nS(_uM("width" to "32.23rpx", "height" to "32.23rpx")), "src" to if (unref(isPlay)) {
                                "/static/ico/recording_play_new_active.png"
                            } else {
                                "/static/ico/recording_play_new.png"
                            }
                            , "mode" to ""), null, 12, _uA(
                                "src"
                            )),
                            _cE("text", _uM("class" to "operation_text", "style" to _nS(_uM("color" to "#C9D4FF"))), "播放录音", 4)
                        ), 64)
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
                return _uM("operation_text" to _pS(_uM("fontSize" to "14rpx", "marginTop" to "10rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("src" to _uM("type" to "String", "default" to fun(): String {
            return ""
        }
        ), "tplType" to _uM("type" to "Number", "default" to fun(): Number {
            return 1
        }
        )))
        var propsNeedCastKeys = _uA(
            "src",
            "tplType"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
