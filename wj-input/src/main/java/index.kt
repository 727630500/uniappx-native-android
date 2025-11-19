@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uts.sdk.modules.wjInput
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
import uts.sdk.modules.wjInput.NoPasteEditText
interface INativeInputContext {
    fun updateText(text: String)
    fun getSelection(): Number
}
open class NativeInput {
    open var `$element`: UniNativeViewElement
    open var _hosText = "" as String
    constructor(element: UniNativeViewElement){
        this.`$element` = element
        this.bindView()
    }
    open var input: NoPasteEditText? = null
    open fun bindView() {
        this.input = NoPasteEditText(this.`$element`.getAndroidActivity()!!)
        this.input?.setBackground(null)
        this.input?.onTapCallback(fun(){
            val detail: UTSJSONObject = UTSJSONObject()
            val event = UniNativeViewEvent("customClick", detail)
            this.`$element`.dispatchEvent(event)
        }
        )
        this.input?.startCursorPositionMonitoring(fun(cursor: Int){
            var detail: UTSJSONObject = UTSJSONObject()
            detail.set("wz", cursor)
            val event = UniNativeViewEvent("cursorWz", detail)
            this.`$element`.dispatchEvent(event)
        }
        )
        this.input?.onInput(fun(str: String){
            var detail: UTSJSONObject = UTSJSONObject()
            detail.set("str", str)
            val event = UniNativeViewEvent("onInput", detail)
            this.`$element`.dispatchEvent(event)
        }
        )
        this.input?.onPositionSize(fun(posX: Float, posY: Float, width: Int, height: Int, screenH: Int){
            var detail: UTSJSONObject = UTSJSONObject()
            detail.set("posX", posX)
            detail.set("posY", posY)
            detail.set("width", width)
            detail.set("height", height)
            detail.set("screenH", screenH)
            val event = UniNativeViewEvent("getPos", detail)
            this.`$element`.dispatchEvent(event)
        }
        )
        this.input?.onValidationError(fun(_, _){
            var detail: UTSJSONObject = UTSJSONObject()
            val event = UniNativeViewEvent("onErr", detail)
            this.`$element`.dispatchEvent(event)
        }
        )
        this.input?.onValidationSuccess(fun(){
            var detail: UTSJSONObject = UTSJSONObject()
            val event = UniNativeViewEvent("onSuccess", detail)
            this.`$element`.dispatchEvent(event)
        }
        )
        this.`$element`.bindAndroidView(this.input!!)
    }
    open fun setShowSoftInputOnFocus(type: Boolean) {
        this.input?.setShowSoftInputOnFocus(type)
    }
    open fun setFocus(type: Boolean) {
        if (type) {
            this.input?.requestFocusDirectly()
        } else {
            this.input?.clearFocusDirectly()
        }
    }
    open fun setBlockKeyboardInput(block: Boolean) {
        this.input?.setBlockKeyboardInput(block)
    }
    open fun setDefaultTextSize(size: Number) {
        this.input?.setDefaultTextSize(size.toFloat())
    }
    open fun setDefaultTextSizeRpx(size: Number) {
        this.input?.setDefaultTextSizeRpx(size.toFloat())
    }
    open fun setTextProgrammatically(text: String) {
        this.input?.setTextProgrammatically(text)
        this._hosText = text
    }
    open fun updateText(text: String) {
        this.input?.setText(text)
        this._hosText = text
    }
    open fun setAutoWidth(isAuto: Boolean) {
        this.input?.setAutoWidth(isAuto)
    }
    open fun setValidationMode(isAuto: Boolean) {
        this.input?.setValidationMode(isAuto)
    }
    open fun setValidationText(txt: String) {
        this.input?.setValidationText(txt)
    }
    open fun setForceGetFocusAfterReplace(isAuto: Boolean) {
        this.input?.setForceGetFocusAfterReplace(isAuto)
    }
    open fun setTextAlignment(center: String) {
        if (center == "center") {
            this.input?.setTextAlignment(NativeInput.ALIGN_CENTER)
        }
        if (center == "left") {
            this.input?.setTextAlignment(NativeInput.ALIGN_LEFT)
        }
        if (center == "right") {
            this.input?.setTextAlignment(NativeInput.ALIGN_RIGHT)
        }
    }
    open fun destroy() {}
    companion object {
        val ALIGN_LEFT: Int = 3
        val ALIGN_CENTER: Int = 17
        val ALIGN_RIGHT: Int = 5
    }
}
open class NativeInputContext : INativeInputContext {
    private var _input: NoPasteEditText? = null
    constructor(_input: NoPasteEditText){
        this._input = _input
    }
    override fun updateText(text: String) {
        if (text == "") {
            return
        }
        this._input?.setText(text)
    }
    override fun getSelection(): Number {
        var start = this._input?.getSelectionStart()
        if (start != null) {
            return start
        }
        return 0
    }
    open fun setAutoWidth(isAuto: Boolean) {
        this._input?.setAutoWidth(isAuto)
    }
}
fun CreateNativeInputContext(id: String, ins: ComponentPublicInstance? = null): INativeInputContext? {
    if (ins == null) {
        val pages = getCurrentPages()
        if (pages.length > 0) {
            val page = pages[pages.length - 1]
            val rootViewElement = page.getElementById(id)
            if (rootViewElement != null) {
                val nativeViewElement = iterateElement(rootViewElement)
                if (nativeViewElement != null) {
                    return NativeInputContext(nativeViewElement.getAndroidView<android.view.View>()!! as NoPasteEditText)
                }
            }
        }
    } else {
        if (ins.`$el` != null) {
            val nativeViewElement = iterateElement(ins.`$el` as UniElement)
            if (nativeViewElement != null) {
                return NativeInputContext(nativeViewElement.getAndroidView<android.view.View>()!! as NoPasteEditText)
            }
        }
    }
    return null
}
fun iterateElement(homeElement: UniElement): UniElement? {
    if ("NATIVE-VIEW" == homeElement.nodeName) {
        return homeElement
    }
    for(perChildEle in resolveUTSValueIterator(homeElement.children)){
        var findEle = iterateElement(perChildEle)
        if (findEle != null) {
            return findEle
        }
    }
    return null
}
