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
open class GenComponentsDictionDiction : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var list: UTSArray<tempTiMu> by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsDictionDiction) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsDictionDiction
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val participleList = computed(fun(): UTSArray<tempTiMu> {
                return props.list
            }
            )
            fun gen_addCheck_fn(item: tempTiMu, _index: Number) {
                if (item.isCheck) {
                    return
                }
                emit("clickItem", tempTiMuParameter(item = item, index = _index))
            }
            val addCheck = ::gen_addCheck_fn
            return fun(): Any? {
                return _cE("view", _uM("class" to "choice_box"), _uA(
                    _cE("text", _uM("class" to "title"), "选词区域"),
                    _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("max-height" to "90rpx", "min-height" to "60rpx"))), _uA(
                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "flex-wrap" to "wrap"))), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(unref(participleList), fun(item, index, __index, _cached): Any {
                                return _cE(Fragment, null, _uA(
                                    if (item.type == 0) {
                                        _cE("view", _uM("key" to 0, "onClick" to fun(){
                                            addCheck(item, index)
                                        }, "class" to _nC(_uA(
                                            "choice_item_box",
                                            _uM("check" to item.isCheck, "not_check" to !item.isCheck)
                                        ))), _uA(
                                            _cE("text", _uM("class" to _nC(_uA(
                                                "choice_item",
                                                _uM("check" to item.isCheck, "not_check" to !item.isCheck)
                                            ))), _tD(item.title), 3)
                                        ), 10, _uA(
                                            "onClick"
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                ), 64)
                            }
                            ), 256)
                        ), 4)
                    ), 4)
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
                return _uM("choice_box" to _pS(_uM("backgroundImage" to "none", "backgroundColor" to "#F2F5FA", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "paddingTop" to 0, "paddingRight" to "13rpx", "paddingBottom" to 0, "paddingLeft" to "13rpx")), "title" to _uM(".choice_box " to _uM("fontSize" to "13rpx", "color" to "#607BA7")), "choice_item_box" to _uM(".choice_box " to _uM("paddingTop" to 0, "paddingRight" to "10rpx", "paddingBottom" to 0, "paddingLeft" to "10rpx", "height" to "26rpx", "marginBottom" to "5rpx", "marginRight" to "10rpx", "alignItems" to "center", "justifyContent" to "center"), ".choice_box .check" to _uM("backgroundImage" to "none", "backgroundColor" to "#EBEBEB"), ".choice_box .not_check" to _uM("backgroundImage" to "none", "backgroundColor" to "#5894F7")), "choice_item" to _uM(".choice_box " to _uM("textAlign" to "center", "fontSize" to "13rpx", "lineHeight" to "26rpx"), ".choice_box .check" to _uM("color" to "#3D3D3D"), ".choice_box .not_check" to _uM("color" to "#ffffff")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("clickItem" to null)
        var props = _nP(_uM("list" to _uM("type" to "Array", "default" to fun(): UTSArray<tempTiMu> {
            return _uA()
        }
        )))
        var propsNeedCastKeys = _uA(
            "list"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
