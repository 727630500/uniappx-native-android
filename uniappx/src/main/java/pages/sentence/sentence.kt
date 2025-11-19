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
open class GenPagesSentenceSentence : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSentenceSentence) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSentenceSentence
            val _cache = __ins.renderCache
            val rightInfo = ref(null as UTSArray<modelItem>?)
            val leftList = ref(_uA<leftItem1>(leftItem1(title = "同步句听说", ico = "/static/ico/sentence/ico1.png", _style = "width: 82.75rpx;height: 77.48rpx", totalProgressValue = "0", currentProgressValue = "0", percentage = 0, path = "/pages/sentence/hear", isWidth = true), leftItem1(title = "同步句听力", ico = "/static/ico/sentence/ico2.png", _style = "width: 75.38rpx;height: 75.38rpx", totalProgressValue = "0", currentProgressValue = "0", percentage = 0, path = "/pages/sentence/hearing", isWidth = false), leftItem1(title = "同步句翻译", ico = "/static/ico/sentence/ico3.png", _style = "width: 92rpx;height: 62rpx", totalProgressValue = "0", currentProgressValue = "0", percentage = 0, path = "/pages/sentence/translate", isWidth = false), leftItem1(title = "同步句默写", ico = "/static/ico/sentence/ico4.png", _style = "width: 72.66rpx;height: 72.66rpx", totalProgressValue = "0", currentProgressValue = "0", percentage = 0, path = "/pages/sentence/writeSilently", isWidth = false), leftItem1(title = "同步句听写", ico = "/static/ico/sentence/ico5.png", _style = "width: 78.27rpx;height: 78.27rpx", totalProgressValue = "0", currentProgressValue = "0", percentage = 0, path = "/pages/sentence/dictation", isWidth = false)))
            fun gen_goTest_fn() {
                uni_navigateTo(NavigateToOptions(url = "/pages/sentence/review"))
            }
            val goTest = ::gen_goTest_fn
            val redNum = ref(0)
            fun gen_getModelList_fn() {
                if (getTextBookId() == 0) {
                    return
                }
                uni_request<Result<UTSArray<modelItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishStat"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步句子")
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
                    if (rightInfo.value != null) {
                        var _arr = rightInfo.value as UTSArray<modelItem>
                        var _num: Number = 0
                        _arr.forEach(fun(item: modelItem){
                            _num += (item.totalNum as Number)
                        }
                        )
                        redNum.value = _num
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getModelList = ::gen_getModelList_fn
            fun gen_getModeProgresslList_fn() {
                if (getTextBookId() == 0) {
                    return
                }
                uni_request<Result<_progress1>>(RequestOptions(url = getUrl("/biz/textbook/api/getEnglishModuleProgress"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步句子")
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    if (responseData.data != null) {
                        var _data = responseData.data as _progress1
                        var _arr = _data.subModuleProgressList as UTSArray<progressItem>
                        _arr.forEach(fun(item: progressItem, index: Number){
                            var _percentage = (parseInt(item.currentProgressValue) / parseInt(item.totalProgressValue)) * 100
                            if (isNaN(_percentage)) {
                                _percentage = 0
                            }
                            if (item.subModule == getSubModelKey("同步句子听说")) {
                                leftList.value[0].percentage = _percentage
                                leftList.value[0].currentProgressValue = item.currentProgressValue
                                leftList.value[0].totalProgressValue = item.totalProgressValue
                            }
                            if (item.subModule == getSubModelKey("同步句子听力")) {
                                leftList.value[1].percentage = _percentage
                                leftList.value[1].currentProgressValue = item.currentProgressValue
                                leftList.value[1].totalProgressValue = item.totalProgressValue
                            }
                            if (item.subModule == getSubModelKey("同步句子翻译")) {
                                leftList.value[2].percentage = _percentage
                                leftList.value[2].currentProgressValue = item.currentProgressValue
                                leftList.value[2].totalProgressValue = item.totalProgressValue
                            }
                            if (item.subModule == getSubModelKey("同步句子默写")) {
                                leftList.value[3].percentage = _percentage
                                leftList.value[3].currentProgressValue = item.currentProgressValue
                                leftList.value[3].totalProgressValue = item.totalProgressValue
                            }
                            if (item.subModule == getSubModelKey("同步句子听写")) {
                                leftList.value[4].percentage = _percentage
                                leftList.value[4].currentProgressValue = item.currentProgressValue
                                leftList.value[4].totalProgressValue = item.totalProgressValue
                            }
                        }
                        )
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getModeProgresslList = ::gen_getModeProgresslList_fn
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
            val goPath = fun(path: String){
                uni_navigateTo(NavigateToOptions(url = path, fail = fun(_) {
                    console.log()
                }
                ))
            }
            onPageShow(fun(){
                getModelList()
                getModeProgresslList()
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
                val _component_progress = resolveComponent("progress")
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "sync_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "同步句子"), _uM("right" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cE("view", _uM("class" to "nav-r", "onClick" to fun(){
                                    goPath("/pages/sentenceBooks/sentenceBooks")
                                }
                                ), _uA(
                                    _cE("image", _uM("src" to "/static/ico/sentence/sentence_ico.png", "style" to _nS(_uM("width" to "23.44rpx", "height" to "25.78rpx")), "mode" to ""), null, 4),
                                    _cE("text", _uM("class" to "word_text"), "同步句本")
                                ), 8, _uA(
                                    "onClick"
                                ))
                            )
                        }
                        ), "default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_LearningInfo),
                                _cV(_component_ChangeGrade)
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "sync_content"), _uA(
                            _cE("view", _uM("class" to "module-container"), _uA(
                                _cE(Fragment, null, RenderHelpers.renderList(unref(leftList), fun(item, __key, __index, _cached): Any {
                                    return _cE("view", _uM("class" to "module-item", "style" to _nS(if (item.isWidth) {
                                        "width: 282.42rpx;"
                                    } else {
                                        ""
                                    }
                                    ), "onClick" to fun(){
                                        goPath(item.path)
                                    }
                                    ), _uA(
                                        _cE("view", _uM("class" to "module-content"), _uA(
                                            _cE("text", _uM("class" to "module-title"), _tD(item.title), 1),
                                            _cE("image", _uM("style" to _nS(item._style), "src" to item.ico), null, 12, _uA(
                                                "src"
                                            )),
                                            _cE("view", _uM("class" to "progress-box"), _uA(
                                                _cE("text", _uM("class" to "progress-text"), _tD(item.currentProgressValue) + "/" + _tD(item.totalProgressValue), 1),
                                                _cV(_component_progress, _uM("border-radius" to 100, "percent" to item.percentage, "stroke-width" to 6, "activeColor" to "#5B77FF", "backgroundColor" to "#E2E6F6"), null, 8, _uA(
                                                    "percent"
                                                ))
                                            ))
                                        ))
                                    ), 12, _uA(
                                        "onClick"
                                    ))
                                }
                                ), 256)
                            )),
                            _cE("view", _uM("class" to "sync-right"), _uA(
                                _cE("text", _uM("class" to "title"), "智能复习"),
                                _cE("view", _uM("class" to "num_list"), _uA(
                                    _cV(_component_BackgroundImage, _uM("src" to "/static/ico/word/word_r1.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "num_item1"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "29rpx", "color" to "#fff", "font-weight" to "bold"))), _tD(getSubNum("同步句子听力")), 5),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#fff"))), "听力", 4)
                                        )
                                    }
                                    ), "_" to 1)),
                                    _cV(_component_BackgroundImage, _uM("src" to "/static/ico/word/word_r4.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "num_item4"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "27rpx", "color" to "#fff", "font-weight" to "bold"))), _tD(getSubNum("同步句子翻译")), 5),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#fff"))), "翻译", 4)
                                        )
                                    }
                                    ), "_" to 1)),
                                    _cV(_component_BackgroundImage, _uM("src" to "/static/ico/word/word_r5.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "num_item5"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "26rpx", "color" to "#fff", "font-weight" to "bold"))), _tD(getSubNum("同步句子听写")), 5),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#fff"))), "听写", 4)
                                        )
                                    }
                                    ), "_" to 1)),
                                    _cV(_component_BackgroundImage, _uM("src" to "/static/ico/word/word_r6.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "num_item6"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "20rpx", "color" to "#fff", "font-weight" to "bold"))), _tD(getSubNum("同步句子默写")), 5),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#fff"))), "默写", 4)
                                        )
                                    }
                                    ), "_" to 1))
                                )),
                                _cE("view", _uM("class" to "tips"), _uA(
                                    _cE("view", _uM("style" to _nS(_uM("width" to "4rpx", "height" to "4rpx", "background-color" to "#E54E4E", "border-radius" to "4rpx"))), null, 4),
                                    _cE("text", _uM("class" to "tips_text"), "有" + _tD(unref(redNum)) + "个红色句子待复习", 1)
                                )),
                                _cV(_component_BackgroundImage, _uM("src" to "/static/ico/word/btn_bg.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "start_review", "onClick" to goTest), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE("text", _uM("class" to "_text"), "开始复习")
                                    )
                                }
                                ), "_" to 1))
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
                return _uM("sync_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "sync_content" to _pS(_uM("paddingTop" to "20rpx", "paddingRight" to "20rpx", "paddingBottom" to "20rpx", "paddingLeft" to "20rpx", "flexDirection" to "row")), "module-container" to _pS(_uM("flex" to 1.5, "flexDirection" to "row", "flexWrap" to "wrap")), "module-item" to _pS(_uM("marginRight" to "8rpx", "width" to "137.11rpx", "height" to "161rpx", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "paddingTop" to "15rpx", "paddingRight" to "12rpx", "paddingBottom" to "15rpx", "paddingLeft" to "12rpx", "display" to "flex", "justifyContent" to "space-between", "alignItems" to "center", "boxShadow" to "0 2rpx 8rpx rgba(0, 0, 0, 0.05)", "boxSizing" to "border-box", "marginBottom" to "10rpx")), "module-content" to _pS(_uM("flex" to 1, "display" to "flex", "flexDirection" to "column", "justifyContent" to "space-between")), "module-title" to _pS(_uM("textAlign" to "center", "fontSize" to "15rpx", "fontWeight" to "bold", "color" to "#3d3d3d")), "progress-text" to _pS(_uM("fontSize" to "11rpx", "color" to "#666666", "marginBottom" to "8rpx", "textAlign" to "center")), "nav-r" to _pS(_uM("width" to "87rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFEFD6", "boxShadow" to "0rpx 1rpx 2rpx 0rpx rgba(64, 78, 149, 0.8)", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_text" to _uM(".nav-r " to _uM("fontWeight" to "700", "fontSize" to "13rpx", "color" to "#FA9600", "marginLeft" to "2.93rpx")), "sync-right" to _pS(_uM("flex" to 0.8, "alignItems" to "center")), "title" to _uM(".sync-right " to _uM("fontWeight" to "700", "fontSize" to "15rpx", "color" to "#FFFFFF")), "num_list" to _uM(".sync-right " to _uM("height" to "236rpx", "marginTop" to "16rpx", "position" to "relative", "width" to "100%")), "num_item1" to _uM(".sync-right .num_list " to _uM("width" to "79rpx", "height" to "79rpx", "borderTopLeftRadius" to "79rpx", "borderTopRightRadius" to "79rpx", "borderBottomRightRadius" to "79rpx", "borderBottomLeftRadius" to "79rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "absolute", "top" to "1.76rpx", "left" to "40rpx")), "num_item2" to _uM(".sync-right .num_list " to _uM("width" to "52.15rpx", "height" to "52.15rpx", "borderTopLeftRadius" to "52.15rpx", "borderTopRightRadius" to "52.15rpx", "borderBottomRightRadius" to "52.15rpx", "borderBottomLeftRadius" to "52.15rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "absolute", "top" to 0, "left" to "125.88rpx")), "num_item3" to _uM(".sync-right .num_list " to _uM("width" to "67.38rpx", "height" to "67.38rpx", "borderTopLeftRadius" to "67.38rpx", "borderTopRightRadius" to "67.38rpx", "borderBottomRightRadius" to "67.38rpx", "borderBottomLeftRadius" to "67.38rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "absolute", "top" to "62.15rpx", "left" to "66.8rpx")), "num_item4" to _uM(".sync-right .num_list " to _uM("width" to "79rpx", "height" to "79rpx", "borderTopLeftRadius" to "79rpx", "borderTopRightRadius" to "79rpx", "borderBottomRightRadius" to "79rpx", "borderBottomLeftRadius" to "79rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "absolute", "top" to "40rpx", "right" to "40rpx")), "num_item5" to _uM(".sync-right .num_list " to _uM("width" to "79rpx", "height" to "79rpx", "borderTopLeftRadius" to "79rpx", "borderTopRightRadius" to "79rpx", "borderBottomRightRadius" to "79rpx", "borderBottomLeftRadius" to "79rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "absolute", "bottom" to "10rpx", "left" to "49.22rpx")), "num_item6" to _uM(".sync-right .num_list " to _uM("width" to "62.11rpx", "height" to "62.11rpx", "borderTopLeftRadius" to "62.11rpx", "borderTopRightRadius" to "62.11rpx", "borderBottomRightRadius" to "62.11rpx", "borderBottomLeftRadius" to "62.11rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "absolute", "bottom" to "26rpx", "right" to "42.19rpx")), "tips" to _uM(".sync-right " to _uM("flexDirection" to "row", "alignItems" to "center")), "tips_text" to _uM(".sync-right .tips " to _uM("fontSize" to "11rpx", "color" to "#E54E4E", "lineHeight" to "13rpx", "textAlign" to "center", "marginLeft" to "5.27rpx", "fontWeight" to "bold")), "start_review" to _uM(".sync-right " to _uM("width" to "171rpx", "height" to "45rpx", "marginTop" to "5.86rpx")), "_text" to _uM(".sync-right .start_review " to _uM("fontWeight" to "700", "fontSize" to "18rpx", "color" to "#3A58EB", "lineHeight" to "45rpx", "textAlign" to "center")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
