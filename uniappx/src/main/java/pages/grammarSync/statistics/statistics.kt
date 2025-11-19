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
open class GenPagesGrammarSyncStatisticsStatistics : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesGrammarSyncStatisticsStatistics) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesGrammarSyncStatisticsStatistics
            val _cache = __ins.renderCache
            val unitTitle = ref<String>("挑战统计")
            val starUpToLevelIcoMap: UTSJSONObject = object : UTSJSONObject() {
                var `1` = "/static/ico/dstars1.png"
                var `2` = "/static/ico/dstars2.png"
                var `3` = "/static/ico/dstars3.png"
                var `4` = "/static/ico/dstars4.png"
                var `5` = "/static/ico/dstars5.png"
            }
            val grammarTrainStat = ref<GrammarTrainStat>(GrammarTrainStat(totalExerciseRecordNum = 0, totalPassExerciseRecordNum = 0, passRate = "0", starCurrentProgressValue = 0))
            val pagingX = ref(null)
            val dataList = ref(_uA<UserExerciseRecordVo>())
            val queryParams = ref(object : UTSJSONObject() {
                var textbookId: Number = 0
                var textbookUnitId: Number = 0
                var module = "0"
                var subModule = "0"
                var pageNum: Number = 1
                var pageSize: Number = 10
            })
            val secondToTimeStr = fun(second: Number?): String {
                return if (second != null) {
                    formatTime(second!!, "HH:MM:SS")
                } else {
                    "00:00:"
                }
            }
            fun gen_goTest_fn(item: UserExerciseRecordVo) {
                uni_navigateTo(NavigateToOptions(url = "/pages/examRecords/examRecords?resultId=" + item.id + "&isYF=1"))
            }
            val goTest = ::gen_goTest_fn
            fun gen_queryList_fn(pageNo: Number, pageSize: Number) {
                queryParams.value["pageNum"] = pageNo
                queryParams.value["pageSize"] = pageSize
                uni_request<Result<UTSArray<UserExerciseRecordVo>>>(RequestOptions(url = getUrl("/biz/textbookUnit/api/userExerciseRecord/list"), method = "GET", header = getHeader(), data = queryParams.value, success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    var data = responseData.data
                    (pagingX.value as ZPagingXComponentPublicInstance).complete(data!! as UTSArray<Any>)
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val queryList = ::gen_queryList_fn
            fun gen_getGrammarTrainStat_fn() {
                uni_request<Result<GrammarTrainStat?>>(RequestOptions(url = getUrl("/biz/textbookUnit/api/getGrammarTrainStat"), method = "GET", data = queryParams.value, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    grammarTrainStat.value = responseData.data as GrammarTrainStat
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getGrammarTrainStat = ::gen_getGrammarTrainStat_fn
            onLoad(fun(e){
                queryParams.value["textbookId"] = parseInt(if (e["textbookId"] != null) {
                    e["textbookId"]!!
                } else {
                    "0"
                }
                )
                queryParams.value["textbookUnitId"] = parseInt(if (e["textbookUnitId"] != null) {
                    e["textbookUnitId"]!!
                } else {
                    "0"
                }
                )
                queryParams.value["module"] = if (e["module"] != null) {
                    e["module"]!!
                } else {
                    "0"
                }
                queryParams.value["subModule"] = if (e["subModule"] != null) {
                    e["subModule"]!!
                } else {
                    "0"
                }
                unitTitle.value = if (e["unitTitle"] != null) {
                    e["unitTitle"]!!
                } else {
                    "挑战统计"
                }
                getGrammarTrainStat()
            }
            )
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_z_paging_x = resolveEasyComponent("z-paging-x", GenUniModulesZPagingXComponentsZPagingXZPagingXClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to unref(unitTitle)), null, 8, _uA(
                            "title"
                        )),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "left_nav"), _uA(
                                _cE("view", _uM("class" to "top_num"), _uA(
                                    _cE("view", _uM("class" to "top_num_item"), _uA(
                                        _cE("text", _uM("class" to "top_num_item_n"), _tD(unref(grammarTrainStat).totalExerciseRecordNum), 1),
                                        _cE("text", _uM("class" to "top_num_item_t"), "总挑战次数")
                                    )),
                                    _cE("view", _uM("class" to "top_num_item"), _uA(
                                        _cE("text", _uM("class" to "top_num_item_n"), _tD(unref(grammarTrainStat).totalPassExerciseRecordNum), 1),
                                        _cE("text", _uM("class" to "top_num_item_t"), "挑战成功数")
                                    )),
                                    _cE("view", _uM("class" to "top_num_item", "style" to _nS(_uM("margin-top" to "10rpx"))), _uA(
                                        _cE("text", _uM("class" to "top_num_item_n"), _tD(unref(grammarTrainStat).passRate) + "%", 1),
                                        _cE("text", _uM("class" to "top_num_item_t"), "挑战成功率")
                                    ), 4)
                                )),
                                _cE("text", _uM("class" to "l_title"), " 挑战进度 "),
                                _cE("view", _uM("class" to "level_list"), _uA(
                                    _cE("view", _uM("class" to "level_item"), _uA(
                                        _cE("image", _uM("src" to if (unref(grammarTrainStat).starCurrentProgressValue > 0) {
                                            "/static/ico/dstars1.png"
                                        } else {
                                            "/static/ico/dstars1_fail.png"
                                        }
                                        , "mode" to "", "style" to _nS(_uM("width" to "29.3rpx", "height" to "33.98rpx"))), null, 12, _uA(
                                            "src"
                                        )),
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "level_item_text",
                                            _uM("level_item_text_active" to (unref(grammarTrainStat).starCurrentProgressValue > 0))
                                        ))), " 1星列兵 ", 2)
                                    )),
                                    _cE("view", _uM("class" to "level_item"), _uA(
                                        _cE("image", _uM("src" to if (unref(grammarTrainStat).starCurrentProgressValue > 1) {
                                            "/static/ico/dstars2.png"
                                        } else {
                                            "/static/ico/dstars2_fail.png"
                                        }
                                        , "mode" to "", "style" to _nS(_uM("width" to "29.3rpx", "height" to "33.98rpx"))), null, 12, _uA(
                                            "src"
                                        )),
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "level_item_text",
                                            _uM("level_item_text_active" to (unref(grammarTrainStat).starCurrentProgressValue > 1))
                                        ))), " 2星士官 ", 2)
                                    )),
                                    _cE("view", _uM("class" to "level_item"), _uA(
                                        _cE("image", _uM("src" to if (unref(grammarTrainStat).starCurrentProgressValue > 2) {
                                            "/static/ico/dstars3.png"
                                        } else {
                                            "/static/ico/dstars3_fail.png"
                                        }
                                        , "mode" to "", "style" to _nS(_uM("width" to "29.3rpx", "height" to "33.98rpx"))), null, 12, _uA(
                                            "src"
                                        )),
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "level_item_text",
                                            _uM("level_item_text_active" to (unref(grammarTrainStat).starCurrentProgressValue > 2))
                                        ))), " 3星少尉 ", 2)
                                    )),
                                    _cE("view", _uM("class" to "level_item"), _uA(
                                        _cE("image", _uM("src" to if (unref(grammarTrainStat).starCurrentProgressValue > 3) {
                                            "/static/ico/dstars4.png"
                                        } else {
                                            "/static/ico/dstars4_fail.png"
                                        }
                                        , "mode" to "", "style" to _nS(_uM("width" to "29.3rpx", "height" to "33.98rpx"))), null, 12, _uA(
                                            "src"
                                        )),
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "level_item_text",
                                            _uM("level_item_text_active" to (unref(grammarTrainStat).starCurrentProgressValue > 3))
                                        ))), " 4星中校 ", 2)
                                    )),
                                    _cE("view", _uM("class" to "level_item"), _uA(
                                        _cE("image", _uM("src" to if (unref(grammarTrainStat).starCurrentProgressValue > 4) {
                                            "/static/ico/dstars5.png"
                                        } else {
                                            "/static/ico/dstars5_fail.png"
                                        }
                                        , "mode" to "", "style" to _nS(_uM("width" to "29.3rpx", "height" to "33.98rpx"))), null, 12, _uA(
                                            "src"
                                        )),
                                        _cE("text", _uM("class" to _nC(_uA(
                                            "level_item_text",
                                            _uM("level_item_text_active" to (unref(grammarTrainStat).starCurrentProgressValue > 4))
                                        ))), " 5星上将 ", 2)
                                    ))
                                ))
                            )),
                            _cE("view", _uM("class" to "right_content"), _uA(
                                _cE("view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
                                    _cE("view", _uM("class" to "word_table"), _uA(
                                        _cE("view", _uM("class" to "word_table-header"), _uA(
                                            _cE("view", _uM("class" to "word_table-header-item"), _uA(
                                                _cE("text", _uM("class" to "word_table-header-item-text"), "挑战结果")
                                            )),
                                            _cE("view", _uM("class" to "word_table-header-item"), _uA(
                                                _cE("text", _uM("class" to "word_table-header-item-text"), "挑战时长")
                                            )),
                                            _cE("view", _uM("class" to "word_table-header-item"), _uA(
                                                _cE("text", _uM("class" to "word_table-header-item-text"), "挑战得分")
                                            )),
                                            _cE("view", _uM("class" to "word_table-header-item", "style" to _nS(_uM("min-width" to "70rpx"))), _uA(
                                                _cE("text", _uM("class" to "word_table-header-item-text"), "挑战时间")
                                            ), 4)
                                        )),
                                        _cE("view", _uM("class" to "content"), _uA(
                                            _cV(_component_z_paging_x, _uM("ref_key" to "pagingX", "ref" to pagingX, "modelValue" to unref(dataList), "onUpdate:modelValue" to fun(`$event`: UTSArray<UserExerciseRecordVo>){
                                                trySetRefValue(dataList, `$event`)
                                            }
                                            , "onQuery" to queryList, "class" to "pagingX", "refresher-enabled" to false), _uM("top" to withSlotCtx(fun(): UTSArray<Any> {
                                                return _uA()
                                            }
                                            ), "loadMore" to withScopedSlotCtx(fun(slotProps: Record<String, Any?>): UTSArray<Any> {
                                                val loadMoreStatus = slotProps["loadMoreStatus"]
                                                return _uA(
                                                    _cE("view", _uM("class" to "loadMore"), _uA(
                                                        if (loadMoreStatus == 1) {
                                                            _cE("text", _uM("key" to 0, "class" to "loadMore_text"), "加载中...")
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                        ,
                                                        if (loadMoreStatus == 2) {
                                                            _cE("text", _uM("key" to 1, "class" to "loadMore_text"), "没有更多数据了")
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                    ))
                                                )
                                            }
                                            ), "empty" to withScopedSlotCtx(fun(slotProps: Record<String, Any?>): UTSArray<Any> {
                                                val isLoadFailed = slotProps["isLoadFailed"]
                                                return _uA(
                                                    _cE("view", _uM("class" to "z-pagng-empty"), _uA(
                                                        if (isLoadFailed == true) {
                                                            _cE("text", _uM("key" to 0, "class" to "loadMore_text"), "加载失败")
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                        ,
                                                        if (isLoadFailed == false) {
                                                            _cE("text", _uM("key" to 1, "class" to "loadMore_text"), "暂无数据")
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                    ))
                                                )
                                            }
                                            ), "default" to withSlotCtx(fun(): UTSArray<Any> {
                                                return _uA(
                                                    _cE(Fragment, null, RenderHelpers.renderList(unref(dataList), fun(item, index, __index, _cached): Any {
                                                        return _cE("list-item", _uM("key" to index), _uA(
                                                            _cE("view", _uM("class" to "word_table-body-row", "onClick" to fun(){
                                                                goTest(item)
                                                            }
                                                            , "style" to _nS("" + (if (((index + 1) % 2) == 0) {
                                                                "background-color: #F1F5FC"
                                                            } else {
                                                                ""
                                                            }
                                                            ))), _uA(
                                                                _cE("view", _uM("class" to "word_table-body-item"), _uA(
                                                                    if (isTrue(item.isPass == "1" && item.starUpToLevel == null)) {
                                                                        _cE("image", _uM("key" to 0, "src" to "/static/ico/success_ico.png", "style" to _nS(_uM("width" to "23.44rpx", "height" to "22.27rpx")), "mode" to ""), null, 4)
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    }
                                                                    ,
                                                                    if (item.starUpToLevel != null) {
                                                                        _cE("image", _uM("key" to 1, "src" to starUpToLevelIcoMap.getString(item.starUpToLevel!!.toString(10)), "style" to _nS(_uM("width" to "24.61rpx", "height" to "28.71rpx")), "mode" to ""), null, 12, _uA(
                                                                            "src"
                                                                        ))
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    }
                                                                    ,
                                                                    if (item.isPass == "0") {
                                                                        _cE("image", _uM("key" to 2, "src" to "/static/ico/fail_ico.png", "style" to _nS(_uM("width" to "21.68rpx", "height" to "22.27rpx")), "mode" to ""), null, 4)
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    }
                                                                    ,
                                                                    if (isTrue(item.isPass == "1" && item.starUpToLevel == null)) {
                                                                        _cE("text", _uM("key" to 3, "class" to "word_table-body-item-text", "style" to _nS(_uM("color" to "#5A9F32"))), "挑战成功", 4)
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    }
                                                                    ,
                                                                    if (item.starUpToLevel != null) {
                                                                        _cE("text", _uM("key" to 4, "class" to "word_table-body-item-text", "style" to _nS(_uM("color" to "#FA9600"))), "进阶成功", 4)
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    }
                                                                    ,
                                                                    if (item.isPass == "0") {
                                                                        _cE("text", _uM("key" to 5, "class" to "word_table-body-item-text", "style" to _nS(_uM("color" to "#E54E4E"))), "挑战失败", 4)
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    }
                                                                )),
                                                                _cE("view", _uM("class" to "word_table-body-item"), _uA(
                                                                    _cE("text", _uM("class" to "word_table-body-item-text"), _tD(secondToTimeStr(item.exerciseSecond)), 1)
                                                                )),
                                                                _cE("view", _uM("class" to "word_table-body-item"), _uA(
                                                                    if (item.isPass == "0") {
                                                                        _cE("text", _uM("key" to 0, "class" to "word_table-body-item-text", "style" to _nS(_uM("color" to "#E54E4E"))), _tD(item.score!!), 5)
                                                                    } else {
                                                                        _cE("text", _uM("key" to 1, "class" to "word_table-body-item-text", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(item.score!!), 5)
                                                                    }
                                                                )),
                                                                _cE("view", _uM("class" to "word_table-body-item", "style" to _nS(_uM("min-width" to "70rpx"))), _uA(
                                                                    _cE("text", _uM("class" to "word_table-body-item-text"), _tD(item.exerciseStartTime), 1)
                                                                ), 4)
                                                            ), 12, _uA(
                                                                "onClick"
                                                            ))
                                                        ))
                                                    }
                                                    ), 128)
                                                )
                                            }
                                            ), "_" to 1), 8, _uA(
                                                "modelValue"
                                            ))
                                        ))
                                    ))
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "340rpx", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "10rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "flexDirection" to "row")), "left_nav" to _uM(".d_content " to _uM("width" to "185rpx", "backgroundImage" to "none", "backgroundColor" to "rgba(255,255,255,0.2)", "paddingTop" to "14.06rpx", "paddingRight" to "12.3rpx", "paddingBottom" to "14.06rpx", "paddingLeft" to "12.3rpx")), "top_num" to _uM(".d_content .left_nav " to _uM("width" to "100%", "flexDirection" to "row", "flexWrap" to "wrap")), "top_num_item" to _uM(".d_content .left_nav .top_num " to _uM("width" to "50%", "alignItems" to "center")), "top_num_item_n" to _uM(".d_content .left_nav .top_num .top_num_item " to _uM("fontSize" to "18rpx", "color" to "#FFFFFF", "lineHeight" to "20rpx", "letterSpacing" to 3, "fontWeight" to "bold")), "top_num_item_t" to _uM(".d_content .left_nav .top_num .top_num_item " to _uM("fontSize" to "11rpx", "color" to "#FFFFFF", "lineHeight" to "20rpx")), "l_title" to _uM(".d_content .left_nav " to _uM("fontSize" to "11rpx", "color" to "#315186", "lineHeight" to "18rpx", "textAlign" to "center", "fontWeight" to "bold")), "level_list" to _uM(".d_content .left_nav " to _uM("marginTop" to "5.86rpx")), "level_item" to _uM(".d_content .left_nav .level_list " to _uM("backgroundImage" to "none", "backgroundColor" to "rgba(255,255,255,0.7)", "width" to "159rpx", "height" to "38rpx", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "marginBottom" to "4rpx")), "level_item_text" to _uM(".d_content .left_nav .level_list .level_item " to _uM("width" to "75rpx", "height" to "21rpx", "backgroundImage" to "none", "backgroundColor" to "#E8E8E8", "borderTopLeftRadius" to "33rpx", "borderTopRightRadius" to "33rpx", "borderBottomRightRadius" to "33rpx", "borderBottomLeftRadius" to "33rpx", "fontWeight" to "bold", "fontSize" to "12rpx", "color" to "#8D8D8D", "lineHeight" to "21rpx", "textAlign" to "center", "marginLeft" to "7.62rpx")), "level_item_text_active" to _uM(".d_content .left_nav .level_list .level_item " to _uM("!color" to "#674800", "!backgroundImage" to "none", "!backgroundColor" to "#FCF3D6")), "right_content" to _uM(".d_content " to _uM("width" to "530rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "position" to "relative")), "word_table" to _pS(_uM("flex" to 1, "paddingTop" to "10rpx", "paddingRight" to 0, "paddingBottom" to "10rpx", "paddingLeft" to 0)), "word_table-header" to _uM(".word_table " to _uM("height" to "35.16rpx", "backgroundColor" to "#F1F5FC", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to "20rpx", "paddingRight" to 0, "paddingBottom" to "20rpx", "paddingLeft" to 0)), "word_table-header-item-text" to _uM(".word_table " to _uM("fontWeight" to "700", "fontSize" to "12rpx", "color" to "#3D3D3D", "marginLeft" to "6.45rpx")), "word_table-body-row" to _uM(".word_table " to _uM("height" to "35.16rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to "20rpx", "paddingRight" to 0, "paddingBottom" to "20rpx", "paddingLeft" to 0)), "word_table-body" to _uM(".word_table " to _uM("flex" to 1)), "word_table-header-item" to _uM(".word_table .word_table-header " to _uM("flexDirection" to "row", "flex" to 1, "alignItems" to "center", "justifyContent" to "center"), ".word_table .word_table-body-row " to _uM("flexDirection" to "row", "flex" to 1, "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item" to _uM(".word_table .word_table-header " to _uM("flexDirection" to "row", "flex" to 1, "alignItems" to "center", "justifyContent" to "center"), ".word_table .word_table-body-row " to _uM("flexDirection" to "row", "flex" to 1, "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item-text" to _uM(".word_table " to _uM("fontFamily" to "Arial, Arial", "fontWeight" to "400", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx", "marginLeft" to "10rpx")), "content" to _pS(_uM("flex" to 1)), "z-pagng-empty" to _pS(_uM("justifyContent" to "center", "alignItems" to "center", "height" to "100%", "width" to "100%")), "loadMore" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "center", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx")), "loadMore_text" to _pS(_uM("color" to "#6694DF", "fontFamily" to "Arial, Arial", "fontSize" to "13rpx", "textAlign" to "left", "fontStyle" to "normal")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
