package uts.sdk.modules.wjScroll

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import io.dcloud.uts.console

class ScrollTextView : View {
    
    // 滚动方向枚举
    enum class ScrollDirection {
        HORIZONTAL, VERTICAL
    }
    
    // 基本属性
    private var text: String = ""
    private var textColor: Int = Color.BLACK
    private var scrollSpeed: Float = 50f // 像素/秒
    private var scrollDirection: ScrollDirection = ScrollDirection.HORIZONTAL
    private var isLoop: Boolean = true
    private var isAutoStart: Boolean = true
    
    // 绘制相关
    private val textPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f
    
    // 滚动相关
    private var scrollAnimator: ValueAnimator? = null
    private var currentScrollX: Float = 0f
    private var currentScrollY: Float = 0f
    private var isScrolling: Boolean = false
    private var isPaused: Boolean = false
    
    // 回调函数
    private var onScrollStartCallback: (() -> Unit)? = null
    private var onScrollEndCallback: (() -> Unit)? = null
    private var onScrollPauseCallback: (() -> Unit)? = null
    private var onScrollResumeCallback: (() -> Unit)? = null
    
    constructor(context: Context) : super(context) {
        init()
    }
    
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
    
    private fun init() {
        // 初始化画笔
        textPaint.textSize = 48f
        textPaint.color = textColor
        textPaint.isAntiAlias = true
        
        // 启用硬件加速
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }
    
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (isAutoStart && !text.isEmpty()) {
            startScroll()
        }
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        if (text.isEmpty()) return
        
