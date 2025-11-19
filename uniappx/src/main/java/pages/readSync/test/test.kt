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
import uts.sdk.modules.uniPreviewImage.PreviewImageOptions as PreviewImageOptions1
import io.dcloud.uniapp.extapi.getElementById as uni_getElementById
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import uts.sdk.modules.uniPreviewImage.previewImage as uni_previewImage
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesReadSyncTestTest : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesReadSyncTestTest) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesReadSyncTestTest
            val _cache = __ins.renderCache
            var examinationIteratorFn = examinationIterator()
            val nowSubArr = ref<UTSArray<timi>?>(null)
            val answerList = ref(_uA<UTSArray<UTSArray<answerItemType>>>(_uA(
                _uA()
            )))
            val fenArr = ref(_uA<UTSArray<examinationList>>(_uA()))
            val nowAnswer = ref<UTSArray<answerItemType>?>(null)
            val unitTitle = ref("")
            val isShowPaperSubject = ref(false)
            val showEndPupup = ref(false)
            val config = ref(UTSJSONObject())
            val tempIndexArr = ref(_uA<String>())
            val timeText = ref("00:00")
            val isShowPupup = ref(false)
            val dcFen = ref(0)
            val yfFen = ref(0)
            val ydFen = ref(0)
            val _tempstudyTaskId = ref(0)
            val scoreVal = ref(0)
            val showType = ref(1)
            val startTime = ref("")
            val timer = ref(0)
            val _countDown = countDown()
            val studyTaskId = ref(0)
            val endData = ref(object : UTSJSONObject() {
                var choose_1_ProblemType = "16"
                var choose_1_pass = false
                var choose_1_rate: Number = 0
                var choose_2_ProblemType = "17"
                var choose_2_pass = false
                var choose_2_rate: Number = 0
            })
            fun gen_heightFn_fn(h: Number) {}
            val heightFn = ::gen_heightFn_fn
            val secondVal = ref(0)
            val nowIndex = ref("")
            val nowSub = ref<examinationList?>(null)
            val isShowKey = ref(true)
            val tempShow = ref(true)
            val isShowKeyboard = ref(false)
            val needShowKeyboard = ref(ucsShare.getStore("isShowKeyboard") as Boolean)
            val exerciseNum = ref(0)
            val topicNum = computed(fun(): Number {
                var _num: Number = 0
                fenArr.value.forEach(fun(arr2){
                    arr2.forEach(fun(i){
                        _num++
                    }
                    )
                }
                )
                return _num
            }
            )
            fun gen_tranY_fn(num: Number) {
                uni_getElementById("tranBox")?.style?.setProperty("transition-property", "transform")
                uni_getElementById("tranBox")?.style?.setProperty("transition-duration", "100ms")
                uni_getElementById("tranBox")?.style?.setProperty("transform", "translateY(-" + num + "px)")
            }
            val tranY = ::gen_tranY_fn
            watch(isShowKeyboard, fun(kVal: Boolean){
                if (!kVal) {
                    if (needShowKeyboard.value) {
                        uni_getElementById("tranBox")?.style?.setProperty("transform", "translateY(0px)")
                    }
                }
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
            val typeMap: UTSJSONObject = object : UTSJSONObject() {
                var `14` = "选词填空"
                var `15` = "语法填空"
                var `16` = "完形填空"
                var `17` = "传统阅读"
                var `18` = "六选五"
            }
            val zlddStopTask = ref(0)
            val isShowResult = ref(false)
            val nowResultId = ref(0)
            fun gen_inputClick_fn() {
                if (needShowKeyboard.value) {
                    isShowKeyboard.value = true
                }
            }
            val inputClick = ::gen_inputClick_fn
            fun gen_previewImage_fn(img: String) {
                var list = _uA<String>()
                list.push(img)
                uni_previewImage(PreviewImageOptions1(urls = list, loop = false, fail = fun(e) {
                    console.log(e)
                }
                ))
            }
            val previewImage = ::gen_previewImage_fn
            fun gen_switch1Change_fn(e: UniSwitchChangeEvent) {
                needShowKeyboard.value = e.detail.value
            }
            val switch1Change = ::gen_switch1Change_fn
            fun gen_getItemNumStatus_fn(index: Number): String {
                var allNum = fenArr.value[index].length
                var makeNum: Number = 0
                answerList.value[index].forEach(fun(item){
                    var isMake = false
                    item.forEach(fun(sub){
                        if (isMake != true) {
                            if (sub.input == null && sub.options.length == 0) {
                                var fillter = item.filter(fun(f): Boolean {
                                    return f.content != ""
                                }
                                )
                                if (fillter.length > 0) {
                                    isMake = true
                                }
                            }
                            if (sub.input != null) {
                                var fillter = sub.input!!.filter(fun(f): Boolean {
                                    return f.filter(fun(q): Boolean {
                                        return q.type == "input" && q.content != ""
                                    }
                                    ).length > 0
                                }
                                )
                                if (fillter.length > 0) {
                                    isMake = true
                                }
                            }
                            if (sub.input == null && sub.options.length != 0) {
                                isMake = sub.isCheck
                            }
                        }
                    }
                    )
                    if (isMake) {
                        makeNum++
                    }
                }
                )
                return "" + makeNum.toString(10) + " / " + allNum.toString(10)
            }
            val getItemNumStatus = ::gen_getItemNumStatus_fn
            watch(showEndPupup, fun(kVal: Boolean){
                if (!kVal && !isShowResult.value) {
                    uni_navigateBack(null)
                }
            }
            )
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
            examinationIteratorFn.change(fun(list){
                answerList.value = list
                var _nowIndex = nowIndex.value
                if (_nowIndex == "") {
                    return
                }
                var valArr = _nowIndex.split("-")
                nowSub.value = fenArr.value[parseInt(valArr[0])][parseInt(valArr[1])]
                nowAnswer.value = answerList.value[parseInt(valArr[0])][parseInt(valArr[1])]
            }
            )
            watch(nowIndex, fun(kVal: String){
                if (kVal == "") {
                    return
                }
                tempShow.value = false
                setTimeout(fun(){
                    tempShow.value = true
                }
                , 0)
                var valArr = kVal.split("-")
                nowSub.value = fenArr.value[parseInt(valArr[0])][parseInt(valArr[1])]
                nowAnswer.value = answerList.value[parseInt(valArr[0])][parseInt(valArr[1])]
            }
            )
            val participleList = ref(_uA<tmitem>(tmitem(isCheck = false, title = "he")))
            val tempPath = ref<String>("")
            fun gen_getProblemType_fn(type: UTSArray<examinationList>?): String {
                if (type == null) {
                    return ""
                }
                if (type.length == 0) {
                    return ""
                }
                val mapStr = typeMap.getString(type[0].problemType)
                return if (mapStr == null) {
                    "未知类型"
                } else {
                    mapStr
                }
            }
            val getProblemType = ::gen_getProblemType_fn
            fun gen_makeExerciseProblemBoList_fn(): UTSArray<makeSubTop> {
                var retArr = _uA<makeSubTop>()
                answerList.value.forEach(fun(item){
                    item.forEach(fun(_content){
                        var make = makeSubTop(problemId = 0, userOption = "", userBlankAnswer = "", subProblemList = _uA<makeSub>())
                        _content.forEach(fun(sub){
                            if (sub.input == null && sub.options.length == 0) {
                                make.problemId = sub.parentId
                                make.subProblemList.push(makeSub(problemId = sub.id, userOption = "", userBlankAnswer = sub.content))
                            }
                            if (sub.input != null) {
                                var fillter = sub.input!!.reduce(fun(acc, line: UTSArray<timi>): UTSArray<timi> {
                                    return acc.concat(line.filter(fun(item): Boolean {
                                        return item.type === "input"
                                    }
                                    ))
                                }
                                , _uA())
                                make.problemId = sub.id
                                fillter.forEach(fun(_sub){
                                    make.subProblemList.push(makeSub(problemId = _sub.id, userOption = "", userBlankAnswer = _sub.content))
                                }
                                )
                            }
                            if (sub.input == null && sub.options.length != 0) {
                                var fillter = sub.options.filter(fun(f): Boolean {
                                    return f.isCheck
                                }
                                )
                                make.problemId = sub.parentId
                                fillter.forEach(fun(_sub){
                                    make.subProblemList.push(makeSub(problemId = _sub.id, userOption = _sub.option, userBlankAnswer = ""))
                                }
                                )
                                if (fillter.length == 0) {
                                    make.subProblemList.push(makeSub(problemId = sub.options[0].id, userOption = "", userBlankAnswer = ""))
                                }
                            }
                        }
                        )
                        retArr.push(make)
                    }
                    )
                }
                )
                return retArr
            }
            val makeExerciseProblemBoList = ::gen_makeExerciseProblemBoList_fn
            val type = ref("0")
            fun gen_submit_fn() {
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/problem/api/exerciseSubmit"), method = "POST", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var type = type.value
                    var textbookUnitId = getTextbookUnitId()
                    var module = getModelKey("同步阅读训练")
                    var subModule = if (type.value != "40") {
                        getSubModelKey("同步阅读闯关")
                    } else {
                        getSubModelKey("模式三：阅读训练")
                    }
                    var exerciseProblemBoList = makeExerciseProblemBoList()
                    var startTime = startTime.value
                    var second = secondVal.value
                    var studyTaskId = if (type.value == "40") {
                        _tempstudyTaskId.value
                    } else {
                        studyTaskId.value
                    }
                }, header = getHeader(), success = fun(res){
                    clearInterval(timer.value)
                    _countDown.clear()
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    console.log(responseData)
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    nowResultId.value = responseData.data?.getNumber("id")!!
                    console.log(responseData.data)
                    if (type.value == "40") {
                        uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/zlddTask/api/" + studyTaskId.value), method = "GET", header = getHeader(), success = fun(res){
                            val responseData = res.data
                            if (responseData == null) {
                                return
                            }
                            console.log(responseData)
                            if (responseData.code as Number != 200) {
                                uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                                return
                            }
                            isShowPupup.value = true
                            dcFen.value = parseFloat(responseData.data?.getString("englishScore") ?: "0")
                            yfFen.value = parseFloat(responseData.data?.getString("grammarTrainScore") ?: "0")
                            ydFen.value = parseFloat(responseData.data?.getString("readTrainScore") ?: "0")
                        }
                        , fail = fun(err){
                            console.log(err)
                        }
                        , complete = fun(_){}))
                        zlddStopTask.value = responseData.data?.getNumber("zlddStopTask") ?: 0
                        return
                    }
                    var problemTypeExerciseRecord = responseData.data?.getJSON("problemTypeExerciseRecord")
                    if (problemTypeExerciseRecord == null) {
                        isShowPupup.value = true
                        scoreVal.value = parseInt(responseData.data?.getString("score") ?: "0")
                        var isPass = responseData.data?.getString("isPass")
                        if (isPass!! == "0") {
                            showType.value = 1
                        } else {
                            showType.value = 2
                        }
                    } else {
                        endData.value = problemTypeExerciseRecord!!
                        showEndPupup.value = true
                    }
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
                uni_request<Result<UTSArray<examinationList>>>(RequestOptions(url = getUrl("/biz/problem/api/list"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var textbookUnitId = getTextbookUnitId()
                    var subjectType = "英语"
                    var module = getModelKey("同步阅读训练")
                    var subModule = getSubModelKey("同步阅读闯关")
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
                    if (responseData.data!!.length == 0) {
                        uni_showToast(ShowToastOptions(title = "题目为空", icon = "none"))
                        return
                    }
                    var arr = examinationGroup(responseData.data!!)
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
                        _tempstudyTaskId.value = responseData.data?.getNumber("studyTaskId") ?: 0
                        uni_request<Result<UTSArray<examinationList>>>(RequestOptions(url = getUrl("/biz/problem/api/studyTask/list"), method = "GET", data = object : UTSJSONObject() {
                            var type = type.value
                            var studyTaskId = _tempstudyTaskId.value
                            var IsExercisedTest = "1"
                        }, header = getHeader(), success = fun(res){
                            val responseData = res.data
                            if (responseData == null) {
                                return
                            }
                            if (responseData.code as Number != 200) {
                                uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                                return
                            }
                            uni_hideLoading()
                            var arr = examinationGroup(responseData.data!!)
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
                    var arr = examinationGroup(responseData.data!!)
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
            fun gen_getLastData_fn(type: Number) {
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/problem/api/getLastExerciseRecord"), method = "GET", data = _uO("textbookId" to if (studyTaskId.value == 0) {
                    getTextBookId()
                } else {
                    ""
                }
                , "textbookUnitId" to if (studyTaskId.value == 0) {
                    getTextbookUnitId()
                } else {
                    ""
                }
                , "module" to if (studyTaskId.value == 0) {
                    getModelKey("同步阅读训练")
                } else {
                    ""
                }
                , "subModule" to if (studyTaskId.value == 0) {
                    getSubModelKey("同步阅读闯关")
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
                    if (ucsShare.getState("debug") as Boolean != true) {
                        if (responseData.data != null) {
                            var viewErrorAnswerAnalysisNum = responseData.data!!.getNumber("viewErrorAnswerAnalysisNum")
                            var errorAnswerNum = responseData.data!!.getNumber("errorAnswerNum")
                            if ((viewErrorAnswerAnalysisNum ?: 0) < (errorAnswerNum ?: 0)) {
                                isShowResult.value = true
                                nowResultId.value = responseData.data!!.getNumber("id")!!
                                return
                            }
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
            val resetType = ref(0)
            fun gen_resetFn_fn() {
                nowIndex.value = ""
                isShowResult.value = false
                var _type = resetType.value
                if (_type == 1) {
                    getLastData(10)
                }
                if (_type == 2) {
                    getLastData(0)
                }
            }
            val resetFn = ::gen_resetFn_fn
            onLoad(fun(event: OnLoadOptions){
                config.value = getConfig("同步阅读配置")
                unitTitle.value = event["unitTitle"] ?: ""
                exerciseNum.value = parseInt(event["exerciseNum"] ?: "0")
                studyTaskId.value = parseInt(event["studyTaskId"] ?: "0")
                if (event["type"] != null) {
                    type.value = event["type"] ?: "0"
                    resetType.value = 1
                    getLastData(10)
                } else {
                    if (event["isTraining"] != null) {
                        type.value = "40"
                        getSubjectStudyTask()
                        return
                    }
                    resetType.value = 2
                    getLastData(0)
                }
            }
            )
            onUnmounted(fun(){
                clearInterval(timer.value)
                _countDown.clear()
            }
            )
            onReady(fun(){
                setScreen()
                run {
                    var i: Number = 0
                    while(i < 10){
                        participleList.value.push(tmitem(isCheck = false, title = "docut"))
                        i++
                    }
                }
            }
            )
            return fun(): Any? {
                val _component_dTitle = resolveEasyComponent("dTitle", GenComponentsDTitleDTitleClass)
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_switch = resolveComponent("switch")
                val _component_wj_selectText = resolveEasyComponent("wj-selectText", GenUniModulesWjSelectTextComponentsWjSelectTextWjSelectTextClass)
                val _component_wj_input = resolveEasyComponent("wj-input", GenUniModulesWjInputComponentsWjInputWjInputClass)
                val _component_paperSubject = resolveEasyComponent("paperSubject", GenComponentsPaperSubjectPaperSubjectClass)
                val _component_readResult = resolveEasyComponent("readResult", GenComponentsReadResultReadResultClass)
                val _component_virtualKeyboard = resolveEasyComponent("virtualKeyboard", GenComponentsVirtualKeyboardVirtualKeyboardClass)
                val _component_examinationPopup = resolveEasyComponent("examinationPopup", GenComponentsExaminationPopupExaminationPopupClass)
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
                                            "模式三：阅读训练"
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
                                _cE("view", null, _uA(
                                    _cE("view", _uM("style" to _nS(_uM("background" to "rgba(255, 255, 255, 0.2)", "padding" to "5.86rpx 0 7.03rpx"))), _uA(
                                        _cE("text", _uM("style" to _nS(_uM("color" to "#DEE9FF", "font-size" to "12rpx", "line-height" to "18rpx", "text-align" to "center"))), "共" + _tD(unref(topicNum)) + "个题目", 5)
                                    ), 4),
                                    _cE("scroll-view", _uM("direction" to "vertical", "class" to "left_nav", "style" to _nS(_uM("width" to "120rpx"))), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(fenArr), fun(item, index, __index, _cached): Any {
                                            return _cE("view", null, _uA(
                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#fff", "line-height" to "18rpx"))), _tD(index + 1) + "、" + _tD(getProblemType(item)), 5),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#DEE9FF", "line-height" to "18rpx"))), _tD(getItemNumStatus(index)), 5)
                                                ), 4),
                                                _cE("view", _uM("style" to _nS(_uM("flex-wrap" to "wrap", "flex-direction" to "row", "padding-right" to "-4rpx"))), _uA(
                                                    _cE(Fragment, null, RenderHelpers.renderList(item, fun(subItem, index2, __index, _cached): Any {
                                                        return _cE("view", _uM("style" to _nS(_uM("align-items" to "flex-start", "justify-content" to "flex-start", "margin-right" to "4rpx")), "onClick" to fun(){
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
                                    ), 4),
                                    _cE("view", _uM("style" to _nS(_uM("background" to "rgba(255, 255, 255, 0.2)", "align-items" to "center", "padding-bottom" to "10rpx"))), _uA(
                                        _cE("view", _uM("class" to "keyboard-switch"), _uA(
                                            _cE("image", _uM("src" to "/static/ico/keyboard_ico.png", "mode" to "", "style" to _nS(_uM("width" to "22.85rpx", "height" to "15.23rpx"))), null, 4),
                                            _cV(_component_switch, _uM("checked" to unref(needShowKeyboard), "onChange" to switch1Change, "color" to "#3A58EB", "style" to _nS(_uM("transform" to "scale(0.7)"))), null, 8, _uA(
                                                "checked",
                                                "style"
                                            ))
                                        ))
                                    ), 4)
                                )),
                                _cE("view", _uM("class" to "right_content"), _uA(
                                    _cE("view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
                                        _cE("view", _uM("class" to "time")),
                                        _cE("view", _uM("class" to "read-test"), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                    _cE("image", _uM("src" to "/static/ico/flag.png", "style" to _nS(_uM("width" to "7.03rpx", "height" to "9.38rpx")), "mode" to ""), null, 4),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#FA9600", "margin-left" to "4.7rpx"))), "第" + _tD(unref(exerciseNum) + 1) + "次挑战", 5)
                                                ), 4),
                                                _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1")), "id" to "tranBox"), _uA(
                                                    _cE("view", _uM("style" to _nS("flex-wrap: wrap;flex-direction: row;" + (if (!_uA(
                                                        "14",
                                                        "18",
                                                        "15"
                                                    ).includes(unref(nowSub)?.problemType ?: "")) {
                                                        "height: 100%;"
                                                    } else {
                                                        "height: auto;"
                                                    }))), _uA(
                                                        if (isTrue(!_uA(
                                                            "14",
                                                            "18",
                                                            "15"
                                                        ).includes(unref(nowSub)?.problemType ?: ""))) {
                                                            _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex" to "1", "height" to "100%"))), _uA(
                                                                _cE("view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
                                                                    _cV(_component_wj_selectText, _uM("style" to _nS(_uM("height" to "100%")), "center" to "center", "text" to unref(nowSub)?.questionContent, "wordBookEnabled" to false), null, 8, _uA(
                                                                        "style",
                                                                        "text"
                                                                    ))
                                                                ), 4)
                                                            ), 4)
                                                        } else {
                                                            _cE(Fragment, _uM("key" to 1), RenderHelpers.renderList(unref(nowAnswer), fun(item, index, __index, _cached): Any {
                                                                return _cE(Fragment, null, _uA(
                                                                    _cE(Fragment, null, RenderHelpers.renderList(item.input, fun(inp, subIndex, __index, _cached): Any {
                                                                        return _cE("view", _uM("style" to _nS(_uM("flex-wrap" to "wrap", "flex-direction" to "row"))), _uA(
                                                                            _cE("view", _uM("style" to _nS(_uM("width" to "20rpx"))), null, 4),
                                                                            _cE(Fragment, null, RenderHelpers.renderList((inp as UTSArray<timi>), fun(inps, ssubIndex, __index, _cached): Any {
                                                                                return _cE("view", null, _uA(
                                                                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                                                                        if (inps.type == "text") {
                                                                                            _cE("text", _uM("key" to 0, "class" to "fTimesNewRoman", "style" to _nS(_uM("font-size" to "20rpx", "line-height" to "30rpx", "margin-right" to "4rpx"))), _tD(inps.content), 5)
                                                                                        } else {
                                                                                            _cC("v-if", true)
                                                                                        }
                                                                                    ), 4),
                                                                                    if (inps.type == "input") {
                                                                                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("min-width" to "90rpx", "min-height" to "30rpx", "margin" to "0 4rpx 0 -4rpx", "position" to "relative", "border-bottom" to "1rpx solid #000"))), _uA(
                                                                                            _cV(_component_wj_input, _uM("center" to "center", "style" to _nS(_uM("width" to "100%", "height" to "32rpx", "position" to "absolute", "z-index" to "99")), "text" to inps.content, "onUpdate:text" to fun(`$event`: String){
                                                                                                inps.content = `$event`
                                                                                            }, "onInput" to fun(`$event`: Any){
                                                                                                changeInput(`$event`, index, subIndex, ssubIndex)
                                                                                            }, "onInputTap" to inputClick, "isAcquisition" to true, "onTranY" to tranY), null, 8, _uA(
                                                                                                "style",
                                                                                                "text",
                                                                                                "onUpdate:text",
                                                                                                "onInput"
                                                                                            )),
                                                                                            _cE("text", _uM("style" to _nS(_uM("color" to "#fff", "margin" to "0 10rpx", "font-size" to "26rpx"))), _tD(inps.content), 5)
                                                                                        ), 4)
                                                                                    } else {
                                                                                        _cC("v-if", true)
                                                                                    },
                                                                                    if (inps.type == "image") {
                                                                                        _cE("view", _uM("key" to 1, "style" to _nS(_uM("width" to "400rpx", "margin" to "10rpx 0"))), _uA(
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
                                                                ), 64)
                                                            }), 256)
                                                        }
                                                    ), 4)
                                                ), 4)
                                            ), 4),
                                            if (isTrue(_uA(
                                                "14",
                                                "18",
                                                "15"
                                            ).includes(unref(nowSub)?.problemType ?: ""))) {
                                                _cE("view", _uM("key" to 0, "class" to "read-test-r", "style" to _nS(_uM("width" to "90rpx"))), null, 4)
                                            } else {
                                                _cE("view", _uM("key" to 1, "class" to "read-test-r", "style" to _nS(_uM("width" to "200rpx"))), _uA(
                                                    if (unref(nowSub)?.problemType == "14") {
                                                        _cE("text", _uM("key" to 0, "style" to _nS(_uM("color" to "#3D3D3D", "font-size" to "12rpx", "margin-bottom" to "10rpx"))), "填空", 4)
                                                    } else {
                                                        _cC("v-if", true)
                                                    },
                                                    _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                                        _cE("view", null, _uA(
                                                            _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "flex-wrap" to "wrap", "padding-left" to "-10rpx"))), _uA(
                                                                _cE(Fragment, null, RenderHelpers.renderList(unref(nowAnswer), fun(inis, index, __index, _cached): Any {
                                                                    return _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "margin-bottom" to "10rpx", "margin-left" to "10rpx", "width" to "100%"))), _uA(
                                                                        if (isTrue(inis.isShow != false && unref(nowSub)?.problemType != "17" && unref(nowSub)?.problemType != "16")) {
                                                                            _cE("text", _uM("key" to 0, "style" to _nS(_uM("font-size" to "10.55rpx", "color" to "#3D3D3D"))), "(" + _tD(index + 1) + "). ", 5)
                                                                        } else {
                                                                            _cE("view", _uM("key" to 1, "style" to _nS(_uM("padding-bottom" to "-3rpx", "align-items" to "flex-start", "width" to "100%"))), _uA(
                                                                                if (isTrue(_uA(
                                                                                    "17",
                                                                                    "16"
                                                                                ).includes(unref(nowSub)?.problemType ?: ""))) {
                                                                                    _cE("view", _uM("key" to 0, "style" to _nS(_uM("margin-bottom" to "4rpx"))), _uA(
                                                                                        _cE("text", _uM("class" to "fTimesNewRoman"), "(" + _tD(index + 1) + ")." + _tD(inis.questionContent), 1)
                                                                                    ), 4)
                                                                                } else {
                                                                                    _cC("v-if", true)
                                                                                },
                                                                                _cE(Fragment, null, RenderHelpers.renderList(inis.options, fun(opt, optIndex, __index, _cached): Any {
                                                                                    return _cE("view", _uM("style" to _nS(_uM("align-items" to "flex-start", "flex-direction" to "row", "margin-bottom" to "4rpx", "justify-content" to "flex-start")), "class" to _nC(_uA(
                                                                                        "checkItem",
                                                                                        _uM("check" to opt.isCheck)
                                                                                    )), "onClick" to fun(){
                                                                                        unref(examinationIteratorFn).wordingTwo(unref(nowAnswer), index, optIndex)
                                                                                    }), _uA(
                                                                                        _cE("view", null, _uA(
                                                                                            _cE("text", _uM("class" to _nC(_uA(
                                                                                                "checkItemText fTimesNewRoman",
                                                                                                _uM("check" to opt.isCheck)
                                                                                            ))), _tD(opt.title), 3),
                                                                                            if (isTrue(opt.picUrlList != null && opt.picUrlList!!.length > 0)) {
                                                                                                _cE("view", _uM("key" to 0), _uA(
                                                                                                    _cE(Fragment, null, RenderHelpers.renderList(opt.picUrlList, fun(src, __key, __index, _cached): Any {
                                                                                                        return _cE("image", _uM("src" to src, "style" to _nS(_uM("width" to "65rpx", "margin-top" to "4rpx", "margin-left" to "16rpx")), "mode" to "widthFix", "onClick" to withModifiers(fun(){
                                                                                                            previewImage(src)
                                                                                                        }, _uA(
                                                                                                            "stop"
                                                                                                        ))), null, 12, _uA(
                                                                                                            "src",
                                                                                                            "onClick"
                                                                                                        ))
                                                                                                    }), 256)
                                                                                                ))
                                                                                            } else {
                                                                                                _cC("v-if", true)
                                                                                            }
                                                                                        ))
                                                                                    ), 14, _uA(
                                                                                        "onClick"
                                                                                    ))
                                                                                }), 256)
                                                                            ), 4)
                                                                        }
                                                                    ), 4)
                                                                }), 256)
                                                            ), 4)
                                                        ))
                                                    ), 4)
                                                ), 4)
                                            }
                                        )),
                                        _cV(_component_BackgroundImage, _uM("src" to "/static/ico/submitPaper.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "submit_paper"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                            return _uA(
                                                _cE("text", _uM("class" to "_sub", "onClick" to fun(){
                                                    isShowPaperSubject.value = true
                                                }), " 交卷 ", 8, _uA(
                                                    "onClick"
                                                ))
                                            )
                                        }), "_" to 1))
                                    ), 4)
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
                        if (isTrue(unref(isShowResult))) {
                            _cE("view", _uM("key" to 1, "class" to "d_content"), _uA(
                                _cV(_component_readResult, _uM("id" to unref(nowResultId), "isTest" to true, "onReset" to resetFn, "showNext" to (unref(type) == "40")), null, 8, _uA(
                                    "id",
                                    "showNext"
                                ))
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(isShowKeyboard))) {
                            _cE("view", _uM("key" to 2, "style" to _nS(_uM("position" to "absolute", "left" to "0", "right" to "0", "bottom" to "0"))), _uA(
                                _cV(_component_virtualKeyboard, _uM("onClose" to fun(){
                                    isShowKeyboard.value = false
                                }), null, 8, _uA(
                                    "onClose"
                                ))
                            ), 4)
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(showEndPupup))) {
                            _cV(unref(GenPagesReadSyncTestEndPopupClass), _uM("key" to 3, "isShow" to unref(showEndPupup), "onUpdate:isShow" to fun(`$event`: Boolean){
                                trySetRefValue(showEndPupup, `$event`)
                            }, "endData" to JSON.stringify(unref(endData)), "onOk" to fun(){
                                isShowResult.value = true
                                showEndPupup.value = false
                            }), null, 8, _uA(
                                "isShow",
                                "endData",
                                "onOk"
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        _cV(_component_examinationPopup, _uM("isShowResult" to unref(isShowResult), "onUpdate:isShowResult" to fun(`$event`: Boolean){
                            trySetRefValue(isShowResult, `$event`)
                        }
                        , "show" to unref(isShowPupup), "onUpdate:show" to fun(`$event`: Boolean){
                            trySetRefValue(isShowPupup, `$event`)
                        }
                        , "scoreVal" to unref(scoreVal), "type" to unref(showType), "tpl" to if (unref(type) == "40") {
                            2
                        } else {
                            1
                        }
                        , "onClose" to fun(){
                            isShowResult.value = true
                            isShowPupup.value = false
                        }
                        ), _uM("title" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                " 智练多多任务完成 "
                            )
                        }
                        ), "content" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cE("text", _uM("style" to _nS(_uM("font-size" to "14rpx", "text-align" to "center", "margin-bottom" to "8rpx"))), "单词练习得分：" + _tD(unref(dcFen)) + "分", 5),
                                _cE("text", _uM("style" to _nS(_uM("font-size" to "14rpx", "text-align" to "center", "margin-bottom" to "8rpx"))), "语法训练得分：" + _tD(unref(yfFen)) + "分", 5),
                                _cE("text", _uM("style" to _nS(_uM("font-size" to "14rpx", "text-align" to "center", "margin-bottom" to "8rpx"))), "阅读训练得分：" + _tD(unref(ydFen)) + "分", 5)
                            )
                        }
                        ), "btnText" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                " 确认 "
                            )
                        }
                        ), "_" to 1), 8, _uA(
                            "isShowResult",
                            "show",
                            "scoreVal",
                            "type",
                            "tpl",
                            "onClose"
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "flexDirection" to "row")), "right_content" to _uM(".d_content " to _uM("flex" to 1, "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "position" to "relative")), "time" to _uM(".d_content .right_content " to _uM("position" to "absolute", "top" to "14rpx", "right" to "14rpx")), "num" to _uM(".d_content .right_content .time " to _uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D")), "time_box" to _uM(".d_content .right_content .time " to _uM("width" to "93.75rpx", "height" to "33.4rpx")), "_time" to _uM(".d_content .right_content .time .time_box " to _uM("width" to "62rpx", "marginLeft" to "31.75rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "fontSize" to "14rpx", "color" to "#5B77FF", "letterSpacing" to 2)), "read-test" to _uM(".d_content .right_content " to _uM("marginTop" to "8.2rpx", "marginRight" to "15rpx", "marginBottom" to "8.2rpx", "marginLeft" to "15rpx", "flexDirection" to "row", "flex" to 1)), "read-test-r" to _uM(".d_content .right_content .read-test " to _uM("width" to "131rpx", "height" to "200rpx", "marginTop" to "50rpx", "marginLeft" to "4rpx")), "submit_paper" to _uM(".d_content .right_content " to _uM("width" to "83.79rpx", "height" to "33.98rpx", "position" to "absolute", "right" to "-3rpx", "bottom" to "32rpx")), "_sub" to _uM(".d_content .right_content .submit_paper " to _uM("width" to "60rpx", "marginLeft" to "23.79rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "color" to "#5B77FF", "fontWeight" to "bold", "fontSize" to "18rpx")), "keyboard-switch" to _pS(_uM("width" to "84rpx", "height" to "35rpx", "backgroundImage" to "linear-gradient(to bottom, #FFFFFF, #D0D8FF)", "backgroundColor" to "rgba(0,0,0,0)", "boxShadow" to "0rpx 1rpx 1rpx 0rpx #6694DF", "borderTopLeftRadius" to "21rpx", "borderTopRightRadius" to "21rpx", "borderBottomRightRadius" to "21rpx", "borderBottomLeftRadius" to "21rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "checkItem" to _uM("" to _uM("backgroundColor" to "#DEE9FF", "borderTopLeftRadius" to "4rpx", "borderTopRightRadius" to "4rpx", "borderBottomRightRadius" to "4rpx", "borderBottomLeftRadius" to "4rpx", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx"), ".check" to _uM("backgroundColor" to "#8597ff", "color" to "#ffffff")), "checkItemText" to _uM(".checkItem " to _uM("color" to "#3D3D3D", "fontSize" to "14rpx"), ".checkItem .check" to _uM("color" to "#ffffff")), "hide" to _pS(_uM("!bottom" to "-9999rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
