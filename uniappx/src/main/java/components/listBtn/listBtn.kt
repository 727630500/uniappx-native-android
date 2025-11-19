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
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenComponentsListBtnListBtn : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var item: Any? by `$props`
    open var model: String by `$props`
    open var subModule: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsListBtnListBtn) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsListBtnListBtn
            val _cache = __ins.renderCache
            fun emits(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val props = __props
            val _item = computed(fun(): teachingMaterial? {
                if (props.item == null) {
                    return null
                }
                return props.item as teachingMaterial
            }
            )
            fun gen_startStudy_fn(item: teachingMaterial?, isMore: Boolean) {
                initConfig()
                if (!isMore) {
                    emits("startStudy", item)
                    return
                } else {
                    uni_request<Result<UTSArray<teachingMaterial>>>(RequestOptions(url = getUrl("/biz/problem/api/englishLearnAgain"), method = "POST", data = object : UTSJSONObject() {
                        var textbookId = getTextBookId()
                        var textbookUnitId = item?.id
                        var module = props.model
                        var subModule = props.subModule
                    }, header = getHeader(), success = fun(res){
                        val responseData = res.data
                        if (responseData == null) {
                            return
                        }
                        if (responseData.code as Number != 200) {
                            uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                            return
                        }
                        emits("startStudy", item)
                    }
                    , fail = fun(err){
                        console.log(err)
                    }
                    , complete = fun(_){}))
                }
            }
            val startStudy = ::gen_startStudy_fn
            fun gen_startExam_fn(item: teachingMaterial?) {
                initConfig()
                emits("startExam", item)
            }
            val startExam = ::gen_startExam_fn
            return fun(): Any? {
                return if (unref(_item)?.englishProgress?.englishTotalProgressValue != 0) {
                    _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex-direction" to "row", "align-items" to "center", "justify-content" to "center"))), _uA(
                        if (unref(_item)?.englishProgress?.englishCurrentProgressValue == 0) {
                            _cE("view", _uM("key" to 0, "class" to "word_table-body-item-status", "style" to _nS(_uM("background-color" to "#FA9600")), "onClick" to fun(){
                                startStudy(unref(_item), false)
                            }), _uA(
                                _cE("text", _uM("class" to "word_table-body-item-status-text", "style" to _nS(_uM("color" to "#fff"))), "开始学习", 4)
                            ), 12, _uA(
                                "onClick"
                            ))
                        } else {
                            _cC("v-if", true)
                        },
                        if (isTrue(unref(_item)?.englishProgress?.englishCurrentProgressValue != 0 && unref(_item)?.englishProgress?.englishCurrentProgressValue != unref(_item)?.englishProgress?.englishTotalProgressValue)) {
                            _cE("view", _uM("key" to 1, "class" to "word_table-body-item-status", "onClick" to fun(){
                                startStudy(unref(_item), false)
                            }), _uA(
                                _cE("text", _uM("class" to "word_table-body-item-status-text"), "继续学习")
                            ), 8, _uA(
                                "onClick"
                            ))
                        } else {
                            _cC("v-if", true)
                        },
                        if (isTrue(unref(_item)?.englishProgress?.englishCurrentProgressValue != 0 && unref(_item)?.englishProgress?.englishCurrentProgressValue == unref(_item)?.englishProgress?.englishTotalProgressValue)) {
                            _cE("view", _uM("key" to 2, "class" to "word_table-body-item-status", "onClick" to fun(){
                                startStudy(unref(_item), true)
                            }), _uA(
                                _cE("text", _uM("class" to "word_table-body-item-status-text"), "再次学习")
                            ), 8, _uA(
                                "onClick"
                            ))
                        } else {
                            _cC("v-if", true)
                        },
                        if (isTrue(unref(_item)?.englishProgress?.isFinish == "1" && unref(_item)?.englishProgress?.englishCurrentProgressValue == unref(_item)?.englishProgress?.englishTotalProgressValue)) {
                            _cE("view", _uM("key" to 3, "class" to "word_table-body-item-status", "onClick" to fun(){
                                startExam(unref(_item))
                            }), _uA(
                                _cE("text", _uM("class" to "word_table-body-item-status-text"), "再次闯关")
                            ), 8, _uA(
                                "onClick"
                            ))
                        } else {
                            _cC("v-if", true)
                        },
                        if (isTrue(unref(_item)?.englishProgress?.isFinish == "0" && unref(_item)?.englishProgress?.englishCurrentProgressValue == unref(_item)?.englishProgress?.englishTotalProgressValue)) {
                            _cE("view", _uM("key" to 4, "class" to "word_table-body-item-status", "onClick" to fun(){
                                startExam(unref(_item))
                            }), _uA(
                                _cE("text", _uM("class" to "word_table-body-item-status-text"), "开始闯关")
                            ), 8, _uA(
                                "onClick"
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                    ), 4)
                } else {
                    _cE("view", _uM("key" to 1), _uA(
                        _cE("text", _uM("class" to "word_table-body-item-status-text"), "--")
                    ))
                }
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA())
        }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("startStudy" to null, "startExam" to null)
        var props = _nP(_uM("item" to _uM("required" to false, "default" to null), "model" to _uM("type" to "String", "required" to true, "default" to ""), "subModule" to _uM("type" to "String", "required" to true, "default" to "")))
        var propsNeedCastKeys = _uA(
            "item",
            "model",
            "subModule"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
