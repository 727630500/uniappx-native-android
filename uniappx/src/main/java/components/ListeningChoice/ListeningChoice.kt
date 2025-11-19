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
import uts.sdk.modules.uniPreviewImage.PreviewImageOptions as PreviewImageOptions1
import uts.sdk.modules.uniPreviewImage.previewImage as uni_previewImage
open class GenComponentsListeningChoiceListeningChoice : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var options: UTSArray<followAlongItemOptions> by `$props`
    open var index: Number by `$props`
    open var disabled: Boolean by `$props`
    open var selectedValue: String by `$props`
    open var resetSelection: () -> Unit
        get() {
            return unref(this.`$exposed`["resetSelection"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "resetSelection", value)
        }
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsListeningChoiceListeningChoice, _arg1: SetupContext) -> Any? = fun(__props, ref1): Any? {
            var __expose = ref1.expose
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsListeningChoiceListeningChoice
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val selectedIndex = ref<Number>(-1)
            val defaultOptions = _uA(
                followAlongItemOptions(option = "A", describe = "听短对话选答案", isRight = false, describeUnderlinePart = null, analysis = null),
                followAlongItemOptions(option = "B", describe = "听长对话选答案", isRight = false, describeUnderlinePart = null, analysis = null),
                followAlongItemOptions(option = "C", describe = "听短文选答案", isRight = false, describeUnderlinePart = null, analysis = null),
                followAlongItemOptions(option = "D", describe = "听文章/看文章/填空", isRight = false, describeUnderlinePart = null, analysis = null)
            ) as UTSArray<followAlongItemOptions>
            val displayOptions = ref<UTSArray<followAlongItemOptions>>(defaultOptions)
            watchEffect(fun(){
                var newOptions = JSON.parse<UTSArray<followAlongItemOptions>>(JSON.stringify(props.options))
                if (newOptions != null && newOptions.length > 0) {
                    displayOptions.value = newOptions
                } else {
                    displayOptions.value = defaultOptions
                }
            }
            )
            val correctAnswer = computed(fun(): String {
                val correctOption = displayOptions.value.find(fun(option): Boolean {
                    return option.isRight
                }
                )
                return if (correctOption != null) {
                    correctOption.option
                } else {
                    ""
                }
            }
            )
            fun gen_getChoiceItemClass_fn(index: Number): String {
                val option = displayOptions.value[index]
                val classes: UTSArray<String> = _uA()
                if (props.disabled) {
                    if (option.option == props.selectedValue) {
                        if (props.selectedValue != correctAnswer.value) {
                            classes.push("choice-item-error")
                        }
                    }
                    if (option.isRight) {
                        classes.push("choice-item-correct")
                    }
                } else {
                    if (selectedIndex.value === index) {
                        classes.push("choice-item-selected")
                    }
                }
                return classes.join(" ")
            }
            val getChoiceItemClass = ::gen_getChoiceItemClass_fn
            fun gen_getChoiceTextClass_fn(index: Number): String {
                val option = displayOptions.value[index]
                val classes = _uA(
                    "choice-text"
                ) as UTSArray<String>
                if (props.disabled) {
                    if (option.option === props.selectedValue) {
                        classes.push("choice-text-selected")
                    }
                } else {
                    if (selectedIndex.value === index) {
                        classes.push("choice-text-active")
                    }
                }
                return classes.join(" ")
            }
            val getChoiceTextClass = ::gen_getChoiceTextClass_fn
            fun gen_selectChoice_fn(index: Number) {
                if (props.disabled) {
                    return
                }
                selectedIndex.value = index
                val selectedOption = displayOptions.value[index]
                emit("select", selectedOption.option, index, props.index)
            }
            val selectChoice = ::gen_selectChoice_fn
            fun gen_resetSelection_fn() {
                selectedIndex.value = -1
            }
            val resetSelection = ::gen_resetSelection_fn
            fun gen_viewImg_fn(url: String) {
                uni_previewImage(PreviewImageOptions1(urls = _uA(
                    url
                )))
            }
            val viewImg = ::gen_viewImg_fn
            __expose(_uM("resetSelection" to resetSelection))
            return fun(): Any? {
                return _cE("view", _uM("class" to "choice-container"), _uA(
                    _cE("view", _uM("class" to "choice-list"), _uA(
                        _cE(Fragment, null, RenderHelpers.renderList(unref(displayOptions), fun(item, index, __index, _cached): Any {
                            return _cE("view", _uM("key" to index, "style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                _cE("view", _uM("class" to _nC(_uA(
                                    "choice-item",
                                    getChoiceItemClass(index)
                                )), "onClick" to fun(){
                                    selectChoice(index)
                                }
                                ), _uA(
                                    _cE("text", _uM("class" to _nC(getChoiceTextClass(index))), _tD(item.option) + ". " + _tD(item.describe), 3),
                                    _cE("view", null, _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(item.picUrlList, fun(src, __key, __index, _cached): Any {
                                            return _cE("image", _uM("src" to src, "style" to _nS(_uM("width" to "145rpx", "margin-top" to "4rpx", "margin-left" to "16rpx")), "mode" to "widthFix", "onClick" to withModifiers(fun(){
                                                viewImg(src)
                                            }
                                            , _uA(
                                                "stop"
                                            ))), null, 12, _uA(
                                                "src",
                                                "onClick"
                                            ))
                                        }
                                        ), 256)
                                    ))
                                ), 10, _uA(
                                    "onClick"
                                ))
                            ), 4)
                        }
                        ), 128)
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
                return _uM("choice-container" to _pS(_uM("backgroundColor" to "#ffffff")), "choice-list" to _pS(_uM("display" to "flex")), "choice-item" to _uM("" to _uM("display" to "flex", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx", "backgroundColor" to "#ffffff", "transitionProperty" to "all", "transitionDuration" to "0.3s", "transitionTimingFunction" to "ease", "marginBottom" to "4rpx"), ".choice-item-selected" to _uM("backgroundColor" to "#5A9F32"), ".choice-item-correct" to _uM("backgroundColor" to "#5A9F32"), ".choice-item-error" to _uM("backgroundColor" to "#ff4444")), "choice-text" to _uM(".choice-item " to _uM("flex" to 1, "fontSize" to "11.72rpx", "color" to "#333333"), ".choice-item .choice-text-active" to _uM("color" to "#ffffff"), ".choice-item .choice-text-selected" to _uM("color" to "#007AFF")), "@TRANSITION" to _uM("choice-item" to _uM("property" to "all", "duration" to "0.3s", "timingFunction" to "ease")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("select" to null)
        var props = _nP(_uM("options" to _uM("type" to "Array", "required" to false, "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "index" to _uM("type" to "Number", "required" to false, "default" to fun(): Number {
            return 0
        }
        ), "disabled" to _uM("type" to "Boolean", "required" to false, "default" to false), "selectedValue" to _uM("type" to "String", "required" to false, "default" to "")))
        var propsNeedCastKeys = _uA(
            "options",
            "index",
            "disabled",
            "selectedValue"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
