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
import uts.sdk.modules.wjKaraoke.CreateNaviteKrokContent
import uts.sdk.modules.limeAudioPlayer.createInnerAudioContext
import io.dcloud.uniapp.extapi.getSystemInfoSync as uni_getSystemInfoSync
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSentenceHearingHearing : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSentenceHearingHearing) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSentenceHearingHearing
            val _cache = __ins.renderCache
            val _countDown = countDown()
            val timeTxt = ref("")
            val ctx = createInnerAudioContext()
            val ctx2 = createInnerAudioContext()
            var _SequentialIterator = subjectIterator()
            _SequentialIterator.module = getModelKey("同步句子")
            _SequentialIterator.subModule = getSubModelKey("同步句子听力")
            val up80List = ref(_uA<Number>())
            val successShow = ref(false)
            val config = ref(UTSJSONObject())
            val meetTheStandard = ref(UTSJSONObject())
            val meetDb = ref(0)
            val unitTitle = ref("")
            val tempTiMuArr = ref<UTSArray<tempTiMu>?>(null)
            val participleList = ref<UTSArray<tempTiMu>?>(null)
            val isReview = ref(false)
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
            val isTest = ref(true)
            val studyTaskId = ref(0)
            val type = ref("0")
            val closeFn = fun(){
                uni_navigateBack(null)
            }
            val palyStatus = ref(false)
            val songArr = ref("")
            val leftWidth = computed(fun(): Number {
                var info = uni_getSystemInfoSync()
                var screenWidth = info.screenWidth * info.devicePixelRatio
                var rpx: Number = 714
                return (screenWidth * rpx) / 750
            }
            )
            val sendMap = ref(_uA<Number>())
            val problemNum = ref(0)
            fun gen_sendUp_fn(isPass: String) {
                if (!isReview.value) {
                    if (!sendMap.value.includes(nowItem.value?.id ?: 0)) {
                        problemNum.value++
                        sendMap.value.push(nowItem.value?.id ?: 0)
                        if (isPass == "1") {
                            englishCurrentProgressValue.value++
                        }
                    }
                }
                var _data: UTSJSONObject = _uO("textbookId" to getTextBookId(), "textbookUnitId" to nowItem.value?.textbookUnitId, "module" to getModelKey("同步句子"), "subModule" to getSubModelKey("同步句子听力"), "problemId" to nowItem.value?.id, "englishText" to nowItem.value?.englishText, "problemIndex" to _SequentialIterator.getIndex(nowItem.value), "isPass" to isPass, "isExercise" to "0", "studyTaskId" to studyTaskId.value)
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
                participleList.value = JSON.parse<UTSArray<tempTiMu>>(JSON.stringify(_tempTiMuArr))
                _tempTiMuArr.sort(fun(a, b): Number {
                    return Math.random() - 0.5
                }
                )
                tempTiMuArr.value = JSON.parse<UTSArray<tempTiMu>>(JSON.stringify(_tempTiMuArr))
            }
            val makeTemp = ::gen_makeTemp_fn
            var endDataArr = ref(_uA<Number>())
            fun gen_subClear_fn() {
                participleList.value = participleList.value?.map(fun(element: tempTiMu, index: Number): tempTiMu {
                    element.isCheck = false
                    if (element.type == 0) {
                        element.content = ""
                    }
                    return element
                }
                )
                tempTiMuArr.value = tempTiMuArr.value?.map(fun(element: tempTiMu, index: Number): tempTiMu {
                    element.isCheck = false
                    if (element.type == 0) {
                        element.content = ""
                    }
                    return element
                }
                )
                endDataArr.value = _uA()
            }
            val subClear = ::gen_subClear_fn
            fun gen_delFn_fn(item: tempTiMu) {
                participleList.value = participleList.value?.map(fun(element: tempTiMu, index: Number): tempTiMu {
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
                tempTiMuArr.value = tempTiMuArr.value?.map(fun(element: tempTiMu, index: Number): tempTiMu {
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
            }
            val delFn = ::gen_delFn_fn
            val next = fun(){
                var context = CreateNaviteKrokContent("enKaraoke")
                if (context != null) {
                    context!!.pauseAnimation()
                }
                isShowResult.value = false
                isFail.value = false
                _countDown.clear()
                val _item = nowItem.value
                if (_item != null) {
                    _SequentialIterator.next().then(fun(kVal){
                        if (kVal != null) {
                            var _practiceSecond = config.value.getNumber("practiceSecond") ?: 0
                            if (_practiceSecond > 0) {
                                _countDown.startCountdown(_practiceSecond)
                            }
                            nowItem.value = kVal
                            ctx.src = nowItem.value?.soundUrl ?: ""
                        } else {
                            successShow.value = true
                        }
                        subClear()
                        makeTemp()
                    }
                    )
                }
            }
            ctx.onCanplay(fun(_){
                ctx.play()
            }
            )
            fun gen_clickItem_fn(kVal: tempTiMuParameter) {
                var _tempparticipleList = participleList.value as UTSArray<tempTiMu>
                var isEmpty = fun(element: tempTiMu, index: Number): Boolean {
                    return element.content == "" && element.type == 0
                }
                var _index = _tempparticipleList.findIndex(isEmpty)
                var isErr = kVal.item.title != _tempparticipleList[_index].title
                var _val = nowItem.value
                if (isFail.value && isErr) {
                    ctx.src = getSondUrl("学习答题不通过提示音")
                    uni_showToast(ShowToastOptions(title = "请选择正确答案", icon = "none"))
                    return
                }
                if (_index != -1) {
                    _tempparticipleList[_index] = tempTiMu(content = kVal.item.title, isCheck = true, index = kVal.item.index, title = _tempparticipleList[_index].title, id = kVal.item.id, type = kVal.item.type)
                }
                participleList.value = _tempparticipleList
                var _tempTiMuArrVal = tempTiMuArr.value
                if (_tempTiMuArrVal != null) {
                    _tempTiMuArrVal[kVal.index].isCheck = true
                }
                tempTiMuArr.value = _tempTiMuArrVal
                var isErrVal = _tempparticipleList.filter(fun(item): Boolean {
                    return item.title != item.content
                }
                ).length > 0
                if (_tempparticipleList.filter(fun(item): Boolean {
                    return item.content == ""
                }
                ).length == 0) {
                    sendUp(if (isErrVal) {
                        "0"
                    } else {
                        "1"
                    }
                    )
                    isFail.value = isErrVal
                    if (isFail.value) {
                        ctx.src = nowItem.value?.soundUrl ?: ""
                    } else {
                        _countDown.clear()
                        palyStatus.value = true
                        setTimeout(fun(){
                            var arr: UTSArray<songArrType> = _uA()
                            arr.push(songArrType(text = nowItem.value?.englishText ?: "", time = nowItem.value?.soundDuration ?: 0, song = nowItem.value?.soundUrl ?: ""))
                            songArr.value = JSON.stringify(arr)
                        }
                        , 0)
                    }
                    isShowResult.value = true
                }
            }
            val clickItem = ::gen_clickItem_fn
            fun gen_reset_fn() {
                ctx.src = ""
                subClear()
                isShowResult.value = false
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
                                    ctx.src = nowItem.value?.soundUrl ?: ""
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
                isFail.value = true
                isShowResult.value = true
                ctx.src = nowItem.value?.soundUrl ?: ""
            }
            )
            fun gen_getSubjectLow80_fn(): UTSPromise<Unit> {
                return UTSPromise<Unit>(fun(resolve, reject){
                    uni_request<Result<UTSArray<followAlongItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishProblemList"), method = "GET", data = object : UTSJSONObject() {
                        var textbookId = getTextBookId()
                        var textbookUnitId = getTextbookUnitId()
                        var module = getModelKey("同步句子")
                        var subModule = getSubModelKey("同步句子听力")
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
                            followAlongList.value = responseData.data
                            val _data = responseData.data
                            if (_data != null) {
                                _SequentialIterator.putArr(_data).then(fun(){
                                    _SequentialIterator.next().then(fun(_nextItem){
                                        nowItem.value = _nextItem
                                        if (_nextItem != null) {
                                            ctx.src = nowItem.value?.soundUrl ?: ""
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
                    uni_redirectTo(RedirectToOptions(url = "/pages/sentence/hearing/test?studyTaskId=" + studyTaskId.value))
                } else {
                    uni_redirectTo(RedirectToOptions(url = "/pages/sentence/hearing/test"))
                }
            }
            val goTest = ::gen_goTest_fn
            fun gen_getReviewList_fn() {
                isReview.value = true
                _SequentialIterator.isReturn80 = false
                _SequentialIterator.isReview = true
                englishCurrentProgressValue.value = 0
                tempEnglishCurrentProgressValue.value = 0
                unitTitle.value = "句子智能听力复习"
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
                        uni_setStorageSync("reviewList", JSON.stringify(_data))
                        englishTotalProgressValue.value = _data.length
                        followAlongList.value = _data
                        _SequentialIterator.putArr(_data).then(fun(){
                            _SequentialIterator.next().then(fun(_nextItem){
                                nowItem.value = _nextItem
                                if (_nextItem != null) {
                                    ctx.src = nowItem.value?.soundUrl ?: ""
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
                config.value = getConfig("句子听力配置")
                meetTheStandard.value = getConfig("语音评测达标分数配置")
                meetDb.value = meetTheStandard.value.getNumber("wordPassScore") ?: 0
                var _isTest = event["isTest"] ?: "1"
                isTest.value = _isTest == "1"
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
            }
            )
            onUnmounted(fun(){
                ctx.src = ""
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
                    var subModule = getSubModelKey("同步句子听说")
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
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_memoryLine = resolveEasyComponent("memoryLine", GenComponentsMemoryLineMemoryLineClass)
                val _component_subCheckList = resolveEasyComponent("subCheckList", GenComponentsSubCheckListSubCheckListClass)
                val _component_diction = resolveEasyComponent("diction", GenComponentsDictionDictionClass)
                val _component_wj_karaoke = resolveEasyComponent("wj-karaoke", GenUniModulesWjKaraokeComponentsWjKaraokeWjKaraokeClass)
                val _component_contrastSentence = resolveEasyComponent("contrastSentence", GenComponentsContrastSentenceContrastSentenceClass)
                val _component_studyCompleted = resolveEasyComponent("studyCompleted", GenComponentsStudyCompletedStudyCompletedClass)
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
                            _cE("view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
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
                                            _cE("view", _uM("style" to _nS(_uM("justify-content" to "center", "align-items" to "center"))), _uA(
                                                if (isTrue(unref(_SequentialIterator).is80 || unref(_SequentialIterator).isErr80)) {
                                                    _cE("text", _uM("key" to 0, "style" to _nS(_uM("color" to "#939393", "margin-bottom" to "6rpx"))), "复习", 4)
                                                } else {
                                                    _cC("v-if", true)
                                                },
                                                _cV(_component_u_playMp3, _uM("src" to unref(nowItem)?.soundUrl, "tplType" to 4), null, 8, _uA(
                                                    "src"
                                                ))
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("margin-top" to "8rpx"))), _uA(
                                                _cV(_component_memoryLine, _uM("num" to unref(nowItem)?.currentMemoryScore), null, 8, _uA(
                                                    "num"
                                                ))
                                            ), 4)
                                        )),
                                        _cE("view", _uM("style" to _nS(_uM("margin-bottom" to "20rpx"))), _uA(
                                            _cV(_component_subCheckList, _uM("list" to unref(participleList), "isClear" to true, "onClear" to subClear, "onDel" to delFn), null, 8, _uA(
                                                "list"
                                            ))
                                        ), 4),
                                        _cV(_component_diction, _uM("list" to unref(tempTiMuArr), "onClickItem" to clickItem), null, 8, _uA(
                                            "list"
                                        ))
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
                                            _cV(_component_wj_karaoke, _uM("key" to 1, "id" to "enKaraoke", "isBold" to true, "forcePlay" to true, "fontSize" to 60, "songArr" to unref(songArr), "maxWidth" to unref(leftWidth), "center" to "center", "paly" to true, "style" to _nS(_uM("height" to "80rpx", "width" to "100%"))), null, 8, _uA(
                                                "songArr",
                                                "maxWidth",
                                                "style"
                                            ))
                                        },
                                        _cE("text", _uM("class" to "b2", "style" to _nS(_uM("margin-bottom" to "20rpx"))), _tD(unref(nowItem)?.chineseExplain), 5),
                                        if (isTrue(unref(isFail))) {
                                            _cV(_component_contrastSentence, _uM("key" to 2, "arr" to unref(participleList)), null, 8, _uA(
                                                "arr"
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13.48rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "paddingTop" to "8.2rpx", "paddingRight" to "16.41rpx", "paddingBottom" to "14rpx", "paddingLeft" to "16.41rpx")), "top_box" to _pS(_uM("height" to "35rpx", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "tip" to _uM(".top_box " to _uM("marginLeft" to "5.86rpx", "fontSize" to "12rpx", "color" to "#3D3D3D")), "title" to _pS(_uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx")), "content_box" to _pS(_uM("alignItems" to "center", "flex" to 1)), "b1" to _uM(".content_box " to _uM("fontWeight" to "700", "fontSize" to "26rpx", "color" to "#3D3D3D", "marginBottom" to "10rpx")), "b2" to _uM(".content_box " to _uM("fontSize" to "14rpx", "color" to "#555555", "marginBottom" to "10rpx")), "btn" to _pS(_uM("width" to "100.78rpx", "height" to "35.16rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "fontSize" to "14rpx", "lineHeight" to "35.16rpx", "textAlign" to "center", "color" to "#ffffff", "borderTopLeftRadius" to "35rpx", "borderTopRightRadius" to "35rpx", "borderBottomRightRadius" to "35rpx", "borderBottomLeftRadius" to "35rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
