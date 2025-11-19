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
open class GenUniModulesZPagingXComponentsZPagingXComponentsZPagingLoadMore : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        val _component_z_paging_loading = resolveComponent("z-paging-loading")
        return _cE("view", _uM("class" to "zpx-l-container", "onClick" to _ctx.onClickMore), _uA(
            if (isTrue(_ctx.isNoMore)) {
                _cE("view", _uM("key" to 0, "class" to "zpx-l-line"))
            } else {
                _cC("v-if", true)
            }
            ,
            if (isTrue(_ctx.isLoading)) {
                _cV(_component_z_paging_loading, _uM("key" to 1))
            } else {
                _cC("v-if", true)
            }
            ,
            _cE("text", _uM("class" to "zpx-l-loading-text"), _tD(_ctx.statusText), 1),
            if (isTrue(_ctx.isNoMore)) {
                _cE("view", _uM("key" to 2, "class" to "zpx-l-line"))
            } else {
                _cC("v-if", true)
            }
        ), 8, _uA(
            "onClick"
        ))
    }
    open var status: String by `$props`
    open var defaultText: String by `$props`
    open var loadingText: String by `$props`
    open var noMoreText: String by `$props`
    open var failText: String by `$props`
    open var isDefault: Boolean by `$data`
    open var isLoading: Boolean by `$data`
    open var isNoMore: Boolean by `$data`
    open var isFail: Boolean by `$data`
    open var statusText: String by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("isDefault" to computed<Boolean>(fun(): Boolean {
            return this.status === `default`.More.Default
        }
        ), "isLoading" to computed<Boolean>(fun(): Boolean {
            return this.status === `default`.More.Loading
        }
        ), "isNoMore" to computed<Boolean>(fun(): Boolean {
            return this.status === `default`.More.NoMore
        }
        ), "isFail" to computed<Boolean>(fun(): Boolean {
            return this.status === `default`.More.Fail
        }
        ), "statusText" to computed<String>(fun(): String {
            if (this.isDefault) {
                return this.defaultText
            } else if (this.isLoading) {
                return this.loadingText
            } else if (this.isNoMore) {
                return this.noMoreText
            } else if (this.isFail) {
                return this.failText
            } else {
                return ""
            }
        }
        ))
    }
    open var onClickMore = ::gen_onClickMore_fn
    open fun gen_onClickMore_fn() {
        this.`$emit`("clickMore")
    }
    companion object {
        var name = "z-paging-load-more"
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("zpx-l-container" to _pS(_uM("height" to "80rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "zpx-l-loading-text" to _pS(_uM("color" to "#a4a4a4", "marginLeft" to "10rpx", "fontSize" to "28rpx")), "zpx-l-line" to _pS(_uM("backgroundColor" to "#eeeeee", "width" to "100rpx", "height" to 1, "marginTop" to "0rpx", "marginRight" to "10rpx", "marginBottom" to "0rpx", "marginLeft" to "10rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("status" to _uM("type" to "String", "default" to `default`.More.Default), "defaultText" to _uM("type" to "String", "default" to "点击加载更多"), "loadingText" to _uM("type" to "String", "default" to "正在加载..."), "noMoreText" to _uM("type" to "String", "default" to "没有更多了"), "failText" to _uM("type" to "String", "default" to "加载失败，点击重新加载")))
        var propsNeedCastKeys = _uA(
            "status",
            "defaultText",
            "loadingText",
            "noMoreText",
            "failText"
        )
        var components: Map<String, CreateVueComponent> = _uM("zPagingLoading" to GenUniModulesZPagingXComponentsZPagingXComponentsZPagingLoadingClass)
    }
}
