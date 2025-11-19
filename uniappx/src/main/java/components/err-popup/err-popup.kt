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
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenComponentsErrPopupErrPopup : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var isShow: Boolean by `$props`
    open var nowItem: Any? by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsErrPopupErrPopup) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsErrPopupErrPopup
            val _cache = __ins.renderCache
            fun emits(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val props = __props
            val feedbackContent = ref("")
            val _item = ref<followAlongItem?>(null)
            val subTypeArr = ref(Set<Number>())
            watchEffect(fun(){
                if (props.nowItem != null) {
                    _item.value = props.nowItem as followAlongItem
                }
            }
            )
            val closeFn = fun(){
                emits("update:isShow", false)
                feedbackContent.value = ""
                subTypeArr.value = Set<Number>()
            }
            fun gen_checkType_fn(type: Number) {
                if (subTypeArr.value.has(type)) {
                    subTypeArr.value.`delete`(type)
                } else {
                    subTypeArr.value.add(type)
                }
            }
            val checkType = ::gen_checkType_fn
            fun gen_getStatus_fn(type: Number): Boolean {
                return subTypeArr.value.has(type)
            }
            val getStatus = ::gen_getStatus_fn
            fun gen_subFn_fn() {
                var endArr = _uA<Number>()
                subTypeArr.value.forEach(fun(item){
                    endArr.push(item)
                }
                )
                if (endArr.length == 0) {
                    uni_showToast(ShowToastOptions(title = "请选择纠错类型", icon = "none"))
                    return
                }
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/userFeedback/api/add"), method = "POST", data = object : UTSJSONObject() {
                    var types = endArr.join(",")
                    var feedbackContent = feedbackContent.value
                    var problemId = _item.value?.id
                }, header = getHeader(), success = fun(res){
                    console.log(object : UTSJSONObject() {
                        var types = endArr.join(",")
                        var feedbackContent = feedbackContent.value
                        var problemId = _item.value?.id
                    })
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    uni_showToast(ShowToastOptions(title = "提交成功", icon = "none"))
                    emits("update:isShow", false)
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val subFn = ::gen_subFn_fn
            return fun(): Any? {
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                return if (isTrue(props.isShow)) {
                    _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "analysis_box"), _uA(
                                _cE("image", _uM("src" to "/static/ico/analysis_bg.png", "mode" to "", "class" to "analysis_bg")),
                                _cE("view", _uM("class" to "analysis_top"), _uA(
                                    _cE("text", _uM("class" to "analysis_title"), "纠错"),
                                    _cE("image", _uM("src" to "/static/ico/close.png", "mode" to "", "class" to "close", "onClick" to closeFn))
                                )),
                                _cE("view", _uM("class" to "err_content"), _uA(
                                    _cE("text", _uM("class" to "title", "style" to _nS(_uM("margin-bottom" to "10rpx"))), _uA(
                                        "纠错单词：",
                                        _cE("text", _uM("style" to _nS(_uM("color" to "#3A58EB", "line-height" to "16rpx"))), _tD(unref(_item)?.englishText), 5)
                                    ), 4),
                                    _cE("text", _uM("class" to "title"), "纠错类型（多选）："),
                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "padding-right" to "-10rpx", "margin-top" to "16rpx"))), _uA(
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "err_btn",
                                            _uM("active" to getStatus(1))
                                        )), "onClick" to fun(){
                                            checkType(1)
                                        }), "单词错误", 10, _uA(
                                            "onClick"
                                        )),
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "err_btn",
                                            _uM("active" to getStatus(2))
                                        )), "onClick" to fun(){
                                            checkType(2)
                                        }), "汉义错误", 10, _uA(
                                            "onClick"
                                        )),
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "err_btn",
                                            _uM("active" to getStatus(3))
                                        )), "onClick" to fun(){
                                            checkType(3)
                                        }), "音标错误", 10, _uA(
                                            "onClick"
                                        )),
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "err_btn",
                                            _uM("active" to getStatus(4))
                                        )), "onClick" to fun(){
                                            checkType(4)
                                        }), "发音错误", 10, _uA(
                                            "onClick"
                                        )),
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "err_btn",
                                            _uM("active" to getStatus(5))
                                        )), "onClick" to fun(){
                                            checkType(5)
                                        }), "其他错误", 10, _uA(
                                            "onClick"
                                        ))
                                    ), 4),
                                    _cE("text", _uM("class" to "title", "style" to _nS(_uM("margin-top" to "16rpx"))), "错误描述：", 4),
                                    _cE("textarea", _uM("name" to "", "id" to "", "modelValue" to unref(feedbackContent), "onInput" to fun(`$event`: UniInputEvent){
                                        trySetRefValue(feedbackContent, `$event`.detail.value)
                                    }, "cols" to "30", "rows" to "10", "class" to "_textarea"), null, 40, _uA(
                                        "modelValue"
                                    )),
                                    _cE("text", _uM("class" to "title", "style" to _nS(_uM("margin-top" to "10rpx", "color" to "#FA9600"))), "注：实名反馈，请如实反馈", 4),
                                    _cE("text", _uM("class" to "next_btn", "style" to _nS(_uM("margin" to "10rpx auto 0")), "onClick" to subFn), "提交", 4)
                                ))
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
                return _uM("analysis_box" to _pS(_uM("width" to "478.71rpx", "height" to "328.13rpx", "left" to "50%", "top" to "50%", "transform" to "translate(-50%, -50%)", "position" to "relative")), "analysis_bg" to _uM(".analysis_box " to _uM("width" to "100%", "height" to "100%", "position" to "absolute")), "analysis_top" to _uM(".analysis_box " to _uM("position" to "relative", "alignItems" to "center")), "analysis_title" to _uM(".analysis_box .analysis_top " to _uM("fontWeight" to "700", "fontSize" to "18rpx", "color" to "#3D3D3D", "lineHeight" to "39rpx", "marginTop" to "10rpx")), "close" to _uM(".analysis_box .analysis_top " to _uM("width" to "17.58rpx", "height" to "17.58rpx", "position" to "absolute", "top" to "20rpx", "right" to "20rpx")), "err_content" to _uM(".analysis_box " to _uM("paddingTop" to 0, "paddingRight" to "28rpx", "paddingBottom" to 0, "paddingLeft" to "28rpx", "flex" to 1)), "title" to _uM(".analysis_box .err_content " to _uM("fontWeight" to "700", "fontSize" to "13rpx", "lineHeight" to "16rpx")), "err_btn" to _uM(".analysis_box .err_content " to _uM("width" to "70rpx", "height" to "27rpx", "backgroundImage" to "none", "backgroundColor" to "#F6F6F6", "borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#DADADA", "borderRightColor" to "#DADADA", "borderBottomColor" to "#DADADA", "borderLeftColor" to "#DADADA", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "27rpx", "textAlign" to "center", "marginRight" to "10rpx"), ".analysis_box .err_content .active" to _uM("color" to "#3A58EB", "fontWeight" to "bold", "borderTopColor" to "#3A58EB", "borderRightColor" to "#3A58EB", "borderBottomColor" to "#3A58EB", "borderLeftColor" to "#3A58EB")), "_textarea" to _uM(".analysis_box .err_content " to _uM("paddingTop" to "10rpx", "paddingRight" to "10rpx", "paddingBottom" to "10rpx", "paddingLeft" to "10rpx", "marginTop" to "10rpx", "width" to "382rpx", "height" to "61rpx", "backgroundImage" to "none", "backgroundColor" to "#F6F6F6", "borderTopLeftRadius" to "9rpx", "borderTopRightRadius" to "9rpx", "borderBottomRightRadius" to "9rpx", "borderBottomLeftRadius" to "9rpx", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#DADADA", "borderRightColor" to "#DADADA", "borderBottomColor" to "#DADADA", "borderLeftColor" to "#DADADA")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("update:isShow" to null)
        var props = _nP(_uM("isShow" to _uM("type" to "Boolean", "default" to fun(): Boolean {
            return false
        }
        ), "nowItem" to _uM("default" to fun(): followAlongItem? {
            return null
        }
        )))
        var propsNeedCastKeys = _uA(
            "isShow",
            "nowItem"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
