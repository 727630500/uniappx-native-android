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
open class GenComponentsQuestionOptionsQuestionOptions : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var options: UTSArray<followAlongItemOptions> by `$props`
    open var resultOptions: UTSArray<followAlongItemOptions> by `$props`
    open var userOption: String by `$props`
    open var modelValue: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsQuestionOptionsQuestionOptions) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsQuestionOptionsQuestionOptions
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val handleSelect = fun(index: Number): Unit {
                emit("update:modelValue", index)
                emit("select", index)
            }
            fun gen_highlightText_fn(text: String, keyword: String?): UTSArray<underline>? {
                if (keyword == null) {
                    return _uA<underline>(underline(text = text, highlight = false))
                }
                if (keyword == "" || text == "") {
                    return null
                }
                val regex = UTSRegExp("(" + keyword + ")", "g")
                val parts = text.split(regex)
                val result = _uA<underline>()
                run {
                    var i: Number = 0
                    while(i < parts.length){
                        val part = parts[i]
                        if (part == keyword) {
                            result.push(underline(text = part, highlight = true))
                        } else if (part !== "") {
                            result.push(underline(text = part, highlight = false))
                        }
                        i++
                    }
                }
                return result
            }
            val highlightText = ::gen_highlightText_fn
            return fun(): Any? {
                return if (props.resultOptions.length == 0) {
                    _cE("view", _uM("key" to 0, "class" to "options-container"), _uA(
                        _cE(Fragment, null, RenderHelpers.renderList(props.options, fun(option, index, __index, _cached): Any {
                            return _cE("view", _uM("key" to index, "class" to _nC(_uA(
                                "option-item",
                                _uM("selected" to (_ctx.modelValue === index))
                            )), "onClick" to fun(){
                                handleSelect(index)
                            }), _uA(
                                _cE("view", _uM("class" to "active-box", "style" to _nS(_uM("opacity" to if (_ctx.modelValue === index) {
                                    1
                                } else {
                                    0
                                }))), _uA(
                                    _cE("view", _uM("class" to "active-box_item"))
                                ), 4),
                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "flex" to "1", "justify-content" to "flex-start"))), _uA(
                                    _cE("text", _uM("class" to _nC(_uA(
                                        _uM("active" to (_ctx.modelValue === index)),
                                        "option-text"
                                    ))), _tD(option.option) + ".", 3),
                                    _cE(Fragment, null, RenderHelpers.renderList(highlightText(option.describe, option?.describeUnderlinePart), fun(txt, __key, __index, _cached): Any {
                                        return _cE("text", _uM("class" to _nC(_uA(
                                            "option-text",
                                            _uM("underline_css" to txt.highlight, "active" to (_ctx.modelValue === index))
                                        ))), _tD(txt.text), 3)
                                    }), 256)
                                ), 4)
                            ), 10, _uA(
                                "onClick"
                            ))
                        }), 128)
                    ))
                } else {
                    _cE("view", _uM("key" to 1, "class" to "options-container"), _uA(
                        _cE(Fragment, null, RenderHelpers.renderList(props.resultOptions, fun(option, index, __index, _cached): Any {
                            return _cE("view", _uM("key" to index, "class" to _nC(_uA(
                                "option-item",
                                _uM("selected" to (option.option == _ctx.userOption), "correct" to option.isRight, "fail" to (option.option == _ctx.userOption && !option.isRight))
                            ))), _uA(
                                _cE("view", _uM("class" to "active-box", "style" to _nS(_uM("opacity" to if (option.option == _ctx.userOption) {
                                    1
                                } else {
                                    0
                                }
                                ))), _uA(
                                    _cE("view", _uM("class" to "active-box_item"))
                                ), 4),
                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "flex" to "1", "justify-content" to "flex-start"))), _uA(
                                    _cE("text", _uM("class" to _nC(_uA(
                                        _uM("active" to (option.option == _ctx.userOption)),
                                        "option-text"
                                    ))), _tD(option.option) + ".", 3),
                                    _cE(Fragment, null, RenderHelpers.renderList(highlightText(option.describe, option?.describeUnderlinePart), fun(txt, __key, __index, _cached): Any {
                                        return _cE("text", _uM("class" to _nC(_uA(
                                            "option-text",
                                            _uM("active" to (option.option == _ctx.userOption), "underline_css" to txt.highlight)
                                        ))), _tD(txt.text), 3)
                                    }
                                    ), 256)
                                ), 4)
                            ), 2)
                        }
                        ), 128)
                    ))
                }
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("option-item" to _uM("" to _uM("marginBottom" to "8rpx", "flexDirection" to "row", "alignItems" to "center", "height" to "32rpx", "backgroundImage" to "none", "backgroundColor" to "#f6f6f6", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "boxSizing" to "border-box"), ".selected" to _uM("backgroundColor" to "#E2E6F6"), ".correct" to _uM("backgroundImage" to "none", "backgroundColor" to "#439216"), ".fail" to _uM("backgroundImage" to "none", "backgroundColor" to "#FFBBBB")), "option-text" to _uM("" to _uM("fontWeight" to "700", "fontSize" to "14rpx", "color" to "#3D3D3D"), ".active" to _uM("color" to "#3A58EB")), "active-box" to _pS(_uM("width" to "17rpx", "height" to "17rpx", "borderTopWidth" to "1.17rpx", "borderRightWidth" to "1.17rpx", "borderBottomWidth" to "1.17rpx", "borderLeftWidth" to "1.17rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#3A58EB", "borderRightColor" to "#3A58EB", "borderBottomColor" to "#3A58EB", "borderLeftColor" to "#3A58EB", "alignItems" to "center", "justifyContent" to "center", "borderTopLeftRadius" to "100rpx", "borderTopRightRadius" to "100rpx", "borderBottomRightRadius" to "100rpx", "borderBottomLeftRadius" to "100rpx", "marginRight" to "17rpx")), "active-box_item" to _uM(".active-box " to _uM("width" to "7.62rpx", "height" to "7.62rpx", "backgroundColor" to "#3A58EB", "borderTopLeftRadius" to "100rpx", "borderTopRightRadius" to "100rpx", "borderBottomRightRadius" to "100rpx", "borderBottomLeftRadius" to "100rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("update:modelValue" to null, "select" to null)
        var props = _nP(_uM("options" to _uM("type" to "Array", "default" to fun(): UTSArray<followAlongItemOptions> {
            return _uA()
        }
        ), "resultOptions" to _uM("type" to "Array", "default" to fun(): UTSArray<followAlongItemOptions> {
            return _uA()
        }
        ), "userOption" to _uM("type" to "String", "default" to fun(): String {
            return ""
        }
        ), "modelValue" to _uM("type" to "Number", "default" to -1)))
        var propsNeedCastKeys = _uA(
            "options",
            "resultOptions",
            "userOption",
            "modelValue"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
