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
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesMyTaskRecord : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesMyTaskRecord) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesMyTaskRecord
            val _cache = __ins.renderCache
            val loading = ref<Boolean>(false)
            val weekDays = _uA(
                "一",
                "二",
                "三",
                "四",
                "五",
                "六",
                "日"
            ) as UTSArray<String>
            val days = ref<UTSArray<UTSArray<DayInfo>>?>(null)
            val userTaskInfoList = ref<UTSArray<RecentStudyTaskInfoVo>?>(null)
            val selectMonthFinishTaskNum = computed(fun(): Number {
                if (userTaskInfoList.value != null && (userTaskInfoList.value as UTSArray<RecentStudyTaskInfoVo>).length > 0) {
                    return userTaskInfoList.value!!.map(fun(item): Number {
                        return item.finishNum
                    }
                    ).reduce(fun(acc, current): Number {
                        return acc + current
                    }
                    , 0)
                }
                return 0
            }
            )
            val getTaskInfoVo = fun(dateStr: String?): RecentStudyTaskInfoVo? {
                if (dateStr == null) {
                    return null
                }
                if (userTaskInfoList.value == null || (userTaskInfoList.value as UTSArray<RecentStudyTaskInfoVo>).length == 0) {
                    return null
                }
                var infoVo: RecentStudyTaskInfoVo? = userTaskInfoList.value!!.find(fun(item): Boolean {
                    return item.taskDate == dateStr
                }
                )
                if (infoVo == null) {
                    return null
                }
                return infoVo
            }
            val selectedDate = ref(Date())
            val displayYear = ref(selectedDate.value.getFullYear())
            val displayMonth = ref(selectedDate.value.getMonth())
            fun gen_formatDateToYYYYMM_fn(date: Date): String {
                val year = date.getFullYear()
                val month = (date.getMonth() + 1).toString(10).padStart(2, "0")
                return "" + year + "-" + month
            }
            val formatDateToYYYYMM = ::gen_formatDateToYYYYMM_fn
            fun gen_formatDateToYYYYMMDD_fn(date: Date): String {
                val year = date.getFullYear()
                val month = (date.getMonth() + 1).toString(10).padStart(2, "0")
                val day = date.getDate().toString(10).padStart(2, "0")
                return "" + year + "-" + month + "-" + day
            }
            val formatDateToYYYYMMDD = ::gen_formatDateToYYYYMMDD_fn
            fun gen_daysOfMonth_fn(year: Number, month: Number): Number {
                return Date(year, month + 1, 0).getDate()
            }
            val daysOfMonth = ::gen_daysOfMonth_fn
            fun gen_firstDayOfMonth_fn(year: Number, month: Number): Number {
                val day = Date(year, month, 1).getDay()
                return if (day == 0) {
                    7
                } else {
                    day
                }
            }
            val firstDayOfMonth = ::gen_firstDayOfMonth_fn
            fun gen_getDays_fn(): UTSArray<UTSArray<DayInfo>> {
                val days: UTSArray<DayInfo> = _uA()
                val totalDays = daysOfMonth(displayYear.value, displayMonth.value)
                val firstDay = firstDayOfMonth(displayYear.value, displayMonth.value)
                val prevMonth = displayMonth.value - 1
                val prevYear = displayYear.value
                val prevMonthDays = daysOfMonth(if (prevMonth < 0) {
                    prevYear - 1
                } else {
                    prevYear
                }
                , if (prevMonth < 0) {
                    11
                } else {
                    prevMonth
                }
                )
                val startPadding = firstDay - 1
                run {
                    var i: Number = 0
                    while(i < startPadding){
                        val day = prevMonthDays - startPadding + i + 1
                        days.push(DayInfo(date = -1 * day, day = day, extinfo = extinfo(info = null), dateStr = ""))
                        i++
                    }
                }
                var tempTime = Date()
                tempTime.setFullYear(displayYear.value)
                tempTime.setMonth(displayMonth.value)
                run {
                    var i: Number = 1
                    while(i <= totalDays){
                        tempTime.setDate(i)
                        days.push(DayInfo(date = i, day = i, extinfo = extinfo(info = null), dateStr = formatDateToYYYYMMDD(tempTime)))
                        i++
                    }
                }
                val remainingDays = 42 - days.length
                run {
                    var i: Number = 1
                    while(i <= remainingDays){
                        days.push(DayInfo(date = -100 - i, day = i, extinfo = extinfo(info = null), dateStr = ""))
                        i++
                    }
                }
                val weeks: UTSArray<UTSArray<DayInfo>> = _uA()
                run {
                    var i: Number = 0
                    while(i < days.length){
                        weeks.push(days.slice(i, i + 7))
                        i += 7
                    }
                }
                return weeks
            }
            val getDays = ::gen_getDays_fn
            fun gen_getDayText_fn(dayInfo: DayInfo): String {
                val dayNum = Math.abs(dayInfo.day)
                return if (dayNum < 10) {
                    "0" + dayNum
                } else {
                    dayNum.toString(10)
                }
            }
            val getDayText = ::gen_getDayText_fn
            fun gen_goQuery_fn(dayInfo: DayInfo) {
                if (getTaskInfoVo(dayInfo.dateStr) != null && getTaskInfoVo(dayInfo.dateStr)?.rate != 100) {
                    ucsShare.setState("queryDate", dayInfo.dateStr ?: "")
                    uni_navigateBack(null)
                }
            }
            val goQuery = ::gen_goQuery_fn
            fun gen_getUserTaskInfoListByMonth_fn(yearMonth: String) {
                if (loading.value == true) {
                    return
                }
                loading.value = true
                uni_request<Result<UTSArray<RecentStudyTaskInfoVo>>>(RequestOptions(url = getUrl("/biz/studyTask/api/myStudyTaskInfoByMonth"), method = "GET", header = getHeader(), data = object : UTSJSONObject() {
                    var month = yearMonth
                }, success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    userTaskInfoList.value = responseData.data
                    if (userTaskInfoList.value != null || (userTaskInfoList.value!! as UTSArray<RecentStudyTaskInfoVo>).length > 0) {
                        userTaskInfoList.value!!.forEach(fun(item){
                            var rate: Number = item.finishNum!! * 100 / item.taskNum!!
                            item.rate = Math.floor(rate)
                        }
                        )
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){
                    loading.value = false
                }
                ))
            }
            val getUserTaskInfoListByMonth = ::gen_getUserTaskInfoListByMonth_fn
            fun gen_changeMonth_fn(delta: Number): Unit {
                var newMonth = displayMonth.value + delta
                if (newMonth > 11) {
                    displayMonth.value = 0
                    displayYear.value += 1
                } else if (newMonth < 0) {
                    displayMonth.value = 11
                    displayYear.value -= 1
                } else {
                    displayMonth.value = newMonth
                }
                selectedDate.value.setFullYear(displayYear.value)
                selectedDate.value.setMonth(displayMonth.value)
                days.value = getDays()
                var month = formatDateToYYYYMM(selectedDate.value)
                getUserTaskInfoListByMonth(month)
            }
            val changeMonth = ::gen_changeMonth_fn
            fun gen_isSelected_fn(dayInfo: DayInfo): Boolean {
                if (dayInfo.date === 0) {
                    return false
                }
                val date = selectedDate.value
                return date.getFullYear() === displayYear.value && date.getMonth() === displayMonth.value && date.getDate() === Math.abs(dayInfo.date)
            }
            val isSelected = ::gen_isSelected_fn
            onReady(fun(){
                setScreen()
            }
            )
            onLoad(fun(_options){
                days.value = getDays()
                var month = formatDateToYYYYMM(selectedDate.value)
                getUserTaskInfoListByMonth(month)
            }
            )
            return fun(): Any? {
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_l_loading = resolveEasyComponent("l-loading", GenUniModulesLimeLoadingComponentsLLoadingLLoadingClass)
                val _component_l_circle = resolveEasyComponent("l-circle", GenUniModulesLimeCircleComponentsLCircleLCircleClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "任务记录")),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "calendar"), _uA(
                                _cE("view", _uM("class" to "calendar-title-wrap"), _uA(
                                    _cE("text", _uM("class" to "calendar-title-wrap-info"), "本月完成任务：" + _tD(unref(selectMonthFinishTaskNum)), 1),
                                    _cE("view", _uM("class" to "calendar-title"), _uA(
                                        _cE("image", _uM("src" to "/static/ico/user/jiantou_icon.png", "style" to _nS(_uM("transform" to "rotate(180deg)")), "class" to "arrow", "onClick" to fun(){
                                            changeMonth(-1)
                                        }
                                        ), null, 12, _uA(
                                            "onClick"
                                        )),
                                        _cE("text", _uM("class" to "month-text"), _tD(unref(displayYear)) + "-" + _tD(unref(displayMonth) + 1), 1),
                                        _cE("image", _uM("src" to "/static/ico/user/jiantou_icon.png", "class" to "arrow", "onClick" to fun(){
                                            changeMonth(1)
                                        }
                                        ), null, 8, _uA(
                                            "onClick"
                                        ))
                                    ))
                                )),
                                withDirectives(_cE("view", _uM("style" to _nS(_uM("justify-content" to "center", "align-items" to "center", "height" to "100%", "width" to "100%"))), _uA(
                                    _cV(_component_l_loading, _uM("color" to "#AEC3FC", "size" to "80rpx"))
                                ), 4), _uA(
                                    _uA(
                                        vShow,
                                        unref(loading)
                                    )
                                )),
                                withDirectives(_cE("view", _uM("class" to "calendar-header"), _uA(
                                    _cE(Fragment, null, RenderHelpers.renderList(weekDays, fun(day, __key, __index, _cached): Any {
                                        return _cE("text", _uM("class" to "weekday", "key" to day), _tD(day), 1)
                                    }
                                    ), 64)
                                ), 512), _uA(
                                    _uA(
                                        vShow,
                                        !unref(loading)
                                    )
                                )),
                                _cE("view", _uM("class" to "calendar-body"), _uA(
                                    _cE(Fragment, null, RenderHelpers.renderList(unref(days), fun(week, index, __index, _cached): Any {
                                        return _cE("view", _uM("class" to "week", "key" to week, "style" to _nS(_uM("backgroundColor" to if (((index + 1) % 2) === 0) {
                                            "#EFF2FF"
                                        } else {
                                            "#fff"
                                        }
                                        ))), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(week, fun(dayInfo, __key, __index, _cached): Any {
                                                return _cE("view", _uM("class" to _nC(_uA(
                                                    "day",
                                                    _uM("other-month" to (dayInfo.date < 0), "selected" to isSelected(dayInfo))
                                                )), "key" to dayInfo.date, "onClick" to fun(){
                                                    goQuery(dayInfo)
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to "day-text"), _tD(getDayText(dayInfo)), 1),
                                                    if (getTaskInfoVo(dayInfo.dateStr)?.rate == 100) {
                                                        _cE("image", _uM("key" to 0, "class" to "day-finish", "src" to "/static/ico/success_ico.png"))
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                    ,
                                                    if (isTrue(getTaskInfoVo(dayInfo.dateStr) != null && getTaskInfoVo(dayInfo.dateStr)?.rate != 100)) {
                                                        _cV(_component_l_circle, _uM("key" to 1, "percent" to getTaskInfoVo(dayInfo.dateStr)!!.rate, "size" to "24", "strokeWidth" to "1", "trailWidth" to "1"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                            return _uA(
                                                                _cE("text", _uM("style" to _nS(_uM("font-size" to "10px", "color" to "#5689DC"))), _tD(getTaskInfoVo(dayInfo.dateStr)?.rate as Number) + "%", 5)
                                                            )
                                                        }), "_" to 2), 1032, _uA(
                                                            "percent"
                                                        ))
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ), 10, _uA(
                                                    "onClick"
                                                ))
                                            }
                                            ), 128)
                                        ), 4)
                                    }
                                    ), 128)
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "paddingTop" to 0, "paddingRight" to "10rpx", "paddingBottom" to 0, "paddingLeft" to "10rpx")), "calendar" to _pS(_uM("width" to "100%", "height" to "100%")), "calendar-title" to _pS(_uM("flexDirection" to "row", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "height" to "35rpx")), "month-text" to _uM(".calendar-title " to _uM("fontWeight" to "700", "fontSize" to "15rpx", "color" to "#3D3D3D", "marginTop" to 0, "marginRight" to "8rpx", "marginBottom" to 0, "marginLeft" to "8rpx")), "arrow" to _uM(".calendar-title " to _uM("width" to "19.34rpx", "height" to "19.34rpx")), "calendar-header" to _pS(_uM("display" to "flex", "flexDirection" to "row", "height" to "35rpx", "backgroundColor" to "#F1F5FC", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx")), "weekday" to _uM(".calendar-header " to _uM("width" to "14.2857142857%", "textAlign" to "center", "fontSize" to "12rpx", "color" to "#333333", "lineHeight" to "35rpx")), "calendar-body" to _pS(_uM("display" to "flex", "flexWrap" to "wrap", "flexDirection" to "row")), "week" to _uM(".calendar-body " to _uM("display" to "flex", "flexDirection" to "row", "width" to "100%", "paddingTop" to "6.45rpx")), "day" to _uM(".calendar-body " to _uM("width" to "14.2857142857%", "height" to "36rpx", "display" to "flex", "alignItems" to "center"), ".calendar-body .other-month" to _uM("opacity" to 0.3)), "day-text" to _uM(".calendar-body " to _uM("fontSize" to "13rpx", "color" to "#3D3D3D")), "calendar-title-wrap" to _pS(_uM("marginTop" to "5rpx", "marginRight" to 0, "marginBottom" to "5rpx", "marginLeft" to 0, "height" to "27rpx", "display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "calendar-title-wrap-info" to _pS(_uM("fontWeight" to "700", "fontSize" to "12rpx", "color" to "#6694DF", "lineHeight" to "27rpx")), "day-finish" to _pS(_uM("width" to "20rpx", "height" to "20rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
