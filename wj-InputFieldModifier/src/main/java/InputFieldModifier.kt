package uts.sdk.modules.wjInputFieldModifier

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import io.dcloud.uts.console
import android.os.Vibrator
import android.os.VibrationEffect
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
// 移除直接导入，改用反射机制
// import uts.sdk.modules.wjInput.NoPasteEditText
class InputFieldModifier(private val context: Context) {

    // 事件去重相关变量
    private var lastEventTime = 0L
    private var lastEventKeyCode = -1
    private var lastEventAction = -1
    private val EVENT_DUPLICATE_THRESHOLD = 100L // 100毫秒内的相同事件视为重复

    // 字符到KeyCode的映射表
    private val charToKeyCodeMap = mapOf(
        // 数字键
        '0' to KeyEvent.KEYCODE_0, '1' to KeyEvent.KEYCODE_1, '2' to KeyEvent.KEYCODE_2,
        '3' to KeyEvent.KEYCODE_3, '4' to KeyEvent.KEYCODE_4, '5' to KeyEvent.KEYCODE_5,
        '6' to KeyEvent.KEYCODE_6, '7' to KeyEvent.KEYCODE_7, '8' to KeyEvent.KEYCODE_8,
        '9' to KeyEvent.KEYCODE_9,
        // 字母键
        'a' to KeyEvent.KEYCODE_A, 'b' to KeyEvent.KEYCODE_B, 'c' to KeyEvent.KEYCODE_C,
        'd' to KeyEvent.KEYCODE_D, 'e' to KeyEvent.KEYCODE_E, 'f' to KeyEvent.KEYCODE_F,
        'g' to KeyEvent.KEYCODE_G, 'h' to KeyEvent.KEYCODE_H, 'i' to KeyEvent.KEYCODE_I,
        'j' to KeyEvent.KEYCODE_J, 'k' to KeyEvent.KEYCODE_K, 'l' to KeyEvent.KEYCODE_L,
        'm' to KeyEvent.KEYCODE_M, 'n' to KeyEvent.KEYCODE_N, 'o' to KeyEvent.KEYCODE_O,
        'p' to KeyEvent.KEYCODE_P, 'q' to KeyEvent.KEYCODE_Q, 'r' to KeyEvent.KEYCODE_R,
        's' to KeyEvent.KEYCODE_S, 't' to KeyEvent.KEYCODE_T, 'u' to KeyEvent.KEYCODE_U,
        'v' to KeyEvent.KEYCODE_V, 'w' to KeyEvent.KEYCODE_W, 'x' to KeyEvent.KEYCODE_X,
        'y' to KeyEvent.KEYCODE_Y, 'z' to KeyEvent.KEYCODE_Z,
        // 大写字母（映射到相同的KeyCode，通过shift修饰符区分）
        'A' to KeyEvent.KEYCODE_A, 'B' to KeyEvent.KEYCODE_B, 'C' to KeyEvent.KEYCODE_C,
        'D' to KeyEvent.KEYCODE_D, 'E' to KeyEvent.KEYCODE_E, 'F' to KeyEvent.KEYCODE_F,
        'G' to KeyEvent.KEYCODE_G, 'H' to KeyEvent.KEYCODE_H, 'I' to KeyEvent.KEYCODE_I,
        'J' to KeyEvent.KEYCODE_J, 'K' to KeyEvent.KEYCODE_K, 'L' to KeyEvent.KEYCODE_L,
        'M' to KeyEvent.KEYCODE_M, 'N' to KeyEvent.KEYCODE_N, 'O' to KeyEvent.KEYCODE_O,
        'P' to KeyEvent.KEYCODE_P, 'Q' to KeyEvent.KEYCODE_Q, 'R' to KeyEvent.KEYCODE_R,
        'S' to KeyEvent.KEYCODE_S, 'T' to KeyEvent.KEYCODE_T, 'U' to KeyEvent.KEYCODE_U,
        'V' to KeyEvent.KEYCODE_V, 'W' to KeyEvent.KEYCODE_W, 'X' to KeyEvent.KEYCODE_X,
        'Y' to KeyEvent.KEYCODE_Y, 'Z' to KeyEvent.KEYCODE_Z,
        // 常用标点符号
        ' ' to KeyEvent.KEYCODE_SPACE,
        '.' to KeyEvent.KEYCODE_PERIOD,
        ',' to KeyEvent.KEYCODE_COMMA,
        '?' to KeyEvent.KEYCODE_SLASH, // 需要shift修饰符
        '!' to KeyEvent.KEYCODE_1,     // 需要shift修饰符
        '@' to KeyEvent.KEYCODE_2,     // 需要shift修饰符
        '#' to KeyEvent.KEYCODE_3,     // 需要shift修饰符
        '$' to KeyEvent.KEYCODE_4,     // 需要shift修饰符
        '%' to KeyEvent.KEYCODE_5,     // 需要shift修饰符
        '^' to KeyEvent.KEYCODE_6,     // 需要shift修饰符
        '&' to KeyEvent.KEYCODE_7,     // 需要shift修饰符
        '*' to KeyEvent.KEYCODE_8,     // 需要shift修饰符
        '(' to KeyEvent.KEYCODE_9,     // 需要shift修饰符
        ')' to KeyEvent.KEYCODE_0,     // 需要shift修饰符
        '-' to KeyEvent.KEYCODE_MINUS,
        '=' to KeyEvent.KEYCODE_EQUALS,
        '[' to KeyEvent.KEYCODE_LEFT_BRACKET,
        ']' to KeyEvent.KEYCODE_RIGHT_BRACKET,
        ';' to KeyEvent.KEYCODE_SEMICOLON,
        '\'' to KeyEvent.KEYCODE_APOSTROPHE,
        '/' to KeyEvent.KEYCODE_SLASH
    )

