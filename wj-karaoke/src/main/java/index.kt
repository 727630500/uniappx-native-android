@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uts.sdk.modules.wjKaraoke
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
import uts.sdk.modules.wjKaraoke.KaraokeTextView
interface INativeKaraokeContext {
    fun setPlayedColor(color: String)
    fun setUnplayedColor(color: String)
    fun setTextSizePX(size: Number)
    fun setMaxWidth(size: Number)
    fun setLyricData(lyrics: String)
    fun controlAnimation()
    fun pauseAnimation()
    fun resumeAnimation()
    fun setCharsColorInLyric(index: Number, startPosition: Number, endPosition: Number, colorString: String)
    fun setLyricScore(index: Number, score: String, colorString: String)
    fun setOnPlaybackStateChangedCallback(callback: (isPlaying: Boolean, lyricIndex: Number, lyricText: String, forceJump: Boolean) -> Unit)
    fun setOnPlayEndCallback(callback: () -> Unit)
    fun jumpToLyric(num: Number)
}
typealias OnLyricPlayedCallback = (index: Number, text: String) -> Unit
typealias OnPlaybackStateChangedCallback = (isPlaying: Boolean, lyricIndex: Number, lyricText: String, forceJump: Boolean) -> Unit
typealias OnPlayEndCallback = () -> Unit
open class NativeKaraoke {
    open var `$element`: UniNativeViewElement
    open var _hosText = "" as String
    private var onLyricPlayedCallback: OnLyricPlayedCallback? = null
    private var onPlaybackStateChangedCallback: OnPlaybackStateChangedCallback? = null
    private var onPlayEndCallback: OnPlayEndCallback? = null
    constructor(element: UniNativeViewElement){
        this.`$element` = element
        this.bindView()
    }
    open var input: KaraokeTextView? = null
    open fun bindView() {
        val arr = "[{\"text\": \"\", \"time\": 0, \"song\": \"\"}]"
        this.input = KaraokeTextView(this.`$element`.getAndroidActivity()!!)
        this.input?.setKaraokeLyrics(arr)
        this.`$element`.bindAndroidView(this.input!!)
        this.input?.setTextAlignment(KaraokeTextView.TextAlignment.LEFT)
        this.input?.setOnLyricPlayedCallback(fun(index: Number, text: String){
            this.onLyricPlayedCallback?.invoke(index, text)
        }
        )
        this.input?.setOnPlaybackStateChangedCallback(fun(isPlaying: Boolean, lyricIndex: Number, lyricText: String, forceJump: Boolean){
            this.onPlaybackStateChangedCallback?.invoke(isPlaying, lyricIndex, lyricText, forceJump)
        }
        )
        this.input?.setOnPlayEndCallback(fun(){
            this.onPlayEndCallback?.invoke()
        }
        )
    }
    open fun setTextAlignment(center: String) {
        if (center == "left") {
            this.input?.setTextAlignment(KaraokeTextView.TextAlignment.LEFT)
        }
        if (center == "center") {
            this.input?.setTextAlignment(KaraokeTextView.TextAlignment.CENTER)
        }
        if (center == "right") {
            this.input?.setTextAlignment(KaraokeTextView.TextAlignment.RIGHT)
        }
    }
    open fun setPlayedColor(color: String) {
        this.input?.setPlayedColor(color)
    }
    open fun setUnplayedColor(color: String) {
        this.input?.setUnplayedColor(color)
    }
    open fun setTextSizePX(size: Number) {
        this.input?.setTextSizePX(size.toFloat())
    }
    open fun setMaxWidth(size: Number) {
        this.input?.setMaxWidth(size.toInt())
    }
    open fun setLyricData(lyrics: String) {
        this.input?.setKaraokeLyrics(lyrics)
    }
    open fun controlAnimation() {
        if (this.input?.getIsPaused() == true) {
            console.log("controlAnimation: 当前处于暂停状态，调用resumeAnimation")
            this.input?.resumeAnimation()
        } else {
            console.log("controlAnimation: 当前未暂停，调用startLyricAnimation")
            this.input?.startLyricAnimation(true)
        }
    }
    open fun pauseAnimation() {
        console.log("zanting")
        this.input?.pauseAnimation()
    }
    open fun resumeAnimation() {
        console.log("jixu")
        this.input?.resumeAnimation()
    }
    open fun jumpToLyric(lyricIndex: Number) {
        console.log(lyricIndex)
        this.input?.jumpToLyric(lyricIndex.toInt())
    }
    open fun setBold(isBold: Boolean) {
        this.input?.setBold(isBold)
    }
    open fun setIsForceLineBreak(isBold: Boolean) {
        this.input?.setIsForceLineBreak(isBold)
    }
    open fun setShouldReserveScoreSpace(isBold: Boolean) {
        this.input?.setShouldReserveScoreSpace(isBold)
    }
    open fun setCharsColorInLyric(index: Number, startPosition: Number, endPosition: Number, colorString: String) {
        this.input?.setCharsColorInLyric(index.toInt(), startPosition.toInt(), endPosition.toInt(), colorString)
    }
    open fun setLyricScore(index: Number, score: String, colorString: String) {
        this.input?.setLyricScore(index.toInt(), score, colorString)
    }
    open fun destroy() {
        console.log(123)
    }
    open fun setOnLyricPlayedCallback(callback: OnLyricPlayedCallback?): Unit {
        this.onLyricPlayedCallback = callback
    }
    open fun setOnPlaybackStateChangedCallback(callback: OnPlaybackStateChangedCallback?): Unit {
        this.onPlaybackStateChangedCallback = callback
    }
    open fun setOnPlayEndCallback(callback: OnPlayEndCallback?): Unit {
        this.onPlayEndCallback = callback
    }
}
open class NaviteKrokContent : INativeKaraokeContext {
    private var _input: KaraokeTextView? = null
    constructor(_input: KaraokeTextView){
        this._input = _input
    }
    override fun setPlayedColor(color: String) {
        this._input?.setPlayedColor(color)
    }
    override fun setUnplayedColor(color: String) {
        this._input?.setUnplayedColor(color)
    }
    override fun setTextSizePX(size: Number) {
        this._input?.setTextSizePX(size.toFloat())
    }
    override fun setLyricData(lyrics: String) {
        this._input?.setKaraokeLyrics(lyrics)
    }
    open fun setTextAlignment(center: String) {
        if (center == "left") {
            this._input?.setTextAlignment(KaraokeTextView.TextAlignment.LEFT)
        }
        if (center == "center") {
            this._input?.setTextAlignment(KaraokeTextView.TextAlignment.CENTER)
        }
        if (center == "right") {
            this._input?.setTextAlignment(KaraokeTextView.TextAlignment.RIGHT)
        }
    }
    override fun controlAnimation() {
        this._input?.startLyricAnimation(true)
    }
    override fun pauseAnimation() {
        this._input?.pauseAnimation()
    }
    override fun resumeAnimation() {
        console.log("jixu")
        this._input?.resumeAnimation()
    }
    override fun jumpToLyric(lyricIndex: Number) {
        this._input?.jumpToLyric(lyricIndex.toInt())
    }
    override fun setMaxWidth(size: Number) {
        this._input?.setMaxWidth(size.toInt())
    }
    open fun setBold(isBold: Boolean) {
        this._input?.setBold(isBold)
    }
    override fun setCharsColorInLyric(index: Number, startPosition: Number, endPosition: Number, colorString: String) {
        this._input?.setCharsColorInLyric(index.toInt(), startPosition.toInt(), endPosition.toInt(), colorString)
    }
    override fun setLyricScore(index: Number, score: String, colorString: String) {
        this._input?.setLyricScore(index.toInt(), score, colorString)
    }
    override fun setOnPlaybackStateChangedCallback(callback: (isPlaying: Boolean, lyricIndex: Number, lyricText: String, forceJump: Boolean) -> Unit): Unit {
        this._input?.setOnPlaybackStateChangedCallback(callback)
    }
    override fun setOnPlayEndCallback(callback: () -> Unit): Unit {
        this._input?.setOnPlayEndCallback(callback)
    }
}
fun CreateNaviteKrokContent(id: String, ins: ComponentPublicInstance? = null): INativeKaraokeContext? {
    if (ins == null) {
        val pages = getCurrentPages()
        if (pages.length > 0) {
            val page = pages[pages.length - 1]
            val rootViewElement = page.getElementById(id)
            if (rootViewElement != null) {
                val nativeViewElement = iterateElement(rootViewElement)
                if (nativeViewElement != null) {
                    return NaviteKrokContent(nativeViewElement.getAndroidView<android.view.View>()!! as KaraokeTextView)
                }
            }
        }
    } else {
        if (ins.`$el` != null) {
            val nativeViewElement = iterateElement(ins.`$el` as UniElement)
            if (nativeViewElement != null) {
                return NaviteKrokContent(nativeViewElement.getAndroidView<android.view.View>()!! as KaraokeTextView)
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
