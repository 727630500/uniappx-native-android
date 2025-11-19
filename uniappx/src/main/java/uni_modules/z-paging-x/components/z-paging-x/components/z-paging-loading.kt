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
import io.dcloud.uniapp.extapi.getElementById as uni_getElementById
open class GenUniModulesZPagingXComponentsZPagingXComponentsZPagingLoading : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {
        onMounted(fun() {
            this.`$nextTick`(fun(){
                setTimeout(fun(){
                    this.element = uni_getElementById("z-paging-loading-img")
                    this.doRotate()
                }
                , 10)
            }
            )
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return _cE("image", _uM("class" to "zpx-loading-img", "id" to "z-paging-loading-img", "src" to _ctx.base64FlowerImg, "onTransitionend" to _ctx.doRotate), null, 40, _uA(
            "src",
            "onTransitionend"
        ))
    }
    open var times: Number by `$data`
    open var element: UniElement? by `$data`
    open var base64FlowerImg: Any? by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("times" to 0, "element" to null as UniElement?, "base64FlowerImg" to base64Flower)
    }
    open var doRotate = ::gen_doRotate_fn
    open fun gen_doRotate_fn() {
        if (this.element != null) {
            this.times = this.times + 1
            this.element!!.style.setProperty("transform", "rotate(" + this.times * 360 + "deg)")
            this.element!!.style.setProperty("transition-duration", "1000ms")
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
                return _uM("zpx-loading-img" to _pS(_uM("width" to "34rpx", "height" to "34rpx", "transitionDuration" to "2000ms", "transitionProperty" to "transform", "transitionTimingFunction" to "linear", "transform" to "rotate(0deg)")), "@TRANSITION" to _uM("zpx-loading-img" to _uM("duration" to "2000ms", "property" to "transform", "timingFunction" to "linear")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
