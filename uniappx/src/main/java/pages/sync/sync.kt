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
open class GenPagesSyncSync : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSyncSync) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSyncSync
            val _cache = __ins.renderCache
            val rightInfo = ref(null as UTSArray<modelItem>?)
            val leftList1 = ref(_uA<leftItem>(leftItem(title = "智能听说", ico = "/static/ico/word/word_ico1.png", _style = "width: 50.39rpx;height: 40.85rpx", totalProgressValue = "0", currentProgressValue = "0", percentage = 0, path = "/pages/wordInter/followAlong"), leftItem(title = "识词辨义", ico = "/static/ico/word/china.png", _style = "width: 50.39rpx;height: 42.77rpx", totalProgressValue = "0", currentProgressValue = "0", percentage = 0, path = "/pages/wordInter/chinese"), leftItem(title = "智能默写", ico = "/static/ico/word/word_ico3.png", _style = "width: 42.29rpx;height: 42.88rpx", totalProgressValue = "0", currentProgressValue = "0", percentage = 0, path = "/pages/wordInter/silently")))
            val leftList = ref(_uA<leftItem>(leftItem(title = "智能记忆", ico = "/static/ico/word/word_ico2.png", _style = "width: 73.24rpx;height: 73.24rpx", totalProgressValue = "0", currentProgressValue = "0", percentage = 0, path = "/pages/wordInter/learning"), leftItem(title = "智能翻译", ico = "/static/ico/word/word_ico3.png", _style = "width: 75.38rpx;height: 75.38rpx", totalProgressValue = "0", currentProgressValue = "0", percentage = 0, path = "/pages/wordInter/translate"), leftItem(title = "智能听写", ico = "/static/ico/word/word_ico5.png", _style = "width: 72.66rpx;height: 72.66rpx", totalProgressValue = "0", currentProgressValue = "0", percentage = 0, path = "/pages/wordInter/dictation"), leftItem(title = "生词本", ico = "/static/ico/word/word_ico6.png", _style = "width: 72.66rpx;height: 72.66rpx", totalProgressValue = "0", currentProgressValue = "0", percentage = 0, path = "/pages/wordInter/words")))
            fun gen_evalNum_fn(item: leftItem): Number {
                if (item.totalProgressValue == "0") {
                    return 0
                }
                return 100 * (parseInt(item.currentProgressValue) / parseInt(item.totalProgressValue))
            }
            val evalNum = ::gen_evalNum_fn
            val redNum = ref(0)
            fun gen_getModelList_fn() {
                if (getTextBookId() == 0) {
                    return
                }
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
                uni_request<Result<_progress>>(RequestOptions(url = getUrl("/biz/textbook/api/getEnglishModuleProgress"), method = "GET", data = object : UTSJSONObject() {
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
                    if (responseData.data != null) {
                        var _data = responseData.data as _progress
                        var _arr = _data.subModuleProgressList as UTSArray<progressItem>
                        _arr.forEach(fun(item: progressItem, index: Number){
                            var _percentage = parseInt(item.currentProgressValue) / parseInt(item.totalProgressValue)
                            if (isNaN(_percentage)) {
                                _percentage = 0
                            }
                            if (item.subModule == getSubModelKey("单词智能听说")) {
                                leftList1.value[0].percentage = _percentage
                                leftList1.value[0].currentProgressValue = item.currentProgressValue
                                leftList1.value[0].totalProgressValue = item.totalProgressValue
                            }
                            if (item.subModule == getSubModelKey("单词识词辩义")) {
                                leftList1.value[1].percentage = _percentage
                                leftList1.value[1].currentProgressValue = item.currentProgressValue
                                leftList1.value[1].totalProgressValue = item.totalProgressValue
                            }
                            if (item.subModule == getSubModelKey("单词智能默写")) {
                                leftList1.value[2].percentage = _percentage
                                leftList1.value[2].currentProgressValue = item.currentProgressValue
                                leftList1.value[2].totalProgressValue = item.totalProgressValue
                            }
                            if (item.subModule == getSubModelKey("单词智能记忆")) {
                                leftList.value[0].percentage = _percentage
                                leftList.value[0].currentProgressValue = item.currentProgressValue
                                leftList.value[0].totalProgressValue = item.totalProgressValue
                            }
                            if (item.subModule == getSubModelKey("单词智能翻译")) {
                                leftList.value[1].percentage = _percentage
                                leftList.value[1].currentProgressValue = item.currentProgressValue
                                leftList.value[1].totalProgressValue = item.totalProgressValue
                            }
                            if (item.subModule == getSubModelKey("单词智能听写")) {
                                leftList.value[2].percentage = _percentage
                                leftList.value[2].currentProgressValue = item.currentProgressValue
                                leftList.value[2].totalProgressValue = item.totalProgressValue
                            }
                            if (item.subModule == getSubModelKey("生词本")) {
                                leftList.value[3].percentage = _percentage
                                leftList.value[3].currentProgressValue = item.currentProgressValue
                                leftList.value[3].totalProgressValue = item.totalProgressValue
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
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "同步单词"), _uM("right" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cE("view", _uM("class" to "nav-r", "onClick" to fun(){
                                    goPath("/pages/wordBooks/wordBooks")
                                }
                                ), _uA(
                                    _cE("image", _uM("src" to "/static/ico/word_ico.png", "style" to _nS(_uM("width" to "26.37rpx", "height" to "26.37rpx")), "mode" to ""), null, 4),
                                    _cE("text", _uM("class" to "word_text"), "单词本")
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
                                _cE("view", null, _uA(
                                    _cE(Fragment, null, RenderHelpers.renderList(unref(leftList1), fun(item, __key, __index, _cached): Any {
                                        return _cE("view", _uM("class" to "module-itemL", "onClick" to fun(){
                                            goPath(item.path)
                                        }
                                        ), _uA(
                                            _cE("view", _uM("class" to "module-content"), _uA(
                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                    _cE("text", _uM("class" to "module-title", "style" to _nS(_uM("font-size" to "14rpx", "margin-right" to "4rpx"))), _tD(item.title), 5),
                                                    _cE("image", _uM("style" to _nS(item._style), "src" to item.ico), null, 12, _uA(
                                                        "src"
                                                    ))
                                                ), 4),
                                                _cE("view", _uM("class" to "progress-box", "style" to _nS(_uM<String, Any?>())), _uA(
                                                    _cE("text", _uM("class" to "progress-text", "style" to _nS(_uM("text-align" to "left"))), _tD(item.currentProgressValue) + "/" + _tD(item.totalProgressValue), 5),
                                                    _cV(_component_progress, _uM("border-radius" to 100, "percent" to evalNum(item), "stroke-width" to 6, "activeColor" to "#5B77FF", "backgroundColor" to "#E2E6F6"), null, 8, _uA(
                                                        "percent"
                                                    ))
                                                ), 4)
                                            ))
                                        ), 8, _uA(
                                            "onClick"
                                        ))
                                    }
                                    ), 256)
                                )),
                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "flex-wrap" to "wrap", "flex" to "1"))), _uA(
                                    _cE(Fragment, null, RenderHelpers.renderList(unref(leftList), fun(item, __key, __index, _cached): Any {
                                        return _cE("view", _uM("class" to "module-item", "onClick" to fun(){
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
                                                    _cV(_component_progress, _uM("border-radius" to 100, "percent" to evalNum(item), "stroke-width" to 6, "activeColor" to "#5B77FF", "backgroundColor" to "#E2E6F6"), null, 8, _uA(
                                                        "percent"
                                                    ))
                                                ))
                                            ))
                                        ), 8, _uA(
                                            "onClick"
                                        ))
                                    }
                                    ), 256)
                                ), 4)
                            )),
                            _cE("view", _uM("class" to "sync-right"), _uA(
                                _cE("text", _uM("class" to "title"), "智能复习"),
                                _cE("view", _uM("class" to "num_list"), _uA(
                                    _cV(_component_BackgroundImage, _uM("src" to "/static/ico/word/word_r1.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "num_item1", "onClick" to fun(){
                                        goPath("/pages/wordInter/review?tab=2")
                                    }
                                    ), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "29rpx", "color" to "#fff", "font-weight" to "bold"))), _tD(getSubNum("单词智能听说")), 5),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#fff"))), "听说", 4)
                                        )
                                    }
                                    ), "_" to 1), 8, _uA(
                                        "onClick"
                                    )),
                                    _cV(_component_BackgroundImage, _uM("src" to "/static/ico/word/word_r2.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "num_item2", "onClick" to fun(){
                                        goPath("/pages/wordInter/review?tab=0")
                                    }
                                    ), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "18rpx", "color" to "#fff", "font-weight" to "bold"))), _tD(getSubNum("单词智能记忆")), 5),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#fff"))), "记忆", 4)
                                        )
                                    }
                                    ), "_" to 1), 8, _uA(
                                        "onClick"
                                    )),
                                    _cV(_component_BackgroundImage, _uM("src" to "/static/ico/word/word_r3.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "num_item3", "onClick" to fun(){
                                        goPath("/pages/wordInter/review?tab=4")
                                    }
                                    ), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "22rpx", "color" to "#fff", "font-weight" to "bold"))), _tD(getSubNum("单词智能默写")), 5),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#fff"))), "默写", 4)
                                        )
                                    }
                                    ), "_" to 1), 8, _uA(
                                        "onClick"
                                    )),
                                    _cV(_component_BackgroundImage, _uM("src" to "/static/ico/word/word_r4.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "num_item4", "onClick" to fun(){
                                        goPath("/pages/wordInter/review?tab=1")
                                    }
                                    ), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "27rpx", "color" to "#fff", "font-weight" to "bold"))), _tD(getSubNum("单词智能翻译")), 5),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#fff"))), "翻译", 4)
                                        )
                                    }
                                    ), "_" to 1), 8, _uA(
                                        "onClick"
                                    )),
                                    _cV(_component_BackgroundImage, _uM("src" to "/static/ico/word/word_r5.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "num_item5", "onClick" to fun(){
                                        goPath("/pages/wordInter/review?tab=6")
                                    }
                                    ), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "26rpx", "color" to "#fff", "font-weight" to "bold"))), _tD(getSubNum("生词本")), 5),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#fff"))), "生词本", 4)
                                        )
                                    }
                                    ), "_" to 1), 8, _uA(
                                        "onClick"
                                    )),
                                    _cV(_component_BackgroundImage, _uM("src" to "/static/ico/word/word_r6.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "num_item6", "onClick" to fun(){
                                        goPath("/pages/wordInter/review?tab=3")
                                    }
                                    ), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "20rpx", "color" to "#fff", "font-weight" to "bold"))), _tD(getSubNum("单词智能听写")), 5),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#fff"))), "听写", 4)
                                        )
                                    }
                                    ), "_" to 1), 8, _uA(
                                        "onClick"
                                    )),
                                    _cV(_component_BackgroundImage, _uM("src" to "/static/ico/word/word_r7.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "num_item7", "onClick" to fun(){
                                        goPath("/pages/wordInter/review?tab=5")
                                    }
                                    ), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "20rpx", "color" to "#fff", "font-weight" to "bold"))), _tD(getSubNum("单词识词辩义")), 5),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#fff"))), "识词辨义", 4)
                                        )
                                    }
                                    ), "_" to 1), 8, _uA(
                                        "onClick"
                                    ))
                                )),
                                _cE("view", _uM("class" to "tips"), _uA(
                                    _cE("view", _uM("style" to _nS(_uM("width" to "4rpx", "height" to "4rpx", "background-color" to "#E54E4E", "border-radius" to "4rpx"))), null, 4),
                                    _cE("text", _uM("class" to "tips_text"), "有" + _tD(unref(redNum)) + "个红色单词待复习", 1)
                                )),
                                _cV(_component_BackgroundImage, _uM("src" to "/static/ico/word/btn_bg.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "start_review"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE("text", _uM("class" to "_text", "onClick" to fun(){
                                            goPath("/pages/wordInter/review")
                                        }
                                        ), "开始复习", 8, _uA(
                                            "onClick"
                                        ))
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
                return _uM("sync_page" to _pS(_uM("width" to "100%", "height" to "100%", "backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "sync_content" to _pS(_uM("paddingTop" to "20rpx", "paddingRight" to "20rpx", "paddingBottom" to "20rpx", "paddingLeft" to "20rpx", "flexDirection" to "row")), "module-container" to _pS(_uM("flex" to 1.5, "flexDirection" to "row")), "module-itemL" to _pS(_uM("marginRight" to "8rpx", "width" to "137.11rpx", "height" to "97.85rpx", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "paddingTop" to "15rpx", "paddingRight" to "12rpx", "paddingBottom" to "15rpx", "paddingLeft" to "12rpx", "display" to "flex", "justifyContent" to "space-between", "alignItems" to "center", "boxShadow" to "0 2rpx 8rpx rgba(0, 0, 0, 0.05)", "boxSizing" to "border-box", "marginBottom" to "10rpx")), "module-item" to _pS(_uM("marginRight" to "8rpx", "width" to "137.11rpx", "height" to "152rpx", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "paddingTop" to "15rpx", "paddingRight" to "12rpx", "paddingBottom" to "15rpx", "paddingLeft" to "12rpx", "display" to "flex", "justifyContent" to "space-between", "alignItems" to "center", "boxShadow" to "0 2rpx 8rpx rgba(0, 0, 0, 0.05)", "boxSizing" to "border-box", "marginBottom" to "10rpx")), "module-content" to _pS(_uM("flex" to 1, "display" to "flex", "flexDirection" to "column", "justifyContent" to "space-between")), "module-title" to _pS(_uM("textAlign" to "center", "fontSize" to "15rpx", "fontWeight" to "bold", "color" to "#3d3d3d")), "progress-text" to _pS(_uM("fontSize" to "11rpx", "color" to "#666666", "marginBottom" to "8rpx", "textAlign" to "center")), "nav-r" to _pS(_uM("width" to "87rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFEFD6", "boxShadow" to "0rpx 1rpx 2rpx 0rpx rgba(64, 78, 149, 0.8)", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_text" to _uM(".nav-r " to _uM("fontWeight" to "700", "fontSize" to "13rpx", "color" to "#FA9600", "marginLeft" to "2.93rpx")), "sync-right" to _pS(_uM("flex" to 0.8, "alignItems" to "center")), "title" to _uM(".sync-right " to _uM("fontWeight" to "700", "fontSize" to "15rpx", "color" to "#FFFFFF")), "num_list" to _uM(".sync-right " to _uM("height" to "220rpx", "marginTop" to "16rpx", "position" to "relative", "width" to "100%")), "num_item1" to _uM(".sync-right .num_list " to _uM("width" to "79rpx", "height" to "79rpx", "borderTopLeftRadius" to "79rpx", "borderTopRightRadius" to "79rpx", "borderBottomRightRadius" to "79rpx", "borderBottomLeftRadius" to "79rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "absolute", "top" to "1.76rpx", "left" to 0)), "num_item2" to _uM(".sync-right .num_list " to _uM("width" to "52.15rpx", "height" to "52.15rpx", "borderTopLeftRadius" to "52.15rpx", "borderTopRightRadius" to "52.15rpx", "borderBottomRightRadius" to "52.15rpx", "borderBottomLeftRadius" to "52.15rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "absolute", "top" to 0, "left" to "125.88rpx")), "num_item3" to _uM(".sync-right .num_list " to _uM("width" to "67.38rpx", "height" to "67.38rpx", "borderTopLeftRadius" to "67.38rpx", "borderTopRightRadius" to "67.38rpx", "borderBottomRightRadius" to "67.38rpx", "borderBottomLeftRadius" to "67.38rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "absolute", "top" to "62.15rpx", "left" to "66.8rpx")), "num_item4" to _uM(".sync-right .num_list " to _uM("width" to "79rpx", "height" to "79rpx", "borderTopLeftRadius" to "79rpx", "borderTopRightRadius" to "79rpx", "borderBottomRightRadius" to "79rpx", "borderBottomLeftRadius" to "79rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "absolute", "top" to "45.15rpx", "right" to 0)), "num_item5" to _uM(".sync-right .num_list " to _uM("width" to "79rpx", "height" to "79rpx", "borderTopLeftRadius" to "79rpx", "borderTopRightRadius" to "79rpx", "borderBottomRightRadius" to "79rpx", "borderBottomLeftRadius" to "79rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "absolute", "bottom" to "20rpx", "left" to 0)), "num_item6" to _uM(".sync-right .num_list " to _uM("width" to "62.11rpx", "height" to "62.11rpx", "borderTopLeftRadius" to "62.11rpx", "borderTopRightRadius" to "62.11rpx", "borderBottomRightRadius" to "62.11rpx", "borderBottomLeftRadius" to "62.11rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "absolute", "bottom" to "38.09rpx", "right" to "42.19rpx")), "num_item7" to _uM(".sync-right .num_list " to _uM("width" to "60rpx", "height" to "60rpx", "borderTopLeftRadius" to "60rpx", "borderTopRightRadius" to "60rpx", "borderBottomRightRadius" to "60rpx", "borderBottomLeftRadius" to "60rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "absolute", "bottom" to "6rpx", "left" to "84.22rpx")), "tips" to _uM(".sync-right " to _uM("flexDirection" to "row", "alignItems" to "center")), "tips_text" to _uM(".sync-right .tips " to _uM("fontSize" to "11rpx", "color" to "#E54E4E", "lineHeight" to "13rpx", "textAlign" to "center", "marginLeft" to "5.27rpx", "fontWeight" to "bold")), "start_review" to _uM(".sync-right " to _uM("width" to "171rpx", "height" to "45rpx", "marginTop" to "5.86rpx")), "_text" to _uM(".sync-right .start_review " to _uM("fontWeight" to "700", "fontSize" to "18rpx", "color" to "#3A58EB", "lineHeight" to "45rpx", "textAlign" to "center")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
