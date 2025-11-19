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
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenComponentsLearningInfoLearningInfo : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var learningInfo: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsLearningInfoLearningInfo) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsLearningInfoLearningInfo
            val _cache = __ins.renderCache
            val info = ref(nowCurriculum())
            val title = ref("")
            fun gen_getLearningInfo_fn() {
                uni_request<Result<nowCurriculum>>(RequestOptions(url = getUrl("/biz/textbook/api/getMyCurrentLearn"), method = "GET", header = getHeader(), success = fun(res){
                    val responseData = res.data
                    if (responseData == null) {
                        return
                    }
                    if (responseData.code as Number != 200) {
                        uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                        return
                    }
                    if (responseData.data != null) {
                        info.value = responseData.data as nowCurriculum
                        title.value = ""
                        setTimeout(fun(){
                            title.value = "\u6B63\u5728\u5B66\u4E60\uFF1A" + info.value.periodBaseDataName + info.value.gradeBaseDataName + "-" + info.value.versionBaseDataName + "-" + info.value.textbookName
                        }, 0)
                        if (info.value.id != null) {
                            var _id = info.value.id as Number
                            uni_setStorageSync("textbookId", _id)
                        } else {
                            uni_setStorageSync("textbookId", 0)
                        }
                    } else {
                        uni_setStorageSync("textbookId", 0)
                    }
                }
                , fail = fun(err){
                    console.log(err)
                }
                , complete = fun(_){}))
            }
            val getLearningInfo = ::gen_getLearningInfo_fn
            onPageShow(fun(){
                getLearningInfo()
            }
            )
            return fun(): Any? {
                val _component_wj_scroll = resolveEasyComponent("wj-scroll", GenUniModulesWjScrollComponentsWjScrollWjScrollClass)
                return _cE("view", _uM("class" to "learning-info"), _uA(
                    _cE("image", _uM("class" to "learning-info-icon", "src" to "/static/ico/leran-icon.png", "mode" to "aspectFit")),
                    if (unref(title) != "") {
                        _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex" to "1"))), _uA(
                            _cV(_component_wj_scroll, _uM("text" to unref(title), "fontSize" to 28, "textColor" to "#333333", "scrollSpeed" to 80, "scrollDirection" to "horizontal", "loop" to true, "autoStart" to true, "style" to _nS(_uM("width" to "100%", "height" to "50px"))), null, 8, _uA(
                                "text",
                                "style"
                            ))
                        ), 4)
                    } else {
                        _cC("v-if", true)
                    }
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
                return _uM("learning-info" to _pS(_uM("marginLeft" to "27.54rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "width" to "295rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "boxShadow" to "0rpx 2rpx 4rpx 0rpx rgba(130, 141, 197, 0.3)", "borderTopLeftRadius" to "15rpx", "borderTopRightRadius" to "15rpx", "borderBottomRightRadius" to "15rpx", "borderBottomLeftRadius" to "15rpx", "paddingTop" to 0, "paddingRight" to "8rpx", "paddingBottom" to 0, "paddingLeft" to "12rpx")), "learning-info-icon" to _uM(".learning-info " to _uM("width" to "10rpx", "height" to "9rpx", "marginRight" to "6rpx")), "learning-info-text" to _uM(".learning-info " to _uM("textOverflow" to "ellipsis", "whiteSpace" to "nowrap", "flex" to 1, "fontSize" to "12rpx", "color" to "#535D8C")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("learningInfo" to _uM("type" to "String", "default" to "")))
        var propsNeedCastKeys = _uA(
            "learningInfo"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
