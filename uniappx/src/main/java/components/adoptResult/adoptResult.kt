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
open class GenComponentsAdoptResultAdoptResult : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var score: Number by `$props`
    open var notDone: Number by `$props`
    open var failNum: Number by `$props`
    open var time: String by `$props`
    open var tpl: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsAdoptResultAdoptResult) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsAdoptResultAdoptResult
            val _cache = __ins.renderCache
            val props = __props
            return fun(): Any? {
                return _cE(Fragment, null, _uA(
                    if (props.tpl == 1) {
                        _cE("view", _uM("key" to 0, "class" to "adopt_inner"), _uA(
                            _cE("view", _uM("class" to "adopt_inner_item"), _uA(
                                _cE("image", _uM("src" to "/static/ico/test/fraction.png", "mode" to "", "style" to _nS(_uM("width" to "11.72rpx", "height" to "13.99rpx"))), null, 4),
                                _cE("text", _uM("class" to "tips color-blue"), "得分"),
                                _cE("text", _uM("class" to "fraction color-green"), _tD(props.score), 1)
                            )),
                            _cE("view", _uM("class" to "adopt_inner_item"), _uA(
                                _cE("image", _uM("src" to "/static/ico/test/wrong.png", "mode" to "", "style" to _nS(_uM("width" to "11.72rpx", "height" to "14.06rpx"))), null, 4),
                                _cE("text", _uM("class" to "tips color-blue"), "错题"),
                                _cE("text", _uM("class" to "fraction color-red"), _tD(props.failNum), 1)
                            )),
                            _cE("view", _uM("class" to "adopt_inner_item"), _uA(
                                _cE("image", _uM("src" to "/static/ico/test/notDone.png", "mode" to "", "style" to _nS(_uM("width" to "11.72rpx", "height" to "11.71rpx"))), null, 4),
                                _cE("text", _uM("class" to "tips color-blue"), "未做"),
                                _cE("text", _uM("class" to "fraction color-blue"), _tD(props.notDone), 1)
                            )),
                            _cE("view", _uM("class" to "adopt_inner_item"), _uA(
                                _cE("image", _uM("src" to "/static/ico/test/time.png", "mode" to "", "style" to _nS(_uM("width" to "12.89rpx", "height" to "11.43rpx"))), null, 4),
                                _cE("text", _uM("class" to "tips color-blue"), "用时"),
                                _cE("text", _uM("class" to "fraction color-blue"), _tD(props.time), 1)
                            ))
                        ))
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (props.tpl == 2) {
                        _cE("view", _uM("key" to 1, "style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "padding-right" to "-10rpx"))), _uA(
                            _cE("view", _uM("class" to "adopt_inner_item2"), _uA(
                                _cE("image", _uM("src" to "/static/ico/test/fraction.png", "mode" to "", "style" to _nS(_uM("width" to "11.72rpx", "height" to "13.99rpx"))), null, 4),
                                _cE("text", _uM("class" to "tips color-blue"), "得分"),
                                _cE("text", _uM("class" to "fraction color-green"), _tD(props.score), 1)
                            )),
                            _cE("view", _uM("class" to "adopt_inner_item2"), _uA(
                                _cE("image", _uM("src" to "/static/ico/test/wrong.png", "mode" to "", "style" to _nS(_uM("width" to "11.72rpx", "height" to "14.06rpx"))), null, 4),
                                _cE("text", _uM("class" to "tips color-blue"), "错题"),
                                _cE("text", _uM("class" to "fraction color-red"), _tD(props.failNum), 1)
                            )),
                            _cE("view", _uM("class" to "adopt_inner_item2"), _uA(
                                _cE("image", _uM("src" to "/static/ico/test/notDone.png", "mode" to "", "style" to _nS(_uM("width" to "11.72rpx", "height" to "11.71rpx"))), null, 4),
                                _cE("text", _uM("class" to "tips color-blue"), "未做"),
                                _cE("text", _uM("class" to "fraction color-blue"), _tD(props.notDone), 1)
                            )),
                            _cE("view", _uM("class" to "adopt_inner_item2"), _uA(
                                _cE("image", _uM("src" to "/static/ico/test/time.png", "mode" to "", "style" to _nS(_uM("width" to "12.89rpx", "height" to "11.43rpx"))), null, 4),
                                _cE("text", _uM("class" to "tips color-blue"), "用时"),
                                _cE("text", _uM("class" to "fraction color-blue"), _tD(props.time), 1)
                            ))
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
                return _uM("color-blue" to _pS(_uM("color" to "#6694DF")), "color-red" to _pS(_uM("color" to "#FF1313")), "color-green" to _pS(_uM("color" to "#5A9F32")), "adopt_inner" to _pS(_uM("width" to "435rpx", "height" to "128rpx", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "17rpx", "borderTopRightRadius" to "17rpx", "borderBottomRightRadius" to "17rpx", "borderBottomLeftRadius" to "17rpx", "flexDirection" to "row", "flexWrap" to "wrap", "paddingTop" to "14rpx", "paddingRight" to "26.37rpx", "paddingBottom" to "14rpx", "paddingLeft" to "26.37rpx")), "adopt_inner_item" to _uM(".adopt_inner " to _uM("width" to "50%", "height" to "50%", "alignItems" to "center", "flexDirection" to "row")), "tips" to _uM(".adopt_inner .adopt_inner_item " to _uM("fontSize" to "14rpx", "lineHeight" to "15rpx", "marginLeft" to "6.45rpx"), ".adopt_inner_item2 " to _uM("fontSize" to "12.89rpx", "lineHeight" to "15rpx", "marginLeft" to "6.45rpx")), "fraction" to _uM(".adopt_inner .adopt_inner_item " to _uM("fontWeight" to "bold", "fontSize" to "27rpx", "marginLeft" to "19.92rpx"), ".adopt_inner_item2 " to _uM("fontWeight" to "bold", "fontSize" to "12.89rpx", "marginLeft" to "5rpx")), "adopt_inner_item2" to _pS(_uM("alignItems" to "center", "flexDirection" to "row", "marginRight" to "10rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("score" to _uM("type" to "Number", "required" to true, "default" to 0), "notDone" to _uM("type" to "Number", "required" to true, "default" to 0), "failNum" to _uM("type" to "Number", "required" to true, "default" to 0), "time" to _uM("type" to "String", "required" to true, "default" to "00:00"), "tpl" to _uM("type" to "Number", "required" to true, "default" to 1)))
        var propsNeedCastKeys = _uA(
            "score",
            "notDone",
            "failNum",
            "time",
            "tpl"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
