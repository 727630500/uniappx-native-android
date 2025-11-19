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
import io.dcloud.uniapp.extapi.createVideoContext as uni_createVideoContext
import io.dcloud.uniapp.extapi.getElementById as uni_getElementById
import io.dcloud.uniapp.extapi.getSystemInfo as uni_getSystemInfo
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesGrammarSyncTextbookMaterialsTextbookMaterials : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var showWindow: (textbookId: Number, textbookUnitId: Number, subModule: String) -> Unit
        get() {
            return unref(this.`$exposed`["showWindow"]) as (textbookId: Number, textbookUnitId: Number, subModule: String) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "showWindow", value)
        }
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesGrammarSyncTextbookMaterialsTextbookMaterials, _arg1: SetupContext) -> Any? = fun(__props, ref1): Any? {
            var __expose = ref1.expose
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesGrammarSyncTextbookMaterialsTextbookMaterials
            val _cache = __ins.renderCache
            val show = ref<Boolean>(true)
            val loading = ref<Boolean>(true)
            val screenWidth = ref<Number>(0)
            val screenHeight = ref<Number>(0)
            val selectId = ref<Number>(0)
            val currentVideoSrc = ref<String>("")
            val videoContext = ref<VideoContext?>(null)
            val queryParams = ref(object : UTSJSONObject() {
                var textbookId: Number = 0
                var textbookUnitId: Number = 0
                var subjectType = "英语"
                var module = "600"
                var subModule = "0"
                var isExercise = "0"
                var pageNum: Number = 1
                var pageSize: Number = 3000
            })
            val dataList = ref(_uA<AppProblemVo>())
            fun gen_getList_fn() {
                uni_request<Result<UTSArray<AppProblemVo>>>(RequestOptions(url = getUrl("/biz/problem/api/list"), method = "GET", data = queryParams.value, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    var _data = responseData.data
                    console.log(_data)
                    if (_data != null && _data.length > 0) {
                        dataList.value = _data
                        selectId.value = _data[0].id!!
                        currentVideoSrc.value = _data[0].videoAnalysisUrl!!
                        loading.value = false
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getList = ::gen_getList_fn
            val showWindow = fun(textbookId: Number, textbookUnitId: Number, subModule: String){
                console.log("showWindow")
                queryParams.value["textbookId"] = textbookId
                queryParams.value["textbookUnitId"] = textbookUnitId
                queryParams.value["subModule"] = subModule
                uni_getSystemInfo(GetSystemInfoOptions(success = fun(res) {
                    screenWidth.value = res.screenWidth
                    screenHeight.value = res.screenHeight
                    show.value = true
                    getList()
                }
                ))
            }
            val stopVideo = fun(){
                console.log("stop")
                uni_getElementById<UniVideoElement>("video")?.stop()
            }
            val startVideo = fun(){
                console.log("play")
                uni_getElementById<UniVideoElement>("video")?.play()
            }
            val selectMaterials = fun(item: AppProblemVo){
                stopVideo()
                selectId.value = item.id!!
                currentVideoSrc.value = item.videoAnalysisUrl!!
                startVideo()
            }
            val closeWindow = fun(){
                show.value = false
                loading.value = true
                stopVideo()
                selectId.value = 0
                currentVideoSrc.value = ""
                dataList.value = _uA()
            }
            onLoad(fun(_options){})
            onReady(fun(){
                videoContext.value = uni_createVideoContext("video", null)
            }
            )
            __expose(_uM("showWindow" to showWindow))
            return fun(): Any? {
                val _component_l_loading = resolveEasyComponent("l-loading", GenUniModulesLimeLoadingComponentsLLoadingLLoadingClass)
                return _cE("view", _uM("class" to "content"), _uA(
                    withDirectives(_cE("view", _uM("class" to "mask", "style" to _nS(_uM("height" to (unref(screenHeight) + "px"), "width" to (unref(screenWidth) + "px")))), _uA(
                        _cE("view", _uM("class" to "mask-content"), _uA(
                            _cE("view", _uM("class" to "title"), _uA(
                                _cE("text", _uM("class" to "title_text"), "视频课堂"),
                                _cE("image", _uM("class" to "close", "src" to "/static/user/qiandao_close.png", "onClick" to closeWindow))
                            )),
                            withDirectives(_cV(_component_l_loading, _uM("style" to _nS(_uM("flex" to "1", "justify-content" to "center", "align-items" to "center")), "color" to "#AEC3FC", "size" to "80rpx"), null, 8, _uA(
                                "style"
                            )), _uA(
                                _uA(
                                    vShow,
                                    unref(loading)
                                )
                            )),
                            withDirectives(_cE("view", _uM("class" to "materials_content"), _uA(
                                _cE("view", _uM("class" to "video_view"), _uA(
                                    _cE("video", _uM("class" to "video", "ref" to "video", "id" to "video", "controls" to true, "show-progress" to true, "show-fullscreen-btn" to false, "show-play-btn" to true, "show-center-play-btn" to true, "show-loading" to true, "show-mute-btn" to true, "enable-play-gesture" to true, "vslide-gesture-in-fullscreen" to true, "src" to unref(currentVideoSrc)), null, 8, _uA(
                                        "src"
                                    ))
                                )),
                                _cE("view", _uM("class" to "materials_list"), _uA(
                                    _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("min-height" to "200rpx", "height" to "100%"))), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(dataList), fun(item, index, __index, _cached): Any {
                                            return _cE("view", _uM("class" to _nC(_uA(
                                                "materials_list_row",
                                                _uM("materials_list_row_select" to (item.id == unref(selectId)))
                                            )), "key" to item, "style" to _nS("" + (if (((index + 1) % 2) == 0) {
                                                "background-color: #F1F5FC"
                                            } else {
                                                ""
                                            }
                                            )), "onClick" to fun(){
                                                selectMaterials(item)
                                            }
                                            ), _uA(
                                                _cE("view", _uM("class" to "materials_list_row_item"), _uA(
                                                    _cE("text", _uM("class" to _nC(_uA(
                                                        "materials_list_row_item_text",
                                                        _uM("materials_list_row_item_text_select" to (item.id == unref(selectId)))
                                                    ))), _tD(item.questionContent), 3)
                                                ))
                                            ), 14, _uA(
                                                "onClick"
                                            ))
                                        }
                                        ), 128)
                                    ), 4)
                                ))
                            ), 512), _uA(
                                _uA(
                                    vShow,
                                    !unref(loading)
                                )
                            ))
                        ))
                    ), 4), _uA(
                        _uA(
                            vShow,
                            unref(show)
                        )
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
                return _uM("mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "backgroundColor" to "rgba(0,0,0,0.5)", "zIndex" to 1000, "alignItems" to "center", "justifyContent" to "center")), "mask-content" to _pS(_uM("width" to "600rpx", "height" to "80%", "backgroundImage" to "linear-gradient(to right, #F6D4FC, #aec3fc)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx")), "title" to _pS(_uM("height" to "28rpx", "paddingTop" to "4rpx", "paddingRight" to "8rpx", "paddingBottom" to "4rpx", "paddingLeft" to "8rpx")), "title_text" to _uM(".title " to _uM("fontFamily" to "Arial, Arial", "fontSize" to "14rpx", "color" to "#244E93", "lineHeight" to "24rpx", "textAlign" to "left", "fontStyle" to "normal")), "close" to _uM(".title " to _uM("width" to "18rpx", "height" to "18rpx", "position" to "absolute", "top" to "6rpx", "right" to "10rpx", "zIndex" to 99)), "materials_content" to _pS(_uM("flex" to 1, "backgroundColor" to "#FFffff", "display" to "flex", "flexDirection" to "row")), "video_view" to _uM(".materials_content " to _uM("width" to "400rpx", "height" to "100%")), "video" to _uM(".materials_content .video_view " to _uM("width" to "100%", "height" to "100%")), "materials_list" to _uM(".materials_content " to _uM("flex" to 1, "height" to "100%")), "materials_list_row" to _uM(".materials_content .materials_list " to _uM("height" to "35.16rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to "20rpx", "paddingRight" to 0, "paddingBottom" to "20rpx", "paddingLeft" to 0)), "materials_list_row_select" to _uM(".materials_content .materials_list " to _uM("!backgroundColor" to "#2791FF")), "materials_list_row_item" to _uM(".materials_content .materials_list " to _uM("flexDirection" to "row", "flex" to 1, "alignItems" to "center", "justifyContent" to "center")), "materials_list_row_item_text" to _uM(".materials_content .materials_list " to _uM("fontFamily" to "Arial, Arial", "fontSize" to "14rpx", "color" to "#3D3D3D")), "materials_list_row_item_text_select" to _uM(".materials_content .materials_list " to _uM("!color" to "#ffffff")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
