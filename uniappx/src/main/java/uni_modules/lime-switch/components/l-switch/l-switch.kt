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
import uts.sdk.modules.limeSwitch.measureTextSize
import io.dcloud.uniapp.extapi.rpx2px as uni_rpx2px
open class GenUniModulesLimeSwitchComponentsLSwitchLSwitch : VueComponent, SwitchProps {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    override var modelValue: Boolean? by `$props`
    override var value: Boolean? by `$props`
    override var defaultValue: Boolean? by `$props`
    override var disabled: Boolean by `$props`
    override var readonly: Boolean by `$props`
    override var loading: Boolean by `$props`
    override var round: Boolean by `$props`
    override var rubberBand: Boolean by `$props`
    override var name: String? by `$props`
    override var placeholder: UTSArray<String> by `$props`
    override var fontSize: String? by `$props`
    override var width: String? by `$props`
    override var height: String? by `$props`
    override var dotSize: String? by `$props`
    override var dotPressedSize: String? by `$props`
    override var checkedColor: String? by `$props`
    override var disabledColor: String? by `$props`
    override var checkedDisabledColor: String? by `$props`
    override var uncheckedColor: String? by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesLimeSwitchComponentsLSwitchLSwitch) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesLimeSwitchComponentsLSwitchLSwitch
            val _cache = __ins.renderCache
            val themeVars = inject("limeConfigProviderThemeVars", computed(fun(): UTSJSONObject {
                return (UTSJSONObject())
            }
            ))
            val name = "l-switch"
            val slots = useSlots()
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val props = __props
            val formDisabled = inject<Ref<Boolean?>?>("formDisabled", null)
            val formReadonly = inject<Ref<Boolean?>?>("formReadonly", null)
            val isReadonly = computed(fun(): Boolean {
                if (props.readonly) {
                    return props.readonly
                }
                return formReadonly?.value ?: false
            }
            )
            val isDisabled = computed(fun(): Boolean {
                if (props.disabled) {
                    return props.disabled
                }
                return formDisabled?.value ?: false
            }
            )
            val modelValue = ref(props.modelValue ?: (props.defaultValue ?: false))
            val innerValue = computed(WritableComputedOptions(set = fun(value: Boolean) {
                modelValue.value = value
                emit("change", value)
                emit("update:modelValue", value)
            }
            , get = fun(): Boolean {
                return props.value ?: props.modelValue ?: modelValue.value
            }
            ))
            val classes = computed(fun(): Map<String, Boolean> {
                val cls = Map<String, Boolean>()
                cls.set("" + name + "--checked", innerValue.value)
                cls.set("" + name + "--unchecked", !innerValue.value)
                cls.set("" + name + "--disabled", isDisabled.value)
                cls.set("" + name + "--round", props.round)
                cls.set("" + name + "--square", !props.round)
                return cls
            }
            )
            val dotClass = computed(fun(): Map<String, Boolean> {
                val cls = Map<String, Boolean>()
                cls.set("" + name + "__dot--disabled", isDisabled.value)
                cls.set("" + name + "__dot--round", props.round)
                cls.set("" + name + "__dot--square", !props.round)
                return cls
            }
            )
            val hoverClass = computed(fun(): String {
                return if (props.rubberBand && !props.disabled && !props.loading) {
                    "l-switch--hover"
                } else {
                    ""
                }
            }
            )
            val styles = computed(fun(): Map<String, Any> {
                val style = Map<String, Any>()
                if (props.width != null) {
                    style.set("--l-switch-width", props.width!!)
                }
                if (props.height != null) {
                    style.set("--l-switch-height", props.height!!)
                }
                if (props.fontSize != null) {
                    style.set("--l-swtich-font-size", props.fontSize!!)
                }
                if (props.dotSize != null) {
                    style.set("--l-switch-dot-size", props.dotSize!!)
                }
                if (props.dotPressedSize != null) {
                    style.set("--l-switch-dot-size-pressed", props.dotPressedSize!!)
                }
                if (props.checkedColor != null) {
                    style.set("--l-switch-checked-color", props.checkedColor!!)
                }
                if (props.checkedDisabledColor != null) {
                    style.set("--l-switch-checked-disabled-color", props.checkedDisabledColor!!)
                }
                if (props.disabledColor != null) {
                    style.set("--l-switch-unchecked-disabled-color", props.disabledColor!!)
                }
                if (props.uncheckedColor != null) {
                    style.set("--l-switch-unchecked-color", props.uncheckedColor!!)
                }
                if (isDisabled.value && props.checkedColor != null && props.checkedDisabledColor == null) {
                    style.set("--l-switch-checked-disabled-color", props.checkedColor!!)
                    style.set("--l-switch-disabled-opacity", 0.5)
                }
                if (props.width != null) {
                    style.set("min-width", props.width!!)
                }
                if (props.height != null) {
                    style.set("height", props.height!!)
                }
                return style
            }
            )
            val dotStyle = computed(fun(): Map<String, Any> {
                val style = Map<String, Any>()
                if (props.dotSize != null) {
                    style.set("width", props.dotSize!!)
                    style.set("height", props.dotSize!!)
                }
                return style
            }
            )
            val handleClick = fun(e: UniPointerEvent){
                if (isDisabled.value || props.loading || isReadonly.value) {
                    return
                }
                innerValue.value = !innerValue.value
            }
            val PLACEHOLDER_SCALE_FACTOR: Number = 1.2
            val TEXT_WIDTH_OFFSET: Number = 20
            val DOT_PRESSED_SCALE: Number = 1.25
            val HOVER_PADDING_SCALE: Number = 1.15
            val CONTAINER_WIDTH_EXPAND_FACTOR: Number = 1.75
            val PLACEHOLDER_POSITION_ADJUSTMENT: Number = 0
            val rootRectWidth = ref(0)
            val rootRectHeight = ref(0)
            val dotRectWidth = ref(0)
            val dotRectHeight = ref(0)
            val switchRailRef = ref<UniElement?>(null)
            val dotRef = ref<UniElement?>(null)
            val checkedStateRef = ref<UniElement?>(null)
            val uncheckedStateRef = ref<UniElement?>(null)
            val placeholderMaxWidth = ref(0)
            val baseContainerWidth = ref(0)
            val isHovered = ref(false)
            val loadingIndicator = useLoading(dotRef)
            loadingIndicator.type = "circular"
            loadingIndicator.color = props.checkedColor ?: "" + (themeVars.value["switchCheckedColor"] ?: "#3283ff")
            loadingIndicator.ratio = 0.8
            val dotPressedDimension = computed(fun(): Number {
                return unitConvert(props.dotPressedSize ?: 0)
            }
            )
            val switchContainerHeight = computed(fun(): Number {
                return unitConvert(props.height ?: rootRectHeight.value ?: 0)
            }
            )
            val dotElementHeight = computed(fun(): Number {
                return unitConvert(props.dotSize ?: dotRectHeight.value ?: 0)
            }
            )
            val dotPositionOffset = computed(fun(): Number {
                return (switchContainerHeight.value - dotElementHeight.value) / 2
            }
            )
            val placeholderPositionOffset = computed(fun(): Number {
                return PLACEHOLDER_SCALE_FACTOR * switchContainerHeight.value - dotPositionOffset.value
            }
            )
            val handleLongPress = fun(event: UniTouchEvent){
                event.stopPropagation()
                if (dotRef.value == null || !props.rubberBand || props.disabled || props.loading) {
                    return
                }
                isHovered.value = true
            }
            val handleTouchEnd = fun(){
                if (dotRef.value == null || !props.rubberBand || props.disabled || props.loading) {
                    return
                }
                isHovered.value = false
            }
            watch(fun(): UTSArray<String> {
                return props.placeholder
            }
            , fun(v: UTSArray<String>, o: UTSArray<String>){
                if (props.placeholder.length == 0) {
                    placeholderMaxWidth.value = 0
                    return
                }
                val rectPromises = props.placeholder.map(fun(text): Number {
                    val fontSize = unitConvert(props.fontSize ?: uni_rpx2px(28))
                    val textWidth = measureTextSize(text, fontSize)
                    return textWidth
                }
                )
                if (rectPromises.length == 0) {
                    return
                }
                val textWidth = Math.max(*rectPromises.toTypedArray())
                nextTick(fun(){
                    placeholderMaxWidth.value = textWidth
                    val adjustedWidth = textWidth + TEXT_WIDTH_OFFSET
                    checkedStateRef.value?.style?.setProperty("width", "" + adjustedWidth + "px")
                    uncheckedStateRef.value?.style?.setProperty("width", "" + adjustedWidth + "px")
                }
                )
            }
            , WatchOptions(immediate = true))
            val currentDotSize = computed(fun(): Number {
                if (dotRectHeight.value == 0) {
                    return 0
                }
                return if (isHovered.value) {
                    Math.max(dotPressedDimension.value, dotRectHeight.value * DOT_PRESSED_SCALE)
                } else {
                    dotRectHeight.value
                }
            }
            )
            val updateLoadingState = fun(){
                if (props.loading && slots["icon"] == null) {
                    loadingIndicator.play()
                } else {
                    loadingIndicator.clear()
                }
            }
            val updateContainerLayout = fun(){
                if (rootRectHeight.value == 0 || dotRectHeight.value == 0) {
                    return
                }
                val containerWidth = if (placeholderMaxWidth.value > 0 && baseContainerWidth.value != placeholderMaxWidth.value) {
                    placeholderMaxWidth.value + rootRectHeight.value * CONTAINER_WIDTH_EXPAND_FACTOR
                } else {
                    baseContainerWidth.value
                }
                if (containerWidth != baseContainerWidth.value) {
                    baseContainerWidth.value = containerWidth
                    switchRailRef.value?.style?.setProperty("width", "" + containerWidth + "px")
                }
            }
            val updateDotPosition = fun(){
                if (rootRectWidth.value == 0 || dotRectHeight.value == 0) {
                    return
                }
                val translateX = if (innerValue.value) {
                    baseContainerWidth.value - currentDotSize.value - dotPositionOffset.value
                } else {
                    dotPositionOffset.value
                }
                dotRef.value?.style?.setProperty("transform", "translateX(" + translateX + "px)")
            }
            watch(isHovered, fun(hoverState: Boolean){
                val calculatePadding = fun(condition: Boolean): Number {
                    return if (condition) {
                        currentDotSize.value * DOT_PRESSED_SCALE
                    } else {
                        HOVER_PADDING_SCALE * switchContainerHeight.value - dotPositionOffset.value
                    }
                }
                checkedStateRef.value?.style?.setProperty("padding-right", "" + calculatePadding(hoverState && !innerValue.value))
                uncheckedStateRef.value?.style?.setProperty("padding-left", "" + calculatePadding(hoverState && innerValue.value))
                dotRef.value?.style?.setProperty("width", "" + currentDotSize.value + "px")
                updateDotPosition()
            }
            )
            watch(innerValue, fun(){
                updateContainerLayout()
                updateDotPosition()
            }
            )
            watch(_uA(
                placeholderMaxWidth,
                baseContainerWidth
            ), fun(){
                updateContainerLayout()
                updateDotPosition()
            }
            )
            watch(fun(): Boolean {
                return props.loading
            }
            , fun(){
                updateLoadingState()
            }
            )
            watch(placeholderPositionOffset, fun(v: Number){
                checkedStateRef.value?.style?.setProperty("padding-right", "" + v + "px")
                uncheckedStateRef.value?.style?.setProperty("padding-left", "" + (v - PLACEHOLDER_POSITION_ADJUSTMENT) + "px")
            }
            )
            watchEffect(fun(){
                dotRef.value?.style?.setProperty("top", "" + dotPositionOffset.value + "px")
            }
            )
            onMounted(fun(){
                nextTick(fun(){
                    if (switchRailRef.value == null) {
                        return
                    }
                    switchRailRef.value?.getBoundingClientRectAsync()?.then(fun(){
                        val railRect = switchRailRef.value?.getBoundingClientRect()
                        val dotRect = dotRef.value?.getBoundingClientRect()
                        dotRef.value?.style?.setProperty("overflow", "visible")
                        rootRectWidth.value = railRect?.width ?: 0
                        rootRectHeight.value = railRect?.height ?: 0
                        dotRectWidth.value = dotRect?.width ?: 0
                        dotRectHeight.value = dotRect?.height ?: 0
                        baseContainerWidth.value = rootRectWidth.value
                        updateContainerLayout()
                        updateDotPosition()
                        updateLoadingState()
                    }
                    )
                }
                )
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "l-switch"), _uA(
                    _cE("view", _uM("class" to _nC(_uA(
                        "l-switch__rail",
                        unref(classes)
                    )), "ref_key" to "switchRailRef", "ref" to switchRailRef, "hover-start-time" to 20, "hover-stay-time" to 70, "onLongpress" to handleLongPress, "hover-class" to unref(hoverClass), "style" to _nS(_uA(
                        unref(styles)
                    )), "aria-role" to "switch", "onTouchend" to handleTouchEnd, "onTouchcancel" to handleTouchEnd, "onClick" to handleClick), _uA(
                        _cE("view", _uM("class" to _nC(_uA(
                            "l-switch__dot",
                            unref(dotClass)
                        )), "style" to _nS(_uA(
                            unref(dotStyle)
                        )), "ref_key" to "dotRef", "ref" to dotRef), _uA(
                            if (_ctx.placeholder.length > 0) {
                                _cE("text", _uM("key" to 0, "class" to "l-switch__placeholder l-switch__placeholder--checked", "ref_key" to "checkedStateRef", "ref" to checkedStateRef), _tD(_ctx.placeholder[0]), 513)
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (_ctx.placeholder.length > 1) {
                                _cE("text", _uM("key" to 1, "class" to "l-switch__placeholder l-switch__placeholder--unchecked", "ref_key" to "uncheckedStateRef", "ref" to uncheckedStateRef), _tD(_ctx.placeholder[1]), 513)
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            renderSlot(_ctx.`$slots`, "icon", GenUniModulesLimeSwitchComponentsLSwitchLSwitchSlotDataIcon(checked = unref(innerValue)))
                        ), 6)
                    ), 46, _uA(
                        "onLongpress",
                        "hover-class"
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
                return _uM("l-switch" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "l-switch__rail" to _pS(_uM("height" to "var(--l-switch-height, 28px)", "minWidth" to "var(--l-switch-width, 46px)", "transitionDuration" to "300ms", "transitionProperty" to "width", "transitionTimingFunction" to "cubic-bezier(0.4,0,0.2,1)")), "l-switch__dot" to _uM(".l-switch--hover " to _uM("maxWidth" to "var(--l-switch-dot-size-pressed, 28px)"), "" to _uM("position" to "absolute", "backgroundColor" to "var(--l-switch-dot-bg-color, white)", "height" to "var(--l-switch-dot-size, 22px)", "pointerEvents" to "none", "justifyContent" to "center", "alignItems" to "center", "transitionDuration" to "300ms", "transitionProperty" to "backgroundColor,opacity,left,maxWidth,width,boxShadow,transform", "transitionTimingFunction" to "cubic-bezier(0.4,0,0.2,1)", "width" to "var(--l-switch-dot-size, 22px)")), "l-switch--checked" to _uM("" to _uM("backgroundColor" to "var(--l-switch-checked-color, #3283ff)"), ".l-switch--disabled" to _uM("backgroundColor" to "var(--l-switch-checked-disabled-color, #add6ff)")), "l-switch--disabled" to _pS(_uM("opacity" to "var(--l-switch-disabled-opacity, 0.8)")), "l-switch--unchecked" to _uM("" to _uM("backgroundColor" to "var(--l-switch-unchecked-color, #dcdcdc)"), ".l-switch--disabled" to _uM("backgroundColor" to "var(--l-switch-unchecked-disabled-color, #eeeeee)")), "l-switch--round" to _pS(_uM("borderTopLeftRadius" to "var(--l-switch-radius, 99px)", "borderTopRightRadius" to "var(--l-switch-radius, 99px)", "borderBottomRightRadius" to "var(--l-switch-radius, 99px)", "borderBottomLeftRadius" to "var(--l-switch-radius, 99px)")), "l-switch--square" to _pS(_uM("borderTopLeftRadius" to "var(--l-switch-radius, 4px)", "borderTopRightRadius" to "var(--l-switch-radius, 4px)", "borderBottomRightRadius" to "var(--l-switch-radius, 4px)", "borderBottomLeftRadius" to "var(--l-switch-radius, 4px)")), "l-switch__dot--round" to _pS(_uM("borderTopLeftRadius" to "var(--l-switch-dot-radius, 99px)", "borderTopRightRadius" to "var(--l-switch-dot-radius, 99px)", "borderBottomRightRadius" to "var(--l-switch-dot-radius, 99px)", "borderBottomLeftRadius" to "var(--l-switch-dot-radius, 99px)")), "l-switch__dot--square" to _pS(_uM("borderTopLeftRadius" to "var(--l-switch-dot-radius, 3px)", "borderTopRightRadius" to "var(--l-switch-dot-radius, 3px)", "borderBottomRightRadius" to "var(--l-switch-dot-radius, 3px)", "borderBottomLeftRadius" to "var(--l-switch-dot-radius, 3px)")), "l-switch__placeholder" to _pS(_uM("overflow" to "visible", "position" to "absolute", "whiteSpace" to "nowrap", "pointerEvents" to "none", "top" to "50%", "transform" to "translateY(-50%)", "fontSize" to "var(--l-swtich-font-size, 14px)", "color" to "var(--l-swtich-text-color, white)", "boxSizing" to "content-box")), "l-switch__placeholder--checked" to _pS(_uM("right" to 0, "textAlign" to "right", "paddingRight" to "33.6px-var(--l-switch-dot-offset, 3px)")), "l-switch__placeholder--unchecked" to _pS(_uM("left" to 0, "paddingLeft" to "33.6px-var(--l-switch-dot-offset, 3px)")), "l-switch__loading" to _pS(_uM("flex" to 1)), "@TRANSITION" to _uM("l-switch__rail" to _uM("duration" to "300ms", "property" to "width", "timingFunction" to "cubic-bezier(0.4,0,0.2,1)"), "l-switch__dot" to _uM("duration" to "300ms", "property" to "backgroundColor,opacity,left,maxWidth,width,boxShadow,transform", "timingFunction" to "cubic-bezier(0.4,0,0.2,1)")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("change" to null, "update:modelValue" to null)
        var props = _nP(_uM("modelValue" to _uM("type" to "Boolean", "required" to false, "default" to null), "value" to _uM("type" to "Boolean", "required" to false, "default" to null), "defaultValue" to _uM("type" to "Boolean", "required" to false), "disabled" to _uM("type" to "Boolean", "required" to true, "default" to false), "readonly" to _uM("type" to "Boolean", "required" to true, "default" to false), "loading" to _uM("type" to "Boolean", "required" to true, "default" to false), "round" to _uM("type" to "Boolean", "required" to true, "default" to true), "rubberBand" to _uM("type" to "Boolean", "required" to true, "default" to true), "name" to _uM("type" to "String", "required" to false), "placeholder" to _uM("type" to "Array", "required" to true, "default" to _uA<String>()), "fontSize" to _uM("type" to "String", "required" to false), "width" to _uM("type" to "String", "required" to false), "height" to _uM("type" to "String", "required" to false), "dotSize" to _uM("type" to "String", "required" to false), "dotPressedSize" to _uM("type" to "String", "required" to false), "checkedColor" to _uM("type" to "String", "required" to false), "disabledColor" to _uM("type" to "String", "required" to false), "checkedDisabledColor" to _uM("type" to "String", "required" to false), "uncheckedColor" to _uM("type" to "String", "required" to false)))
        var propsNeedCastKeys = _uA(
            "modelValue",
            "value",
            "defaultValue",
            "disabled",
            "readonly",
            "loading",
            "round",
            "rubberBand",
            "placeholder"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
