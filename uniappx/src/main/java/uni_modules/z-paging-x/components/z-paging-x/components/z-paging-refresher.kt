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
open class GenUniModulesZPagingXComponentsZPagingXComponentsZPagingRefresher : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        val _component_z_paging_loading = resolveComponent("z-paging-loading")
        return _cE("view", _uM("class" to "zpx-r-container", "style" to _nS(_uM("height" to if (_ctx.showUpdateTime) {
            "60px"
        } else {
            "40px"
        }
        ))), _uA(
            if (isTrue(_ctx.isLoading)) {
                _cV(_component_z_paging_loading, _uM("key" to 0))
            } else {
                _cE("image", _uM("key" to 1, "class" to _nC(_uA(
                    "zpx-r-image",
                    _uM("zpx-r-arrow-down" to _ctx.isDefault)
                )), "src" to _ctx.base64ArrowImg), null, 10, _uA(
                    "src"
                ))
            }
            ,
            _cE("view", _uM("class" to "zpx-r-text-container", "style" to _nS(_uM("marginLeft" to if (_ctx.showRefresherTimeText) {
                "25rpx"
            } else {
                "10rpx"
            }
            ))), _uA(
                _cE("text", _uM("class" to "zpx-r-text"), _tD(_ctx.statusText), 1),
                if (isTrue(_ctx.showRefresherTimeText)) {
                    _cE("text", _uM("key" to 0, "class" to "zpx-r-text zpx-r-time-text"), _tD(_ctx.refresherTimeText), 1)
                } else {
                    _cC("v-if", true)
                }
            ), 4)
        ), 4)
    }
    open var status: String by `$props`
    open var defaultText: String by `$props`
    open var pullingText: String by `$props`
    open var refreshingText: String by `$props`
    open var showUpdateTime: Boolean by `$props`
    open var updateTimeKey: String by `$props`
    open var timeTextTimestamp: Number by `$props`
    open var times: Number by `$data`
    open var base64ArrowImg: Any? by `$data`
    open var isDefault: Boolean by `$data`
    open var isReleaseToRefresh: Boolean by `$data`
    open var isLoading: Boolean by `$data`
    open var statusText: String by `$data`
    open var refresherTimeText: String by `$data`
    open var showRefresherTimeText: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("times" to 0, "base64ArrowImg" to base64Arrow, "isDefault" to computed<Boolean>(fun(): Boolean {
            return this.status === `default`.Refresher.Default
        }
        ), "isReleaseToRefresh" to computed<Boolean>(fun(): Boolean {
            return this.status === `default`.Refresher.ReleaseToRefresh
        }
        ), "isLoading" to computed<Boolean>(fun(): Boolean {
            return this.status === `default`.Refresher.Loading
        }
        ), "statusText" to computed<String>(fun(): String {
            if (this.isDefault) {
                return this.defaultText
            } else if (this.isReleaseToRefresh) {
                return this.pullingText
            } else if (this.isLoading) {
                return this.refreshingText
            } else {
                return ""
            }
        }
        ), "refresherTimeText" to computed<String>(fun(): String {
            this.timeTextTimestamp
            return getRefesrherFormatTimeByKey(this.updateTimeKey)
        }
        ), "showRefresherTimeText" to computed<Boolean>(fun(): Boolean {
            return this.showUpdateTime && this.refresherTimeText.length > 0
        }
        ))
    }
    companion object {
        var name = "z-paging-refresher"
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("zpx-r-container" to _pS(_uM("height" to 40, "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "zpx-r-image" to _pS(_uM("width" to "34rpx", "height" to "34rpx", "transitionDuration" to ".2s", "transitionProperty" to "transform", "transform" to "rotate(0deg)")), "zpx-r-arrow-top" to _pS(_uM("transform" to "rotate(0deg)")), "zpx-r-arrow-down" to _pS(_uM("transform" to "rotate(180deg)")), "zpx-r-text-container" to _pS(_uM("flexDirection" to "column", "alignItems" to "center")), "zpx-r-text" to _pS(_uM("color" to "#808080", "fontSize" to "28rpx")), "zpx-r-time-text" to _pS(_uM("fontSize" to "26rpx", "marginTop" to "10rpx")), "@TRANSITION" to _uM("zpx-r-image" to _uM("duration" to ".2s", "property" to "transform")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("status" to _uM("type" to "String", "default" to `default`.Refresher.Default), "defaultText" to _uM("type" to "String", "default" to "继续下拉刷新"), "pullingText" to _uM("type" to "String", "default" to "松开立即刷新"), "refreshingText" to _uM("type" to "String", "default" to "正在刷新..."), "showUpdateTime" to _uM("type" to "Boolean", "default" to false), "updateTimeKey" to _uM("type" to "String", "default" to "default"), "timeTextTimestamp" to _uM("type" to "Number", "default" to 0)))
        var propsNeedCastKeys = _uA(
            "status",
            "defaultText",
            "pullingText",
            "refreshingText",
            "showUpdateTime",
            "updateTimeKey",
            "timeTextTimestamp"
        )
        var components: Map<String, CreateVueComponent> = _uM("zPagingLoading" to GenUniModulesZPagingXComponentsZPagingXComponentsZPagingLoadingClass)
    }
}
