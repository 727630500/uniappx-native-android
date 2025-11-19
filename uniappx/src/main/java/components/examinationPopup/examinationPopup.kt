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
open class GenComponentsExaminationPopupExaminationPopup : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var type: Number by `$props`
    open var scoreVal: Number by `$props`
    open var isShowResult: Boolean by `$props`
    open var show: Boolean by `$props`
    open var isTraining: Boolean by `$props`
    open var tpl: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsExaminationPopupExaminationPopup) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsExaminationPopupExaminationPopup
            val _cache = __ins.renderCache
            val ctx = createInnerAudioContext()
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val showJb = ref(true)
            val props = __props
            val backgroundImage = computed(fun(): String {
                return if (props.type == 2) {
                    "/static/ico/test/success.png"
                } else {
                    "/static/ico/test/fail.png"
                }
            }
            )
            val messageText = computed(fun(): String {
                return if (props.type == 2) {
                    "恭喜你！闯关成功"
                } else {
                    "很遗憾，继续加油"
                }
            }
            )
            ctx.onEnded(fun(_res){
                console.log("bofjieshu")
                showJb.value = false
            }
            )
            ctx.onCanplay(fun(_){
                ctx.play()
            }
            )
            watchEffect(fun(){
                if (props.show) {
                    if (props.tpl == 1) {
                        if (props.type == 2) {
                            ctx.src = getSondUrl("闯关通过提示音")
                        } else {
                            ctx.src = getSondUrl("闯关失败提示音")
                        }
                    }
                }
            }
            )
            fun gen_hide_fn() {
                emit("update:isShowResult", true)
                emit("update:show", false)
                emit("close")
            }
            val hide = ::gen_hide_fn
            return fun(): Any? {
                return _cE(Fragment, null, _uA(
                    if (isTrue(props.show)) {
                        _cE("scroll-view", _uM("key" to 0, "direction" to "vertical", "style" to _nS(_uM("position" to "fixed", "top" to "0", "left" to "0", "width" to "750rpx", "height" to "100%"))), _uA(
                            _cE("view", _uM("style" to _nS(_uM("height" to "100%"))), _uA(
                                _cE("view", _uM("style" to _nS(_uM("width" to "750rpx", "height" to "100%"))), _uA(
                                    if (_ctx.tpl == 1) {
                                        _cE(Fragment, _uM("key" to 0), _uA(
                                            _cE("image", _uM("src" to unref(backgroundImage), "mode" to "", "style" to _nS(_uM("width" to "100%", "height" to "100%"))), null, 12, _uA(
                                                "src"
                                            )),
                                            _cE("view", _uM("style" to _nS(_uM("position" to "absolute", "left" to "0", "top" to "0", "width" to "100%", "height" to "100%", "align-items" to "center", "justify-content" to "center"))), _uA(
                                                _cE("image", _uM("src" to "/static/ico/close.png", "class" to "close_ico", "style" to _nS(_uM("z-index" to "9")), "mode" to "", "onClick" to fun(){
                                                    hide()
                                                }), null, 12, _uA(
                                                    "onClick"
                                                )),
                                                _cE("text", _uM("class" to "fail_text", "style" to _nS(_uM("margin-top" to "80rpx"))), _tD(unref(messageText)), 5),
                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "flex-end", "margin-top" to "10rpx"))), _uA(
                                                    _cE("text", _uM("class" to "source", "style" to _nS(_uM("color" to if (props.type == 2) {
                                                        "#3A58EB"
                                                    } else {
                                                        ""
                                                    }))), _tD(_ctx.scoreVal), 5),
                                                    _cE("text", _uM("class" to "source", "style" to _nS(_uM("fontSize" to "29rpx", "marginBottom" to "6rpx", "color" to if (props.type == 2) {
                                                        "#3A58EB"
                                                    } else {
                                                        ""
                                                    }))), "分", 4)
                                                ), 4),
                                                _cE("text", _uM("class" to "result_btn", "style" to _nS(_uM("margin-top" to "10rpx")), "onClick" to fun(){
                                                    hide()
                                                }), "查看结果", 12, _uA(
                                                    "onClick"
                                                ))
                                            ), 4)
                                        ), 64)
                                    } else {
                                        _cE("view", _uM("key" to 1, "style" to _nS(_uM("position" to "absolute", "left" to "0", "top" to "0", "width" to "100%", "align-items" to "center", "justify-content" to "center")), "class" to "bg"), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("width" to "284.77rpx", "height" to "220.31rpx"))), _uA(
                                                _cE("image", _uM("src" to "/static/ico/analysis_bg.png", "mode" to "", "style" to _nS(_uM("position" to "absolute", "width" to "100%", "height" to "100%"))), null, 4),
                                                _cE("image", _uM("src" to "/static/ico/close.png", "class" to "close_ico", "mode" to "", "onClick" to fun(){
                                                    hide()
                                                }, "style" to _nS(_uM("top" to "20rpx", "right" to "20rpx", "z-index" to "9"))), null, 12, _uA(
                                                    "onClick"
                                                )),
                                                _cE("text", _uM("style" to _nS(_uM("text-align" to "center", "font-size" to "16rpx", "margin" to "18rpx 0"))), _uA(
                                                    renderSlot(_ctx.`$slots`, "title")
                                                ), 4),
                                                _cE("view", _uM("style" to _nS(_uM("height" to "110rpx"))), _uA(
                                                    renderSlot(_ctx.`$slots`, "content")
                                                ), 4),
                                                _cE("text", _uM("class" to "result_btn", "style" to _nS(_uM("margin" to "10rpx auto 0")), "onClick" to fun(){
                                                    hide()
                                                }), _uA(
                                                    renderSlot(_ctx.`$slots`, "btnText")
                                                ), 12, _uA(
                                                    "onClick"
                                                ))
                                            ), 4)
                                        ), 4)
                                    }
                                ), 4)
                            ), 4)
                        ), 4)
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (isTrue(props.show && props.type == 2 && unref(showJb) && _ctx.tpl == 1)) {
                        _cE("view", _uM("key" to 1, "style" to _nS(_uM("position" to "fixed", "top" to "0", "left" to "0", "width" to "750rpx", "height" to "100%", "align-items" to "center", "justify-content" to "center", "z-index" to "999", "pointer-events" to "none"))), _uA(
                            _cE("image", _uM("src" to "/static/jb.gif", "style" to _nS(_uM("width" to "700rpx")), "mode" to "widthFix"), null, 4)
                        ), 4)
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
                return _uM("source" to _pS(_uM("fontWeight" to "bold", "fontSize" to "47rpx", "color" to "#FA9600")), "result_btn" to _pS(_uM("width" to "101rpx", "height" to "35rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "fontWeight" to "bold", "fontSize" to "14rpx", "lineHeight" to "35rpx", "color" to "#ffffff", "textAlign" to "center")), "close_ico" to _pS(_uM("width" to "17.58rpx", "height" to "17.58rpx", "position" to "absolute", "top" to "117.19rpx", "right" to "206.25rpx")), "bg" to _pS(_uM("backgroundColor" to "rgba(4,19,65,0.6)", "width" to "100%", "height" to "414rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("update:isShowResult" to null, "update:show" to null, "close" to null)
        var props = _nP(_uM("type" to _uM("type" to "Number", "required" to true, "default" to 0), "scoreVal" to _uM("type" to "Number", "required" to true, "default" to 0), "isShowResult" to _uM("type" to "Boolean", "required" to true, "default" to false), "show" to _uM("type" to "Boolean", "required" to true, "default" to false), "isTraining" to _uM("type" to "Boolean", "required" to true, "default" to false), "tpl" to _uM("type" to "Number", "required" to true, "default" to 1)))
        var propsNeedCastKeys = _uA(
            "type",
            "scoreVal",
            "isShowResult",
            "show",
            "isTraining",
            "tpl"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
