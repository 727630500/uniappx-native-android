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
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSentenceHearHear : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSentenceHearHear) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSentenceHearHear
            val _cache = __ins.renderCache
            val _countDown = countDown()
            val timeTxt = ref("")
            val ctx = createInnerAudioContext()
            var _SequentialIterator = subjectIterator()
            _SequentialIterator.module = getModelKey("同步句子")
            _SequentialIterator.subModule = getSubModelKey("同步句子听说")
            val refRecognition = ref<RecognitionComponentPublicInstance?>(null)
            val isAutoPlay = ref(true)
            val isAudioPlaying = ref(false)
            fun gen_handleAudioStatus_fn(isPlaying: Boolean) {
                isAudioPlaying.value = isPlaying
                if (isPlaying && refRecognition.value?.isStart == true) {
                    refRecognition.value?.endRecord?.invoke()
                }
                if (!isPlaying && isAutoPlay.value) {
                    isAutoPlay.value = false
                }
            }
            val handleAudioStatus = ::gen_handleAudioStatus_fn
            val up80List = ref(_uA<Number>())
            val isShowNext = ref(false)
            val successShow = ref(false)
            val config = ref(UTSJSONObject())
            val meetTheStandard = ref(UTSJSONObject())
            val meetDb = ref(0)
            val unitTitle = ref("")
            val language = ref("英语")
            val category = ref("句子")
            val tempPath = ref<String>("")
            val mp3Path = ref("")
            val startTime = ref("")
            val timer = ref(0)
            val secondVal = ref(0)
            val isStudy = ref(false)
            val tempEnglishCurrentProgressValue = ref(0)
            val englishCurrentProgressValue = ref(0)
            val englishTotalProgressValue = ref(0)
            val followAlongList = ref<UTSArray<followAlongItem>?>(null)
            val nowItem = ref<followAlongItem?>(null)
            val smailNum = ref(0)
            val isFail = ref(false)
            val analysisArr = ref<UTSArray<analysis>?>(null)
            val isTest = ref(true)
            val studyTaskId = ref(0)
            val type = ref("0")
            val problemNum = ref(0)
            val sendMap = ref(_uA<Number>())
            val adoptObj = adopt1(score = 0, complete = 0, accurate = 0, fluent = 0)
            fun gen_sendUp_fn(isPass: String) {
                if (!sendMap.value.includes(nowItem.value?.id ?: 0)) {
                    problemNum.value++
                    sendMap.value.push(nowItem.value?.id ?: 0)
                }
                var _data: UTSJSONObject = _uO("textbookId" to getTextBookId(), "textbookUnitId" to nowItem.value?.textbookUnitId, "module" to getModelKey("同步句子"), "subModule" to getSubModelKey("同步句子听说"), "problemId" to nowItem.value?.id, "englishText" to nowItem.value?.englishText, "problemIndex" to _SequentialIterator.getIndex(nowItem.value), "isPass" to isPass, "isExercise" to "0", "studyTaskId" to studyTaskId.value)
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/problem/api/englishLearnSubmit"), method = "POST", data = _data, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    var type = responseData.data?.getNumber("type")
                    var progressValue = responseData.data?.getNumber("progressValue")
                    if (type == 1) {
                        englishCurrentProgressValue.value = progressValue!!
                    }
                    if (type == 2) {
                        englishCurrentProgressValue.value++
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val sendUp = ::gen_sendUp_fn
            val nextFn = fun(){
                isShowNext.value = false
                isFail.value = false
                _SequentialIterator.next().then(fun(_item){
                    if (_item != null) {
                        isStudy.value = false
                        mp3Path.value = ""
                        tempPath.value = ""
                        nowItem.value = _item
                        setTimeout(fun(){
                            mp3Path.value = nowItem.value?.soundUrl ?: ""
                        }, 0)
                    } else {
                        successShow.value = true
                    }
                }
                )
            }
            fun gen_handleSuccess_fn(arr: UTSArray<UTSJSONObject>?): UTSArray<analysis>? {
                if (arr != null) {
                    var mapArr = _uA<analysis>()
                    arr.forEach(fun(item){
                        if (item.getString("total_score") != null) {
                            mapArr.push(analysis(content = item.getString("content") ?: "", total_score = parseFloat(item.getString("total_score") ?: "0"), dp_message = parseFloat(item.getString("dp_message") ?: "0")))
                        }
                    }
                    )
                    return mapArr
                }
                return null
            }
            val handleSuccess = ::gen_handleSuccess_fn
            ctx.onCanplay(fun(_){
                ctx.play()
            }
            )
            val recognitionSuccess = fun(data: recognitionSuccessType){
                uni_hideLoading()
                isStudy.value = true
                var _source = data.result.getString("total_score") ?: "0"
                var _sourceNum = parseInt(_source)
                if (ucsShare.getState("debug") as Boolean) {
                    ctx.src = getSondUrl("学习答题通过提示音")
                    sendUp("1")
                    isShowNext.value = true
                    return
                }
                sendUp(if (_sourceNum >= meetDb.value) {
                    "1"
                } else {
                    "0"
                }
                )
                if (_sourceNum >= meetDb.value) {
                    ctx.src = getSondUrl("学习答题通过提示音")
                    isShowNext.value = true
                } else {
                    ctx.src = getSondUrl("学习答题不通过提示音")
                }
                isFail.value = true
                adoptObj.score = parseInt(parseFloat(data.result.getString("total_score") ?: "0").toFixed(0))
                adoptObj.complete = parseInt(parseFloat(data.result.getString("integrity_score") ?: "0").toFixed(0))
                adoptObj.accurate = parseInt(parseFloat(data.result.getString("accuracy_score") ?: "0").toFixed(0))
                adoptObj.fluent = parseInt(parseFloat(data.result.getString("fluency_score") ?: "0").toFixed(0))
                var wordArr = data.result.getJSON("sentence")?.getArray("word")
                if (wordArr == null) {
                    var word = data.result.getArray("sentence")
                    if (word != null) {
                        wordArr = word
                    } else {
                        var _word = data.result.getJSON("word")
                        if (_word != null) {
                            wordArr = _uA(
                                _word
                            )
                        }
                    }
                }
                if (wordArr != null) {
                    analysisArr.value = handleSuccess(wordArr as UTSArray<UTSJSONObject>)
                }
                tempPath.value = data.path
            }
            fun gen_getSubjectStudyTask_fn() {
                uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/studyTask/list"), method = "GET", data = object : UTSJSONObject() {
                    var type = "10"
                    var studyTaskId = studyTaskId.value
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    englishTotalProgressValue.value = responseData.data!!.length
                    followAlongList.value = responseData.data
                    val _data = responseData.data
                    if (_data != null) {
                        _SequentialIterator.isTask = true
                        _SequentialIterator.taksId = studyTaskId.value
                        _SequentialIterator.putArr(_data).then(fun(){
                            _SequentialIterator.next().then(fun(_data){
                                nowItem.value = _data
                                mp3Path.value = nowItem.value?.soundUrl ?: ""
                                startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                                timer.value = setInterval(fun(){
                                    secondVal.value++
                                }
                                , 1000)
                            }
                            )
                        }
                        )
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getSubjectStudyTask = ::gen_getSubjectStudyTask_fn
            fun gen_getSubjectLow80_fn(): UTSPromise<Unit> {
                return UTSPromise<Unit>(fun(resolve, reject){
                    uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishProblemList"), method = "GET", data = object : UTSJSONObject() {
                        var textbookId = getTextBookId()
                        var textbookUnitId = getTextbookUnitId()
                        var module = getModelKey("同步句子")
                        var subModule = getSubModelKey("同步句子听说")
                    }, header = getHeader(), success = fun(res){
                        val responseData = res.data
                        if (responseData == null) {
                            return
                        }
                        if (responseData.code as Number != 200) {
                            uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                            return
                        }
                        _SequentialIterator.putArr80(responseData.data)
                        resolve(Unit)
                    }
                    , fail = fun(err){
                        reject(err)
                        console.log(err)
                    }
                    , complete = fun(_){}))
                }
                )
            }
            val getSubjectLow80 = ::gen_getSubjectLow80_fn
            fun gen_getSubjectList_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        await(getSubjectLow80())
                        uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/list"), method = "GET", data = object : UTSJSONObject() {
                            var textbookId = getTextBookId()
                            var textbookUnitId = getTextbookUnitId()
                            var subjectType = "英语"
                            var module = getModelKey("同步句子")
                            var subModule = getSubModelKey("同步句子听说")
                            var isExercise = "1"
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
                            followAlongList.value = responseData.data
                            val _data = responseData.data
                            if (_data != null) {
                                _SequentialIterator.putArr(_data).then(fun(){
                                    nextFn()
                                    startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                                    timer.value = setInterval(fun(){
                                        secondVal.value++
                                    }
                                    , 1000)
                                }
                                )
                            }
                        }
                        , fail = fun(err){
                            console.log(err)
                        }
                        , complete = fun(_){}))
                })
            }
            val getSubjectList = ::gen_getSubjectList_fn
            fun gen_goTest_fn() {
                if (!isTest.value) {
                    uni_setStorageSync("studyTaskEnd", 1)
                    uni_navigateBack(null)
                    return
                }
                if (studyTaskId.value != 0) {
                    uni_redirectTo(RedirectToOptions(url = "/pages/sentence/hear/test?studyTaskId=" + studyTaskId.value))
                } else {
                    uni_redirectTo(RedirectToOptions(url = "/pages/sentence/hear/test"))
                }
            }
            val goTest = ::gen_goTest_fn
            onLoad(fun(event: OnLoadOptions){
                unitTitle.value = event["unitTitle"] ?: ""
                var _isTest = event["isTest"] ?: "1"
                isTest.value = _isTest == "1"
                if (event["type"] != null) {
                    var _englishCurrentProgressValue = event["englishCurrentProgressValue"] ?: "0"
                    englishCurrentProgressValue.value = parseInt(_englishCurrentProgressValue)
                    type.value = event["type"] ?: "0"
                    studyTaskId.value = parseInt(event["studyTaskId"] ?: "0")
                    getSubjectStudyTask()
                } else {
                    var _englishCurrentProgressValue = event["englishCurrentProgressValue"] ?: ""
                    var _englishTotalProgressValue = event["englishTotalProgressValue"] ?: ""
                    if (_englishCurrentProgressValue != "" && _englishTotalProgressValue != "") {
                        englishCurrentProgressValue.value = parseInt(_englishCurrentProgressValue)
                        tempEnglishCurrentProgressValue.value = parseInt(_englishCurrentProgressValue)
                        englishTotalProgressValue.value = parseInt(_englishTotalProgressValue)
                        getSubjectList()
                    }
                }
                config.value = getConfig("句子听说配置")
                meetTheStandard.value = getConfig("语音评测达标分数配置")
                meetDb.value = meetTheStandard.value.getNumber("wordPassScore") ?: 0
                _countDown.onComplete(fun(str: String){
                    timeTxt.value = str
                }
                )
                _countDown.onEnd(fun(){
                    if (refRecognition.value?.isStart == true) {
                        refRecognition.value?.endRecord?.invoke()
                    } else if (!isStudy.value) {
                        refRecognition.value?.startRecord2?.invoke()
                        setTimeout(fun(){
                            refRecognition.value?.endRecord?.invoke()
                        }
                        , 500)
                    }
                }
                )
            }
            )
            onUnmounted(fun(){
                _countDown.clear()
                if (type.value != "0") {
                    return
                }
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/userStudyStatDetail/api/learnSubmit"), method = "POST", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var textbookUnitId = getTextbookUnitId()
                    var module = getModelKey("同步句子")
                    var subModule = getSubModelKey("同步句子听说")
                    var startTime = startTime.value
                    var second = secondVal.value
                    var problemNum = problemNum.value
                }, header = getHeader(), success = fun(res){
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
                clearInterval(timer.value)
                _countDown.clear()
            }
            )
            watch(config, fun(kVal: UTSJSONObject){
                var _practiceSecond = kVal.getNumber("practiceSecond") ?: 0
                if (_practiceSecond > 0) {
                    _countDown.startCountdown(_practiceSecond)
                }
            }
            , WatchOptions(deep = true))
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_dTitle = resolveEasyComponent("dTitle", GenComponentsDTitleDTitleClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_progress = resolveComponent("progress")
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_sentenceAnalysis = resolveEasyComponent("sentenceAnalysis", GenComponentsSentenceAnalysisSentenceAnalysisClass)
                val _component_hearResult = resolveEasyComponent("hearResult", GenComponentsHearResultHearResultClass)
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_Recognition = resolveEasyComponent("Recognition", GenComponentsRecognitionRecognitionClass)
                val _component_u_playPcm = resolveEasyComponent("u-playPcm", GenComponentsUPlayPcmUPlayPcmClass)
                val _component_studyCompleted = resolveEasyComponent("studyCompleted", GenComponentsStudyCompletedStudyCompletedClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to ""), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_dTitle, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _tD(unref(unitTitle))
                                    )
                                }
                                ), "_" to 1))
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            if (isTrue(!unref(isFail))) {
                                _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex" to "1"))), _uA(
                                    _cE("view", _uM("class" to "top_box"), _uA(
                                        _cE("view", _uM("class" to "uni-row", "style" to _nS(_uM("align-items" to "center"))), _uA(
                                            _cE("text", _uM("class" to "tip"), "当前进展"),
                                            _cE("view", _uM("style" to _nS(_uM("width" to "160rpx", "margin" to "0 12.3rpx 0 16rpx"))), _uA(
                                                _cV(_component_progress, _uM("border-radius" to 100, "percent" to (unref(englishCurrentProgressValue) / unref(englishTotalProgressValue) * 100), "stroke-width" to 6, "activeColor" to "#5B77FF", "backgroundColor" to "#E2E6F6"), null, 8, _uA(
                                                    "percent"
                                                ))
                                            ), 4),
                                            _cE("text", _uM("class" to "tip"), _tD(unref(englishCurrentProgressValue)) + "/" + _tD(unref(englishTotalProgressValue)), 1)
                                        ), 4),
                                        _cE("view", _uM("class" to "uni-row", "style" to _nS(_uM("align-items" to "center"))), _uA(
                                            _cV(_component_BackgroundImage, _uM("src" to "/static/ico/time.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "time_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                return _uA(
                                                    _cE("text", _uM("class" to "_time"), _tD(unref(timeTxt)), 1)
                                                )
                                            }), "_" to 1))
                                        ), 4)
                                    )),
                                    _cE("view", _uM("class" to "content_box", "style" to _nS(_uM("align-items" to "flex-start"))), _uA(
                                        _cE("text", _uM("class" to "b1"), _tD(unref(nowItem)?.englishText), 1),
                                        _cE("text", _uM("class" to "b2"), _tD(unref(nowItem)?.chineseExplain), 1)
                                    ), 4)
                                ), 4)
                            } else {
                                _cE("view", _uM("key" to 1, "style" to _nS(_uM("flex" to "1"))), _uA(
                                    _cE("view", _uM("class" to "top_box"), _uA(
                                        _cE("view", _uM("class" to "uni-row", "style" to _nS(_uM("align-items" to "center"))), _uA(
                                            _cE("text", _uM("class" to "tip"), "当前进展"),
                                            _cE("view", _uM("style" to _nS(_uM("width" to "160rpx", "margin" to "0 12.3rpx 0 16rpx"))), _uA(
                                                _cV(_component_progress, _uM("border-radius" to 100, "percent" to (unref(englishCurrentProgressValue) / unref(englishTotalProgressValue) * 100), "stroke-width" to 6, "activeColor" to "#5B77FF", "backgroundColor" to "#E2E6F6"), null, 8, _uA(
                                                    "percent"
                                                ))
                                            ), 4),
                                            _cE("text", _uM("class" to "tip"), _tD(unref(englishCurrentProgressValue)) + "/" + _tD(unref(englishTotalProgressValue)), 1)
                                        ), 4),
                                        _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("padding" to "0 21rpx"))), _uA(
                                            _cE("view", _uM("class" to "uni-row uni-center"), _uA(
                                                _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("margin-right" to "14.65rpx"))), _uA(
                                                    _cE("view", _uM("class" to "dot", "style" to _nS(_uM("background" to "#5A9F32"))), null, 4),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#555"))), "正确", 4)
                                                ), 4),
                                                _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("margin-right" to "14.65rpx"))), _uA(
                                                    _cE("view", _uM("class" to "dot", "style" to _nS(_uM("background" to "#6694DF"))), null, 4),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#555"))), "较好", 4)
                                                ), 4),
                                                _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("margin-right" to "14.65rpx"))), _uA(
                                                    _cE("view", _uM("class" to "dot", "style" to _nS(_uM("background" to "#FA9600"))), null, 4),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#555"))), "一般", 4)
                                                ), 4),
                                                _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("margin-right" to "14.65rpx"))), _uA(
                                                    _cE("view", _uM("class" to "dot", "style" to _nS(_uM("background" to "#E54E4E"))), null, 4),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#555"))), "错误", 4)
                                                ), 4),
                                                _cE("view", _uM("class" to "uni-row uni-center"), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("background" to "#E54E4E", "width" to "18rpx", "height" to "2rpx", "margin-right" to "5.27rpx"))), null, 4),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#555"))), "漏读", 4)
                                                ))
                                            ))
                                        ), 4)
                                    )),
                                    _cE("view", _uM("style" to _nS(_uM("margin-top" to "10rpx", "flex" to "1"))), _uA(
                                        _cE("view", null, _uA(
                                            _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("image", _uM("style" to _nS(_uM("width" to "13.48rpx", "height" to "13.48rpx", "margin-right" to "3rpx")), "src" to "/static/ico/sentence/analysis.png", "mode" to ""), null, 4),
                                                _cE("text", _uM("style" to _nS(_uM("color" to "#6694DF", "font-size" to "14rpx"))), "句子读音分析：", 4)
                                            ), 4),
                                            _cV(_component_sentenceAnalysis, _uM("list" to unref(analysisArr)), null, 8, _uA(
                                                "list"
                                            ))
                                        )),
                                        _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "flex" to "1", "justify-content" to "flex-end"))), _uA(
                                            _cV(_component_hearResult, _uM("score" to adoptObj.score, "accurate" to adoptObj.accurate, "complete" to adoptObj.complete, "fluent" to adoptObj.fluent), null, 8, _uA(
                                                "score",
                                                "accurate",
                                                "complete",
                                                "fluent"
                                            ))
                                        ), 4)
                                    ), 4)
                                ), 4)
                            }
                            ,
                            _cE("view", _uM("class" to "operation_box"), _uA(
                                _cV(_component_u_playMp3, _uM("src" to unref(mp3Path), "isAuto" to unref(isAutoPlay), "onStatusChange" to handleAudioStatus), null, 8, _uA(
                                    "src",
                                    "isAuto"
                                )),
                                if (isTrue(!unref(isAudioPlaying))) {
                                    _cE("view", _uM("key" to 0, "style" to _nS(_uM("margin" to "0 24.61rpx")), "onTouchstart" to fun(){
                                        isFail.value = false
                                    }), _uA(
                                        _cV(_component_Recognition, _uM("onSuccess" to recognitionSuccess, "language" to unref(language), "category" to unref(category), "subject" to unref(nowItem)?.englishText, "seconds" to 30, "ref_key" to "refRecognition", "ref" to refRecognition), null, 8, _uA(
                                            "language",
                                            "category",
                                            "subject"
                                        ))
                                    ), 44, _uA(
                                        "onTouchstart"
                                    ))
                                } else {
                                    _cE("image", _uM("key" to 1, "style" to _nS(_uM("margin" to "0 46rpx", "width" to "80rpx", "height" to "80rpx", "opacity" to "0.5")), "src" to "/static/ico/dis.png", "mode" to ""), null, 4)
                                }
                                ,
                                _cV(_component_u_playPcm, _uM("src" to unref(tempPath)), null, 8, _uA(
                                    "src"
                                ))
                            )),
                            if (isTrue(unref(_SequentialIterator).is80 || unref(_SequentialIterator).isErr80)) {
                                _cE("text", _uM("key" to 2, "style" to _nS(_uM("position" to "absolute", "bottom" to "30rpx", "left" to "20rpx", "color" to "#86909c"))), "复习", 4)
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(unref(isShowNext))) {
                                _cE("text", _uM("key" to 3, "class" to "next_btn", "onClick" to fun(){
                                    nextFn()
                                }, "style" to _nS(_uM("position" to "absolute", "bottom" to "30rpx", "right" to "20rpx"))), "下一题", 12, _uA(
                                    "onClick"
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                        )),
                        if (isTrue(unref(successShow))) {
                            _cV(_component_studyCompleted, _uM("key" to 0, "isTest" to unref(isTest), "onClose" to fun(){
                                successShow.value = false
                            }, "onSuccess" to goTest), null, 8, _uA(
                                "isTest",
                                "onClose"
                            ))
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13.48rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "paddingTop" to "8.2rpx", "paddingRight" to "16.41rpx", "paddingBottom" to "14rpx", "paddingLeft" to "16.41rpx")), "top_box" to _pS(_uM("height" to "35rpx", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "tip" to _uM(".top_box " to _uM("marginLeft" to "5.86rpx", "fontSize" to "12rpx", "color" to "#3D3D3D")), "title" to _pS(_uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx")), "content_box" to _pS(_uM("alignItems" to "center", "flex" to 1)), "b1" to _uM(".content_box " to _uM("fontWeight" to "700", "fontSize" to "26rpx", "color" to "#3D3D3D", "marginBottom" to "10rpx")), "b2" to _uM(".content_box " to _uM("fontSize" to "14rpx", "color" to "#555555", "marginBottom" to "10rpx")), "operation_box" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end", "justifyContent" to "center")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
