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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesIndexIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {}, __ins)
        onReady(fun() {
            setScreen()
        }
        , __ins)
        onPageShow(fun() {
            this.getUserInfo2()
            initConfig()
            this.getAppAndroidUpdate()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        val _component_UserSignInBtn = resolveEasyComponent("UserSignInBtn", GenComponentsUserSignInBtnUserSignInBtnClass)
        val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
        val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
        val _component_progress = resolveComponent("progress")
        val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
        val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
        return _cV(_component_PageWrap, _uM("className" to "index_bg"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
            return _uA(
                _cV(_component_navbar, null, _uM("left" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cE("view", _uM("class" to "menu-box"), _uA(
                            _cE("text", _uM("class" to "menu-item-text active mr-10"), "学练首页"),
                            _cE("text", _uM("class" to "menu-item-text", "onClick" to fun(){
                                _ctx.goPath("/pages/user/index")
                            }
                            ), "个人中心", 8, _uA(
                                "onClick"
                            ))
                        ))
                    )
                }
                ), "right" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_UserSignInBtn, _uM("style" to _nS(_uM("margin-right" to "16.41rpx"))), null, 8, _uA(
                            "style"
                        )),
                        _cE("view", _uM("onClick" to _ctx.exit), _uA(
                            _cV(_component_BackgroundImage, _uM("src" to "/static/ico/exit_icon.png", "bgStyle" to "width:10rpx;height:10rpx;position:relative;margin-right:3.52rpx", "className" to "exit_btn"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("text", null, "退出")
                                )
                            }
                            ), "_" to 1))
                        ), 8, _uA(
                            "onClick"
                        ))
                    )
                }
                ), "_" to 1)),
                _cE("view", _uM("class" to "index-content", "style" to _nS(_uM("justify-content" to "center", "margin-top" to "10rpx"))), _uA(
                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "justify-content" to "center", "align-items" to "center"))), _uA(
                        _cE("image", _uM("src" to "/static/index_l_ico.png", "style" to _nS(_uM("width" to "203rpx", "height" to "280rpx")), "onClick" to fun(){
                            _ctx.goPath("/pages/index/indexNew")
                        }
                        ), null, 12, _uA(
                            "onClick"
                        )),
                        _cE("image", _uM("src" to "/static/index_r_ico.png", "style" to _nS(_uM("width" to "217rpx", "height" to "250rpx", "margin-top" to "30rpx"))), null, 4)
                    ), 4)
                ), 4),
                if (isTrue(_ctx.showUp)) {
                    _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "uni-center", "style" to _nS(_uM("justify-content" to "center", "width" to "100%", "height" to "100%"))), _uA(
                                _cE("view", _uM("class" to "up_box"), _uA(
                                    _cE("image", _uM("src" to "/static/app_up_bg.png", "class" to "up_box_bg", "mode" to "")),
                                    _cE("view", _uM("class" to "up_box_content"), _uA(
                                        _cE("image", _uM("src" to "/static/up_title.png", "class" to "title", "mode" to "")),
                                        _cE("text", _uM("class" to "verson"), _tD(_ctx.appVerson), 1),
                                        _cE("view", _uM("style" to _nS(_uM("flex" to "1", "margin-top" to "10rpx"))), _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "line-height" to "15rpx", "color" to "#3D3D3D"))), _tD(_ctx.verDescribe), 5)
                                        ), 4),
                                        _cE("view", _uM("style" to _nS(_uM("width" to "90%", "align-items" to "center"))), _uA(
                                            if (isTrue(!_ctx.updateBtn)) {
                                                _cE("view", _uM("key" to 0, "style" to _nS(_uM("width" to "100%"))), _uA(
                                                    _cV(_component_progress, _uM("class" to "progress", "border-radius" to 35, "percent" to _ctx.percent, "activeColor" to "#3DA7FF", "show-info" to _ctx.showInfo, "stroke-width" to 10), null, 8, _uA(
                                                        "percent",
                                                        "show-info"
                                                    )),
                                                    _cE("view", null, _uA(
                                                        _cE("text", _uM("style" to _nS(_uM("font-size" to "14rpx", "margin-top" to "10rpx"))), "正在下载，请稍后 (" + _tD(_ctx.downloadedSize) + "/" + _tD(_ctx.packageFileSize) + "M)", 5)
                                                    ))
                                                ), 4)
                                            } else {
                                                _cC("v-if", true)
                                            },
                                            if (isTrue(_ctx.updateBtn)) {
                                                _cE("text", _uM("key" to 1, "class" to "up_btn", "onClick" to _ctx.download), "立即更新", 8, _uA(
                                                    "onClick"
                                                ))
                                            } else {
                                                _cC("v-if", true)
                                            }
                                        ), 4)
                                    ))
                                ))
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
    open var dcNum: Number by `$data`
    open var dcShow: Boolean by `$data`
    open var jzNum: Number by `$data`
    open var jzShow: Boolean by `$data`
    open var isShowPupup: Boolean by `$data`
    open var title: String by `$data`
    open var appUrl: String by `$data`
    open var verDescribe: String by `$data`
    open var appVerson: String by `$data`
    open var showUp: Boolean by `$data`
    open var isForceUpdate: String by `$data`
    open var percent: Number by `$data`
    open var updateBtn: Boolean by `$data`
    open var downloadedSize: Number by `$data`
    open var packageFileSize: Number by `$data`
    open var showInfo: Boolean by `$data`
    open var showUpdate: Boolean by `$data`
    open var userInfo: UserModel? by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("dcNum" to 0, "dcShow" to true, "jzNum" to 0, "jzShow" to true, "isShowPupup" to false, "title" to "Hello", "appUrl" to "", "verDescribe" to "", "appVerson" to "", "showUp" to false, "isForceUpdate" to "1", "percent" to 0, "updateBtn" to true, "downloadedSize" to 0, "packageFileSize" to 0, "showInfo" to true, "showUpdate" to false, "userInfo" to null as UserModel?)
    }
    open var getUserInfo2 = ::gen_getUserInfo2_fn
    open fun gen_getUserInfo2_fn() {
        uni_request<Result<UserModel?>>(RequestOptions(url = getUrl("/biz/studentUser/api/userInfo"), method = "GET", header = getHeader(), success = fun(res){
            val responseData = res.data
            if (responseData == null) {
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
            this.userInfo = responseData.data ?: null
            var _userInfo = this.userInfo
            if (this.userInfo != null) {
                updateUserState(this.userInfo)
            }
            if (responseData.data?.studentAccountType == "过期账号") {
                this.exit()
                return
            }
            if (_userInfo?.actualSchoolName == null || _userInfo?.phonenumber == null || _userInfo?.periodBaseDataId == null || _userInfo?.sex == null) {
                this.goPath("/pages/user/editProfile")
                uni_showToast(ShowToastOptions(title = "请补全信息", icon = "none"))
            }
        }
        , fail = fun(err){
            console.log(err)
        }
        , complete = fun(_){}))
    }
    open var exit = ::gen_exit_fn
    open fun gen_exit_fn() {
        uni_removeStorageSync("token")
        uni_removeStorageSync("tenantCode")
        uni_removeStorageSync("storeId")
        uni_reLaunch(ReLaunchOptions(url = "/pages/login/login"))
    }
    open var getAppAndroidUpdate = ::gen_getAppAndroidUpdate_fn
    open fun gen_getAppAndroidUpdate_fn() {
        val appOpt = uni_getAppBaseInfo(null)
        uni_request<Result<upData>>(RequestOptions(url = getUrl("/api/general/appAndroidUpdate"), method = "GET", data = UTSJSONObject(), header = getHeader(), success = fun(res){
            val responseData = res.data
            if (responseData == null) {
                return
            }
            if (responseData.code as Number != 200) {
                uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                return
            }
            val _data = responseData.data
            if (_data == null) {
                return
            }
            val version = _data.version
            if (version == null) {
                return
            }
            var _version = parseInt(version)
            var _appVersionCode = parseInt(appOpt.appVersionCode ?: "0")
            if (_version > _appVersionCode) {
                this.appUrl = _data.apkUrl ?: ""
                this.appVerson = version
                this.verDescribe = _data.verDescribe ?: ""
                this.isForceUpdate = _data?.isForceUpdate ?: "1"
                if ((_data?.isForceUpdate ?: "1") == "1") {
                    this.showUp = true
                }
            }
        }
        , fail = fun(err){
            console.log(err)
        }
        , complete = fun(_){}))
    }
    open var download = ::gen_download_fn
    open fun gen_download_fn() {
        this.updateBtn = false
        val downloadTask = uni_downloadFile(DownloadFileOptions(url = this.appUrl, success = fun(res){
            if (res.statusCode === 200) {
                uni_installApk(InstallApkOptions(filePath = res.tempFilePath, complete = fun(_){
                    this.showUpdate = false
                    this.updateBtn = true
                    this.percent = 0
                    this.downloadedSize = 0
                    this.packageFileSize = 0
                }
                ))
            }
        }
        ))
        downloadTask.onProgressUpdate(fun(res){
            this.percent = parseInt(res.progress.toFixed(0))
            this.downloadedSize = parseFloat((res.totalBytesWritten / Math.pow(1024, 2)).toFixed(2))
            this.packageFileSize = parseFloat((res.totalBytesExpectedToWrite / Math.pow(1024, 2)).toFixed(2))
        }
        )
    }
    open var goPath = ::gen_goPath_fn
    open fun gen_goPath_fn(path: String) {
        uni_navigateTo(NavigateToOptions(url = path, fail = fun(_) {
            console.log()
        }
        ))
    }
    open var handleModuleClick = ::gen_handleModuleClick_fn
    open fun gen_handleModuleClick_fn(module: StudyModule) {
        if (this.userInfo?.studentAccountType == "过期账号") {
            uni_showToast(ShowToastOptions(title = "账号过期，请联系管理员", icon = "none"))
            return
        }
        if (this.userInfo?.englishStudyDayResidue == 0) {
            uni_showToast(ShowToastOptions(title = "学习时长不足", icon = "none"))
            return
        }
        uni_navigateTo(NavigateToOptions(url = module.path, fail = fun(_) {
            console.log()
        }
        ))
    }
    open var goTask = ::gen_goTask_fn
    open fun gen_goTask_fn() {
        if (this.userInfo?.studentAccountType == "过期账号") {
            uni_showToast(ShowToastOptions(title = "账号过期，请联系管理员", icon = "none"))
            return
        }
        if (this.userInfo?.englishStudyDayResidue == 0) {
            uni_showToast(ShowToastOptions(title = "学习时长不足", icon = "none"))
            return
        }
        uni_navigateTo(NavigateToOptions(url = "/pages/myTask/myTask", fail = fun(_) {
            console.log()
        }
        ))
    }
    open var goReview = ::gen_goReview_fn
    open fun gen_goReview_fn(type: String) {
        if (this.userInfo?.englishStudyDayResidue == 0) {
            uni_showToast(ShowToastOptions(title = "学习时长不足", icon = "none"))
            return
        }
        if (type == "dc") {
            this.dcShow = false
            uni_navigateTo(NavigateToOptions(url = "/pages/wordInter/review", fail = fun(err) {
                console.log(err)
            }))
        } else {
            this.jzShow = false
            uni_navigateTo(NavigateToOptions(url = "/pages/sentence/review", fail = fun(err) {
                console.log(err)
            }
            ))
        }
    }
    open var startStudy = ::gen_startStudy_fn
    open fun gen_startStudy_fn(item: returnItems) {
        if (this.userInfo?.studentAccountType == "过期账号") {
            uni_showToast(ShowToastOptions(title = "账号过期，请联系管理员", icon = "none"))
            return
        }
        if (this.userInfo?.englishStudyDayResidue == 0) {
            uni_showToast(ShowToastOptions(title = "学习时长不足", icon = "none"))
            return
        }
        if (item.userStudyTaskProgress?.isFinish == "1") {
            return
        }
        var _url = getJumpUrl(item)
        if (_url == "") {
            return
        }
        uni_navigateTo(NavigateToOptions(url = _url))
    }
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ), _uA(
                GenApp.styles
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("index_bg" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "exit_btn" to _pS(_uM("display" to "flex", "width" to "60rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "menu-box" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "menu-item-text" to _uM(".menu-box " to _uM("color" to "#E5EEFF", "fontSize" to "12rpx")), "mr-10" to _uM(".menu-box " to _uM("marginRight" to "15.8rpx")), "active" to _uM(".menu-box " to _uM("fontWeight" to "700", "color" to "#FFFFFF", "fontSize" to "15rpx")), "index-content" to _pS(_uM("flexDirection" to "row", "display" to "flex")), "study-modules" to _pS(_uM("paddingTop" to "14.65rpx", "paddingRight" to "17.58rpx", "paddingBottom" to "14.65rpx", "paddingLeft" to "17.58rpx", "display" to "flex", "flexWrap" to "wrap", "flexDirection" to "row", "flex" to 1)), "module-item" to _pS(_uM("backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "boxShadow" to "0rpx 2rpx 4rpx 0rpx rgba(130, 141, 197, 0.3)", "width" to "232rpx", "height" to "105rpx", "marginBottom" to "12rpx", "marginRight" to "12rpx", "position" to "relative")), "module-content" to _pS(_uM("display" to "flex", "justifyContent" to "space-between", "flexDirection" to "row")), "module-text" to _pS(_uM("paddingTop" to "20rpx", "paddingRight" to "15rpx", "paddingBottom" to "10rpx", "paddingLeft" to "15rpx")), "module-title" to _pS(_uM("fontSize" to "15.23rpx", "fontWeight" to "bold", "color" to "#333333", "marginBottom" to "6rpx")), "module-subtitle" to _pS(_uM("marginBottom" to "12rpx", "paddingTop" to "2rpx", "paddingRight" to "4rpx", "paddingBottom" to "2rpx", "paddingLeft" to "4rpx", "borderTopLeftRadius" to "20rpx", "borderTopRightRadius" to "20rpx", "borderBottomRightRadius" to "20rpx", "borderBottomLeftRadius" to "20rpx", "backgroundImage" to "none", "backgroundColor" to "rgba(255,255,255,0.8)")), "module-subtitle-text" to _uM(".module-subtitle " to _uM("fontSize" to "11.72rpx", "color" to "#666666")), "start-btn-box" to _pS(_uM("display" to "flex", "alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(255,255,255,0.8)", "borderRightColor" to "rgba(255,255,255,0.8)", "borderBottomColor" to "rgba(255,255,255,0.8)", "borderLeftColor" to "rgba(255,255,255,0.8)", "backgroundColor" to "#FFFFFF", "width" to "68rpx", "height" to "22rpx", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx")), "start-btn" to _pS(_uM("color" to "#FFFFFF", "fontSize" to "11.72rpx")), "divider" to _pS(_uM("width" to "221rpx", "height" to "100%", "paddingTop" to "20rpx", "paddingRight" to "17.58rpx", "paddingBottom" to "20rpx", "paddingLeft" to 0, "flexDirection" to "column")), "task-box" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "marginBottom" to "10rpx")), "task-all" to _uM(".task-box " to _uM("width" to "76rpx", "height" to "19rpx", "backgroundImage" to "none", "backgroundColor" to "#E2E6F6", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "text" to _uM(".task-box .task-all " to _uM("fontSize" to "14rpx", "color" to "#3D3D3D", "fontWeight" to "bold", "lineHeight" to "19rpx")), "task-title" to _uM(".task-box " to _uM("fontSize" to "15.23rpx", "fontWeight" to "bold", "color" to "#FDF8FF")), "task-swiper" to _pS(_uM("height" to "230rpx", "marginBottom" to "10rpx", "width" to "100%", "paddingBottom" to "-9.62rpx")), "task-item-box" to _uM(".task-swiper " to _uM("height" to "50rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginBottom" to "9.62rpx")), "task-item-shadow" to _uM(".task-swiper " to _uM("width" to "100%", "height" to "53rpx", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginTop" to "3rpx", "backgroundColor" to "#BFC4DC", "position" to "absolute", "top" to 0, "left" to 0, "zIndex" to -1)), "task-item" to _uM(".task-swiper " to _uM("width" to "100%", "height" to "52rpx", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "paddingTop" to 0, "paddingRight" to "8rpx", "paddingBottom" to 0, "paddingLeft" to "8rpx")), "task-item-title" to _uM(".task-swiper .task-item " to _uM("marginTop" to "5.27rpx", "fontWeight" to "400", "fontSize" to "11rpx", "height" to "13rpx", "color" to "#3D3D3D", "lineHeight" to "13rpx", "marginBottom" to "4rpx")), "task-item-progress" to _uM(".task-swiper .task-item " to _uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "task-item-progress-text" to _uM(".task-swiper .task-item " to _uM("flex" to 1, "marginRight" to "15rpx")), "task-item-progress-text-text" to _uM(".task-swiper .task-item " to _uM("fontWeight" to "400", "fontSize" to "11rpx", "color" to "#555555", "lineHeight" to "13rpx", "marginBottom" to "4rpx")), "task-item-progress-btn" to _uM(".task-swiper .task-item " to _uM("width" to "44rpx", "height" to "18rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "relative")), "_bgimg" to _uM(".task-swiper .task-item .task-item-progress-btn " to _uM("position" to "absolute", "width" to "100%", "height" to "100%", "left" to 0, "top" to 0)), "task-item-progress-btn-text" to _uM(".task-swiper .task-item " to _uM("fontSize" to "11rpx", "color" to "#FFFFFF", "lineHeight" to "13rpx", "position" to "relative", "zIndex" to 9)), "home-banner" to _pS(_uM("width" to "100%", "height" to "68.55rpx")), "up_box" to _pS(_uM("width" to "346.88rpx", "height" to "217.97rpx", "position" to "relative", "marginLeft" to "-30rpx")), "up_box_bg" to _uM(".up_box " to _uM("position" to "absolute", "width" to "100%", "height" to "100%", "top" to 0, "left" to 0)), "up_box_content" to _uM(".up_box " to _uM("marginLeft" to "30rpx", "height" to "100%", "paddingTop" to "15.23rpx", "paddingRight" to "15.23rpx", "paddingBottom" to "15.23rpx", "paddingLeft" to "15.23rpx", "alignItems" to "center")), "title" to _uM(".up_box .up_box_content " to _uM("marginTop" to "4.68rpx", "width" to "145.9rpx", "height" to "26rpx")), "verson" to _uM(".up_box .up_box_content " to _uM("width" to "87rpx", "height" to "22rpx", "backgroundImage" to "none", "backgroundColor" to "#FFD597", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "22rpx", "textAlign" to "center", "marginTop" to "20rpx")), "up_btn" to _uM(".up_box .up_box_content " to _uM("width" to "96rpx", "height" to "29rpx", "backgroundImage" to "none", "backgroundColor" to "#082337", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "fontSize" to "12rpx", "color" to "#FFFFFF", "lineHeight" to "29rpx", "textAlign" to "center")), "popup_top" to _pS(_uM("position" to "fixed", "top" to "11.72rpx", "left" to "50%", "transform" to "translateX(-50%)")), "p_item" to _uM(".popup_top " to _uM("width" to "501rpx", "height" to "59rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "boxShadow" to "0rpx 2rpx 6rpx 0rpx rgba(0, 0, 0, 0.3)", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginBottom" to "2rpx", "flexDirection" to "row", "alignItems" to "center")), "p_title" to _uM(".popup_top .p_item " to _uM("fontWeight" to "700", "fontSize" to "12rpx", "color" to "#6694DF", "lineHeight" to "23rpx")), "tips_index" to _uM(".popup_top .p_item " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "23rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
