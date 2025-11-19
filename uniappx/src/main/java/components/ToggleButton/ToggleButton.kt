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
open class GenComponentsToggleButtonToggleButton : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var defaultState: String by `$props`
    open var expandText: String by `$props`
    open var collapseText: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsToggleButtonToggleButton) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsToggleButtonToggleButton
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val activeButton = ref(props.defaultState)
            val handleExpand = fun(){
                activeButton.value = "expand"
                emit("toggle", true)
            }
            val handleCollapse = fun(){
                activeButton.value = "collapse"
                emit("toggle", false)
            }
            onMounted(fun(){
                emit("toggle", props.defaultState === "expand")
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "toggle-container"), _uA(
                    _cE("view", _uM("class" to _nC(_uA(
                        "toggle-button",
                        _uM("toggle-button--active" to (unref(activeButton) === "expand"))
                    )), "onClick" to handleExpand), _uA(
                        _cE("text", _uM("class" to _nC(_uA(
                            "toggle-text",
                            _uM("active" to (unref(activeButton) === "expand"))
                        ))), _tD(props.expandText), 3)
                    ), 2),
                    _cE("view", _uM("class" to _nC(_uA(
                        "toggle-button",
                        _uM("toggle-button--active" to (unref(activeButton) === "collapse"))
                    )), "onClick" to handleCollapse), _uA(
                        _cE("text", _uM("class" to _nC(_uA(
                            "toggle-text",
                            _uM("active" to (unref(activeButton) === "collapse"))
                        ))), _tD(props.collapseText), 3)
                    ), 2)
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
                return _uM("toggle-container" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#98A6EE", "borderRightColor" to "#98A6EE", "borderBottomColor" to "#98A6EE", "borderLeftColor" to "#98A6EE", "borderTopLeftRadius" to 888, "borderTopRightRadius" to 888, "borderBottomRightRadius" to 888, "borderBottomLeftRadius" to 888, "paddingTop" to "2rpx", "paddingRight" to "2rpx", "paddingBottom" to "2rpx", "paddingLeft" to "2rpx", "minWidth" to "100rpx", "backgroundColor" to "#FFFFFF")), "toggle-button" to _uM("" to _uM("display" to "flex", "alignItems" to "center", "justifyContent" to "center", "height" to "15rpx", "borderTopLeftRadius" to 888, "borderTopRightRadius" to 888, "borderBottomRightRadius" to 888, "borderBottomLeftRadius" to 888, "backgroundColor" to "#FFFFFF", "transitionProperty" to "all", "transitionDuration" to "0.3s", "transitionTimingFunction" to "ease", "paddingTop" to 0, "paddingRight" to "10rpx", "paddingBottom" to 0, "paddingLeft" to "10rpx"), ".toggle-button--active" to _uM("backgroundColor" to "#607BFF")), "toggle-text" to _uM(".toggle-button " to _uM("fontSize" to 12, "color" to "#333333", "fontWeight" to "700"), ".toggle-button .active" to _uM("color" to "#ffffff")), "@TRANSITION" to _uM("toggle-button" to _uM("property" to "all", "duration" to "0.3s", "timingFunction" to "ease")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("toggle" to null)
        var props = _nP(_uM("defaultState" to _uM("type" to "String", "required" to false, "default" to "collapse"), "expandText" to _uM("type" to "String", "required" to true, "default" to "展开原文"), "collapseText" to _uM("type" to "String", "required" to true, "default" to "收起原文")))
        var propsNeedCastKeys = _uA(
            "defaultState",
            "expandText",
            "collapseText"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
