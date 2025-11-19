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
import io.dcloud.uniapp.extapi.getElementById as uni_getElementById
open class GenComponentsReadResultYufa : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var id: Number by `$props`
    open var problemId: Number by `$props`
    open var secondVal: Number by `$props`
    open var type: String by `$props`
    open var visible: Boolean by `$props`
    open var nowItem: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsReadResultYufa) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsReadResultYufa
            val _cache = __ins.renderCache
            val nowIndexAnswer = ref(0)
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
            val yuanwen = ref<UTSArray<UniElement>?>(null)
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
            val currentType = ref<String?>(null)
            val currentTypeText = computed(fun(): String {
                if (currentType.value == null) {
                    return "听力练习"
                }
                return ("题型：" + problemTypeMap.value.get(currentType.value!!)) ?: "听力练习"
            }
            )
            fun gen_replaceNewlineWithBr_fn(text: String?): String {
                if (text == null) {
                    return ""
                }
                return text.replace(UTSRegExp("\\n", "g"), "<br/>")
            }
            val replaceNewlineWithBr = ::gen_replaceNewlineWithBr_fn
            val currentTypeIcon = computed(fun(): String {
                if (currentType.value == null) {
                    return "/static/ico/listening/ico1.png"
                }
                return problemTypeIconMap.value.get(currentType.value!!) ?: "/static/ico/listening/ico1.png"
            }
            )
            val nowItem = ref<AppProblemList2?>(null)
            val subProblemListLength = computed(fun(): Number {
                return nowItem.value?.subProblemList?.length ?: 0
            }
            )
            fun emits(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val props = __props
            val isShowYw = ref(false)
            val isShowYwNot33 = ref(false)
            fun gen_handleNot33_fn(ev: Boolean) {
                isShowYwNot33.value = ev
            }
            val handleNot33 = ::gen_handleNot33_fn
            val yWtext = computed(fun(): String {
                console.log(nowItem.value?.subProblemList)
                return "" + replaceNewlineWithBr(nowItem.value?.soundInfoList?.get(0)?.originalText ?: "")
            }
            )
            fun gen_heightFn_fn(h: Number) {
                uni_getElementById("selectText")?.style?.setProperty("max-height", "" + h + "px")
                uni_getElementById("selectText")?.style?.setProperty("min-height", "" + h + "px")
            }
            val heightFn = ::gen_heightFn_fn
            val WjAudioPlayer1 = ref<UTSArray<WjAudioPlayerComponentPublicInstance>?>(null)
            val playMap = ref(Map<Number, Boolean>())
            val playMapTime = ref(Map<Number, String>())
            fun gen_onProgressChanged_fn(ev: UTSJSONObject, index: Number) {
                val currentTime = ev.getNumber("currentTime") ?: 0
                playMapTime.value.set(index, formatTime(currentTime))
            }
            val onProgressChanged = ::gen_onProgressChanged_fn
            fun gen_palyMp3_fn(index: Number) {
                WjAudioPlayer1.value?.forEach(fun(item){
                    item.pause?.invoke()
                }
                )
                val playState = playMap.value.get(index)
                if (playState == null || playState == false) {
                    WjAudioPlayer1.value?.get(index)?.play?.invoke()
                    playMap.value.set(index, true)
                    return
                }
                if (playState == true) {
                    playMap.value.set(index, false)
                    return
                }
            }
            val palyMp3 = ::gen_palyMp3_fn
            fun gen_playbackCompleted_fn(index: Number) {
                playMap.value.set(index, false)
                playMapTime.value.set(index, formatTime(0))
            }
            val playbackCompleted = ::gen_playbackCompleted_fn
            val nowAnswer = computed(fun(): String? {
                return JSON.stringify(nowItem.value?.subProblemList?.get(0))
            }
            )
            val isVisible = computed(fun(): Boolean {
                return props.visible
            }
            )
            fun gen_formatTimeFn_fn(num: Number): String {
                return formatTime(num)
            }
            val formatTimeFn = ::gen_formatTimeFn_fn
            val isShowJx = ref(false)
            val showJxType = ref(0)
            fun gen_showJx_fn(type: Number, index: Number) {
                isShowJx.value = true
                showJxType.value = type
                nowIndexAnswer.value = index
            }
            val showJx = ::gen_showJx_fn
            val nowText = ref("")
            fun gen_processRichText_fn(rich: AppProblemList2): UTSArray<UTSArray<resultItem>>? {
                var richText = rich.questionContent!!
                val htmlTagRegex = UTSRegExp("<[^>]+>", "g")
                val textWithoutTags = richText.replace(UTSRegExp("<\\/?([^img][^>]*?|img\\s+[^>]*?)(?=\\/?)>", "gi"), "")
                val pTagRegex = UTSRegExp("<\\/?p[^>]*>", "gi")
                val normalizedText = richText.replace(pTagRegex, "\n ")
                val lineBreakRegex = UTSRegExp(UTSRegExp("\\r?\\n", ""))
                val lines = normalizedText.replace(UTSRegExp("\"", "g"), "'").split(lineBreakRegex)
                val nonEmptyLines = lines.filter(fun(line): Boolean {
                    return line.trim() !== ""
                }
                )
                val result: UTSArray<UTSArray<resultItem>> = _uA()
                val underlineRegex = UTSRegExp("_{1,}|＿{1,}", "g")
                var nowIndex: Number = 0
                nonEmptyLines.forEach(fun(line){
                    val parts = line.split(underlineRegex)
                    val matches = line.match(underlineRegex) ?: _uA()
                    var blankAnswer = _uA<String>()
                    var userBlankAnswer = _uA<String>()
                    if (rich.blankAnswer != null) {
                        if (rich.blankAnswer!!.indexOf("|") != -1) {
                            blankAnswer = rich.blankAnswer!!.split("|")
                        } else {
                            blankAnswer = _uA(
                                rich.blankAnswer!!
                            )
                        }
                    }
                    if (rich.userBlankAnswer != null) {
                        if (rich.userBlankAnswer!!.indexOf("|") != -1) {
                            userBlankAnswer = rich.userBlankAnswer!!.split("|")
                        } else {
                            userBlankAnswer = _uA(
                                rich.userBlankAnswer!!
                            )
                        }
                    } else {
                        userBlankAnswer = blankAnswer.map(fun(item): String {
                            return ""
                        }
                        )
                    }
                    val lineItems: UTSArray<resultItem> = _uA()
                    parts.forEach(fun(part, index){
                        if (part.trim() !== "") {
                            val htmlTagRegex = UTSRegExp("<(img|br|hr|input|meta|link|script|style|div|span|p|a|table|tr|td)[^>]*>", "i")
                            if (htmlTagRegex.test(part.trim())) {
                                lineItems.push(resultItem(type = "text", content = part, userBlankAnswer = "", answer = ""))
                            } else {
                                part.split(" ").forEach(fun(txt){
                                    lineItems.push(resultItem(type = "text", content = txt + " ", userBlankAnswer = "", answer = ""))
                                }
                                )
                            }
                        }
                        if (matches.length > 0) {
                            if (index < matches.length) {
                                if (rich.subProblemList != null && rich.problemType != "13") {
                                    if (nowIndex < rich.subProblemList!!.length) {
                                        lineItems.push(resultItem(type = "input", content = part, userBlankAnswer = rich.subProblemList!![nowIndex]?.userBlankAnswer ?: "", answer = rich.subProblemList!![nowIndex].blankAnswer!!))
                                    } else {
                                        lineItems.push(resultItem(type = "input", content = part, userBlankAnswer = "异常题目", answer = ""))
                                    }
                                }
                                if (rich.problemType == "13") {
                                    if (nowIndex > blankAnswer.length - 1) {
                                        lineItems.push(resultItem(type = "input", content = part, userBlankAnswer = if (userBlankAnswer.length == 1) {
                                            userBlankAnswer[0]
                                        } else {
                                            userBlankAnswer[nowIndex]
                                        }, answer = "未知"))
                                    } else {
                                        lineItems.push(resultItem(type = "input", content = part, userBlankAnswer = if (userBlankAnswer.length == 1) {
                                            userBlankAnswer[0]
                                        } else {
                                            userBlankAnswer[nowIndex]
                                        }
                                        , answer = if (blankAnswer.length == 1) {
                                            blankAnswer[0]
                                        } else {
                                            blankAnswer[nowIndex]
                                        }
                                        ))
                                    }
                                }
                                nowIndex++
                            }
                        }
                    }
                    )
                    result.push(lineItems)
                }
                )
                return result
            }
            val processRichText = ::gen_processRichText_fn
            fun gen_handleToggle_fn(isActive: Boolean, index: Number) {
                yuanwen.value!![index].style.setProperty("display", if (isActive) {
                    "flex"
                } else {
                    "none"
                }
                )
            }
            val handleToggle = ::gen_handleToggle_fn
            fun gen_getDetail_fn() {
                nowItem.value = JSON.parse<AppProblemList2>(props.nowItem)
                currentType.value = nowItem.value?.problemType
                if (currentType.value == "33") {
                    var _arr = processRichText(nowItem.value!!)!!
                    var arr = _uA<UTSJSONObject>()
                    _arr.forEach(fun(item){
                        item.forEach(fun(temp){
                            if (temp.type == "input") {
                                if (temp.answer.includes("^")) {
                                    var _answer = temp.answer.split("^")
                                    if (_answer.findIndex(fun(i): Boolean {
                                        return i == (temp.userBlankAnswer ?: "")
                                    }) != -1) {
                                        arr.push(object : UTSJSONObject() {
                                            var text = temp.userBlankAnswer + " "
                                            var color = "#008000"
                                            var bold = false
                                            var underline = true
                                        })
                                    } else {
                                        arr.push(object : UTSJSONObject() {
                                            var text = if (temp.userBlankAnswer == "") {
                                                "未做题"
                                            } else {
                                                temp.userBlankAnswer + " "
                                            }
                                            var color = "#e23d30"
                                            var bold = false
                                            var underline = true
                                        })
                                    }
                                } else {
                                    if (temp.answer == temp.userBlankAnswer) {
                                        arr.push(object : UTSJSONObject() {
                                            var text = temp.answer + " "
                                            var color = "#008000"
                                            var bold = false
                                            var underline = true
                                        })
                                    } else {
                                        arr.push(object : UTSJSONObject() {
                                            var text = if (temp.userBlankAnswer == "") {
                                                "未做题"
                                            } else {
                                                temp.userBlankAnswer + " "
                                            }
                                            var color = "#e23d30"
                                            var bold = false
                                            var underline = true
                                        })
                                    }
                                }
                            } else {
                                arr.push(object : UTSJSONObject() {
                                    var text = temp.content
                                    var color = "#333333"
                                    var bold = false
                                })
                            }
                        }
                        )
                        arr.push(object : UTSJSONObject() {
                            var text = "\n"
                            var color = "#333333"
                            var bold = false
                        })
                    }
                    )
                    nowText.value = JSON.stringify(arr)
                }
            }
            val getDetail = ::gen_getDetail_fn
            fun gen_getCorrectAnswer_fn(options: UTSArray<Options>?): String {
                if (options == null || options.length == 0) {
                    return ""
                }
                val correctOption = options.find(fun(option): Boolean {
                    return option.isRight
                }
                )
                return if (correctOption != null) {
                    "" + correctOption.option
                } else {
                    ""
                }
            }
            val getCorrectAnswer = ::gen_getCorrectAnswer_fn
            fun gen_closeFullScreen_fn() {
                emits("close")
            }
            val closeFullScreen = ::gen_closeFullScreen_fn
            watchEffect(fun(){
                getDetail()
                playMap.value.clear()
            }
            )
            return fun(): Any? {
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_wj_audio_player = resolveEasyComponent("wj-audio-player", GenUniModulesWjAudioPlayerComponentsWjAudioPlayerWjAudioPlayerClass)
                val _component_ToggleButton = resolveEasyComponent("ToggleButton", GenComponentsToggleButtonToggleButtonClass)
                val _component_wj_selectText = resolveEasyComponent("wj-selectText", GenUniModulesWjSelectTextComponentsWjSelectTextWjSelectTextClass)
                val _component_ListeningChoice = resolveEasyComponent("ListeningChoice", GenComponentsListeningChoiceListeningChoiceClass)
                val _component_analysis_popup = resolveEasyComponent("analysis-popup", GenComponentsAnalysisPopupAnalysisPopupClass)
                val _component_defult_popup = resolveEasyComponent("defult-popup", GenComponentsDefultPopupDefultPopupClass)
                return _cE(Fragment, null, _uA(
                    _cE("view", _uM("class" to "d_content2"), _uA(
                        if (isTrue(unref(currentType) == "31" || unref(currentType) == "32" || unref(currentType) == "33")) {
                            _cE("view", _uM("key" to 0, "class" to "d_left"), _uA(
                                if (isTrue(unref(currentType) == "31" || unref(currentType) == "32")) {
                                    _cE(Fragment, _uM("key" to 0), _uA(
                                        _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("margin" to "12rpx 19rpx"))), _uA(
                                            _cE("view", null, _uA(
                                                _cE("rich-text", _uM("nodes" to (unref(nowItem)?.questionContent ?: "")), null, 8, _uA(
                                                    "nodes"
                                                ))
                                            ))
                                        ), 4),
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(nowItem)?.subProblemList, fun(item, index, __index, _cached): Any {
                                            return _cE("view", null, _uA(
                                                if (index === 0) {
                                                    _cE("view", _uM("key" to 0, "style" to _nS(_uM("margin" to "0 0 0 11rpx", "align-items" to "flex-start"))), _uA(
                                                        _cE("view", null, _uA(
                                                            _cV(_component_u_playMp3, _uM("style" to _nS(_uM("align-items" to "flex-start")), "src" to (unref(nowItem)?.soundInfoList?.get(0)?.soundUrl ?: ""), "tplType" to 6, "disabled" to (unref(currentType) != "33"), "onClick" to fun(){
                                                                palyMp3(index)
                                                            }), null, 8, _uA(
                                                                "style",
                                                                "src",
                                                                "disabled",
                                                                "onClick"
                                                            )),
                                                            _cE("view", _uM("style" to _nS(_uM("margin-top" to "12rpx", "flex-direction" to "row", "align-items" to "center", "justify-content" to "center", "margin-bottom" to "10rpx"))), _uA(
                                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                                    _cE("image", _uM("style" to _nS(_uM("width" to "24rpx", "height" to "24rpx")), "src" to if (unref(playMap).get(index) == null || unref(playMap).get(index) == false) {
                                                                        "/static/ico/paly_2.png"
                                                                    } else {
                                                                        "/static/ico/pause_ico.png"
                                                                    }, "mode" to "aspectFit", "onClick" to fun(){
                                                                        palyMp3(index)
                                                                    }), null, 12, _uA(
                                                                        "src",
                                                                        "onClick"
                                                                    )),
                                                                    _cE("view", _uM("style" to _nS(_uM("width" to "50rpx"))), _uA(
                                                                        _cV(_component_wj_audio_player, _uM("audio-url" to (unref(nowItem)?.soundInfoList?.get(0)?.soundUrl ?: ""), "auto-play" to false, "progress-color" to "#4e6ef2", "progress-height" to 12, "ref_for" to true, "ref_key" to "WjAudioPlayer1", "ref" to WjAudioPlayer1, "seekEnabled" to true, "onPlaybackCompleted" to fun(){
                                                                            playbackCompleted(index)
                                                                        }, "onProgressChanged" to fun(`$event`: Any){
                                                                            onProgressChanged(`$event` as UTSJSONObject, index)
                                                                        }), null, 8, _uA(
                                                                            "audio-url",
                                                                            "onPlaybackCompleted",
                                                                            "onProgressChanged"
                                                                        ))
                                                                    ), 4),
                                                                    _cE("text", _uM("style" to _nS(_uM("margin-left" to "6rpx"))), _tD(unref(playMapTime).get(index)), 5)
                                                                ), 4),
                                                                _cE("view", _uM("style" to _nS(_uM("margin-left" to "12rpx"))), _uA(
                                                                    _cV(_component_ToggleButton, _uM("onToggle" to fun(`$event`: Any){
                                                                        handleNot33(`$event` as Boolean)
                                                                    }, "defaultState" to "expand"), null, 8, _uA(
                                                                        "onToggle"
                                                                    ))
                                                                ), 4)
                                                            ), 4)
                                                        ))
                                                    ), 4)
                                                } else {
                                                    _cC("v-if", true)
                                                }
                                            ))
                                        }), 256),
                                        _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1", "margin" to "12rpx 19rpx"))), _uA(
                                            if (unref(currentType) != "33") {
                                                _cE("view", _uM("key" to 0), _uA(
                                                    if (isTrue(unref(isShowYwNot33))) {
                                                        _cE("rich-text", _uM("key" to 0, "nodes" to replaceNewlineWithBr(unref(nowItem)?.soundInfoList?.get(0)?.originalText ?: "")), null, 8, _uA(
                                                            "nodes"
                                                        ))
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ))
                                            } else {
                                                _cE("view", _uM("key" to 1, "style" to _nS(_uM("height" to "100%"))), _uA(
                                                    _cV(_component_wj_selectText, _uM("center" to "center", "style" to _nS(_uM("height" to "100%")), "text" to unref(nowText)), null, 8, _uA(
                                                        "style",
                                                        "text"
                                                    ))
                                                ), 4)
                                            }
                                        ), 4)
                                    ), 64)
                                } else {
                                    _cC("v-if", true)
                                },
                                if (unref(currentType) == "33") {
                                    _cE(Fragment, _uM("key" to 1), _uA(
                                        _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1", "margin" to "12rpx 19rpx"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("height" to "100%"))), _uA(
                                                _cV(_component_wj_selectText, _uM("center" to "center", "style" to _nS(_uM("height" to "100%")), "text" to unref(nowText)), null, 8, _uA(
                                                    "style",
                                                    "text"
                                                ))
                                            ), 4)
                                        ), 4),
                                        _cE("view", _uM("style" to _nS(_uM("padding" to "10rpx"))), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(nowItem)?.subProblemList, fun(item, index, __index, _cached): Any {
                                                return _cE("view", null, _uA(
                                                    if (index == 0) {
                                                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("align-items" to "flex-start", "flex-wrap" to "wrap", "flex-direction" to "row", "padding-bottom" to "-10rpx"))), _uA(
                                                            _cE(Fragment, null, RenderHelpers.renderList(unref(nowItem)?.soundInfoList, fun(mp3, __key, __index, _cached): Any {
                                                                return _cE("view", null, _uA(
                                                                    _cV(_component_u_playMp3, _uM("style" to _nS(_uM("align-items" to "flex-start")), "tplType" to 6, "disabled" to true, "onClick" to fun(){
                                                                        palyMp3(index)
                                                                    }), null, 8, _uA(
                                                                        "style",
                                                                        "onClick"
                                                                    )),
                                                                    _cE("view", _uM("style" to _nS(_uM("margin-top" to "12rpx", "flex-direction" to "row", "align-items" to "center", "justify-content" to "center", "margin-bottom" to "10rpx"))), _uA(
                                                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                                            _cE("image", _uM("style" to _nS(_uM("width" to "24rpx", "height" to "24rpx")), "src" to if (unref(playMap).get(index) == null || unref(playMap).get(index) == false) {
                                                                                "/static/ico/paly_2.png"
                                                                            } else {
                                                                                "/static/ico/pause_ico.png"
                                                                            }, "mode" to "aspectFit", "onClick" to fun(){
                                                                                palyMp3(index)
                                                                            }), null, 12, _uA(
                                                                                "src",
                                                                                "onClick"
                                                                            )),
                                                                            _cE("view", _uM("style" to _nS(_uM("width" to "120rpx"))), _uA(
                                                                                _cV(_component_wj_audio_player, _uM("audio-url" to (mp3?.soundUrl ?: ""), "auto-play" to false, "progress-color" to "#4e6ef2", "progress-height" to 12, "ref_for" to true, "ref_key" to "WjAudioPlayer1", "ref" to WjAudioPlayer1, "seekEnabled" to true, "onPlaybackCompleted" to fun(){
                                                                                    playbackCompleted(index)
                                                                                }, "onProgressChanged" to fun(`$event`: Any){
                                                                                    onProgressChanged(`$event` as UTSJSONObject, index)
                                                                                }), null, 8, _uA(
                                                                                    "audio-url",
                                                                                    "onPlaybackCompleted",
                                                                                    "onProgressChanged"
                                                                                ))
                                                                            ), 4),
                                                                            _cE("text", _uM("style" to _nS(_uM("margin-left" to "6rpx"))), _tD(unref(playMapTime).get(index)), 5)
                                                                        ), 4),
                                                                        _cE("view", _uM("style" to _nS(_uM("margin-left" to "12rpx")), "onClick" to fun(){
                                                                            isShowYw.value = true
                                                                        }), _uA(
                                                                            _cE("view", _uM("style" to _nS(_uM("width" to "49rpx", "height" to "15rpx", "background" to "#607BFF", "border-radius" to "999rpx", "align-items" to "center", "justify-content" to "center"))), _uA(
                                                                                _cE("text", _uM("style" to _nS(_uM("font-size" to "9rpx", "color" to "#FFF", "line-height" to "15rpx"))), "展开原文", 4)
                                                                            ), 4)
                                                                        ), 12, _uA(
                                                                            "onClick"
                                                                        ))
                                                                    ), 4)
                                                                ))
                                                            }), 256)
                                                        ), 4)
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ))
                                            }), 256)
                                        ), 4)
                                    ), 64)
                                } else {
                                    _cC("v-if", true)
                                }
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        _cE("view", _uM("class" to "d_right"), _uA(
                            if (unref(currentType) == "30") {
                                _cE("view", _uM("key" to 0, "style" to _nS(_uM("margin" to "12rpx 0 0 11rpx")), "id" to "selectText"), _uA(
                                    _cV(_component_wj_selectText, _uM("onHeightFn" to heightFn, "center" to "center", "style" to _nS(_uM("height" to "100%")), "text" to (unref(nowItem)?.questionContent ?: "")), null, 8, _uA(
                                        "style",
                                        "text"
                                    ))
                                ), 4)
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cE("scroll-view", _uM("direction" to "vertical", "class" to "scroll_box"), _uA(
                                _cE(Fragment, null, RenderHelpers.renderList(unref(nowItem)?.subProblemList, fun(item, index, __index, _cached): Any {
                                    return _cE("view", _uM("class" to _nC(_uM("show-border" to ((unref(currentType) == "30" || unref(currentType) == "31" || unref(currentType) == "32") && index < unref(subProblemListLength) - 1)))), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                            _cE("text", _uM("class" to "question_text"), _tD(index + 1) + ". " + _tD(item?.questionContent), 1),
                                            if (isTrue(item.userOption == "" && item.userBlankAnswer == "")) {
                                                _cE("text", _uM("key" to 0, "style" to _nS(_uM("color" to "red", "margin-left" to "10rpx"))), "未做题", 4)
                                            } else {
                                                _cC("v-if", true)
                                            }
                                        ), 4),
                                        if (unref(currentType) == "33") {
                                            _cE("view", _uM("key" to 0), _uA(
                                                _cE("text", _uM("style" to _nS(_uA(
                                                    _uM("height" to "20rpx", "line-height" to "20rpx", "width" to "100%", "border-radius" to "10rpx", "padding" to "0 10rpx"),
                                                    _uM("backgroundColor" to if (item.userBlankAnswer === "") {
                                                        "#F56C6C"
                                                    } else {
                                                        if (item?.rightStatus == "1") {
                                                            "#67C23A"
                                                        } else {
                                                            "#F56C6C"
                                                        }
                                                    })
                                                ))), _tD(item?.userBlankAnswer), 5)
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        }
                                        ,
                                        _cE("view", _uM("style" to _nS(_uM("align-items" to "flex-start"))), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(item?.soundInfoList, fun(mp3, __key, __index, _cached): Any {
                                                return _cE(Fragment, null, _uA(
                                                    if ((mp3?.soundUrl ?: "") != "") {
                                                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("margin-bottom" to "10rpx"))), _uA(
                                                            _cV(_component_u_playMp3, _uM("src" to mp3?.soundUrl, "tplType" to 4, "disabled" to (unref(currentType) != "33"), "onClick" to fun(){
                                                                palyMp3(index)
                                                            }), null, 8, _uA(
                                                                "src",
                                                                "disabled",
                                                                "onClick"
                                                            ))
                                                        ), 4)
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                    ,
                                                    if (unref(currentType) == "30") {
                                                        _cE("view", _uM("key" to 1), _uA(
                                                            if ((mp3?.soundUrl ?: "") != "") {
                                                                _cE("view", _uM("key" to 0, "style" to _nS(_uM("margin-top" to "12rpx", "flex-direction" to "row", "align-items" to "center", "margin-bottom" to "10rpx"))), _uA(
                                                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                                        _cE("image", _uM("style" to _nS(_uM("width" to "24rpx", "height" to "24rpx")), "src" to if (unref(playMap).get(index) == null || unref(playMap).get(index) == false) {
                                                                            "/static/ico/paly_2.png"
                                                                        } else {
                                                                            "/static/ico/pause_ico.png"
                                                                        }, "mode" to "aspectFit", "onClick" to fun(){
                                                                            palyMp3(index)
                                                                        }), null, 12, _uA(
                                                                            "src",
                                                                            "onClick"
                                                                        )),
                                                                        _cE("view", _uM("style" to _nS(_uM("width" to "170rpx"))), _uA(
                                                                            _cV(_component_wj_audio_player, _uM("audio-url" to (mp3.soundUrl ?: ""), "auto-play" to false, "progress-color" to "#4e6ef2", "progress-height" to 12, "ref_for" to true, "ref_key" to "WjAudioPlayer1", "ref" to WjAudioPlayer1, "seekEnabled" to true, "onPlaybackCompleted" to fun(){
                                                                                playbackCompleted(index)
                                                                            }, "onProgressChanged" to fun(`$event`: Any){
                                                                                onProgressChanged(`$event` as UTSJSONObject, index)
                                                                            }), null, 8, _uA(
                                                                                "audio-url",
                                                                                "onPlaybackCompleted",
                                                                                "onProgressChanged"
                                                                            ))
                                                                        ), 4),
                                                                        _cE("text", _uM("style" to _nS(_uM("margin-left" to "6rpx"))), _tD(unref(playMapTime).get(index)), 5)
                                                                    ), 4),
                                                                    if ((mp3.originalText ?: "") != "") {
                                                                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("margin-left" to "12rpx"))), _uA(
                                                                            _cV(_component_ToggleButton, _uM("onToggle" to fun(`$event`: Any){
                                                                                handleToggle(`$event` as Boolean, index)
                                                                            }, "defaultState" to "expand"), null, 8, _uA(
                                                                                "onToggle"
                                                                            ))
                                                                        ), 4)
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    }
                                                                ), 4)
                                                            } else {
                                                                _cC("v-if", true)
                                                            },
                                                            if ((mp3.originalText ?: "") != "") {
                                                                _cE("scroll-view", _uM("key" to 1, "class" to "scroll_box2", "style" to _nS(_uM("padding" to "5rpx 10rpx")), "direction" to "vertical", "ref_for" to true, "ref_key" to "yuanwen", "ref" to yuanwen), _uA(
                                                                    _cE("rich-text", _uM("nodes" to replaceNewlineWithBr(mp3.originalText)), null, 8, _uA(
                                                                        "nodes"
                                                                    ))
                                                                ), 4)
                                                            } else {
                                                                _cC("v-if", true)
                                                            }
                                                        ))
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ), 64)
                                            }
                                            ), 256),
                                            if (isTrue(!_uA(
                                                "30",
                                                "33"
                                            ).includes(unref(currentType) ?: ""))) {
                                                _cE("view", _uM("key" to 0), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("margin-top" to "12rpx", "flex-direction" to "row", "align-items" to "center", "justify-content" to "center", "margin-bottom" to "10rpx"))), _uA(
                                                        if ((item?.soundInfoList?.get(0)?.originalText ?: "") != "") {
                                                            _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                                _cE("image", _uM("style" to _nS(_uM("width" to "24rpx", "height" to "24rpx")), "src" to if (unref(playMap).get(index) == null || unref(playMap).get(index) == false) {
                                                                    "/static/ico/paly_2.png"
                                                                } else {
                                                                    "/static/ico/pause_ico.png"
                                                                }, "mode" to "aspectFit", "onClick" to fun(){
                                                                    palyMp3(index)
                                                                }), null, 12, _uA(
                                                                    "src",
                                                                    "onClick"
                                                                )),
                                                                _cE("view", _uM("style" to _nS(_uM("width" to "100rpx"))), _uA(
                                                                    _cV(_component_wj_audio_player, _uM("audio-url" to (item?.soundInfoList?.get(0)?.soundUrl ?: ""), "auto-play" to false, "progress-color" to "#4e6ef2", "progress-height" to 12, "ref_for" to true, "ref_key" to "WjAudioPlayer1", "ref" to WjAudioPlayer1, "seekEnabled" to true, "onPlaybackCompleted" to fun(){
                                                                        playbackCompleted(index)
                                                                    }, "onProgressChanged" to fun(`$event`: Any){
                                                                        onProgressChanged(`$event` as UTSJSONObject, index)
                                                                    }), null, 8, _uA(
                                                                        "audio-url",
                                                                        "onPlaybackCompleted",
                                                                        "onProgressChanged"
                                                                    ))
                                                                ), 4),
                                                                _cE("text", _uM("style" to _nS(_uM("margin-left" to "6rpx"))), _tD(unref(playMapTime).get(index)), 5)
                                                            ), 4)
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        if ((item?.soundInfoList?.get(0)?.originalText ?: "") != "") {
                                                            _cE("view", _uM("key" to 1, "style" to _nS(_uM("width" to "100rpx", "margin-left" to "12rpx"))), _uA(
                                                                _cV(_component_ToggleButton, _uM("onToggle" to fun(`$event`: Any){
                                                                    handleToggle(`$event` as Boolean, index)
                                                                }, "defaultState" to "expand"), null, 8, _uA(
                                                                    "onToggle"
                                                                ))
                                                            ), 4)
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                    ), 4),
                                                    if ((item?.soundInfoList?.get(0)?.originalText ?: "") != "") {
                                                        _cE("scroll-view", _uM("key" to 0, "class" to "scroll_box2", "direction" to "vertical", "ref_for" to true, "ref_key" to "yuanwen", "ref" to yuanwen), _uA(
                                                            _cE("view", _uM("style" to _nS(_uM("padding" to "5rpx 10rpx"))), _uA(
                                                                _cE("rich-text", _uM("nodes" to replaceNewlineWithBr(item?.soundInfoList?.get(0)?.originalText ?: "")), null, 8, _uA(
                                                                    "nodes"
                                                                ))
                                                            ), 4)
                                                        ), 512)
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ))
                                            } else {
                                                _cC("v-if", true)
                                            }
                                        ), 4),
                                        if (unref(currentType) != "33") {
                                            _cV(_component_ListeningChoice, _uM("key" to 1, "options" to item?.options, "index" to index, "disabled" to true, "selectedValue" to item.userOption), null, 8, _uA(
                                                "options",
                                                "index",
                                                "selectedValue"
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        }
                                        ,
                                        _cE("view", _uM("style" to _nS(_uM("margin-top" to "10rpx", "margin-bottom" to "10rpx", "flex-direction" to "row", "align-items" to "center"))), _uA(
                                            _cE("text", _uM("style" to _nS(_uM("color" to "#333"))), "正确答案：", 4),
                                            if (unref(currentType) != "33") {
                                                _cE("text", _uM("key" to 0, "style" to _nS(_uM("color" to "#4CAF50", "font-size" to "14rpx", "font-weight" to "bold", "margin-right" to "10rpx"))), _tD(getCorrectAnswer(item?.options)), 5)
                                            } else {
                                                _cE("text", _uM("key" to 1, "style" to _nS(_uM("color" to "#4CAF50", "font-size" to "14rpx", "font-weight" to "bold", "margin-right" to "10rpx"))), _tD(item.blankAnswer), 5)
                                            }
                                            ,
                                            _cE("text", _uM("class" to "showJx", "onClick" to fun(){
                                                showJx(0, index)
                                            }
                                            ), "查看解析", 8, _uA(
                                                "onClick"
                                            )),
                                            if (isTrue(item?.videoAnalysisUrl != null && item?.videoAnalysisUrl != "")) {
                                                _cE("text", _uM("key" to 2, "class" to "showSpJx", "onClick" to fun(){
                                                    showJx(1, index)
                                                }), "视频解析", 8, _uA(
                                                    "onClick"
                                                ))
                                            } else {
                                                _cC("v-if", true)
                                            }
                                        ), 4)
                                    ), 2)
                                }
                                ), 256)
                            ))
                        ))
                    )),
                    _cV(_component_analysis_popup, _uM("isShow" to unref(isShowJx), "onUpdate:isShow" to fun(`$event`: Boolean){
                        trySetRefValue(isShowJx, `$event`)
                    }
                    , "nowAnswer" to unref(nowAnswer), "tpl" to unref(showJxType), "index" to unref(nowIndexAnswer)), null, 8, _uA(
                        "isShow",
                        "nowAnswer",
                        "tpl",
                        "index"
                    )),
                    _cV(_component_defult_popup, _uM("isShow" to unref(isShowYw), "onUpdate:isShow" to fun(`$event`: Boolean){
                        trySetRefValue(isShowYw, `$event`)
                    }
                    , "title" to "听力原文"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("rich-text", _uM("nodes" to unref(yWtext), "style" to _nS(_uM("margin" to "0 10rpx"))), null, 12, _uA(
                                "nodes"
                            ))
                        )
                    }
                    ), "_" to 1), 8, _uA(
                        "isShow"
                    ))
                ), 64)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("d_content2" to _pS(_uM("width" to "500rpx", "height" to "280rpx", "flexDirection" to "row", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "d_left" to _uM(".d_content2 " to _uM("width" to "270rpx", "height" to "100%", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF")), "top_box" to _uM(".d_content2 .d_left " to _uM("marginTop" to "12rpx", "marginRight" to 0, "marginBottom" to 0, "marginLeft" to 0, "flexDirection" to "row", "paddingTop" to 0, "paddingRight" to "11rpx", "paddingBottom" to 0, "paddingLeft" to "11rpx"), ".d_content2 .d_right " to _uM("marginTop" to "12rpx", "marginRight" to 0, "marginBottom" to 0, "marginLeft" to 0, "flexDirection" to "row", "paddingTop" to 0, "paddingRight" to "11rpx", "paddingBottom" to 0, "paddingLeft" to "11rpx")), "btn_box" to _uM(".d_content2 .d_left .top_box " to _uM("flexDirection" to "row", "alignItems" to "center", "marginRight" to "20rpx"), ".d_content2 .d_left .top_box .active" to _uM("backgroundImage" to "none", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to "100rpx", "borderTopRightRadius" to "100rpx", "borderBottomRightRadius" to "100rpx", "borderBottomLeftRadius" to "100rpx", "paddingTop" to "5rpx", "paddingRight" to "8rpx", "paddingBottom" to "5rpx", "paddingLeft" to "8rpx"), ".d_content2 .d_right .top_box " to _uM("flexDirection" to "row", "alignItems" to "center", "marginRight" to "20rpx"), ".d_content2 .d_right .top_box .active" to _uM("backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "100rpx", "borderTopRightRadius" to "100rpx", "borderBottomRightRadius" to "100rpx", "borderBottomLeftRadius" to "100rpx", "paddingTop" to "5rpx", "paddingRight" to "8rpx", "paddingBottom" to "5rpx", "paddingLeft" to "8rpx")), "t_txt" to _uM(".d_content2 .d_left " to _uM("fontWeight" to "bold", "fontSize" to "21rpx", "color" to "#030229", "lineHeight" to "21rpx")), "defen" to _uM(".d_content2 .d_left " to _uM("textAlign" to "center", "fontSize" to "9rpx", "color" to "#030229", "lineHeight" to "11rpx")), "tip-item" to _uM(".d_content2 .d_left " to _uM("flexDirection" to "row", "alignItems" to "center", "marginLeft" to "15rpx")), "tip-title" to _uM(".d_content2 .d_left .tip-item " to _uM("marginRight" to "6rpx", "fontSize" to "9rpx", "color" to "#030229", "lineHeight" to "23rpx")), "tip-content" to _uM(".d_content2 .d_left .tip-item " to _uM("width" to "27rpx", "height" to "12rpx", "backgroundImage" to "linear-gradient(180deg, #FFFFFF 0%, #DAE8FF 100%)", "backgroundColor" to "rgba(0,0,0,0)", "boxShadow" to "inset 0rpx 1rpx 1rpx 0rpx #A1ACE4", "fontSize" to "8rpx", "color" to "#E54E4E", "lineHeight" to "12rpx", "textAlign" to "center", "borderTopLeftRadius" to "4rpx", "borderTopRightRadius" to "4rpx", "borderBottomRightRadius" to "4rpx", "borderBottomLeftRadius" to "4rpx", "marginLeft" to "5rpx")), "d_right" to _uM(".d_content2 " to _uM("flex" to 1, "width" to "100%", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "height" to "100%", "borderTopLeftRadius" to 0, "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to 0)), "scroll_box" to _uM(".d_content2 .d_right " to _uM("width" to "100%", "flex" to 1, "paddingTop" to "13rpx", "paddingRight" to "13rpx", "paddingBottom" to "13rpx", "paddingLeft" to "13rpx")), "show-border" to _uM(".d_content2 .d_right .scroll_box " to _uM("borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#f0f0f0", "marginBottom" to "10rpx")), "question_text" to _uM(".d_content2 .d_right .scroll_box " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "18rpx")), "scroll_box2" to _uM(".d_content2 .d_right .scroll_box " to _uM("width" to "379rpx", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginTop" to "7rpx", "marginRight" to 0, "marginBottom" to "7rpx", "marginLeft" to 0)), "tingli" to _uM(".d_content2 .d_right .scroll_box .scroll_box2 " to _uM("fontSize" to "11rpx", "color" to "#3D3D3D", "lineHeight" to "18rpx")), "time" to _pS(_uM("position" to "absolute", "top" to "14rpx", "right" to "120rpx")), "num" to _uM(".time " to _uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D"), ".submit-content " to _uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D")), "time_box" to _uM(".time " to _uM("width" to "93.75rpx", "height" to "33.4rpx")), "_time" to _uM(".time .time_box " to _uM("width" to "62rpx", "marginLeft" to "31.75rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "fontSize" to "14rpx", "color" to "#5B77FF", "letterSpacing" to 2)), "showJx" to _pS(_uM("width" to "56rpx", "height" to "25rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "textAlign" to "center", "lineHeight" to "25rpx", "color" to "#ffffff", "fontSize" to "11.72rpx")), "showSpJx" to _pS(_uM("width" to "56rpx", "height" to "25rpx", "backgroundImage" to "linear-gradient(to bottom, #B2EA92, #439216)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "textAlign" to "center", "lineHeight" to "25rpx", "color" to "#ffffff", "fontSize" to "11.72rpx", "marginLeft" to "10rpx")), "submit-content" to _pS(_uM("position" to "absolute", "top" to "14rpx", "right" to "14rpx")), "submit_box" to _uM(".submit-content " to _uM("width" to "93.75rpx", "height" to "33.4rpx")), "_submit" to _uM(".submit-content .submit_box " to _uM("width" to "62rpx", "marginLeft" to "31.75rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "fontSize" to "14rpx", "color" to "#5B77FF", "letterSpacing" to 2)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("close" to null)
        var props = _nP(_uM("id" to _uM("type" to "Number", "required" to true, "default" to 0), "problemId" to _uM("type" to "Number", "required" to true, "default" to 0), "secondVal" to _uM("type" to "Number", "required" to true, "default" to 0), "type" to _uM("type" to "String", "required" to false, "default" to "listening"), "visible" to _uM("type" to "Boolean", "required" to true), "nowItem" to _uM("type" to "String", "required" to true, "default" to "")))
        var propsNeedCastKeys = _uA(
            "id",
            "problemId",
            "secondVal",
            "type",
            "visible",
            "nowItem"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
