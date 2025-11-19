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
open class GenComponentsBackgroundImageBackgroundImage : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var src: String by `$props`
    open var mode: String by `$props`
    open var className: String by `$props`
    open var imgClassName: String by `$props`
    open var bgStyle: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsBackgroundImageBackgroundImage) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsBackgroundImageBackgroundImage
            val _cache = __ins.renderCache
            return fun(): Any? {
                return _cE("view", _uM("class" to _nC(_uA(
                    "bg-image-box",
                    _ctx.className
                ))), _uA(
                    _cE("image", _uM("class" to _nC(_uA(
                        "bg-image",
                        _ctx.imgClassName
                    )), "src" to _ctx.src, "mode" to _ctx.mode, "style" to _nS(_ctx.bgStyle)), null, 14, _uA(
                        "src",
                        "mode"
                    )),
                    renderSlot(_ctx.`$slots`, "default")
                ), 2)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("bg-image" to _pS(_uM("width" to "100%", "height" to "100%")), "bg-image-box" to _pS(_uM("width" to "100%", "height" to "100%", "position" to "relative")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("src" to _uM("type" to "String", "default" to ""), "mode" to _uM("type" to "String", "default" to ""), "className" to _uM("type" to "String", "default" to ""), "imgClassName" to _uM("type" to "String", "default" to ""), "bgStyle" to _uM("type" to "String", "default" to "")))
        var propsNeedCastKeys = _uA(
            "src",
            "mode",
            "className",
            "imgClassName",
            "bgStyle"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
