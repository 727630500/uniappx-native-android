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
open class GenPagesWordInterWords : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesWordInterWords) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesWordInterWords
            val _cache = __ins.renderCache
            val wordList = ref<UTSArray<followAlongItem>?>(null)
            val wordInfo = ref<wordInfoType?>(null)
            fun gen_getList_fn() {
                uni_showLoading(ShowLoadingOptions(title = "加载中..."))
                uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/list"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var subjectType = "英语"
                    var textbookUnitId = "0"
                    var module = getModelKey("同步单词")
                    var subModule = getSubModelKey("生词本")
                    var isExercise = "0"
                    var pageSize: Number = 3000
                }, header = getHeader(), success = fun(res){
                    uni_hideLoading()
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    wordList.value = responseData.data
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
                uni_request<Result<wordInfoType>>(RequestOptions(url = getUrl("/biz/problem/api/getNewWordStat"), method = "GET", header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    wordInfo.value = responseData.data
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getList = ::gen_getList_fn
            fun gen_startStudy_fn() {
                if (wordList.value?.length == 0) {
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/wordInter/words/words"))
            }
            val startStudy = ::gen_startStudy_fn
            onPageShow(fun(){
                getList()
                initConfig()
            }
            )
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_LearningInfo = resolveEasyComponent("LearningInfo", GenComponentsLearningInfoLearningInfoClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "生词本"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_LearningInfo)
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "d_right"), _uA(
                                _cE("view", _uM("class" to "d_top"), _uA(
                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "justify-content" to "space-between", "width" to "100%"))), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("width" to "4rpx", "height" to "4rpx", "border-radius" to "4rpx", "background-color" to "#6694DF", "margin-right" to "5.7rpx"))), null, 4),
                                            _cE("text", _uM("class" to "d_top_text_tip"), "共" + _tD(unref(wordInfo)?.newWordBookShowNum) + "个生词待练习，累计已掌握" + _tD(unref(wordInfo)?.newWordBookHideNum) + "个", 1),
                                            _cE("text", _uM("class" to "d_top_text"), "（累计答对3次才掌握，移出生词本）")
                                        ), 4),
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                            _cE("text", _uM("class" to "btn", "onClick" to startStudy), "开始学习")
                                        ), 4)
                                    ), 4)
                                )),
                                _cE("view", _uM("class" to "d-list", "style" to _nS(_uM("flex" to "1"))), _uA(
                                    _cE("text", _uM("class" to "d-list-title"), "生词列表"),
                                    _cE("scroll-view", _uM("direction" to "vertical", "class" to "list"), _uA(
                                        _cE("view", null, _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(wordList), fun(item, index, __index, _cached): Any {
                                                return _cE("view", _uM("class" to "item"), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                                        _cE("text", _uM("style" to _nS(_uM("width" to "30rpx", "margin-right" to "4rpx"))), "(" + _tD(index + 1) + ")", 5),
                                                        _cE("text", null, _tD(item?.englishText), 1)
                                                    ), 4),
                                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "justify-content" to "space-between"))), _uA(
                                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "margin-left" to "34rpx"))), _uA(
                                                            _cE("text", _uM("style" to _nS(_uM("color" to "#8D8D8D", "font-size" to "10.55rpx"))), _tD(item?.phoneticSymbol), 5)
                                                        ), 4),
                                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "width" to "110rpx"))), _uA(
                                                            _cE("text", _uM("style" to _nS(_uM("color" to "#8D8D8D", "font-size" to "10.55rpx"))), "累计答对次数：", 4),
                                                            _cE("text", _uM("style" to _nS(_uM("color" to "#216CFF", "font-size" to "10.55rpx"))), _tD(item?.currentKeepRightNum ?: 0), 5),
                                                            _cE("text", _uM("style" to _nS(_uM("color" to "#8D8D8D", "font-size" to "10.55rpx"))), "次", 4)
                                                        ), 4)
                                                    ), 4)
                                                ))
                                            }
                                            ), 256)
                                        ))
                                    ))
                                ), 4)
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "marginTop" to "13.48rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "btn" to _uM(".d_content " to _uM("width" to "100.78rpx", "height" to "35.16rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "fontSize" to "14rpx", "lineHeight" to "35.16rpx", "textAlign" to "center", "color" to "#ffffff", "borderTopLeftRadius" to "35rpx", "borderTopRightRadius" to "35rpx", "borderBottomRightRadius" to "35rpx", "borderBottomLeftRadius" to "35rpx")), "d_left" to _uM(".d_content " to _uM("height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginRight" to "5.86rpx", "paddingTop" to "17.58rpx", "paddingRight" to "9.96rpx", "paddingBottom" to "17.58rpx", "paddingLeft" to "9.96rpx")), "d_left_top" to _uM(".d_content .d_left " to _uM("flexDirection" to "row")), "d_left_title" to _uM(".d_content .d_left " to _uM("fontWeight" to "700", "fontSize" to "14rpx", "color" to "#3D3D3D", "marginLeft" to "6.45rpx")), "unit_list" to _uM(".d_content .d_left " to _uM("marginTop" to "15.82rpx", "height" to "281rpx")), "unit_item" to _uM(".d_content .d_left .unit_list " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "unit_item_title" to _uM(".d_content .d_left .unit_list .unit_item " to _uM("fontWeight" to "400", "fontSize" to "11.72rpx", "flex" to 1, "marginLeft" to "6.45rpx")), "unit_item_count" to _uM(".d_content .d_left .unit_list .unit_item " to _uM("fontWeight" to "400", "fontSize" to "11.72rpx", "color" to "#8BA0C4", "lineHeight" to "35rpx")), "d_right" to _uM(".d_content " to _uM("flex" to 1, "width" to "100%", "height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx")), "d_top" to _uM(".d_content .d_right " to _uM("paddingTop" to "13.48rpx", "paddingRight" to "16.41rpx", "paddingBottom" to "13.48rpx", "paddingLeft" to "16.41rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "d_top_text" to _uM(".d_content .d_right .d_top " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D")), "d_top_text_tip" to _uM(".d_content .d_right .d_top " to _uM("fontSize" to "12rpx", "color" to "#6694DF")), "d-list" to _uM(".d_content .d_right " to _uM("paddingTop" to 0, "paddingRight" to "7rpx", "paddingBottom" to 0, "paddingLeft" to "7rpx")), "d-list-title" to _uM(".d_content .d_right .d-list " to _uM("width" to "100%", "height" to "35rpx", "backgroundImage" to "none", "backgroundColor" to "#F1F5FC", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "fontWeight" to "bold", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx", "paddingLeft" to "18rpx")), "list" to _uM(".d_content .d_right .d-list " to _uM("flex" to 1, "marginBottom" to "8.2rpx")), "item" to _uM(".d_content .d_right .d-list .list " to _uM("marginTop" to "8.2rpx", "width" to "700rpx", "height" to "54rpx", "backgroundImage" to "none", "backgroundColor" to "#F6F6F6", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "paddingTop" to "8rpx", "paddingRight" to "18.16rpx", "paddingBottom" to "8rpx", "paddingLeft" to "18.16rpx", "justifyContent" to "space-between")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
