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
open class GenPagesExamRecordsExamRecords : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesExamRecordsExamRecords) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesExamRecordsExamRecords
            val _cache = __ins.renderCache
            val nowResultId = ref(0)
            val isShow = ref(false)
            val title = ref("")
            val isTemp = ref(false)
            val isYF = ref(false)
            onLoad(fun(event: OnLoadOptions){
                isTemp.value = if (event["isTemp"] ?: "" != "") {
                    true
                } else {
                    false
                }
                isYF.value = if (event["isYF"] ?: "" != "") {
                    true
                } else {
                    false
                }
                title.value = event["title"] ?: "考试记录"
                nowResultId.value = parseInt(event["resultId"] ?: "0")
            }
            )
            onUnmounted(fun(){})
            onReady(fun(){
                setScreen()
                setTimeout(fun(){
                    isShow.value = true
                }
                , 0)
            }
            )
            return fun(): Any? {
                val _component_dTitle = resolveEasyComponent("dTitle", GenComponentsDTitleDTitleClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_readResult = resolveEasyComponent("readResult", GenComponentsReadResultReadResultClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to ""), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_dTitle, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _tD(unref(title))
                                    )
                                }
                                ), "_" to 1))
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            if (isTrue(unref(isShow))) {
                                _cV(_component_readResult, _uM("key" to 0, "id" to unref(nowResultId), "isTemp" to unref(isTemp), "isYF" to unref(isYF)), null, 8, _uA(
                                    "id",
                                    "isTemp",
                                    "isYF"
                                ))
                            } else {
                                _cC("v-if", true)
                            }
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "flexDirection" to "row")), "right_content" to _uM(".d_content " to _uM("flex" to 1, "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "position" to "relative")), "time" to _uM(".d_content .right_content " to _uM("position" to "absolute", "top" to "14rpx", "right" to "14rpx")), "num" to _uM(".d_content .right_content .time " to _uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3D3D3D")), "time_box" to _uM(".d_content .right_content .time " to _uM("width" to "93.75rpx", "height" to "33.4rpx")), "_time" to _uM(".d_content .right_content .time .time_box " to _uM("width" to "62rpx", "marginLeft" to "31.75rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "fontSize" to "14rpx", "color" to "#5B77FF", "letterSpacing" to 2)), "read-test" to _uM(".d_content .right_content " to _uM("marginTop" to "8.2rpx", "marginRight" to "15rpx", "marginBottom" to "8.2rpx", "marginLeft" to "15rpx", "flexDirection" to "row", "flex" to 1)), "read-test-r" to _uM(".d_content .right_content .read-test " to _uM("width" to "131rpx", "height" to "200rpx", "marginTop" to "50rpx", "marginLeft" to "4rpx")), "submit_paper" to _uM(".d_content .right_content " to _uM("width" to "83.79rpx", "height" to "33.98rpx", "position" to "absolute", "right" to "-3rpx", "bottom" to "32rpx")), "_sub" to _uM(".d_content .right_content .submit_paper " to _uM("width" to "60rpx", "marginLeft" to "23.79rpx", "textAlign" to "center", "lineHeight" to "33.4rpx", "color" to "#5B77FF", "fontWeight" to "bold", "fontSize" to "18rpx")), "keyboard-switch" to _pS(_uM("width" to "84rpx", "height" to "35rpx", "backgroundImage" to "linear-gradient(to bottom, #FFFFFF, #D0D8FF)", "backgroundColor" to "rgba(0,0,0,0)", "boxShadow" to "0rpx 1rpx 1rpx 0rpx #6694DF", "borderTopLeftRadius" to "21rpx", "borderTopRightRadius" to "21rpx", "borderBottomRightRadius" to "21rpx", "borderBottomLeftRadius" to "21rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "checkItem" to _uM("" to _uM("backgroundColor" to "#DEE9FF", "color" to "#3D3D3D", "fontSize" to "14rpx", "borderTopLeftRadius" to "4rpx", "borderTopRightRadius" to "4rpx", "borderBottomRightRadius" to "4rpx", "borderBottomLeftRadius" to "4rpx", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx"), ".check" to _uM("backgroundColor" to "#8597ff", "color" to "#ffffff")), "hide" to _pS(_uM("!bottom" to "-9999rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
