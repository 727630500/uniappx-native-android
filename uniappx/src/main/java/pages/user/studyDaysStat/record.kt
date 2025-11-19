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
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesUserStudyDaysStatRecord : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesUserStudyDaysStatRecord) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesUserStudyDaysStatRecord
            val _cache = __ins.renderCache
            val loading = ref<Boolean>(false)
            val weekDays = _uA(
                "Mon",
                "Tue",
                "Wed",
                "Thu",
                "Fri",
                "Sat",
                "Sun"
            ) as UTSArray<String>
            val days = ref<UTSArray<UTSArray<DayInfo>>?>(null)
            val userStudyDaysStat = ref<AppUserStudyDaysStat?>(null)
            val selectedDate = ref(Date())
            val displayYear = ref(selectedDate.value.getFullYear())
            val displayMonth = ref(selectedDate.value.getMonth())
            val userStudyStat = ref<UTSArray<UserStudyStatGroupByMonthVo>?>(null)
            val userStudyStatMonth = ref<UserStudyStatGroupByMonthVo?>(null)
            val isStudied = fun(dateStr: String?): Boolean {
                if (dateStr == null) {
                    return false
                }
                if (userStudyStatMonth.value == null) {
                    return false
                }
                var index = userStudyStatMonth.value!!.voList!!.findIndex(fun(item): Boolean {
                    return item.recordDate == dateStr
                }
                )
                if (index == -1) {
                    return false
                }
                return true
            }
            val selectMonthStudyDayNum = computed(fun(): Number {
                if (userStudyStatMonth.value != null) {
                    return userStudyStatMonth.value!!.voList!!.length
                }
                return 0
            }
            )
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
                return Date(year, month, 1).getDay()
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
            fun gen_getUserStudyDaysStat_fn() {
                uni_request<Result<AppUserStudyDaysStat?>>(RequestOptions(url = getUrl("/biz/userStudyStat/api/getUserStudyDaysStat"), method = "GET", header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    userStudyDaysStat.value = responseData.data ?: null
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getUserStudyDaysStat = ::gen_getUserStudyDaysStat_fn
            fun gen_getUserRecordListByMonth_fn(yearMonth: String) {
                if (loading.value == true) {
                    return
                }
                loading.value = true
                uni_request<Result<UTSArray<UserStudyStatGroupByMonthVo>>>(RequestOptions(url = getUrl("/biz/userStudyStat/api/myListGroupByMonth"), method = "GET", header = getHeader(), data = object : UTSJSONObject() {
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
                    userStudyStat.value = responseData.data
                    if (userStudyStat.value != null && (userStudyStat.value as UTSArray<UserStudyStatGroupByMonthVo>).length > 0) {
                        userStudyStatMonth.value = userStudyStat.value!!?.get(0)
                    } else {
                        userStudyStatMonth.value = null
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
            val getUserRecordListByMonth = ::gen_getUserRecordListByMonth_fn
            fun gen_isSelected_fn(dayInfo: DayInfo): Boolean {
                if (dayInfo.date === 0) {
                    return false
                }
                val date = selectedDate.value
                return date.getFullYear() === displayYear.value && date.getMonth() === displayMonth.value && date.getDate() === Math.abs(dayInfo.date)
            }
            val isSelected = ::gen_isSelected_fn
            fun gen_changeMonth_fn(delta: Number): Unit {
                if (loading.value) {
                    return
                }
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
                getUserRecordListByMonth(month)
            }
            val changeMonth = ::gen_changeMonth_fn
            onReady(fun(){
                setScreen()
            }
            )
            onLoad(fun(_options){
                days.value = getDays()
                getUserStudyDaysStat()
                var month = formatDateToYYYYMM(selectedDate.value)
                getUserRecordListByMonth(month)
            }
            )
            return fun(): Any? {
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_l_loading = resolveEasyComponent("l-loading", GenUniModulesLimeLoadingComponentsLLoadingLLoadingClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "学习天数累计")),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "calendar"), _uA(
                                _cE("view", _uM("class" to "calendar-title-wrap"), _uA(
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
                                        )),
                                        _cE("view", _uM("class" to "studyDaysInfo"), _uA(
                                            _cE("text", _uM("class" to "studyDaysInfo_text"), "学习"),
                                            _cE("view", null, _uA(
                                                _cE("image", _uM("src" to "/static/user/studyDaysStat/rili.png", "class" to "riliImage")),
                                                _cE("text", _uM("class" to "studyDays"), _tD(unref(selectMonthStudyDayNum)), 1)
                                            )),
                                            _cE("text", _uM("class" to "studyDaysInfo_text"), "天")
                                        ))
                                    )),
                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                        _cE("view", _uM("style" to _nS(_uM("padding" to "0 20rpx"))), _uA(
                                            _cE("view", _uM("class" to "calendar-title_right_box"), _uA(
                                                _cE("text", _uM("class" to "text1"), "学习总天数:"),
                                                _cE("text", _uM("class" to "text2"), _tD(unref(userStudyDaysStat)?.userStudyStatNum), 1)
                                            ))
                                        ), 4),
                                        _cE("view", _uM("style" to _nS(_uM("padding" to "0 20rpx"))), _uA(
                                            _cE("view", _uM("class" to "calendar-title_right_box"), _uA(
                                                _cE("text", _uM("class" to "text1"), "本月学习天数:"),
                                                _cE("text", _uM("class" to "text3"), _tD(unref(userStudyDaysStat)?.currentMonthUserStudyStatNum), 1)
                                            ))
                                        ), 4)
                                    ), 4)
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
                                                )), "key" to dayInfo.date), _uA(
                                                    _cE("text", _uM("class" to "day-text"), _tD(getDayText(dayInfo)), 1),
                                                    if (isTrue(isStudied(dayInfo.dateStr))) {
                                                        _cE("image", _uM("key" to 0, "class" to "day-study", "src" to "/static/ico/user/renwu_icon.png"))
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ), 2)
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "13rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx", "paddingTop" to 0, "paddingRight" to "10rpx", "paddingBottom" to 0, "paddingLeft" to "10rpx")), "calendar" to _pS(_uM("width" to "100%", "height" to "100%")), "calendar-title" to _pS(_uM("flexDirection" to "row", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "height" to "35rpx")), "month-text" to _uM(".calendar-title " to _uM("width" to "60rpx", "fontWeight" to "700", "fontSize" to "15rpx", "color" to "#3D3D3D", "marginTop" to 0, "marginRight" to "8rpx", "marginBottom" to 0, "marginLeft" to "8rpx")), "arrow" to _uM(".calendar-title " to _uM("width" to "19.34rpx", "height" to "19.34rpx")), "calendar-title_right_box" to _pS(_uM("width" to "137rpx", "height" to "32rpx", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx", "flexDirection" to "row", "justifyContent" to "center", "alignItems" to "center")), "text1" to _uM(".calendar-title_right_box " to _uM("fontSize" to "14rpx", "color" to "#244E93", "lineHeight" to "32rpx", "textAlign" to "left", "fontStyle" to "normal", "paddingTop" to 0, "paddingRight" to "6rpx", "paddingBottom" to 0, "paddingLeft" to "6rpx")), "text2" to _uM(".calendar-title_right_box " to _uM("fontSize" to "14rpx", "color" to "#5a9f32", "lineHeight" to "32rpx", "textAlign" to "left", "fontStyle" to "normal", "paddingTop" to 0, "paddingRight" to "6rpx", "paddingBottom" to 0, "paddingLeft" to "6rpx")), "text3" to _uM(".calendar-title_right_box " to _uM("fontSize" to "14rpx", "color" to "#FA9600", "lineHeight" to "32rpx", "textAlign" to "left", "fontStyle" to "normal", "paddingTop" to 0, "paddingRight" to "6rpx", "paddingBottom" to 0, "paddingLeft" to "6rpx")), "calendar-header" to _pS(_uM("display" to "flex", "flexDirection" to "row", "height" to "35rpx", "backgroundColor" to "#F1F5FC", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx")), "weekday" to _uM(".calendar-header " to _uM("width" to "14.2857142857%", "textAlign" to "center", "fontSize" to "12rpx", "color" to "#333333", "lineHeight" to "35rpx")), "calendar-body" to _pS(_uM("display" to "flex", "flexWrap" to "wrap", "flexDirection" to "row")), "week" to _uM(".calendar-body " to _uM("display" to "flex", "flexDirection" to "row", "width" to "100%", "paddingTop" to "6.45rpx")), "day" to _uM(".calendar-body " to _uM("width" to "14.2857142857%", "height" to "36rpx", "display" to "flex", "alignItems" to "center"), ".calendar-body .other-month" to _uM("opacity" to 0.3)), "day-text" to _uM(".calendar-body " to _uM("fontSize" to "13rpx", "color" to "#3D3D3D")), "day-study" to _uM(".calendar-body " to _uM("width" to "18rpx", "height" to "18rpx")), "calendar-title-wrap" to _pS(_uM("marginTop" to "5rpx", "marginRight" to 0, "marginBottom" to "5rpx", "marginLeft" to 0, "height" to "27rpx", "display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "calendar-title-wrap-info" to _pS(_uM("fontWeight" to "700", "fontSize" to "12rpx", "color" to "#6694DF", "lineHeight" to "27rpx")), "riliImage" to _pS(_uM("height" to "34rpx", "width" to "34rpx")), "studyDays" to _pS(_uM("position" to "absolute", "left" to "9rpx", "fontSize" to "14rpx", "color" to "#FA9600", "lineHeight" to "35rpx", "width" to "20rpx", "textAlign" to "center", "fontStyle" to "normal", "fontWeight" to "bold", "zIndex" to 99)), "studyDaysInfo" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginLeft" to "20rpx")), "studyDaysInfo_text" to _pS(_uM("fontSize" to "14rpx", "color" to "#6694DF", "lineHeight" to "35rpx", "textAlign" to "left", "fontStyle" to "normal", "paddingTop" to 0, "paddingRight" to "8rpx", "paddingBottom" to 0, "paddingLeft" to "8rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
