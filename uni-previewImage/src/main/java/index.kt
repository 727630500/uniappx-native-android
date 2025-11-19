@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uts.sdk.modules.uniPreviewImage
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import io.dcloud.uniapp.extapi.`$emit` as uni__emit
import io.dcloud.uniapp.extapi.`$once` as uni__once
import io.dcloud.uniapp.extapi.openDialogPage as uni_openDialogPage
typealias MediaErrorCode = Number
interface IMediaError : IUniError {
    override var errCode: MediaErrorCode
}
open class PreviewImageSuccess (
    @JsonNotNull
    open var errSubject: String,
    @JsonNotNull
    open var errMsg: String,
) : UTSObject()
typealias PreviewImageFail = IMediaError
typealias PreviewImageSuccessCallback = (callback: PreviewImageSuccess) -> Unit
typealias PreviewImageFailCallback = (callback: PreviewImageFail) -> Unit
typealias PreviewImageCompleteCallback = (callback: Any) -> Unit
open class LongPressActionsSuccessResult (
    @JsonNotNull
    open var tapIndex: Number,
    @JsonNotNull
    open var index: Number,
) : UTSObject()
typealias LongPressActionsFailResult = IMediaError
open class LongPressActionsOptions (
    @JsonNotNull
    open var itemList: UTSArray<String>,
    open var itemColor: String? = null,
    open var success: ((result: LongPressActionsSuccessResult) -> Unit)? = null,
    open var fail: ((result: LongPressActionsFailResult) -> Unit)? = null,
    open var complete: ((result: Any) -> Unit)? = null,
) : UTSObject()
open class PreviewImageOptions (
    open var current: Any? = null,
    @JsonNotNull
    open var urls: UTSArray<String>,
    open var indicator: String? = null,
    open var loop: Boolean? = true,
    open var longPressActions: LongPressActionsOptions? = null,
    open var success: PreviewImageSuccessCallback? = null,
    open var fail: PreviewImageFailCallback? = null,
    open var complete: PreviewImageCompleteCallback? = null,
) : UTSObject()
typealias PreviewImage = (options: PreviewImageOptions) -> Unit
val UniError_PreviewImage = "uni-previewImage"
val MediaUniErrors: Map<Number, String> = Map(_uA(
    _uA(
        1101001,
        "user cancel"
    ),
    _uA(
        1101002,
        "fail parameter error: parameter.urls should have at least 1 item"
    ),
    _uA(
        1101003,
        "file not find"
    ),
    _uA(
        1101004,
        "Failed to load resource"
    ),
    _uA(
        1101005,
        "No Permission"
    ),
    _uA(
        1101010,
        "unexpect error:please check previewImage.uvue is in page.json"
    )
))
open class MediaErrorImpl : UniError, IMediaError {
    override var errCode: MediaErrorCode
    constructor(errCode: MediaErrorCode, uniErrorSubject: String) : super() {
        this.errSubject = uniErrorSubject
        this.errCode = errCode
        this.errMsg = MediaUniErrors.get(errCode) ?: ""
    }
}
fun __previewImage(option: PreviewImageOptions) {
    if (option.urls.length == 0) {
        var error = MediaErrorImpl(1101002, UniError_PreviewImage)
        option.fail?.invoke(error)
        option.complete?.invoke(error)
        return
    }
    uni__once("__onPreviewLoad", fun(){
        uni__emit("__onPreviewLoadCallback", option)
    }
    )
    uni_openDialogPage(OpenDialogPageOptions(url = "/uni_modules/uni-previewImage/pages/previewImage/previewImage", animationType = "fade-in", success = fun(_) {
        var success = PreviewImageSuccess(errMsg = "ok", errSubject = UniError_PreviewImage)
        option.success?.invoke(success)
        option.complete?.invoke(success)
    }
    , fail = fun(_) {
        var error = MediaErrorImpl(1101010, UniError_PreviewImage)
        option.fail?.invoke(error)
        option.complete?.invoke(error)
    }
    ))
}
val previewImage: PreviewImage = fun(option: PreviewImageOptions) {
    __previewImage(option)
}
