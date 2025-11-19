package uts.sdk.modules.wjInput;
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.EditText
import io.dcloud.uts.console
import kotlinx.coroutines.*
import android.os.Looper
import android.util.TypedValue

import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.view.ViewTreeObserver
import io.dcloud.uts.UTSAndroid
import android.graphics.Typeface
import java.io.File

class NoPasteEditText : EditText {

	val scaledDensity2 = resources.displayMetrics.scaledDensity
	        // setTextSize(size * scaledDensity)
  // 最小宽度（示例值：100dp）
    private var minWidthPx = 10.dpToPx()
	
	// 默认文字大小（单位：px）
	private var defaultTextSize = 30f
	
	// 默认行高倍数
	private var defaultLineSpacingMultiplier = 1f
	
	// 默认行高额外空间（单位：dp）
	private var defaultLineSpacingExtra = 1f
	
	// 是否显示底部边框
	private var showBottomBorder = false
	
	// 底部边框颜色
	private var bottomBorderColor = android.graphics.Color.GRAY
	
	// 底部边框宽度（单位：dp）
	private var bottomBorderWidth = 1f
	
	// 是否自动调整宽度
	private var isAutoWidth = false
	private var onTapCallback: (() -> Unit)? = null
	
	private var onCursorPositionChangedCallback: ((Int) -> Unit)? = null
	private var onInputCallback: ((String) -> Unit)? = null
	
	// 添加位置和尺寸回调
	private var onPositionSizeCallback: ((Float, Float, Int, Int, Int) -> Unit)? = null
	
	// 是否屏蔽键盘输入
	private var blockKeyboardInput = false
	
	// 按键事件回调
	private var onKeyEventCallback: ((String) -> Unit)? = null
	
	// 文本对齐方式
	private var textAlignment = android.view.Gravity.START
	
	// 是否启用校验模式
	private var validationMode = false
	
	// 校验目标文本
	private var validationTargetText = ""
	
	// 校验错误回调
	private var onValidationErrorCallback: ((Int, Char) -> Unit)? = null
	// 校验正确回调
	
	private var onValidationSuccessCallback: (() -> Unit)? = null
    private var ttfPath = UTSAndroid.convert2AbsFullPath("static/ttf/TimesNewRoman.ttf")
	

    // 最大宽度（示例值：屏幕宽度的80%）
    private val maxWidthPx by lazy { 
        (context.resources.displayMetrics.widthPixels * 0.8).toInt() 
    }
	
	
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
		
		 private val textWatcher = object : TextWatcher {
		        private var isUpdating = false
		        private var lastText = ""
		        
		        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
		            // 记录变化前的文本
		            if (!isUpdating) {
		                lastText = s?.toString() ?: ""
		            }
		        }
		        
		        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
		            if (isUpdating) return
		            
		            val currentText = s?.toString() ?: ""
		            
		            // 如果启用了校验模式，进行文本校验
		            if (validationMode && !s.isNullOrEmpty()) {
		                val inputText = s.toString()
		                val currentPosition = selectionStart
		                
		                // 检查输入文本长度是否超过目标文本长度
		                if (inputText.length > validationTargetText.length) {
		                    isUpdating = true
		                    // 如果超过，截取与目标文本等长的部分
		                    val truncatedText = inputText.substring(0, validationTargetText.length)
		                    setText(truncatedText)
		                    // 设置光标位置到文本末尾
		                    setSelection(truncatedText.length)
		                    isUpdating = false
		                    return
		                }
		                
		                // 逐字符校验
		                for (i in inputText.indices) {
		                    // 检查是否超出目标文本长度
		                    if (i >= validationTargetText.length) {
		                        isUpdating = true
		                        // 截取到目标文本长度
		                        val truncatedText = inputText.substring(0, validationTargetText.length)
		                        setText(truncatedText)
		                        // 设置光标位置到文本末尾
		                        setSelection(truncatedText.length)
		                        isUpdating = false
		                        return
		                    }
		                    
		                    // 检查字符是否匹配
		                    if (inputText[i] != validationTargetText[i]) {
		                        // 触发错误回调
		                        onValidationErrorCallback?.invoke(i, inputText[i])
		                        
		                        isUpdating = true
		                        // 截取到错误位置前的文本
		                        if (i > 0) {
		                            val validText = inputText.substring(0, i)
		                            setText(validText)
		                            // 设置光标位置到文本末尾
		                            setSelection(validText.length)
		                        } else {
		                            setText("")
		                            // 设置光标位置到文本开始
		                            setSelection(0)
		                        }
		                        isUpdating = false
		                        return
		                    }
		                }
		                onValidationSuccessCallback?.invoke()
		            }
		            
