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
open class GenUniModulesLimePickerComponentsLPickerItemLPickerItem : VueComponent, PickerItemProps {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    override var options: UTSArray<PickerColumnItem> by `$props`
    override var value: Any? by `$props`
    override var column: Number by `$props`
    override var name: Any? by `$props`
    open var setIndex: (index: Number) -> Unit
        get() {
            return unref(this.`$exposed`["setIndex"]) as (index: Number) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setIndex", value)
        }
    open var setValue: (value: PickerValue?) -> Unit
        get() {
            return unref(this.`$exposed`["setValue"]) as (value: PickerValue?) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setValue", value)
        }
    open var getIndexByValue: (kVal: PickerValue?) -> Number
        get() {
            return unref(this.`$exposed`["getIndexByValue"]) as (kVal: PickerValue?) -> Number
        }
        set(value) {
            setRefValue(this.`$exposed`, "getIndexByValue", value)
        }
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesLimePickerComponentsLPickerItemLPickerItem, _arg1: SetupContext) -> Any? = fun(__props, ref1): Any? {
            var __expose = ref1.expose
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesLimePickerComponentsLPickerItemLPickerItem
            val _cache = __ins.renderCache
            val themeMode = inject("limeConfigProviderTheme", computed(fun(): String {
                return "light"
            }
            ))
            val instance = getCurrentInstance()!!
            val props = __props
            val picker = inject<LPickerComponentPublicInstance?>("limePicker", null)
            val pickerItemInstanceArray = inject<UTSArray<LPickerItemComponentPublicInstance>?>("limePickerItems", null)
            val manageChildInList = inject<ManageChildInList?>("limePickerManageChildInList", null)
            manageChildInList?.invoke(instance.proxy!! as LPickerItemComponentPublicInstance, true)
            val onPick = inject<OnPick?>("limePickerOnPick", null)
            val updateItems = inject<UpdateItems?>("limePickerUpdateItems", null)
            val curIndex = ref(0)
            val curValue = ref<PickerValue?>(props.value)
            val column = computed(fun(): Number {
                return if (props.column != -1) {
                    props.column
                } else {
                    pickerItemInstanceArray?.indexOf(instance.proxy!! as LPickerItemComponentPublicInstance) ?: props.column
                }
            }
            )
            val elementPosition = computed(fun(): UTSArray<Boolean> {
                val totalElements = pickerItemInstanceArray?.length ?: 0
                return _uA(
                    column.value == 0,
                    column.value == totalElements - 1
                )
            }
            )
            val innerIndex = computed(fun(): UTSArray<Number> {
                return _uA(
                    curIndex.value
                )
            }
            )
            val isDarkMode = computed(fun(): Boolean {
                return themeMode.value == "dark"
            }
            )
            val maskStyle = computed(fun(): String {
                if (isDarkMode.value) {
                    return "background-image: \n				linear-gradient(180deg, #242424, rgba(36, 36, 36, 0)),\n				linear-gradient(0deg,   #242424, rgba(36, 36, 36, 0))"
                }
                return " "
            }
            )
            val maskTopStyle = computed(fun(): String {
                if (isDarkMode.value) {
                    return "background-image: linear-gradient(\n				to bottom,\n				#242424,\n				rgba(36, 36, 36, 0)\n			)"
                }
                return "background-image: linear-gradient(\n				to bottom,\n				#fff,\n				rgba(255, 255, 255, 0)\n			)"
            }
            )
            val maskBottomStyle = computed(fun(): String {
                if (isDarkMode.value) {
                    return "background-image: linear-gradient(\n				to top,\n				#242424,\n				rgba(36, 36, 36, 0)\n			)"
                }
                return "background-image: linear-gradient(\n			to top,\n			#fff,\n			rgba(255, 255, 255, 0)\n		)"
            }
            )
            val indicatorStyles = computed(fun(): String {
                val _elementPosition_value = elementPosition.value
                val isFirst = _elementPosition_value[0]
                val isLast = _elementPosition_value[1]
                var style = "border-width: 0;"
                if (isFirst) {
                    style += "\n				border-top-left-radius:6px; \n				border-bottom-left-radius:6px;\n				border-top-right-radius: 0px;\n				border-bottom-right-radius: 0px;\n			"
                }
                if (isLast) {
                    style += "\n				border-top-left-radius: 0px;\n				border-bottom-left-radius: 0px;\n				border-top-right-radius:6px; \n				border-bottom-right-radius:6px;\n			"
                }
                val color = if (isDarkMode.value) {
                    "rgba(255, 255, 255, 0.06)"
                } else {
                    "rgba(0, 0, 0, 0.04)"
                }
                return "\n			" + style + "\n			height: " + (picker?.itemHeight ?: "50px") + ";\n			background-color: " + color + "; " + (picker?.indicatorStyle ?: "")
            }
            )
            val itemStyles = computed(fun(): Map<String, Any> {
                val style = Map<String, Any>()
                if (picker?.itemColor != null) {
                    style.set("color", picker.itemColor!!)
                }
                if (picker?.itemFontSize != null) {
                    style.set("font-size", picker.itemFontSize!!)
                }
                if (picker?.itemHeight != null) {
                    style.set("line-height", picker.itemHeight!!)
                    style.set("height", picker.itemHeight!!)
                }
                return style
            }
            )
            val itemActiveStyles = computed(fun(): Map<String, Any> {
                val style = Map<String, Any>()
                if (picker?.itemActiveColor != null) {
                    style.set("color", picker.itemActiveColor!!)
                }
                if (picker?.itemActiveFontWeight != null) {
                    style.set("font-weight", picker.itemActiveFontWeight!!)
                }
                return style
            }
            )
            val getIndexByValue = fun(kVal: PickerValue?): Number {
                var defaultIndex: Number = 0
                if (kVal != null) {
                    defaultIndex = props.options.findIndex(fun(item): Boolean {
                        return item.value == kVal
                    }
                    )
                }
                return if (defaultIndex < 0) {
                    0
                } else {
                    defaultIndex
                }
            }
            val setIndex = fun(index: Number){
                var lastIndex = curIndex.value
                var _index = clamp(index, 0, props.options.length - 1)
                if (props.options.length > _index) {
                    curIndex.value = _index
                    curValue.value = props.options[_index].value
                }
            }
            val setValue = fun(value: PickerValue?){
                if (value == curValue.value) {
                    return
                }
                curValue.value = value
                val index = getIndexByValue(value)
                setIndex(index)
            }
            val setUpdateItems = fun(){
                val index = clamp(curIndex.value, 0, props.options.length - 1)
                val curItem = if (props.options.length > index) {
                    props.options[index]
                } else {
                    null
                }
                if (curItem == null) {
                    return
                }
                updateItems?.invoke(curItem, index, column.value)
            }
            val handlePick = fun(e: UniPickerViewChangeEvent){
                if (props.options.length == 0) {
                    return
                }
                val index = clamp(e.detail.value[0], 0, props.options.length - 1)
                val curItem = props.options[index]
                if (index == curIndex.value) {
                    return
                }
                setIndex(index)
                onPick?.invoke(curItem, index, column.value)
            }
            val stopValue = watch(fun(): PickerValue? {
                return props.value
            }
            , fun(v: PickerValue?){
                setValue(v)
                setUpdateItems()
            }
            , WatchOptions(immediate = true))
            val itemRef = ref<UniElement?>(null)
            val updateItemStyle = fun(){
                if (itemRef.value != null) {
                    val ctx = itemRef.value!!.getDrawableContext()!!
                    val height = unitConvert(picker?.itemHeight ?: 50)
                    val fontSize = unitConvert(picker?.itemFontSize ?: "32rpx")
                    val rect = itemRef.value!!.getBoundingClientRect()
                    val x = itemRef.value!!.offsetWidth / 2
                    val itemActiveFontWeight = picker?.itemActiveFontWeight ?: 700
                    itemRef.value!!.style.setProperty("height", height * props.options.length)
                    ctx.reset()
                    val color = picker?.itemColor ?: (if (isDarkMode.value) {
                        "rgba(255,255,255,0.88)"
                    } else {
                        "rgba(0,0,0,0.88)"
                    }
                    )
                    val itemActiveColor = picker?.itemActiveColor ?: (if (isDarkMode.value) {
                        "rgba(255,255,255,0.88)"
                    } else {
                        "rgba(0,0,0,0.88)"
                    }
                    )
                    ctx.font = "" + fontSize + "px"
                    ctx.textAlign = "center"
                    ctx.lineWidth = 0.5
                    props.options.forEach(fun(item, index){
                        val y = height * index + fontSize + (height - fontSize) * 0.4
                        val isActive = index == curIndex.value && itemActiveFontWeight > 600
                        ctx.fillStyle = if (isActive) {
                            itemActiveColor
                        } else {
                            color
                        }
                        ctx.strokeStyle = if (isActive) {
                            itemActiveColor
                        } else {
                            color
                        }
                        ctx.fillText(item.label, x, y)
                        if (isActive) {
                            ctx.strokeText(item.label, x, y)
                        }
                    }
                    )
                    ctx.update()
                }
            }
            watchEffect(fun(){
                if (curIndex.value > 0) {
                }
                if (isDarkMode.value) {
                    nextTick(updateItemStyle)
                } else {
                    nextTick(updateItemStyle)
                }
            }
            )
            val stop = watch(fun(): UTSArray<PickerColumnItem> {
                return props.options
            }
            , fun(v: UTSArray<PickerColumnItem>, o: UTSArray<PickerColumnItem>){
                nextTick(updateItemStyle)
            }
            )
            val resizeObserver = UniResizeObserver(fun(entries: UTSArray<UniResizeObserverEntry>){
                updateItemStyle()
            }
            )
            val stopWatch = watch(fun(): UniElement? {
                return itemRef.value
            }
            , fun(el: UniElement?){
                if (el == null) {
                    return
                }
                resizeObserver.observe(el)
            }
            )
            onBeforeUnmount(fun(){
                manageChildInList?.invoke(instance.proxy!! as LPickerItemComponentPublicInstance, false)
                stop()
                stopWatch()
                resizeObserver.disconnect()
            }
            )
            __expose(_uM("setIndex" to setIndex, "setValue" to setValue, "getIndexByValue" to getIndexByValue))
            return fun(): Any? {
                val _component_picker_view_column = resolveComponent("picker-view-column")
                val _component_picker_view = resolveComponent("picker-view")
                return _cV(_component_picker_view, _uM("class" to "l-picker-item__group", "style" to _nS(_uM("opacity" to if (_ctx.options.length > 0) {
                    1
                } else {
                    0
                }
                )), "mask-style" to unref(maskStyle), "mask-top-style" to unref(maskTopStyle), "mask-bottom-style" to unref(maskBottomStyle), "indicator-style" to unref(indicatorStyles), "mask-class" to "l-picker-item__mask", "value" to unref(innerIndex), "onChange" to handlePick, "indicator-class" to "l-picker-item__indicator"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_picker_view_column, _uM("class" to "l-picker-item__wrapper"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cE("view", _uM("ref_key" to "itemRef", "ref" to itemRef), null, 512)
                            )
                        }
                        ), "_" to 1))
                    )
                }
                ), "_" to 1), 8, _uA(
                    "style",
                    "mask-style",
                    "mask-top-style",
                    "mask-bottom-style",
                    "indicator-style",
                    "value"
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
                return _uM("l-picker-item__group" to _pS(_uM("flex" to 1)), "l-picker-item__group-item" to _pS(_uM("height" to "var(--l-picker-item-height, 50px)", "lineHeight" to "var(--l-picker-item-height, 50px)", "textAlign" to "center", "transitionDuration" to "100ms", "transitionProperty" to "fontWeight,color", "transitionTimingFunction" to "linear", "fontWeight" to "400", "color" to "var(--l-picker-item-color, #000000E0)", "fontSize" to "var(--l-picker-item-font-size, 16px)", "whiteSpace" to "nowrap")), "l-picker-item__group-item--active" to _pS(_uM("color" to "var(--l-picker-item-active-color, #000000E0)", "fontWeight" to "var(--l-picker-item-active-font-weight, 700)")), "l-picker-item__wrapper" to _pS(_uM("width" to "100%")), "@TRANSITION" to _uM("l-picker-item__group-item" to _uM("duration" to "100ms", "property" to "fontWeight,color", "timingFunction" to "linear")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("options" to _uM("type" to "Array", "required" to true, "default" to _uA<PickerColumnItem>()), "value" to _uM("type" to _uA(
            "String",
            "Number"
        ), "required" to false), "column" to _uM("type" to "Number", "required" to true, "default" to -1), "name" to _uM("type" to _uA(
            "String",
            "Number"
        ), "required" to false)))
        var propsNeedCastKeys = _uA(
            "options",
            "column"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
