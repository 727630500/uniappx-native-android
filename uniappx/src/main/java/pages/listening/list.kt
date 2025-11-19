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
open class GenPagesListeningList : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesListeningList) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesListeningList
            val _cache = __ins.renderCache
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
            val totalCount = computed(fun(): Number {
                return showList.value.length
            }
            )
            val finishCount = computed(fun(): Number {
                return showList.value.filter(fun(item): Boolean {
                    return item.englishProgress.isFinish == "1"
                }
                ).length
            }
            )
            val exercisePassScore = computed(fun(): Number {
                return getConfig(if (title.value == "听力训练") {
                    "听力训练配置"
                } else {
                    "口语训练配置"
                }
                ).getNumber("exercisePassScore") ?: 0
            }
            )
            fun gen_getList_fn() {
                uni_request<Result<UTSArray<teachingMaterial>>>(RequestOptions(url = getUrl("/biz/textbookUnit/api/list"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步口语听力")
                    var subModule = getSubModelKey(if (title.value == "听力训练") {
                        "听力训练"
                    } else {
                        "口语训练"
                    }
                    )
                    var pageSize: Number = 3000
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    console.log(responseData)
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
                var _englishCurrentProgressValue = if (item.englishProgress?.englishCurrentProgressValue != item.englishProgress?.englishTotalProgressValue) {
                    item.englishProgress?.englishCurrentProgressValue
                } else {
                    0
                }
                setTextbookUnitId(item.id)
                uni_navigateTo(NavigateToOptions(url = "/pages/listening/listening/list?englishCurrentProgressValue=" + _englishCurrentProgressValue + "&englishTotalProgressValue=" + item.englishProgress?.englishTotalProgressValue + "&unitTitle=" + item.unitName2 + "&title=" + title.value + "&isFinish=" + item.englishProgress.isFinish))
            }
            val startStudy = ::gen_startStudy_fn
            fun gen_startExam_fn(item: teachingMaterial) {}
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
                val _component_progress = resolveComponent("progress")
                val _component_no_data = resolveEasyComponent("no-data", GenComponentsNoDataNoDataClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to unref(title)), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_LearningInfo)
                            )
                        }
                        ), "_" to 1), 8, _uA(
                            "title"
                        )),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "d_right"), _uA(
                                _cE("view", _uM("class" to "d_top"), _uA(
                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                        _cE("text", _uM("class" to "d_top_text"), "共" + _tD(unref(totalCount)) + "个单元，已完成", 1),
                                        _cE("text", _uM("class" to "d_top_text_num"), _tD(unref(finishCount)), 1),
                                        _cE("text", _uM("class" to "d_top_text"), "个单元"),
                                        _cE("text", _uM("class" to "d_top_text_tip"), "（题目全部点亮才算完成）")
                                    ), 4),
                                    _cE("view", _uM("class" to "d_top_btn_group"), _uA(
                                        _cE("view", _uM("class" to "d_top_btn", "onClick" to fun(){
                                            showStatus.value = 0
                                        }
                                        ), _uA(
                                            _cE("image", _uM("src" to if (unref(showStatus) == 0) {
                                                "/static/ico/all_active.png"
                                            } else {
                                                "/static/ico/all.png"
                                            }
                                            , "mode" to "aspectFit", "class" to "d_top_btn_ico"), null, 8, _uA(
                                                "src"
                                            )),
                                            _cE("text", _uM("class" to _nC(_uA(
                                                "d_top_btn_text",
                                                _uM("d_top_btn_text_active" to (unref(showStatus) == 0))
                                            ))), "全部", 2)
                                        ), 8, _uA(
                                            "onClick"
                                        )),
                                        _cE("view", _uM("class" to "d_top_btn", "onClick" to fun(){
                                            showStatus.value = 1
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
                                            showStatus.value = 2
                                        }
                                        ), _uA(
                                            _cE("image", _uM("src" to if (unref(showStatus) == 2) {
                                                "/static/ico/undone_active.png"
                                            } else {
                                                "/static/ico/undone.png"
                                            }
                                            , "mode" to "aspectFit", "class" to "d_top_btn_ico"), null, 8, _uA(
                                                "src"
                                            )),
                                            _cE("text", _uM("class" to _nC(_uA(
                                                "d_top_btn_text",
                                                _uM("d_top_btn_text_active" to (unref(showStatus) == 2))
                                            ))), "未完成", 2)
                                        ), 8, _uA(
                                            "onClick"
                                        ))
                                    ))
                                )),
                                _cE("view", _uM("class" to "word_table"), _uA(
                                    _cE("view", _uM("class" to "word_table-header"), _uA(
                                        _cE("view", _uM("class" to "word_table-header-item word_table-header-item-unit-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "单元")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item word_table-header-item-progress-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "题量（已通过数量/总题量）")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item word_table-header-item-status-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "操作")
                                        ))
                                    )),
                                    _cE("scroll-view", _uM("class" to "word_table-body", "direction" to "vertical"), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList((unref(showList) as UTSArray<teachingMaterial>), fun(item, index, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "word_table-body-row", "key" to item.id, "style" to _nS("" + (if (((index + 1) % 2) == 0) {
                                                "background-color: #F1F5FC"
                                            } else {
                                                ""
                                            }
                                            ))), _uA(
                                                _cE("view", _uM("class" to "word_table-body-item word_table-body-item-unit-box", "style" to _nS(_uM("position" to "relative"))), _uA(
                                                    if (item.englishProgress?.isFinish == "1") {
                                                        _cE("image", _uM("key" to 0, "src" to "/static/ico/success_ico.png", "style" to _nS(_uM("height" to "20rpx", "width" to "20rpx", "position" to "absolute", "left" to "6rpx")), "mode" to ""), null, 4)
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                    ,
                                                    if (isTrue(item.englishProgress?.isFinish == "0" && parseInt(item.englishProgress?.englishExercisePassScore as String) > 0)) {
                                                        _cE("text", _uM("key" to 1, "style" to _nS(_uM("position" to "absolute", "left" to "6rpx", "color" to "#FA9600"))), _tD(item.englishProgress?.englishExercisePassScore), 5)
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                    ,
                                                    _cE("text", _uM("class" to "word_table-body-item-text word_table-body-item-text-unit"), _tD(item.unitName2), 1)
                                                ), 4),
                                                _cE("view", _uM("class" to "word_table-body-item word_table-body-item-progress-box"), _uA(
                                                    if (item.englishProgress?.isFinish == "1") {
                                                        _cE("view", _uM("key" to 0, "class" to "word_table-body-item-progress"), _uA(
                                                            _cV(_component_progress, _uM("class" to "word_table-body-item-progress-progress", "border-radius" to 100, "percent" to 100, "stroke-width" to 7, "activeColor" to "#5B77FF", "backgroundColor" to "#E2E6F6")),
                                                            _cE("text", _uM("class" to "word_table-body-item-text"), _tD(item.englishProgress?.englishTotalProgressValue) + " / " + _tD(item.englishProgress?.englishTotalProgressValue), 1)
                                                        ))
                                                    } else {
                                                        _cE("view", _uM("key" to 1, "class" to "word_table-body-item-progress"), _uA(
                                                            _cV(_component_progress, _uM("class" to "word_table-body-item-progress-progress", "border-radius" to 100, "percent" to ((item.englishProgress?.englishCurrentProgressValue ?: 0) / (item.englishProgress?.englishTotalProgressValue ?: 0) * 100).toInt(), "stroke-width" to 7, "activeColor" to "#5B77FF", "backgroundColor" to "#E2E6F6"), null, 8, _uA(
                                                                "percent"
                                                            )),
                                                            _cE("text", _uM("class" to "word_table-body-item-text"), _tD(item.englishProgress?.englishCurrentProgressValue) + " / " + _tD(item.englishProgress?.englishTotalProgressValue), 1)
                                                        ))
                                                    }
                                                )),
                                                _cE("view", _uM("class" to "word_table-body-item word_table-body-item-status-box"), _uA(
                                                    if (isTrue(item?.englishProgress?.isFinish == "0" && item?.englishProgress?.englishCurrentProgressValue == 0)) {
                                                        _cE("view", _uM("key" to 0, "class" to "start-button", "onClick" to fun(){
                                                            startStudy(item)
                                                        }), _uA(
                                                            _cE("text", _uM("class" to "text"), "开始训练")
                                                        ), 8, _uA(
                                                            "onClick"
                                                        ))
                                                    } else {
                                                        _cE("view", _uM("key" to 1, "class" to "continue-button", "onClick" to fun(){
                                                            startStudy(item)
                                                        }
                                                        ), _uA(
                                                            _cE("text", _uM("class" to "text"), "继续训练")
                                                        ), 8, _uA(
                                                            "onClick"
                                                        ))
                                                    }
                                                ))
                                            ), 4)
                                        }
                                        ), 128),
                                        if (unref(showList)?.length == 0) {
                                            _cV(_component_no_data, _uM("key" to 0))
                                        } else {
                                            _cC("v-if", true)
                                        }
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "marginTop" to "13.48rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "d_left" to _uM(".d_content " to _uM("height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginRight" to "5.86rpx", "paddingTop" to "17.58rpx", "paddingRight" to "9.96rpx", "paddingBottom" to "17.58rpx", "paddingLeft" to "9.96rpx")), "d_left_top" to _uM(".d_content .d_left " to _uM("flexDirection" to "row")), "d_left_title" to _uM(".d_content .d_left " to _uM("fontWeight" to "700", "fontSize" to "14rpx", "color" to "#3D3D3D", "marginLeft" to "6.45rpx")), "unit_list" to _uM(".d_content .d_left " to _uM("marginTop" to "15.82rpx", "height" to "281rpx")), "unit_item" to _uM(".d_content .d_left .unit_list " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "unit_item_title" to _uM(".d_content .d_left .unit_list .unit_item " to _uM("fontWeight" to "400", "fontSize" to "11.72rpx", "flex" to 1, "marginLeft" to "6.45rpx")), "unit_item_count" to _uM(".d_content .d_left .unit_list .unit_item " to _uM("fontWeight" to "400", "fontSize" to "11.72rpx", "color" to "#8BA0C4", "lineHeight" to "35rpx")), "d_right" to _uM(".d_content " to _uM("flex" to 1, "width" to "100%", "height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx")), "word_table" to _uM(".d_content .d_right " to _uM("marginTop" to "7.62rpx", "marginRight" to "7.62rpx", "marginBottom" to "7.62rpx", "marginLeft" to "7.62rpx", "width" to "700rpx")), "title_box" to _pS(_uM("width" to "234rpx", "height" to "28rpx", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "marginLeft" to "196.29rpx")), "_text" to _uM(".title_box " to _uM("textAlign" to "center", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#535D8C", "lineHeight" to "28rpx")), "search_box" to _pS(_uM("width" to "210rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "boxShadow" to "0rpx 2rpx 4rpx 0rpx rgba(130, 141, 197, 0.3)", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "search_ico" to _uM(".search_box " to _uM("width" to "10rpx", "height" to "10rpx", "marginLeft" to "12rpx")), "search_input" to _uM(".search_box " to _uM("flex" to 1, "height" to "100%", "fontSize" to "14rpx", "color" to "#3a3a3a", "marginTop" to 0, "marginRight" to "12rpx", "marginBottom" to 0, "marginLeft" to "12rpx")), "d_top" to _pS(_uM("paddingTop" to "13.48rpx", "paddingRight" to "16.41rpx", "paddingBottom" to "13.48rpx", "paddingLeft" to "16.41rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "d_top_text" to _uM(".d_top " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D")), "d_top_text_num" to _uM(".d_top " to _uM("fontSize" to "12rpx", "color" to "#5A9F32")), "d_top_text_tip" to _uM(".d_top " to _uM("fontSize" to "12rpx", "color" to "#98A6EE")), "d_top_btn_group" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "d_top_btn" to _pS(_uM("marginLeft" to "5.27rpx", "width" to "67rpx", "height" to "25rpx", "borderTopLeftRadius" to "21rpx", "borderTopRightRadius" to "21rpx", "borderBottomRightRadius" to "21rpx", "borderBottomLeftRadius" to "21rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "backgroundImage" to "linear-gradient(to bottom, #F3F7FF, #D5DFF0)", "backgroundColor" to "rgba(0,0,0,0)")), "d_top_btn_text" to _uM(".d_top_btn " to _uM("fontWeight" to "400", "fontSize" to "12rpx", "color" to "#98A6EE")), "d_top_btn_text_active" to _uM(".d_top_btn " to _uM("color" to "#3A58EB")), "d_top_btn_ico" to _uM(".d_top_btn " to _uM("width" to "9.4rpx", "height" to "9.4rpx", "marginRight" to "4.1rpx")), "word_table-header" to _uM(".word_table " to _uM("height" to "35.16rpx", "backgroundColor" to "#F1F5FC", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-header-item-text" to _uM(".word_table " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D")), "word_table-body-row" to _uM(".word_table " to _uM("height" to "35.16rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body" to _uM(".word_table " to _uM("height" to "220rpx")), "word_table-header-item" to _uM(".word_table .word_table-header " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center"), ".word_table .word_table-body-row " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item" to _uM(".word_table .word_table-header " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center"), ".word_table .word_table-body-row " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item-progress-box" to _uM(".word_table " to _uM("flex" to 1.5)), "word_table-header-item-progress-box" to _uM(".word_table " to _uM("flex" to 1.5)), "word_table-body-item-progress" to _uM(".word_table " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item-progress-progress" to _uM(".word_table " to _uM("width" to "160rpx", "height" to "7rpx", "marginRight" to "12.3rpx")), "word_table-body-item-text" to _uM(".word_table " to _uM("fontFamily" to "Arial, Arial", "fontWeight" to "400", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx")), "word_table-body-item-text-unit" to _uM(".word_table " to _uM("color" to "#3A58EB")), "word_table-body-item-count-box" to _uM(".word_table " to _uM("flex" to 0.5)), "word_table-header-item-count-box" to _uM(".word_table " to _uM("flex" to 0.5)), "word_table-body-item-unit-box" to _uM(".word_table " to _uM("flex" to 1)), "word_table-header-item-unit-box" to _uM(".word_table " to _uM("flex" to 1)), "word_table-body-item-status-box" to _uM(".word_table " to _uM("flex" to 1)), "word_table-header-item-status-box" to _uM(".word_table " to _uM("flex" to 1)), "start-button" to _uM(".word_table .word_table-body-item-status-box " to _uM("width" to "65rpx", "height" to "23rpx", "borderTopLeftRadius" to "999rpx", "borderTopRightRadius" to "999rpx", "borderBottomRightRadius" to "999rpx", "borderBottomLeftRadius" to "999rpx", "justifyContent" to "center", "alignItems" to "center", "backgroundColor" to "#FA9600")), "continue-button" to _uM(".word_table .word_table-body-item-status-box " to _uM("width" to "65rpx", "height" to "23rpx", "borderTopLeftRadius" to "999rpx", "borderTopRightRadius" to "999rpx", "borderBottomRightRadius" to "999rpx", "borderBottomLeftRadius" to "999rpx", "justifyContent" to "center", "alignItems" to "center", "backgroundColor" to "#ECEFFA")), "text" to _uM(".word_table .word_table-body-item-status-box .start-button " to _uM("color" to "#ffffff"), ".word_table .word_table-body-item-status-box .continue-button " to _uM("color" to "#818DCA")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
