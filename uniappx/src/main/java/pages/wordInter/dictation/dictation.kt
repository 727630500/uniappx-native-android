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
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesWordInterDictationDictation : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesWordInterDictationDictation) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesWordInterDictationDictation
            val _cache = __ins.renderCache
            val subjectIteratorFn = subjectIterator()
            subjectIteratorFn.module = getModelKey("同步单词")
            subjectIteratorFn.subModule = getSubModelKey("单词智能听写")
            val isAuto = ref(true)
            val ctx = createInnerAudioContext()
            ctx.src = getSondUrl("学习默写时输错警示音")
            val ctxPlayNow = createInnerAudioContext()
            val refMp3 = ref<UPlayMp3ComponentPublicInstance?>(null)
            val styleTransformWithOrigin = ref<UniElement?>(null)
            val up80List = ref(_uA<Number>())
            val followAlongList = ref<UTSArray<followAlongItem>?>(null)
            val nowItem = ref<followAlongItem?>(null)
            val config = ref(UTSJSONObject())
            val meetTheStandard = ref(UTSJSONObject())
            val meetDb = ref(0)
            val inputText = ref("")
            val _input = ref(null)
            val unitTitle = ref("")
            val timer = ref(0)
            val secondVal = ref(0)
            val startTime = ref("")
            val tempEnglishCurrentProgressValue = ref(0)
            val englishCurrentProgressValue = ref(0)
            val englishTotalProgressValue = ref(0)
            val isShowKeyboard = ref(false)
            val cursorPosition = ref(0)
            val needShowKeyboard = ref(ucsShare.getStore("isShowKeyboard") as Boolean)
            val showFail = ref(false)
            val showCorrect = ref(false)
            val showCorrectEnd = ref(false)
            val isTest = ref(true)
            val studyTaskId = ref(0)
            val type = ref("0")
            val successShow = ref(false)
            val _countDown = countDown()
            val timeTxt = ref("")
            val isStudy = ref(false)
            val showErr = ref(false)
            val isReview = ref(false)
            val inspectStatus = ref("")
            val problemNum = ref(0)
            val sendMap = ref(_uA<Number>())
            fun gen_sendUp_fn(isPass: String) {
                if (!isReview.value) {
                    if (!sendMap.value.includes(nowItem.value?.id ?: 0)) {
                        problemNum.value++
                        sendMap.value.push(nowItem.value?.id ?: 0)
                    }
                }
                var _data: UTSJSONObject = _uO("textbookId" to getTextBookId(), "textbookUnitId" to nowItem.value?.textbookUnitId, "module" to getModelKey("同步单词"), "subModule" to getSubModelKey("单词智能听写"), "problemId" to nowItem.value?.id, "englishText" to nowItem.value?.englishText, "problemIndex" to subjectIteratorFn.getIndex(nowItem.value), "isPass" to isPass, "isExercise" to "0", "studyTaskId" to studyTaskId.value)
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/problem/api/englishLearnSubmit"), method = "POST", data = _data, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    if (isReview.value) {
                        if (isPass == "1") {
                            englishCurrentProgressValue.value++
                        }
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
            watch(inputText, fun(kVal: String, old: String){
                if (inspectStatus.value == "secondary") {
                    if (old.length > kVal.length) {
                        return
                    }
                    var _str = ""
                    val txtArray = kVal.split("")
                    val contrastArray = nowItem.value?.englishText?.split("")
                    if (contrastArray == null) {
                        return
                    }
                    var isErr = false
                    if (txtArray.length > contrastArray.length) {
                        inputText.value = old
                        return
                    }
                    run {
                        var i: Number = 0
                        while(i < txtArray.length){
                            val char = txtArray[i]
                            val contrastChar = contrastArray[i]
                            if (char == contrastChar && !isErr) {
                                _str += char
                            } else {
                                isErr = true
                                ctx.play()
                            }
                            i++
                        }
                    }
                    showFail.value = isErr
                    showCorrect.value = !isErr
                    inputText.value = _str
                }
            }
            )
            val close = fun(){
                styleTransformWithOrigin.value?.style?.setProperty("transform", "translateY(0)")
                isShowKeyboard.value = false
            }
            val inputClick = fun(){
                if (needShowKeyboard.value) {
                    isShowKeyboard.value = true
                    styleTransformWithOrigin.value?.style?.setProperty("transition-property", "transform")
                    styleTransformWithOrigin.value?.style?.setProperty("transition-duration", "100ms")
                    styleTransformWithOrigin.value?.style?.setProperty("transform", "translateY(-40px)")
                }
            }
            fun gen_nextItem_fn() {
                showCorrectEnd.value = false
                isStudy.value = false
                _countDown.clear()
                subjectIteratorFn.next().then(fun(next){
                    if (next != null) {
                        var _practiceSecond = config.value.getNumber("practiceSecond") ?: 0
                        if (_practiceSecond > 0) {
                            _countDown.startCountdown(_practiceSecond)
                        }
                        inputText.value = ""
                        nowItem.value = next
                        ctxPlayNow.src = next.soundUrl
                        inspectStatus.value = ""
                        showCorrect.value = false
                        showFail.value = false
                        if (ucsShare.getState("debug") as Boolean) {
                            inputText.value = next.englishText
                        }
                    } else {
                        successShow.value = true
                    }
                }
                )
            }
            val nextItem = ::gen_nextItem_fn
            ctxPlayNow.onEnded(fun(_res){
                console.log("准备执行下一个")
                nextItem()
            }
            )
            fun gen_inspectText_fn() {
                val _followAlongList = followAlongList.value
                isStudy.value = true
                if (_followAlongList != null && inputText.value == nowItem.value?.englishText) {
                    sendUp("1")
                    close()
                    showCorrect.value = true
                    showCorrectEnd.value = true
                    showFail.value = false
                    ctxPlayNow.play()
                    return
                }
                if (inspectStatus.value == "secondary" && inputText.value != nowItem.value?.englishText) {
                    sendUp("0")
                    close()
                    ctx.play()
                    return
                }
                if (inspectStatus.value == "secondary" && _followAlongList != null) {
                    close()
                    showCorrectEnd.value = false
                    ctxPlayNow.play()
                    showCorrect.value = true
                    showFail.value = false
                    return
                }
                if (inspectStatus.value == "fail") {
                    close()
                    showFail.value = false
                    showCorrect.value = false
                    inspectStatus.value = "secondary"
                    inputText.value = ""
                    return
                }
                if (inputText.value != nowItem.value?.englishText) {
                    inspectStatus.value = "fail"
                    showFail.value = true
                    showCorrect.value = !showFail.value
                    sendUp("0")
                    ctx.play()
                    close()
                    return
                }
            }
            val inspectText = ::gen_inspectText_fn
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
                inspectText()
            }
            )
            val callBackFn = ref(_uA<IUniActivityCallback>())
            onReady(fun(){
                setScreen()
                styleTransformWithOrigin.value = uni_getElementById("styleTransformWithOrigin")
                uni__on("enterKey", fun(){
                    inspectText()
                }
                )
            }
            )
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
                        subjectIteratorFn.isTask = true
                        subjectIteratorFn.taksId = studyTaskId.value
                        subjectIteratorFn.putArr(_data).then(fun(){
                            subjectIteratorFn.setStartIndex(englishCurrentProgressValue.value)
                            nextItem()
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
            }
            val getSubjectStudyTask = ::gen_getSubjectStudyTask_fn
            fun gen_getSubjectLow80_fn(): UTSPromise<Unit> {
                return UTSPromise<Unit>(fun(resolve, reject){
                    uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishProblemList"), method = "GET", data = object : UTSJSONObject() {
                        var textbookId = getTextBookId()
                        var textbookUnitId = getTextbookUnitId()
                        var module = getModelKey("同步单词")
                        var subModule = getSubModelKey("单词智能听写")
                    }, header = getHeader(), success = fun(res){
                        val responseData = res.data
                        if (responseData == null) {
                            return
                        }
                        if (responseData.code as Number != 200) {
                            uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                            return
                        }
                        subjectIteratorFn.putArr80(responseData.data)
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
                            var module = getModelKey("同步单词")
                            var subModule = getSubModelKey("单词智能听写")
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
                                subjectIteratorFn.putArr(_data).then(fun(){
                                    subjectIteratorFn.setStartIndex(englishCurrentProgressValue.value)
                                    nextItem()
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
                if (isReview.value) {
                    uni_redirectTo(RedirectToOptions(url = "/pages/wordInter/dictation/test?isReview=1"))
                    return
                }
                if (!isTest.value) {
                    uni_setStorageSync("studyTaskEnd", 1)
                    uni_navigateBack(null)
                    return
                }
                if (studyTaskId.value != 0) {
                    uni_redirectTo(RedirectToOptions(url = "/pages/wordInter/dictation/test?studyTaskId=" + studyTaskId.value))
                } else {
                    uni_redirectTo(RedirectToOptions(url = "/pages/wordInter/dictation/test"))
                }
            }
            val goTest = ::gen_goTest_fn
            onPageShow(fun(){
                _countDown.isPause = false
                if (successShow.value) {
                    isAuto.value = false
                    return
                }
            }
            )
            onPageHide(fun(){
                _countDown.isPause = true
            }
            )
            fun gen_getReviewList_fn() {
                isReview.value = true
                subjectIteratorFn.isReturn80 = false
                subjectIteratorFn.isReview = true
                englishCurrentProgressValue.value = 0
                tempEnglishCurrentProgressValue.value = 0
                unitTitle.value = "单词智能听写复习"
                uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishProblemList"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步单词")
                    var subModule = getSubModelKey("单词智能听写")
                    var isExercisedTest = "1"
                    var problemNum: Number = 15
                    var leMemoryScore: Number = 100
                    var pageSize: Number = 3000
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
                        subjectIteratorFn.putArr(_data).then(fun(){
                            subjectIteratorFn.nextTempCount = getStrengthen("单词智能听写")
                            nextItem()
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
            onLoad(fun(event: OnLoadOptions){
                unitTitle.value = event["unitTitle"] ?: ""
                config.value = getConfig("单词智能听写配置")
                meetTheStandard.value = getConfig("语音评测达标分数配置")
                meetDb.value = meetTheStandard.value.getNumber("wordPassScore") ?: 0
                if (event["isReview"] != null) {
                    getReviewList()
                    return
                }
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
            }
            )
            onUnmounted(fun(){
                uni__off("enterKey", null)
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
                    var subModule = getSubModelKey("单词智能听写")
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
            return fun(): Any? {
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_progress = resolveComponent("progress")
                val _component_contrastTxt = resolveEasyComponent("contrastTxt", GenComponentsContrastTxtContrastTxtClass)
                val _component_wj_input = resolveEasyComponent("wj-input", GenUniModulesWjInputComponentsWjInputWjInputClass)
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_virtualKeyboard = resolveEasyComponent("virtualKeyboard", GenComponentsVirtualKeyboardVirtualKeyboardClass)
                val _component_studyCompleted = resolveEasyComponent("studyCompleted", GenComponentsStudyCompletedStudyCompletedClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to ""), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_BackgroundImage, _uM("src" to "/static/ico/follow.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "title_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE("text", _uM("class" to "_text"), _tD(unref(unitTitle)), 1)
                                    )
                                }
                                ), "_" to 1))
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("id" to "styleTransformWithOrigin"), _uA(
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
                                        if (isTrue(unref(subjectIteratorFn).is80 || unref(subjectIteratorFn).isErr80)) {
                                            _cE("text", _uM("key" to 0, "style" to _nS(_uM("color" to "#86909c"))), "复习", 4)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ), 4)
                                )),
                                _cE("view", _uM("class" to "content_box"), _uA(
                                    if (isTrue(unref(showErr))) {
                                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("justify-content" to "center", "align-items" to "center", "width" to "100%", "height" to "35rpx"))), _uA(
                                            _cV(_component_contrastTxt, _uM("txt" to unref(nowItem)?.englishText, "contrastTxt" to unref(inputText)), null, 8, _uA(
                                                "txt",
                                                "contrastTxt"
                                            ))
                                        ), 4)
                                    } else {
                                        _cC("v-if", true)
                                    }
                                    ,
                                    _cE("view", _uM("class" to "word_inp_box"), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("flex" to "1", "height" to "100%", "position" to "relative", "justify-content" to "center"))), _uA(
                                            if (unref(inspectStatus) != "fail") {
                                                _cV(_component_wj_input, _uM("key" to 0, "style" to _nS(_uM("width" to "100%", "height" to "32rpx")), "text" to unref(inputText), "onUpdate:text" to fun(`$event`: String){
                                                    trySetRefValue(inputText, `$event`)
                                                }, "onInputTap" to inputClick, "checkText" to unref(nowItem)?.englishText, "isCheck" to (unref(inspectStatus) == "secondary"), "onOnSuccess" to fun(){
                                                    showFail.value = false
                                                    showCorrect.value = true
                                                }, "onOnErr" to fun(){
                                                    showFail.value = true
                                                    showCorrect.value = false
                                                    unref(ctx).play()
                                                }, "isAcquisition" to true), null, 8, _uA(
                                                    "style",
                                                    "text",
                                                    "checkText",
                                                    "isCheck",
                                                    "onOnSuccess",
                                                    "onOnErr"
                                                ))
                                            } else {
                                                _cE("text", _uM("key" to 1, "style" to _nS(_uM("font-size" to "20rpx", "color" to "red"))), _tD(unref(inputText)), 5)
                                            }
                                        ), 4),
                                        if (isTrue(unref(showFail))) {
                                            _cE("image", _uM("key" to 0, "src" to "/static/ico/fail_close_ico.png", "mode" to "", "style" to _nS(_uM("width" to "17.58rpx", "height" to "17.58rpx", "margin-right" to "10rpx"))), null, 4)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                        ,
                                        if (isTrue(unref(showCorrect))) {
                                            _cE("image", _uM("key" to 1, "src" to "/static/ico/correct_ico.png", "mode" to "", "style" to _nS(_uM("width" to "17.58rpx", "height" to "17.58rpx", "margin-right" to "10rpx"))), null, 4)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                        ,
                                        _cE("text", _uM("class" to "sub_btn", "onClick" to fun(){
                                            inspectText()
                                        }
                                        ), _tD(if (unref(inspectStatus) == "fail") {
                                            "再次输入"
                                        } else {
                                            "提交"
                                        }
                                        ), 9, _uA(
                                            "onClick"
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "grammar_box"), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                            if (isTrue(unref(inspectStatus) == "fail" || unref(showFail))) {
                                                _cE("text", _uM("key" to 0, "style" to _nS(_uM("font-size" to "20rpx", "color" to "green", "margin-right" to "20rpx"))), _tD(unref(nowItem)?.englishText), 5)
                                            } else {
                                                _cC("v-if", true)
                                            }
                                            ,
                                            _cV(_component_u_playMp3, _uM("isAuto" to unref(isAuto), "src" to unref(nowItem)?.soundUrl, "tplType" to 5), null, 8, _uA(
                                                "isAuto",
                                                "src"
                                            ))
                                        ), 4)
                                    )),
                                    if (isTrue(unref(inspectStatus) == "fail" || unref(showFail) || unref(showCorrectEnd))) {
                                        _cE("text", _uM("key" to 1, "style" to _nS(_uM("font-size" to "20rpx", "color" to "green", "margin-right" to "20rpx"))), _tD(unref(nowItem)?.chineseExplain), 5)
                                    } else {
                                        _cC("v-if", true)
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
                        if (isTrue(unref(isShowKeyboard))) {
                            _cE("view", _uM("key" to 0, "style" to _nS(_uM("position" to "absolute", "left" to "0", "right" to "0", "bottom" to "0"))), _uA(
                                _cV(_component_virtualKeyboard, _uM("onClose" to close))
                            ), 4)
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(successShow))) {
                            _cV(_component_studyCompleted, _uM("key" to 1, "isTest" to unref(isTest), "onClose" to fun(){
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791ff, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "14rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "paddingTop" to "8.2rpx", "paddingRight" to "16.41rpx", "paddingBottom" to "14rpx", "paddingLeft" to "16.41rpx")), "title_box" to _pS(_uM("width" to "234rpx", "height" to "28rpx", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "marginLeft" to "196.29rpx")), "_text" to _uM(".title_box " to _uM("textAlign" to "center", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#535d8c", "lineHeight" to "28rpx")), "top_box" to _pS(_uM("height" to "35rpx", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "tip" to _uM(".top_box " to _uM("marginLeft" to "5.86rpx", "fontSize" to "12rpx", "color" to "#3d3d3d")), "title" to _pS(_uM("fontWeight" to "700", "fontSize" to "14rpx", "color" to "#3d3d3d", "lineHeight" to "20rpx")), "content_box" to _pS(_uM("alignItems" to "center", "flex" to 1)), "word_inp_box" to _uM(".content_box " to _uM("width" to "355rpx", "paddingTop" to "3rpx", "paddingRight" to "3rpx", "paddingBottom" to "3rpx", "paddingLeft" to "3rpx", "marginTop" to "10rpx", "backgroundImage" to "none", "backgroundColor" to "#f6f8ff", "borderTopLeftRadius" to "33rpx", "borderTopRightRadius" to "33rpx", "borderBottomRightRadius" to "33rpx", "borderBottomLeftRadius" to "33rpx", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#b6bcd9", "borderRightColor" to "#b6bcd9", "borderBottomColor" to "#b6bcd9", "borderLeftColor" to "#b6bcd9", "flexDirection" to "row", "alignItems" to "center")), "sub_btn" to _uM(".content_box .word_inp_box " to _uM("paddingTop" to 0, "paddingRight" to "20rpx", "paddingBottom" to 0, "paddingLeft" to "20rpx", "height" to "39rpx", "backgroundImage" to "linear-gradient(to bottom, #cbd4ff, #516df4)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "textAlign" to "center", "fontSize" to "15rpx", "lineHeight" to "39rpx", "color" to "#ffffff")), "grammar_box" to _uM(".content_box " to _uM("marginTop" to "8rpx", "flexDirection" to "row", "alignItems" to "center")), "grammar_item" to _uM(".content_box .grammar_box " to _uM("fontSize" to "14rpx", "color" to "#3d3d3d", "lineHeight" to "16rpx", "marginLeft" to "6rpx")), "native-button" to _pS(_uM("height" to "100%", "width" to "100%")), "isShow" to _pS(_uM("bottom" to 0)), "close" to _pS(_uM("bottom" to "-400rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
