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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
open class GenComponentsChangeGradeChangeGrade : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsChangeGradeChangeGrade) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsChangeGradeChangeGrade
            val _cache = __ins.renderCache
            val goPath = fun(){
                uni_navigateTo(NavigateToOptions(url = "/pages/user/change"))
            }
            return fun(): Any? {
                return _cE("view", _uM("class" to "change_grade_btn", "onClick" to goPath), _uA(
                    _cE("image", _uM("class" to "change_grade_btn_icon", "src" to "/static/ico/change.png")),
                    _cE("text", _uM("class" to "change_grade_btn_text"), "更换教材")
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
                return _uM("change_grade_btn" to _pS(_uM("width" to "90rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx", "alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#D5DFF0", "borderRightColor" to "#D5DFF0", "borderBottomColor" to "#D5DFF0", "borderLeftColor" to "#D5DFF0", "flexDirection" to "row", "marginLeft" to "14.65rpx")), "change_grade_btn_icon" to _uM(".change_grade_btn " to _uM("width" to "28rpx", "height" to "28rpx", "marginRight" to "6rpx")), "change_grade_btn_text" to _uM(".change_grade_btn " to _uM("flex" to 1, "fontSize" to "12rpx", "color" to "#3D3D3D")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
