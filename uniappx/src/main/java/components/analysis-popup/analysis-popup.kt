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
open class GenComponentsAnalysisPopupAnalysisPopup : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var nowAnswer: String by `$props`
    open var tpl: Number by `$props`
    open var index: Number by `$props`
    open var isShow: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsAnalysisPopupAnalysisPopup) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsAnalysisPopupAnalysisPopup
            val _cache = __ins.renderCache
            fun gen_detectionHtmlFn_fn(str: String?): String {
                return detectionHtml(str)
            }
            val detectionHtmlFn = ::gen_detectionHtmlFn_fn
            fun emits(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val props = __props
            val nowAnswerData = computed<AppProblemList?>(fun(): AppProblemList? {
                console.log(props.nowAnswer)
                return JSON.parse<AppProblemList>(props.nowAnswer)
            }
            )
            val currentVideoSrc = ref("http://jiaopei-dev.oss-cn-beijing.aliyuncs.com/default/knowledgePoints/video/20250105202658-oyCjzCGQoi.mp4")
            val closeFn = fun(){
                emits("update:isShow", false)
            }
            fun gen_evlItem_fn(src: String): UTSArray<String> {
                return src.split(",")
            }
            val evlItem = ::gen_evlItem_fn
            return fun(): Any? {
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                return if (isTrue(props.isShow)) {
                    _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "analysis_box"), _uA(
                                _cE("image", _uM("src" to "/static/ico/analysis_bg.png", "mode" to "", "class" to "analysis_bg")),
                                _cE("view", _uM("class" to "analysis_top"), _uA(
                                    _cE("text", _uM("class" to "analysis_title"), _tD(if (props.tpl == 2) {
                                        "查看知识点"
                                    } else {
                                        "查看解析"
                                    }), 1),
                                    _cE("image", _uM("src" to "/static/ico/close.png", "mode" to "", "class" to "close", "onClick" to closeFn))
                                )),
                                if (isTrue((_uA<Number>(0, 2)).includes(props.tpl) && _ctx.index >= 0)) {
                                    _cE("scroll-view", _uM("key" to 0, "direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                        if (props.tpl == 0) {
                                            _cE("view", _uM("key" to 0, "style" to _nS(_uM("padding" to "0 20rpx 20rpx 20rpx"))), _uA(
                                                _cE("text", _uM("class" to "title"), "解析"),
                                                if (detectionHtmlFn(unref(nowAnswerData)?.subProblemList?.get(props.index)?.questionAnalysis ?: "") == "") {
                                                    _cE("text", _uM("key" to 0, "class" to "content"), _tD(unref(nowAnswerData)?.subProblemList?.get(props.index)?.questionAnalysis), 1)
                                                } else {
                                                    _cE("rich-text", _uM("key" to 1, "selectable" to true, "nodes" to unref(nowAnswerData)?.subProblemList?.get(props.index)?.questionAnalysis, "style" to _nS(_uM("width" to "100%", "height" to "100%"))), null, 12, _uA(
                                                        "nodes"
                                                    ))
                                                }
                                            ), 4)
                                        } else {
                                            _cC("v-if", true)
                                        },
                                        if (props.tpl == 2) {
                                            _cE("view", _uM("key" to 1, "style" to _nS(_uM("padding" to "0 20rpx 4rpx 20rpx"))), _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(unref(nowAnswerData)?.subProblemList?.get(props.index)?.knowledgePointsList, fun(item, __key, __index, _cached): Any {
                                                    return _cE("view", _uM("style" to _nS(_uM("margin-bottom" to "16rpx"))), _uA(
                                                        _cE("text", _uM("class" to "title"), _tD(item.knowledgePointsName), 1),
                                                        if (item.knowledgePointsDes != null) {
                                                            _cE("text", _uM("key" to 0, "class" to "desc_title"), "知识点描述")
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        if (item.knowledgePointsDes != null) {
                                                            _cE("text", _uM("key" to 1, "class" to "desc_content"), _tD(item.knowledgePointsDes), 1)
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        if (item.knowledgePointsPicList != null) {
                                                            _cE("text", _uM("key" to 2, "class" to "desc_title"), "知识点图片")
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        if (item.knowledgePointsPicList != null) {
                                                            _cE(Fragment, _uM("key" to 3), RenderHelpers.renderList(evlItem(item.knowledgePointsPicList ?: ""), fun(img, __key, __index, _cached): Any {
                                                                return _cE("image", _uM("src" to img, "style" to _nS(_uM("width" to "100%", "margin-bottom" to "10rpx")), "mode" to "widthFix"), null, 12, _uA(
                                                                    "src"
                                                                ))
                                                            }), 256)
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        if (item.knowledgePointsVideoList != null) {
                                                            _cE("text", _uM("key" to 4, "class" to "desc_title"), "知识点视频")
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        if (item.knowledgePointsVideoList != null) {
                                                            _cE(Fragment, _uM("key" to 5), RenderHelpers.renderList(evlItem(item.knowledgePointsVideoList ?: ""), fun(video, __key, __index, _cached): Any {
                                                                return _cE("video", _uM("style" to _nS(_uM("width" to "100%", "height" to "200rpx")), "controls" to true, "show-progress" to true, "show-fullscreen-btn" to false, "show-play-btn" to true, "show-center-play-btn" to true, "show-loading" to true, "show-mute-btn" to true, "enable-play-gesture" to true, "vslide-gesture-in-fullscreen" to true, "src" to video), null, 12, _uA(
                                                                    "src"
                                                                ))
                                                            }), 256)
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                    ), 4)
                                                }), 256)
                                            ), 4)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ), 4)
                                } else {
                                    _cC("v-if", true)
                                },
                                if (isTrue((_uA<Number>(0, 2)).includes(props.tpl) && _ctx.index < 0)) {
                                    _cE("scroll-view", _uM("key" to 1, "direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                        if (props.tpl == 0) {
                                            _cE("view", _uM("key" to 0, "style" to _nS(_uM("padding" to "0 20rpx 20rpx 20rpx"))), _uA(
                                                _cE("text", _uM("class" to "title"), "解析"),
                                                if (detectionHtmlFn(unref(nowAnswerData)?.questionAnalysis) == "") {
                                                    _cE("text", _uM("key" to 0, "class" to "content"), _tD(unref(nowAnswerData)?.questionAnalysis), 1)
                                                } else {
                                                    _cE("rich-text", _uM("key" to 1, "selectable" to true, "nodes" to unref(nowAnswerData)?.questionAnalysis, "style" to _nS(_uM("width" to "100%", "height" to "100%"))), null, 12, _uA(
                                                        "nodes"
                                                    ))
                                                }
                                            ), 4)
                                        } else {
                                            _cC("v-if", true)
                                        },
                                        if (props.tpl == 2) {
                                            _cE("view", _uM("key" to 1, "style" to _nS(_uM("padding" to "0 20rpx 4rpx 20rpx"))), _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(unref(nowAnswerData)?.knowledgePointsList, fun(item, __key, __index, _cached): Any {
                                                    return _cE("view", _uM("style" to _nS(_uM("margin-bottom" to "16rpx"))), _uA(
                                                        _cE("text", _uM("class" to "title"), _tD(item.knowledgePointsName), 1),
                                                        if (item.knowledgePointsDes != null) {
                                                            _cE("text", _uM("key" to 0, "class" to "desc_title"), "知识点描述")
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        if (item.knowledgePointsDes != null) {
                                                            _cE("text", _uM("key" to 1, "class" to "desc_content"), _tD(item.knowledgePointsDes), 1)
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        if (item.knowledgePointsPicList != null) {
                                                            _cE("text", _uM("key" to 2, "class" to "desc_title"), "知识点图片")
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        if (item.knowledgePointsPicList != null) {
                                                            _cE(Fragment, _uM("key" to 3), RenderHelpers.renderList(evlItem(item.knowledgePointsPicList ?: ""), fun(img, __key, __index, _cached): Any {
                                                                return _cE("image", _uM("src" to img, "style" to _nS(_uM("width" to "100%", "margin-bottom" to "10rpx")), "mode" to "widthFix"), null, 12, _uA(
                                                                    "src"
                                                                ))
                                                            }), 256)
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        if (item.knowledgePointsVideoList != null) {
                                                            _cE("text", _uM("key" to 4, "class" to "desc_title"), "知识点视频")
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        if (item.knowledgePointsVideoList != null) {
                                                            _cE(Fragment, _uM("key" to 5), RenderHelpers.renderList(evlItem(item.knowledgePointsVideoList ?: ""), fun(video, __key, __index, _cached): Any {
                                                                return _cE("video", _uM("style" to _nS(_uM("width" to "100%", "height" to "200rpx")), "controls" to true, "show-progress" to true, "show-fullscreen-btn" to false, "show-play-btn" to true, "show-center-play-btn" to true, "show-loading" to true, "show-mute-btn" to true, "enable-play-gesture" to true, "vslide-gesture-in-fullscreen" to true, "src" to video), null, 12, _uA(
                                                                    "src"
                                                                ))
                                                            }), 256)
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                    ), 4)
                                                }), 256)
                                            ), 4)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ), 4)
                                } else {
                                    _cC("v-if", true)
                                },
                                if (isTrue(props.tpl == 1 && _ctx.index >= 0 && unref(nowAnswerData)?.subProblemList?.get(props.index)?.videoAnalysisUrl != null)) {
                                    _cE("view", _uM("key" to 2, "style" to _nS(_uM("padding" to "0 20rpx 20rpx 20rpx", "flex" to "1"))), _uA(
                                        _cE("video", _uM("style" to _nS(_uM("width" to "100%", "height" to "100%")), "controls" to true, "show-progress" to true, "show-fullscreen-btn" to false, "show-play-btn" to true, "show-center-play-btn" to true, "show-loading" to true, "show-mute-btn" to true, "enable-play-gesture" to true, "vslide-gesture-in-fullscreen" to true, "src" to unref(nowAnswerData)?.subProblemList?.get(props.index)?.videoAnalysisUrl), null, 12, _uA(
                                            "src"
                                        ))
                                    ), 4)
                                } else {
                                    _cC("v-if", true)
                                },
                                if (isTrue(props.tpl == 1 && _ctx.index < 0 && unref(nowAnswerData)?.videoAnalysisUrl != null)) {
                                    _cE("view", _uM("key" to 3, "style" to _nS(_uM("padding" to "0 20rpx 20rpx 20rpx", "flex" to "1"))), _uA(
                                        _cE("video", _uM("style" to _nS(_uM("width" to "100%", "height" to "100%")), "controls" to true, "show-progress" to true, "show-fullscreen-btn" to false, "show-play-btn" to true, "show-center-play-btn" to true, "show-loading" to true, "show-mute-btn" to true, "enable-play-gesture" to true, "vslide-gesture-in-fullscreen" to true, "src" to unref(nowAnswerData)?.videoAnalysisUrl), null, 12, _uA(
                                            "src"
                                        ))
                                    ), 4)
                                } else {
                                    _cC("v-if", true)
                                }
                            ))
                        )
                    }), "_" to 1))
                } else {
                    _cC("v-if", true)
                }
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("analysis_box" to _pS(_uM("width" to "478.71rpx", "height" to "328.13rpx", "left" to "50%", "top" to "50%", "transform" to "translate(-50%, -50%)", "position" to "relative")), "analysis_bg" to _uM(".analysis_box " to _uM("width" to "100%", "height" to "100%", "position" to "absolute")), "analysis_top" to _uM(".analysis_box " to _uM("position" to "relative", "alignItems" to "center")), "analysis_title" to _uM(".analysis_box .analysis_top " to _uM("fontWeight" to "700", "fontSize" to "18rpx", "color" to "#3D3D3D", "lineHeight" to "39rpx", "marginTop" to "10rpx")), "close" to _uM(".analysis_box .analysis_top " to _uM("width" to "17.58rpx", "height" to "17.58rpx", "position" to "absolute", "top" to "20rpx", "right" to "20rpx")), "title" to _uM(".analysis_box " to _uM("fontWeight" to "700", "fontSize" to "18rpx", "color" to "#3A58EB")), "desc_title" to _uM(".analysis_box " to _uM("fontWeight" to "700", "fontSize" to "15rpx", "color" to "#3D3D3D", "lineHeight" to "39rpx")), "desc_content" to _uM(".analysis_box " to _uM("backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "boxShadow" to "0rpx 2rpx 6rpx rgba(102, 133, 184, 0.53)", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "18rpx", "paddingTop" to "9rpx", "paddingRight" to "9rpx", "paddingBottom" to "9rpx", "paddingLeft" to "9rpx", "marginTop" to 0, "marginRight" to "6rpx", "marginBottom" to "2rpx", "marginLeft" to "6rpx")), "content" to _uM(".analysis_box " to _uM("fontSize" to "18rpx", "color" to "#3D3D3D", "lineHeight" to "39rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("update:isShow" to null)
        var props = _nP(_uM("nowAnswer" to _uM("type" to "String", "required" to true, "default" to ""), "tpl" to _uM("type" to "Number", "required" to true, "default" to 0), "index" to _uM("type" to "Number", "required" to true, "default" to 0), "isShow" to _uM("type" to "Boolean", "required" to true, "default" to false)))
        var propsNeedCastKeys = _uA(
            "nowAnswer",
            "tpl",
            "index",
            "isShow"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