        // 计算文本尺寸
        textWidth = textPaint.measureText(text)
        val fontMetrics = textPaint.fontMetrics
        textHeight = fontMetrics.bottom - fontMetrics.top
        
        
        when (scrollDirection) {
            ScrollDirection.HORIZONTAL -> drawHorizontalScroll(canvas)
            ScrollDirection.VERTICAL -> drawVerticalScroll(canvas)
        }
    }
    
    private fun drawHorizontalScroll(canvas: Canvas) {
        val y = height / 2f - (textPaint.fontMetrics.top + textPaint.fontMetrics.bottom) / 2f
        
        // 如果文字宽度小于等于视图宽度，直接显示在最左边，不滚动
        if (textWidth <= width) {
            canvas.drawText(text, 0f, y, textPaint)
            return
        }
        
        
        if (isLoop) {
            // 循环滚动模式
            val x1 = -currentScrollX
            val x2 = x1 + textWidth + 100f // 添加间距
            
            
            canvas.drawText(text, x1, y, textPaint)
            if (x1 + textWidth < width) {
                canvas.drawText(text, x2, y, textPaint)
            }
        } else {
            // 普通滚动模式
            val x = -currentScrollX
            canvas.drawText(text, x, y, textPaint)
        }
    }
    
    private fun drawVerticalScroll(canvas: Canvas) {
        val x = (width - textWidth) / 2f
        
        // 如果文字高度小于等于视图高度，直接显示在顶部，不滚动
        if (textHeight <= height) {
            val y = textHeight - textPaint.fontMetrics.bottom
            canvas.drawText(text, x, y, textPaint)
            return
        }
        
        if (isLoop) {
            // 循环滚动模式
            val y1 = textHeight - currentScrollY
            val y2 = y1 + textHeight + 50f // 添加间距
            
            canvas.drawText(text, x, y1, textPaint)
            if (y1 - textHeight > 0) {
                canvas.drawText(text, x, y2, textPaint)
            }
        } else {
            // 普通滚动模式
            val y = height - currentScrollY
            canvas.drawText(text, x, y, textPaint)
        }
    }
    
    // 设置文本
    fun setText(newText: String): ScrollTextView {
        this.text = newText
        resetScroll()
        invalidate()
        return this
    }
    
    // 设置字体大小
    fun setFontSize(size: Float): ScrollTextView {
        textPaint.textSize = size
        invalidate()
        return this
    }
    
    // 设置文字颜色
    fun setTextColor(color: String): ScrollTextView {
        try {
            this.textColor = Color.parseColor(color)
            textPaint.color = this.textColor
            invalidate()
        } catch (e: Exception) {
            console.error("无效的颜色格式: $color")
        }
        return this
    }
    
    // 设置滚动速度
    fun setScrollSpeed(speed: Float): ScrollTextView {
        this.scrollSpeed = speed
        if (isScrolling) {
            // 如果正在滚动，重新开始以应用新速度
            stopScroll()
            startScroll()
        }
        return this
    }
    
    // 设置滚动方向
    fun setScrollDirection(direction: String): ScrollTextView {
        this.scrollDirection = when (direction.lowercase()) {
            "vertical" -> ScrollDirection.VERTICAL
            else -> ScrollDirection.HORIZONTAL
        }
        resetScroll()
        invalidate()
        return this
    }
    
    // 设置是否循环
    fun setLoop(loop: Boolean): ScrollTextView {
        this.isLoop = loop
        return this
    }
    
    // 设置自动开始
    fun setAutoStart(autoStart: Boolean): ScrollTextView {
        this.isAutoStart = autoStart
        return this
    }
    
    // 开始滚动
    fun startScroll(): ScrollTextView {
        
        if (text.isEmpty() || width == 0 || height == 0) {
            return this
        }
        
        // 重新计算文本尺寸
        textWidth = textPaint.measureText(text)
        val fontMetrics = textPaint.fontMetrics
        textHeight = fontMetrics.bottom - fontMetrics.top
        
        
        // 检查是否需要滚动
        val needScroll = when (scrollDirection) {
            ScrollDirection.HORIZONTAL -> textWidth > width
            ScrollDirection.VERTICAL -> textHeight > height
        }
        
        
        if (!needScroll) {
            // 如果不需要滚动，直接返回
            invalidate()
            return this
        }
        
        stopScroll() // 先停止之前的动画
        
        val duration = when (scrollDirection) {
            ScrollDirection.HORIZONTAL -> {
                val distance = if (isLoop) textWidth + 100f else textWidth
                (distance / scrollSpeed * 1000).toLong()
            }
            ScrollDirection.VERTICAL -> {
                val distance = if (isLoop) textHeight + 50f else textHeight
                (distance / scrollSpeed * 1000).toLong()
            }
        }
        
        
        scrollAnimator = when (scrollDirection) {
            ScrollDirection.HORIZONTAL -> {
                ValueAnimator.ofFloat(0f, if (isLoop) textWidth + 100f else textWidth)
            }
            ScrollDirection.VERTICAL -> {
                ValueAnimator.ofFloat(0f, if (isLoop) textHeight + 50f else textHeight)
            }
        }.apply {
            setDuration(duration)
            interpolator = LinearInterpolator()
            repeatCount = if (isLoop) ValueAnimator.INFINITE else 0
            repeatMode = ValueAnimator.RESTART
            
            addUpdateListener { animator ->
                val animatedValue = animator.animatedValue as Float
                when (scrollDirection) {
                    ScrollDirection.HORIZONTAL -> {
                        currentScrollX = animatedValue
                    }
                    ScrollDirection.VERTICAL -> {
                        currentScrollY = animatedValue
                    }
                }
                invalidate()
            }
            
            addListener(object : android.animation.AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: android.animation.Animator) {
                    this@ScrollTextView.isScrolling = true
                    this@ScrollTextView.isPaused = false
                    onScrollStartCallback?.invoke()
                }
                
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    if (!this@ScrollTextView.isPaused) {
                        this@ScrollTextView.isScrolling = false
                        onScrollEndCallback?.invoke()
                    }
                }
            })
            
            start()
        }
        
        return this
    }
    
    // 暂停滚动
    fun pauseScroll(): ScrollTextView {
        scrollAnimator?.pause()
        isPaused = true
        onScrollPauseCallback?.invoke()
        return this
    }
    
    // 恢复滚动
    fun resumeScroll(): ScrollTextView {
        scrollAnimator?.resume()
        isPaused = false
        onScrollResumeCallback?.invoke()
        return this
    }
    
    // 停止滚动
    fun stopScroll(): ScrollTextView {
        scrollAnimator?.cancel()
        scrollAnimator = null
        isScrolling = false
        isPaused = false
        return this
    }
    
    // 重置滚动位置
    fun resetScroll(): ScrollTextView {
        stopScroll()
        currentScrollX = 0f
        currentScrollY = 0f
        invalidate()
        return this
    }
    
    // 设置回调函数
    fun setOnScrollStartCallback(callback: (() -> Unit)?): ScrollTextView {
        this.onScrollStartCallback = callback
        return this
    }
    
    fun setOnScrollEndCallback(callback: (() -> Unit)?): ScrollTextView {
        this.onScrollEndCallback = callback
        return this
    }
    
    fun setOnScrollPauseCallback(callback: (() -> Unit)?): ScrollTextView {
        this.onScrollPauseCallback = callback
        return this
    }
    
    fun setOnScrollResumeCallback(callback: (() -> Unit)?): ScrollTextView {
        this.onScrollResumeCallback = callback
        return this
    }
    
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopScroll()
    }
}