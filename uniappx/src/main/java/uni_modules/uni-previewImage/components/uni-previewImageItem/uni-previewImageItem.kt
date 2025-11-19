@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uni.UNIF47E312
import android.view.View
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
import io.dcloud.uniapp.extapi.downloadFile as uni_downloadFile
import io.dcloud.uniapp.extapi.env as uni_env
import io.dcloud.uniapp.extapi.getFileSystemManager as uni_getFileSystemManager
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.setStorage as uni_setStorage
import io.dcloud.uniapp.extapi.showActionSheet as uni_showActionSheet
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenUniModulesUniPreviewImageComponentsUniPreviewImageItemUniPreviewImageItem : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {
        onMounted(fun() {
            this.imageView = this.`$refs`["imageView"] as UniElement?
            this.androidView = (this.`$refs`["mask"] as UniElement?)?.getAndroidView<android.view.View>() as View?
            this.getImageBound()
        }
        , __ins)
        this.`$watch`(fun(): Any? {
            return this.src
        }
        , fun(newValue: String, oldValue: String) {
            if (newValue != "") {
                this.getSrcLocalPath(newValue)
            }
        }
        , WatchOptions(immediate = true))
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        val _component_loading_circle = resolveEasyComponent("loading-circle", GenUniModulesUniPreviewImageComponentsLoadingCircleLoadingCircleClass)
        return _cE("view", _uM("style" to _nS(_uM("flex" to "1", "background-color" to "rgba(0, 0, 0, 0.8)", "justify-content" to "center"))), _uA(
            _cE("image", _uM("mode" to _ctx.imageMode, "class" to "item", "src" to _ctx.srcPath), null, 8, _uA(
                "mode",
                "src"
            )),
            _cE("view", _uM("ref" to "mask", "class" to "patch", "onTouchstart" to _ctx.onstart, "onTouchmove" to _ctx.onmove, "onTouchend" to _ctx.onend, "onTouchcancel" to _ctx.oncancel), null, 40, _uA(
                "onTouchstart",
                "onTouchmove",
                "onTouchend",
                "onTouchcancel"
            )),
            if (isTrue(!_ctx.loadingFinished)) {
                _cE("view", _uM("key" to 0, "class" to "loading"), _uA(
                    _cV(_component_loading_circle, _uM("style" to _nS(_uM("margin" to "auto")), "speed" to 16, "size" to 54, "color" to "#d3d3d3"), null, 8, _uA(
                        "style"
                    ))
                ))
            } else {
                _cC("v-if", true)
            }
        ), 4)
    }
    open var src: String by `$props`
    open var index: Number by `$props`
    open var longPressAction: LongPressActionsOptions? by `$props`
    open var imageMode: String by `$data`
    open var lastTouchEndTime: Number by `$data`
    open var srcPath: String by `$data`
    open var imageView: UniElement? by `$data`
    open var screenWidth: Number by `$data`
    open var screenHeight: Number by `$data`
    open var scaleSize: Number by `$data`
    open var lastSlideTouch: UTSArray<UniTouch>? by `$data`
    open var imageTop: Number by `$data`
    open var imageMarginTop: Number by `$data`
    open var imageLeft: Number by `$data`
    open var withAnimation: Boolean by `$data`
    open var imageHeight: Number by `$data`
    open var historyX: UTSArray<Number> by `$data`
    open var historyY: UTSArray<Number> by `$data`
    open var historyT: UTSArray<Number> by `$data`
    open var _friction: Friction by `$data`
    open var requestId: Number by `$data`
    open var needExecLongPress: Boolean by `$data`
    open var androidView: View? by `$data`
    open var downPoint: Point? by `$data`
    open var longPressActionTimeoutId: Number by `$data`
    open var inScaleMode: Boolean by `$data`
    open var inDoubleTapMode: Boolean by `$data`
    open var startTimestamp: Number by `$data`
    open var clickTimeoutId: Number by `$data`
    open var transformOrigin: UTSArray<Number> by `$data`
    open var loadingFinished: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("imageMode" to "heightFix", "lastTouchEndTime" to 0, "srcPath" to "", "imageView" to null as UniElement?, "screenWidth" to 0, "screenHeight" to 0, "scaleSize" to 1, "lastSlideTouch" to null as UTSArray<UniTouch>?, "imageTop" to 0, "imageMarginTop" to 0, "imageLeft" to 0, "withAnimation" to false, "imageHeight" to 0, "historyX" to _uA(
            0,
            0
        ), "historyY" to _uA(
            0,
            0
        ), "historyT" to _uA(
            0,
            0
        ), "_friction" to Friction(1, 2), "requestId" to -1, "needExecLongPress" to false, "androidView" to null as View?, "downPoint" to null as Point?, "longPressActionTimeoutId" to -1, "inScaleMode" to false, "inDoubleTapMode" to false, "startTimestamp" to 0, "clickTimeoutId" to -1, "transformOrigin" to _uA(
            0,
            0
        ), "loadingFinished" to false)
    }
    open var previewImageError = ::gen_previewImageError_fn
    open fun gen_previewImageError_fn(e: UniImageErrorEvent) {
        uni_showToast(ShowToastOptions(title = e.detail.errMsg, position = "bottom"))
        this.loadingFinished = true
    }
    open var isNetPath = ::gen_isNetPath_fn
    open fun gen_isNetPath_fn(url: String): Boolean {
        if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("rtmp://") || url.startsWith("rtsp://")) {
            return true
        }
        return false
    }
    open var getSrcLocalPath = ::gen_getSrcLocalPath_fn
    open fun gen_getSrcLocalPath_fn(url: String) {
        if (!this.isNetPath(url)) {
            this.srcPath = url
            this.getImageBound()
            this.loadingFinished = true
            return
        }
        var realPath = uni_getStorageSync(url)
        if (realPath != null && realPath != "") {
            uni_getFileSystemManager().getFileInfo(GetFileInfoOptions(filePath = realPath as String, success = fun(e){
                this.srcPath = realPath as String
                this.getImageBound()
                this.loadingFinished = true
            }, fail = fun(_res){
                uni_downloadFile(DownloadFileOptions(timeout = 5000, url = url, filePath = uni_env.USER_DATA_PATH + "uni-previewImage/", success = fun(e){
                    this.srcPath = e.tempFilePath
                    this.loadingFinished = true
                    uni_setStorage(SetStorageOptions(key = url, data = e.tempFilePath))
                    this.getImageBound()
                }, fail = fun(e){}))
            }))
        } else {
            uni_downloadFile(DownloadFileOptions(timeout = 5000, url = url, filePath = uni_env.USER_DATA_PATH + "uni-previewImage/", success = fun(e){
                this.srcPath = e.tempFilePath
                this.loadingFinished = true
                uni_setStorage(SetStorageOptions(key = url, data = e.tempFilePath))
                this.getImageBound()
            }
            , fail = fun(e){}))
        }
    }
    open var onstart = ::gen_onstart_fn
    open fun gen_onstart_fn(e: UniTouchEvent) {
        if (this.androidView == null) {
            this.androidView = (this.`$refs`["mask"] as UniElement?)?.getAndroidView<android.view.View>() as View?
        }
        this.inScaleMode = false
        this.withAnimation = false
        cancelAnimationFrame(this.requestId)
        clearTimeout(this.clickTimeoutId)
        this.lastSlideTouch = e.touches
        this.historyX = _uA(
            0,
            0
        )
        this.historyY = _uA(
            0,
            0
        )
        this.historyT = _uA(
            0,
            0
        )
        this.downPoint = Point(x = e.touches[0].clientX, y = e.touches[0].clientY)
        this.inDoubleTapMode = false
        this.startTimestamp = e.timeStamp
        e.preventDefault()
        this.needExecLongPress = true
        this.longPressActionTimeoutId = setTimeout(fun(){
            if (this.needExecLongPress) {
                this.onLongPressAction()
            }
        }
        , 350)
    }
    open var onmove = ::gen_onmove_fn
    open fun gen_onmove_fn(e: UniTouchEvent) {
        if (e.touches.length == 1) {
            var currentSlideTouch = e.touches[0]
            if (this.lastSlideTouch != null) {
                var slideX = (currentSlideTouch.clientX - this.lastSlideTouch!![0].clientX)
                var slideY = (currentSlideTouch.clientY - this.lastSlideTouch!![0].clientY)
                var downX = Math.abs(currentSlideTouch.clientX - this.downPoint!!.x)
                var downY = Math.abs(currentSlideTouch.clientY - this.downPoint!!.y)
                if (downX > DEFAULT_DISTANCE || downY > DEFAULT_DISTANCE) {
                    if (this.scaleSize > 1 || this.imageHeight > this.screenHeight) {
                        this.imageLeft = this.imageLeft + slideX
                        this.imageTop = this.imageTop + slideY
                        this.updateStyle(e, currentSlideTouch.clientX, currentSlideTouch.clientY)
                    } else {
                        this.needExecLongPress = true
                        this.onInterceptTouchEvent(e)
                    }
                    this.historyX.shift()
                    this.historyX.push(this.imageLeft)
                    this.historyY.shift()
                    this.historyY.push(this.imageTop)
                    this.historyT.shift()
                    this.historyT.push(e.timeStamp)
                    this.lastSlideTouch = e.touches
                    this.needExecLongPress = false
                } else {
                    this.needExecLongPress = true
                }
            } else {
                this.lastSlideTouch = e.touches
            }
        } else if (e.touches.length >= 2) {
            this.inScaleMode = true
            var currentFirstTouch = e.touches[0]
            var currentSecondTouch = e.touches[1]
            var currentXSlideLength = currentFirstTouch.clientX - currentSecondTouch.clientX
            var currentYSlideLength = currentFirstTouch.clientY - currentSecondTouch.clientY
            var currentLongSideLength = Math.sqrt(currentXSlideLength * currentXSlideLength + currentYSlideLength * currentYSlideLength)
            if (this.lastSlideTouch != null && this.lastSlideTouch!!.length >= 2) {
                var lastFirstTouch = this.lastSlideTouch!![0]
                var lastSecondTouch = this.lastSlideTouch!![1]
                var lastXSlideLength = lastFirstTouch.clientX - lastSecondTouch.clientX
                var lastYSlideLength = lastFirstTouch.clientY - lastSecondTouch.clientY
                var lastLongSideLength = Math.sqrt(lastXSlideLength * lastXSlideLength + lastYSlideLength * lastYSlideLength)
                if (currentLongSideLength != lastLongSideLength) {
                    this.scaleSize = this.scaleSize * (currentLongSideLength / lastLongSideLength)
                    this.updateStyle(e, NaN, NaN)
                }
            }
            this.preventDefaultScall(e)
            this.needExecLongPress = false
            this.lastSlideTouch = e.touches
        }
    }
    open var onend = ::gen_onend_fn
    open fun gen_onend_fn(e: UniTouchEvent) {
        this.inScaleMode = false
        this.needExecLongPress = false
        clearTimeout(this.longPressActionTimeoutId)
        var current = Date.now()
        if (this.historyY[0] == 0 && this.historyY[1] == 0 && this.historyX[0] == 0 && this.historyX[1] == 0) {
            this.withAnimation = true
            if (current - this.lastTouchEndTime < 350) {
                if (this.lastSlideTouch != null && this.lastSlideTouch!!.length > 0) {
                    var downX = Math.abs(this.lastSlideTouch!![0].clientX - this.downPoint!!.x)
                    var downY = Math.abs(this.lastSlideTouch!![0].clientY - this.downPoint!!.y)
                    if (downX > FAST_SLIDE_LENGTH || downY > FAST_SLIDE_LENGTH) {
                        this.lastSlideTouch = null
                        return
                    }
                }
                if (this.scaleSize > 1) {
                    this.scaleSize = 1
                    this.imageLeft = 0
                    this.updateStyle(e, NaN, NaN)
                } else if (this.scaleSize == 1) {
                    this.scaleSize = 2
                    this.inDoubleTapMode = true
                    this.updateStyle(e, NaN, NaN)
                }
            } else if (e.touches.length == 0) {
                if (this.lastSlideTouch != null && this.lastSlideTouch!!.length == 1) {
                    if (e.timeStamp - this.startTimestamp < 160) {
                        if (this.lastSlideTouch != null) {
                            var downX = Math.abs(this.lastSlideTouch!![0].clientX - this.downPoint!!.x)
                            var downY = Math.abs(this.lastSlideTouch!![0].clientY - this.downPoint!!.y)
                            if (downX < FAST_SLIDE_LENGTH && downY < FAST_SLIDE_LENGTH) {
                                this.clickTimeoutId = setTimeout(fun(){
                                    uni__emit("__UNIPREVIEWIMAGECLOSE", null)
                                }
                                , 200)
                            }
                        } else {
                            this.clickTimeoutId = setTimeout(fun(){
                                uni__emit("__UNIPREVIEWIMAGECLOSE", null)
                            }
                            , 200)
                        }
                    }
                }
                if (this.scaleSize > 3) {
                    this.scaleSize = 3
                    this.updateStyle(e, NaN, NaN)
                } else if (this.scaleSize < 1) {
                    this.scaleSize = 1
                    this.imageLeft = 0
                    this.updateStyle(e, NaN, NaN)
                }
                this.lastTouchEndTime = current
            }
        } else {
            var xv = 1000 * (this.historyX[1] - this.historyX[0]) / (this.historyT[1] - this.historyT[0])
            var yv = 1000 * (this.historyY[1] - this.historyY[0]) / (this.historyT[1] - this.historyT[0])
            this._friction.setVelocity(xv, yv)
            this._friction.setStartPosition(this.imageLeft, this.imageTop)
            val x0 = this._friction.displacement().x
            val y0 = this._friction.displacement().y
            var x = this.imageLeft
            if (!UTSNumber.isNaN(x0)) {
                x = x0 + this.imageLeft
            }
            var y = this.imageTop
            if (!UTSNumber.isNaN(y0)) {
                y = y0 + this.imageTop
            }
            this._friction.setEndPosition(x, y)
            this.doTransform(fun(){
                var p = this._friction.positionAtTime(null)
                if (UTSNumber.isNaN(p.x) && UTSNumber.isNaN(p.y)) {
                    cancelAnimationFrame(this.requestId)
                }
                if (!UTSNumber.isNaN(p.x)) {
                    this.imageLeft = p.x
                }
                if (!UTSNumber.isNaN(p.y)) {
                    this.imageTop = p.y
                }
                this.updateStyle(e, NaN, NaN)
            }
            )
        }
        this.lastSlideTouch = null
    }
    open var oncancel = ::gen_oncancel_fn
    open fun gen_oncancel_fn(e: UniTouchEvent) {
        this.onend(e)
        clearTimeout(this.clickTimeoutId)
    }
    open var doTransform = ::gen_doTransform_fn
    open fun gen_doTransform_fn(callback: (() -> Unit)) {
        this.requestId = requestAnimationFrame(fun(_task){
            callback()
            if (!this._friction.isDone()) {
                this.doTransform(callback)
            }
        }
        )
    }
    open var updateStyle = ::gen_updateStyle_fn
    open fun gen_updateStyle_fn(e: UniTouchEvent?, xDistance: Number, yDistance: Number) {
        this.caculatorTransformOrigin(e)
        if (1 < this.scaleSize) {
            var scrollWidthLength = (this.screenWidth * (this.scaleSize - 1))
            var scrollRadio = this.transformOrigin[0] / this.screenWidth
            if (this.imageLeft > (scrollWidthLength * scrollRadio)) {
                this.imageLeft = (scrollWidthLength * scrollRadio)
                this.onInterceptTouchEvent(e)
            } else if (this.imageLeft < -(scrollWidthLength * (1 - scrollRadio))) {
                this.imageLeft = -(scrollWidthLength * (1 - scrollRadio))
                this.onInterceptTouchEvent(e)
            } else {
                this.preventDefaultScall(e)
            }
        } else {
            this.imageLeft = 0
            this.onInterceptTouchEvent(e)
        }
        if (this.screenHeight < (this.imageHeight * this.scaleSize)) {
            var topMargin = (this.transformOrigin[1] - (if (this.imageMarginTop > 0) {
                this.imageMarginTop
            } else {
                0
            })) * this.scaleSize - this.transformOrigin[1]
            var bottomMargin = ((this.imageHeight + (if (this.imageMarginTop > 0) {
                this.imageMarginTop
            } else {
                0
            }) - this.transformOrigin[1]) * this.scaleSize) - (this.screenHeight - this.transformOrigin[1])
            if (this.imageTop > topMargin) {
                this.imageTop = topMargin
            } else if (this.imageTop < -bottomMargin) {
                this.imageTop = -bottomMargin
            } else {
                if (!UTSNumber.isNaN(yDistance) && Math.abs(yDistance - this.downPoint!!.y) > DEFAULT_DISTANCE) {
                    this.preventDefaultScall(e)
                }
            }
        } else {
            if (!this.inScaleMode) {
                this.imageTop = 0
                if (!UTSNumber.isNaN(yDistance) && Math.abs(yDistance - this.downPoint!!.y) > DEFAULT_DISTANCE) {
                    this.preventDefaultScall(e)
                }
            } else {
                this.preventDefaultScall(e)
            }
        }
        this.imageView?.style?.setProperty("transition-duration", if (this.withAnimation) {
            "200ms"
        } else {
            "0ms"
        }
        )
        this.imageView?.style?.setProperty("transform-origin", this.transformOrigin[0] + "px " + this.transformOrigin[1] + "px")
        this.imageView?.style?.setProperty("transform", "translate(" + this.imageLeft + "px," + this.imageTop + "px) scale(" + this.scaleSize + ")")
    }
    open var onLongPressAction = ::gen_onLongPressAction_fn
    open fun gen_onLongPressAction_fn() {
        if (this.longPressAction != null && (this.longPressAction as LongPressActionsOptions).itemList.length > 0) {
            uni_showActionSheet(ShowActionSheetOptions(itemList = (this.longPressAction as LongPressActionsOptions).itemList, itemColor = (this.longPressAction as LongPressActionsOptions).itemColor, success = fun(e){
                var successcallback = LongPressActionsSuccessResult(tapIndex = e.tapIndex!!, index = this.index)
                (this.longPressAction as LongPressActionsOptions).success?.invoke(successcallback)
                (this.longPressAction as LongPressActionsOptions).complete?.invoke(successcallback)
            }
            , fail = fun(_) {
                var fail: LongPressActionsFailResult = MediaErrorImpl(1101001, UniError_PreviewImage)
                (this.longPressAction as LongPressActionsOptions).fail?.invoke(fail)
                (this.longPressAction as LongPressActionsOptions).complete?.invoke(fail)
            }
            ))
        }
    }
    open var onImageLoad = ::gen_onImageLoad_fn
    open fun gen_onImageLoad_fn(e: UniImageLoadEvent) {}
    open var caculatorImageSize = ::gen_caculatorImageSize_fn
    open fun gen_caculatorImageSize_fn(imageWidth: Number, imageHeight: Number) {
        var scaleImageSize = (imageHeight / (imageWidth / this.screenWidth))
        if (scaleImageSize > this.screenHeight) {
            this.imageHeight = scaleImageSize
            this.imageMode = "aspectFill"
            this.imageView?.style?.setProperty("height", scaleImageSize + "px")
        } else {
            this.imageMode = "aspectFit"
        }
        this.imageMarginTop = (this.screenHeight - scaleImageSize) / 2
        this.imageHeight = scaleImageSize
    }
    open var getImageBound = ::gen_getImageBound_fn
    open fun gen_getImageBound_fn() {
        return
    }
    open var preventDefaultScall = ::gen_preventDefaultScall_fn
    open fun gen_preventDefaultScall_fn(e: UniTouchEvent?) {
        e?.preventDefault()
        e?.stopPropagation()
    }
    open var onInterceptTouchEvent = ::gen_onInterceptTouchEvent_fn
    open fun gen_onInterceptTouchEvent_fn(e: UniTouchEvent?) {
        if (this.inScaleMode) {
            this.preventDefaultScall(e)
            return
        }
        clearTimeout(this.clickTimeoutId)
        this.androidView?.parent?.requestDisallowInterceptTouchEvent(false)
    }
    open var caculatorTransformOrigin = ::gen_caculatorTransformOrigin_fn
    open fun gen_caculatorTransformOrigin_fn(e: UniTouchEvent?) {
        var originalCenterX: Number
        var originalCenterY: Number
        if (e != null) {
            if (e.touches.length >= 2) {
                var point1 = e.touches[0]
                var point2 = e.touches[1]
                originalCenterX = (point1.clientX + point2.clientX) / 2
                originalCenterY = (point1.clientY + point2.clientY) / 2
                if (this.scaleSize * this.imageHeight < this.screenHeight) {
                    originalCenterY = this.screenHeight / 2
                }
                if (this.imageHeight > this.screenHeight) {
                    originalCenterY = originalCenterY - this.imageTop / this.scaleSize
                }
                this.imageLeft = this.imageLeft + (this.scaleSize - 1) * (originalCenterX - this.transformOrigin[0])
                this.imageTop = this.imageTop + (this.scaleSize - 1) * (originalCenterY - this.transformOrigin[1])
                this.transformOrigin = _uA(
                    originalCenterX,
                    originalCenterY
                )
            } else if (e.type == "touchend") {
                if (this.inDoubleTapMode && this.scaleSize == 2 && this.lastSlideTouch != null && this.lastSlideTouch!!.length == 1) {
                    originalCenterX = this.lastSlideTouch!![0].clientX
                    originalCenterY = this.lastSlideTouch!![0].clientY
                    if (this.scaleSize * this.imageHeight < this.screenHeight) {
                        originalCenterY = this.screenHeight / 2
                    }
                    if (this.imageHeight > this.screenHeight) {
                        originalCenterY = originalCenterY - this.imageTop
                    }
                    this.transformOrigin = _uA(
                        originalCenterX,
                        originalCenterY
                    )
                    this.imageLeft = this.imageLeft + (this.scaleSize - 1) * (originalCenterX - this.transformOrigin[0])
                    this.imageTop = this.imageTop + (this.scaleSize - 1) * (originalCenterY - this.transformOrigin[1])
                }
            }
        }
    }
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("item" to _pS(_uM("marginTop" to 0, "marginRight" to "auto", "marginBottom" to 0, "marginLeft" to "auto", "height" to "300rpx", "transitionProperty" to "transform", "transitionDuration" to "0ms")), "patch" to _pS(_uM("width" to "100%", "height" to "100%", "backgroundColor" to "rgba(0,0,0,0)", "position" to "absolute")), "loading" to _pS(_uM("position" to "absolute", "top" to 0, "bottom" to 0, "left" to 0, "right" to 0, "pointerEvents" to "none")), "@TRANSITION" to _uM("item" to _uM("property" to "transform", "duration" to "0ms")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("src" to _uM("type" to "String", "default" to ""), "index" to _uM("type" to "Number", "default" to -1), "longPressAction" to _uM("type" to "Object")))
        var propsNeedCastKeys = _uA(
            "src",
            "index"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
