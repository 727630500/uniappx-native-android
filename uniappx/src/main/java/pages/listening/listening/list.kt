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
open class GenPagesListeningListeningList : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesListeningListeningList) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesListeningListeningList
            val _cache = __ins.renderCache
            val rightInfo = ref("\uE600")
            val wordList = ref<UTSArray<teachingMaterial>?>(null)
            val wordListEnd = ref<UTSArray<teachingMaterial>?>(null)
            val title = ref("")
            val wordListNotEnd = ref<UTSArray<teachingMaterial>?>(null)
            val followAlongList = ref(_uA<followAlongItem2>())
            val unitTitle = ref("")
            val studyTaskId = ref(0)
            val type = ref(0)
            val problemTypeMap = ref<Map<String, String>>(Map(_uA(
                _uA(
                    "30",
                    "听短对话选答案"
                ),
                _uA(
                    "31",
                    "听长对话选答案"
                ),
                _uA(
                    "32",
                    "听短文选答案"
                ),
                _uA(
                    "33",
                    "听文章/看文章/填空"
                ),
                _uA(
                    "34",
                    "听句子,复述句子"
                ),
                _uA(
                    "35",
                    "听文章,复述文章"
                )
            )))
            val selectedFilterType = ref<String?>(null)
            val problemTypeIconMap = ref(Map<String, String>(_uA(
                _uA(
                    "30",
                    "/static/ico/listening/ico1.png"
                ),
                _uA(
                    "31",
                    "/static/ico/listening/ico2.png"
                ),
                _uA(
                    "32",
                    "/static/ico/listening/ico3.png"
                ),
                _uA(
                    "33",
                    "/static/ico/listening/ico4.png"
                ),
                _uA(
                    "34",
                    "/static/ico/listening/ico1.png"
                ),
                _uA(
                    "35",
                    "/static/ico/listening/ico1.png"
                )
            )))
            val currentFilterTypeText = computed(fun(): String {
                if (selectedFilterType.value == null) {
                    return "听力练习"
                }
                return problemTypeMap.value.get(selectedFilterType.value!!) ?: "听力练习"
            }
            )
            val currentFilterTypeIcon = computed(fun(): String {
                if (selectedFilterType.value == null) {
                    return "/static/ico/listening/ico1.png"
                }
                return problemTypeIconMap.value.get(selectedFilterType.value!!) ?: "/static/ico/listening/ico1.png"
            }
            )
            val stripHtmlTags = fun(html: String?): String {
                if (html == null) {
                    return ""
                }
                return html.replace(UTSRegExp("<[^>]*>", "g"), "")
            }
            val getQuestionContent = fun(item: followAlongItem2): String {
                val content = stripHtmlTags(item.questionContent)
                if (content.trim() == "") {
                    return "题干：听以下对话，选择正确的答案。"
                }
                val limitedContent = if (content.length > 30) {
                    content.substring(0, 30) + "..."
                } else {
                    content
                }
                return "\u9898\u5E72\uFF1A" + limitedContent
            }
            val filteredList = computed(fun(): UTSArray<followAlongItem2> {
                var list = followAlongList.value
                if (selectedFilterType.value != null) {
                    list = list.filter(fun(item): Boolean {
                        return item.problemType == selectedFilterType.value
                    }
                    )
                }
                return list
            }
            )
            val getTypeCount = fun(problemType: String): Number {
                return followAlongList.value.filter(fun(item): Boolean {
                    return item.problemType == problemType
                }
                ).length
            }
            val handleFilterClick = fun(filterType: String){
                selectedFilterType.value = filterType
            }
            val setDefaultFilterType = fun(){
                val typeOrder = _uA(
                    "30",
                    "31",
                    "32",
                    "33",
                    "34",
                    "35"
                )
                for(type in resolveUTSValueIterator(typeOrder)){
                    if (getTypeCount(type) > 0) {
                        if (selectedFilterType.value == null) {
                            selectedFilterType.value = type
                        }
                        return
                    }
                }
                selectedFilterType.value = null
            }
            val goToTest = fun(item: followAlongItem2){
                uni_setStorageSync("testItem", item)
                if (_uA(
                    "34",
                    "35"
                ).includes(item.problemType)) {
                    uni_navigateTo(NavigateToOptions(url = "/pages/listening/speaking/test?currentType=" + item.problemType + "&title=" + encodeURIComponent(title.value) + "&type=" + type.value + "&studyTaskId=" + studyTaskId.value))
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/listening/listening/test?currentType=" + item.problemType + "&title=" + encodeURIComponent(title.value) + "&type=" + type.value + "&studyTaskId=" + studyTaskId.value))
            }
            val exercisePassScore = computed(fun(): Number {
                return getConfig("听力训练配置").getNumber("exercisePassScore") ?: 0
            }
            )
            fun gen_getList_fn() {
                uni_request<Result<UTSArray<followAlongItem2>>>(RequestOptions(url = getUrl("/biz/problem/api/list"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var textbookUnitId = getTextbookUnitId()
                    var subjectType = "英语"
                    var module = getModelKey("同步口语听力")
                    var subModule = getSubModelKey(if (title.value == "听力训练") {
                        "听力训练"
                    } else {
                        "口语训练"
                    }
                    )
                    var isExercise = "0"
                    var pageNum: Number = 1
                    var pageSize: Number = 3000
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    followAlongList.value = responseData.data!!
                    setDefaultFilterType()
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getList = ::gen_getList_fn
            fun gen_getSubjectStudyTask_fn() {
                uni_request<Result<UTSArray<followAlongItem2>>>(RequestOptions(url = getUrl("/biz/problem/api/studyTask/list"), method = "GET", data = object : UTSJSONObject() {
                    var type = "10"
                    var studyTaskId = studyTaskId.value
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    if (responseData.data!!.length == 0) {
                        uni_showToast(ShowToastOptions(title = "题目为空", icon = "none"))
                        return
                    }
                    followAlongList.value = responseData.data!!
                    setDefaultFilterType()
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getSubjectStudyTask = ::gen_getSubjectStudyTask_fn
            val isFinish = ref("")
            onLoad(fun(ev){
                title.value = ev["title"] ?: ""
                studyTaskId.value = parseInt(ev["studyTaskId"] ?: "0")
                type.value = parseInt(ev["type"] ?: "0")
                unitTitle.value = ev["unitTitle"] ?: ""
                isFinish.value = ev["isFinish"] ?: ""
            }
            )
            onPageShow(fun(){
                if (studyTaskId.value == 0) {
                    getList()
                } else {
                    getSubjectStudyTask()
                }
            }
            )
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to unref(title)), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_BackgroundImage, _uM("src" to "/static/ico/follow.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "title_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE("text", _uM("class" to "_text"), _tD(unref(unitTitle)), 1)
                                    )
                                }
                                ), "_" to 1))
                            )
                        }
                        ), "_" to 1), 8, _uA(
                            "title"
                        )),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "d_right"), _uA(
                                _cE("view", _uM("class" to "top_box"), _uA(
                                    if (getTypeCount("30") > 0) {
                                        _cE("view", _uM("key" to 0, "class" to _nC(_uA(
                                            "btn_box",
                                            _uM("active" to (unref(selectedFilterType) == "30"))
                                        )), "onClick" to fun(){
                                            handleFilterClick("30")
                                        }), _uA(
                                            _cE("image", _uM("src" to "/static/ico/listening/ico1.png", "style" to _nS(_uM("width" to "18.75rpx", "height" to "18.75rpx", "margin-right" to "4rpx"))), null, 4),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B"))), "听短对话选答案", 4),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B"))), "(" + _tD(getTypeCount("30")) + ")", 5)
                                        ), 10, _uA(
                                            "onClick"
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                    ,
                                    if (getTypeCount("31") > 0) {
                                        _cE("view", _uM("key" to 1, "class" to _nC(_uA(
                                            "btn_box",
                                            _uM("active" to (unref(selectedFilterType) == "31"))
                                        )), "onClick" to fun(){
                                            handleFilterClick("31")
                                        }), _uA(
                                            _cE("image", _uM("src" to "/static/ico/listening/ico2.png", "style" to _nS(_uM("width" to "18.75rpx", "height" to "18.75rpx", "margin-right" to "4rpx"))), null, 4),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B"))), "听长对话选答案", 4),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B"))), "(" + _tD(getTypeCount("31")) + ")", 5)
                                        ), 10, _uA(
                                            "onClick"
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                    ,
                                    if (getTypeCount("32") > 0) {
                                        _cE("view", _uM("key" to 2, "class" to _nC(_uA(
                                            "btn_box",
                                            _uM("active" to (unref(selectedFilterType) == "32"))
                                        )), "onClick" to fun(){
                                            handleFilterClick("32")
                                        }), _uA(
                                            _cE("image", _uM("src" to "/static/ico/listening/ico3.png", "style" to _nS(_uM("width" to "18.75rpx", "height" to "18.75rpx", "margin-right" to "4rpx"))), null, 4),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B"))), "听短文选答案", 4),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B"))), "(" + _tD(getTypeCount("32")) + ")", 5)
                                        ), 10, _uA(
                                            "onClick"
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                    ,
                                    if (getTypeCount("33") > 0) {
                                        _cE("view", _uM("key" to 3, "class" to _nC(_uA(
                                            "btn_box",
                                            _uM("active" to (unref(selectedFilterType) == "33"))
                                        )), "onClick" to fun(){
                                            handleFilterClick("33")
                                        }), _uA(
                                            _cE("image", _uM("src" to "/static/ico/listening/ico4.png", "style" to _nS(_uM("width" to "18.75rpx", "height" to "18.75rpx", "margin-right" to "4rpx"))), null, 4),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B"))), "听文章/看文章/填空", 4),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B"))), "(" + _tD(getTypeCount("33")) + ")", 5)
                                        ), 10, _uA(
                                            "onClick"
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                    ,
                                    if (getTypeCount("34") > 0) {
                                        _cE("view", _uM("key" to 4, "class" to _nC(_uA(
                                            "btn_box",
                                            _uM("active" to (unref(selectedFilterType) == "34"))
                                        )), "onClick" to fun(){
                                            handleFilterClick("34")
                                        }), _uA(
                                            _cE("image", _uM("src" to "/static/ico/listening/ico4.png", "style" to _nS(_uM("width" to "18.75rpx", "height" to "18.75rpx", "margin-right" to "4rpx"))), null, 4),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B"))), "听句子,复述句子", 4),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B"))), "(" + _tD(getTypeCount("34")) + ")", 5)
                                        ), 10, _uA(
                                            "onClick"
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                    ,
                                    if (getTypeCount("35") > 0) {
                                        _cE("view", _uM("key" to 5, "class" to _nC(_uA(
                                            "btn_box",
                                            _uM("active" to (unref(selectedFilterType) == "35"))
                                        )), "onClick" to fun(){
                                            handleFilterClick("35")
                                        }), _uA(
                                            _cE("image", _uM("src" to "/static/ico/listening/ico4.png", "style" to _nS(_uM("width" to "18.75rpx", "height" to "18.75rpx", "margin-right" to "4rpx"))), null, 4),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B"))), "听文章,复述文章", 4),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B"))), "(" + _tD(getTypeCount("35")) + ")", 5)
                                        ), 10, _uA(
                                            "onClick"
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                )),
                                _cE("scroll-view", _uM("class" to "scroll_box", "direction" to "vertical"), _uA(
                                    _cE("view", _uM("class" to "list_box"), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList((unref(filteredList) as UTSArray<followAlongItem2>), fun(item, index, __index, _cached): Any {
                                            return _cE("view", _uM("key" to index, "class" to _nC(_uA(
                                                "list_item",
                                                _uM("active" to (unref(isFinish) == "1" || (item as followAlongItem2).unitProgressPass == 1))
                                            )), "onClick" to fun(){
                                                goToTest(item as followAlongItem2)
                                            }
                                            ), _uA(
                                                _cE("view", _uM("class" to "item_content"), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("margin-bottom" to "6rpx")), "class" to "item_content_title"), _uA(
                                                        _cE("view", _uM("class" to "item_content_title_l"), _uA(
                                                            _cE("image", _uM("src" to unref(currentFilterTypeIcon), "style" to _nS(_uM("width" to "18.75rpx", "height" to "18.75rpx", "margin-right" to "4rpx"))), null, 12, _uA(
                                                                "src"
                                                            )),
                                                            _cE("text", _uM("class" to _nC(_uA(
                                                                "item_title",
                                                                _uM("active" to (unref(isFinish) == "1" || (item as followAlongItem2).unitProgressPass == 1))
                                                            ))), _tD(unref(currentFilterTypeText)), 3)
                                                        )),
                                                        _cE("text", _uM("class" to _nC(_uA(
                                                            "item_title_r",
                                                            _uM("active" to (unref(isFinish) == "1" || (item as followAlongItem2).unitProgressPass == 1))
                                                        ))), _tD(unref(rightInfo)), 3)
                                                    ), 4),
                                                    _cE("text", _uM("class" to _nC(_uA(
                                                        "item_subtitle",
                                                        _uM("active" to (unref(isFinish) == "1" || (item as followAlongItem2).unitProgressPass == 1))
                                                    ))), _tD(getQuestionContent(item)), 3)
                                                )),
                                                _cE("view", _uM("class" to "difficulty-wrap"), _uA(
                                                    _cE("text", _uM("class" to _nC(_uA(
                                                        "text",
                                                        _uM("active" to (unref(isFinish) == "1" || (item as followAlongItem2).unitProgressPass == 1))
                                                    ))), "难度：", 2),
                                                    _cV(unref(GenComponentsStarRatingStarRatingClass), _uM("value" to item.level, "maxStars" to 5, "size" to "9rpx", "spacing" to "3rpx"), null, 8, _uA(
                                                        "value"
                                                    ))
                                                ))
                                            ), 10, _uA(
                                                "onClick"
                                            ))
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "title_box" to _pS(_uM("width" to "234rpx", "height" to "28rpx", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "marginLeft" to "90rpx")), "_text" to _uM(".title_box " to _uM("textAlign" to "center", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#535D8C", "lineHeight" to "28rpx")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "marginTop" to "13.48rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "d_right" to _uM(".d_content " to _uM("flex" to 1, "width" to "100%", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx")), "top_box" to _uM(".d_content .d_right " to _uM("marginTop" to "12rpx", "marginRight" to 0, "marginBottom" to "12rpx", "marginLeft" to 0, "flexDirection" to "row", "paddingTop" to 0, "paddingRight" to "11rpx", "paddingBottom" to 0, "paddingLeft" to "11rpx")), "btn_box" to _uM(".d_content .d_right .top_box " to _uM("flexDirection" to "row", "alignItems" to "center", "marginRight" to "12rpx", "paddingTop" to "5rpx", "paddingRight" to "8rpx", "paddingBottom" to "5rpx", "paddingLeft" to "8rpx"), ".d_content .d_right .top_box .active" to _uM("backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "100rpx", "borderTopRightRadius" to "100rpx", "borderBottomRightRadius" to "100rpx", "borderBottomLeftRadius" to "100rpx")), "scroll_box" to _uM(".d_content .d_right " to _uM("height" to "266rpx", "paddingTop" to 0, "paddingRight" to "11rpx", "paddingBottom" to 0, "paddingLeft" to "11rpx")), "list_box" to _uM(".d_content .d_right .scroll_box " to _uM("flexDirection" to "row", "flexWrap" to "wrap", "marginRight" to "-7rpx", "marginBottom" to "-7rpx")), "list_item" to _uM(".d_content .d_right .scroll_box .list_box " to _uM("width" to "133rpx", "height" to "87rpx", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginRight" to "7rpx", "marginBottom" to "7rpx", "paddingTop" to "8rpx", "paddingRight" to "8rpx", "paddingBottom" to "8rpx", "paddingLeft" to "8rpx", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "display" to "flex", "flexDirection" to "column", "justifyContent" to "space-between"), ".d_content .d_right .scroll_box .list_box .active" to _uM("backgroundImage" to "none", "backgroundColor" to "#609CFF")), "difficulty-wrap" to _uM(".d_content .d_right .scroll_box .list_box .list_item " to _uM("flexDirection" to "row", "alignItems" to "center")), "text" to _uM(".d_content .d_right .scroll_box .list_box .list_item .difficulty-wrap " to _uM("fontSize" to "9rpx", "color" to "#7B7B7B"), ".d_content .d_right .scroll_box .list_box .list_item .difficulty-wrap .active" to _uM("color" to "#ffffff")), "difficulty_stars" to _uM(".d_content .d_right .scroll_box .list_box .list_item " to _uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to "4rpx")), "star" to _uM(".d_content .d_right .scroll_box .list_box .list_item .difficulty_stars " to _uM("width" to "9rpx", "height" to "9rpx", "marginRight" to "3rpx")), "item_content" to _uM(".d_content .d_right .scroll_box .list_box .list_item " to _uM("flex" to 1, "display" to "flex", "flexDirection" to "column")), "item_content_title" to _uM(".d_content .d_right .scroll_box .list_box .list_item .item_content " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "item_content_title_l" to _uM(".d_content .d_right .scroll_box .list_box .list_item .item_content .item_content_title " to _uM("flexDirection" to "row", "alignItems" to "center")), "item_title_r" to _uM(".d_content .d_right .scroll_box .list_box .list_item .item_content .item_content_title " to _uM("fontFamily" to "uni-icon", "fontSize" to "12rpx"), ".d_content .d_right .scroll_box .list_box .list_item .item_content .item_content_title .active" to _uM("color" to "#FFFFFF")), "item_title" to _uM(".d_content .d_right .scroll_box .list_box .list_item .item_content " to _uM("fontSize" to "10rpx", "fontWeight" to "bold", "color" to "#000000"), ".d_content .d_right .scroll_box .list_box .list_item .item_content .active" to _uM("color" to "#FFFFFF")), "item_subtitle" to _uM(".d_content .d_right .scroll_box .list_box .list_item .item_content " to _uM("fontSize" to "9rpx", "color" to "#7B7B7B", "textOverflow" to "ellipsis", "lines" to 2), ".d_content .d_right .scroll_box .list_box .list_item .item_content .active" to _uM("color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
