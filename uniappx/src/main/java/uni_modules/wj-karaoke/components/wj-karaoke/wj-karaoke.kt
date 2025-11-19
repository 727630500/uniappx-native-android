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
import uts.sdk.modules.wjKaraoke.NativeKaraoke
import uts.sdk.modules.wjKaraoke.OnLyricPlayedCallback
import uts.sdk.modules.wjKaraoke.OnPlaybackStateChangedCallback
import uts.sdk.modules.wjKaraoke.OnPlayEndCallback
open class GenUniModulesWjKaraokeComponentsWjKaraokeWjKaraoke : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var songArr: String by `$props`
    open var fontSize: Number by `$props`
    open var maxWidth: Number by `$props`
    open var paly: Boolean by `$props`
    open var center: String by `$props`
    open var isBold: Boolean by `$props`
    open var forcePlay: Boolean by `$props`
    open var shouldReserve: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesWjKaraokeComponentsWjKaraokeWjKaraoke) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesWjKaraokeComponentsWjKaraokeWjKaraoke
            val _cache = __ins.renderCache
            var Karaoke: NativeKaraoke? = null
            val props = __props
            var oldStr = ""
            val isPlay = ref(false)
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            fun gen_updateSong_fn(value: String) {
                Karaoke?.setLyricData(value)
            }
            val updateSong = ::gen_updateSong_fn
            fun gen_setTextSizePX_fn(size: Number) {
                Karaoke?.setTextSizePX(size)
            }
            val setTextSizePX = ::gen_setTextSizePX_fn
            fun gen_controlAnimation_fn() {
                Karaoke?.controlAnimation()
            }
            val controlAnimation = ::gen_controlAnimation_fn
            fun gen_pauseAnimation_fn() {
                Karaoke?.controlAnimation()
            }
            val pauseAnimation = ::gen_pauseAnimation_fn
            fun gen_resumeAnimation_fn() {
                Karaoke?.resumeAnimation()
            }
            val resumeAnimation = ::gen_resumeAnimation_fn
            fun gen_jumpToLyric_fn(lyricIndex: Number) {
                Karaoke?.jumpToLyric(lyricIndex)
            }
            val jumpToLyric = ::gen_jumpToLyric_fn
            fun gen_setMaxWidth_fn(size: Number) {
                Karaoke?.setMaxWidth(size)
            }
            val setMaxWidth = ::gen_setMaxWidth_fn
            fun gen_setTextAlignment_fn(center: String) {
                Karaoke?.setTextAlignment(center)
            }
            val setTextAlignment = ::gen_setTextAlignment_fn
            fun gen_setBold_fn(isBild: Boolean) {
                Karaoke?.setBold(isBild)
            }
            val setBold = ::gen_setBold_fn
            fun gen_setIsForceLineBreak_fn(isBild: Boolean) {
                Karaoke?.setIsForceLineBreak(isBild)
            }
            val setIsForceLineBreak = ::gen_setIsForceLineBreak_fn
            fun gen_setShouldReserveScoreSpace_fn(isBild: Boolean) {
                Karaoke?.setShouldReserveScoreSpace(isBild)
            }
            val setShouldReserveScoreSpace = ::gen_setShouldReserveScoreSpace_fn
            fun gen_setCharsColorInLyric_fn(index: Number, startPosition: Number, endPosition: Number, colorString: String) {
                Karaoke?.setCharsColorInLyric(index, startPosition, endPosition, colorString)
            }
            val setCharsColorInLyric = ::gen_setCharsColorInLyric_fn
            fun gen_setLyricScore_fn(index: Number, score: String, colorString: String) {
                Karaoke?.setLyricScore(index, score, colorString)
            }
            val setLyricScore = ::gen_setLyricScore_fn
            watchEffect(fun(){
                val text = props.songArr
                val play = props.paly
                setTextAlignment(props.center)
                setIsForceLineBreak(true)
                setMaxWidth(props.maxWidth)
                setShouldReserveScoreSpace(props.shouldReserve)
                if (oldStr != text) {
                    updateSong(text)
                    oldStr = text
                }
                if (props.forcePlay) {
                    controlAnimation()
                }
            }
            )
            fun gen_onviewinit_fn(e: UniNativeViewInitEvent) {
                Karaoke = NativeKaraoke(e.detail.element)
                Karaoke?.setTextSizePX(props.fontSize)
                Karaoke?.setPlayedColor("#4e6ef2")
                Karaoke?.setUnplayedColor("#000000")
                Karaoke?.setMaxWidth(props.maxWidth)
                Karaoke?.setLyricData(props.songArr)
                Karaoke?.setShouldReserveScoreSpace(props.shouldReserve)
                Karaoke?.setOnLyricPlayedCallback(fun(index: Number, text: String){
                    emit("onPlayCall", _uO("index" to index, "text" to text))
                }
                )
                Karaoke?.setOnPlaybackStateChangedCallback(fun(isPlaying: Boolean, lyricIndex: Number, lyricText: String, forceJump: Boolean){
                    emit("playing", _uO("isPlaying" to isPlaying, "lyricIndex" to lyricIndex, "lyricText" to lyricText, "forceJump" to forceJump))
                }
                )
                Karaoke?.setOnPlayEndCallback(fun(){
                    emit("playEnd", UTSJSONObject())
                }
                )
                if (props.forcePlay) {
                    Karaoke?.controlAnimation()
                }
            }
            val onviewinit = ::gen_onviewinit_fn
            fun gen_onUnmounted_fn() {}
            val onUnmounted1 = ::gen_onUnmounted_fn
            return fun(): Any? {
                return _cE("native-view", _uM("onInit" to onviewinit), null, 32)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA())
        }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("playing" to null, "cursorChange" to null, "onPlayCall" to null, "playEnd" to null)
        var props = _nP(_uM("songArr" to _uM("type" to "String", "required" to true, "default" to ""), "fontSize" to _uM("type" to "Number", "required" to true, "default" to 50), "maxWidth" to _uM("type" to "Number", "required" to true, "default" to 0), "paly" to _uM("type" to "Boolean", "required" to true, "default" to false), "center" to _uM("type" to "String", "required" to true, "default" to "left"), "isBold" to _uM("type" to "Boolean", "required" to true, "default" to false), "forcePlay" to _uM("type" to "Boolean", "required" to true, "default" to false), "shouldReserve" to _uM("type" to "Boolean", "required" to true, "default" to false)))
        var propsNeedCastKeys = _uA(
            "songArr",
            "fontSize",
            "maxWidth",
            "paly",
            "center",
            "isBold",
            "forcePlay",
            "shouldReserve"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
