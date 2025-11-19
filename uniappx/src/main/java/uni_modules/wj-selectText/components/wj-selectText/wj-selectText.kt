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
import uts.sdk.modules.wjSelectText.selectInput
open class GenUniModulesWjSelectTextComponentsWjSelectTextWjSelectText : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var text: String by `$props`
    open var isAutoWidth: Boolean by `$props`
    open var isFocus: Boolean by `$props`
    open var center: String by `$props`
    open var isCheck: Boolean by `$props`
    open var checkText: String by `$props`
    open var isAcquisition: Boolean by `$props`
    open var size: Number by `$props`
    open var wordBookEnabled: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesWjSelectTextComponentsWjSelectTextWjSelectText) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesWjSelectTextComponentsWjSelectTextWjSelectText
            val _cache = __ins.renderCache
            var input: selectInput? = null
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            fun gen_updateText_fn(value: String) {
                input?.updateText(value)
            }
            val updateText = ::gen_updateText_fn
            fun gen_setJsonText_fn(value: String) {
                input?.setJsonText(value)
            }
            val setJsonText = ::gen_setJsonText_fn
            fun gen_setWordBookEnabled_fn(enabled: Boolean) {
                input?.setWordBookEnabled(enabled)
            }
            val setWordBookEnabled = ::gen_setWordBookEnabled_fn
            watchEffect(fun(){
                val text = props.text
                setJsonText(text)
            }
            )
            watchEffect(fun(){
                val enabled = props.wordBookEnabled
                setWordBookEnabled(enabled)
            }
            )
            fun gen_onviewinit_fn(e: UniNativeViewInitEvent) {
                input = selectInput(e.detail.element)
                setJsonText(props.text)
                setWordBookEnabled(props.wordBookEnabled)
            }
            val onviewinit = ::gen_onviewinit_fn
            fun gen_onInputFn_fn(e: UniNativeViewEvent) {
                var _str = e.detail.getString("str") ?: ""
                emit("select", _str)
            }
            val onInputFn = ::gen_onInputFn_fn
            fun gen_onTextHeightFn_fn(e: UniNativeViewEvent) {
                var _str = e.detail.getNumber("height") ?: 0
                emit("heightFn", _str)
            }
            val onTextHeightFn = ::gen_onTextHeightFn_fn
            fun gen_onUnmounted_fn() {
                input?.destroy()
            }
            val onUnmounted1 = ::gen_onUnmounted_fn
            return fun(): Any? {
                return _cE("native-view", _uM("style" to _nS(_uM("height" to "100px")), "onInit" to onviewinit, "on:onInput" to onInputFn, "on:onTextHeight" to onTextHeightFn), null, 36)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA())
        }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("select" to null, "heightFn" to null)
        var props = _nP(_uM("text" to _uM("type" to "String", "required" to true, "default" to ""), "isAutoWidth" to _uM("type" to "Boolean", "required" to true, "default" to false), "isFocus" to _uM("type" to "Boolean", "required" to true, "default" to false), "center" to _uM("type" to "String", "required" to true, "default" to "left"), "isCheck" to _uM("type" to "Boolean", "required" to true, "default" to false), "checkText" to _uM("type" to "String", "required" to true, "default" to ""), "isAcquisition" to _uM("type" to "Boolean", "required" to true, "default" to false), "size" to _uM("type" to "Number", "required" to true, "default" to 30), "wordBookEnabled" to _uM("type" to "Boolean", "required" to true, "default" to true)))
        var propsNeedCastKeys = _uA(
            "text",
            "isAutoWidth",
            "isFocus",
            "center",
            "isCheck",
            "checkText",
            "isAcquisition",
            "size",
            "wordBookEnabled"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
