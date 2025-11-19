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
open class GenComponentsGrammarStarsGrammarStars : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var isFall: Boolean by `$props`
    open var half: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsGrammarStarsGrammarStars) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsGrammarStarsGrammarStars
            val _cache = __ins.renderCache
            return fun(): Any? {
                return _cE("view", _uM("style" to _nS(_uM("width" to "41.02rpx", "height" to "47.46rpx", "align-items" to "center"))), _uA(
                    if (isTrue(_ctx.isFall && !_ctx.half)) {
                        _cE(Fragment, _uM("key" to 0), _uA(
                            _cE("view", _uM("class" to "stars_box"), _uA(
                                _cE("image", _uM("src" to "/static/ico/armmar_t_fail.png", "class" to "stars_s", "mode" to "")),
                                _cE("text", _uM("class" to "_text"), _uA(
                                    renderSlot(_ctx.`$slots`, "default")
                                ))
                            )),
                            _cE("image", _uM("src" to "/static/ico/armmar_stat_fail.png", "mode" to "", "class" to "armmar_stat_ico"))
                        ), 64)
                    } else {
                        _cE(Fragment, _uM("key" to 1), _uA(
                            _cE("view", _uM("class" to "stars_box"), _uA(
                                _cE("image", _uM("src" to "/static/ico/armmar_t.png", "class" to "stars_s", "mode" to "")),
                                _cE("text", _uM("class" to "_text"), _uA(
                                    renderSlot(_ctx.`$slots`, "default")
                                ))
                            )),
                            if (isTrue(_ctx.half)) {
                                _cE("image", _uM("key" to 0, "src" to "/static/ico/armmar_t_stat50.png", "mode" to "", "class" to "armmar_stat_ico"))
                            } else {
                                _cE("image", _uM("key" to 1, "src" to "/static/ico/armmar_stat.png", "mode" to "", "class" to "armmar_stat_ico"))
                            }
                        ), 64)
                    }
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
                return _uM("stars_box" to _pS(_uM("width" to "100%", "height" to "41.02rpx", "position" to "relative")), "_text" to _uM(".stars_box " to _uM("color" to "#ffffff", "textAlign" to "center", "marginTop" to "8.7rpx", "lineHeight" to "18rpx", "fontSize" to "12rpx")), "stars_s" to _uM(".stars_box " to _uM("width" to "41.02rpx", "height" to "41.02rpx", "position" to "absolute", "left" to 0, "top" to 0)), "armmar_stat_ico" to _pS(_uM("width" to "17.58rpx", "height" to "15.82rpx", "marginTop" to "-9.38rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("isFall" to _uM("type" to "Boolean", "required" to true, "default" to false), "half" to _uM("type" to "Boolean", "required" to true, "default" to false)))
        var propsNeedCastKeys = _uA(
            "isFall",
            "half"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
