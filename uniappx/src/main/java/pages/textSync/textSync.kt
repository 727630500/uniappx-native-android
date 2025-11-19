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
import io.dcloud.uniapp.extapi.getSystemInfoSync as uni_getSystemInfoSync
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesTextSyncTextSync : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTextSyncTextSync) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTextSyncTextSync
            val _cache = __ins.renderCache
            val language = ref("英语")
            val category = ref("句子")
            val nowExplain = ref("")
            val isShowScore = ref(false)
            val showPopup = ref(false)
            val readAlong = ref(false)
            val isShowChinese = ref(false)
            val showNext = ref(false)
            val isPlay = ref(false)
            val palyStatus = ref(false)
            val nowReadList = ref<UTSArray<readSubProblemList>?>(null)
            val readList = ref<UTSArray<teachingMaterial2>?>(null)
            val startTime = ref("")
            val nowReadTitle = ref("")
            val nowId = ref(0)
            val nowPlayIndex = ref(0)
            val secondVal = ref(0)
            val timer = ref(0)
            val leftWidth = computed(fun(): Number {
                var info = uni_getSystemInfoSync()
                var screenWidth = info.screenWidth * info.devicePixelRatio
                var rpx: Number = 670
                if (isShowChinese.value) {
                    rpx = 400
                }
                return ((screenWidth * rpx) / 750)
            }
            )
            val rightWidth = computed(fun(): Number {
                var info = uni_getSystemInfoSync()
                var screenWidth = info.screenWidth * info.devicePixelRatio
                var rpx: Number = 360
                return (screenWidth * rpx) / 750
            }
            )
            val songArr = computed(fun(): String {
                palyStatus.value = false
                var arr: UTSArray<songArrType> = _uA()
                nowReadList.value?.forEach(fun(item){
                    arr.push(songArrType(text = ((item.englishTextPrefix ?: "") + (item.englishText ?: "")), time = item.soundDuration ?: 0, song = item.soundUrl ?: ""))
                }
                )
                return JSON.stringify(arr)
            }
            )
            val songArrZh = computed(fun(): String {
                palyStatus.value = false
                var arr: UTSArray<songArrType> = _uA()
                nowReadList.value?.forEach(fun(item){
                    arr.push(songArrType(text = item.chineseExplain ?: "", time = item.soundDuration ?: 0, song = ""))
                }
                )
                return JSON.stringify(arr)
            }
            )
            fun gen_startFn_fn() {
                if (songArr.value == "[]") {
                    return
                }
                var context = CreateNaviteKrokContent("enKaraoke")
                var zhKaraoke = CreateNaviteKrokContent("zhKaraoke")
                if (context == null) {
                    console.log("获取控件失败")
                    return
                }
                if (!isPlay.value) {
                    if (startTime.value == "") {
                        startTime.value = formatDate(null, "YYYY-MM-DD HH:mm:ss")
                        timer.value = setInterval(fun(){
                            secondVal.value++
                        }
                        , 1000)
                    }
                    context!!.controlAnimation()
                    zhKaraoke?.controlAnimation()
                    isPlay.value = true
                } else {
                    console.log("继续播放")
                    context!!.resumeAnimation()
                    zhKaraoke?.resumeAnimation()
                }
                showNext.value = false
                isShowScore.value = false
                palyStatus.value = true
                ucsShare.setState("isPlay", false)
            }
            val startFn = ::gen_startFn_fn
            fun gen_pauseFn_fn() {
                console.log("暂停播放")
                var context = CreateNaviteKrokContent("enKaraoke")
                var zhKaraoke = CreateNaviteKrokContent("zhKaraoke")
                if (context == null) {
                    console.log("获取控件失败")
                    return
                }
                if (isPlay.value) {
                    context!!.pauseAnimation()
                    zhKaraoke?.pauseAnimation()
                }
                ucsShare.setState("isPlay", true)
                isPlay.value = false
                palyStatus.value = false
            }
            val pauseFn = ::gen_pauseFn_fn
            watch(readAlong, fun(kVal: Boolean){
                if (kVal) {
                    if (!palyStatus.value) {
                        startFn()
                    }
                }
            }
            )
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
                var _source = data.result.getString("total_score") ?: "0"
                var _sourceNum = parseInt(_source)
                var context = CreateNaviteKrokContent("enKaraoke")
                var color = "#E54E4E"
                if (_sourceNum >= 90) {
                    color = "#5A9F32"
                } else if (_sourceNum >= 80) {
                    color = "#6694DF"
                } else if (_sourceNum >= 60) {
                    color = "#FA9600"
                }
                context?.setLyricScore(nowPlayIndex.value, _sourceNum.toString(10) + "分", color)
                isShowScore.value = true
                var wordArr = data.result.getJSON("sentence")?.getArray("word")
                if (wordArr == null) {
                    var word = data.result.getArray("sentence")
                    if (word != null) {
                        wordArr = word
                    } else {
                        var _word = data.result.getJSON("word")
                        if (_word != null) {
                            wordArr = _uA(
                                _word
                            )
                        }
                    }
                }
                if (wordArr != null) {
                    var analysisArr = handleSuccess(wordArr as UTSArray<UTSJSONObject>)
                    if (analysisArr != null && context != null) {
                        var currentText = nowExplain.value
                        if (currentText != "") {
                            var words = currentText.split(UTSRegExp("\\s+", ""))
                            analysisArr.forEach(fun(item, index){
                                var color = "#E54E4E"
                                if (item.total_score >= 90) {
                                    color = "#5A9F32"
                                } else if (item.total_score >= 80) {
                                    color = "#6694DF"
                                } else if (item.total_score >= 60) {
                                    color = "#FA9600"
                                }
                                var startIndex: Number = 0
                                run {
                                    var i: Number = 0
                                    while(i < index){
                                        startIndex += words[i].length + 1
                                        i++
                                    }
                                }
                                context?.setCharsColorInLyric(nowPlayIndex.value, startIndex, startIndex + item.content.length, color)
                                showNext.value = true
                            }
                            )
                        }
                    }
                }
            }
            fun gen_switch1Change_fn() {}
            val switch1Change = ::gen_switch1Change_fn
            fun gen_changeTeaching_fn() {
                uni_navigateTo(NavigateToOptions(url = "/pages/user/change"))
            }
            val changeTeaching = ::gen_changeTeaching_fn
            fun gen_onPlayCall_fn(ev: UTSJSONObject) {
                var _index = ev.getNumber("index")
                var _text = ev.getString("text")
                nowPlayIndex.value = _index ?: 0
                if (nowReadList.value != null) {
                    var item = nowReadList.value!![nowPlayIndex.value]
                    nowExplain.value = item.englishText ?: ""
                } else {
                    nowExplain.value = ""
                }
                var context = CreateNaviteKrokContent("enKaraoke")
                var zhKaraoke = CreateNaviteKrokContent("zhKaraoke")
                if (readAlong.value) {
                    palyStatus.value = false
                    context?.pauseAnimation()
                    zhKaraoke?.pauseAnimation()
                    ucsShare.setState("isPlay", true)
                } else {
                    if (isPlay.value) {
                    }
                }
            }
            val onPlayCall = ::gen_onPlayCall_fn
            fun gen_playEnd_fn() {
                console.log("全部播放结束")
                isPlay.value = false
                palyStatus.value = false
            }
            val playEnd = ::gen_playEnd_fn
            val oldIndex = ref(0)
            fun gen_playing_fn(ev: UTSJSONObject, type: Number) {
                var _isPlay = ev.getBoolean("isPlaying")
                var _forceJump = ev.getBoolean("forceJump")
                palyStatus.value = _isPlay ?: false
                isPlay.value = _isPlay ?: false
                var lyricIndex = ev.getNumber("lyricIndex") ?: 0
                var context = CreateNaviteKrokContent("enKaraoke")
                var zhKaraoke = CreateNaviteKrokContent("zhKaraoke")
                if (palyStatus.value && oldIndex.value != lyricIndex) {
                    oldIndex.value = lyricIndex
                    if (_forceJump ?: false) {
                        if (type == 1) {
                            zhKaraoke?.jumpToLyric(lyricIndex)
                        } else {
                            context?.jumpToLyric(lyricIndex)
                        }
                    }
                }
            }
            val playing = ::gen_playing_fn
            fun gen_getSubjectDetail_fn(id: Number) {
                uni_request<Result<UTSArray<readItem>>>(RequestOptions(url = getUrl("/biz/problem/api/list"), method = "GET", data = _uO("id" to id, "textbookId" to getTextBookId(), "subjectType" to "英语", "module" to getModelKey("同步课文"), "subModule" to "301", "isExercise" to "1", "pageSize" to 3000), header = getHeader(), success = fun(res){
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
                        nowReadList.value = _data[0].subProblemList
                        nowReadTitle.value = _data[0].englishText
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getSubjectDetail = ::gen_getSubjectDetail_fn
            watch(nowId, fun(kVal: Number){
                getSubjectDetail(kVal)
                showPopup.value = false
                isShowScore.value = false
            }
            )
            fun gen_getSubjectList_fn() {
                uni_request<Result<UTSArray<teachingMaterial2>>>(RequestOptions(url = getUrl("/biz/textbookUnit/api/list"), method = "GET", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var module = getModelKey("同步课文")
                    var subModule = "301"
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
                    readList.value = responseData.data
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getSubjectList = ::gen_getSubjectList_fn
            onPageShow(fun(){
                getSubjectList()
            }
            )
            onReady(fun(){
                setScreen()
            }
            )
            onUnmounted(fun(){
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/userStudyStatDetail/api/learnSubmit"), method = "POST", data = object : UTSJSONObject() {
                    var textbookId = getTextBookId()
                    var textbookUnitId = getTextbookUnitId()
                    var module = getModelKey("同步课文")
                    var subModule = getSubModelKey("同步课文文章")
                    var startTime = startTime.value
                    var second = secondVal.value
                    var problemNum: Number = 0
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
                ucsShare.setState("isPlay", true)
            }
            )
            return fun(): Any? {
                val _component_switch = resolveComponent("switch")
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_wj_karaoke = resolveEasyComponent("wj-karaoke", GenUniModulesWjKaraokeComponentsWjKaraokeWjKaraokeClass)
                val _component_Recognition = resolveEasyComponent("Recognition", GenComponentsRecognitionRecognitionClass)
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "sync_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "同步课文"), _uM("right" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cE("view", _uM("class" to "nav-r"), _uA(
                                    _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("padding" to "0 10rpx"))), _uA(
                                        if (isTrue(unref(isShowScore))) {
                                            _cE("view", _uM("key" to 0, "class" to "uni-row uni-center"), _uA(
                                                _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("margin-right" to "14.65rpx"))), _uA(
                                                    _cE("view", _uM("class" to "dot", "style" to _nS(_uM("background" to "#5A9F32"))), null, 4),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#fff"))), "正确", 4)
                                                ), 4),
                                                _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("margin-right" to "14.65rpx"))), _uA(
                                                    _cE("view", _uM("class" to "dot", "style" to _nS(_uM("background" to "#6694DF"))), null, 4),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#fff"))), "较好", 4)
                                                ), 4),
                                                _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("margin-right" to "14.65rpx"))), _uA(
                                                    _cE("view", _uM("class" to "dot", "style" to _nS(_uM("background" to "#FA9600"))), null, 4),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#fff"))), "一般", 4)
                                                ), 4),
                                                _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("margin-right" to "14.65rpx"))), _uA(
                                                    _cE("view", _uM("class" to "dot", "style" to _nS(_uM("background" to "#E54E4E"))), null, 4),
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#fff"))), "错误", 4)
                                                ), 4)
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ), 4),
                                    _cE("view", _uM("class" to "nav-r-item"), _uA(
                                        _cE("text", _uM("class" to "nav_text"), "是否跟读"),
                                        _cV(_component_switch, _uM("class" to "switch-checked", "checked" to unref(readAlong), "color" to "#5689DC", "onChange" to fun(){
                                            readAlong.value = !unref(readAlong)
                                        }
                                        ), null, 8, _uA(
                                            "checked",
                                            "onChange"
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "nav-r-item", "style" to _nS(_uM("margin-right" to "0"))), _uA(
                                        _cE("text", _uM("class" to "nav_text"), "是否显示中文"),
                                        _cV(_component_switch, _uM("class" to "switch-checked", "checked" to unref(isShowChinese), "color" to "#5689DC", "onChange" to fun(){
                                            isShowChinese.value = !unref(isShowChinese)
                                        }
                                        ), null, 8, _uA(
                                            "checked",
                                            "onChange"
                                        ))
                                    ), 4)
                                ))
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "sync_content"), _uA(
                            _cE("view", _uM("style" to _nS("height: 100%;width: 670rpx;" + (if (unref(isShowChinese)) {
                                "flex:1"
                            } else {
                                ""
                            }
                            ))), _uA(
                                _cE("view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
                                    _cV(_component_wj_karaoke, _uM("id" to "enKaraoke", "shouldReserve" to true, "songArr" to unref(songArr), "onOnPlayCall" to onPlayCall, "maxWidth" to unref(leftWidth), "style" to _nS(_uM("height" to "100%", "width" to "100%")), "onPlaying" to fun(`$event`: Any){
                                        playing(`$event` as UTSJSONObject, 1)
                                    }
                                    , "onPlayEnd" to playEnd), null, 8, _uA(
                                        "songArr",
                                        "maxWidth",
                                        "style",
                                        "onPlaying"
                                    ))
                                ), 4)
                            ), 4),
                            _cE("view", _uM("style" to _nS("width: 260rpx;height: 100%;position: relative;top: " + (if (unref(isShowChinese)) {
                                "0"
                            } else {
                                "-99999rpx"
                            }
                            ))), _uA(
                                _cV(_component_wj_karaoke, _uM("id" to "zhKaraoke", "songArr" to unref(songArrZh), "maxWidth" to unref(rightWidth), "style" to "height: 100%;width: 100%;", "onPlaying" to fun(`$event`: Any){
                                    playing(`$event` as UTSJSONObject, 2)
                                }
                                ), null, 8, _uA(
                                    "songArr",
                                    "maxWidth",
                                    "onPlaying"
                                ))
                            ), 4)
                        )),
                        _cE("image", _uM("src" to "/static/ico/text_xz.png", "mode" to "", "class" to "show_list_btn", "onClick" to fun(){
                            showPopup.value = true
                        }
                        ), null, 8, _uA(
                            "onClick"
                        )),
                        _cE("view", _uM("class" to "play_btn_box"), _uA(
                            if (isTrue(!unref(readAlong))) {
                                _cE(Fragment, _uM("key" to 0), _uA(
                                    if (isTrue(!unref(palyStatus))) {
                                        _cE("image", _uM("key" to 0, "src" to "/static/ico/text_play.png", "mode" to "", "class" to "play_btn", "onClick" to startFn))
                                    } else {
                                        _cE("image", _uM("key" to 1, "src" to "/static/ico/text_play_pause.png", "mode" to "", "class" to "play_btn", "onClick" to pauseFn))
                                    }
                                ), 64)
                            } else {
                                _cE("view", _uM("key" to 1, "style" to _nS(_uM("flex-direction" to "row", "align-items" to "flex-end"))), _uA(
                                    _cV(_component_Recognition, _uM("onSuccess" to recognitionSuccess, "language" to unref(language), "category" to unref(category), "subject" to unref(nowExplain), "ref" to "refRecognition", "seconds" to 30, "intIsShowStat" to false), null, 8, _uA(
                                        "language",
                                        "category",
                                        "subject"
                                    )),
                                    if (isTrue(unref(showNext))) {
                                        _cE("image", _uM("key" to 0, "src" to "/static/ico/next_btn_ico.png", "style" to _nS(_uM("width" to "80rpx", "height" to "80rpx")), "mode" to "", "onClick" to startFn), null, 4)
                                    } else {
                                        _cC("v-if", true)
                                    }
                                ), 4)
                            }
                        )),
                        if (isTrue(unref(showPopup))) {
                            _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("view", _uM("style" to _nS(_uM("width" to "100%", "height" to "100%"))), _uA(
                                        _cE("view", _uM("class" to "list"), _uA(
                                            _cE("view", _uM("class" to "change_grade_btn", "onClick" to changeTeaching), _uA(
                                                _cE("image", _uM("class" to "change_grade_btn_icon", "src" to "/static/ico/change.png")),
                                                _cE("text", _uM("class" to "change_grade_btn_text"), "更换教材")
                                            )),
                                            _cE("text", _uM("class" to "text_list_title"), "课文列表"),
                                            _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1")), "class" to "text_list"), _uA(
                                                _cE("view", _uM("style" to _nS(_uM("padding-bottom" to "-7.62rpx"))), _uA(
                                                    _cE(Fragment, null, RenderHelpers.renderList(unref(readList), fun(item, index, __index, _cached): Any {
                                                        return _cE(Fragment, null, _uA(
                                                            _cE(Fragment, null, RenderHelpers.renderList(item.appProblemList, fun(sub, __key, __index, _cached): Any {
                                                                return _cE("view", _uM("class" to _nC(_uA(
                                                                    "text_list_item",
                                                                    _uM("active" to (sub.id == unref(nowId)))
                                                                )), "onClick" to fun(){
                                                                    nowId.value = sub.id
                                                                }), _uA(
                                                                    _cE("view", _uM("class" to "text_list_item_l"), _uA(
                                                                        _cE("text", _uM("class" to _nC(_uA(
                                                                            "text_list_item_lt",
                                                                            _uM("active" to (sub.id == unref(nowId)))
                                                                        ))), _tD(item?.unitName), 3),
                                                                        _cE("text", _uM("class" to _nC(_uA(
                                                                            "text_list_item_lt",
                                                                            _uM("active" to (sub.id == unref(nowId)))
                                                                        ))), _tD((sub?.englishTextPrefix ?: "") + (sub?.englishText ?: "")), 3)
                                                                    )),
                                                                    if (sub.id == unref(nowId)) {
                                                                        _cE("image", _uM("key" to 0, "src" to "/static/ico/microphone.png", "style" to _nS(_uM("width" to "22.85rpx", "height" to "22.85rpx")), "mode" to ""), null, 4)
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    }
                                                                ), 10, _uA(
                                                                    "onClick"
                                                                ))
                                                            }), 256)
                                                        ), 64)
                                                    }), 256)
                                                ), 4)
                                            ), 4),
                                            _cE("image", _uM("src" to "/static/ico/close_btn_text.png", "mode" to "", "class" to "close_btn", "onClick" to fun(){
                                                showPopup.value = false
                                            }), null, 8, _uA(
                                                "onClick"
                                            ))
                                        ))
                                    ), 4)
                                )
                            }), "_" to 1))
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
                return _uM("sync_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "sync_content" to _pS(_uM("marginTop" to "14rpx", "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto", "paddingTop" to "20rpx", "paddingRight" to "20rpx", "paddingBottom" to "20rpx", "paddingLeft" to "20rpx", "flexDirection" to "row", "width" to "715rpx", "height" to "306rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx")), "text_title" to _uM(".sync_content " to _uM("fontWeight" to "bold", "fontSize" to "23rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx")), "nav-r" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "nav-r-item" to _uM(".nav-r " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "marginRight" to "27rpx")), "nav_text" to _uM(".nav-r .nav-r-item " to _uM("fontWeight" to "bold", "fontSize" to "12rpx", "color" to "#FFFFFF", "marginRight" to "6.45rpx")), "show_list_btn" to _pS(_uM("position" to "absolute", "left" to "11.72rpx", "bottom" to "14.72rpx", "zIndex" to 9, "width" to "56.25rpx", "height" to "68.55rpx")), "play_btn_box" to _pS(_uM("position" to "absolute", "left" to "50%", "transform" to "translateX(-50%)", "bottom" to "14.72rpx", "zIndex" to 9)), "play_btn" to _pS(_uM("width" to "105.47rpx", "height" to "59.77rpx")), "list" to _pS(_uM("width" to "250rpx", "height" to "100%", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF")), "change_grade_btn" to _uM(".list " to _uM("height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx", "alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#D5DFF0", "borderRightColor" to "#D5DFF0", "borderBottomColor" to "#D5DFF0", "borderLeftColor" to "#D5DFF0", "flexDirection" to "row", "marginTop" to "20rpx", "marginRight" to "20rpx", "marginBottom" to "10rpx", "marginLeft" to "20rpx")), "change_grade_btn_icon" to _uM(".list .change_grade_btn " to _uM("width" to "28rpx", "height" to "28rpx", "marginRight" to "6rpx")), "change_grade_btn_text" to _uM(".list .change_grade_btn " to _uM("flex" to 1, "fontSize" to "12rpx", "color" to "#3D3D3D", "textAlign" to "center")), "text_list_title" to _uM(".list " to _uM("fontWeight" to "bold", "fontSize" to "15rpx", "color" to "#244E93", "lineHeight" to "23rpx", "textAlign" to "center")), "text_list" to _uM(".list " to _uM("marginTop" to "9.38rpx")), "text_list_item" to _uM(".list .text_list " to _uM("backgroundImage" to "none", "backgroundColor" to "#F7FAFF", "marginBottom" to "7.62rpx", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "8.2rpx", "paddingRight" to "14rpx", "paddingBottom" to "8.2rpx", "paddingLeft" to "14rpx"), ".list .text_list .active" to _uM("backgroundColor" to "#E2E6F6")), "text_list_item_l" to _uM(".list .text_list .text_list_item " to _uM("flex" to 1, "height" to "100%")), "text_list_item_lt" to _uM(".list .text_list .text_list_item .text_list_item_l " to _uM("fontSize" to "16rpx", "color" to "#7B7B7B"), ".list .text_list .text_list_item .text_list_item_l .active" to _uM("fontSize" to "15rpx", "fontWeight" to "bold", "color" to "#244E93")), "close_btn" to _pS(_uM("width" to "93.75rpx", "height" to "35.16rpx", "marginTop" to 0, "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
