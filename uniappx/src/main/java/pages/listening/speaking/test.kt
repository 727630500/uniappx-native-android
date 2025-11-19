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
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesListeningSpeakingTest : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesListeningSpeakingTest) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesListeningSpeakingTest
            val _cache = __ins.renderCache
            val rightInfo = ref("\uE600")
            val nowItem = ref<followAlongItem2?>(null)
            val wordList = ref<UTSArray<teachingMaterial>?>(null)
            val nowSubIndex = ref(0)
            val nowSub = ref<SubProblemList2?>(null)
            val wordListEnd = ref<UTSArray<teachingMaterial>?>(null)
            val answerMap = ref<Map<Number, answerMapType1>>(Map())
            val isDisable = ref(false)
            val recognitionSuccessFlag = ref(false)
            val refRecognition = ref<RecognitionComponentPublicInstance?>(null)
            val isShowStart = ref(true)
            val soundUrl = computed(fun(): String {
                if (nowItem.value?.problemType != "35") {
                    return nowSub.value?.soundInfoList!![0].soundUrl ?: ""
                }
                return nowItem.value?.soundInfoList!![0].soundUrl ?: ""
            }
            )
            val soundText = computed(fun(): String {
                if (nowItem.value?.problemType != "35") {
                    return nowSub.value?.soundInfoList!![0].originalText ?: ""
                }
                return nowItem.value?.soundInfoList!![0].originalText ?: ""
            }
            )
            val soundCount = ref(0)
            val timuText = computed(fun(): String {
                val richText = nowItem.value?.questionContent ?: ""
                return "<div style=\"text-align: center;\">" + richText + "</div>"
            }
            )
            val title = ref("")
            val problemId = ref(0)
            val isShowTime = ref(false)
            val language = ref("英语")
            val category = ref("文章")
            val nowTimeTxt = ref("")
            val isShow = ref(false)
            fun gen_handleToggle_fn(isActive: Boolean) {
                console.log("Toggle状态:", isActive)
                isShow.value = isActive
            }
            val handleToggle = ::gen_handleToggle_fn
            val secondNum = ref(0)
            val nowTime = computed(fun(): String {
                return formatTime(secondNum.value)
            }
            )
            val WjAudioPlayer1 = ref<WjAudioPlayerComponentPublicInstance?>(null)
            val wordListNotEnd = ref<UTSArray<teachingMaterial>?>(null)
            val problemTypeMap = ref<Map<String, String>>(Map(_uA(
                _uA(
                    "30",
                    "听短对话选答案"
                ),
                _uA(
                    "31",
                    "听长对话选答案"
                ),
                _uA(
                    "32",
                    "听短文选答案"
                ),
                _uA(
                    "33",
                    "听文章/看文章/填空"
                ),
                _uA(
                    "34",
                    "听句子,复述句子"
                ),
                _uA(
                    "35",
                    "听文章,复述文章"
                )
            )))
            val problemTypeIconMap = ref(Map<String, String>(_uA(
                _uA(
                    "30",
                    "/static/ico/listening/ico1.png"
                ),
                _uA(
                    "31",
                    "/static/ico/listening/ico2.png"
                ),
                _uA(
                    "32",
                    "/static/ico/listening/ico3.png"
                ),
                _uA(
                    "33",
                    "/static/ico/listening/ico4.png"
                ),
                _uA(
                    "34",
                    "/static/ico/listening/ico1.png"
                ),
                _uA(
                    "35",
                    "/static/ico/listening/ico1.png"
                )
            )))
            val studyTaskId = ref(0)
            val type = ref(0)
            val _countDown = countDown()
            val config = ref(UTSJSONObject())
            val secondVal = ref(0)
            val timeText = ref("")
            val startTime = ref("")
            val timer = ref(0)
            val meetDb = ref(0)
            val isShowNext = ref(false)
            val isStop = ref(false)
            val currentType = ref<String?>(null)
            val currentTypeText = computed(fun(): String {
                if (currentType.value == null) {
                    return "听力练习"
                }
                return ("题型：" + problemTypeMap.value.get(currentType.value!!)) ?: "听力练习"
            }
            )
            fun gen_stopMp3_fn() {
                isShowTime.value = false
                isDisable.value = false
                WjAudioPlayer1.value?.stop?.invoke()
                nowTimeTxt.value = ""
                isStop.value = true
            }
            val stopMp3 = ::gen_stopMp3_fn
            fun gen_nextFn_fn() {
                var _len = nowItem.value?.subProblemList!!.length ?: 0
                if (nowSubIndex.value + 1 < _len) {
                    nowSubIndex.value = nowSubIndex.value + 1
                    soundCount.value = 0
                    nowSub.value = nowItem.value?.subProblemList!![nowSubIndex.value] ?: null
                }
                isShowNext.value = false
            }
            val nextFn = ::gen_nextFn_fn
            val currentTypeIcon = computed(fun(): String {
                if (currentType.value == null) {
                    return "/static/ico/listening/ico1.png"
                }
                return problemTypeIconMap.value.get(currentType.value!!) ?: "/static/ico/listening/ico1.png"
            }
            )
            val pathArr = ref<Map<Number, String>>(Map())
            val pathUpArr = ref<Map<Number, String>>(Map())
            val isShowDuration = ref(false)
            fun gen_palyMp3_fn() {
                isShowDuration.value = true
                isShowStart.value = false
                if (isStop.value) {
                    return
                }
                if (isDisable.value) {
                    return
                }
                console.log("soundCount.value", soundCount.value)
                if (soundCount.value > 1) {
                    return uni_showToast(ShowToastOptions(title = "每个音频最多可以播放两次", icon = "none"))
                }
                WjAudioPlayer1.value?.play?.invoke()
            }
            val palyMp3 = ::gen_palyMp3_fn
            val exerciseTime = computed(fun(): Number {
                if (currentType.value == "34") {
                    return getConfig("单词本、句子录音倒计时配置").getNumber("oralListeningSentenceRecordSecond") ?: 0
                } else {
                    return getConfig("单词本、句子录音倒计时配置").getNumber("oralListeningArticleRecordSecond") ?: 0
                }
            }
            )
            fun gen_makeExerciseProblemBoList_fn(): UTSArray<makeSubTop3> {
                if (nowItem.value?.problemType != "35") {
                    var retArr = _uA<makeSubTop3>(makeSubTop3(problemId = nowItem.value?.id ?: 0, subProblemList = nowItem.value?.subProblemList!!.map(fun(item, index): makeSubTop3 {
                        return makeSubTop3(problemId = item.id, userSpeakUrl = pathUpArr.value.get(index) ?: "", temp = answerMap.value.get(index)?.temp ?: _uA(), adopt = answerMap.value.get(index)!!.adopt, isPass = "1")
                    })))
                    return retArr
                } else {
                    var aa = _uA<makeSubTop3>(makeSubTop3(problemId = nowItem.value!!.id, userSpeakUrl = pathUpArr.value.get(0) ?: "", temp = answerMap.value.get(0)?.temp ?: _uA(), adopt = answerMap.value.get(0)?.adopt ?: adopt(score = 0, complete = 0, accurate = 0, fluent = 0), isPass = "1"))
                    return aa
                }
            }
            val makeExerciseProblemBoList = ::gen_makeExerciseProblemBoList_fn
            fun gen_sendUp_fn() {
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/problem/api/exerciseSubmit"), method = "POST", data = object : UTSJSONObject() {
                    var type = type.value
                    var textbookId = getTextBookId()
                    var textbookUnitId = getTextbookUnitId()
                    var module = getModelKey("同步口语听力")
                    var subModule = getSubModelKey(if (title.value == "智能听写") {
                        "听力训练"
                    } else {
                        "口语训练"
                    }
                    )
                    var exerciseProblemBoList = makeExerciseProblemBoList()
                    var startTime = startTime.value
                    var second = secondVal.value
                    var studyTaskId = studyTaskId.value
                }, header = getHeader(), success = fun(res){
                    clearInterval(timer.value)
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    val _id = responseData.data?.getNumber("id") ?: 0
                    uni_redirectTo(RedirectToOptions(url = "/pages/listening/speaking/result?id=" + _id.toString(10) + "&isSentence=" + (if (nowItem.value?.problemType != "35") {
                        1
                    } else {
                        0
                    }
                    ) + "&problemId=" + nowItem.value?.id + "&title=" + title.value))
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val sendUp = ::gen_sendUp_fn
            fun gen_checkAndSendUp_fn() {
                if (recognitionSuccessFlag.value) {
                    sendUp()
                } else {
                    setTimeout(fun(){
                        gen_checkAndSendUp_fn()
                    }
                    , 100)
                }
            }
            val checkAndSendUp = ::gen_checkAndSendUp_fn
            fun gen_handleSuccess_fn(arr: UTSArray<UTSJSONObject>?): UTSArray<analysis>? {
                if (arr != null) {
                    var mapArr = _uA<analysis>()
                    arr.forEach(fun(item){
                        if (item.getString("total_score") != null) {
                            mapArr.push(analysis(content = item.getString("content") ?: "", total_score = parseFloat(item.getString("total_score") ?: "0"), dp_message = parseFloat(item.getString("dp_message") ?: "0")))
                        }
                    }
                    )
                    return mapArr
                }
                return null
            }
            val handleSuccess = ::gen_handleSuccess_fn
            val recognitionSuccess = fun(data: recognitionSuccessType){
                uni_hideLoading()
                val _nowSubIndex = nowSubIndex.value
                var adoptObj = adopt(score = parseInt(parseFloat(data.result.getString("total_score") ?: "0").toFixed(0)), complete = parseInt(parseFloat(data.result.getString("integrity_score") ?: "0").toFixed(0)), accurate = parseInt(parseFloat(data.result.getString("accuracy_score") ?: "0").toFixed(0)), fluent = parseInt(parseFloat(data.result.getString("fluency_score") ?: "0").toFixed(0)))
                recognitionSuccessFlag.value = false
                uni_showLoading(ShowLoadingOptions(title = "测评中..."))
                uploadFileToOSS(data.path, fun(err, data){
                    if (err != null) {
                        return
                    }
                    pathUpArr.value.set(_nowSubIndex, data ?: "")
                    recognitionSuccessFlag.value = true
                    uni_hideLoading()
                }
                )
                var wordArr = data.result.getJSON("sentence")?.getArray("word")
                if (wordArr == null) {
                    var word = data.result.getJSON("word")
                    if (word != null) {
                        wordArr = _uA(
                            word
                        )
                    } else {
                        var _arr = data.result.getArray("sentence")
                        if (_arr != null && _arr.length != 0) {
                            var _endArr = _uA<Any>()
                            _arr.forEach(fun(item){
                                JSON.parse<UTSJSONObject>(JSON.stringify(item))?.getArray("word")?.forEach(fun(_word){
                                    _endArr.push(_word ?: UTSJSONObject())
                                }
                                )
                            }
                            )
                            wordArr = _endArr
                        }
                    }
                }
                if (wordArr != null) {
                    var analysisArr = handleSuccess(wordArr as UTSArray<UTSJSONObject>)
                    if (analysisArr != null) {
                        answerMap.value.set(_nowSubIndex, answerMapType1(adopt = adoptObj, temp = analysisArr))
                    }
                }
                pathArr.value.set(_nowSubIndex, JSON.stringify(data))
                var _source = data.result.getString("total_score") ?: "0"
                var _sourceNum = parseInt(_source)
                if (_sourceNum >= meetDb.value) {
                    if (nowItem.value?.problemType != "35") {
                        var _len = nowItem.value?.subProblemList!!.length ?: 0
                        if (_nowSubIndex + 1 < _len) {
                            uni_showToast(ShowToastOptions(title = "准备下一句", icon = "none"))
                            isStop.value = false
                            WjAudioPlayer1.value?.reset?.invoke()
                            nextFn()
                        } else {
                            checkAndSendUp()
                        }
                    } else {
                        checkAndSendUp()
                    }
                }
                isShowStart.value = true
                isShowDuration.value = false
            }
            val hasCompleted = ref(false)
            val totalTimeText = ref("")
            fun gen_onProgressChanged_fn(e: UTSJSONObject) {
                val currentTime = e.getNumber("currentTime") ?: 0
                val duration = e.getNumber("duration") ?: 0
                secondNum.value = currentTime
                totalTimeText.value = formatTime(duration)
                if (currentTime > 0) {
                    isShowTime.value = true
                    isDisable.value = true
                    nowTimeTxt.value = formatTime(currentTime)
                } else {
                    isShowTime.value = false
                    isDisable.value = false
                    nowTimeTxt.value = ""
                }
                if (currentTime >= duration && !hasCompleted.value) {
                    soundCount.value++
                    hasCompleted.value = true
                } else if (currentTime < duration && hasCompleted.value) {
                    hasCompleted.value = false
                }
            }
            val onProgressChanged = ::gen_onProgressChanged_fn
            fun gen_playbackCompleted_fn() {
                isDisable.value = false
                uni_showToast(ShowToastOptions(title = "开始录音", icon = "none"))
                setTimeout(fun(){
                    refRecognition.value?.startRecord2?.invoke()
                }
                , 0)
            }
            val playbackCompleted = ::gen_playbackCompleted_fn
            fun gen_startCountdown_fn() {
                var _config = config.value
                var _sec: Number = 0
                var exerciseTimeType = _config.getNumber("exerciseTimeType")!!
                if (exerciseTimeType == 0) {
                    _sec = _config.getNumber("exerciseSecond")!!
                } else {
                    if (nowItem.value?.problemType != "35") {
                        _sec = _config.getNumber("exerciseSecond")!! * 1
                    } else {
                        var sub = nowItem.value?.subProblemList
                        var len = if (sub == null) {
                            0
                        } else {
                            sub.length
                        }
                        _sec = _config.getNumber("exerciseSecond")!! * len
                    }
                }
                _countDown.startCountdown(_sec)
            }
            val startCountdown = ::gen_startCountdown_fn
            _countDown.onComplete(fun(str: String){
                timeText.value = str
            }
            )
            _countDown.onEnd(fun(){})
            var unitTitle = ref("")
            val subProblemCount = ref(0)
            onLoad(fun(ev){
                unitTitle.value = ev["unitTitle"] ?: ""
                config.value = getConfig("口语训练配置")
                meetDb.value = getConfig("语音评测达标分数配置").getNumber("sentencePassScore") ?: 0
                startCountdown()
                timer.value = setInterval(fun(){
                    secondVal.value++
                }
                , 1000)
                startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                title.value = ev["title"] ?: ""
                currentType.value = ev["currentType"] ?: null
                type.value = parseInt(ev["type"] ?: "0")
                studyTaskId.value = parseInt(ev["studyTaskId"] ?: "0")
                val testItem = uni_getStorageSync("testItem")
                if (testItem != "") {
                    nowItem.value = JSON.parse<followAlongItem2>(JSON.stringify(testItem) ?: "")
                    problemId.value = nowItem.value?.id ?: 0
                    console.log(nowItem.value)
                    if (nowItem.value?.problemType != "35") {
                        nowSub.value = nowItem.value?.subProblemList!![0]
                        subProblemCount.value = nowItem.value?.subProblemList?.length ?: 0
                    }
                }
            }
            )
            onPageShow(fun(){})
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_wj_audio_player = resolveEasyComponent("wj-audio-player", GenUniModulesWjAudioPlayerComponentsWjAudioPlayerWjAudioPlayerClass)
                val _component_ToggleButton = resolveEasyComponent("ToggleButton", GenComponentsToggleButtonToggleButtonClass)
                val _component_Recognition = resolveEasyComponent("Recognition", GenComponentsRecognitionRecognitionClass)
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "口语训练")),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "d_right"), _uA(
                                _cE("view", _uM("class" to "top_box"), _uA(
                                    if (unref(currentType) != null) {
                                        _cE("view", _uM("key" to 0, "class" to "btn_box active"), _uA(
                                            _cE("image", _uM("src" to unref(currentTypeIcon), "style" to _nS(_uM("width" to "18.75rpx", "height" to "18.75rpx", "margin-right" to "4rpx"))), null, 12, _uA(
                                                "src"
                                            )),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#7B7B7B", "font-weight" to "bold"))), _tD(unref(currentTypeText)), 5)
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                )),
                                _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("max-height" to "230rpx"))), _uA(
                                    _cE("view", null, _uA(
                                        _cE("view", null, _uA(
                                            _cE("rich-text", _uM("nodes" to unref(timuText)), null, 8, _uA(
                                                "nodes"
                                            ))
                                        )),
                                        _cE("view", _uM("style" to _nS(_uM("margin-top" to "6rpx"))), _uA(
                                            _cV(_component_u_playMp3, _uM("src" to unref(soundUrl), "tplType" to 4, "disabled" to true, "onClick" to palyMp3), null, 8, _uA(
                                                "src"
                                            ))
                                        ), 4),
                                        _cE("view", _uM("style" to _nS(_uM("margin-top" to "12rpx", "flex-direction" to "row", "align-items" to "center", "justify-content" to "center"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                _cE("image", _uM("style" to _nS(_uM("width" to "24rpx", "height" to "24rpx")), "src" to if (!unref(isDisable)) {
                                                    "/static/ico/paly_2.png"
                                                } else {
                                                    "/static/ico/pause_ico.png"
                                                }
                                                , "mode" to "aspectFit", "onClick" to palyMp3), null, 12, _uA(
                                                    "src"
                                                )),
                                                _cE("view", _uM("style" to _nS(_uM("width" to "170rpx"))), _uA(
                                                    _cV(_component_wj_audio_player, _uM("audio-url" to unref(soundUrl), "auto-play" to false, "progress-color" to "#4e6ef2", "progress-height" to 12, "onProgressChanged" to onProgressChanged, "ref_key" to "WjAudioPlayer1", "ref" to WjAudioPlayer1, "onPlaybackCompleted" to playbackCompleted), null, 8, _uA(
                                                        "audio-url"
                                                    ))
                                                ), 4),
                                                if (isTrue(unref(nowTimeTxt) != "" && unref(nowTimeTxt) != null)) {
                                                    _cE("text", _uM("key" to 0, "style" to _nS(_uM("margin-left" to "5rpx"))), _tD(unref(nowTimeTxt)), 5)
                                                } else {
                                                    _cE("text", _uM("key" to 1, "style" to _nS(_uM("margin-left" to "5rpx"))), "00:00", 4)
                                                }
                                                ,
                                                if (isTrue(unref(totalTimeText) != "" && unref(totalTimeText) != null && unref(isShowDuration))) {
                                                    _cE("text", _uM("key" to 2), "/" + _tD(unref(totalTimeText)), 1)
                                                } else {
                                                    _cC("v-if", true)
                                                }
                                            ), 4),
                                            _cE("view", _uM("style" to _nS(_uM("margin-left" to "12rpx"))), _uA(
                                                _cV(_component_ToggleButton, _uM("onToggle" to handleToggle, "defaultState" to "expand"))
                                            ), 4)
                                        ), 4),
                                        if (isTrue(unref(isShow))) {
                                            _cE("view", _uM("key" to 0, "style" to _nS(_uM("padding" to "5rpx 10rpx")), "class" to "scroll_box"), _uA(
                                                _cE("view", null, _uA(
                                                    _cE("text", null, "听力原文")
                                                )),
                                                _cE("rich-text", _uM("nodes" to unref(soundText)), null, 8, _uA(
                                                    "nodes"
                                                ))
                                            ), 4)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ))
                                ), 4),
                                _cE("view", _uM("style" to _nS(_uM("margin" to "0 auto", "position" to "absolute", "bottom" to "-10rpx", "left" to "50%", "transform" to "translateX(-50%)", "flex-direction" to "row"))), _uA(
                                    _cV(_component_Recognition, _uM("onSuccess" to recognitionSuccess, "language" to unref(language), "category" to unref(category), "subject" to unref(soundText), "ref_key" to "refRecognition", "ref" to refRecognition, "seconds" to unref(exerciseTime), "scale" to 0.7, "isDisable" to unref(isDisable), "intIsShowStat" to unref(isShowStart)), null, 8, _uA(
                                        "language",
                                        "category",
                                        "subject",
                                        "seconds",
                                        "isDisable",
                                        "intIsShowStat"
                                    )),
                                    if (isTrue(unref(isShowNext))) {
                                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("justify-content" to "flex-end", "padding-bottom" to "20rpx"))), _uA(
                                            _cE("image", _uM("src" to "/static/ico/next_btn_ico.png", "style" to _nS(_uM("width" to "56rpx", "height" to "56rpx")), "mode" to "", "onClick" to nextFn), null, 4)
                                        ), 4)
                                    } else {
                                        _cC("v-if", true)
                                    }
                                ), 4),
                                if (isTrue(unref(isShowTime))) {
                                    _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "position" to "absolute", "bottom" to "10rpx", "left" to "10rpx"))), _uA(
                                        _cE("image", _uM("src" to "/static/ico/listening/suo.png", "mode" to "", "style" to _nS(_uM("width" to "23.44rpx", "height" to "23.44rpx")), "onClick" to stopMp3), null, 4),
                                        _cE("text", _uM("class" to "currentTimeTip"), "播放听力中" + _tD(unref(nowTime)), 1)
                                    ), 4)
                                } else {
                                    _cC("v-if", true)
                                }
                            ))
                        )),
                        _cE("view", _uM("class" to "time"), _uA(
                            _cV(_component_BackgroundImage, _uM("src" to "/static/ico/time.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "time_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("text", _uM("class" to "_time"), _tD(unref(timeText)), 1)
                                )
                            }
                            ), "_" to 1))
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "340rpx", "marginTop" to "13.48rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "d_right" to _uM(".d_content " to _uM("flex" to 1, "width" to "100%", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx")), "top_box" to _uM(".d_content .d_right " to _uM("marginTop" to "12rpx", "marginRight" to 0, "marginBottom" to 0, "marginLeft" to 0, "flexDirection" to "row", "paddingTop" to 0, "paddingRight" to "11rpx", "paddingBottom" to 0, "paddingLeft" to "11rpx")), "btn_box" to _uM(".d_content .d_right .top_box " to _uM("flexDirection" to "row", "alignItems" to "center", "marginRight" to "20rpx"), ".d_content .d_right .top_box .active" to _uM("backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "100rpx", "borderTopRightRadius" to "100rpx", "borderBottomRightRadius" to "100rpx", "borderBottomLeftRadius" to "100rpx", "paddingTop" to "5rpx", "paddingRight" to "8rpx", "paddingBottom" to "5rpx", "paddingLeft" to "8rpx")), "tips" to _uM(".d_content .d_right " to _uM("fontWeight" to "400", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "14rpx", "textAlign" to "center", "fontStyle" to "normal", "textTransform" to "none")), "scroll_box" to _uM(".d_content .d_right " to _uM("width" to "379rpx", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginTop" to "14rpx", "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto")), "tingli" to _uM(".d_content .d_right .scroll_box " to _uM("fontSize" to "11rpx", "color" to "#3D3D3D", "lineHeight" to "18rpx")), "currentTimeTip" to _pS(_uM("fontWeight" to "400", "fontSize" to "11rpx", "color" to "#3A58EB", "lineHeight" to "18rpx")), "time" to _pS(_uM("position" to "absolute", "top" to "14rpx", "right" to "14rpx")), "num" to _uM(".time " to _uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D")), "time_box" to _uM(".time " to _uM("width" to "93.75rpx", "height" to "33.4rpx")), "_time" to _uM(".time .time_box " to _uM("width" to "62rpx", "marginLeft" to "31.75rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "fontSize" to "14rpx", "color" to "#5B77FF", "letterSpacing" to 2)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
