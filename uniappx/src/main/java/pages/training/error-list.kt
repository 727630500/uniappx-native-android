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
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesTrainingErrorList : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTrainingErrorList) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTrainingErrorList
            val _cache = __ins.renderCache
            var taskList = ref<UTSArray<errZsdItem>?>(null)
            val sortNum = ref(0)
            val params: UTSJSONObject = object : UTSJSONObject() {
                var sourceType: Number = 3
                var knowledgePointsId = null
            }
            fun gen_problemTypeFilter_fn(type: String?): String {
                if (type == null) {
                    return ""
                }
                when (parseInt(type)) {
                    1 -> 
                        return "英语单词"
                    2 -> 
                        return "英语句子"
                    3 -> 
                        return "英语文章"
                    10 -> 
                        return "单选题"
                    11 -> 
                        return "多选题"
                    12 -> 
                        return "判断题"
                    13 -> 
                        return "填空题"
                    14 -> 
                        return "选词填空"
                    15 -> 
                        return "语法填空"
                    16 -> 
                        return "完形填空"
                    17 -> 
                        return "传统阅读"
                    18 -> 
                        return "六选五"
                    100 -> 
                        return "教材资料"
                    else -> 
                        return ""
                }
            }
            val problemTypeFilter = ::gen_problemTypeFilter_fn
            val isExpanded = ref(false)
            fun gen_getList_fn() {
                taskList.value = _uA()
                uni_showLoading(ShowLoadingOptions(title = "加载中..."))
                if (!isExpanded.value) {
                    uni_request<Result<UTSArray<errZsdItem>>>(RequestOptions(url = getUrl("/biz/problem/api/errorStat/errorKnowledgeList"), method = "GET", data = params, header = getHeader(), success = fun(res){
                        val responseData = res.data
                        if (responseData == null) {
                            return
                        }
                        if (responseData.code as Number != 200) {
                            return uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        }
                        taskList.value = responseData.data
                    }, fail = fun(err){
                        console.log(err)
                    }, complete = fun(_){
                        uni_hideLoading()
                    }))
                } else {
                    uni_request<Result<UTSArray<errZsdItem>>>(RequestOptions(url = getUrl("/biz/problem/api/errorStat/errorBookList"), method = "GET", data = params, header = getHeader(), success = fun(res){
                        val responseData = res.data
                        if (responseData == null) {
                            return
                        }
                        if (responseData.code as Number != 200) {
                            return uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        }
                        taskList.value = responseData.data
                    }
                    , fail = fun(err){
                        console.log(err)
                    }
                    , complete = fun(_){
                        uni_hideLoading()
                    }
                    ))
                }
            }
            val getList = ::gen_getList_fn
            val seeCtList = fun(item: errZsdItem){
                uni_navigateTo(NavigateToOptions(url = "/pages/training/error-list-ct?id=" + item.id))
            }
            val seeCt = fun(item: errZsdItem){
                uni_showLoading(ShowLoadingOptions(title = "加载中..."))
                var _id = item.id
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/problem/api/" + _id), method = "GET", header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        return uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                    }
                    uni_setStorageSync("temp_data", responseData.data ?: UTSJSONObject())
                    uni_navigateTo(NavigateToOptions(url = "/pages/examRecords/examRecords?isTemp=1&title=错题解析"))
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){
                    uni_hideLoading()
                }
                ))
            }
            val handleSort = fun(){
                if (sortNum.value == 0) {
                    sortNum.value = 1
                    taskList.value = taskList.value!!.sort(fun(a, b): Number {
                        return (a.errorRate ?: 0) - (b.errorRate ?: 0)
                    }).slice()
                } else if (sortNum.value == 1) {
                    sortNum.value = 2
                    taskList.value = taskList.value!!.sort(fun(a, b): Number {
                        return (b.errorRate ?: 0) - (a.errorRate ?: 0)
                    }).slice()
                } else if (sortNum.value == 2) {
                    sortNum.value = 0
                    getList()
                }
            }
            fun gen_toggleFn_fn(ev: Boolean) {
                console.log(ev)
                isExpanded.value = !ev
                getList()
            }
            val toggleFn = ::gen_toggleFn_fn
            val stripHtmlTags = fun(html: String?): String {
                if (html == null) {
                    return ""
                }
                return html.replace(UTSRegExp("<[^>]*>", "g"), "")
            }
            val getQuestionContent = fun(str: String?): String {
                if (str == null) {
                    return ""
                }
                val content = stripHtmlTags(str)
                if (content.trim() == "") {
                    return ""
                }
                val limitedContent = if (content.length > 20) {
                    content.substring(0, 20) + "..."
                } else {
                    content
                }
                return "" + limitedContent
            }
            onPageShow(fun(){
                getList()
            }
            )
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_ToggleButton = resolveEasyComponent("ToggleButton", GenComponentsToggleButtonToggleButtonClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "错题统计"), _uM("right" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_ToggleButton, _uM("onToggle" to fun(`$event`: Any){
                                    toggleFn(`$event` as Boolean)
                                }
                                , "defaultState" to "expand", "expandText" to "知识点错题统计", "collapseText" to "错题列表"), null, 8, _uA(
                                    "onToggle"
                                ))
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "d_right"), _uA(
                                _cE("view", _uM("class" to "word_table"), _uA(
                                    _cE("view", _uM("class" to "word_table-title"), _uA(
                                        _cE("text", _uM("class" to "word_table-title-text"), _tD(if (unref(isExpanded)) {
                                            "题号"
                                        } else {
                                            "知识点编号"
                                        }
                                        ), 1),
                                        _cE("text", _uM("class" to "word_table-title-text"), _tD(if (unref(isExpanded)) {
                                            "题目类型"
                                        } else {
                                            "知识点名称"
                                        }
                                        ), 1),
                                        if (isTrue(unref(isExpanded))) {
                                            _cE("text", _uM("key" to 0, "class" to "word_table-title-text"), "题干")
                                        } else {
                                            _cC("v-if", true)
                                        }
                                        ,
                                        _cE("text", _uM("class" to "word_table-title-text"), "出题次数"),
                                        _cE("text", _uM("class" to "word_table-title-text"), "答错次数"),
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                            _cE("text", _uM("class" to "word_table-title-text", "style" to _nS(_uM("margin-right" to "4rpx"))), "错误率", 4),
                                            if (unref(sortNum) == 0) {
                                                _cE("image", _uM("key" to 0, "src" to "/static/sort.png", "mode" to "widthFix", "style" to _nS(_uM("width" to "20rpx")), "onClick" to handleSort), null, 4)
                                            } else {
                                                _cC("v-if", true)
                                            }
                                            ,
                                            if (unref(sortNum) == 1) {
                                                _cE("image", _uM("key" to 1, "src" to "/static/sort-up.png", "mode" to "widthFix", "style" to _nS(_uM("width" to "20rpx")), "onClick" to handleSort), null, 4)
                                            } else {
                                                _cC("v-if", true)
                                            }
                                            ,
                                            if (unref(sortNum) == 2) {
                                                _cE("image", _uM("key" to 2, "src" to "/static/sort-down.png", "mode" to "widthFix", "style" to _nS(_uM("width" to "20rpx")), "onClick" to handleSort), null, 4)
                                            } else {
                                                _cC("v-if", true)
                                            }
                                        ), 4),
                                        _cE("text", _uM("class" to "word_table-title-text", "style" to _nS(_uM("max-width" to "80rpx"))), "操作", 4)
                                    )),
                                    _cE("scroll-view", _uM("direction" to "vertical", "class" to "word_table-body", "style" to _nS(_uM("margin-top" to "6rpx"))), _uA(
                                        _cE("view", null, _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(taskList), fun(item, index, __index, _cached): Any {
                                                return _cE("view", _uM("class" to "word_table-item", "style" to _nS(_uM("backgroundColor" to if (index % 2 == 0) {
                                                    "#fff"
                                                } else {
                                                    "#F6F6F6"
                                                }
                                                ))), _uA(
                                                    _cE("text", _uM("class" to "word_table-item-text-desc"), _tD(if (unref(isExpanded)) {
                                                        item.id
                                                    } else {
                                                        item.knowledgePointsCode
                                                    }
                                                    ), 1),
                                                    _cE("text", _uM("class" to "word_table-item-text-desc"), _tD(if (unref(isExpanded)) {
                                                        problemTypeFilter(item.problemType)
                                                    } else {
                                                        item.knowledgePointsName
                                                    }
                                                    ), 1),
                                                    if (isTrue(unref(isExpanded))) {
                                                        _cE("text", _uM("key" to 0, "class" to "word_table-item-text-desc"), _tD(getQuestionContent(item.questionContent)), 1)
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                    ,
                                                    _cE("text", _uM("class" to "word_table-item-text-desc"), _tD(item.exerciseNum), 1),
                                                    _cE("text", _uM("class" to "word_table-item-text-desc"), _tD(item.exerciseErrorNum), 1),
                                                    _cE("text", _uM("class" to "word_table-item-text-desc"), _tD(item.errorRate) + "%", 1),
                                                    if (isTrue(!unref(isExpanded))) {
                                                        _cE("text", _uM("key" to 1, "class" to "btn-text", "style" to _nS(_uM("max-width" to "80rpx", "flex" to "1")), "onClick" to fun(){
                                                            seeCtList(item)
                                                        }), "查看错题列表", 12, _uA(
                                                            "onClick"
                                                        ))
                                                    } else {
                                                        _cE("text", _uM("key" to 2, "class" to "btn-text", "style" to _nS(_uM("max-width" to "80rpx", "flex" to "1")), "onClick" to fun(){
                                                            seeCt(item)
                                                        }
                                                        ), "预览题目", 12, _uA(
                                                            "onClick"
                                                        ))
                                                    }
                                                ), 4)
                                            }
                                            ), 256)
                                        ))
                                    ), 4)
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "marginTop" to "13.48rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "d_right" to _uM(".d_content " to _uM("flex" to 1, "width" to "100%", "height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx")), "btn-text" to _uM(".d_content .d_right " to _uM("fontSize" to "12rpx", "color" to "#607BFF", "lineHeight" to "18rpx", "textAlign" to "center")), "word_table" to _uM(".d_content .d_right " to _uM("marginTop" to "7.62rpx", "marginRight" to "7.62rpx", "marginBottom" to "7.62rpx", "marginLeft" to "7.62rpx", "flex" to 1)), "word_table-title" to _uM(".d_content .d_right .word_table " to _uM("height" to "35rpx", "backgroundImage" to "none", "backgroundColor" to "#F1F5FC", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "flexDirection" to "row", "width" to "100%", "alignItems" to "center")), "word_table-title-text" to _uM(".d_content .d_right .word_table .word_table-title " to _uM("flex" to 1, "textAlign" to "center", "fontWeight" to "700")), "word_table-body" to _uM(".d_content .d_right .word_table " to _uM("flex" to 1, "paddingBottom" to "-5.86rpx")), "word_table-item" to _uM(".d_content .d_right .word_table .word_table-body " to _uM("marginBottom" to "5.86rpx", "height" to "46rpx", "backgroundImage" to "none", "backgroundColor" to "#F6F6F6", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "flexDirection" to "row", "alignItems" to "center")), "word_table-item-text-desc" to _uM(".d_content .d_right .word_table .word_table-body .word_table-item " to _uM("textAlign" to "center", "flex" to 1, "fontSize" to "12rpx", "color" to "#3D3D3D")), "word_table-body-item-status" to _uM(".d_content .d_right .word_table .word_table-body .word_table-item " to _uM("width" to "43.95rpx", "height" to "17.58rpx", "backgroundImage" to "none", "backgroundColor" to "#B0BFD9", "borderTopLeftRadius" to "17.58rpx", "borderTopRightRadius" to "17.58rpx", "borderBottomRightRadius" to "17.58rpx", "borderBottomLeftRadius" to "17.58rpx", "justifyContent" to "center", "alignItems" to "center")), "word_table-body-item-status-text" to _uM(".d_content .d_right .word_table .word_table-body .word_table-item " to _uM("fontSize" to "10.55rpx", "color" to "#818DCA")), "nav-r" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "width" to "100rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFEFD6", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx")), "word_text" to _uM(".nav-r " to _uM("fontWeight" to "700", "fontSize" to "13rpx", "color" to "#FA9600", "marginLeft" to "2.93rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
