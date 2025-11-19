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
import uts.sdk.modules.limeAudioPlayer.createInnerAudioContext
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
open class GenComponentsStudyCompletedStudyCompleted : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var isTest: Boolean by `$props`
    open var tpl: Number by `$props`
    open var tips: String by `$props`
    open var btnText: String by `$props`
    open var showJb: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsStudyCompletedStudyCompleted) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsStudyCompletedStudyCompleted
            val _cache = __ins.renderCache
            val ctx = createInnerAudioContext()
            ctx.src = getSondUrl("完成声音")
            ctx.onCanplay(fun(_){
                ctx.play()
            }
            )
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val handleSelect = fun(): Unit {
                emit("success")
            }
            val clostFn = fun(){
                uni_navigateBack(null)
            }
            val clostFn2 = fun(){
                emit("close")
            }
            return fun(): Any? {
                return _cE("view", _uM("style" to _nS(_uM("position" to "fixed", "top" to "0", "left" to "0", "width" to "750rpx", "height" to "469rpx", "background-color" to "rgba(0, 0, 0, 0.6)"))), _uA(
                    _cE("image", _uM("src" to "/static/ico/complete.png", "mode" to "", "style" to _nS(_uM("width" to "375rpx", "height" to "310.55rpx", "margin" to "65.3rpx auto 0"))), null, 4),
                    if (_ctx.tpl == 1) {
                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("position" to "absolute", "left" to "0", "top" to "0", "width" to "100%", "height" to "100%", "align-items" to "center", "justify-content" to "center"))), _uA(
                            _cE("image", _uM("src" to "/static/ico/close.png", "class" to "close_ico", "mode" to "", "onClick" to clostFn)),
                            _cE("text", _uM("class" to "fail_text", "style" to _nS(_uM("margin-top" to "80rpx"))), "恭喜你！完成本单元的学习", 4),
                            _cE("text", _uM("class" to "result_btn", "style" to _nS(_uM("margin-top" to "40rpx")), "onClick" to handleSelect), _tD(if (_ctx.isTest) {
                                "立即测试"
                            } else {
                                "完成"
                            }), 5)
                        ), 4)
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (_ctx.tpl == 2) {
                        _cE("view", _uM("key" to 1, "style" to _nS(_uM("position" to "absolute", "left" to "0", "top" to "0", "width" to "100%", "height" to "100%", "align-items" to "center", "justify-content" to "center"))), _uA(
                            _cE("image", _uM("src" to "/static/ico/close.png", "class" to "close_ico", "mode" to "", "onClick" to clostFn2)),
                            _cE("text", _uM("class" to "fail_text", "style" to _nS(_uM("margin-top" to "80rpx"))), _tD(props.tips), 5),
                            _cE("text", _uM("class" to "result_btn", "style" to _nS(_uM("margin-top" to "40rpx")), "onClick" to handleSelect), _tD(_ctx.btnText), 5)
                        ), 4)
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (isTrue(props.showJb)) {
                        _cE("view", _uM("key" to 2, "style" to _nS(_uM("position" to "fixed", "top" to "0", "left" to "0", "width" to "750rpx", "height" to "100%", "align-items" to "center", "justify-content" to "center", "z-index" to "999", "pointer-events" to "none"))), _uA(
                            _cE("image", _uM("src" to "/static/jb.gif", "style" to _nS(_uM("width" to "700rpx")), "mode" to "widthFix"), null, 4)
                        ), 4)
                    } else {
                        _cC("v-if", true)
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
                return _uM("fail_text" to _pS(_uM("fontWeight" to "bold", "fontSize" to "23.44rpx", "color" to "#3D3D3D")), "close_ico" to _pS(_uM("width" to "17.58rpx", "height" to "17.58rpx", "position" to "absolute", "top" to "117.19rpx", "right" to "206.25rpx")), "result_btn" to _pS(_uM("minWidth" to "101rpx", "height" to "35rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "fontWeight" to "bold", "fontSize" to "14rpx", "lineHeight" to "35rpx", "color" to "#ffffff", "textAlign" to "center", "paddingTop" to 0, "paddingRight" to "20rpx", "paddingBottom" to 0, "paddingLeft" to "20rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("success" to null, "close" to null)
        var props = _nP(_uM("isTest" to _uM("type" to "Boolean", "required" to true, "default" to true), "tpl" to _uM("type" to "Number", "required" to true, "default" to 1), "tips" to _uM("type" to "String", "required" to true, "default" to "恭喜完成"), "btnText" to _uM("type" to "String", "required" to true, "default" to "确认"), "showJb" to _uM("type" to "Boolean", "required" to true, "default" to false)))
        var propsNeedCastKeys = _uA(
            "isTest",
            "tpl",
            "tips",
            "btnText",
            "showJb"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
