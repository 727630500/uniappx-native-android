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
import uts.sdk.modules.wjScroll.NativeScroll
open class GenUniModulesWjScrollComponentsWjScrollWjScroll : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var text: String by `$props`
    open var fontSize: Number by `$props`
    open var textColor: String by `$props`
    open var scrollSpeed: Number by `$props`
    open var scrollDirection: String by `$props`
    open var loop: Boolean by `$props`
    open var autoStart: Boolean by `$props`
    open var width: Number by `$props`
    open var height: Number by `$props`
    open var startScroll: () -> Unit
        get() {
            return unref(this.`$exposed`["startScroll"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "startScroll", value)
        }
    open var pauseScroll: () -> Unit
        get() {
            return unref(this.`$exposed`["pauseScroll"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "pauseScroll", value)
        }
    open var resumeScroll: () -> Unit
        get() {
            return unref(this.`$exposed`["resumeScroll"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "resumeScroll", value)
        }
    open var stopScroll: () -> Unit
        get() {
            return unref(this.`$exposed`["stopScroll"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "stopScroll", value)
        }
    open var resetScroll: () -> Unit
        get() {
            return unref(this.`$exposed`["resetScroll"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "resetScroll", value)
        }
    open var setText: (text: String) -> Unit
        get() {
            return unref(this.`$exposed`["setText"]) as (text: String) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setText", value)
        }
    open var setFontSize: (size: Number) -> Unit
        get() {
            return unref(this.`$exposed`["setFontSize"]) as (size: Number) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setFontSize", value)
        }
    open var setTextColor: (color: String) -> Unit
        get() {
            return unref(this.`$exposed`["setTextColor"]) as (color: String) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setTextColor", value)
        }
    open var setScrollSpeed: (speed: Number) -> Unit
        get() {
            return unref(this.`$exposed`["setScrollSpeed"]) as (speed: Number) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setScrollSpeed", value)
        }
    open var setScrollDirection: (direction: String) -> Unit
        get() {
            return unref(this.`$exposed`["setScrollDirection"]) as (direction: String) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setScrollDirection", value)
        }
    open var setLoop: (loop: Boolean) -> Unit
        get() {
            return unref(this.`$exposed`["setLoop"]) as (loop: Boolean) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setLoop", value)
        }
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesWjScrollComponentsWjScrollWjScroll, _arg1: SetupContext) -> Any? = fun(__props, ref1): Any? {
            var __expose = ref1.expose
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesWjScrollComponentsWjScrollWjScroll
            val _cache = __ins.renderCache
            var scrollInstance: NativeScroll? = null
            val props = __props
            var oldText = ""
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            fun gen_applyProps_fn() {
                if (scrollInstance == null) {
                    return
                }
                scrollInstance?.setFontSize(props.fontSize)
                scrollInstance?.setTextColor(props.textColor)
                scrollInstance?.setScrollSpeed(props.scrollSpeed)
                scrollInstance?.setScrollDirection(props.scrollDirection)
                scrollInstance?.setLoop(props.loop)
                if (props.text != oldText) {
                    scrollInstance?.setText(props.text)
                    oldText = props.text
                    if (props.autoStart && props.text.length > 0) {
                        scrollInstance?.startScroll()
                    }
                }
            }
            val applyProps = ::gen_applyProps_fn
            watchEffect(fun(){
                applyProps()
            }
            )
            fun gen_onViewInit_fn(e: UniNativeViewInitEvent) {
                scrollInstance = NativeScroll(e.detail.element)
                scrollInstance?.setOnScrollStartCallback(fun(){
                    emit("onScrollStart")
                }
                )
                scrollInstance?.setOnScrollEndCallback(fun(){
                    emit("onScrollEnd")
                }
                )
                scrollInstance?.setOnScrollPauseCallback(fun(){
                    emit("onScrollPause")
                }
                )
                scrollInstance?.setOnScrollResumeCallback(fun(){
                    emit("onScrollResume")
                }
                )
                applyProps()
            }
            val onViewInit = ::gen_onViewInit_fn
            fun gen_startScroll_fn() {
                scrollInstance?.startScroll()
            }
            val startScroll = ::gen_startScroll_fn
            fun gen_pauseScroll_fn() {
                scrollInstance?.pauseScroll()
            }
            val pauseScroll = ::gen_pauseScroll_fn
            fun gen_resumeScroll_fn() {
                scrollInstance?.resumeScroll()
            }
            val resumeScroll = ::gen_resumeScroll_fn
            fun gen_stopScroll_fn() {
                scrollInstance?.stopScroll()
            }
            val stopScroll = ::gen_stopScroll_fn
            fun gen_resetScroll_fn() {
                scrollInstance?.resetScroll()
            }
            val resetScroll = ::gen_resetScroll_fn
            fun gen_setText_fn(text: String) {
                scrollInstance?.setText(text)
            }
            val setText = ::gen_setText_fn
            fun gen_setFontSize_fn(size: Number) {
                scrollInstance?.setFontSize(size)
            }
            val setFontSize = ::gen_setFontSize_fn
            fun gen_setTextColor_fn(color: String) {
                scrollInstance?.setTextColor(color)
            }
            val setTextColor = ::gen_setTextColor_fn
            fun gen_setScrollSpeed_fn(speed: Number) {
                scrollInstance?.setScrollSpeed(speed)
            }
            val setScrollSpeed = ::gen_setScrollSpeed_fn
            fun gen_setScrollDirection_fn(direction: String) {
                scrollInstance?.setScrollDirection(direction)
            }
            val setScrollDirection = ::gen_setScrollDirection_fn
            fun gen_setLoop_fn(loop: Boolean) {
                scrollInstance?.setLoop(loop)
            }
            val setLoop = ::gen_setLoop_fn
            __expose(_uM("startScroll" to startScroll, "pauseScroll" to pauseScroll, "resumeScroll" to resumeScroll, "stopScroll" to stopScroll, "resetScroll" to resetScroll, "setText" to setText, "setFontSize" to setFontSize, "setTextColor" to setTextColor, "setScrollSpeed" to setScrollSpeed, "setScrollDirection" to setScrollDirection, "setLoop" to setLoop))
            return fun(): Any? {
                return _cE("native-view", _uM("onInit" to onViewInit, "style" to _nS(_uM("width" to "100%", "height" to "100%"))), null, 36)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA())
        }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("onScrollStart" to null, "onScrollEnd" to null, "onScrollPause" to null, "onScrollResume" to null)
        var props = _nP(_uM("text" to _uM("type" to "String", "required" to true, "default" to ""), "fontSize" to _uM("type" to "Number", "required" to true, "default" to 16), "textColor" to _uM("type" to "String", "required" to true, "default" to "#000000"), "scrollSpeed" to _uM("type" to "Number", "required" to true, "default" to 50), "scrollDirection" to _uM("type" to "String", "required" to true, "default" to "horizontal"), "loop" to _uM("type" to "Boolean", "required" to true, "default" to true), "autoStart" to _uM("type" to "Boolean", "required" to true, "default" to true), "width" to _uM("type" to "Number", "required" to true, "default" to 300), "height" to _uM("type" to "Number", "required" to true, "default" to 50)))
        var propsNeedCastKeys = _uA(
            "text",
            "fontSize",
            "textColor",
            "scrollSpeed",
            "scrollDirection",
            "loop",
            "autoStart",
            "width",
            "height"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