    // 需要shift修饰符的字符
    private val shiftRequiredChars = setOf(
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '?'
    )

    /**
     * 在当前获得焦点的输入框中插入文本
     * @param text 要插入的文本
     */
    fun insertText(text: String) {
        // 执行震动
        vibrate()
        
        getFocusedInputField()?.let { input ->
            // 尝试使用键盘事件派发
            var allCharactersDispatched = true
            
            for (char in text) {
                if (!dispatchCharacterKeyEvent(input, char)) {
                    allCharactersDispatched = false
                    break
                }
            }
            
            // 如果键盘事件派发失败，回退到直接文本操作
            if (!allCharactersDispatched) {
                console.log("键盘事件派发失败，回退到直接文本操作")
                fallbackInsertText(input, text)
            }
            
            // 使用post方法确保在UI线程的下一个循环中再次请求焦点
            input.post {
                input.requestFocus()
            }
        }
    }

    /**
     * 派发字符键盘事件
     * @param input 目标输入框
     * @param char 要输入的字符
     * @return 是否成功派发事件
     */
    private fun dispatchCharacterKeyEvent(input: EditText, char: Char): Boolean {
        val keyCode = charToKeyCodeMap[char] ?: return false
        
        try {
            val needsShift = shiftRequiredChars.contains(char)
            val metaState = if (needsShift) KeyEvent.META_SHIFT_ON else 0
            val currentTime = System.currentTimeMillis()
            
            // 检查是否是重复事件
            if (currentTime - lastEventTime < EVENT_DUPLICATE_THRESHOLD &&
                lastEventKeyCode == keyCode &&
                lastEventAction == KeyEvent.ACTION_DOWN) {
                console.log("检测到重复键盘事件，跳过处理: keyCode=$keyCode")
                return true // 返回true表示事件已处理，避免回退到直接文本操作
            }
            
            // 检查输入框类型，如果是NoPasteEditText则使用延迟策略避免冲突
            if (input.javaClass.simpleName == "NoPasteEditText") {
                console.log("检测到NoPasteEditText，使用延迟策略: keyCode=$keyCode")
                // 延迟20毫秒后派发，避免与noPause.kt的事件处理冲突
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    dispatchCharacterKeyEventDirect(input, char, keyCode, needsShift, metaState)
                }, 20)
                return true
            }
            
