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
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesUserChange : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesUserChange) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesUserChange
            val _cache = __ins.renderCache
            var isAll = ref<Boolean>(false)
            var pageSize = ref<Number>(10)
            var pageNum = ref<Number>(1)
            var textBookList = ref<UTSArray<treeLevelType>?>(null)
            var total = ref<Number?>(0)
            var versionBaseDataId = ref<Number?>(null)
            var gradeBaseDataId = ref<Number>(0)
            var versionBaseDataArr = ref(_uA<gradeType>())
            var gradeArr = ref<UTSArray<gradeType>?>(null)
            var classArr = ref(_uA<classType>())
            var gradeId = ref<Number?>(null)
            var classId = ref<Number?>(null)
            onReady(fun(){
                setScreen()
            }
            )
            val textBookId = ref(getTextBookId())
            fun gen_getMyTextbook_fn() {
                var data: UTSJSONObject = object : UTSJSONObject() {
                    var pageSize = pageSize.value
                    var pageNum = pageNum.value
                }
                uni_request<Result<UTSArray<treeLevelType>?>>(RequestOptions(url = getUrl("/biz/textbook/api/getMyLearnList"), method = "GET", data = data, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    total.value = responseData?.total
                    if (responseData.data == null) {
                        return
                    }
                    textBookList.value = responseData?.data
                }
                , fail = fun(err){
                    console.log(err)
                }
                ))
            }
            val getMyTextbook = ::gen_getMyTextbook_fn
            fun gen_getAllTextbook_fn() {
                var data: UTSJSONObject = object : UTSJSONObject() {
                    var pageSize = pageSize.value
                    var pageNum = pageNum.value
                    var versionBaseDataId = versionBaseDataId.value
                    var periodBaseDataId = gradeId.value
                    var gradeBaseDataId = classId.value
                }
                uni_request<Result<UTSArray<treeLevelType>?>>(RequestOptions(url = getUrl("/biz/textbook/api/list"), method = "GET", data = data, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    total.value = responseData?.total
                    if (responseData.data == null) {
                        return
                    }
                    textBookList.value = responseData?.data
                }
                , fail = fun(err){
                    console.log(err)
                }
                ))
            }
            val getAllTextbook = ::gen_getAllTextbook_fn
            fun gen_selectjc_fn(type: Number?) {
                versionBaseDataId.value = type
                getAllTextbook()
            }
            val selectjc = ::gen_selectjc_fn
            fun gen_selectClass_fn(id: Number?) {
                classId.value = id
                var item = classArr.value?.find(fun(item): Boolean {
                    return item.id == id
                }
                )
                if (item != null) {
                    versionBaseDataArr.value = item?.children as UTSArray<gradeType>
                    selectjc(versionBaseDataArr.value!![0].id)
                } else {
                    versionBaseDataArr.value = _uA()
                    textBookList.value = _uA()
                }
            }
            val selectClass = ::gen_selectClass_fn
            fun gen_selectGrade_fn(id: Number?) {
                gradeId.value = id
                var item = gradeArr.value?.find(fun(item): Boolean {
                    return item.id == id
                }
                )
                if (item != null) {
                    classArr.value = item.children as UTSArray<classType>
                    selectClass(classArr.value!![0].id)
                } else {
                    classArr.value = _uA()
                    versionBaseDataArr.value = _uA()
                    textBookList.value = _uA()
                }
            }
            val selectGrade = ::gen_selectGrade_fn
            fun gen_getClassData_fn() {
                uni_request<Result<UTSArray<gradeType>?>>(RequestOptions(url = getUrl("/biz/textbook/api/groupCascadeTreeLevel3"), method = "GET", header = getHeader(), success = fun(res){
                    val responseData = res?.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData?.data == null) {
                        return
                    }
                    gradeArr.value = responseData?.data!!
                    if (responseData?.data != null) {
                        selectGrade(responseData?.data!![0].id)
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                ))
            }
            val getClassData = ::gen_getClassData_fn
            fun gen_selectTextBook_fn(type: Number) {
                textBookList.value = null
                if (type == 1) {
                    isAll.value = false
                    getMyTextbook()
                } else {
                    isAll.value = true
                    getClassData()
                }
            }
            val selectTextBook = ::gen_selectTextBook_fn
            fun gen_selectSubmit_fn(item: treeLevelType) {
                uni_showLoading(ShowLoadingOptions(title = ""))
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/textbook/api/selectSubmit"), method = "POST", header = getHeader(), data = object : UTSJSONObject() {
                    var id = item.id
                }, success = fun(res){
                    textBookId.value = item.id!!
                    uni_showToast(ShowToastOptions(icon = "none", title = "切换成功"))
                    uni_setStorageSync("textbookId", item.id!!)
                    uni_navigateBack(null)
                }
                , fail = fun(err){
                    console.log(err)
                }
                ))
            }
            val selectSubmit = ::gen_selectSubmit_fn
            onPageShow(fun(){
                getMyTextbook()
            }
            )
            return fun(): Any? {
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "更换教材")),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            if (isTrue(unref(isAll))) {
                                _cE("view", _uM("key" to 0, "class" to "d_content_left"), _uA(
                                    _cE("view", _uM("class" to "left_item"), _uA(
                                        _cE("view", _uM("class" to "period_box", "style" to _nS(_uM("max-height" to "140rpx"))), _uA(
                                            _cE("text", _uM("class" to "period_title"), "学段"),
                                            _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                                _cE("view", null, _uA(
                                                    _cE(Fragment, null, RenderHelpers.renderList(unref(gradeArr), fun(item, __key, __index, _cached): Any {
                                                        return _cE("view", _uM("key" to item.id, "class" to _nC(_uA(
                                                            "period_item",
                                                            _uM("active" to (unref(gradeId) == item.id))
                                                        )), "onClick" to fun(){
                                                            selectGrade(item.id)
                                                        }), _uA(
                                                            _cE("text", _uM("class" to "period_text"), _tD(item.name), 1)
                                                        ), 10, _uA(
                                                            "onClick"
                                                        ))
                                                    }), 128)
                                                ))
                                            ), 4)
                                        ), 4),
                                        if (unref(classArr) != null) {
                                            _cE("view", _uM("key" to 0, "class" to "period_box", "style" to _nS(_uM("flex" to "1"))), _uA(
                                                _cE("text", _uM("class" to "period_title"), "年级"),
                                                _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                                    _cE("view", null, _uA(
                                                        _cE(Fragment, null, RenderHelpers.renderList(unref(classArr), fun(item, __key, __index, _cached): Any {
                                                            return _cE("view", _uM("key" to item.id, "class" to _nC(_uA(
                                                                "period_item",
                                                                _uM("active" to (unref(classId) == item.id))
                                                            )), "onClick" to fun(){
                                                                selectClass(item.id)
                                                            }), _uA(
                                                                _cE("text", _uM("class" to "period_text"), _tD(item.name), 1)
                                                            ), 10, _uA(
                                                                "onClick"
                                                            ))
                                                        }), 128)
                                                    ))
                                                ), 4)
                                            ), 4)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    )),
                                    _cE("view", _uM("class" to "period_box version_box"), _uA(
                                        _cE("text", _uM("class" to "period_title", "style" to _nS(_uM("text-align" to "left"))), "版本", 4),
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(versionBaseDataArr), fun(item, __key, __index, _cached): Any {
                                            return _cE("view", _uM("key" to item.id, "class" to _nC(_uA(
                                                "version_item",
                                                _uM("version_item_active" to (unref(versionBaseDataId) == item.id))
                                            )), "onClick" to fun(){
                                                selectjc(item.id)
                                            }), _uA(
                                                _cE("text", _uM("class" to _nC(_uA(
                                                    "period_text",
                                                    _uM("active" to (unref(versionBaseDataId) == item.id))
                                                ))), _tD(item.name), 3)
                                            ), 10, _uA(
                                                "onClick"
                                            ))
                                        }), 128)
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cE("view", _uM("class" to "d_content-right"), _uA(
                                _cE("view", _uM("class" to "textbook-header"), _uA(
                                    _cE("text", _uM("class" to "textbook-header-text"), "共 " + _tD(unref(total)) + " 本", 1),
                                    _cE("view", _uM("class" to "textbook-header-right"), _uA(
                                        _cE("view", _uM("class" to "textbook-header-right-btn"), _uA(
                                            _cE("text", _uM("class" to _nC(_uA(
                                                "textbook-header-right-btn-text",
                                                _uM("active" to !unref(isAll))
                                            )), "onClick" to fun(){
                                                selectTextBook(1)
                                            }
                                            ), "我的教材", 10, _uA(
                                                "onClick"
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "textbook-header-right-btn"), _uA(
                                            _cE("text", _uM("class" to _nC(_uA(
                                                "textbook-header-right-btn-text",
                                                _uM("active" to unref(isAll))
                                            )), "onClick" to fun(){
                                                selectTextBook(2)
                                            }
                                            ), "所有教材", 10, _uA(
                                                "onClick"
                                            ))
                                        ))
                                    ))
                                )),
                                _cE("scroll-view", _uM("class" to "d_scroll_view"), _uA(
                                    _cE("view", _uM("class" to "textbook-list"), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(textBookList), fun(item, index, __index, _cached): Any {
                                            return _cE("view", _uM("style" to _nS("" + (if (unref(isAll)) {
                                                "width:158rpx"
                                            } else {
                                                ""
                                            }
                                            )), "class" to "textbook-list-item-box", "key" to item.id), _uA(
                                                _cE("view", _uM("class" to "rt_ico_box"), _uA(
                                                    if (unref(textBookId) == item.id) {
                                                        _cE(Fragment, _uM("key" to 0), _uA(
                                                            _cE("image", _uM("src" to "/static/ico/red_ico.png", "mode" to "", "class" to "rt_ico")),
                                                            _cE("text", _uM("class" to "rt_txt"), " 正在学习 ")
                                                        ), 64)
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                    ,
                                                    if (isTrue(item.isCurrentLearn != null && unref(textBookId) != item.id)) {
                                                        _cE(Fragment, _uM("key" to 1), _uA(
                                                            _cE("image", _uM("src" to "/static/ico/blue_ico.png", "mode" to "", "class" to "rt_ico")),
                                                            _cE("text", _uM("class" to "rt_txt"), " 学习过 ")
                                                        ), 64)
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                )),
                                                _cE("view", _uM("class" to "textbook-list-item"), _uA(
                                                    _cE("image", _uM("src" to (item.picUrl ?: "/static/course_bg.png"), "mode" to "", "class" to "textbook-list-item-img"), null, 8, _uA(
                                                        "src"
                                                    )),
                                                    _cE("text", _uM("class" to "textbook-list-item-title"), _tD(item.textbookName) + " " + _tD(item.gradeBaseDataName), 1),
                                                    _cE("view", _uM("class" to "textbook-list-item-info"), _uA(
                                                        _cE("view", _uM("class" to "textbook-list-item-info-item"), _uA(
                                                            _cE("text", _uM("class" to "textbook-list-item-info-item-text1"), "单词："),
                                                            _cE("text", _uM("class" to "textbook-list-item-info-item-text2"), _tD(item.englishWordNum), 1)
                                                        )),
                                                        _cE("view", _uM("class" to "textbook-list-item-info-item"), _uA(
                                                            _cE("text", _uM("class" to "textbook-list-item-info-item-text1"), "句子："),
                                                            _cE("text", _uM("class" to "textbook-list-item-info-item-text2"), _tD(item.englishSentenceNum), 1)
                                                        )),
                                                        _cE("view", _uM("class" to "textbook-list-item-info-item", "style" to _nS(_uM("width" to "100%"))), _uA(
                                                            _cE("text", _uM("class" to "textbook-list-item-info-item-text1"), "同步训练："),
                                                            _cE("text", _uM("class" to "textbook-list-item-info-item-text2"), _tD(item.grammarTrainNum), 1)
                                                        ), 4)
                                                    )),
                                                    _cE("view", _uM("class" to "textbook-list-item-btn"), _uA(
                                                        _cE("text", _uM("class" to "textbook-list-item-btn-text", "onClick" to fun(){
                                                            selectSubmit(item)
                                                        }
                                                        ), "选择教材", 8, _uA(
                                                            "onClick"
                                                        ))
                                                    ))
                                                ))
                                            ), 4)
                                        }
                                        ), 128)
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "backgroundColor" to "rgba(255,255,255,0.6)", "flex" to 1, "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13rpx", "marginRight" to "17.58rpx", "marginBottom" to "17rpx", "marginLeft" to "17.58rpx", "flexDirection" to "row")), "d_content_left" to _pS(_uM("width" to "170rpx", "flexDirection" to "row", "paddingTop" to "14rpx")), "d_scroll_view" to _pS(_uM("paddingTop" to 0, "paddingRight" to "15rpx", "paddingBottom" to 0, "paddingLeft" to "15rpx", "flex" to 1, "boxSizing" to "border-box")), "textbook-header" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to "13.48rpx", "paddingRight" to "16.41rpx", "paddingBottom" to "13.48rpx", "paddingLeft" to "16.41rpx")), "textbook-header-text" to _pS(_uM("fontWeight" to "700", "fontSize" to "12rpx", "color" to "#244E93", "lineHeight" to "35rpx")), "textbook-header-right" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "textbook-header-right-btn" to _pS(_uM("marginLeft" to "6.45rpx", "width" to "84rpx", "height" to "25rpx", "backgroundImage" to "linear-gradient(to bottom, #F3F7FF, #D5DFF0)", "boxShadow" to "0rpx 1rpx 3rpx 0rpx rgba(36, 78, 147, 0.3)", "borderTopLeftRadius" to "21rpx", "borderTopRightRadius" to "21rpx", "borderBottomRightRadius" to "21rpx", "borderBottomLeftRadius" to "21rpx", "display" to "flex", "justifyContent" to "center", "alignItems" to "center")), "textbook-header-right-btn-text" to _uM("" to _uM("fontFamily" to "Arial, Arial", "fontWeight" to "400", "fontSize" to "12rpx", "color" to "#98A6EE"), ".active" to _uM("color" to "#3A58EB", "fontWeight" to "bold")), "textbook-list" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "paddingRight" to "-17rpx")), "textbook-list-item-box" to _pS(_uM("width" to "158rpx", "position" to "relative", "marginRight" to "17rpx")), "rt_ico_box" to _uM(".textbook-list-item-box " to _uM("position" to "absolute", "top" to 0, "right" to 0, "width" to "70.31rpx", "height" to "70.31rpx", "zIndex" to 9)), "rt_ico" to _uM(".textbook-list-item-box .rt_ico_box " to _uM("width" to "100%", "height" to "100%", "position" to "absolute", "top" to 0, "left" to 0)), "rt_txt" to _uM(".textbook-list-item-box .rt_ico_box " to _uM("position" to "relative", "zIndex" to 6, "fontSize" to "13rpx", "width" to "60rpx", "color" to "#FFFFFF", "fontWeight" to "bold", "textAlign" to "center", "transform" to "rotate(44.12deg) translate(14rpx, 16rpx)")), "textbook-list-item" to _pS(_uM("flex" to 1, "height" to "205rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "boxShadow" to "0rpx 2rpx 4rpx 0rpx rgba(130, 141, 197, 0.3)", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "paddingTop" to "12.3rpx", "paddingRight" to "14.65rpx", "paddingBottom" to "10rpx", "paddingLeft" to "14.65rpx", "position" to "relative", "marginTop" to "4rpx", "marginRight" to "4rpx", "marginBottom" to "22rpx", "marginLeft" to 0)), "textbook-list-item-img" to _pS(_uM("width" to "129rpx", "height" to "94rpx", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx")), "textbook-list-item-title" to _pS(_uM("marginTop" to "4.1rpx", "height" to "18rpx", "fontWeight" to "700", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "18rpx")), "textbook-list-item-info-item-text1" to _pS(_uM("fontSize" to "11rpx", "color" to "#7B7B7B", "lineHeight" to "18rpx")), "textbook-list-item-info" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "flexWrap" to "wrap")), "textbook-list-item-info-item" to _pS(_uM("width" to "50%", "display" to "flex", "flexDirection" to "row")), "textbook-list-item-info-item-text2" to _pS(_uM("fontSize" to "11rpx", "color" to "#5689DC", "lineHeight" to "18rpx")), "textbook-list-item-btn" to _pS(_uM("alignSelf" to "center", "width" to "76rpx", "height" to "25rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "borderTopLeftRadius" to "100rpx", "borderTopRightRadius" to "100rpx", "borderBottomRightRadius" to "100rpx", "borderBottomLeftRadius" to "100rpx", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "marginTop" to "5.86rpx")), "textbook-list-item-btn-text" to _pS(_uM("fontWeight" to "700", "fontSize" to "13rpx", "color" to "#FFFFFF")), "d_content-right" to _pS(_uM("flex" to 1)), "left_item" to _pS(_uM("width" to "79rpx", "flexDirection" to "column", "alignItems" to "center")), "period_title" to _pS(_uM("height" to "35rpx", "fontWeight" to "700", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx", "textAlign" to "center")), "period_item" to _uM("" to _uM("minWidth" to "50rpx", "paddingTop" to "6rpx", "paddingRight" to 0, "paddingBottom" to "6rpx", "paddingLeft" to 0, "backgroundImage" to "none", "backgroundColor" to "#6694DF", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "alignItems" to "center", "justifyContent" to "center", "marginBottom" to "6.45rpx"), ".active" to _uM("backgroundColor" to "#80C757")), "period_text" to _uM(".period_item.active " to _uM("!color" to "#FFFFFF", "fontWeight" to "700"), "" to _uM("fontSize" to "12rpx", "color" to "#FFFFFF"), ".version_box .version_item " to _uM("color" to "#244E93"), ".version_box .version_item .active" to _uM("color" to "#ffffff")), "version_box" to _pS(_uM("width" to "102rpx", "paddingRight" to "10rpx")), "version_item" to _uM(".version_box " to _uM("flexDirection" to "row", "backgroundImage" to "none", "backgroundColor" to "#6694DF", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "paddingTop" to "6rpx", "paddingRight" to "6rpx", "paddingBottom" to "6rpx", "paddingLeft" to "6rpx", "marginBottom" to "6.45rpx"), ".version_box .version_item_active" to _uM("backgroundColor" to "#80C757", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
