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
open class GenUniModulesLimeLoadingComponentsLLoadingLLoading : VueComponent, LoadingProps {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    override var color: String? by `$props`
    override var type: String by `$props`
    override var size: String by `$props`
    override var text: String? by `$props`
    override var textColor: String? by `$props`
    override var textSize: Any? by `$props`
    override var mode: String by `$props`
    override var vertical: Boolean by `$props`
    override var animated: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesLimeLoadingComponentsLLoadingLLoading) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesLimeLoadingComponentsLLoadingLLoading
            val _cache = __ins.renderCache
            val themeVars = inject("limeConfigProviderThemeVars", computed(fun(): UTSJSONObject {
                return (UTSJSONObject())
            }
            ))
            val name = "l-loading"
            val props = __props
            val classes = computed<Map<String, Any>>(fun(): Map<String, Any> {
                val cls = Map<String, Any>()
                cls.set(name + "--" + props.type, true)
                if (props.vertical) {
                    cls.set("is-vertical", props.vertical)
                } else {
                    cls.set("is-horizontal", !props.vertical)
                }
                return cls
            }
            )
            val spinnerStyle = computed<Map<String, Any>>(fun(): Map<String, Any> {
                val style = Map<String, Any>()
                style.set("width", props.size)
                style.set("height", props.size)
                return style
            }
            )
            val textStyle = computed<Map<String, Any>>(fun(): Map<String, Any> {
                val style = Map<String, Any>()
                if (props.textColor != null) {
                    style.set("color", props.textColor!!)
                }
                if (props.textSize != null) {
                    style.set("font-size", props.textSize!!)
                }
                return style
            }
            )
            val loadingRef = ref<UniElement?>(null)
            val loading = useLoading(loadingRef)
            loading.type = props.type
            loading.mode = props.mode
            if (props.animated) {
                loading.play()
            } else {
                loading.pause()
            }
            watchEffect(fun(){
                if (loadingRef.value == null) {
                    return
                }
                loading.color = props.color ?: "" + (themeVars.value["loadingColor"] ?: "#3283ff")
                if (props.animated) {
                    loading.play()
                } else {
                    loading.pause()
                }
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to _nC(_uA(
                    "l-loading",
                    unref(classes)
                ))), _uA(
                    _cE("view", _uM("class" to "l-loading__view", "ref_key" to "loadingRef", "ref" to loadingRef, "style" to _nS(unref(spinnerStyle))), null, 4),
                    if (isTrue(_ctx.`$slots`["default"] != null || _ctx.text != null)) {
                        _cE("text", _uM("key" to 0, "class" to "l-loading__text", "style" to _nS(unref(textStyle))), _uA(
                            renderSlot(_ctx.`$slots`, "default", UTSJSONObject(), fun(): UTSArray<Any> {
                                return _uA(
                                    _tD(_ctx.text)
                                )
                            })
                        ), 4)
                    } else {
                        _cC("v-if", true)
                    }
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
                return _uM("l-loading" to _uM("" to _uM("display" to "flex", "position" to "relative", "flexDirection" to "row", "alignItems" to "center"), ".is-vertical" to _uM("flexDirection" to "column")), "l-loading__text" to _uM("" to _uM("marginLeft" to 8, "color" to "var(--l-loading-text-color, #00000073)", "fontSize" to "var(--l-loading-font-size, 14px)"), ".l-loading.is-vertical " to _uM("marginTop" to 4, "marginRight" to 0, "marginBottom" to 0, "marginLeft" to 0)), "l-loading__ball" to _pS(_uM("width" to "var(--l-loading-size, 20px)", "height" to "var(--l-loading-size, 20px)")), "l-loading__circular" to _pS(_uM("width" to "var(--l-loading-size, 20px)", "height" to "var(--l-loading-size, 20px)")), "l-loading__spinner" to _pS(_uM("width" to "var(--l-loading-size, 20px)", "height" to "var(--l-loading-size, 20px)")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("color" to _uM("type" to "String", "required" to false), "type" to _uM("type" to "String", "required" to true, "default" to "circular"), "size" to _uM("type" to "String", "required" to true, "default" to "20px"), "text" to _uM("type" to "String", "required" to false), "textColor" to _uM("type" to "String", "required" to false), "textSize" to _uM("type" to _uA(
            "String",
            "Number"
        ), "required" to false), "mode" to _uM("type" to "String", "required" to true, "default" to "raf"), "vertical" to _uM("type" to "Boolean", "required" to true, "default" to false), "animated" to _uM("type" to "Boolean", "required" to true, "default" to true)))
        var propsNeedCastKeys = _uA(
            "type",
            "size",
            "mode",
            "vertical",
            "animated"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
