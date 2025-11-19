@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uts.sdk.modules.wjHookScreen
import android.app.Application
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.view.KeyEvent
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.EditText
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
typealias MyApiErrorCode = Number
val MyAPIErrors: Map<MyApiErrorCode, String> = Map(_uA(
    _uA(
        9010001,
        "custom error mseeage1"
    ),
    _uA(
        9010002,
        "custom error mseeage2"
    )
))
open class AppHookProxy : UTSAndroidHookProxy {
    override fun onCreate(application: Application) {
        android.util.Log.d("AppHookProxy", "AppHookProxy--onCreate---")
        if (UTSAndroid.isPrivacyAgree()) {
            android.util.Log.d("AppHookProxy", "AppHookProxy--onCreate---isPrivacyAgree")
        }
    }
}
var callback: (eventLog: UTSJSONObject) -> Unit = fun(res){}
fun onCallbackChange(fn: (eventLog: UTSJSONObject) -> Unit) {
    callback = fn
}
open class UTSActivityWindowCallback : UniActivityWindowCallback {
    constructor() : super() {}
    override fun dispatchKeyEvent(params: UniActivityParams, event: KeyEvent?) {
        val _log: UTSJSONObject = _uO("type" to if ((event?.getAction() ?: 0) == 0) {
            "onKeyDown"
        } else {
            "onKeyUp"
        }
        , "params" to params, "keyCode" to (event?.scanCode ?: 0), "event" to event)
        callback(_log)
    }
}
open class UTSAcvitiyKeyEventCallback : UniActivityKeyEventCallback {
    constructor() : super() {}
    override fun onKeyDown(params: UniActivityParams, keyCode: Int, event: KeyEvent?) {
        val _log: UTSJSONObject = _uO("type" to "onKeyDown", "params" to params, "keyCode" to keyCode, "event" to event)
        callback(_log)
    }
    override fun onPreKeyDown(params: UniActivityParams, keyCode: Int, event: KeyEvent?) {}
    override fun onKeyLongPress(params: UniActivityParams, keyCode: Int, event: KeyEvent?) {
        val _log: UTSJSONObject = _uO("type" to "onKeyLongPress", "params" to params, "keyCode" to keyCode, "event" to event)
        callback(_log)
    }
    override fun onPreKeyLongPress(params: UniActivityParams, keyCode: Int, event: KeyEvent?) {
        val _log: UTSJSONObject = _uO("type" to "onPreKeyLongPress", "params" to params, "keyCode" to keyCode, "event" to event)
        callback(_log)
    }
}
fun hookBug(height: Int) {
    AndroidBug5497Workaround.assistActivity(UTSAndroid.getUniActivity()!!, height, true)
}
val setScreen = fun() {
    val window = UTSAndroid.getUniActivity()!!.getWindow()
    val controller = window.getInsetsController()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        controller!!.hide(WindowInsets.Type.statusBars())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(Color.BLACK)
        }
        controller!!.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE)
        controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE)
    } else {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}
val outScreen = fun() {
    val window = UTSAndroid.getUniActivity()!!.getWindow()
    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}
fun hideKeyboard(text: EditText) {
    disableKeyboardInput(text)
}
