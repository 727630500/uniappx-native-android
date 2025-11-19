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
open class GenComponentsSentenceAnalysisSentenceAnalysis : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var list: UTSArray<analysis>? by `$props`
    open var marginTopVal: String by `$props`
    open var fontSizeVal: String by `$props`
    open var lineHeightVal: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsSentenceAnalysisSentenceAnalysis) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsSentenceAnalysisSentenceAnalysis
            val _cache = __ins.renderCache
            val props = __props
            val _list = computed<UTSArray<analysis>?>(fun(): UTSArray<analysis>? {
                return props.list?.map(fun(item): analysis {
                    var content = item.content.replace(UTSRegExp("\\r?\\n|\\r", "g"), "")
                    item.content = content
                    return item
                }
                )
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uA(
                    _uM("flex-wrap" to "wrap"),
                    _uM("marginTop" to props.marginTopVal)
                ))), _uA(
                    _cE(Fragment, null, RenderHelpers.renderList(unref(_list), fun(item, __key, __index, _cached): Any {
                        return _cE(Fragment, null, _uA(
                            if (item.dp_message != 32) {
                                _cE("text", _uM("key" to 0, "style" to _nS(_uA(
                                    _uM("line-height" to "23rpx", "font-size" to "18rpx", "margin-right" to "6rpx", "font-weight" to "700", "margin-bottom" to "6rpx"),
                                    _uM("fontSize" to props.fontSizeVal, "lineHeight" to props.lineHeightVal)
                                )), "class" to _nC(_uM("_green" to (item.total_score >= 90), "_blue" to (item.total_score >= 80 && item.total_score < 90), "_orgin" to (item.total_score >= 60 && item.total_score < 80), "_red" to (item.total_score < 60), "_not" to (item.dp_message == 16)))), _tD(item.content), 7)
                            } else {
                                _cC("v-if", true)
                            }
                        ), 64)
                    }
                    ), 256)
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
                return _uM("_green" to _pS(_uM("color" to "#5A9F32")), "_blue" to _pS(_uM("color" to "#6694DF")), "_orgin" to _pS(_uM("color" to "#FA9600")), "_red" to _pS(_uM("color" to "#E54E4E")), "_not" to _pS(_uM("color" to "#E54E4E", "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#E54E4E")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("list" to _uM("type" to "Array", "required" to false, "default" to null), "marginTopVal" to _uM("type" to "String", "required" to false, "default" to "10rpx"), "fontSizeVal" to _uM("type" to "String", "required" to false, "default" to "18rpx"), "lineHeightVal" to _uM("type" to "String", "required" to false, "default" to "23rpx")))
        var propsNeedCastKeys = _uA(
            "list",
            "marginTopVal",
            "fontSizeVal",
            "lineHeightVal"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
