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
open class GenPagesReadSyncReadSync : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesReadSyncReadSync) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesReadSyncReadSync
            val _cache = __ins.renderCache
            val modelVale = ref(0)
            val readArr = ref(_uA<readList>())
            val statisticsInfo = ref<readStatistics?>(null)
            val statisticsTime = computed(fun(): String {
                if (statisticsInfo.value != null) {
                    return formatTime(statisticsInfo.value?.totalExerciseSecond ?: 0, "HH:MM:SS")
                }
                return "00:00"
            }
            )
            val averageTime = computed(fun(): String {
                if (statisticsInfo.value != null) {
                    return formatTime(statisticsInfo.value?.averageExerciseSecond ?: 0, "HH:MM:SS")
                }
                return "00:00"
            }
            )
            val accuracy = computed(fun(): String {
                var kVal = statisticsInfo.value
                if (kVal != null) {
                    var num = kVal?.totalProblemTypeExercisePassNum ?: 0
                    var num2 = kVal?.totalProblemTypeExerciseNum ?: 0
                    var endNum = num / num2
                    return ((if (isNaN(endNum)) {
                        0
                    } else {
                        endNum
                    }
                    ) * 100).toFixed(0)
                }
                return "0"
            }
            )
            fun gen_goTest_fn(item: readList) {
                setTextbookUnitId(item.id)
                uni_navigateTo(NavigateToOptions(url = "/pages/readSync/test/test?unitTitle=" + item.unitName2 + "&exerciseNum=" + item.readTrainProgress.exerciseNum))
            }
            val goTest = ::gen_goTest_fn
            val goStatPath = fun(item: readList){
                var textbookId = item.textbookId
                var unitId = item.id
                var module = item.readTrainProgress.textbookUnitModule
                var subModule = item.readTrainProgress.textbookUnitSubModule
                var unitName = item.unitName2
                uni_navigateTo(NavigateToOptions(url = "/pages/readSync/statistics/statistics?textbookId=" + textbookId + "&textbookUnitId=" + unitId + "&module=" + module + "&subModule=" + subModule + "&unitTitle=" + unitName + " \u6311\u6218\u7EDF\u8BA1", fail = fun(_) {
                    console.log()
                }
                ))
            }
            fun gen_getModelTj_fn() {
                uni_request<Result<readStatistics>>(RequestOptions(url = getUrl("/biz/textbookUnit/api/getReadTrainStatByTextbook"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步阅读训练")
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    var _data = responseData.data
                    if (_data != null) {
                        statisticsInfo.value = _data
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getModelTj = ::gen_getModelTj_fn
            fun gen_getList_fn() {
                uni_request<Result<UTSArray<readList>>>(RequestOptions(url = getUrl("/biz/textbookUnit/api/list"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步阅读训练")
                    var subModule = getSubModelKey("同步阅读闯关")
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
                    var _data = responseData.data
                    if (_data != null) {
                        readArr.value = _data
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getList = ::gen_getList_fn
            fun gen_getBcSubItem_fn(arr: UTSArray<readProblemTypeStatList>?, key: String): readProblemTypeStatList? {
                if (arr == null) {
                    return null
                }
                val item = arr.filter(fun(item: readProblemTypeStatList): Boolean {
                    return item.problemType == key
                }
                )
                if (item.length == 0) {
                    return null as readProblemTypeStatList?
                }
                return item[0]
            }
            val getBcSubItem = ::gen_getBcSubItem_fn
            onLoad(fun(_options){})
            onPageShow(fun(){
                getModelTj()
                setTimeout(fun(){
                    getList()
                }
                , 0)
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
                val _component_l_circle = resolveEasyComponent("l-circle", GenUniModulesLimeCircleComponentsLCircleLCircleClass)
                val _component_grammarStars = resolveEasyComponent("grammarStars", GenComponentsGrammarStarsGrammarStarsClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "sync_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "同步阅读"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_LearningInfo),
                                _cV(_component_ChangeGrade)
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "sync_content"), _uA(
                            _cV(_component_BackgroundImage, _uM("src" to "/static/ico/read_head.png", "bgStyle" to "width:100%;height:100%;position:absolute;left:0;top:0", "className" to "top_head"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("view", _uM("class" to "top_head_content"), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("margin-right" to "50rpx"))), _uA(
                                                _cE("text", _uM("style" to _nS(_uM("font-size" to "11.72rpx", "line-height" to "17.58rpx", "color" to "#244E93"))), "正确率", 4),
                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "flex-end"))), _uA(
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "29.3rpx", "line-height" to "29.3rpx", "color" to "#244E93"))), _tD(unref(accuracy)), 5),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "14.06rpx", "line-height" to "14.06rpx", "color" to "#244E93", "margin-bottom" to "5rpx"))), "%", 4)
                                                ), 4)
                                            ), 4),
                                            _cE("view", _uM("class" to "top_item"), _uA(
                                                _cE("view", _uM("class" to "top_item_img_box"), _uA(
                                                    _cE("image", _uM("src" to "/static/ico/read/read_ico1.png", "style" to _nS(_uM("width" to "34.57rpx", "height" to "18.75rpx")), "mode" to ""), null, 4)
                                                )),
                                                _cE("text", _uM("class" to "top_item_title"), " 选词填空 "),
                                                _cE("text", _uM("class" to "top_item_num"), _tD(getBcSubItem(unref(statisticsInfo)?.problemTypeStatList, "14")?.problemTypeExercisePassNum), 1)
                                            )),
                                            _cE("view", _uM("class" to "top_item"), _uA(
                                                _cE("view", _uM("class" to "top_item_img_box"), _uA(
                                                    _cE("image", _uM("src" to "/static/ico/read/read_ico2.png", "style" to _nS(_uM("width" to "28.12rpx", "height" to "23.44rpx")), "mode" to ""), null, 4)
                                                )),
                                                _cE("text", _uM("class" to "top_item_title"), " 语法填空题型 "),
                                                _cE("text", _uM("class" to "top_item_num"), _tD(getBcSubItem(unref(statisticsInfo)?.problemTypeStatList, "15")?.problemTypeExercisePassNum), 1)
                                            )),
                                            _cE("view", _uM("class" to "top_item"), _uA(
                                                _cE("view", _uM("class" to "top_item_img_box"), _uA(
                                                    _cE("image", _uM("src" to "/static/ico/read/read_ico3.png", "style" to _nS(_uM("width" to "18.75rpx", "height" to "21.44rpx")), "mode" to ""), null, 4)
                                                )),
                                                _cE("text", _uM("class" to "top_item_title"), " 完形填空题型 "),
                                                _cE("text", _uM("class" to "top_item_num"), _tD(getBcSubItem(unref(statisticsInfo)?.problemTypeStatList, "16")?.problemTypeExercisePassNum), 1)
                                            )),
                                            _cE("view", _uM("class" to "top_item"), _uA(
                                                _cE("view", _uM("class" to "top_item_img_box"), _uA(
                                                    _cE("image", _uM("src" to "/static/ico/read/read_ico4.png", "style" to _nS(_uM("width" to "25.78rpx", "height" to "20.22rpx")), "mode" to ""), null, 4)
                                                )),
                                                _cE("text", _uM("class" to "top_item_title"), " 传统阅读题型 "),
                                                _cE("text", _uM("class" to "top_item_num"), _tD(getBcSubItem(unref(statisticsInfo)?.problemTypeStatList, "17")?.problemTypeExercisePassNum), 1)
                                            )),
                                            _cE("view", _uM("class" to "top_item"), _uA(
                                                _cE("view", _uM("class" to "top_item_img_box"), _uA(
                                                    _cE("image", _uM("src" to "/static/ico/read/read_ico5.png", "style" to _nS(_uM("width" to "23.44rpx", "height" to "16rpx")), "mode" to ""), null, 4)
                                                )),
                                                _cE("text", _uM("class" to "top_item_title"), " 六选五题型 "),
                                                _cE("text", _uM("class" to "top_item_num"), _tD(getBcSubItem(unref(statisticsInfo)?.problemTypeStatList, "18")?.problemTypeExercisePassNum), 1)
                                            ))
                                        ), 4),
                                        _cE("view", null, _uA(
                                            _cE("view", _uM("class" to "r_item"), _uA(
                                                _cE("text", _uM("class" to "t1"), "挑战成功/总次数："),
                                                _cE("text", _uM("class" to "t2"), _tD(unref(statisticsInfo)?.totalProblemTypeExercisePassNum) + "/" + _tD(unref(statisticsInfo)?.totalProblemTypeExerciseNum), 1)
                                            )),
                                            _cE("view", _uM("class" to "r_item"), _uA(
                                                _cE("text", _uM("class" to "t1"), "挑战总时长："),
                                                _cE("text", _uM("class" to "t2"), _tD(unref(statisticsTime)), 1)
                                            )),
                                            _cE("view", _uM("class" to "r_item"), _uA(
                                                _cE("text", _uM("class" to "t1"), "挑战平均时长："),
                                                _cE("text", _uM("class" to "t2"), _tD(unref(averageTime)), 1)
                                            ))
                                        ))
                                    ))
                                )
                            }
                            ), "_" to 1)),
                            _cE("view", _uM("class" to "b_content"), _uA(
                                _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1", "width" to "100%"))), _uA(
                                    _cE("view", _uM("style" to _nS(_uM("padding-bottom" to "-2.34rpx"))), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(readArr), fun(item, __key, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "read_item"), _uA(
                                                _cE("view", _uM("style" to _nS(_uM("width" to "2rpx", "height" to "11rpx", "background" to "#5689DC", "border-radius" to "0rpx 6rpx 6rpx 0rpx"))), null, 4),
                                                _cE("view", _uM("class" to "read_item_content", "style" to _nS(_uM("margin-left" to "15.82rpx", "flex-direction" to "row", "align-items" to "center"))), _uA(
                                                    _cE("view", _uM("class" to ""), _uA(
                                                        _cE("text", null, _tD(item.unitName2), 1),
                                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                            _cE("view", _uM("style" to _nS(_uM("margin-right" to "60rpx"))), _uA(
                                                                _cV(_component_l_circle, _uM("percent" to parseInt(item.readTrainProgress.finishRate), "size" to "60"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                                    return _uA(
                                                                        _cE("text", null, _tD(item.readTrainProgress?.finishRate) + "%", 1)
                                                                    )
                                                                }
                                                                ), "_" to 2), 1032, _uA(
                                                                    "percent"
                                                                ))
                                                            ), 4),
                                                            _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                                _cE("view", _uM("class" to "top_item", "style" to _nS(_uM("width" to "60rpx"))), _uA(
                                                                    _cE("view", _uM("class" to "top_item_img_box"), _uA(
                                                                        _cE("image", _uM("src" to "/static/ico/read/read_ico1.png", "style" to _nS(_uM("width" to "34.57rpx", "height" to "18.75rpx")), "mode" to ""), null, 4)
                                                                    )),
                                                                    _cE("view", _uM("style" to _nS(_uM("width" to "31.64rpx", "height" to "36.61rpx", "align-items" to "center", "justify-content" to "center"))), _uA(
                                                                        _cV(_component_grammarStars, _uM("isFall" to (getBcSubItem(item.readTrainProgress.problemTypeStatList, "14")?.passStatus == 0), "half" to (getBcSubItem(item.readTrainProgress.problemTypeStatList, "14")?.passStatus == 1), "style" to _nS(_uM("transform" to "scale(0.7)"))), null, 8, _uA(
                                                                            "isFall",
                                                                            "half",
                                                                            "style"
                                                                        ))
                                                                    ), 4)
                                                                ), 4),
                                                                _cE("view", _uM("class" to "top_item", "style" to _nS(_uM("width" to "60rpx"))), _uA(
                                                                    _cE("view", _uM("class" to "top_item_img_box"), _uA(
                                                                        _cE("image", _uM("src" to "/static/ico/read/read_ico2.png", "style" to _nS(_uM("width" to "28.12rpx", "height" to "23.44rpx")), "mode" to ""), null, 4)
                                                                    )),
                                                                    _cE("view", _uM("style" to _nS(_uM("width" to "31.64rpx", "height" to "36.61rpx", "align-items" to "center", "justify-content" to "center"))), _uA(
                                                                        _cV(_component_grammarStars, _uM("isFall" to (getBcSubItem(item.readTrainProgress.problemTypeStatList, "15")?.passStatus == 0), "half" to (getBcSubItem(item.readTrainProgress.problemTypeStatList, "15")?.passStatus == 1), "style" to _nS(_uM("transform" to "scale(0.7)"))), null, 8, _uA(
                                                                            "isFall",
                                                                            "half",
                                                                            "style"
                                                                        ))
                                                                    ), 4)
                                                                ), 4),
                                                                _cE("view", _uM("class" to "top_item", "style" to _nS(_uM("width" to "60rpx"))), _uA(
                                                                    _cE("view", _uM("class" to "top_item_img_box"), _uA(
                                                                        _cE("image", _uM("src" to "/static/ico/read/read_ico3.png", "style" to _nS(_uM("width" to "18.75rpx", "height" to "21.44rpx")), "mode" to ""), null, 4)
                                                                    )),
                                                                    _cE("view", _uM("style" to _nS(_uM("width" to "31.64rpx", "height" to "36.61rpx", "align-items" to "center", "justify-content" to "center"))), _uA(
                                                                        _cV(_component_grammarStars, _uM("isFall" to (getBcSubItem(item.readTrainProgress.problemTypeStatList, "16")?.passStatus == 0), "half" to (getBcSubItem(item.readTrainProgress.problemTypeStatList, "16")?.passStatus == 1), "style" to _nS(_uM("transform" to "scale(0.7)"))), null, 8, _uA(
                                                                            "isFall",
                                                                            "half",
                                                                            "style"
                                                                        ))
                                                                    ), 4)
                                                                ), 4),
                                                                _cE("view", _uM("class" to "top_item", "style" to _nS(_uM("width" to "60rpx"))), _uA(
                                                                    _cE("view", _uM("class" to "top_item_img_box"), _uA(
                                                                        _cE("image", _uM("src" to "/static/ico/read/read_ico4.png", "style" to _nS(_uM("width" to "25.78rpx", "height" to "20.22rpx")), "mode" to ""), null, 4)
                                                                    )),
                                                                    _cE("view", _uM("style" to _nS(_uM("width" to "31.64rpx", "height" to "36.61rpx", "align-items" to "center", "justify-content" to "center"))), _uA(
                                                                        _cV(_component_grammarStars, _uM("isFall" to (getBcSubItem(item.readTrainProgress.problemTypeStatList, "17")?.passStatus == 0), "half" to (getBcSubItem(item.readTrainProgress.problemTypeStatList, "17")?.passStatus == 1), "style" to _nS(_uM("transform" to "scale(0.7)"))), null, 8, _uA(
                                                                            "isFall",
                                                                            "half",
                                                                            "style"
                                                                        ))
                                                                    ), 4)
                                                                ), 4),
                                                                _cE("view", _uM("class" to "top_item", "style" to _nS(_uM("width" to "60rpx"))), _uA(
                                                                    _cE("view", _uM("class" to "top_item_img_box"), _uA(
                                                                        _cE("image", _uM("src" to "/static/ico/read/read_ico5.png", "style" to _nS(_uM("width" to "23.44rpx", "height" to "16rpx")), "mode" to ""), null, 4)
                                                                    )),
                                                                    _cE("view", _uM("style" to _nS(_uM("width" to "31.64rpx", "height" to "36.61rpx", "align-items" to "center", "justify-content" to "center"))), _uA(
                                                                        _cV(_component_grammarStars, _uM("isFall" to (getBcSubItem(item.readTrainProgress.problemTypeStatList, "18")?.passStatus == 0), "half" to (getBcSubItem(item.readTrainProgress.problemTypeStatList, "18")?.passStatus == 1), "style" to _nS(_uM("transform" to "scale(0.7)"))), null, 8, _uA(
                                                                            "isFall",
                                                                            "half",
                                                                            "style"
                                                                        ))
                                                                    ), 4)
                                                                ), 4)
                                                            ), 4)
                                                        ), 4)
                                                    )),
                                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                                        _cE("text", _uM("class" to "item_btn1", "onClick" to fun(){
                                                            goTest(item)
                                                        }
                                                        ), "继续挑战", 8, _uA(
                                                            "onClick"
                                                        )),
                                                        _cE("text", _uM("class" to "item_btn2", "onClick" to fun(){
                                                            goStatPath(item)
                                                        }
                                                        ), "查看统计", 8, _uA(
                                                            "onClick"
                                                        ))
                                                    ), 4)
                                                ), 4)
                                            ))
                                        }
                                        ), 256)
                                    ), 4)
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
                return _uM("sync_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "sync_content" to _pS(_uM("width" to "715rpx", "height" to "332rpx", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFFFFF", "borderRightColor" to "#FFFFFF", "borderBottomColor" to "#FFFFFF", "borderLeftColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "14.56rpx", "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto")), "top_head" to _uM(".sync_content " to _uM("width" to "100%", "height" to "82.03rpx", "paddingTop" to 0, "paddingRight" to "30rpx", "paddingBottom" to 0, "paddingLeft" to "40rpx")), "top_head_content" to _uM(".sync_content .top_head " to _uM("height" to "70rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "top_item" to _uM(".sync_content " to _uM("alignItems" to "center", "marginRight" to "16rpx")), "top_item_img_box" to _uM(".sync_content .top_item " to _uM("height" to "23.44rpx", "flexDirection" to "row", "alignItems" to "center")), "top_item_title" to _uM(".sync_content .top_item " to _uM("fontSize" to "11rpx", "color" to "#244E93", "lineHeight" to "14rpx")), "top_item_num" to _uM(".sync_content .top_item " to _uM("fontSize" to "15rpx", "color" to "#244E93", "lineHeight" to "16rpx")), "r_item" to _uM(".sync_content " to _uM("flexDirection" to "row")), "t1" to _uM(".sync_content .r_item " to _uM("width" to "100rpx", "textAlign" to "right", "fontSize" to "11rpx", "color" to "#244E93", "lineHeight" to "15rpx")), "t2" to _uM(".sync_content .r_item " to _uM("fontWeight" to "bold", "fontSize" to "11rpx", "color" to "#244E93", "lineHeight" to "15rpx")), "b_content" to _uM(".sync_content " to _uM("width" to "100%", "flex" to 1, "backgroundImage" to "none", "backgroundColor" to "#DAE9FC", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "-12.62rpx", "paddingTop" to "6.54rpx", "paddingRight" to "10.55rpx", "paddingBottom" to "6.54rpx", "paddingLeft" to "10.55rpx")), "read_item" to _uM(".sync_content .b_content " to _uM("flexDirection" to "row", "alignItems" to "center", "width" to "100%", "height" to "82rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "marginBottom" to "2.34rpx")), "read_item_content" to _uM(".sync_content .b_content .read_item " to _uM("flex" to 1)), "item_btn1" to _uM(".sync_content " to _uM("width" to "65rpx", "height" to "23rpx", "backgroundImage" to "none", "backgroundColor" to "#FA9600", "borderTopLeftRadius" to "23rpx", "borderTopRightRadius" to "23rpx", "borderBottomRightRadius" to "23rpx", "borderBottomLeftRadius" to "23rpx", "textAlign" to "center", "lineHeight" to "23rpx", "color" to "#FFFFFF", "fontSize" to "12rpx", "marginRight" to "14rpx")), "item_btn2" to _uM(".sync_content " to _uM("width" to "65rpx", "height" to "23rpx", "backgroundImage" to "none", "backgroundColor" to "#ECEFFA", "borderTopLeftRadius" to "23rpx", "borderTopRightRadius" to "23rpx", "borderBottomRightRadius" to "23rpx", "borderBottomLeftRadius" to "23rpx", "textAlign" to "center", "lineHeight" to "23rpx", "fontSize" to "12rpx", "color" to "#818DCA")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
