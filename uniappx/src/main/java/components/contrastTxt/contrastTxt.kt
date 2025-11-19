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
open class GenComponentsContrastTxtContrastTxt : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var txt: String by `$props`
    open var contrastTxt: String by `$props`
    open var isSublet: Boolean by `$props`
    open var isText: Boolean by `$props`
    open var txtArr: UTSArray<tempTiMu> by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenComponentsContrastTxtContrastTxt) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenComponentsContrastTxtContrastTxt
            val _cache = __ins.renderCache
            val resultList = ref<UTSArray<resultItem1>?>(null)
            val props = __props
            val txt = ref("")
            val contrastTxt = ref("")
            fun startDiff(splitText: String = "") {
                val txtArray = props.txt.split(splitText)
                val contrastArray = props.contrastTxt.split(splitText)
                val results = _uA<resultItem1>()
                if (props.txt.replace(UTSRegExp("\\s+", "g"), "") == "") {
                    run {
                        var i: Number = 0
                        while(i < contrastArray.length){
                            results.push(resultItem1(str = "_", isFail = true))
                            i++
                        }
                    }
                    resultList.value = results
                    return
                }
                run {
                    var i: Number = 0
                    while(i < txtArray.length){
                        val char = txtArray[i]
                        val contrastChar = if (i >= contrastArray.length) {
                            ""
                        } else {
                            contrastArray[i]
                        }
                        results.push(resultItem1(str = if (char == "") {
                            contrastArray[i]
                        } else {
                            char
                        }
                        , isFail = char != contrastChar))
                        i++
                    }
                }
                resultList.value = results
            }
            fun gen_startDiffArr_fn() {
                val results = _uA<resultItem1>()
                var _arr = props.txtArr
                _arr?.forEach(fun(item){
                    var _txt = item.title.split("")
                    _txt.forEach(fun(txt, index){
                        if (index >= item.content.length) {
                            results.push(resultItem1(str = "_", isFail = true))
                        } else {
                            results.push(resultItem1(str = item.content.charAt(index), isFail = txt != item.content.charAt(index)))
                        }
                    }
                    )
                    results.push(resultItem1(str = " ", isFail = false))
                }
                )
                resultList.value = results
            }
            val startDiffArr = ::gen_startDiffArr_fn
            watchEffect(fun(){
                if (props.txtArr?.length == 0) {
                    if (props.isSublet == false) {
                        startDiff("")
                    } else {
                        startDiff(" ")
                    }
                } else {
                    startDiffArr()
                }
            }
            )
            return fun(): Any? {
                return if (isTrue(!props.isText)) {
                    _cE("view", _uM("key" to 0, "style" to _nS(_uM("flex-direction" to "row", "flex-wrap" to "wrap"))), _uA(
                        _cE(Fragment, null, RenderHelpers.renderList(unref(resultList), fun(item, __key, __index, _cached): Any {
                            return _cE("text", _uM("class" to _nC(_uA(
                                "txt",
                                _uM("warn" to item.isFail)
                            ))), _tD(item.str) + _tD(if (props.isSublet) {
                                " "
                            } else {
                                ""
                            }), 3)
                        }), 256)
                    ), 4)
                } else {
                    _cE("text", _uM("key" to 1, "class" to "_text"), _uA(
                        _cE(Fragment, null, RenderHelpers.renderList(unref(resultList), fun(item, __key, __index, _cached): Any {
                            return _cE("text", _uM("class" to _nC(_uA(
                                "_text",
                                _uM("warn" to item.isFail)
                            ))), _tD(item.str) + _tD(if (props.isSublet) {
                                " "
                            } else {
                                ""
                            }
                            ), 3)
                        }
                        ), 256)
                    ))
                }
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("txt" to _uM("" to _uM("fontSize" to "23rpx", "color" to "#008000"), ".warn" to _uM("color" to "#FF0000")), "_text" to _uM("" to _uM("fontSize" to "14rpx", "color" to "#008000", "lineHeight" to "23.44rpx"), ".warn" to _uM("color" to "#FF0000")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("update:current" to null)
        var props = _nP(_uM("txt" to _uM("type" to "String", "required" to true, "default" to ""), "contrastTxt" to _uM("type" to "String", "required" to true, "default" to ""), "isSublet" to _uM("type" to "Boolean", "required" to true, "default" to false), "isText" to _uM("type" to "Boolean", "required" to true, "default" to false), "txtArr" to _uM("type" to "Array", "required" to false, "default" to _uA<tempTiMu>())))
        var propsNeedCastKeys = _uA(
            "txt",
            "contrastTxt",
            "isSublet",
            "isText",
            "txtArr"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
