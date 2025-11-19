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
open class GenPagesWordInterReview : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesWordInterReview) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesWordInterReview
            val _cache = __ins.renderCache
            val countDownFn = countDown()
            val wordList = ref<UTSArray<reviewItem>?>(null)
            val nowItem = ref<reviewItem?>(null)
            val showItem = ref(false)
            val showStatus = ref(0)
            val timeTxt = ref("00:00:00")
            val showList = computed(fun(): UTSArray<reviewItem>? {
                return wordList.value
            }
            )
            watch(showItem.value, fun(kVal: Boolean){
                if (!kVal) {
                    countDownFn.clear()
                }
            }
            )
            countDownFn.onComplete(fun(str: String){
                timeTxt.value = str
            }
            )
            fun gen_showItemFn_fn(item: reviewItem) {
                countDownFn.clear()
                val parsedDate = Date(item.lastAutoReduceTime)
                parsedDate.setHours(parsedDate.getHours() + 8)
                countDownFn.initDate(parsedDate)
                countDownFn.start()
                nowItem.value = item
                showItem.value = true
            }
            val showItemFn = ::gen_showItemFn_fn
            val rightInfo = ref(null as UTSArray<modelItem>?)
            fun gen_getSubNum_fn(key: String): Number? {
                if (rightInfo.value == null) {
                    return 0
                }
                var _info = rightInfo.value as UTSArray<modelItem>
                val _infoF = _info.filter(fun(item: modelItem): Boolean {
                    return item.subModule == getSubModelKey(key)
                }
                )
                if (_infoF.length == 0) {
                    return 0
                }
                return _infoF[0].totalNum
            }
            val getSubNum = ::gen_getSubNum_fn
            fun getList(subModule: String = "") {
                uni_request<Result<UTSArray<reviewItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishProblemList"), method = "GET", data = _uO("textbookId" to getTextBookId(), "module" to getModelKey("同步单词"), "subModule" to subModule, "leMemoryScore" to 100, "pageSize" to 3000), header = getHeader(), success = fun(res){
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
            }
            watch(showStatus, fun(kVal: Number){
                val jumpUrlMap: UTSJSONObject = object : UTSJSONObject() {
                    var `0` = getSubModelKey("单词智能记忆")
                    var `1` = getSubModelKey("单词智能翻译")
                    var `2` = getSubModelKey("单词智能听说")
                    var `3` = getSubModelKey("单词智能听写")
                    var `4` = getSubModelKey("单词智能默写")
                    var `5` = getSubModelKey("单词识词辩义")
                    var `6` = getSubModelKey("生词本")
                }
                getList(jumpUrlMap.getString(kVal.toString(10)) ?: "")
            }
            , WatchOptions(immediate = true))
            fun gen_getModelList_fn() {
                uni_request<Result<UTSArray<modelItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishStat"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步单词")
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    rightInfo.value = responseData.data
                    val jumpUrlMap: UTSJSONObject = object : UTSJSONObject() {
                        var `0` = getSubModelKey("单词智能记忆")
                        var `1` = getSubModelKey("单词智能翻译")
                        var `2` = getSubModelKey("单词智能听说")
                        var `3` = getSubModelKey("单词智能听写")
                        var `4` = getSubModelKey("单词智能默写")
                        var `5` = getSubModelKey("单词识词辩义")
                        var `6` = getSubModelKey("生词本")
                    }
                    getList(jumpUrlMap.getString(showStatus.value.toString(10)) ?: "")
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getModelList = ::gen_getModelList_fn
            fun gen_startReview_fn() {
                if (showList.value?.length == 0) {
                    return
                }
                val jumpUrlMap: UTSJSONObject = object : UTSJSONObject() {
                    var `0` = "/pages/wordInter/learning/learning?isReview=1"
                    var `1` = "/pages/wordInter/translate/translate?isReview=1"
                    var `2` = "/pages/wordInter/followAlong/followAlong?isReview=1"
                    var `3` = "/pages/wordInter/dictation/dictation?isReview=1"
                    var `4` = "/pages/wordInter/silently/silently?isReview=1"
                    var `5` = "/pages/wordInter/chinese/chinese?isReview=1"
                    var `6` = "/pages/wordInter/words/words?isReview=1"
                }
                var _url = jumpUrlMap.getString(showStatus.value.toString(10))
                if (_url == null) {
                    return
                }
                uni_navigateTo(NavigateToOptions(url = _url))
            }
            val startReview = ::gen_startReview_fn
            onPageShow(fun(){
                getModelList()
            }
            )
            onReady(fun(){
                setScreen()
            }
            )
            onLoad(fun(ev){
                showStatus.value = parseInt(ev["tab"] ?: "0")
            }
            )
            onBeforeUnmount(fun(){
                countDownFn.clear()
            }
            )
            return fun(): Any? {
                val _component_dTitle = resolveEasyComponent("dTitle", GenComponentsDTitleDTitleClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to ""), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_dTitle, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        " 智能复习 "
                                    )
                                }
                                ), "_" to 1))
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "d_left"), _uA(
                                _cE("view", _uM("class" to "d_top"), _uA(
                                    _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1")), "direction" to "horizontal"), _uA(
                                        _cE("view", _uM("class" to "d_top_btn_group", "style" to _nS(_uM("width" to "800rpx"))), _uA(
                                            _cE("view", _uM("class" to "d_top_btn", "onClick" to fun(){
                                                showStatus.value = 0
                                            }
                                            ), _uA(
                                                _cE("image", _uM("src" to if (unref(showStatus) == 0) {
                                                    "/static/ico/review/ico1_active.png"
                                                } else {
                                                    "/static/ico/review/ico1.png"
                                                }
                                                , "style" to _nS(_uM("width" to "13.48rpx", "height" to "13.48rpx"))), null, 12, _uA(
                                                    "src"
                                                )),
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "d_top_btn_text",
                                                    _uM("d_top_btn_text_active" to (unref(showStatus) == 0))
                                                ))), "智能记忆", 2),
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "d_top_btn_text",
                                                    _uM("d_top_btn_text_num_active" to (unref(showStatus) == 0))
                                                ))), _tD(getSubNum("单词智能记忆")), 3)
                                            ), 8, _uA(
                                                "onClick"
                                            )),
                                            _cE("view", _uM("class" to "d_top_btn", "onClick" to fun(){
                                                showStatus.value = 1
                                            }
                                            ), _uA(
                                                _cE("image", _uM("src" to if (unref(showStatus) == 1) {
                                                    "/static/ico/review/ico2_active.png"
                                                } else {
                                                    "/static/ico/review/ico2.png"
                                                }
                                                , "style" to _nS(_uM("width" to "11.72rpx", "height" to "11.04rpx"))), null, 12, _uA(
                                                    "src"
                                                )),
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "d_top_btn_text",
                                                    _uM("d_top_btn_text_active" to (unref(showStatus) == 1))
                                                ))), "智能翻译", 2),
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "d_top_btn_text",
                                                    _uM("d_top_btn_text_num_active" to (unref(showStatus) == 1))
                                                ))), _tD(getSubNum("单词智能翻译")), 3)
                                            ), 8, _uA(
                                                "onClick"
                                            )),
                                            _cE("view", _uM("class" to "d_top_btn", "onClick" to fun(){
                                                showStatus.value = 2
                                            }
                                            ), _uA(
                                                _cE("image", _uM("src" to if (unref(showStatus) == 2) {
                                                    "/static/ico/review/ico3_active.png"
                                                } else {
                                                    "/static/ico/review/ico3.png"
                                                }
                                                , "style" to _nS(_uM("width" to "11.72rpx", "height" to "11.72rpx"))), null, 12, _uA(
                                                    "src"
                                                )),
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "d_top_btn_text",
                                                    _uM("d_top_btn_text_active" to (unref(showStatus) == 2))
                                                ))), "智能听说", 2),
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "d_top_btn_text",
                                                    _uM("d_top_btn_text_num_active" to (unref(showStatus) == 2))
                                                ))), _tD(getSubNum("单词智能听说")), 3)
                                            ), 8, _uA(
                                                "onClick"
                                            )),
                                            _cE("view", _uM("class" to "d_top_btn", "onClick" to fun(){
                                                showStatus.value = 3
                                            }
                                            ), _uA(
                                                _cE("image", _uM("src" to if (unref(showStatus) == 3) {
                                                    "/static/ico/review/ico4_active.png"
                                                } else {
                                                    "/static/ico/review/ico4.png"
                                                }
                                                , "style" to _nS(_uM("width" to "11.72rpx", "height" to "12.31rpx"))), null, 12, _uA(
                                                    "src"
                                                )),
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "d_top_btn_text",
                                                    _uM("d_top_btn_text_active" to (unref(showStatus) == 3))
                                                ))), "智能听写", 2),
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "d_top_btn_text",
                                                    _uM("d_top_btn_text_num_active" to (unref(showStatus) == 3))
                                                ))), _tD(getSubNum("单词智能听写")), 3)
                                            ), 8, _uA(
                                                "onClick"
                                            )),
                                            _cE("view", _uM("class" to "d_top_btn", "onClick" to fun(){
                                                showStatus.value = 4
                                            }
                                            ), _uA(
                                                _cE("image", _uM("src" to if (unref(showStatus) == 4) {
                                                    "/static/ico/review/ico5_active.png"
                                                } else {
                                                    "/static/ico/review/ico5.png"
                                                }
                                                , "style" to _nS(_uM("width" to "11.72rpx", "height" to "11.12rpx"))), null, 12, _uA(
                                                    "src"
                                                )),
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "d_top_btn_text",
                                                    _uM("d_top_btn_text_active" to (unref(showStatus) == 4))
                                                ))), "智能默写", 2),
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "d_top_btn_text",
                                                    _uM("d_top_btn_text_num_active" to (unref(showStatus) == 4))
                                                ))), _tD(getSubNum("单词智能默写")), 3)
                                            ), 8, _uA(
                                                "onClick"
                                            )),
                                            _cE("view", _uM("class" to "d_top_btn", "onClick" to fun(){
                                                showStatus.value = 5
                                            }
                                            ), _uA(
                                                _cE("image", _uM("src" to if (unref(showStatus) == 5) {
                                                    "/static/ico/review/ico6_active.png"
                                                } else {
                                                    "/static/ico/review/ico6.png"
                                                }
                                                , "style" to _nS(_uM("width" to "10.55rpx", "height" to "12.31rpx"))), null, 12, _uA(
                                                    "src"
                                                )),
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "d_top_btn_text",
                                                    _uM("d_top_btn_text_active" to (unref(showStatus) == 5))
                                                ))), "识词辩义 ", 2),
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "d_top_btn_text",
                                                    _uM("d_top_btn_text_num_active" to (unref(showStatus) == 5))
                                                ))), _tD(getSubNum("单词识词辩义")), 3)
                                            ), 8, _uA(
                                                "onClick"
                                            )),
                                            _cE("view", _uM("class" to "d_top_btn", "onClick" to fun(){
                                                showStatus.value = 6
                                            }
                                            ), _uA(
                                                _cE("image", _uM("src" to if (unref(showStatus) == 6) {
                                                    "/static/ico/review/ico6_active.png"
                                                } else {
                                                    "/static/ico/review/ico6.png"
                                                }
                                                , "style" to _nS(_uM("width" to "10.55rpx", "height" to "12.31rpx"))), null, 12, _uA(
                                                    "src"
                                                )),
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "d_top_btn_text",
                                                    _uM("d_top_btn_text_active" to (unref(showStatus) == 6))
                                                ))), "生词本 ", 2),
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "d_top_btn_text",
                                                    _uM("d_top_btn_text_num_active" to (unref(showStatus) == 6))
                                                ))), _tD(getSubNum("生词本")), 3)
                                            ), 8, _uA(
                                                "onClick"
                                            ))
                                        ), 4)
                                    ), 4)
                                )),
                                _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1", "margin" to "30rpx", "padding-bottom" to "-30rpx")), "direction" to "vertical"), _uA(
                                    _cE("view", null, _uA(
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "flex-wrap" to "wrap"))), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(showList), fun(item, __key, __index, _cached): Any {
                                                return _cE("text", _uM("class" to _nC(_uA(
                                                    "_text",
                                                    _uM("lever3" to (item.memoryLevel == 3), "lever2" to (item.memoryLevel == 2), "lever1" to (item.memoryLevel == 1))
                                                )), "onClick" to fun(){
                                                    showItemFn(item)
                                                }
                                                ), _tD(item?.englishText), 11, _uA(
                                                    "onClick"
                                                ))
                                            }
                                            ), 256)
                                        ), 4)
                                    ))
                                ), 4),
                                _cE("view", _uM("style" to _nS(_uM("align-items" to "center"))), _uA(
                                    _cE("text", _uM("class" to "btn", "onClick" to startReview), "智能复习")
                                ), 4)
                            ))
                        )),
                        if (isTrue(unref(showItem))) {
                            _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("view", _uM("style" to _nS(_uM("width" to "100%", "height" to "100%", "justify-content" to "center", "align-items" to "center"))), _uA(
                                        _cE("view", _uM("class" to "review_pop_box"), _uA(
                                            _cE("text", _uM("class" to "top_title"), "单词卡"),
                                            _cE("view", _uM("class" to "pop_content"), _uA(
                                                _cE("image", _uM("src" to "/static/ico/close.png", "mode" to "", "style" to _nS(_uM("width" to "17.58rpx", "height" to "17.58rpx", "position" to "absolute", "right" to "16.44rpx", "top" to "9.38rpx")), "onClick" to fun(){
                                                    showItem.value = false
                                                }), null, 12, _uA(
                                                    "onClick"
                                                )),
                                                _cE("view", _uM("class" to "pop_content_top"), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "width" to "100%", "justify-content" to "space-between"))), _uA(
                                                        _cE("view", null, _uA(
                                                            _cE("text", _uM("class" to "pop_content_top_title"), _tD(unref(nowItem)?.englishText), 1),
                                                            _cE("text", _uM("style" to _nS(_uM("color" to "#8D8D8D", "font-size" to "14.06rpx", "margin-top" to "8.79rpx"))), _tD(unref(nowItem)?.phoneticSymbol), 5)
                                                        )),
                                                        _cE("view", null, _uA(
                                                            _cV(_component_u_playMp3, _uM("src" to unref(nowItem)?.soundUrl, "tplType" to 4), null, 8, _uA(
                                                                "src"
                                                            ))
                                                        ))
                                                    ), 4),
                                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "margin-top" to "8rpx", "margin-bottom" to "15rpx"))), _uA(
                                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "padding" to "3rpx 7rpx", "border-radius" to "999rpx", "background-color" to "#EFF2FF", "align-items" to "flex-end", "margin-right" to "15rpx"))), _uA(
                                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "15.23rpx", "color" to "#6B85AF"))), "学习", 4),
                                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "21.09rpx", "color" to "#6B85AF", "margin-bottom" to "-3rpx"))), _tD(unref(nowItem)?.learnNum), 5),
                                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "15.23rpx", "color" to "#6B85AF"))), "次", 4)
                                                        ), 4),
                                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "padding" to "3rpx 7rpx", "border-radius" to "999rpx", "background-color" to "#DAF9C3", "align-items" to "flex-end", "margin-right" to "15rpx"))), _uA(
                                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "15.23rpx", "color" to "#5A9F32"))), "答错", 4),
                                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "21.09rpx", "color" to "#5A9F32", "margin-bottom" to "-3rpx"))), _tD(unref(nowItem)?.answerErrorNum), 5),
                                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "15.23rpx", "color" to "#5A9F32"))), "次", 4)
                                                        ), 4),
                                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "padding" to "3rpx 7rpx", "border-radius" to "999rpx", "background-color" to "#FFEBCD", "align-items" to "flex-end"))), _uA(
                                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "15.23rpx", "color" to "#D99024"))), "记忆强度", 4),
                                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "21.09rpx", "color" to "#D99024", "margin-bottom" to "-3rpx"))), _tD(unref(nowItem)?.currentMemoryScore), 5)
                                                        ), 4)
                                                    ), 4)
                                                )),
                                                _cE("view", _uM("style" to _nS(_uM("background-color" to "#FFF6EA", "flex" to "1", "width" to "100%", "align-items" to "center", "justify-content" to "space-between", "padding" to "14rpx 0"))), _uA(
                                                    _cE("text", _uM("style" to _nS(_uM("color" to "#FA9600", "font-size" to "14.06rpx", "line-height" to "17.58rpx"))), "记忆强化中！ 请在8小时内复习，避免扣分哦！", 4),
                                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                        _cE("text", _uM("style" to _nS(_uM("color" to "#FA9600", "font-size" to "14.06rpx", "line-height" to "17.58rpx"))), "还有", 4),
                                                        _cE("text", _uM("style" to _nS(_uM("color" to "#FA9600", "font-size" to "23.44rpx", "letter-spacing" to "12px", "font-weight" to "bold"))), _tD(unref(timeTxt)), 5)
                                                    ), 4)
                                                ), 4)
                                            ))
                                        ))
                                    ), 4)
                                )
                            }), "_" to 1))
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "marginTop" to "13.48rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx")), "d_left" to _uM(".d_content " to _uM("height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginRight" to "5.86rpx", "paddingTop" to "17.58rpx", "paddingRight" to "9.96rpx", "paddingBottom" to "17.58rpx", "paddingLeft" to "9.96rpx")), "d_top_btn_group" to _uM(".d_content " to _uM("flexDirection" to "row")), "d_top_btn" to _uM(".d_content " to _uM("marginLeft" to "5.27rpx", "width" to "105.47rpx", "height" to "24.61rpx", "borderTopLeftRadius" to "21rpx", "borderTopRightRadius" to "21rpx", "borderBottomRightRadius" to "21rpx", "borderBottomLeftRadius" to "21rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "backgroundImage" to "linear-gradient(to bottom, #F3F7FF, #D5DFF0)", "backgroundColor" to "rgba(0,0,0,0)")), "d_top_btn_text" to _uM(".d_content .d_top_btn " to _uM("fontWeight" to "400", "fontSize" to "12rpx", "color" to "#98A6EE", "marginLeft" to "5.72rpx")), "d_top_btn_text_active" to _uM(".d_content .d_top_btn " to _uM("color" to "#3A58EB", "fontWeight" to "bold")), "d_top_btn_text_num_active" to _uM(".d_content .d_top_btn " to _uM("color" to "#FDAD35", "fontWeight" to "bold")), "d_top_btn_ico" to _uM(".d_content .d_top_btn " to _uM("width" to "9.4rpx", "height" to "9.4rpx", "marginRight" to "4.1rpx")), "_text" to _uM(".d_content " to _uM("fontWeight" to "700", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "18rpx", "marginRight" to "20rpx", "marginBottom" to "30rpx", "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#3D3D3D"), ".d_content .lever3" to _uM("color" to "#FF0000", "borderBottomColor" to "#FF0000"), ".d_content .lever2" to _uM("color" to "#FAA739", "borderBottomColor" to "#FAA739"), ".d_content .lever1" to _uM("color" to "#008000", "borderBottomColor" to "#008000")), "btn" to _uM(".d_content " to _uM("width" to "100.78rpx", "height" to "35.16rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "fontSize" to "14rpx", "lineHeight" to "35.16rpx", "textAlign" to "center", "color" to "#ffffff", "borderTopLeftRadius" to "35rpx", "borderTopRightRadius" to "35rpx", "borderBottomRightRadius" to "35rpx", "borderBottomLeftRadius" to "35rpx")), "review_pop_box" to _pS(_uM("width" to "456.45rpx", "height" to "258.4rpx", "alignItems" to "center")), "top_title" to _uM(".review_pop_box " to _uM("width" to "134.77rpx", "height" to "45.12rpx", "backgroundImage" to "linear-gradient(to bottom, #FAA739, #F45425)", "backgroundColor" to "rgba(0,0,0,0)", "fontSize" to "23.44rpx", "lineHeight" to "45.12rpx", "textAlign" to "center", "color" to "#ffffff", "borderTopLeftRadius" to "4rpx", "borderTopRightRadius" to "4rpx", "borderBottomRightRadius" to "10rpx", "borderBottomLeftRadius" to "10rpx", "position" to "relative", "zIndex" to 9)), "pop_content" to _uM(".review_pop_box " to _uM("marginTop" to "-31.64rpx", "width" to "456rpx", "height" to "245rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "position" to "relative")), "pop_content_top" to _uM(".review_pop_box .pop_content " to _uM("marginTop" to "47.46rpx", "paddingTop" to 0, "paddingRight" to "23rpx", "paddingBottom" to 0, "paddingLeft" to "23rpx")), "pop_content_top_title" to _uM(".review_pop_box .pop_content .pop_content_top " to _uM("fontWeight" to "700", "fontSize" to "23rpx", "color" to "#3D3D3D")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
