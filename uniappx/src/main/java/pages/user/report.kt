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
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesUserReport : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesUserReport) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesUserReport
            val _cache = __ins.renderCache
            val nickName = ref(ucsShare.getState("userName"))
            val info = ref<StudyReportType?>(null)
            val readTrainStatInfo = ref<readTrainStat?>(null)
            val grammarConutInfo = ref<grammar?>(null)
            fun gen_getReversedModelKeyFn_fn(key: String): String {
                return getReversedSubModelKey(key)
            }
            val getReversedModelKeyFn = ::gen_getReversedModelKeyFn_fn
            fun gen_getSubItem_fn(arr: UTSArray<UnitGrammarTrainStatList>, key: String): UnitGrammarTrainStatList? {
                val item = arr.filter(fun(item: UnitGrammarTrainStatList): Boolean {
                    return item.textbookUnitSubModule == getSubModelKey(key)
                }
                )
                if (item.length == 0) {
                    return null as UnitGrammarTrainStatList?
                }
                return item[0]
            }
            val getSubItem = ::gen_getSubItem_fn
            fun gen_getBcSubItem_fn(arr: UTSArray<ProblemTypeStatList>, key: String): ProblemTypeStatList? {
                val item = arr.filter(fun(item: ProblemTypeStatList): Boolean {
                    return item.problemType == key
                }
                )
                if (item.length == 0) {
                    return null as ProblemTypeStatList?
                }
                return item[0]
            }
            val getBcSubItem = ::gen_getBcSubItem_fn
            fun gen_exTBYD_fn() {
                var tz_conut: Number = 0
                var tz_conutSussce: Number = 0
                var dy_conut: Number = 0
                var dy_conutSussce: Number = 0
                info.value?.readTrainStatList?.forEach(fun(item: readTrainStatList){
                    dy_conut++
                    if (item.isFinish == "1") {
                        dy_conutSussce++
                    }
                    item.problemTypeStatList.forEach(fun(sub: ProblemTypeStatList){
                        tz_conut += sub.problemTypeExerciseNum
                        tz_conutSussce += sub.problemTypeExercisePassNum
                    }
                    )
                }
                )
                var rate = if (tz_conut > 0) {
                    Math.round((tz_conutSussce / tz_conut) * 10000) / 100
                } else {
                    0
                }
                readTrainStatInfo.value = readTrainStat(tz_conut = tz_conut, tz_conutSussce = tz_conutSussce, dy_conut = dy_conut, dy_conutSussce = dy_conutSussce, rate = rate)
            }
            val exTBYD = ::gen_exTBYD_fn
            fun gen_exTBYF_fn() {
                var tz_conut: Number = 0
                var tz_conutSussce: Number = 0
                var star0ExerciseRecordNum: Number = 0
                var star1ExerciseRecordNum: Number = 0
                var star2ExerciseRecordNum: Number = 0
                var star3ExerciseRecordNum: Number = 0
                var star4ExerciseRecordNum: Number = 0
                info.value?.grammarTrainStatList?.forEach(fun(item: GrammarTrainStatList){
                    tz_conut += item.totalExerciseRecordNum
                    tz_conutSussce += item.totalPassExerciseRecordNum
                    star0ExerciseRecordNum += item.star0ExerciseRecordNum
                    star1ExerciseRecordNum += item.star1ExerciseRecordNum
                    star2ExerciseRecordNum += item.star2ExerciseRecordNum
                    star3ExerciseRecordNum += item.star3ExerciseRecordNum
                    star4ExerciseRecordNum += item.star4ExerciseRecordNum
                }
                )
                var rate = if (tz_conut > 0) {
                    Math.round((tz_conutSussce / tz_conut) * 10000) / 100
                } else {
                    0
                }
                grammarConutInfo.value = grammar(tz_conut = tz_conut, tz_conutSussce = tz_conutSussce, rate = rate, star0ExerciseRecordNum = star0ExerciseRecordNum, star1ExerciseRecordNum = star1ExerciseRecordNum, star2ExerciseRecordNum = star2ExerciseRecordNum, star3ExerciseRecordNum = star3ExerciseRecordNum, star4ExerciseRecordNum = star4ExerciseRecordNum)
            }
            val exTBYF = ::gen_exTBYF_fn
            fun gen_getInfo_fn() {
                uni_request<Result<StudyReportType>>(RequestOptions(url = getUrl("/biz/textbook/api/getTextbookReport"), method = "GET", header = getHeader(), data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                }, success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    info.value = responseData.data
                    exTBYD()
                    exTBYF()
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getInfo = ::gen_getInfo_fn
            onLoad(fun(_options){
                getInfo()
            }
            )
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_progress = resolveComponent("progress")
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "学习报告")),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cV(_component_BackgroundImage, _uM("src" to "/static/ico/left.png", "className" to "title-box", "imgClassName" to "title-bg"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("text", _uM("class" to "title"), _tD(unref(info)?.versionBaseDataName) + "-" + _tD(unref(info)?.textbookName) + "（" + _tD(unref(nickName)) + "）", 1)
                                )
                            }
                            ), "_" to 1)),
                            _cE("scroll-view", _uM("class" to "d_scroll_view"), _uA(
                                _cE("view", _uM("class" to "module-item"), _uA(
                                    _cE("view", _uM("class" to "module-item-title-box"), _uA(
                                        _cE("view", _uM("class" to "module-item-title-per")),
                                        _cE("text", _uM("class" to "module-item-title"), "同步单词")
                                    )),
                                    _cE("view", _uM("class" to "module-item-box"), _uA(
                                        _cE("view", _uM("class" to "module-table-header"), _uA(
                                            _cE("text", _uM("class" to "header-title"), "模块"),
                                            _cE("text", _uM("class" to "header-title flex2"), "进度（已学单词/总单词）"),
                                            _cE("text", _uM("class" to "header-title flex2", "style" to _nS(_uM("text-align" to "center"))), "学习通过单词/未通过单词", 4)
                                        )),
                                        _cE("view", _uM("class" to "module-table-body"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(info)?.wordStatList, fun(item, __key, __index, _cached): Any {
                                                return _cE("view", _uM("class" to "module-table-body-row"), _uA(
                                                    _cE("view", _uM("class" to "body-row-item"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(getReversedModelKeyFn(item.textbookUnitSubModule)), 1)
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flex2 progress-item"), _uA(
                                                        _cE("view", _uM("style" to _nS(_uM("width" to "100rpx", "margin-right" to "20rpx"))), _uA(
                                                            _cV(_component_progress, _uM("border-radius" to 100, "percent" to parseInt(item.rate), "stroke-width" to 6, "activeColor" to "#5B77FF", "backgroundColor" to "#E2E6F6"), null, 8, _uA(
                                                                "percent"
                                                            ))
                                                        ), 4),
                                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                                            _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("width" to "50rpx"))), _tD(item.rate) + "%", 5),
                                                            _cE("text", _uM("class" to "module-row-text1"), "（" + _tD(item.totalEnglishMaxProgressValue) + "/" + _tD(item.totalProblemNum) + "）", 1)
                                                        ), 4)
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flex2 flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(item.totalEnglishPassNum) + "/", 1),
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#E54E4E"))), _tD(item.totalEnglishFailNum), 5)
                                                    ))
                                                ))
                                            }
                                            ), 256)
                                        ))
                                    ))
                                )),
                                _cE("view", _uM("class" to "module-item"), _uA(
                                    _cE("view", _uM("class" to "module-item-title-box"), _uA(
                                        _cE("view", _uM("class" to "module-item-title-per")),
                                        _cE("text", _uM("class" to "module-item-title"), "同步句子")
                                    )),
                                    _cE("view", _uM("class" to "module-item-box"), _uA(
                                        _cE("view", _uM("class" to "module-table-header"), _uA(
                                            _cE("text", _uM("class" to "header-title"), "模块"),
                                            _cE("text", _uM("class" to "header-title flex2"), "进度（已学单词/总单词）"),
                                            _cE("text", _uM("class" to "header-title flex2", "style" to _nS(_uM("text-align" to "center"))), "学习通过单词/未通过单词", 4)
                                        )),
                                        _cE("view", _uM("class" to "module-table-body"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(info)?.sentenceStatList, fun(item, __key, __index, _cached): Any {
                                                return _cE("view", _uM("class" to "module-table-body-row"), _uA(
                                                    _cE("view", _uM("class" to "body-row-item"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(getReversedModelKeyFn(item.textbookUnitSubModule)), 1)
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flex2 progress-item"), _uA(
                                                        _cE("view", _uM("style" to _nS(_uM("width" to "100rpx", "margin-right" to "20rpx"))), _uA(
                                                            _cV(_component_progress, _uM("border-radius" to 100, "percent" to parseInt(item.rate), "stroke-width" to 6, "activeColor" to "#5B77FF", "backgroundColor" to "#E2E6F6"), null, 8, _uA(
                                                                "percent"
                                                            ))
                                                        ), 4),
                                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                                            _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("width" to "50rpx"))), _tD(item.rate) + "%", 5),
                                                            _cE("text", _uM("class" to "module-row-text1"), "（" + _tD(item.totalEnglishMaxProgressValue) + "/" + _tD(item.totalProblemNum) + "）", 1)
                                                        ), 4)
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flex2 flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(item.totalEnglishPassNum) + "/", 1),
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#E54E4E"))), _tD(item.totalEnglishFailNum), 5)
                                                    ))
                                                ))
                                            }
                                            ), 256)
                                        ))
                                    ))
                                )),
                                _cE("view", _uM("class" to "module-item"), _uA(
                                    _cE("view", _uM("class" to "module-item-title-box"), _uA(
                                        _cE("view", _uM("class" to "module-item-title-per")),
                                        _cE("text", _uM("class" to "module-item-title"), "同步语法训练")
                                    )),
                                    _cE("view", _uM("class" to "module-item-box"), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "margin-bottom" to "20rpx", "align-items" to "center", "justify-content" to "space-between"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                _cE("text", _uM("class" to "subTitle", "style" to _nS(_uM("margin-right" to "40rpx"))), "挑战成功/总次数：" + _tD(unref(grammarConutInfo)?.tz_conutSussce) + "/" + _tD(unref(grammarConutInfo)?.tz_conut), 5),
                                                _cE("text", _uM("class" to "subTitle"), "正确率：" + _tD(unref(grammarConutInfo)?.rate ?: 0) + "%", 1)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                _cE("image", _uM("src" to "/static/ico/dstars1.png", "style" to _nS(_uM("width" to "23.44rpx", "height" to "27.19rpx", "margin-right" to "2rpx")), "mode" to ""), null, 4),
                                                _cE("text", _uM("class" to "subTitle", "style" to _nS(_uM("margin-right" to "16rpx"))), _tD(unref(grammarConutInfo)?.star0ExerciseRecordNum), 5),
                                                _cE("image", _uM("src" to "/static/ico/dstars2.png", "style" to _nS(_uM("width" to "23.44rpx", "height" to "27.19rpx", "margin-right" to "2rpx")), "mode" to ""), null, 4),
                                                _cE("text", _uM("class" to "subTitle", "style" to _nS(_uM("margin-right" to "16rpx"))), _tD(unref(grammarConutInfo)?.star1ExerciseRecordNum), 5),
                                                _cE("image", _uM("src" to "/static/ico/dstars3.png", "style" to _nS(_uM("width" to "23.44rpx", "height" to "27.19rpx", "margin-right" to "2rpx")), "mode" to ""), null, 4),
                                                _cE("text", _uM("class" to "subTitle", "style" to _nS(_uM("margin-right" to "16rpx"))), _tD(unref(grammarConutInfo)?.star2ExerciseRecordNum), 5),
                                                _cE("image", _uM("src" to "/static/ico/dstars4.png", "style" to _nS(_uM("width" to "23.44rpx", "height" to "27.19rpx", "margin-right" to "2rpx")), "mode" to ""), null, 4),
                                                _cE("text", _uM("class" to "subTitle", "style" to _nS(_uM("margin-right" to "16rpx"))), _tD(unref(grammarConutInfo)?.star3ExerciseRecordNum), 5),
                                                _cE("image", _uM("src" to "/static/ico/dstars5.png", "style" to _nS(_uM("width" to "23.44rpx", "height" to "27.19rpx", "margin-right" to "2rpx")), "mode" to ""), null, 4),
                                                _cE("text", _uM("class" to "subTitle"), _tD(unref(grammarConutInfo)?.star4ExerciseRecordNum), 1)
                                            ), 4)
                                        ), 4),
                                        _cE("view", _uM("class" to "module-table-header"), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("width" to "80rpx"))), _uA(
                                                _cE("text", _uM("class" to "header-title"), "单元")
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "Section A", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "11.27rpx"))), "（当前难度/总难度）", 4)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "Section B", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "11.27rpx"))), "（当前难度/总难度）", 4)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "百词大战", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "11.27rpx"))), "（当前难度/总难度）", 4)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "章节复习", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "11.27rpx"))), "（当前难度/总难度）", 4)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "总挑战次数", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "11.27rpx"))), "（当前难度/总难度）", 4)
                                            ), 4)
                                        )),
                                        _cE("view", _uM("class" to "module-table-body"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(info)?.grammarTrainStatList, fun(item, __key, __index, _cached): Any {
                                                return _cE("view", _uM("class" to "module-table-body-row"), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("width" to "80rpx"))), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(item.textbookUnitName2), 1)
                                                    ), 4),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(getSubItem(item.unitGrammarTrainStatList, "SectionA")?.starCurrentProgressValue) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), "5")
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(getSubItem(item.unitGrammarTrainStatList, "SectionB")?.starCurrentProgressValue) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), "5")
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(getSubItem(item.unitGrammarTrainStatList, "百词大战")?.starCurrentProgressValue) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), "5")
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(getSubItem(item.unitGrammarTrainStatList, "章节复习")?.starCurrentProgressValue) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), "5")
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(item.totalExerciseRecordNum) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(parseInt(item.passRate)) + "%", 1)
                                                    ))
                                                ))
                                            }
                                            ), 256)
                                        ))
                                    ))
                                )),
                                _cE("view", _uM("class" to "module-item"), _uA(
                                    _cE("view", _uM("class" to "module-item-title-box"), _uA(
                                        _cE("view", _uM("class" to "module-item-title-per")),
                                        _cE("text", _uM("class" to "module-item-title"), "同步阅读训练")
                                    )),
                                    _cE("view", _uM("class" to "module-item-box"), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "margin-bottom" to "20rpx"))), _uA(
                                            _cE("text", _uM("class" to "subTitle", "style" to _nS(_uM("margin-right" to "40rpx"))), "题型达标次数/总挑战次数：" + _tD(unref(readTrainStatInfo)?.tz_conutSussce) + "/" + _tD(unref(readTrainStatInfo)?.tz_conut), 5),
                                            _cE("text", _uM("class" to "subTitle"), "正确率：" + _tD(unref(readTrainStatInfo)?.rate ?: 0) + "%", 1),
                                            _cE("text", _uM("class" to "subTitle", "style" to _nS(_uM("margin-left" to "17rpx"))), "单元完成数/总单元数：" + _tD(unref(readTrainStatInfo)?.dy_conutSussce) + "/" + _tD(unref(readTrainStatInfo)?.dy_conut), 5)
                                        ), 4),
                                        _cE("view", _uM("class" to "module-table-header"), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("width" to "80rpx"))), _uA(
                                                _cE("text", _uM("class" to "header-title"), "单元")
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "选词填空题型", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "10rpx"))), "（达标次数/挑战次数）", 4)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "语法填空题型", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "10rpx"))), "（达标次数/挑战次数）", 4)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "完形填空题型", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "10rpx"))), "（达标次数/挑战次数）", 4)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "传统阅读题型", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "10rpx"))), "（达标次数/挑战次数）", 4)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "六选五", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "10rpx"))), "（达标次数/挑战次数）", 4)
                                            ), 4)
                                        )),
                                        _cE("view", _uM("class" to "module-table-body"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(info)?.readTrainStatList, fun(item, __key, __index, _cached): Any {
                                                return _cE("view", _uM("class" to "module-table-body-row"), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("width" to "80rpx"))), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(item.textbookUnitName2), 1)
                                                    ), 4),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(getBcSubItem(item.problemTypeStatList, "14")?.problemTypeExercisePassNum) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(getBcSubItem(item.problemTypeStatList, "14")?.problemTypeExerciseNum), 1)
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(getBcSubItem(item.problemTypeStatList, "15")?.problemTypeExercisePassNum) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(getBcSubItem(item.problemTypeStatList, "15")?.problemTypeExerciseNum), 1)
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(getBcSubItem(item.problemTypeStatList, "16")?.problemTypeExercisePassNum) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(getBcSubItem(item.problemTypeStatList, "16")?.problemTypeExerciseNum), 1)
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(getBcSubItem(item.problemTypeStatList, "17")?.problemTypeExercisePassNum) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(getBcSubItem(item.problemTypeStatList, "17")?.problemTypeExerciseNum), 1)
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(getBcSubItem(item.problemTypeStatList, "18")?.problemTypeExercisePassNum) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(getBcSubItem(item.problemTypeStatList, "18")?.problemTypeExerciseNum), 1)
                                                    ))
                                                ))
                                            }
                                            ), 256)
                                        ))
                                    ))
                                )),
                                _cE("view", _uM("class" to "module-item"), _uA(
                                    _cE("view", _uM("class" to "module-item-title-box"), _uA(
                                        _cE("view", _uM("class" to "module-item-title-per")),
                                        _cE("text", _uM("class" to "module-item-title"), "同步口语听力")
                                    )),
                                    _cE("view", _uM("class" to "module-item-box"), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "margin-bottom" to "20rpx"))), _uA(
                                            _cE("text", _uM("class" to "subTitle"), "听力训练题目挑战进度")
                                        ), 4),
                                        _cE("view", _uM("class" to "module-table-header"), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("width" to "80rpx"))), _uA(
                                                _cE("text", _uM("class" to "header-title"), "单元")
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "听短对话选答案", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "10rpx"))), "（通过题量/总题量）", 4)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "听长对话选答案", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "10rpx"))), "（通过题量/总题量）", 4)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "听短文选答案", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "10rpx"))), "（通过题量/总题量）", 4)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "听文章、看文章、填空", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "10rpx"))), "（通过题量/总题量）", 4)
                                            ), 4)
                                        )),
                                        _cE("view", _uM("class" to "module-table-body"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(info)?.oralListeningSub701StatList, fun(item, __key, __index, _cached): Any {
                                                return _cE("view", _uM("class" to "module-table-body-row"), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("width" to "80rpx"))), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(item.textbookUnitName2), 1)
                                                    ), 4),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(item?.problemType30PassNum ?: 0) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(item?.problemType30Num ?: 0), 1)
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(item?.problemType31PassNum ?: 0) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(item?.problemType31Num ?: 0), 1)
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(item?.problemType32PassNum ?: 0) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(item?.problemType32Num ?: 0), 1)
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(item?.problemType33PassNum ?: 0) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(item?.problemType33Num ?: 0), 1)
                                                    ))
                                                ))
                                            }
                                            ), 256)
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "module-item-box"), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "margin-bottom" to "20rpx"))), _uA(
                                            _cE("text", _uM("class" to "subTitle"), " 口语训练题目挑战进度")
                                        ), 4),
                                        _cE("view", _uM("class" to "module-table-header"), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("width" to "80rpx"))), _uA(
                                                _cE("text", _uM("class" to "header-title"), "单元")
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "听句子，复述句子", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "10rpx"))), "（通过题量/总题量）", 4)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "14.06rpx"))), "听文章，复述文章", 4),
                                                _cE("text", _uM("class" to "header-title", "style" to _nS(_uM("font-size" to "10rpx"))), "（通过题量/总题量）", 4)
                                            ), 4)
                                        )),
                                        _cE("view", _uM("class" to "module-table-body"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(info)?.oralListeningSub702StatList, fun(item, __key, __index, _cached): Any {
                                                return _cE("view", _uM("class" to "module-table-body-row"), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("width" to "80rpx"))), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(item.textbookUnitName2), 1)
                                                    ), 4),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(item?.problemType34PassNum ?: 0) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(item?.problemType34Num ?: 0), 1)
                                                    )),
                                                    _cE("view", _uM("class" to "body-row-item flexrow flex-center"), _uA(
                                                        _cE("text", _uM("class" to "module-row-text1", "style" to _nS(_uM("color" to "#5A9F32"))), _tD(item?.problemType35PassNum ?: 0) + "/", 5),
                                                        _cE("text", _uM("class" to "module-row-text1"), _tD(item?.problemType35Num ?: 0), 1)
                                                    ))
                                                ))
                                            }
                                            ), 256)
                                        ))
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "backgroundColor" to "#ffffff", "flex" to 1, "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13rpx", "marginRight" to "17.58rpx", "marginBottom" to "17rpx", "marginLeft" to "17.58rpx", "flexDirection" to "column", "paddingTop" to "16rpx", "paddingRight" to "20rpx", "paddingBottom" to "16rpx", "paddingLeft" to "20rpx")), "title-box" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "height" to "auto")), "title" to _pS(_uM("marginLeft" to "4rpx", "fontWeight" to "700", "fontSize" to "18rpx", "color" to "#6694DF", "textAlign" to "left")), "title-bg" to _pS(_uM("width" to "11.72rpx", "height" to "18.75rpx")), "d_scroll_view" to _pS(_uM("flex" to 1)), "module-item" to _pS(_uM("marginTop" to "14.65rpx")), "module-item-title-box" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginBottom" to "11.13rpx")), "module-item-title-per" to _uM(".module-item " to _uM("width" to "3rpx", "height" to "11rpx", "backgroundImage" to "none", "backgroundColor" to "#3D3D3D", "borderTopLeftRadius" to "4rpx", "borderTopRightRadius" to "4rpx", "borderBottomRightRadius" to "4rpx", "borderBottomLeftRadius" to "4rpx", "marginRight" to "8.79rpx")), "module-item-title" to _uM(".module-item " to _uM("fontFamily" to "Arial, Arial", "fontWeight" to "700", "fontSize" to "15rpx", "color" to "#3D3D3D")), "module-item-box" to _uM(".module-item " to _uM("width" to "100%", "backgroundImage" to "none", "backgroundColor" to "#F3F5FF", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "paddingTop" to "12rpx", "paddingRight" to "15.82rpx", "paddingBottom" to "12rpx", "paddingLeft" to "15.82rpx")), "module-table-header" to _uM(".module-item " to _uM("flexDirection" to "row", "paddingBottom" to "8rpx")), "header-title" to _uM(".module-item .module-table-header " to _uM("fontFamily" to "Arial, Arial", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#535D8C", "flex" to 1)), "module-table-body-row" to _uM(".module-item " to _uM("flexDirection" to "row", "paddingTop" to "8rpx", "paddingRight" to 0, "paddingBottom" to "8rpx", "paddingLeft" to 0)), "body-row-item" to _uM(".module-item " to _uM("flex" to 1)), "progress-item" to _uM(".module-item " to _uM("flexDirection" to "row", "alignItems" to "center")), "flex2" to _uM(".module-item " to _uM("!flex" to 2)), "flexrow" to _uM(".module-item " to _uM("flexDirection" to "row")), "module-row-text1" to _uM(".module-item " to _uM("fontFamily" to "Arial, Arial", "fontWeight" to "400", "fontSize" to "14rpx", "color" to "#3D3D3D")), "flex-center" to _pS(_uM("!alignItems" to "center", "!justifyContent" to "center")), "subTitle" to _pS(_uM("fontSize" to "14rpx", "fontWeight" to "bold", "color" to "#3d3d3d")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
