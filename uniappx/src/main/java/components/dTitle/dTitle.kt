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
open class GenComponentsDTitleDTitle : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
        return _cE("view", _uM("style" to _nS(_uM("width" to "100%", "position" to "relative"))), _uA(
            _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                _cV(_component_BackgroundImage, _uM("src" to "/static/ico/follow.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "title_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cE("view", _uM("style" to _nS(_uM("min-width" to "200rpx"))), _uA(
                            _cE("text", _uM("class" to "_text"), _uA(
                                renderSlot(_ctx.`$slots`, "default")
                            ))
                        ), 4)
                    )
                }
                ), "_" to 3))
            ), 4)
        ), 4)
    }
    companion object {
        var name = "dTitle"
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("title_box" to _pS(_uM("!width" to "auto", "height" to "28rpx", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "paddingTop" to 0, "paddingRight" to "20rpx", "paddingBottom" to 0, "paddingLeft" to "20rpx", "left" to "50%", "transform" to "translateX(-50%)", "marginLeft" to "-21rpx")), "_text" to _uM(".title_box " to _uM("textAlign" to "center", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#535D8C", "lineHeight" to "28rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
