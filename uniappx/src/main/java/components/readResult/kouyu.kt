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
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
open class GenComponentsReadResultKouyu : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var nowItem: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsReadResultKouyu) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsReadResultKouyu
            val _cache = __ins.renderCache
            val rightInfo = ref("\uE600")
            val ctx = createInnerAudioContext()
            val title = ref("")
            val target = ref(50)
            val modelVale = ref(0)
            val answerList = ref<UTSArray<AppProblemList2>?>(null)
            val props = __props
            fun gen_testGo_fn() {
                val _type = answerList.value!![0].problemType
                uni_redirectTo(RedirectToOptions(url = "/pages/listening/speaking/test?currentType=" + _type + "&title=" + title.value))
            }
            val testGo = ::gen_testGo_fn
            val calculateAverageScores = fun(): ScoreType {
                if (answerList.value == null || answerList.value!!.length === 0) {
                    return ScoreType(complete = 0, accurate = 0, fluent = 0)
                }
                var totalComplete: Number = 0
                var totalAccurate: Number = 0
                var totalFluent: Number = 0
                var count: Number = 0
                answerList.value!!.forEach(fun(item){
                    if (item.subProblemList != null) {
                        item.subProblemList!!.forEach(fun(sub){
                            if (sub.adopt != null) {
                                totalComplete += if (sub.adopt!!.complete != null) {
                                    sub.adopt!!.complete
                                } else {
                                    0
                                }
                                totalAccurate += if (sub.adopt!!.accurate != null) {
                                    sub.adopt!!.accurate
                                } else {
                                    0
                                }
                                totalFluent += if (sub.adopt!!.fluent != null) {
                                    sub.adopt!!.fluent
                                } else {
                                    0
                                }
                                count++
                            }
                        })
                    } else {
                        totalComplete += if (item?.adopt?.complete != null) {
                            item.adopt!!.complete
                        } else {
                            0
                        }
                        totalAccurate += if (item?.adopt?.accurate != null) {
                            item.adopt!!.accurate
                        } else {
                            0
                        }
                        totalFluent += if (item?.adopt?.fluent != null) {
                            item.adopt!!.fluent
                        } else {
                            0
                        }
                        count++
                    }
                }
                )
                if (count === 0) {
                    return ScoreType(complete = 0, accurate = 0, fluent = 0)
                }
                return ScoreType(complete = totalComplete / count, accurate = totalAccurate / count, fluent = totalFluent / count)
            }
            val calculateAverageScore = fun(): Number {
                if (answerList.value == null || answerList.value!!.length === 0) {
                    return 0
                }
                var totalScore: Number = 0
                var count: Number = 0
                answerList.value!!.forEach(fun(item){
                    if (item.subProblemList != null) {
                        item.subProblemList!!.forEach(fun(sub){
                            if (sub.adopt != null) {
                                totalScore += if (sub.adopt!!.score != null) {
                                    sub.adopt!!.score
                                } else {
                                    0
                                }
                                count++
                            }
                        })
                    } else {
                        totalScore += if (item?.adopt?.score != null) {
                            item.adopt!!.score
                        } else {
                            0
                        }
                        count++
                    }
                }
                )
                if (count === 0) {
                    return 0
                }
                return totalScore / count
            }
            val convertToStarRating = fun(score: Number): Number {
                return Math.min(5, Math.max(0, score / 20))
            }
            val averageScores = computed(fun(): ScoreType {
                return calculateAverageScores()
            }
            )
            val averageScore = computed(fun(): Number {
                return calculateAverageScore()
            }
            )
            val completeRating = computed(fun(): Number {
                return convertToStarRating(averageScores.value.complete)
            }
            )
            val accurateRating = computed(fun(): Number {
                return convertToStarRating(averageScores.value.accurate)
            }
            )
            val fluentRating = computed(fun(): Number {
                return convertToStarRating(averageScores.value.fluent)
            }
            )
            ctx.onCanplay(fun(_){
                ctx.play()
            }
            )
            val isSentence = ref(false)
            fun gen_playFn_fn(src: String) {
                console.log(src)
                ctx.src = src
            }
            val playFn = ::gen_playFn_fn
            fun getDetail(id: Number = 0, problemId: Number = 0) {
                answerList.value = JSON.parse<UTSArray<AppProblemList2>>("[" + props.nowItem + "]")
                isSentence.value = answerList.value?.get(0)?.problemType == "34"
            }
            watchEffect(fun(){
                getDetail()
            }
            )
            onUnmounted(fun(){
                ucsShare.setState("isPlay", true)
                ctx.src = ""
                ctx.destroy()
            }
            )
            return fun(): Any? {
                val _component_sentenceAnalysis = resolveEasyComponent("sentenceAnalysis", GenComponentsSentenceAnalysisSentenceAnalysisClass)
                return _cE("view", _uM("class" to "d_content2"), _uA(
                    _cE("view", _uM("class" to "d_right"), _uA(
                        _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("padding" to "0 15rpx", "margin-top" to "10rpx"))), _uA(
                            _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                _cE("text", _uM("style" to _nS(_uM("color" to "#3D3D3D", "font-size" to "14rpx"))), "评分详情：", 4)
                            ), 4),
                            _cE("view", _uM("class" to "uni-row uni-center"), _uA(
                                _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("margin-right" to "14.65rpx"))), _uA(
                                    _cE("view", _uM("class" to "dot", "style" to _nS(_uM("background" to "#5A9F32"))), null, 4),
                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#555"))), "优秀", 4)
                                ), 4),
                                _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("margin-right" to "14.65rpx"))), _uA(
                                    _cE("view", _uM("class" to "dot", "style" to _nS(_uM("background" to "#6694DF"))), null, 4),
                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#555"))), "较好", 4)
                                ), 4),
                                _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("margin-right" to "14.65rpx"))), _uA(
                                    _cE("view", _uM("class" to "dot", "style" to _nS(_uM("background" to "#FA9600"))), null, 4),
                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#555"))), "一般", 4)
                                ), 4),
                                _cE("view", _uM("class" to "uni-row uni-center", "style" to _nS(_uM("margin-right" to "14.65rpx"))), _uA(
                                    _cE("view", _uM("class" to "dot", "style" to _nS(_uM("background" to "#E54E4E"))), null, 4),
                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#555"))), "错误", 4)
                                ), 4),
                                _cE("view", _uM("class" to "uni-row uni-center"), _uA(
                                    _cE("view", _uM("style" to _nS(_uM("background" to "#E54E4E", "width" to "18rpx", "height" to "2rpx", "margin-right" to "5.27rpx"))), null, 4),
                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#555"))), "漏读", 4)
                                ))
                            ))
                        ), 4),
                        _cE("scroll-view", _uM("direction" to "vertical", "class" to "scroll_box"), _uA(
                            if (isTrue(unref(isSentence))) {
                                _cE(Fragment, _uM("key" to 0), RenderHelpers.renderList(unref(answerList), fun(item, index, __index, _cached): Any {
                                    return _cE(Fragment, null, _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(item.subProblemList, fun(sub, index2, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "sub-item"), _uA(
                                                _cE("view", _uM("class" to "uni-row sub-item-line", "style" to _nS(_uM("justify-content" to "space-between"))), _uA(
                                                    _cE("view", _uM("class" to "sub-item-left"), _uA(
                                                        _cE("text", _uM("class" to "sub-title"), "第" + _tD(index2 + 1) + "句：", 1),
                                                        _cV(_component_sentenceAnalysis, _uM("style" to _nS(_uM("max-width" to "370rpx")), "list" to (sub.temp ?: _uA()), "marginTopVal" to "", "fontSizeVal" to "12rpx", "lineHeightVal" to "14rpx"), null, 8, _uA(
                                                            "style",
                                                            "list"
                                                        ))
                                                    )),
                                                    _cE("view", _uM("class" to "uni-row"), _uA(
                                                        _cE("text", _uM("class" to "score-text"), "(得分："),
                                                        _cE("text", _uM("class" to "score-text", "style" to _nS(_uM("color" to "#E54E4E"))), _tD(sub.adopt?.score ?: 0), 5),
                                                        _cE("text", _uM("class" to "score-text"), "/100)")
                                                    ))
                                                ), 4),
                                                _cE("view", _uM("class" to "uni-row sub-item-line uni-center"), _uA(
                                                    _cE("text", _uM("class" to "sub-title"), "录音："),
                                                    _cE("view", _uM("class" to "uni-row uni-center", "onClick" to fun(){
                                                        playFn(sub.soundInfoList!![0].soundUrl!!)
                                                    }), _uA(
                                                        _cE("view", _uM("class" to "uni-row uni-center btn-box"), _uA(
                                                            _cE("image", _uM("style" to _nS(_uM("width" to "11.72rpx", "height" to "11.72rpx")), "src" to "/static/ico/play.png", "mode" to ""), null, 4),
                                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#3A58EB", "margin-left" to "8rpx"))), "听原文", 4)
                                                        ))
                                                    ), 8, _uA(
                                                        "onClick"
                                                    )),
                                                    _cE("view", _uM("class" to "uni-row uni-center", "onClick" to fun(){
                                                        playFn(sub.userSpeakUrl ?: "")
                                                    }), _uA(
                                                        _cE("view", _uM("class" to "uni-row uni-center btn-box"), _uA(
                                                            _cE("image", _uM("style" to _nS(_uM("width" to "11.72rpx", "height" to "11.72rpx")), "src" to "/static/ico/recording_play.png", "mode" to ""), null, 4),
                                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#3A58EB", "margin-left" to "8rpx"))), "听录音", 4)
                                                        ))
                                                    ), 8, _uA(
                                                        "onClick"
                                                    ))
                                                ))
                                            ))
                                        }), 256)
                                    ), 64)
                                }), 256)
                            } else {
                                _cE(Fragment, _uM("key" to 1), RenderHelpers.renderList(unref(answerList), fun(item, index, __index, _cached): Any {
                                    return _cE("view", _uM("style" to _nS(_uM("max-width" to "450rpx"))), _uA(
                                        _cV(_component_sentenceAnalysis, _uM("list" to (item?.temp ?: _uA()), "marginTopVal" to "", "fontSizeVal" to "12rpx", "lineHeightVal" to "14rpx"), null, 8, _uA(
                                            "list"
                                        ))
                                    ), 4)
                                }
                                ), 256)
                            }
                        )),
                        if (isTrue(!unref(isSentence))) {
                            _cE("view", _uM("key" to 0, "class" to "uni-row uni-center", "style" to _nS(_uM("justify-content" to "space-between", "padding" to "0 15rpx", "margin-top" to "10rpx"))), _uA(
                                _cE("view", _uM("class" to "uni-row uni-center"), _uA(
                                    _cE("view", _uM("class" to "uni-row uni-center"), _uA(
                                        _cE("view", _uM("class" to "uni-row uni-center btn-box", "style" to _nS(_uM("background-color" to "#EFF2FF")), "onClick" to fun(){
                                            playFn(unref(answerList)!![0].soundInfoList!![0].soundUrl ?: "")
                                        }), _uA(
                                            _cE("image", _uM("style" to _nS(_uM("width" to "11.72rpx", "height" to "11.72rpx")), "src" to "/static/ico/play.png", "mode" to ""), null, 4),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#3A58EB", "margin-left" to "8rpx"))), "听原文", 4)
                                        ), 12, _uA(
                                            "onClick"
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "uni-row uni-center"), _uA(
                                        _cE("view", _uM("class" to "uni-row uni-center btn-box", "style" to _nS(_uM("background-color" to "#EFF2FF")), "onClick" to fun(){
                                            playFn(unref(answerList)!![0].userSpeakUrl ?: "")
                                        }), _uA(
                                            _cE("image", _uM("style" to _nS(_uM("width" to "11.72rpx", "height" to "11.72rpx")), "src" to "/static/ico/recording_play.png", "mode" to ""), null, 4),
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "12rpx", "color" to "#3A58EB", "margin-left" to "8rpx"))), "听录音", 4)
                                        ), 12, _uA(
                                            "onClick"
                                        ))
                                    ))
                                ))
                            ), 4)
                        } else {
                            _cC("v-if", true)
                        }
                    ))
                ))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("d_content2" to _pS(_uM("flexDirection" to "row", "width" to "500rpx", "height" to "280rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "d_left" to _uM(".d_content2 " to _uM("width" to "185rpx", "height" to "100%", "backgroundImage" to "none", "backgroundColor" to "rgba(255,255,255,0.2)", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "0rpx", "borderBottomRightRadius" to "0rpx", "borderBottomLeftRadius" to "18rpx")), "t_txt" to _uM(".d_content2 .d_left " to _uM("fontWeight" to "bold", "fontSize" to "21rpx", "color" to "#030229", "lineHeight" to "21rpx")), "defen" to _uM(".d_content2 .d_left " to _uM("textAlign" to "center", "fontSize" to "9rpx", "color" to "#030229", "lineHeight" to "11rpx")), "tip-item" to _uM(".d_content2 .d_left " to _uM("flexDirection" to "row", "alignItems" to "center", "marginLeft" to "15rpx")), "tip-title" to _uM(".d_content2 .d_left .tip-item " to _uM("marginRight" to "6rpx", "fontSize" to "9rpx", "color" to "#030229", "lineHeight" to "23rpx")), "tip-content" to _uM(".d_content2 .d_left .tip-item " to _uM("width" to "27rpx", "height" to "12rpx", "backgroundImage" to "linear-gradient(180deg, #FFFFFF 0%, #DAE8FF 100%)", "backgroundColor" to "rgba(0,0,0,0)", "boxShadow" to "inset 0rpx 1rpx 1rpx 0rpx #A1ACE4", "fontSize" to "8rpx", "color" to "#E54E4E", "lineHeight" to "12rpx", "textAlign" to "center", "borderTopLeftRadius" to "4rpx", "borderTopRightRadius" to "4rpx", "borderBottomRightRadius" to "4rpx", "borderBottomLeftRadius" to "4rpx", "marginLeft" to "5rpx")), "d_right" to _uM(".d_content2 " to _uM("flex" to 1, "width" to "100%", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "height" to "100%", "borderTopLeftRadius" to 0, "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to 0)), "top_box" to _uM(".d_content2 .d_right " to _uM("marginTop" to "12rpx", "marginRight" to 0, "marginBottom" to 0, "marginLeft" to 0, "flexDirection" to "row", "paddingTop" to 0, "paddingRight" to "11rpx", "paddingBottom" to 0, "paddingLeft" to "11rpx")), "btn_box" to _uM(".d_content2 .d_right .top_box " to _uM("flexDirection" to "row", "alignItems" to "center", "marginRight" to "20rpx"), ".d_content2 .d_right .top_box .active" to _uM("backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "100rpx", "borderTopRightRadius" to "100rpx", "borderBottomRightRadius" to "100rpx", "borderBottomLeftRadius" to "100rpx", "paddingTop" to "5rpx", "paddingRight" to "8rpx", "paddingBottom" to "5rpx", "paddingLeft" to "8rpx")), "tips" to _uM(".d_content2 .d_right " to _uM("fontWeight" to "400", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "14rpx", "textAlign" to "center")), "scroll_box" to _uM(".d_content2 .d_right " to _uM("width" to "100%", "height" to "200rpx", "marginTop" to "14rpx", "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto", "paddingTop" to 0, "paddingRight" to "12rpx", "paddingBottom" to "-5rpx", "paddingLeft" to "12rpx")), "sub-item" to _uM(".d_content2 .d_right .scroll_box " to _uM("width" to "100%", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginBottom" to "5rpx", "paddingTop" to "9rpx", "paddingRight" to "9rpx", "paddingBottom" to "-2rpx", "paddingLeft" to "9rpx")), "sub-item-line" to _uM(".d_content2 .d_right .scroll_box .sub-item " to _uM("marginBottom" to "11rpx")), "sub-title" to _uM(".d_content2 .d_right .scroll_box .sub-item " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "16rpx")), "sub-content" to _uM(".d_content2 .d_right .scroll_box .sub-item " to _uM("maxWidth" to "350rpx")), "sub-item-left" to _uM(".d_content2 .d_right .scroll_box .sub-item " to _uM("flexDirection" to "row", "flex" to 1)), "btn-box" to _uM(".d_content2 .d_right " to _uM("flexDirection" to "row", "width" to "78rpx", "height" to "23rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "33rpx", "borderTopRightRadius" to "33rpx", "borderBottomRightRadius" to "33rpx", "borderBottomLeftRadius" to "33rpx", "marginRight" to "8rpx", "alignItems" to "center", "justifyContent" to "center")), "btn" to _uM(".d_content2 .d_right " to _uM("width" to "100.78rpx", "height" to "35.16rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "fontSize" to "14rpx", "lineHeight" to "35.16rpx", "textAlign" to "center", "color" to "#ffffff", "borderTopLeftRadius" to "35rpx", "borderTopRightRadius" to "35rpx", "borderBottomRightRadius" to "35rpx", "borderBottomLeftRadius" to "35rpx")), "score-text" to _uM(".d_content2 .d_right " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("nowItem" to _uM("type" to "String", "required" to true, "default" to "")))
        var propsNeedCastKeys = _uA(
            "nowItem"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
