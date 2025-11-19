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
import uts.sdk.modules.wjInput.NativeInput
import io.dcloud.uniapp.extapi.rpx2px as uni_rpx2px
open class GenUniModulesWjInputComponentsWjInputWjInput : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var text: String by `$props`
    open var isAutoWidth: Boolean by `$props`
    open var isFocus: Boolean by `$props`
    open var center: String by `$props`
    open var isCheck: Boolean by `$props`
    open var checkText: String by `$props`
    open var isAcquisition: Boolean by `$props`
    open var size: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesWjInputComponentsWjInputWjInput) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesWjInputComponentsWjInputWjInput
            val _cache = __ins.renderCache
            var input: NativeInput? = null
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            fun gen_setForceGetFocusAfterReplace_fn(type: Boolean) {
                input?.setForceGetFocusAfterReplace(type)
            }
            val setForceGetFocusAfterReplace = ::gen_setForceGetFocusAfterReplace_fn
            fun gen_updateText_fn(value: String) {
                input?.setTextProgrammatically(value)
            }
            val updateText = ::gen_updateText_fn
            fun gen_setAutoWidth_fn(value: Boolean) {
                input?.setAutoWidth(value)
            }
            val setAutoWidth = ::gen_setAutoWidth_fn
            fun gen_setShowSoftInputOnFocus_fn(type: Boolean) {
                input?.setShowSoftInputOnFocus(type)
            }
            val setShowSoftInputOnFocus = ::gen_setShowSoftInputOnFocus_fn
            fun gen_setFocus_fn(type: Boolean) {
                input?.setFocus(type)
            }
            val setFocus = ::gen_setFocus_fn
            fun gen_setTextAlignment_fn(type: String) {
                input?.setTextAlignment(type)
            }
            val setTextAlignment = ::gen_setTextAlignment_fn
            fun gen_setDefaultTextSizeRpx_fn(size: Number) {
                input?.setDefaultTextSizeRpx(size)
            }
            val setDefaultTextSizeRpx = ::gen_setDefaultTextSizeRpx_fn
            fun gen_setBlockKeyboardInput_fn(type: Boolean) {
                input?.setBlockKeyboardInput(type)
            }
            val setBlockKeyboardInput = ::gen_setBlockKeyboardInput_fn
            fun gen_setValidationMode_fn(type: Boolean) {
                input?.setValidationMode(type)
            }
            val setValidationMode = ::gen_setValidationMode_fn
            fun gen_setValidationText_fn(str: String) {
                input?.setValidationText(str)
            }
            val setValidationText = ::gen_setValidationText_fn
            watchEffect(fun(){
                updateText(props.text)
                setFocus(props.isFocus)
                setValidationMode(props.isCheck)
            }
            )
            watch(props.isAutoWidth, fun(newValue: Boolean){
                setAutoWidth(newValue)
            }
            )
            watch(props.center, fun(newValue: String){
                setTextAlignment(newValue)
            }
            )
            watch(props.checkText, fun(newValue: String){
                setValidationText(newValue)
            }
            )
            watch(props.size, fun(newValue: Number){
                setDefaultTextSizeRpx(newValue)
            }
            )
            watch(props.isAcquisition, fun(newValue: Boolean){
                setForceGetFocusAfterReplace(newValue)
            }
            )
            fun gen_onviewinit_fn(e: UniNativeViewInitEvent) {
                input = NativeInput(e.detail.element)
                updateText(props.text)
                setAutoWidth(props.isAutoWidth)
                setFocus(props.isFocus)
                setTextAlignment(props.center)
                setValidationMode(props.isCheck)
                setValidationText(props.checkText)
                setForceGetFocusAfterReplace(props.isAcquisition)
                setDefaultTextSizeRpx(props.size)
            }
            val onviewinit = ::gen_onviewinit_fn
            fun gen_ontap_fn(e: UniNativeViewEvent) {
                emit("inputTap", e)
            }
            val ontap = ::gen_ontap_fn
            fun gen_onCursor_fn(e: UniNativeViewEvent) {
                emit("cursorChange", e)
            }
            val onCursor = ::gen_onCursor_fn
            fun gen_onInputFn_fn(e: UniNativeViewEvent) {
                var _str = e.detail.getString("str")
                emit("input", _str)
                emit("update:text", _str)
            }
            val onInputFn = ::gen_onInputFn_fn
            fun gen_getPos_fn(e: UniNativeViewEvent) {
                var screenH = e.detail.getNumber("screenH") ?: 0
                var posY = e.detail.getNumber("posY") ?: 0
                var height = e.detail.getNumber("height") ?: 0
                var keyH = uni_rpx2px(170)
                var controlBottomPos = posY + height
                var keyboardTopPos = screenH - keyH - 140
                var offsetY: Number = 0
                if (controlBottomPos > keyboardTopPos) {
                    offsetY = controlBottomPos - keyboardTopPos
                }
                emit("tranY", offsetY)
            }
            val getPos = ::gen_getPos_fn
            fun gen_onErrFn_fn(e: UniNativeViewEvent) {
                emit("onErr", null)
            }
            val onErrFn = ::gen_onErrFn_fn
            fun gen_onSuccessFn_fn(e: UniNativeViewEvent) {
                emit("onSuccess", null)
            }
            val onSuccessFn = ::gen_onSuccessFn_fn
            fun gen_onUnmounted_fn() {
                input?.destroy()
            }
            val onUnmounted1 = ::gen_onUnmounted_fn
            return fun(): Any? {
                return _cE("native-view", _uM("onInit" to onviewinit, "on:customClick" to ontap, "on:cursorWz" to onCursor, "on:onInput" to onInputFn, "on:onErr" to onErrFn, "on:onSuccess" to onSuccessFn, "on:getPos" to getPos), null, 32)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA())
        }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("inputTap" to null, "cursorChange" to null, "input" to null, "onErr" to null, "onSuccess" to null, "tranY" to null)
        var props = _nP(_uM("text" to _uM("type" to "String", "required" to true, "default" to ""), "isAutoWidth" to _uM("type" to "Boolean", "required" to true, "default" to false), "isFocus" to _uM("type" to "Boolean", "required" to true, "default" to false), "center" to _uM("type" to "String", "required" to true, "default" to "left"), "isCheck" to _uM("type" to "Boolean", "required" to true, "default" to false), "checkText" to _uM("type" to "String", "required" to true, "default" to ""), "isAcquisition" to _uM("type" to "Boolean", "required" to true, "default" to false), "size" to _uM("type" to "Number", "required" to true, "default" to 18)))
        var propsNeedCastKeys = _uA(
            "text",
            "isAutoWidth",
            "isFocus",
            "center",
            "isCheck",
            "checkText",
            "isAcquisition",
            "size"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
