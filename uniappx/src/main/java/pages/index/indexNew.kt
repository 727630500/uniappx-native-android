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
import io.dcloud.uniapp.extapi.downloadFile as uni_downloadFile
import io.dcloud.uniapp.extapi.getAppBaseInfo as uni_getAppBaseInfo
import io.dcloud.uniapp.extapi.installApk as uni_installApk
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.request as uni_request
import uts.sdk.modules.wjHookScreen.setScreen
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesIndexIndexNew : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {}, __ins)
        onReady(fun() {
            setScreen()
        }
        , __ins)
        onPageShow(fun() {
            uni_removeStorageSync("studyTaskEnd")
            this.getUserInfo2()
            this.getTaskList()
            initConfig()
            this.`$nextTick`(fun(){
                if (ucsShare.getState("isShowPupup") as Boolean) {
                    this.getNumFn()
                    ucsShare.setState("isShowPupup", false)
                }
            }
            )
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        val _component_LearningInfo = resolveEasyComponent("LearningInfo", GenComponentsLearningInfoLearningInfoClass)
        val _component_ChangeGrade = resolveEasyComponent("ChangeGrade", GenComponentsChangeGradeChangeGradeClass)
        val _component_navbar = resolveEasyComponent("navbar", GenComponentsNavbarNavbarClass)
        val _component_progress = resolveComponent("progress")
        val _component_u_popup = resolveEasyComponent("u-popup", GenComponentsUPopupUPopupClass)
        val _component_PageWrap = resolveEasyComponent("PageWrap", GenComponentsPageWrapPageWrapClass)
        return _cV(_component_PageWrap, _uM("className" to "index_bg"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
            return _uA(
                _cV(_component_navbar, _uM("title" to "英语"), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                    return _uA(
                        _cV(_component_LearningInfo),
                        _cV(_component_ChangeGrade)
                    )
                }
                ), "_" to 1)),
                _cE("view", _uM("class" to "index-content"), _uA(
                    _cE("view", _uM("class" to "study-modules-style-2"), _uA(
                        _cE("view", _uM("class" to "module-item module-item-0", "onClick" to fun(){
                            _ctx.handleModuleClick(_ctx.studyModules[0])
                        }
                        ), _uA(
                            _cE("view", _uM("class" to "module-text"), _uA(
                                _cE("text", _uM("class" to "module-title"), _tD(_ctx.studyModules[0].title), 1),
                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                    _cE("view", _uM("class" to "module-subtitle"), _uA(
                                        _cE("text", _uM("class" to "module-subtitle-text"), _tD(_ctx.studyModules[0].subTitle), 1)
                                    ))
                                ), 4),
                                _cE("image", _uM("class" to "module-icon", "mode" to "heightFix", "src" to _ctx.studyModules[0].icon), null, 8, _uA(
                                    "src"
                                )),
                                _cE("view", _uM("class" to "start-btn-box"), _uA(
                                    _cE("text", _uM("class" to "start-btn"), "开始练习")
                                ))
                            ))
                        ), 8, _uA(
                            "onClick"
                        )),
                        _cE("view", _uM("class" to "small-module-item-wrap"), _uA(
                            _cE("view", _uM("class" to "module-item module-item-1", "onClick" to fun(){
                                _ctx.handleModuleClick(_ctx.studyModules[1])
                            }
                            ), _uA(
                                _cE("view", _uM("class" to "module-text"), _uA(
                                    _cE("text", _uM("class" to "module-title"), _tD(_ctx.studyModules[1].title), 1),
                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                        _cE("view", _uM("class" to "module-subtitle"), _uA(
                                            _cE("text", _uM("class" to "module-subtitle-text"), _tD(_ctx.studyModules[1].subTitle), 1)
                                        ))
                                    ), 4),
                                    _cE("view", _uM("class" to "start-btn-box"), _uA(
                                        _cE("text", _uM("class" to "start-btn"), "开始练习")
                                    ))
                                )),
                                _cE("image", _uM("class" to "module-icon", "mode" to "heightFix", "src" to _ctx.studyModules[1].icon), null, 8, _uA(
                                    "src"
                                ))
                            ), 8, _uA(
                                "onClick"
                            )),
                            _cE("view", _uM("class" to "module-item module-item-2", "onClick" to fun(){
                                _ctx.handleModuleClick(_ctx.studyModules[2])
                            }
                            ), _uA(
                                _cE("view", _uM("class" to "module-text"), _uA(
                                    _cE("text", _uM("class" to "module-title"), _tD(_ctx.studyModules[2].title), 1),
                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                        _cE("view", _uM("class" to "module-subtitle"), _uA(
                                            _cE("text", _uM("class" to "module-subtitle-text"), _tD(_ctx.studyModules[2].subTitle), 1)
                                        ))
                                    ), 4),
                                    _cE("view", _uM("class" to "start-btn-box"), _uA(
                                        _cE("text", _uM("class" to "start-btn"), "开始练习")
                                    ))
                                )),
                                _cE("image", _uM("class" to "module-icon", "mode" to "heightFix", "src" to _ctx.studyModules[2].icon), null, 8, _uA(
                                    "src"
                                ))
                            ), 8, _uA(
                                "onClick"
                            )),
                            _cE("view", _uM("class" to "module-item module-item-3", "onClick" to fun(){
                                _ctx.handleModuleClick(_ctx.studyModules[3])
                            }
                            ), _uA(
                                _cE("view", _uM("class" to "module-text"), _uA(
                                    _cE("text", _uM("class" to "module-title"), _tD(_ctx.studyModules[3].title), 1),
                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                        _cE("view", _uM("class" to "module-subtitle"), _uA(
                                            _cE("text", _uM("class" to "module-subtitle-text"), _tD(_ctx.studyModules[3].subTitle), 1)
                                        ))
                                    ), 4),
                                    _cE("view", _uM("class" to "start-btn-box"), _uA(
                                        _cE("text", _uM("class" to "start-btn"), "开始练习")
                                    ))
                                )),
                                _cE("image", _uM("class" to "module-icon", "mode" to "heightFix", "src" to _ctx.studyModules[3].icon), null, 8, _uA(
                                    "src"
                                ))
                            ), 8, _uA(
                                "onClick"
                            )),
                            _cE("view", _uM("class" to "module-item module-item-4", "onClick" to fun(){
                                _ctx.handleModuleClick(_ctx.studyModules[4])
                            }
                            ), _uA(
                                _cE("view", _uM("class" to "module-text"), _uA(
                                    _cE("text", _uM("class" to "module-title"), _tD(_ctx.studyModules[4].title), 1),
                                    _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                        _cE("view", _uM("class" to "module-subtitle"), _uA(
                                            _cE("text", _uM("class" to "module-subtitle-text"), _tD(_ctx.studyModules[4].subTitle), 1)
                                        ))
                                    ), 4),
                                    _cE("view", _uM("class" to "start-btn-box"), _uA(
                                        _cE("text", _uM("class" to "start-btn"), "开始练习")
                                    ))
                                )),
                                _cE("image", _uM("class" to "module-icon", "mode" to "heightFix", "src" to _ctx.studyModules[4].icon), null, 8, _uA(
                                    "src"
                                ))
                            ), 8, _uA(
                                "onClick"
                            ))
                        )),
                        _cE("view", _uM("class" to "module-item module-item-5", "onClick" to fun(){
                            _ctx.handleModuleClick(_ctx.studyModules[5])
                        }
                        ), _uA(
                            _cE("view", _uM("class" to "module-text"), _uA(
                                _cE("text", _uM("class" to "module-title"), _tD(_ctx.studyModules[5].title), 1),
                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                    _cE("view", _uM("class" to "module-subtitle"), _uA(
                                        _cE("text", _uM("class" to "module-subtitle-text"), _tD(_ctx.studyModules[5].subTitle), 1)
                                    ))
                                ), 4),
                                _cE("view", _uM("class" to "start-btn-box"), _uA(
                                    _cE("text", _uM("class" to "start-btn"), "开始练习")
                                ))
                            )),
                            _cE("image", _uM("class" to "module-icon", "mode" to "heightFix", "src" to _ctx.studyModules[5].icon), null, 8, _uA(
                                "src"
                            ))
                        ), 8, _uA(
                            "onClick"
                        )),
                        _cE("view", _uM("class" to "module-item module-item-6", "onClick" to fun(){
                            _ctx.handleModuleClick(_ctx.studyModules[6])
                        }
                        ), _uA(
                            _cE("view", _uM("class" to "module-text"), _uA(
                                _cE("text", _uM("class" to "module-title"), _tD(_ctx.studyModules[6].title), 1),
                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row"))), _uA(
                                    _cE("view", _uM("class" to "module-subtitle"), _uA(
                                        _cE("text", _uM("class" to "module-subtitle-text"), _tD(_ctx.studyModules[6].subTitle), 1)
                                    ))
                                ), 4),
                                _cE("view", _uM("class" to "start-btn-box"), _uA(
                                    _cE("text", _uM("class" to "start-btn"), "开始练习")
                                ))
                            )),
                            _cE("image", _uM("class" to "module-icon", "mode" to "heightFix", "src" to _ctx.studyModules[6].icon), null, 8, _uA(
                                "src"
                            ))
                        ), 8, _uA(
                            "onClick"
                        ))
                    )),
                    _cE("view", _uM("class" to "divider"), _uA(
                        _cE("view", _uM("class" to "task-box"), _uA(
                            _cE("text", _uM("class" to "task-title"), "我的任务"),
                            _cE("view", _uM("class" to "task-all", "onClick" to fun(){
                                _ctx.goTask()
                            }
                            ), _uA(
                                _cE("text", _uM("class" to "text"), " 全部任务 "),
                                _cE("image", _uM("src" to "/static/ico/rig_smail.png", "style" to _nS(_uM("width" to "5.86rpx", "height" to "5rpx"))), null, 4)
                            ), 8, _uA(
                                "onClick"
                            ))
                        )),
                        _cE("scroll-view", _uM("class" to "task-swiper", "direction" to "vertical"), _uA(
                            _cE("view", _uM("class" to "task-swiper-item"), _uA(
                                _cE(Fragment, null, RenderHelpers.renderList(_ctx.taskList, fun(item, index, __index, _cached): Any {
                                    return _cE(Fragment, null, _uA(
                                        if (item.userStudyTaskProgress?.isFinish != "1") {
                                            _cE("view", _uM("key" to 0, "class" to "task-item-box"), _uA(
                                                _cE("view", _uM("class" to "task-item-shadow")),
                                                _cE("view", _uM("class" to "task-item"), _uA(
                                                    _cE("text", _uM("class" to "task-item-title"), _tD(item.taskName ?: item.testPaperName), 1),
                                                    _cE("view", _uM("class" to "task-item-progress"), _uA(
                                                        _cE("view", _uM("class" to "task-item-progress-text"), _uA(
                                                            _cE("text", _uM("class" to "task-item-progress-text-text")),
                                                            _cV(_component_progress, _uM("percent" to (((item.userStudyTaskProgress?.currentProgressValue ?: 0) / item.taskProgressValue) * 100), "stroke-width" to 3, "backgroundColor" to "#E2E6F6", "activeColor" to "#98A6EE"), null, 8, _uA(
                                                                "percent"
                                                            ))
                                                        )),
                                                        _cE("view", _uM("class" to "task-item-progress-btn", "onClick" to fun(){
                                                            _ctx.startStudy(item)
                                                        }), _uA(
                                                            if (item.userStudyTaskProgress != null) {
                                                                _cE("image", _uM("key" to 0, "src" to "/static/ico/btn_bg1.png", "mode" to "", "class" to "_bgimg"))
                                                            } else {
                                                                _cE("image", _uM("key" to 1, "src" to "/static/ico/btn_bg2.png", "mode" to "", "class" to "_bgimg"))
                                                            },
                                                            if (isTrue(item.userStudyTaskProgress)) {
                                                                _cE(Fragment, _uM("key" to 2), _uA(
                                                                    if (item.userStudyTaskProgress?.isFinish == "0") {
                                                                        _cE("text", _uM("key" to 0, "class" to "task-item-progress-btn-text"), "学习中")
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    },
                                                                    if (item.userStudyTaskProgress?.isFinish == "1") {
                                                                        _cE("text", _uM("key" to 1, "class" to "task-item-progress-btn-text"), "已完成")
                                                                    } else {
                                                                        _cC("v-if", true)
                                                                    }
                                                                ), 64)
                                                            } else {
                                                                _cE("text", _uM("key" to 3, "class" to "task-item-progress-btn-text"), "去完成")
                                                            }
                                                        ), 8, _uA(
                                                            "onClick"
                                                        ))
                                                    ))
                                                ))
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ), 64)
                                }
                                ), 256)
                            ))
                        )),
                        _cE("image", _uM("src" to "/static/home-banner.png", "class" to "home-banner", "mode" to "aspectFit", "onClick" to fun(){
                            _ctx.goPath("/pages/user/report")
                        }
                        ), null, 8, _uA(
                            "onClick"
                        ))
                    ))
                )),
                if (isTrue(_ctx.showUp)) {
                    _cV(_component_u_popup, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "uni-center", "style" to _nS(_uM("justify-content" to "center", "width" to "100%", "height" to "100%"))), _uA(
                                _cE("view", _uM("class" to "up_box"), _uA(
                                    _cE("image", _uM("src" to "/static/app_up_bg.png", "class" to "up_box_bg", "mode" to "")),
                                    _cE("view", _uM("class" to "up_box_content"), _uA(
                                        _cE("image", _uM("src" to "/static/up_title.png", "class" to "title", "mode" to "")),
                                        _cE("text", _uM("class" to "verson"), _tD(_ctx.appVerson), 1),
                                        _cE("view", _uM("style" to _nS(_uM("flex" to "1", "margin-top" to "10rpx"))), _uA(
                                            _cE("text", _uM("style" to _nS(_uM("font-size" to "11rpx", "line-height" to "15rpx", "color" to "#3D3D3D"))), _tD(_ctx.verDescribe), 5)
                                        ), 4),
                                        _cE("view", _uM("style" to _nS(_uM("width" to "90%", "align-items" to "center"))), _uA(
                                            if (isTrue(!_ctx.updateBtn)) {
                                                _cE("view", _uM("key" to 0, "style" to _nS(_uM("width" to "100%"))), _uA(
                                                    _cV(_component_progress, _uM("class" to "progress", "border-radius" to 35, "percent" to _ctx.percent, "activeColor" to "#3DA7FF", "show-info" to _ctx.showInfo, "stroke-width" to 10), null, 8, _uA(
                                                        "percent",
                                                        "show-info"
                                                    )),
                                                    _cE("view", null, _uA(
                                                        _cE("text", _uM("style" to _nS(_uM("font-size" to "14rpx", "margin-top" to "10rpx"))), "正在下载，请稍后 (" + _tD(_ctx.downloadedSize) + "/" + _tD(_ctx.packageFileSize) + "M)", 5)
                                                    ))
                                                ), 4)
                                            } else {
                                                _cC("v-if", true)
                                            },
                                            if (isTrue(_ctx.updateBtn)) {
                                                _cE("text", _uM("key" to 1, "class" to "up_btn", "onClick" to _ctx.download), "立即更新", 8, _uA(
                                                    "onClick"
                                                ))
                                            } else {
                                                _cC("v-if", true)
                                            }
                                        ), 4)
                                    ))
                                ))
                            ), 4)
                        )
                    }), "_" to 1))
                } else {
                    _cC("v-if", true)
                }
                ,
                if (isTrue(_ctx.jzNum > 0 || _ctx.dcNum > 0)) {
                    _cE("view", _uM("key" to 1, "class" to "popup_top"), _uA(
                        if (isTrue(_ctx.dcNum > 0 && _ctx.dcShow)) {
                            _cE("view", _uM("key" to 0, "class" to "p_item", "style" to _nS(_uM("justify-content" to "space-between", "position" to "relative"))), _uA(
                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                    _cE("image", _uM("src" to "/static/home1.png", "mode" to "", "style" to _nS(_uM("width" to "44.53rpx", "height" to "41.02rpx"))), null, 4),
                                    _cE("view", null, _uA(
                                        _cE("text", _uM("class" to "p_title", "style" to _nS(_uM("margin-left" to "7rpx"))), "同步单词智能复习", 4),
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("width" to "5rpx", "height" to "5rpx", "background-color" to "#E54E4E", "border-radius" to "5rpx", "margin-right" to "2rpx"))), null, 4),
                                            _cE("text", _uM("class" to "tips_index"), "有" + _tD(_ctx.dcNum) + "个单词待你复习！点击这里开始！赶紧巩固，提升记忆吧！", 1)
                                        ), 4)
                                    ))
                                ), 4),
                                _cE("text", _uM("class" to "next_btn", "style" to _nS(_uM("width" to "56.84rpx", "height" to "22.27rpx", "line-height" to "22.27rpx", "font-size" to "11rpx", "margin-right" to "20rpx")), "onClick" to fun(){
                                    _ctx.goReview("dc")
                                }), "去复习", 12, _uA(
                                    "onClick"
                                )),
                                _cE("image", _uM("src" to "/static/ico/close.png", "onClick" to fun(){
                                    _ctx.dcShow = false
                                }, "style" to _nS(_uM("position" to "absolute", "top" to "6rpx", "right" to "6rpx", "width" to "14rpx", "height" to "14rpx"))), null, 12, _uA(
                                    "onClick"
                                ))
                            ), 4)
                        } else {
                            _cC("v-if", true)
                        },
                        if (isTrue(_ctx.jzNum > 0 && _ctx.jzShow)) {
                            _cE("view", _uM("key" to 1, "class" to "p_item", "style" to _nS(_uM("justify-content" to "space-between", "position" to "relative"))), _uA(
                                _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                    _cE("image", _uM("src" to "/static/home2.png", "mode" to "", "style" to _nS(_uM("width" to "35.74rpx", "height" to "41.6rpx", "margin" to "0 5rpx"))), null, 4),
                                    _cE("view", null, _uA(
                                        _cE("text", _uM("class" to "p_title", "style" to _nS(_uM("margin-left" to "7rpx"))), "同步句子智能复习", 4),
                                        _cE("view", _uM("style" to _nS(_uM("flex-direction" to "row", "align-items" to "center"))), _uA(
                                            _cE("view", _uM("style" to _nS(_uM("width" to "5rpx", "height" to "5rpx", "background-color" to "#E54E4E", "border-radius" to "5rpx", "margin-right" to "2rpx"))), null, 4),
                                            _cE("text", _uM("class" to "tips_index"), "有" + _tD(_ctx.jzNum) + "个句子待你复习！点击这里开始！赶紧巩固，提升记忆吧！", 1)
                                        ), 4)
                                    ))
                                ), 4),
                                _cE("text", _uM("class" to "next_btn", "style" to _nS(_uM("width" to "56.84rpx", "height" to "22.27rpx", "line-height" to "22.27rpx", "font-size" to "11rpx", "margin-right" to "20rpx")), "onClick" to fun(){
                                    _ctx.goReview("jz")
                                }), "去复习", 12, _uA(
                                    "onClick"
                                )),
                                _cE("image", _uM("src" to "/static/ico/close.png", "onClick" to fun(){
                                    _ctx.jzShow = false
                                }, "style" to _nS(_uM("position" to "absolute", "top" to "6rpx", "right" to "6rpx", "width" to "14rpx", "height" to "14rpx"))), null, 12, _uA(
                                    "onClick"
                                ))
                            ), 4)
                        } else {
                            _cC("v-if", true)
                        }
                    ))
                } else {
                    _cC("v-if", true)
                }
            )
        }
        ), "_" to 1))
    }
    open var dcNum: Number by `$data`
    open var dcShow: Boolean by `$data`
    open var jzNum: Number by `$data`
    open var jzShow: Boolean by `$data`
    open var isShowPupup: Boolean by `$data`
    open var title: String by `$data`
    open var studyModules: UTSArray<StudyModule1> by `$data`
    open var taskList: UTSArray<returnItems>? by `$data`
    open var appUrl: String by `$data`
    open var verDescribe: String by `$data`
    open var appVerson: String by `$data`
    open var showUp: Boolean by `$data`
    open var percent: Number by `$data`
    open var updateBtn: Boolean by `$data`
    open var downloadedSize: Number by `$data`
    open var packageFileSize: Number by `$data`
    open var showInfo: Boolean by `$data`
    open var showUpdate: Boolean by `$data`
    open var userInfo: UserModel? by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("dcNum" to 0, "dcShow" to true, "jzNum" to 0, "jzShow" to true, "isShowPupup" to false, "title" to "Hello", "studyModules" to _uA<StudyModule1>(StudyModule1(title = "同步单词", subTitle = "单词学习和测试", icon = "/static/home1new.png", path = "/pages/sync/sync", module = "100"), StudyModule1(title = "同步句子", subTitle = "重点句子学习、测试", icon = "/static/home2new.png", path = "/pages/sentence/sentence", module = "200"), StudyModule1(title = "同步语法训练", subTitle = "自主训练", icon = "/static/home5new.png", path = "/pages/grammarSync/grammarSync", module = "500"), StudyModule1(title = "同步课文", subTitle = "课文朗读、跟读", icon = "/static/home3new.png", path = "/pages/textSync/textSync", module = "300"), StudyModule1(title = "同步阅读", subTitle = "题型阅读", icon = "/static/home4new.png", path = "/pages/readSync/readSync", module = "400"), StudyModule1(title = "同步口语听力", subTitle = "听说训练", icon = "/static/home6new.png", path = "/pages/listening/index", module = "600"), StudyModule1(title = "智练多多", subTitle = "AI智练", icon = "/static/home7.png", path = "/pages/training/index", module = "600")), "taskList" to null as UTSArray<returnItems>?, "appUrl" to "", "verDescribe" to "", "appVerson" to "", "showUp" to false, "percent" to 0, "updateBtn" to true, "downloadedSize" to 0, "packageFileSize" to 0, "showInfo" to true, "showUpdate" to false, "userInfo" to null as UserModel?)
    }
    open var getUserInfo2 = ::gen_getUserInfo2_fn
    open fun gen_getUserInfo2_fn() {
        uni_request<Result<UserModel?>>(RequestOptions(url = getUrl("/biz/studentUser/api/userInfo"), method = "GET", header = getHeader(), success = fun(res){
            val responseData = res.data
            if (responseData == null) {
                return
            }
            if (responseData.code as Number != 200) {
                uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                return
            }
            this.userInfo = responseData.data ?: null
            var _userInfo = this.userInfo
            if (this.userInfo != null) {
                updateUserState(this.userInfo)
            }
            if (responseData.data?.studentAccountType == "过期账号") {
                this.exit()
                return
            }
            ucsShare.setState("userName", responseData.data?.nickName ?: "")
            if (_userInfo?.actualSchoolName == null || _userInfo?.phonenumber == null || _userInfo?.periodBaseDataId == null || _userInfo?.sex == null) {
                this.goPath("/pages/user/editProfile")
                uni_showToast(ShowToastOptions(title = "请补全信息", icon = "none"))
            }
        }
        , fail = fun(err){
            console.log(err)
        }
        , complete = fun(_){}))
    }
    open var exit = ::gen_exit_fn
    open fun gen_exit_fn() {
        uni_removeStorageSync("token")
        uni_removeStorageSync("tenantCode")
        uni_removeStorageSync("storeId")
        uni_reLaunch(ReLaunchOptions(url = "/pages/login/login"))
    }
    open var getAppAndroidUpdate = ::gen_getAppAndroidUpdate_fn
    open fun gen_getAppAndroidUpdate_fn() {
        val appOpt = uni_getAppBaseInfo(null)
        uni_request<Result<upData1>>(RequestOptions(url = getUrl("/api/general/appAndroidUpdate"), method = "GET", data = UTSJSONObject(), header = getHeader(), success = fun(res){
            val responseData = res.data
            if (responseData == null) {
                return
            }
            if (responseData.code as Number != 200) {
                if (responseData.code == 401) {
                    uni_removeStorageSync("token")
                    uni_removeStorageSync("tenantCode")
                    uni_removeStorageSync("storeId")
                    uni_reLaunch(ReLaunchOptions(url = "/pages/login/login"))
                }
                uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                return
            }
            val _data = responseData.data
            if (_data == null) {
                return
            }
            val version = _data.version
            if (version == null) {
                return
            }
            var _version = parseInt(version)
            var _appVersionCode = parseInt(appOpt.appVersionCode ?: "0")
            if (_version > _appVersionCode) {
                this.appUrl = _data.apkUrl ?: ""
                this.appVerson = version
                this.verDescribe = _data.verDescribe ?: ""
                this.showUp = true
            }
        }
        , fail = fun(err){
            console.log(err)
        }
        , complete = fun(_){}))
    }
    open var download = ::gen_download_fn
    open fun gen_download_fn() {
        this.updateBtn = false
        val downloadTask = uni_downloadFile(DownloadFileOptions(url = this.appUrl, success = fun(res){
            if (res.statusCode === 200) {
                uni_installApk(InstallApkOptions(filePath = res.tempFilePath, complete = fun(_){
                    this.showUpdate = false
                    this.updateBtn = true
                    this.percent = 0
                    this.downloadedSize = 0
                    this.packageFileSize = 0
                }
                ))
            }
        }
        ))
        downloadTask.onProgressUpdate(fun(res){
            this.percent = parseInt(res.progress.toFixed(0))
            this.downloadedSize = parseFloat((res.totalBytesWritten / Math.pow(1024, 2)).toFixed(2))
            this.packageFileSize = parseFloat((res.totalBytesExpectedToWrite / Math.pow(1024, 2)).toFixed(2))
        }
        )
    }
    open var getTaskList = ::gen_getTaskList_fn
    open fun gen_getTaskList_fn() {
        uni_request<Result<UTSArray<returnItems>>>(RequestOptions(url = getUrl("/biz/studyTask/api/myList"), method = "GET", data = object : UTSJSONObject() {
            var pageSize: Number = 3000
        }, header = getHeader(), success = fun(res){
            val responseData = res.data
            if (responseData == null) {
                return
            }
            if (responseData.code as Number != 200) {
                if (responseData.code == 401) {
                    uni_reLaunch(ReLaunchOptions(url = "/pages/login/login"))
                }
                uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                return
            }
            this.taskList = responseData.data?.filter(fun(item): Boolean {
                return item.userStudyTaskProgress?.isFinish != "1"
            }
            )
        }
        , fail = fun(err){
            console.log(err)
        }
        , complete = fun(_){}))
    }
    open var goPath = ::gen_goPath_fn
    open fun gen_goPath_fn(path: String) {
        uni_navigateTo(NavigateToOptions(url = path, fail = fun(_) {
            console.log()
        }
        ))
    }
    open var handleModuleClick = ::gen_handleModuleClick_fn
    open fun gen_handleModuleClick_fn(module: StudyModule1) {
        if (this.userInfo?.studentAccountType == "过期账号") {
            uni_showToast(ShowToastOptions(title = "账号过期，请联系管理员", icon = "none"))
            return
        }
        if (this.userInfo?.englishStudyDayResidue == 0) {
            uni_showToast(ShowToastOptions(title = "学习时长不足", icon = "none"))
            return
        }
        uni_navigateTo(NavigateToOptions(url = module.path, fail = fun(_) {
            console.log()
        }
        ))
    }
    open var goTask = ::gen_goTask_fn
    open fun gen_goTask_fn() {
        if (this.userInfo?.studentAccountType == "过期账号") {
            uni_showToast(ShowToastOptions(title = "账号过期，请联系管理员", icon = "none"))
            return
        }
        if (this.userInfo?.englishStudyDayResidue == 0) {
            uni_showToast(ShowToastOptions(title = "学习时长不足", icon = "none"))
            return
        }
        uni_navigateTo(NavigateToOptions(url = "/pages/myTask/myTask", fail = fun(_) {
            console.log()
        }
        ))
    }
    open var goReview = ::gen_goReview_fn
    open fun gen_goReview_fn(type: String) {
        if (this.userInfo?.englishStudyDayResidue == 0) {
            uni_showToast(ShowToastOptions(title = "学习时长不足", icon = "none"))
            return
        }
        if (type == "dc") {
            this.dcShow = false
            uni_navigateTo(NavigateToOptions(url = "/pages/wordInter/review", fail = fun(err) {
                console.log(err)
            }))
        } else {
            this.jzShow = false
            uni_navigateTo(NavigateToOptions(url = "/pages/sentence/review", fail = fun(err) {
                console.log(err)
            }
            ))
        }
    }
    open var startStudy = ::gen_startStudy_fn
    open fun gen_startStudy_fn(item: returnItems) {
        if (this.userInfo?.studentAccountType == "过期账号") {
            uni_showToast(ShowToastOptions(title = "账号过期，请联系管理员", icon = "none"))
            return
        }
        if (this.userInfo?.englishStudyDayResidue == 0) {
            uni_showToast(ShowToastOptions(title = "学习时长不足", icon = "none"))
            return
        }
        if (item.userStudyTaskProgress?.isFinish == "1") {
            return
        }
        var _url = getJumpUrl(item)
        if (_url == "") {
            return
        }
        uni_navigateTo(NavigateToOptions(url = _url))
    }
    open var getNumFn = ::gen_getNumFn_fn
    open fun gen_getNumFn_fn() {
        uni_request<Result<UTSArray<modelItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishStat"), method = "GET", data = object : UTSJSONObject() {
            var textbookId = getTextBookId()
            var module = getModelKey("同步单词")
        }, header = getHeader(), success = fun(res){
            val responseData = res.data
            if (responseData == null) {
                return
            }
            if (responseData.code as Number != 200) {
                uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                return
            }
            if (responseData.data != null) {
                var _arr = responseData.data as UTSArray<modelItem>
                var _num: Number = 0
                _arr.forEach(fun(item: modelItem){
                    _num += (item.totalNum as Number)
                }
                )
                setTimeout(fun(){
                    this.dcNum = _num
                    this.dcShow = true
                }
                , 500)
            }
        }
        , fail = fun(err){
            console.log(err)
        }
        , complete = fun(_){}))
        uni_request<Result<UTSArray<modelItem>>>(RequestOptions(url = getUrl("/biz/problem/api/getReviewEnglishStat"), method = "GET", data = object : UTSJSONObject() {
            var textbookId = getTextBookId()
            var module = getModelKey("同步句子")
        }, header = getHeader(), success = fun(res){
            val responseData = res.data
            if (responseData == null) {
                return
            }
            if (responseData.code as Number != 200) {
                uni_showToast(ShowToastOptions(title = responseData.msg, icon = "none"))
                return
            }
            if (responseData.data != null) {
                var _arr = responseData.data as UTSArray<modelItem>
                var _num: Number = 0
                _arr.forEach(fun(item: modelItem){
                    _num += (item.totalNum as Number)
                }
                )
                setTimeout(fun(){
                    this.jzNum = _num
                    this.jzShow = true
                }
                , 500)
            }
        }
        , fail = fun(err){
            console.log(err)
        }
        , complete = fun(_){}))
    }
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ), _uA(
                GenApp.styles
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("index_bg" to _pS(_uM("backgroundImage" to "linear-gradient(to right, #2791FF, rgba(174, 195, 252, 0.53))", "backgroundColor" to "rgba(0,0,0,0)")), "exit_btn" to _pS(_uM("display" to "flex", "width" to "60rpx", "height" to "28rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "menu-box" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "menu-item-text" to _uM(".menu-box " to _uM("color" to "#E5EEFF", "fontSize" to "12rpx")), "mr-10" to _uM(".menu-box " to _uM("marginRight" to "15.8rpx")), "active" to _uM(".menu-box " to _uM("fontWeight" to "700", "color" to "#FFFFFF", "fontSize" to "15rpx")), "index-content" to _pS(_uM("flexDirection" to "row", "marginTop" to "20rpx")), "study-modules-style-2" to _pS(_uM("paddingTop" to "14.65rpx", "paddingRight" to "17.58rpx", "paddingBottom" to "14.65rpx", "paddingLeft" to "17.58rpx", "flexWrap" to "wrap", "flexDirection" to "row", "flex" to 1, "alignContent" to "flex-start")), "module-item" to _uM(".study-modules-style-2 " to _uM("borderTopLeftRadius" to "9rpx", "borderTopRightRadius" to "9rpx", "borderBottomRightRadius" to "9rpx", "borderBottomLeftRadius" to "9rpx", "boxShadow" to "0rpx 2rpx 4rpx 0rpx rgba(130, 141, 197, 0.3)", "marginBottom" to "6rpx", "position" to "relative")), "module-item-0" to _uM(".study-modules-style-2 " to _uM("width" to "124rpx", "height" to "199rpx", "marginRight" to "6rpx", "backgroundColor" to "#FFEFE6")), "module-text" to _uM(".study-modules-style-2 .module-item-0 .module-content " to _uM("alignItems" to "center"), ".study-modules-style-2 .module-item-0 " to _uM("marginTop" to "20rpx", "alignItems" to "center"), ".study-modules-style-2 .module-item-1 " to _uM("marginTop" to "7rpx", "marginLeft" to "12rpx"), ".study-modules-style-2 .module-item-2 " to _uM("marginTop" to "7rpx", "marginLeft" to "12rpx"), ".study-modules-style-2 .module-item-3 " to _uM("marginTop" to "7rpx", "marginLeft" to "12rpx"), ".study-modules-style-2 .module-item-4 " to _uM("marginTop" to "7rpx", "marginLeft" to "12rpx"), ".study-modules-style-2 .module-item-5 " to _uM("marginTop" to "8rpx", "marginLeft" to "10rpx"), ".study-modules-style-2 .module-item-6 " to _uM("marginTop" to "8rpx", "marginLeft" to "10rpx")), "small-module-item-wrap" to _uM(".study-modules-style-2 " to _uM("flexWrap" to "wrap", "flexDirection" to "row", "width" to "352rpx")), "module-item-1" to _uM(".study-modules-style-2 " to _uM("marginRight" to "6rpx", "width" to "173rpx", "height" to "97rpx", "backgroundColor" to "#FCF3D6")), "module-item-3" to _uM(".study-modules-style-2 " to _uM("marginRight" to "6rpx", "width" to "173rpx", "height" to "97rpx", "backgroundColor" to "#E7EBFD")), "module-item-5" to _uM(".study-modules-style-2 " to _uM("marginRight" to "6rpx", "width" to "238rpx", "height" to "110rpx", "backgroundColor" to "#F4E6FC")), "module-item-2" to _uM(".study-modules-style-2 " to _uM("width" to "173rpx", "height" to "97rpx", "backgroundColor" to "#DEF0FB")), "module-item-4" to _uM(".study-modules-style-2 " to _uM("width" to "173rpx", "height" to "97rpx", "backgroundColor" to "#E7EBFD")), "module-item-6" to _uM(".study-modules-style-2 " to _uM("width" to "238rpx", "height" to "110rpx", "backgroundColor" to "#D6E6FF")), "module-content" to _uM(".study-modules-style-2 .module-item-1 " to _uM("justifyContent" to "space-between", "flexDirection" to "row"), ".study-modules-style-2 .module-item-2 " to _uM("justifyContent" to "space-between", "flexDirection" to "row"), ".study-modules-style-2 .module-item-3 " to _uM("justifyContent" to "space-between", "flexDirection" to "row"), ".study-modules-style-2 .module-item-4 " to _uM("justifyContent" to "space-between", "flexDirection" to "row"), ".study-modules-style-2 .module-item-5 " to _uM("justifyContent" to "space-between", "flexDirection" to "row"), ".study-modules-style-2 .module-item-6 " to _uM("justifyContent" to "space-between", "flexDirection" to "row")), "module-subtitle-text" to _uM(".study-modules-style-2 " to _uM("opacity" to 0.6), ".study-modules-style-2 .module-item-0 .module-text " to _uM("fontSize" to "12rpx", "color" to "#9B6516", "marginTop" to "8rpx"), ".study-modules-style-2 .module-item-1 .module-text " to _uM("fontSize" to "9rpx", "color" to "#9E5F00", "marginTop" to "4rpx"), ".study-modules-style-2 .module-item-2 .module-text " to _uM("fontSize" to "9rpx", "color" to "#4488E8", "marginTop" to "4rpx"), ".study-modules-style-2 .module-item-3 .module-text " to _uM("fontSize" to "9rpx", "color" to "#3A57E2", "marginTop" to "4rpx"), ".study-modules-style-2 .module-item-4 .module-text " to _uM("fontSize" to "9rpx", "color" to "#589F2F", "marginTop" to "4rpx"), ".study-modules-style-2 .module-item-5 .module-text " to _uM("fontSize" to "9rpx", "color" to "#8249D4", "marginTop" to "6rpx"), ".study-modules-style-2 .module-item-6 .module-text " to _uM("fontSize" to "9rpx", "color" to "#546FEE", "marginTop" to "6rpx")), "module-title" to _uM(".study-modules-style-2 .module-item-0 .module-text " to _uM("fontWeight" to "700", "fontSize" to "15rpx", "color" to "#9B6516"), ".study-modules-style-2 .module-item-1 .module-text " to _uM("fontWeight" to "700", "fontSize" to "14rpx", "color" to "#9E5F00"), ".study-modules-style-2 .module-item-2 .module-text " to _uM("fontWeight" to "700", "fontSize" to "14rpx", "color" to "#2C6CEA"), ".study-modules-style-2 .module-item-3 .module-text " to _uM("fontWeight" to "700", "fontSize" to "14rpx", "color" to "#3A57E2"), ".study-modules-style-2 .module-item-4 .module-text " to _uM("fontWeight" to "700", "fontSize" to "14rpx", "color" to "#5A9F32"), ".study-modules-style-2 .module-item-5 .module-text " to _uM("fontWeight" to "700", "fontSize" to "14rpx", "color" to "#8E2CD9"), ".study-modules-style-2 .module-item-6 .module-text " to _uM("fontWeight" to "700", "fontSize" to "14rpx", "color" to "#546FEE")), "module-icon" to _uM(".study-modules-style-2 .module-item-0 .module-text " to _uM("width" to "80rpx", "height" to "80rpx", "marginTop" to "8rpx"), ".study-modules-style-2 .module-item-1 " to _uM("width" to "48rpx", "height" to "49rpx", "position" to "absolute", "right" to "10rpx", "bottom" to "6rpx"), ".study-modules-style-2 .module-item-2 " to _uM("width" to "72rpx", "height" to "54rpx", "position" to "absolute", "right" to "12rpx", "bottom" to "14rpx"), ".study-modules-style-2 .module-item-3 " to _uM("width" to "59rpx", "height" to "59rpx", "position" to "absolute", "right" to "10rpx", "bottom" to "20rpx"), ".study-modules-style-2 .module-item-4 " to _uM("width" to "88rpx", "height" to "50rpx", "position" to "absolute", "right" to "12rpx", "bottom" to "10rpx"), ".study-modules-style-2 .module-item-5 " to _uM("width" to "82rpx", "height" to "82rpx", "position" to "absolute", "right" to "18rpx", "bottom" to "12rpx"), ".study-modules-style-2 .module-item-6 " to _uM("width" to "108rpx", "height" to "74rpx", "position" to "absolute", "right" to "16rpx", "bottom" to "16rpx")), "start-btn-box" to _uM(".study-modules-style-2 .module-item-0 .module-text " to _uM("width" to "85rpx", "height" to "26rpx", "backgroundColor" to "#FFF8F4", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E4BE85", "borderRightColor" to "#E4BE85", "borderBottomColor" to "#E4BE85", "borderLeftColor" to "#E4BE85", "borderTopLeftRadius" to "999rpx", "borderTopRightRadius" to "999rpx", "borderBottomRightRadius" to "999rpx", "borderBottomLeftRadius" to "999rpx", "marginTop" to "11rpx", "alignItems" to "center", "justifyContent" to "center"), ".study-modules-style-2 .module-item-1 .module-text " to _uM("width" to "54rpx", "height" to "20rpx", "backgroundColor" to "#FFF8F4", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E4BE85", "borderRightColor" to "#E4BE85", "borderBottomColor" to "#E4BE85", "borderLeftColor" to "#E4BE85", "borderTopLeftRadius" to "999rpx", "borderTopRightRadius" to "999rpx", "borderBottomRightRadius" to "999rpx", "borderBottomLeftRadius" to "999rpx", "marginTop" to "14rpx", "alignItems" to "center", "justifyContent" to "center"), ".study-modules-style-2 .module-item-2 .module-text " to _uM("width" to "54rpx", "height" to "20rpx", "backgroundColor" to "#F4F7FD", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#7DA9FF", "borderRightColor" to "#7DA9FF", "borderBottomColor" to "#7DA9FF", "borderLeftColor" to "#7DA9FF", "borderTopLeftRadius" to "999rpx", "borderTopRightRadius" to "999rpx", "borderBottomRightRadius" to "999rpx", "borderBottomLeftRadius" to "999rpx", "marginTop" to "14rpx", "alignItems" to "center", "justifyContent" to "center"), ".study-modules-style-2 .module-item-3 .module-text " to _uM("width" to "54rpx", "height" to "20rpx", "backgroundColor" to "#F4F7FD", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#98A6EE", "borderRightColor" to "#98A6EE", "borderBottomColor" to "#98A6EE", "borderLeftColor" to "#98A6EE", "borderTopLeftRadius" to "999rpx", "borderTopRightRadius" to "999rpx", "borderBottomRightRadius" to "999rpx", "borderBottomLeftRadius" to "999rpx", "marginTop" to "14rpx", "alignItems" to "center", "justifyContent" to "center"), ".study-modules-style-2 .module-item-4 .module-text " to _uM("width" to "54rpx", "height" to "20rpx", "backgroundColor" to "#F7FFF1", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#CFE4C2", "borderRightColor" to "#CFE4C2", "borderBottomColor" to "#CFE4C2", "borderLeftColor" to "#CFE4C2", "borderTopLeftRadius" to "999rpx", "borderTopRightRadius" to "999rpx", "borderBottomRightRadius" to "999rpx", "borderBottomLeftRadius" to "999rpx", "marginTop" to "14rpx", "alignItems" to "center", "justifyContent" to "center"), ".study-modules-style-2 .module-item-5 .module-text " to _uM("width" to "54rpx", "height" to "20rpx", "backgroundColor" to "#F4F7FD", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#C79CDF", "borderRightColor" to "#C79CDF", "borderBottomColor" to "#C79CDF", "borderLeftColor" to "#C79CDF", "borderTopLeftRadius" to "999rpx", "borderTopRightRadius" to "999rpx", "borderBottomRightRadius" to "999rpx", "borderBottomLeftRadius" to "999rpx", "marginTop" to "24rpx", "alignItems" to "center", "justifyContent" to "center"), ".study-modules-style-2 .module-item-6 .module-text " to _uM("width" to "54rpx", "height" to "20rpx", "backgroundColor" to "#F4F7FD", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#93A6FF", "borderRightColor" to "#93A6FF", "borderBottomColor" to "#93A6FF", "borderLeftColor" to "#93A6FF", "borderTopLeftRadius" to "999rpx", "borderTopRightRadius" to "999rpx", "borderBottomRightRadius" to "999rpx", "borderBottomLeftRadius" to "999rpx", "marginTop" to "24rpx", "alignItems" to "center", "justifyContent" to "center")), "start-btn" to _uM(".study-modules-style-2 .module-item-0 .module-text .start-btn-box " to _uM("fontSize" to "12rpx", "color" to "#E59D33"), ".study-modules-style-2 .module-item-1 .module-text .start-btn-box " to _uM("fontSize" to "9rpx", "color" to "#DEA148"), ".study-modules-style-2 .module-item-2 .module-text .start-btn-box " to _uM("fontSize" to "9rpx", "color" to "#438BF0"), ".study-modules-style-2 .module-item-3 .module-text .start-btn-box " to _uM("fontSize" to "9rpx", "color" to "#3A57E2"), ".study-modules-style-2 .module-item-4 .module-text .start-btn-box " to _uM("fontSize" to "9rpx", "color" to "#5A9F32"), ".study-modules-style-2 .module-item-5 .module-text .start-btn-box " to _uM("fontSize" to "9rpx", "color" to "#8249D5"), ".study-modules-style-2 .module-item-6 .module-text .start-btn-box " to _uM("fontSize" to "9rpx", "color" to "#546FEE")), "divider" to _pS(_uM("width" to "221rpx", "height" to "100%", "paddingTop" to 0, "paddingRight" to "17.58rpx", "paddingBottom" to "20rpx", "paddingLeft" to 0, "flexDirection" to "column")), "task-box" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "marginBottom" to "10rpx")), "task-all" to _uM(".task-box " to _uM("width" to "76rpx", "height" to "19rpx", "backgroundImage" to "none", "backgroundColor" to "#E2E6F6", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "text" to _uM(".task-box .task-all " to _uM("fontSize" to "14rpx", "color" to "#3D3D3D", "fontWeight" to "bold", "lineHeight" to "19rpx")), "task-title" to _uM(".task-box " to _uM("fontSize" to "15.23rpx", "fontWeight" to "bold", "color" to "#FDF8FF")), "task-swiper" to _pS(_uM("height" to "230rpx", "marginBottom" to "10rpx", "width" to "100%", "paddingBottom" to "-9.62rpx")), "task-item-box" to _uM(".task-swiper " to _uM("height" to "50rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginBottom" to "9.62rpx")), "task-item-shadow" to _uM(".task-swiper " to _uM("width" to "100%", "height" to "53rpx", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginTop" to "3rpx", "backgroundColor" to "#BFC4DC", "position" to "absolute", "top" to 0, "left" to 0, "zIndex" to -1)), "task-item" to _uM(".task-swiper " to _uM("width" to "100%", "height" to "52rpx", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "paddingTop" to 0, "paddingRight" to "8rpx", "paddingBottom" to 0, "paddingLeft" to "8rpx")), "task-item-title" to _uM(".task-swiper .task-item " to _uM("marginTop" to "5.27rpx", "fontWeight" to "400", "fontSize" to "11rpx", "height" to "13rpx", "color" to "#3D3D3D", "lineHeight" to "13rpx", "marginBottom" to "4rpx")), "task-item-progress" to _uM(".task-swiper .task-item " to _uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "task-item-progress-text" to _uM(".task-swiper .task-item " to _uM("flex" to 1, "marginRight" to "15rpx")), "task-item-progress-text-text" to _uM(".task-swiper .task-item " to _uM("fontWeight" to "400", "fontSize" to "11rpx", "color" to "#555555", "lineHeight" to "13rpx", "marginBottom" to "4rpx")), "task-item-progress-btn" to _uM(".task-swiper .task-item " to _uM("width" to "44rpx", "height" to "18rpx", "alignItems" to "center", "justifyContent" to "center", "position" to "relative")), "_bgimg" to _uM(".task-swiper .task-item .task-item-progress-btn " to _uM("position" to "absolute", "width" to "100%", "height" to "100%", "left" to 0, "top" to 0)), "task-item-progress-btn-text" to _uM(".task-swiper .task-item " to _uM("fontSize" to "11rpx", "color" to "#FFFFFF", "lineHeight" to "13rpx", "position" to "relative", "zIndex" to 9)), "home-banner" to _pS(_uM("width" to "100%", "height" to "68.55rpx")), "up_box" to _pS(_uM("width" to "346.88rpx", "height" to "217.97rpx", "position" to "relative", "marginLeft" to "-30rpx")), "up_box_bg" to _uM(".up_box " to _uM("position" to "absolute", "width" to "100%", "height" to "100%", "top" to 0, "left" to 0)), "up_box_content" to _uM(".up_box " to _uM("marginLeft" to "30rpx", "height" to "100%", "paddingTop" to "15.23rpx", "paddingRight" to "15.23rpx", "paddingBottom" to "15.23rpx", "paddingLeft" to "15.23rpx", "alignItems" to "center")), "title" to _uM(".up_box .up_box_content " to _uM("marginTop" to "4.68rpx", "width" to "145.9rpx", "height" to "26rpx")), "verson" to _uM(".up_box .up_box_content " to _uM("width" to "87rpx", "height" to "22rpx", "backgroundImage" to "none", "backgroundColor" to "#FFD597", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "22rpx", "textAlign" to "center", "marginTop" to "20rpx")), "up_btn" to _uM(".up_box .up_box_content " to _uM("width" to "96rpx", "height" to "29rpx", "backgroundImage" to "none", "backgroundColor" to "#082337", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "fontSize" to "12rpx", "color" to "#FFFFFF", "lineHeight" to "29rpx", "textAlign" to "center")), "popup_top" to _pS(_uM("position" to "fixed", "top" to "11.72rpx", "left" to "50%", "transform" to "translateX(-50%)")), "p_item" to _uM(".popup_top " to _uM("width" to "501rpx", "height" to "59rpx", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "boxShadow" to "0rpx 2rpx 6rpx 0rpx rgba(0, 0, 0, 0.3)", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginBottom" to "2rpx", "flexDirection" to "row", "alignItems" to "center")), "p_title" to _uM(".popup_top .p_item " to _uM("fontWeight" to "700", "fontSize" to "12rpx", "color" to "#6694DF", "lineHeight" to "23rpx")), "tips_index" to _uM(".popup_top .p_item " to _uM("fontSize" to "12rpx", "color" to "#3D3D3D", "lineHeight" to "23rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
