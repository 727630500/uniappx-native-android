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
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesUserIntegralRecord : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesUserIntegralRecord) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesUserIntegralRecord
            val _cache = __ins.renderCache
            val icoMap: UTSJSONObject = object : UTSJSONObject() {
                var 兑换礼品 = "/static/ico/user/lipin_icon.png"
                var 完成任务奖励 = "/static/ico/user/renwu_icon.png"
                var 签到奖励 = "/static/ico/user/qiandao_icon.png"
                var 积分改动 = "/static/ico/user/jifen_icon.png"
                var 连续签到奖励 = "/static/ico/user/qiandao_icon"
            }
            val recordList = ref<UTSArray<UserPointsRecordGroupByMonthVo>?>(null)
            val queryParams = ref(object : UTSJSONObject() {
                var pageNum: Number = 1
                var pageSize: Number = 5
            })
            fun gen_getDetailIco_fn(subType: String): String {
                var icoPath = icoMap.getString(subType)
                return if (icoPath != null) {
                    icoPath
                } else {
                    "/static/ico/user/jifen_icon.png"
                }
            }
            val getDetailIco = ::gen_getDetailIco_fn
            fun gen_UserPointsRecordList_fn() {
                uni_request<Result<UTSArray<UserPointsRecordGroupByMonthVo>>>(RequestOptions(url = getUrl("/biz/userPointsRecord/api/myListGroupByMonth"), method = "GET", header = getHeader(), data = queryParams.value, success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    recordList.value = responseData.data
                }
                , fail = fun(err){
                    console.log(err)
                    uni_showToast(ShowToastOptions(title = "请求失败", icon = "none"))
                }
                , complete = fun(_){}))
            }
            val UserPointsRecordList = ::gen_UserPointsRecordList_fn
            fun gen_formatDate_fn(dateStr: String): String {
                val date = Date(dateStr.replace(UTSRegExp("-", "g"), "-"))
                val year = date.getFullYear()
                val month = date.getMonth() + 1
                return "" + year + "\u5E74" + month + "\u6708"
            }
            val formatDate = ::gen_formatDate_fn
            onReady(fun(){
                setScreen()
            }
            )
            onLoad(fun(_options){
                UserPointsRecordList()
            }
            )
            return fun(): Any? {
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "积分记录明细")),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("scroll-view", _uM("class" to "d_scroll_view"), _uA(
                                _cE(Fragment, null, RenderHelpers.renderList(unref(recordList), fun(item, __key, __index, _cached): Any {
                                    return _cE("view", _uM("class" to "d_scroll_view_item", "key" to item), _uA(
                                        _cE("text", _uM("class" to "d_scroll_view_item_title"), _tD(formatDate(item.month)), 1),
                                        _cE(Fragment, null, RenderHelpers.renderList(item.voList, fun(subItem, __key, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "d_scroll_view_item_box", "key" to subItem.id), _uA(
                                                _cE("image", _uM("class" to "d_scroll_view_item_box_ico", "src" to getDetailIco(subItem.subType), "mode" to "widthFix"), null, 8, _uA(
                                                    "src"
                                                )),
                                                _cE("view", _uM("class" to "d_scroll_view_item_box_item"), _uA(
                                                    _cE("text", _uM("class" to "d_scroll_view_item_box_item_text1"), _tD(subItem.subType), 1),
                                                    _cE("text", _uM("class" to "d_scroll_view_item_box_item_text2"), _tD(subItem.createTime) + " " + _tD(subItem.subType), 1)
                                                )),
                                                withDirectives(_cE("text", _uM("class" to "d_scroll_view_item_box_item_text", "style" to _nS(_uM("color" to "#5A9F32"))), "+" + _tD(subItem.points), 5), _uA(
                                                    _uA(
                                                        vShow,
                                                        subItem.type == "计入"
                                                    )
                                                )),
                                                withDirectives(_cE("text", _uM("class" to "d_scroll_view_item_box_item_text", "style" to _nS(_uM("color" to "#E54E4E"))), "-" + _tD(subItem.points), 5), _uA(
                                                    _uA(
                                                        vShow,
                                                        subItem.type == "计出"
                                                    )
                                                ))
                                            ))
                                        }
                                        ), 128)
                                    ))
                                }
                                ), 128)
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "backgroundColor" to "#ffffff", "height" to "324rpx", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "22rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx")), "d_scroll_view" to _pS(_uM("paddingTop" to "8rpx", "paddingRight" to "15rpx", "paddingBottom" to "8rpx", "paddingLeft" to "15rpx", "height" to "324rpx", "boxSizing" to "border-box")), "d_scroll_view_item_title" to _pS(_uM("fontWeight" to "700", "fontSize" to "18rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx", "marginBottom" to "2rpx")), "d_scroll_view_item_box" to _pS(_uM("width" to "100%", "height" to "57rpx", "backgroundImage" to "none", "backgroundColor" to "#F1F5FC", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "paddingTop" to "10rpx", "paddingRight" to "15rpx", "paddingBottom" to "10rpx", "paddingLeft" to "15rpx", "boxSizing" to "border-box", "flexDirection" to "row", "alignItems" to "center", "marginBottom" to "7.62rpx")), "d_scroll_view_item_box_ico" to _pS(_uM("width" to "27rpx", "height" to "27rpx", "marginRight" to "16.41rpx")), "d_scroll_view_item_box_item" to _pS(_uM("display" to "flex", "flex" to 1, "flexDirection" to "column", "justifyContent" to "space-between")), "d_scroll_view_item_box_item_text1" to _pS(_uM("fontWeight" to "700", "fontSize" to "14rpx", "color" to "#244E93")), "d_scroll_view_item_box_item_text2" to _pS(_uM("fontSize" to "12rpx", "color" to "#98A6EE")), "d_scroll_view_item_box_item_text" to _pS(_uM("fontSize" to "18rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
