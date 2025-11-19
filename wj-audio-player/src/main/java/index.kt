@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uts.sdk.modules.wjAudioPlayer
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
import uts.sdk.modules.wjAudioPlayer.AudioPlayerView
typealias OnPlaybackStateChangedCallback = (isPlaying: Boolean) -> Unit
typealias OnProgressChangedCallback = (currentTime: Number, duration: Number, progress: Number) -> Unit
typealias OnPlaybackCompletedCallback = () -> Unit
typealias OnErrorCallback = (error: String) -> Unit
open class NativeAudioPlayer {
    open var `$element`: UniNativeViewElement
    private var audioPlayerView: AudioPlayerView? = null
    private var onPlaybackStateChangedCallback: OnPlaybackStateChangedCallback? = null
    private var onProgressChangedCallback: OnProgressChangedCallback? = null
    private var onPlaybackCompletedCallback: OnPlaybackCompletedCallback? = null
    private var onErrorCallback: OnErrorCallback? = null
    constructor(element: UniNativeViewElement){
        this.`$element` = element
        this.bindView()
    }
    open fun bindView() {
        this.audioPlayerView = AudioPlayerView(this.`$element`.getAndroidActivity()!!)
        this.`$element`.bindAndroidView(this.audioPlayerView!!)
        this.audioPlayerView?.setProgressColor("#4e6ef2")
        this.audioPlayerView?.setProgressBackgroundColor("#e0e0e0")
        this.audioPlayerView?.setProgressHeight(4)
        this.audioPlayerView?.setProgressRadius(2)
        this.audioPlayerView?.setOnPlaybackStateChangedCallback(fun(isPlaying: Boolean){
            this.onPlaybackStateChangedCallback?.invoke(isPlaying)
        }
        )
        this.audioPlayerView?.setOnProgressChangedCallback(fun(currentTime: Number, duration: Number, progress: Number){
            this.onProgressChangedCallback?.invoke(currentTime, duration, progress)
        }
        )
        this.audioPlayerView?.setOnPlaybackCompletedCallback(fun(){
            this.onPlaybackCompletedCallback?.invoke()
        }
        )
        this.audioPlayerView?.setOnErrorCallback(fun(error: String){
            this.onErrorCallback?.invoke(error)
        }
        )
    }
    open fun setAudioSource(url: String) {
        this.audioPlayerView?.setAudioSource(url)
    }
    open fun play() {
        this.audioPlayerView?.play()
    }
    open fun pause() {
        this.audioPlayerView?.pause()
    }
    open fun stop() {
        this.audioPlayerView?.stop()
    }
    open fun reset() {
        this.audioPlayerView?.reset()
    }
    open fun seekTo(position: Number) {
        this.audioPlayerView?.seekTo(position.toInt())
    }
    open fun setVolume(volume: Number) {
        this.audioPlayerView?.setVolume(volume.toFloat())
    }
    open fun setProgressColor(color: String) {
        this.audioPlayerView?.setProgressColor(color)
    }
    open fun setProgressBackgroundColor(color: String) {
        this.audioPlayerView?.setProgressBackgroundColor(color)
    }
    open fun setProgressHeight(height: Number) {
        this.audioPlayerView?.setProgressHeight(height.toInt())
    }
    open fun setProgressRadius(radius: Number) {
        this.audioPlayerView?.setProgressRadius(radius.toInt())
    }
    open fun setSeekEnabled(enabled: Boolean) {
        this.audioPlayerView?.setSeekEnabled(enabled)
    }
    open fun isSeekEnabled(): Boolean {
        return this.audioPlayerView?.isSeekEnabled() ?: true
    }
    open fun setOnPlaybackStateChangedCallback(callback: OnPlaybackStateChangedCallback) {
        this.onPlaybackStateChangedCallback = callback
    }
    open fun setOnProgressChangedCallback(callback: OnProgressChangedCallback) {
        this.onProgressChangedCallback = callback
    }
    open fun setOnPlaybackCompletedCallback(callback: OnPlaybackCompletedCallback) {
        this.onPlaybackCompletedCallback = callback
    }
    open fun setOnErrorCallback(callback: OnErrorCallback) {
        this.onErrorCallback = callback
    }
    open fun getCurrentTime(): Number {
        return this.audioPlayerView?.getCurrentTime() ?: 0
    }
    open fun getDuration(): Number {
        return this.audioPlayerView?.getDuration() ?: 0
    }
    open fun isPlaying(): Boolean {
        return this.audioPlayerView?.isPlaying() ?: false
    }
    open fun setResetProgressOnComplete(reset: Boolean) {
        this.audioPlayerView?.setResetProgressOnComplete(reset)
    }
    open fun getResetProgressOnComplete(): Boolean {
        return this.audioPlayerView?.getResetProgressOnComplete() ?: true
    }
    open fun destroy() {
        this.audioPlayerView?.destroy()
        this.audioPlayerView = null
    }
}
