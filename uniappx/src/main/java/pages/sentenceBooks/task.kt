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
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSentenceBooksTask : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSentenceBooksTask) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSentenceBooksTask
            val _cache = __ins.renderCache
            val ctx = createInnerAudioContext()
            val loading = ref<Boolean>(false)
            val dataList = ref(_uA<AppProblemVo>())
            val unitTitle = ref("")
            val studyTaskId = ref(0)
            val successShow = ref(false)
            val notPassNum = computed(fun(): Number {
                var retLen = dataList.value.filter(fun(i): Boolean {
                    return i.unitProgressPass == 0
                }
                ).length
                successShow.value = dataList.value.length != 0 && retLen == 0
                return retLen
            }
            )
            val close = fun(){
                uni_navigateBack(null)
            }
            val showStrengthen = ref(false)
            val language = ref("英语")
            val category = ref("句子")
            val nowExplain = ref("")
            val nowIndex = ref(0)
            val meetTheStandard = ref(UTSJSONObject())
            val meetDb = ref(0)
            val sendUp = fun(isPass: String, totalScore: String){
                var _data: UTSJSONObject = _uO("textbookId" to getTextBookId(), "module" to getModelKey("同步句子"), "subModule" to getSubModelKey("句子本"), "type" to "10", "isPass" to isPass, "startTime" to formatDate(null, "YYYY-MM-DD HH:mm:ss"), "studyTaskId" to studyTaskId.value, "second" to 0, "isExercise" to "0", "exerciseProblemBoList" to _uA(
                    object : UTSJSONObject() {
                        var problemId = dataList.value[nowIndex.value].id
                        var isPass = isPass
                        var totalScore = totalScore
                    }
                ))
                console.log(_data)
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/problem/api/exerciseSubmit"), method = "POST", data = _data, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val recognitionSuccess = fun(data: recognitionSuccessType){
                uni_hideLoading()
                var _source = data.result.getString("total_score") ?: "0"
                var _sourceNum = parseInt(_source)
                showStrengthen.value = false
                var _totalScore = parseFloat(dataList.value[nowIndex.value].totalScore ?: "0")
                if (_sourceNum > _totalScore) {
                    dataList.value[nowIndex.value].speakPassStatus = 1
                    dataList.value[nowIndex.value].unitProgressPass = 1
                    dataList.value[nowIndex.value].totalScore = _sourceNum.toString(10)
                    dataList.value[nowIndex.value].speakScore = _sourceNum
                    sendUp(if (_sourceNum >= meetDb.value) {
                        "1"
                    } else {
                        "0"
                    }
                    , _sourceNum.toString(10))
                    if (_sourceNum < meetDb.value) {
                        dataList.value[nowIndex.value].speakPassStatus = 2
                    }
                }
            }
            fun gen_startLy_fn(item: AppProblemVo, index: Number) {
                nowIndex.value = index
                nowExplain.value = item.englishText!!
                language.value = "英语"
                showStrengthen.value = true
            }
            val startLy = ::gen_startLy_fn
            fun gen_queryList_fn() {
                uni_request<Result<UTSArray<AppProblemVo>>>(RequestOptions(url = getUrl("/biz/problem/api/studyTask/list"), method = "GET", header = getHeader(), data = object : UTSJSONObject() {
                    var type = "10"
                    var studyTaskId = studyTaskId.value
                }, success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    var data = responseData.data
                    data?.forEach(fun(item, index){
                        var _sourceNum = parseFloat(item.totalScore ?: "0")
                        item.speakScore = _sourceNum
                        if (item?.unitProgressDid == 0) {
                            item.speakPassStatus = 0
                        } else {
                            console.log(_sourceNum)
                            if (_sourceNum >= meetDb.value) {
                                item.speakPassStatus = 1
                            } else {
                                item.speakPassStatus = 2
                            }
                        }
                    }
                    )
                    dataList.value = data!!
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){
                    loading.value = false
                }
                ))
            }
            val queryList = ::gen_queryList_fn
            ctx.onCanplay(fun(_){
                ctx.play()
            }
            )
            fun gen_playSoundUrl_fn(item: AppProblemVo) {
                if (item.soundUrl == null || item.soundUrl!! == "") {
                    uni_showToast(ShowToastOptions(title = "音频文件缺失，无法播放", icon = "none"))
                    return
                }
                ctx.src = item.soundUrl!!
            }
            val playSoundUrl = ::gen_playSoundUrl_fn
            onLoad(fun(ev){
                loading.value = true
                studyTaskId.value = parseInt(ev["studyTaskId"] ?: "0")
                queryList()
                meetTheStandard.value = getConfig("语音评测达标分数配置")
                meetDb.value = meetTheStandard.value.getNumber("wordPassScore") ?: 0
                unitTitle.value = ev["unitTitle"] ?: ""
            }
            )
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_LearningInfo = resolveEasyComponent("LearningInfo", GenComponentsLearningInfoLearningInfoClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_l_loading = resolveEasyComponent("l-loading", GenUniModulesLimeLoadingComponentsLLoadingLLoadingClass)
                val _component_Recognition = resolveEasyComponent("Recognition", GenComponentsRecognitionRecognitionClass)
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                val _component_studyCompleted = resolveEasyComponent("studyCompleted", GenComponentsStudyCompletedStudyCompletedClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to unref(unitTitle)), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_LearningInfo)
                            )
                        }
                        ), "_" to 1), 8, _uA(
                            "title"
                        )),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "d_right"), _uA(
                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "height" to "30rpx", "padding" to "0 6rpx"))), _uA(
                                    _cE("text", null, " 未通过句子：" + _tD(unref(notPassNum)), 1),
                                    _cE("text", _uM("style" to _nS(_uM("margin-left" to "20rpx"))), " 已通过句子：" + _tD(unref(dataList).length - unref(notPassNum)), 5)
                                ), 4),
                                _cE("view", _uM("class" to "word_table", "style" to _nS(_uM("margin-top" to "-2rpx"))), _uA(
                                    _cE("view", _uM("class" to "word_table-header"), _uA(
                                        _cE("view", _uM("class" to "word_table-header-item"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "单词")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item"), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "中文")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item", "style" to _nS(_uM("flex" to "0.5"))), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "说出英文")
                                        ), 4),
                                        _cE("view", _uM("class" to "word_table-header-item", "style" to _nS(_uM("flex" to "0.5"))), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "分数")
                                        ), 4)
                                    )),
                                    withDirectives(_cE("view", _uM("style" to _nS(_uM("justify-content" to "center", "align-items" to "center", "height" to "100%", "width" to "100%", "flex" to "1"))), _uA(
                                        _cV(_component_l_loading, _uM("color" to "#AEC3FC", "size" to "80rpx"))
                                    ), 4), _uA(
                                        _uA(
                                            vShow,
                                            unref(loading)
                                        )
                                    )),
                                    withDirectives(_cE("view", _uM("class" to "word_table-body"), _uA(
                                        _cE("list-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(dataList), fun(item, index, __index, _cached): Any {
                                                return _cE("list-item", _uM("key" to index), _uA(
                                                    _cE("view", _uM("class" to "word_table-body-row", "style" to _nS("" + (if (((index + 1) % 2) == 0) {
                                                        "background-color: #F1F5FC"
                                                    } else {
                                                        ""
                                                    }
                                                    ))), _uA(
                                                        _cE("view", _uM("class" to "word_table-body-item"), _uA(
                                                            _cE("text", _uM("onClick" to fun(){
                                                                playSoundUrl(item)
                                                            }
                                                            , "class" to "word_table-body-item-text", "style" to _nS(_uM("color" to "blue"))), _tD(item.englishText), 13, _uA(
                                                                "onClick"
                                                            ))
                                                        )),
                                                        _cE("view", _uM("class" to "word_table-body-item"), _uA(
                                                            _cE("text", _uM("class" to "word_table-body-item-text"), _tD(item.chineseExplain), 1)
                                                        )),
                                                        _cE("view", _uM("class" to "word_table-body-item", "style" to _nS(_uM("flex" to "0.5"))), _uA(
                                                            _cE("image", _uM("src" to "/static/ico/voice.png", "style" to _nS(_uM("width" to "11.72rpx", "height" to "14.41rpx")), "mode" to "aspectFit", "onClick" to fun(){
                                                                startLy(item, index)
                                                            }
                                                            ), null, 12, _uA(
                                                                "onClick"
                                                            ))
                                                        ), 4),
                                                        _cE("view", _uM("class" to "word_table-body-item", "style" to _nS(_uM("flex" to "0.5"))), _uA(
                                                            if (item.speakPassStatus == 0) {
                                                                _cE("text", _uM("key" to 0, "class" to "word_table-body-item-text"), "无")
                                                            } else {
                                                                _cC("v-if", true)
                                                            }
                                                            ,
                                                            if (item.speakPassStatus != 0) {
                                                                _cE("text", _uM("key" to 1, "class" to "word_table-body-item-text", "style" to _nS(_uM("color" to if (item.speakPassStatus == 1) {
                                                                    "#5A9F32"
                                                                } else {
                                                                    "#FA9600"
                                                                }))), _tD(item.speakScore), 5)
                                                            } else {
                                                                _cC("v-if", true)
                                                            }
                                                        ), 4)
                                                    ), 4)
                                                ))
                                            }
                                            ), 128)
                                        ), 4)
                                    ), 512), _uA(
                                        _uA(
                                            vShow,
                                            !unref(loading)
                                        )
                                    ))
                                ), 4)
                            ))
                        )),
                        if (isTrue(unref(showStrengthen))) {
                            _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("view", _uM("class" to "popup_content"), _uA(
                                        _cE("view", _uM("class" to "operation_box", "style" to _nS(_uM("margin-bottom" to "20rpx"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("margin" to "0 24.61rpx"))), _uA(
                                                _cV(_component_Recognition, _uM("onSuccess" to recognitionSuccess, "language" to unref(language), "category" to unref(category), "subject" to unref(nowExplain), "seconds" to 10), null, 8, _uA(
                                                    "language",
                                                    "category",
                                                    "subject"
                                                ))
                                            ), 4)
                                        ), 4)
                                    ))
                                )
                            }), "_" to 1))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(successShow))) {
                            _cV(_component_studyCompleted, _uM("key" to 1, "isTest" to false, "onClose" to close, "onSuccess" to close, "showJb" to true, "tpl" to 2, "tips" to "恭喜你，任务已完成!", "btnText" to "确认"))
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("height" to "351rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "marginTop" to "10rpx")), "d_right" to _uM(".d_content " to _uM("width" to "720rpx", "height" to "340rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx")), "word_table" to _uM(".d_content .d_right " to _uM("flex" to 1, "marginTop" to "11.72rpx", "marginRight" to "8.79rpx", "marginBottom" to "11.72rpx", "marginLeft" to "8.79rpx", "height" to "100%")), "title_box" to _pS(_uM("width" to "234rpx", "height" to "28rpx", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "marginLeft" to "196.29rpx")), "_text" to _uM(".title_box " to _uM("textAlign" to "center", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#535D8C", "lineHeight" to "28rpx")), "search_box" to _pS(_uM("width" to "210rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "boxShadow" to "0rpx 2rpx 4rpx 0rpx rgba(130, 141, 197, 0.3)", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "search_ico" to _uM(".search_box " to _uM("width" to "10rpx", "height" to "10rpx", "marginLeft" to "12rpx")), "search_input" to _uM(".search_box " to _uM("flex" to 1, "height" to "100%", "fontSize" to "14rpx", "color" to "#3a3a3a", "marginTop" to 0, "marginRight" to "12rpx", "marginBottom" to 0, "marginLeft" to "12rpx")), "word_table-header" to _uM(".word_table " to _uM("height" to "35.16rpx", "backgroundColor" to "#F1F5FC", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-header-item-text" to _uM(".word_table " to _uM("fontWeight" to "700", "fontSize" to "12rpx", "color" to "#3D3D3D", "marginLeft" to "6.45rpx")), "word_table-body-row" to _uM(".word_table " to _uM("height" to "45.16rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body" to _uM(".word_table " to _uM("height" to "260rpx")), "word_table-header-item" to _uM(".word_table .word_table-header " to _uM("flexDirection" to "row", "flex" to 1, "alignItems" to "center", "justifyContent" to "center"), ".word_table .word_table-body-row " to _uM("flexDirection" to "row", "flex" to 1, "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item" to _uM(".word_table .word_table-header " to _uM("flexDirection" to "row", "flex" to 1, "alignItems" to "center", "justifyContent" to "center"), ".word_table .word_table-body-row " to _uM("flexDirection" to "row", "flex" to 1, "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item-text" to _uM(".word_table " to _uM("fontFamily" to "Arial, Arial", "fontWeight" to "400", "fontSize" to "12rpx", "color" to "#3D3D3D", "textOverflow" to "ellipsis", "lines" to 2)), "z-pagng-empty" to _pS(_uM("justifyContent" to "center", "alignItems" to "center", "height" to "100%", "width" to "100%")), "loadMore" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "center", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx")), "loadMore_text" to _pS(_uM("color" to "#6694DF", "fontFamily" to "Arial, Arial", "fontSize" to "13rpx", "textAlign" to "left", "fontStyle" to "normal")), "popup_content" to _pS(_uM("width" to "100%", "height" to "100%", "alignItems" to "center", "justifyContent" to "flex-end")), "_num" to _uM(".popup_content " to _uM("width" to "56rpx", "height" to "56rpx", "borderTopLeftRadius" to "56rpx", "borderTopRightRadius" to "56rpx", "borderBottomRightRadius" to "56rpx", "borderBottomLeftRadius" to "56rpx", "backgroundImage" to "none", "backgroundColor" to "#FCF3D6", "fontWeight" to "bold", "fontSize" to "23rpx", "color" to "#FA9600", "lineHeight" to "56rpx", "textAlign" to "center")), "tips" to _uM(".popup_content " to _uM("fontSize" to "19rpx", "color" to "#FFDEAD", "lineHeight" to "35rpx", "marginTop" to "9.38rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
