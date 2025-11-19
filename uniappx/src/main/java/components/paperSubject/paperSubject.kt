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
open class GenComponentsPaperSubjectPaperSubject : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var isShow: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsPaperSubjectPaperSubject) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsPaperSubjectPaperSubject
            val _cache = __ins.renderCache
            fun emits(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val props = __props
            val closeFn = fun(){
                emits("update:isShow", false)
            }
            val ok = fun(){
                emits("ok", "")
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
                                    _cE("text", _uM("class" to "analysis_title"), "提示"),
                                    _cE("image", _uM("src" to "/static/ico/close.png", "mode" to "", "class" to "close", "onClick" to closeFn))
                                )),
                                _cE("view", _uM("style" to _nS(_uM("align-items" to "center"))), _uA(
                                    _cE("image", _uM("src" to "/static/ico/sub_ico.png", "mode" to "", "style" to _nS(_uM("width" to "99.61rpx", "height" to "87.3rpx"))), null, 4),
                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12.89rpx"))), "确定要交卷吗？", 4)
                                ), 4),
                                _cE("view", _uM("style" to _nS(_uM("width" to "60%", "margin" to "20rpx auto 0", "flex-direction" to "row", "justify-content" to "space-between"))), _uA(
                                    _cE("text", _uM("class" to "sub_btn clear", "onClick" to closeFn), "取消"),
                                    _cE("text", _uM("class" to "sub_btn", "onClick" to ok), "提交")
                                ), 4)
                            ))
                        )
                    }), "_" to 1))
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
                return _uM("analysis_box" to _pS(_uM("width" to "284.77rpx", "height" to "220.31rpx", "left" to "50%", "top" to "50%", "transform" to "translate(-50%, -50%)", "position" to "relative")), "analysis_bg" to _uM(".analysis_box " to _uM("width" to "100%", "height" to "100%", "position" to "absolute")), "analysis_top" to _uM(".analysis_box " to _uM("position" to "relative", "alignItems" to "center")), "analysis_title" to _uM(".analysis_box .analysis_top " to _uM("fontWeight" to "700", "fontSize" to "11.72rpx", "color" to "#3D3D3D", "lineHeight" to "39rpx", "marginTop" to "10rpx")), "close" to _uM(".analysis_box .analysis_top " to _uM("width" to "11.72rpx", "height" to "11.72rpx", "position" to "absolute", "top" to "20rpx", "right" to "20rpx")), "title" to _uM(".analysis_box " to _uM("fontWeight" to "700", "fontSize" to "18rpx", "color" to "#3A58EB")), "desc_title" to _uM(".analysis_box " to _uM("fontWeight" to "700", "fontSize" to "15rpx", "color" to "#3D3D3D", "lineHeight" to "39rpx")), "desc_content" to _uM(".analysis_box " to _uM("backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "boxShadow" to "0rpx 2rpx 6rpx rgba(102, 133, 184, 0.53)", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "18rpx", "paddingTop" to "9rpx", "paddingRight" to "9rpx", "paddingBottom" to "9rpx", "paddingLeft" to "9rpx", "marginTop" to 0, "marginRight" to "6rpx", "marginBottom" to "2rpx", "marginLeft" to "6rpx")), "content" to _uM(".analysis_box " to _uM("fontSize" to "18rpx", "color" to "#3D3D3D", "lineHeight" to "39rpx")), "sub_btn" to _uM("" to _uM("width" to "70.31rpx", "height" to "27.54rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "fontSize" to "11.72rpx", "lineHeight" to "27.54rpx", "textAlign" to "center", "color" to "#ffffff", "borderTopLeftRadius" to "35rpx", "borderTopRightRadius" to "35rpx", "borderBottomRightRadius" to "35rpx", "borderBottomLeftRadius" to "35rpx"), ".clear" to _uM("backgroundImage" to "linear-gradient(to bottom, #F1F5FC, #B1BBF0)", "backgroundColor" to "rgba(0,0,0,0)", "color" to "#3B4992")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("update:isShow" to null, "ok" to null)
        var props = _nP(_uM("isShow" to _uM("type" to "Boolean", "required" to true, "default" to false)))
        var propsNeedCastKeys = _uA(
            "isShow"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
