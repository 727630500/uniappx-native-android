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
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesMyTaskRecordNew : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesMyTaskRecordNew) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesMyTaskRecordNew
            val _cache = __ins.renderCache
            val studyTaskId = ref(0)
            var wordList = ref<UTSArray<wordListItem>?>(null)
            val taskName = ref("")
            fun gen_gsTime_fn(str: Number): String {
                val seconds = str
                val hours = Math.floor(seconds / 3600)
                val minutes = Math.floor((seconds % 3600) / 60)
                val secs = seconds % 60
                var result = ""
                if (hours > 0) {
                    result += "" + hours + "\u5C0F\u65F6"
                }
                if (minutes > 0) {
                    result += "" + minutes + "\u5206\u949F"
                }
                if (secs > 0 || result === "") {
                    result += "" + secs + "\u79D2"
                }
                return result
            }
            val gsTime = ::gen_gsTime_fn
            fun gen_getList_fn() {
                uni_showLoading(ShowLoadingOptions(title = "加载中...", mask = true))
                var data: UTSJSONObject = object : UTSJSONObject() {
                    var pageSize: Number = 3000
                    var pageNum: Number = 1
                    var studyTaskId = studyTaskId.value
                }
                uni_request<Result<UTSArray<wordListItem>>>(RequestOptions(url = getUrl("/biz/studyTask/api/userExerciseRecord/list"), method = "GET", data = data, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        return uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                    }
                    wordList.value = responseData.data
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){
                    uni_hideLoading()
                }
                ))
            }
            val getList = ::gen_getList_fn
            fun gen_startStudy_fn(item: wordListItem) {
                var _url = getJumpUrlTask(item, taskName.value)
                if (_url == "") {
                    return
                }
                uni_navigateTo(NavigateToOptions(url = _url + ("&title=" + taskName.value)))
            }
            val startStudy = ::gen_startStudy_fn
            onLoad(fun(ev){
                taskName.value = ev["name"] ?: ""
                studyTaskId.value = parseInt(ev["id"] ?: "0")
            }
            )
            onPageShow(fun(){
                getList()
            }
            )
            onReachBottom(fun(){})
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to unref(taskName)), null, 8, _uA(
                            "title"
                        )),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "d_right"), _uA(
                                _cE("view", _uM("class" to "word_table"), _uA(
                                    _cE("view", _uM("class" to "word_table-header"), _uA(
                                        _cE("view", _uM("class" to "word_table-header-item word_table-header-item-unit-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "挑战结果")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item word_table-header-item-time-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "挑战用时")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item word_table-header-item-count-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "挑战得分")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item word_table-header-item-status-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "挑战时间")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item word_table-header-item-status-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "操作")
                                        ))
                                    )),
                                    _cE("scroll-view", _uM("scroll-y" to "true", "class" to "word_table-body"), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(wordList), fun(item, index, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "word_table-body-row", "key" to item, "style" to _nS("" + (if (((index + 1) % 2) == 0) {
                                                "background-color: #F1F5FC"
                                            } else {
                                                ""
                                            }
                                            ))), _uA(
                                                _cE("view", _uM("class" to "word_table-body-item word_table-body-item-unit-box"), _uA(
                                                    _cE("text", _uM("class" to "word_table-body-item-text", "style" to _nS(if (item.isPass == "1") {
                                                        "color: #5A9F32"
                                                    } else {
                                                        "color: #FF4D4F"
                                                    }
                                                    )), _tD(if (item.isPass == "1") {
                                                        "通过"
                                                    } else {
                                                        "未通过"
                                                    }
                                                    ), 5)
                                                )),
                                                _cE("view", _uM("class" to "word_table-body-item word_table-body-item-time-box"), _tD(gsTime(item.exerciseSecond)), 1),
                                                _cE("view", _uM("class" to "word_table-body-item word_table-body-item-count-box"), _uA(
                                                    _cE("text", _uM("style" to _nS(_uM("color" to if (parseInt(item.score) >= 90) {
                                                        "#5A9F32"
                                                    } else {
                                                        if (parseInt(item.score) >= 80) {
                                                            "#2791FF"
                                                        } else {
                                                            if (parseInt(item.score) >= 60) {
                                                                "#FA9600"
                                                            } else {
                                                                "#FF4D4F"
                                                            }
                                                        }
                                                    }
                                                    ))), _tD(parseInt(item.score)), 5)
                                                )),
                                                _cE("view", _uM("class" to "word_table-body-item word_table-body-item-status-box"), _tD(item.exerciseStartTime), 1),
                                                _cE("view", _uM("class" to "word_table-body-item word_table-body-item-status-box"), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "justify-content" to "center"))), _uA(
                                                        _cE("view", _uM("class" to "word_table-body-item-status", "style" to _nS(_uM("background-color" to "#CCE2EE")), "onClick" to fun(){
                                                            startStudy(item)
                                                        }
                                                        ), _uA(
                                                            _cE("text", _uM("class" to "word_table-body-item-status-text", "style" to _nS(_uM("color" to "#fff"))), "查看详情", 4)
                                                        ), 12, _uA(
                                                            "onClick"
                                                        ))
                                                    ), 4)
                                                ))
                                            ), 4)
                                        }
                                        ), 128)
                                    ))
                                ))
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "marginTop" to "13.48rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "d_left" to _uM(".d_content " to _uM("height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginRight" to "5.86rpx", "paddingTop" to "17.58rpx", "paddingRight" to "9.96rpx", "paddingBottom" to "17.58rpx", "paddingLeft" to "9.96rpx")), "d_left_top" to _uM(".d_content .d_left " to _uM("flexDirection" to "row")), "d_left_title" to _uM(".d_content .d_left " to _uM("fontWeight" to "700", "fontSize" to "14rpx", "color" to "#3D3D3D", "marginLeft" to "6.45rpx")), "unit_list" to _uM(".d_content .d_left " to _uM("marginTop" to "15.82rpx", "height" to "281rpx")), "unit_item" to _uM(".d_content .d_left .unit_list " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "unit_item_title" to _uM(".d_content .d_left .unit_list .unit_item " to _uM("fontWeight" to "400", "fontSize" to "11.72rpx", "flex" to 1, "marginLeft" to "6.45rpx")), "unit_item_count" to _uM(".d_content .d_left .unit_list .unit_item " to _uM("fontWeight" to "400", "fontSize" to "11.72rpx", "color" to "#8BA0C4", "lineHeight" to "35rpx")), "d_right" to _uM(".d_content " to _uM("flex" to 1, "width" to "100%", "height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx")), "word_table" to _uM(".d_content .d_right " to _uM("marginTop" to "7.62rpx", "marginRight" to "7.62rpx", "marginBottom" to "7.62rpx", "marginLeft" to "7.62rpx", "width" to "700rpx")), "title_box" to _pS(_uM("width" to "234rpx", "height" to "28rpx", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "marginLeft" to "196.29rpx")), "_text" to _uM(".title_box " to _uM("textAlign" to "center", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#535D8C", "lineHeight" to "28rpx")), "search_box" to _pS(_uM("width" to "210rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "boxShadow" to "0rpx 2rpx 4rpx 0rpx rgba(130, 141, 197, 0.3)", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "search_ico" to _uM(".search_box " to _uM("width" to "10rpx", "height" to "10rpx", "marginLeft" to "12rpx")), "search_input" to _uM(".search_box " to _uM("flex" to 1, "height" to "100%", "fontSize" to "14rpx", "color" to "#3a3a3a", "marginTop" to 0, "marginRight" to "12rpx", "marginBottom" to 0, "marginLeft" to "12rpx")), "d_top" to _pS(_uM("paddingTop" to "13.48rpx", "paddingRight" to "16.41rpx", "paddingBottom" to "13.48rpx", "paddingLeft" to "16.41rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "d_top_text" to _uM(".d_top " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D")), "d_top_text_num" to _uM(".d_top " to _uM("fontSize" to "12rpx", "color" to "#5A9F32")), "d_top_text_tip" to _uM(".d_top " to _uM("fontSize" to "12rpx", "color" to "#98A6EE")), "d_top_btn_group" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "d_top_btn" to _pS(_uM("marginLeft" to "5.27rpx", "width" to "67rpx", "height" to "25rpx", "borderTopLeftRadius" to "21rpx", "borderTopRightRadius" to "21rpx", "borderBottomRightRadius" to "21rpx", "borderBottomLeftRadius" to "21rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "backgroundImage" to "linear-gradient(to bottom, #F3F7FF, #D5DFF0)", "backgroundColor" to "rgba(0,0,0,0)")), "d_top_btn_text" to _uM(".d_top_btn " to _uM("fontWeight" to "400", "fontSize" to "12rpx", "color" to "#98A6EE")), "d_top_btn_text_active" to _uM(".d_top_btn " to _uM("color" to "#3A58EB")), "d_top_btn_ico" to _uM(".d_top_btn " to _uM("width" to "9.4rpx", "height" to "9.4rpx", "marginRight" to "4.1rpx")), "word_table-header" to _uM(".word_table " to _uM("height" to "35.16rpx", "backgroundColor" to "#F1F5FC", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-header-item-text" to _uM(".word_table " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D")), "word_table-body-row" to _uM(".word_table " to _uM("height" to "47rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body" to _uM(".word_table " to _uM("height" to "220rpx")), "word_table-header-item" to _uM(".word_table .word_table-header " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center"), ".word_table .word_table-body-row " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item" to _uM(".word_table .word_table-header " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center"), ".word_table .word_table-body-row " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item-progress-box" to _uM(".word_table " to _uM("flex" to 1.5)), "word_table-header-item-progress-box" to _uM(".word_table " to _uM("flex" to 1.5)), "word_table-body-item-progress" to _uM(".word_table " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item-progress-progress" to _uM(".word_table " to _uM("width" to "140rpx", "height" to "7rpx", "marginRight" to "12.3rpx")), "word_table-body-item-text" to _uM(".word_table " to _uM("fontFamily" to "Arial, Arial", "fontWeight" to "400", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx")), "word_table-body-item-count-box" to _uM(".word_table " to _uM("width" to "80rpx")), "word_table-header-item-count-box" to _uM(".word_table " to _uM("width" to "80rpx")), "word_table-header-item-unit-box" to _uM(".word_table " to _uM("width" to "150rpx")), "word_table-body-item-unit-box" to _uM(".word_table " to _uM("width" to "150rpx")), "word_table-body-item-status-box" to _uM(".word_table " to _uM("flex" to 0.5)), "word_table-header-item-status-box" to _uM(".word_table " to _uM("flex" to 0.5)), "word_table-body-item-time-box" to _uM(".word_table " to _uM("width" to "60rpx")), "word_table-header-item-time-box" to _uM(".word_table " to _uM("width" to "60rpx")), "word_table-body-item-status" to _uM(".word_table " to _uM("width" to "50rpx", "height" to "17.58rpx", "backgroundImage" to "none", "backgroundColor" to "#B0BFD9", "borderTopLeftRadius" to "17.58rpx", "borderTopRightRadius" to "17.58rpx", "borderBottomRightRadius" to "17.58rpx", "borderBottomLeftRadius" to "17.58rpx", "justifyContent" to "center", "alignItems" to "center")), "word_table-body-item-status-text" to _uM(".word_table " to _uM("fontSize" to "10.55rpx", "color" to "#818DCA")), "nav-r" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_text" to _uM(".nav-r " to _uM("fontWeight" to "700", "fontSize" to "13rpx", "color" to "#FA9600", "marginLeft" to "2.93rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
