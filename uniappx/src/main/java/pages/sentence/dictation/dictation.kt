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
import io.dcloud.uniapp.extapi.getSystemInfoSync as uni_getSystemInfoSync
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSentenceDictationDictation : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSentenceDictationDictation) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSentenceDictationDictation
            val _cache = __ins.renderCache
            val _countDown = countDown()
            val timeTxt = ref("")
            val ctx = createInnerAudioContext()
            ctx.src = getSondUrl("学习答题不通过提示音")
            val ctx2 = createInnerAudioContext()
            var _SequentialIterator = subjectIterator()
            _SequentialIterator.module = getModelKey("同步句子")
            _SequentialIterator.subModule = getSubModelKey("同步句子听写")
            val styleTransformWithOrigin = ref<UniElement?>(null)
            val up80List = ref(_uA<Number>())
            val _nowTitle = ref("")
            val isShowKeyboard = ref(false)
            val needShowKeyboard = ref(ucsShare.getStore("isShowKeyboard") as Boolean)
            val cursorPosition = ref(0)
            val nowIndex = ref(0)
            val successShow = ref(false)
            val config = ref(UTSJSONObject())
            val meetTheStandard = ref(UTSJSONObject())
            val meetDb = ref(0)
            val unitTitle = ref("")
            val tempTiMuArr = ref<UTSArray<tempTiMu>?>(null)
            val songArr = ref("")
            val isReview = ref(false)
            val sendMap = ref(_uA<Number>())
            val problemNum = ref(0)
            val startTime = ref("")
            val timer = ref(0)
            val secondVal = ref(0)
            val tempEnglishCurrentProgressValue = ref(0)
            val englishCurrentProgressValue = ref(0)
            val englishTotalProgressValue = ref(0)
            val followAlongList = ref<UTSArray<followAlongItem>?>(null)
            val nowItem = ref<followAlongItem?>(null)
            val smailNum = ref(0)
            val analysisArr = ref<UTSArray<analysis>?>(null)
            val isFail = ref(false)
            val isShowResult = ref(false)
            val checkText = ref("")
            val isTwo = ref(false)
            val isTest = ref(true)
            val studyTaskId = ref(0)
            val type = ref("0")
            val leftWidth = computed(fun(): Number {
                var info = uni_getSystemInfoSync()
                var screenWidth = info.screenWidth * info.devicePixelRatio
                var rpx: Number = 714
                return (screenWidth * rpx) / 750
            }
            )
            val closeFn = fun(){
                uni_navigateBack(null)
            }
            fun gen_showKeyboardFn_fn(index: Number) {
                var _tempTiMuArr = tempTiMuArr.value
                if (_tempTiMuArr != null) {
                    nowIndex.value = index
                    _nowTitle.value = _tempTiMuArr[index].content
                }
                if (needShowKeyboard.value) {
                    isShowKeyboard.value = true
                }
            }
            val showKeyboardFn = ::gen_showKeyboardFn_fn
            fun gen_getCursorFn_fn(pos: Number) {
                cursorPosition.value = pos
            }
            val getCursorFn = ::gen_getCursorFn_fn
            watch(isShowKeyboard, fun(kVal: Boolean){
                if (kVal) {
                    styleTransformWithOrigin.value?.style?.setProperty("transition-property", "transform")
                    styleTransformWithOrigin.value?.style?.setProperty("transition-duration", "100ms")
                    styleTransformWithOrigin.value?.style?.setProperty("transform", "translateY(-100px)")
                } else {
                    styleTransformWithOrigin.value?.style?.setProperty("transform", "translateY(0)")
                }
            }
            )
            fun gen_sendUp_fn(isPass: String) {
                if (!isReview.value) {
                    if (!sendMap.value.includes(nowItem.value?.id ?: 0)) {
                        problemNum.value++
                        sendMap.value.push(nowItem.value?.id ?: 0)
                    }
                }
                var _data: UTSJSONObject = _uO("textbookId" to getTextBookId(), "textbookUnitId" to nowItem.value?.textbookUnitId, "module" to getModelKey("同步句子"), "subModule" to getSubModelKey("同步句子听写"), "problemId" to nowItem.value?.id, "englishText" to nowItem.value?.englishText, "problemIndex" to smailNum.value, "isPass" to isPass, "isExercise" to "0", "studyTaskId" to studyTaskId.value)
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
            fun gen_makeTemp_fn() {
                var kVal = nowItem.value
                if (kVal == null) {
                    return
                }
                var arr = splitText(kVal.englishText)
                var _tempTiMuArr = _uA<tempTiMu>()
                arr.forEach(fun(item: wordList){
                    val _data = tempTiMu(content = if (item.type == 0) {
                        ""
                    } else {
                        item.word
                    }
                    , index = item.sort, isCheck = false, title = item.word, type = item.type, id = kVal!!.id)
                    if (ucsShare.getState("debug") == true) {
                        _data.content = item.word
                    }
                    _tempTiMuArr.push(_data)
                }
                )
                tempTiMuArr.value = JSON.parse<UTSArray<tempTiMu>>(JSON.stringify(_tempTiMuArr))
            }
            val makeTemp = ::gen_makeTemp_fn
            var endDataArr = ref(_uA<Number>())
            ctx2.onCanplay(fun(_){
                ctx2.play()
            }
            )
            fun gen_diffClick_fn() {
                var find = tempTiMuArr.value!!.find(fun(item: tempTiMu): Boolean {
                    return item.title != item.content
                }
                )
                isShowResult.value = true
                sendUp(if (find != null) {
                    "0"
                } else {
                    "1"
                }
                )
                isFail.value = find != null
                if (find == null) {
                    ctx2.src = ""
                    setTimeout(fun(){
                        var arr: UTSArray<songArrType> = _uA()
                        arr.push(songArrType(text = nowItem.value?.englishText ?: "", time = nowItem.value?.soundDuration ?: 0, song = nowItem.value?.soundUrl ?: ""))
                        songArr.value = JSON.stringify(arr)
                    }
                    , 0)
                }
                _countDown.isPause = true
            }
            val diffClick = ::gen_diffClick_fn
            fun gen_subClear_fn() {
                tempTiMuArr.value = tempTiMuArr.value?.map(fun(element: tempTiMu, index: Number): tempTiMu {
                    element.isCheck = false
                    element.content = if (element.type == 0) {
                        ""
                    } else {
                        element.content
                    }
                    return element
                }
                )
                endDataArr.value = _uA()
            }
            val subClear = ::gen_subClear_fn
            val next = fun(){
                isShowResult.value = false
                isFail.value = false
                isTwo.value = false
                _countDown.isPause = false
                val _item = nowItem.value
                if (_item != null) {
                    _SequentialIterator.next().then(fun(kVal){
                        if (kVal != null) {
                            nowItem.value = kVal
                            ctx2.src = ""
                            setTimeout(fun(){
                                ctx2.src = kVal.soundUrl
                            }, 0)
                        } else {
                            successShow.value = true
                        }
                        subClear()
                        makeTemp()
                    }
                    )
                }
            }
            fun gen_reset_fn() {
                subClear()
                isTwo.value = true
                isShowResult.value = false
                ctx2.play()
            }
            val reset = ::gen_reset_fn
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
                            _SequentialIterator.next().then(fun(_nextItem){
                                nowItem.value = _nextItem
                                if (_nextItem != null) {
                                    ctx2.src = nowItem.value?.soundUrl ?: ""
                                    makeTemp()
                                }
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
                        var subModule = getSubModelKey("同步句子听写")
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
                            var subModule = getSubModelKey("同步句子听写")
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
                                    _SequentialIterator.next().then(fun(_nextItem){
                                        nowItem.value = _nextItem
                                        if (_nextItem != null) {
                                            ctx2.src = nowItem.value?.soundUrl ?: ""
                                            makeTemp()
                                        }
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
            fun gen_goTest_fn() {
                if (!isTest.value) {
                    uni_setStorageSync("studyTaskEnd", 1)
                    uni_navigateBack(null)
                    return
                }
                if (studyTaskId.value != 0) {
                    uni_redirectTo(RedirectToOptions(url = "/pages/sentence/writeSilently/test?studyTaskId=" + studyTaskId.value))
                } else {
                    uni_redirectTo(RedirectToOptions(url = "/pages/sentence/writeSilently/test"))
                }
            }
            val goTest = ::gen_goTest_fn
            fun gen_getReviewList_fn() {
                isReview.value = true
                _SequentialIterator.isReturn80 = false
                _SequentialIterator.isReview = true
                englishCurrentProgressValue.value = 0
                tempEnglishCurrentProgressValue.value = 0
                unitTitle.value = "句子智能听写复习"
                uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishProblemList"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步句子")
                    var subModule = getSubModelKey("同步句子听写")
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
                        uni_setStorageSync("reviewList", JSON.stringify(_data))
                        englishTotalProgressValue.value = _data.length
                        followAlongList.value = _data
                        _SequentialIterator.putArr(_data).then(fun(){
                            _SequentialIterator.next().then(fun(_nextItem){
                                nowItem.value = _nextItem
                                if (_nextItem != null) {
                                    ctx2.src = nowItem.value?.soundUrl ?: ""
                                    makeTemp()
                                }
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
            val getReviewList = ::gen_getReviewList_fn
            onLoad(fun(event: OnLoadOptions){
                unitTitle.value = event["unitTitle"] ?: ""
                var _isTest = event["isTest"] ?: "1"
                isTest.value = _isTest == "1"
                config.value = getConfig("句子听写配置")
                meetTheStandard.value = getConfig("语音评测达标分数配置")
                meetDb.value = meetTheStandard.value.getNumber("wordPassScore") ?: 0
                if (event["isReview"] != null) {
                    getReviewList()
                    return
                }
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
                _countDown.onComplete(fun(str: String){
                    timeTxt.value = str
                }
                )
                _countDown.onEnd(fun(){
                    diffClick()
                }
                )
            }
            )
            onUnmounted(fun(){
                _countDown.clear()
                if (type.value != "0") {
                    return
                }
                if (isReview.value) {
                    return
                }
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/userStudyStatDetail/api/learnSubmit"), method = "POST", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var textbookUnitId = getTextbookUnitId()
                    var module = getModelKey("同步句子")
                    var subModule = getSubModelKey("同步句子听写")
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
                styleTransformWithOrigin.value = uni_getElementById("styleTransformWithOrigin")
            }
            )
            return fun(): Any? {
                val _component_dTitle = resolveEasyComponent("dTitle", GenComponentsDTitleDTitleClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_progress = resolveComponent("progress")
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_memoryLine = resolveEasyComponent("memoryLine", GenComponentsMemoryLineMemoryLineClass)
                val _component_subCheckListInput = resolveEasyComponent("subCheckListInput", GenComponentsSubCheckListInputSubCheckListInputClass)
                val _component_wj_karaoke = resolveEasyComponent("wj-karaoke", GenUniModulesWjKaraokeComponentsWjKaraokeWjKaraokeClass)
                val _component_contrastTxt = resolveEasyComponent("contrastTxt", GenComponentsContrastTxtContrastTxtClass)
                val _component_studyCompleted = resolveEasyComponent("studyCompleted", GenComponentsStudyCompletedStudyCompletedClass)
                val _component_virtualKeyboard = resolveEasyComponent("virtualKeyboard", GenComponentsVirtualKeyboardVirtualKeyboardClass)
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
                            _cE("view", _uM("style" to _nS(_uM("flex" to "1")), "id" to "styleTransformWithOrigin"), _uA(
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
                                        }
                                        ), "_" to 1))
                                    ), 4)
                                )),
                                if (isTrue(!unref(isShowResult))) {
                                    _cE(Fragment, _uM("key" to 0), _uA(
                                        _cE("view", _uM("class" to "content_box"), _uA(
                                            _cE("view", null, _uA(
                                                _cV(_component_u_playMp3, _uM("src" to unref(nowItem)?.soundUrl, "tplType" to 4), null, 8, _uA(
                                                    "src"
                                                ))
                                            )),
                                            _cE("view", _uM("style" to _nS(_uM("margin-top" to "8rpx"))), _uA(
                                                _cV(_component_memoryLine, _uM("num" to unref(nowItem)?.currentMemoryScore), null, 8, _uA(
                                                    "num"
                                                ))
                                            ), 4)
                                        )),
                                        _cE("view", _uM("style" to _nS(_uM("margin-bottom" to "20rpx"))), _uA(
                                            _cV(_component_subCheckListInput, _uM("isCheck" to unref(isTwo), "list" to unref(tempTiMuArr), "onShowKeyboard" to showKeyboardFn), null, 8, _uA(
                                                "isCheck",
                                                "list"
                                            ))
                                        ), 4),
                                        _cE("view", _uM("style" to _nS(_uM("flex" to "1", "justify-content" to "flex-end", "align-items" to "center"))), _uA(
                                            _cE("text", _uM("class" to "btn", "onClick" to diffClick), "提交")
                                        ), 4),
                                        if (isTrue(unref(_SequentialIterator).is80 || unref(_SequentialIterator).isErr80)) {
                                            _cE("text", _uM("key" to 0, "style" to _nS(_uM("position" to "absolute", "bottom" to "20rpx", "left" to "20rpx", "color" to "#939393"))), "复习", 4)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ), 64)
                                } else {
                                    _cC("v-if", true)
                                }
                                ,
                                if (isTrue(unref(isShowResult))) {
                                    _cE("view", _uM("key" to 1, "class" to "content_box", "style" to _nS(_uM("align-items" to "center"))), _uA(
                                        if (isTrue(unref(isFail))) {
                                            _cE("text", _uM("key" to 0, "class" to "b1"), _tD(unref(nowItem)?.englishText), 1)
                                        } else {
                                            _cV(_component_wj_karaoke, _uM("key" to 1, "forcePlay" to true, "isBold" to true, "fontSize" to 60, "songArr" to unref(songArr), "maxWidth" to unref(leftWidth), "center" to "center", "paly" to true, "style" to _nS(_uM("width" to "100%", "height" to "80rpx"))), null, 8, _uA(
                                                "songArr",
                                                "maxWidth",
                                                "style"
                                            ))
                                        },
                                        _cE("text", _uM("class" to "b2", "style" to _nS(_uM("margin-bottom" to "20rpx"))), _tD(unref(nowItem)?.chineseExplain), 5),
                                        if (isTrue(unref(isFail))) {
                                            _cV(_component_contrastTxt, _uM("key" to 2, "txtArr" to unref(tempTiMuArr)), null, 8, _uA(
                                                "txtArr"
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        },
                                        _cE("view", _uM("style" to _nS(_uM("flex" to "1", "justify-content" to "flex-end"))), _uA(
                                            if (isTrue(unref(isFail))) {
                                                _cE("text", _uM("key" to 0, "class" to "btn", "onClick" to reset), "重组")
                                            } else {
                                                _cE("text", _uM("key" to 1, "class" to "btn", "onClick" to next), "下一题")
                                            }
                                        ), 4)
                                    ), 4)
                                } else {
                                    _cC("v-if", true)
                                }
                            ), 4)
                        )),
                        if (isTrue(unref(successShow))) {
                            _cV(_component_studyCompleted, _uM("key" to 0, "isTest" to unref(isTest), "onClose" to closeFn, "onSuccess" to goTest), null, 8, _uA(
                                "isTest"
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(isShowKeyboard))) {
                            _cE("view", _uM("key" to 1, "style" to _nS(_uM("position" to "absolute", "left" to "0", "right" to "0", "bottom" to "0"))), _uA(
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13.48rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "paddingTop" to "8.2rpx", "paddingRight" to "16.41rpx", "paddingBottom" to "14rpx", "paddingLeft" to "16.41rpx")), "top_box" to _pS(_uM("height" to "35rpx", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "tip" to _uM(".top_box " to _uM("marginLeft" to "5.86rpx", "fontSize" to "12rpx", "color" to "#3D3D3D")), "title" to _pS(_uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx")), "content_box" to _pS(_uM("alignItems" to "center", "flex" to 1)), "b1" to _uM(".content_box " to _uM("fontWeight" to "700", "fontSize" to "26rpx", "color" to "#3D3D3D", "marginBottom" to "10rpx")), "b2" to _uM(".content_box " to _uM("fontSize" to "14rpx", "color" to "#555555", "marginBottom" to "10rpx")), "btn" to _pS(_uM("width" to "100.78rpx", "height" to "35.16rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "fontSize" to "14rpx", "lineHeight" to "35.16rpx", "textAlign" to "center", "color" to "#ffffff", "borderTopLeftRadius" to "35rpx", "borderTopRightRadius" to "35rpx", "borderBottomRightRadius" to "35rpx", "borderBottomLeftRadius" to "35rpx")), "isShow" to _pS(_uM("bottom" to 0)), "close" to _pS(_uM("bottom" to "-400rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
