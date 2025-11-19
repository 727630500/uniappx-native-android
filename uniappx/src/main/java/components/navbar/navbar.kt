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
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
open class GenComponentsNavbarNavbar : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var title: String by `$props`
    open var showBack: Boolean by `$props`
    open var isBackTwo: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsNavbarNavbar) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsNavbarNavbar
            val _cache = __ins.renderCache
            val props = __props
            val handleBack = fun(){
                if (props.isBackTwo) {
                }
                uni_navigateBack(null)
            }
            return fun(): Any? {
                return _cE("view", _uM("class" to "navbar flex"), _uA(
                    _cE("view", _uM("class" to "navbar-left"), _uA(
                        renderSlot(_ctx.`$slots`, "left", UTSJSONObject(), fun(): UTSArray<Any> {
                            return _uA(
                                _cE("image", _uM("src" to "/static/ico/back_ico.png", "mode" to "", "onClick" to handleBack, "class" to "back-icon")),
                                if (_ctx.title !== "") {
                                    _cE("text", _uM("key" to 0, "class" to "l-title-text"), _tD(_ctx.title), 1)
                                } else {
                                    _cC("v-if", true)
                                }
                            )
                        }
                        )
                    )),
                    _cE("view", _uM("class" to "navbar-title"), _uA(
                        renderSlot(_ctx.`$slots`, "default")
                    )),
                    _cE("view", _uM("class" to "navbar-right"), _uA(
                        renderSlot(_ctx.`$slots`, "right")
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
                return _uM("navbar" to _pS(_uM("width" to "750rpx", "height" to "28rpx", "paddingTop" to 0, "paddingRight" to "17.58rpx", "paddingBottom" to 0, "paddingLeft" to "17.58rpx", "marginTop" to "16rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "navbar-left" to _pS(_uM("alignItems" to "center", "flexDirection" to "row")), "navbar-title" to _pS(_uM("flex" to 1, "flexDirection" to "row", "alignItems" to "center", "position" to "relative")), "navbar-right" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "back-icon" to _pS(_uM("width" to "29.3rpx", "height" to "27.97rpx", "marginRight" to "12.89rpx")), "l-title-text" to _pS(_uM("fontSize" to "15rpx", "color" to "#FFF9F9", "marginRight" to "46.29rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("title" to _uM("type" to "String", "default" to ""), "showBack" to _uM("type" to "Boolean", "default" to false), "isBackTwo" to _uM("type" to "Boolean", "default" to false)))
        var propsNeedCastKeys = _uA(
            "title",
            "showBack",
            "isBackTwo"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
