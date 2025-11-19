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
import io.dcloud.uniapp.extapi.chooseImage as uni_chooseImage
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.uploadFile as uni_uploadFile
open class GenPagesUserEditProfile : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesUserEditProfile) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesUserEditProfile
            val _cache = __ins.renderCache
            val defaultAvatar = ref<String>("/static/user/defaultAvatar.png")
            val slectBirthdayShow = ref<Boolean>(false)
            val userAvatar = ref<String>("")
            val userName = ref<String>("")
            val nickName = ref<String>("")
            val actualSchoolName = ref<String>("")
            val phonenumber = ref<String>("")
            val sex = ref<String>("0")
            val birthday = ref(_uA<String>())
            val birthdayText = ref("")
            val periodBaseDataId = ref(0)
            val gradeBaseDataId = ref(0)
            val showPicker = ref(false)
            var gradeTypeArr = ref(_uA<Any>())
            fun gen_getClassData_fn() {
                uni_request<Result<UTSArray<gradeType>?>>(RequestOptions(url = getUrl("/biz/textbook/api/groupCascadeTreeLevel3"), method = "GET", header = getHeader(), success = fun(res){
                    val responseData = res?.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData?.data == null) {
                        return
                    }
                    var endArr: UTSArray<PickerColumnItem> = _uA()
                    var text = ""
                    responseData?.data!!.forEach(fun(item){
                        var _arr = PickerColumnItem(id = item?.id, label = item.name ?: "", value = item?.id?.toString() ?: "", disabled = false, children = _uA())
                        if (item.id == periodBaseDataId.value) {
                            text += item.name + "-"
                        }
                        var _child: UTSArray<PickerColumnItem> = _uA()
                        item.children?.forEach(fun(_childre){
                            _child.push(PickerColumnItem(id = _childre?.id, label = _childre.name ?: "", value = _childre?.id?.toString() ?: "", disabled = false))
                            if (_childre.id == gradeBaseDataId.value) {
                                text += _childre.name
                            }
                        }
                        )
                        _arr.children = _child
                        endArr.push(_arr)
                    }
                    )
                    birthdayText.value = text
                    gradeTypeArr.value = JSON.parse<UTSArray<Any>>(JSON.stringify(endArr)) ?: _uA()
                }
                , fail = fun(err){
                    console.log(err)
                }
                ))
            }
            val getClassData = ::gen_getClassData_fn
            val task = ref<UploadTask?>(null)
            val onConfirm = fun(value: PickerConfirmEvent){
                console.log("onConfirm", value)
                slectBirthdayShow.value = false
                birthdayText.value = value.items[0].label + "-" + value.items[1].label
                periodBaseDataId.value = parseInt(value.items[0].value)
                gradeBaseDataId.value = parseInt(value.items[1].value)
            }
            val submit = fun(){
                if (actualSchoolName.value.trim() == "") {
                    uni_showToast(ShowToastOptions(title = "请填写学校", icon = "none"))
                    return
                }
                if (phonenumber.value.trim() == "") {
                    uni_showToast(ShowToastOptions(title = "请填写手机号", icon = "none"))
                    return
                }
                if (sex.value == "") {
                    uni_showToast(ShowToastOptions(title = "请选择性别", icon = "none"))
                    return
                }
                if (birthdayText.value.trim() == "") {
                    uni_showToast(ShowToastOptions(title = "请选择年级", icon = "none"))
                    return
                }
                var data: UTSJSONObject = object : UTSJSONObject() {
                    var phonenumber = phonenumber.value
                    var birthday = ""
                    var actualSchoolName = actualSchoolName.value
                    var avatar = userAvatar.value
                    var sex = sex.value
                    var periodBaseDataId = periodBaseDataId.value
                    var gradeBaseDataId = gradeBaseDataId.value
                }
                uni_showLoading(ShowLoadingOptions(title = "提交中"))
                uni_request<Result<Number>>(RequestOptions(url = getUrl("/biz/studentUser/api/editInfo"), method = "POST", data = data, header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    uni_showToast(ShowToastOptions(title = "提交成功", icon = "success", duration = 1000))
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){
                    uni_hideLoading()
                }
                ))
            }
            onLoad(fun(_options){
                if (userState.value != null) {
                    var data = userState.value as UserModel
                    userAvatar.value = data.avatar
                    userName.value = data.userName
                    nickName.value = data.nickName
                    actualSchoolName.value = data.actualSchoolName ?: ""
                    phonenumber.value = data.phonenumber
                    sex.value = data.sex
                    periodBaseDataId.value = data.periodBaseDataId ?: 0
                    gradeBaseDataId.value = data.gradeBaseDataId ?: 0
                }
                getClassData()
            }
            )
            onReady(fun(){
                setScreen()
            }
            )
            fun gen_uploadAvatar_fn(filePath: String) {
                uni_showLoading(ShowLoadingOptions(title = "上传中"))
                task.value = uni_uploadFile(UploadFileOptions(url = getUrl("/api/general/upload"), filePath = filePath, header = getHeader(), name = "file", formData = UTSJSONObject(), success = fun(res: UploadFileSuccess){
                    uni_showToast(ShowToastOptions(title = "上传成功", icon = "success", duration = 1000))
                    var data = JSON.parseObject(res.data)
                    var url: String? = data?.getString("data")
                    if (url != null) {
                        userAvatar.value = url
                    }
                }
                , fail = fun(err){
                    console.log("上传失败", err)
                    uni_showModal(ShowModalOptions(content = "上传失败", showCancel = false))
                }
                , complete = fun(res){
                    uni_hideLoading()
                    task.value = null
                }
                ))
            }
            val uploadAvatar = ::gen_uploadAvatar_fn
            val chooseImage = fun(){
                uni_chooseImage(ChooseImageOptions(sourceType = _uA(
                    "album"
                ), sizeType = _uA(
                    "original",
                    "compressed"
                ), count = 1, pageOrientation = "landscape", albumMode = "system", success = fun(res){
                    console.log(res.tempFilePaths)
                    uploadAvatar(res.tempFilePaths[0])
                }
                , fail = fun(err){
                    console.log("err: ", JSON.stringify(err))
                }
                ))
            }
            return fun(): Any? {
                val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                val _component_l_cascade = resolveEasyComponent("l-cascade", GenUniModulesLimePickerComponentsLCascadeLCascadeClass)
                val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
                val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
                return _cV(_component_PageWrap, _uM("class" to "d_page"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_navbar, _uM("showBack" to true, "title" to "个人信息")),
                        _cE("view", _uM("class" to "d_content"), _uA(
                            _cE("view", _uM("class" to "user_info"), _uA(
                                _cE("view", _uM("class" to "user_info_row"), _uA(
                                    _cE("view", _uM("class" to "user_info_row_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "头像"),
                                        _cE("view", _uM("class" to "user_info_value", "style" to _nS(_uM("display" to "flex", "flex-direction" to "row", "align-items" to "center"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("background-color" to "gray", "width" to "67rpx", "height" to "67rpx", "border-radius" to "67rpx", "position" to "relative"))), _uA(
                                                _cV(_component_BackgroundImage, _uM("src" to if (unref(userAvatar) != "") {
                                                    unref(userAvatar)
                                                } else {
                                                    unref(defaultAvatar)
                                                }
                                                , "mode" to "aspectFill", "bgStyle" to "position: absolute;left:-3rpx;top:-3rpx;width:78rpx;height:78rpx"), null, 8, _uA(
                                                    "src"
                                                ))
                                            ), 4),
                                            _cE("view", _uM("onClick" to chooseImage, "style" to _nS(_uM("margin-left" to "25rpx"))), _uA(
                                                _cE("text", _uM("class" to "btn", "style" to _nS(_uM("width" to "76rpx", "height" to "26rpx", "line-height" to "26rpx"))), "上传头像", 4)
                                            ), 4)
                                        ), 4)
                                    ))
                                )),
                                _cE("view", _uM("class" to "user_info_row"), _uA(
                                    _cE("view", _uM("class" to "user_info_row_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "账号"),
                                        _cE("view", _uM("class" to "user_info_value input_box"), _uA(
                                            _cE("input", _uM("class" to "input inputDisabled", "type" to "text", "placeholder" to "", "disabled" to true, "modelValue" to unref(userName), "onInput" to fun(`$event`: UniInputEvent){
                                                trySetRefValue(userName, `$event`.detail.value)
                                            }
                                            ), null, 40, _uA(
                                                "modelValue"
                                            ))
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "user_info_row_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "姓名"),
                                        _cE("view", _uM("class" to "user_info_value input_box"), _uA(
                                            _cE("input", _uM("class" to "input inputDisabled", "type" to "text", "placeholder" to "", "disabled" to true, "modelValue" to unref(nickName), "onInput" to fun(`$event`: UniInputEvent){
                                                trySetRefValue(nickName, `$event`.detail.value)
                                            }
                                            ), null, 40, _uA(
                                                "modelValue"
                                            ))
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "user_info_row_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "学校"),
                                        _cE("view", _uM("class" to "user_info_value input_box"), _uA(
                                            _cE("input", _uM("class" to "input", "type" to "text", "placeholder" to "", "modelValue" to unref(actualSchoolName), "onInput" to fun(`$event`: UniInputEvent){
                                                trySetRefValue(actualSchoolName, `$event`.detail.value)
                                            }
                                            ), null, 40, _uA(
                                                "modelValue"
                                            ))
                                        ))
                                    ))
                                )),
                                _cE("view", _uM("class" to "user_info_row"), _uA(
                                    _cE("view", _uM("class" to "user_info_row_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "手机号"),
                                        _cE("view", _uM("class" to "user_info_value input_box"), _uA(
                                            _cE("input", _uM("class" to "input", "type" to "number", "placeholder" to "", "modelValue" to unref(phonenumber), "onInput" to fun(`$event`: UniInputEvent){
                                                trySetRefValue(phonenumber, `$event`.detail.value)
                                            }
                                            ), null, 40, _uA(
                                                "modelValue"
                                            ))
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "user_info_row_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "性别"),
                                        _cE("view", _uM("class" to "user_info_value", "style" to _nS(_uM("display" to "flex", "flex-direction" to "row", "justify-content" to "space-between", "width" to "165rpx"))), _uA(
                                            _cE("view", _uM("class" to _nC(_uA(
                                                "sex_box",
                                                _uM("sex_box_select" to (unref(sex) == "0"))
                                            )), "onClick" to fun(){
                                                sex.value = "0"
                                            }
                                            ), _uA(
                                                _cE("image", _uM("src" to "/static/ico/user/man.png", "mode" to "", "style" to _nS(_uM("width" to "24rpx", "height" to "24rpx"))), null, 4),
                                                _cE("text", _uM("class" to "sex_box_text"), "男")
                                            ), 10, _uA(
                                                "onClick"
                                            )),
                                            _cE("view", _uM("class" to _nC(_uA(
                                                "sex_box",
                                                _uM("sex_box_select" to (unref(sex) == "1"))
                                            )), "onClick" to fun(){
                                                sex.value = "1"
                                            }
                                            ), _uA(
                                                _cE("image", _uM("src" to "/static/ico/user/woman.png", "mode" to "", "style" to _nS(_uM("width" to "24rpx", "height" to "24rpx"))), null, 4),
                                                _cE("text", _uM("class" to "sex_box_text"), "女")
                                            ), 10, _uA(
                                                "onClick"
                                            ))
                                        ), 4)
                                    )),
                                    _cE("view", _uM("class" to "user_info_row_item"), _uA(
                                        _cE("text", _uM("class" to "user_info_label"), "年级"),
                                        _cE("view", _uM("class" to "user_info_value input_box", "onClick" to fun(){
                                            slectBirthdayShow.value = true
                                        }
                                        ), _uA(
                                            _cE("input", _uM("class" to "input", "type" to "text", "placeholder" to "", "disabled" to true, "modelValue" to unref(birthdayText), "onInput" to fun(`$event`: UniInputEvent){
                                                trySetRefValue(birthdayText, `$event`.detail.value)
                                            }
                                            ), null, 40, _uA(
                                                "modelValue"
                                            ))
                                        ), 8, _uA(
                                            "onClick"
                                        ))
                                    ))
                                ))
                            )),
                            _cE("view", _uM("style" to _nS(_uM("display" to "flex", "flex-direction" to "row", "justify-content" to "center"))), _uA(
                                _cE("text", _uM("class" to "btn", "style" to _nS(_uM("width" to "100rpx", "height" to "35rpx", "line-height" to "35rpx")), "onClick" to submit), "提交", 4)
                            ), 4)
                        )),
                        if (isTrue(unref(slectBirthdayShow))) {
                            _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cE("view", _uM("style" to _nS(_uM("flex" to "1", "justify-content" to "flex-end", "align-items" to "center"))), _uA(
                                        _cE("view", null, _uA(
                                            _cV(_component_l_cascade, _uM("cancel-btn" to "取消", "confirm-btn" to "确定", "modelValue" to unref(birthday), "onUpdate:modelValue" to fun(`$event`: UTSArray<String>){
                                                trySetRefValue(birthday, `$event`)
                                            }, "columns" to unref(gradeTypeArr), "title" to "选择年级", "itemHeight" to "60px", "groupHeight" to "150rpx", "titleStyle" to "font-size:20px", "cancelStyle" to "font-size:20px", "confirmStyle" to "font-size:20px", "itemFontSize" to "20px", "onConfirm" to onConfirm, "onCancel" to fun(){
                                                slectBirthdayShow.value = false
                                            }), null, 8, _uA(
                                                "modelValue",
                                                "columns",
                                                "onCancel"
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
                return _uM("d_page" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "d_content" to _pS(_uM("width" to "715rpx", "height" to "330rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginTop" to "11.05rpx", "marginRight" to "17.58rpx", "marginBottom" to 0, "marginLeft" to "17.58rpx")), "user_info" to _pS(_uM("paddingTop" to "10rpx", "paddingRight" to "20rpx", "paddingBottom" to "10rpx", "paddingLeft" to "20rpx")), "user_info_row" to _uM(".user_info " to _uM("display" to "flex", "flexDirection" to "row")), "user_info_row_item" to _uM(".user_info .user_info_row " to _uM("width" to "240rpx", "paddingTop" to "5rpx", "paddingRight" to "21rpx", "paddingBottom" to "5rpx", "paddingLeft" to "21rpx")), "user_info_label" to _uM(".user_info .user_info_row .user_info_row_item " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "35rpx", "textAlign" to "left", "fontStyle" to "normal")), "btn" to _pS(_uM("color" to "#ffffff", "textAlign" to "center", "backgroundImage" to "linear-gradient(to bottom, #CBD4FF, #516DF4)", "borderTopLeftRadius" to "35.16rpx", "borderTopRightRadius" to "35.16rpx", "borderBottomRightRadius" to "35.16rpx", "borderBottomLeftRadius" to "35.16rpx", "fontSize" to "13rpx")), "input_box" to _pS(_uM("width" to "165rpx", "height" to "35rpx", "backgroundImage" to "none", "backgroundColor" to "#E2EAFF", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "alignItems" to "center", "flexDirection" to "row", "paddingLeft" to "10rpx", "paddingRight" to "10rpx")), "input" to _uM(".input_box " to _uM("fontSize" to "15rpx", "color" to "#535D8C", "textAlign" to "left", "fontStyle" to "normal")), "inputDisabled" to _uM(".input_box " to _uM("!color" to "#ABBCD8")), "sex_box" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-around", "paddingTop" to 0, "paddingRight" to "5rpx", "paddingBottom" to 0, "paddingLeft" to "5rpx", "width" to "72rpx", "height" to "33rpx", "backgroundImage" to "none", "backgroundColor" to "#EFF2FF", "borderTopLeftRadius" to "19rpx", "borderTopRightRadius" to "19rpx", "borderBottomRightRadius" to "19rpx", "borderBottomLeftRadius" to "19rpx")), "sex_box_text" to _uM(".sex_box " to _uM("fontSize" to "15rpx", "color" to "#244E93", "textAlign" to "left", "fontStyle" to "normal")), "sex_box_select" to _pS(_uM("!borderTopWidth" to "1rpx", "!borderRightWidth" to "1rpx", "!borderBottomWidth" to "1rpx", "!borderLeftWidth" to "1rpx", "!borderTopStyle" to "solid", "!borderRightStyle" to "solid", "!borderBottomStyle" to "solid", "!borderLeftStyle" to "solid", "!borderTopColor" to "#4E91FF", "!borderRightColor" to "#4E91FF", "!borderBottomColor" to "#4E91FF", "!borderLeftColor" to "#4E91FF", "width" to "73rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
