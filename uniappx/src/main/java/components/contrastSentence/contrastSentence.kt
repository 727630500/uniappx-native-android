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
open class GenComponentsContrastSentenceContrastSentence : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var arr: UTSArray<Any?> by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsContrastSentenceContrastSentence) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsContrastSentenceContrastSentence
            val _cache = __ins.renderCache
            val props = __props
            val _arrList = ref(_uA<tempTiMu>())
            watchEffect(fun(){
                _arrList.value = props.arr as UTSArray<tempTiMu>
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "flex-wrap" to "wrap", "padding-right" to "-6rpx"))), _uA(
                    _cE(Fragment, null, RenderHelpers.renderList(unref(_arrList), fun(item, __key, __index, _cached): Any {
                        return _cE("text", _uM("class" to _nC(_uA(
                            "txt",
                            _uM("warn" to (item.content != item.title))
                        ))), _tD(if (item.content == "") {
                            "_"
                        } else {
                            item.content
                        }
                        ), 3)
                    }
                    ), 256)
                ), 4)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("txt" to _uM("" to _uM("fontSize" to "23rpx", "color" to "#008000", "marginRight" to "6rpx"), ".warn" to _uM("color" to "#FF0000")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("arr" to _uM("type" to "Array", "default" to fun(): UTSArray<tempTiMu> {
            return _uA()
        }
        )))
        var propsNeedCastKeys = _uA(
            "arr"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
