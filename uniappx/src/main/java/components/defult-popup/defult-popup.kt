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
open class GenComponentsDefultPopupDefultPopup : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var isShow: Boolean by `$props`
    open var title: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsDefultPopupDefultPopup) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsDefultPopupDefultPopup
            val _cache = __ins.renderCache
            fun emits(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val props = __props
            val closeFn = fun(){
                emits("update:isShow", false)
            }
            fun gen_evlItem_fn(src: String): UTSArray<String> {
                return src.split(",")
            }
            val evlItem = ::gen_evlItem_fn
            return fun(): Any? {
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                return if (isTrue(props.isShow)) {
                    _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "analysis_box"), _uA(
                                _cE("image", _uM("src" to "/static/ico/analysis_bg.png", "mode" to "", "class" to "analysis_bg")),
                                _cE("view", _uM("class" to "analysis_top"), _uA(
                                    _cE("text", _uM("class" to "analysis_title"), _tD(props.title), 1),
                                    _cE("image", _uM("src" to "/static/ico/close.png", "mode" to "", "class" to "close", "onClick" to closeFn))
                                )),
                                _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                    _cE("view", null, _uA(
                                        renderSlot(_ctx.`$slots`, "default")
                                    ))
                                ), 4)
                            ))
                        )
                    }), "_" to 3))
                } else {
                    _cC("v-if", true)
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
                return _uM("analysis_box" to _pS(_uM("width" to "478.71rpx", "height" to "328.13rpx", "left" to "50%", "top" to "50%", "transform" to "translate(-50%, -50%)", "position" to "relative")), "analysis_bg" to _uM(".analysis_box " to _uM("width" to "100%", "height" to "100%", "position" to "absolute")), "analysis_top" to _uM(".analysis_box " to _uM("position" to "relative", "alignItems" to "center")), "analysis_title" to _uM(".analysis_box .analysis_top " to _uM("fontWeight" to "700", "fontSize" to "18rpx", "color" to "#3D3D3D", "lineHeight" to "39rpx", "marginTop" to "10rpx")), "close" to _uM(".analysis_box .analysis_top " to _uM("width" to "17.58rpx", "height" to "17.58rpx", "position" to "absolute", "top" to "20rpx", "right" to "20rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("update:isShow" to null)
        var props = _nP(_uM("isShow" to _uM("type" to "Boolean", "required" to true, "default" to false), "title" to _uM("type" to "String", "required" to true, "default" to "")))
        var propsNeedCastKeys = _uA(
            "isShow",
            "title"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