            // 更新最后事件信息
            lastEventTime = currentTime
            lastEventKeyCode = keyCode
            lastEventAction = KeyEvent.ACTION_DOWN
            
            // 创建ACTION_DOWN事件
            val downEvent = KeyEvent(
                currentTime,
                currentTime,
                KeyEvent.ACTION_DOWN,
                keyCode,
                0, // repeat count
                metaState
            )
            
            // 创建ACTION_UP事件
            val upEvent = KeyEvent(
                currentTime + 10, // UP事件稍微延后
                currentTime + 10,
                KeyEvent.ACTION_UP,
                keyCode,
                0, // repeat count
                metaState
            )
            
            console.log("派发键盘事件: keyCode=$keyCode, char=$char, needsShift=$needsShift")
            
            // 派发事件
            val downResult = input.dispatchKeyEvent(downEvent)
            val upResult = input.dispatchKeyEvent(upEvent)
            
            console.log("键盘事件派发结果: down=$downResult, up=$upResult")
            
            return downResult && upResult
        } catch (e: Exception) {
            console.log("派发字符键盘事件失败: ${e.message}")
            return false
        }
    }
    
    /**
     * 直接派发字符键盘事件（用于延迟调用）
     * @param input 目标输入框
     * @param char 要输入的字符
     * @param keyCode 键码
     * @param needsShift 是否需要shift修饰符
     * @param metaState 修饰符状态
     */
    private fun dispatchCharacterKeyEventDirect(input: EditText, char: Char, keyCode: Int, needsShift: Boolean, metaState: Int) {
        try {
            console.log("延迟派发键盘事件: keyCode=$keyCode, char=$char, needsShift=$needsShift")
            
            val downEvent = KeyEvent(
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                KeyEvent.ACTION_DOWN,
                keyCode,
                0,
                metaState
            )
            
            val upEvent = KeyEvent(
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                KeyEvent.ACTION_UP,
                keyCode,
                0,
                metaState
            )
            
            val downResult = input.dispatchKeyEvent(downEvent)
            val upResult = input.dispatchKeyEvent(upEvent)
            
            console.log("延迟键盘事件派发结果: down=$downResult, up=$upResult")
            
        } catch (e: Exception) {
            console.log("延迟派发键盘事件失败: ${e.message}")
        }
    }
     
     /**
      * 直接派发退格键盘事件（用于延迟调用）
      * @param input 目标输入框
      */
     private fun dispatchBackspaceKeyEventDirect(input: EditText) {
         try {
             console.log("延迟派发退格键盘事件")
             
             val downEvent = KeyEvent(
                 System.currentTimeMillis(),
                 System.currentTimeMillis(),
                 KeyEvent.ACTION_DOWN,
                 KeyEvent.KEYCODE_DEL,
                 0,
                 0
             )
             
             val upEvent = KeyEvent(
                 System.currentTimeMillis(),
                 System.currentTimeMillis(),
                 KeyEvent.ACTION_UP,
                 KeyEvent.KEYCODE_DEL,
                 0,
                 0
             )
             
             val downResult = input.dispatchKeyEvent(downEvent)
             val upResult = input.dispatchKeyEvent(upEvent)
             
             console.log("延迟退格键盘事件派发结果: down=$downResult, up=$upResult")
             
         } catch (e: Exception) {
             console.log("延迟派发退格键盘事件失败: ${e.message}")
         }
     }
     
     /**
      * 回退到直接文本操作的方法
     * @param input 目标输入框
     * @param text 要插入的文本
     */
    private fun fallbackInsertText(input: EditText, text: String) {
        val start = input.selectionStart
        val end = input.selectionEnd
        
        // 检查是否为NoPasteEditText类型，通过类名检查而不是直接使用is操作符
        if (input.javaClass.name == "uts.sdk.modules.wjInput.NoPasteEditText") {
            // 使用反射调用replaceText方法
            try {
                val replaceTextMethod = input.javaClass.getMethod("replaceText", Int::class.java, Int::class.java, CharSequence::class.java)
                replaceTextMethod.invoke(input, minOf(start, end), maxOf(start, end), text)
            } catch (e: Exception) {
                // 如果反射调用失败，回退到标准方法
                console.log("反射调用replaceText失败: ${e.message}")
                input.text?.replace(
                    minOf(start, end),
                    maxOf(start, end),
                    text
                )
                // 移动光标到插入文本之后
                input.setSelection(start + text.length)
            }
        } else {
            // 对于普通EditText，使用标准的replace方法
            input.text?.replace(
                minOf(start, end),
                maxOf(start, end),
                text
            )
            // 移动光标到插入文本之后
            input.setSelection(start + text.length)
        }
    }

    /**
     * 在当前获得焦点的输入框中执行退格操作
     */
    fun performBackspace() {
        // 执行震动
        vibrate()
        
        getFocusedInputField()?.let { input ->
            // 尝试使用键盘事件派发退格键
            if (!dispatchBackspaceKeyEvent(input)) {
                console.log("退格键盘事件派发失败，回退到直接文本操作")
                fallbackPerformBackspace(input)
            }
            
            input.post {
                input.requestFocus()
            }
        }
    }
    
    /**
     * 派发退格键盘事件
     * @param input 目标输入框
     * @return 是否成功派发事件
     */
    private fun dispatchBackspaceKeyEvent(input: EditText): Boolean {
        try {
            val currentTime = System.currentTimeMillis()
            val keyCode = KeyEvent.KEYCODE_DEL
            
            // 检查是否是重复事件
            if (currentTime - lastEventTime < EVENT_DUPLICATE_THRESHOLD &&
                lastEventKeyCode == keyCode &&
                lastEventAction == KeyEvent.ACTION_DOWN) {
                console.log("检测到重复退格事件，跳过处理")
                return true // 返回true表示事件已处理，避免回退到直接文本操作
            }
            
            // 检查输入框类型，如果是NoPasteEditText则使用延迟策略避免冲突
            if (input.javaClass.simpleName == "NoPasteEditText") {
                console.log("检测到NoPasteEditText，使用延迟策略派发退格键")
                // 延迟20毫秒后派发，避免与noPause.kt的事件处理冲突
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    dispatchBackspaceKeyEventDirect(input)
                }, 20)
                return true
            }
            
            // 更新最后事件信息
            lastEventTime = currentTime
            lastEventKeyCode = keyCode
            lastEventAction = KeyEvent.ACTION_DOWN
            
            // 创建退格键的ACTION_DOWN事件
            val downEvent = KeyEvent(
                currentTime,
                currentTime,
                KeyEvent.ACTION_DOWN,
                KeyEvent.KEYCODE_DEL,
                0 // repeat count
            )
            
            // 创建退格键的ACTION_UP事件
            val upEvent = KeyEvent(
                currentTime + 10, // UP事件稍微延后
                currentTime + 10,
                KeyEvent.ACTION_UP,
                KeyEvent.KEYCODE_DEL,
                0 // repeat count
            )
            
            console.log("派发退格键盘事件")
            
            // 派发事件
            val downResult = input.dispatchKeyEvent(downEvent)
            val upResult = input.dispatchKeyEvent(upEvent)
            
            console.log("退格键盘事件派发结果: down=$downResult, up=$upResult")
            
            return downResult && upResult
        } catch (e: Exception) {
            console.log("退格键盘事件派发异常: ${e.message}")
            return false
        }
    }
    
    /**
     * 回退到直接文本操作的退格方法
     * @param input 目标输入框
     */
    private fun fallbackPerformBackspace(input: EditText) {
        val start = input.selectionStart
        val end = input.selectionEnd
        
        // 检查是否为NoPasteEditText类型，通过类名检查
        if (input.javaClass.name == "uts.sdk.modules.wjInput.NoPasteEditText") {
            try {
                val replaceTextMethod = input.javaClass.getMethod("replaceText", Int::class.java, Int::class.java, CharSequence::class.java)
                
                when {
                    start == end && start > 0 -> {
                        // 没有选中文本，删除前一个字符
                        replaceTextMethod.invoke(input, start - 1, start, "")
                    }
                    start != end -> {
                        // 有选中文本，删除选中部分
                        replaceTextMethod.invoke(input, start, end, "")
                    }
                    else -> {
                        // 光标在文本开始位置，无法执行退格操作
                        // 不执行任何操作
                    }
                }
            } catch (e: Exception) {
                // 如果反射调用失败，回退到标准方法
                console.log("反射调用replaceText失败: ${e.message}")
                // 使用标准的delete方法
                when {
                    start == end && start > 0 -> {
                        // 没有选中文本，删除前一个字符
                        input.text?.delete(start - 1, start)
                        input.setSelection(start - 1)
                    }
                    start != end -> {
                        // 有选中文本，删除选中部分
                        input.text?.delete(start, end)
                        input.setSelection(start)
                    }
                    else -> {
                        // 光标在文本开始位置，无法执行退格操作
                        // 不执行任何操作
                    }
                }
            }
        } else {
            // 对于普通EditText，使用标准的delete方法
            when {
                start == end && start > 0 -> {
                    // 没有选中文本，删除前一个字符
                    input.text?.delete(start - 1, start)
                    input.setSelection(start - 1)
                }
                start != end -> {
                    // 有选中文本，删除选中部分
                    input.text?.delete(start, end)
                    input.setSelection(start)
                }
                else -> {
                    // 光标在文本开始位置，无法执行退格操作
                    // 不执行任何操作
                }
            }
        }
    }

    /**
     * 执行短震动
     */
    private fun vibrate() {
        try {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            if (vibrator != null && vibrator.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // 使用新的API (Android 8.0及以上)
                    vibrator.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    // 兼容旧版本
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(40)
                }
            }
        } catch (e: Exception) {
            console.log("震动执行失败: ${e.message}")
        }
    }

    /**
     * 获取当前获得焦点的输入框
     */
    private fun getFocusedInputField(): EditText? {
        return when (context) {
            is Activity -> getFocusedViewFromActivity(context)
            else -> getFocusedViewFromWindowManager()
        }
    }

    /**
     * 从Activity获取焦点视图
     */
    private fun getFocusedViewFromActivity(activity: Activity): EditText? {
        return activity.currentFocus?.let { view ->
            if (view is EditText) view else null
        } ?: findFocusedView(activity.window.decorView)
    }

    /**
     * 通过WindowManager获取焦点视图
     */
    private fun getFocusedViewFromWindowManager(): EditText? {
        val windowManager = ContextCompat.getSystemService(context, WindowManager::class.java) ?: return null
        return (context as? Activity)?.window?.decorView?.let { decorView ->
            findFocusedView(decorView)
        }
    }

    /**
     * 在View层次结构中查找获得焦点的EditText
     */
    private fun findFocusedView(view: View?): EditText? {
        if (view == null) return null
        
        if (view.isFocused && view is EditText) {
            return view
        }
        
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                val result = findFocusedView(child)
                if (result != null) {
                    return result
                }
            }
        }
        
        return null
    }

    companion object {
        /**
         * 快速使用的静态方法
         */
        @JvmStatic
        fun insertText(context: Context, text: String) {
            InputFieldModifier(context).insertText(text)
        }

        @JvmStatic
        fun performBackspace(context: Context) {
            InputFieldModifier(context).performBackspace()
        }
    }
}