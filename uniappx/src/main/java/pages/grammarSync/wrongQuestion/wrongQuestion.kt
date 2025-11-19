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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesGrammarSyncWrongQuestionWrongQuestion : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesGrammarSyncWrongQuestionWrongQuestion) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesGrammarSyncWrongQuestionWrongQuestion
            val _cache = __ins.renderCache
            val loading = ref<Boolean>(false)
            val modelVale = ref(0)
            val showIndex = ref(0)
            val pagingX = ref(null)
            val queryParams = ref(object : UTSJSONObject() {
                var textbookId = getTextBookId()
                var textbookUnitId = null
                var module = "500"
                var subModule = null
                var isExercise = "0"
                var problemType = null
                var pageSize: Number = 3000
            })
            val dataList = ref(_uA<AppProblemVo>())
            val unitSelectList = ref(_uA<IdAndNameVo>())
            val problemTypeSelectList = ref(_uA<IdAndNameVo>(IdAndNameVo(id = 10, name = problemTypeStrMap.getString("10")), IdAndNameVo(id = 12, name = problemTypeStrMap.getString("12")), IdAndNameVo(id = 13, name = problemTypeStrMap.getString("13"))))
            val sobModuleSelectList = ref(_uA<IdAndNameVo>(IdAndNameVo(id = 501, name = getReversedSubModelKey("501")), IdAndNameVo(id = 502, name = getReversedSubModelKey("502")), IdAndNameVo(id = 503, name = getReversedSubModelKey("503")), IdAndNameVo(id = 504, name = getReversedSubModelKey("504"))))
            val removeHTMLTags = fun(html: String?): String {
                if (html == null) {
                    return ""
                }
                return html!!.replace(UTSRegExp("<[^>]*>", "g"), "")
            }
            val getProblemTypeStr = fun(problemType: String?): String {
                if (problemType == null) {
                    return ""
                }
                var str = problemTypeStrMap.getString(problemType)
                return str ?: ""
            }
            fun gen_queryList_fn() {
                uni_request<Result<UTSArray<AppProblemVo>>>(RequestOptions(url = getUrl("/biz/problem/api/userErrorBook/problemList"), method = "GET", header = getHeader(), data = queryParams.value, success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    var data = responseData.data
                    dataList.value = data!!
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){
                    loading.value = false
                }
                ))
            }
            val queryList = ::gen_queryList_fn
            fun gen_goInfo_fn(item: AppProblemVo) {
                uni_setStorageSync("temp_data", item)
                uni_navigateTo(NavigateToOptions(url = "/pages/examRecords/examRecords?isTemp=1"))
            }
            val goInfo = ::gen_goInfo_fn
            fun gen_selectTextbookUnitId_fn(id: Number?) {
                queryParams.value["textbookUnitId"] = id
                queryList()
            }
            val selectTextbookUnitId = ::gen_selectTextbookUnitId_fn
            fun gen_selectSubModule_fn(id: Number?) {
                queryParams.value["subModule"] = id
                queryList()
            }
            val selectSubModule = ::gen_selectSubModule_fn
            fun gen_selectProblemType_fn(id: Number?) {
                queryParams.value["problemType"] = id
                queryList()
            }
            val selectProblemType = ::gen_selectProblemType_fn
            val txText = ref("全部题型")
            val dyText = ref("全部单元")
            val zjText = ref("全部章节")
            fun gen_txOk_fn(item: IdAndNameVo) {
                showIndex.value = 0
                txText.value = item.name!!
                try {
                    selectProblemType(item.id)
                }
                 catch (error: Throwable) {}
            }
            val txOk = ::gen_txOk_fn
            fun gen_dyOk_fn(item: IdAndNameVo) {
                showIndex.value = 0
                dyText.value = item.name!!
                try {
                    selectTextbookUnitId(item.id)
                }
                 catch (error: Throwable) {}
            }
            val dyOk = ::gen_dyOk_fn
            fun gen_zjOk_fn(item: IdAndNameVo) {
                showIndex.value = 0
                zjText.value = item.name!!
                try {
                    selectSubModule(item.id)
                }
                 catch (error: Throwable) {}
            }
            val zjOk = ::gen_zjOk_fn
            fun gen_getUnitSelectList_fn() {
                uni_request<Result<UTSArray<IdAndNameVo>>>(RequestOptions(url = getUrl("/biz/textbookUnit/api/selectList"), method = "GET", header = getHeader(), data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var haveUnitName2 = "1"
                }, success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    var data = responseData.data
                    unitSelectList.value = data!!
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){
                    loading.value = false
                }
                ))
            }
            val getUnitSelectList = ::gen_getUnitSelectList_fn
            fun gen_goUserErrorBookTest_fn() {
                if (dataList.value.length == 0) {
                    uni_showToast(ShowToastOptions(title = "没有待练习的题目", icon = "none"))
                    return
                }
                var params: UTSJSONObject = object : UTSJSONObject() {
                    var textbookId = queryParams.value["textbookId"]
                    var textbookUnitId = queryParams.value["textbookUnitId"]
                    var module = "500"
                    var subModule = queryParams.value["subModule"]
                    var isExercise = "1"
                    var problemType = queryParams.value["problemType"]
                }
                var textbookId = params["textbookId"] ?: ""
                var unitId = params["textbookUnitId"] ?: ""
                var module = params["module"] ?: ""
                var subModule = params["textbookUnitSubModule"] ?: ""
                var unitName = "智能测试"
                uni_navigateTo(NavigateToOptions(url = "/pages/grammarSync/test/test?textbookId=" + textbookId + "&textbookUnitId=" + unitId + "&module=" + module + "&subModule=" + subModule + "&unitTitle=" + unitName + "&intelligence=1", fail = fun(_) {
                    console.log()
                }
                ))
                return
            }
            val goUserErrorBookTest = ::gen_goUserErrorBookTest_fn
            onLoad(fun(_options){
                loading.value = true
                getUnitSelectList()
                queryList()
            }
            )
            onPageHide(fun(){
                showIndex.value = 0
            }
            )
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_LearningInfo = resolveEasyComponent("LearningInfo", GenComponentsLearningInfoLearningInfoClass)
                val _component_ChangeGrade = resolveEasyComponent("ChangeGrade", GenComponentsChangeGradeChangeGradeClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_l_loading = resolveEasyComponent("l-loading", GenUniModulesLimeLoadingComponentsLLoadingLLoadingClass)
                val _component_selectOption = resolveEasyComponent("selectOption", GenComponentsSelectOptionSelectOptionClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "sync_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "错题本"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_LearningInfo),
                                _cV(_component_ChangeGrade)
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "sync_content", "onClick" to fun(){
                            showIndex.value = 0
                        }
                        ), _uA(
                            _cV(_component_BackgroundImage, _uM("src" to "/static/ico/read_head.png", "bgStyle" to "width:100%;height:100%;position:absolute;left:0;top:0", "className" to "top_head"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("view", _uM("class" to "top_head_content"), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                            _cE("view", _uM("class" to "top_item"), _uA(
                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                    _cE("text", _uM("class" to "top_item_title"), "题型")
                                                ), 4),
                                                _cE("view", _uM("class" to "select_box", "onClick" to withModifiers(fun(){
                                                    if (unref(showIndex) == 1) {
                                                        showIndex.value = 0
                                                    } else {
                                                        showIndex.value = 1
                                                    }
                                                }
                                                , _uA(
                                                    "stop"
                                                ))), _uA(
                                                    _cE("text", _uM("class" to "title"), _tD(unref(txText)), 1),
                                                    _cE("image", _uM("src" to "/static/ico/bottom_ico.png", "mode" to "", "class" to "bottom_ico"))
                                                ), 8, _uA(
                                                    "onClick"
                                                ))
                                            )),
                                            _cE("view", _uM("class" to "top_item"), _uA(
                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                    _cE("text", _uM("class" to "top_item_title"), "单元")
                                                ), 4),
                                                _cE("view", _uM("class" to "select_box", "onClick" to withModifiers(fun(){
                                                    if (unref(showIndex) == 2) {
                                                        showIndex.value = 0
                                                    } else {
                                                        showIndex.value = 2
                                                    }
                                                }
                                                , _uA(
                                                    "stop"
                                                ))), _uA(
                                                    _cE("text", _uM("class" to "title"), _tD(unref(dyText)), 1),
                                                    _cE("image", _uM("src" to "/static/ico/bottom_ico.png", "mode" to "", "class" to "bottom_ico"))
                                                ), 8, _uA(
                                                    "onClick"
                                                ))
                                            )),
                                            _cE("view", _uM("class" to "top_item"), _uA(
                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                    _cE("text", _uM("class" to "top_item_title"), "章节")
                                                ), 4),
                                                _cE("view", _uM("class" to "select_box", "onClick" to withModifiers(fun(){
                                                    if (unref(showIndex) == 3) {
                                                        showIndex.value = 0
                                                    } else {
                                                        showIndex.value = 3
                                                    }
                                                }
                                                , _uA(
                                                    "stop"
                                                ))), _uA(
                                                    _cE("text", _uM("class" to "title"), _tD(unref(zjText)), 1),
                                                    _cE("image", _uM("src" to "/static/ico/bottom_ico.png", "mode" to "", "class" to "bottom_ico"))
                                                ), 8, _uA(
                                                    "onClick"
                                                ))
                                            ))
                                        ), 4),
                                        _cE("view", null, _uA(
                                            _cV(_component_BackgroundImage, _uM("src" to "/static/ico/worngBoot_ico.png", "bgStyle" to "width:100%;height:100%;position:absolute;left:0;top:0", "onClick" to fun(){
                                                goUserErrorBookTest()
                                            }
                                            ), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                return _uA(
                                                    _cE("view", _uM("class" to "r_img"), _uA(
                                                        _cE("text", _uM("class" to "t1"), "错题智能测试")
                                                    ))
                                                )
                                            }
                                            ), "_" to 1), 8, _uA(
                                                "onClick"
                                            ))
                                        ))
                                    ))
                                )
                            }
                            ), "_" to 1)),
                            _cE("view", _uM("class" to "b_content"), _uA(
                                _cE("view", _uM("style" to _nS(_uM("padding-bottom" to "-9.38rpx", "flex" to "1"))), _uA(
                                    _cE("view", _uM("class" to "read_item", "style" to _nS(_uM("flex" to "1"))), _uA(
                                        _cE("view", _uM("class" to "read_title"), _uA(
                                            _cE("text", null, "错题列表"),
                                            _cE("text", _uM("class" to "read_title_err"), "错题数量：" + _tD(unref(dataList).length), 1)
                                        )),
                                        withDirectives(_cE("view", _uM("style" to _nS(_uM("justify-content" to "center", "align-items" to "center", "height" to "100%", "width" to "100%"))), _uA(
                                            _cV(_component_l_loading, _uM("color" to "#AEC3FC", "size" to "80rpx"))
                                        ), 4), _uA(
                                            _uA(
                                                vShow,
                                                unref(loading)
                                            )
                                        )),
                                        withDirectives(_cE("view", _uM("class" to "content read_item_content"), _uA(
                                            _cE("list-view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(unref(dataList), fun(item, index, __index, _cached): Any {
                                                    return _cE("list-item", _uM("key" to index), _uA(
                                                        _cE("view", _uM("class" to "read_item_item", "onClick" to fun(){
                                                            goInfo(item)
                                                        }
                                                        ), _uA(
                                                            _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "flex" to "1", "justify-content" to "space-between"))), _uA(
                                                                _cE("text", null, _tD(removeHTMLTags(item.questionContent)), 1),
                                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                                    _cE("image", _uM("src" to "/static/ico/right_ico.png", "style" to _nS(_uM("width" to "5.86rpx", "height" to "9.38rpx")), "mode" to ""), null, 4),
                                                                    _cE("view", null, _uA(
                                                                        _cE("text", _uM("style" to _nS(_uM("color" to "#6694DF", "font-size" to "11.72rpx", "font-weight" to "700"))), _tD(getProblemTypeStr(item.problemType)), 5)
                                                                    ))
                                                                ), 4)
                                                            ), 4),
                                                            _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "justify-content" to "space-between", "margin-top" to "14rpx", "margin-bottom" to "10rpx"))), _uA(
                                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "11.72rpx", "color" to "#7B7B7B"))), "答错次数/总次数：", 4),
                                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "11.72rpx", "color" to "#5A9F32"))), _tD(item.exerciseErrorNum) + "/" + _tD(item.exerciseNum), 5)
                                                                ), 4),
                                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "11.72rpx", "color" to "#7B7B7B"))), "最近答题结果：", 4),
                                                                    if (item.lastRightStatus == "1") {
                                                                        _cE("text", _uM("key" to 0, "style" to _nS(_uM("font-size" to "11.72rpx", "color" to "#5A9F32"))), "正确", 4)
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    }
                                                                    ,
                                                                    if (item.lastRightStatus == "0") {
                                                                        _cE("text", _uM("key" to 1, "style" to _nS(_uM("font-size" to "11.72rpx", "color" to "#E13535"))), "错误", 4)
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    }
                                                                ), 4),
                                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "11.72rpx", "color" to "#7B7B7B"))), "最近答题时间：", 4),
                                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "11.72rpx", "color" to "#3D3D3D"))), _tD(item.lastExerciseStatTime), 5)
                                                                ), 4)
                                                            ), 4)
                                                        ), 8, _uA(
                                                            "onClick"
                                                        ))
                                                    ))
                                                }
                                                ), 128)
                                            ), 4)
                                        ), 512), _uA(
                                            _uA(
                                                vShow,
                                                !unref(loading)
                                            )
                                        ))
                                    ), 4)
                                ), 4)
                            ))
                        ), 8, _uA(
                            "onClick"
                        )),
                        if (unref(showIndex) == 1) {
                            _cV(_component_selectOption, _uM("key" to 0, "list" to unref(problemTypeSelectList), "defName" to "全部题型", "style" to _nS(_uM("position" to "fixed", "top" to "109rpx", "left" to "75rpx")), "onOk" to txOk), null, 8, _uA(
                                "list",
                                "style"
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (unref(showIndex) == 2) {
                            _cV(_component_selectOption, _uM("key" to 1, "list" to unref(unitSelectList), "defName" to "全部单元", "style" to _nS(_uM("position" to "fixed", "top" to "109rpx", "left" to "254rpx")), "onOk" to dyOk), null, 8, _uA(
                                "list",
                                "style"
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (unref(showIndex) == 3) {
                            _cV(_component_selectOption, _uM("key" to 2, "list" to unref(sobModuleSelectList), "defName" to "全部章节", "style" to _nS(_uM("position" to "fixed", "top" to "109rpx", "left" to "433rpx")), "onOk" to zjOk), null, 8, _uA(
                                "list",
                                "style"
                            ))
                        } else {
                            _cC("v-if", true)
                        }
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
                return _uM("sync_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "sync_content" to _pS(_uM("width" to "715rpx", "height" to "332rpx", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFFFFF", "borderRightColor" to "#FFFFFF", "borderBottomColor" to "#FFFFFF", "borderLeftColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "14.56rpx", "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto")), "top_head" to _uM(".sync_content " to _uM("width" to "100%", "height" to "82.03rpx", "paddingTop" to 0, "paddingRight" to "15rpx", "paddingBottom" to 0, "paddingLeft" to "15rpx")), "top_head_content" to _uM(".sync_content .top_head " to _uM("height" to "69.41rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "top_item" to _uM(".sync_content " to _uM("flexDirection" to "row", "justifyContent" to "center")), "top_item_title" to _uM(".sync_content .top_item " to _uM("fontSize" to "15rpx", "color" to "#3D3D3D", "fontWeight" to "bold", "marginRight" to "11.72rpx")), "select_box" to _uM(".sync_content .top_item " to _uM("width" to "121rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "3rpx", "borderTopRightRadius" to "3rpx", "borderBottomRightRadius" to "3rpx", "borderBottomLeftRadius" to "3rpx", "flexDirection" to "row", "alignItems" to "center", "marginRight" to "16rpx", "paddingTop" to 0, "paddingRight" to "6rpx", "paddingBottom" to 0, "paddingLeft" to "4rpx")), "title" to _uM(".sync_content .top_item .select_box " to _uM("flex" to 1)), "bottom_ico" to _uM(".sync_content .top_item .select_box " to _uM("width" to "9.96rpx", "height" to "5.86rpx")), "r_item" to _uM(".sync_content " to _uM("flexDirection" to "row")), "b_content" to _uM(".sync_content " to _uM("width" to "100%", "flex" to 1, "backgroundImage" to "none", "backgroundColor" to "#DAE9FC", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "-12.62rpx", "paddingTop" to "6.54rpx", "paddingRight" to "10.55rpx", "paddingBottom" to "6.54rpx", "paddingLeft" to "10.55rpx")), "read_item" to _uM(".sync_content .b_content " to _uM("alignItems" to "center", "width" to "100%", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "marginBottom" to "9.38rpx", "paddingTop" to "9.96rpx", "paddingRight" to "9.96rpx", "paddingBottom" to "9.96rpx", "paddingLeft" to "9.96rpx")), "read_title" to _uM(".sync_content .b_content .read_item " to _uM("width" to "100%", "height" to "30rpx", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "3rpx", "borderTopRightRadius" to "3rpx", "borderBottomRightRadius" to "3rpx", "borderBottomLeftRadius" to "3rpx", "paddingTop" to 0, "paddingRight" to "8.2rpx", "paddingBottom" to 0, "paddingLeft" to "8.2rpx", "display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "read_title_err" to _uM(".sync_content .b_content .read_item .read_title " to _uM("color" to "#FF0000", "fontWeight" to "bold")), "item_btn1" to _uM(".sync_content " to _uM("width" to "65rpx", "height" to "23rpx", "backgroundImage" to "none", "backgroundColor" to "#FA9600", "borderTopLeftRadius" to "23rpx", "borderTopRightRadius" to "23rpx", "borderBottomRightRadius" to "23rpx", "borderBottomLeftRadius" to "23rpx", "textAlign" to "center", "lineHeight" to "23rpx", "color" to "#FFFFFF", "fontSize" to "12rpx", "marginRight" to "14rpx")), "item_btn2" to _uM(".sync_content " to _uM("width" to "65rpx", "height" to "23rpx", "backgroundImage" to "none", "backgroundColor" to "#ECEFFA", "borderTopLeftRadius" to "23rpx", "borderTopRightRadius" to "23rpx", "borderBottomRightRadius" to "23rpx", "borderBottomLeftRadius" to "23rpx", "textAlign" to "center", "lineHeight" to "23rpx", "fontSize" to "12rpx", "color" to "#818DCA")), "content" to _pS(_uM("flex" to 1)), "read_item_content" to _pS(_uM("width" to "100%")), "read_item_item" to _pS(_uM("backgroundColor" to "#F6F6F6", "marginTop" to "8rpx", "paddingTop" to "7rpx", "paddingRight" to "7rpx", "paddingBottom" to "7rpx", "paddingLeft" to "7rpx", "borderTopLeftRadius" to "3rpx", "borderTopRightRadius" to "3rpx", "borderBottomRightRadius" to "3rpx", "borderBottomLeftRadius" to "3rpx")), "nav-r" to _pS(_uM("borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_text" to _uM(".nav-r " to _uM("fontWeight" to "700", "fontSize" to "13rpx", "color" to "#FA9600", "marginLeft" to "2.93rpx")), "r_img" to _pS(_uM("width" to "124.8rpx", "height" to "36.91rpx", "position" to "relative", "alignItems" to "center", "justifyContent" to "center")), "t1" to _uM(".r_img " to _uM("marginTop" to "6rpx", "marginLeft" to "24rpx", "fontWeight" to "700", "fontSize" to "13rpx", "color" to "#FA9600", "lineHeight" to "15rpx")), "z-pagng-empty" to _pS(_uM("justifyContent" to "center", "alignItems" to "center", "height" to "100%", "width" to "100%")), "loadMore" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "center", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx")), "loadMore_text" to _pS(_uM("color" to "#6694DF", "fontFamily" to "Arial, Arial", "fontSize" to "13rpx", "textAlign" to "left", "fontStyle" to "normal")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
