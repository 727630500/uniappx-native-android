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
open class GenComponentsPageWrapPageWrap : VueComponent, Props {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    override var className: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsPageWrapPageWrap) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsPageWrapPageWrap
            val _cache = __ins.renderCache
            return fun(): Any? {
                return _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("height" to "100%", "width" to "100%"))), _uA(
                    _cE("view", _uM("class" to _nC(_uA(
                        "page-wrap",
                        _ctx.className
                    ))), _uA(
                        renderSlot(_ctx.`$slots`, "default")
                    ), 2)
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
                return _uM("page-wrap" to _pS(_uM("width" to "750rpx", "height" to "414rpx", "boxSizing" to "border-box", "position" to "relative")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("className" to _uM("type" to "String", "required" to true)))
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
