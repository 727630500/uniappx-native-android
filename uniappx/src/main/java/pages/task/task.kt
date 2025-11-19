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
open class GenPagesTaskTask : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _cache = this.`$`.renderCache
        val _component_LearningInfo = resolveEasyComponent("LearningInfo", GenComponentsLearningInfoLearningInfoClass)
        val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
        val _component_progress = resolveComponent("progress")
        val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
        return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
            return _uA(
                _cV(_component_navbar, _uM("showBack" to true, "title" to "我的任务"), _uM("right" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cE("view", _uM("class" to "search_box"), _uA(
                            _cE("image", _uM("class" to "search_ico", "src" to "/static/ico/search.png")),
                            _cE("input", _uM("class" to "search_input", "placeholder" to "请输入单词查询"))
                        ))
                    )
                }
                ), "default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_LearningInfo)
                    )
                }
                ), "_" to 1)),
                _cE("view", _uM("class" to "d_content"), _uA(
                    _cE("view", _uM("class" to "d_right"), _uA(
                        _cE("view", _uM("class" to "d_top"), _uA(
                            _cE("view", _uM("class" to "d_top_text_group"), _uA(
                                _cE("text", _uM("class" to "d_top_text d_top_blue"), "• 已完成 1"),
                                _cE("text", _uM("class" to "d_top_text d_top_red"), "• 未完成 1")
                            )),
                            _cE("view", _uM("class" to "d_top_btn_group"), _uA(
                                _cE("view", _uM("class" to "d_top_btn"), _uA(
                                    _cE("image", _uM("src" to "/static/ico/all_active.png", "mode" to "aspectFit", "class" to "d_top_btn_ico")),
                                    _cE("text", _uM("class" to "d_top_btn_text d_top_btn_text_active"), "全部")
                                )),
                                _cE("view", _uM("class" to "d_top_btn"), _uA(
                                    _cE("image", _uM("src" to "/static/ico/done.png", "mode" to "aspectFit", "class" to "d_top_btn_ico")),
                                    _cE("text", _uM("class" to "d_top_btn_text"), "已完成")
                                )),
                                _cE("view", _uM("class" to "d_top_btn"), _uA(
                                    _cE("image", _uM("src" to "/static/ico/undone.png", "mode" to "aspectFit", "class" to "d_top_btn_ico")),
                                    _cE("text", _uM("class" to "d_top_btn_text"), "未完成")
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "word_table"), _uA(
                            _cE("view", _uM("class" to "word_table-header"), _uA(
                                _cE("view", _uM("class" to "word_table-header-item word_table-header-item-unit-box"), _uA(
                                    _cE("text", _uM("class" to "word_table-header-item-text"), "任务")
                                )),
                                _cE("view", _uM("class" to "word_table-header-item word_table-header-item-progress-box"), _uA(
                                    _cE("text", _uM("class" to "word_table-header-item-text"), "进度")
                                )),
                                _cE("view", _uM("class" to "word_table-header-item word_table-header-item-count-box"), _uA(
                                    _cE("text", _uM("class" to "word_table-header-item-text"), "完成奖励")
                                )),
                                _cE("view", _uM("class" to "word_table-header-item word_table-header-item-status-box"), _uA(
                                    _cE("text", _uM("class" to "word_table-header-item-text"), "预估时间")
                                )),
                                _cE("view", _uM("class" to "word_table-header-item word_table-header-item-status-box"), _uA(
                                    _cE("text", _uM("class" to "word_table-header-item-text"), "状态")
                                ))
                            )),
                            _cE("scroll-view", _uM("class" to "word_table-body", "scroll-y" to ""), _uA(
                                _cE(Fragment, null, RenderHelpers.renderList(10, fun(item, index, __index, _cached): Any {
                                    return _cE("view", _uM("class" to "word_table-body-row", "key" to item, "style" to _nS("" + (if (((index + 1) % 2) == 0) {
                                        "background-color: #F1F5FC"
                                    } else {
                                        ""
                                    }
                                    ))), _uA(
                                        _cE("view", _uM("class" to "word_table-body-item word_table-body-item-unit-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-body-item-text"), "自主组卷】期中考试")
                                        )),
                                        _cE("view", _uM("class" to "word_table-body-item word_table-body-item-progress-box"), _uA(
                                            _cE("view", _uM("class" to "word_table-body-item-progress"), _uA(
                                                _cV(_component_progress, _uM("class" to "word_table-body-item-progress-progress", "border-radius" to 100, "percent" to 14, "stroke-width" to 7, "activeColor" to "#5B77FF", "backgroundColor" to "#E2E6F6")),
                                                _cE("text", _uM("class" to "word_table-body-item-text"), "12/20")
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "word_table-body-item word_table-body-item-count-box"), _uA(
                                            _cE("view", _uM("class" to "word_table-body-item-progress"), " - ")
                                        )),
                                        _cE("view", _uM("class" to "word_table-body-item word_table-body-item-status-box"), _uA(
                                            _cE("text", _uM("class" to "word_table-body-item-text"), "30分钟")
                                        )),
                                        _cE("view", _uM("class" to "word_table-body-item word_table-body-item-status-box"), _uA(
                                            _cE("view", _uM("class" to "word_table-body-item-status"), _uA(
                                                _cE("text", _uM("class" to "word_table-body-item-status-text"), "已完成")
                                            ))
                                        ))
                                    ), 4)
                                }
                                ), 64)
                            ))
                        ))
                    ))
                ))
            )
        }
        ), "_" to 1))
    }
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ), _uA(
                GenApp.styles
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "378rpx", "marginTop" to "13rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "d_right" to _uM(".d_content " to _uM("flex" to 1, "width" to "100%", "height" to "378rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx")), "word_table" to _uM(".d_content .d_right " to _uM("marginTop" to "7.62rpx", "marginRight" to "7.62rpx", "marginBottom" to "7.62rpx", "marginLeft" to "7.62rpx", "width" to "700rpx")), "title_box" to _pS(_uM("width" to "234rpx", "height" to "28rpx", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "marginLeft" to "196.29rpx")), "_text" to _uM(".title_box " to _uM("textAlign" to "center", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#535D8C", "lineHeight" to "28rpx")), "search_box" to _pS(_uM("width" to "210rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "boxShadow" to "0rpx 2rpx 4rpx 0rpx rgba(130, 141, 197, 0.3)", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "search_ico" to _uM(".search_box " to _uM("width" to "10rpx", "height" to "10rpx", "marginLeft" to "12rpx")), "search_input" to _uM(".search_box " to _uM("flex" to 1, "height" to "100%", "fontSize" to "12rpx", "color" to "#3a3a3a", "marginTop" to 0, "marginRight" to "12rpx", "marginBottom" to 0, "marginLeft" to "12rpx")), "d_top" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to "13.48rpx", "paddingRight" to "16.41rpx", "paddingBottom" to "13.48rpx", "paddingLeft" to "16.41rpx")), "d_top_blue" to _uM(".d_top " to _uM("color" to "#6694DF", "marginRight" to "30rpx")), "d_top_red" to _uM(".d_top " to _uM("color" to "#E13535")), "d_top_text_group" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "d_top_text" to _uM(".d_top_text_group " to _uM("fontWeight" to "700", "fontSize" to "12rpx", "color" to "#E13535")), "d_top_btn_group" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "d_top_btn" to _pS(_uM("marginLeft" to "5.27rpx", "width" to "67rpx", "height" to "25rpx", "borderTopLeftRadius" to "21rpx", "borderTopRightRadius" to "21rpx", "borderBottomRightRadius" to "21rpx", "borderBottomLeftRadius" to "21rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "backgroundImage" to "linear-gradient(to bottom, #F3F7FF, #D5DFF0)", "backgroundColor" to "rgba(0,0,0,0)")), "d_top_btn_text" to _uM(".d_top_btn " to _uM("fontWeight" to "400", "fontSize" to "12rpx", "color" to "#98A6EE")), "d_top_btn_text_active" to _uM(".d_top_btn " to _uM("color" to "#3A58EB")), "d_top_btn_ico" to _uM(".d_top_btn " to _uM("width" to "9.4rpx", "height" to "9.4rpx", "marginRight" to "4.1rpx")), "word_table-header" to _uM(".word_table " to _uM("height" to "35.16rpx", "backgroundColor" to "#F1F5FC", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-header-item-text" to _uM(".word_table " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D")), "word_table-body-row" to _uM(".word_table " to _uM("height" to "35.16rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body" to _uM(".word_table " to _uM("height" to "320rpx")), "word_table-header-item" to _uM(".word_table .word_table-header " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center"), ".word_table .word_table-body-row " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item" to _uM(".word_table .word_table-header " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center"), ".word_table .word_table-body-row " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item-progress-box" to _uM(".word_table " to _uM("flex" to 1.5)), "word_table-header-item-progress-box" to _uM(".word_table " to _uM("flex" to 1.5)), "word_table-body-item-progress" to _uM(".word_table " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item-progress-progress" to _uM(".word_table " to _uM("width" to "160rpx", "height" to "7rpx", "marginRight" to "12.3rpx")), "word_table-body-item-text" to _uM(".word_table " to _uM("fontFamily" to "Arial, Arial", "fontWeight" to "400", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx")), "word_table-body-item-text-unit" to _uM(".word_table " to _uM("color" to "#3A58EB")), "word_table-body-item-count-box" to _uM(".word_table " to _uM("flex" to 0.5)), "word_table-header-item-count-box" to _uM(".word_table " to _uM("flex" to 0.5)), "word_table-body-item-unit-box" to _uM(".word_table " to _uM("flex" to 1)), "word_table-header-item-unit-box" to _uM(".word_table " to _uM("flex" to 1)), "word_table-body-item-status-box" to _uM(".word_table " to _uM("flex" to 1)), "word_table-header-item-status-box" to _uM(".word_table " to _uM("flex" to 1)), "word_table-body-item-status" to _uM(".word_table " to _uM("width" to "44rpx", "height" to "18rpx", "backgroundImage" to "none", "backgroundColor" to "#CCE2EE", "boxShadow" to "inset 0rpx 2rpx 2rpx 0rpx #B0BFD9", "borderTopLeftRadius" to "14rpx", "borderTopRightRadius" to "14rpx", "borderBottomRightRadius" to "14rpx", "borderBottomLeftRadius" to "14rpx", "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item-status-text" to _uM(".word_table " to _uM("fontWeight" to "700", "fontSize" to "11rpx", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
