package uts.sdk.modules.wjHookScreen;
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager

import android.view.View

import android.widget.EditText

fun hideKeyboardFn(activity: Activity) {
     val view = activity.currentFocus ?: View(activity) // 确保有一个非空视图
       val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
       imm.hideSoftInputFromWindow(view.windowToken, 0)
}



fun disableKeyboardInput(editText: EditText) {
    editText.setOnTouchListener { _, _ -> true }
}

fun enableKeyboardInput(editText: EditText) {
    editText.setOnTouchListener(null)
}