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
import io.dcloud.uniapp.extapi.downloadFile as uni_downloadFile
import io.dcloud.uniapp.extapi.getAppBaseInfo as uni_getAppBaseInfo
import io.dcloud.uniapp.extapi.installApk as uni_installApk
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesUserIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesUserIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesUserIndex
            val _cache = __ins.renderCache
            val userInfo = ref<UserModel?>(null)
            val defaultAvatar = ref<String>("/static/user/defaultAvatar.png")
            val aboutMeShow = ref<Boolean>(false)
            val contactModeShow = ref<Boolean>(false)
            val loading = ref<Boolean>(false)
            val aboutMeConfigText = ref<String>("")
            val contactModeText = ref<String>("")
            val contactModeValueText = ref<String>("")
            val appUrl = ref("")
            val verDescribe = ref("")
            val appVerson = ref("")
            val showUp = ref(false)
            val percent = ref(0)
            val updateBtn = ref(true)
            val downloadedSize = ref(0)
            val packageFileSize = ref(0)
            val showInfo = ref(false)
            val showUpdate = ref(false)
            val isForceUpdate = ref("1")
            val appOptInfo = ref(uni_getAppBaseInfo(null))
            val virtualKeyBoardStatus = ref<Boolean>(ucsShare.getStore("isShowKeyboard") as Boolean)
            val virtualKeyBoardChange = fun(e: Boolean){
                console.log("键盘开关改变: " + e)
                ucsShare.setStore("isShowKeyboard", e)
            }
            fun gen_getUserInfo2_fn() {
                uni_request<Result<UserModel?>>(RequestOptions(url = getUrl("/biz/studentUser/api/userInfo"), method = "GET", header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    userInfo.value = responseData.data ?: null
                    if (userInfo.value != null) {
                        updateUserState(userInfo.value!!)
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getUserInfo2 = ::gen_getUserInfo2_fn
            fun gen_getAppUp_fn() {
                val appOpt = uni_getAppBaseInfo(null)
                uni_request<Result<upData2>>(RequestOptions(url = getUrl("/api/general/appAndroidUpdate"), method = "GET", data = UTSJSONObject(), header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        uni_showToast(ShowToastOptions(title = "暂无新版本", icon = "none"))
                        return
                    }
                    if (responseData.code as Number != 200) {
                        if (responseData.code == 401) {
                            uni_removeStorageSync("token")
                            uni_removeStorageSync("tenantCode")
                            uni_removeStorageSync("storeId")
                            uni_reLaunch(ReLaunchOptions(url = "/pages/login/login"))
                        }
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    val _data = responseData.data
                    if (_data == null) {
                        return
                    }
                    val version = _data.version
                    if (version == null) {
                        uni_showToast(ShowToastOptions(title = "暂无新版本", icon = "none"))
                        return
                    }
                    var _version = parseInt(version)
                    var _appVersionCode = parseInt(appOpt.appVersionCode ?: "0")
                    if (_version > _appVersionCode) {
                        appUrl.value = _data.apkUrl ?: ""
                        appVerson.value = version
                        verDescribe.value = _data.verDescribe ?: ""
                        showUp.value = true
                        isForceUpdate.value = _data?.isForceUpdate ?: "1"
                        uni_showToast(ShowToastOptions(title = "发现新版本", icon = "none"))
                    } else {
                        uni_showToast(ShowToastOptions(title = "暂无新版本", icon = "none"))
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getAppUp = ::gen_getAppUp_fn
            fun gen_download_fn() {
                updateBtn.value = false
                val downloadTask = uni_downloadFile(DownloadFileOptions(url = appUrl.value, success = fun(res){
                    if (res.statusCode === 200) {
                        uni_installApk(InstallApkOptions(filePath = res.tempFilePath, complete = fun(_){
                            showUpdate.value = false
                            updateBtn.value = true
                            percent.value = 0
                            downloadedSize.value = 0
                            packageFileSize.value = 0
                        }
                        ))
                    }
                }
                ))
                downloadTask.onProgressUpdate(fun(res){
                    percent.value = parseInt(res.progress.toFixed(0))
                    downloadedSize.value = parseFloat((res.totalBytesWritten / Math.pow(1024, 2)).toFixed(2))
                    packageFileSize.value = parseFloat((res.totalBytesExpectedToWrite / Math.pow(1024, 2)).toFixed(2))
                }
                )
            }
            val download = ::gen_download_fn
            fun gen_upUser_fn() {
                getUserInfo2()
            }
            val upUser = ::gen_upUser_fn
            fun gen_exit_fn() {
                uni_removeStorageSync("token")
                uni_removeStorageSync("tenantCode")
                uni_removeStorageSync("storeId")
                uni_reLaunch(ReLaunchOptions(url = "/pages/login/login"))
            }
            val exit = ::gen_exit_fn
            onReady(fun(){
                setScreen()
            }
            )
            onLoad(fun(_options){})
            onPageShow(fun(){
                getUserInfo2()
                aboutMeConfigText.value = getSchoolSystemConfigValue("关于我们")
                var contactModeConfig = getSchoolSystemConfigUtsJson("联系方式")
                contactModeText.value = contactModeConfig.getString("mode") ?: ""
                contactModeValueText.value = contactModeConfig.getString("modeValue") ?: ""
            }
            )
            val getUserStudyStartDate = fun(userStudyAuthVo: UserStudyAuthVo?): String {
                if (userStudyAuthVo == null) {
                    return " - "
                }
                return userStudyAuthVo.createTime
            }
            val getUserStudyEndDate = fun(userStudyAuthVo: UserStudyAuthVo?): String {
                if (userStudyAuthVo == null) {
                    return " - "
                }
                return userStudyAuthVo.expireTime
            }
            val handleShowAboutMe = fun(){
                aboutMeShow.value = true
            }
            val handleShowContactMode = fun(){
                contactModeShow.value = true
            }
            val goPath = fun(path: String){
                uni_navigateTo(NavigateToOptions(url = path, fail = fun(_) {
                    console.log()
                }
                ))
            }
            val back = fun(){
                uni_navigateBack(NavigateBackOptions(fail = fun(_){
                    uni_redirectTo(RedirectToOptions(url = "/pages/index/index", fail = fun(_) {
                        console.log()
                    }
                    ))
                }
                ))
            }
            return fun(): Any? {
                val _component_LearningInfo = resolveEasyComponent("LearningInfo", GenComponentsLearningInfoLearningInfoClass)
                val _component_ChangeGrade = resolveEasyComponent("ChangeGrade", GenComponentsChangeGradeChangeGradeClass)
                val _component_UserSignInBtn = resolveEasyComponent("UserSignInBtn", GenComponentsUserSignInBtnUserSignInBtnClass)
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_l_switch = resolveEasyComponent("l-switch", GenUniModulesLimeSwitchComponentsLSwitchLSwitchClass)
                val _component_l_loading = resolveEasyComponent("l-loading", GenUniModulesLimeLoadingComponentsLLoadingLLoadingClass)
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                val _component_progress = resolveComponent("progress")
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, null, _uM("left" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cE("view", _uM("class" to "menu-box"), _uA(
                                    _cE("text", _uM("class" to "menu-item-text mr-10", "onClick" to back), "学练首页"),
                                    _cE("text", _uM("class" to "menu-item-text active"), "个人中心")
                                ))
                            )
                        }
                        ), "right" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_UserSignInBtn, _uM("style" to _nS(_uM("margin-right" to "16.41rpx")), "onUpUser" to upUser), null, 8, _uA(
                                    "style"
                                )),
                                _cE("view", _uM("onClick" to exit), _uA(
                                    _cV(_component_BackgroundImage, _uM("src" to "/static/ico/exit_icon.png", "bgStyle" to "width:10rpx;height:10rpx;position:relative;margin-right:3.52rpx", "className" to "exit_btn"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE("text", null, "退出")
                                        )
                                    }
                                    ), "_" to 1))
                                ))
                            )
                        }
                        ), "default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cV(_component_LearningInfo),
                                _cV(_component_ChangeGrade)
                            )
                        }
                        ), "_" to 1)),
                        _cE("view", _uM("class" to "user_info_box"), _uA(
                            _cE("view", _uM("class" to "user_info_left"), _uA(
                                _cE("text", _uM("class" to "user_info_in", "onClick" to fun(){
                                    goPath("/pages/user/editProfile")
                                }
                                ), "编辑信息", 8, _uA(
                                    "onClick"
                                )),
                                _cE("view", _uM("class" to "user_info_avatar"), _uA(
                                    _cE("view", _uM("style" to _nS(_uM("background-color" to "gray", "width" to "78rpx", "height" to "78rpx", "border-radius" to "78rpx", "position" to "relative"))), _uA(
                                        _cV(_component_BackgroundImage, _uM("src" to if (unref(userInfo)?.avatar != "") {
                                            unref(userInfo)?.avatar
                                        } else {
                                            unref(defaultAvatar)
                                        }
                                        , "mode" to "aspectFill", "bgStyle" to "position: absolute;left:-3rpx;top:-3rpx;width:88rpx;height:88rpx"), null, 8, _uA(
                                            "src"
                                        ))
                                    ), 4)
                                )),
                                _cE("view", _uM("class" to "user_info_info"), _uA(
                                    _cE("view", _uM("class" to "user_info_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "账号："),
                                        _cE("text", _uM("class" to "user_info_value"), _tD(unref(userInfo)?.userName), 1)
                                    )),
                                    _cE("view", _uM("class" to "user_info_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "姓名："),
                                        _cE("text", _uM("class" to "user_info_value"), _tD(unref(userInfo)?.nickName), 1)
                                    )),
                                    _cE("view", _uM("class" to "user_info_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "性别："),
                                        _cE("text", _uM("class" to "user_info_value"), _tD(if (unref(userInfo)?.sex == "0") {
                                            "男"
                                        } else {
                                            if (unref(userInfo)?.sex == "1") {
                                                "女"
                                            } else {
                                                if (unref(userInfo)?.sex == "2") {
                                                    "未知"
                                                } else {
                                                    ""
                                                }
                                            }
                                        }
                                        ), 1)
                                    )),
                                    _cE("view", _uM("class" to "user_info_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "英语学习卡："),
                                        _cE("view", _uM("style" to _nS(_uM("display" to "flex", "flex-direction" to "row", "flex" to "1"))), _uA(
                                            _cE("text", _uM("class" to "user_info_value", "style" to _nS(_uM("text-align" to "center"))), _tD(getUserStudyStartDate(unref(userInfo)?.englishStudyAuth)), 5),
                                            _cE("text", _uM("class" to "user_info_value", "style" to _nS(_uM("margin" to "0 4rpx"))), "至", 4),
                                            _cE("text", _uM("class" to "user_info_value", "style" to _nS(_uM("text-align" to "center", "color" to "#3A58EB"))), _tD(getUserStudyEndDate(unref(userInfo)?.englishStudyAuth)), 5)
                                        ), 4)
                                    )),
                                    _cE("view", _uM("class" to "user_info_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "手机号码："),
                                        _cE("text", _uM("class" to "user_info_value"), _tD(unref(userInfo)?.phonenumber), 1)
                                    )),
                                    _cE("view", _uM("class" to "user_info_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "学校："),
                                        _cE("text", _uM("class" to "user_info_value"), _tD(unref(userInfo)?.actualSchoolName), 1)
                                    )),
                                    _cE("view", _uM("class" to "user_info_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "机构："),
                                        _cE("text", _uM("class" to "user_info_value"), _tD(unref(userInfo)?.schoolName), 1)
                                    )),
                                    _cE("view", _uM("class" to "user_info_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "班级："),
                                        _cE("text", _uM("class" to "user_info_value"), _tD(unref(userInfo)?.schoolClassName), 1)
                                    )),
                                    _cE("view", _uM("class" to "user_info_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "负责人："),
                                        _cE("text", _uM("class" to "user_info_value"), _tD(unref(userInfo)?.teacherUserNickNames), 1)
                                    ))
                                ))
                            )),
                            _cE("view", _uM("class" to "user_info_right"), _uA(
                                _cE("view", _uM("class" to "user_info_right_top"), _uA(
                                    _cE("view", _uM("class" to "top_text_box"), _uA(
                                        _cE("text", _uM("class" to "top_text1"), _tD(unref(userInfo)?.learnFinishTextbookNum), 1),
                                        _cE("text", _uM("class" to "top_text2"), "完成本数")
                                    )),
                                    _cE("view", _uM("class" to "top_text_box", "onClick" to fun(){
                                        goPath("/pages/user/studyDaysStat/record")
                                    }
                                    ), _uA(
                                        _cE("text", _uM("class" to "top_text1"), _tD(unref(userInfo)?.totalStudyDayNum), 1),
                                        _cE("text", _uM("class" to "top_text2"), "累计天数")
                                    ), 8, _uA(
                                        "onClick"
                                    )),
                                    _cE("view", _uM("class" to "top_text_box", "onClick" to fun(){
                                        goPath("/pages/user/integral/record")
                                    }
                                    ), _uA(
                                        _cE("text", _uM("class" to "top_text1"), _tD(unref(userInfo)?.points), 1),
                                        _cE("text", _uM("class" to "top_text2"), "积分数量")
                                    ), 8, _uA(
                                        "onClick"
                                    )),
                                    _cE("view", _uM("class" to "top_text_box"), _uA(
                                        _cE("text", _uM("class" to "top_text1"), _tD(unref(userInfo)?.englishStudyDayResidue), 1),
                                        _cE("text", _uM("class" to "top_text2"), "剩余学习天数")
                                    ))
                                )),
                                _cE("view", _uM("class" to "user_info_integ", "onClick" to fun(){
                                    goPath("/pages/user/integral/exchange")
                                }
                                ), _uA(
                                    _cE("image", _uM("src" to "/static/user/jinbi_bg.png", "style" to _nS(_uM("position" to "absolute", "right" to "24rpx", "top" to "5rpx", "width" to "72rpx", "height" to "95rpx", "z-index" to "999"))), null, 4),
                                    _cE("text", _uM("class" to "integ_text1"), "积分兑换中心"),
                                    _cE("text", _uM("class" to "integ_text2"), "做任务、签到获得积分，积分可以兑换学习卡")
                                ), 8, _uA(
                                    "onClick"
                                )),
                                _cE("view", _uM("class" to "user_info_bottom"), _uA(
                                    _cE("view", _uM("class" to "bottom_item", "onClick" to handleShowContactMode), _uA(
                                        _cE("image", _uM("class" to "bottom_item_img", "src" to "/static/user/kefu.png")),
                                        _cE("text", _uM("class" to "bottom_item_text"), "联系客服")
                                    )),
                                    _cE("view", _uM("class" to "bottom_item", "onClick" to handleShowAboutMe), _uA(
                                        _cE("image", _uM("class" to "bottom_item_img", "src" to "/static/user/guanyu.png")),
                                        _cE("text", _uM("class" to "bottom_item_text"), "关于我们")
                                    )),
                                    _cE("view", _uM("class" to "bottom_item"), _uA(
                                        _cE("image", _uM("class" to "bottom_item_img_jianpan", "src" to "/static/user/jianpan.png")),
                                        _cV(_component_l_switch, _uM("dotSize" to "16rpx", "height" to "20rpx", "width" to "50rpx", "onChange" to virtualKeyBoardChange, "modelValue" to unref(virtualKeyBoardStatus), "onUpdate:modelValue" to fun(`$event`: Boolean){
                                            trySetRefValue(virtualKeyBoardStatus, `$event`)
                                        }
                                        ), null, 8, _uA(
                                            "modelValue"
                                        )),
                                        _cE("text", _uM("class" to "bottom_item_text"), "键盘开关")
                                    ))
                                ))
                            ))
                        )),
                        if (isTrue(unref(aboutMeShow))) {
                            _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("view", _uM("style" to _nS(_uM("display" to "flex", "flex-direction" to "row", "justify-content" to "center", "align-items" to "center", "width" to "100%", "height" to "100%"))), _uA(
                                        _cE("view", _uM("class" to "about_me_box"), _uA(
                                            _cE("image", _uM("class" to "about_me_close", "src" to "/static/user/qiandao_close.png", "onClick" to fun(){
                                                aboutMeShow.value = false
                                            }), null, 8, _uA(
                                                "onClick"
                                            )),
                                            _cV(_component_BackgroundImage, _uM("src" to "/static/user/guanyu_bg.png", "bgStyle" to "width: 478.47rpx;height: 328.51rpx;position:absolute;top: 0;left: 0;"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                return _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("padding" to "20rpx 20rpx", "display" to "flex", "flex-direction" to "column", "align-items" to "center", "height" to "100%"))), _uA(
                                                        _cE("text", _uM("class" to "about_title"), " 关于我们 "),
                                                        _cE("view", _uM("style" to _nS(_uM("flex" to "1", "margin-top" to "15rpx"))), _uA(
                                                            withDirectives(_cE("view", _uM("style" to _nS(_uM("flex" to "1", "justify-content" to "center"))), _uA(
                                                                _cV(_component_l_loading, _uM("color" to "#AEC3FC", "size" to "60rpx"))
                                                            ), 4), _uA(
                                                                _uA(
                                                                    vShow,
                                                                    unref(loading)
                                                                )
                                                            )),
                                                            withDirectives(_cE("view", null, _uA(
                                                                _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("flex" to "1"))), _uA(
                                                                    _cE("text", _uM("class" to "about_content"), _tD(unref(aboutMeConfigText)), 1)
                                                                ), 4)
                                                            ), 512), _uA(
                                                                _uA(
                                                                    vShow,
                                                                    !unref(loading)
                                                                )
                                                            ))
                                                        ), 4),
                                                        _cE("view", _uM("style" to _nS(_uM("justify-content" to "center", "width" to "100%", "align-items" to "center"))), _uA(
                                                            _cE("text", _uM("onClick" to getAppUp, "style" to _nS(_uM("padding" to "6rpx 10rpx", "background-color" to "#3A58EB", "color" to "#fff", "border-radius" to "20rpx"))), "检查更新", 4),
                                                            _cE("text", _uM("style" to _nS(_uM("color" to "#5e6355", "margin-top" to "6rpx"))), "v" + _tD(unref(appOptInfo).appVersion), 5)
                                                        ), 4)
                                                    ), 4)
                                                )
                                            }), "_" to 1))
                                        ))
                                    ), 4)
                                )
                            }), "_" to 1))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(contactModeShow))) {
                            _cV(_component_u_popup, _uM("key" to 1), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("view", _uM("style" to _nS(_uM("display" to "flex", "flex-direction" to "row", "justify-content" to "center", "align-items" to "center", "width" to "100%", "height" to "100%"))), _uA(
                                        _cE("view", _uM("class" to "about_me_box"), _uA(
                                            _cE("image", _uM("class" to "about_me_close", "src" to "/static/user/qiandao_close.png", "onClick" to fun(){
                                                contactModeShow.value = false
                                            }), null, 8, _uA(
                                                "onClick"
                                            )),
                                            _cV(_component_BackgroundImage, _uM("src" to "/static/user/guanyu_bg.png", "bgStyle" to "width: 478.47rpx;height: 328.51rpx;position:absolute;top: 0;left: 0;"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                                return _uA(
                                                    _cE("view", _uM("style" to _nS(_uM("padding" to "20rpx 20rpx", "display" to "flex", "flex-direction" to "column", "align-items" to "center", "height" to "100%"))), _uA(
                                                        _cE("text", _uM("class" to "about_title"), " 联系方式 "),
                                                        withDirectives(_cE("view", _uM("style" to _nS(_uM("flex" to "1", "justify-content" to "center"))), _uA(
                                                            _cV(_component_l_loading, _uM("color" to "#AEC3FC", "size" to "60rpx"))
                                                        ), 4), _uA(
                                                            _uA(
                                                                vShow,
                                                                unref(loading)
                                                            )
                                                        )),
                                                        withDirectives(_cE("view", null, _uA(
                                                            _cE("scroll-view", _uM("direction" to "vertical", "style" to _nS(_uM("height" to "240rpx", "margin-top" to "15rpx"))), _uA(
                                                                _cE("text", _uM("class" to "about_content"), _tD(unref(contactModeText) + " : " + unref(contactModeValueText)), 1)
                                                            ), 4)
                                                        ), 512), _uA(
                                                            _uA(
                                                                vShow,
                                                                !unref(loading)
                                                            )
                                                        ))
                                                    ), 4)
                                                )
                                            }), "_" to 1))
                                        ))
                                    ), 4)
                                )
                            }), "_" to 1))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(unref(showUp))) {
                            _cV(_component_u_popup, _uM("key" to 2), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("view", _uM("class" to "uni-center", "style" to _nS(_uM("justify-content" to "center", "width" to "100%", "height" to "100%"))), _uA(
                                        _cE("view", _uM("class" to "up_box"), _uA(
                                            _cE("image", _uM("src" to "/static/app_up_bg.png", "class" to "up_box_bg", "mode" to "")),
                                            _cE("view", _uM("class" to "up_box_content"), _uA(
                                                _cE("image", _uM("src" to "/static/up_title.png", "class" to "title", "mode" to "")),
                                                _cE("text", _uM("class" to "verson"), _tD(unref(appVerson)), 1),
                                                _cE("view", _uM("style" to _nS(_uM("flex" to "1", "margin-top" to "10rpx"))), _uA(
                                                    _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "line-height" to "15rpx", "color" to "#3D3D3D"))), _tD(unref(verDescribe)), 5)
                                                ), 4),
                                                _cE("view", _uM("style" to _nS(_uM("width" to "90%", "align-items" to "center"))), _uA(
                                                    if (isTrue(!unref(updateBtn))) {
                                                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("width" to "100%"))), _uA(
                                                            _cV(_component_progress, _uM("class" to "progress", "border-radius" to 35, "percent" to unref(percent), "activeColor" to "#3DA7FF", "show-info" to unref(showInfo), "stroke-width" to 10), null, 8, _uA(
                                                                "percent",
                                                                "show-info"
                                                            )),
                                                            _cE("view", null, _uA(
                                                                _cE("text", _uM("style" to _nS(_uM("font-size" to "14rpx", "margin-top" to "10rpx"))), "正在下载，请稍后 (" + _tD(unref(downloadedSize)) + "/" + _tD(unref(packageFileSize)) + "M)", 5)
                                                            ))
                                                        ), 4)
                                                    } else {
                                                        _cC("v-if", true)
                                                    },
                                                    if (isTrue(unref(updateBtn))) {
                                                        _cE("text", _uM("key" to 1, "class" to "up_btn", "onClick" to download), "立即更新")
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ), 4)
                                            ))
                                        )),
                                        if (unref(isForceUpdate) == "1") {
                                            _cE("view", _uM("key" to 0, "style" to _nS(_uM("width" to "30rpx", "height" to "30rpx", "background-color" to "#fff", "border-radius" to "30rpx", "align-items" to "center", "justify-content" to "center", "margin-top" to "10rpx")), "onClick" to fun(){
                                                showUp.value = false
                                            }), _uA(
                                                _cE("image", _uM("src" to "/static/ico/close.png", "style" to _nS(_uM("width" to "14rpx", "height" to "14rpx")), "mode" to ""), null, 4)
                                            ), 12, _uA(
                                                "onClick"
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ), 4)
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "up_box" to _pS(_uM("width" to "346.88rpx", "height" to "217.97rpx", "position" to "relative", "marginLeft" to "-30rpx")), "up_box_bg" to _uM(".up_box " to _uM("position" to "absolute", "width" to "100%", "height" to "100%", "top" to 0, "left" to 0)), "up_box_content" to _uM(".up_box " to _uM("marginLeft" to "30rpx", "height" to "100%", "paddingTop" to "15.23rpx", "paddingRight" to "15.23rpx", "paddingBottom" to "15.23rpx", "paddingLeft" to "15.23rpx", "alignItems" to "center")), "title" to _uM(".up_box .up_box_content " to _uM("marginTop" to "4.68rpx", "width" to "145.9rpx", "height" to "26rpx")), "verson" to _uM(".up_box .up_box_content " to _uM("width" to "87rpx", "height" to "22rpx", "backgroundImage" to "none", "backgroundColor" to "#FFD597", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "22rpx", "textAlign" to "center", "marginTop" to "20rpx")), "up_btn" to _uM(".up_box .up_box_content " to _uM("width" to "96rpx", "height" to "29rpx", "backgroundImage" to "none", "backgroundColor" to "#082337", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "fontSize" to "12rpx", "color" to "#FFFFFF", "lineHeight" to "29rpx", "textAlign" to "center")), "user_info_box" to _pS(_uM("display" to "flex", "flexDirection" to "row", "paddingTop" to "20.51rpx", "paddingRight" to "17.58rpx", "paddingBottom" to 0, "paddingLeft" to "17.58rpx")), "user_info_avatar" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "center", "width" to "100%")), "user_info_left" to _pS(_uM("paddingTop" to "14rpx", "paddingRight" to "11.72rpx", "paddingBottom" to 0, "paddingLeft" to "11.72rpx", "width" to "240rpx", "height" to "326rpx", "backgroundImage" to "linear-gradient(to bottom, #CBD3FC, #fff)", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "marginRight" to "14.56rpx", "position" to "relative")), "user_info_in" to _pS(_uM("width" to "57rpx", "height" to "20rpx", "textAlign" to "center", "lineHeight" to "20rpx", "position" to "absolute", "top" to "8.2rpx", "right" to "9.3rpx", "zIndex" to 9999, "backgroundColor" to "#2791FF", "color" to "#ffffff", "borderTopLeftRadius" to "10rpx", "borderTopRightRadius" to "10rpx", "borderBottomRightRadius" to "10rpx", "borderBottomLeftRadius" to "10rpx", "fontSize" to "10rpx")), "user_info_right" to _pS(_uM("flex" to 1)), "exit_btn" to _pS(_uM("display" to "flex", "width" to "60rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "menu-box" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "menu-item-text" to _uM(".menu-box " to _uM("color" to "#E5EEFF", "fontSize" to "12rpx")), "mr-10" to _uM(".menu-box " to _uM("marginRight" to "15.8rpx")), "active" to _uM(".menu-box " to _uM("fontWeight" to "700", "color" to "#FFFFFF", "fontSize" to "15rpx")), "user_info_item" to _pS(_uM("flexDirection" to "row")), "user_info_label" to _pS(_uM("fontSize" to "11rpx", "color" to "#7B7B7B", "lineHeight" to "23rpx", "width" to "70.31rpx", "textAlign" to "right")), "user_info_value" to _pS(_uM("fontWeight" to "700", "fontSize" to "11rpx", "color" to "#535D8C", "lineHeight" to "23rpx")), "user_info_right_top" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "height" to "85rpx", "backgroundImage" to "linear-gradient(to bottom, #E4F6DA, #FFFFFF)", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "paddingTop" to "19.34rpx", "paddingRight" to 0, "paddingBottom" to "16rpx", "paddingLeft" to 0, "boxSizing" to "border-box", "marginBottom" to "5.68rpx")), "user_info_bottom" to _uM(".user_info_right " to _uM("flexDirection" to "row", "justifyContent" to "space-between")), "bottom_item_img" to _uM(".user_info_right " to _uM("width" to "64rpx", "height" to "64rpx")), "bottom_item_img_jianpan" to _uM(".user_info_right " to _uM("width" to "76rpx", "height" to "44rpx")), "bottom_item_text" to _uM(".user_info_right " to _uM("height" to "23rpx", "fontSize" to "14rpx", "color" to "#535D8C", "lineHeight" to "23rpx")), "bottom_item" to _uM(".user_info_right " to _uM("width" to "146rpx", "height" to "117rpx", "backgroundImage" to "linear-gradient(to bottom, #C9E4FF, #FFFFFF)", "boxShadow" to "0rpx 2rpx 4rpx 0rpx rgba(130, 141, 197, 0.3)", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to "14rpx", "paddingRight" to 0, "paddingBottom" to "14rpx", "paddingLeft" to 0, "boxSizing" to "border-box")), "user_info_integ" to _uM(".user_info_right " to _uM("height" to "111.33rpx", "backgroundImage" to "linear-gradient(to bottom, #FFEDCD, #FFFFFF)", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "paddingTop" to "34.57rpx", "paddingRight" to "21.68rpx", "paddingBottom" to "21.68rpx", "paddingLeft" to "21.68rpx", "boxSizing" to "border-box", "marginBottom" to "5.68rpx", "justifyContent" to "space-between")), "integ_text1" to _uM(".user_info_right .user_info_integ " to _uM("fontWeight" to "700", "fontSize" to "23rpx", "color" to "#79581D")), "integ_text2" to _uM(".user_info_right .user_info_integ " to _uM("fontSize" to "12rpx", "color" to "#8C703E")), "top_text_box" to _uM(".user_info_right " to _uM("flex" to 1, "justifyContent" to "space-between", "alignItems" to "center")), "top_text1" to _uM(".user_info_right .top_text_box " to _uM("fontWeight" to "700", "fontSize" to "23rpx", "color" to "#3D3D3D")), "top_text2" to _uM(".user_info_right .top_text_box " to _uM("fontSize" to "12rpx", "color" to "#535D8C")), "about_me_box" to _pS(_uM("width" to "478.47rpx", "height" to "328.51rpx")), "about_me_close" to _pS(_uM("width" to "18rpx", "height" to "18rpx", "position" to "absolute", "top" to "30rpx", "right" to "22rpx", "zIndex" to 99)), "about_title" to _pS(_uM("fontFamily" to "Arial, Arial", "fontSize" to "18rpx", "color" to "#3D3D3D", "lineHeight" to "39rpx", "fontStyle" to "normal")), "about_content" to _pS(_uM("fontFamily" to "Arial, Arial", "fontSize" to "16rpx", "color" to "#3D3D3D", "fontStyle" to "normal")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
