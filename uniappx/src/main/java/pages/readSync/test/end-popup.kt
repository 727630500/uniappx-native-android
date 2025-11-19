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
open class GenPagesReadSyncTestEndPopup : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var isShow: Boolean by `$props`
    open var endData: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesReadSyncTestEndPopup) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesReadSyncTestEndPopup
            val _cache = __ins.renderCache
            val ctx = createInnerAudioContext()
            ctx.onCanplay(fun(_){
                ctx.play()
            }
            )
            fun emits(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val props = __props
            val typeMap: UTSJSONObject = object : UTSJSONObject() {
                var `14` = "选词填空"
                var `15` = "语法填空"
                var `16` = "完形填空"
                var `17` = "传统阅读"
                var `18` = "六选五"
            }
            val feedbackContent = ref("")
            val _item = ref<ProblemTypeExerciseRecord?>(null)
            val subTypeArr = ref(Set<Number>())
            watchEffect(fun(){
                if (props.endData != "") {
                    _item.value = JSON.parse<ProblemTypeExerciseRecord>(props.endData)
                    if (_item.value!!.choose_1_pass && _item.value!!.choose_2_pass) {
                        ctx.src = getSondUrl("挑战成功提示音")
                    } else {
                        ctx.src = getSondUrl("挑战失败提示音")
                    }
                }
            }
            )
            val closeFn = fun(){
                emits("update:isShow", false)
            }
            val ok = fun(){
                emits("ok", false)
            }
            return fun(): Any? {
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                return _cV(_component_u_popup, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cE("view", _uM("class" to "analysis_box"), _uA(
                            _cE("image", _uM("src" to "/static/ico/analysis_bg.png", "mode" to "", "class" to "analysis_bg")),
                            _cE("view", _uM("class" to "analysis_top"), _uA(
                                _cE("text", _uM("class" to "analysis_title"), "挑战结果"),
                                _cE("image", _uM("src" to "/static/ico/close.png", "mode" to "", "class" to "close", "onClick" to closeFn))
                            )),
                            _cE("view", _uM("class" to "end_content", "style" to _nS(_uM("flex" to "1"))), _uA(
                                _cE("view", _uM("style" to _nS(_uM("align-items" to "center"))), _uA(
                                    if (isTrue(unref(_item)!!.choose_1_pass)) {
                                        _cE("image", _uM("key" to 0, "src" to "/static/ico/success_p_ico.png", "style" to _nS(_uM("height" to "62rpx", "width" to "70rpx")), "mode" to "aspectFit"), null, 4)
                                    } else {
                                        _cE("image", _uM("key" to 1, "src" to "/static/ico/fail_p_ico.png", "style" to _nS(_uM("height" to "62rpx", "width" to "74rpx")), "mode" to "aspectFit"), null, 4)
                                    }
                                    ,
                                    _cE("text", _uM("class" to "fraction", "style" to _nS("color:" + (if (unref(_item)!!.choose_1_pass) {
                                        "#7EB65D"
                                    } else {
                                        "#EA6868"
                                    }
                                    ))), _tD(unref(_item)?.choose_1_rate) + "%", 5),
                                    _cE("text", _uM("class" to "tip", "style" to _nS("color:" + (if (unref(_item)!!.choose_1_pass) {
                                        "#7EB65D"
                                    } else {
                                        "#EA6868"
                                    }
                                    ))), _tD(typeMap[unref(_item)?.choose_1_ProblemType ?: ""]) + "正确率", 5),
                                    _cE("text", _uM("class" to "status", "style" to _nS("color:" + (if (unref(_item)!!.choose_1_pass) {
                                        "#7EB65D"
                                    } else {
                                        "#EA6868"
                                    }
                                    ))), "闯关" + _tD(if (unref(_item)!!.choose_1_pass) {
                                        "成功"
                                    } else {
                                        "失败"
                                    }
                                    ), 5)
                                ), 4),
                                _cE("view", _uM("style" to _nS(_uM("height" to "100%", "justify-content" to "flex-end"))), _uA(
                                    _cE("view", _uM("style" to _nS(_uM("height" to "107rpx", "width" to "2rpx", "background-color" to "#D8D8D8", "margin-bottom" to "27rpx"))), null, 4)
                                ), 4),
                                _cE("view", _uM("style" to _nS(_uM("align-items" to "center"))), _uA(
                                    if (isTrue(unref(_item)!!.choose_2_pass)) {
                                        _cE("image", _uM("key" to 0, "src" to "/static/ico/success_p_ico.png", "style" to _nS(_uM("height" to "62rpx", "width" to "70rpx")), "mode" to "aspectFit"), null, 4)
                                    } else {
                                        _cE("image", _uM("key" to 1, "src" to "/static/ico/fail_p_ico.png", "style" to _nS(_uM("height" to "62rpx", "width" to "74rpx")), "mode" to "aspectFit"), null, 4)
                                    }
                                    ,
                                    _cE("text", _uM("class" to "fraction", "style" to _nS("color:" + (if (unref(_item)!!.choose_2_pass) {
                                        "#7EB65D"
                                    } else {
                                        "#EA6868"
                                    }
                                    ))), _tD(unref(_item)?.choose_2_rate) + "%", 5),
                                    _cE("text", _uM("class" to "tip", "style" to _nS("color:" + (if (unref(_item)!!.choose_2_pass) {
                                        "#7EB65D"
                                    } else {
                                        "#EA6868"
                                    }
                                    ))), _tD(typeMap[unref(_item)?.choose_2_ProblemType ?: ""]) + "正确率", 5),
                                    _cE("text", _uM("class" to "status", "style" to _nS("color:" + (if (unref(_item)!!.choose_2_pass) {
                                        "#7EB65D"
                                    } else {
                                        "#EA6868"
                                    }
                                    ))), "闯关" + _tD(if (unref(_item)!!.choose_2_pass) {
                                        "成功"
                                    } else {
                                        "失败"
                                    }
                                    ), 5)
                                ), 4)
                            ), 4),
                            _cE("text", _uM("class" to "next_btn", "style" to _nS(_uM("margin" to "0 auto 17rpx")), "onClick" to ok), "查看结果", 4)
                        ))
                    )
                }
                ), "_" to 1))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("analysis_box" to _pS(_uM("width" to "375rpx", "height" to "298rpx", "left" to "50%", "top" to "50%", "transform" to "translate(-50%, -50%)", "position" to "relative")), "analysis_bg" to _uM(".analysis_box " to _uM("width" to "100%", "height" to "100%", "position" to "absolute")), "analysis_top" to _uM(".analysis_box " to _uM("position" to "relative", "alignItems" to "center")), "analysis_title" to _uM(".analysis_box .analysis_top " to _uM("fontWeight" to "700", "fontSize" to "18rpx", "color" to "#3D3D3D", "lineHeight" to "39rpx", "marginTop" to "10rpx")), "close" to _uM(".analysis_box .analysis_top " to _uM("width" to "17.58rpx", "height" to "17.58rpx", "position" to "absolute", "top" to "20rpx", "right" to "20rpx")), "end_content" to _uM(".analysis_box " to _uM("flexDirection" to "row", "justifyContent" to "space-between", "marginTop" to 0, "marginRight" to "26rpx", "marginBottom" to 0, "marginLeft" to "26rpx")), "fraction" to _uM(".analysis_box .end_content " to _uM("fontWeight" to "700", "fontSize" to "40rpx", "color" to "#5A9F32", "lineHeight" to "46rpx", "textAlign" to "center")), "tip" to _uM(".analysis_box .end_content " to _uM("fontSize" to "18rpx", "color" to "#7EB65D", "lineHeight" to "23rpx", "textAlign" to "center", "marginTop" to "19rpx")), "status" to _uM(".analysis_box .end_content " to _uM("fontWeight" to "700", "fontSize" to "23rpx", "color" to "#5A9F32", "lineHeight" to "25rpx", "textAlign" to "center", "marginTop" to "10rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("update:isShow" to null, "ok" to null)
        var props = _nP(_uM("isShow" to _uM("type" to "Boolean", "default" to fun(): Boolean {
            return false
        }
        ), "endData" to _uM("type" to "String", "default" to fun(): String {
            return ""
        }
        )))
        var propsNeedCastKeys = _uA(
            "isShow",
            "endData"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
