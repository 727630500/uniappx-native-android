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
open class GenComponentsScoreDisplayScoreDisplay : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var score: String by `$props`
    open var fontSize: String by `$props`
    open var lineHeight: String by `$props`
    open var displayText: String? by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsScoreDisplayScoreDisplay) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsScoreDisplayScoreDisplay
            val _cache = __ins.renderCache
            val props = __props
            val displayText = computed(fun(): String {
                return if (props.displayText != null) {
                    props.displayText!!
                } else {
                    props.score
                }
            }
            )
            val scoreColorClass = computed(fun(): String {
                val scoreNum = parseFloat(props.score)
                if (isNaN(scoreNum)) {
                    return "_red"
                }
                if (scoreNum >= 60) {
                    return "_green"
                } else {
                    return "_red"
                }
            }
            )
            return fun(): Any? {
                return _cE("text", _uM("class" to _nC(unref(scoreColorClass)), "style" to _nS(_uM("fontSize" to props.fontSize, "lineHeight" to props.lineHeight, "fontWeight" to "700"))), _tD(unref(displayText)), 7)
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
        var props = _nP(_uM("score" to _uM("type" to "String", "required" to true, "default" to "0"), "fontSize" to _uM("type" to "String", "required" to false, "default" to "14rpx"), "lineHeight" to _uM("type" to "String", "required" to false, "default" to "20rpx"), "displayText" to _uM("type" to "String", "required" to false, "default" to null)))
        var propsNeedCastKeys = _uA(
            "score",
            "fontSize",
            "lineHeight",
            "displayText"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
