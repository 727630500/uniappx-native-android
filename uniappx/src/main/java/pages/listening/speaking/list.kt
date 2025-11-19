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
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesListeningSpeakingList : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesListeningSpeakingList) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesListeningSpeakingList
            val _cache = __ins.renderCache
            val rightInfo = ref("\uE600")
            val wordList = ref<UTSArray<teachingMaterial>?>(null)
            val wordListEnd = ref<UTSArray<teachingMaterial>?>(null)
            val title = ref("")
            val wordListNotEnd = ref<UTSArray<teachingMaterial>?>(null)
            val showStatus = ref(0)
            val showList = computed(fun(): UTSArray<teachingMaterial> {
                if (showStatus.value == 1) {
                    return wordListEnd.value ?: _uA()
                }
                if (showStatus.value == 2) {
                    return wordListNotEnd.value ?: _uA()
                }
                return wordList.value ?: _uA()
            }
            )
            val exercisePassScore = computed(fun(): Number {
                return getConfig("单词智能听写配置").getNumber("exercisePassScore") ?: 0
            }
            )
            fun gen_getList_fn() {
                uni_request<Result<UTSArray<teachingMaterial>>>(RequestOptions(url = getUrl("/biz/textbookUnit/api/list"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步单词")
                    var subModule = getSubModelKey("单词智能听写")
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
                    wordList.value = responseData.data
                    if (wordList.value != null) {
                        val _val = wordList.value!!
                        wordListEnd.value = _val?.filter(fun(item: teachingMaterial): Boolean {
                            return item.englishProgress.isFinish == "1"
                        }
                        )
                        wordListNotEnd.value = _val?.filter(fun(item: teachingMaterial): Boolean {
                            return item.englishProgress.isFinish == "0"
                        }
                        )
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getList = ::gen_getList_fn
            fun gen_startStudy_fn(item: teachingMaterial) {
                var _englishCurrentProgressValue = if (item.englishProgress.englishCurrentProgressValue != item.englishProgress.englishTotalProgressValue) {
                    item.englishProgress.englishCurrentProgressValue
                } else {
                    0
                }
                setTextbookUnitId(item.id)
                uni_navigateTo(NavigateToOptions(url = "/pages/wordInter/dictation/dictation?englishCurrentProgressValue=" + _englishCurrentProgressValue + "&englishTotalProgressValue=" + item.englishProgress.englishTotalProgressValue + "&unitTitle=" + item.unitName))
            }
            val startStudy = ::gen_startStudy_fn
            fun gen_startExam_fn(item: teachingMaterial) {
                var _englishCurrentProgressValue = if (item.englishProgress.englishCurrentProgressValue != item.englishProgress.englishTotalProgressValue) {
                    item.englishProgress.englishCurrentProgressValue
                } else {
                    0
                }
                setTextbookUnitId(item.id)
                uni_navigateTo(NavigateToOptions(url = "/pages/wordInter/dictation/test?unitTitle=" + item.unitName))
            }
            val startExam = ::gen_startExam_fn
            onLoad(fun(ev){
                title.value = ev["title"] ?: ""
            }
            )
            onPageShow(fun(){
                getList()
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
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "听力训练"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_LearningInfo)
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "d_right"), _uA(
                                _cE("view", _uM("class" to "top_box"), _uA(
                                    _cE("view", _uM("class" to "btn_box active"), _uA(
                                        _cE("image", _uM("src" to "/static/ico/listening/ico1.png", "style" to _nS(_uM("width" to "18.75rpx", "height" to "18.75rpx"))), null, 4),
                                        _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "margin-left" to "5rpx"))), "听短对话选答案(124)", 4)
                                    )),
                                    _cE("view", _uM("class" to "btn_box"), _uA(
                                        _cE("image", _uM("src" to "/static/ico/listening/ico2.png", "style" to _nS(_uM("width" to "18.75rpx", "height" to "18.75rpx"))), null, 4),
                                        _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "margin-left" to "5rpx"))), "听长对话选答案(24)", 4)
                                    )),
                                    _cE("view", _uM("class" to "btn_box"), _uA(
                                        _cE("image", _uM("src" to "/static/ico/listening/ico3.png", "style" to _nS(_uM("width" to "18.75rpx", "height" to "18.75rpx"))), null, 4),
                                        _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "margin-left" to "5rpx"))), "听短文选答案(16)", 4)
                                    )),
                                    _cE("view", _uM("class" to "btn_box"), _uA(
                                        _cE("image", _uM("src" to "/static/ico/listening/ico4.png", "style" to _nS(_uM("width" to "18.75rpx", "height" to "18.75rpx"))), null, 4),
                                        _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "margin-left" to "5rpx"))), "听文章/看文章/填空(9)", 4)
                                    ))
                                )),
                                _cE("scroll-view", _uM("class" to "scroll_box", "direction" to "vertical"), _uA(
                                    _cE("view", _uM("class" to "list_box"), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(20, fun(i, __key, __index, _cached): Any {
                                            return _cE("view", _uM("class" to _nC(_uA(
                                                "list_item",
                                                _uM("active" to false)
                                            )), "key" to i), _uA(
                                                _cE("view", _uM("class" to "item_content"), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("margin-bottom" to "6rpx")), "class" to "item_content_title"), _uA(
                                                        _cE("view", _uM("class" to "item_content_title_l"), _uA(
                                                            _cE("image", _uM("src" to "/static/ico/listening/ico2.png", "style" to _nS(_uM("width" to "18.75rpx", "height" to "18.75rpx"))), null, 4),
                                                            _cE("text", _uM("class" to "item_title"), "听短对话选答案")
                                                        )),
                                                        _cE("text", _uM("class" to "item_title_r"), _tD(unref(rightInfo)), 1)
                                                    ), 4),
                                                    _cE("text", _uM("class" to "item_subtitle"), "题干：听以下对话，选择正确的答案。")
                                                )),
                                                _cE("view", _uM("class" to "difficulty_stars"), _uA(
                                                    _cE("image", _uM("src" to "/static/ico/armmar_stat.png", "class" to "star")),
                                                    _cE("image", _uM("src" to "/static/ico/armmar_stat.png", "class" to "star")),
                                                    _cE("image", _uM("src" to "/static/ico/armmar_stat.png", "class" to "star")),
                                                    _cE("image", _uM("src" to "/static/ico/armmar_stat_fail.png", "class" to "star")),
                                                    _cE("image", _uM("src" to "/static/ico/armmar_stat_fail.png", "class" to "star"))
                                                ))
                                            ), 2)
                                        }
                                        ), 64)
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "marginTop" to "13.48rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "d_right" to _uM(".d_content " to _uM("flex" to 1, "width" to "100%", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx")), "top_box" to _uM(".d_content .d_right " to _uM("marginTop" to "12rpx", "marginRight" to 0, "marginBottom" to "12rpx", "marginLeft" to 0, "flexDirection" to "row", "paddingTop" to 0, "paddingRight" to "11rpx", "paddingBottom" to 0, "paddingLeft" to "11rpx")), "btn_box" to _uM(".d_content .d_right .top_box " to _uM("flexDirection" to "row", "alignItems" to "center", "marginRight" to "20rpx"), ".d_content .d_right .top_box .active" to _uM("backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "100rpx", "borderTopRightRadius" to "100rpx", "borderBottomRightRadius" to "100rpx", "borderBottomLeftRadius" to "100rpx", "paddingTop" to "5rpx", "paddingRight" to "8rpx", "paddingBottom" to "5rpx", "paddingLeft" to "8rpx")), "scroll_box" to _uM(".d_content .d_right " to _uM("height" to "266rpx", "paddingTop" to 0, "paddingRight" to "11rpx", "paddingBottom" to 0, "paddingLeft" to "11rpx")), "list_box" to _uM(".d_content .d_right .scroll_box " to _uM("flexDirection" to "row", "flexWrap" to "wrap", "marginRight" to "-7rpx", "marginBottom" to "-7rpx")), "list_item" to _uM(".d_content .d_right .scroll_box .list_box " to _uM("width" to "133rpx", "height" to "87rpx", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginRight" to "7rpx", "marginBottom" to "7rpx", "paddingTop" to "8rpx", "paddingRight" to "8rpx", "paddingBottom" to "8rpx", "paddingLeft" to "8rpx", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "display" to "flex", "flexDirection" to "column", "justifyContent" to "space-between"), ".d_content .d_right .scroll_box .list_box .active" to _uM("backgroundImage" to "none", "backgroundColor" to "#609CFF")), "difficulty_stars" to _uM(".d_content .d_right .scroll_box .list_box .list_item " to _uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to "4rpx")), "star" to _uM(".d_content .d_right .scroll_box .list_box .list_item .difficulty_stars " to _uM("width" to "9rpx", "height" to "9rpx", "marginRight" to "3rpx")), "item_content" to _uM(".d_content .d_right .scroll_box .list_box .list_item " to _uM("flex" to 1, "display" to "flex", "flexDirection" to "column")), "item_content_title" to _uM(".d_content .d_right .scroll_box .list_box .list_item .item_content " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "item_content_title_l" to _uM(".d_content .d_right .scroll_box .list_box .list_item .item_content .item_content_title " to _uM("flexDirection" to "row", "alignItems" to "center")), "item_title_r" to _uM(".d_content .d_right .scroll_box .list_box .list_item .item_content .item_content_title " to _uM("fontFamily" to "uni-icon", "fontSize" to "16rpx")), "item_title" to _uM(".d_content .d_right .scroll_box .list_box .list_item .item_content " to _uM("fontSize" to "12rpx", "fontWeight" to "bold", "color" to "#000000", "marginBottom" to "2rpx"), ".d_content .d_right .scroll_box .list_box .list_item.active " to _uM("color" to "#FFFFFF")), "item_subtitle" to _uM(".d_content .d_right .scroll_box .list_box .list_item .item_content " to _uM("fontSize" to "9rpx", "color" to "#7B7B7B"), ".d_content .d_right .scroll_box .list_box .list_item.active " to _uM("color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
