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
import io.dcloud.uniapp.extapi.`$off` as uni__off
import io.dcloud.uniapp.extapi.`$on` as uni__on
import uts.sdk.modules.limeAudioPlayer.createInnerAudioContext
import io.dcloud.uniapp.extapi.getElementById as uni_getElementById
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesWordInterChineseTest : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesWordInterChineseTest) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesWordInterChineseTest
            val _cache = __ins.renderCache
            val subjectIteratorFn = subjectIterator()
            subjectIteratorFn.isReturn80 = false
            subjectIteratorFn.isTest = true
            val sendUpClassFn = sendUpClass()
            val studyTaskId = ref<Number?>(null)
            val _countDown = countDown()
            val isShowPaperSubject = ref(false)
            val ctx = createInnerAudioContext()
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
            val isReview = ref(false)
            val isTask = ref(false)
            val startTime = ref("")
            val secondVal = ref(0)
            val timer = ref(0)
            val scoreVal = ref(0)
            val isShowAdopt = ref(false)
            val isShowFail = ref(false)
            val isShowResult = ref(false)
            val isAdoptStatus = ref(false)
            val isShowPupup = ref(false)
            val showType = ref(1)
            val styleTransformWithOrigin = ref<UniElement?>(null)
            val isShowTypewriting = ref(false)
            val inputText = ref("")
            val recordId = ref(0)
            val isShowKeyboard = ref(false)
            val cursorPosition = ref(0)
            val nowExplain = computed(fun(): String {
                return nowSub.value?.englishText ?: ""
            }
            )
            val isShowNext = computed(fun(): Boolean {
                var _list = subList.value
                if (_list == null) {
                    return false
                }
                return nowIndex.value + 1 < subList.value!!.length
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
            subjectIteratorFn.onComplete(fun(index: Number){
                nowIndex.value = index
            }
            )
            fun gen_evalSouce_fn() {
                var _exercisePassScore = config.value.getNumber("exercisePassScore") ?: 0
                if (scoreVal.value >= _exercisePassScore) {
                    showType.value = 2
                    isAdoptStatus.value = true
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
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/problem/api/englishPracticeSubmit"), method = "POST", data = _uO("textbookId" to getTextBookId(), "subjectType" to "英语", "textbookUnitId" to if (isReview.value) {
                    ""
                } else {
                    getTextbookUnitId()
                }
                , "module" to getModelKey("同步单词"), "subModule" to getSubModelKey("单词识词辩义"), "score" to scoreVal.value, "startTime" to startTime.value, "passStatus" to passStatus, "second" to secondVal.value, "studyTaskId" to studyTaskId.value, "type" to if (studyTaskId.value != null) {
                    "10"
                } else {
                    "0"
                }
                , "exerciseProblemBoList" to sendUpClassFn.get()), header = getHeader(), success = fun(res){
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
                    evalSouce()
                    nowIndex.value = 0
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
            val EvalformatTime = computed(fun(): String {
                return formatTime(secondVal.value)
            }
            )
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
            fun gen_nextItem_fn() {
                subjectIteratorFn.next().then(fun(next){
                    if (next != null) {
                        nowSub.value = next
                    }
                }
                )
            }
            val nextItem = ::gen_nextItem_fn
            fun gen_getStatus_fn(index: Number): String {
                return sendUpClassFn.getStatus(index)
            }
            val getStatus = ::gen_getStatus_fn
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
            fun gen_checkTextFn_fn(txt: String): Boolean {
                val separator = UTSRegExp("[,，]", "")
                val resultArray = txt.split(separator).filter(fun(item): Boolean {
                    return item.trim() !== ""
                }
                )
                val _nowItem = nowSub.value
                if (_nowItem == null) {
                    return false
                }
                val nowItemTxt = _nowItem.chineseExplain
                if (nowItemTxt == null) {
                    return false
                }
                val colonSeparator = UTSRegExp("[：:]", "")
                val parts = nowItemTxt.split(colonSeparator)
                var firstPart = if (parts.length > 1) {
                    parts[1].trim()
                } else {
                    null
                }
                if (firstPart == null) {
                    firstPart = nowItemTxt
                }
                val multiSeparator = UTSRegExp("[、&;；，]", "")
                val firstPartArray = firstPart.split(multiSeparator).filter(fun(item): Boolean {
                    return item.trim() !== ""
                }
                )
                for(item in resolveUTSValueIterator(resultArray)){
                    val itemChinese = item.trim().replace(UTSRegExp("[^\\u4e00-\\u9fa5]", "g"), "")
                    for(target in resolveUTSValueIterator(firstPartArray)){
                        val targetChinese = target.trim().replace(UTSRegExp("[^\\u4e00-\\u9fa5]", "g"), "")
                        if (itemChinese == targetChinese && itemChinese != "") {
                            return true
                        }
                    }
                }
                return false
            }
            val checkTextFn = ::gen_checkTextFn_fn
            fun gen_inspectText_fn() {
                val _followAlongList = subList.value
                if (inputText.value == "") {
                    return
                }
                var _now = nowSub.value
                if (_now == null) {
                    return
                }
                ctx.play()
                if (_followAlongList != null) {
                    if (checkTextFn(inputText.value)) {
                        if (!successList.value.includes(_now.id)) {
                            successList.value.push(_now.id)
                        }
                    } else {
                        if (!failList.value.includes(_now.id)) {
                            failList.value.push(_now.id)
                        }
                    }
                    sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = _now.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步单词"), subModule = getSubModelKey("单词识词辩义"), problemId = _now.id, englishText = _now.englishText, problemIndex = nowIndex.value.toString(10), isPass = if (checkTextFn(inputText.value)) {
                        "1"
                    } else {
                        "0"
                    }
                    , isExercise = "1", didDo = true, selectOpt = inputText.value))
                }
                addTemp()
                nextItem()
            }
            val inspectText = ::gen_inspectText_fn
            watch(nowIndex, fun(kVal: Number){
                var _arr = sendUpClassFn.get()!!
                var _item = _arr[kVal]
                inputText.value = _item.selectOpt!!
            }
            )
            fun gen_getSubjectListTask_fn() {
                uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/studyTask/list"), method = "GET", data = object : UTSJSONObject() {
                    var type = "10"
                    var studyTaskId = studyTaskId.value
                    var isExercisedTest = "1"
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
                        subList.value = _data
                        subjectIteratorFn.putArr(_data)
                        nextItem()
                        startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                        startCountdown()
                        timer.value = setInterval(fun(){
                            secondVal.value++
                        }
                        , 1000)
                        _data.forEach(fun(item, index){
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = item.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步单词"), subModule = getSubModelKey("单词识词辩义"), problemId = item.id, englishText = item.englishText, problemIndex = index.toString(10), isPass = "0", isExercise = "1", didDo = false, selectOpt = "", options = item.options ?: _uA()))
                        }
                        )
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
                    var module = getModelKey("同步单词")
                    var subModule = getSubModelKey("单词识词辩义")
                    var isExercise = "1"
                    var isExercisedTest = "1"
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
                        subList.value = _data
                        subjectIteratorFn.putArr(_data)
                        nextItem()
                        startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                        startCountdown()
                        timer.value = setInterval(fun(){
                            secondVal.value++
                        }
                        , 1000)
                        _data.forEach(fun(item, index){
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = item.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步单词"), subModule = getSubModelKey("单词识词辩义"), problemId = item.id, englishText = item.englishText, problemIndex = index.toString(10), isPass = "0", isExercise = "1", didDo = false, selectOpt = ""))
                        }
                        )
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
                    successList.value.push(_nowSub.id)
                    sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = _nowSub.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步单词"), subModule = getSubModelKey("单词识词辩义"), problemId = _nowSub.id, englishText = _nowSub.englishText, problemIndex = nowIndex.value.toString(10), isPass = "1", isExercise = "0", options = _nowSub.options ?: _uA()))
                }
                nextItem()
            }
            val test = ::gen_test_fn
            onReady(fun(){
                uni__on("enterKey", fun(){
                    inspectText()
                }
                )
                setScreen()
                styleTransformWithOrigin.value = uni_getElementById("styleTransformWithOrigin")
            }
            )
            fun gen_getReviewList_fn() {
                isReview.value = true
                if (uni_getStorageSync("reviewList") != "") {
                    var _tempDataMake = uni_getStorageSync("reviewList")!!
                    var _tempData = JSON.parse<UTSArray<followAlongItem>>(_tempDataMake as String)
                    if (_tempData != null) {
                        subList.value = _tempData
                        subjectIteratorFn.putArr(_tempData)
                        nextItem()
                        startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                        startCountdown()
                        timer.value = setInterval(fun(){
                            secondVal.value++
                        }
                        , 1000)
                        _tempData.forEach(fun(item, index){
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = item.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步单词"), subModule = getSubModelKey("单词识词辩义"), problemId = item.id, englishText = item.englishText, problemIndex = index.toString(10), isPass = "0", isExercise = "1", didDo = false, selectOpt = ""))
                        }
                        )
                    }
                    return
                }
                uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishProblemList"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步单词")
                    var subModule = getSubModelKey("单词识词辩义")
                    var isExercisedTest = "1"
                    var problemNum: Number = 15
                    var leMemoryScore: Number = 100
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
                            uni_showToast(ShowToastOptions(title = "没有待复习内容了", icon = "none"))
                            uni_navigateBack(null)
                            return
                        }
                        subList.value = _data
                        subjectIteratorFn.putArr(_data)
                        nextItem()
                        startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                        startCountdown()
                        timer.value = setInterval(fun(){
                            secondVal.value++
                        }, 1000)
                        _data.forEach(fun(item, index){
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = item.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步单词"), subModule = getSubModelKey("单词识词辩义"), problemId = item.id, englishText = item.englishText, problemIndex = index.toString(10), isPass = "0", isExercise = "1", didDo = false, selectOpt = ""))
                        })
                    } else {
                        uni_showToast(ShowToastOptions(title = "没有待复习内容了", icon = "none"))
                        uni_navigateBack(null)
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getReviewList = ::gen_getReviewList_fn
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
                        secondVal.value = _data.getNumber("exerciseSecond") ?: 0
                        var _arrData = _uA<followAlongItem>()
                        tempList.value = _uA()
                        _data.getArray("appProblemList")!!.forEach(fun(item, index){
                            tempList.value?.push(index)
                            val __item = JSON.parse<followAlongItem>(JSON.stringify(item))!!
                            _arrData.push(__item)
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = (item as UTSJSONObject).getNumber("textbookUnitId") ?: 0, studyTaskId = 0, module = getModelKey("同步单词"), subModule = getSubModelKey("单词识词辩义"), problemId = (item as UTSJSONObject).getNumber("id") ?: 0, englishText = (item as UTSJSONObject).getString("englishText") ?: "0", problemIndex = index.toString(10), isPass = (item as UTSJSONObject).getString("rightStatus") ?: "0", isExercise = "1", didDo = true, selectOpt = (item as UTSJSONObject).getString("selectOpt") ?: ""))
                        }
                        )
                        subList.value = _arrData
                        nowSub.value = _arrData[0]
                        inputText.value = sendUpClassFn.get()!![0].selectOpt ?: ""
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
                config.value = getConfig("单词识词辩义配置")
                meetTheStandard.value = getConfig("语音评测达标分数配置")
                meetDb.value = meetTheStandard.value.getNumber("wordPassScore") ?: 0
                if (ev["recordId"] != null) {
                    recordId.value = parseInt(ev["recordId"] ?: "0")
                    getRecordList()
                } else if (ev["studyTaskId"] != null) {
                    studyTaskId.value = parseInt(ev["studyTaskId"] ?: "0")
                    getSubjectListTask()
                } else if (ev["isReview"] != null) {
                    getReviewList()
                } else {
                    getSubjectList()
                }
                ctx.src = getConfig("提交答案进入下一题提示音").getString("value") ?: ""
            }
            )
            onUnmounted(fun(){
                uni__off("enterKey", null)
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
                val _component_dTitle = resolveEasyComponent("dTitle", GenComponentsDTitleDTitleClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_adoptResult = resolveEasyComponent("adoptResult", GenComponentsAdoptResultAdoptResultClass)
                val _component_examinationPopup = resolveEasyComponent("examinationPopup", GenComponentsExaminationPopupExaminationPopupClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                val _component_paperSubject = resolveEasyComponent("paperSubject", GenComponentsPaperSubjectPaperSubjectClass)
                return _cE(Fragment, null, _uA(
                    _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cV(_component_navbar, _uM("showBack" to true, "title" to ""), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cV(_component_dTitle, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            " 单词识词辨义学后测试 "
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
                                                unref(subjectIteratorFn).currentElementIndex = index + 1
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
                                                _cE("text", _uM("class" to "num", "onClick" to test), " (" + _tD(unref(nowIndex) + 1) + ") ", 1),
                                                _cV(_component_BackgroundImage, _uM("src" to "/static/ico/time.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "time_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                    return _uA(
                                                        _cE("text", _uM("class" to "_time"), _tD(unref(timeText)), 1)
                                                    )
                                                }), "_" to 1))
                                            )),
                                            _cE("view", _uM("class" to "word_box", "style" to _nS(_uM("align-items" to "center", "margin-top" to "-30rpx"))), _uA(
                                                _cE("view", null, _uA(
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "23.44rpx", "line-height" to "25rpx"))), _tD(unref(nowSub)?.englishText), 5)
                                                )),
                                                _cE("view", _uM("class" to "word_inp_box"), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("flex" to "1", "height" to "100%", "position" to "relative"))), _uA(
                                                        _cE("input", _uM("type" to "text", "modelValue" to unref(inputText), "onInput" to fun(`$event`: UniInputEvent){
                                                            trySetRefValue(inputText, `$event`.detail.value)
                                                        }, "style" to _nS(_uM("height" to "100%")), "placeholder" to "用逗号'，'隔开多个汉义"), null, 44, _uA(
                                                            "modelValue"
                                                        ))
                                                    ), 4),
                                                    _cE("text", _uM("class" to "sub_btn", "onClick" to inspectText), "提交")
                                                ))
                                            ), 4),
                                            _cV(_component_BackgroundImage, _uM("src" to "/static/ico/submitPaper.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "submit_paper", "onClick" to fun(){
                                                isShowPaperSubject.value = true
                                            }), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                return _uA(
                                                    _cE("text", _uM("class" to "_sub"), " 交卷 ")
                                                )
                                            }), "_" to 1), 8, _uA(
                                                "onClick"
                                            ))
                                        ), 4)
                                    } else {
                                        _cC("v-if", true)
                                    }
                                    ,
                                    if (isTrue(unref(isShowResult))) {
                                        _cE("view", _uM("key" to 1, "class" to "adopt_box"), _uA(
                                            _cE("view", _uM("class" to "title"), _uA(
                                                _cE("text", _uM("class" to "num", "onClick" to test), " (" + _tD(unref(nowIndex) + 1) + ") ", 1),
                                                _cV(_component_adoptResult, _uM("tpl" to 2, "score" to unref(scoreVal), "notDone" to unref(notDoneNum), "failNum" to unref(failList).length, "time" to unref(EvalformatTime)), null, 8, _uA(
                                                    "score",
                                                    "notDone",
                                                    "failNum",
                                                    "time"
                                                ))
                                            )),
                                            _cE("view", _uM("class" to "word_box"), _uA(
                                                _cE("text", _uM("style" to _nS(_uM("font-size" to "23rpx"))), _tD(unref(nowSub)?.englishText), 5),
                                                _cE("view", _uM("class" to "word_inp_box"), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("flex" to "1", "height" to "100%", "position" to "relative"))), _uA(
                                                        if (getStatus(unref(nowIndex)) != "通过") {
                                                            _cE("text", _uM("key" to 0, "style" to _nS(_uM("font-size" to "23rpx", "color" to "red", "height" to "28rpx", "line-height" to "28rpx"))), _tD(unref(inputText)), 5)
                                                        } else {
                                                            _cE("text", _uM("key" to 1, "style" to _nS(_uM("font-size" to "23rpx", "color" to "green", "height" to "28rpx", "line-height" to "28rpx"))), _tD(unref(inputText)), 5)
                                                        }
                                                    ), 4)
                                                )),
                                                _cE("view", _uM("class" to "title", "style" to _nS(_uM("justify-content" to "flex-start"))), _uA(
                                                    _cE("text", _uM("class" to "num"), _tD(unref(nowSub)?.chineseExplain), 1)
                                                ), 4)
                                            ))
                                        ))
                                    } else {
                                        _cC("v-if", true)
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
                            ))
                        )
                    }
                    ), "_" to 1)),
                    _cV(_component_paperSubject, _uM("isShow" to unref(isShowPaperSubject), "onUpdate:isShow" to fun(`$event`: Boolean){
                        trySetRefValue(isShowPaperSubject, `$event`)
                    }
                    , "onOk" to submit), null, 8, _uA(
                        "isShow"
                    ))
                ), 64)
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "14rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "flexDirection" to "row")), "right_content" to _uM(".d_content " to _uM("width" to "530rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "position" to "relative")), "title" to _uM(".d_content .right_content " to _uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginTop" to "14.06rpx", "paddingTop" to 0, "paddingRight" to "14.65rpx", "paddingBottom" to 0, "paddingLeft" to "14.65rpx")), "num" to _uM(".d_content .right_content .title " to _uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D")), "time_box" to _uM(".d_content .right_content .title " to _uM("width" to "93.75rpx", "height" to "33.4rpx")), "_time" to _uM(".d_content .right_content .title .time_box " to _uM("width" to "62rpx", "marginLeft" to "31.75rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "fontSize" to "14rpx", "color" to "#5B77FF", "letterSpacing" to 2)), "word_box" to _uM(".d_content .right_content " to _uM("marginTop" to "20rpx", "marginRight" to "60rpx", "marginBottom" to "20rpx", "marginLeft" to "60rpx", "flex" to 1)), "submit_paper" to _uM(".d_content .right_content " to _uM("width" to "83.79rpx", "height" to "33.98rpx", "position" to "absolute", "right" to "-3rpx", "bottom" to "32rpx")), "_sub" to _uM(".d_content .right_content .submit_paper " to _uM("width" to "60rpx", "marginLeft" to "23.79rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "color" to "#5B77FF", "fontWeight" to "bold", "fontSize" to "18rpx")), "badge" to _uM(".d_content .right_content .adopt_box " to _uM("width" to "114.84rpx", "height" to "110.16rpx")), "adopt_status" to _uM(".d_content .right_content .adopt_box " to _uM("fontWeight" to "bold", "fontSize" to "23rpx", "color" to "#FA9600", "marginTop" to "15rpx")), "fail_text" to _pS(_uM("fontWeight" to "bold", "fontSize" to "29rpx", "color" to "#3D3D3D")), "source" to _pS(_uM("fontWeight" to "bold", "fontSize" to "47rpx", "color" to "#FA9600")), "result_btn" to _pS(_uM("width" to "101rpx", "height" to "35rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "fontWeight" to "bold", "fontSize" to "14rpx", "lineHeight" to "35rpx", "color" to "#ffffff", "textAlign" to "center")), "close_ico" to _pS(_uM("width" to "17.58rpx", "height" to "17.58rpx", "position" to "absolute", "top" to "117.19rpx", "right" to "206.25rpx")), "word_inp_box" to _pS(_uM("width" to "355rpx", "paddingTop" to "3rpx", "paddingRight" to "3rpx", "paddingBottom" to "3rpx", "paddingLeft" to "3rpx", "marginTop" to "10rpx", "backgroundImage" to "none", "backgroundColor" to "#f6f8ff", "borderTopLeftRadius" to "33rpx", "borderTopRightRadius" to "33rpx", "borderBottomRightRadius" to "33rpx", "borderBottomLeftRadius" to "33rpx", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#b6bcd9", "borderRightColor" to "#b6bcd9", "borderBottomColor" to "#b6bcd9", "borderLeftColor" to "#b6bcd9", "flexDirection" to "row", "alignItems" to "center")), "sub_btn" to _uM(".word_inp_box " to _uM("paddingTop" to 0, "paddingRight" to "20rpx", "paddingBottom" to 0, "paddingLeft" to "20rpx", "height" to "39rpx", "backgroundImage" to "linear-gradient(to bottom, #cbd4ff, #516df4)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "textAlign" to "center", "fontSize" to "15rpx", "lineHeight" to "39rpx", "color" to "#ffffff")), "native-button" to _pS(_uM("height" to "100%", "width" to "100%")), "isShow" to _pS(_uM("bottom" to 0)), "close" to _pS(_uM("bottom" to "-400rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
