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
open class GenUniModulesLimeCircleComponentsLCircleLCircle : VueComponent, CircleProps {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    override var canvasId: String? by `$props`
    override var size: String by `$props`
    override var percent: Number by `$props`
    override var lineCap: String by `$props`
    override var strokeWidth: Any by `$props`
    override var strokeColor: Any? by `$props`
    override var trailWidth: Any by `$props`
    override var trailColor: String? by `$props`
    override var dashboard: Boolean by `$props`
    override var clockwise: Boolean by `$props`
    override var duration: Number by `$props`
    override var max: Number by `$props`
    override var gapDegree: Number by `$props`
    override var gapPosition: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesLimeCircleComponentsLCircleLCircle) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesLimeCircleComponentsLCircleLCircle
            val _cache = __ins.renderCache
            val themeVars = inject("limeConfigProviderThemeVars", computed(fun(): UTSJSONObject {
                return (UTSJSONObject())
            }
            ))
            fun emits(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val props = __props
            val instance = getCurrentInstance()!!
            val context = instance.proxy!!
            val uuid = Math.random().toString(16).slice(2)
            val innerCanvasId = "l-circle-" + (props.canvasId ?: (instance.uid + uuid))
            val classes = computed<Map<String, Boolean>>(fun(): Map<String, Boolean> {
                val cls = Map<String, Boolean>()
                cls.set("clockwise", !props.clockwise)
                cls.set("is-" + props.lineCap, true)
                return cls
            }
            )
            val contentStyles = computed<String>(fun(): String {
                return if (!props.clockwise) {
                    "transform: scale(-1,1)"
                } else {
                    ""
                }
            }
            )
            val styles = computed<Map<String, String>>(fun(): Map<String, String> {
                val style = Map<String, String>()
                val size = addUnit(props.size)
                if (size != null) {
                    style.set("width", size)
                    style.set("height", size)
                }
                return style
            }
            )
            val circleRef = ref<UniElement?>(null)
            var circle: Circle? = null
            val percent = ref<Number>(0)
            val current = useTransition(percent, UseTransitionOptions(duration = props.duration, immediate = true))
            val stopCurrent = watch(current, fun(v: Number){
                val value = Math.floor(v * 100) / 100
                circle?.play(value)
                emits("update:current", value)
            }
            )
            val stopPercent = watch(fun(): Number {
                return props.percent
            }
            , fun(v: Number){
                percent.value = Math.min(v, props.max)
            }
            )
            onMounted(fun(){
                nextTick(fun(){
                    val option = CircleOptions(size = unitConvert(props.size), lineCap = props.lineCap, strokeWidth = unitConvert(props.strokeWidth), strokeColor = props.strokeColor ?: "" + (themeVars.value["circleStrokeColor"] ?: "#3283ff"), trailColor = props.trailColor ?: "" + (themeVars.value["circleTrailColor"] ?: "#eaeef2"), trailWidth = unitConvert(props.trailWidth), dashboard = props.dashboard, max = props.max, gapDegree = props.gapDegree, gapPosition = props.gapPosition)
                    val ctx = circleRef.value!!.getDrawableContext()
                    circle = Circle(ctx!!)
                    circle!!.setOption(option)
                    percent.value = props.percent
                }
                )
            }
            )
            watchEffect(fun(){
                val strokeColor = props.strokeColor ?: "" + (themeVars.value["circleStrokeColor"] ?: "#3283ff")
                val trailColor = props.trailColor ?: "" + (themeVars.value["circleTrailColor"] ?: "#eaeef2")
                circle?.setOption(CircleOptions(dashboard = props.dashboard, strokeColor = strokeColor, trailColor = trailColor))
                circle?.render()
            }
            )
            onUnmounted(fun(){
                stopCurrent()
                stopPercent()
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("ref_key" to "circleRef", "ref" to circleRef, "class" to _nC(_uA(
                    "l-circle",
                    unref(classes)
                )), "style" to _nS(_uA(
                    unref(styles)
                ))), _uA(
                    _cE("view", _uM("class" to "l-circle__inner", "style" to _nS(unref(contentStyles))), _uA(
                        renderSlot(_ctx.`$slots`, "default")
                    ), 4)
                ), 6)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("l-circle__inner" to _pS(_uM("flex" to 1, "alignItems" to "center", "justifyContent" to "center")), "clockwise" to _pS(_uM("transform" to "scale(-1, 1)")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("update:current" to null)
        var props = _nP(_uM("canvasId" to _uM("type" to "String", "required" to false), "size" to _uM("type" to "String", "required" to true, "default" to "120px"), "percent" to _uM("type" to "Number", "required" to true, "default" to 0), "lineCap" to _uM("type" to "String", "required" to true, "default" to "round"), "strokeWidth" to _uM("required" to true, "default" to 6), "strokeColor" to _uM("required" to false), "trailWidth" to _uM("required" to true, "default" to 6), "trailColor" to _uM("type" to "String", "required" to false), "dashboard" to _uM("type" to "Boolean", "required" to true, "default" to false), "clockwise" to _uM("type" to "Boolean", "required" to true, "default" to true), "duration" to _uM("type" to "Number", "required" to true, "default" to 300), "max" to _uM("type" to "Number", "required" to true, "default" to 100), "gapDegree" to _uM("type" to "Number", "required" to true, "default" to 90), "gapPosition" to _uM("type" to "String", "required" to true, "default" to "bottom")))
        var propsNeedCastKeys = _uA(
            "size",
            "percent",
            "lineCap",
            "strokeWidth",
            "trailWidth",
            "dashboard",
            "clockwise",
            "duration",
            "max",
            "gapDegree",
            "gapPosition"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
