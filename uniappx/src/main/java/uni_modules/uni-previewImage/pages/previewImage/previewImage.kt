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
import io.dcloud.uniapp.extapi.`$emit` as uni__emit
import io.dcloud.uniapp.extapi.`$off` as uni__off
import io.dcloud.uniapp.extapi.`$on` as uni__on
import io.dcloud.uniapp.extapi.`$once` as uni__once
import uts.sdk.modules.uniPreviewImage.MediaErrorCode
import uts.sdk.modules.uniPreviewImage.IMediaError
import uts.sdk.modules.uniPreviewImage.PreviewImageSuccess
import uts.sdk.modules.uniPreviewImage.PreviewImageFail
import uts.sdk.modules.uniPreviewImage.PreviewImageSuccessCallback
import uts.sdk.modules.uniPreviewImage.PreviewImageFailCallback
import uts.sdk.modules.uniPreviewImage.PreviewImageCompleteCallback
import uts.sdk.modules.uniPreviewImage.LongPressActionsSuccessResult
import uts.sdk.modules.uniPreviewImage.LongPressActionsFailResult
import uts.sdk.modules.uniPreviewImage.LongPressActionsOptions
import uts.sdk.modules.uniPreviewImage.PreviewImageOptions
import uts.sdk.modules.uniPreviewImage.PreviewImage
import io.dcloud.uniapp.extapi.closeDialogPage as uni_closeDialogPage
import io.dcloud.uniapp.extapi.getWindowInfo as uni_getWindowInfo
open class GenUniModulesUniPreviewImagePagesPreviewImagePreviewImage : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            uni__once("__onPreviewLoadCallback", this.__onPreviewLoadCallback)
            uni__emit("__onPreviewLoad", null)
            uni__on("__UNIPREVIEWIMAGE", this.setDisableTouch)
            uni__on("__UNIPREVIEWIMAGECLOSE", this.closePreviewPage)
        }
        , __ins)
        onReady(fun() {
            var windowInfo = uni_getWindowInfo()
            (this.`$refs`["numberIndicator"] as UniElement?)?.style?.setProperty("top", (windowInfo.statusBarHeight + 8) + "px")
            (this.`$refs`["defaultIndicator"] as UniElement?)?.style?.setProperty("bottom", ((windowInfo.screenHeight - windowInfo.safeArea.bottom) + 8) + "px")
        }
        , __ins)
        onUnload(fun() {
            uni__off("__UNIPREVIEWIMAGE", null)
            uni__off("__UNIPREVIEWIMAGECLOSE", null)
        }
        , __ins)
        onBackPress(fun(options: OnBackPressOptions): Boolean? {
            this.closePreviewPage()
            return true
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        val _component_uni_previewImageItem = resolveEasyComponent("uni-previewImageItem", GenUniModulesUniPreviewImageComponentsUniPreviewImageItemUniPreviewImageItemClass)
        return _cE(Fragment, null, _uA(
            _cE("swiper", _uM("style" to _nS(_uM("flex" to "1")), "indicator-dots" to false, "circular" to _ctx.loop, "current" to _ctx.current, "onChange" to _ctx.onPreviewImageChanged, "disable-touch" to _ctx.disableTouch), _uA(
                if (_ctx.urls != null) {
                    _cE(Fragment, _uM("key" to 0), RenderHelpers.renderList(_ctx.urls, fun(item, index, __index, _cached): Any {
                        return _cE("swiper-item", null, _uA(
                            _cV(_component_uni_previewImageItem, _uM("index" to index, "src" to item, "longPressAction" to _ctx.longPressAction), null, 8, _uA(
                                "index",
                                "src",
                                "longPressAction"
                            ))
                        ))
                    }), 256)
                } else {
                    _cC("v-if", true)
                }
            ), 44, _uA(
                "circular",
                "current",
                "onChange",
                "disable-touch"
            )),
            if (_ctx.indicator == "number") {
                _cE("view", _uM("key" to 0, "ref" to "numberIndicator", "class" to "number-indicator"), _uA(
                    _cE("text", _uM("class" to "number-indicator-text"), _tD(_ctx.numberIndicator), 1)
                ), 512)
            } else {
                _cC("v-if", true)
            }
            ,
            if (_ctx.indicator == "default") {
                _cE("view", _uM("key" to 1, "ref" to "defaultIndicator", "class" to "default-indicator"), _uA(
                    _cE(Fragment, null, RenderHelpers.renderList(_ctx.urls?.length, fun(i, __key, __index, _cached): Any {
                        return _cE("view", _uM("class" to "indicator-style", "style" to _nS(_uM("backgroundColor" to if (((_ctx.current + 1) == i)) {
                            "#ffffff"
                        } else {
                            "#AAAAAA"
                        }))), null, 4)
                    }), 256)
                ), 512)
            } else {
                _cC("v-if", true)
            }
        ), 64)
    }
    open var urls: UTSArray<String>? by `$data`
    open var current: Number by `$data`
    open var loop: Boolean by `$data`
    open var disableTouch: Boolean by `$data`
    open var numberIndicator: String by `$data`
    open var indicator: String by `$data`
    open var longPressAction: LongPressActionsOptions? by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("urls" to null as UTSArray<String>?, "current" to 0, "loop" to false, "disableTouch" to false, "numberIndicator" to "", "indicator" to "number", "longPressAction" to null as LongPressActionsOptions?)
    }
    open var __onPreviewLoadCallback = ::gen___onPreviewLoadCallback_fn
    open fun gen___onPreviewLoadCallback_fn(result: PreviewImageOptions) {
        this.urls = result.urls
        if (result.current != null) {
            var c = result.current
            if ((UTSAndroid.`typeof`(c) == "number")) {
                var d: Number = c as Number
                if (d < 0 || d > this.urls!!.length) {
                    d = 0
                }
                this.current = d
            } else if (UTSAndroid.`typeof`(c) == "string") {
                var index = this.urls!!.indexOf(c as String)
                if (index < 0) {
                    index = 0
                }
                this.current = index
            }
        }
        if (result.indicator != null) {
            this.indicator = result.indicator as String
        }
        if (result.longPressActions != null) {
            this.longPressAction = result.longPressActions
        }
        if (result.loop != null) {
            this.loop = result.loop!!
        }
        this.numberIndicator = (this.current + 1) + " / " + this.urls!!.length
    }
    open var onPreviewImageChanged = ::gen_onPreviewImageChanged_fn
    open fun gen_onPreviewImageChanged_fn(e: UniSwiperChangeEvent) {
        this.numberIndicator = (e.detail.current + 1) + " / " + this.urls?.length
        this.current = e.detail.current
    }
    open var setDisableTouch = ::gen_setDisableTouch_fn
    open fun gen_setDisableTouch_fn(isDisable: Any) {}
    open var closePreviewPage = ::gen_closePreviewPage_fn
    open fun gen_closePreviewPage_fn() {
        uni_closeDialogPage(CloseDialogPageOptions(dialogPage = this.`$page`, animationType = "fade-out"))
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
                return _uM("indicator-style" to _pS(_uM("width" to 9, "height" to 9, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopLeftRadius" to 9, "borderTopRightRadius" to 9, "borderBottomRightRadius" to 9, "borderBottomLeftRadius" to 9, "marginTop" to 2, "marginRight" to 3, "marginBottom" to 2, "marginLeft" to 3, "borderTopWidth" to 0.1, "borderRightWidth" to 0.1, "borderBottomWidth" to 0.1, "borderLeftWidth" to 0.1, "borderTopColor" to "#AAAAAA", "borderRightColor" to "#AAAAAA", "borderBottomColor" to "#AAAAAA", "borderLeftColor" to "#AAAAAA")), "default-indicator" to _pS(_uM("flexDirection" to "row", "position" to "absolute", "bottom" to 0, "left" to 0, "right" to 0, "justifyContent" to "center")), "number-indicator" to _pS(_uM("position" to "absolute", "color" to "#FFFFFF", "fontSize" to 16, "textAlign" to "center", "left" to 0, "right" to 0)), "number-indicator-text" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 16, "marginTop" to "auto", "marginRight" to "auto", "marginBottom" to "auto", "marginLeft" to "auto", "paddingTop" to 8, "paddingRight" to 20, "paddingBottom" to 8, "paddingLeft" to 20, "backgroundColor" to "rgba(0,0,0,0.1)", "lineHeight" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopWidth" to 0, "borderRightWidth" to 0, "borderBottomWidth" to 0, "borderLeftWidth" to 0, "borderTopLeftRadius" to 32, "borderTopRightRadius" to 32, "borderBottomRightRadius" to 32, "borderBottomLeftRadius" to 32)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
