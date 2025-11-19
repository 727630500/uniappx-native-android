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
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesWordBooksWordBooks : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesWordBooksWordBooks) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesWordBooksWordBooks
            val _cache = __ins.renderCache
            val ctx = createInnerAudioContext()
            val loading = ref<Boolean>(false)
            val pagingX = ref(null)
            val unitList = ref(_uA<AppTextbookUnitVo>())
            val isUnitAllChecked = ref<Boolean>(false)
            val hideEnglishText = ref<Boolean>(false)
            val hidePhoneticSymbol = ref<Boolean>(false)
            val hidechineseExplain = ref<Boolean>(false)
            val dataList = ref(_uA<AppProblemVo>())
            val searchValue = ref<String>("")
            val queryParams = ref(object : UTSJSONObject() {
                var textbookId = getTextBookId()
                var textbookUnitId = null
                var subjectType = "英语"
                var module = getModelKey("同步单词")
                var subModule = getSubModelKey("单词本")
                var isExercise = "0"
                var textbookUnitIdList = ""
                var englishText = ""
                var pageNum: Number = 1
                var pageSize: Number = 10
            })
            watch(unitList, fun(value: UTSArray<AppTextbookUnitVo>, oldvalue: UTSArray<AppTextbookUnitVo>){
                if (value != null && value.length > 0) {
                    var allChecked = true
                    value.forEach(fun(item){
                        if (item.checked == false) {
                            allChecked = false
                        } else {
                            ""
                        }
                    }
                    )
                    isUnitAllChecked.value = allChecked
                }
            }
            , WatchOptions(deep = true))
            watch(hideEnglishText, fun(){
                dataList.value.forEach(fun(item){
                    item.speakScore = 0
                    item.speakPassStatus = 0
                }
                )
            }
            )
            watch(hidePhoneticSymbol, fun(){
                dataList.value.forEach(fun(item){
                    item.speakScore = 0
                    item.speakPassStatus = 0
                }
                )
            }
            )
            watch(hidechineseExplain, fun(){
                dataList.value.forEach(fun(item){
                    item.speakScore = 0
                    item.speakPassStatus = 0
                }
                )
            }
            )
            val selectUnitIds = fun(): UTSArray<String> {
                var idList: UTSArray<String> = _uA()
                if (unitList.value != null && unitList.value.length > 0) {
                    unitList.value.forEach(fun(item){
                        if (item.checked == true) {
                            idList.push(item.id!!.toString(10))
                        } else {
                            ""
                        }
                    }
                    )
                }
                return idList
            }
            val showStrengthen = ref(false)
            val language = ref("英语")
            val category = ref("句子")
            val nowExplain = ref("")
            val nowIndex = ref(0)
            val recognitionSuccess = fun(data: recognitionSuccessType){
                uni_hideLoading()
                var _source = data.result.getString("total_score") ?: "0"
                var _sourceNum = parseInt(_source)
                showStrengthen.value = false
                dataList.value[nowIndex.value].speakScore = _sourceNum
                if (_sourceNum >= 60) {
                    dataList.value[nowIndex.value].speakPassStatus = 1
                } else {
                    dataList.value[nowIndex.value].speakPassStatus = 2
                }
            }
            fun gen_startLy_fn(item: AppProblemVo, index: Number) {
                nowIndex.value = index
                if (hidechineseExplain.value) {
                    val result = item.chineseExplain!!.match(UTSRegExp("[\u4E00-\u9FFF\u3400-\u4DBF\uF900-\uFAFF]", "g"))
                    var _endText = result?.join("")
                    nowExplain.value = _endText!!
                    language.value = "汉语"
                } else {
                    nowExplain.value = item.englishText!!
                    language.value = "英语"
                }
                showStrengthen.value = true
            }
            val startLy = ::gen_startLy_fn
            var timer: Number = -1
            fun gen_restList_fn() {
                if (timer > -1) {
                    clearTimeout(timer)
                }
                timer = setTimeout(fun(){
                    console.log("重载")
                    (pagingX.value as ZPagingXComponentPublicInstance).reload()
                }
                , 250)
            }
            val restList = ::gen_restList_fn
            fun gen_allUnitClick_fn() {
                isUnitAllChecked.value = !isUnitAllChecked.value
                if (unitList.value != null && unitList.value.length > 0) {
                    unitList.value.forEach(fun(item){
                        item.checked = isUnitAllChecked.value
                    }
                    )
                }
                restList()
            }
            val allUnitClick = ::gen_allUnitClick_fn
            fun gen_unitClick_fn(item: AppTextbookUnitVo) {
                item.checked = !item.checked!!
                restList()
            }
            val unitClick = ::gen_unitClick_fn
            fun gen_getUnitList_fn() {
                uni_request<Result<UTSArray<AppTextbookUnitVo>>>(RequestOptions(url = getUrl("/biz/textbookUnit/api/list"), method = "GET", header = getHeader(), data = object : UTSJSONObject() {
                    var textbookId = queryParams.value["textbookId"]
                    var module = queryParams.value["module"]
                    var subModule = queryParams.value["subModule"]
                    var pageNum: Number = 1
                    var pageSize: Number = 3000
                }, success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    var data = responseData.data
                    data?.forEach(fun(item){
                        item.checked = false
                    }
                    )
                    unitList.value = data!!
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getUnitList = ::gen_getUnitList_fn
            fun gen_queryList_fn(pageNo: Number, pageSize: Number) {
                queryParams.value["textbookUnitIdList"] = selectUnitIds().join(",")
                queryParams.value["englishText"] = searchValue.value
                queryParams.value["pageNum"] = pageNo
                queryParams.value["pageSize"] = pageSize
                uni_request<Result<UTSArray<AppProblemVo>>>(RequestOptions(url = getUrl("/biz/problem/api/list"), method = "GET", header = getHeader(), data = queryParams.value, success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    var data = responseData.data
                    data?.forEach(fun(item){
                        item.speakPassStatus = 0
                        item.speakScore = 0
                    }
                    )
                    (pagingX.value as ZPagingXComponentPublicInstance).complete(data!! as UTSArray<Any>)
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){
                    loading.value = false
                }
                ))
            }
            val queryList = ::gen_queryList_fn
            ctx.onCanplay(fun(_){
                ctx.play()
            }
            )
            fun gen_playSoundUrl_fn(item: AppProblemVo) {
                if (item.soundUrl == null || item.soundUrl!! == "") {
                    uni_showToast(ShowToastOptions(title = "音频文件缺失，无法播放", icon = "none"))
                    return
                }
                ctx.src = item.soundUrl!!
            }
            val playSoundUrl = ::gen_playSoundUrl_fn
            onLoad(fun(_options){
                loading.value = true
                getUnitList()
            }
            )
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_LearningInfo = resolveEasyComponent("LearningInfo", GenComponentsLearningInfoLearningInfoClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_checkbox = resolveComponent("checkbox")
                val _component_l_loading = resolveEasyComponent("l-loading", GenUniModulesLimeLoadingComponentsLLoadingLLoadingClass)
                val _component_z_paging_x = resolveEasyComponent("z-paging-x", GenUniModulesZPagingXComponentsZPagingXZPagingXClass)
                val _component_Recognition = resolveEasyComponent("Recognition", GenComponentsRecognitionRecognitionClass)
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "单词本"), _uM("right" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cE("view", _uM("class" to "search_box"), _uA(
                                    _cE("image", _uM("class" to "search_ico", "src" to "/static/ico/search.png")),
                                    _cE("input", _uM("modelValue" to unref(searchValue), "onInput" to _uA(
                                        fun(`$event`: UniInputEvent){
                                            trySetRefValue(searchValue, `$event`.detail.value)
                                        }
                                        ,
                                        fun(event: UniInputEvent){
                                            return restList()
                                        }
                                    ), "class" to "search_input", "placeholder" to "请输入单词查询"), null, 40, _uA(
                                        "modelValue",
                                        "onInput"
                                    ))
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
                            _cE("view", _uM("class" to "d_left"), _uA(
                                _cE("view", _uM("class" to "d_left_top"), _uA(
                                    _cV(_component_checkbox, _uM("checked" to unref(isUnitAllChecked), "class" to "checkbox-item", "onClick" to fun(){
                                        allUnitClick()
                                    }
                                    ), null, 8, _uA(
                                        "checked",
                                        "onClick"
                                    )),
                                    _cE("text", _uM("class" to "d_left_title"), "全部单元")
                                )),
                                _cE("scroll-view", _uM("class" to "unit_list", "direction" to "vertical"), _uA(
                                    _cE(Fragment, null, RenderHelpers.renderList(unref(unitList), fun(item, __key, __index, _cached): Any {
                                        return _cE("view", _uM("class" to "unit_item", "key" to item.id), _uA(
                                            _cV(_component_checkbox, _uM("checked" to item.checked, "class" to "checkbox-item", "onClick" to fun(){
                                                unitClick(item)
                                            }
                                            ), null, 8, _uA(
                                                "checked",
                                                "onClick"
                                            )),
                                            _cE("text", _uM("class" to "unit_item_title"), _tD(item.unitName), 1),
                                            _cE("text", _uM("class" to "unit_item_count"), _tD(item.problemTypeNumStat!!.problemType1Num) + "词", 1)
                                        ))
                                    }
                                    ), 128)
                                ))
                            )),
                            _cE("view", _uM("class" to "d_right"), _uA(
                                _cE("view", _uM("class" to "word_table"), _uA(
                                    _cE("view", _uM("class" to "word_table-header"), _uA(
                                        _cE("view", _uM("class" to "word_table-header-item"), _uA(
                                            _cV(_component_checkbox, _uM("class" to "checkbox-item", "checked" to unref(hideEnglishText), "onClick" to fun(){
                                                hideEnglishText.value = !unref(hideEnglishText)
                                            }
                                            ), null, 8, _uA(
                                                "checked",
                                                "onClick"
                                            )),
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "隐藏单词")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item"), _uA(
                                            _cV(_component_checkbox, _uM("class" to "checkbox-item", "checked" to unref(hidePhoneticSymbol), "onClick" to fun(){
                                                hidePhoneticSymbol.value = !unref(hidePhoneticSymbol)
                                            }
                                            ), null, 8, _uA(
                                                "checked",
                                                "onClick"
                                            )),
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "隐藏音标")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item"), _uA(
                                            _cV(_component_checkbox, _uM("class" to "checkbox-item", "checked" to unref(hidechineseExplain), "onClick" to fun(){
                                                hidechineseExplain.value = !unref(hidechineseExplain)
                                            }
                                            ), null, 8, _uA(
                                                "checked",
                                                "onClick"
                                            )),
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "隐藏中文")
                                        )),
                                        _cE("view", _uM("class" to "word_table-header-item", "style" to _nS(_uM("flex" to "0.5"))), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), _tD(if (unref(hidechineseExplain)) {
                                                "说出中文"
                                            } else {
                                                "说出英文"
                                            }
                                            ), 1)
                                        ), 4),
                                        _cE("view", _uM("class" to "word_table-header-item", "style" to _nS(_uM("flex" to "0.5"))), _uA(
                                            _cE("text", _uM("class" to "word_table-header-item-text"), "分数")
                                        ), 4)
                                    )),
                                    withDirectives(_cE("view", _uM("style" to _nS(_uM("justify-content" to "center", "align-items" to "center", "height" to "100%", "width" to "100%", "flex" to "1"))), _uA(
                                        _cV(_component_l_loading, _uM("color" to "#AEC3FC", "size" to "80rpx"))
                                    ), 4), _uA(
                                        _uA(
                                            vShow,
                                            unref(loading)
                                        )
                                    )),
                                    withDirectives(_cE("view", _uM("class" to "word_table-body"), _uA(
                                        _cV(_component_z_paging_x, _uM("ref_key" to "pagingX", "ref" to pagingX, "modelValue" to unref(dataList), "onUpdate:modelValue" to fun(`$event`: UTSArray<AppProblemVo>){
                                            trySetRefValue(dataList, `$event`)
                                        }
                                        , "onQuery" to queryList, "class" to "pagingX", "refresher-enabled" to false), _uM("top" to withSlotCtx(fun(): UTSArray<Any> {
                                            return _uA()
                                        }
                                        ), "loadMore" to withScopedSlotCtx(fun(slotProps: Record<String, Any?>): UTSArray<Any> {
                                            val loadMoreStatus = slotProps["loadMoreStatus"]
                                            return _uA(
                                                _cE("view", _uM("class" to "loadMore"), _uA(
                                                    if (loadMoreStatus == 1) {
                                                        _cE("text", _uM("key" to 0, "class" to "loadMore_text"), "加载中...")
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                    ,
                                                    if (loadMoreStatus == 2) {
                                                        _cE("text", _uM("key" to 1, "class" to "loadMore_text"), "没有更多数据了")
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ))
                                            )
                                        }
                                        ), "empty" to withScopedSlotCtx(fun(slotProps: Record<String, Any?>): UTSArray<Any> {
                                            val isLoadFailed = slotProps["isLoadFailed"]
                                            return _uA(
                                                _cE("view", _uM("class" to "z-pagng-empty"), _uA(
                                                    if (isLoadFailed == true) {
                                                        _cE("text", _uM("key" to 0, "class" to "loadMore_text"), "加载失败")
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                    ,
                                                    if (isLoadFailed == false) {
                                                        _cE("text", _uM("key" to 1, "class" to "loadMore_text"), "暂无数据")
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ))
                                            )
                                        }
                                        ), "default" to withSlotCtx(fun(): UTSArray<Any> {
                                            return _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(unref(dataList), fun(item, index, __index, _cached): Any {
                                                    return _cE("list-item", _uM("key" to index), _uA(
                                                        _cE("view", _uM("class" to "word_table-body-row", "style" to _nS("" + (if (((index + 1) % 2) == 0) {
                                                            "background-color: #F1F5FC"
                                                        } else {
                                                            ""
                                                        }
                                                        ))), _uA(
                                                            _cE("view", _uM("class" to "word_table-body-item"), _uA(
                                                                withDirectives(_cE("text", _uM("onClick" to fun(){
                                                                    playSoundUrl(item)
                                                                }
                                                                , "class" to "word_table-body-item-text", "style" to _nS(_uM("color" to "blue"))), _tD(item.englishText), 13, _uA(
                                                                    "onClick"
                                                                )), _uA(
                                                                    _uA(
                                                                        vShow,
                                                                        !unref(hideEnglishText)
                                                                    )
                                                                ))
                                                            )),
                                                            _cE("view", _uM("class" to "word_table-body-item"), _uA(
                                                                withDirectives(_cE("text", _uM("class" to "word_table-body-item-text"), _tD(item.phoneticSymbol), 513), _uA(
                                                                    _uA(
                                                                        vShow,
                                                                        !unref(hidePhoneticSymbol)
                                                                    )
                                                                ))
                                                            )),
                                                            _cE("view", _uM("class" to "word_table-body-item"), _uA(
                                                                withDirectives(_cE("text", _uM("class" to "word_table-body-item-text"), _tD(item.chineseExplain), 513), _uA(
                                                                    _uA(
                                                                        vShow,
                                                                        !unref(hidechineseExplain)
                                                                    )
                                                                ))
                                                            )),
                                                            _cE("view", _uM("class" to "word_table-body-item", "style" to _nS(_uM("flex" to "0.5"))), _uA(
                                                                _cE("image", _uM("src" to "/static/ico/voice.png", "style" to _nS(_uM("width" to "11.72rpx", "height" to "14.41rpx")), "mode" to "aspectFit", "onClick" to fun(){
                                                                    startLy(item, index)
                                                                }
                                                                ), null, 12, _uA(
                                                                    "onClick"
                                                                ))
                                                            ), 4),
                                                            _cE("view", _uM("class" to "word_table-body-item", "style" to _nS(_uM("flex" to "0.5"))), _uA(
                                                                if (item.speakPassStatus == 1) {
                                                                    _cE("image", _uM("key" to 0, "src" to "/static/ico/right.png", "style" to _nS(_uM("width" to "14.06rpx", "height" to "9.96rpx", "margin-right" to "2rpx")), "mode" to "aspectFit"), null, 4)
                                                                } else {
                                                                    _cC("v-if", true)
                                                                }
                                                                ,
                                                                if (item.speakPassStatus == 2) {
                                                                    _cE("image", _uM("key" to 1, "src" to "/static/ico/del.png", "style" to _nS(_uM("width" to "14.06rpx", "height" to "14.06rpx", "margin-right" to "2rpx")), "mode" to "aspectFit"), null, 4)
                                                                } else {
                                                                    _cC("v-if", true)
                                                                }
                                                                ,
                                                                if (item.speakPassStatus == 0) {
                                                                    _cE("text", _uM("key" to 2, "class" to "word_table-body-item-text"), "无")
                                                                } else {
                                                                    _cC("v-if", true)
                                                                }
                                                                ,
                                                                if (item.speakPassStatus != 0) {
                                                                    _cE("text", _uM("key" to 3, "class" to "word_table-body-item-text", "style" to _nS(_uM("color" to if (item.speakPassStatus == 1) {
                                                                        "#5A9F32"
                                                                    } else {
                                                                        "#FA9600"
                                                                    }))), _tD(item.speakScore), 5)
                                                                } else {
                                                                    _cC("v-if", true)
                                                                }
                                                            ), 4)
                                                        ), 4)
                                                    ))
                                                }
                                                ), 128)
                                            )
                                        }
                                        ), "_" to 1), 8, _uA(
                                            "modelValue"
                                        ))
                                    ), 512), _uA(
                                        _uA(
                                            vShow,
                                            !unref(loading)
                                        )
                                    ))
                                ))
                            ))
                        )),
                        if (isTrue(unref(showStrengthen))) {
                            _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("view", _uM("class" to "popup_content"), _uA(
                                        _cE("view", _uM("class" to "operation_box", "style" to _nS(_uM("margin-bottom" to "20rpx"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("margin" to "0 24.61rpx"))), _uA(
                                                _cV(_component_Recognition, _uM("onSuccess" to recognitionSuccess, "language" to unref(language), "category" to unref(category), "subject" to unref(nowExplain), "seconds" to 10), null, 8, _uA(
                                                    "language",
                                                    "category",
                                                    "subject"
                                                ))
                                            ), 4)
                                        ), 4)
                                    ))
                                )
                            }), "_" to 1))
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("height" to "351rpx", "marginTop" to "13.84rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "d_left" to _uM(".d_content " to _uM("width" to "179rpx", "height" to "329rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginRight" to "5.86rpx", "paddingTop" to "17.58rpx", "paddingRight" to "9.96rpx", "paddingBottom" to "17.58rpx", "paddingLeft" to "9.96rpx")), "d_left_top" to _uM(".d_content .d_left " to _uM("flexDirection" to "row")), "d_left_title" to _uM(".d_content .d_left " to _uM("fontWeight" to "700", "fontSize" to "14rpx", "color" to "#3D3D3D", "marginLeft" to "6.45rpx")), "unit_list" to _uM(".d_content .d_left " to _uM("marginTop" to "15.82rpx", "height" to "281rpx")), "unit_item" to _uM(".d_content .d_left .unit_list " to _uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "unit_item_title" to _uM(".d_content .d_left .unit_list .unit_item " to _uM("fontWeight" to "400", "fontSize" to "11.72rpx", "flex" to 1, "marginLeft" to "6.45rpx")), "unit_item_count" to _uM(".d_content .d_left .unit_list .unit_item " to _uM("fontWeight" to "400", "fontSize" to "11.72rpx", "color" to "#8BA0C4", "lineHeight" to "35rpx")), "d_right" to _uM(".d_content " to _uM("width" to "530rpx", "height" to "329rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx")), "word_table" to _uM(".d_content .d_right " to _uM("width" to "512.7rpx", "marginTop" to "11.72rpx", "marginRight" to "8.79rpx", "marginBottom" to "11.72rpx", "marginLeft" to "8.79rpx", "height" to "100%")), "title_box" to _pS(_uM("width" to "234rpx", "height" to "28rpx", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "marginLeft" to "196.29rpx")), "_text" to _uM(".title_box " to _uM("textAlign" to "center", "fontWeight" to "700", "fontSize" to "14rpx", "color" to "#535D8C", "lineHeight" to "28rpx")), "search_box" to _pS(_uM("width" to "210rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "boxShadow" to "0rpx 2rpx 4rpx 0rpx rgba(130, 141, 197, 0.3)", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "search_ico" to _uM(".search_box " to _uM("width" to "10rpx", "height" to "10rpx", "marginLeft" to "12rpx")), "search_input" to _uM(".search_box " to _uM("flex" to 1, "height" to "100%", "fontSize" to "14rpx", "color" to "#3a3a3a", "marginTop" to 0, "marginRight" to "12rpx", "marginBottom" to 0, "marginLeft" to "12rpx")), "word_table-header" to _uM(".word_table " to _uM("height" to "35.16rpx", "backgroundColor" to "#F1F5FC", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-header-item-text" to _uM(".word_table " to _uM("fontWeight" to "700", "fontSize" to "12rpx", "color" to "#3D3D3D", "marginLeft" to "6.45rpx")), "word_table-body-row" to _uM(".word_table " to _uM("height" to "45.16rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "word_table-body" to _uM(".word_table " to _uM("height" to "281rpx")), "word_table-header-item" to _uM(".word_table .word_table-header " to _uM("flexDirection" to "row", "flex" to 1, "alignItems" to "center", "justifyContent" to "center"), ".word_table .word_table-body-row " to _uM("flexDirection" to "row", "flex" to 1, "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item" to _uM(".word_table .word_table-header " to _uM("flexDirection" to "row", "flex" to 1, "alignItems" to "center", "justifyContent" to "center"), ".word_table .word_table-body-row " to _uM("flexDirection" to "row", "flex" to 1, "alignItems" to "center", "justifyContent" to "center")), "word_table-body-item-text" to _uM(".word_table " to _uM("fontFamily" to "Arial, Arial", "fontWeight" to "400", "fontSize" to "12rpx", "color" to "#3D3D3D", "textOverflow" to "ellipsis", "lines" to 2)), "z-pagng-empty" to _pS(_uM("justifyContent" to "center", "alignItems" to "center", "height" to "100%", "width" to "100%")), "loadMore" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "center", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx")), "loadMore_text" to _pS(_uM("color" to "#6694DF", "fontFamily" to "Arial, Arial", "fontSize" to "13rpx", "textAlign" to "left", "fontStyle" to "normal")), "popup_content" to _pS(_uM("width" to "100%", "height" to "100%", "alignItems" to "center", "justifyContent" to "flex-end")), "_num" to _uM(".popup_content " to _uM("width" to "56rpx", "height" to "56rpx", "borderTopLeftRadius" to "56rpx", "borderTopRightRadius" to "56rpx", "borderBottomRightRadius" to "56rpx", "borderBottomLeftRadius" to "56rpx", "backgroundImage" to "none", "backgroundColor" to "#FCF3D6", "fontWeight" to "bold", "fontSize" to "23rpx", "color" to "#FA9600", "lineHeight" to "56rpx", "textAlign" to "center")), "tips" to _uM(".popup_content " to _uM("fontSize" to "19rpx", "color" to "#FFDEAD", "lineHeight" to "35rpx", "marginTop" to "9.38rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
