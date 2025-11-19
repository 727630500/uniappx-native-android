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
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesWordInterTranslateStrengthen : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesWordInterTranslateStrengthen) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesWordInterTranslateStrengthen
            val _cache = __ins.renderCache
            val ctx = createInnerAudioContext()
            ctx.src = getSondUrl("学习默写时输错警示音")
            val ctxPlayNow = createInnerAudioContext()
            val refMp3 = ref<UPlayMp3ComponentPublicInstance?>(null)
            val styleTransformWithOrigin = ref<UniElement?>(null)
            val followAlongList = ref<UTSArray<followAlongItem>?>(null)
            val nowItem = ref<followAlongItem?>(null)
            val tempPath = ref<String>("")
            val inputText = ref("")
            val _input = ref(null)
            val showFail = ref(false)
            val showCorrect = ref(false)
            val isShowKeyboard = ref(false)
            val cursorPosition = ref(0)
            val needShowKeyboard = ref(ucsShare.getStore("isShowKeyboard") as Boolean)
            val inspectStatus = ref("")
            val defIndex = ref(0)
            val isBack = ref(false)
            val unitTitle = ref("")
            val close = fun(){
                styleTransformWithOrigin.value?.style?.setProperty("transform", "translateY(0)")
                isShowKeyboard.value = false
            }
            val inputClick = fun(){
                if (needShowKeyboard.value) {
                    isShowKeyboard.value = true
                    styleTransformWithOrigin.value?.style?.setProperty("transition-property", "transform")
                    styleTransformWithOrigin.value?.style?.setProperty("transition-duration", "100ms")
                    styleTransformWithOrigin.value?.style?.setProperty("transform", "translateY(0)")
                }
            }
            fun gen_saveTempFn_fn(_index: Number) {
                var _data = _uA<followAlongItem>()
                if (_index >= 0) {
                    followAlongList.value?.forEach(fun(item, index){
                        if (index >= _index) {
                            _data.push(item)
                        }
                    }
                    )
                }
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/studentUser/api/saveCache"), method = "POST", data = object : UTSJSONObject() {
                    var key = uni_getStorageSync("mapKey")
                    var value = if (_data.length > 0) {
                        JSON.stringify(_data)
                    } else {
                        null
                    }
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    if (_data.length == 0) {
                        uni_removeStorageSync("isBack")
                        uni_navigateBack(null)
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val saveTempFn = ::gen_saveTempFn_fn
            fun gen_getTempData_fn() {
                var _data = uni_getStorageSync("tempSub")
                if (_data != "") {
                    var gsData = JSON.parse<UTSArray<followAlongItem>>(JSON.stringify(_data))
                    if (gsData != null) {
                        uni_setStorageSync("isBack", true)
                        followAlongList.value = gsData
                        nowItem.value = gsData[0]
                        ctxPlayNow.src = gsData[0].soundUrl
                        saveTempFn(0)
                        if (ucsShare.getState("debug") as Boolean) {
                            inputText.value = gsData[0].englishText
                        }
                    }
                }
            }
            val getTempData = ::gen_getTempData_fn
            fun gen_nextItem_fn() {
                val _followAlongList = followAlongList.value
                if (_followAlongList != null) {
                    defIndex.value++
                    if (defIndex.value == _followAlongList.length) {
                        saveTempFn(-1)
                        return
                    }
                    inputText.value = ""
                    inspectStatus.value = ""
                    nowItem.value = _followAlongList[defIndex.value]
                    ctxPlayNow.src = _followAlongList[defIndex.value].soundUrl
                    showCorrect.value = false
                    showFail.value = false
                    saveTempFn(defIndex.value)
                    if (ucsShare.getState("debug") as Boolean) {
                        inputText.value = _followAlongList[defIndex.value].englishText
                    }
                }
            }
            val nextItem = ::gen_nextItem_fn
            ctxPlayNow.onEnded(fun(_res){
                nextItem()
            }
            )
            fun gen_inspectText_fn() {
                val _followAlongList = followAlongList.value
                if (inputText.value == "") {
                    return
                }
                if (_followAlongList != null && inputText.value == nowItem.value?.englishText) {
                    showCorrect.value = true
                    showFail.value = false
                    close()
                    ctxPlayNow.play()
                    return
                }
                if (inspectStatus.value == "secondary" && inputText.value != nowItem.value?.englishText) {
                    close()
                    showCorrect.value = false
                    showFail.value = true
                    ctx.play()
                    return
                }
                if (inspectStatus.value == "secondary" && _followAlongList != null) {
                    showCorrect.value = true
                    showFail.value = false
                    close()
                    ctxPlayNow.play()
                    return
                }
                if (inspectStatus.value == "fail") {
                    close()
                    inspectStatus.value = "secondary"
                    inputText.value = ""
                    showFail.value = false
                    showCorrect.value = false
                    return
                }
                if (inputText.value != nowItem.value?.englishText) {
                    inspectStatus.value = "fail"
                    showFail.value = true
                    showCorrect.value = !showFail.value
                    ctx.play()
                    close()
                    return
                }
            }
            val inspectText = ::gen_inspectText_fn
            onLoad(fun(event: OnLoadOptions){
                unitTitle.value = event["unitTitle"] ?: ""
            }
            )
            onReady(fun(){
                setScreen()
                getTempData()
                uni__on("enterKey", fun(){
                    inspectText()
                }
                )
                styleTransformWithOrigin.value = uni_getElementById("styleTransformWithOrigin")
            }
            )
            onUnmounted(fun(){
                uni__off("enterKey", null)
            }
            )
            return fun(): Any? {
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_wj_input = resolveEasyComponent("wj-input", GenUniModulesWjInputComponentsWjInputWjInputClass)
                val _component_virtualKeyboard = resolveEasyComponent("virtualKeyboard", GenComponentsVirtualKeyboardVirtualKeyboardClass)
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
                                _cE("text", _uM("class" to "title"), " 单词强化 "),
                                _cE("view", _uM("class" to "content_box"), _uA(
                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                        _cE("text", _uM("style" to _nS(_uM("font-size" to "23.44rpx", "line-height" to "25rpx"))), _tD(unref(nowItem)?.chineseExplain), 5)
                                    ), 4),
                                    _cE("view", _uM("class" to "word_inp_box"), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("flex" to "1", "height" to "100%", "position" to "relative", "justify-content" to "center"))), _uA(
                                            if (unref(inspectStatus) != "fail") {
                                                _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex" to "1", "justify-content" to "center", "padding" to "6rpx 10rpx 0"))), _uA(
                                                    _cV(_component_wj_input, _uM("id" to "helloView", "style" to _nS(_uM("width" to "100%", "height" to "32rpx")), "class" to "native-button", "text" to unref(inputText), "onUpdate:text" to fun(`$event`: String){
                                                        trySetRefValue(inputText, `$event`)
                                                    }, "onInputTap" to inputClick, "isCheck" to (unref(inspectStatus) == "secondary"), "checkText" to unref(nowItem)?.englishText, "onOnErr" to fun(){
                                                        unref(ctx).play()
                                                        showFail.value = true
                                                        showCorrect.value = false
                                                    }, "onOnSuccess" to fun(){
                                                        showFail.value = false
                                                        showCorrect.value = true
                                                    }, "isAcquisition" to true), null, 8, _uA(
                                                        "style",
                                                        "text",
                                                        "isCheck",
                                                        "checkText",
                                                        "onOnErr",
                                                        "onOnSuccess"
                                                    ))
                                                ), 4)
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
                                        _cE("text", _uM("class" to "sub_btn", "onClick" to inspectText), _tD(if (unref(inspectStatus) == "fail") {
                                            "再次输入"
                                        } else {
                                            "提交"
                                        }
                                        ), 1)
                                    )),
                                    if (isTrue(unref(inspectStatus) == "fail" || unref(showFail))) {
                                        _cE("view", _uM("key" to 0, "class" to "grammar_box"), _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "20rpx", "color" to "green", "margin-right" to "20rpx"))), _tD(unref(nowItem)?.englishText), 5),
                                            _cE("text", _uM("id" to "text", "class" to "grammar_item"), _tD(unref(nowItem)?.phoneticSymbol), 1)
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                ))
                            ))
                        )),
                        if (isTrue(unref(isShowKeyboard))) {
                            _cE("view", _uM("key" to 0, "style" to _nS(_uM("position" to "absolute", "left" to "0", "right" to "0", "bottom" to "0"))), _uA(
                                _cV(_component_virtualKeyboard, _uM("onClose" to close))
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