		            // 只有当文本真正发生改变时才触发回调
		            if (currentText != lastText) {
		                onInputCallback?.invoke(currentText)
		            }
		        }
		        
		        override fun afterTextChanged(s: Editable?) {
		            if (!isUpdating) {
		                adjustWidth() // 触发宽度调整
		            }
		        }
		    }
		
    private fun init() {
        // 禁用长按选择
        customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode) {}
        }

        // 设置文本颜色为黑色
        setTextColor(android.graphics.Color.BLACK)

        // 禁用上下文菜单
        isLongClickable = false
        setTextIsSelectable(false)
        
        // 禁止系统输入法弹出
        showSoftInputOnFocus = false
        
        // 设置为单行模式，允许文本水平滚动
        setSingleLine(true)
        setHorizontallyScrolling(true)
        
        // 强制使用英文输入法
        inputType = android.text.InputType.TYPE_CLASS_TEXT or 
                   android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or
                   android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        
        // 应用默认文字大小，指定使用px单位
        setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultTextSize)
        
        // 设置自定义字体
        try {
            val fontFile = File(ttfPath)
            if (fontFile.exists()) {
                val customTypeface = Typeface.createFromFile(fontFile)
                typeface = customTypeface
            } else {
                console.log("字体文件不存在: $ttfPath")
            }
        } catch (e: Exception) {
            console.error("加载字体失败: ${e.message}")
        }

        // 设置文字垂直居中
        gravity = android.view.Gravity.CENTER_VERTICAL or android.view.Gravity.START
        
        // 设置内边距，确保文字不会贴边
        setPadding(paddingLeft, 0, paddingRight, 0)
        
        // 应用默认行高
        setLineSpacing(defaultLineSpacingExtra.dpToPx(), defaultLineSpacingMultiplier)
        
        // 应用底部边框（如果启用）
        updateBottomBorder()
		
		// 初始宽度调整
		adjustWidth()
				
		addTextChangedListener(textWatcher)

        // 添加焦点变化监听
        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                ensureFocusAndCursor()
            }
        }
    }

    // 是否在替换文本后强制获取焦点
	private var forceGetFocusAfterReplace = false
	
	/**
	 * 设置是否在替换文本后强制获取焦点
	 * @param force 是否强制获取焦点
	 */
	fun setForceGetFocusAfterReplace(force: Boolean) {
	    forceGetFocusAfterReplace = force
	}
	
	 
	
	 private fun adjustWidth() {
			return
			if(!isAutoWidth){
				return
			}
	        // 确保 layoutParams 不为 null
	        if (layoutParams == null) {
	            layoutParams = ViewGroup.LayoutParams(
	                ViewGroup.LayoutParams.WRAP_CONTENT,
	                ViewGroup.LayoutParams.WRAP_CONTENT
	            )
	        }

	        // 计算文本宽度（包含padding）
	        val textWidth = paint.measureText(text?.toString() ?: "")
	        // 添加额外的宽度余量，确保文本完全显示
	        val extraSpace = 50.dpToPx() // 增加额外空间以确保文本完全显示
	        val targetWidth = (textWidth + paddingStart + paddingEnd + extraSpace)
	            .coerceAtLeast(minWidthPx)
	            // 移除最大宽度限制，防止文本折行
	            // .coerceAtMost(maxWidthPx.toFloat())

	        // 更新宽度
	        layoutParams = layoutParams?.apply {
	            width = targetWidth.toInt()
	        }
	        requestLayout()
	    }
	
	    // 扩展函数：dp转px
	    private fun Int.dpToPx(): Float {
	        return this * resources.displayMetrics.density
	    }
	    
	    // 扩展函数：Float类型的dp转px
	    private fun Float.dpToPx(): Float {
	        return this * resources.displayMetrics.density
	    }
	    
	    // 扩展函数：rpx转px (基于750px设计稿的响应式单位)
	    private fun Float.rpxToPx(): Float {
	        val screenWidth = resources.displayMetrics.widthPixels
	        return this * screenWidth / 750f
	    }
	    
	    /**
	     * 更新底部边框
	     */
	    private fun updateBottomBorder() {
	        if (showBottomBorder) {
	            // 创建底部边框的drawable
	            val borderDrawable = android.graphics.drawable.ShapeDrawable(android.graphics.drawable.shapes.RectShape())
	            val paint = borderDrawable.paint
	            paint.color = bottomBorderColor
	            paint.style = android.graphics.Paint.Style.STROKE
	            paint.strokeWidth = bottomBorderWidth.dpToPx()
	            
	            // 只在底部显示边框
	            val inset = android.graphics.drawable.InsetDrawable(borderDrawable, 0, -1, 0, 0)
	            background = inset
	        } else {
	            // 如果不显示底部边框，则清除背景
	            background = null
	        }
	    }
	    
	    /**
	     * 设置默认文字大小
	     * @param size 文字大小，单位px
	     */
	    fun setDefaultTextSize(size: Float) {
	        defaultTextSize = size
	        setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
	    }
	    
	    /**
	     * 设置默认文字大小(RPX单位)
	     * @param rpxSize 文字大小，单位rpx (基于750px设计稿)
	     */
	    fun setDefaultTextSizeRpx(rpxSize: Float) {
	        val pxSize = rpxSize.rpxToPx()
	        defaultTextSize = pxSize
	        setTextSize(TypedValue.COMPLEX_UNIT_PX, pxSize)
	    }
	    
	    /**
	     * 设置行高
	     * @param extra 额外的行间距，单位dp
	     * @param multiplier 行高倍数
	     */
	    fun setDefaultLineSpacing(extra: Float, multiplier: Float) {
	        defaultLineSpacingExtra = extra
	        defaultLineSpacingMultiplier = multiplier
	        setLineSpacing(extra.dpToPx(), multiplier)
	    }
	    
	    /**
	     * 设置底部边框
	     * @param show 是否显示底部边框
	     * @param color 边框颜色
	     * @param width 边框宽度，单位dp
	     */
	    fun setBottomBorder(show: Boolean, color: Int = bottomBorderColor, width: Float = bottomBorderWidth) {
	        showBottomBorder = show
	        bottomBorderColor = color
	        bottomBorderWidth = width
	        updateBottomBorder()
	    }



    private var cursorJob: Job? = null

    fun onTapCallback(callback: () -> Unit) {
        onTapCallback = callback
		
    }

    fun startCursorPositionMonitoring(onCursorPositionChanged: (Int) -> Unit) {
		onCursorPositionChangedCallback = onCursorPositionChanged
       
    }
	
	fun onInput(onInput: (String) -> Unit) {
		onInputCallback = onInput
	}

    fun stopCursorPositionMonitoring() {
        cursorJob?.cancel()
    }
	
	fun setAutoWidth(isAuto:Boolean) {
	    isAutoWidth = isAuto
	}
	
	/**
	 * 设置是否屏蔽键盘输入
	 * @param block 是否屏蔽
	 */
	fun setBlockKeyboardInput(block: Boolean) {
	    blockKeyboardInput = block
	}
	
	/**
	 * 设置按键事件回调
	 * @param callback 回调函数，参数为按键对应的文本
	 */
	fun onKeyEvent(callback: (String) -> Unit) {
	    onKeyEventCallback = callback
	}
	
	/**
	 * 设置校验模式
	 * @param enable 是否启用校验模式
	 * @param targetText 校验的目标文本
	 */
	fun setValidationMode(enable: Boolean) {
	    validationMode = enable

	}
	
	/**
	 * 设置校验模式文本
	 * @param enable 是否启用校验模式
	 * @param targetText 校验的目标文本
	 */
	fun setValidationText( targetText: String = "") {
	    validationTargetText = targetText
	}
	
	/**
	 * 设置校验错误回调
	 * @param callback 回调函数，参数为错误位置和错误输入的字符
	 */
	fun onValidationError(callback: (Int, Char) -> Unit) {
	    onValidationErrorCallback = callback
	}
	
	/**
	 * 设置正确回调
	 * @param callback 回调函数，参数为错误位置和错误输入的字符
	 */
	fun onValidationSuccess(callback: () -> Unit) {
	    onValidationSuccessCallback = callback
	}
	
	
	
	
	/**
	 * 程序控制设置文本内容
	 * 此方法不会触发键盘事件处理，但会触发onInputCallback
	 * 只有当新文本与当前文本不同时才会调用setText
	 * @param text 要设置的文本
	 */
	fun setTextProgrammatically(text: String) {
	    // 获取当前文本内容
	    val currentText = getText().toString()
	    
	    // 只有当新文本与当前文本不同时才调用setText
	    if (text != currentText) {
	        // 使用setText方法设置文本
	        setText(text)
	    }
	    // 手动触发onInputCallback
	    // onInputCallback?.invoke(text)
	}

      /**
     * 设置文本垂直对齐方式
     * @param verticalAlignment 垂直对齐方式：
     *                         android.view.Gravity.TOP (顶部对齐)
     *                         android.view.Gravity.CENTER_VERTICAL (垂直居中)
     *                         android.view.Gravity.BOTTOM (底部对齐)
     */
    fun setVerticalTextAlignment(verticalAlignment: Int) {
        // 保持水平对齐方式不变，只改变垂直对齐
        gravity = (gravity and android.view.Gravity.HORIZONTAL_GRAVITY_MASK) or verticalAlignment
        requestLayout()
    }

	
	/**
	 * 设置文本对齐方式
	 * @param alignment 对齐方式：
	 *                  android.view.Gravity.START (左对齐)
	 *                  android.view.Gravity.CENTER_HORIZONTAL (居中对齐)
	 *                  android.view.Gravity.END (右对齐)
	 */
	override fun setTextAlignment(alignment: Int) {
	    textAlignment = alignment
	    gravity = alignment
	    // 请求重新布局以应用更改
	    requestLayout()
	}
	
	   /**
	     * 直接获取焦点的方法
	     */
	    fun requestFocusDirectly() {
	        // 确保View是可见且可获取焦点的
	        isFocusable = true
	        isFocusableInTouchMode = true
	        
	        // 请求焦点
	        requestFocus()
	        // 隐藏系统输入法
	        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	        imm.hideSoftInputFromWindow(windowToken, 0)
	    }
	
	    /**
	     * 清除焦点的方法
	     */
	    fun clearFocusDirectly() {
	        clearFocus()
	        
	    }
		

    override fun onTextContextMenuItem(id: Int): Boolean {
        // 禁止粘贴功能
        return if (id == android.R.id.paste || id == android.R.id.pasteAsPlainText) {
            false
        } else {
            super.onTextContextMenuItem(id)
        }
    }

    override fun isSuggestionsEnabled(): Boolean {
        return false
    }
    
    // 添加一个变量来跟踪上一次的按键事件
    private var lastKeyEvent: KeyEvent? = null
    private var lastKeyEventTime: Long = 0
    private val KEY_EVENT_THRESHOLD = 50L // 50毫秒的事件去重阈值

    private var isProcessingKeyEvent = false
    private var lastProcessedEvent: KeyEvent? = null
    private var lastProcessedTime = 0L
    private var lastEventSequence = 0L
    
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val currentTime = System.currentTimeMillis()
        
        // 检查是否是重复事件
        if (lastKeyEvent != null && 
            lastKeyEvent?.keyCode == event.keyCode && 
            lastKeyEvent?.action == event.action &&
            currentTime - lastKeyEventTime < KEY_EVENT_THRESHOLD) {
            console.log("Duplicate key event detected, ignoring")
            return true
        }
        
        // 检查是否是刚刚处理过的事件
        if (lastProcessedEvent != null &&
            lastProcessedEvent?.keyCode == event.keyCode &&
            lastProcessedEvent?.action == event.action &&
            currentTime - lastProcessedTime < 100) { // 100ms内认为是同一个事件
            // console.log("Recently processed event, ignoring")
            return true
        }
        
        // // 检查事件序列号
        val eventSequence = event.eventTime
        // if (eventSequence <= lastEventSequence) {
        //     console.log("Out of order event detected, ignoring")
        //     return true
        // }
        
        // 如果正在处理事件，直接返回父类处理结果
        if (isProcessingKeyEvent) {
            console.log("正在处理事件，跳过")
            return super.dispatchKeyEvent(event)
        }
        
        // 更新最后的事件信息
        lastKeyEvent = event
        lastKeyEventTime = currentTime
        lastEventSequence = eventSequence
        
        // 如果是按键按下事件
        if (event.action == KeyEvent.ACTION_DOWN) {
            // 只处理按键输入，不处理系统功能键如音量键等
            if (event.keyCode in KeyEvent.KEYCODE_A..KeyEvent.KEYCODE_Z ||
                event.keyCode in KeyEvent.KEYCODE_0..KeyEvent.KEYCODE_9 ||
                event.keyCode == KeyEvent.KEYCODE_SPACE ||
                event.keyCode == KeyEvent.KEYCODE_DEL ||
                event.keyCode == KeyEvent.KEYCODE_ENTER ||
                event.keyCode == KeyEvent.KEYCODE_TAB ||
                event.keyCode >= KeyEvent.KEYCODE_COMMA && event.keyCode <= KeyEvent.KEYCODE_SLASH) {
                
                // 将键码转换为对应的文本
                val keyText = when (event.keyCode) {
                    KeyEvent.KEYCODE_DEL -> "BACKSPACE"
                    KeyEvent.KEYCODE_ENTER -> "ENTER"
                    KeyEvent.KEYCODE_TAB -> "TAB"
                    KeyEvent.KEYCODE_SPACE -> " "
                    else -> event.unicodeChar.toChar().toString()
                }
                
                // 通过回调函数传递按键文本
                onKeyEventCallback?.invoke(keyText)
                
                // 如果启用了校验模式，进行校验处理
                if (validationMode) {
                    // 处理删除键的校验逻辑
                    if (keyText == "BACKSPACE") {
                        // 允许删除操作，但在删除后检查是否变为空
                        val result = super.dispatchKeyEvent(event)
                        
                        // 删除操作执行后，检查文本是否变为空
                        post {
                            val currentText = text?.toString() ?: ""
                            if (currentText.isEmpty()) {
                                // 文字彻底变成空时触发错误回调
                                onValidationErrorCallback?.invoke(0, ' ') // 位置0，字符用空格表示删除操作
                            }
                        }
                        
                        return result
                    }
                    
                    // 处理字符输入的校验逻辑
                     if (keyText.length == 1) {
                         val currentText = text?.toString() ?: ""
                         val currentPosition = currentText.length
                         
                         // 检查是否超出目标文本长度
                         if (currentPosition >= validationTargetText.length || validationTargetText.isEmpty()) {
                             return true // 不允许输入超出目标文本长度的字符或目标文本为空
                         }
                         
                         // 检查输入字符是否与目标文本对应位置的字符匹配
                         val targetChar = validationTargetText[currentPosition]
                         val inputChar = keyText[0]
                         
                         // 确保即使文本为空（currentPosition=0），第一个字符也必须匹配
                         if (inputChar != targetChar) {
                             // 输入错误，触发错误回调
                             onValidationErrorCallback?.invoke(currentPosition, inputChar)
                             return true // 拦截错误输入
                         }
                         
                         onValidationSuccessCallback?.invoke()
                     }
                 }
                
                // 如果设置了屏蔽键盘输入，则拦截事件
                if (blockKeyboardInput) {
                    return true // 消费事件，不再传递
                }
            }
        }
        
        // 如果设置了屏蔽键盘输入，则拦截所有按键事件
        if (blockKeyboardInput) {
            return true
        }
        
        // 在调用父类方法之前保存当前光标位置
        val currentPosition = selectionStart
        val textLength = text?.length ?: 0
        val currentText = text?.toString() ?: ""
        
      
        
        // 标记开始处理事件
        isProcessingKeyEvent = true
        
        // 先调用父类方法处理事件
        val result = super.dispatchKeyEvent(event)
        
        // 使用Handler确保在主线程上执行，并延迟一点时间
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            try {
                if (forceGetFocusAfterReplace) {
                    requestFocusDirectly()
                }
                // 确保输入法不会弹出
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken, 0)
                
                // 如果是输入字符，根据之前保存的光标位置计算新的光标位置
                if (event.action == KeyEvent.ACTION_DOWN && 
                    (event.keyCode in KeyEvent.KEYCODE_A..KeyEvent.KEYCODE_Z ||
                     event.keyCode in KeyEvent.KEYCODE_0..KeyEvent.KEYCODE_9 ||
                     event.keyCode == KeyEvent.KEYCODE_SPACE ||
                     event.keyCode >= KeyEvent.KEYCODE_COMMA && event.keyCode <= KeyEvent.KEYCODE_SLASH)) {
                    
                    val newTextLength = text?.length ?: 0
                    val newText = text?.toString() ?: ""
                    
                    
                    // 计算新的光标位置
                    val newPosition = when {
                        // 如果是删除键，向前移动一位
                        event.keyCode == KeyEvent.KEYCODE_DEL -> {
                            maxOf(0, currentPosition - 1)
                        }
                        // 如果之前的光标位置在文本末尾，移动到新的末尾
                        currentPosition >= textLength -> {
                            newTextLength
                        }
                        // 如果之前的光标位置在文本中间，移动到下一个位置
                        currentPosition > 0 -> {
                            minOf(currentPosition + 1, newTextLength)
                        }
                        // 如果之前的光标位置在开始，移动到第一个字符后
                        else -> {
                            minOf(1, newTextLength)
                        }
                    }
                    
                    
                    if (newTextLength > 0) {
                        try {
                            setSelection(newPosition)
                        } catch (e: Exception) {
                            // 如果设置失败，将光标设置到文本末尾
                            setSelection(newTextLength)
                        }
                    }
                }
                
                // 更新最后处理的事件信息
                lastProcessedEvent = event
                lastProcessedTime = System.currentTimeMillis()
            } finally {
                // 确保在任何情况下都重置标志
                isProcessingKeyEvent = false
            }
        }, 50) // 50毫秒的延迟
        
        return result
    }

    // 添加onKeyDown方法来处理按键按下事件
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // 如果设置了屏蔽键盘输入，则拦截事件
        if (blockKeyboardInput) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    // 添加onKeyUp方法来处理按键释放事件
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        // 如果设置了屏蔽键盘输入，则拦截事件
        if (blockKeyboardInput) {
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    // 重写onTouchEvent以监听点击事件
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            // 处理点击事件
            // 例如，打印日志或执行其他操作
			 // 获取触摸点相对于屏幕的坐标
			                val screenX = event.rawX
			                val screenY = event.rawY
			                // 获取控件本身在屏幕中的位置
			                val viewPosition = IntArray(2).apply { getLocationOnScreen(this) }
			                
			                // 获取屏幕高度
			                val screenHeight = resources.displayMetrics.heightPixels
			                    
			                // 调用位置和尺寸回调
			                onPositionSizeCallback?.invoke(viewPosition[0].toFloat(), viewPosition[1].toFloat(), width, height, screenHeight)
			                
			onTapCallback?.invoke()
             if (!isFocused) {
                requestFocus()
                // 隐藏系统输入法
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken, 0)
            }
        }
        return super.onTouchEvent(event)
    }
    
    // 使用ViewTreeObserver监听全局布局变化
    private var viewTreeObserver: ViewTreeObserver? = null
    private val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        // 确保系统输入法不会弹出
        post {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }
    }
    
    // 在View附加到窗口时添加监听器
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver = getViewTreeObserver()
        viewTreeObserver?.addOnGlobalLayoutListener(globalLayoutListener)
    }
    
    // 在View从窗口分离时移除监听器
    override fun onDetachedFromWindow() {
        viewTreeObserver?.removeOnGlobalLayoutListener(globalLayoutListener)
        viewTreeObserver = null
        super.onDetachedFromWindow()
    }
    
    // 处理窗口焦点变化
    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus && isFocused) {
            // 当窗口重新获得焦点且输入框有焦点时，确保系统输入法不会弹出
            post {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken, 0)
            }
        }
    }

  //   // 重写onFocusChanged以监听焦点变化事件
  //   override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
  //       super.onFocusChanged(focused, direction, previouslyFocusedRect)
  //       if (focused) {
  //           // 调用传入的回调函数
		// 	cursorJob = CoroutineScope(Dispatchers.Main).launch {
		// 	    while (isActive) {
		// 	        val currentCursorPosition = selectionStart
		// 			onCursorPositionChangedCallback?.invoke(currentCursorPosition)
		// 	        delay(20) // 每500毫秒获取一次光标位置
		// 	    }
		// 	}
			
		// 	// console.log("输入框焦点")
  //       }
		// else{
		// 	stopCursorPositionMonitoring()
		// }
  //   }

    // 添加一个变量来跟踪上一次的光标位置
    private var lastCursorPosition = -1

     override fun setText(text: CharSequence?, type: BufferType?) {
       
        
        val isFocused = hasFocus()
        
        // 设置文本
        super.setText(text, type)
        
        // 如果之前有焦点，保持焦点
        if (isFocused) {
            requestFocus()
        }
    }
    
    /**
     * 自定义文本替换方法，在校验模式下拦截和验证文本修改
     * 注意：此方法不是重写，而是提供一个自定义的替换功能
     */
    fun replaceText(start: Int, end: Int, newText: CharSequence) {
        
        // 如果启用了校验模式，进行文本校验
        if (validationMode) {
            // 获取当前文本
            val currentText = this.text?.toString() ?: ""
            
            // 计算替换后的新文本
            val beforeReplace = currentText.substring(0, start)
            val afterReplace = if (end < currentText.length) currentText.substring(end) else ""
            val resultText = beforeReplace + newText + afterReplace
            
            // 检查新文本长度是否超过目标文本长度
            if (resultText.length > validationTargetText.length) {
                console.log("校验模式 - 文本长度超出限制")
                // 如果超过，不执行替换操作
                onValidationErrorCallback?.invoke(start, newText.toString().firstOrNull() ?: ' ')
                return
            }
            
            // 逐字符校验新文本
            for (i in resultText.indices) {
                // 检查是否超出目标文本长度
                if (i >= validationTargetText.length) {
                    // console.log("校验模式 - 超出目标文本长度")
                    // 不执行替换操作
                    return
                }
                
                // 检查字符是否匹配
                if (resultText[i] != validationTargetText[i]) {
                    // console.log("校验模式 - 字符不匹配: 位置=$i, 输入='${resultText[i]}', 目标='${validationTargetText[i]}'")
                    // 触发错误回调
                    onValidationErrorCallback?.invoke(i, resultText[i])
                    return
                }
            }
            // console.log("校验模式 - 校验通过")
            onValidationSuccessCallback?.invoke()
        }
        
        // 保存当前光标位置
        val currentPosition = selectionStart
        
        // 通过校验或未启用校验模式，正常执行替换操作
        val currentText = this.text?.toString() ?: ""
        val newText = if (start == end) {
            // 如果是插入操作，在当前位置插入文本
            val before = currentText.substring(0, currentPosition)
            val after = currentText.substring(currentPosition)
            before + newText + after
        } else {
            // 如果是替换操作，替换指定范围的文本
            currentText.replaceRange(start, end, newText)
        }
        setText(newText)
        
        // 计算新的光标位置
        val newPosition = when {
            // 如果是插入操作（start == end），移动到插入文本的末尾
            start == end -> {
                minOf(currentPosition + 1, newText.length)
            }
            // 如果是替换操作，移动到替换文本的末尾
            else -> {
                minOf(start + 1, newText.length)
            }
        }
        
        // 使用post确保在UI线程上执行光标设置
        post {
            try {
                // 设置新的光标位置
                setSelection(newPosition)
                
                if (forceGetFocusAfterReplace) {
                    console.log("强制获取焦点")
                    requestFocusDirectly()
                }
            } catch (e: Exception) {
                // 如果设置失败，尝试将光标设置到文本末尾
                try {
                    setSelection(newText.length)
                } catch (e: Exception) {
                    console.log("设置光标到文本末尾也失败: ${e.message}")
                }
            }
        }
        
        // 调整宽度
        adjustWidth()
    }
    
    /**
     * 添加TextWatcher来拦截文本变化
     * 这是一个更好的方式来处理文本验证，而不是尝试重写replace方法
     */
    private fun setupTextValidation() {
        // 可以在这里添加额外的TextWatcher来实现文本验证
        // 当前已经在textWatcher中实现了onInputCallback的调用
    }

    /**
     * 根据父容器高度调整控件，使文本垂直居中
     */
    fun adjustHeightToParent() {
        post {
            val parent = parent as? ViewGroup ?: return@post
            val parentHeight = parent.height
            
            // 设置控件高度与父容器相同
            layoutParams = layoutParams.apply {
                height = parentHeight
            }
            
            // 确保文本垂直居中
            gravity = (gravity and android.view.Gravity.HORIZONTAL_GRAVITY_MASK) or 
                     android.view.Gravity.CENTER_VERTICAL
            
            requestLayout()
        }
    }

    // 添加位置和尺寸回调函数
    fun onPositionSize(callback: (Float, Float, Int, Int, Int) -> Unit) {
        onPositionSizeCallback = callback
    }

    // 重写onCheckIsTextEditor方法来控制文本编辑
    override fun onCheckIsTextEditor(): Boolean {
        return !blockKeyboardInput // 如果屏蔽键盘输入，则不允许文本编辑
    }

    // 添加一个方法来确保焦点和光标
    private fun ensureFocusAndCursor() {
        // if (hasFocus()) {
        //     requestFocus()
        //     // 确保光标位置正确
        //     val text = this.text?.toString() ?: ""
        //     setSelection(text.length)
        // }
    }
}