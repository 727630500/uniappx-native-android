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
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesTrainingIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTrainingIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTrainingIndex
            val _cache = __ins.renderCache
            var taskList = ref<UTSArray<trainingItem>?>(null)
            fun gen_getList_fn() {
                uni_showLoading(ShowLoadingOptions(title = "加载中...", mask = true))
                var data: UTSJSONObject = object : UTSJSONObject() {
                    var pageSize: Number = 3000
                    var pageNum: Number = 1
                }
                uni_request<Result<UTSArray<trainingItem>>>(RequestOptions(url = getUrl("/biz/zlddTask/api/myList"), method = "GET", data = data, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        return uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                    }
                    taskList.value = responseData.data?.filter(fun(item): Boolean {
                        return item.finishStatus != 2
                    }
                    )
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){
                    uni_hideLoading()
                }
                ))
            }
            val getList = ::gen_getList_fn
            val goPath = fun(path: String){
                uni_navigateTo(NavigateToOptions(url = path, fail = fun(_) {
                    console.log()
                }
                ))
            }
            fun gen_startStudy_fn(item: trainingItem) {
                if (item?.finishStatus == -1) {
                    uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/zlddTask/api/reset"), method = "GET", data = object : UTSJSONObject() {
                        var zlddTaskId = item.id
                    }, header = getHeader(), success = fun(res){
                        val responseData = res.data
                        if (responseData == null) {
                            return
                        }
                        if (responseData.code as Number != 200) {
                            return uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        }
                        uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/zlddTask/api/studyTask/currentTask"), method = "GET", data = object : UTSJSONObject() {
                            var zlddTaskId = item.id
                        }, header = getHeader(), success = fun(res){
                            uni_hideLoading()
                            val responseData = res.data
                            if (responseData == null) {
                                return
                            }
                            if (responseData.code as Number != 200) {
                                return uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                            }
                            var _id = responseData.data?.getNumber("studyTaskId")
                            var _subModule = responseData.data?.getString("subModule") ?: "0"
                            console.log(responseData.data?.getString("taskModule"))
                            uni_removeStorageSync("mapKey")
                            if (_subModule == getSubModelKey("模式一：单词遍历阶段")) {
                                if (responseData.data?.getString("taskModule") == "1") {
                                    uni_navigateTo(NavigateToOptions(url = "/pages/wordInter/learning/learning?isTraining=1&studyTaskId=" + item.id + "&unitTitle=\u6A21\u5F0F\u4E00\uFF1A\u9519\u8BCD\u7CBE\u7814"))
                                    return
                                }
                                uni_navigateTo(NavigateToOptions(url = "/pages/wordInter/learning/test?isTraining=1&studyTaskId=" + (item.id ?: 0)))
                            }
                            if (_subModule == getSubModelKey("模式二：语法训练")) {
                                uni_navigateTo(NavigateToOptions(url = "/pages/grammarSync/test/test?isTraining=1&studyTaskId=" + (item.id ?: 0)))
                            }
                            if (_subModule == getSubModelKey("模式三：阅读训练")) {
                                uni_navigateTo(NavigateToOptions(url = "/pages/readSync/test/test?isTraining=1&studyTaskId=" + (item.id ?: 0)))
                            }
                        }
                        , fail = fun(err){
                            console.log(err)
                        }
                        , complete = fun(_){
                            uni_hideLoading()
                        }
                        ))
                    }
                    , fail = fun(err){
                        console.log(err)
                    }
                    , complete = fun(_){
                        uni_hideLoading()
                    }
                    ))
                    return
                }
                uni_request<Result<UTSJSONObject>>(RequestOptions(url = getUrl("/biz/zlddTask/api/studyTask/currentTask"), method = "GET", data = object : UTSJSONObject() {
                    var zlddTaskId = item.id
                }, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        return uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                    }
                    var _id = responseData.data?.getNumber("studyTaskId")
                    var _subModule = responseData.data?.getString("subModule") ?: "0"
                    uni_removeStorageSync("mapKey")
                    if (_subModule == getSubModelKey("模式一：单词遍历阶段")) {
                        if (responseData.data?.getString("taskModule") == "1") {
                            uni_navigateTo(NavigateToOptions(url = "/pages/wordInter/learning/learning?isTraining=1&studyTaskId=" + item.id + "&unitTitle=\u6A21\u5F0F\u4E00\uFF1A\u9519\u8BCD\u7CBE\u7814"))
                            return
                        }
                        uni_navigateTo(NavigateToOptions(url = "/pages/wordInter/learning/test?isTraining=1&studyTaskId=" + (item.id ?: 0)))
                    }
                    if (_subModule == getSubModelKey("模式二：语法训练")) {
                        uni_navigateTo(NavigateToOptions(url = "/pages/grammarSync/test/test?isTraining=1&studyTaskId=" + (item.id ?: 0)))
                    }
                    if (_subModule == getSubModelKey("模式三：阅读训练")) {
                        uni_navigateTo(NavigateToOptions(url = "/pages/readSync/test/test?isTraining=1&studyTaskId=" + (item.id ?: 0)))
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){
                    uni_hideLoading()
                }
                ))
            }
            val startStudy = ::gen_startStudy_fn
            onPageShow(fun(){
                getList()
            }
            )
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "智练多多"), _uM("right" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cE("view", _uM("class" to "nav-r", "onClick" to fun(){
                                    goPath("/pages/training/finished-list")
                                }
                                , "style" to _nS(_uM("margin-right" to "13.48rpx"))), _uA(
                                    _cE("image", _uM("src" to "/static/ico/task/task-icon-1.png", "style" to _nS(_uM("width" to "20rpx", "height" to "20rpx")), "mode" to ""), null, 4),
                                    _cE("text", _uM("class" to "word_text"), "完成记录")
                                ), 12, _uA(
                                    "onClick"
                                )),
                                _cE("view", _uM("class" to "nav-r", "onClick" to fun(){
                                    goPath("/pages/training/error-list")
                                }
                                , "style" to _nS(_uM("background-color" to "#EFF2FF"))), _uA(
                                    _cE("image", _uM("src" to "/static/ico/task/task-icon-2.png", "style" to _nS(_uM("width" to "19.92rpx", "height" to "16.99rpx")), "mode" to ""), null, 4),
                                    _cE("text", _uM("class" to "word_text", "style" to _nS(_uM("color" to "#3A58EB"))), "错题统计", 4)
                                ), 12, _uA(
                                    "onClick"
                                ))
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "d_right"), _uA(
                                _cE("view", _uM("class" to "word_table"), _uA(
                                    _cE("list-view", _uM("direction" to "vertical", "class" to "word_table-body"), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(taskList), fun(item, __key, __index, _cached): Any {
                                            return _cE("list-item", _uM("class" to "word_table-item"), _uA(
                                                _cE("image", _uM("src" to "/static/ico/task/task-img.png", "style" to _nS(_uM("width" to "41.6rpx", "height" to "41.6rpx")), "mode" to ""), null, 4),
                                                _cE("view", _uM("class" to "word_table-item-text", "style" to _nS(_uM("min-width" to "160rpx"))), _uA(
                                                    _cE("text", _uM("class" to "word_table-item-text-title"), _tD(item.taskName), 1),
                                                    _cE("text", _uM("class" to "word_table-item-text-desc"), "任务名称")
                                                ), 4),
                                                _cE("view", _uM("class" to "word_table-item-text"), _uA(
                                                    _cE("text", _uM("class" to "word_table-item-text-title"), _tD(item.taskDate ?: "--"), 1),
                                                    _cE("text", _uM("class" to "word_table-item-text-desc"), "生成时间")
                                                )),
                                                _cE("view", _uM("class" to "word_table-item-text"), _uA(
                                                    _cE("text", _uM("class" to "word_table-item-text-title"), _tD(item.finishDate ?: "--"), 1),
                                                    _cE("text", _uM("class" to "word_table-item-text-desc"), "完成时间")
                                                )),
                                                _cE("view", _uM("class" to "word_table-item-text"), _uA(
                                                    _cE("text", _uM("class" to "word_table-item-text-title"), _tD(item.avgScore ?: "--"), 1),
                                                    _cE("text", _uM("class" to "word_table-item-text-desc"), "分数")
                                                )),
                                                _cE("view", _uM("style" to _nS(_uM("margin" to "0 20rpx 0 40rpx"))), _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "justify-content" to "center"))), _uA(
                                                        if (item?.finishStatus == 0) {
                                                            _cE("view", _uM("key" to 0, "class" to "word_table-body-item-status", "onClick" to fun(){
                                                                startStudy(item)
                                                            }), _uA(
                                                                _cE("text", _uM("class" to "word_table-body-item-status-text"), "未开始")
                                                            ), 8, _uA(
                                                                "onClick"
                                                            ))
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                        ,
                                                        if (item?.finishStatus == 1) {
                                                            _cE("view", _uM("key" to 1, "class" to "word_table-body-item-status", "style" to _nS(_uM("background-color" to "#CCE2EE")), "onClick" to fun(){
                                                                startStudy(item)
                                                            }), _uA(
                                                                _cE("text", _uM("class" to "word_table-body-item-status-text", "style" to _nS(_uM("color" to "#fff"))), "进行中", 4)
                                                            ), 12, _uA(
                                                                "onClick"
                                                            ))
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                        ,
                                                        if (item?.finishStatus == 2) {
                                                            _cE("view", _uM("key" to 2, "class" to "word_table-body-item-status", "style" to _nS(_uM("background-color" to "#ACDFD7"))), _uA(
                                                                _cE("text", _uM("class" to "word_table-body-item-status-text"), "已完成")
                                                            ), 4)
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                        ,
                                                        if (item?.finishStatus == -1) {
                                                            _cE("view", _uM("key" to 3, "class" to "word_table-body-item-status", "onClick" to fun(){
                                                                startStudy(item)
                                                            }, "style" to _nS(_uM("background-color" to "#ACDFD7"))), _uA(
                                                                _cE("text", _uM("class" to "word_table-body-item-status-text"), "重新开始")
                                                            ), 12, _uA(
                                                                "onClick"
                                                            ))
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                    ), 4)
                                                ), 4)
                                            ))
                                        }
                                        ), 256)
                                    ))
                                ))
                            ))
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "marginTop" to "13.48rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "d_right" to _uM(".d_content " to _uM("flex" to 1, "width" to "100%", "height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx")), "word_table" to _uM(".d_content .d_right " to _uM("marginTop" to "7.62rpx", "marginRight" to "7.62rpx", "marginBottom" to "7.62rpx", "marginLeft" to "7.62rpx", "flex" to 1)), "word_table-body" to _uM(".d_content .d_right .word_table " to _uM("flex" to 1, "paddingBottom" to "-5.86rpx")), "word_table-item" to _uM(".d_content .d_right .word_table .word_table-body " to _uM("marginBottom" to "5.86rpx", "height" to "58rpx", "backgroundImage" to "none", "backgroundColor" to "#F6F6F6", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to 0, "paddingRight" to "8rpx", "paddingBottom" to 0, "paddingLeft" to "8rpx")), "word_table-item-text" to _uM(".d_content .d_right .word_table .word_table-body .word_table-item " to _uM("flex" to 1)), "word_table-item-text-title" to _uM(".d_content .d_right .word_table .word_table-body .word_table-item .word_table-item-text " to _uM("height" to "21rpx", "fontWeight" to "700", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "18rpx", "textAlign" to "center")), "word_table-item-text-desc" to _uM(".d_content .d_right .word_table .word_table-body .word_table-item .word_table-item-text " to _uM("fontSize" to "9rpx", "color" to "#8A8A8A", "lineHeight" to "18rpx", "textAlign" to "center")), "word_table-body-item-status" to _uM(".d_content .d_right .word_table .word_table-body .word_table-item " to _uM("width" to "50rpx", "height" to "17.58rpx", "backgroundImage" to "none", "backgroundColor" to "#B0BFD9", "borderTopLeftRadius" to "17.58rpx", "borderTopRightRadius" to "17.58rpx", "borderBottomRightRadius" to "17.58rpx", "borderBottomLeftRadius" to "17.58rpx", "justifyContent" to "center", "alignItems" to "center")), "word_table-body-item-status-text" to _uM(".d_content .d_right .word_table .word_table-body .word_table-item " to _uM("fontSize" to "10.55rpx", "color" to "#818DCA")), "nav-r" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "width" to "100rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFEFD6", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx")), "word_text" to _uM(".nav-r " to _uM("fontWeight" to "700", "fontSize" to "13rpx", "color" to "#FA9600", "marginLeft" to "2.93rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
