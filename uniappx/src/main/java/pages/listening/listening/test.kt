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
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesListeningListeningTest : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesListeningListeningTest) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesListeningListeningTest
            val _cache = __ins.renderCache
            val ctxMp3 = createInnerAudioContext()
            ctxMp3.onCanplay(fun(_){
                ctxMp3.play()
            }
            )
            val isShowPaperSubject = ref(false)
            val isShowResult = ref(false)
            val isShowPupup = ref(false)
            val scoreVal = ref(0)
            val showType = ref(1)
            val nowResultId = ref(0)
            val isShowFullScreenResult = ref(false)
            val refMp3 = ref<UTSArray<UPlayMp3ComponentPublicInstance>?>(null)
            val problemId = ref(0)
            val _countDown = countDown()
            val config = ref(UTSJSONObject())
            val secondVal = ref(0)
            val startTime = ref("")
            val timer = ref(0)
            val rightInfo = ref("\uE600")
            val target = ref(50)
            val modelVale = ref(0)
            val title = ref("")
            val nowItem = ref<followAlongItem2?>(null)
            val nowAnswer = ref<UTSArray<UTSArray<timi>>?>(null)
            val isShowKeyboard = ref(false)
            val needShowKeyboard = ref(ucsShare.getStore("isShowKeyboard") as Boolean)
            val timeText = ref("")
            val isSentence = ref(false)
            val optionMap = ref<Map<Number, String>>(Map())
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
            val currentType = ref<String?>(null)
            val currentTypeText = computed(fun(): String {
                if (currentType.value == null) {
                    return "听力练习"
                }
                return ("题型：" + problemTypeMap.value.get(currentType.value!!)) ?: "听力练习"
            }
            )
            val currentTypeIcon = computed(fun(): String {
                if (currentType.value == null) {
                    return "/static/ico/listening/ico1.png"
                }
                return problemTypeIconMap.value.get(currentType.value!!) ?: "/static/ico/listening/ico1.png"
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
            val type = ref(0)
            val studyTaskId = ref(0)
            fun gen_makeExerciseProblemBoList_fn(): UTSArray<makeSubTop2> {
                var retArr = _uA<makeSubTop2>()
                var make = makeSubTop2(id = nowItem.value?.id ?: 0, userOption = "", userBlankAnswer = "", subProblemList = _uA<makeSub2>())
                if (currentType.value == "33") {
                    var fillter = nowAnswer.value!!.reduce(fun(acc, line: UTSArray<timi>): UTSArray<timi> {
                        return acc.concat(line.filter(fun(item): Boolean {
                            return item.type === "input"
                        }))
                    }, _uA())
                    make.id = nowItem.value!!.id
                    fillter.forEach(fun(_sub){
                        make.subProblemList?.push(makeSub2(id = _sub.id, userOption = "", userBlankAnswer = _sub.content))
                    })
                } else {
                    optionMap.value.forEach(fun(value: String, key: Number, map: Map<Number, String>){
                        make.subProblemList!!.push(makeSub2(id = key, userOption = value, userBlankAnswer = ""))
                    }
                    )
                }
                retArr.push(make)
                return retArr
            }
            val makeExerciseProblemBoList = ::gen_makeExerciseProblemBoList_fn
            val uuidMap = Map<String, Number>()
            fun gen_getUUID_fn(isSub: String, index: Number, subIndex: Number): Number {
                var mapKey = isSub + index.toString(10) + subIndex.toString(10)
                if (uuidMap.get(mapKey) == null) {
                    var timestamp = Date.now()
                    var randomNum = Math.floor(Math.random() * 100)
                    var uuid = timestamp * 100 + randomNum
                    uuidMap.set(mapKey, uuid)
                    return uuid
                } else {
                    return uuidMap.get(mapKey)!!
                }
            }
            val getUUID = ::gen_getUUID_fn
            val isPlayNum = ref(0)
            fun gen_pauseMp3_fn(src: String?) {
                isPlayNum.value++
                if (isPlayNum.value > 2) {
                    ctxMp3.src = ""
                    uni_showToast(ShowToastOptions(title = "每个音频最多播放2次", icon = "none"))
                    return
                }
                ctxMp3.src = src ?: ""
            }
            val pauseMp3 = ::gen_pauseMp3_fn
            fun gen_submit_fn() {
                _countDown.clear()
                ctxMp3.pause()
                if (refMp3.value != null) {
                    refMp3.value!!.forEach(fun(item, _index){
                        item?.endPlay()
                    }
                    )
                }
                isShowKeyboard.value = false
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/problem/api/exerciseSubmit"), method = "POST", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var type = type.value
                    var textbookUnitId = getTextbookUnitId()
                    var module = getModelKey("同步口语听力")
                    var subModule = getSubModelKey("听力训练")
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
                    isShowResult.value = true
                    nowResultId.value = responseData.data?.getNumber("id")!!
                    console.log(responseData.data?.getNumber("id")!!)
                    clearInterval(timer.value)
                    _countDown.clear()
                    scoreVal.value = parseFloat(responseData.data?.getString("score")!!)
                    if (responseData.data?.getString("isPass")!! == "0") {
                        showType.value = 1
                    } else {
                        showType.value = 2
                        uni_setStorageSync("studyTaskEnd", 1)
                    }
                    isShowPupup.value = true
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
                    var sub = nowItem.value?.subProblemList
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
            fun gen_inputClick_fn() {
                if (needShowKeyboard.value) {
                    isShowKeyboard.value = true
                }
            }
            val inputClick = ::gen_inputClick_fn
            fun gen_closeFullScreenResult_fn() {
                isShowFullScreenResult.value = false
            }
            val closeFullScreenResult = ::gen_closeFullScreenResult_fn
            fun gen_changeInput_fn(ev: Any, index: Number, subIndex: Number) {
                if (nowAnswer.value?.length == 0) {
                    return
                }
                nowAnswer.value!![index]!![subIndex].content = ev as String
            }
            val changeInput = ::gen_changeInput_fn
            fun gen_onChoiceSelect_fn(value: String, index: Number, pIndex: Number) {
                console.log("选择了题目类型：", value, "索引：", index, "题目索引", pIndex)
                optionMap.value.set(nowItem.value!!.subProblemList!![pIndex].id, value)
            }
            val onChoiceSelect = ::gen_onChoiceSelect_fn
            val subProblemCount = ref(0)
            onLoad(fun(ev){
                config.value = getConfig("听力训练配置")
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
                    if (currentType.value == "33") {
                        nowAnswer.value = processRichText(JSON.parse<examinationList>(JSON.stringify(testItem) ?: "")!!)
                    } else {
                        nowItem.value!!.subProblemList?.forEach(fun(item, index){
                            optionMap.value.set(item.id, "")
                        }
                        )
                    }
                    if (currentType.value == "30") {
                        subProblemCount.value = nowItem.value?.subProblemList?.length ?: 0
                    }
                }
            }
            )
            onUnmounted(fun(){
                clearInterval(timer.value)
                _countDown.clear()
                ctxMp3.pause()
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
                val _component_ListeningChoice = resolveEasyComponent("ListeningChoice", GenComponentsListeningChoiceListeningChoiceClass)
                val _component_wj_input = resolveEasyComponent("wj-input", GenUniModulesWjInputComponentsWjInputWjInputClass)
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_virtualKeyboard = resolveEasyComponent("virtualKeyboard", GenComponentsVirtualKeyboardVirtualKeyboardClass)
                val _component_paperSubject = resolveEasyComponent("paperSubject", GenComponentsPaperSubjectPaperSubjectClass)
                val _component_examinationPopup = resolveEasyComponent("examinationPopup", GenComponentsExaminationPopupExaminationPopupClass)
                val _component_fullScreenResult = resolveEasyComponent("fullScreenResult", GenComponentsFullScreenResultFullScreenResultClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "听力训练")),
                        if (isTrue(!unref(isShowFullScreenResult))) {
                            _cE(Fragment, _uM("key" to 0), _uA(
                                _cE("view", _uM("class" to "d_content"), _uA(
                                    if (isTrue(unref(currentType) == "31" || unref(currentType) == "32")) {
                                        _cE("view", _uM("key" to 0, "class" to "d_left"), _uA(
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
                                            _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1", "margin" to "12rpx 19rpx"))), _uA(
                                                _cE("view", null, _uA(
                                                    _cE("rich-text", _uM("nodes" to (unref(nowItem)?.questionContent ?: "")), null, 8, _uA(
                                                        "nodes"
                                                    ))
                                                ))
                                            ), 4),
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(nowItem)?.soundInfoList, fun(item, __key, __index, _cached): Any {
                                                return _cE("view", _uM("style" to _nS(_uM("margin" to "0 10rpx 10rpx"))), _uA(
                                                    if (item?.soundUrl != "") {
                                                        _cE("view", _uM("key" to 0), _uA(
                                                            _cV(_component_u_playMp3, _uM("style" to _nS(_uM("align-items" to "flex-start")), "src" to item?.soundUrl, "tplType" to 6, "isPlayNum" to true, "maxPlayNum" to 2), null, 8, _uA(
                                                                "style",
                                                                "src"
                                                            ))
                                                        ))
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ), 4)
                                            }), 256)
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    _cE("view", _uM("class" to "d_right"), _uA(
                                        if (isTrue(unref(currentType) == "30" || unref(currentType) == "33")) {
                                            _cE(Fragment, _uM("key" to 0), _uA(
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
                                                if (unref(currentType) == "30") {
                                                    _cE("view", _uM("key" to 0, "style" to _nS(_uM("margin" to "12rpx 0 0 11rpx"))), _uA(
                                                        _cE("rich-text", _uM("nodes" to (unref(nowItem)?.questionContent ?: "")), null, 8, _uA(
                                                            "nodes"
                                                        ))
                                                    ), 4)
                                                } else {
                                                    _cC("v-if", true)
                                                }
                                            ), 64)
                                        } else {
                                            _cC("v-if", true)
                                        },
                                        _cE("scroll-view", _uM("direction" to "vertical", "class" to "scroll_box"), _uA(
                                            if (unref(currentType) == "30") {
                                                _cE("view", _uM("key" to 0, "style" to _nS(_uM("padding-bottom" to "-10rpx"))), _uA(
                                                    _cE(Fragment, null, RenderHelpers.renderList(unref(nowItem)?.subProblemList, fun(item, index, __index, _cached): Any {
                                                        return _cE("view", _uM("style" to _nS(_uM("margin-bottom" to "10rpx"))), _uA(
                                                            _cE("text", _uM("class" to "question_text"), _tD(index + 1) + ". " + _tD(item?.questionContent), 1),
                                                            _cE("view", _uM("style" to _nS(_uM("align-items" to "flex-start"))), _uA(
                                                                _cE(Fragment, null, RenderHelpers.renderList(item?.soundInfoList, fun(mp3, subIndex, __index, _cached): Any {
                                                                    return _cE(Fragment, null, _uA(
                                                                        if ((mp3?.soundUrl ?: "") != "") {
                                                                            _cE("view", _uM("key" to 0, "style" to _nS(_uM("margin" to "6rpx 0"))), _uA(
                                                                                _cV(_component_u_playMp3, _uM("tplType" to 4, "ref_for" to true, "ref_key" to "refMp3", "ref" to refMp3, "src" to mp3?.soundUrl, "isPlayNum" to true, "maxPlayNum" to 2), null, 8, _uA(
                                                                                    "src"
                                                                                ))
                                                                            ), 4)
                                                                        } else {
                                                                            _cC("v-if", true)
                                                                        }
                                                                    ), 64)
                                                                }), 256)
                                                            ), 4),
                                                            _cV(_component_ListeningChoice, _uM("onSelect" to onChoiceSelect, "options" to item?.options, "index" to index), null, 8, _uA(
                                                                "options",
                                                                "index"
                                                            ))
                                                        ), 4)
                                                    }), 256)
                                                ), 4)
                                            } else {
                                                _cC("v-if", true)
                                            },
                                            if (isTrue(unref(currentType) == "31" || unref(currentType) == "32")) {
                                                _cE("view", _uM("key" to 1, "style" to _nS(_uM("padding-bottom" to "-10rpx"))), _uA(
                                                    _cE(Fragment, null, RenderHelpers.renderList(unref(nowItem)?.subProblemList, fun(item, index, __index, _cached): Any {
                                                        return _cE("view", _uM("style" to _nS(_uM("margin-bottom" to "10rpx"))), _uA(
                                                            _cE("text", _uM("class" to "question_text"), _tD(index + 1) + ". " + _tD(item?.questionContent), 1),
                                                            _cV(_component_ListeningChoice, _uM("onSelect" to onChoiceSelect, "options" to item?.options, "index" to index), null, 8, _uA(
                                                                "options",
                                                                "index"
                                                            ))
                                                        ), 4)
                                                    }), 256)
                                                ), 4)
                                            } else {
                                                _cC("v-if", true)
                                            },
                                            if (unref(currentType) == "33") {
                                                _cE("view", _uM("key" to 2, "id" to "tranBox"), _uA(
                                                    _cE(Fragment, null, RenderHelpers.renderList(unref(nowAnswer), fun(item, index, __index, _cached): Any {
                                                        return _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "flex-wrap" to "wrap"))), _uA(
                                                            _cE("view", _uM("style" to _nS(_uM("width" to "20rpx"))), null, 4),
                                                            _cE(Fragment, null, RenderHelpers.renderList((item as UTSArray<timi>), fun(inps, ssubIndex, __index, _cached): Any {
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
                                                                                changeInput(`$event`, index, ssubIndex)
                                                                            }, "onInputTap" to inputClick, "isAcquisition" to true, "onTranY" to tranY), null, 8, _uA(
                                                                                "style",
                                                                                "text",
                                                                                "onUpdate:text",
                                                                                "onInput"
                                                                            )),
                                                                            _cE("text", _uM("style" to _nS(_uM("color" to "transparent", "margin" to "0 10rpx", "font-size" to "26rpx"))), _tD(inps.content), 5)
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
                                                ))
                                            } else {
                                                _cC("v-if", true)
                                            }
                                        )),
                                        if (unref(currentType) == "33") {
                                            _cE(Fragment, _uM("key" to 1), RenderHelpers.renderList(unref(nowItem)?.soundInfoList, fun(item, __key, __index, _cached): Any {
                                                return _cE("view", _uM("style" to _nS(_uM("margin" to "0 10rpx 10rpx"))), _uA(
                                                    if (item?.soundUrl != "") {
                                                        _cE("view", _uM("key" to 0), _uA(
                                                            _cV(_component_u_playMp3, _uM("style" to _nS(_uM("align-items" to "flex-start")), "src" to item?.soundUrl, "tplType" to 4, "isPlayNum" to true, "maxPlayNum" to 2, "ref_for" to true, "ref_key" to "refMp3", "ref" to refMp3), null, 8, _uA(
                                                                "style",
                                                                "src"
                                                            ))
                                                        ))
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ), 4)
                                            }), 256)
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
                                    }), "_" to 1))
                                )),
                                _cE("view", _uM("class" to "submit-content", "onClick" to fun(){
                                    isShowPaperSubject.value = true
                                }), _uA(
                                    _cV(_component_BackgroundImage, _uM("src" to "/static/ico/sub.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "submit_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("text", _uM("class" to "_submit"), " 交卷 ")
                                        )
                                    }), "_" to 1))
                                ), 8, _uA(
                                    "onClick"
                                ))
                            ), 64)
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
                        ,
                        _cV(_component_paperSubject, _uM("isShow" to unref(isShowPaperSubject), "onUpdate:isShow" to fun(`$event`: Boolean){
                            trySetRefValue(isShowPaperSubject, `$event`)
                        }
                        , "onOk" to submit), null, 8, _uA(
                            "isShow"
                        )),
                        _cV(_component_examinationPopup, _uM("isShowResult" to unref(isShowFullScreenResult), "onUpdate:isShowResult" to fun(`$event`: Boolean){
                            trySetRefValue(isShowFullScreenResult, `$event`)
                        }
                        , "show" to unref(isShowPupup), "onUpdate:show" to fun(`$event`: Boolean){
                            trySetRefValue(isShowPupup, `$event`)
                        }
                        , "scoreVal" to unref(scoreVal), "type" to unref(showType)), null, 8, _uA(
                            "isShowResult",
                            "show",
                            "scoreVal",
                            "type"
                        )),
                        _cV(_component_fullScreenResult, _uM("id" to unref(nowResultId), "problemId" to unref(problemId), "secondVal" to unref(secondVal), "type" to "listening", "visible" to unref(isShowFullScreenResult), "onClose" to closeFullScreenResult), null, 8, _uA(
                            "id",
                            "problemId",
                            "secondVal",
                            "visible"
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "flexDirection" to "row", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13.48rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "d_left" to _uM(".d_content " to _uM("width" to "293rpx", "height" to "100%", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "0rpx", "borderBottomRightRadius" to "0rpx", "borderBottomLeftRadius" to "18rpx")), "top_box" to _uM(".d_content .d_left " to _uM("marginTop" to "12rpx", "marginRight" to 0, "marginBottom" to 0, "marginLeft" to 0, "flexDirection" to "row", "paddingTop" to 0, "paddingRight" to "11rpx", "paddingBottom" to 0, "paddingLeft" to "11rpx"), ".d_content .d_right " to _uM("marginTop" to "12rpx", "marginRight" to 0, "marginBottom" to 0, "marginLeft" to 0, "flexDirection" to "row", "paddingTop" to 0, "paddingRight" to "11rpx", "paddingBottom" to 0, "paddingLeft" to "11rpx")), "btn_box" to _uM(".d_content .d_left .top_box " to _uM("flexDirection" to "row", "alignItems" to "center", "marginRight" to "20rpx"), ".d_content .d_left .top_box .active" to _uM("backgroundImage" to "none", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to "100rpx", "borderTopRightRadius" to "100rpx", "borderBottomRightRadius" to "100rpx", "borderBottomLeftRadius" to "100rpx", "paddingTop" to "5rpx", "paddingRight" to "8rpx", "paddingBottom" to "5rpx", "paddingLeft" to "8rpx"), ".d_content .d_right .top_box " to _uM("flexDirection" to "row", "alignItems" to "center", "marginRight" to "20rpx"), ".d_content .d_right .top_box .active" to _uM("backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "100rpx", "borderTopRightRadius" to "100rpx", "borderBottomRightRadius" to "100rpx", "borderBottomLeftRadius" to "100rpx", "paddingTop" to "5rpx", "paddingRight" to "8rpx", "paddingBottom" to "5rpx", "paddingLeft" to "8rpx")), "t_txt" to _uM(".d_content .d_left " to _uM("fontWeight" to "bold", "fontSize" to "21rpx", "color" to "#030229", "lineHeight" to "21rpx")), "defen" to _uM(".d_content .d_left " to _uM("textAlign" to "center", "fontSize" to "9rpx", "color" to "#030229", "lineHeight" to "11rpx")), "tip-item" to _uM(".d_content .d_left " to _uM("flexDirection" to "row", "alignItems" to "center", "marginLeft" to "15rpx")), "tip-title" to _uM(".d_content .d_left .tip-item " to _uM("marginRight" to "6rpx", "fontSize" to "9rpx", "color" to "#030229", "lineHeight" to "23rpx")), "tip-content" to _uM(".d_content .d_left .tip-item " to _uM("width" to "27rpx", "height" to "12rpx", "backgroundImage" to "linear-gradient(180deg, #FFFFFF 0%, #DAE8FF 100%)", "backgroundColor" to "rgba(0,0,0,0)", "boxShadow" to "inset 0rpx 1rpx 1rpx 0rpx #A1ACE4", "fontSize" to "8rpx", "color" to "#E54E4E", "lineHeight" to "12rpx", "textAlign" to "center", "borderTopLeftRadius" to "4rpx", "borderTopRightRadius" to "4rpx", "borderBottomRightRadius" to "4rpx", "borderBottomLeftRadius" to "4rpx", "marginLeft" to "5rpx")), "d_right" to _uM(".d_content " to _uM("flex" to 1, "width" to "100%", "height" to "100%")), "scroll_box" to _uM(".d_content .d_right " to _uM("width" to "100%", "flex" to 1, "paddingTop" to "13rpx", "paddingRight" to "13rpx", "paddingBottom" to "13rpx", "paddingLeft" to "13rpx")), "question_text" to _uM(".d_content .d_right .scroll_box " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "18rpx")), "time" to _pS(_uM("position" to "absolute", "top" to "14rpx", "right" to "120rpx")), "num" to _uM(".time " to _uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D"), ".submit-content " to _uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D")), "time_box" to _uM(".time " to _uM("width" to "93.75rpx", "height" to "33.4rpx")), "_time" to _uM(".time .time_box " to _uM("width" to "62rpx", "marginLeft" to "31.75rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "fontSize" to "14rpx", "color" to "#5B77FF", "letterSpacing" to 2)), "submit-content" to _pS(_uM("position" to "absolute", "top" to "14rpx", "right" to "14rpx")), "submit_box" to _uM(".submit-content " to _uM("width" to "93.75rpx", "height" to "33.4rpx")), "_submit" to _uM(".submit-content .submit_box " to _uM("width" to "62rpx", "marginLeft" to "25.75rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "fontSize" to "14rpx", "color" to "#5B77FF", "letterSpacing" to 2)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
