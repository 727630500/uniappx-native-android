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
open class GenComponentsSubCheckListSubCheckList : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var list: UTSArray<tempTiMu> by `$props`
    open var listResult: UTSArray<tmResult> by `$props`
    open var isClear: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsSubCheckListSubCheckList) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsSubCheckListSubCheckList
            val _cache = __ins.renderCache
            val props = __props
            val _list = ref(_uA<String>())
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            fun gen_clear_fn() {
                emit("clear", null)
            }
            val clear = ::gen_clear_fn
            fun gen_delFn_fn(item: tempTiMu) {
                emit("del", item)
            }
            val delFn = ::gen_delFn_fn
            watch(props.list, fun(){})
            return fun(): Any? {
                return _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "flex-wrap" to "wrap", "flex" to "1", "align-items" to "center"))), _uA(
                        if (props.list.length > 0) {
                            _cE(Fragment, _uM("key" to 0), RenderHelpers.renderList(props.list, fun(item, __key, __index, _cached): Any {
                                return _cE(Fragment, null, _uA(
                                    if (item.type == 0) {
                                        _cE("view", _uM("key" to 0, "class" to _nC(_uA(
                                            "item",
                                            _uM("flex-1" to (item.content == ""))
                                        ))), _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "18rpx", "line-height" to "26rpx", "height" to "26rpx")), "onClick" to fun(){
                                                delFn(item)
                                            }), _tD(item.content), 13, _uA(
                                                "onClick"
                                            ))
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
                        ,
                        if (props.listResult.length > 0) {
                            _cE(Fragment, _uM("key" to 1), RenderHelpers.renderList(props.listResult, fun(item, __key, __index, _cached): Any {
                                return _cE(Fragment, null, _uA(
                                    _cE("view", _uM("class" to _nC(_uA(
                                        "item",
                                        _uM("flex-1" to (item.title == ""))
                                    ))), _uA(
                                        _cE("text", _uM("style" to _nS(_uM("font-size" to "18rpx", "line-height" to "26rpx", "height" to "26rpx")), "class" to _nC(_uM("type1" to (item.type == 1), "type2" to (item.type == 2)))), _tD(item.title), 7)
                                    ), 2),
                                    _cE("view", _uM("style" to _nS(_uM("width" to "6rpx"))), null, 4)
                                ), 64)
                            }), 256)
                        } else {
                            _cC("v-if", true)
                        }
                    ), 4),
                    if (isTrue(_ctx.isClear && props.list.length > 0)) {
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
                return _uM("item" to _pS(_uM("borderBottomWidth" to "2rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#000000", "alignItems" to "center", "paddingTop" to 0, "paddingRight" to "6rpx", "paddingBottom" to 0, "paddingLeft" to "6rpx")), "flex-1" to _pS(_uM("flex" to 1)), "clear" to _pS(_uM("width" to "45rpx", "height" to "23rpx", "backgroundImage" to "none", "backgroundColor" to "#7BC74F", "borderTopLeftRadius" to "11rpx", "borderTopRightRadius" to "11rpx", "borderBottomRightRadius" to "11rpx", "borderBottomLeftRadius" to "11rpx", "fontSize" to "13rpx", "textAlign" to "center", "lineHeight" to "23rpx", "color" to "#ffffff")), "type1" to _pS(_uM("color" to "#5A9F32")), "type2" to _pS(_uM("color" to "#E54E4E")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("clear" to null, "del" to null)
        var props = _nP(_uM("list" to _uM("type" to "Array", "default" to fun(): UTSArray<tmitem> {
            return _uA()
        }
        ), "listResult" to _uM("type" to "Array", "default" to fun(): UTSArray<tmResult> {
            return _uA()
        }
        ), "isClear" to _uM("type" to "Boolean", "default" to fun(): Boolean {
            return false
        }
        )))
        var propsNeedCastKeys = _uA(
            "list",
            "listResult",
            "isClear"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
