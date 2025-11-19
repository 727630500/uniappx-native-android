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
open class GenComponentsMemoryLineMemoryLine : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var num: Number by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsMemoryLineMemoryLine) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsMemoryLineMemoryLine
            val _cache = __ins.renderCache
            val props = __props
            val wStyle = ref(object : UTSJSONObject() {
                var width = "0"
            })
            val lStyle = ref(object : UTSJSONObject() {
                var left = "0"
            })
            watchEffect(fun(){
                var _num: Number = props.num
                if (_num == 100 || _num == 90) {
                    _num = 88
                }
                lStyle.value.set("left", _num.toString(10) + "%")
                wStyle.value.set("width", props.num.toString(10) + "%")
            }
            )
            return fun(): Any? {
                val _component_BackgroundImage = resolveEasyComponent("BackgroundImage", GenComponentsBackgroundImageBackgroundImageClass)
                return _cE("view", _uM("class" to "_box"), _uA(
                    _cE("view", _uM("class" to "pos-a", "style" to _nS(unref(lStyle))), _uA(
                        _cV(_component_BackgroundImage, _uM("src" to if (_ctx.num <= 90) {
                            "/static/ico/bubble_ico.png"
                        } else {
                            "/static/ico/bubble_ico_f.png"
                        }
                        , "bgStyle" to "width:100%;height:100%;position: absolute;left:0;top:0", "className" to "tip_box"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                            return _uA(
                                _cE("text", _uM("class" to "num_text"), _tD(_ctx.num), 1)
                            )
                        }
                        ), "_" to 1), 8, _uA(
                            "src"
                        ))
                    ), 4),
                    _cE("view", _uM("class" to "line_box"), _uA(
                        _cE("view", _uM("class" to "line_blue_f", "style" to _nS(unref(wStyle))), _uA(
                            _cE("view", _uM("class" to "line_blue"), _uA(
                                _cE(Fragment, null, RenderHelpers.renderList(11, fun(i, __key, __index, _cached): Any {
                                    return _cE("view", _uM("class" to _nC(_uA(
                                        "fg",
                                        _uM("w-0" to (i == 1 || i == 11))
                                    ))), null, 2)
                                }
                                ), 64)
                            ))
                        ), 4),
                        _cE("view", _uM("class" to "line_hui"), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(11, fun(i, __key, __index, _cached): Any {
                                return _cE("view", _uM("class" to _nC(_uA(
                                    "fg",
                                    _uM("w-0" to (i == 1 || i == 11))
                                ))), null, 2)
                            }
                            ), 64)
                        ))
                    ))
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
                return _uM("_box" to _pS(_uM("position" to "relative")), "pos-a" to _uM("._box " to _uM("position" to "absolute", "top" to 0)), "tip_box" to _uM("._box " to _uM("width" to "22.85rpx", "height" to "16.99rpx")), "num_text" to _uM("._box " to _uM("color" to "#ffffff", "fontSize" to "11rpx", "lineHeight" to "16.99rpx", "width" to "22.85rpx", "textAlign" to "center", "position" to "absolute", "top" to "-1rpx")), "line_box" to _uM("._box " to _uM("marginTop" to "20rpx", "width" to "193.36rpx", "height" to "6.45rpx", "position" to "relative")), "line_blue_f" to _uM("._box .line_box " to _uM("position" to "absolute", "zIndex" to 9)), "line_blue" to _uM("._box .line_box " to _uM("backgroundImage" to "linear-gradient(to right, #C7D3FF, #216CFF)", "backgroundColor" to "rgba(0,0,0,0)", "width" to "193.36rpx", "height" to "6.45rpx", "borderTopLeftRadius" to "6.45rpx", "borderTopRightRadius" to "6.45rpx", "borderBottomRightRadius" to "6.45rpx", "borderBottomLeftRadius" to "6.45rpx", "flexDirection" to "row", "justifyContent" to "space-between")), "line_hui" to _uM("._box .line_box " to _uM("backgroundImage" to "none", "backgroundColor" to "#D8D8D8", "width" to "193.36rpx", "height" to "6.45rpx", "borderTopLeftRadius" to "6.45rpx", "borderTopRightRadius" to "6.45rpx", "borderBottomRightRadius" to "6.45rpx", "borderBottomLeftRadius" to "6.45rpx", "flexDirection" to "row", "justifyContent" to "space-between")), "fg" to _uM("._box " to _uM("height" to "100%", "width" to "2rpx", "backgroundColor" to "#ffffff")), "w-0" to _uM("._box " to _uM("width" to 0)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("num" to _uM("type" to "Number", "default" to fun(): Number {
            return 0
        }
        )))
        var propsNeedCastKeys = _uA(
            "num"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
