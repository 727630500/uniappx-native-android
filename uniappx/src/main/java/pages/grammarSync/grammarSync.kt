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
open class GenPagesGrammarSyncGrammarSync : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesGrammarSyncGrammarSync) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesGrammarSyncGrammarSync
            val _cache = __ins.renderCache
            val statisticsInfo = ref<GrammarTrainStatByTextbook?>(null)
            val grammarArr = ref(_uA<AppTextbookUnitVo1>())
            val secondToTimeStr = fun(second: Number?): String {
                return if (second != null) {
                    formatTime(second!!, "HH:MM:SS")
                } else {
                    "00:00:"
                }
            }
            val textbookMaterialsRef = ref<ComponentPublicInstance?>(null)
            fun gen_getModelTj_fn() {
                uni_request<Result<GrammarTrainStatByTextbook>>(RequestOptions(url = getUrl("/biz/textbookUnit/api/getGrammarTrainStatByTextbook"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步语法训练")
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
                uni_request<Result<UTSArray<AppTextbookUnitVo1>>>(RequestOptions(url = getUrl("/biz/textbookUnit/api/list"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步语法训练")
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
                        grammarArr.value = _data
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getList = ::gen_getList_fn
            fun gen_openVideoFrom_fn(item: AppTextbookUnitVo1, progressItem: GrammarTrainProgress1) {
                var textbookId = item.textbookId
                var unitId = item.id
                var subModule = progressItem.textbookUnitSubModule
                if (textbookMaterialsRef.value != null) {
                    textbookMaterialsRef.value!!.`$callMethod`("showWindow", textbookId, unitId, subModule)
                }
            }
            val openVideoFrom = ::gen_openVideoFrom_fn
            val goStatPath = fun(item: AppTextbookUnitVo1, progressItem: GrammarTrainProgress1){
                var textbookId = item.textbookId
                var unitId = item.id
                var module = progressItem.textbookUnitModule
                var subModule = progressItem.textbookUnitSubModule
                var unitName = item.unitName2
                uni_navigateTo(NavigateToOptions(url = "/pages/grammarSync/statistics/statistics?textbookId=" + textbookId + "&textbookUnitId=" + unitId + "&module=" + module + "&subModule=" + subModule + "&unitTitle=" + unitName + " \u6311\u6218\u7EDF\u8BA1", fail = fun(_) {
                    console.log()
                }
                ))
            }
            val goTest = fun(item: AppTextbookUnitVo1, progressItem: GrammarTrainProgress1){
                var textbookId = item.textbookId
                var unitId = item.id
                var module = progressItem.textbookUnitModule
                var subModule = progressItem.textbookUnitSubModule
                var unitName = item.unitName2
                uni_navigateTo(NavigateToOptions(url = "/pages/grammarSync/test/test?textbookId=" + textbookId + "&textbookUnitId=" + unitId + "&module=" + module + "&subModule=" + subModule + "&unitTitle=" + unitName, fail = fun(_) {
                    console.log()
                }
                ))
            }
            val goPath = fun(path: String){
                uni_navigateTo(NavigateToOptions(url = path, fail = fun(err) {
                    console.log(err)
                }
                ))
            }
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
                val _component_grammarStars = resolveEasyComponent("grammarStars", GenComponentsGrammarStarsGrammarStarsClass)
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "sync_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "同步语法训练"), _uM("right" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cE("view", _uM("class" to "nav-r", "onClick" to fun(){
                                    goPath("/pages/grammarSync/wrongQuestion/wrongQuestion")
                                }
                                ), _uA(
                                    _cE("image", _uM("src" to "/static/ico/wrong-ico.png", "style" to _nS(_uM("width" to "86.72rpx", "height" to "27.54rpx")), "mode" to ""), null, 4)
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
                            _cV(_component_BackgroundImage, _uM("src" to "/static/ico/read_head.png", "bgStyle" to "width:100%;height:100%;position:absolute;left:0;top:0", "className" to "top_head"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("view", _uM("class" to "top_head_content"), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("margin-right" to "50rpx"))), _uA(
                                                _cE("text", _uM("style" to _nS(_uM("font-size" to "11.72rpx", "line-height" to "17.58rpx", "color" to "#244E93"))), "正确率", 4),
                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "flex-end"))), _uA(
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "29.3rpx", "line-height" to "29.3rpx", "color" to "#244E93"))), _tD(unref(statisticsInfo)?.passRate), 5),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "14.06rpx", "line-height" to "14.06rpx", "color" to "#244E93", "margin-bottom" to "5rpx"))), "%", 4)
                                                ), 4)
                                            ), 4),
                                            _cE("view", _uM("class" to "top_item", "style" to _nS(_uM("margin-right" to "30rpx"))), _uA(
                                                _cV(_component_grammarStars, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                    return _uA(
                                                        "1"
                                                    )
                                                }
                                                ), "_" to 1)),
                                                _cE("text", _uM("class" to "top_item_title"), _tD(unref(statisticsInfo)?.star0MoveUpNum), 1)
                                            ), 4),
                                            _cE("view", _uM("class" to "top_item", "style" to _nS(_uM("margin-right" to "30rpx"))), _uA(
                                                _cV(_component_grammarStars, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                    return _uA(
                                                        "2"
                                                    )
                                                }
                                                ), "_" to 1)),
                                                _cE("text", _uM("class" to "top_item_title"), _tD(unref(statisticsInfo)?.star1MoveUpNum), 1)
                                            ), 4),
                                            _cE("view", _uM("class" to "top_item", "style" to _nS(_uM("margin-right" to "30rpx"))), _uA(
                                                _cV(_component_grammarStars, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                    return _uA(
                                                        "3"
                                                    )
                                                }
                                                ), "_" to 1)),
                                                _cE("text", _uM("class" to "top_item_title"), _tD(unref(statisticsInfo)?.star2MoveUpNum), 1)
                                            ), 4),
                                            _cE("view", _uM("class" to "top_item", "style" to _nS(_uM("margin-right" to "30rpx"))), _uA(
                                                _cV(_component_grammarStars, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                    return _uA(
                                                        "4"
                                                    )
                                                }
                                                ), "_" to 1)),
                                                _cE("text", _uM("class" to "top_item_title"), _tD(unref(statisticsInfo)?.star3MoveUpNum), 1)
                                            ), 4),
                                            _cE("view", _uM("class" to "top_item"), _uA(
                                                _cV(_component_grammarStars, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                    return _uA(
                                                        "5"
                                                    )
                                                }
                                                ), "_" to 1)),
                                                _cE("text", _uM("class" to "top_item_title"), _tD(unref(statisticsInfo)?.star4MoveUpNum), 1)
                                            ))
                                        ), 4),
                                        _cE("view", null, _uA(
                                            _cE("view", _uM("class" to "r_item"), _uA(
                                                _cE("text", _uM("class" to "t1"), "挑战成功/总次数："),
                                                _cE("text", _uM("class" to "t2"), _tD(unref(statisticsInfo)?.totalPassExerciseRecordNum) + "/" + _tD(unref(statisticsInfo)?.totalExerciseRecordNum), 1)
                                            )),
                                            _cE("view", _uM("class" to "r_item"), _uA(
                                                _cE("text", _uM("class" to "t1"), "挑战总时长："),
                                                _cE("text", _uM("class" to "t2"), _tD(secondToTimeStr(unref(statisticsInfo)?.totalExerciseSecond)), 1)
                                            )),
                                            _cE("view", _uM("class" to "r_item"), _uA(
                                                _cE("text", _uM("class" to "t1"), "挑战平均时长："),
                                                _cE("text", _uM("class" to "t2"), _tD(secondToTimeStr(unref(statisticsInfo)?.averageExerciseSecond)), 1)
                                            ))
                                        ))
                                    ))
                                )
                            }
                            ), "_" to 1)),
                            _cE("view", _uM("class" to "b_content"), _uA(
                                _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1", "width" to "100%"))), _uA(
                                    _cE("view", _uM("style" to _nS(_uM("padding-bottom" to "-9.38rpx"))), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(grammarArr), fun(item, __key, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "read_item"), _uA(
                                                _cE("text", _uM("class" to "read_title"), _tD(item.unitName2), 1),
                                                _cE(Fragment, null, RenderHelpers.renderList(item.grammarTrainProgressList, fun(progressItem, __key, __index, _cached): Any {
                                                    return _cE("view", _uM("class" to "read_item_content", "style" to _nS(_uM("margin-top" to "11.7rpx"))), _uA(
                                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "flex" to "1"))), _uA(
                                                            _cE("view", _uM("style" to _nS(_uM("margin-right" to "10rpx"))), _uA(
                                                                if (progressItem.haveTextbookMaterials == "1") {
                                                                    _cE("image", _uM("key" to 0, "src" to "/static/ico/video_img.png", "style" to _nS(_uM("width" to "66.21rpx", "height" to "52.15rpx")), "mode" to "", "onClick" to fun(){
                                                                        openVideoFrom(item, progressItem)
                                                                    }), null, 12, _uA(
                                                                        "onClick"
                                                                    ))
                                                                } else {
                                                                    _cC("v-if", true)
                                                                }
                                                                ,
                                                                if (progressItem.haveTextbookMaterials == "0") {
                                                                    _cE("image", _uM("key" to 1, "src" to "/static/ico/video_img2.png", "style" to _nS(_uM("width" to "66.21rpx", "height" to "52.15rpx")), "mode" to ""), null, 4)
                                                                } else {
                                                                    _cC("v-if", true)
                                                                }
                                                            ), 4),
                                                            _cE("view", _uM("style" to _nS(_uM("margin-right" to "20rpx"))), _uA(
                                                                _cE("text", _uM("style" to _nS(_uM("font-weight" to "700", "color" to "#3D3D3D", "font-size" to "14.06rpx", "line-height" to "15.23rpx", "margin-bottom" to "7.62rpx"))), _tD(unref(getReversedSubModelKey)(progressItem.textbookUnitSubModule!!)), 5),
                                                                _cE("text", _uM("style" to _nS(_uM("color" to "#7B7B7B", "font-size" to "14.06rpx", "line-height" to "15.23rpx"))), "挑战到" + _tD(progressItem.starCurrentProgressValue) + "级", 5)
                                                            ), 4),
                                                            _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                                _cE(Fragment, null, RenderHelpers.renderList(5, fun(star, __key, __index, _cached): Any {
                                                                    return _cE("view", _uM("class" to "top_item", "style" to _nS(_uM("margin-right" to "15rpx"))), _uA(
                                                                        _cV(_component_grammarStars, _uM("isFall" to (star > progressItem.starCurrentProgressValue!!), "half" to (star == (progressItem.starCurrentProgressValue!! + 1) && progressItem.isFinish == "0" && progressItem.starCurrentExercisePassNum != 0)), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                                            return _uA(
                                                                                _tD(star)
                                                                            )
                                                                        }
                                                                        ), "_" to 2), 1032, _uA(
                                                                            "isFall",
                                                                            "half"
                                                                        ))
                                                                    ), 4)
                                                                }
                                                                ), 64)
                                                            ), 4)
                                                        ), 4),
                                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                                            _cE("text", _uM("class" to "item_btn1", "onClick" to fun(){
                                                                goTest(item, progressItem)
                                                            }
                                                            ), _tD(if ((progressItem.starCurrentProgressValue == 0 && progressItem.starCurrentExercisePassNum == 0)) {
                                                                "开始挑战"
                                                            } else {
                                                                "继续挑战"
                                                            }
                                                            ), 9, _uA(
                                                                "onClick"
                                                            )),
                                                            _cE("text", _uM("class" to "item_btn2", "onClick" to fun(){
                                                                goStatPath(item, progressItem)
                                                            }
                                                            ), "查看统计", 8, _uA(
                                                                "onClick"
                                                            ))
                                                        ), 4)
                                                    ), 4)
                                                }
                                                ), 256)
                                            ))
                                        }
                                        ), 256)
                                    ), 4)
                                ), 4)
                            ))
                        )),
                        _cV(unref(GenPagesGrammarSyncTextbookMaterialsTextbookMaterialsClass), _uM("ref_key" to "textbookMaterialsRef", "ref" to textbookMaterialsRef), null, 512)
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
                return _uM("sync_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "sync_content" to _pS(_uM("width" to "715rpx", "height" to "332rpx", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFFFFF", "borderRightColor" to "#FFFFFF", "borderBottomColor" to "#FFFFFF", "borderLeftColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "14.56rpx", "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto")), "top_head" to _uM(".sync_content " to _uM("width" to "100%", "height" to "82.03rpx", "paddingTop" to 0, "paddingRight" to "30rpx", "paddingBottom" to 0, "paddingLeft" to "40rpx")), "top_head_content" to _uM(".sync_content .top_head " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "top_item" to _uM(".sync_content " to _uM("alignItems" to "center")), "top_item_img_box" to _uM(".sync_content .top_item " to _uM("height" to "23.44rpx", "flexDirection" to "row", "alignItems" to "center")), "top_item_title" to _uM(".sync_content .top_item " to _uM("fontSize" to "11rpx", "color" to "#244E93", "lineHeight" to "14rpx", "marginTop" to "3rpx")), "r_item" to _uM(".sync_content " to _uM("flexDirection" to "row")), "t1" to _uM(".sync_content .r_item " to _uM("width" to "100rpx", "textAlign" to "right", "fontSize" to "11rpx", "color" to "#244E93", "lineHeight" to "15rpx")), "t2" to _uM(".sync_content .r_item " to _uM("fontWeight" to "bold", "fontSize" to "11rpx", "color" to "#244E93", "lineHeight" to "15rpx")), "b_content" to _uM(".sync_content " to _uM("width" to "100%", "flex" to 1, "backgroundImage" to "none", "backgroundColor" to "#DAE9FC", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "-12.62rpx", "paddingTop" to "6.54rpx", "paddingRight" to "10.55rpx", "paddingBottom" to "6.54rpx", "paddingLeft" to "10.55rpx")), "read_item" to _uM(".sync_content .b_content " to _uM("alignItems" to "center", "width" to "100%", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "marginBottom" to "9.38rpx", "paddingTop" to "9.96rpx", "paddingRight" to "9.96rpx", "paddingBottom" to "9.96rpx", "paddingLeft" to "9.96rpx")), "read_title" to _uM(".sync_content .b_content .read_item " to _uM("width" to "100%", "height" to "30rpx", "fontSize" to "12rpx", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "3rpx", "borderTopRightRadius" to "3rpx", "borderBottomRightRadius" to "3rpx", "borderBottomLeftRadius" to "3rpx", "lineHeight" to "30rpx", "paddingLeft" to "8.2rpx")), "read_item_content" to _uM(".sync_content .b_content .read_item " to _uM("width" to "100%", "flexDirection" to "row", "alignItems" to "center")), "item_btn1" to _uM(".sync_content " to _uM("width" to "65rpx", "height" to "23rpx", "backgroundImage" to "none", "backgroundColor" to "#FA9600", "borderTopLeftRadius" to "23rpx", "borderTopRightRadius" to "23rpx", "borderBottomRightRadius" to "23rpx", "borderBottomLeftRadius" to "23rpx", "textAlign" to "center", "lineHeight" to "23rpx", "color" to "#FFFFFF", "fontSize" to "12rpx", "marginRight" to "14rpx")), "item_btn2" to _uM(".sync_content " to _uM("width" to "65rpx", "height" to "23rpx", "backgroundImage" to "none", "backgroundColor" to "#ECEFFA", "borderTopLeftRadius" to "23rpx", "borderTopRightRadius" to "23rpx", "borderBottomRightRadius" to "23rpx", "borderBottomLeftRadius" to "23rpx", "textAlign" to "center", "lineHeight" to "23rpx", "fontSize" to "12rpx", "color" to "#818DCA")), "nav-r" to _pS(_uM("borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_text" to _uM(".nav-r " to _uM("fontWeight" to "700", "fontSize" to "13rpx", "color" to "#FA9600", "marginLeft" to "2.93rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
