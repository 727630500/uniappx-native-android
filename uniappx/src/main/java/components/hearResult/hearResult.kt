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
open class GenComponentsHearResultHearResult : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var score: Number by `$props`
    open var complete: Number by `$props`
    open var accurate: Number by `$props`
    open var fluent: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsHearResultHearResult) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsHearResultHearResult
            val _cache = __ins.renderCache
            val props = __props
            return fun(): Any? {
                return _cE("view", _uM("class" to "adopt_inner"), _uA(
                    _cE("view", _uM("class" to "adopt_inner_item", "style" to _nS(_uM("border-right" to "1rpx solid #D8D8D8"))), _uA(
                        _cE("text", _uM("class" to "fraction", "style" to _nS(_uM("color" to "#5A9F32", "font-size" to "30rpx", "line-height" to "31rpx"))), _tD(props.score), 5),
                        _cE("text", _uM("class" to "tips"), "综合得分")
                    ), 4),
                    _cE("view", _uM("class" to "adopt_inner_item"), _uA(
                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                            _cE("text", _uM("class" to "fraction"), _tD(props.complete), 1),
                            _cE("text", _uM("class" to "fen"), "分")
                        ), 4),
                        _cE("text", _uM("class" to "tips"), "完整度")
                    )),
                    _cE("view", _uM("class" to "adopt_inner_item"), _uA(
                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                            _cE("text", _uM("class" to "fraction"), _tD(props.accurate), 1),
                            _cE("text", _uM("class" to "fen"), "分")
                        ), 4),
                        _cE("text", _uM("class" to "tips"), "准确度")
                    )),
                    _cE("view", _uM("class" to "adopt_inner_item"), _uA(
                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                            _cE("text", _uM("class" to "fraction"), _tD(props.fluent), 1),
                            _cE("text", _uM("class" to "fen"), "分")
                        ), 4),
                        _cE("text", _uM("class" to "tips"), "流利度")
                    ))
                ))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("adopt_inner" to _pS(_uM("width" to "387rpx", "height" to "59rpx", "backgroundImage" to "none", "backgroundColor" to "#F2F5FA", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "flexDirection" to "row", "paddingTop" to "8rpx", "paddingRight" to 0, "paddingBottom" to "8rpx", "paddingLeft" to 0)), "adopt_inner_item" to _uM(".adopt_inner " to _uM("alignItems" to "center", "justifyContent" to "flex-end", "flex" to 1)), "tips" to _uM(".adopt_inner .adopt_inner_item " to _uM("fontSize" to "12rpx", "color" to "#7B7B7B", "lineHeight" to "13rpx", "textAlign" to "center")), "fraction" to _uM(".adopt_inner .adopt_inner_item " to _uM("fontWeight" to "bold", "color" to "#3D3D3D", "fontSize" to "21rpx", "lineHeight" to "22rpx")), "fen" to _uM(".adopt_inner .adopt_inner_item " to _uM("fontSize" to "12rpx", "color" to "#7B7B7B", "lineHeight" to "13rpx", "marginTop" to "6rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("score" to _uM("type" to "Number", "required" to true, "default" to 0), "complete" to _uM("type" to "Number", "required" to true, "default" to 0), "accurate" to _uM("type" to "Number", "required" to true, "default" to 0), "fluent" to _uM("type" to "Number", "required" to true, "default" to 0)))
        var propsNeedCastKeys = _uA(
            "score",
            "complete",
            "accurate",
            "fluent"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
