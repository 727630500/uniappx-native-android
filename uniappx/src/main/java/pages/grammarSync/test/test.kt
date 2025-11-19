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
import io.dcloud.uniapp.extapi.getElementById as uni_getElementById
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesGrammarSyncTestTest : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesGrammarSyncTestTest) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesGrammarSyncTestTest
            val _cache = __ins.renderCache
            val sendUpClassFn = sendUpClass()
            var examinationIteratorFn = examinationIterator()
            val ctx = createInnerAudioContext()
            ctx.onCanplay(fun(_){
                ctx.play()
            }
            )
            val showTpl2 = ref(false)
            val zlddNextTask = ref(0)
            onReady(fun(){
                setScreen()
            }
            )
            val isIntelligence = ref(false)
            val tempIndexArr = ref(_uA<String>())
            val config = ref(UTSJSONObject())
            val meetTheStandard = ref(UTSJSONObject())
            val meetDb = ref(0)
            val isShowPaperSubject = ref(false)
            val nowIndex = ref<String>("")
            val failList = ref(_uA<Number>())
            val successList = ref(_uA<Number>())
            val tempList = ref<UTSArray<Number>?>(null)
            val timeText = ref("00:00")
            val _countDown = countDown()
            val nowSubArr = ref<UTSArray<timi>?>(null)
            val answerList = ref(_uA<UTSArray<UTSArray<answerItemType>>>(_uA(
                _uA()
            )))
            val fenArr = ref(_uA<UTSArray<examinationList>>(_uA()))
            val nowAnswer = ref<UTSArray<answerItemType>?>(null)
            val isShowResult = ref(false)
            val nowResultId = ref(0)
            val needShowKeyboard = ref(ucsShare.getStore("isShowKeyboard") as Boolean)
            val isShowKeyboard = ref(false)
            fun gen_inputClick_fn() {
                if (needShowKeyboard.value) {
                    isShowKeyboard.value = true
                }
            }
            val inputClick = ::gen_inputClick_fn
            val maxPlayNum = ref(2)
            val subList = ref<UTSArray<followAlongItem>?>(null)
            val nowSub = ref<examinationList?>(null)
            val textbookUnitId = ref(0)
            val startTime = ref("")
            val secondVal = ref(0)
            val timer = ref(0)
            val scoreVal = ref(0)
            val isShowAdopt = ref(false)
            val isShowFail = ref(false)
            val isAdoptStatus = ref(false)
            val playArr = _uA<Number>()
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
            val selectedOption = ref<Number>(-1)
            val unitTitle = ref<String>("挑战统计")
            val isShowKey = ref(false)
            fun gen_changeInput_fn(ev: Any, index: Number, subIndex: Number, ssubIndex: Number) {
                if (nowAnswer.value?.length == 0) {
                    return
                }
                nowAnswer.value!![index].input!![subIndex][ssubIndex].content = ev as String
                val allInputsFilled = nowAnswer.value!![index].input!!.every(fun(arr): Boolean {
                    return arr.every(fun(item): Boolean {
                        return item.content != ""
                    }
                    )
                }
                )
                if (allInputsFilled) {
                    if (!tempIndexArr.value.includes(nowIndex.value)) {
                        tempIndexArr.value.push(nowIndex.value)
                    }
                } else {
                    val idx = tempIndexArr.value.indexOf(nowIndex.value)
                    if (idx > -1) {
                        tempIndexArr.value.splice(idx, 1)
                    }
                }
            }
            val changeInput = ::gen_changeInput_fn
            watch(nowIndex, fun(kVal: String){
                if (kVal == "" && nowSub.value?.id != null) {
                    return
                }
                var valArr = kVal.split("-")
                nowSub.value = fenArr.value[parseInt(valArr[0])][parseInt(valArr[1])]
                selectedOption.value = answerList.value[parseInt(valArr[0])][parseInt(valArr[1])][0].options.findIndex(fun(item): Boolean {
                    return item.isCheck
                }
                )
                nowAnswer.value = _uA()
                setTimeout(fun(){
                    nowAnswer.value = answerList.value[parseInt(valArr[0])][parseInt(valArr[1])]
                    var _len = playArr.filter(fun(i): Boolean {
                        return i == (nowSub.value?.id ?: 0)
                    }
                    ).length
                    maxPlayNum.value = 2 - (_len ?: 0)
                }
                , 50)
            }
            )
            fun gen_detectionHtml_fn(text: String?): String {
                if (text == null) {
                    return ""
                }
                val underlineRegex = UTSRegExp("<[^>]+>", "i")
                val matches = text.match(underlineRegex) ?: _uA()
                if (matches.length > 0) {
                    return matches.join("")
                }
                return ""
            }
            val detectionHtml = ::gen_detectionHtml_fn
            val queryParams = ref(object : UTSJSONObject() {
                var textbookId: Number = 0
                var textbookUnitId: Number = 0
                var module = "0"
                var subModule = "0"
                var isExercise = "1"
                var pageSize: Number = 3000
                var subjectType = "英语"
                var problemIdList = ""
            })
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
            val type = ref("0")
            val studyTaskId = ref(0)
            fun gen_playEndFn_fn() {
                playArr.push(nowSub.value?.id ?: 0)
                maxPlayNum.value = 2 - (playArr.filter(fun(i): Boolean {
                    return i == (nowSub.value?.id ?: 0)
                }
                ).length ?: 0)
            }
            val playEndFn = ::gen_playEndFn_fn
            fun gen_heightFn_fn(h: Number) {
                if (nowSub.value == null) {
                    return
                }
                if (_uA(
                    "10",
                    "12",
                    "13"
                ).includes(nowSub.value!!.problemType)) {
                    uni_getElementById("styleTransformWithOrigin")?.style?.setProperty("height", "" + h + "px")
                    uni_getElementById("styleTransformWithOrigin")?.style?.removeProperty("flex")
                } else {
                    uni_getElementById("styleTransformWithOrigin")?.style?.setProperty("flex", "1")
                    uni_getElementById("styleTransformWithOrigin")?.style?.removeProperty("height")
                }
            }
            val heightFn = ::gen_heightFn_fn
            fun gen_makeExerciseProblemBoList_fn(): UTSArray<makeSubTop1> {
                var retArr = _uA<makeSubTop1>()
                answerList.value.forEach(fun(item){
                    item.forEach(fun(_content){
                        _content.forEach(fun(sub){
                            if (sub.input != null) {
                                var fillter = sub.input!!.reduce(fun(acc, line: UTSArray<timi>): UTSArray<timi> {
                                    return acc.concat(line.filter(fun(item): Boolean {
                                        return item.type === "input"
                                    }))
                                }, _uA())
                                fillter.forEach(fun(_sub){
                                    var _index = retArr.findIndex(fun(item): Boolean {
                                        return item.problemId == _sub.id
                                    })
                                    if (_index == -1) {
                                        retArr.push(makeSubTop1(problemId = _sub.id, userOption = "", userBlankAnswer = _sub.content))
                                    } else {
                                        var _textArr = retArr[_index].userBlankAnswer.split("|")
                                        _textArr.push(_sub.content)
                                        retArr[_index].userBlankAnswer = _textArr.join("|")
                                    }
                                })
                            } else {
                                var fillter = sub.options.filter(fun(f): Boolean {
                                    return f.isCheck
                                }
                                )
                                fillter.forEach(fun(_sub){
                                    retArr.push(makeSubTop1(problemId = sub.id, userOption = _sub.option, userBlankAnswer = ""))
                                }
                                )
                                if (fillter.length == 0) {
                                    retArr.push(makeSubTop1(problemId = sub.id, userOption = "", userBlankAnswer = ""))
                                }
                            }
                        }
                        )
                    }
                    )
                }
                )
                return retArr
            }
            val makeExerciseProblemBoList = ::gen_makeExerciseProblemBoList_fn
            val advancedNum = ref(0)
            val isTraining = ref(false)
            val _tempStudyTaskId = ref(0)
            fun gen_submit_fn() {
                clearInterval(timer.value)
                _countDown.clear()
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/problem/api/exerciseSubmit"), method = "POST", data = object : UTSJSONObject() {
                    var textbookId = queryParams.value["textbookId"]
                    var type = type.value
                    var textbookUnitId = queryParams.value["textbookUnitId"]
                    var module = if (type.value == "40") {
                        "500"
                    } else {
                        queryParams.value["module"]
                    }
                    var subModule = if (type.value == "40") {
                        "505"
                    } else {
                        if (type.value == "30") {
                            "500"
                        } else {
                            queryParams.value["subModule"]
                        }
                    }
                    var exerciseProblemBoList = makeExerciseProblemBoList()
                    var startTime = startTime.value
                    var second = secondVal.value
                    var studyTaskId = if (type.value == "40") {
                        _tempStudyTaskId.value
                    } else {
                        studyTaskId.value
                    }
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    nowResultId.value = responseData.data?.getNumber("id")!!
                    scoreVal.value = parseInt(responseData.data?.getString("score") ?: "0")
                    if (type.value == "40") {
                        zlddNextTask.value = responseData.data?.getNumber("zlddNextTask") ?: 0
                        showTpl2.value = true
                        return
                    }
                    var isPass = responseData.data?.getString("isPass")
                    if (isPass == "1") {
                        isShowAdopt.value = true
                        if (responseData.data?.getNumber("starUpToLevel") == null) {
                            ctx.src = getSondUrl("挑战成功提示音")
                        } else {
                            advancedNum.value = responseData.data?.getNumber("starUpToLevel") ?: 0
                            ctx.src = getSondUrl("晋升提示音语法训练")
                        }
                    } else {
                        ctx.src = getSondUrl("挑战失败提示音")
                        isShowFail.value = true
                    }
                    _countDown.clear()
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val submit = ::gen_submit_fn
            fun gen_startCountdown_fn() {
                var _config = config.value
                var _sec: Number = 0
                var exerciseTimeType = _config.getNumber("exerciseTimeType")!!
                if (exerciseTimeType == 0) {
                    _sec = _config.getNumber("exerciseSecond")!!
                } else {
                    var sub = answerList.value
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
            fun gen_getSubjectList_fn() {
                uni_request<Result<UTSArray<examinationList>>>(RequestOptions(url = getUrl("/biz/problem/api/list"), method = "GET", data = queryParams.value, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    if (responseData.data!!.length == 0) {
                        uni_showToast(ShowToastOptions(title = "题目为空", icon = "none"))
                        return
                    }
                    var arr = examinationGroup(responseData.data!!, true)
                    fenArr.value = arr
                    examinationIteratorFn.putArr(arr)
                    answerList.value = examinationIteratorFn.getAnswerLIst()
                    nowIndex.value = "0-0"
                    startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                    startCountdown()
                    timer.value = setInterval(fun(){
                        secondVal.value++
                    }
                    , 1000)
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getSubjectList = ::gen_getSubjectList_fn
            fun gen_getSubjectStudyTask_fn() {
                if (type.value == "40") {
                    uni_showLoading(ShowLoadingOptions(title = "加载中..."))
                    uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/zlddTask/api/studyTask/currentTask"), method = "GET", data = object : UTSJSONObject() {
                        var zlddTaskId = studyTaskId.value
                    }, header = getHeader(), success = fun(res){
                        val responseData = res.data
                        if (responseData == null) {
                            return
                        }
                        if (responseData.code as Number != 200) {
                            return uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        }
                        _tempStudyTaskId.value = responseData.data?.getNumber("studyTaskId") ?: 0
                        uni_request<Result<UTSArray<examinationList>>>(RequestOptions(url = getUrl("/biz/problem/api/studyTask/list"), method = "GET", data = object : UTSJSONObject() {
                            var type = type.value
                            var studyTaskId = _tempStudyTaskId.value
                        }, header = getHeader(), success = fun(res){
                            val responseData = res.data
                            if (responseData == null) {
                                return
                            }
                            if (responseData.code as Number != 200) {
                                uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                                return
                            }
                            if (responseData.data!!.length == 0) {
                                uni_showToast(ShowToastOptions(title = "题目为空", icon = "none"))
                                return
                            }
                            uni_hideLoading()
                            var arr = examinationGroup(responseData.data!!, true)
                            fenArr.value = arr
                            examinationIteratorFn.putArr(arr)
                            answerList.value = examinationIteratorFn.getAnswerLIst()
                            nowIndex.value = "0-0"
                            startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                            startCountdown()
                            timer.value = setInterval(fun(){
                                secondVal.value++
                            }
                            , 1000)
                        }
                        , fail = fun(err){
                            console.log(err)
                        }
                        , complete = fun(_){}))
                    }
                    , fail = fun(err){
                        console.log(err)
                    }
                    , complete = fun(_){
                        uni_hideLoading()
                    }
                    ))
                    return
                }
                uni_request<Result<UTSArray<examinationList>>>(RequestOptions(url = getUrl("/biz/problem/api/studyTask/list"), method = "GET", data = object : UTSJSONObject() {
                    var type = type.value
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
                    if (responseData.data!!.length == 0) {
                        uni_showToast(ShowToastOptions(title = "题目为空", icon = "none"))
                        return
                    }
                    var arr = examinationGroup(responseData.data!!, true)
                    fenArr.value = arr
                    examinationIteratorFn.putArr(arr)
                    answerList.value = examinationIteratorFn.getAnswerLIst()
                    nowIndex.value = "0-0"
                    startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                    startCountdown()
                    timer.value = setInterval(fun(){
                        secondVal.value++
                    }
                    , 1000)
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getSubjectStudyTask = ::gen_getSubjectStudyTask_fn
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
            watch(selectedOption, fun(kVal: Number, prevVal: Number?){
                if (kVal == -1) {
                    return
                }
                tempIndexArr.value.push(nowIndex.value)
                var _nowItem = nowSub.value
                if (_nowItem != null) {
                    var _options = _nowItem.options as UTSArray<Options>?
                    if (_options != null) {
                        var valArr = nowIndex.value.split("-")
                        var opt = answerList.value[parseInt(valArr[0])][parseInt(valArr[1])][0].options
                        opt.forEach(fun(item){
                            item.isCheck = false
                        }
                        )
                        answerList.value[parseInt(valArr[0])][parseInt(valArr[1])][0].options[kVal].isCheck = true
                    }
                }
            }
            , WatchOptions(immediate = true))
            fun gen_getLastData_fn(type: Number) {
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/problem/api/getLastExerciseRecord"), method = "GET", data = _uO("textbookId" to if (studyTaskId.value == 0) {
                    queryParams.value["textbookId"]
                } else {
                    ""
                }
                , "textbookUnitId" to if (studyTaskId.value == 0) {
                    queryParams.value["textbookUnitId"]
                } else {
                    ""
                }
                , "module" to if (studyTaskId.value == 0) {
                    queryParams.value["module"]
                } else {
                    ""
                }
                , "subModule" to if (studyTaskId.value == 0) {
                    queryParams.value["subModule"]
                } else {
                    ""
                }
                , "type" to type, "studyTaskId" to studyTaskId.value), header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    if (responseData.data != null) {
                        var viewErrorAnswerAnalysisNum = responseData.data!!.getNumber("viewErrorAnswerAnalysisNum")
                        var errorAnswerNum = responseData.data!!.getNumber("errorAnswerNum")
                        if (viewErrorAnswerAnalysisNum != errorAnswerNum) {
                            isShowResult.value = true
                            nowResultId.value = responseData.data!!.getNumber("id")!!
                            return
                        }
                    }
                    if (type == 10) {
                        getSubjectStudyTask()
                    }
                    if (type == 0) {
                        getSubjectList()
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getLastData = ::gen_getLastData_fn
            fun gen_getIntelligenceList_fn() {
                uni_request<Result<UTSArray<examinationList>>>(RequestOptions(url = getUrl("/biz/problem/api/userErrorBook/problemList"), method = "GET", data = UTSJSONObject.assign(UTSJSONObject(), queryParams.value, object : UTSJSONObject() {
                    var haveUnitName2 = "1"
                }), header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    if (responseData.data!!.length == 0) {
                        uni_showToast(ShowToastOptions(title = "题目为空", icon = "none"))
                        return
                    }
                    var arr = examinationGroup(responseData.data!!, true)
                    fenArr.value = arr
                    examinationIteratorFn.putArr(arr)
                    answerList.value = examinationIteratorFn.getAnswerLIst()
                    nowIndex.value = "0-0"
                    startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                    startCountdown()
                    timer.value = setInterval(fun(){
                        secondVal.value++
                    }
                    , 1000)
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getIntelligenceList = ::gen_getIntelligenceList_fn
            val resetType = ref(0)
            fun gen_resetFn_fn() {
                nowIndex.value = ""
                isShowResult.value = false
                tempIndexArr.value = _uA()
                var _type = resetType.value
                if (_type == 0) {
                    getIntelligenceList()
                }
                if (_type == 1) {
                    getLastData(10)
                }
                if (_type == 2) {
                    getLastData(0)
                }
            }
            val resetFn = ::gen_resetFn_fn
            onLoad(fun(e){
                config.value = getConfig("同步语法训练配置")
                unitTitle.value = if (e["unitTitle"] != null) {
                    e["unitTitle"]!!
                } else {
                    "挑战"
                }
                studyTaskId.value = parseInt(e["studyTaskId"] ?: "0")
                if (e["intelligence"] != null) {
                    isIntelligence.value = true
                    queryParams.value["textbookId"] = parseInt(if (e["textbookId"] != "") {
                        e["textbookId"]!!
                    } else {
                        "0"
                    }
                    )
                    queryParams.value["textbookUnitId"] = parseInt(if (e["textbookUnitId"] != "") {
                        e["textbookUnitId"]!!
                    } else {
                        "0"
                    }
                    )
                    queryParams.value["module"] = if (e["module"] != "") {
                        e["module"]!!
                    } else {
                        ""
                    }
                    queryParams.value["subModule"] = if (e["subModule"] != "") {
                        e["subModule"]!!
                    } else {
                        ""
                    }
                    type.value = "30"
                    getIntelligenceList()
                    resetType.value = 0
                    return
                }
                if (e["type"] != null) {
                    type.value = "10"
                    getLastData(10)
                    resetType.value = 1
                    return
                }
                if (e["isTraining"] != null) {
                    type.value = "40"
                    getSubjectStudyTask()
                    return
                }
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
                    "挑战"
                }
                getLastData(0)
                resetType.value = 2
            }
            )
            onUnmounted(fun(){
                clearInterval(timer.value)
                _countDown.clear()
            }
            )
            return fun(): Any? {
                val _component_dTitle = resolveEasyComponent("dTitle", GenComponentsDTitleDTitleClass)
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_wj_selectText = resolveEasyComponent("wj-selectText", GenUniModulesWjSelectTextComponentsWjSelectTextWjSelectTextClass)
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_QuestionOptionsTest = resolveEasyComponent("QuestionOptionsTest", GenComponentsQuestionOptionsTestQuestionOptionsTestClass)
                val _component_wj_input = resolveEasyComponent("wj-input", GenUniModulesWjInputComponentsWjInputWjInputClass)
                val _component_paperSubject = resolveEasyComponent("paperSubject", GenComponentsPaperSubjectPaperSubjectClass)
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                val _component_examinationPopup = resolveEasyComponent("examinationPopup", GenComponentsExaminationPopupExaminationPopupClass)
                val _component_readResult = resolveEasyComponent("readResult", GenComponentsReadResultReadResultClass)
                val _component_virtualKeyboard = resolveEasyComponent("virtualKeyboard", GenComponentsVirtualKeyboardVirtualKeyboardClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to ""), _uM("right" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_BackgroundImage, _uM("src" to "/static/ico/time.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "time_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE("text", _uM("class" to "_time"), _tD(unref(timeText)), 1)
                                    )
                                }
                                ), "_" to 1))
                            )
                        }
                        ), "default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_dTitle, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _tD(if (unref(type) == "40") {
                                            "模式二：语法训练"
                                        } else {
                                            unref(unitTitle)
                                        }
                                        )
                                    )
                                }
                                ), "_" to 1))
                            )
                        }
                        ), "_" to 1)),
                        if (isTrue(!unref(isShowResult))) {
                            _cE("view", _uM("key" to 0, "class" to "d_content"), _uA(
                                _cE("scroll-view", _uM("direction" to "vertical", "class" to "left_nav"), _uA(
                                    _cE(Fragment, null, RenderHelpers.renderList(unref(fenArr), fun(item, index, __index, _cached): Any {
                                        return _cE("view", null, _uA(
                                            _cE("view", _uM("style" to _nS(_uM("flex-wrap" to "wrap", "flex-direction" to "row"))), _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(item, fun(subItem, index2, __index, _cached): Any {
                                                    return _cE("view", _uM("style" to _nS(_uM("width" to "25%", "align-items" to "center", "justify-content" to "center")), "onClick" to fun(){
                                                        nowIndex.value = index.toString(10) + "-" + index2.toString(10)
                                                        isShowKeyboard.value = false
                                                    }), _uA(
                                                        if (isTrue(unref(tempIndexArr).includes(index.toString(10) + "-" + index2.toString(10)) && unref(nowIndex) != index.toString(10) + "-" + index2.toString(10))) {
                                                            _cE("text", _uM("key" to 0, "class" to "item item_complete"), _tD(index2 + 1), 1)
                                                        } else {
                                                            if (unref(nowIndex) == index.toString(10) + "-" + index2.toString(10)) {
                                                                _cE("text", _uM("key" to 1, "class" to "item item_now"), _tD(index2 + 1), 1)
                                                            } else {
                                                                _cE("text", _uM("key" to 2, "class" to "item"), _tD(index2 + 1), 1)
                                                            }
                                                        }
                                                    ), 12, _uA(
                                                        "onClick"
                                                    ))
                                                }), 256)
                                            ), 4)
                                        ))
                                    }), 256)
                                )),
                                _cE("view", _uM("class" to "right_content"), _uA(
                                    if (isTrue(!unref(isShowResult))) {
                                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex" to "1"))), _uA(
                                            _cE("view", _uM("class" to "title", "style" to _nS(_uM("position" to "absolute", "top" to "0", "width" to "100%", "left" to "0"))), _uA(
                                                _cE("text", _uM("class" to "num"))
                                            ), 4),
                                            _cE("view", _uM("class" to "word_box"), _uA(
                                                if (unref(nowSub)?.problemType != "13") {
                                                    _cE(Fragment, _uM("key" to 0), _uA(
                                                        _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("max-height" to "200rpx"))), _uA(
                                                            _cE("view", _uM("style" to _nS(_uM("padding-right" to "80rpx", "margin-bottom" to "10rpx"))), _uA(
                                                                _cE("view", _uM("id" to "styleTransformWithOrigin", "style" to _nS(_uM("max-height" to "100rpx"))), _uA(
                                                                    _cV(_component_wj_selectText, _uM("wordBookEnabled" to false, "center" to "center", "style" to _nS(_uM("height" to "100%")), "text" to unref(nowSub)?.questionContent, "onHeightFn" to heightFn), null, 8, _uA(
                                                                        "style",
                                                                        "text"
                                                                    ))
                                                                ), 4)
                                                            ), 4)
                                                        ), 4),
                                                        if (isTrue(unref(nowSub)?.soundUrl)) {
                                                            _cE("view", _uM("key" to 0, "style" to _nS(_uM("justify-content" to "flex-start", "align-items" to "flex-start", "margin-bottom" to "10rpx"))), _uA(
                                                                _cV(_component_u_playMp3, _uM("src" to unref(nowSub)?.soundUrl, "tplType" to 4, "isPlayNum" to true, "maxPlayNum" to unref(maxPlayNum), "onPlayEnd" to playEndFn, "isNotIn" to true), null, 8, _uA(
                                                                    "src",
                                                                    "maxPlayNum"
                                                                ))
                                                            ), 4)
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                                            _cE("view", null, _uA(
                                                                _cV(_component_QuestionOptionsTest, _uM("style" to _nS(_uM("width" to "80%", "margin" to "0 auto")), "options" to unref(nowSub)?.options, "modelValue" to unref(selectedOption), "onUpdate:modelValue" to fun(`$event`: Number){
                                                                    trySetRefValue(selectedOption, `$event`)
                                                                }), null, 8, _uA(
                                                                    "style",
                                                                    "options",
                                                                    "modelValue"
                                                                ))
                                                            ))
                                                        ), 4)
                                                    ), 64)
                                                } else {
                                                    _cE("scroll-view", _uM("key" to 1, "direction" to "vertical", "style" to _nS(_uM("padding-right" to "80rpx", "flex" to "1"))), _uA(
                                                        _cE("view", null, _uA(
                                                            _cE(Fragment, null, RenderHelpers.renderList(unref(nowAnswer), fun(item, index, __index, _cached): Any {
                                                                return _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "flex-wrap" to "wrap"))), _uA(
                                                                    _cE(Fragment, null, RenderHelpers.renderList(item.input, fun(inp, subIndex, __index, _cached): Any {
                                                                        return _cE("view", _uM("style" to _nS(_uM("flex-wrap" to "wrap", "flex-direction" to "row"))), _uA(
                                                                            _cE(Fragment, null, RenderHelpers.renderList((inp as UTSArray<timi>), fun(inps, ssubIndex, __index, _cached): Any {
                                                                                return _cE("view", null, _uA(
                                                                                    if (inps.type == "text") {
                                                                                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                                                                            _cE("text", _uM("class" to "fTimesNewRoman", "style" to _nS(_uM("font-size" to "20rpx", "line-height" to "30rpx", "margin-right" to "4rpx"))), _tD(inps.content), 5)
                                                                                        ), 4)
                                                                                    } else {
                                                                                        _cE("view", _uM("key" to 1, "style" to _nS(_uM("min-width" to "90rpx", "min-height" to "30rpx", "margin" to "0 4rpx 0 -4rpx", "position" to "relative", "border-bottom" to "1rpx solid #000"))), _uA(
                                                                                            _cV(_component_wj_input, _uM("center" to "center", "style" to _nS(_uM("width" to "100%", "height" to "32rpx", "position" to "absolute", "z-index" to "99")), "text" to inps.content, "onUpdate:text" to fun(`$event`: String){
                                                                                                inps.content = `$event`
                                                                                            }, "onInput" to fun(`$event`: Any){
                                                                                                changeInput(`$event`, index, subIndex, ssubIndex)
                                                                                            }, "onInputTap" to inputClick, "isAcquisition" to true), null, 8, _uA(
                                                                                                "style",
                                                                                                "text",
                                                                                                "onUpdate:text",
                                                                                                "onInput"
                                                                                            )),
                                                                                            _cE("text", _uM("style" to _nS(_uM("color" to "#fff", "margin" to "0 16rpx", "font-size" to "26rpx"))), _tD(inps.content), 5)
                                                                                        ), 4)
                                                                                    },
                                                                                    if (inps.type == "image") {
                                                                                        _cE("view", _uM("key" to 2, "style" to _nS(_uM("width" to "400rpx", "margin" to "10rpx 0"))), _uA(
                                                                                            _cE("image", _uM("src" to inps.src!!), null, 8, _uA(
                                                                                                "src"
                                                                                            ))
                                                                                        ), 4)
                                                                                    } else {
                                                                                        _cC("v-if", true)
                                                                                    }
                                                                                ))
                                                                            }), 256)
                                                                        ), 4)
                                                                    }), 256)
                                                                ), 4)
                                                            }), 256)
                                                        )),
                                                        if (isTrue(unref(nowSub)?.soundUrl)) {
                                                            _cE("view", _uM("key" to 0, "style" to _nS(_uM("margin-top" to "10rpx", "justify-content" to "flex-start", "align-items" to "flex-start"))), _uA(
                                                                _cV(_component_u_playMp3, _uM("src" to unref(nowSub)?.soundUrl, "tplType" to 4, "isPlayNum" to true, "maxPlayNum" to unref(maxPlayNum), "onPlayEnd" to playEndFn, "isNotIn" to true), null, 8, _uA(
                                                                    "src",
                                                                    "maxPlayNum"
                                                                ))
                                                            ), 4)
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                    ), 4)
                                                }
                                            )),
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
                                )),
                                _cV(_component_paperSubject, _uM("isShow" to unref(isShowPaperSubject), "onUpdate:isShow" to fun(`$event`: Boolean){
                                    trySetRefValue(isShowPaperSubject, `$event`)
                                }, "onOk" to submit), null, 8, _uA(
                                    "isShow"
                                ))
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (unref(type) != "40") {
                            _cE(Fragment, _uM("key" to 1), _uA(
                                if (isTrue(unref(isShowFail))) {
                                    _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("view", _uM("style" to _nS(_uM("width" to "750rpx", "height" to "469rpx"))), _uA(
                                                _cE("image", _uM("src" to "/static/ico/test/fail.png", "mode" to "", "style" to _nS(_uM("width" to "100%", "height" to "100%"))), null, 4),
                                                _cE("view", _uM("style" to _nS(_uM("position" to "absolute", "left" to "0", "top" to "0", "width" to "100%", "height" to "100%", "align-items" to "center", "justify-content" to "center"))), _uA(
                                                    _cE("image", _uM("src" to "/static/ico/close.png", "class" to "close_ico", "mode" to "", "onClick" to fun(){
                                                        isShowResult.value = true
                                                        isShowFail.value = false
                                                    }), null, 8, _uA(
                                                        "onClick"
                                                    )),
                                                    _cE("text", _uM("class" to "fail_text", "style" to _nS(_uM("margin-top" to "80rpx"))), "很遗憾，闯关失败", 4),
                                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "flex-end", "margin-top" to "10rpx"))), _uA(
                                                        _cE("text", _uM("class" to "source"), _tD(unref(scoreVal)), 1),
                                                        _cE("text", _uM("class" to "source", "style" to _nS(_uM("font-size" to "29rpx", "margin-bottom" to "6rpx"))), "分", 4)
                                                    ), 4),
                                                    _cE("text", _uM("class" to "result_btn", "style" to _nS(_uM("margin-top" to "10rpx")), "onClick" to fun(){
                                                        isShowResult.value = true
                                                        isShowFail.value = false
                                                    }), "查看结果", 12, _uA(
                                                        "onClick"
                                                    ))
                                                ), 4)
                                            ), 4)
                                        )
                                    }), "_" to 1))
                                } else {
                                    _cC("v-if", true)
                                },
                                if (isTrue(unref(isShowAdopt))) {
                                    _cV(_component_u_popup, _uM("key" to 1), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("view", _uM("style" to _nS(_uM("width" to "750rpx", "height" to "469rpx"))), _uA(
                                                _cE("image", _uM("src" to "/static/ico/test/success.png", "mode" to "", "style" to _nS(_uM("width" to "100%", "height" to "100%"))), null, 4),
                                                _cE("view", _uM("style" to _nS(_uM("position" to "absolute", "left" to "0", "top" to "0", "width" to "100%", "height" to "100%", "align-items" to "center", "justify-content" to "center"))), _uA(
                                                    _cE("image", _uM("src" to "/static/ico/close.png", "class" to "close_ico", "mode" to "", "onClick" to fun(){
                                                        isShowResult.value = true
                                                        isShowAdopt.value = false
                                                    }), null, 8, _uA(
                                                        "onClick"
                                                    )),
                                                    if (unref(advancedNum) == 0) {
                                                        _cE("text", _uM("key" to 0, "class" to "fail_text", "style" to _nS(_uM("margin-top" to "80rpx"))), "恭喜你！闯关成功", 4)
                                                    } else {
                                                        _cE("text", _uM("key" to 1, "class" to "fail_text", "style" to _nS(_uM("margin-top" to "80rpx"))), "恭喜你！晋升" + _tD(unref(advancedNum)) + "星", 5)
                                                    },
                                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "flex-end", "margin-top" to "10rpx"))), _uA(
                                                        _cE("text", _uM("class" to "source", "style" to _nS(_uM("color" to "#3A58EB"))), _tD(unref(scoreVal)), 5),
                                                        _cE("text", _uM("class" to "source", "style" to _nS(_uM("font-size" to "29rpx", "margin-bottom" to "6rpx", "color" to "#3A58EB"))), "分", 4)
                                                    ), 4),
                                                    _cE("text", _uM("class" to "result_btn", "style" to _nS(_uM("margin-top" to "10rpx")), "onClick" to fun(){
                                                        isShowResult.value = true
                                                        isShowAdopt.value = false
                                                    }), "查看结果", 12, _uA(
                                                        "onClick"
                                                    ))
                                                ), 4)
                                            ), 4)
                                        )
                                    }), "_" to 1))
                                } else {
                                    _cC("v-if", true)
                                }
                            ), 64)
                        } else {
                            _cV(_component_examinationPopup, _uM("key" to 2, "show" to unref(showTpl2), "onUpdate:show" to fun(`$event`: Boolean){
                                trySetRefValue(showTpl2, `$event`)
                            }
                            , "isShowResult" to unref(isShowResult), "onUpdate:isShowResult" to fun(`$event`: Boolean){
                                trySetRefValue(isShowResult, `$event`)
                            }
                            , "tpl" to 2, "onClose" to fun(){
                                isShowResult.value = true
                                showTpl2.value = false
                            }
                            ), _uM("title" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    " 语法训练得分 "
                                )
                            }
                            ), "content" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("text", _uM("style" to _nS(_uM("font-weight" to "bold", "font-size" to "36rpx", "color" to "#FA9600", "text-align" to "center", "margin-top" to "20rpx"))), _tD(unref(scoreVal)) + "分", 5)
                                )
                            }
                            ), "btnText" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    " 查看结果 "
                                )
                            }
                            ), "_" to 1), 8, _uA(
                                "show",
                                "isShowResult",
                                "onClose"
                            ))
                        }
                        ,
                        if (isTrue(unref(isShowResult))) {
                            _cE("view", _uM("key" to 3, "class" to "d_content"), _uA(
                                _cV(_component_readResult, _uM("id" to unref(nowResultId), "isYF" to true, "isTest" to true, "onReset" to resetFn, "showNext" to (unref(type) == "40"), "zlddNextTask" to unref(zlddNextTask), "studyTaskId" to unref(studyTaskId)), null, 8, _uA(
                                    "id",
                                    "showNext",
                                    "zlddNextTask",
                                    "studyTaskId"
                                ))
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(isShowKeyboard))) {
                            _cE("view", _uM("key" to 4, "style" to _nS(_uM("position" to "absolute", "left" to "0", "right" to "0", "bottom" to "0"))), _uA(
                                _cV(_component_virtualKeyboard, _uM("onClose" to fun(){
                                    isShowKeyboard.value = false
                                }), null, 8, _uA(
                                    "onClose"
                                ))
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "flexDirection" to "row")), "right_content" to _uM(".d_content " to _uM("width" to "530rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "position" to "relative")), "title" to _uM(".d_content .right_content " to _uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginTop" to "14.06rpx", "paddingTop" to 0, "paddingRight" to "14.65rpx", "paddingBottom" to 0, "paddingLeft" to "14.65rpx")), "num" to _uM(".d_content .right_content .title " to _uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D")), "word_box" to _uM(".d_content .right_content " to _uM("marginTop" to "20rpx", "marginRight" to "20rpx", "marginBottom" to "20rpx", "marginLeft" to "20rpx", "flex" to 1)), "submit_paper" to _uM(".d_content .right_content " to _uM("width" to "83.79rpx", "height" to "33.98rpx", "position" to "absolute", "right" to "-3rpx", "bottom" to "32rpx")), "_sub" to _uM(".d_content .right_content .submit_paper " to _uM("width" to "60rpx", "marginLeft" to "23.79rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "color" to "#5B77FF", "fontWeight" to "bold", "fontSize" to "18rpx")), "adopt_box" to _uM(".d_content .right_content " to _uM("alignItems" to "center")), "badge" to _uM(".d_content .right_content .adopt_box " to _uM("width" to "114.84rpx", "height" to "110.16rpx")), "adopt_status" to _uM(".d_content .right_content .adopt_box " to _uM("fontWeight" to "bold", "fontSize" to "23rpx", "color" to "#FA9600", "marginTop" to "15rpx")), "fail_text" to _pS(_uM("fontWeight" to "bold", "fontSize" to "29rpx", "color" to "#3D3D3D")), "source" to _pS(_uM("fontWeight" to "bold", "fontSize" to "47rpx", "color" to "#FA9600")), "result_btn" to _pS(_uM("width" to "101rpx", "height" to "35rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "fontWeight" to "bold", "fontSize" to "14rpx", "lineHeight" to "35rpx", "color" to "#ffffff", "textAlign" to "center")), "close_ico" to _pS(_uM("width" to "17.58rpx", "height" to "17.58rpx", "position" to "absolute", "top" to "117.19rpx", "right" to "206.25rpx")), "hide" to _pS(_uM("!bottom" to "-9999rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
