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
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesTestPaperKouyu : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var nowSub: String by `$props`
    open var nowSubIndex: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTestPaperKouyu) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTestPaperKouyu
            val _cache = __ins.renderCache
            val rightInfo = ref("\uE600")
            val wordList = ref<UTSArray<teachingMaterial>?>(null)
            val props = __props
            val nowSubIndex = computed(fun(): Number {
                return props.nowSubIndex
            }
            )
            val isStop = ref(false)
            val nowItem = computed(fun(): followAlongItem2? {
                isStop.value = false
                return JSON.parse<followAlongItem2>(props.nowSub)
            }
            )
            val nowSub = computed(fun(): SubProblemList2? {
                return nowItem.value?.subProblemList?.get(nowSubIndex.value) ?: null
            }
            )
            val wordListEnd = ref<UTSArray<teachingMaterial>?>(null)
            val answerMap = ref<Map<Number, answerMapType>>(Map())
            val isDisable = ref(false)
            val recognitionSuccessFlag = ref(false)
            fun emits(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val refRecognition = ref<RecognitionComponentPublicInstance?>(null)
            val isShowStart = ref(false)
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
                    emits("setNowSubIndex", nowSubIndex.value + 1)
                    soundCount.value = 0
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
                console.log(isStop.value, isDisable.value)
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
            fun gen_makeExerciseProblemBoList_fn(): UTSArray<makeSubTop4> {
                if (nowItem.value?.problemType != "35") {
                    var retArr = _uA<makeSubTop4>(makeSubTop4(problemId = nowItem.value?.id ?: 0, subProblemList = nowItem.value?.subProblemList!!.map(fun(item, index): makeSubTop4 {
                        return makeSubTop4(problemId = item.id, userSpeakUrl = pathUpArr.value.get(index) ?: "", temp = answerMap.value.get(index)?.temp ?: _uA(), adopt = answerMap.value.get(index)!!.adopt, isPass = "1")
                    })))
                    return retArr
                } else {
                    var aa = _uA<makeSubTop4>(makeSubTop4(problemId = nowItem.value!!.id, userSpeakUrl = pathUpArr.value.get(0) ?: "", temp = answerMap.value.get(0)?.temp ?: _uA(), adopt = answerMap.value.get(0)?.adopt ?: adopt(score = 0, complete = 0, accurate = 0, fluent = 0), isPass = "1"))
                    return aa
                }
            }
            val makeExerciseProblemBoList = ::gen_makeExerciseProblemBoList_fn
            fun gen_sendUp_fn() {
                return
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
                uploadFileToOSS(data.path, fun(err, url){
                    if (err != null) {
                        return
                    }
                    pathUpArr.value.set(_nowSubIndex, url ?: "")
                    recognitionSuccessFlag.value = true
                    uni_hideLoading()
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
                    var _source = data.result.getString("total_score") ?: "0"
                    var _sourceNum = parseInt(_source)
                    meetDb.value = getConfig("语音评测达标分数配置").getNumber("sentencePassScore") ?: 0
                    if (wordArr != null) {
                        var analysisArr = handleSuccess(wordArr as UTSArray<UTSJSONObject>)
                        if (analysisArr != null) {
                            answerMap.value.set(_nowSubIndex, answerMapType(adopt = adoptObj, temp = analysisArr, url = url ?: ""))
                            if (nowItem.value?.problemType != "35") {
                                emits("setSongMap", answerMapType(adopt = adoptObj, temp = analysisArr, url = url ?: "", isPass = if (_sourceNum >= meetDb.value) {
                                    "1"
                                } else {
                                    "0"
                                }), nowSub.value?.id)
                            } else {
                                emits("setSongMap", answerMapType(adopt = adoptObj, temp = analysisArr, url = url ?: "", isPass = if (_sourceNum >= meetDb.value) {
                                    "1"
                                } else {
                                    "0"
                                }
                                ), nowItem.value?.id)
                            }
                        }
                    }
                    pathArr.value.set(_nowSubIndex, JSON.stringify(data))
                    if (nowItem.value?.problemType != "35") {
                        var _len = nowItem.value?.subProblemList!!.length ?: 0
                        if (_nowSubIndex + 1 < _len) {
                            uni_showToast(ShowToastOptions(title = "准备下一句", icon = "none"))
                            isDisable.value = false
                            isStop.value = false
                            WjAudioPlayer1.value?.reset?.invoke()
                            nextFn()
                        } else {
                            checkAndSendUp()
                        }
                    } else {
                        checkAndSendUp()
                    }
                    isShowDuration.value = false
                }
                )
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
            onPageShow(fun(){})
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_wj_audio_player = resolveEasyComponent("wj-audio-player", GenUniModulesWjAudioPlayerComponentsWjAudioPlayerWjAudioPlayerClass)
                val _component_ToggleButton = resolveEasyComponent("ToggleButton", GenComponentsToggleButtonToggleButtonClass)
                val _component_Recognition = resolveEasyComponent("Recognition", GenComponentsRecognitionRecognitionClass)
                return _cE("view", _uM("class" to "d_content22"), _uA(
                    _cE("view", _uM("class" to "d_right"), _uA(
                        _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("max-height" to "200rpx"))), _uA(
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
                ))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("d_content22" to _pS(_uM("width" to "100%", "flex" to 1, "marginTop" to "40rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "d_right" to _uM(".d_content22 " to _uM("flex" to 1, "width" to "100%")), "tips" to _uM(".d_content22 .d_right " to _uM("fontWeight" to "400", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "14rpx", "textAlign" to "center", "fontStyle" to "normal", "textTransform" to "none")), "scroll_box" to _uM(".d_content22 .d_right " to _uM("width" to "100%", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "marginTop" to "14rpx", "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto")), "tingli" to _uM(".d_content22 .d_right .scroll_box " to _uM("fontSize" to "11rpx", "color" to "#3D3D3D", "lineHeight" to "18rpx")), "currentTimeTip" to _pS(_uM("fontWeight" to "400", "fontSize" to "11rpx", "color" to "#3A58EB", "lineHeight" to "18rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("setNowSubIndex" to null, "setSongMap" to null)
        var props = _nP(_uM("nowSub" to _uM("type" to "String", "required" to true, "default" to ""), "nowSubIndex" to _uM("type" to "Number", "required" to true, "default" to 0)))
        var propsNeedCastKeys = _uA(
            "nowSub",
            "nowSubIndex"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
