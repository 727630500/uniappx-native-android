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
import uts.sdk.modules.uniPreviewImage.PreviewImageOptions as PreviewImageOptions1
import io.dcloud.uniapp.extapi.getElementById as uni_getElementById
import uts.sdk.modules.uniPreviewImage.previewImage as uni_previewImage
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesTestPaperTestPaper : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTestPaperTestPaper) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTestPaperTestPaper
            val _cache = __ins.renderCache
            var examinationIteratorFn = examinationIterator()
            val nowSubArr = ref<UTSArray<timi>?>(null)
            val answerList = ref(_uA<UTSArray<UTSArray<answerItemType>>>(_uA(
                _uA()
            )))
            val fenArr = ref(_uA<UTSArray<examinationList>>(_uA()))
            val nowAnswer = ref<UTSArray<answerItemType>?>(null)
            val unitTitle = ref("")
            val tempIndexArr = ref(_uA<String>())
            val timeText = ref("00:00")
            val startTime = ref("")
            val timer = ref(0)
            val _countDown = countDown()
            val isShowPaperSubject = ref(false)
            fun gen_previewImage_fn(img: String) {
                var list = _uA<String>()
                list.push(img)
                uni_previewImage(PreviewImageOptions1(urls = list, loop = false, fail = fun(e) {
                    console.log(e)
                }
                ))
            }
            val previewImage = ::gen_previewImage_fn
            val scoreVal = ref(0)
            val isShowPupup = ref(false)
            val showType = ref(1)
            val secondVal = ref(0)
            val nowIndex = ref("")
            val nowSub = ref<examinationList?>(null)
            val isShowKey = ref(true)
            val tempShow = ref(true)
            val isShowKeyboard = ref(false)
            val exerciseNum = ref(0)
            val topicNum = computed(fun(): Number {
                var _num: Number = 0
                fenArr.value.forEach(fun(arr2){
                    arr2.forEach(fun(i){
                        _num++
                    }
                    )
                }
                )
                return _num
            }
            )
            val selectedOption = ref(_uA<Number>())
            fun gen_detectionHtml_fn(text: String?): String {
                if (text == null) {
                    return ""
                }
                val underlineRegex = UTSRegExp("<[^>]+>", "i")
                val matches = text.match(underlineRegex) ?: _uA()
                if (matches.length > 0) {
                    return matches.join("")
                }
                return ""
            }
            val detectionHtml = ::gen_detectionHtml_fn
            val isShowResult = ref(false)
            val nowResultId = ref(0)
            val timeSeconds = ref(0)
            val nowSubIndexMap = ref(Map<Number, Number>())
            val songMap = ref(Map<Number, answerMapType>())
            val getNowSubIndex = computed(fun(): Number {
                return nowSubIndexMap.value.get(nowSub.value?.id ?: 0) ?: 0
            }
            )
            fun setNowSubIndexFn(index: Number = 0) {
                nowSubIndexMap.value.set(nowSub.value?.id ?: 0, index)
            }
            fun setSongMapFn(map: answerMapType, id: Number = 0) {
                songMap.value.set(id ?: 0, map)
                tempIndexArr.value.push(nowIndex.value)
            }
            fun gen_switch1Change_fn(e: UniSwitchChangeEvent) {
                isShowKey.value = e.detail.value
            }
            val switch1Change = ::gen_switch1Change_fn
            val needShowKeyboard = ref(ucsShare.getStore("isShowKeyboard") as Boolean)
            fun gen_inputClick_fn() {
                if (needShowKeyboard.value) {
                    isShowKeyboard.value = true
                }
            }
            val inputClick = ::gen_inputClick_fn
            fun gen_getItemNumStatus_fn(index: Number): String {
                var allNum = fenArr.value[index].length
                var makeNum: Number = 0
                answerList.value[index].forEach(fun(item){
                    var isMake = false
                    item.forEach(fun(sub){
                        if (isMake != true) {
                            if (sub.input == null && sub.options.length == 0) {
                                var fillter = item.filter(fun(f): Boolean {
                                    return f.content != ""
                                }
                                )
                                if (fillter.length > 0) {
                                    isMake = true
                                }
                            }
                            if (sub.input != null) {
                                var fillter = sub.input!!.reduce(fun(acc, line: UTSArray<timi>): UTSArray<timi> {
                                    return acc.concat(line.filter(fun(f): Boolean {
                                        return f.type == "input" && f.content != ""
                                    }
                                    ))
                                }
                                , _uA())
                                if (fillter.length > 0) {
                                    isMake = true
                                }
                            }
                            if (sub.input == null && sub.options.length != 0) {
                                isMake = sub.isCheck
                            }
                        }
                    }
                    )
                    if (isMake) {
                        makeNum++
                    }
                }
                )
                return "" + makeNum.toString(10) + " / " + allNum.toString(10)
            }
            val getItemNumStatus = ::gen_getItemNumStatus_fn
            fun gen_changeInput_fn(ev: Any, index: Number, subIndex: Number, ssubIndex: Number) {
                nowAnswer.value!![index].input!![subIndex][ssubIndex].content = ev as String
                tempIndexArr.value.push(nowIndex.value)
            }
            val changeInput = ::gen_changeInput_fn
            examinationIteratorFn.change(fun(list){
                answerList.value = list
                var _nowIndex = nowIndex.value
                if (_nowIndex == "") {
                    return
                }
                var valArr = _nowIndex.split("-")
                nowSub.value = fenArr.value[parseInt(valArr[0])][parseInt(valArr[1])]
                nowAnswer.value = answerList.value[parseInt(valArr[0])][parseInt(valArr[1])]
            }
            )
            watch(nowIndex, fun(kVal: String){
                if (kVal == "") {
                    return
                }
                isShowKeyboard.value = false
                var valArr = kVal.split("-")
                nowSub.value = fenArr.value[parseInt(valArr[0])][parseInt(valArr[1])]
                nowAnswer.value = answerList.value[parseInt(valArr[0])][parseInt(valArr[1])]
                selectedOption.value = answerList.value[parseInt(valArr[0])][parseInt(valArr[1])].map(fun(item): Number {
                    return item.options.findIndex(fun(item): Boolean {
                        return item.isCheck
                    }
                    )
                }
                )
            }
            )
            fun gen_selectedOptionFn_fn(kVal: Number, subIndex: Number) {
                if (kVal == -1) {
                    return
                }
                var _nowItem = nowSub.value
                if (_nowItem != null) {
                    var _options = _nowItem.options as UTSArray<Options>?
                    if (_options != null) {
                        var valArr = nowIndex.value.split("-")
                        answerList.value[parseInt(valArr[0])][parseInt(valArr[1])][subIndex].options.forEach(fun(opt){
                            opt.isCheck = false
                        }
                        )
                        answerList.value[parseInt(valArr[0])][parseInt(valArr[1])][subIndex].options[kVal].isCheck = true
                        console.log(answerList.value[parseInt(valArr[0])][parseInt(valArr[1])][subIndex])
                        tempIndexArr.value.push(nowIndex.value)
                    }
                }
            }
            val selectedOptionFn = ::gen_selectedOptionFn_fn
            val tempPath = ref<String>("")
            fun gen_getProblemType_fn(type: UTSArray<examinationList>?): String {
                if (type == null) {
                    return ""
                }
                val typeMap: UTSJSONObject = object : UTSJSONObject() {
                    var `14` = "选词填空"
                    var `15` = "语法填空"
                    var `16` = "完形填空"
                    var `17` = "传统阅读"
                    var `18` = "六选五"
                }
                if (type.length == 0) {
                    return ""
                }
                val mapStr = typeMap.getString(type[0].problemType)
                return if (mapStr == null) {
                    "未知类型"
                } else {
                    mapStr
                }
            }
            val getProblemType = ::gen_getProblemType_fn
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
            fun gen_makeExerciseProblemBoList_fn(): UTSArray<makeSubTop5> {
                var retArr = _uA<makeSubTop5>()
                answerList.value.forEach(fun(item){
                    item.forEach(fun(_content){
                        if (!_uA(
                            "10",
                            "11",
                            "12",
                            "13",
                            "35"
                        ).includes(_content[0].problemType)) {
                            var make = makeSubTop5(id = 0, userOption = "", userBlankAnswer = "", subProblemList = _uA<makeSub3>())
                            _content.forEach(fun(sub){
                                if (sub.input == null && sub.options.length == 0) {
                                    make.id = sub.parentId
                                    make.subProblemList?.push(makeSub3(id = sub.id, userOption = "", userBlankAnswer = sub.content, temp = songMap.value.get(sub.id)?.temp, adopt = songMap.value.get(sub.id)?.adopt, userSpeakUrl = songMap.value.get(sub.id)?.url, isPass = songMap.value.get(sub.id)?.isPass))
                                }
                                if (sub.input != null) {
                                    var fillter = sub.input!!.reduce(fun(acc, line: UTSArray<timi>): UTSArray<timi> {
                                        return acc.concat(line.filter(fun(item): Boolean {
                                            return item.type === "input"
                                        }
                                        ))
                                    }
                                    , _uA())
                                    make.id = sub.id
                                    fillter.forEach(fun(_sub){
                                        make.subProblemList?.push(makeSub3(id = _sub.id, userOption = "", userBlankAnswer = _sub.content))
                                    }
                                    )
                                }
                                if (sub.input == null && sub.options.length != 0) {
                                    var fillter = sub.options.filter(fun(f): Boolean {
                                        return f.isCheck
                                    }
                                    )
                                    make.id = sub.parentId
                                    fillter.forEach(fun(_sub){
                                        make.subProblemList?.push(makeSub3(id = _sub.id, userOption = _sub.option, userBlankAnswer = ""))
                                    }
                                    )
                                    if (fillter.length == 0) {
                                        make.subProblemList?.push(makeSub3(id = sub.options[0].id, userOption = "", userBlankAnswer = ""))
                                    }
                                }
                            })
                            retArr.push(make)
                        } else {
                            _content.forEach(fun(sub){
                                if (sub.input != null) {
                                    var fillter = sub.input!!.reduce(fun(acc, line: UTSArray<timi>): UTSArray<timi> {
                                        return acc.concat(line.filter(fun(item): Boolean {
                                            return item.type === "input"
                                        }
                                        ))
                                    }
                                    , _uA())
                                    fillter.forEach(fun(_sub){
                                        var _index = retArr.findIndex(fun(item): Boolean {
                                            return item.id == _sub.id
                                        }
                                        )
                                        if (_index == -1) {
                                            retArr.push(makeSubTop5(id = _sub.id, userOption = "", userBlankAnswer = _sub.content))
                                        } else {
                                            var _textArr = retArr[_index].userBlankAnswer.split("|")
                                            _textArr.push(_sub.content)
                                            retArr[_index].userBlankAnswer = _textArr.join("|")
                                        }
                                    }
                                    )
                                }
                                if (sub.input == null && sub.options.length == 0) {
                                    retArr.push(makeSubTop5(id = sub.id, userOption = "", userBlankAnswer = sub.content, temp = songMap.value.get(sub.id)?.temp, adopt = songMap.value.get(sub.id)?.adopt, userSpeakUrl = songMap.value.get(sub.id)?.url, isPass = songMap.value.get(sub.id)?.isPass))
                                } else {
                                    var fillter = sub.options.filter(fun(f): Boolean {
                                        return f.isCheck
                                    }
                                    )
                                    fillter.forEach(fun(_sub){
                                        retArr.push(makeSubTop5(id = sub.id, userOption = _sub.option, userBlankAnswer = ""))
                                    }
                                    )
                                    if (fillter.length == 0) {
                                        retArr.push(makeSubTop5(id = sub.id, userOption = "", userBlankAnswer = ""))
                                    }
                                }
                            }
                            )
                        }
                    }
                    )
                }
                )
                return retArr
            }
            val makeExerciseProblemBoList = ::gen_makeExerciseProblemBoList_fn
            val testPaperId = ref(0)
            val type = ref("0")
            fun gen_submit_fn() {
                console.log(makeExerciseProblemBoList())
                clearInterval(timer.value)
                _countDown.clear()
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/problem/api/exerciseSubmit"), method = "POST", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var type = type.value
                    var exerciseProblemBoList = makeExerciseProblemBoList()
                    var startTime = startTime.value
                    var second = secondVal.value
                    var testPaperId = testPaperId.value
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    isShowResult.value = true
                    nowResultId.value = responseData.data?.getNumber("id") ?: 0
                    console.log(nowResultId.value)
                    if (responseData.data?.getString("isPass")!! == "0") {
                        showType.value = 1
                    } else {
                        showType.value = 2
                        uni_setStorageSync("studyTaskEnd", 1)
                    }
                    isShowPupup.value = true
                    scoreVal.value = parseInt(responseData.data?.getString("score") ?: "0")
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val submit = ::gen_submit_fn
            fun gen_startCountdown_fn() {
                _countDown.startCountdown(timeSeconds.value * 60)
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
            fun gen_getSubjectStudyTask_fn() {
                uni_removeStorageSync("maxMap")
                uni_request<Result<UTSArray<examinationList>>>(RequestOptions(url = getUrl("/biz/problem/api/studyTask/list"), method = "GET", data = object : UTSJSONObject() {
                    var type = "20"
                    var testPaperId = testPaperId.value
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    var arr = examinationGroup(responseData.data!!, true)
                    fenArr.value = arr
                    examinationIteratorFn.putArr(arr)
                    answerList.value = examinationIteratorFn.getAnswerLIst()
                    answerList.value.forEach(fun(item){
                        item.forEach(fun(item2){
                            item2.forEach(fun(content){
                                if (_uA(
                                    "34",
                                    "35"
                                ).includes(content.problemType)) {
                                    var adoptObj = adopt(score = 0, complete = 0, accurate = 0, fluent = 0)
                                    var arr = content.soundInfoList?.get(0)?.originalText?.split(" ")
                                    var analysisArr = arr?.map(fun(arrItem: String): analysis {
                                        return analysis(content = arrItem, total_score = 0, dp_message = 16)
                                    }
                                    )
                                    songMap.value.set(content.id, answerMapType(adopt = adoptObj, temp = analysisArr as UTSArray<analysis>, url = "", isPass = "0"))
                                }
                            }
                            )
                        }
                        )
                    }
                    )
                    nowIndex.value = "0-0"
                    startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                    startCountdown()
                    timer.value = setInterval(fun(){
                        secondVal.value++
                    }
                    , 1000)
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getSubjectStudyTask = ::gen_getSubjectStudyTask_fn
            onLoad(fun(event: OnLoadOptions){
                unitTitle.value = event["unitTitle"] ?: ""
                type.value = event["type"] ?: "0"
                testPaperId.value = parseInt(event["testPaperId"] ?: "0")
                timeSeconds.value = parseInt(event["time"] ?: "0")
                getSubjectStudyTask()
            }
            )
            onUnmounted(fun(){
                clearInterval(timer.value)
                _countDown.clear()
                uni_removeStorageSync("maxMap")
            }
            )
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_dTitle = resolveEasyComponent("dTitle", GenComponentsDTitleDTitleClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_switch = resolveComponent("switch")
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_wj_selectText = resolveEasyComponent("wj-selectText", GenUniModulesWjSelectTextComponentsWjSelectTextWjSelectTextClass)
                val _component_wj_input = resolveEasyComponent("wj-input", GenUniModulesWjInputComponentsWjInputWjInputClass)
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_QuestionOptionsTest = resolveEasyComponent("QuestionOptionsTest", GenComponentsQuestionOptionsTestQuestionOptionsTestClass)
                val _component_paperSubject = resolveEasyComponent("paperSubject", GenComponentsPaperSubjectPaperSubjectClass)
                val _component_examinationPopup = resolveEasyComponent("examinationPopup", GenComponentsExaminationPopupExaminationPopupClass)
                val _component_readResult = resolveEasyComponent("readResult", GenComponentsReadResultReadResultClass)
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
                        if (isTrue(!unref(isShowResult))) {
                            _cE("view", _uM("key" to 0, "class" to "d_content"), _uA(
                                _cE("view", null, _uA(
                                    _cE("view", _uM("style" to _nS(_uM("background" to "rgba(255, 255, 255, 0.2)", "padding" to "5.86rpx 0 7.03rpx"))), _uA(
                                        _cE("text", _uM("style" to _nS(_uM("color" to "#DEE9FF", "font-size" to "12rpx", "line-height" to "18rpx", "text-align" to "center"))), "共" + _tD(unref(topicNum)) + "个题目", 5)
                                    ), 4),
                                    _cE("scroll-view", _uM("direction" to "vertical", "class" to "left_nav"), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(fenArr), fun(item, index, __index, _cached): Any {
                                            return _cE("view", null, _uA(
                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                    if (getProblemType(item) != "未知类型") {
                                                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#fff", "line-height" to "18rpx"))), _tD(index + 1) + "、" + _tD(getProblemType(item)), 5),
                                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#DEE9FF", "line-height" to "18rpx"))), _tD(getItemNumStatus(index)), 5)
                                                        ), 4)
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ), 4),
                                                _cE("view", _uM("style" to _nS(_uM("flex-wrap" to "wrap", "flex-direction" to "row"))), _uA(
                                                    _cE(Fragment, null, RenderHelpers.renderList(item, fun(subItem, index2, __index, _cached): Any {
                                                        return _cE("view", _uM("style" to _nS(_uM("width" to "25%", "align-items" to "center", "justify-content" to "center")), "onClick" to fun(){
                                                            nowIndex.value = index.toString(10) + "-" + index2.toString(10)
                                                        }), _uA(
                                                            if (isTrue(unref(tempIndexArr).includes(index.toString(10) + "-" + index2.toString(10)) && unref(nowIndex) != index.toString(10) + "-" + index2.toString(10))) {
                                                                _cE("text", _uM("key" to 0, "class" to "item item_complete"), _tD(index2 + 1), 1)
                                                            } else {
                                                                if (unref(nowIndex) == index.toString(10) + "-" + index2.toString(10)) {
                                                                    _cE("text", _uM("key" to 1, "class" to "item item_now"), _tD(index2 + 1), 1)
                                                                } else {
                                                                    _cE("text", _uM("key" to 2, "class" to "item"), _tD(index2 + 1), 1)
                                                                }
                                                            }
                                                        ), 12, _uA(
                                                            "onClick"
                                                        ))
                                                    }), 256)
                                                ), 4)
                                            ))
                                        }), 256)
                                    )),
                                    _cE("view", _uM("style" to _nS(_uM("background" to "rgba(255, 255, 255, 0.2)", "align-items" to "center", "padding-bottom" to "10rpx"))), _uA(
                                        _cE("view", _uM("class" to "keyboard-switch"), _uA(
                                            _cE("image", _uM("src" to "/static/ico/keyboard_ico.png", "mode" to "", "style" to _nS(_uM("width" to "22.85rpx", "height" to "15.23rpx"))), null, 4),
                                            _cV(_component_switch, _uM("checked" to unref(isShowKey), "onChange" to switch1Change, "color" to "#3A58EB", "style" to _nS(_uM("transform" to "scale(0.7)"))), null, 8, _uA(
                                                "checked",
                                                "style"
                                            ))
                                        ))
                                    ), 4)
                                )),
                                _cE("view", _uM("class" to "right_content"), _uA(
                                    _cE("view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
                                        _cE("view", _uM("class" to "time"), _uA(
                                            _cV(_component_BackgroundImage, _uM("src" to "/static/ico/time.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "time_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                return _uA(
                                                    _cE("text", _uM("class" to "_time"), _tD(unref(timeText)), 1)
                                                )
                                            }), "_" to 1))
                                        )),
                                        _cE("view", _uM("class" to "read-test"), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
                                                if (isTrue(!_uA(
                                                    "34",
                                                    "35"
                                                ).includes(unref(nowSub)?.problemType ?: ""))) {
                                                    _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex" to "1")), "id" to "tranBox"), _uA(
                                                        if (isTrue(!_uA(
                                                            "30",
                                                            "32",
                                                            "31"
                                                        ).includes(unref(nowSub)?.problemType ?: ""))) {
                                                            _cE("view", _uM("key" to 0, "style" to _nS("" + (if (_uA(
                                                                "14",
                                                                "15",
                                                                "16",
                                                                "17",
                                                                "18"
                                                            ).includes(unref(nowSub)?.problemType ?: "")) {
                                                                ""
                                                            } else {
                                                                "margin-right: 100rpx;"
                                                            }) + "height:100%")), _uA(
                                                                if (isTrue(_uA(
                                                                    "14",
                                                                    "15",
                                                                    "16",
                                                                    "17",
                                                                    "18",
                                                                    "33"
                                                                ).includes(unref(nowSub)?.problemType ?: ""))) {
                                                                    _cE("view", _uM("key" to 0, "style" to _nS(_uM("width" to "100%", "flex-wrap" to "wrap", "flex-direction" to "row", "height" to "100%"))), _uA(
                                                                        _cE("view", _uM("style" to _nS(_uM("width" to "100%", "height" to "100%"))), _uA(
                                                                            if (isTrue(!_uA(
                                                                                "14",
                                                                                "15",
                                                                                "18",
                                                                                "33"
                                                                            ).includes(unref(nowSub)?.problemType ?: ""))) {
                                                                                _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex" to "1"))), _uA(
                                                                                    _cV(_component_wj_selectText, _uM("style" to _nS(_uM("height" to "100%")), "center" to "center", "text" to unref(nowSub)?.questionContent), null, 8, _uA(
                                                                                        "style",
                                                                                        "text"
                                                                                    ))
                                                                                ), 4)
                                                                            } else {
                                                                                _cE("scroll-view", _uM("key" to 1, "direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                                                                    _cE(Fragment, null, RenderHelpers.renderList(unref(nowAnswer), fun(item, index, __index, _cached): Any {
                                                                                        return _cE(Fragment, null, _uA(
                                                                                            _cE(Fragment, null, RenderHelpers.renderList(item.input, fun(inp, subIndex, __index, _cached): Any {
                                                                                                return _cE("view", _uM("style" to _nS(_uM("flex-wrap" to "wrap", "flex-direction" to "row"))), _uA(
                                                                                                    _cE(Fragment, null, RenderHelpers.renderList((inp as UTSArray<timi>), fun(inps, ssubIndex, __index, _cached): Any {
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
                                                                                                                        changeInput(`$event`, index, subIndex, ssubIndex)
                                                                                                                    }, "onInputTap" to inputClick, "isAcquisition" to true, "onTranY" to tranY), null, 8, _uA(
                                                                                                                        "style",
                                                                                                                        "text",
                                                                                                                        "onUpdate:text",
                                                                                                                        "onInput"
                                                                                                                    )),
                                                                                                                    _cE("text", _uM("style" to _nS(_uM("color" to "#fff", "margin" to "0 10rpx", "font-size" to "26rpx"))), _tD(inps.content), 5)
                                                                                                                ), 4)
                                                                                                            } else {
                                                                                                                _cC("v-if", true)
                                                                                                            },
                                                                                                            if (inps.type == "image") {
                                                                                                                _cE("view", _uM("key" to 1, "style" to _nS(_uM("width" to "400rpx"))), _uA(
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
                                                                                        ), 64)
                                                                                    }), 256),
                                                                                    if (isTrue(unref(nowSub)?.soundUrl)) {
                                                                                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("margin-top" to "10rpx", "justify-content" to "flex-start", "align-items" to "flex-start"))), _uA(
                                                                                            _cV(_component_u_playMp3, _uM("src" to unref(nowSub)?.soundUrl, "tplType" to 4, "id" to unref(nowSub)?.id, "isPlayNum" to true, "maxPlayNum" to 2), null, 8, _uA(
                                                                                                "src",
                                                                                                "id"
                                                                                            ))
                                                                                        ), 4)
                                                                                    } else {
                                                                                        _cC("v-if", true)
                                                                                    }
                                                                                ), 4)
                                                                            },
                                                                            if (unref(nowSub)?.problemType == "33") {
                                                                                _cE(Fragment, _uM("key" to 2), RenderHelpers.renderList(unref(nowSub)?.soundInfoList, fun(item, __key, __index, _cached): Any {
                                                                                    return _cE("view", _uM("style" to _nS(_uM("margin" to "10rpx"))), _uA(
                                                                                        if (item?.soundUrl != "") {
                                                                                            _cE("view", _uM("key" to 0), _uA(
                                                                                                _cV(_component_u_playMp3, _uM("style" to _nS(_uM("align-items" to "flex-start")), "src" to item?.soundUrl, "tplType" to 4, "ref_for" to true, "ref" to "refMp3", "id" to unref(nowSub)?.id, "isPlayNum" to true, "maxPlayNum" to 2), null, 8, _uA(
                                                                                                    "style",
                                                                                                    "src",
                                                                                                    "id"
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
                                                                        ), 4)
                                                                    ), 4)
                                                                } else {
                                                                    _cC("v-if", true)
                                                                },
                                                                if (isTrue(_uA(
                                                                    "10",
                                                                    "11",
                                                                    "12",
                                                                    "13"
                                                                ).includes(unref(nowSub)?.problemType ?: ""))) {
                                                                    _cE("view", _uM("key" to 1, "style" to _nS(_uM("width" to "100%", "height" to "100%"))), _uA(
                                                                        if (isTrue(_uA(
                                                                            "10",
                                                                            "11",
                                                                            "12"
                                                                        ).includes(unref(nowSub)?.problemType ?: ""))) {
                                                                            _cE(Fragment, _uM("key" to 0), _uA(
                                                                                _cV(_component_wj_selectText, _uM("center" to "center", "text" to unref(nowSub)?.questionContent), null, 8, _uA(
                                                                                    "text"
                                                                                )),
                                                                                _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                                                                    if (unref(selectedOption).length > 0) {
                                                                                        _cE("view", _uM("key" to 0), _uA(
                                                                                            _cV(_component_QuestionOptionsTest, _uM("style" to _nS(_uM("width" to "80%", "margin" to "0 auto")), "onSelect" to fun(`$event`: Any){
                                                                                                selectedOptionFn(`$event` as Number, 0)
                                                                                            }, "options" to unref(nowSub)?.options, "modelValue" to unref(selectedOption)[0], "onUpdate:modelValue" to fun(`$event`: Number){
                                                                                                unref(selectedOption)[0] = `$event`
                                                                                            }), null, 8, _uA(
                                                                                                "style",
                                                                                                "onSelect",
                                                                                                "options",
                                                                                                "modelValue",
                                                                                                "onUpdate:modelValue"
                                                                                            ))
                                                                                        ))
                                                                                    } else {
                                                                                        _cC("v-if", true)
                                                                                    }
                                                                                ), 4)
                                                                            ), 64)
                                                                        } else {
                                                                            _cC("v-if", true)
                                                                        },
                                                                        if (unref(nowSub)?.problemType == "13") {
                                                                            _cE("view", _uM("key" to 1, "style" to _nS(_uM("flex-direction" to "row", "flex-wrap" to "wrap", "padding-right" to "80rpx"))), _uA(
                                                                                _cE(Fragment, null, RenderHelpers.renderList(unref(nowAnswer), fun(item, index, __index, _cached): Any {
                                                                                    return _cE(Fragment, null, _uA(
                                                                                        _cE(Fragment, null, RenderHelpers.renderList(item.input, fun(inp, subIndex, __index, _cached): Any {
                                                                                            return _cE("view", _uM("style" to _nS(_uM("flex-wrap" to "wrap", "flex-direction" to "row"))), _uA(
                                                                                                _cE(Fragment, null, RenderHelpers.renderList((inp as UTSArray<timi>), fun(inps, ssubIndex, __index, _cached): Any {
                                                                                                    return _cE("view", null, _uA(
                                                                                                        if (inps.type == "text") {
                                                                                                            _cE("text", _uM("key" to 0, "style" to _nS(_uM("line-height" to "30rpx", "margin-right" to "4rpx"))), _tD(inps.content), 5)
                                                                                                        } else {
                                                                                                            _cE("view", _uM("key" to 1, "style" to _nS(_uM("min-width" to "90rpx", "min-height" to "30rpx", "margin" to "0 4rpx 0 -4rpx", "position" to "relative", "border-bottom" to "1rpx solid #000"))), _uA(
                                                                                                                _cV(_component_wj_input, _uM("center" to "center", "style" to _nS(_uM("width" to "100%", "height" to "100%", "position" to "absolute", "z-index" to "99")), "text" to inps.content, "onUpdate:text" to fun(`$event`: String){
                                                                                                                    inps.content = `$event`
                                                                                                                }, "onInput" to fun(`$event`: Any){
                                                                                                                    changeInput(`$event`, index, subIndex, ssubIndex)
                                                                                                                }, "onInputTap" to inputClick, "isAcquisition" to true), null, 8, _uA(
                                                                                                                    "style",
                                                                                                                    "text",
                                                                                                                    "onUpdate:text",
                                                                                                                    "onInput"
                                                                                                                )),
                                                                                                                _cE("text", _uM("style" to _nS(_uM("color" to "#fff", "margin" to "0 10rpx", "font-size" to "26rpx"))), _tD(inps.content), 5)
                                                                                                            ), 4)
                                                                                                        }
                                                                                                    ))
                                                                                                }), 256)
                                                                                            ), 4)
                                                                                        }), 256)
                                                                                    ), 64)
                                                                                }), 256)
                                                                            ), 4)
                                                                        } else {
                                                                            _cC("v-if", true)
                                                                        },
                                                                        if (isTrue(unref(nowSub)?.soundUrl)) {
                                                                            _cE("view", _uM("key" to 2, "style" to _nS(_uM("margin-top" to "10rpx", "justify-content" to "flex-start", "align-items" to "flex-start"))), _uA(
                                                                                _cV(_component_u_playMp3, _uM("src" to unref(nowSub)?.soundUrl, "tplType" to 4, "id" to unref(nowSub)?.id, "isPlayNum" to true, "maxPlayNum" to 2), null, 8, _uA(
                                                                                    "src",
                                                                                    "id"
                                                                                ))
                                                                            ), 4)
                                                                        } else {
                                                                            _cC("v-if", true)
                                                                        }
                                                                    ), 4)
                                                                } else {
                                                                    _cC("v-if", true)
                                                                }
                                                            ), 4)
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        _cE("view", _uM("style" to _nS(_uM("width" to "100%", "height" to "100%", "flex-direction" to "row"))), _uA(
                                                            _cE("view", _uM("style" to _nS(_uM("width" to "100%", "flex" to "1"))), _uA(
                                                                if (isTrue(_uA(
                                                                    "30",
                                                                    "31",
                                                                    "32"
                                                                ).includes(unref(nowSub)?.problemType ?: ""))) {
                                                                    _cE("view", _uM("key" to 0, "style" to _nS(_uM("width" to "100%", "flex" to "1"))), _uA(
                                                                        _cE("rich-text", _uM("nodes" to (unref(nowSub)?.questionContent ?: "")), null, 8, _uA(
                                                                            "nodes"
                                                                        )),
                                                                        _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                                                            _cE("view", null, _uA(
                                                                                _cE(Fragment, null, RenderHelpers.renderList(unref(nowSub)?.subProblemList, fun(sub, subIndex, __index, _cached): Any {
                                                                                    return _cE("view", null, _uA(
                                                                                        _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#3D3D3D", "line-height" to "18rpx"))), _tD(subIndex + 1) + "." + _tD(sub.questionContent), 5),
                                                                                        _cE("view", _uM("style" to _nS(_uM("align-items" to "flex-start"))), _uA(
                                                                                            _cE(Fragment, null, RenderHelpers.renderList(sub?.soundInfoList, fun(mp3, __key, __index, _cached): Any {
                                                                                                return _cE(Fragment, null, _uA(
                                                                                                    if ((mp3?.soundUrl ?: "") != "") {
                                                                                                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("margin" to "6rpx 0"))), _uA(
                                                                                                            _cV(_component_u_playMp3, _uM("tplType" to 4, "ref_for" to true, "ref" to "refMp3", "src" to mp3?.soundUrl, "id" to sub.id, "isPlayNum" to true, "maxPlayNum" to 2), null, 8, _uA(
                                                                                                                "src",
                                                                                                                "id"
                                                                                                            ))
                                                                                                        ), 4)
                                                                                                    } else {
                                                                                                        _cC("v-if", true)
                                                                                                    }
                                                                                                ), 64)
                                                                                            }), 256)
                                                                                        ), 4),
                                                                                        if (unref(selectedOption).length > 0) {
                                                                                            _cV(_component_QuestionOptionsTest, _uM("key" to 0, "style" to _nS(_uM("width" to "80%")), "options" to (sub?.options ?: _uA()), "onSelect" to fun(`$event`: Any){
                                                                                                selectedOptionFn(`$event` as Number, subIndex)
                                                                                            }, "modelValue" to unref(selectedOption)[subIndex], "onUpdate:modelValue" to fun(`$event`: Number){
                                                                                                unref(selectedOption)[subIndex] = `$event`
                                                                                            }), null, 8, _uA(
                                                                                                "style",
                                                                                                "options",
                                                                                                "onSelect",
                                                                                                "modelValue",
                                                                                                "onUpdate:modelValue"
                                                                                            ))
                                                                                        } else {
                                                                                            _cC("v-if", true)
                                                                                        }
                                                                                    ))
                                                                                }), 256)
                                                                            ))
                                                                        ), 4)
                                                                    ), 4)
                                                                } else {
                                                                    _cC("v-if", true)
                                                                },
                                                                if (isTrue(_uA(
                                                                    "31",
                                                                    "32"
                                                                ).includes(unref(nowSub)?.problemType ?: ""))) {
                                                                    _cE(Fragment, _uM("key" to 1), RenderHelpers.renderList(unref(nowSub)?.soundInfoList, fun(item, __key, __index, _cached): Any {
                                                                        return _cE("view", _uM("style" to _nS(_uM("margin" to "10rpx"))), _uA(
                                                                            if (item?.soundUrl != "") {
                                                                                _cE("view", _uM("key" to 0), _uA(
                                                                                    _cV(_component_u_playMp3, _uM("style" to _nS(_uM("align-items" to "flex-start")), "src" to item?.soundUrl, "tplType" to 4, "id" to unref(nowSub)?.id, "ref_for" to true, "ref" to "refMp3", "isPlayNum" to true, "maxPlayNum" to 2), null, 8, _uA(
                                                                                        "style",
                                                                                        "src",
                                                                                        "id"
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
                                                            ), 4),
                                                            if (isTrue(_uA(
                                                                "30"
                                                            ).includes(unref(nowSub)?.problemType ?: ""))) {
                                                                _cE("view", _uM("key" to 0, "class" to "read-test-r", "style" to _nS(_uM("width" to "90rpx"))), null, 4)
                                                            } else {
                                                                _cC("v-if", true)
                                                            }
                                                        ), 4)
                                                    ), 4)
                                                } else {
                                                    _cE("view", _uM("key" to 1, "style" to _nS(_uM("flex" to "1", "width" to "100%"))), _uA(
                                                        _cV(unref(GenPagesTestPaperKouyuClass), _uM("nowSub" to JSON.stringify(unref(nowSub)), "nowSubIndex" to unref(getNowSubIndex), "onSetNowSubIndex" to fun(index: Number){
                                                            return setNowSubIndexFn(index)
                                                        }, "onSetSongMap" to fun(map: answerMapType, id: Number){
                                                            return setSongMapFn(map, id)
                                                        }), null, 8, _uA(
                                                            "nowSub",
                                                            "nowSubIndex",
                                                            "onSetNowSubIndex",
                                                            "onSetSongMap"
                                                        ))
                                                    ), 4)
                                                }
                                            ), 4),
                                            if (isTrue(_uA(
                                                "14",
                                                "18",
                                                "15"
                                            ).includes(unref(nowSub)?.problemType ?: ""))) {
                                                _cE("view", _uM("key" to 0, "class" to "read-test-r", "style" to _nS(_uM("width" to "90rpx"))), null, 4)
                                            } else {
                                                _cC("v-if", true)
                                            },
                                            if (isTrue(_uA(
                                                "16",
                                                "17"
                                            ).includes(unref(nowSub)?.problemType ?: ""))) {
                                                _cE("view", _uM("key" to 1, "class" to "read-test-r"), _uA(
                                                    _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                                        _cE("view", null, _uA(
                                                            _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "flex-wrap" to "wrap", "padding-left" to "-10rpx"))), _uA(
                                                                _cE(Fragment, null, RenderHelpers.renderList(unref(nowAnswer), fun(inis, index, __index, _cached): Any {
                                                                    return _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "margin-bottom" to "10rpx", "margin-left" to "10rpx"))), _uA(
                                                                        _cE("text", _uM("style" to _nS(_uM("font-size" to "10.55rpx", "color" to "#3D3D3D"))), "(" + _tD(index + 1) + "). ", 5),
                                                                        _cE("view", _uM("style" to _nS(_uM("padding-bottom" to "-3rpx", "align-items" to "flex-start"))), _uA(
                                                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "14rpx", "max-width" to "90rpx", "margin-bottom" to "6rpx")), "class" to "fTimesNewRoman"), _tD(inis.questionContent), 5),
                                                                            _cE(Fragment, null, RenderHelpers.renderList(inis.options, fun(opt, optIndex, __index, _cached): Any {
                                                                                return _cE("view", _uM("style" to _nS(_uM("max-width" to "94rpx", "align-items" to "flex-start", "margin-bottom" to "3rpx")), "class" to _nC(_uA(
                                                                                    "checkItem",
                                                                                    _uM("check" to opt.isCheck)
                                                                                )), "onClick" to fun(){
                                                                                    unref(examinationIteratorFn).wordingTwo(unref(nowAnswer), index, optIndex)
                                                                                    unref(tempIndexArr).push(unref(nowIndex))
                                                                                }), _uA(
                                                                                    _cE("text", _uM("class" to _nC(_uA(
                                                                                        "checkItemText fTimesNewRoman",
                                                                                        _uM("check" to opt.isCheck)
                                                                                    ))), _tD(opt.title), 3),
                                                                                    if (isTrue(opt.picUrlList != null && opt.picUrlList!!.length > 0)) {
                                                                                        _cE("view", _uM("key" to 0), _uA(
                                                                                            _cE(Fragment, null, RenderHelpers.renderList(opt.picUrlList, fun(src, __key, __index, _cached): Any {
                                                                                                return _cE("image", _uM("src" to src, "style" to _nS(_uM("width" to "65rpx", "margin-top" to "4rpx", "margin-left" to "16rpx")), "mode" to "widthFix", "onClick" to withModifiers(fun(){
                                                                                                    previewImage(src)
                                                                                                }, _uA(
                                                                                                    "stop"
                                                                                                ))), null, 12, _uA(
                                                                                                    "src",
                                                                                                    "onClick"
                                                                                                ))
                                                                                            }), 256)
                                                                                        ))
                                                                                    } else {
                                                                                        _cC("v-if", true)
                                                                                    }
                                                                                ), 14, _uA(
                                                                                    "onClick"
                                                                                ))
                                                                            }), 256)
                                                                        ), 4)
                                                                    ), 4)
                                                                }), 256)
                                                            ), 4)
                                                        ))
                                                    ), 4)
                                                ))
                                            } else {
                                                _cC("v-if", true)
                                            }
                                        )),
                                        _cV(_component_BackgroundImage, _uM("src" to "/static/ico/submitPaper.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "submit_paper"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                            return _uA(
                                                _cE("text", _uM("class" to "_sub", "onClick" to fun(){
                                                    isShowPaperSubject.value = true
                                                }), " 交卷 ", 8, _uA(
                                                    "onClick"
                                                ))
                                            )
                                        }), "_" to 1))
                                    ), 4)
                                )),
                                _cV(_component_paperSubject, _uM("isShow" to unref(isShowPaperSubject), "onUpdate:isShow" to fun(`$event`: Boolean){
                                    trySetRefValue(isShowPaperSubject, `$event`)
                                }, "onOk" to submit), null, 8, _uA(
                                    "isShow"
                                ))
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        _cV(_component_examinationPopup, _uM("isShowResult" to unref(isShowResult), "onUpdate:isShowResult" to fun(`$event`: Boolean){
                            trySetRefValue(isShowResult, `$event`)
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
                        if (isTrue(unref(isShowResult))) {
                            _cE("view", _uM("key" to 1, "class" to "d_content"), _uA(
                                _cV(_component_readResult, _uM("id" to unref(nowResultId), "isYF" to true), null, 8, _uA(
                                    "id"
                                ))
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(isShowKeyboard))) {
                            _cE("view", _uM("key" to 2, "style" to _nS(_uM("position" to "absolute", "left" to "0", "right" to "0", "bottom" to "0"))), _uA(
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "flexDirection" to "row")), "right_content" to _uM(".d_content " to _uM("width" to "530rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "position" to "relative")), "time" to _uM(".d_content .right_content " to _uM("position" to "absolute", "top" to "14rpx", "right" to "14rpx")), "num" to _uM(".d_content .right_content .time " to _uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D")), "time_box" to _uM(".d_content .right_content .time " to _uM("width" to "93.75rpx", "height" to "33.4rpx")), "_time" to _uM(".d_content .right_content .time .time_box " to _uM("width" to "62rpx", "marginLeft" to "31.75rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "fontSize" to "14rpx", "color" to "#5B77FF", "letterSpacing" to 2)), "read-test" to _uM(".d_content .right_content " to _uM("marginTop" to "8.2rpx", "marginRight" to "15rpx", "marginBottom" to "8.2rpx", "marginLeft" to "15rpx", "flexDirection" to "row", "flex" to 1)), "read-test-r" to _uM(".d_content .right_content .read-test " to _uM("width" to "131rpx", "height" to "200rpx", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "marginTop" to "50rpx", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx")), "submit_paper" to _uM(".d_content .right_content " to _uM("width" to "83.79rpx", "height" to "33.98rpx", "position" to "absolute", "right" to "-3rpx", "bottom" to "32rpx")), "_sub" to _uM(".d_content .right_content .submit_paper " to _uM("width" to "60rpx", "marginLeft" to "23.79rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "color" to "#5B77FF", "fontWeight" to "bold", "fontSize" to "18rpx")), "keyboard-switch" to _pS(_uM("width" to "84rpx", "height" to "35rpx", "backgroundImage" to "linear-gradient(to bottom, #FFFFFF, #D0D8FF)", "backgroundColor" to "rgba(0,0,0,0)", "boxShadow" to "0rpx 1rpx 1rpx 0rpx #6694DF", "borderTopLeftRadius" to "21rpx", "borderTopRightRadius" to "21rpx", "borderBottomRightRadius" to "21rpx", "borderBottomLeftRadius" to "21rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "checkItem" to _uM("" to _uM("backgroundColor" to "#DEE9FF", "borderTopLeftRadius" to "4rpx", "borderTopRightRadius" to "4rpx", "borderBottomRightRadius" to "4rpx", "borderBottomLeftRadius" to "4rpx", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx"), ".check" to _uM("backgroundColor" to "#8597ff", "color" to "#ffffff")), "checkItemText" to _uM(".checkItem " to _uM("color" to "#3D3D3D", "fontSize" to "14rpx"), ".checkItem .check" to _uM("color" to "#ffffff")), "hide" to _pS(_uM("!bottom" to "-9999rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
