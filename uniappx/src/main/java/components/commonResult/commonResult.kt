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
import uts.sdk.modules.uniPreviewImage.previewImage as uni_previewImage
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenComponentsCommonResultCommonResult : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var nowAnswer: String by `$props`
    open var seeFailNum: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsCommonResultCommonResult) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsCommonResultCommonResult
            val _cache = __ins.renderCache
            val props = __props
            fun emits(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val nowAnswer = computed<AppProblemList?>(fun(): AppProblemList? {
                return JSON.parse<AppProblemList>(props.nowAnswer)
            }
            )
            val nowIndex = ref(0)
            val isShowJx = ref(false)
            val showJxType = ref(0)
            fun gen_showJx_fn(type: Number) {
                isShowJx.value = true
                showJxType.value = type
            }
            val showJx = ::gen_showJx_fn
            fun gen_seeFail_fn(userExerciseRecordDetailId: Number?, rightStatus: String?) {
                var _findIndex = nowAnswer.value?.subProblemList?.findIndex(fun(item): Boolean {
                    return item.userExerciseRecordDetailId == userExerciseRecordDetailId
                }
                )
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
                    if (rightStatus!! == "0") {
                        if (nowAnswer.value!!.subProblemList!![_findIndex!!].viewErrorAnswerAnalysis == "0") {
                            nowAnswer.value!!.subProblemList!![_findIndex!!].viewErrorAnswerAnalysis = "1"
                            var seeFailNum = props.seeFailNum
                            seeFailNum++
                            emits("update:seeFailNum", seeFailNum)
                            emits("setStatus", _findIndex!!)
                        }
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val seeFail = ::gen_seeFail_fn
            fun gen_previewImage_fn(img: String) {
                var list = _uA<String>()
                list.push(img)
                uni_previewImage(PreviewImageOptions1(urls = list, loop = false, fail = fun(e) {
                    console.log(e)
                }
                ))
            }
            val previewImage = ::gen_previewImage_fn
            return fun(): Any? {
                val _component_analysis_popup = resolveEasyComponent("analysis-popup", GenComponentsAnalysisPopupAnalysisPopupClass)
                return _cE(Fragment, null, _uA(
                    if (props.nowAnswer != "") {
                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("padding-left" to "-10rpx"))), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(unref(nowAnswer)?.subProblemList, fun(inis, index, __index, _cached): Any {
                                return _cE("view", _uM("style" to _nS(_uM("margin-bottom" to "10rpx", "margin-left" to "10rpx"))), _uA(
                                    if (isTrue(index != 5 && unref(nowAnswer)?.problemType == "18" || unref(nowAnswer)?.problemType != "18")) {
                                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                            if (unref(nowAnswer)?.problemType != "17") {
                                                _cE("text", _uM("key" to 0, "style" to _nS(_uM("font-size" to "10.55rpx", "color" to "#3D3D3D"))), "(" + _tD(index + 1) + "). ", 5)
                                            } else {
                                                _cC("v-if", true)
                                            },
                                            _cE("view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
                                                if (unref(nowAnswer)?.problemType != "17") {
                                                    _cE(Fragment, _uM("key" to 0), _uA(
                                                        if (isTrue(inis.rightStatus == "0" && inis.userBlankAnswer == "" && inis.userOption == "")) {
                                                            _cE("text", _uM("key" to 0, "style" to _nS(_uM("color" to "red", "margin-bottom" to "4rpx"))), _uA(
                                                                "未做题",
                                                                _cE("text", _uM("style" to _nS("color: " + (if (inis.viewErrorAnswerAnalysis == "0") {
                                                                    "red"
                                                                } else {
                                                                    "green"
                                                                }) + ";margin-bottom: 4rpx;")), "(错题" + _tD(if (inis.viewErrorAnswerAnalysis == "0") {
                                                                    "未"
                                                                } else {
                                                                    "已"
                                                                }) + "查看)", 5)
                                                            ), 4)
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        if (isTrue(inis.rightStatus == "0" && (inis.userBlankAnswer != "" || inis.userOption != ""))) {
                                                            _cE("text", _uM("key" to 1, "style" to _nS(_uM("color" to "red", "margin-bottom" to "4rpx"))), _uA(
                                                                "错误",
                                                                _cE("text", _uM("style" to _nS("color: " + (if (inis.viewErrorAnswerAnalysis == "0") {
                                                                    "red"
                                                                } else {
                                                                    "green"
                                                                }) + ";margin-bottom: 4rpx;")), "(错题" + _tD(if (inis.viewErrorAnswerAnalysis == "0") {
                                                                    "未"
                                                                } else {
                                                                    "已"
                                                                }) + "查看)", 5)
                                                            ), 4)
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        if (inis.rightStatus == "1") {
                                                            _cE("text", _uM("key" to 2, "style" to _nS(_uM("color" to "green", "margin-bottom" to "4rpx"))), "正确", 4)
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                    ), 64)
                                                } else {
                                                    _cC("v-if", true)
                                                },
                                                if (isTrue(_uA(
                                                    "14",
                                                    "15",
                                                    "18"
                                                ).includes(unref(nowAnswer)?.problemType!!))) {
                                                    _cE("view", _uM("key" to 1, "style" to _nS(_uM("padding-bottom" to "-3rpx", "flex" to "1"))), _uA(
                                                        _cE("text", _uM("class" to _nC(_uA(
                                                            "checked_item",
                                                            _uM("sussce" to (inis.userBlankAnswer == inis.blankAnswer), "fail" to (inis.userBlankAnswer != inis.blankAnswer), "empty" to (inis.userBlankAnswer == ""))
                                                        )), "style" to _nS(_uM("width" to "100%", "font-size" to "14rpx"))), _tD(inis.userBlankAnswer), 7)
                                                    ), 4)
                                                } else {
                                                    _cC("v-if", true)
                                                },
                                                if (isTrue(_uA(
                                                    "17",
                                                    "16"
                                                ).includes(unref(nowAnswer)?.problemType!!))) {
                                                    _cE("view", _uM("key" to 2, "style" to _nS(_uM("padding-bottom" to "-3rpx", "flex" to "1"))), _uA(
                                                        if (unref(nowAnswer)?.problemType == "17") {
                                                            _cE("view", _uM("key" to 0), _uA(
                                                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "10.55rpx", "color" to "#3D3D3D"))), "(" + _tD(index + 1) + "). ", 5),
                                                                    if (isTrue(inis.rightStatus == "0" && inis.userBlankAnswer == "" && inis.userOption == "")) {
                                                                        _cE("text", _uM("key" to 0, "style" to _nS(_uM("color" to "red", "margin-bottom" to "4rpx"))), _uA(
                                                                            "未做题",
                                                                            _cE("text", _uM("style" to _nS("color: " + (if (inis.viewErrorAnswerAnalysis == "0") {
                                                                                "red"
                                                                            } else {
                                                                                "green"
                                                                            }) + ";margin-bottom: 4rpx;")), "(错题" + _tD(if (inis.viewErrorAnswerAnalysis == "0") {
                                                                                "未"
                                                                            } else {
                                                                                "已"
                                                                            }) + "查看)", 5)
                                                                        ), 4)
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    },
                                                                    if (isTrue(inis.rightStatus == "0" && (inis.userBlankAnswer != "" || inis.userOption != ""))) {
                                                                        _cE("text", _uM("key" to 1, "style" to _nS(_uM("color" to "red", "margin-bottom" to "4rpx"))), _uA(
                                                                            "错误",
                                                                            _cE("text", _uM("style" to _nS("color: " + (if (inis.viewErrorAnswerAnalysis == "0") {
                                                                                "red"
                                                                            } else {
                                                                                "green"
                                                                            }) + ";margin-bottom: 4rpx;")), "(错题" + _tD(if (inis.viewErrorAnswerAnalysis == "0") {
                                                                                "未"
                                                                            } else {
                                                                                "已"
                                                                            }) + "查看)", 5)
                                                                        ), 4)
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    },
                                                                    if (inis.rightStatus == "1") {
                                                                        _cE("text", _uM("key" to 2, "style" to _nS(_uM("color" to "green", "margin-bottom" to "4rpx"))), "正确", 4)
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    }
                                                                ), 4),
                                                                " " + _tD(inis.questionContent)
                                                            ))
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        _cE(Fragment, null, RenderHelpers.renderList(inis.options, fun(opt, __key, __index, _cached): Any {
                                                            return _cE("view", _uM("class" to _nC(_uA(
                                                                "checked_item",
                                                                _uM("sussce" to opt.isRight, "fail" to (inis.userOption == opt.option && !opt.isRight))
                                                            ))), _uA(
                                                                _cE("text", _uM("style" to _nS(_uM("flex" to "1", "font-size" to "14rpx"))), _tD(opt.option) + "." + _tD(opt.describe), 5),
                                                                if (isTrue(opt.picUrlList != null && opt.picUrlList!!.length > 0)) {
                                                                    _cE("view", _uM("key" to 0), _uA(
                                                                        _cE("image", _uM("src" to opt.picUrlList!![0], "mode" to "widthFix", "style" to _nS(_uM("width" to "60rpx")), "onClick" to fun(){
                                                                            previewImage(opt.picUrlList!![0])
                                                                        }), null, 12, _uA(
                                                                            "src",
                                                                            "onClick"
                                                                        ))
                                                                    ))
                                                                } else {
                                                                    _cC("v-if", true)
                                                                }
                                                            ), 2)
                                                        }), 256)
                                                    ), 4)
                                                } else {
                                                    _cC("v-if", true)
                                                }
                                            ), 4)
                                        ), 4)
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    if (isTrue(index != 5 && unref(nowAnswer)?.problemType == "18" || unref(nowAnswer)?.problemType != "18")) {
                                        _cE(Fragment, _uM("key" to 1), _uA(
                                            if (isTrue(_uA(
                                                "14",
                                                "15",
                                                "18"
                                            ).includes(unref(nowAnswer)?.problemType!!))) {
                                                _cE("text", _uM("key" to 0, "style" to _nS(_uM("font-size" to "10.55rpx", "text-align" to "left", "margin-top" to "10rpx", "padding-right" to "10rpx"))), " 正确答案：" + _tD(inis.blankAnswer), 5)
                                            } else {
                                                _cC("v-if", true)
                                            }
                                        ), 64)
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    if (isTrue(_uA(
                                        "17",
                                        "16"
                                    ).includes(unref(nowAnswer)?.problemType!!))) {
                                        _cE(Fragment, _uM("key" to 2), RenderHelpers.renderList(inis.options, fun(opt, __key, __index, _cached): Any {
                                            return _cE(Fragment, null, _uA(
                                                if (isTrue(opt.isRight)) {
                                                    _cE("text", _uM("key" to 0, "style" to _nS(_uM("font-size" to "10.55rpx", "text-align" to "left", "margin-top" to "10rpx", "padding-right" to "10rpx"))), " 正确答案：" + _tD(opt.option), 5)
                                                } else {
                                                    _cC("v-if", true)
                                                }
                                            ), 64)
                                        }), 256)
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    if (isTrue(index != 5 && unref(nowAnswer)?.problemType == "18" || unref(nowAnswer)?.problemType != "18")) {
                                        _cE("view", _uM("key" to 3, "style" to _nS(_uM("flex-direction" to "row", "justify-content" to "space-between", "margin-top" to "12rpx", "width" to "100%", "padding-right" to "10rpx"))), _uA(
                                            _cE("text", _uM("class" to "showJx", "onClick" to fun(){
                                                showJx(0)
                                                nowIndex.value = index
                                                seeFail(inis.userExerciseRecordDetailId, inis?.rightStatus)
                                            }), "查看解析", 8, _uA(
                                                "onClick"
                                            )),
                                            if (isTrue(inis?.videoAnalysisUrl != null && inis?.videoAnalysisUrl != "")) {
                                                _cE("text", _uM("key" to 0, "class" to "showSpJx", "onClick" to fun(){
                                                    showJx(1)
                                                    nowIndex.value = index
                                                    seeFail(inis.userExerciseRecordDetailId, inis?.rightStatus)
                                                }), "视频解析", 8, _uA(
                                                    "onClick"
                                                ))
                                            } else {
                                                _cC("v-if", true)
                                            }
                                        ), 4)
                                    } else {
                                        _cC("v-if", true)
                                    }
                                ), 4)
                            }), 256)
                        ), 4)
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    _cV(_component_analysis_popup, _uM("isShow" to unref(isShowJx), "onUpdate:isShow" to fun(`$event`: Boolean){
                        trySetRefValue(isShowJx, `$event`)
                    }
                    , "nowAnswer" to props.nowAnswer, "tpl" to unref(showJxType), "index" to unref(nowIndex)), null, 8, _uA(
                        "isShow",
                        "nowAnswer",
                        "tpl",
                        "index"
                    ))
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
                return _uM("showJx" to _pS(_uM("width" to "56rpx", "height" to "25rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "textAlign" to "center", "lineHeight" to "25rpx", "color" to "#ffffff", "fontSize" to "11.72rpx")), "showSpJx" to _pS(_uM("width" to "56rpx", "height" to "25rpx", "backgroundImage" to "linear-gradient(to bottom, #B2EA92, #439216)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "28rpx", "borderTopRightRadius" to "28rpx", "borderBottomRightRadius" to "28rpx", "borderBottomLeftRadius" to "28rpx", "textAlign" to "center", "lineHeight" to "25rpx", "color" to "#ffffff", "fontSize" to "11.72rpx")), "checked_item" to _uM("" to _uM("backgroundColor" to "#DEE9FF", "color" to "#3D3D3D", "borderTopLeftRadius" to "4rpx", "borderTopRightRadius" to "4rpx", "borderBottomRightRadius" to "4rpx", "borderBottomLeftRadius" to "4rpx", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx", "marginBottom" to "3rpx"), ".sussce" to _uM("backgroundColor" to "#5A9F32", "color" to "#ffffff"), ".fail" to _uM("backgroundColor" to "#FF1313", "color" to "#ffffff"), ".empty" to _uM("width" to "60rpx", "height" to "22rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("update:seeFailNum" to null, "setStatus" to null)
        var props = _nP(_uM("nowAnswer" to _uM("type" to "String", "required" to true, "default" to ""), "seeFailNum" to _uM("type" to "Number", "required" to true, "default" to 0)))
        var propsNeedCastKeys = _uA(
            "nowAnswer",
            "seeFailNum"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
