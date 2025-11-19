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
import uts.sdk.modules.limeAudioPlayer.createInnerAudioContext
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesMyTaskMyTask : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesMyTaskMyTask) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesMyTaskMyTask
            val _cache = __ins.renderCache
            var options = reactive<defaultOptions>(defaultOptions(taskCount = "0", finishCount = "0", pageNum = 1, unFinish = 0))
            val showJb = ref(false)
            var wordList = ref<UTSArray<returnItems>?>(null)
            val wordListEnd = ref<UTSArray<returnItems>?>(null)
            val wordListNotEnd = ref<UTSArray<returnItems>?>(null)
            val showStatus = ref(2)
            fun gen_getList_fn(status: Number, pageNum: Number?) {
                var _date = ucsShare.getState("queryDate")
                showStatus.value = status
                uni_showLoading(ShowLoadingOptions(title = "加载中...", mask = true))
                var data: UTSJSONObject = _uO("pageSize" to 3000, "pageNum" to pageNum, "isFinish" to if (status == 2) {
                    ""
                } else {
                    status
                }
                , "date" to _date)
                uni_request<Result<UTSArray<returnItems>>>(RequestOptions(url = getUrl("/biz/studyTask/api/myList"), method = "GET", data = data, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        return uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                    }
                    wordList.value = responseData.data
                    wordList.value?.forEach(fun(item){
                        if (item.readTrainProblemTypeConfig != null) {
                            var str = item.readTrainProblemTypeConfig!!
                            var arr = str.split(",")
                            if (arr.length > 1) {
                                item.readTrainProblemTypeConfig = "完形填空、六选五"
                            } else {
                                item.readTrainProblemTypeConfig = if (arr[0] === "16") {
                                    "完形填空"
                                } else {
                                    "六选五"
                                }
                            }
                        }
                    }
                    )
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){
                    uni_hideLoading()
                }
                ))
                ucsShare.removeState("queryDate")
            }
            val getList = ::gen_getList_fn
            fun gen_getTaskNum_fn() {
                uni_request<Result<defaultOptions>>(RequestOptions(url = getUrl("/biz/studyTask/api/myStat"), method = "GET", header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        return
                    }
                    options.taskCount = responseData.data?.taskCount
                    options.finishCount = responseData.data?.finishCount
                    if (options.taskCount != null && options.finishCount != null) {
                        var str1 = options.taskCount!!
                        var str2 = options.finishCount!!
                        options.unFinish = parseInt(str1) - parseInt(str2)
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getTaskNum = ::gen_getTaskNum_fn
            val goPath = fun(path: String){
                uni_navigateTo(NavigateToOptions(url = path, fail = fun(_) {
                    console.log()
                }
                ))
            }
            fun gen_startStudy_fn(item: returnItems) {
                var _url = getJumpUrl(item)
                if (_url == "") {
                    return
                }
                uni_navigateTo(NavigateToOptions(url = _url))
            }
            val startStudy = ::gen_startStudy_fn
            val ctx = createInnerAudioContext()
            ctx.onCanplay(fun(_){
                ctx.play()
            }
            )
            ctx.onEnded(fun(_res){
                showJb.value = false
            }
            )
            onPageShow(fun(){
                getTaskNum()
                getList(2, options.pageNum)
                if (uni_getStorageSync("studyTaskEnd") != "") {
                    ctx.src = getSondUrl("完成声音")
                    uni_removeStorageSync("studyTaskEnd")
                }
            }
            )
            onReachBottom(fun(){})
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_LearningInfo = resolveEasyComponent("LearningInfo", GenComponentsLearningInfoLearningInfoClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_progress = resolveComponent("progress")
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "我的任务"), _uM("right" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cE("view", _uM("class" to "nav-r", "onClick" to fun(){
                                    goPath("/pages/myTask/record")
                                }
                                ), _uA(
                                    _cE("image", _uM("src" to "/static/ico/task_ico.png", "style" to _nS(_uM("width" to "99.61rpx", "height" to "27.54rpx")), "mode" to ""), null, 4)
                                ), 8, _uA(
                                    "onClick"
                                ))
                            )
                        }
                        ), "default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_LearningInfo)
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "d_right"), _uA(
                                _cE("view", _uM("class" to "d_top"), _uA(
                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("margin-right" to "30rpx", "flex-direction" to "row", "align-items" to "center"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("width" to "4rpx", "height" to "4rpx", "background-color" to "#6694DF", "border-radius" to "4rpx", "margin-right" to "5rpx"))), null, 4),
                                            _cE("text", _uM("class" to "d_top_text", "style" to _nS(_uM("color" to "#6694DF"))), "已完成 " + _tD(unref(options).finishCount), 5)
                                        ), 4),
                                        _cE("view", _uM("style" to _nS(_uM("margin-right" to "30rpx", "flex-direction" to "row", "align-items" to "center"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("width" to "4rpx", "height" to "4rpx", "background-color" to "#E13535", "border-radius" to "4rpx", "margin-right" to "5rpx"))), null, 4),
                                            _cE("text", _uM("class" to "d_top_text", "style" to _nS(_uM("color" to "#E13535"))), "未完成 " + _tD(unref(options).unFinish), 5)
                                        ), 4)
                                    ), 4),
                                    _cE("view", _uM("class" to "d_top_btn_group"), _uA(
                                        _cE("view", _uM("class" to "d_top_btn", "onClick" to fun(){
                                            getList(2, 1)
                                        }
                                        ), _uA(
                                            _cE("image", _uM("src" to if (unref(showStatus) == 2) {
                                                "/static/ico/all_active.png"
                                            } else {
                                                "/static/ico/all.png"
                                            }
                                            , "mode" to "aspectFit", "class" to "d_top_btn_ico"), null, 8, _uA(
                                                "src"
                                            )),
                                            _cE("text", _uM("class" to _nC(_uA(
                                                "d_top_btn_text",
                                                _uM("d_top_btn_text_active" to (unref(showStatus) == 2))
                                            ))), "全部", 2)
                                        ), 8, _uA(
                                            "onClick"
                                        )),
                                        _cE("view", _uM("class" to "d_top_btn", "onClick" to fun(){
                                            getList(1, 1)
                                        }
                                        ), _uA(
                                            _cE("image", _uM("src" to if (unref(showStatus) == 1) {
                                                "/static/ico/done_active.png"
                                            } else {
                                                "/static/ico/done.png"
                                            }
                                            , "mode" to "aspectFit", "class" to "d_top_btn_ico"), null, 8, _uA(
                                                "src"
                                            )),
                                            _cE("text", _uM("class" to _nC(_uA(
                                                "d_top_btn_text",
                                                _uM("d_top_btn_text_active" to (unref(showStatus) == 1))
                                            ))), "已完成", 2)
                                        ), 8, _uA(
                                            "onClick"
                                        )),
                                        _cE("view", _uM("class" to "d_top_btn", "onClick" to fun(){
                                            getList(0, 1)
                                        }
                                        ), _uA(
                                            _cE("image", _uM("src" to if (unref(showStatus) == 0) {
                                                "/static/ico/undone_active.png"
                                            } else {
                                                "/static/ico/undone.png"
                                            }
                                            , "mode" to "aspectFit", "class" to "d_top_btn_ico"), null, 8, _uA(
                                                "src"
                                            )),
                                            _cE("text", _uM("class" to _nC(_uA(
                                                "d_top_btn_text",
                                                _uM("d_top_btn_text_active" to (unref(showStatus) == 0))
                                            ))), "未完成", 2)
                                        ), 8, _uA(
                                            "onClick"
                                        ))
                                    ))
                                )),
                                _cE("view", _uM("class" to "word_table"), _uA(
                                    _cE("view", _uM("class" to "word_table-header"), _uA(
                                        _cE("view", _uM("class" to "word_table-header-item word_table-header-item-unit-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "任务")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item word_table-header-item-progress-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "进度")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item word_table-header-item-count-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "完成奖励")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item word_table-header-item-time-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "预估时间")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item word_table-header-item-status-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "状态")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item word_table-header-item-status-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"))
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
                                                    _cE("text", _uM("class" to "word_table-body-item-text word_table-body-item-text-unit"), _tD(item.taskName ?: item.testPaperName), 1)
                                                )),
                                                _cE("view", _uM("class" to "word_table-body-item word_table-body-item-progress-box", "style" to _nS(_uM("height" to "100%"))), _uA(
                                                    _cE("view", _uM("class" to "word_table-body-item-progress", "style" to _nS(_uM("height" to "100%"))), _uA(
                                                        _cE("view", _uM("style" to _nS(_uM("justify-content" to "flex-end", "height" to "100%"))), _uA(
                                                            _cV(_component_progress, _uM("class" to "word_table-body-item-progress-progress", "border-radius" to 100, "percent" to (((item.userStudyTaskProgress?.currentProgressValue ?: 0) / item.taskProgressValue) * 100), "stroke-width" to 7, "activeColor" to "#5B77FF", "backgroundColor" to "#E2E6F6"), null, 8, _uA(
                                                                "percent"
                                                            )),
                                                            _cE("view", _uM("style" to _nS(_uM("height" to "10rpx", "margin-top" to "3rpx", "margin-bottom" to "6rpx"))), _uA(
                                                                _cE("text", _uM("style" to _nS(_uM("font-size" to "9.38rpx", "line-height" to "10rpx", "color" to "#7B7B7B"))), _tD(item.readTrainProblemTypeConfig), 5)
                                                            ), 4)
                                                        ), 4),
                                                        _cE("text", _uM("class" to "word_table-body-item-text"), _tD(item.userStudyTaskProgress?.currentProgressValue ?: 0) + " / " + _tD(item.taskProgressValue), 1)
                                                    ), 4)
                                                ), 4),
                                                _cE("view", _uM("class" to "word_table-body-item word_table-body-item-count-box"), _uA(
                                                    _cE("image", _uM("src" to "/static/ico/user/jinbi_icon.png", "mode" to "", "style" to _nS(_uM("width" to "23.44rpx", "height" to "27.19rpx"))), null, 4),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "margin-left" to "6rpx"))), "x" + _tD(item.pointsPrize ?: 0), 5)
                                                )),
                                                _cE("view", _uM("class" to "word_table-body-item word_table-body-item-time-box"), _tD(item.testUseMinute ?: "-"), 1),
                                                _cE("view", _uM("class" to "word_table-body-item word_table-body-item-status-box"), _uA(
                                                    if (isTrue(item.userStudyTaskProgress)) {
                                                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "justify-content" to "center"))), _uA(
                                                            if (item.userStudyTaskProgress?.isFinish == "1") {
                                                                _cE("view", _uM("key" to 0, "class" to "word_table-body-item-status"), _uA(
                                                                    _cE("text", _uM("class" to "word_table-body-item-status-text"), "已完成")
                                                                ))
                                                            } else {
                                                                _cC("v-if", true)
                                                            },
                                                            if (item.userStudyTaskProgress?.isFinish == "0") {
                                                                _cE("view", _uM("key" to 1, "class" to "word_table-body-item-status", "onClick" to fun(){
                                                                    startStudy(item)
                                                                }, "style" to _nS(_uM("background-color" to "#ACDFD7"))), _uA(
                                                                    _cE("text", _uM("class" to "word_table-body-item-status-text"), "学习中")
                                                                ), 12, _uA(
                                                                    "onClick"
                                                                ))
                                                            } else {
                                                                _cC("v-if", true)
                                                            }
                                                        ), 4)
                                                    } else {
                                                        _cE("view", _uM("key" to 1, "style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "justify-content" to "center"))), _uA(
                                                            _cE("view", _uM("class" to "word_table-body-item-status", "style" to _nS(_uM("background-color" to "#CCE2EE")), "onClick" to fun(){
                                                                startStudy(item)
                                                            }
                                                            ), _uA(
                                                                _cE("text", _uM("class" to "word_table-body-item-status-text", "style" to _nS(_uM("color" to "#fff"))), "去完成", 4)
                                                            ), 12, _uA(
                                                                "onClick"
                                                            ))
                                                        ), 4)
                                                    }
                                                )),
                                                _cE("view", _uM("class" to "word_table-body-item word_table-body-item-status-box"), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "justify-content" to "center"))), _uA(
                                                        if (isTrue(_uA(
                                                            "10",
                                                            "20"
                                                        ).includes(item.taskType ?: ""))) {
                                                            _cE("view", _uM("key" to 0, "onClick" to fun(){
                                                                goPath("/pages/myTask/recordNew?name=" + ((item.taskName ?: item.testPaperName) ?: "") + "&id=" + (item.studyTaskId ?: item.testPaperId))
                                                            }), _uA(
                                                                if (isTrue(_uA(
                                                                    "2",
                                                                    "3",
                                                                    "",
                                                                    "12"
                                                                ).includes(item.taskModule ?: ""))) {
                                                                    _cE("text", _uM("key" to 0, "class" to "word_table-body-item-status-text", "style" to _nS(_uM("color" to "#08f"))), "答题记录", 4)
                                                                } else {
                                                                    _cC("v-if", true)
                                                                }
                                                            ), 8, _uA(
                                                                "onClick"
                                                            ))
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                    ), 4)
                                                ))
                                            ), 4)
                                        }
                                        ), 128)
                                    ))
                                ))
                            ))
                        )),
                        if (isTrue(unref(showJb))) {
                            _cE("view", _uM("key" to 0, "style" to _nS(_uM("position" to "fixed", "top" to "0", "left" to "0", "width" to "750rpx", "height" to "100%", "align-items" to "center", "justify-content" to "center", "z-index" to "999"))), _uA(
                                _cE("image", _uM("src" to "/static/jb.gif", "style" to _nS(_uM("width" to "700rpx")), "mode" to "widthFix"), null, 4)
                            ), 4)
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "marginTop" to "13.48rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "d_left" to _uM(".d_content " to _uM("height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginRight" to "5.86rpx", "paddingTop" to "17.58rpx", "paddingRight" to "9.96rpx", "paddingBottom" to "17.58rpx", "paddingLeft" to "9.96rpx")), "d_left_top" to _uM(".d_content .d_left " to _uM("flexDirection" to "row")), "d_left_title" to _uM(".d_content .d_left " to _uM("fontWeight" to "700", "fontSize" to "14rpx", "color" to "#3D3D3D", "marginLeft" to "6.45rpx")), "unit_list" to _uM(".d_content .d_left " to _uM("marginTop" to "15.82rpx", "height" to "281rpx")), "unit_item" to _uM(".d_content .d_left .unit_list " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "unit_item_title" to _uM(".d_content .d_left .unit_list .unit_item " to _uM("fontWeight" to "400", "fontSize" to "11.72rpx", "flex" to 1, "marginLeft" to "6.45rpx")), "unit_item_count" to _uM(".d_content .d_left .unit_list .unit_item " to _uM("fontWeight" to "400", "fontSize" to "11.72rpx", "color" to "#8BA0C4", "lineHeight" to "35rpx")), "d_right" to _uM(".d_content " to _uM("flex" to 1, "width" to "100%", "height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx")), "word_table" to _uM(".d_content .d_right " to _uM("marginTop" to "7.62rpx", "marginRight" to "7.62rpx", "marginBottom" to "7.62rpx", "marginLeft" to "7.62rpx", "width" to "700rpx")), "title_box" to _pS(_uM("width" to "234rpx", "height" to "28rpx", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "marginLeft" to "196.29rpx")), "_text" to _uM(".title_box " to _uM("textAlign" to "center", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#535D8C", "lineHeight" to "28rpx")), "search_box" to _pS(_uM("width" to "210rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "boxShadow" to "0rpx 2rpx 4rpx 0rpx rgba(130, 141, 197, 0.3)", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "search_ico" to _uM(".search_box " to _uM("width" to "10rpx", "height" to "10rpx", "marginLeft" to "12rpx")), "search_input" to _uM(".search_box " to _uM("flex" to 1, "height" to "100%", "fontSize" to "14rpx", "color" to "#3a3a3a", "marginTop" to 0, "marginRight" to "12rpx", "marginBottom" to 0, "marginLeft" to "12rpx")), "d_top" to _pS(_uM("paddingTop" to "13.48rpx", "paddingRight" to "16.41rpx", "paddingBottom" to "13.48rpx", "paddingLeft" to "16.41rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "d_top_text" to _uM(".d_top " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D")), "d_top_text_num" to _uM(".d_top " to _uM("fontSize" to "12rpx", "color" to "#5A9F32")), "d_top_text_tip" to _uM(".d_top " to _uM("fontSize" to "12rpx", "color" to "#98A6EE")), "d_top_btn_group" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "d_top_btn" to _pS(_uM("marginLeft" to "5.27rpx", "width" to "67rpx", "height" to "25rpx", "borderTopLeftRadius" to "21rpx", "borderTopRightRadius" to "21rpx", "borderBottomRightRadius" to "21rpx", "borderBottomLeftRadius" to "21rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "backgroundImage" to "linear-gradient(to bottom, #F3F7FF, #D5DFF0)", "backgroundColor" to "rgba(0,0,0,0)")), "d_top_btn_text" to _uM(".d_top_btn " to _uM("fontWeight" to "400", "fontSize" to "12rpx", "color" to "#98A6EE")), "d_top_btn_text_active" to _uM(".d_top_btn " to _uM("color" to "#3A58EB")), "d_top_btn_ico" to _uM(".d_top_btn " to _uM("width" to "9.4rpx", "height" to "9.4rpx", "marginRight" to "4.1rpx")), "word_table-header" to _uM(".word_table " to _uM("height" to "35.16rpx", "backgroundColor" to "#F1F5FC", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-header-item-text" to _uM(".word_table " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D")), "word_table-body-row" to _uM(".word_table " to _uM("height" to "47rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body" to _uM(".word_table " to _uM("height" to "220rpx")), "word_table-header-item" to _uM(".word_table .word_table-header " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center"), ".word_table .word_table-body-row " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item" to _uM(".word_table .word_table-header " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center"), ".word_table .word_table-body-row " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item-progress-box" to _uM(".word_table " to _uM("flex" to 1.5)), "word_table-header-item-progress-box" to _uM(".word_table " to _uM("flex" to 1.5)), "word_table-body-item-progress" to _uM(".word_table " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item-progress-progress" to _uM(".word_table " to _uM("width" to "140rpx", "height" to "7rpx", "marginRight" to "12.3rpx")), "word_table-body-item-text" to _uM(".word_table " to _uM("fontFamily" to "Arial, Arial", "fontWeight" to "400", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx")), "word_table-body-item-count-box" to _uM(".word_table " to _uM("width" to "80rpx")), "word_table-header-item-count-box" to _uM(".word_table " to _uM("width" to "80rpx")), "word_table-header-item-unit-box" to _uM(".word_table " to _uM("width" to "150rpx")), "word_table-body-item-unit-box" to _uM(".word_table " to _uM("width" to "150rpx")), "word_table-body-item-status-box" to _uM(".word_table " to _uM("flex" to 0.5)), "word_table-header-item-status-box" to _uM(".word_table " to _uM("flex" to 0.5)), "word_table-body-item-time-box" to _uM(".word_table " to _uM("width" to "60rpx")), "word_table-header-item-time-box" to _uM(".word_table " to _uM("width" to "60rpx")), "word_table-body-item-status" to _uM(".word_table " to _uM("width" to "43.95rpx", "height" to "17.58rpx", "backgroundImage" to "none", "backgroundColor" to "#B0BFD9", "borderTopLeftRadius" to "17.58rpx", "borderTopRightRadius" to "17.58rpx", "borderBottomRightRadius" to "17.58rpx", "borderBottomLeftRadius" to "17.58rpx", "justifyContent" to "center", "alignItems" to "center")), "word_table-body-item-status-text" to _uM(".word_table " to _uM("fontSize" to "10.55rpx", "color" to "#818DCA")), "nav-r" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_text" to _uM(".nav-r " to _uM("fontWeight" to "700", "fontSize" to "13rpx", "color" to "#FA9600", "marginLeft" to "2.93rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
