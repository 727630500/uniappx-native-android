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
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesWordInterTranslateTranslate : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesWordInterTranslateTranslate) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesWordInterTranslateTranslate
            val _cache = __ins.renderCache
            val _subjectIterator = subjectIterator()
            _subjectIterator.module = getModelKey("同步单词")
            _subjectIterator.subModule = getSubModelKey("单词智能翻译")
            val up80List = ref(_uA<Number>())
            val ctx = createInnerAudioContext()
            val playNow = createInnerAudioContext()
            val selectedOption = ref(-1 as Number)
            val showErrPopup = ref(false)
            val questionOptions = ref<UTSArray<followAlongItemOptions>?>(null)
            val strengthenConfigNum = ref<Number>(getStrengthen("单词智能翻译"))
            val loopNum = ref(0)
            val showStrengthen = ref(false)
            val strengthenNum = ref(3)
            val config = ref(UTSJSONObject())
            val meetTheStandard = ref(UTSJSONObject())
            val meetDb = ref(0)
            val unitTitle = ref("")
            val language = ref("英语")
            val category = ref("句子")
            val tempPath = ref<String>("")
            val startTime = ref("")
            val timer = ref(0)
            val secondVal = ref(0)
            val tempEnglishCurrentProgressValue = ref(0)
            val englishCurrentProgressValue = ref(0)
            val englishTotalProgressValue = ref(0)
            val followAlongList = ref<UTSArray<followAlongItem>?>(null)
            val nowItem = ref<followAlongItem?>(null)
            val isTest = ref(true)
            val studyTaskId = ref(0)
            val type = ref("0")
            val isReview = ref(false)
            val _countDown = countDown()
            val timeTxt = ref("")
            val isStudy = ref(false)
            val successShow = ref(false)
            val successShowPupup = ref(false)
            val problemNum = ref(0)
            val sendMap = ref(_uA<Number>())
            fun gen_back_fn() {
                uni_navigateBack(null)
            }
            val back = ::gen_back_fn
            val nowExplain = computed(fun(): String {
                var _chinese = nowItem.value?.chineseExplain
                if (_chinese != null) {
                    val result = _chinese.match(UTSRegExp("[\u4E00-\u9FFF\u3400-\u4DBF\uF900-\uFAFF]", "g"))
                    var _endText = result?.join("")
                    if (_endText != null) {
                        return _endText
                    }
                    return ""
                }
                return ""
            }
            )
            val isAotoNext = ref(true)
            fun gen_sendUp_fn(isPass: String) {
                if (!isReview.value) {
                    if (!sendMap.value.includes(nowItem.value?.id ?: 0)) {
                        problemNum.value++
                        sendMap.value.push(nowItem.value?.id ?: 0)
                    }
                }
                var _data: UTSJSONObject = _uO("textbookId" to getTextBookId(), "textbookUnitId" to nowItem.value?.textbookUnitId, "module" to getModelKey("同步单词"), "subModule" to getSubModelKey("单词智能翻译"), "problemId" to nowItem.value?.id, "englishText" to nowItem.value?.englishText, "problemIndex" to _subjectIterator.getIndex(nowItem.value), "isPass" to isPass, "isExercise" to "0", "studyTaskId" to studyTaskId.value)
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/problem/api/englishLearnSubmit"), method = "POST", data = _data, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    console.log("上报结果", responseData)
                    if (isReview.value) {
                        englishCurrentProgressValue.value++
                    } else {
                        var type = responseData.data?.getNumber("type")
                        var progressValue = responseData.data?.getNumber("progressValue")
                        if (type == 1) {
                            englishCurrentProgressValue.value = progressValue!!
                        }
                        if (type == 2) {
                            englishCurrentProgressValue.value++
                        }
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val sendUp = ::gen_sendUp_fn
            val lastGoNum = ref(0)
            _subjectIterator.onTemp(fun(list: UTSArray<followAlongItem>?){
                if (getWriteStrengStatus("单词智能翻译") == 0) {
                    return
                }
                if (list == null) {
                    return
                }
                uni_setStorageSync("tempSub", list)
                uni_setStorageSync("tempData", list)
                uni_navigateTo(NavigateToOptions(url = "/pages/wordInter/translate/strengthen?unitTitle=" + unitTitle.value))
            }
            )
            watch(config, fun(kVal: UTSJSONObject){
                var _practiceSecond = kVal.getNumber("practiceSecond") ?: 0
                if (_practiceSecond > 0) {
                    _countDown.startCountdown(_practiceSecond)
                }
            }
            , WatchOptions(deep = true))
            _countDown.onComplete(fun(str: String){
                timeTxt.value = str
            }
            )
            _countDown.onEnd(fun(){
                if (!isStudy.value && !successShow.value) {
                    showStrengthen.value = true
                    strengthenNum.value = 3
                    language.value = "汉语"
                    isAotoNext.value = false
                    sendUp("0")
                    ctx.src = getSondUrl("学习答题不通过提示音")
                }
            }
            )
            fun gen_nextItem_fn() {
                isStudy.value = false
                _countDown.clear()
                var _followAlongList = followAlongList.value
                if (_followAlongList != null) {
                    _subjectIterator.next().then(fun(_subData){
                        if (_subData == null) {
                            successShow.value = true
                            return
                        }
                        var _practiceSecond = config.value.getNumber("practiceSecond") ?: 0
                        if (_practiceSecond > 0) {
                            _countDown.startCountdown(_practiceSecond)
                        }
                        console.log(_subData)
                        nowItem.value = _subData
                        selectedOption.value = -1
                    }
                    )
                }
            }
            val nextItem = ::gen_nextItem_fn
            val recognitionSuccess = fun(data: recognitionSuccessType){
                uni_hideLoading()
                var _source = data.result.getString("total_score") ?: "0"
                var _sourceNum = parseInt(_source)
                if (_sourceNum >= meetDb.value) {
                    ctx.src = getSondUrl("学习答题通过提示音")
                    strengthenNum.value--
                    var _followAlongList = followAlongList.value
                    if (strengthenNum.value == 0 && _followAlongList != null) {
                        showStrengthen.value = false
                        selectedOption.value = -1
                        isAotoNext.value = false
                        nextItem()
                    }
                } else {
                    ctx.src = getSondUrl("学习答题不通过提示音")
                }
                tempPath.value = data.path
            }
            watch(selectedOption, fun(kVal: Number, prevVal: Number?){
                if (kVal == -1) {
                    return
                }
                isStudy.value = true
                var _nowItem = nowItem.value
                if (_nowItem != null) {
                    var _options = _nowItem.options as UTSArray<followAlongItemOptions>?
                    if (_options != null) {
                        if (ucsShare.getState("debug") as Boolean) {
                            ctx.src = getSondUrl("学习答题通过提示音")
                            sendUp("1")
                            return
                        }
                        console.log(_options[kVal], 123)
                        if (!_options[kVal].isRight) {
                            showStrengthen.value = true
                            strengthenNum.value = 3
                            language.value = "汉语"
                            sendUp("0")
                            ctx.src = getSondUrl("学习答题不通过提示音")
                            console.log("错了")
                        } else {
                            console.log("对了", getSondUrl("学习答题通过提示音"))
                            ctx.src = getSondUrl("学习答题通过提示音")
                            sendUp("1")
                        }
                    } else {
                        uni_showToast(ShowToastOptions(title = "识别错误", icon = "none"))
                    }
                }
            }
            , WatchOptions(immediate = true))
            ctx.onCanplay(fun(_){
                ctx.play()
            }
            )
            ctx.onError(fun(_){
                console.log("音频错误")
            }
            )
            ctx.onEnded(fun(_res){
                if (!showStrengthen.value && isAotoNext.value) {
                    playNow.src = nowItem.value!!.soundUrl
                }
                isAotoNext.value = true
            }
            )
            playNow.onCanplay(fun(_){
                playNow.play()
            }
            )
            playNow.onEnded(fun(_res){
                console.log("正确提示播放完毕")
                nextItem()
            }
            )
            fun gen_goTest_fn() {
                if (isReview.value) {
                    uni_redirectTo(RedirectToOptions(url = "/pages/wordInter/translate/test?isReview=1"))
                    return
                }
                if (!isTest.value) {
                    uni_setStorageSync("studyTaskEnd", 1)
                    uni_navigateBack(null)
                    return
                }
                if (studyTaskId.value != 0) {
                    uni_redirectTo(RedirectToOptions(url = "/pages/wordInter/translate/test?studyTaskId=" + studyTaskId.value))
                } else {
                    uni_redirectTo(RedirectToOptions(url = "/pages/wordInter/translate/test"))
                }
            }
            val goTest = ::gen_goTest_fn
            fun gen_getSubjectLow80_fn(): UTSPromise<Unit> {
                return UTSPromise<Unit>(fun(resolve, reject){
                    uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishProblemList"), method = "GET", data = object : UTSJSONObject() {
                        var textbookId = getTextBookId()
                        var textbookUnitId = getTextbookUnitId()
                        var module = getModelKey("同步单词")
                        var subModule = getSubModelKey("单词智能翻译")
                    }, header = getHeader(), success = fun(res){
                        val responseData = res.data
                        if (responseData == null) {
                            return
                        }
                        if (responseData.code as Number != 200) {
                            uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                            return
                        }
                        _subjectIterator.putArr80(responseData.data)
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
                    val _data = responseData.data
                    if (_data != null) {
                        followAlongList.value = _data
                        _subjectIterator.isTask = true
                        _subjectIterator.taksId = studyTaskId.value
                        _subjectIterator.putArr(_data).then(fun(){
                            _subjectIterator.nextTempCount = getStrengthen("单词智能翻译")
                            _subjectIterator.next().then(fun(_nextItem){
                                nowItem.value = _nextItem
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
            fun gen_getSubjectList_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        await(getSubjectLow80())
                        uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/list"), method = "GET", data = object : UTSJSONObject() {
                            var textbookId = getTextBookId()
                            var textbookUnitId = getTextbookUnitId()
                            var subjectType = "英语"
                            var module = getModelKey("同步单词")
                            var subModule = getSubModelKey("单词智能翻译")
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
                                followAlongList.value = _data
                                _subjectIterator.putArr(_data).then(fun(){
                                    _subjectIterator.nextTempCount = getStrengthen("单词智能翻译")
                                    _subjectIterator.next().then(fun(_nextItem){
                                        nowItem.value = _nextItem
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
                })
            }
            val getSubjectList = ::gen_getSubjectList_fn
            fun gen_getReviewList_fn() {
                isReview.value = true
                _subjectIterator.isReturn80 = false
                _subjectIterator.isReview = true
                englishCurrentProgressValue.value = 0
                tempEnglishCurrentProgressValue.value = 0
                unitTitle.value = "单词智能记忆复习"
                uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishProblemList"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步单词")
                    var subModule = getSubModelKey("单词智能翻译")
                    var leMemoryScore: Number = 100
                    var pageSize: Number = 3000
                    var problemNum: Number = 15
                    var isIntelligentReview: Number = 1
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
                        englishTotalProgressValue.value = _data.length
                        followAlongList.value = _data
                        _subjectIterator.putArr(_data).then(fun(){
                            _subjectIterator.nextTempCount = getStrengthen("单词智能翻译")
                            _subjectIterator.next().then(fun(_data){
                                nowItem.value = _data
                                startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                                timer.value = setInterval(fun(){
                                    secondVal.value++
                                }
                                , 1000)
                            }
                            )
                        }
                        )
                        uni_setStorageSync("reviewList", JSON.stringify(_data))
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getReviewList = ::gen_getReviewList_fn
            fun gen_getTempData_fn() {
                open class tempType (
                    @JsonNotNull
                    open var key: String,
                    open var value: String? = null,
                ) : UTSObject()
                var _mapKey = "" + (if (isReview.value) {
                    "1"
                } else {
                    "0"
                }
                ) + "_" + (if (isTest.value) {
                    "1"
                } else {
                    "0"
                }
                ) + "_" + studyTaskId.value + "_" + getTextBookId() + "_" + getTextbookUnitId() + "_" + getModelKey("同步单词") + "_" + getSubModelKey("单词智能翻译")
                uni_setStorageSync("mapKey", _mapKey)
                uni_request<Result<tempType>>(RequestOptions(url = getUrl("/biz/studentUser/api/getCache"), method = "POST", data = object : UTSJSONObject() {
                    var key = _mapKey
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    if (responseData.data?.value != null) {
                        uni_setStorageSync("tempSub", JSON.parse<UTSArray<followAlongItem>>(responseData.data?.value ?: "[]") ?: "")
                        uni_setStorageSync("tempData", JSON.parse<UTSArray<followAlongItem>>(responseData.data?.value ?: "[]") ?: "")
                        uni_navigateTo(NavigateToOptions(url = "/pages/wordInter/translate/strengthen?unitTitle=" + unitTitle.value))
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getTempData = ::gen_getTempData_fn
            watch(successShow, fun(kVal: Boolean){
                if (kVal) {
                    if (getWriteStrengStatus("单词智能翻译") == 0) {
                        successShowPupup.value = true
                    }
                }
            }
            )
            onLoad(fun(event: OnLoadOptions){
                unitTitle.value = event["unitTitle"] ?: ""
                var _isTest = event["isTest"] ?: "1"
                isTest.value = _isTest == "1"
                config.value = getConfig("单词智能翻译配置")
                meetTheStandard.value = getConfig("语音评测达标分数配置")
                meetDb.value = meetTheStandard.value.getNumber("wordPassScore") ?: 0
                getTempData()
                if (event["isReview"] != null) {
                    getReviewList()
                    return
                }
                if (event["type"] != null) {
                    type.value = event["type"] ?: "0"
                    studyTaskId.value = parseInt(event["studyTaskId"] ?: "0")
                    var _englishCurrentProgressValue = event["englishCurrentProgressValue"] ?: "0"
                    englishCurrentProgressValue.value = parseInt(_englishCurrentProgressValue)
                    getSubjectStudyTask()
                } else {
                    var _englishCurrentProgressValue = event["englishCurrentProgressValue"] ?: ""
                    var _englishTotalProgressValue = event["englishTotalProgressValue"] ?: ""
                    unitTitle.value = event["unitTitle"] ?: ""
                    if (_englishCurrentProgressValue != "" && _englishTotalProgressValue != "") {
                        englishCurrentProgressValue.value = parseInt(_englishCurrentProgressValue)
                        tempEnglishCurrentProgressValue.value = parseInt(_englishCurrentProgressValue)
                        englishTotalProgressValue.value = parseInt(_englishTotalProgressValue)
                        getSubjectList()
                    }
                }
            }
            )
            onPageShow(fun(){
                _countDown.isPause = false
                var _isBack = uni_getStorageSync("isBack")
                if (_isBack != "") {
                    uni_navigateBack(null)
                }
                if (successShow.value) {
                    setTimeout(fun(){
                        successShowPupup.value = true
                    }
                    , 500)
                    return
                }
            }
            )
            onPageHide(fun(){
                _countDown.isPause = true
            }
            )
            onUnmounted(fun(){
                uni_removeStorageSync("isBack")
                _countDown.clear()
                if (isReview.value) {
                    return
                }
                if (type.value != "0") {
                    return
                }
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/userStudyStatDetail/api/learnSubmit"), method = "POST", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var textbookUnitId = getTextbookUnitId()
                    var module = getModelKey("同步单词")
                    var subModule = getSubModelKey("单词智能翻译")
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
            }
            )
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_dTitle = resolveEasyComponent("dTitle", GenComponentsDTitleDTitleClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_progress = resolveComponent("progress")
                val _component_QuestionOptions = resolveEasyComponent("QuestionOptions", GenComponentsQuestionOptionsQuestionOptionsClass)
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_Recognition = resolveEasyComponent("Recognition", GenComponentsRecognitionRecognitionClass)
                val _component_u_playPcm = resolveEasyComponent("u-playPcm", GenComponentsUPlayPcmUPlayPcmClass)
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                val _component_studyCompleted = resolveEasyComponent("studyCompleted", GenComponentsStudyCompletedStudyCompletedClass)
                val _component_err_popup = resolveEasyComponent("err-popup", GenComponentsErrPopupErrPopupClass)
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
                                _cE("view", _uM("class" to "uni-row", "style" to _nS(_uM("align-items" to "center")), "onClick" to fun(){
                                    showErrPopup.value = true
                                }
                                ), _uA(
                                    _cE("image", _uM("src" to "/static/ico/edit.png", "style" to _nS(_uM("width" to "9.38rpx", "height" to "9.54rpx")), "mode" to ""), null, 4),
                                    _cE("text", _uM("class" to "tip"), "纠错")
                                ), 12, _uA(
                                    "onClick"
                                ))
                            )),
                            _cE("view", _uM("class" to "content_box_group"), _uA(
                                _cE("view", _uM("class" to "content_box"), _uA(
                                    _cE("text", _uM("class" to "b1"), _tD(unref(nowItem)?.chineseExplain), 1),
                                    if (isTrue(unref(_subjectIterator).is80 || unref(_subjectIterator).isErr80)) {
                                        _cE("text", _uM("key" to 0, "style" to _nS(_uM("margin-top" to "20rpx", "color" to "#86909c"))), "复习", 4)
                                    } else {
                                        _cC("v-if", true)
                                    }
                                )),
                                _cE("view", _uM("class" to "question_box"), _uA(
                                    if (isTrue(!unref(showStrengthen))) {
                                        _cV(_component_QuestionOptions, _uM("key" to 0, "options" to unref(nowItem)?.options, "modelValue" to unref(selectedOption), "onUpdate:modelValue" to fun(`$event`: Number){
                                            trySetRefValue(selectedOption, `$event`)
                                        }), null, 8, _uA(
                                            "options",
                                            "modelValue"
                                        ))
                                    } else {
                                        _cE("view", _uM("key" to 1, "class" to "question_box_r"), _uA(
                                            _cE("text", _uM("class" to "b1"), _tD(unref(nowItem)?.englishText), 1)
                                        ))
                                    }
                                ))
                            )),
                            _cV(_component_BackgroundImage, _uM("src" to "/static/ico/time.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "time_box", "style" to _nS(_uM("position" to "absolute", "bottom" to "30rpx", "left" to "20rpx"))), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("text", _uM("class" to "_time"), _tD(unref(timeTxt)), 1)
                                )
                            }
                            ), "_" to 1), 8, _uA(
                                "style"
                            ))
                        )),
                        if (isTrue(unref(showStrengthen))) {
                            _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("view", _uM("class" to "popup_content"), _uA(
                                        _cE("text", _uM("class" to "_num"), _tD(unref(strengthenNum)), 1),
                                        _cE("text", _uM("class" to "tips"), "说出英语和汉义"),
                                        _cE("view", _uM("class" to "operation_box", "style" to _nS(_uM("margin-bottom" to "20rpx"))), _uA(
                                            _cV(_component_u_playMp3, _uM("src" to unref(nowItem)?.soundUrl, "tplType" to 2), null, 8, _uA(
                                                "src"
                                            )),
                                            _cE("view", _uM("style" to _nS(_uM("margin" to "0 24.61rpx"))), _uA(
                                                _cV(_component_Recognition, _uM("onSuccess" to recognitionSuccess, "language" to unref(language), "category" to unref(category), "subject" to unref(nowExplain), "seconds" to 5), null, 8, _uA(
                                                    "language",
                                                    "category",
                                                    "subject"
                                                ))
                                            ), 4),
                                            _cV(_component_u_playPcm, _uM("src" to unref(tempPath), "tplType" to 2), null, 8, _uA(
                                                "src"
                                            ))
                                        ), 4)
                                    ))
                                )
                            }), "_" to 1))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(successShowPupup))) {
                            _cV(_component_studyCompleted, _uM("key" to 1, "isTest" to unref(isTest), "onClose" to back, "onSuccess" to goTest), null, 8, _uA(
                                "isTest"
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        _cV(_component_err_popup, _uM("isShow" to unref(showErrPopup), "onUpdate:isShow" to fun(`$event`: Boolean){
                            trySetRefValue(showErrPopup, `$event`)
                        }
                        , "nowItem" to unref(nowItem)), null, 8, _uA(
                            "isShow",
                            "nowItem"
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "paddingTop" to "8.2rpx", "paddingRight" to "16.41rpx", "paddingBottom" to "14rpx", "paddingLeft" to "16.41rpx")), "top_box" to _pS(_uM("height" to "35rpx", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "tip" to _uM(".top_box " to _uM("marginLeft" to "5.86rpx", "fontSize" to "12rpx", "color" to "#3D3D3D")), "title" to _pS(_uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx")), "content_box" to _pS(_uM("alignItems" to "center", "paddingTop" to "60rpx", "width" to "270rpx")), "b1" to _uM(".content_box " to _uM("fontWeight" to "700", "fontSize" to "22rpx", "color" to "#3D3D3D", "marginBottom" to "10rpx"), ".question_box .question_box_r " to _uM("fontWeight" to "700", "fontSize" to "22rpx", "color" to "#3D3D3D", "marginBottom" to "10rpx")), "b2" to _uM(".content_box " to _uM("fontSize" to "14rpx", "color" to "#555555", "marginBottom" to "10rpx")), "operation_box" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-end", "justifyContent" to "center", "marginTop" to "20rpx")), "operation_text" to _uM(".operation_box " to _uM("fontSize" to "14rpx", "marginTop" to "10rpx")), "content_box_group" to _pS(_uM("flexDirection" to "row", "height" to "100%")), "question_box" to _pS(_uM("width" to "354rpx")), "question_box_r" to _uM(".question_box " to _uM("marginLeft" to "60rpx", "marginTop" to "60rpx", "alignItems" to "center")), "question_box_bottom" to _pS(_uM("alignItems" to "center", "marginTop" to "7rpx")), "question_box_btn" to _pS(_uM("width" to "100rpx", "height" to "35rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "alignItems" to "center", "justifyContent" to "center", "borderTopLeftRadius" to "100rpx", "borderTopRightRadius" to "100rpx", "borderBottomRightRadius" to "100rpx", "borderBottomLeftRadius" to "100rpx")), "question_box_btn_text" to _uM(".question_box_btn " to _uM("fontFamily" to "Arial, Arial", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#F6F6F6")), "popup_content" to _pS(_uM("width" to "100%", "height" to "100%", "alignItems" to "center", "justifyContent" to "flex-end")), "_num" to _uM(".popup_content " to _uM("width" to "56rpx", "height" to "56rpx", "borderTopLeftRadius" to "56rpx", "borderTopRightRadius" to "56rpx", "borderBottomRightRadius" to "56rpx", "borderBottomLeftRadius" to "56rpx", "backgroundImage" to "none", "backgroundColor" to "#FCF3D6", "fontWeight" to "bold", "fontSize" to "23rpx", "color" to "#FA9600", "lineHeight" to "56rpx", "textAlign" to "center")), "tips" to _uM(".popup_content " to _uM("fontSize" to "19rpx", "color" to "#FFDEAD", "lineHeight" to "35rpx", "marginTop" to "9.38rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
