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
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesLoginLogin : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesLoginLogin) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesLoginLogin
            val _cache = __ins.renderCache
            val user = ref<User>(User(userName = "25stu010751", password = "999999"))
            val showPassword = ref(true)
            val loginFn = fun(){
                if (user.value.userName == "") {
                    uni_showToast(ShowToastOptions(title = "请输入用户名", icon = "none"))
                    return
                }
                if (user.value.password == "") {
                    uni_showToast(ShowToastOptions(title = "请输入密码", icon = "none"))
                    return
                }
                uni_showLoading(ShowLoadingOptions(title = ""))
                uni_request<defaultResult>(RequestOptions(url = getUrl("/biz/studentUser/api/loginByUserName"), method = "POST", data = object : UTSJSONObject() {
                    var userName = user.value.userName
                    var password = user.value.password
                    var tenantCode = ucsShare.getState("channel_id") as String
                }, success = fun(res){
                    uni_hideLoading()
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    var _data = responseData.data as UTSJSONObject
                    uni_setStorageSync("token", _data["tokenValue"] as String)
                    uni_showToast(ShowToastOptions(icon = "none", title = "登录成功"))
                    setTimeout(fun(){
                        uni_reLaunch(ReLaunchOptions(url = "/pages/index/index"))
                    }
                    , 500)
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            onReady(fun(){
                setScreen()
            }
            )
            return fun(): Any? {
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_BackgroundImage, _uM("src" to "/static/login_bg.png")),
                        _cE("view", _uM("class" to "inner"), _uA(
                            _cE("text", _uM("style" to _nS(_uM("font-size" to "21rpx", "font-weight" to "bold", "margin-top" to "40rpx"))), " 欢迎登录 ", 4),
                            _cE("view", _uM("style" to _nS(_uM("height" to "60rpx"))), null, 4),
                            _cE("view", _uM("class" to "input_box"), _uA(
                                _cE("image", _uM("src" to "/static/ico/user.png", "mode" to "", "style" to _nS(_uM("width" to "18rpx", "height" to "17rpx", "margin-right" to "12rpx"))), null, 4),
                                _cE("input", _uM("type" to "text", "placeholder" to "请输入用户登录账号", "modelValue" to unref(user).userName, "onInput" to fun(`$event`: UniInputEvent){
                                    unref(user).userName = `$event`.detail.value
                                }
                                ), null, 40, _uA(
                                    "modelValue",
                                    "onInput"
                                ))
                            )),
                            _cE("view", _uM("style" to _nS(_uM("height" to "22rpx"))), null, 4),
                            _cE("view", _uM("class" to "input_box"), _uA(
                                _cE("image", _uM("src" to "/static/ico/pwd.png", "mode" to "", "style" to _nS(_uM("width" to "18rpx", "height" to "22rpx", "margin-right" to "12rpx"))), null, 4),
                                _cE("input", _uM("cursor-spacing" to 100, "placeholder" to "请输入密码", "password" to unref(showPassword), "style" to _nS(_uM("flex" to "1")), "modelValue" to unref(user).password, "onInput" to fun(`$event`: UniInputEvent){
                                    unref(user).password = `$event`.detail.value
                                }
                                ), null, 44, _uA(
                                    "password",
                                    "modelValue",
                                    "onInput"
                                )),
                                if (unref(user).password !== "") {
                                    _cE("image", _uM("key" to 0, "style" to _nS(_uM("width" to "18rpx", "height" to "11rpx")), "src" to if (unref(showPassword)) {
                                        "/static/ico/pwd-hide.png"
                                    } else {
                                        "/static/ico/pwd-show.png"
                                    }, "onClick" to fun(){
                                        showPassword.value = !unref(showPassword)
                                    }), null, 12, _uA(
                                        "src",
                                        "onClick"
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                            )),
                            _cE("view", _uM("class" to "login_btn", "onClick" to loginFn), _uA(
                                _cE("image", _uM("src" to "/static/ico/login_btn.png", "mode" to "", "class" to "_image")),
                                _cE("text", _uM("class" to "_text"), "登录")
                            )),
                            _cE("view", _uM("style" to _nS(_uM("margin-top" to "20rpx"))), _uA(
                                _cE("text", _uM("class" to "login_tips"), "忘记密码请联系管理员")
                            ), 4)
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
                return _uM("login-page-wrap" to _pS(_uM("height" to "100%")), "login_bg" to _pS(_uM("width" to "100%", "height" to "100%")), "inner" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "width" to "100%", "height" to "100%", "alignItems" to "center")), "input_box" to _uM(".inner " to _uM("width" to "291rpx", "height" to "47rpx", "backgroundImage" to "none", "backgroundColor" to "#E2EAFF", "borderTopLeftRadius" to "32rpx", "borderTopRightRadius" to "32rpx", "borderBottomRightRadius" to "32rpx", "borderBottomLeftRadius" to "32rpx", "alignItems" to "center", "flexDirection" to "row", "paddingLeft" to "20rpx", "paddingRight" to "22rpx")), "login_btn" to _pS(_uM("width" to "171rpx", "height" to "53rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "relative", "marginTop" to "40rpx")), "_image" to _uM(".login_btn " to _uM("width" to "100%", "height" to "100%", "top" to 0, "left" to 0, "position" to "absolute")), "_text" to _uM(".login_btn " to _uM("fontWeight" to "700", "fontSize" to "21rpx", "color" to "#3A58EB")), "login_tips" to _pS(_uM("fontSize" to "16rpx", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
