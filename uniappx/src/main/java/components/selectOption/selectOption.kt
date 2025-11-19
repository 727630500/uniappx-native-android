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
open class GenComponentsSelectOptionSelectOption : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var list: Any? by `$props`
    open var defName: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsSelectOptionSelectOption) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsSelectOptionSelectOption
            val _cache = __ins.renderCache
            val props = __props
            fun emits(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val _list = ref(_uA<IdAndNameVo>())
            watchEffect(fun(){
                _list.value = _uA(
                    IdAndNameVo(name = props.defName as String, id = null)
                ).concat((props.list as UTSArray<IdAndNameVo>))
            }
            )
            return fun(): Any? {
                return _cE("scroll-view", _uM("direction" to "vertical", "class" to "list_box"), _uA(
                    _cE(Fragment, null, RenderHelpers.renderList(unref(_list), fun(item, __key, __index, _cached): Any {
                        return _cE("text", _uM("class" to "list_item", "onClick" to fun(){
                            emits("ok", item)
                        }
                        ), _tD(item?.name), 9, _uA(
                            "onClick"
                        ))
                    }
                    ), 256)
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
                return _uM("list_box" to _pS(_uM("width" to "121rpx", "maxHeight" to "116rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "3rpx", "borderTopRightRadius" to "3rpx", "borderBottomRightRadius" to "3rpx", "borderBottomLeftRadius" to "3rpx")), "list_item" to _uM(".list_box " to _uM("width" to "121rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "color" to "#3D3D3D", "lineHeight" to "28rpx", "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#eeeeee", "paddingTop" to 0, "paddingRight" to "8rpx", "paddingBottom" to 0, "paddingLeft" to "8rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("ok" to null)
        var props = _nP(_uM("list" to _uM("default" to fun(): UTSArray<IdAndNameVo> {
            return _uA()
        }
        ), "defName" to _uM("type" to "String", "default" to fun(): String {
            return "全部"
        }
        )))
        var propsNeedCastKeys = _uA(
            "list",
            "defName"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
