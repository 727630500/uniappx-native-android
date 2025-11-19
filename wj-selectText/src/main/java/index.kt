@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uts.sdk.modules.wjSelectText
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
import uts.sdk.modules.wjSelectText.SelectableTextView
open class selectInput {
    open var `$element`: UniNativeViewElement
    open var _hosText = "" as String
    constructor(element: UniNativeViewElement){
        this.`$element` = element
        this.bindView()
    }
    open var input: SelectableTextView? = null
    open fun bindView() {
        this.input = SelectableTextView(this.`$element`.getAndroidActivity()!!)
        this.input?.onTextSelected(fun(str: String){
            var detail: UTSJSONObject = UTSJSONObject()
            detail.set("str", str)
            val event = UniNativeViewEvent("onInput", detail)
            this.`$element`.dispatchEvent(event)
        }
        )
        this.input?.onTextHeight(fun(height: Number){
            var detail: UTSJSONObject = UTSJSONObject()
            detail.set("height", height)
            val event = UniNativeViewEvent("onTextHeight", detail)
            this.`$element`.dispatchEvent(event)
        }
        )
        this.`$element`.bindAndroidView(this.input!!)
    }
    open fun updateText(text: String) {
        this.input?.setText(text)
        this._hosText = text
    }
    open fun setJsonText(text: String) {
        this.input?.setJsonText(text)
    }
    open fun setWordBookEnabled(enabled: Boolean) {
        this.input?.setWordBookEnabled(enabled)
    }
    open fun destroy() {}
    companion object {
        val ALIGN_LEFT: Number = 3
        val ALIGN_CENTER: Number = 17
        val ALIGN_RIGHT: Number = 5
    }
}
