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
import uts.sdk.modules.wjInputFieldModifier.insText
open class GenComponentsVirtualKeyboardVirtualKeyboard : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var modelValue: String by `$props`
    open var cursorPosition: Number? by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsVirtualKeyboardVirtualKeyboard) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsVirtualKeyboardVirtualKeyboard
            val _cache = __ins.renderCache
            val insTextFn = insText()
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val isUpperCase = ref(false)
            val isNumberMode = ref(false)
            val cursorPosition = computed(fun(): Number {
                return props.cursorPosition ?: 0
            }
            )
            val letterLayout = _uA(
                _uA(
                    "q",
                    "w",
                    "e",
                    "r",
                    "t",
                    "y",
                    "u",
                    "i",
                    "o",
                    "p",
                    "⌫"
                ),
                _uA(
                    "a",
                    "s",
                    "d",
                    "f",
                    "g",
                    "h",
                    "j",
                    "k",
                    "l"
                ),
                _uA(
                    "↑",
                    "z",
                    "x",
                    "c",
                    "v",
                    "b",
                    "n",
                    "m",
                    "!",
                    "-",
                    "?"
                ),
                _uA(
                    "123",
                    ",",
                    "",
                    ".",
                    "'",
                    "收"
                )
            ) as KeyboardLayout
            val numberLayout = _uA(
                _uA(
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6",
                    "7",
                    "8",
                    "9",
                    "0",
                    "⌫"
                ),
                _uA(
                    "@",
                    "#",
                    "\$",
                    "%",
                    "&",
                    "-",
                    "+",
                    "(",
                    ")",
                    "⌫"
                ),
                _uA(
                    "ABC",
                    ",",
                    "!",
                    ".",
                    "<",
                    ">",
                    "收"
                )
            ) as KeyboardLayout
            val currentLetterLayout = computed(fun(): KeyboardLayout {
                return if (isUpperCase.value) {
                    letterLayout.map(fun(row): UTSArray<String> {
                        return row.map(fun(key): String {
                            return if (key === "↑") {
                                key
                            } else {
                                key.toUpperCase()
                            }
                        })
                    })
                } else {
                    letterLayout
                }
            }
            )
            val getKeyDisplay = fun(key: String): String {
                return if (key === "↑") {
                    if (isUpperCase.value) {
                        "⇧"
                    } else {
                        "↑"
                    }
                } else {
                    key
                }
            }
            val toggleCase = fun(){
                isUpperCase.value = !isUpperCase.value
            }
            val handleKeyPress = fun(key: String){
                when (key) {
                    "↑" -> 
                        toggleCase()
                    "⌫" -> 
                        insTextFn.performBackspace()
                    "123" -> 
                        isNumberMode.value = true
                    "ABC" -> 
                        isNumberMode.value = false
                    "收" -> 
                        emit("close", true)
                    "" -> 
                        insTextFn.insertText(" ")
                    else -> 
                        {
                            console.log("11111112123123123123123123132123")
                            insTextFn.insertText(key)
                            if (isUpperCase.value) {
                                isUpperCase.value = false
                            }
                        }
                }
            }
            return fun(): Any? {
                return _cE("view", _uM("class" to "keyboard-container"), _uA(
                    if (isTrue(unref(isNumberMode))) {
                        _cE(Fragment, _uM("key" to 0), RenderHelpers.renderList(numberLayout, fun(row, rowIndex, __index, _cached): Any {
                            return _cE("view", _uM("key" to ("num-row-" + rowIndex), "class" to "keyboard-row"), _uA(
                                _cE(Fragment, null, RenderHelpers.renderList(row, fun(key, keyIndex, __index, _cached): Any {
                                    return _cE("button", _uM("key" to ("num-key-" + keyIndex), "class" to _nC(_uA(
                                        "key",
                                        _uM("function-key" to _uA(
                                            "⌫",
                                            "ABC"
                                        ).includes(key), "number-key" to !_uA(
                                            "⌫",
                                            "ABC"
                                        ).includes(key))
                                    )), "onClick" to fun(){
                                        handleKeyPress(key)
                                    }), _tD(key), 11, _uA(
                                        "onClick"
                                    ))
                                }), 128)
                            ))
                        }), 64)
                    } else {
                        _cE(Fragment, _uM("key" to 1), RenderHelpers.renderList(unref(currentLetterLayout), fun(row, rowIndex, __index, _cached): Any {
                            return _cE("view", _uM("key" to ("char-row-" + rowIndex), "class" to "keyboard-row"), _uA(
                                _cE(Fragment, null, RenderHelpers.renderList(row, fun(key, keyIndex, __index, _cached): Any {
                                    return _cE("button", _uM("key" to ("char-key-" + keyIndex), "class" to _nC(_uA(
                                        "key",
                                        _uM("function-key" to _uA(
                                            "⌫",
                                            "↑",
                                            "123"
                                        ).includes(key), "uppercase-indicator" to (key === "↑" && unref(isUpperCase)), "kong" to (key == ""))
                                    )), "onClick" to fun(){
                                        handleKeyPress(key)
                                    }
                                    ), _tD(getKeyDisplay(key)), 11, _uA(
                                        "onClick"
                                    ))
                                }
                                ), 128)
                            ))
                        }
                        ), 128)
                    }
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
                return _uM("keyboard-container" to _pS(_uM("backgroundImage" to "none", "backgroundColor" to "#f0f0f0", "paddingTop" to "6rpx", "paddingRight" to "6rpx", "paddingBottom" to "6rpx", "paddingLeft" to "6rpx", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "maxWidth" to "650rpx", "marginTop" to 0, "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto", "flexDirection" to "column")), "keyboard-row" to _uM(".keyboard-container " to _uM("flexDirection" to "row", "justifyContent" to "center", "marginBottom" to "4rpx")), "key" to _uM(".keyboard-container .keyboard-row " to _uM("width" to "50rpx", "height" to "36rpx", "marginTop" to 0, "marginRight" to "3rpx", "marginBottom" to 0, "marginLeft" to "3rpx", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx", "backgroundImage" to "none", "backgroundColor" to "#ffffff", "fontSize" to "26rpx", "paddingTop" to 0, "paddingRight" to 0, "paddingBottom" to 0, "paddingLeft" to 0, "boxShadow" to "0 2rpx 4rpx rgba(0, 0, 0, 0.1)", "textAlign" to "center", "lineHeight" to "36rpx"), ".keyboard-container .keyboard-row .function-key" to _uM("backgroundImage" to "none", "backgroundColor" to "#d3d3d3", "width" to "50rpx", "fontSize" to "24rpx"), ".keyboard-container .keyboard-row .uppercase-indicator" to _uM("backgroundImage" to "none", "backgroundColor" to "#007aff", "color" to "#FFFFFF"), ".keyboard-container .keyboard-row .number-key" to _uM("backgroundImage" to "none", "backgroundColor" to "#ffffff"), ".keyboard-container .keyboard-row .kong" to _uM("flex" to 1)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("update:modelValue" to null, "close" to null, "onKey" to null)
        var props = _nP(_uM("modelValue" to _uM("type" to "String", "required" to true), "cursorPosition" to _uM("type" to "Number", "required" to false)))
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
