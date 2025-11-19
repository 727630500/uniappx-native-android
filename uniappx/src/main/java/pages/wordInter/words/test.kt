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
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesWordInterWordsTest : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesWordInterWordsTest) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesWordInterWordsTest
            val _cache = __ins.renderCache
            val sendUpClassFn = sendUpClass()
            val studyTaskId = ref<Number?>(null)
            val ctx = createInnerAudioContext()
            val _countDown = countDown()
            val isShowPaperSubject = ref(false)
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
            val isLFive = computed(fun(): Boolean {
                var _subList = subList.value
                if (_subList == null) {
                    return true
                }
                var _len = _subList.length
                return nowIndex.value + 1 <= _len / 2
            }
            )
            val startTime = ref("")
            val secondVal = ref(0)
            val timer = ref(0)
            val scoreVal = ref(0)
            val isShowAdopt = ref(false)
            val isShowFail = ref(false)
            val isShowResult = ref(false)
            val isAdoptStatus = ref(false)
            val needShowKeyboard = ref(ucsShare.getStore("isShowKeyboard") as Boolean)
            val inputText = ref("")
            val isShowKeyboard = ref(false)
            val cursorPosition = ref(0)
            val isReview = ref(false)
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
            val selectedOption = ref<Number>(-1)
            val selectOpt = computed(fun(): String {
                var _arr = sendUpClassFn.get()!!
                if (!_arr[nowIndex.value].didDo) {
                    return ""
                }
                return _arr[nowIndex.value].selectOpt!!
            }
            )
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
            val close = fun(){
                isShowKeyboard.value = false
            }
            val inputClick = fun(){
                if (needShowKeyboard.value) {
                    isShowKeyboard.value = true
                }
            }
            watch(nowIndex, fun(kVal: Number){
                close()
                var _arr = sendUpClassFn.get()!!
                var _item = _arr[kVal]
                if (isLFive.value) {
                    var _subArr = subList.value
                    if (_subArr == null) {
                        return
                    }
                    var index = _subArr[kVal].options!!.findIndex(fun(item): Boolean {
                        return item.option == _item.selectOpt
                    })
                    selectedOption.value = index
                } else {
                    inputText.value = _item.selectOpt!!
                }
            }
            )
            fun gen_nextItem_fn() {
                var _subList = subList.value
                if (_subList != null) {
                    if (nowIndex.value + 1 >= _subList.length) {
                        return
                    }
                    nowIndex.value++
                    nowSub.value = _subList[nowIndex.value]
                    selectedOption.value = -1
                }
            }
            val nextItem = ::gen_nextItem_fn
            fun gen_getStatus_fn(index: Number): String {
                return sendUpClassFn.getStatus(index)
            }
            val getStatus = ::gen_getStatus_fn
            fun gen_inspectText_fn() {
                close()
                val _followAlongList = subList.value
                if (inputText.value == "") {
                    return
                }
                var _now = nowSub.value
                if (_now == null) {
                    return
                }
                if (_followAlongList != null) {
                    if (inputText.value == _now.englishText) {
                        if (!successList.value.includes(_now.id)) {
                            successList.value.push(_now.id)
                        }
                    } else {
                        if (!failList.value.includes(_now.id)) {
                            failList.value.push(_now.id)
                        }
                    }
                    sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = _now.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步单词"), subModule = getSubModelKey("生词本"), problemId = _now.id, englishText = _now.englishText, problemIndex = nowIndex.value.toString(10), isPass = if (inputText.value == _now.englishText) {
                        "1"
                    } else {
                        "0"
                    }
                    , isExercise = "1", didDo = true, selectOpt = inputText.value))
                    ctx.play()
                }
                addTemp()
                nextItem()
            }
            val inspectText = ::gen_inspectText_fn
            fun gen_evalSouce_fn() {
                var _exercisePassScore = config.value.getNumber("exercisePassScore") ?: 0
                if (scoreVal.value >= _exercisePassScore) {
                    isShowAdopt.value = true
                    isAdoptStatus.value = true
                } else {
                    isShowFail.value = true
                }
            }
            val evalSouce = ::gen_evalSouce_fn
            fun gen_submit_fn() {
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
                console.log(_uO("textbookId" to getTextBookId(), "subjectType" to "英语", "textbookUnitId" to if (isReview.value) {
                    ""
                } else {
                    getTextbookUnitId()
                }
                , "module" to getModelKey("同步单词"), "subModule" to getSubModelKey("生词本"), "score" to scoreVal.value, "startTime" to startTime.value, "passStatus" to passStatus, "second" to secondVal.value, "studyTaskId" to studyTaskId.value, "exerciseProblemBoList" to sendUpClassFn.get()))
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/problem/api/englishPracticeSubmit"), method = "POST", data = _uO("textbookId" to getTextBookId(), "subjectType" to "英语", "textbookUnitId" to if (isReview.value) {
                    ""
                } else {
                    getTextbookUnitId()
                }
                , "module" to getModelKey("同步单词"), "subModule" to getSubModelKey("生词本"), "score" to scoreVal.value, "startTime" to startTime.value, "passStatus" to passStatus, "second" to secondVal.value, "studyTaskId" to studyTaskId.value, "exerciseProblemBoList" to sendUpClassFn.get()), header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    clearInterval(timer.value)
                    nowSub.value = subList.value!![0]
                    nowIndex.value = 0
                    val _data = responseData.data
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
            val EvalformatTime = computed(fun(): String {
                return formatTime(secondVal.value)
            }
            )
            fun gen_startCountdown_fn() {
                var _config = config.value
                var _sec: Number = 0
                var exerciseTimeType = _config.getNumber("exerciseTimeType") ?: 0
                if (exerciseTimeType == 0) {
                    _sec = _config.getNumber("exerciseSecond") ?: 0
                } else {
                    var sub = subList.value
                    var len = if (sub == null) {
                        0
                    } else {
                        sub.length
                    }
                    _sec = (_config.getNumber("exerciseSecond") ?: 0) * len
                }
                _countDown.startCountdown(_sec)
            }
            val startCountdown = ::gen_startCountdown_fn
            _countDown.onComplete(fun(str: String){
                timeText.value = str
            }
            )
            _countDown.onEnd(fun(){
                submit()
            }
            )
            fun gen_selectFn_fn(kVal: Number) {
                var _nowItem = nowSub.value
                if (_nowItem != null) {
                    var _options = _nowItem.options as UTSArray<followAlongItemOptions>?
                    if (_options != null) {
                        var _nowSub = nowSub.value
                        addTemp()
                        if (_nowSub != null) {
                            if (_options[kVal].isRight) {
                                if (successList.value.includes(_nowSub.id)) {
                                    return
                                }
                                successList.value.push(_nowSub.id)
                            } else {
                                if (failList.value.includes(_nowSub.id)) {
                                    return
                                }
                                failList.value.push(_nowSub.id)
                            }
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = _nowItem.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步单词"), subModule = getSubModelKey("生词本"), problemId = _nowSub.id, englishText = _nowSub.englishText, problemIndex = nowIndex.value.toString(10), isPass = if (_options[kVal].isRight) {
                                "1"
                            } else {
                                "0"
                            }
                            , isExercise = "1", didDo = true, selectOpt = _options[kVal].option, options = _nowSub.options ?: _uA()))
                            ctx.play()
                        }
                        nextItem()
                    }
                }
            }
            val selectFn = ::gen_selectFn_fn
            fun gen_test_fn() {
                addTemp()
                var _nowSub = nowSub.value
                if (_nowSub != null) {
                    if (!successList.value.includes(_nowSub.id)) {
                        sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = _nowSub.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步单词"), subModule = getSubModelKey("生词本"), problemId = _nowSub.id, englishText = _nowSub.englishText, problemIndex = nowIndex.value.toString(10), isPass = "1", isExercise = "1", options = _nowSub.options ?: _uA()))
                        successList.value.push(_nowSub.id)
                    }
                }
                var _subList = subList.value
                if (_subList != null) {
                    if (nowIndex.value + 1 >= _subList.length) {
                        return
                    }
                    nowIndex.value++
                    nowSub.value = _subList[nowIndex.value]
                    selectedOption.value = -1
                }
            }
            val test = ::gen_test_fn
            fun gen_getSubjectList_fn() {
                uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/list"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var subjectType = "英语"
                    var textbookUnitId: Number = 0
                    var module = getModelKey("同步单词")
                    var subModule = getSubModelKey("生词本")
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
                        nowSub.value = _data[0]
                        startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                        startCountdown()
                        timer.value = setInterval(fun(){
                            secondVal.value++
                        }
                        , 1000)
                        _data.forEach(fun(item, index){
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = item.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步单词"), subModule = getSubModelKey("生词本"), problemId = item.id, englishText = item.englishText, problemIndex = index.toString(10), isPass = "0", isExercise = "1", didDo = false, selectOpt = "", options = item.options ?: _uA()))
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
            fun gen_getReviewList_fn() {
                isReview.value = true
                if (uni_getStorageSync("reviewList") != "") {
                    var _tempDataMake = uni_getStorageSync("reviewList")!!
                    var _tempData = JSON.parse<UTSArray<followAlongItem>>(_tempDataMake as String)
                    if (_tempData != null) {
                        subList.value = _tempData
                        nowSub.value = _tempData[0]
                        startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                        startCountdown()
                        timer.value = setInterval(fun(){
                            secondVal.value++
                        }
                        , 1000)
                        _tempData.forEach(fun(item, index){
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = item.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步单词"), subModule = getSubModelKey("生词本"), problemId = item.id, englishText = item.englishText, problemIndex = index.toString(10), isPass = "0", isExercise = "1", didDo = false, selectOpt = "", options = item.options ?: _uA()))
                        }
                        )
                    }
                    return
                }
                uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishProblemList"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步单词")
                    var subModule = getSubModelKey("生词本")
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
                        subList.value = _data
                        nowSub.value = _data[0]
                        startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                        startCountdown()
                        timer.value = setInterval(fun(){
                            secondVal.value++
                        }, 1000)
                        _data.forEach(fun(item, index){
                            sendUpClassFn.push(sendUpType(textbookId = getTextBookId(), textbookUnitId = item.textbookUnitId, studyTaskId = studyTaskId.value, module = getModelKey("同步单词"), subModule = getSubModelKey("生词本"), problemId = item.id, englishText = item.englishText, problemIndex = index.toString(10), isPass = "0", isExercise = "1", didDo = false, selectOpt = "", options = item.options ?: _uA()))
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
            onLoad(fun(ev){
                if (ev["isReview"] != null) {
                    getReviewList()
                } else {
                    getSubjectList()
                }
                config.value = getConfig("单词智能记忆配置")
                meetTheStandard.value = getConfig("语音评测达标分数配置")
                meetDb.value = meetTheStandard.value.getNumber("wordPassScore") ?: 0
                ctx.src = getConfig("提交答案进入下一题提示音").getString("value") ?: ""
            }
            )
            onReady(fun(){
                uni__on("enterKey", fun(){
                    inspectText()
                }
                )
                setScreen()
            }
            )
            onUnmounted(fun(){
                uni__off("enterKey", null)
                clearInterval(timer.value)
                _countDown.clear()
            }
            )
            return fun(): Any? {
                val _component_dTitle = resolveEasyComponent("dTitle", GenComponentsDTitleDTitleClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_QuestionOptions = resolveEasyComponent("QuestionOptions", GenComponentsQuestionOptionsQuestionOptionsClass)
                val _component_wj_input = resolveEasyComponent("wj-input", GenUniModulesWjInputComponentsWjInputWjInputClass)
                val _component_adoptResult = resolveEasyComponent("adoptResult", GenComponentsAdoptResultAdoptResultClass)
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                val _component_virtualKeyboard = resolveEasyComponent("virtualKeyboard", GenComponentsVirtualKeyboardVirtualKeyboardClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                val _component_paperSubject = resolveEasyComponent("paperSubject", GenComponentsPaperSubjectPaperSubjectClass)
                return _cE(Fragment, null, _uA(
                    _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cV(_component_navbar, _uM("showBack" to true, "title" to ""), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cV(_component_dTitle, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            " 生词本学后测试 "
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
                                            if (isTrue(unref(isLFive))) {
                                                _cE(Fragment, _uM("key" to 0), _uA(
                                                    _cE("view", _uM("class" to "title"), _uA(
                                                        _cE("text", _uM("class" to "num"), " (" + _tD(unref(nowIndex) + 1) + ")" + _tD(unref(nowSub)?.englishText), 1),
                                                        _cV(_component_BackgroundImage, _uM("src" to "/static/ico/time.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "time_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                            return _uA(
                                                                _cE("text", _uM("class" to "_time"), _tD(unref(timeText)), 1)
                                                            )
                                                        }), "_" to 1))
                                                    )),
                                                    _cE("view", _uM("class" to "word_box"), _uA(
                                                        _cV(_component_QuestionOptions, _uM("options" to unref(nowSub)?.options, "modelValue" to unref(selectedOption), "onUpdate:modelValue" to fun(`$event`: Number){
                                                            trySetRefValue(selectedOption, `$event`)
                                                        }, "onSelect" to selectFn), null, 8, _uA(
                                                            "options",
                                                            "modelValue"
                                                        ))
                                                    ))
                                                ), 64)
                                            } else {
                                                _cE(Fragment, _uM("key" to 1), _uA(
                                                    _cE("view", _uM("class" to "title"), _uA(
                                                        _cE("text", _uM("class" to "num"), " (" + _tD(unref(nowIndex) + 1) + ") " + _tD(unref(nowSub)?.chineseExplain), 1),
                                                        _cV(_component_BackgroundImage, _uM("src" to "/static/ico/time.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "time_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                            return _uA(
                                                                _cE("text", _uM("class" to "_time"), _tD(unref(timeText)), 1)
                                                            )
                                                        }), "_" to 1))
                                                    )),
                                                    _cE("view", _uM("class" to "word_box"), _uA(
                                                        _cE("view", _uM("class" to "word_inp_box"), _uA(
                                                            _cE("view", _uM("style" to _nS(_uM("flex" to "1", "height" to "100%", "position" to "relative", "justify-content" to "center"))), _uA(
                                                                _cV(_component_wj_input, _uM("style" to _nS(_uM("width" to "100%", "height" to "32rpx")), "class" to "native-button", "text" to unref(inputText), "onUpdate:text" to fun(`$event`: String){
                                                                    trySetRefValue(inputText, `$event`)
                                                                }, "onInputTap" to inputClick, "isAcquisition" to true), null, 8, _uA(
                                                                    "style",
                                                                    "text"
                                                                ))
                                                            ), 4),
                                                            _cE("text", _uM("class" to "sub_btn", "onClick" to inspectText), "提交")
                                                        ))
                                                    ))
                                                ), 64)
                                            },
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
                                            if (isTrue(!unref(isLFive))) {
                                                _cE(Fragment, _uM("key" to 0), _uA(
                                                    _cE("view", _uM("class" to "title", "style" to _nS(_uM("justify-content" to "flex-end"))), _uA(
                                                        _cV(_component_adoptResult, _uM("tpl" to 2, "score" to unref(scoreVal), "notDone" to unref(notDoneNum), "failNum" to unref(failList).length, "time" to unref(EvalformatTime)), null, 8, _uA(
                                                            "score",
                                                            "notDone",
                                                            "failNum",
                                                            "time"
                                                        ))
                                                    ), 4),
                                                    _cE("view", _uM("class" to "title"), _uA(
                                                        _cE("text", _uM("class" to "num", "onClick" to test), " (" + _tD(unref(nowIndex) + 1) + ") " + _tD(unref(nowSub)?.chineseExplain), 1)
                                                    )),
                                                    _cE("view", _uM("class" to "word_box"), _uA(
                                                        if (getStatus(unref(nowIndex)) != "通过") {
                                                            _cE("text", _uM("key" to 0, "style" to _nS(_uM("font-size" to "23rpx", "color" to "green"))), _tD(unref(nowSub)?.englishText), 5)
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        _cE("view", _uM("class" to "word_inp_box"), _uA(
                                                            _cE("view", _uM("style" to _nS(_uM("flex" to "1", "height" to "100%", "position" to "relative"))), _uA(
                                                                if (getStatus(unref(nowIndex)) != "通过") {
                                                                    _cE("text", _uM("key" to 0, "style" to _nS(_uM("font-size" to "23rpx", "color" to "red", "height" to "28rpx", "line-height" to "28rpx"))), _tD(unref(inputText)), 5)
                                                                } else {
                                                                    _cE("text", _uM("key" to 1, "style" to _nS(_uM("font-size" to "23rpx", "color" to "green", "height" to "28rpx", "line-height" to "28rpx"))), _tD(unref(inputText)), 5)
                                                                }
                                                            ), 4)
                                                        ))
                                                    ))
                                                ), 64)
                                            } else {
                                                _cE(Fragment, _uM("key" to 1), _uA(
                                                    _cE("view", _uM("class" to "title", "style" to _nS(_uM("justify-content" to "flex-end"))), _uA(
                                                        _cV(_component_adoptResult, _uM("tpl" to 2, "score" to unref(scoreVal), "notDone" to unref(notDoneNum), "failNum" to unref(failList).length, "time" to unref(EvalformatTime)), null, 8, _uA(
                                                            "score",
                                                            "notDone",
                                                            "failNum",
                                                            "time"
                                                        ))
                                                    ), 4),
                                                    _cE("view", _uM("class" to "title"), _uA(
                                                        _cE("text", _uM("class" to "num", "onClick" to test), " (" + _tD(unref(nowIndex) + 1) + ") " + _tD(unref(nowSub)?.englishText), 1)
                                                    )),
                                                    _cE("view", _uM("class" to "word_box"), _uA(
                                                        _cV(_component_QuestionOptions, _uM("resultOptions" to unref(nowSub)?.options, "userOption" to unref(selectOpt)), null, 8, _uA(
                                                            "resultOptions",
                                                            "userOption"
                                                        ))
                                                    ))
                                                ), 64)
                                            }
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                ))
                            )),
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
                            }
                            ,
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
                                                _cE("text", _uM("class" to "fail_text", "style" to _nS(_uM("margin-top" to "80rpx"))), "恭喜你！闯关成功", 4),
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
                            ,
                            if (isTrue(unref(isShowKeyboard))) {
                                _cE("view", _uM("key" to 2, "style" to _nS(_uM("position" to "absolute", "left" to "0", "right" to "0", "bottom" to "0"))), _uA(
                                    _cV(_component_virtualKeyboard, _uM("onClose" to close))
                                ), 4)
                            } else {
                                _cC("v-if", true)
                            }
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "flexDirection" to "row")), "right_content" to _uM(".d_content " to _uM("width" to "530rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "position" to "relative")), "title" to _uM(".d_content .right_content " to _uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginTop" to "14.06rpx", "paddingTop" to 0, "paddingRight" to "14.65rpx", "paddingBottom" to 0, "paddingLeft" to "14.65rpx")), "num" to _uM(".d_content .right_content .title " to _uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D")), "time_box" to _uM(".d_content .right_content .title " to _uM("width" to "93.75rpx", "height" to "33.4rpx")), "_time" to _uM(".d_content .right_content .title .time_box " to _uM("width" to "62rpx", "marginLeft" to "31.75rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "fontSize" to "14rpx", "color" to "#5B77FF", "letterSpacing" to 2)), "word_box" to _uM(".d_content .right_content " to _uM("marginTop" to "20rpx", "marginRight" to "100rpx", "marginBottom" to "20rpx", "marginLeft" to "100rpx", "flex" to 1)), "submit_paper" to _uM(".d_content .right_content " to _uM("width" to "83.79rpx", "height" to "33.98rpx", "position" to "absolute", "right" to "-3rpx", "bottom" to "32rpx")), "_sub" to _uM(".d_content .right_content .submit_paper " to _uM("width" to "60rpx", "marginLeft" to "23.79rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "color" to "#5B77FF", "fontWeight" to "bold", "fontSize" to "18rpx")), "badge" to _uM(".d_content .right_content .adopt_box " to _uM("width" to "114.84rpx", "height" to "110.16rpx")), "adopt_status" to _uM(".d_content .right_content .adopt_box " to _uM("fontWeight" to "bold", "fontSize" to "23rpx", "color" to "#FA9600", "marginTop" to "15rpx")), "fail_text" to _pS(_uM("fontWeight" to "bold", "fontSize" to "29rpx", "color" to "#3D3D3D")), "source" to _pS(_uM("fontWeight" to "bold", "fontSize" to "47rpx", "color" to "#FA9600")), "result_btn" to _pS(_uM("width" to "101rpx", "height" to "35rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "fontWeight" to "bold", "fontSize" to "14rpx", "lineHeight" to "35rpx", "color" to "#ffffff", "textAlign" to "center")), "close_ico" to _pS(_uM("width" to "17.58rpx", "height" to "17.58rpx", "position" to "absolute", "top" to "117.19rpx", "right" to "206.25rpx")), "word_inp_box" to _pS(_uM("width" to "100%", "paddingTop" to "3rpx", "paddingRight" to "3rpx", "paddingBottom" to "3rpx", "paddingLeft" to "3rpx", "marginTop" to "10rpx", "backgroundImage" to "none", "backgroundColor" to "#f6f8ff", "borderTopLeftRadius" to "33rpx", "borderTopRightRadius" to "33rpx", "borderBottomRightRadius" to "33rpx", "borderBottomLeftRadius" to "33rpx", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#b6bcd9", "borderRightColor" to "#b6bcd9", "borderBottomColor" to "#b6bcd9", "borderLeftColor" to "#b6bcd9", "flexDirection" to "row", "alignItems" to "center")), "sub_btn" to _uM(".word_inp_box " to _uM("paddingTop" to 0, "paddingRight" to "20rpx", "paddingBottom" to 0, "paddingLeft" to "20rpx", "height" to "39rpx", "backgroundImage" to "linear-gradient(to bottom, #cbd4ff, #516df4)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "textAlign" to "center", "fontSize" to "15rpx", "lineHeight" to "39rpx", "color" to "#ffffff")), "native-button" to _pS(_uM("height" to "100%", "width" to "100%")), "isShow" to _pS(_uM("bottom" to 0)), "close" to _pS(_uM("bottom" to "-400rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
