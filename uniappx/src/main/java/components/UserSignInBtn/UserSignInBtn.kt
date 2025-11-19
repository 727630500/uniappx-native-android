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
import io.dcloud.uniapp.extapi.getSystemInfo as uni_getSystemInfo
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenComponentsUserSignInBtnUserSignInBtn : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsUserSignInBtnUserSignInBtn) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsUserSignInBtnUserSignInBtn
            val _cache = __ins.renderCache
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val signInStatusText = _uA(
                "签到失败",
                "签到成功",
                "今天已经签到"
            ) as UTSArray<String>
            val showSignDialog = ref<Boolean>(false)
            val signedIn = ref<Boolean>(true)
            val screenWidth = ref<Number>(0)
            val screenHeight = ref<Number>(0)
            val loading = ref<Boolean>(true)
            val appSignInResult = ref<AppSignInResult>(AppSignInResult(status = 0, points = 0, keepSignInNum = 0))
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
                    if (responseData.data != null) {
                        updateUserState(responseData.data!!)
                        emit("upUser")
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getUserInfo2 = ::gen_getUserInfo2_fn
            fun gen_checkSignIn_fn() {
                uni_request<Result<Boolean>>(RequestOptions(url = getUrl("/biz/userSignInRecord/api/checkSignIn"), method = "GET", header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    signedIn.value = responseData.data as Boolean
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val checkSignIn = ::gen_checkSignIn_fn
            onLoad(fun(_options){
                checkSignIn()
            }
            )
            fun gen_signIn_fn() {
                uni_getSystemInfo(GetSystemInfoOptions(success = fun(res) {
                    screenWidth.value = res.screenWidth
                    screenHeight.value = res.screenHeight
                    loading.value = true
                    showSignDialog.value = true
                    uni_request<Result<AppSignInResult?>>(RequestOptions(url = getUrl("/biz/userSignInRecord/api/signIn"), method = "POST", header = getHeader(), success = fun(res){
                        val responseData = res.data
                        if (responseData == null) {
                            return
                        }
                        if (responseData.code as Number != 200) {
                            uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                            return
                        }
                        appSignInResult.value = responseData.data as AppSignInResult
                        checkSignIn()
                        getUserInfo2()
                    }
                    , fail = fun(err){
                        console.log(err)
                        uni_showToast(ShowToastOptions(title = "请求失败", icon = "none"))
                    }
                    , complete = fun(_){
                        loading.value = false
                    }
                    ))
                }
                ))
            }
            val signIn = ::gen_signIn_fn
            return fun(): Any? {
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_l_loading = resolveEasyComponent("l-loading", GenUniModulesLimeLoadingComponentsLLoadingLLoadingClass)
                return _cE("view", null, _uA(
                    _cE("view", _uM("class" to "exit_btn", "onClick" to fun(){
                        signIn()
                    }
                    , "style" to _nS(_uM("width" to "68rpx"))), _uA(
                        _cV(_component_BackgroundImage, _uM("src" to "/static/ico/qiandao.png", "bgStyle" to "width:68.55rpx;height:28rpx;position:relative;")),
                        withDirectives(_cE("view", _uM("class" to "notification-dot"), null, 512), _uA(
                            _uA(
                                vShow,
                                !unref(signedIn)
                            )
                        ))
                    ), 12, _uA(
                        "onClick"
                    )),
                    if (isTrue(unref(showSignDialog))) {
                        _cE("view", _uM("key" to 0, "class" to "mask", "style" to _nS(_uM("height" to (unref(screenHeight) + "px"), "width" to (unref(screenWidth) + "px")))), _uA(
                            _cE("view", _uM("class" to "mask-content"), _uA(
                                _cE("image", _uM("class" to "qiandao_close", "src" to "/static/user/qiandao_close.png", "onClick" to fun(){
                                    showSignDialog.value = false
                                }), null, 8, _uA(
                                    "onClick"
                                )),
                                _cV(_component_BackgroundImage, _uM("src" to "/static/user/qiandao_bg.png", "bgStyle" to "width: 405.47rpx;height: 320.51rpx;position:absolute;top: 0;left: 0;"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE("view", _uM("class" to "qiandao_bg_box"), _uA(
                                            _cE("image", _uM("src" to "/static/user/qiandao_rili.png", "class" to "qiandao_bg")),
                                            withDirectives(_cV(_component_l_loading, _uM("color" to "#AEC3FC", "size" to "60rpx"), null, 512), _uA(
                                                _uA(
                                                    vShow,
                                                    unref(loading)
                                                )
                                            )),
                                            withDirectives(_cE("view", _uM("class" to "qiandao_success"), _uA(
                                                _cE("text", _uM("class" to "qiandao_success_text1"), _tD(signInStatusText[unref(appSignInResult).status]), 1),
                                                withDirectives(_cE("text", _uM("class" to "qiandao_success_text2"), "+ " + _tD(unref(appSignInResult).points), 513), _uA(
                                                    _uA(
                                                        vShow,
                                                        unref(appSignInResult).status == 1
                                                    )
                                                )),
                                                withDirectives(_cE("text", _uM("class" to "qiandao_success_text1"), "积分", 512), _uA(
                                                    _uA(
                                                        vShow,
                                                        unref(appSignInResult).status == 1
                                                    )
                                                ))
                                            ), 512), _uA(
                                                _uA(
                                                    vShow,
                                                    !unref(loading)
                                                )
                                            )),
                                            withDirectives(_cE("view", _uM("class" to "qiandao_num"), _uA(
                                                _cE("text", _uM("class" to "qiandao_num_text1"), "已连续签到"),
                                                _cE("text", _uM("class" to "qiandao_num_text2"), _tD(if (unref(appSignInResult).keepSignInNum != null) {
                                                    unref(appSignInResult).keepSignInNum
                                                } else {
                                                    0
                                                }), 1),
                                                _cE("text", _uM("class" to "qiandao_num_text1"), "天")
                                            ), 512), _uA(
                                                _uA(
                                                    vShow,
                                                    !unref(loading)
                                                )
                                            ))
                                        ))
                                    )
                                }), "_" to 1))
                            ))
                        ), 4)
                    } else {
                        _cC("v-if", true)
                    }
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
                return _uM("exit_btn" to _pS(_uM("display" to "flex", "width" to "60rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "backgroundColor" to "rgba(0,0,0,0.5)", "zIndex" to 1000, "alignItems" to "center", "justifyContent" to "center")), "mask-content" to _pS(_uM("width" to "405.47rpx", "height" to "320.51rpx")), "qiandao_close" to _pS(_uM("width" to "18rpx", "height" to "18rpx", "position" to "absolute", "top" to "30rpx", "right" to "22rpx", "zIndex" to 99)), "qiandao_bg_box" to _pS(_uM("position" to "relative", "alignItems" to "center", "justifyContent" to "center")), "qiandao_bg" to _pS(_uM("marginTop" to "35.16rpx", "width" to "151.76rpx", "height" to "151.76rpx")), "qiandao_success" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "qiandao_num" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "qiandao_success_text1" to _pS(_uM("fontSize" to "18rpx", "color" to "#7B7B7B")), "qiandao_success_text2" to _pS(_uM("fontFamily" to "Impact, Impact", "fontWeight" to "400", "fontSize" to "47rpx", "color" to "#5689DC")), "qiandao_num_text1" to _pS(_uM("fontSize" to "18rpx", "color" to "#535D8C")), "qiandao_num_text2" to _pS(_uM("fontWeight" to "700", "fontSize" to "29.3rpx", "color" to "#FA9600")), "notification-dot" to _pS(_uM("width" to "5rpx", "height" to "5rpx", "backgroundColor" to "#FF0000", "borderTopLeftRadius" to "3rpx", "borderTopRightRadius" to "3rpx", "borderBottomRightRadius" to "3rpx", "borderBottomLeftRadius" to "3rpx", "position" to "absolute", "top" to "4rpx", "right" to "6rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("upUser" to null)
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
