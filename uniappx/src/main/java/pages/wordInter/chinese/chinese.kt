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
open class GenPagesWordInterChineseChinese : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesWordInterChineseChinese) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesWordInterChineseChinese
            val _cache = __ins.renderCache
            val subjectIteratorFn = subjectIterator()
            subjectIteratorFn.module = getModelKey("同步单词")
            subjectIteratorFn.subModule = getSubModelKey("单词识词辩义")
            val isAuto = ref(true)
            val ctx = createInnerAudioContext()
            ctx.src = getSondUrl("学习默写时输错警示音")
            val ctxPlayNow = createInnerAudioContext()
            ctxPlayNow.src = getSondUrl("学习答题通过提示音")
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
            val cursorPosition = ref(0)
            val isShowTypewriting = ref(false)
            val showFail = ref(false)
            val showCorrect = ref(false)
            val isTest = ref(true)
            val studyTaskId = ref(0)
            val type = ref("0")
            val successShow = ref(false)
            val _countDown = countDown()
            val timeTxt = ref("")
            val isStudy = ref(false)
            val showErr = ref(false)
            val isReview = ref(false)
            val isNext = ref(false)
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
                var _data: UTSJSONObject = _uO("textbookId" to getTextBookId(), "textbookUnitId" to getTextbookUnitId(), "module" to getModelKey("同步单词"), "subModule" to getSubModelKey("单词识词辩义"), "problemId" to nowItem.value?.id, "englishText" to nowItem.value?.englishText, "problemIndex" to subjectIteratorFn.getIndex(nowItem.value), "isPass" to isPass, "isExercise" to "0", "studyTaskId" to studyTaskId.value)
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
            val nowEnglishText = computed(fun(): String {
                val _nowItem = nowItem.value
                if (_nowItem == null) {
                    return ""
                }
                val nowItemTxt = _nowItem.chineseExplain
                if (nowItemTxt == null) {
                    return ""
                }
                val colonSeparator = UTSRegExp("[：:]", "")
                val parts = nowItemTxt.split(colonSeparator)
                var firstPart = if (parts.length > 1) {
                    parts[1].trim()
                } else {
                    null
                }
                if (firstPart == null) {
                    return _nowItem.englishText
                }
                return parts[0] + " " + _nowItem.englishText
            }
            )
            val nowChineseExplain = computed(fun(): String {
                val _nowItem = nowItem.value
                if (_nowItem == null) {
                    return ""
                }
                val nowItemTxt = _nowItem.chineseExplain
                if (nowItemTxt == null) {
                    return ""
                }
                val colonSeparator = UTSRegExp("[：:]", "")
                val parts = nowItemTxt.split(colonSeparator)
                var firstPart = if (parts.length > 1) {
                    parts[1].trim()
                } else {
                    null
                }
                if (firstPart == null) {
                    return nowItemTxt
                }
                return parts[1]
            }
            )
            fun gen_nextItem_fn() {
                isStudy.value = false
                _countDown.clear()
                isNext.value = false
                subjectIteratorFn.next().then(fun(next){
                    if (next != null) {
                        var _practiceSecond = config.value.getNumber("practiceSecond") ?: 0
                        if (_practiceSecond > 0) {
                            _countDown.startCountdown(_practiceSecond)
                        }
                        inputText.value = ""
                        nowItem.value = next
                        inspectStatus.value = ""
                        showCorrect.value = false
                        showFail.value = false
                        if (ucsShare.getState("debug") as Boolean) {
                            inputText.value = next.chineseExplain
                        }
                    } else {
                        successShow.value = true
                    }
                }
                )
            }
            val nextItem = ::gen_nextItem_fn
            ctxPlayNow.onEnded(fun(_res){
                isNext.value = true
                setTimeout(fun(){
                    nextItem()
                }
                , 1500)
                _countDown.clear()
            }
            )
            fun gen_checkTextFn_fn(txt: String): Boolean {
                val separator = UTSRegExp("[,，]", "")
                val resultArray = txt.split(separator).filter(fun(item): Boolean {
                    return item.trim() !== ""
                }
                )
                val _nowItem = nowItem.value
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
                val _followAlongList = followAlongList.value
                isStudy.value = true
                if (_followAlongList != null && checkTextFn(inputText.value)) {
                    sendUp("1")
                    showCorrect.value = true
                    showFail.value = false
                    ctxPlayNow.play()
                    return
                }
                if (inspectStatus.value == "fail") {
                    showFail.value = false
                    showCorrect.value = false
                    inspectStatus.value = "secondary"
                    inputText.value = ""
                    return
                }
                if (!checkTextFn(inputText.value)) {
                    inspectStatus.value = "fail"
                    showFail.value = true
                    showCorrect.value = !showFail.value
                    sendUp("0")
                    ctx.play()
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
            onReady(fun(){
                uni__on("enterKey", fun(){
                    inspectText()
                }
                )
                setScreen()
                styleTransformWithOrigin.value = uni_getElementById("styleTransformWithOrigin")
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
                        var subModule = getSubModelKey("单词识词辩义")
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
                            var subModule = getSubModelKey("单词识词辩义")
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
                    uni_redirectTo(RedirectToOptions(url = "/pages/wordInter/chinese/test?isReview=1"))
                    return
                }
                if (!isTest.value) {
                    uni_setStorageSync("studyTaskEnd", 1)
                    uni_navigateBack(null)
                    return
                }
                if (studyTaskId.value != 0) {
                    uni_redirectTo(RedirectToOptions(url = "/pages/wordInter/chinese/test?studyTaskId=" + studyTaskId.value))
                } else {
                    uni_redirectTo(RedirectToOptions(url = "/pages/wordInter/chinese/test"))
                }
            }
            val goTest = ::gen_goTest_fn
            fun gen_getReviewList_fn() {
                isReview.value = true
                subjectIteratorFn.isReturn80 = false
                subjectIteratorFn.isReview = true
                englishCurrentProgressValue.value = 0
                tempEnglishCurrentProgressValue.value = 0
                unitTitle.value = "单词识词辩义复习"
                uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishProblemList"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步单词")
                    var subModule = getSubModelKey("单词识词辩义")
                    var isExercisedTest = "1"
                    var leMemoryScore: Number = 100
                    var problemNum: Number = 15
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
                            nextItem()
                            startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                            timer.value = setInterval(fun(){
                                secondVal.value++
                            }
                            , 1000)
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
            onPageShow(fun(){
                _countDown.isPause = false
            }
            )
            onPageHide(fun(){
                _countDown.isPause = true
            }
            )
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
                config.value = getConfig("单词识词辩义配置")
                meetTheStandard.value = getConfig("语音评测达标分数配置")
                meetDb.value = meetTheStandard.value.getNumber("wordPassScore") ?: 0
                if (event["isReview"] != null) {
                    getReviewList()
                    return
                }
            }
            )
            onUnmounted(fun(){
                uni__off("enterKey", null)
                _countDown.clear()
                if (type.value != "0") {
                    return
                }
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/userStudyStatDetail/api/learnSubmit"), method = "POST", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var textbookUnitId = getTextbookUnitId()
                    var module = getModelKey("同步单词")
                    var subModule = getSubModelKey("单词识词辩义")
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
                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "23.44rpx", "line-height" to "25rpx"))), _tD(unref(nowEnglishText)), 5),
                                    _cE("view", _uM("class" to "word_inp_box"), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("flex" to "1", "height" to "100%", "position" to "relative", "justify-content" to "center"))), _uA(
                                            if (unref(inspectStatus) != "fail") {
                                                _cE("input", _uM("key" to 0, "type" to "text", "modelValue" to unref(inputText), "onInput" to fun(`$event`: UniInputEvent){
                                                    trySetRefValue(inputText, `$event`.detail.value)
                                                }, "style" to _nS(_uM("height" to "100%")), "placeholder" to "用逗号'，'隔开多个汉义"), null, 44, _uA(
                                                    "modelValue"
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
                                        if (isTrue(!unref(isNext))) {
                                            _cE("text", _uM("key" to 2, "class" to "sub_btn", "onClick" to fun(){
                                                inspectText()
                                            }), _tD(if (unref(inspectStatus) == "fail") {
                                                "再次输入"
                                            } else {
                                                "提交"
                                            }), 9, _uA(
                                                "onClick"
                                            ))
                                        } else {
                                            _cE("text", _uM("key" to 3, "class" to "sub_btn"), "答对了")
                                        }
                                    )),
                                    _cE("view", _uM("class" to "grammar_box"), _uA(
                                        if (isTrue(unref(inspectStatus) == "fail" || unref(showFail) || unref(isNext))) {
                                            _cE("text", _uM("key" to 0, "style" to _nS(_uM("font-size" to "20rpx", "color" to "green", "margin-right" to "20rpx"))), _tD(unref(nowChineseExplain)), 5)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ))
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
