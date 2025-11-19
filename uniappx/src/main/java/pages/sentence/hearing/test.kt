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
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSentenceHearingTest : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSentenceHearingTest) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSentenceHearingTest
            val _cache = __ins.renderCache
            val sendUpClassFn = sendUpClass()
            val studyTaskId = ref<Number?>(null)
            val _countDown = countDown()
            val answerMapNow = ref<topic1?>(null)
            val answerMap = Map<Number, topic1>()
            val tempShow = ref<UTSArray<tempTiMu>?>(null)
            val tempCheck = ref<UTSArray<tempTiMu>?>(null)
            onReady(fun(){
                setScreen()
            }
            )
            val isShowPaperSubject = ref(false)
            val config = ref(UTSJSONObject())
            val meetTheStandard = ref(UTSJSONObject())
            val meetDb = ref(0)
            val nowIndex = ref<Number>(0)
            val failList = Set<Number>()
            val successList = Set<Number>()
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
            val recordId = ref(0)
            val analysisArr = ref<UTSArray<analysis>?>(null)
            val isFail = ref(false)
            val checkText = ref("")
            val isReview = ref(false)
            val isTask = ref(false)
            val nowExplain = computed(fun(): String {
                return nowSub.value?.englishText ?: ""
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
            val EvalformatTime = computed(fun(): String {
                return formatTime(secondVal.value)
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
            fun gen_refreshTxt_fn() {
                var kVal = nowSub.value
                if (kVal == null) {
                    return
                }
                var _item = answerMap.get(kVal.id)
                answerMapNow.value = _item
                checkText.value = _item?.tempShow?.map(fun(item): String {
                    if (item.isCheck) {
                        return item.content
                    } else {
                        return ""
                    }
                }
                )?.join(" ") ?: ""
                tempShow.value = _item?.tempShow
                tempCheck.value = _item?.tempCheck
            }
            val refreshTxt = ::gen_refreshTxt_fn
            fun gen_evalSouce_fn() {
                isShowResult.value = true
                var _exercisePassScore = config.value.getNumber("exercisePassScore") ?: 0
                if (scoreVal.value >= _exercisePassScore) {
                    showType.value = 2
                } else {
                    showType.value = 1
                }
                isShowPupup.value = true
                nowIndex.value = 0
                checkText.value = "-1"
                refreshTxt()
                var list = subList.value
                if (list != null) {
                    nowSub.value = list[0]
                }
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
                var _score = Math.trunc(successList.size * questionFen)
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
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/problem/api/englishPracticeSubmit"), method = "POST", data = _uO("textbookId" to getTextBookId(), "subjectType" to "英语", "textbookUnitId" to if (isReview.value) {
                    ""
                } else {
                    getTextbookUnitId()
                }
                , "module" to getModelKey("同步句子"), "subModule" to getSubModelKey("同步句子听力"), "score" to scoreVal.value, "startTime" to startTime.value, "passStatus" to passStatus, "second" to secondVal.value, "studyTaskId" to studyTaskId.value, "type" to if (studyTaskId.value != null) {
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
            fun gen_delTemp_fn() {
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
            val delTemp = ::gen_delTemp_fn
            fun gen_test_fn() {
                addTemp()
                var _nowSub = nowSub.value
                if (_nowSub != null) {
                    if (!successList.has(_nowSub.id)) {
                        successList.add(_nowSub.id)
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
            var endDataArr = ref(_uA<Number>())
            fun gen_subClear_fn() {
                var _val = nowSub.value
                if (_val == null) {
                    return
                }
                var id = _val.id
                var nowItem = answerMap.get(id)
                nowItem = JSON.parse<topic1?>(JSON.stringify(nowItem))
                if (nowItem == null) {
                    return
                }
                tempShow.value = tempShow.value?.map(fun(element: tempTiMu, index: Number): tempTiMu {
                    if (element.type == 1) {
                        return element
                    }
                    element.isCheck = false
                    element.content = ""
                    return element
                }
                )
                tempCheck.value = tempCheck.value?.map(fun(element: tempTiMu, index: Number): tempTiMu {
                    if (element.type == 1) {
                        return element
                    }
                    element.isCheck = false
                    element.content = ""
                    return element
                }
                )
                endDataArr.value = _uA()
                nowItem.tempCheck = tempCheck.value
                nowItem.tempShow = tempShow.value
                answerMap.set(id, nowItem)
            }
            val subClear = ::gen_subClear_fn
            fun gen_delFn_fn(item: tempTiMu) {
                var _val = nowSub.value
                if (_val == null) {
                    return
                }
                var id = _val.id
                var nowItem = answerMap.get(id)
                nowItem = JSON.parse<topic1?>(JSON.stringify(nowItem))
                if (nowItem == null) {
                    return
                }
                tempShow.value = tempShow.value?.map(fun(element: tempTiMu, index: Number): tempTiMu {
                    if (element.type == 1) {
                        return element
                    }
                    element.isCheck = if (item.index == element.index) {
                        false
                    } else {
                        element.isCheck
                    }
                    element.content = if (item.index == element.index) {
                        ""
                    } else {
                        element.content
                    }
                    return element
                }
                )
                tempCheck.value = tempCheck.value?.map(fun(element: tempTiMu, index: Number): tempTiMu {
                    if (element.type == 1) {
                        return element
                    }
                    element.isCheck = if (item.index == element.index) {
                        false
                    } else {
                        element.isCheck
                    }
                    element.content = if (item.index == element.index) {
                        ""
                    } else {
                        element.content
                    }
                    return element
                }
                )
                endDataArr.value = _uA()
                nowItem!!.tempCheck = tempCheck.value
                nowItem!!.tempShow = tempShow.value
                answerMap.set(id, nowItem!!)
            }
            val delFn = ::gen_delFn_fn
            val next = fun(){
                var _subList = subList.value
                if (_subList != null) {
                    if (nowIndex.value + 1 >= _subList.length) {
                        return
                    }
                    nowIndex.value++
                    nowSub.value = _subList[nowIndex.value]
                }
            }
            watch(nowSub, fun(kVal: followAlongItem){
                refreshTxt()
            }
            )
            fun gen_clickItem_fn(kVal: tempTiMuParameter) {
                var id = kVal.item.id
                var _item = answerMap.get(id)
                _item = JSON.parse<topic1?>(JSON.stringify(_item))
                if (_item == null) {
                    return
                }
                var _tempparticipleList = _item.tempShow as UTSArray<tempTiMu>
                var isEmpty = fun(element: tempTiMu, index: Number): Boolean {
                    return element.content == "" && element.type == 0
                }
                var _index = _tempparticipleList.findIndex(isEmpty)
                if (_index != -1) {
                    _tempparticipleList[_index] = tempTiMu(content = kVal.item.title, isCheck = true, index = kVal.item.index, title = _tempparticipleList[_index].title, id = kVal.item.id, type = kVal.item.type)
                }
                _item.tempShow = _tempparticipleList
                var _tempTiMuArrVal = _item.tempCheck
                if (_tempTiMuArrVal != null) {
                    _tempTiMuArrVal[kVal.index].isCheck = true
                }
                _item.tempCheck = _tempTiMuArrVal
                answerMap.set(id, _item)
                tempShow.value = _item.tempShow!!
                tempCheck.value = _item.tempCheck
                var isErrVal = _tempparticipleList.filter(fun(item): Boolean {
                    return item.title != item.content
                }
                ).length > 0
                if (_tempparticipleList.filter(fun(item): Boolean {
                    return item.content == ""
                }
                ).length == 0) {
                    if (isErrVal) {
                        if (successList.has(id)) {
                            (successList.`delete`(id) && failList.add(id))
                        } else {
                            failList.add(id)
                        }
                    } else {
                        if (failList.has(id)) {
                            (failList.`delete`(id) && successList.add(id))
                        } else {
                            successList.add(id)
                        }
                    }
                } else {
                    if (successList.has(id)) {
                        (successList.`delete`(id) && failList.add(id))
                    } else {
                        failList.add(id)
                    }
                }
                sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = _item.item.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步句子"), subModule = getSubModelKey("同步句子听力"), problemId = _item.item.id, englishText = _item.item.englishText, problemIndex = "0", isPass = if (isErrVal) {
                    "0"
                } else {
                    "1"
                }
                , isExercise = "1", didDo = true, selectOpt = ""))
                answerMap.set(_item.item.id, _item)
                addTemp()
            }
            val clickItem = ::gen_clickItem_fn
            fun gen_reset_fn() {
                subClear()
            }
            val reset = ::gen_reset_fn
            fun gen_makeInitTemp_fn(kVal: followAlongItem): topic1 {
                var arr = kVal.wordList
                var _tempTiMuArr = _uA<tempTiMu>()
                arr.forEach(fun(item: wordList){
                    val _data = tempTiMu(content = if (item.type == 0) {
                        ""
                    } else {
                        item.word
                    }
                    , index = item.sort, isCheck = false, title = item.word, type = item.type, id = kVal!!.id)
                    _tempTiMuArr.push(_data)
                }
                )
                var _makeTemp = JSON.parse<UTSArray<tempTiMu>>(JSON.stringify(_tempTiMuArr))
                _tempTiMuArr.sort(fun(a, b): Number {
                    return Math.random() - 0.5
                }
                )
                return topic1(item = kVal, tempCheck = JSON.parse<UTSArray<tempTiMu>>(JSON.stringify(_tempTiMuArr)), tempShow = _makeTemp)
            }
            val makeInitTemp = ::gen_makeInitTemp_fn
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
            fun gen_getStatus_fn(index: Number): String {
                return sendUpClassFn.getStatus(index)
            }
            val getStatus = ::gen_getStatus_fn
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
                            var arr = item.englishText.split(" ")
                            var makeData = makeInitTemp(item)
                            answerMap.set(item.id, makeData)
                        }
                        )
                        subList.value = _data
                        nowSub.value = _data[0]
                        answerMapNow.value = answerMap.get(_data[0].id)
                        tempShow.value = answerMapNow.value?.tempShow
                        tempCheck.value = answerMapNow.value?.tempCheck
                        _data.forEach(fun(item, index){
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = item.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步句子"), subModule = getSubModelKey("同步句子听力"), problemId = item.id, englishText = item.englishText, problemIndex = index.toString(10), isPass = "0", isExercise = "1", didDo = false, selectOpt = "", options = item.options ?: _uA()))
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
                    var subModule = getSubModelKey("同步句子听力")
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
                            var arr = item.englishText.split(" ")
                            var makeData = makeInitTemp(item)
                            answerMap.set(item.id, makeData)
                        }
                        )
                        subList.value = _data
                        nowSub.value = _data[0]
                        answerMapNow.value = answerMap.get(_data[0].id)
                        tempShow.value = answerMapNow.value?.tempShow
                        tempCheck.value = answerMapNow.value?.tempCheck
                        _data.forEach(fun(item, index){
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = item.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步句子"), subModule = getSubModelKey("同步句子听力"), problemId = item.id, englishText = item.englishText, problemIndex = index.toString(10), isPass = "0", isExercise = "1", didDo = false, selectOpt = "", options = item.options ?: _uA()))
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
            val getSubjectList = ::gen_getSubjectList_fn
            fun gen_getReviewList_fn() {
                isReview.value = true
                if (uni_getStorageSync("reviewList") != "") {
                    val reviewListStr = uni_getStorageSync("reviewList") as String
                    val _data = JSON.parse(reviewListStr) as UTSArray<followAlongItem>
                    if (_data != null && _data.length > 0) {
                        _data.forEach(fun(item){
                            var arr = item.englishText.split(" ")
                            var makeData = makeInitTemp(item)
                            answerMap.set(item.id, makeData)
                        }
                        )
                        subList.value = _data
                        nowSub.value = _data[0]
                        answerMapNow.value = answerMap.get(_data[0].id)
                        tempShow.value = answerMapNow.value?.tempShow
                        tempCheck.value = answerMapNow.value?.tempCheck
                        _data.forEach(fun(item, index){
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = item.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步句子"), subModule = getSubModelKey("同步句子听力"), problemId = item.id, englishText = item.englishText, problemIndex = index.toString(10), isPass = "0", isExercise = "1", didDo = false, selectOpt = ""))
                        }
                        )
                        startCountdown()
                        startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                        timer.value = setInterval(fun(){
                            secondVal.value++
                        }
                        , 1000)
                        return
                    }
                }
                uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishProblemList"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步句子")
                    var subModule = getSubModelKey("同步句子听力")
                    var isExercisedTest = "1"
                    var leMemoryScore: Number = 100
                    var problemNum: Number = 15
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
                        _data.forEach(fun(item){
                            var arr = item.englishText.split(" ")
                            var makeData = makeInitTemp(item)
                            answerMap.set(item.id, makeData)
                        })
                        subList.value = _data
                        nowSub.value = _data[0]
                        answerMapNow.value = answerMap.get(_data[0].id)
                        tempShow.value = answerMapNow.value?.tempShow
                        tempCheck.value = answerMapNow.value?.tempCheck
                        _data.forEach(fun(item, index){
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = item.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步句子"), subModule = getSubModelKey("同步句子听力"), problemId = item.id, englishText = item.englishText, problemIndex = index.toString(10), isPass = "0", isExercise = "1", didDo = false, selectOpt = ""))
                        })
                        startCountdown()
                        startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                        timer.value = setInterval(fun(){
                            secondVal.value++
                        }, 1000)
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
                        secondVal.value = _data.getNumber("exerciseSecond") ?: 0
                        var _arrData = _uA<followAlongItem>()
                        tempList.value = _uA()
                        _data.getArray("appProblemList")!!.forEach(fun(item, index){
                            tempList.value?.push(index)
                            val __item = JSON.parse<followAlongItem>(JSON.stringify(item))!!
                            _arrData.push(__item)
                            answerMap.set(__item.id, topic1(item = __item, tempShow = JSON.parse<UTSArray<tempTiMu>>(JSON.stringify((item as UTSJSONObject).getArray("tempShow")!!))))
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = (item as UTSJSONObject).getNumber("textbookUnitId") ?: 0, studyTaskId = 0, module = getModelKey("同步句子"), subModule = getSubModelKey("同步句子翻译"), problemId = (item as UTSJSONObject).getNumber("id") ?: 0, englishText = (item as UTSJSONObject).getString("englishText") ?: "0", problemIndex = index.toString(10), isPass = (item as UTSJSONObject).getString("rightStatus") ?: "0", isExercise = "1", didDo = true, selectOpt = ""))
                        }
                        )
                        subList.value = _arrData
                        nowSub.value = _arrData[0]
                        var _item = answerMap.get(_arrData[0].id)
                        answerMapNow.value = _item
                        tempShow.value = answerMapNow.value?.tempShow
                        isShowResult.value = true
                        scoreVal.value = parseInt((_data.getString("score") ?: "0"))
                        run {
                            var index: Number = 0
                            while(index < (_data.getNumber("errorAnswerNum") ?: 0)){
                                failList.add(index)
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
                } else if (ev["isReview"] != null) {
                    getReviewList()
                } else {
                    getSubjectList()
                }
                config.value = getConfig("句子听力配置")
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
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_subCheckList = resolveEasyComponent("subCheckList", GenComponentsSubCheckListSubCheckListClass)
                val _component_diction = resolveEasyComponent("diction", GenComponentsDictionDictionClass)
                val _component_contrastSentence = resolveEasyComponent("contrastSentence", GenComponentsContrastSentenceContrastSentenceClass)
                val _component_adoptResult = resolveEasyComponent("adoptResult", GenComponentsAdoptResultAdoptResultClass)
                val _component_examinationPopup = resolveEasyComponent("examinationPopup", GenComponentsExaminationPopupExaminationPopupClass)
                val _component_paperSubject = resolveEasyComponent("paperSubject", GenComponentsPaperSubjectPaperSubjectClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to ""), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_BackgroundImage, _uM("src" to "/static/ico/follow.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "title_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE("text", _uM("class" to "_text"), " 单元重点句听力学后测试 ")
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
                                            _cV(_component_u_playMp3, _uM("src" to unref(nowSub)?.soundUrl, "tplType" to 4, "isAuto" to true), null, 8, _uA(
                                                "src"
                                            ))
                                        )),
                                        _cE("view", _uM("style" to _nS(_uM("padding" to "0 22rpx"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("margin-bottom" to "20rpx"))), _uA(
                                                _cV(_component_subCheckList, _uM("list" to unref(tempShow), "isClear" to true, "onClear" to subClear, "onDel" to delFn), null, 8, _uA(
                                                    "list"
                                                ))
                                            ), 4),
                                            _cV(_component_diction, _uM("list" to unref(tempCheck), "onClickItem" to clickItem), null, 8, _uA(
                                                "list"
                                            ))
                                        ), 4),
                                        _cE("view", _uM("style" to _nS(_uM("align-items" to "center", "margin-bottom" to "20rpx", "margin-top" to "10rpx"))), _uA(
                                            _cE("text", _uM("class" to "btn", "onClick" to next), "下一题")
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
                                    _cE("view", _uM("key" to 1, "class" to "adopt_box", "style" to _nS(_uM("flex" to "1"))), _uA(
                                        _cE("view", _uM("class" to "top_box"), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "word_text"), _tD(unref(answerMapNow)?.item?.englishText), 1),
                                                _cE("text", _uM("class" to "word_tip"), _tD(unref(answerMapNow)?.item?.chineseExplain), 1)
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
                                        _cE("view", _uM("style" to _nS(_uM("padding" to "0 10rpx 10rpx 21rpx", "flex" to "1"))), _uA(
                                            _cV(_component_contrastSentence, _uM("style" to _nS(_uM("width" to "100%")), "arr" to unref(answerMapNow)?.tempShow), null, 8, _uA(
                                                "style",
                                                "arr"
                                            )),
                                            _cE("view", _uM("style" to _nS(_uM("justify-content" to "flex-end", "align-items" to "center", "flex" to "1"))), _uA(
                                                _cV(_component_adoptResult, _uM("score" to unref(scoreVal), "notDone" to unref(notDoneNum), "failNum" to unref(failList).size, "time" to unref(EvalformatTime)), null, 8, _uA(
                                                    "score",
                                                    "notDone",
                                                    "failNum",
                                                    "time"
                                                ))
                                            ), 4)
                                        ), 4)
                                    ), 4)
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "title_box" to _pS(_uM("width" to "234rpx", "height" to "28rpx", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "marginLeft" to "196.29rpx")), "_text" to _uM(".title_box " to _uM("textAlign" to "center", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#535D8C", "lineHeight" to "28rpx")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "flexDirection" to "row")), "right_content" to _uM(".d_content " to _uM("width" to "530rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "position" to "relative")), "title" to _uM(".d_content .right_content " to _uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginTop" to "14.06rpx", "paddingTop" to 0, "paddingRight" to "14.65rpx", "paddingBottom" to 0, "paddingLeft" to "14.65rpx")), "time_box" to _uM(".d_content .right_content .title " to _uM("width" to "93.75rpx", "height" to "33.4rpx")), "_time" to _uM(".d_content .right_content .title .time_box " to _uM("width" to "62rpx", "marginLeft" to "31.75rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "fontSize" to "14rpx", "color" to "#5B77FF", "letterSpacing" to 2)), "word_box" to _uM(".d_content .right_content " to _uM("flex" to 1, "paddingTop" to 0, "paddingRight" to "21rpx", "paddingBottom" to 0, "paddingLeft" to "21rpx")), "word_text" to _uM(".d_content .right_content .word_box " to _uM("fontWeight" to "bold", "fontSize" to "23rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx"), ".d_content .right_content .adopt_box .top_box " to _uM("fontWeight" to "bold", "color" to "#3D3D3D", "fontSize" to "18rpx", "lineHeight" to "23rpx")), "word_tip" to _uM(".d_content .right_content .word_box " to _uM("fontSize" to "14rpx", "color" to "#555555", "lineHeight" to "35rpx", "marginTop" to "7rpx"), ".d_content .right_content .adopt_box .top_box " to _uM("fontSize" to "14rpx", "color" to "#555555", "lineHeight" to "35rpx", "marginTop" to "4rpx")), "submit_paper" to _uM(".d_content .right_content " to _uM("width" to "83.79rpx", "height" to "33.98rpx", "position" to "absolute", "right" to "-3rpx", "bottom" to "32rpx")), "_sub" to _uM(".d_content .right_content .submit_paper " to _uM("width" to "60rpx", "marginLeft" to "23.79rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "color" to "#5B77FF", "fontWeight" to "bold", "fontSize" to "18rpx"), ".d_content .right_content .adopt_box .fail_box " to _uM("width" to "46rpx", "marginLeft" to "30.17rpx", "textAlign" to "center", "lineHeight" to "25.2rpx", "color" to "#FA9600", "fontWeight" to "bold", "fontSize" to "11.72rpx")), "top_box" to _uM(".d_content .right_content .adopt_box " to _uM("flexDirection" to "row", "marginTop" to "15.82rpx", "paddingLeft" to "21rpx")), "fail_box" to _uM(".d_content .right_content .adopt_box " to _uM("width" to "76.17rpx", "height" to "25.2rpx")), "dot" to _pS(_uM("width" to "7.62rpx", "height" to "7.62rpx", "borderTopLeftRadius" to "7.62rpx", "borderTopRightRadius" to "7.62rpx", "borderBottomRightRadius" to "7.62rpx", "borderBottomLeftRadius" to "7.62rpx", "marginRight" to "5rpx")), "_green" to _pS(_uM("color" to "#5A9F32")), "_blue" to _pS(_uM("color" to "#6694DF")), "_orgin" to _pS(_uM("color" to "#FA9600")), "_red" to _pS(_uM("color" to "#E54E4E")), "_not" to _pS(_uM("color" to "#E54E4E", "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#E54E4E")), "fraction_info" to _pS(_uM("width" to "484rpx", "height" to "53rpx", "backgroundImage" to "none", "backgroundColor" to "#F2F5FA", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginTop" to "12.3rpx", "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto", "paddingTop" to 0, "paddingRight" to "40rpx", "paddingBottom" to 0, "paddingLeft" to "40rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-around")), "btn" to _pS(_uM("width" to "100.78rpx", "height" to "35.16rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "fontSize" to "14rpx", "lineHeight" to "35.16rpx", "textAlign" to "center", "color" to "#ffffff", "borderTopLeftRadius" to "35rpx", "borderTopRightRadius" to "35rpx", "borderBottomRightRadius" to "35rpx", "borderBottomLeftRadius" to "35rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
