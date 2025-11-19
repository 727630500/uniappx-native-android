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
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSentenceHearTest : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSentenceHearTest) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSentenceHearTest
            val _cache = __ins.renderCache
            val sendUpClassFn = sendUpClass()
            val _countDown = countDown()
            val studyTaskId = ref<Number?>(null)
            val isShowPaperSubject = ref(false)
            val answerMap = Map<Number, topic>()
            onReady(fun(){
                setScreen()
            }
            )
            val config = ref(UTSJSONObject())
            val meetTheStandard = ref(UTSJSONObject())
            val meetDb = ref(0)
            val nowIndex = ref<Number>(0)
            val failList = ref(_uA<Number>())
            val successList = ref(_uA<Number>())
            val tempList = ref<UTSArray<Number>?>(null)
            val timeText = ref("00:00")
            val subList = ref<UTSArray<followAlongItem>?>(null)
            val nowSub = ref<followAlongItem?>(null)
            val textbookUnitId = ref(0)
            val language = ref("英语")
            val category = ref("句子")
            val startTime = ref("")
            val secondVal = ref(0)
            val timer = ref(0)
            val scoreVal = ref(0)
            val isShowPupup = ref(false)
            val showType = ref(1)
            val isShowResult = ref(false)
            val isTask = ref(false)
            val recordId = ref(0)
            val nowExplain = computed(fun(): String {
                return nowSub.value?.englishText ?: ""
            }
            )
            val nowEndItem = computed(fun(): topic? {
                if (nowSub.value == null) {
                    return null
                }
                return answerMap.get(nowSub.value?.id)
            }
            )
            val notDoneNum = computed(fun(): Number {
                var _subList = subList.value
                if (_subList == null) {
                    return 0
                }
                var _tempList = tempList.value
                if (_tempList == null) {
                    return _subList.length
                }
                return _subList.length - _tempList.length
            }
            )
            fun gen_getTepmNow_fn(id: Number): Boolean {
                var isLargeNumber = fun(element: Number, index: Number): Boolean {
                    return element == id
                }
                if (tempList.value == null) {
                    return false
                }
                if (tempList.value?.findIndex(isLargeNumber) == -1) {
                    return false
                }
                return true
            }
            val getTepmNow = ::gen_getTepmNow_fn
            fun gen_getStatus_fn(index: Number): String {
                return sendUpClassFn.getStatus(index)
            }
            val getStatus = ::gen_getStatus_fn
            fun gen_evalSouce_fn() {
                var _exercisePassScore = config.value.getNumber("exercisePassScore") ?: 0
                if (scoreVal.value >= _exercisePassScore) {
                    showType.value = 2
                } else {
                    showType.value = 1
                }
                isShowPupup.value = true
            }
            val evalSouce = ::gen_evalSouce_fn
            fun gen_submit_fn() {
                _countDown.clear()
                var _passNum = config.value.getNumber("exercisePassScore") ?: 0
                var _subListLen = subList.value?.length ?: 0
                var questionFen = (100 as Number) / _subListLen
                questionFen = if (isNaN(questionFen)) {
                    0
                } else {
                    questionFen
                }
                var _score = Math.trunc(successList.value.length * questionFen)
                scoreVal.value = if (isNaN(_score)) {
                    0
                } else {
                    _score
                }
                var passStatus = if (isNaN(_score)) {
                    "0"
                } else {
                    if (_score >= _passNum) {
                        "1"
                    } else {
                        "0"
                    }
                }
                var _data = sendUpClassFn.get()
                var endArr = _uA<UTSJSONObject>()
                _data?.forEach(fun(item){
                    var _endItem = UTSJSONObject.assign(JSON.parse<UTSJSONObject>(JSON.stringify(answerMap.get(item.problemId))) ?: UTSJSONObject(), item)
                    endArr.push(_endItem as UTSJSONObject)
                }
                )
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/problem/api/englishPracticeSubmit"), method = "POST", data = _uO("textbookId" to getTextBookId(), "subjectType" to "英语", "textbookUnitId" to getTextbookUnitId(), "module" to getModelKey("同步句子"), "subModule" to getSubModelKey("同步句子听说"), "score" to scoreVal.value, "startTime" to startTime.value, "passStatus" to passStatus, "second" to secondVal.value, "studyTaskId" to studyTaskId.value, "type" to if (studyTaskId.value != null) {
                    "10"
                } else {
                    "0"
                }
                , "exerciseProblemBoList" to endArr), header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    clearInterval(timer.value)
                    val _data = responseData.data
                    console.log(_data, "已交卷")
                    evalSouce()
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/problem/api/englishLearnBatchSubmit"), method = "POST", data = object : UTSJSONObject() {
                    var submitList = sendUpClassFn.get()
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
            }
            val submit = ::gen_submit_fn
            fun gen_addTemp_fn() {
                var _nowSub = nowSub.value
                if (_nowSub != null) {
                    var _nowList = tempList.value
                    if (_nowList != null) {
                        if (!_nowList.includes(_nowSub.id)) {
                            _nowList.push(_nowSub.id)
                        }
                        tempList.value = _nowList
                    } else {
                        tempList.value = _uA(
                            _nowSub.id
                        )
                    }
                }
            }
            val addTemp = ::gen_addTemp_fn
            fun gen_startCountdown_fn() {
                var _config = config.value
                var _sec: Number = 0
                var exerciseTimeType = _config.getNumber("exerciseTimeType")!!
                if (exerciseTimeType == 0) {
                    _sec = _config.getNumber("exerciseSecond")!!
                } else {
                    var sub = subList.value
                    var len = if (sub == null) {
                        0
                    } else {
                        sub.length
                    }
                    _sec = _config.getNumber("exerciseSecond")!! * len
                }
                _countDown.startCountdown(_sec)
            }
            val startCountdown = ::gen_startCountdown_fn
            _countDown.onComplete(fun(str: String){
                timeText.value = str
            }
            )
            _countDown.onEnd(fun(){
                clearInterval(timer.value)
                submit()
            }
            )
            fun gen_getSubjectListTask_fn() {
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
                    val _data = responseData.data
                    if (_data != null) {
                        if (_data.length == 0) {
                            return
                        }
                        _data.forEach(fun(item){
                            var adoptObj = adopt2(score = 0, complete = 0, accurate = 0, fluent = 0)
                            var arr = item.englishText.split(" ")
                            var analysisArr = arr.map(fun(arrItem: String): analysis {
                                return analysis(content = arrItem, total_score = 0, dp_message = 16)
                            }
                            )
                            answerMap.set(item.id, topic(item = item, adopt = adoptObj, temp = analysisArr as UTSArray<analysis>))
                        }
                        )
                        subList.value = _data
                        nowSub.value = _data[0]
                        _data.forEach(fun(item, index){
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = getTextbookUnitId(), studyTaskId = studyTaskId.value, module = getModelKey("同步句子"), subModule = getSubModelKey("同步句子听说"), problemId = item.id, englishText = item.englishText, problemIndex = index.toString(10), isPass = "0", isExercise = "1", didDo = false, selectOpt = ""))
                        }
                        )
                        startCountdown()
                        startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                        timer.value = setInterval(fun(){
                            secondVal.value++
                        }
                        , 1000)
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getSubjectListTask = ::gen_getSubjectListTask_fn
            fun gen_getSubjectList_fn() {
                uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/list"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var subjectType = "英语"
                    var textbookUnitId = getTextbookUnitId()
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
                    val _data = responseData.data
                    if (_data != null) {
                        if (_data.length == 0) {
                            return
                        }
                        _data.forEach(fun(item){
                            var adoptObj = adopt2(score = 0, complete = 0, accurate = 0, fluent = 0)
                            var arr = item.englishText.split(" ")
                            var analysisArr = arr.map(fun(arrItem: String): analysis {
                                return analysis(content = arrItem, total_score = 0, dp_message = 16)
                            }
                            )
                            answerMap.set(item.id, topic(item = item, adopt = adoptObj, temp = analysisArr as UTSArray<analysis>))
                        }
                        )
                        _data.forEach(fun(item, index){
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = getTextbookUnitId(), studyTaskId = studyTaskId.value, module = getModelKey("同步句子"), subModule = getSubModelKey("同步句子听说"), problemId = item.id, englishText = item.englishText, problemIndex = index.toString(10), isPass = "0", isExercise = "1", didDo = false, selectOpt = ""))
                        }
                        )
                        subList.value = _data
                        nowSub.value = _data[0]
                        startCountdown()
                        startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                        timer.value = setInterval(fun(){
                            secondVal.value++
                        }
                        , 1000)
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getSubjectList = ::gen_getSubjectList_fn
            fun gen_test_fn() {
                addTemp()
                var _nowSub = nowSub.value
                if (_nowSub != null) {
                    if (!successList.value.includes(_nowSub.id)) {
                        successList.value.push(_nowSub.id)
                        sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = getTextbookUnitId(), studyTaskId = studyTaskId.value, module = getModelKey("同步句子"), subModule = getSubModelKey("同步句子听说"), problemId = _nowSub.id, englishText = _nowSub.englishText, problemIndex = nowIndex.value.toString(10), isPass = "1", isExercise = "1", didDo = true, selectOpt = "", options = _nowSub.options ?: _uA()))
                    }
                }
                var _subList = subList.value
                if (_subList != null) {
                    if (nowIndex.value + 1 >= _subList.length) {
                        return
                    }
                    nowIndex.value++
                    nowSub.value = _subList[nowIndex.value]
                }
            }
            val test = ::gen_test_fn
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
            val recognitionSuccess = fun(data: recognitionSuccessType){
                addTemp()
                var _subList = subList.value
                var _nowSub = nowSub.value
                var _source = data.result.getString("total_score") ?: "0"
                var _sourceNum = parseInt(_source)
                if (_nowSub != null) {
                    if (_sourceNum >= meetDb.value) {
                        console.log("通过")
                        if (!successList.value.includes(_nowSub.id)) {
                            successList.value.push(_nowSub.id)
                        }
                        val failIndex = failList.value.indexOf(_nowSub.id)
                        if (failIndex > -1) {
                            failList.value.splice(failIndex, 1)
                        }
                    } else {
                        if (!failList.value.includes(_nowSub.id)) {
                            failList.value.push(_nowSub.id)
                        }
                        val successIndex = successList.value.indexOf(_nowSub.id)
                        if (successIndex > -1) {
                            successList.value.splice(successIndex, 1)
                        }
                    }
                    var adoptObj = adopt2(score = parseInt(parseFloat(data.result.getString("total_score") ?: "0").toFixed(0)), complete = parseInt(parseFloat(data.result.getString("integrity_score") ?: "0").toFixed(0)), accurate = parseInt(parseFloat(data.result.getString("accuracy_score") ?: "0").toFixed(0)), fluent = parseInt(parseFloat(data.result.getString("fluency_score") ?: "0").toFixed(0)))
                    var analysisArr: UTSArray<analysis>?
                    var wordArr = data.result.getJSON("sentence")?.getArray("word")
                    if (wordArr != null) {
                        analysisArr = handleSuccess(wordArr as UTSArray<UTSJSONObject>)
                        if (analysisArr != null) {
                            answerMap.set(_nowSub.id, topic(item = _nowSub, adopt = adoptObj, temp = analysisArr))
                        }
                    }
                    sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = getTextbookUnitId(), studyTaskId = studyTaskId.value, module = getModelKey("同步句子"), subModule = getSubModelKey("同步句子听说"), problemId = _nowSub.id, englishText = _nowSub.englishText, problemIndex = nowIndex.value.toString(10), isPass = if (_sourceNum >= meetDb.value) {
                        "1"
                    } else {
                        "0"
                    }
                    , isExercise = "1", didDo = true, selectOpt = "", options = _nowSub.options ?: _uA()))
                }
                if (_subList != null) {
                    if (nowIndex.value + 1 >= _subList.length) {
                        return
                    }
                    nowIndex.value++
                    nowSub.value = _subList[nowIndex.value]
                }
            }
            fun gen_getRecordList_fn() {
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/problem/api/getExerciseRecordDetail"), method = "GET", data = object : UTSJSONObject() {
                    var recordId = recordId.value
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    val _data = responseData.data
                    if (_data != null) {
                        console.log(_data)
                        var _arrData = _uA<followAlongItem>()
                        tempList.value = _uA()
                        _data.getArray("appProblemList")!!.forEach(fun(item, index){
                            tempList.value?.push(index)
                            val __item = JSON.parse<followAlongItem>(JSON.stringify(item))!!
                            _arrData.push(__item)
                            answerMap.set(__item.id, topic(item = __item, adopt = JSON.parse<adopt2>(JSON.stringify((item as UTSJSONObject).getJSON("adopt")!!))!!, temp = JSON.parse<UTSArray<analysis>>(JSON.stringify((item as UTSJSONObject).getArray("temp")!!))!!))
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = (item as UTSJSONObject).getNumber("textbookUnitId") ?: 0, studyTaskId = 0, module = getModelKey("同步句子"), subModule = getSubModelKey("同步句子听说"), problemId = (item as UTSJSONObject).getNumber("id") ?: 0, englishText = (item as UTSJSONObject).getString("englishText") ?: "0", problemIndex = index.toString(10), isPass = (item as UTSJSONObject).getString("rightStatus") ?: "0", isExercise = "1", didDo = true, selectOpt = (item as UTSJSONObject).getString("selectOpt") ?: ""))
                        }
                        )
                        subList.value = _arrData
                        nowSub.value = _arrData[0]
                        isShowResult.value = true
                        scoreVal.value = parseInt((_data.getString("score") ?: "0"))
                        run {
                            var index: Number = 0
                            while(index < (_data.getNumber("errorAnswerNum") ?: 0)){
                                failList.value.push(index)
                                index++
                            }
                        }
                        secondVal.value = _data.getNumber("exerciseSecond") ?: 0
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getRecordList = ::gen_getRecordList_fn
            onLoad(fun(ev){
                if (ev["recordId"] != null) {
                    recordId.value = parseInt(ev["recordId"] ?: "0")
                    getRecordList()
                } else if (ev["studyTaskId"] != null) {
                    studyTaskId.value = parseInt(ev["studyTaskId"] ?: "0")
                    getSubjectListTask()
                } else {
                    getSubjectList()
                }
                config.value = getConfig("句子听说配置")
                meetTheStandard.value = getConfig("语音评测达标分数配置")
                meetDb.value = meetTheStandard.value.getNumber("wordPassScore") ?: 0
            }
            )
            onUnmounted(fun(){
                clearInterval(timer.value)
                _countDown.clear()
            }
            )
            onBeforeUnmount(fun(){
                if (isShowResult.value && studyTaskId.value != 0 && recordId.value == 0) {
                    uni_setStorageSync("studyTaskEnd", 1)
                }
            }
            )
            return fun(): Any? {
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_Recognition = resolveEasyComponent("Recognition", GenComponentsRecognitionRecognitionClass)
                val _component_sentenceAnalysis = resolveEasyComponent("sentenceAnalysis", GenComponentsSentenceAnalysisSentenceAnalysisClass)
                val _component_examinationPopup = resolveEasyComponent("examinationPopup", GenComponentsExaminationPopupExaminationPopupClass)
                val _component_paperSubject = resolveEasyComponent("paperSubject", GenComponentsPaperSubjectPaperSubjectClass)
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to ""), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_BackgroundImage, _uM("src" to "/static/ico/follow.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "title_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE("text", _uM("class" to "_text"), " 单元重点句听说学后测试 ")
                                    )
                                }
                                ), "_" to 1))
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("scroll-view", _uM("direction" to "vertical", "class" to "left_nav"), _uA(
                                _cE("view", _uM("style" to _nS(_uM("flex-wrap" to "wrap", "flex-direction" to "row", "padding-right" to "-6rpx"))), _uA(
                                    _cE(Fragment, null, RenderHelpers.renderList(unref(subList), fun(item, index, __index, _cached): Any {
                                        return _cE("view", _uM("style" to _nS(_uM("width" to "35rpx", "margin-right" to "6rpx")), "onClick" to fun(){
                                            nowSub.value = item
                                            nowIndex.value = index
                                        }
                                        ), _uA(
                                            if (isTrue(!unref(isShowResult))) {
                                                _cE(Fragment, _uM("key" to 0), _uA(
                                                    if (isTrue(unref(nowSub)?.id != item.id && getTepmNow(item.id))) {
                                                        _cE("text", _uM("key" to 0, "class" to "item item_complete"), _tD(index + 1), 1)
                                                    } else {
                                                        if (unref(nowSub)?.id == item.id) {
                                                            _cE("text", _uM("key" to 1, "class" to "item item_now"), _tD(index + 1), 1)
                                                        } else {
                                                            _cE("text", _uM("key" to 2, "class" to "item"), _tD(index + 1), 1)
                                                        }
                                                    }
                                                ), 64)
                                            } else {
                                                _cE(Fragment, _uM("key" to 1), _uA(
                                                    if (isTrue(unref(nowSub)?.id != item.id && getStatus(index) == "通过")) {
                                                        _cE("text", _uM("key" to 0, "class" to "item item_complete"), _tD(index + 1), 1)
                                                    } else {
                                                        if (unref(nowSub)?.id == item.id) {
                                                            _cE("text", _uM("key" to 1, "class" to "item item_now"), _tD(index + 1), 1)
                                                        } else {
                                                            if (getStatus(index) != "通过") {
                                                                _cE("text", _uM("key" to 2, "class" to "item item_fail"), _tD(index + 1), 1)
                                                            } else {
                                                                _cC("v-if", true)
                                                            }
                                                        }
                                                    }
                                                ), 64)
                                            }
                                        ), 12, _uA(
                                            "onClick"
                                        ))
                                    }
                                    ), 256)
                                ), 4)
                            )),
                            _cE("view", _uM("class" to "right_content"), _uA(
                                if (isTrue(!unref(isShowResult))) {
                                    _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex" to "1"))), _uA(
                                        _cE("view", _uM("class" to "title"), _uA(
                                            _cE("text", _uM("class" to "num"), " (" + _tD(unref(nowIndex) + 1) + ") ", 1),
                                            _cV(_component_BackgroundImage, _uM("src" to "/static/ico/time.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "time_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                return _uA(
                                                    _cE("text", _uM("class" to "_time"), _tD(unref(timeText)), 1)
                                                )
                                            }), "_" to 1))
                                        )),
                                        _cE("view", _uM("class" to "word_box"), _uA(
                                            _cE("text", _uM("class" to "word_text"), _tD(unref(nowSub)?.englishText), 1),
                                            _cE("text", _uM("class" to "word_tip"), _tD(unref(nowSub)?.chineseExplain), 1)
                                        )),
                                        _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "margin-bottom" to "10rpx"))), _uA(
                                            _cV(_component_Recognition, _uM("seconds" to 30, "onSuccess" to recognitionSuccess, "intIsShowStat" to false, "language" to unref(language), "category" to unref(category), "subject" to unref(nowExplain)), null, 8, _uA(
                                                "language",
                                                "category",
                                                "subject"
                                            ))
                                        ), 4),
                                        _cV(_component_BackgroundImage, _uM("src" to "/static/ico/submitPaper.png", "onClick" to fun(){
                                            isShowPaperSubject.value = true
                                        }, "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "submit_paper"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                            return _uA(
                                                _cE("text", _uM("class" to "_sub"), " 交卷 ")
                                            )
                                        }), "_" to 1), 8, _uA(
                                            "onClick"
                                        ))
                                    ), 4)
                                } else {
                                    _cE("view", _uM("key" to 1, "class" to "adopt_box"), _uA(
                                        _cE("view", _uM("class" to "top_box"), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "word_text"), _tD(unref(nowEndItem)?.item?.englishText), 1),
                                                _cE("text", _uM("class" to "word_tip"), _tD(unref(nowEndItem)?.item?.chineseExplain), 1)
                                            ), 4),
                                            if (getStatus(unref(nowIndex)) != "通过") {
                                                _cV(_component_BackgroundImage, _uM("key" to 0, "src" to "/static/ico/sentence/notDb.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "fail_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                    return _uA(
                                                        _cE("text", _uM("class" to "_sub"), " 未达标 ")
                                                    )
                                                }), "_" to 1))
                                            } else {
                                                _cV(_component_BackgroundImage, _uM("key" to 1, "src" to "/static/ico/sentence/successDb.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "fail_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                    return _uA(
                                                        _cE("text", _uM("class" to "_sub", "style" to _nS(_uM("color" to "#5A9F32"))), " 已达标 ", 4)
                                                    )
                                                }
                                                ), "_" to 1))
                                            }
                                        )),
                                        _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("padding" to "0 21rpx", "margin-top" to "10rpx"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "flex" to "1"))), _uA(
                                                _cE("image", _uM("style" to _nS(_uM("width" to "13.48rpx", "height" to "13.48rpx", "margin-right" to "3rpx")), "src" to "/static/ico/sentence/analysis.png", "mode" to ""), null, 4),
                                                _cE("text", _uM("style" to _nS(_uM("color" to "#6694DF", "font-size" to "14rpx"))), "句子读音分析：", 4)
                                            ), 4),
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
                                        ), 4),
                                        _cE("view", _uM("style" to _nS(_uM("padding" to "0 21rpx"))), _uA(
                                            _cV(_component_sentenceAnalysis, _uM("list" to unref(nowEndItem)?.temp), null, 8, _uA(
                                                "list"
                                            ))
                                        ), 4),
                                        _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "margin-top" to "10rpx"))), _uA(
                                            _cE("text", _uM("style" to _nS(_uM("color" to "#5A9F32", "font-size" to "32rpx", "font-weight" to "bold"))), _tD(unref(nowEndItem)?.adopt?.score), 5),
                                            _cE("text", _uM("style" to _nS(_uM("color" to "#7B7B7B", "font-size" to "12rpx"))), "综合得分", 4)
                                        ), 4),
                                        _cE("view", _uM("class" to "fraction_info"), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center"))), _uA(
                                                _cE("view", _uM("class" to "uni-row", "style" to _nS(_uM("align-items" to "flex-end"))), _uA(
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "21rpx", "color" to "#3D3D3D", "font-weight" to "bold"))), _tD(unref(nowEndItem)?.adopt?.complete), 5),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B", "margin-bottom" to "4rpx"))), "分", 4)
                                                ), 4),
                                                _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B", "line-height" to "15rpx"))), "完整度", 4)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center"))), _uA(
                                                _cE("view", _uM("class" to "uni-row", "style" to _nS(_uM("align-items" to "flex-end"))), _uA(
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "21rpx", "color" to "#3D3D3D", "font-weight" to "bold"))), _tD(unref(nowEndItem)?.adopt?.accurate), 5),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B", "margin-bottom" to "4rpx"))), "分", 4)
                                                ), 4),
                                                _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B", "line-height" to "15rpx"))), "准确度", 4)
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "center"))), _uA(
                                                _cE("view", _uM("class" to "uni-row", "style" to _nS(_uM("align-items" to "flex-end"))), _uA(
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "21rpx", "color" to "#3D3D3D", "font-weight" to "bold"))), _tD(unref(nowEndItem)?.adopt?.fluent), 5),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B", "margin-bottom" to "4rpx"))), "分", 4)
                                                ), 4),
                                                _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B", "line-height" to "15rpx"))), "流利度", 4)
                                            ), 4)
                                        ))
                                    ))
                                }
                            ))
                        )),
                        _cV(_component_examinationPopup, _uM("isShowResult" to unref(isShowResult), "onUpdate:isShowResult" to fun(`$event`: Boolean){
                            trySetRefValue(isShowResult, `$event`)
                        }
                        , "show" to unref(isShowPupup), "onUpdate:show" to fun(`$event`: Boolean){
                            trySetRefValue(isShowPupup, `$event`)
                        }
                        , "scoreVal" to unref(scoreVal), "type" to unref(showType)), null, 8, _uA(
                            "isShowResult",
                            "show",
                            "scoreVal",
                            "type"
                        )),
                        _cV(_component_paperSubject, _uM("isShow" to unref(isShowPaperSubject), "onUpdate:isShow" to fun(`$event`: Boolean){
                            trySetRefValue(isShowPaperSubject, `$event`)
                        }
                        , "onOk" to submit), null, 8, _uA(
                            "isShow"
                        )),
                        _cV(_component_u_playMp3, _uM("isAuto" to true, "src" to unref(nowSub)?.soundUrl, "tplType" to 5, "style" to _nS(_uM("position" to "fixed", "bottom" to "-999rpx"))), null, 8, _uA(
                            "src",
                            "style"
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "title_box" to _pS(_uM("width" to "234rpx", "height" to "28rpx", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "marginLeft" to "196.29rpx")), "_text" to _uM(".title_box " to _uM("textAlign" to "center", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#535D8C", "lineHeight" to "28rpx")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "flexDirection" to "row")), "right_content" to _uM(".d_content " to _uM("width" to "530rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "position" to "relative")), "title" to _uM(".d_content .right_content " to _uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginTop" to "14.06rpx", "paddingTop" to 0, "paddingRight" to "14.65rpx", "paddingBottom" to 0, "paddingLeft" to "14.65rpx")), "time_box" to _uM(".d_content .right_content .title " to _uM("width" to "93.75rpx", "height" to "33.4rpx")), "_time" to _uM(".d_content .right_content .title .time_box " to _uM("width" to "62rpx", "marginLeft" to "31.75rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "fontSize" to "14rpx", "color" to "#5B77FF", "letterSpacing" to 2)), "word_box" to _uM(".d_content .right_content " to _uM("flex" to 1, "paddingTop" to 0, "paddingRight" to "21rpx", "paddingBottom" to 0, "paddingLeft" to "21rpx")), "word_text" to _uM(".d_content .right_content .word_box " to _uM("fontWeight" to "bold", "fontSize" to "23rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx"), ".d_content .right_content .adopt_box .top_box " to _uM("fontWeight" to "bold", "color" to "#3D3D3D", "fontSize" to "18rpx", "lineHeight" to "23rpx")), "word_tip" to _uM(".d_content .right_content .word_box " to _uM("fontSize" to "14rpx", "color" to "#555555", "lineHeight" to "35rpx", "marginTop" to "7rpx"), ".d_content .right_content .adopt_box .top_box " to _uM("fontSize" to "14rpx", "color" to "#555555", "lineHeight" to "35rpx", "marginTop" to "4rpx")), "submit_paper" to _uM(".d_content .right_content " to _uM("width" to "83.79rpx", "height" to "33.98rpx", "position" to "absolute", "right" to "-3rpx", "bottom" to "32rpx")), "_sub" to _uM(".d_content .right_content .submit_paper " to _uM("width" to "60rpx", "marginLeft" to "23.79rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "color" to "#5B77FF", "fontWeight" to "bold", "fontSize" to "18rpx"), ".d_content .right_content .adopt_box .fail_box " to _uM("width" to "46rpx", "marginLeft" to "30.17rpx", "textAlign" to "center", "lineHeight" to "25.2rpx", "color" to "#FA9600", "fontWeight" to "bold", "fontSize" to "11.72rpx")), "top_box" to _uM(".d_content .right_content .adopt_box " to _uM("flexDirection" to "row", "marginTop" to "15.82rpx", "paddingLeft" to "21rpx")), "fail_box" to _uM(".d_content .right_content .adopt_box " to _uM("width" to "76.17rpx", "height" to "25.2rpx")), "dot" to _pS(_uM("width" to "7.62rpx", "height" to "7.62rpx", "borderTopLeftRadius" to "7.62rpx", "borderTopRightRadius" to "7.62rpx", "borderBottomRightRadius" to "7.62rpx", "borderBottomLeftRadius" to "7.62rpx", "marginRight" to "5rpx")), "_green" to _pS(_uM("color" to "#5A9F32")), "_blue" to _pS(_uM("color" to "#6694DF")), "_orgin" to _pS(_uM("color" to "#FA9600")), "_red" to _pS(_uM("color" to "#E54E4E")), "_not" to _pS(_uM("color" to "#E54E4E", "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#E54E4E")), "fraction_info" to _pS(_uM("width" to "484rpx", "height" to "53rpx", "backgroundImage" to "none", "backgroundColor" to "#F2F5FA", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginTop" to "12.3rpx", "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto", "paddingTop" to 0, "paddingRight" to "40rpx", "paddingBottom" to 0, "paddingLeft" to "40rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-around")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
