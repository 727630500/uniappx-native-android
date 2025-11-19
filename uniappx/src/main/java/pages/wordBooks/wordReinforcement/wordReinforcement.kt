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
import uts.sdk.modules.wjHookScreen.setScreen
open class GenPagesWordBooksWordReinforcementWordReinforcement : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesWordBooksWordReinforcementWordReinforcement) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesWordBooksWordReinforcementWordReinforcement
            val _cache = __ins.renderCache
            val tempPath = ref<String>("")
            val bttt = ref("123")
            val _input = ref(null)
            val isShowKeyboard = ref(false)
            val cursorPosition = ref(0)
            val isShowTypewriting = ref(false)
            onReady(fun(){
                setScreen()
            }
            )
            val getCursor = fun(event: UniNativeViewEvent){
                var _pos = event.detail.getNumber("wz")
                if (_pos != null) {
                    cursorPosition.value = _pos
                }
            }
            val inputClick = fun(){
                if (isShowTypewriting.value == false) {
                    isShowKeyboard.value = true
                }
            }
            return fun(): Any? {
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_progress = resolveComponent("progress")
                val _component_u_playMp3 = resolveEasyComponent("u-playMp3", GenComponentsUPlayMp3UPlayMp3Class)
                val _component_wj_input = resolveEasyComponent("wj-input", GenUniModulesWjInputComponentsWjInputWjInputClass)
                val _component_virtualKeyboard = resolveEasyComponent("virtualKeyboard", GenComponentsVirtualKeyboardVirtualKeyboardClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to ""), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_BackgroundImage, _uM("src" to "/static/ico/follow.png", "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "title_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE("text", _uM("class" to "_text"), " Brdging unit01 ")
                                    )
                                }
                                ), "_" to 1))
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "top_box"), _uA(
                                _cE("view", _uM("class" to "uni-row", "style" to _nS(_uM("align-items" to "center"))), _uA(
                                    _cE("text", _uM("class" to "tip"), "当前进展"),
                                    _cE("view", _uM("style" to _nS(_uM("width" to "160rpx", "margin" to "0 12.3rpx 0 16rpx"))), _uA(
                                        _cV(_component_progress, _uM("border-radius" to 100, "percent" to 14, "stroke-width" to 6, "activeColor" to "#5B77FF", "backgroundColor" to "#E2E6F6"))
                                    ), 4),
                                    _cE("text", _uM("class" to "tip"), "4/16")
                                ), 4)
                            )),
                            _cE("text", _uM("class" to "title"), " 单词强化 "),
                            _cE("view", _uM("class" to "content_box"), _uA(
                                _cE("view", _uM("style" to _nS(_uM("margin-top" to "22rpx"))), _uA(
                                    _cV(_component_u_playMp3, _uM("tplType" to 3))
                                ), 4),
                                _cE("view", _uM("class" to "word_inp_box"), _uA(
                                    _cE("view", _uM("style" to _nS(_uM("flex" to "1", "height" to "100%", "position" to "relative"))), _uA(
                                        _cV(_component_wj_input, _uM("id" to "helloView", "class" to "native-button", "text" to unref(bttt), "onInputTap" to inputClick, "onCursorChange" to getCursor), null, 8, _uA(
                                            "text"
                                        ))
                                    ), 4),
                                    _cE("text", _uM("class" to "sub_btn"), "提交")
                                )),
                                _cE("view", _uM("class" to "grammar_box"), _uA(
                                    _cE("text", _uM("id" to "text", "class" to "grammar_item"), "[ɡʊd] (adj.): 好的；有效的")
                                ))
                            ))
                        )),
                        _cE("view", _uM("style" to _nS(_uM("position" to "absolute", "left" to "0", "right" to "0")), "class" to _nC(_uA(
                            "close",
                            _uM("isShow" to unref(isShowKeyboard))
                        ))), _uA(
                            _cV(_component_virtualKeyboard, _uM("cursorPosition" to unref(cursorPosition), "modelValue" to unref(bttt), "onUpdate:modelValue" to fun(`$event`: String){
                                trySetRefValue(bttt, `$event`)
                            }
                            , "onClose" to fun(){
                                isShowKeyboard.value = false
                            }
                            ), null, 8, _uA(
                                "cursorPosition",
                                "modelValue",
                                "onClose"
                            ))
                        ), 6)
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791ff, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "351rpx", "backgroundImage" to "none", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "paddingTop" to "8.2rpx", "paddingRight" to "16.41rpx", "paddingBottom" to "14rpx", "paddingLeft" to "16.41rpx")), "title_box" to _pS(_uM("width" to "234rpx", "height" to "28rpx", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "marginLeft" to "196.29rpx")), "_text" to _uM(".title_box " to _uM("textAlign" to "center", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#535d8c", "lineHeight" to "28rpx")), "top_box" to _pS(_uM("height" to "35rpx", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "tip" to _uM(".top_box " to _uM("marginLeft" to "5.86rpx", "fontSize" to "12rpx", "color" to "#3d3d3d")), "title" to _pS(_uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3d3d3d", "lineHeight" to "35rpx")), "content_box" to _pS(_uM("alignItems" to "center", "flex" to 1)), "word_inp_box" to _uM(".content_box " to _uM("width" to "355rpx", "paddingTop" to "8rpx", "paddingRight" to "8rpx", "paddingBottom" to "8rpx", "paddingLeft" to "8rpx", "marginTop" to "23rpx", "backgroundImage" to "none", "backgroundColor" to "#f6f8ff", "borderTopLeftRadius" to "33rpx", "borderTopRightRadius" to "33rpx", "borderBottomRightRadius" to "33rpx", "borderBottomLeftRadius" to "33rpx", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#b6bcd9", "borderRightColor" to "#b6bcd9", "borderBottomColor" to "#b6bcd9", "borderLeftColor" to "#b6bcd9", "flexDirection" to "row", "alignItems" to "center")), "sub_btn" to _uM(".content_box .word_inp_box " to _uM("width" to "72rpx", "height" to "39rpx", "backgroundImage" to "linear-gradient(to bottom, #cbd4ff, #516df4)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "textAlign" to "center", "fontSize" to "15rpx", "lineHeight" to "39rpx", "color" to "#ffffff")), "grammar_box" to _uM(".content_box " to _uM("marginTop" to "18rpx")), "grammar_item" to _uM(".content_box .grammar_box " to _uM("fontSize" to "14rpx", "color" to "#3d3d3d", "lineHeight" to "35rpx")), "native-button" to _pS(_uM("height" to "100%", "width" to "100%")), "isShow" to _pS(_uM("bottom" to 0)), "close" to _pS(_uM("bottom" to "-400rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
