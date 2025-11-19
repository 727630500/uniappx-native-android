@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uts.sdk.modules.wjScroll
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
import uts.sdk.modules.wjScroll.ScrollTextView
typealias OnScrollCallback = () -> Unit
open class NativeScroll {
    open var `$element`: UniNativeViewElement
    private var scrollView: ScrollTextView? = null
    private var onScrollStartCallback: OnScrollCallback? = null
    private var onScrollEndCallback: OnScrollCallback? = null
    private var onScrollPauseCallback: OnScrollCallback? = null
    private var onScrollResumeCallback: OnScrollCallback? = null
    constructor(element: UniNativeViewElement){
        this.`$element` = element
        this.bindView()
    }
    open fun bindView() {
        this.scrollView = ScrollTextView(this.`$element`.getAndroidActivity()!!)
        this.`$element`.bindAndroidView(this.scrollView!!)
        this.scrollView?.setText("")
        this.scrollView?.setFontSize(48.0.toFloat())
        this.scrollView?.setTextColor("#000000")
        this.scrollView?.setScrollSpeed(50.0.toFloat())
        this.scrollView?.setScrollDirection("horizontal")
        this.scrollView?.setLoop(true)
        this.scrollView?.setAutoStart(true)
        this.scrollView?.setOnScrollStartCallback(fun(){
            this.onScrollStartCallback?.invoke()
        }
        )
        this.scrollView?.setOnScrollEndCallback(fun(){
            this.onScrollEndCallback?.invoke()
        }
        )
        this.scrollView?.setOnScrollPauseCallback(fun(){
            this.onScrollPauseCallback?.invoke()
        }
        )
        this.scrollView?.setOnScrollResumeCallback(fun(){
            this.onScrollResumeCallback?.invoke()
        }
        )
    }
    open fun setText(text: String) {
        this.scrollView?.setText(text)
    }
    open fun setFontSize(size: Number) {
        this.scrollView?.setFontSize(size.toFloat())
    }
    open fun setTextColor(color: String) {
        this.scrollView?.setTextColor(color)
    }
    open fun setScrollSpeed(speed: Number) {
        this.scrollView?.setScrollSpeed(speed.toFloat())
    }
    open fun setScrollDirection(direction: String) {
        this.scrollView?.setScrollDirection(direction)
    }
    open fun startScroll() {
        this.scrollView?.startScroll()
    }
    open fun pauseScroll() {
        this.scrollView?.pauseScroll()
    }
    open fun resumeScroll() {
        this.scrollView?.resumeScroll()
    }
    open fun stopScroll() {
        this.scrollView?.stopScroll()
    }
    open fun resetScroll() {
        this.scrollView?.resetScroll()
    }
    open fun setLoop(loop: Boolean) {
        this.scrollView?.setLoop(loop)
    }
    open fun setScrollWidth(width: Number) {
        console.log("setScrollWidth: " + width)
    }
    open fun setScrollHeight(height: Number) {
        console.log("setScrollHeight: " + height)
    }
    open fun setOnScrollStartCallback(callback: OnScrollCallback?) {
        this.onScrollStartCallback = callback
    }
    open fun setOnScrollEndCallback(callback: OnScrollCallback?) {
        this.onScrollEndCallback = callback
    }
    open fun setOnScrollPauseCallback(callback: OnScrollCallback?) {
        this.onScrollPauseCallback = callback
    }
    open fun setOnScrollResumeCallback(callback: OnScrollCallback?) {
        this.onScrollResumeCallback = callback
    }
}
open class INativeScrollContextImpl {
    private var nativeScroll: NativeScroll
    constructor(nativeScroll: NativeScroll){
        this.nativeScroll = nativeScroll
    }
    open fun setText(text: String): Unit {
        this.nativeScroll.setText(text)
    }
    open fun setFontSize(size: Number): Unit {
        this.nativeScroll.setFontSize(size)
    }
    open fun setTextColor(color: String): Unit {
        this.nativeScroll.setTextColor(color)
    }
    open fun setScrollSpeed(speed: Number): Unit {
        this.nativeScroll.setScrollSpeed(speed)
    }
    open fun setScrollDirection(direction: String): Unit {
        this.nativeScroll.setScrollDirection(direction)
    }
    open fun startScroll(): Unit {
        this.nativeScroll.startScroll()
    }
    open fun pauseScroll(): Unit {
        this.nativeScroll.pauseScroll()
    }
    open fun resumeScroll(): Unit {
        this.nativeScroll.resumeScroll()
    }
    open fun stopScroll(): Unit {
        this.nativeScroll.stopScroll()
    }
    open fun resetScroll(): Unit {
        this.nativeScroll.resetScroll()
    }
    open fun setLoop(loop: Boolean): Unit {
        this.nativeScroll.setLoop(loop)
    }
    open fun setScrollWidth(width: Number): Unit {
        this.nativeScroll.setScrollWidth(width)
    }
    open fun setScrollHeight(height: Number): Unit {
        this.nativeScroll.setScrollHeight(height)
    }
}
fun CreateNativeScrollContext(id: String, ins: ComponentPublicInstance? = null): INativeScrollContextImpl? {
    if (ins == null) {
        val pages = getCurrentPages()
        if (pages.length > 0) {
            val page = pages[pages.length - 1]
            val rootViewElement = page.getElementById(id)
            if (rootViewElement != null) {
                val nativeViewElement = findNativeViewElement(rootViewElement)
                if (nativeViewElement != null) {
                    val nativeScroll = NativeScroll(nativeViewElement)
                    return INativeScrollContextImpl(nativeScroll)
                }
            }
        }
    } else {
        val rootViewElement = ins.`$`.refs[id] as UniNativeViewElement
        if (rootViewElement != null) {
            val nativeViewElement = findNativeViewElement(rootViewElement)
            if (nativeViewElement != null) {
                val nativeScroll = NativeScroll(nativeViewElement)
                return INativeScrollContextImpl(nativeScroll)
            }
        }
    }
    return null
}
fun findNativeViewElement(element: UniElement): UniNativeViewElement? {
    if (element is UniNativeViewElement) {
        return element
    }
    run {
        var i: Number = 0
        while(i < element.children.length){
            val child = element.children[i]
            val result = findNativeViewElement(child)
            if (result != null) {
                return result
            }
            i++
        }
    }
    return null
}
