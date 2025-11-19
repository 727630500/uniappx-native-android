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
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesUserIntegralExchange : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesUserIntegralExchange) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesUserIntegralExchange
            val _cache = __ins.renderCache
            val userPoints = ref<Number>(0)
            val initLoading = ref<Boolean>(true)
            val btnLoading = ref<Boolean>(false)
            val pointsGoodsList = ref<UTSArray<PointsGoodsVo>?>(null)
            val defaultGoodsPic = ref<String>("/static/user/integral/defaultGoodsPic.png")
            val selectId = ref<Number>(-1)
            fun gen_getPointsGoodsList_fn() {
                uni_request<Result<UTSArray<PointsGoodsVo>?>>(RequestOptions(url = getUrl("/biz/pointsGoods/api/list"), method = "GET", header = getHeader(), data = object : UTSJSONObject() {
                    var pageNum: Number = 1
                    var pageSize: Number = 3000
                }, success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    pointsGoodsList.value = responseData.data ?: null
                }
                , fail = fun(err){
                    console.log(err)
                    uni_showToast(ShowToastOptions(title = "请求失败", icon = "none"))
                }
                , complete = fun(_){
                    initLoading.value = false
                }
                ))
            }
            val getPointsGoodsList = ::gen_getPointsGoodsList_fn
            fun gen_submit_fn() {
                if (btnLoading.value == true) {
                    return
                }
                if (selectId.value < 0) {
                    uni_showToast(ShowToastOptions(title = "请选择礼品", duration = 1000, icon = "error"))
                    return
                }
                uni_showModal(ShowModalOptions(title = "兑换提示", content = "确定兑换选择的礼品吗", showCancel = true, cancelText = "取消", confirmText = "确定", confirmColor = "#5689DC", success = fun(res){
                    if (res.confirm != true) {
                        return
                    }
                    btnLoading.value = true
                    var pointsGoods = pointsGoodsList.value?.find(fun(item): Boolean {
                        return item.id == selectId.value
                    }
                    )
                    uni_showLoading(ShowLoadingOptions(title = "提交中...", mask = true))
                    uni_request<Result<Boolean>>(RequestOptions(url = getUrl("/biz/pointsGoods/api/exchange"), method = "POST", header = getHeader(), data = object : UTSJSONObject() {
                        var id = selectId.value
                    }, success = fun(res){
                        val responseData = res.data
                        if (responseData == null) {
                            return
                        }
                        if (responseData.code as Number != 200) {
                            uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                            return
                        }
                        var title = "兑换成功"
                        if (pointsGoods != null) {
                            if ((pointsGoods as PointsGoodsVo).goodsType == "0") {
                                title = title + ",学习时长已增加"
                            } else {
                                ""
                            }
                            if ((pointsGoods as PointsGoodsVo).goodsType == "1") {
                                title = title + ",请等待发货"
                            } else {
                                ""
                            }
                        }
                        uni_showToast(ShowToastOptions(title = title, icon = "success"))
                        if (pointsGoods != null) {
                            userPoints.value = userPoints.value - (pointsGoods as PointsGoodsVo).exchangePoints as Number
                        }
                        return
                    }
                    , fail = fun(err){
                        console.log(err)
                        uni_showToast(ShowToastOptions(title = "请求失败", icon = "none"))
                    }
                    , complete = fun(_){
                        btnLoading.value = false
                        uni_hideLoading()
                    }
                    ))
                }
                , fail = fun(res){
                    return
                }
                ))
            }
            val submit = ::gen_submit_fn
            val selectPointsGood = fun(id: Number){
                selectId.value = id
            }
            val goPath = fun(url: String){
                uni_navigateTo(NavigateToOptions(url = url))
            }
            onReady(fun(){
                setScreen()
            }
            )
            onLoad(fun(_options){
                if (userState.value != null) {
                    var data = userState.value as UserModel
                    userPoints.value = data.points
                }
                getPointsGoodsList()
            }
            )
            return fun(): Any? {
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "积分兑换中心"), _uM("right" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cE("view", _uM("class" to "show_detail_btn", "onClick" to fun(){
                                    goPath("/pages/user/integral/record")
                                }
                                ), _uA(
                                    _cE("image", _uM("class" to "show_detail_btn_icon", "src" to "/static/ico/armmar_stat.png")),
                                    _cE("text", _uM("class" to "show_detail_btn_text"), "积分明细")
                                ), 8, _uA(
                                    "onClick"
                                ))
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "d_content_box"), _uA(
                                _cE("view", _uM("class" to "d_content_box_item"), _uA(
                                    _cE("image", _uM("class" to "d_content_box_item_ico", "src" to "/static/ico/user/jinbi_icon.png", "mode" to "widthFix")),
                                    _cE("view", _uM("class" to "d_content_box_item_text_box"), _uA(
                                        _cE("text", _uM("class" to "d_content_box_item_text"), _tD(unref(userPoints)) + "积分", 1)
                                    ))
                                )),
                                _cE("text", _uM("class" to "d_content_box_text"), "注：选择合适的进行兑换，一旦兑换，不允许退换")
                            )),
                            _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("height" to "254rpx"))), _uA(
                                if (isTrue(unref(pointsGoodsList) != null && (unref(pointsGoodsList) as UTSArray<PointsGoodsVo>).length > 0)) {
                                    _cE("view", _uM("key" to 0, "class" to "d_scroll_view"), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(pointsGoodsList), fun(item, index, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "d_scroll_view_item", "key" to item, "onClick" to fun(){
                                                selectPointsGood(item.id)
                                            }, "style" to _nS(_uM("marginRight" to if ((index + 1) % 4 === 0) {
                                                "0rpx"
                                            } else {
                                                "16.41rpx"
                                            }))), _uA(
                                                if (unref(selectId) == item.id) {
                                                    _cE("image", _uM("key" to 0, "class" to "d_scroll_view_item_select_ico", "src" to "/static/ico/user/exchange_select_icon.png"))
                                                } else {
                                                    _cC("v-if", true)
                                                },
                                                _cE("image", _uM("class" to "d_scroll_view_item_img", "src" to if ((item.goodsPicUrl != null && (item.goodsPicUrl as String) != "")) {
                                                    item.goodsPicUrl
                                                } else {
                                                    unref(defaultGoodsPic)
                                                }), null, 8, _uA(
                                                    "src"
                                                )),
                                                _cE("view", _uM("class" to "d_scroll_view_item_text_box"), _uA(
                                                    _cE("text", _uM("class" to "d_scroll_view_item_text_box_text1"), _tD(item.goodsName), 1),
                                                    _cE("text", _uM("class" to "d_scroll_view_item_text_box_text2"), _tD(item.exchangePoints) + "积分", 1)
                                                )),
                                                _cE("text", _uM("class" to "d_scroll_view_item_text3"), _tD(item.subGoodsName), 1)
                                            ), 12, _uA(
                                                "onClick"
                                            ))
                                        }), 128)
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                            ), 4),
                            _cE("view", _uM("class" to "exchange_btn", "onClick" to submit), _uA(
                                _cE("text", _uM("class" to "exchange_btn_text"), "兑换")
                            ))
                        ))
                    )
                }
                ), "_" to 1))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ), _uA(
                GenApp.styles
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "324rpx", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "22rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "position" to "relative", "backgroundColor" to "#ffffff")), "exchange_btn" to _pS(_uM("position" to "absolute", "bottom" to "10rpx", "right" to "50%", "transform" to "translateX(50%)", "width" to "101rpx", "height" to "35rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "borderTopLeftRadius" to "100rpx", "borderTopRightRadius" to "100rpx", "borderBottomRightRadius" to "100rpx", "borderBottomLeftRadius" to "100rpx", "justifyContent" to "center", "alignItems" to "center")), "exchange_btn_text" to _pS(_uM("fontFamily" to "Arial, Arial", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#F6F6F6")), "d_scroll_view" to _pS(_uM("paddingTop" to "8rpx", "paddingRight" to "15rpx", "paddingBottom" to "8rpx", "paddingLeft" to "15rpx", "boxSizing" to "border-box", "flexDirection" to "row", "flexWrap" to "wrap")), "d_scroll_view_item" to _pS(_uM("width" to "158rpx", "height" to "144rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "boxShadow" to "0rpx 2rpx 4rpx 2rpx rgba(130, 141, 197, 0.3)", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "paddingTop" to "12.3rpx", "paddingRight" to "14.65rpx", "paddingBottom" to "12.3rpx", "paddingLeft" to "14.65rpx", "boxSizing" to "border-box", "marginRight" to "16.41rpx", "marginBottom" to "10.5rpx")), "d_scroll_view_item_img" to _pS(_uM("width" to "129rpx", "height" to "70rpx", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginBottom" to "5.55rpx")), "d_scroll_view_item_select_ico" to _pS(_uM("position" to "absolute", "top" to 0, "right" to 0, "width" to "31rpx", "height" to "31rpx", "zIndex" to 99)), "d_scroll_view_item_text_box" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between")), "d_scroll_view_item_text_box_text1" to _pS(_uM("fontWeight" to "700", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "18rpx", "flex" to 1, "maxHeight" to "36rpx", "overflow" to "hidden", "textOverflow" to "ellipsis")), "d_scroll_view_item_text_box_text2" to _pS(_uM("fontWeight" to "700", "fontSize" to "12rpx", "color" to "#FA9600", "lineHeight" to "18rpx")), "d_scroll_view_item_text3" to _pS(_uM("fontSize" to "12rpx", "color" to "#7B7B7B", "lineHeight" to "18rpx")), "d_content_box" to _pS(_uM("paddingTop" to "15rpx", "paddingRight" to "15rpx", "paddingBottom" to "8rpx", "paddingLeft" to "15rpx", "boxSizing" to "border-box", "flexDirection" to "row", "alignItems" to "center")), "d_content_box_item" to _pS(_uM("height" to "47rpx", "width" to "164rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "position" to "relative")), "d_content_box_item_ico" to _pS(_uM("width" to "47rpx", "height" to "47rpx", "position" to "absolute", "top" to 0, "left" to 0, "zIndex" to 2)), "d_content_box_item_text_box" to _pS(_uM("width" to "100%", "height" to "35rpx", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "19rpx", "borderTopRightRadius" to "19rpx", "borderBottomRightRadius" to "19rpx", "borderBottomLeftRadius" to "19rpx", "justifyContent" to "center", "alignItems" to "center", "alignSelf" to "flex-end", "paddingLeft" to "47rpx", "boxSizing" to "border-box")), "d_content_box_item_text_box_text" to _pS(_uM("fontWeight" to "700", "fontSize" to "15rpx", "color" to "#3D3D3D")), "d_content_box_text" to _pS(_uM("paddingTop" to "18rpx", "fontSize" to "14rpx", "color" to "#5689DC", "marginLeft" to "16rpx")), "show_detail_btn" to _pS(_uM("width" to "90rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx", "alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#D5DFF0", "borderRightColor" to "#D5DFF0", "borderBottomColor" to "#D5DFF0", "borderLeftColor" to "#D5DFF0", "flexDirection" to "row", "marginRight" to "14.65rpx")), "show_detail_btn_icon" to _uM(".show_detail_btn " to _uM("width" to "20rpx", "height" to "20rpx", "marginLeft" to "4rpx")), "show_detail_btn_text" to _uM(".show_detail_btn " to _uM("marginLeft" to "4rpx", "flex" to 1, "fontSize" to "12rpx", "color" to "#3D3D3D")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
