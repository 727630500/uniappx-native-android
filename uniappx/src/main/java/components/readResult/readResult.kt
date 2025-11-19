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
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenComponentsReadResultReadResult : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var id: Number by `$props`
    open var isYF: Boolean by `$props`
    open var isTemp: Boolean by `$props`
    open var isTest: Boolean by `$props`
    open var showNext: Boolean by `$props`
    open var zlddNextTask: Number by `$props`
    open var studyTaskId: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsReadResultReadResult) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsReadResultReadResult
            val _cache = __ins.renderCache
            val styleTransformWithOrigin = ref<UniElement?>(null)
            fun gen_detectionHtmlFn_fn(str: String?): String {
                return detectionHtml(str)
            }
            val detectionHtmlFn = ::gen_detectionHtmlFn_fn
            fun gen_formatTimeFn_fn(num: Number): String {
                return formatTime(num)
            }
            val formatTimeFn = ::gen_formatTimeFn_fn
            val showAddWord = ref(false)
            val showAddWordInfo = ref<wordData?>(null)
            fun emits(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val props = __props
            val resetFn = fun(){
                emits("reset")
            }
            val seeFailNum = ref(0)
            val isShowJx = ref(false)
            val showJxType = ref(0)
            fun gen_showJx_fn(type: Number) {
                isShowJx.value = true
                showJxType.value = type
            }
            val showJx = ::gen_showJx_fn
            fun gen_getStatus_fn(data: AppProblemList): Boolean {
                if (_uA(
                    "10",
                    "12",
                    "13",
                    "35"
                ).includes(data?.problemType ?: "")) {
                    return data.rightStatus == "1"
                } else {
                    var find = data.subProblemList?.find(fun(item): Boolean {
                        return item.rightStatus == "0"
                    }
                    )
                    return find == null
                }
            }
            val getStatus = ::gen_getStatus_fn
            val answerList = ref(_uA<UTSArray<AppProblemList>>(_uA()))
            val nowIndex = ref("")
            val nowAnswer = ref<AppProblemList?>(null)
            val nowAnswerInput = ref(_uA<UTSArray<resultItem>>(_uA()))
            val isShowText = ref(false)
            fun gen_nextMode_fn() {
                uni_redirectTo(RedirectToOptions(url = "/pages/readSync/test/test?isTraining=1&studyTaskId=" + props.studyTaskId))
            }
            val nextMode = ::gen_nextMode_fn
            val detail = ref<answerItem?>(null)
            fun gen_processRichText_fn(rich: AppProblemList): UTSArray<UTSArray<resultItem>>? {
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
                                        lineItems.push(resultItem(type = "input", content = part, userBlankAnswer = rich.subProblemList!![nowIndex].userBlankAnswer, answer = rich.subProblemList!![nowIndex].blankAnswer!!))
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
            val nowText = ref("")
            val type = ref("0")
            watch(nowIndex, fun(kVal: String){
                if (kVal == "") {
                    return
                }
                if (answerList.value.length == 0) {
                    return
                }
                isShowText.value = false
                var valArr = kVal.split("-")
                nowAnswer.value = answerList.value[parseInt(valArr[0])][parseInt(valArr[1])]
                console.log(nowAnswer.value)
                if (_uA(
                    "13",
                    "14",
                    "15",
                    "18",
                    "33"
                ).includes(nowAnswer.value!!.problemType)) {
                    var _arr = processRichText(nowAnswer.value!!)!!
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
                                                "未作答"
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
                                                "未作答"
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
                        })
                        arr.push(object : UTSJSONObject() {
                            var text = "\n"
                            var color = "#333333"
                            var bold = false
                        })
                    })
                    nowText.value = JSON.stringify(arr)
                } else {
                    nowText.value = nowAnswer.value?.questionContent ?: ""
                }
                setTimeout(fun(){
                    isShowText.value = true
                }
                , 0)
            }
            )
            val topicNum = computed(fun(): Number {
                var _num: Number = 0
                answerList.value.forEach(fun(arr2){
                    arr2.forEach(fun(i){
                        _num++
                    }
                    )
                }
                )
                return _num
            }
            )
            fun gen_getProblemType_fn(type: UTSArray<AppProblemList>?): String {
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
            fun gen_getCorrectOption_fn(opt: UTSArray<Options>?): String {
                if (opt == null) {
                    return ""
                }
                var f = opt.filter(fun(item): Boolean {
                    return item.isRight
                }
                )
                if (f.length == 0) {
                    return ""
                }
                return f[0].option
            }
            val getCorrectOption = ::gen_getCorrectOption_fn
            fun gen_addWord_fn() {
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/problem/api/addProblemToNewWordBook"), method = "POST", data = object : UTSJSONObject() {
                    var id = showAddWordInfo.value?.appProblemVo?.id
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    showAddWord.value = false
                    uni_showToast(ShowToastOptions(title = "添加成功", icon = "none"))
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val addWord = ::gen_addWord_fn
            fun gen_getWordInfo_fn(txt: String) {
                uni_request<Result<wordData>>(RequestOptions(url = getUrl("/biz/problem/api/queryNewWord"), method = "POST", data = object : UTSJSONObject() {
                    var englishText = txt
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    var _data = responseData.data
                    showAddWordInfo.value = _data
                    if (_data?.appProblemVo == null) {
                        uni_showToast(ShowToastOptions(title = "暂时无法添加", icon = "none"))
                        return
                    }
                    showAddWord.value = true
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getWordInfo = ::gen_getWordInfo_fn
            fun gen_addVOCAB_fn(str: String) {
                getWordInfo(str)
            }
            val addVOCAB = ::gen_addVOCAB_fn
            fun gen_heightFn_fn(h: Number) {
                if (nowAnswer.value == null) {
                    return
                }
                uni_getElementById("styleTransformWithOrigin")?.style?.setProperty("flex", "1")
                if (_uA(
                    "10",
                    "12",
                    "13"
                ).includes(nowAnswer.value!!.problemType)) {
                    uni_getElementById("styleTransformWithOrigin")?.style?.setProperty("max-height", "" + h + "px")
                    uni_getElementById("styleTransformWithOrigin")?.style?.setProperty("min-height", "" + h + "px")
                } else {
                    uni_getElementById("styleTransformWithOrigin")?.style?.setProperty("max-height", "288rpx")
                    uni_getElementById("styleTransformWithOrigin")?.style?.setProperty("min-height", "288rpx")
                }
            }
            val heightFn = ::gen_heightFn_fn
            fun gen_getDetail_fn() {
                uni_showLoading(ShowLoadingOptions(title = "加载中"))
                uni_request<Result<answerItem>>(RequestOptions(url = getUrl("/biz/problem/api/getExerciseRecordDetail"), method = "GET", data = object : UTSJSONObject() {
                    var recordId = props.id
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    uni_hideLoading()
                    seeFailNum.value = responseData.data?.viewErrorAnswerAnalysisNum ?: 0
                    detail.value = responseData.data!!
                    type.value = responseData.data?.type ?: "0"
                    console.log(detail.value)
                    answerList.value = answerGroup(responseData.data!!.appProblemList!!, props.isYF)
                    if (nowIndex.value == "") {
                        nowIndex.value = "0-0"
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getDetail = ::gen_getDetail_fn
            fun gen_seeFail_fn(userExerciseRecordDetailId: Number?, rightStatus: String?, viewErrorAnswerAnalysis: String?) {
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/problem/api/viewErrorAnswerAnalysis"), method = "POST", data = object : UTSJSONObject() {
                    var userExerciseRecordDetailId = userExerciseRecordDetailId!!
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    if (rightStatus!! == "0" && viewErrorAnswerAnalysis == "0") {
                        seeFailNum.value++
                        if (!props.isTemp) {
                            var valArr = nowIndex.value.split("-")
                            answerList.value[parseInt(valArr[0])][parseInt(valArr[1])].viewErrorAnswerAnalysis = "1"
                        }
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val seeFail = ::gen_seeFail_fn
            fun gen_setviewErrorAnswerAnalysis_fn(index: Number) {
                var valArr = nowIndex.value.split("-")
                answerList.value[parseInt(valArr[0])][parseInt(valArr[1])]!!.subProblemList!![index!!].viewErrorAnswerAnalysis = "1"
            }
            val setviewErrorAnswerAnalysis = ::gen_setviewErrorAnswerAnalysis_fn
            watchEffect(fun(){
                if (!props.isTemp) {
                    getDetail()
                } else {
                    var data = uni_getStorageSync("temp_data") as UTSJSONObject
                    data["textbookUnitSubModule"] = 0
                    data["textbookUnitModule"] = 0
                    data["userExerciseRecordDetailId"] = 0
                    data["rightStatus"] = "0"
                    data["viewErrorAnswerAnalysis"] = "0"
                    var _item = JSON.parse<AppProblemList>(JSON.stringify(data))
                    nowAnswer.value = _item
                    var _arr = processRichText(nowAnswer.value!!)!!
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
                                                "未作答"
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
                                                "未作答"
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
            )
            onReady(fun(){})
            return fun(): Any? {
                val _component_adoptResult = resolveEasyComponent("adoptResult", GenComponentsAdoptResultAdoptResultClass)
                val _component_wj_selectText = resolveEasyComponent("wj-selectText", GenUniModulesWjSelectTextComponentsWjSelectTextWjSelectTextClass)
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_QuestionOptionsTest = resolveEasyComponent("QuestionOptionsTest", GenComponentsQuestionOptionsTestQuestionOptionsTestClass)
                val _component_commonResult = resolveEasyComponent("commonResult", GenComponentsCommonResultCommonResultClass)
                val _component_analysis_popup = resolveEasyComponent("analysis-popup", GenComponentsAnalysisPopupAnalysisPopupClass)
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                return _cE(Fragment, null, _uA(
                    _cE("view", null, _uA(
                        if (isTrue(!props.isTemp)) {
                            _cE("view", _uM("key" to 0, "style" to _nS(_uM("background" to "rgba(255, 255, 255, 0.2)", "padding" to "5.86rpx 0 7.03rpx"))), _uA(
                                _cE("text", _uM("style" to _nS(_uM("color" to "#DEE9FF", "font-size" to "12rpx", "line-height" to "18rpx", "text-align" to "center"))), "共" + _tD(unref(topicNum)) + "个题目", 5)
                            ), 4)
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(!props.isTemp)) {
                            _cE("view", _uM("key" to 1, "class" to "left_nav", "style" to _nS("" + (if (props.isYF) {
                                ""
                            } else {
                                "width: 120rpx;"
                            }))), _uA(
                                _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1", "padding-right" to "-12rpx"))), _uA(
                                    _cE(Fragment, null, RenderHelpers.renderList(unref(answerList), fun(item, index, __index, _cached): Any {
                                        return _cE("view", null, _uA(
                                            if (getProblemType(item) != "未知类型") {
                                                _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "color" to "#fff", "line-height" to "18rpx"))), _tD(index + 1) + "、" + _tD(getProblemType(item)), 5)
                                                ), 4)
                                            } else {
                                                _cC("v-if", true)
                                            },
                                            _cE("view", _uM("style" to _nS(_uM("flex-wrap" to "wrap", "flex-direction" to "row"))), _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(item, fun(subItem, index2, __index, _cached): Any {
                                                    return _cE("view", _uM("style" to _nS("" + (if (props.isYF) {
                                                        "width:25%;"
                                                    } else {
                                                        "width: 30%;"
                                                    }) + "align-items: flex-start;justify-content: center;"), "onClick" to fun(){
                                                        nowIndex.value = index.toString(10) + "-" + index2.toString(10)
                                                    }), _uA(
                                                        if (isTrue(unref(nowIndex) != index.toString(10) + "-" + index2.toString(10) && getStatus(subItem))) {
                                                            _cE("text", _uM("key" to 0, "class" to "item item_complete"), _tD(index2 + 1), 1)
                                                        } else {
                                                            if (unref(nowIndex) == index.toString(10) + "-" + index2.toString(10)) {
                                                                _cE("text", _uM("key" to 1, "class" to "item item_now"), _tD(index2 + 1), 1)
                                                            } else {
                                                                if (isTrue(!getStatus(subItem))) {
                                                                    _cE("text", _uM("key" to 2, "class" to "item item_fail"), _tD(index2 + 1), 1)
                                                                } else {
                                                                    _cC("v-if", true)
                                                                }
                                                            }
                                                        }
                                                    ), 12, _uA(
                                                        "onClick"
                                                    ))
                                                }), 256)
                                            ), 4)
                                        ))
                                    }), 256)
                                ), 4),
                                if (isTrue(props.showNext)) {
                                    _cE("view", _uM("key" to 0, "style" to _nS(_uM("margin-top" to "10rpx", "align-items" to "center"))), _uA(
                                        if (props.zlddNextTask == 1) {
                                            _cE("text", _uM("key" to 0, "class" to "next_btn", "onClick" to nextMode), "进入下一模式")
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ), 4)
                                } else {
                                    _cE(Fragment, _uM("key" to 1), _uA(
                                        if (isTrue(unref(seeFailNum) < (unref(detail)?.errorAnswerNum ?: 0) || !props.isTest)) {
                                            _cE("text", _uM("key" to 0, "style" to _nS(_uM("color" to "#fff", "text-align" to "center"))), " 错题查看" + _tD(unref(seeFailNum)) + "/" + _tD(unref(detail)?.errorAnswerNum ?: 0), 5)
                                        } else {
                                            _cC("v-if", true)
                                        },
                                        if (isTrue(unref(type) != "40" && ((unref(seeFailNum) >= (unref(detail)?.errorAnswerNum ?: 0) && unref(seeFailNum) > 0) || unref(detail)?.errorAnswerNum == 0) && props.isTest)) {
                                            _cE("text", _uM("key" to 1, "class" to "next_btn", "style" to _nS(_uM("margin" to "0 auto")), "onClick" to resetFn), "继续挑战", 4)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ), 64)
                                }
                            ), 4)
                        } else {
                            _cC("v-if", true)
                        }
                    )),
                    _cE("view", _uM("class" to "right_content"), _uA(
                        _cE("view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
                            _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "justify-content" to "space-between", "margin" to "8.2rpx 15rpx 0"))), _uA(
                                if (unref(nowIndex) != "") {
                                    _cE("text", _uM("key" to 0), _uA(
                                        "(" + _tD(parseInt(unref(nowIndex).split("-")[1]) + 1) + ") " + _tD(unref(nowAnswer)?.id) + " ",
                                        if (isTrue(_uA(
                                            "10",
                                            "12",
                                            "13"
                                        ).includes(unref(nowAnswer)?.problemType ?: ""))) {
                                            _cE("text", _uM("key" to 0, "style" to _nS(_uM("color" to "red", "margin-left" to "10rpx"))), _uA(
                                                _tD(if (unref(nowAnswer)?.userOption == "" && unref(nowAnswer)?.userBlankAnswer == "") {
                                                    "未做题"
                                                } else {
                                                    ""
                                                }) + " ",
                                                if (isTrue(!getStatus(unref(nowAnswer)!!))) {
                                                    _cE("text", _uM("key" to 0, "style" to _nS(_uM("color" to "red"))), _uA(
                                                        _tD(if (unref(nowAnswer)?.userOption == "" && unref(nowAnswer)?.userBlankAnswer == "") {
                                                            ""
                                                        } else {
                                                            "错误"
                                                        }) + " ",
                                                        _cE("text", _uM("style" to _nS("color: " + (if (unref(nowAnswer)?.viewErrorAnswerAnalysis == "0") {
                                                            "red"
                                                        } else {
                                                            "green"
                                                        }) + ";margin-bottom: 4rpx;")), "(错题" + _tD(if (unref(nowAnswer)?.viewErrorAnswerAnalysis == "0") {
                                                            "未"
                                                        } else {
                                                            "已"
                                                        }) + "查看)", 5)
                                                    ), 4)
                                                } else {
                                                    _cC("v-if", true)
                                                }
                                            ), 4)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ))
                                } else {
                                    _cE("text", _uM("key" to 1), "1. " + _tD(unref(nowAnswer)?.id), 1)
                                }
                                ,
                                if (isTrue(!props.isTemp)) {
                                    _cV(_component_adoptResult, _uM("key" to 2, "score" to parseInt(unref(detail)?.score ?: "0"), "notDone" to parseInt(unref(detail)?.notDoneAnswerNum ?: "0"), "failNum" to (unref(detail)?.errorAnswerNum ?: 0), "time" to formatTimeFn(unref(detail)?.exerciseSecond ?: 0), "tpl" to 2), null, 8, _uA(
                                        "score",
                                        "notDone",
                                        "failNum",
                                        "time"
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                            ), 4),
                            _cE("view", _uM("class" to "read-test"), _uA(
                                if (isTrue(_uA(
                                    "30",
                                    "31",
                                    "32",
                                    "33",
                                    "34",
                                    "35"
                                ).includes(unref(nowAnswer)?.problemType ?: ""))) {
                                    _cE("view", _uM("key" to 0), _uA(
                                        if (isTrue(_uA(
                                            "30",
                                            "31",
                                            "32",
                                            "33"
                                        ).includes(unref(nowAnswer)?.problemType ?: ""))) {
                                            _cE("view", _uM("key" to 0), _uA(
                                                _cV(unref(GenComponentsReadResultYufaClass), _uM("nowItem" to JSON.stringify(unref(nowAnswer))), null, 8, _uA(
                                                    "nowItem"
                                                ))
                                            ))
                                        } else {
                                            _cE("view", _uM("key" to 1), _uA(
                                                _cV(unref(GenComponentsReadResultKouyuClass), _uM("nowItem" to JSON.stringify(unref(nowAnswer))), null, 8, _uA(
                                                    "nowItem"
                                                ))
                                            ))
                                        }
                                    ))
                                } else {
                                    _cE("view", _uM("key" to 1, "style" to _nS(_uM("flex" to "1"))), _uA(
                                        _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                            _cE("view", _uM("style" to _nS("" + (if (_uA(
                                                "10",
                                                "12",
                                                "13"
                                            ).includes(unref(nowAnswer)?.problemType ?: "")) {
                                                ""
                                            } else {
                                                "flex: 1;"
                                            }
                                            ))), _uA(
                                                _cE("view", _uM("id" to "styleTransformWithOrigin"), _uA(
                                                    _cV(_component_wj_selectText, _uM("center" to "center", "style" to _nS(_uM("height" to "100%")), "text" to unref(nowText), "onSelect" to addVOCAB, "onHeightFn" to heightFn), null, 8, _uA(
                                                        "style",
                                                        "text"
                                                    ))
                                                )),
                                                if (isTrue(unref(nowAnswer)?.soundUrl)) {
                                                    _cE("view", _uM("key" to 0, "style" to _nS(_uM("margin-top" to "10rpx", "justify-content" to "flex-start", "align-items" to "flex-start"))), _uA(
                                                        _cV(_component_u_playMp3, _uM("src" to unref(nowAnswer)?.soundUrl, "tplType" to 4), null, 8, _uA(
                                                            "src"
                                                        ))
                                                    ), 4)
                                                } else {
                                                    _cC("v-if", true)
                                                }
                                                ,
                                                if (isTrue(!_uA(
                                                    "16",
                                                    "17"
                                                ).includes(unref(nowAnswer)?.problemType ?: ""))) {
                                                    _cE("view", _uM("key" to 1, "style" to _nS(_uM("margin-left" to "10rpx"))), _uA(
                                                        if (isTrue(!_uA(
                                                            "14",
                                                            "16",
                                                            "17",
                                                            "18"
                                                        ).includes(unref(nowAnswer)?.problemType ?: ""))) {
                                                            _cE(Fragment, _uM("key" to 0), _uA(
                                                                if (isTrue(_uA(
                                                                    "10",
                                                                    "12",
                                                                    "13"
                                                                ).includes(unref(nowAnswer)?.problemType ?: ""))) {
                                                                    _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex-direction" to "row", "margin-bottom" to "10rpx", "margin-top" to "10rpx", "align-items" to "center"))), _uA(
                                                                        if (unref(nowAnswer)?.problemType != "13") {
                                                                            _cE("text", _uM("key" to 0), _uA(
                                                                                "正确答案：",
                                                                                _cE("text", _uM("style" to _nS(_uM("color" to "#5A9F32"))), _tD(getCorrectOption(unref(nowAnswer)?.options)), 5)
                                                                            ))
                                                                        } else {
                                                                            _cC("v-if", true)
                                                                        },
                                                                        if (unref(nowAnswer)?.problemType == "13") {
                                                                            _cE("text", _uM("key" to 1), _uA(
                                                                                "正确答案：",
                                                                                _cE("text", _uM("style" to _nS(_uM("color" to "#5A9F32"))), _tD(unref(nowAnswer)?.blankAnswer), 5)
                                                                            ))
                                                                        } else {
                                                                            _cC("v-if", true)
                                                                        },
                                                                        _cE("text", _uM("style" to _nS(_uM("margin-left" to "50rpx")), "class" to "look_btn btn1", "onClick" to fun(){
                                                                            showJx(0)
                                                                            seeFail(unref(nowAnswer)?.userExerciseRecordDetailId, unref(nowAnswer)?.rightStatus, unref(nowAnswer)?.viewErrorAnswerAnalysis)
                                                                        }), "查看解析", 12, _uA(
                                                                            "onClick"
                                                                        )),
                                                                        if (isTrue(unref(nowAnswer)?.videoAnalysisUrl != null && unref(nowAnswer)?.videoAnalysisUrl != "")) {
                                                                            _cE("text", _uM("key" to 2, "class" to "look_btn btn2", "onClick" to fun(){
                                                                                showJx(1)
                                                                                seeFail(unref(nowAnswer)?.userExerciseRecordDetailId, unref(nowAnswer)?.rightStatus, unref(nowAnswer)?.viewErrorAnswerAnalysis)
                                                                            }), "视频解析", 8, _uA(
                                                                                "onClick"
                                                                            ))
                                                                        } else {
                                                                            _cC("v-if", true)
                                                                        },
                                                                        if (unref(nowAnswer)?.knowledgePointsList != null) {
                                                                            _cE("text", _uM("key" to 3, "class" to "look_btn btn3", "onClick" to fun(){
                                                                                showJx(2)
                                                                                seeFail(unref(nowAnswer)?.userExerciseRecordDetailId, unref(nowAnswer)?.rightStatus, unref(nowAnswer)?.viewErrorAnswerAnalysis)
                                                                            }), "查看知识点", 8, _uA(
                                                                                "onClick"
                                                                            ))
                                                                        } else {
                                                                            _cC("v-if", true)
                                                                        }
                                                                    ), 4)
                                                                } else {
                                                                    _cC("v-if", true)
                                                                }
                                                            ), 64)
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        if (isTrue(_uA(
                                                            "10",
                                                            "12",
                                                            "30",
                                                            "31",
                                                            "32"
                                                        ).includes(unref(nowAnswer)?.problemType ?: ""))) {
                                                            _cE("view", _uM("key" to 1, "style" to _nS(_uM("width" to "100%"))), _uA(
                                                                _cV(_component_QuestionOptionsTest, _uM("style" to _nS(_uM("width" to "80%", "margin" to "0 auto")), "resultOptions" to unref(nowAnswer)?.options, "userOption" to unref(nowAnswer)?.userOption), null, 8, _uA(
                                                                    "style",
                                                                    "resultOptions",
                                                                    "userOption"
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
                                        ), 4)
                                    ), 4)
                                }
                                ,
                                if (isTrue(_uA(
                                    "14",
                                    "15",
                                    "16",
                                    "17",
                                    "18"
                                ).includes(unref(nowAnswer)?.problemType ?: ""))) {
                                    _cE("view", _uM("key" to 2, "class" to "read-test-r", "style" to _nS(_uM("margin-top" to "0", "width" to "200rpx", "height" to "100%", "background-color" to "#F3F7FF"))), _uA(
                                        _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                            _cE("view", null, _uA(
                                                _cV(_component_commonResult, _uM("nowAnswer" to JSON.stringify(unref(nowAnswer)), "seeFailNum" to unref(seeFailNum), "onUpdate:seeFailNum" to fun(`$event`: Number){
                                                    trySetRefValue(seeFailNum, `$event`)
                                                }, "onSetStatus" to setviewErrorAnswerAnalysis), null, 8, _uA(
                                                    "nowAnswer",
                                                    "seeFailNum"
                                                ))
                                            ))
                                        ), 4)
                                    ), 4)
                                } else {
                                    _cC("v-if", true)
                                }
                            ))
                        ), 4)
                    )),
                    _cV(_component_analysis_popup, _uM("isShow" to unref(isShowJx), "onUpdate:isShow" to fun(`$event`: Boolean){
                        trySetRefValue(isShowJx, `$event`)
                    }
                    , "nowAnswer" to JSON.stringify(unref(nowAnswer)), "tpl" to unref(showJxType), "index" to -1), null, 8, _uA(
                        "isShow",
                        "nowAnswer",
                        "tpl"
                    )),
                    if (isTrue(unref(showAddWord))) {
                        _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cE("view", _uM("class" to "new_word"), _uA(
                                    _cE("view", _uM("class" to "new_word_box"), _uA(
                                        _cE("text", _uM("class" to "title"), "单词抓取"),
                                        _cE("view", null, _uA(
                                            _cE("text", _uM("class" to "new_word_box_text"), _tD(unref(showAddWordInfo)?.appProblemVo?.englishText), 1),
                                            _cV(_component_u_playMp3, _uM("src" to unref(showAddWordInfo)?.appProblemVo?.soundUrl, "tplType" to 5), null, 8, _uA(
                                                "src"
                                            )),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "16rpx", "line-height" to "35rpx", "text-align" to "center"))), _tD(unref(showAddWordInfo)?.appProblemVo?.phoneticSymbol) + _tD(unref(showAddWordInfo)?.appProblemVo?.chineseExplain), 5),
                                            if (unref(showAddWordInfo)!!.userNewBookExist == "0") {
                                                _cE("text", _uM("key" to 0, "class" to "word_box_add_btn", "onClick" to addWord), "加入生词本")
                                            } else {
                                                _cE("text", _uM("key" to 1, "class" to "word_box_add_btn"), "已加入生词本")
                                            }
                                        )),
                                        _cE("image", _uM("src" to "/static/ico/close.png", "onClick" to fun(){
                                            showAddWord.value = false
                                        }, "mode" to "", "style" to _nS(_uM("position" to "absolute", "right" to "10rpx", "top" to "10rpx", "width" to "14rpx", "height" to "14rpx"))), null, 12, _uA(
                                            "onClick"
                                        ))
                                    ))
                                ))
                            )
                        }), "_" to 1))
                    } else {
                        _cC("v-if", true)
                    }
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
                return _uM("read-test" to _pS(_uM("marginTop" to "8.2rpx", "marginRight" to "15rpx", "marginBottom" to "8.2rpx", "marginLeft" to "15rpx", "flexDirection" to "row", "flex" to 1)), "read-test-r" to _uM(".read-test " to _uM("width" to "131rpx", "height" to "200rpx", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "marginTop" to "50rpx", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx")), "look_btn" to _uM("" to _uM("borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "fontSize" to "12rpx", "color" to "#FFFFFF", "paddingTop" to "6rpx", "paddingRight" to "8rpx", "paddingBottom" to "6rpx", "paddingLeft" to "8rpx", "marginRight" to "17rpx"), ".btn1" to _uM("backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)"), ".btn2" to _uM("backgroundImage" to "linear-gradient(to bottom, #B2EA92, #439216)"), ".btn3" to _uM("backgroundImage" to "linear-gradient(to bottom, #FFBD59, #D98303)", "marginRight" to 0)), "new_word" to _pS(_uM("left" to "50%", "top" to "50%", "position" to "absolute", "minWidth" to "200rpx", "zIndex" to 9, "transform" to "translate(-50%, -50%)")), "new_word_box" to _uM(".new_word " to _uM("backgroundImage" to "linear-gradient(to top, #EFF2FF, #FFFFFF)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "10rpx", "borderTopRightRadius" to "10rpx", "borderBottomRightRadius" to "10rpx", "borderBottomLeftRadius" to "10rpx", "minWidth" to "200rpx")), "title" to _uM(".new_word .new_word_box " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx", "textAlign" to "center", "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#D8D8D8")), "new_word_box_text" to _uM(".new_word .new_word_box " to _uM("fontWeight" to "700", "fontSize" to "18rpx", "color" to "#3A58EB", "lineHeight" to "35rpx", "textAlign" to "center")), "word_box_add_btn" to _uM(".new_word .new_word_box " to _uM("backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#F6F6F6", "lineHeight" to "34rpx", "textAlign" to "center")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("reset" to null)
        var props = _nP(_uM("id" to _uM("type" to "Number", "required" to true, "default" to 0), "isYF" to _uM("type" to "Boolean", "required" to true, "default" to false), "isTemp" to _uM("type" to "Boolean", "required" to true, "default" to false), "isTest" to _uM("type" to "Boolean", "required" to true, "default" to false), "showNext" to _uM("type" to "Boolean", "required" to true, "default" to false), "zlddNextTask" to _uM("type" to "Number", "required" to true, "default" to 0), "studyTaskId" to _uM("type" to "Number", "required" to true, "default" to 0)))
        var propsNeedCastKeys = _uA(
            "id",
            "isYF",
            "isTemp",
            "isTest",
            "showNext",
            "zlddNextTask",
            "studyTaskId"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
