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
import uts.sdk.modules.limeAudioPlayer.createInnerAudioContext
open class GenComponentsSubCheckListInputSubCheckListInput : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var list: UTSArray<tempTiMu> by `$props`
    open var isClear: Boolean by `$props`
    open var isCheck: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsSubCheckListInputSubCheckListInput) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsSubCheckListInputSubCheckListInput
            val _cache = __ins.renderCache
            val ctx = createInnerAudioContext()
            ctx.src = getSondUrl("学习答题不通过提示音")
            val props = __props
            val _list = ref(props.list)
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val isShowKeyboard = ref(ucsShare.getStore("isShowKeyboard") as Boolean)
            val focusIndex = ref(0)
            val _input = ref(null)
            val _nowIndex = ref(0)
            val cursorPosition = ref(0)
            val isShowTypewriting = ref(false)
            watchEffect(fun(){
                _list.value = _uA()
                console.log(props.list)
                setTimeout(fun(){
                    _list.value = props.list
                }
                , 0)
            }
            )
            val inputClick = fun(index: Number){
                _nowIndex.value = index
                focusIndex.value = index
                if (isShowKeyboard.value) {
                }
                emit("showKeyboard", index, _list.value[_nowIndex.value].content, true)
            }
            fun gen_inputFn_fn(str: Any, _index: Number) {
                if (focusIndex.value != _index) {
                    return
                }
                var _item = _list.value[_nowIndex.value]
                _item.content = str as String
                if (_item.content.length == _item.title.length) {
                    var __index = _list.value.findIndex(fun(item): Boolean {
                        return (item.content.length != item.title.length) || item.content == ""
                    }
                    )
                    if (__index == -1) {
                        console.log("全部完成")
                        emit("showKeyboard", _index, str, false)
                        return
                    }
                    setTimeout(fun(){
                        focusIndex.value = __index
                        _nowIndex.value = __index
                    }
                    , 10)
                }
                emit("showKeyboard", _index, str, false)
            }
            val inputFn = ::gen_inputFn_fn
            fun gen_showIndex_fn(index: Number) {
                console.log(index)
            }
            val showIndex = ::gen_showIndex_fn
            fun gen_clear_fn() {
                emit("clear", null)
            }
            val clear = ::gen_clear_fn
            watch(props.list, fun(){})
            return fun(): Any? {
                val _component_wj_input = resolveEasyComponent("wj-input", GenUniModulesWjInputComponentsWjInputWjInputClass)
                return _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "flex-wrap" to "wrap", "flex" to "1"))), _uA(
                        if (unref(_list).length > 0) {
                            _cE(Fragment, _uM("key" to 0), RenderHelpers.renderList(unref(_list), fun(item, index, __index, _cached): Any {
                                return _cE(Fragment, null, _uA(
                                    if (item.type == 0) {
                                        _cE("view", _uM("key" to 0, "class" to _nC(_uA(
                                            "item",
                                            _uM("flex-1" to (item.content == ""))
                                        ))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("width" to "100%", "height" to "100%", "position" to "relative"))), _uA(
                                                _cV(_component_wj_input, _uM("center" to "center", "isFocus" to (unref(focusIndex) == index), "isAutoWidth" to true, "style" to _nS(_uM("width" to "100%", "height" to "100%", "position" to "absolute", "z-index" to "99")), "text" to item.content, "onUpdate:text" to fun(`$event`: String){
                                                    item.content = `$event`
                                                }, "isCheck" to props.isCheck, "checkText" to item.title, "onInputTap" to fun(){
                                                    inputClick(index)
                                                }, "onInput" to fun(`$event`: Any){
                                                    inputFn(`$event`, index)
                                                }, "onOnErr" to fun(){
                                                    unref(ctx).play()
                                                }), null, 8, _uA(
                                                    "isFocus",
                                                    "style",
                                                    "text",
                                                    "onUpdate:text",
                                                    "isCheck",
                                                    "checkText",
                                                    "onInputTap",
                                                    "onInput",
                                                    "onOnErr"
                                                )),
                                                _cE("text", _uM("style" to _nS(_uM("color" to "#fff", "margin" to "0 10rpx", "font-size" to "32rpx"))), _tD(item.content), 5)
                                            ), 4)
                                        ), 2)
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    if (item.type == 1) {
                                        _cE("text", _uM("key" to 1, "style" to _nS(_uM("font-size" to "18rpx", "line-height" to "26rpx", "height" to "26rpx"))), _tD(item.title), 5)
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    _cE("view", _uM("style" to _nS(_uM("width" to "6rpx"))), null, 4)
                                ), 64)
                            }), 256)
                        } else {
                            _cC("v-if", true)
                        }
                    ), 4),
                    if (isTrue(_ctx.isClear && unref(_list).length > 0)) {
                        _cE("text", _uM("key" to 0, "class" to "clear", "onClick" to clear), " 清除 ")
                    } else {
                        _cC("v-if", true)
                    }
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
                return _uM("item" to _pS(_uM("borderBottomWidth" to "2rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#000000", "alignItems" to "center", "paddingTop" to 0, "paddingRight" to "6rpx", "paddingBottom" to 0, "paddingLeft" to "6rpx", "height" to "34rpx", "minWidth" to "60rpx")), "flex-1" to _pS(_uM("flex" to 1)), "clear" to _pS(_uM("width" to "45rpx", "height" to "23rpx", "backgroundImage" to "none", "backgroundColor" to "#7BC74F", "borderTopLeftRadius" to "11rpx", "borderTopRightRadius" to "11rpx", "borderBottomRightRadius" to "11rpx", "borderBottomLeftRadius" to "11rpx", "fontSize" to "13rpx", "textAlign" to "center", "lineHeight" to "23rpx", "color" to "#ffffff")), "type1" to _pS(_uM("color" to "#5A9F32")), "type2" to _pS(_uM("color" to "#E54E4E")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("clear" to null, "showKeyboard" to null, "getCursor" to null)
        var props = _nP(_uM("list" to _uM("type" to "Array", "default" to fun(): UTSArray<tempTiMu> {
            return _uA()
        }
        ), "isClear" to _uM("type" to "Boolean", "default" to fun(): Boolean {
            return false
        }
        ), "isCheck" to _uM("type" to "Boolean", "default" to fun(): Boolean {
            return false
        }
        )))
        var propsNeedCastKeys = _uA(
            "list",
            "isClear",
            "isCheck"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
