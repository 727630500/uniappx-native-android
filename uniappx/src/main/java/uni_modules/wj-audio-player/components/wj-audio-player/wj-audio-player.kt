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
import uts.sdk.modules.wjAudioPlayer.NativeAudioPlayer
import uts.sdk.modules.wjAudioPlayer.OnPlaybackStateChangedCallback as OnPlaybackStateChangedCallback1
import uts.sdk.modules.wjAudioPlayer.OnProgressChangedCallback
import uts.sdk.modules.wjAudioPlayer.OnPlaybackCompletedCallback
import uts.sdk.modules.wjAudioPlayer.OnErrorCallback
open class GenUniModulesWjAudioPlayerComponentsWjAudioPlayerWjAudioPlayer : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var audioUrl: String by `$props`
    open var autoPlay: Boolean by `$props`
    open var volume: Number by `$props`
    open var progressColor: String by `$props`
    open var progressBackgroundColor: String by `$props`
    open var progressHeight: Number by `$props`
    open var progressRadius: Number by `$props`
    open var showControls: Boolean by `$props`
    open var seekEnabled: Boolean by `$props`
    open var resetProgressOnComplete: Boolean by `$props`
    open var play: () -> Unit
        get() {
            return unref(this.`$exposed`["play"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "play", value)
        }
    open var pause: () -> Unit
        get() {
            return unref(this.`$exposed`["pause"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "pause", value)
        }
    open var stop: () -> Unit
        get() {
            return unref(this.`$exposed`["stop"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "stop", value)
        }
    open var reset: () -> Unit
        get() {
            return unref(this.`$exposed`["reset"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "reset", value)
        }
    open var toggle: () -> Unit
        get() {
            return unref(this.`$exposed`["toggle"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "toggle", value)
        }
    open var seekTo: (position: Number) -> Unit
        get() {
            return unref(this.`$exposed`["seekTo"]) as (position: Number) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "seekTo", value)
        }
    open var setVolume: (vol: Number) -> Unit
        get() {
            return unref(this.`$exposed`["setVolume"]) as (vol: Number) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setVolume", value)
        }
    open var getCurrentTime: () -> Number
        get() {
            return unref(this.`$exposed`["getCurrentTime"]) as () -> Number
        }
        set(value) {
            setRefValue(this.`$exposed`, "getCurrentTime", value)
        }
    open var getDuration: () -> Number
        get() {
            return unref(this.`$exposed`["getDuration"]) as () -> Number
        }
        set(value) {
            setRefValue(this.`$exposed`, "getDuration", value)
        }
    open var getIsPlaying: () -> Boolean
        get() {
            return unref(this.`$exposed`["getIsPlaying"]) as () -> Boolean
        }
        set(value) {
            setRefValue(this.`$exposed`, "getIsPlaying", value)
        }
    open var setResetProgressOnComplete: (reset: Boolean) -> Unit
        get() {
            return unref(this.`$exposed`["setResetProgressOnComplete"]) as (reset: Boolean) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setResetProgressOnComplete", value)
        }
    open var getResetProgressOnComplete: () -> Boolean
        get() {
            return unref(this.`$exposed`["getResetProgressOnComplete"]) as () -> Boolean
        }
        set(value) {
            setRefValue(this.`$exposed`, "getResetProgressOnComplete", value)
        }
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesWjAudioPlayerComponentsWjAudioPlayerWjAudioPlayer, _arg1: SetupContext) -> Any? = fun(__props, ref1): Any? {
            var __expose = ref1.expose
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesWjAudioPlayerComponentsWjAudioPlayerWjAudioPlayer
            val _cache = __ins.renderCache
            var audioPlayer: NativeAudioPlayer? = null
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val isPlaying = ref(false)
            val currentTime = ref(0)
            val duration = ref(0)
            val progress = ref(0)
            fun gen_play_fn() {
                audioPlayer?.play()
            }
            val play = ::gen_play_fn
            fun gen_pause_fn() {
                audioPlayer?.pause()
            }
            val pause = ::gen_pause_fn
            fun gen_stop_fn() {
                audioPlayer?.stop()
            }
            val stop = ::gen_stop_fn
            fun gen_reset_fn() {
                audioPlayer?.reset()
            }
            val reset = ::gen_reset_fn
            fun gen_toggle_fn() {
                if (isPlaying.value) {
                    pause()
                } else {
                    play()
                }
            }
            val toggle = ::gen_toggle_fn
            fun gen_seekTo_fn(position: Number) {
                audioPlayer?.seekTo(position)
            }
            val seekTo = ::gen_seekTo_fn
            fun gen_setVolume_fn(vol: Number) {
                audioPlayer?.setVolume(vol)
            }
            val setVolume = ::gen_setVolume_fn
            fun gen_getCurrentTime_fn(): Number {
                return audioPlayer?.getCurrentTime() ?: 0
            }
            val getCurrentTime = ::gen_getCurrentTime_fn
            fun gen_getDuration_fn(): Number {
                return audioPlayer?.getDuration() ?: 0
            }
            val getDuration = ::gen_getDuration_fn
            fun gen_getIsPlaying_fn(): Boolean {
                return audioPlayer?.isPlaying() ?: false
            }
            val getIsPlaying = ::gen_getIsPlaying_fn
            fun gen_setResetProgressOnComplete_fn(reset: Boolean) {
                audioPlayer?.setResetProgressOnComplete(reset)
            }
            val setResetProgressOnComplete = ::gen_setResetProgressOnComplete_fn
            fun gen_getResetProgressOnComplete_fn(): Boolean {
                return audioPlayer?.getResetProgressOnComplete() ?: true
            }
            val getResetProgressOnComplete = ::gen_getResetProgressOnComplete_fn
            watchEffect(fun(){
                console.log("props.audioUrl", props.audioUrl)
                if (audioPlayer != null && props.audioUrl != "") {
                    audioPlayer?.setAudioSource(props.audioUrl)
                }
            }
            )
            watchEffect(fun(){
                if (audioPlayer != null) {
                    audioPlayer?.setVolume(props.volume)
                }
            }
            )
            watchEffect(fun(){
                if (audioPlayer != null) {
                    audioPlayer?.setProgressColor(props.progressColor)
                }
            }
            )
            watchEffect(fun(){
                if (audioPlayer != null) {
                    audioPlayer?.setProgressBackgroundColor(props.progressBackgroundColor)
                }
            }
            )
            watchEffect(fun(){
                if (audioPlayer != null) {
                    audioPlayer?.setProgressHeight(props.progressHeight)
                }
            }
            )
            watchEffect(fun(){
                if (audioPlayer != null) {
                    audioPlayer?.setProgressRadius(props.progressRadius)
                }
            }
            )
            watchEffect(fun(){
                if (audioPlayer != null) {
                    audioPlayer?.setSeekEnabled(props.seekEnabled)
                }
            }
            )
            watchEffect(fun(){
                if (audioPlayer != null) {
                    audioPlayer?.setResetProgressOnComplete(props.resetProgressOnComplete)
                }
            }
            )
            fun gen_onviewinit_fn(e: UniNativeViewInitEvent) {
                audioPlayer = NativeAudioPlayer(e.detail.element)
                audioPlayer?.setVolume(props.volume)
                audioPlayer?.setProgressColor(props.progressColor)
                audioPlayer?.setProgressBackgroundColor(props.progressBackgroundColor)
                audioPlayer?.setProgressHeight(props.progressHeight)
                audioPlayer?.setProgressRadius(props.progressRadius)
                audioPlayer?.setSeekEnabled(props.seekEnabled)
                audioPlayer?.setResetProgressOnComplete(props.resetProgressOnComplete)
                if (props.audioUrl != "") {
                    audioPlayer?.setAudioSource(props.audioUrl)
                }
                audioPlayer?.setOnPlaybackStateChangedCallback(fun(playing: Boolean){
                    isPlaying.value = playing
                    emit("playbackStateChanged", playing)
                }
                )
                audioPlayer?.setOnProgressChangedCallback(fun(current: Number, dur: Number, prog: Number){
                    currentTime.value = current
                    duration.value = dur
                    progress.value = prog
                    emit("progressChanged", object : UTSJSONObject() {
                        var currentTime = current
                        var duration = dur
                        var progress = prog
                    })
                    if (dur > 0 && duration.value !== dur) {
                        emit("ready", dur)
                    }
                }
                )
                audioPlayer?.setOnPlaybackCompletedCallback(fun(){
                    isPlaying.value = false
                    emit("playbackCompleted")
                }
                )
                audioPlayer?.setOnErrorCallback(fun(error: String){
                    emit("error", error)
                }
                )
                if (props.autoPlay) {
                    setTimeout(fun(){
                        play()
                    }
                    , 100)
                }
            }
            val onviewinit = ::gen_onviewinit_fn
            fun gen_onUnmounted_fn() {
                audioPlayer?.destroy()
                audioPlayer = null
            }
            val onUnmounted1 = ::gen_onUnmounted_fn
            __expose(_uM("play" to play, "pause" to pause, "stop" to stop, "reset" to reset, "toggle" to toggle, "seekTo" to seekTo, "setVolume" to setVolume, "getCurrentTime" to getCurrentTime, "getDuration" to getDuration, "getIsPlaying" to getIsPlaying, "setResetProgressOnComplete" to setResetProgressOnComplete, "getResetProgressOnComplete" to getResetProgressOnComplete, "isPlaying" to readonly(isPlaying), "currentTime" to readonly(currentTime), "duration" to readonly(duration), "progress" to readonly(progress)))
            return fun(): Any? {
                return _cE("native-view", _uM("onInit" to onviewinit, "style" to _nS(_uM("width" to "100%", "height" to (_ctx.progressHeight + "px")))), null, 36)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA())
        }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("playbackStateChanged" to null, "progressChanged" to null, "playbackCompleted" to null, "error" to null, "ready" to null)
        var props = _nP(_uM("audioUrl" to _uM("type" to "String", "required" to true, "default" to ""), "autoPlay" to _uM("type" to "Boolean", "required" to true, "default" to false), "volume" to _uM("type" to "Number", "required" to true, "default" to 1.0), "progressColor" to _uM("type" to "String", "required" to true, "default" to "#4e6ef2"), "progressBackgroundColor" to _uM("type" to "String", "required" to true, "default" to "#e0e0e0"), "progressHeight" to _uM("type" to "Number", "required" to true, "default" to 8), "progressRadius" to _uM("type" to "Number", "required" to true, "default" to 4), "showControls" to _uM("type" to "Boolean", "required" to true, "default" to true), "seekEnabled" to _uM("type" to "Boolean", "required" to true, "default" to false), "resetProgressOnComplete" to _uM("type" to "Boolean", "required" to true, "default" to true)))
        var propsNeedCastKeys = _uA(
            "audioUrl",
            "autoPlay",
            "volume",
            "progressColor",
            "progressBackgroundColor",
            "progressHeight",
            "progressRadius",
            "showControls",
            "seekEnabled",
            "resetProgressOnComplete"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
