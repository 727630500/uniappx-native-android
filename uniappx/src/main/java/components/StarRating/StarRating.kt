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
open class GenComponentsStarRatingStarRating : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var value: Number by `$props`
    open var maxStars: Number by `$props`
    open var size: String by `$props`
    open var spacing: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsStarRatingStarRating) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsStarRatingStarRating
            val _cache = __ins.renderCache
            val props = __props
            val STAR_IMAGES: UTSJSONObject = object : UTSJSONObject() {
                var full = "/static/ico/armmar_stat.png" as String
                var half = "/static/ico/armmar_t_stat50.png" as String
                var empty = "/static/ico/armmar_stat_fail.png" as String
            }
            val starsList = ref(_uA<StarState>())
            watchEffect(fun(){
                val result: UTSArray<StarState> = _uA()
                val rating = Math.max(0, Math.min(props.value, props.maxStars))
                run {
                    var i: Number = 0
                    while(i < props.maxStars){
                        val starValue = i + 1
                        if (rating >= starValue) {
                            result.push(StarState(src = STAR_IMAGES["full"] as String))
                        } else if (rating > i && rating < starValue) {
                            result.push(StarState(src = STAR_IMAGES["half"] as String))
                        } else {
                            result.push(StarState(src = STAR_IMAGES["empty"] as String))
                        }
                        i++
                    }
                }
                starsList.value = result
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "star-rating"), _uA(
                    _cE(Fragment, null, RenderHelpers.renderList(unref(starsList), fun(star, index, __index, _cached): Any {
                        return _cE("view", _uM("key" to index, "class" to "star-item", "style" to _nS(_uM("width" to props.size, "height" to props.size, "marginRight" to if (index < unref(starsList).length - 1) {
                            props.spacing
                        } else {
                            "0"
                        }
                        ))), _uA(
                            _cE("image", _uM("src" to star.src, "style" to _nS(_uM("width" to props.size, "height" to props.size)), "mode" to "aspectFit"), null, 12, _uA(
                                "src"
                            ))
                        ), 4)
                    }
                    ), 128)
                ))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("star-rating" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "flex-start")), "star-item" to _pS(_uM("display" to "flex", "alignItems" to "center", "justifyContent" to "center")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("value" to _uM("type" to "Number", "required" to true, "default" to 0), "maxStars" to _uM("type" to "Number", "required" to false, "default" to 5), "size" to _uM("type" to "String", "required" to false, "default" to "20rpx"), "spacing" to _uM("type" to "String", "required" to false, "default" to "4rpx")))
        var propsNeedCastKeys = _uA(
            "value",
            "maxStars",
            "size",
            "spacing"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
