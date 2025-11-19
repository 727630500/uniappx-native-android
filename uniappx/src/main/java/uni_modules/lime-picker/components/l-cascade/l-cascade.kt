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
open class GenUniModulesLimePickerComponentsLCascadeLCascade : VueComponent, CascadeProps {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    override var cancelBtn: String? by `$props`
    override var cancelStyle: String? by `$props`
    override var confirmBtn: String? by `$props`
    override var confirmStyle: String? by `$props`
    override var title: String? by `$props`
    override var titleStyle: String? by `$props`
    override var keys: UTSJSONObject? by `$props`
    override var columns: UTSArray<UTSJSONObject> by `$props`
    override var modelValue: UTSArray<PickerValue>? by `$props`
    override var defaultValue: UTSArray<PickerValue>? by `$props`
    override var value: UTSArray<PickerValue>? by `$props`
    override var loading: Boolean by `$props`
    override var loadingColor: String? by `$props`
    override var loadingMaskColor: String? by `$props`
    override var loadingSize: String by `$props`
    override var itemHeight: String? by `$props`
    override var itemColor: String? by `$props`
    override var itemFontSize: String? by `$props`
    override var itemActiveColor: String? by `$props`
    override var indicatorStyle: String? by `$props`
    override var bgColor: String? by `$props`
    override var groupHeight: String? by `$props`
    override var radius: String? by `$props`
    override var resetIndex: Boolean by `$props`
    open var confirm: () -> Unit
        get() {
            return unref(this.`$exposed`["confirm"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "confirm", value)
        }
    open var getSelectedOptions: () -> PickerConfirmEvent?
        get() {
            return unref(this.`$exposed`["getSelectedOptions"]) as () -> PickerConfirmEvent?
        }
        set(value) {
            setRefValue(this.`$exposed`, "getSelectedOptions", value)
        }
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesLimePickerComponentsLCascadeLCascade, _arg1: SetupContext) -> Any? = fun(__props, ref1): Any? {
            var __expose = ref1.expose
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesLimePickerComponentsLCascadeLCascade
            val _cache = __ins.renderCache
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val props = __props
            val keys = parseKeys(props.keys)
            val innerValue = ref<UTSArray<PickerValue>>(props.value ?: props.modelValue ?: props.defaultValue ?: _uA())
            val innerColumns = computed(fun(): UTSArray<PickerColumn> {
                return formatCascadeColumns(props.columns, keys, innerValue)
            }
            )
            val onPick = fun(options: PickerPickEvent){
                val values = options.values
                val column = options.column
                val index = options.index
                innerValue.value = values.slice()
            }
            val onConfirm = fun(options: PickerConfirmEvent){
                emit("confirm", options)
            }
            val onCancel = fun(){
                emit("cancel")
            }
            watchEffect(fun(){
                emit("update:modelValue", innerValue.value)
            }
            )
            val pickerRef = ref<ComponentPublicInstance?>(null)
            __expose(_uM("confirm" to fun(){
                pickerRef.value?.`$callMethod`("confirm")
            }
            , "getSelectedOptions" to fun(): PickerConfirmEvent? {
                return pickerRef.value?.`$callMethod`("getSelectedOptions") as PickerConfirmEvent?
            }
            ))
            return fun(): Any? {
                val _component_l_picker = resolveEasyComponent("l-picker", GenUniModulesLimePickerComponentsLPickerLPickerClass)
                return _cV(_component_l_picker, _uM("ref_key" to "pickerRef", "ref" to pickerRef, "modelValue" to unref(innerValue), "onUpdate:modelValue" to fun(`$event`: UTSArray<PickerValue>){
                    trySetRefValue(innerValue, `$event`)
                }
                , "cancelBtn" to _ctx.cancelBtn, "cancelStyle" to _ctx.cancelStyle, "confirmBtn" to _ctx.confirmBtn, "confirmStyle" to _ctx.confirmStyle, "title" to _ctx.title, "titleStyle" to _ctx.titleStyle, "loading" to _ctx.loading, "loadingColor" to _ctx.loadingColor, "loadingMaskColor" to _ctx.loadingMaskColor, "loadingSize" to _ctx.loadingSize, "itemHeight" to _ctx.itemHeight, "itemColor" to _ctx.itemColor, "itemFontSize" to _ctx.itemFontSize, "itemActiveColor" to _ctx.itemActiveColor, "indicatorStyle" to _ctx.indicatorStyle, "bgColor" to _ctx.bgColor, "groupHeight" to _ctx.groupHeight, "radius" to _ctx.radius, "resetIndex" to _ctx.resetIndex, "columns" to unref(innerColumns), "onCancel" to onCancel, "onConfirm" to onConfirm, "onPick" to onPick), null, 8, _uA(
                    "modelValue",
                    "cancelBtn",
                    "cancelStyle",
                    "confirmBtn",
                    "confirmStyle",
                    "title",
                    "titleStyle",
                    "loading",
                    "loadingColor",
                    "loadingMaskColor",
                    "loadingSize",
                    "itemHeight",
                    "itemColor",
                    "itemFontSize",
                    "itemActiveColor",
                    "indicatorStyle",
                    "bgColor",
                    "groupHeight",
                    "radius",
                    "resetIndex",
                    "columns"
                ))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA())
        }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("change" to null, "cancel" to null, "pick" to null, "confirm" to null, "update:modelValue" to null, "update:value" to null)
        var props = _nP(_uM("cancelBtn" to _uM("type" to "String", "required" to false), "cancelStyle" to _uM("type" to "String", "required" to false), "confirmBtn" to _uM("type" to "String", "required" to false), "confirmStyle" to _uM("type" to "String", "required" to false), "title" to _uM("type" to "String", "required" to false), "titleStyle" to _uM("type" to "String", "required" to false), "keys" to _uM("type" to "UTSJSONObject", "required" to false), "columns" to _uM("type" to "Array", "required" to true, "default" to _uA<PickerColumnItem>()), "modelValue" to _uM("type" to "Array", "required" to false), "defaultValue" to _uM("type" to "Array", "required" to false), "value" to _uM("type" to "Array", "required" to false), "loading" to _uM("type" to "Boolean", "required" to true, "default" to false), "loadingColor" to _uM("type" to "String", "required" to false), "loadingMaskColor" to _uM("type" to "String", "required" to false), "loadingSize" to _uM("type" to "String", "required" to true, "default" to "64rpx"), "itemHeight" to _uM("type" to "String", "required" to false), "itemColor" to _uM("type" to "String", "required" to false), "itemFontSize" to _uM("type" to "String", "required" to false), "itemActiveColor" to _uM("type" to "String", "required" to false), "indicatorStyle" to _uM("type" to "String", "required" to false), "bgColor" to _uM("type" to "String", "required" to false), "groupHeight" to _uM("type" to "String", "required" to false), "radius" to _uM("type" to "String", "required" to false), "resetIndex" to _uM("type" to "Boolean", "required" to true, "default" to false)))
        var propsNeedCastKeys = _uA(
            "columns",
            "loading",
            "loadingSize",
            "resetIndex"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
