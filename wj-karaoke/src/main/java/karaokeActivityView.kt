package uts.sdk.modules.wjKaraoke

import android.animation.ValueAnimator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Region
import android.media.MediaPlayer
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import io.dcloud.uts.console
import org.json.JSONArray




class KaraokeTextView : View {
    // 添加硬件加速和双缓冲支持
    init {
        // 启用硬件加速
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }
    
    private var playedColor: Int = Color.BLUE
    private var unplayedColor: Int = Color.GRAY
    private var currentProgress: Float = 0f
    private var lyrics: String = ""
    
    // 添加字符颜色映射，用于存储每个字符的自定义颜色
    private val charColors = mutableMapOf<Int, Int>()
    // 添加基于歌词项的字符颜色映射，用于多句子场景
    private val lyricCharColors = mutableMapOf<Pair<Int, Int>, Int>()
    private val textPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    
    // 添加MediaPlayer相关属性
    private var mediaPlayer: MediaPlayer? = null
    private var currentPlayingSong: String? = null
       // 在类的开始处添加枚举定义
    enum class TextAlignment {
        LEFT, CENTER, RIGHT
    }
    
    // 添加对齐方式属性
    private var textAlignment: TextAlignment = TextAlignment.CENTER
    
    // 添加控制多句展示形式的变量
    private var isForceLineBreak: Boolean = true
    
    // 添加滚动相关属性
    private var scrollAnimator: ValueAnimator? = null
    private var resumeScrollRunnable: Runnable? = null
    private val RESUME_SCROLL_DELAY = 2000L // 2秒延迟
    private var touchSlop = 0
    private var isScrollingVertically = false
    private var lastTouchY = 0f
    private var initialTouchY = 0f
    private var lastTouchX = 0f
    private var initialTouchX = 0f
    private var autoScrollEnabled = true
    private var textHeight: Float = 0f
    private var targetScrollY: Float = 0f
    private var delayBetweenLyrics: Long = 1000L // 添加延迟时间变量，单位毫秒
    private var onLyricPlayedCallback: ((Int, String) -> Unit)? = null // 添加播放完成的回调函数
    private var onPlaybackStateChangedCallback: ((Boolean, Int, String, Boolean) -> Unit)? = null // 添加播放状态变化的回调函数，第四个参数表示是否强制跳转
    private var onPlayEndCallback: (() -> Unit)? = null // 添加播放结束的回调函数
    
    // 添加状态管理相关的变量
    private var isPaused: Boolean = false
    private var pauseTimestamp: Long = 0L // 暂停时间戳，用于防止过期的延时任务执行
    private var currentLyricIndex: Int = 0
    private var savedAnimationProgress: Float = 0f
    private var savedLyricIndex: Int = 0
    private var savedMediaPosition: Int = 0
    
    // 用于管理异步任务的Runnable对象
    private var nextLyricRunnable: Runnable? = null
    
    // 添加控制是否预留评分空间的变量
    private var shouldReserveScoreSpace: Boolean = true
    
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
        textPaint.textSize = 40f
        textPaint.color = unplayedColor
        
        // 初始化触摸阈值
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }
    
    // 歌词项数据类
    data class LyricItem(
        val text: String,
        val time: Long,  // 毫秒
        val song: String
    )
    
    // 存储歌词项列表
    private var lyricItems: List<LyricItem> = listOf()
    private var isAnimationFinished = true
    private var isDragging = false
    private var scrollY = 0f
    private var text: String = ""
    private var isTextLayoutLocked = false
    private val actualItemDurations = mutableMapOf<Int, Long>()
    
    // 添加布局缓存，避免在onDraw中重复创建
    private var cachedLayout: StaticLayout? = null
    private var lastAvailableWidth = 0
    private var lastTextWidth = 0f
    
    // 存储每个歌词项的位置和宽度信息
    private data class LyricPosition(
        val startX: Float,
        val width: Float
    )
    private val lyricPositions = mutableListOf<LyricPosition>()
    
    // 选中歌词相关状态
    private var selectedLyricIndex = -1  // 当前选中的歌词索引
    
    // 弹窗相关属性
    private var isPopupShowing = false
    private var customPopupView: View? = null
    
    // 存储评分信息的数据类
    private data class ScoreInfo(
        val score: String,
        val color: Int
    )
    // 存储每个歌词项的评分信息
    private val lyricScores = mutableMapOf<Int, ScoreInfo>()
    
    // 设置特定位置字符的颜色
    fun setCharColor(position: Int, color: Int): KaraokeTextView {
        charColors[position] = color
        invalidate()
        return this
    }
    
    // 设置特定位置字符的颜色（使用颜色字符串）
    fun setCharColor(position: Int, colorString: String): KaraokeTextView {
        try {
            val color = Color.parseColor(colorString)
            return setCharColor(position, color)
        } catch (e: Exception) {
            console.error("无效的颜色格式: $colorString")
            return this
        }
    }
    
    // 设置多个字符的颜色
    fun setCharsColor(startPosition: Int, endPosition: Int, color: Int): KaraokeTextView {
        for (i in startPosition until endPosition) {
            charColors[i] = color
        }
        invalidate()
        return this
    }
    
    // 设置多个字符的颜色（使用颜色字符串）
    fun setCharsColor(startPosition: Int, endPosition: Int, colorString: String): KaraokeTextView {
        try {
            val color = Color.parseColor(colorString)
            return setCharsColor(startPosition, endPosition, color)
        } catch (e: Exception) {
            console.error("无效的颜色格式: $colorString")
            return this
        }
    }
    
    // 清除所有自定义字符颜色
    fun clearCharColors(): KaraokeTextView {
        charColors.clear()
        lyricCharColors.clear()
        invalidate()
        return this
    }
    
    // 设置评分的方法
    fun setLyricScore(lyricIndex: Int, score: String, colorString: String): KaraokeTextView {
        try {
            val color = Color.parseColor(colorString)
            return setLyricScore(lyricIndex, score, color)
        } catch (e: Exception) {
            console.error("无效的颜色格式: $colorString")
            return this
        }
    }
    
    // 设置评分的方法（使用颜色整数值）
    fun setLyricScore(lyricIndex: Int, score: String, color: Int): KaraokeTextView {
        // 确保歌词索引有效
        if (lyricIndex < 0 || lyricIndex >= lyricItems.size) {
            console.error("无效的歌词索引: $lyricIndex")
            return this
        }
        
        // 存储评分信息
        lyricScores[lyricIndex] = ScoreInfo(score, color)
        
        // 重置布局缓存，因为评分可能影响布局
        cachedLayout = null
        lastAvailableWidth = 0
        lastTextWidth = 0f
        
        // 重新计算布局和位置
        if (width > 0 && !lyrics.isEmpty()) {
            val availableWidth = if (maxWidth > 0) minOf(maxWidth, width) else width
            val textWidth = textPaint.measureText(lyrics)
            val x = when (textAlignment) {
                TextAlignment.LEFT -> 0f
                TextAlignment.RIGHT -> width - textWidth
                else -> (width - textWidth) / 2 // CENTER
            }
            calculateLyricPositions(x)
            
            // 更新滚动位置
            updateScrollPosition()
        }
        
        // 强制重绘
        invalidate()
        return this
    }
    
    // 清除所有评分
    fun clearLyricScores(): KaraokeTextView {
        lyricScores.clear()
        invalidate()
        return this
    }
    
    // 设置特定歌词项中特定位置字符的颜色
    fun setCharColorInLyric(lyricIndex: Int, charPosition: Int, color: Int): KaraokeTextView {
        lyricCharColors[Pair(lyricIndex, charPosition)] = color
        invalidate()
        return this
    }
    
    // 设置特定歌词项中特定位置字符的颜色（使用颜色字符串）
    fun setCharColorInLyric(lyricIndex: Int, charPosition: Int, colorString: String): KaraokeTextView {
        try {
            val color = Color.parseColor(colorString)
            return setCharColorInLyric(lyricIndex, charPosition, color)
        } catch (e: Exception) {
            console.error("无效的颜色格式: $colorString")
            return this
        }
    }
    
    // 设置特定歌词项中多个字符的颜色
    fun setCharsColorInLyric(lyricIndex: Int, startPosition: Int, endPosition: Int, color: Int): KaraokeTextView {
        // 清除该范围内已有的颜色设置
        for (i in startPosition until endPosition) {
            lyricCharColors.remove(Pair(lyricIndex, i))
        }
        
        // 设置新的颜色
        for (i in startPosition until endPosition) {
            lyricCharColors[Pair(lyricIndex, i)] = color
        }
        
        // 强制更新视图
        post {
            invalidate()
            requestLayout()
        }
        return this
    }
    
    // 设置特定歌词项中多个字符的颜色（使用颜色字符串）
    fun setCharsColorInLyric(lyricIndex: Int, startPosition: Int, endPosition: Int, colorString: String): KaraokeTextView {
        try {
            val color = Color.parseColor(colorString)
            return setCharsColorInLyric(lyricIndex, startPosition, endPosition, color)
        } catch (e: Exception) {
            console.error("无效的颜色格式: $colorString")
            return this
        }
    }
    
    // 设置歌词数据
    fun setKaraokeLyrics(jsonString: String): KaraokeTextView {
        // 重置布局缓存
        cachedLayout = null
        lastAvailableWidth = 0
        lastTextWidth = 0f
        try {
            // 停止当前所有动画和播放
            if (lyricAnimator != null) {
                lyricAnimator?.cancel()
                lyricAnimator = null
            }
            
            // 释放MediaPlayer资源
            releaseMediaPlayer()
            
            // 通知播放状态变化：停止播放
            onPlaybackStateChangedCallback?.invoke(false, -1, "", false)
            
            // 重置所有状态
            currentProgress = 0f
            scrollY = 0f
            currentLyricIndex = 0
            isDragging = false
            isPaused = false
            isAnimationFinished = true
            
            // 清空实际时长缓存和自定义字符颜色
            actualItemDurations.clear()
            lyricPositions.clear()
            charColors.clear()
            lyricCharColors.clear()
            
            // 清除得分数据
            lyricScores.clear()
            
            // 解析新的歌词数据
            val jsonArray = JSONArray(jsonString)
            val items = mutableListOf<LyricItem>()
            val fullText = StringBuilder()
            
            console.log("[DEBUG] 开始解析歌词，总数量: ${jsonArray.length()}")
            console.log("[DEBUG] isForceLineBreak: $isForceLineBreak")
            
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val text = obj.getString("text")
                val time = obj.getLong("time")
                val song = obj.getString("song")
                
                console.log("[DEBUG] 歌词[$i]: text='$text', time=$time, song='$song'")
                
                if (i > 0) {
                    // 根据isForceLineBreak决定使用换行符还是空格
                    if (isForceLineBreak) {
                        fullText.append("\n")
                        console.log("[DEBUG] 添加换行符")
                    } else {
                        fullText.append(" ")
                        console.log("[DEBUG] 添加空格")
                    }
                }
                fullText.append(text)
                items.add(LyricItem(text, time, song))
            }
            
            // 设置新数据
            this.lyricItems = items
            this.text = fullText.toString()
            this.lyrics = fullText.toString()
            
            console.log("[DEBUG] 最终文本长度: ${this.text.length}")
            console.log("[DEBUG] 最终文本内容: '${this.text}'")
            console.log("[DEBUG] 歌词项数量: ${this.lyricItems.size}")

            // 更新视图
            invalidate()
            
        } catch (e: Exception) {
            console.error("解析歌词JSON失败: ${e.message}")
            this.text = "歌词解析错误"
            this.lyrics = "歌词解析错误"
            this.lyricItems = listOf()
            invalidate()
        }
        
        return this
    }
    
    fun setPlayedColor(color: Int) {
        playedColor = color
        invalidate()
    }
    
    // isClickOnStartButton 和 getStartButtonRect 方法已移除，现在使用弹窗代替按钮
    
    // 获取选中歌词的Y坐标
    private fun getSelectedLyricY(): Float {
        if (selectedLyricIndex == -1) return 0f
        
        // 根据当前显示模式计算Y坐标
        val availableWidth = width - paddingLeft - paddingRight
        val textWidth = textPaint.measureText(lyrics)
        
        return if (textWidth <= availableWidth) {
            // 单行模式
            height / 2f
        } else {
            // 多行模式 - 需要计算具体行的Y坐标
            val layout = cachedLayout ?: return 0f
            val lineIndex = getLineIndexForLyric(selectedLyricIndex)
            
            // 获取行的基线位置
            val lineTop = layout.getLineTop(lineIndex).toFloat()
            val lineBottom = layout.getLineBottom(lineIndex).toFloat()
            val lineCenter = (lineTop + lineBottom) / 2f
            
            // 考虑滚动偏移量，确保按钮在可视区域内
            val absoluteY = paddingTop + lineCenter - scrollY
            // console.log("getSelectedLyricY: 行$lineIndex lineTop=$lineTop lineBottom=$lineBottom lineCenter=$lineCenter scrollY=$scrollY absoluteY=$absoluteY")
            
            return absoluteY
        }
    }
    
    // 获取歌词所在的行索引
    private fun getLineIndexForLyric(lyricIndex: Int): Int {
        if (lyricIndex < 0 || lyricIndex >= lyricItems.size) return 0
        
        val layout = cachedLayout ?: return 0
        
        // 计算选中歌词在完整文本中的字符位置
        var charIndex = 0
        for (i in 0 until lyricIndex) {
            charIndex += lyricItems[i].text.length + 1 // +1 是分隔符
        }
        
        // 获取该字符位置所在的行
        val lineIndex = layout.getLineForOffset(charIndex)
        // console.log("getLineIndexForLyric: 歌词$lyricIndex 字符位置=$charIndex 所在行=$lineIndex")
        return lineIndex
    }
    
    // 绘制"从此处开始"按钮
    // drawStartButton 方法已移除，现在使用弹窗代替按钮
    
    // 添加 dpToPx 方法
    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
    
    // 显示开始按钮弹窗
    // 显示开始按钮弹窗
    private fun showStartButtonDialog() {
        try {
            // 添加触摸位置调试信息
            console.log("=== 自定义弹窗位置调试信息 ===")
            console.log("触摸位置: initialTouchX=$initialTouchX, initialTouchY=$initialTouchY")
            console.log("屏幕尺寸: width=${resources.displayMetrics.widthPixels}, height=${resources.displayMetrics.heightPixels}")
            
            // 如果已经有弹窗在显示，先关闭它
            if (isPopupShowing) {
                hideCustomPopup()
                return
            }
            
            // 标记弹窗正在显示
            isPopupShowing = true
            
            // 获取选中歌词的位置信息
            val selectedLyricBounds = getSelectedLyricBounds()
            
            // 创建聚光灯遮罩View
            val spotlightOverlay = createSpotlightOverlay(selectedLyricBounds)
            
            // 创建全屏容器
            val popupOverlay = FrameLayout(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(Color.TRANSPARENT)
                
                // 点击背景关闭弹窗
                setOnClickListener {
                    hideCustomPopup()
                }
            }
            
            // 添加聚光灯遮罩
            popupOverlay.addView(spotlightOverlay)
            
            // 获取View在屏幕中的位置
            val viewLocation = IntArray(2)
            this.getLocationOnScreen(viewLocation)
            val viewScreenX = viewLocation[0]
            val viewScreenY = viewLocation[1]
            
            // 计算触摸位置在屏幕坐标系中的位置
            val touchScreenX = viewScreenX + initialTouchX
            val touchScreenY = viewScreenY + initialTouchY
            
            // 创建按钮容器
            val buttonContainer = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
                
                // 设置圆角背景
                val containerBackground = android.graphics.drawable.GradientDrawable().apply {
                    setColor(Color.parseColor("#E0000000")) // 半透明黑色背景
                    cornerRadius = dpToPx(12).toFloat()
                }
                background = containerBackground
                
                setPadding(dpToPx(16), dpToPx(12), dpToPx(16), dpToPx(12))
                
                // 阻止点击事件传递到背景
                setOnClickListener { /* 阻止事件冒泡 */ }
            }
            
            // 创建"从此处开始"按钮
            val startButton = AppCompatButton(context).apply {
                text = "从此处开始"
                setTextColor(Color.WHITE)
                textSize = 14f
                
                // 创建按钮背景
                val buttonBackground = android.graphics.drawable.GradientDrawable().apply {
                    setColor(Color.parseColor("#4285F4"))
                    cornerRadius = dpToPx(8).toFloat()
                }
                background = buttonBackground
                
                setPadding(dpToPx(16), dpToPx(10), dpToPx(16), dpToPx(10))
                
                // 设置点击事件
                setOnClickListener {
                    if (selectedLyricIndex != -1) {
                        jumpToLyricInternal(selectedLyricIndex)
                    }
                    selectedLyricIndex = -1
                    invalidate()
                    hideCustomPopup()
                }
                
                // 设置布局参数
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
            
            // 将按钮添加到容器
            buttonContainer.addView(startButton)
            
            // 计算按钮容器位置
            val containerWidth = dpToPx(140) // 预估容器宽度
            val containerHeight = dpToPx(60) // 预估容器高度
            
            // 获取父容器在屏幕中的位置
            val parentView = parent as? ViewGroup
            val parentLocation = IntArray(2)
            if (parentView != null) {
                parentView.getLocationOnScreen(parentLocation)
            } else {
                // 如果无法获取父容器，使用Activity根视图
                val activity = context as? android.app.Activity
                val rootView = activity?.findViewById<ViewGroup>(android.R.id.content)
                rootView?.getLocationOnScreen(parentLocation)
            }
            val parentScreenX = parentLocation[0]
            val parentScreenY = parentLocation[1]
            
            // 将屏幕坐标转换为相对于父容器的坐标
            val relativeX = touchScreenX - parentScreenX
            val relativeY = touchScreenY - parentScreenY
            
            val containerX = calculateDialogX(containerWidth, relativeX)
            val containerY = calculateDialogY(containerHeight, relativeY)
            
            // 设置按钮容器的位置
            val containerParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                leftMargin = containerX
                topMargin = containerY
            }
            buttonContainer.layoutParams = containerParams
            
            // 将按钮容器添加到覆盖层
            popupOverlay.addView(buttonContainer)
            
            // 将覆盖层添加到父容器
            val targetParentView = parent as? ViewGroup
            if (targetParentView != null) {
                targetParentView.addView(popupOverlay)
                customPopupView = popupOverlay
            } else {
                // 如果无法获取父容器，尝试添加到Activity的根视图
                val activity = context as? android.app.Activity
                val rootView = activity?.findViewById<ViewGroup>(android.R.id.content)
                rootView?.addView(popupOverlay)
                customPopupView = popupOverlay
            }
            
            // 添加调试信息
            console.log("自定义弹窗显示成功")
            console.log("触摸位置(屏幕坐标): touchScreenX=$touchScreenX, touchScreenY=$touchScreenY")
            console.log("父容器位置(屏幕坐标): parentScreenX=$parentScreenX, parentScreenY=$parentScreenY")
            console.log("触摸位置(相对坐标): relativeX=$relativeX, relativeY=$relativeY")
            console.log("容器位置: containerX=$containerX, containerY=$containerY")
            console.log("选中歌词索引: selectedLyricIndex=$selectedLyricIndex")
            console.log("选中歌词边界: ${selectedLyricBounds?.toString() ?: "null"}")
            console.log("=== 自定义弹窗位置调试信息结束 ===")
            
        } catch (e: Exception) {
            // 重置弹窗状态
            isPopupShowing = false
            customPopupView = null
            console.error("显示自定义弹窗失败: ${e.message}")
            // 降级处理
            Toast.makeText(context, "歌词操作菜单", Toast.LENGTH_SHORT).show()
        }
    }
    
    // 隐藏自定义弹窗
    private fun hideCustomPopup() {
        try {
            customPopupView?.let { popup ->
                val parentView = popup.parent as? ViewGroup
                parentView?.removeView(popup)
            }
            customPopupView = null
            isPopupShowing = false
        } catch (e: Exception) {
            console.error("隐藏自定义弹窗失败: ${e.message}")
        }
    }
    
    // 计算弹窗的X坐标 - 基于相对坐标
    private fun calculateDialogX(dialogWidth: Int, relativeX: Float): Int {
        // 使用触摸位置作为弹窗中心
        var dialogX = (relativeX - dialogWidth / 2).toInt()
        
        // 获取父容器的宽度进行边界检查
        val currentParentView = parent as? ViewGroup
        val containerWidth = if (currentParentView != null) {
            currentParentView.width
        } else {
            // 如果无法获取父容器，使用屏幕宽度
            resources.displayMetrics.widthPixels
        }
        
        // 确保弹窗不超出容器边界
        dialogX = maxOf(0, minOf(dialogX, containerWidth - dialogWidth))
        
        return dialogX
    }
    
    // 获取选中歌词的边界信息
    private fun getSelectedLyricBounds(): RectF? {
        if (selectedLyricIndex == -1 || selectedLyricIndex >= lyricPositions.size) {
            return null
        }
        
        val layout = cachedLayout ?: return null
        val position = lyricPositions[selectedLyricIndex]
        val lyricText = lyricItems[selectedLyricIndex].text
        
        // 计算选中歌词在完整文本中的字符位置
        var charStartIndex = 0
        for (i in 0 until selectedLyricIndex) {
            charStartIndex += lyricItems[i].text.length + 1 // +1 是分隔符
        }
        val charEndIndex = charStartIndex + lyricText.length
        
        // 获取歌词文本的起始行和结束行
        val startLine = layout.getLineForOffset(charStartIndex)
        val endLine = layout.getLineForOffset(charEndIndex.coerceAtMost(layout.text.length - 1))
        
        // 计算多行文本的边界
        var minX = Float.MAX_VALUE
        var maxX = Float.MIN_VALUE
        var minY = Float.MAX_VALUE
        var maxY = Float.MIN_VALUE
        
        for (line in startLine..endLine) {
            val lineTop = layout.getLineTop(line).toFloat()
            val lineBottom = layout.getLineBottom(line).toFloat()
            
            // 获取该行中歌词文本的起始和结束位置
            val lineStart = layout.getLineStart(line)
            val lineEnd = layout.getLineEnd(line)
            
            // 计算歌词在该行中的实际起始和结束字符位置
            val textStartInLine = maxOf(charStartIndex, lineStart)
            val textEndInLine = minOf(charEndIndex, lineEnd)
            
            if (textStartInLine < textEndInLine) {
                // 对于多行文本，使用整行的宽度而不是只覆盖文本部分
                val lineLeft = layout.getLineLeft(line)
                val lineRight = layout.getLineRight(line)
                
                // 如果是单行，使用精确的文本边界
                // 如果是多行，使用整行的宽度以获得更好的视觉效果
                if (startLine == endLine) {
                    // 单行情况：使用精确的文本边界
                    val startX = layout.getPrimaryHorizontal(textStartInLine)
                    val endX = layout.getPrimaryHorizontal(textEndInLine)
                    minX = minOf(minX, minOf(startX, endX))
                    maxX = maxOf(maxX, maxOf(startX, endX))
                } else {
                    // 多行情况：使用整行的宽度
                    minX = minOf(minX, lineLeft)
                    maxX = maxOf(maxX, lineRight)
                }
                
                minY = minOf(minY, lineTop)
                maxY = maxOf(maxY, lineBottom)
            }
        }
        
        // 如果没有找到有效的边界，回退到原来的单行计算方式
        if (minX == Float.MAX_VALUE) {
            val lineIndex = getLineIndexForLyric(selectedLyricIndex)
            val lineTop = layout.getLineTop(lineIndex).toFloat()
            val lineBottom = layout.getLineBottom(lineIndex).toFloat()
            
            minX = position.startX
            maxX = position.startX + position.width
            minY = lineTop
            maxY = lineBottom
        }
        
        // 考虑滚动偏移
        val adjustedTop = minY - scrollY
        val adjustedBottom = maxY - scrollY
        
        // 添加一些边距使聚光灯区域更大
        val margin = dpToPx(12).toFloat()
        
        return RectF(
            minX - margin,
            adjustedTop - margin,
            maxX + margin,
            adjustedBottom + margin
        )
    }
    
    // 创建聚光灯遮罩View
    private fun createSpotlightOverlay(lyricBounds: RectF?): View {
        return object : View(context) {
            private var glowAlpha = 0.3f
            private var pulseAnimator: ValueAnimator? = null
            
            init {
                // 启动脉冲动画
                startPulseAnimation()
            }
            
            private fun startPulseAnimation() {
                pulseAnimator = ValueAnimator.ofFloat(0.2f, 0.6f).apply {
                    duration = 2000
                    repeatCount = ValueAnimator.INFINITE
                    repeatMode = ValueAnimator.REVERSE
                    addUpdateListener { animator ->
                        glowAlpha = animator.animatedValue as Float
                        invalidate()
                    }
                    start()
                }
            }
            
            override fun onDetachedFromWindow() {
                super.onDetachedFromWindow()
                pulseAnimator?.cancel()
            }
            
            override fun onDraw(canvas: Canvas) {
                super.onDraw(canvas)
                
                if (lyricBounds == null) {
                    // 如果没有选中歌词，显示半透明遮罩
                    canvas.drawColor(Color.parseColor("#80000000"))
                    return
                }
                
                // 创建半透明遮罩
                val paint = Paint().apply {
                    color = Color.parseColor("#80000000")
                    style = Paint.Style.FILL
                    isAntiAlias = true
                }
                
                // 绘制遮罩，但在歌词区域留出透明洞
                val path = Path().apply {
                    // 添加整个屏幕区域
                    addRect(0f, 0f, width.toFloat(), height.toFloat(), Path.Direction.CW)
                    
                    // 减去歌词区域（创建透明洞）
                    addRoundRect(
                        lyricBounds,
                        dpToPx(12).toFloat(),
                        dpToPx(12).toFloat(),
                        Path.Direction.CCW
                    )
                }
                
                canvas.drawPath(path, paint)
                
                // 绘制多层发光效果
                val cornerRadius = dpToPx(12).toFloat()
                
                // 外层发光
                val outerGlowPaint = Paint().apply {
                    color = Color.argb((glowAlpha * 255 * 0.3f).toInt(), 255, 255, 255)
                    style = Paint.Style.STROKE
                    strokeWidth = dpToPx(4).toFloat()
                    isAntiAlias = true
                }
                
                val outerBounds = RectF(
                    lyricBounds.left - dpToPx(6),
                    lyricBounds.top - dpToPx(6),
                    lyricBounds.right + dpToPx(6),
                    lyricBounds.bottom + dpToPx(6)
                )
                
                canvas.drawRoundRect(outerBounds, cornerRadius + dpToPx(6), cornerRadius + dpToPx(6), outerGlowPaint)
                
                // 内层发光
                val innerGlowPaint = Paint().apply {
                    color = Color.argb((glowAlpha * 255 * 0.6f).toInt(), 255, 255, 255)
                    style = Paint.Style.STROKE
                    strokeWidth = dpToPx(2).toFloat()
                    isAntiAlias = true
                }
                
                canvas.drawRoundRect(lyricBounds, cornerRadius, cornerRadius, innerGlowPaint)
                
                // 添加闪烁点效果
                val sparkleCount = 8
                val sparkleRadius = dpToPx(2).toFloat()
                val sparklePaint = Paint().apply {
                    color = Color.argb((glowAlpha * 255).toInt(), 255, 255, 255)
                    style = Paint.Style.FILL
                    isAntiAlias = true
                }
                
                for (i in 0 until sparkleCount) {
                    val angle = (i * 360f / sparkleCount) + (System.currentTimeMillis() / 20f) % 360f
                    val distance = (lyricBounds.width() + lyricBounds.height()) / 4f
                    val centerX = lyricBounds.centerX()
                    val centerY = lyricBounds.centerY()
                    
                    val sparkleX = centerX + kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat() * distance
                    val sparkleY = centerY + kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat() * distance
                    
                    canvas.drawCircle(sparkleX, sparkleY, sparkleRadius, sparklePaint)
                }
            }
        }.apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            
            // 添加淡入动画
            alpha = 0f
            animate().alpha(1f).setDuration(300).start()
        }
    }
    
    // 计算弹窗的Y坐标 - 基于相对坐标
    private fun calculateDialogY(dialogHeight: Int, relativeY: Float): Int {
        // 直接使用触摸位置作为弹窗顶部，显示在触摸位置下方
        var dialogY = relativeY.toInt()
        
        // 获取父容器的高度进行边界检查
        val currentParentView = parent as? ViewGroup
        val containerHeight = if (currentParentView != null) {
            currentParentView.height
        } else {
            // 如果无法获取父容器，使用屏幕高度
            resources.displayMetrics.heightPixels
        }
        
        // 确保不超出容器底部
        if (dialogY + dialogHeight > containerHeight) {
            // 如果下方空间不够，显示在触摸位置上方
            dialogY = (relativeY - dialogHeight).toInt()
        }
        
        return maxOf(0, dialogY)
    }
    
    fun setPlayedColor(colorString: String): KaraokeTextView {
        try {
            playedColor = Color.parseColor(colorString)
            invalidate()
        } catch (e: Exception) {
            console.error("无效的颜色格式: $colorString")
        }
        return this
    }
    
    fun setUnplayedColor(color: Int) {
        unplayedColor = color
        invalidate()
    }
    
    fun setUnplayedColor(colorString: String): KaraokeTextView {
        try {
            unplayedColor = Color.parseColor(colorString)
            invalidate()
        } catch (e: Exception) {
            console.error("无效的颜色格式: $colorString")
        }
        return this
    }
    
    fun setProgress(progress: Float) {
        this.currentProgress = progress.coerceIn(0f, 1f)
        invalidate()
    }
    
    fun setTextSizePX(size: Float): KaraokeTextView {
        // 重置布局缓存，因为文本大小变化会影响布局
        cachedLayout = null
        lastAvailableWidth = 0
        lastTextWidth = 0f
        textPaint.textSize = size
        invalidate()
        return this
    }
    
    fun setTextAlignment(alignment: TextAlignment): KaraokeTextView {
        // 重置布局缓存，因为对齐方式变化会影响布局
        cachedLayout = null
        lastAvailableWidth = 0
        lastTextWidth = 0f
        textAlignment = alignment
        invalidate()
        return this
    }
    
    fun setBold(bold: Boolean): KaraokeTextView {
        textPaint.isFakeBoldText = bold
        invalidate()
        return this
    }
    
    // 设置是否强制分行显示歌词
    // 添加设置是否强制换行的方法
    fun setIsForceLineBreak(forceLineBreak: Boolean): KaraokeTextView {
        // 如果值没有变化，不需要更新
        if (this.isForceLineBreak == forceLineBreak) {
            return this
        }
        
        // 更新值
        this.isForceLineBreak = forceLineBreak
        
        // 如果有歌词数据，需要重新解析并更新布局
        if (lyricItems.isNotEmpty()) {
            // 重置布局缓存
            cachedLayout = null
            lastAvailableWidth = 0
            lastTextWidth = 0f
            
            // 重新构建文本
            val fullText = StringBuilder()
            for (i in lyricItems.indices) {
                if (i > 0) {
                    // 根据isForceLineBreak决定使用换行符还是空格
                    if (isForceLineBreak) {
                        fullText.append("\n")
                    } else {
                        fullText.append(" ")
                    }
                }
                fullText.append(lyricItems[i].text)
            }
            
            // 更新文本
            this.text = fullText.toString()
            this.lyrics = fullText.toString()
            
            // 更新视图
            invalidate()
        }
        
        return this
    }
    
    private var maxWidth: Int = 0
    
    fun setMaxWidth(maxWidth: Int): KaraokeTextView {
        // 重置布局缓存，因为最大宽度变化会影响布局
        cachedLayout = null
        lastAvailableWidth = 0
        lastTextWidth = 0f
        this.maxWidth = maxWidth
         
        // 重新计算歌词位置
        if (width > 0 && !lyrics.isEmpty()) {
            val availableWidth = if (maxWidth > 0) minOf(maxWidth, width) else width
            val textWidth = textPaint.measureText(lyrics)
            val x = when (textAlignment) {
                TextAlignment.LEFT -> 0f
                TextAlignment.RIGHT -> width - textWidth
                else -> (width - textWidth) / 2 // CENTER
            }
            calculateLyricPositions(x)
        }
        
        // 如果动画正在进行，需要更新动画状态
        if (lyricAnimator != null && lyricAnimator?.isRunning == true) {
            // 保存当前状态
            val currentAnimProgress = currentProgress
            val savedLyricIndex = currentLyricIndex
            val isPlaying = mediaPlayer?.isPlaying ?: false
            val currentPosition = mediaPlayer?.currentPosition ?: 0
            val currentSong = currentPlayingSong
            
            // 暂停当前动画和音频
            lyricAnimator?.pause()
            mediaPlayer?.pause()
            
            // 使用post确保布局更新后再恢复动画和音频
            post {
                // 恢复歌词索引
                currentLyricIndex = savedLyricIndex
                
                // 恢复到之前的进度
                currentProgress = currentAnimProgress
                
                // 如果当前歌词项有音频且之前正在播放，则恢复音频播放
                if (isPlaying && currentSong != null) {
                    // 如果是同一首歌，则从保存的位置继续播放
                    if (currentSong == currentPlayingSong && mediaPlayer != null) {
                        mediaPlayer?.seekTo(currentPosition)
                        mediaPlayer?.start()
                    } else if (savedLyricIndex < lyricItems.size) {
                        // 如果歌曲已经改变，则播放当前歌词项的音频，但不从头开始
                        val songUrl = lyricItems[savedLyricIndex].song
                        if (songUrl.isNotEmpty()) {
                            try {
                                // 释放之前的MediaPlayer资源
                                releaseMediaPlayer()
                                
                                // 创建新的MediaPlayer并从保存的位置播放
                                mediaPlayer = MediaPlayer().apply {
                                    setDataSource(songUrl)
                                    setOnPreparedListener { 
                                        it.seekTo(currentPosition)
                                        it.start() 
                                    }
                                    setOnErrorListener { _, what, extra ->
                                        console.error("音频播放错误: what=$what, extra=$extra")
                                        false
                                    }
                                    prepareAsync()
                                }
                                
                                currentPlayingSong = songUrl
                            } catch (e: Exception) {
                                console.error("恢复音频播放失败: ${e.message}")
                            }
                        }
                    }
                }
                
                // 恢复动画，但保持当前进度
                if (lyricAnimator != null) {
                    // 设置动画的当前进度
                    lyricAnimator?.setCurrentFraction(currentAnimProgress)
                    // 恢复动画
                    lyricAnimator?.resume()
                }
            }
        }
        
        invalidate()
        return this
    }
    
    private var lyricAnimator: ValueAnimator? = null
    
    fun startLyricAnimation(forcePlay: Boolean = false): KaraokeTextView {
        
        if (lyrics.isEmpty() || lyricItems.isEmpty()) {
            console.log("startLyricAnimation 歌词为空，直接返回")
            return this
        }
        
        // 检测是否已经播放完毕，如果是则重置所有状态从头开始
        if (currentLyricIndex >= lyricItems.size - 1 && currentProgress >= 0.99f) {
            console.log("startLyricAnimation 检测到播放已完毕，重置所有状态从头开始")
            // 重置播放状态
            currentLyricIndex = 0
            currentProgress = 0f
            isPaused = false
            pauseTimestamp = 0L
            isAnimationFinished = false
            
            // 清除所有评分数据
            lyricScores.clear()
            actualItemDurations.clear()
            
            // 停止当前音频
            releaseMediaPlayer()
            currentPlayingSong = null
            
            // 清除待执行的任务
            nextLyricRunnable?.let {
                removeCallbacks(it)
                nextLyricRunnable = null
            }
            
            console.log("startLyricAnimation 状态重置完成，从第一句开始播放")
        }
        
        // 根据 forcePlay 参数处理暂停状态
        if (isPaused) {
            if (forcePlay) {
                console.log("startLyricAnimation 强制播放模式，恢复播放状态 - isPaused=$isPaused")
                isPaused = false
                pauseTimestamp = 0L
            } else {
                console.log("startLyricAnimation 检测到暂停状态，跳过动画启动 - isPaused=$isPaused")
                return this
            }
        }
        
        // console.log("startLyricAnimation 开始执行 - isPaused=$isPaused, currentLyricIndex=$currentLyricIndex, currentProgress=$currentProgress")
        
        // 检查是否已经在播放当前歌词项
        val isCurrentlyPlaying = lyricAnimator != null && lyricAnimator?.isRunning == true && !isPaused
        
        // 如果当前正在播放，保存当前进度
        val savedProgress = if (isCurrentlyPlaying) currentProgress else currentProgress
        
        // 停止当前动画
        lyricAnimator?.cancel()
        
        // 获取当前歌词项的时间
        val currentItem = lyricItems[currentLyricIndex]
        val animationDuration = if (currentItem.time > 0) currentItem.time else 1000L
        
        // 只有当song不为空时才播放音频
        if (currentItem.song.isNotEmpty()) {
            // 当从头开始播放时，从头开始播放音频
            if (currentProgress == 0f) {
                playLyricAudio(currentItem.song)
            } else {
                // 当恢复播放时，计算应该开始的音频位置
                val audioPosition = (currentProgress * animationDuration).toInt()
                playLyricAudioFromPosition(currentItem.song, audioPosition)
            }
        }
        
        // 通知播放状态变化：开始播放
        onPlaybackStateChangedCallback?.invoke(true, currentLyricIndex, currentItem.text, false)
        
        // 在动画开始前更新滚动位置，确保当前歌词在可见区域
        updateScrollPosition()
        
        // 创建并配置动画
        lyricAnimator = ValueAnimator.ofFloat(savedProgress, 1f).apply {
            // 根据保存的进度计算剩余时间
            val remainingDuration = (animationDuration * (1f - savedProgress)).toLong().coerceAtLeast(100L)
            duration = remainingDuration
            
            addUpdateListener { animation ->
                val newProgress = animation.animatedValue as Float
                // 只有当进度真正变化时才触发重绘
                if (Math.abs(newProgress - currentProgress) > 0.001f) {
                    currentProgress = newProgress
                    // 更新滚动位置以保持当前播放区域可见
                    updateScrollPosition()
                    invalidate()
                }
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    isAnimationFinished = true
                    
                    // 记录实际播放时长
                    actualItemDurations[currentLyricIndex] = animationDuration
                    
                    // 调用播放完成的回调函数
                    onLyricPlayedCallback?.invoke(currentLyricIndex, lyricItems[currentLyricIndex].text)
                    
                    // 检查是否动画确实完成了（进度是否达到1.0或非常接近1.0）
                    if (currentProgress >= 0.99f) {
                        // 如果不是暂停状态且有下一个歌词项，则自动播放下一个
                        if (!isPaused && currentLyricIndex < lyricItems.size - 1) {
                            // 先增加索引，再重置进度
                            currentLyricIndex++
                            currentProgress = 0f
                            
                            // 添加延迟逻辑
                            val currentTimestamp = System.currentTimeMillis()
                            if (delayBetweenLyrics > 0) {
                                nextLyricRunnable = Runnable {
                                    console.log("nextLyricRunnable 执行检查: isPaused=$isPaused, currentTimestamp=$currentTimestamp")
                                    if (!isPaused) {
                                        startLyricAnimation()
                                    }
                                }
                                handler.postDelayed(nextLyricRunnable!!, delayBetweenLyrics.toLong())
                            } else {
                                nextLyricRunnable = Runnable {
                                    console.log("nextLyricRunnable 执行检查: isPaused=$isPaused, currentTimestamp=$currentTimestamp")
                                    if (!isPaused) {
                                        startLyricAnimation()
                                    }
                                }
                                handler.post(nextLyricRunnable!!)
                            }
                        } else if (!isPaused && currentLyricIndex == lyricItems.size - 1) {
                            // 所有歌词播放完毕，调用播放结束回调
                            console.log("所有歌词播放完毕，调用 playEnd 回调")
                            onPlayEndCallback?.invoke()
                        }
                    } else {
                        console.log("动画提前结束，但未完成播放，currentProgress=$currentProgress")
                    }
                }
            })
            
            // 设置动画状态
            isAnimationFinished = false
            
            start()
        }
        
        return this
    }
    
    fun pauseAnimation(): KaraokeTextView {
        
        // 如果已经是暂停状态，检查是否动画仍在运行
        if (isPaused) {
            // 即使已经是暂停状态，也再次尝试停止动画，以防之前的暂停没有成功
            if (lyricAnimator != null && lyricAnimator?.isRunning == true) {
                lyricAnimator?.cancel()
                // 创建一个新的暂停状态的动画实例，确保动画真正停止
                lyricAnimator = null
            }
            return this
        }
        
        // 保存当前状态
        savedAnimationProgress = currentProgress
        savedLyricIndex = currentLyricIndex
        
        // 暂停动画 - 使用更可靠的方式确保动画停止
        if (lyricAnimator != null) {
            if (lyricAnimator?.isRunning == true) {
                try {
                    // 先尝试暂停
                    lyricAnimator?.pause()
                    
                    // 等待一小段时间确保动画真正暂停
                    postDelayed({
                        if (lyricAnimator?.isRunning == true) {
                            console.log("pauseAnimation 检测到暂停后动画仍在运行，强制取消")
                            lyricAnimator?.cancel()
                            lyricAnimator = null
                            // 强制重绘以确保状态更新
                            invalidate()
                        }
                    }, 50) // 50毫秒后检查
                } catch (e: Exception) {
                    // 处理可能的异常，确保不会崩溃
                    console.error("pauseAnimation 暂停动画时发生异常: ${e.message}")
                    // 如果暂停失败，尝试取消
                    lyricAnimator?.cancel()
                    lyricAnimator = null
                    // 强制重绘以确保状态更新
                    invalidate()
                }
            } else {
                lyricAnimator?.cancel()
                lyricAnimator = null
            }
        } else {
        }
        
        // 暂停音频并保存位置
        if (mediaPlayer != null && mediaPlayer?.isPlaying == true) {
            savedMediaPosition = mediaPlayer?.currentPosition ?: 0
            mediaPlayer?.pause()
        } else {
            console.log("pauseAnimation 音频不存在或未播放")
        }
        
        // 设置暂停状态和时间戳
        isPaused = true
        pauseTimestamp = System.currentTimeMillis()
        console.log("pauseAnimation 设置 isPaused = true, pauseTimestamp = $pauseTimestamp")
        
        // 清除待执行的下一个歌词任务，防止暂停后仍然自动播放
        nextLyricRunnable?.let {
            removeCallbacks(it)
            nextLyricRunnable = null
        }
        console.log("pauseAnimation 清除待执行的异步任务")
        
        // 通知播放状态变化：暂停播放
        if (currentLyricIndex >= 0 && currentLyricIndex < lyricItems.size) {
            onPlaybackStateChangedCallback?.invoke(false, currentLyricIndex, lyricItems[currentLyricIndex].text, false)
        }
        
        // 取消延迟恢复滚动
        cancelDelayedResumeScroll()
        
        // 停止滚动动画
        scrollAnimator?.cancel()
        
        // 强制重绘
        invalidate()
        
        return this
    }
    
    fun resumeAnimation(): KaraokeTextView {
        
        // 如果不是暂停状态，直接返回
        if (!isPaused) {
            console.log("resumeAnimation 未处于暂停状态，直接返回")
            return this
        }
        
        // 检查保存的状态与当前状态是否不一致
        val isIndexChanged = savedLyricIndex != currentLyricIndex
        if (isIndexChanged) {
            // 如果歌词索引已变更，重置进度
            if (currentProgress > 0) {
                console.log("resumeAnimation 检测到索引变更但进度不为0，重置进度")
                currentProgress = 0f
            }
        } else {
            // 恢复进度
            currentProgress = savedAnimationProgress
            console.log("resumeAnimation 恢复进度: currentProgress=$currentProgress")
        }
        
        // 恢复状态并重置时间戳
        isPaused = false
        pauseTimestamp = 0L
        console.log("resumeAnimation 设置 isPaused = false, 重置 pauseTimestamp = 0")
        
        // 通知播放状态变化：恢复播放
        if (currentLyricIndex >= 0 && currentLyricIndex < lyricItems.size) {
            onPlaybackStateChangedCallback?.invoke(true, currentLyricIndex, lyricItems[currentLyricIndex].text, false)
        }
        
        // 恢复音频播放
        if (mediaPlayer != null && !isIndexChanged) {
            try {
                mediaPlayer?.seekTo(savedMediaPosition)
                mediaPlayer?.start()
            } catch (e: Exception) {
            }
        } else {
            // 如果媒体播放器不存在，但有保存的歌词项，则尝试从头开始播放
            if (currentLyricIndex >= 0 && currentLyricIndex < lyricItems.size) {
                val songUrl = lyricItems[currentLyricIndex].song
                if (songUrl.isNotEmpty()) {
                    if (isIndexChanged) {
                        console.log("resumeAnimation 歌词索引已变更，从头开始播放新歌词的音频: $songUrl")
                        playLyricAudio(songUrl)
                    } else {
                        console.log("resumeAnimation 重新创建音频播放器 - 歌曲: $songUrl, 位置: $savedMediaPosition")
                        playLyricAudioFromPosition(songUrl, savedMediaPosition)
                    }
                }
            }
        }
        
        // 检查进度是否已接近完成
        if (currentProgress >= 0.99f) {
            console.log("resumeAnimation 检测到进度已接近完成: $currentProgress，直接完成当前歌词并准备下一个")
            
            // 设置完成标志
            isAnimationFinished = true
            
            // 调用播放完成的回调函数
            onLyricPlayedCallback?.invoke(currentLyricIndex, lyricItems[currentLyricIndex].text)
            
            // 如果有下一个歌词项，则自动播放下一个
            if (currentLyricIndex < lyricItems.size - 1) {
                // 先增加索引，再重置进度
                currentLyricIndex++
                currentProgress = 0f
                console.log("resumeAnimation 准备播放下一个歌词: currentLyricIndex=$currentLyricIndex")
                
                // 添加延迟逻辑
                val currentTimestamp = System.currentTimeMillis()
                nextLyricRunnable = Runnable {
                    // 确保在调用时状态正确，并检查时间戳防止过期任务执行
                    console.log("resumeAnimation nextLyricRunnable 执行检查: isPaused=$isPaused, currentTimestamp=$currentTimestamp, pauseTimestamp=$pauseTimestamp")
                    if (!isPaused && currentTimestamp > pauseTimestamp) {
                        startLyricAnimation()
                    } else {
                        console.log("resumeAnimation nextLyricRunnable 跳过执行: 暂停状态或任务过期")
                    }
                }
                
                if (delayBetweenLyrics > 0) {
                    console.log("resumeAnimation 添加延迟: $delayBetweenLyrics ms")
                    postDelayed(nextLyricRunnable!!, delayBetweenLyrics)
                } else {
                    post(nextLyricRunnable!!)
                }
            } else if (currentLyricIndex == lyricItems.size - 1) {
                // 所有歌词播放完毕，调用播放结束回调
                console.log("resumeAnimation: 所有歌词播放完毕，调用 playEnd 回调")
                onPlayEndCallback?.invoke()
            }
            
            // 强制重绘
            invalidate()
            
            console.log("resumeAnimation 完成 - 最终状态: isPaused=$isPaused, currentLyricIndex=$currentLyricIndex, currentProgress=$currentProgress")
            return this
        }
        
        // 重新开始动画
        if (lyricAnimator != null) {
            console.log("resumeAnimation 恢复动画 - 设置进度: $currentProgress")
            
            // 取消当前的动画实例并防止进度重置
            lyricAnimator?.cancel()
            
            // 获取当前歌词项的时间
            val currentItem = lyricItems[currentLyricIndex]
            val animationDuration = if (currentItem.time > 0) currentItem.time else 1000L
            console.log("resumeAnimation 创建新动画 - 时长: $animationDuration, 进度: $currentProgress")
            
            // 创建新的动画，从保存的进度点开始
            lyricAnimator = ValueAnimator.ofFloat(currentProgress, 1f).apply {
                // 计算剩余时长，确保至少有100毫秒的时间
                val remainingDuration = (animationDuration * (1f - currentProgress)).toLong().coerceAtLeast(100L)
                duration = remainingDuration
                
                console.log("resumeAnimation 动画剩余时长: $remainingDuration ms")
                
                addUpdateListener { animation ->
                    val newProgress = animation.animatedValue as Float
                    // 只有当进度真正变化时才触发重绘
                    if (Math.abs(newProgress - currentProgress) > 0.001f) {
                        currentProgress = newProgress
                        console.log("resumeAnimation 进度更新: currentProgress=$currentProgress")
                        // 更新滚动位置以保持当前播放区域可见
                        updateScrollPosition()
                        invalidate()
                    }
                }
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: android.animation.Animator) {
                        isAnimationFinished = true
                        console.log("resumeAnimation 动画结束 - 当前状态: currentLyricIndex=$currentLyricIndex, currentProgress=$currentProgress")
                        
                        // 记录实际播放时长
                        actualItemDurations[currentLyricIndex] = animationDuration
                        
                        // 调用播放完成的回调函数
                        onLyricPlayedCallback?.invoke(currentLyricIndex, lyricItems[currentLyricIndex].text)
                        
                        // 检查是否动画确实完成了（进度是否达到1.0或非常接近1.0）
                        if (currentProgress >= 0.99f) {
                            console.log("resumeAnimation 当前歌词已播放完毕，准备下一个")
                            // 如果不是暂停状态且有下一个歌词项，则自动播放下一个
                            if (!isPaused && currentLyricIndex < lyricItems.size - 1) {
                                // 先增加索引，再重置进度
                                currentLyricIndex++
                                currentProgress = 0f
                                console.log("resumeAnimation 准备播放下一个歌词: currentLyricIndex=$currentLyricIndex")
                                
                                // 添加延迟逻辑
                                val currentTimestamp = System.currentTimeMillis()
                                if (delayBetweenLyrics > 0) {
                                    nextLyricRunnable = Runnable {
                                        console.log("resumeAnimation nextLyricRunnable 执行检查: isPaused=$isPaused, currentTimestamp=$currentTimestamp")
                                        if (!isPaused) {
                                            startLyricAnimation()
                                        }
                                    }
                                    handler.postDelayed(nextLyricRunnable!!, delayBetweenLyrics.toLong())
                                } else {
                                    nextLyricRunnable = Runnable {
                                        console.log("resumeAnimation nextLyricRunnable 执行检查: isPaused=$isPaused, currentTimestamp=$currentTimestamp")
                                        if (!isPaused) {
                                            startLyricAnimation()
                                        }
                                    }
                                    handler.post(nextLyricRunnable!!)
                                }
                            } else if (!isPaused && currentLyricIndex == lyricItems.size - 1) {
                                // 所有歌词播放完毕，调用播放结束回调
                                console.log("resumeAnimation: 所有歌词播放完毕，调用 playEnd 回调")
                                onPlayEndCallback?.invoke()
                            }
                        } else {
                            console.log("resumeAnimation 动画提前结束，但未完成播放（进度=$currentProgress）")
                        }
                    }
                })
                
                // 设置动画状态
                isAnimationFinished = false
                
                start()
            }
        } else {
            console.log("resumeAnimation 动画不存在，创建新动画")
            // 直接调用startLyricAnimation会导致进度重置，因此使用相同的逻辑但保留当前进度
            
            // 获取当前歌词项的时间
            val currentItem = lyricItems[currentLyricIndex]
            val animationDuration = if (currentItem.time > 0) currentItem.time else 1000L
            console.log("resumeAnimation 创建全新动画 - 时长: $animationDuration, 进度: $currentProgress")
            
            // 只有当song不为空时才播放音频
            if (currentItem.song.isNotEmpty() && !isIndexChanged) {
                // 如果歌词索引没有变更，则从保存的位置恢复
                val audioPosition = if (savedMediaPosition > 0) savedMediaPosition else (currentProgress * animationDuration).toInt()
                console.log("resumeAnimation 从保存位置恢复音频: ${currentItem.song}, 位置: $audioPosition")
                playLyricAudioFromPosition(currentItem.song, audioPosition)
            }
            
            // 创建新的动画，从当前进度点开始
            lyricAnimator = ValueAnimator.ofFloat(currentProgress, 1f).apply {
                // 计算剩余时长，确保至少有100毫秒的时间
                val remainingDuration = (animationDuration * (1f - currentProgress)).toLong().coerceAtLeast(100L)
                duration = remainingDuration
                
                console.log("resumeAnimation 动画剩余时长: $remainingDuration ms")
                
                addUpdateListener { animation ->
                    val newProgress = animation.animatedValue as Float
                    // 只有当进度真正变化时才触发重绘
                    if (Math.abs(newProgress - currentProgress) > 0.001f) {
                        currentProgress = newProgress
                        // 更新滚动位置以保持当前播放区域可见
                        updateScrollPosition()
                        invalidate()
                    }
                }
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: android.animation.Animator) {
                        isAnimationFinished = true
                        console.log("resumeAnimation 动画结束 - 当前状态: currentLyricIndex=$currentLyricIndex, currentProgress=$currentProgress")
                        
                        // 记录实际播放时长
                        actualItemDurations[currentLyricIndex] = animationDuration
                        
                        // 调用播放完成的回调函数
                        onLyricPlayedCallback?.invoke(currentLyricIndex, lyricItems[currentLyricIndex].text)
                        
                        // 检查是否动画确实完成了（进度是否达到1.0或非常接近1.0）
                        if (currentProgress >= 0.99f) {
                            console.log("resumeAnimation 当前歌词已播放完毕，准备下一个")
                            // 如果不是暂停状态且有下一个歌词项，则自动播放下一个
                            if (!isPaused && currentLyricIndex < lyricItems.size - 1) {
                                // 先增加索引，再重置进度
                                currentLyricIndex++
                                currentProgress = 0f
                                console.log("resumeAnimation 准备播放下一个歌词: currentLyricIndex=$currentLyricIndex")
                                
                                // 添加延迟逻辑
                                nextLyricRunnable = Runnable {
                                    // 确保在调用时状态正确
                                    if (!isPaused) {
                                        startLyricAnimation()
                                    }
                                }
                                
                                if (delayBetweenLyrics > 0) {
                                    console.log("resumeAnimation 添加延迟: $delayBetweenLyrics ms")
                                    postDelayed(nextLyricRunnable!!, delayBetweenLyrics)
                                } else {
                                    post(nextLyricRunnable!!)
                                }
                            } else if (!isPaused && currentLyricIndex == lyricItems.size - 1) {
                                // 所有歌词播放完毕，调用播放结束回调
                                console.log("resumeAnimation(第二处): 所有歌词播放完毕，调用 playEnd 回调")
                                onPlayEndCallback?.invoke()
                            }
                        } else {
                            console.log("resumeAnimation 动画提前结束，但未完成播放（进度=$currentProgress）")
                        }
                    }
                })
                
                // 设置动画状态
                isAnimationFinished = false
                
                start()
            }
        }
        
        console.log("resumeAnimation 完成 - 最终状态: isPaused=$isPaused, currentLyricIndex=$currentLyricIndex, currentProgress=$currentProgress")
        
        // 强制重绘
        invalidate()
        
        return this
    }
    
    // 播放歌词音频
    private fun playLyricAudio(songUrl: String) {
        // 如果歌曲URL为空，不执行任何音频播放操作
        if (songUrl.isEmpty()) {
            // 确保释放之前的MediaPlayer资源
            releaseMediaPlayer()
            return
        }
        
        try {
            // 如果当前正在播放相同的音频，则不需要重新创建
            if (songUrl == currentPlayingSong && mediaPlayer != null) {
                mediaPlayer?.seekTo(0)
                mediaPlayer?.start()
                return
            }
            
            // 释放之前的MediaPlayer资源
            releaseMediaPlayer()
            
            // 创建新的MediaPlayer并播放
            mediaPlayer = MediaPlayer().apply {
                setDataSource(songUrl)
                setOnPreparedListener { it.start() }
                setOnErrorListener { _, what, extra ->
                    console.error("音频播放错误: what=$what, extra=$extra")
                    false
                }
                prepareAsync()
            }
            
            currentPlayingSong = songUrl
            
        } catch (e: Exception) {
            console.error("播放音频失败: ${e.message}")
        }
    }
    
    // 添加从指定位置播放音频的方法
    private fun playLyricAudioFromPosition(songUrl: String, position: Int) {
        console.log("playLyricAudioFromPosition: songUrl=$songUrl, position=$position")
        
        // 如果歌曲URL为空，不执行任何音频播放操作
        if (songUrl.isEmpty()) {
            // 确保释放之前的MediaPlayer资源
            releaseMediaPlayer()
            return
        }
        
        try {
            // 如果当前正在播放相同的音频，检查是否需要调整位置
            if (songUrl == currentPlayingSong && mediaPlayer != null) {
                val currentPosition = mediaPlayer?.currentPosition ?: 0
                // 只有当位置差异较大时才进行 seekTo，避免频繁调用
                if (Math.abs(currentPosition - position) > 100) {
                    console.log("playLyricAudioFromPosition: 使用现有播放器从位置 $position 开始播放")
                    mediaPlayer?.seekTo(position)
                    if (mediaPlayer?.isPlaying != true) {
                        mediaPlayer?.start()
                    }
                }
                return
            }
            
            // 释放之前的MediaPlayer资源
            releaseMediaPlayer()
            
            console.log("playLyricAudioFromPosition: 创建新的播放器从位置 $position 开始播放")
            
            // 创建新的MediaPlayer并从指定位置播放
            mediaPlayer = MediaPlayer().apply {
                setDataSource(songUrl)
                setOnPreparedListener { 
                    console.log("playLyricAudioFromPosition: 音频准备完成，开始从位置 $position 播放")
                    it.seekTo(position)
                    it.start() 
                }
                setOnErrorListener { _, what, extra ->
                    console.error("音频播放错误: what=$what, extra=$extra")
                    false
                }
                prepareAsync()
            }
            
            currentPlayingSong = songUrl
            
        } catch (e: Exception) {
            console.error("从指定位置播放音频失败: ${e.message}")
        }
    }
    
    // 释放MediaPlayer资源
    private fun releaseMediaPlayer() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            reset()
            release()
        }
        mediaPlayer = null
        currentPlayingSong = null
    }
    
    // 计算每个歌词项的位置和宽度，考虑评分文本的空间
    private fun calculateLyricPositions(baseX: Float) {
        console.log("[DEBUG] calculateLyricPositions - baseX: $baseX")
        console.log("[DEBUG] calculateLyricPositions - lyricItems.size: ${lyricItems.size}")
        console.log("[DEBUG] calculateLyricPositions - isForceLineBreak: $isForceLineBreak")
        
        lyricPositions.clear()
        var currentX = baseX
        
        // 保存当前文本画笔设置，用于计算评分文本宽度
        val originalTextSize = textPaint.textSize
        val originalBold = textPaint.isFakeBoldText
        
        for (i in lyricItems.indices) {
            val item = lyricItems[i]
            console.log("[DEBUG] 处理歌词[$i]: '${item.text}', currentX: $currentX")
            
            // 使用Rect获取更准确的文本边界
            val textBounds = Rect()
            textPaint.getTextBounds(item.text, 0, item.text.length, textBounds)
            
            // 使用边界宽度作为文本宽度，并添加一些安全边距
            val safetyMargin = textPaint.textSize * 0.1f // 添加10%的安全边距
            val width = textBounds.width().toFloat() + safetyMargin
            
            // 计算评分文本需要的额外空间
            var extraSpace = 0f
            
            // 只有在需要预留评分空间时才计算
            if (shouldReserveScoreSpace) {
                // 设置评分文本的样式以计算宽度
                textPaint.textSize = originalTextSize * 0.8f
                textPaint.isFakeBoldText = true
                
                // 使用Rect获取更准确的评分文本边界
                val scoreBounds = Rect()
                val defaultScoreText = "9999"
                textPaint.getTextBounds(defaultScoreText, 0, defaultScoreText.length, scoreBounds)
                val defaultScoreWidth = scoreBounds.width().toFloat() + safetyMargin
                
                // 计算空格宽度，确保一致性
                val spaceText = " "
                val spaceBounds = Rect()
                textPaint.getTextBounds(spaceText, 0, spaceText.length, spaceBounds)
                val spaceWidth = maxOf(spaceBounds.width().toFloat(), textPaint.textSize * 0.25f) // 确保空格宽度至少为字体大小的25%
                
                // 如果有实际评分，则使用实际评分的宽度
                if (lyricScores.containsKey(i)) {
                    val scoreInfo = lyricScores[i]!!
                    val scoreTextBounds = Rect()
                    textPaint.getTextBounds(scoreInfo.score, 0, scoreInfo.score.length, scoreTextBounds)
                    val scoreWidth = scoreTextBounds.width().toFloat() + safetyMargin
                    
                    // 取实际评分宽度和默认宽度的较大值，确保空间足够
                    // 使用固定的空格数量（20个空格宽度）作为间距，确保评分显示在歌词右侧足够远的位置
                    // 增加间距以避免遮挡
                    extraSpace = spaceWidth * 20 + maxOf(scoreWidth, defaultScoreWidth)
                } else {
                    // 没有评分时，预留默认空间
                    // 同样使用固定的空格数量，保持一致性
                    extraSpace = spaceWidth * 20 + defaultScoreWidth
                }
            }
            
            // 恢复原始文本画笔设置
            textPaint.textSize = originalTextSize
            textPaint.isFakeBoldText = originalBold
            
            // 注意：这里只存储歌词本身的宽度，不包含评分空间
            // 这样评分就会显示在歌词右侧，而不会遮挡歌词
            lyricPositions.add(LyricPosition(currentX, width))
            console.log("[DEBUG] 歌词[$i] 位置已存储: startX=$currentX, width=$width")
            
            // 根据isForceLineBreak决定使用换行符还是空格的宽度
            if (isForceLineBreak) {
                // 下一个歌词项从行首开始
                console.log("[DEBUG] 强制换行模式，下一个歌词从行首开始: $baseX")
                currentX = baseX
            } else {
                // 计算下一个歌词的起始位置，需要加上当前歌词宽度、空格宽度和评分空间
                // 使用之前计算的spaceWidth确保一致性
                val nextX = currentX + width + (if (shouldReserveScoreSpace) extraSpace else 0f)
                console.log("[DEBUG] 非强制换行模式，下一个歌词位置: $currentX + $width + ${if (shouldReserveScoreSpace) extraSpace else 0f} = $nextX")
                currentX = nextX
            }
        }
    }
    
    // 获取最大滚动值
    private fun getMaxScrollY(): Float {
        if (cachedLayout == null) return 0f
        if (height <= 0) return 0f
        
        // 使用实际计算的文本高度
        val totalTextHeight = cachedLayout!!.height.toFloat() + paddingTop + paddingBottom
        return (totalTextHeight - height).coerceAtLeast(0f)
    }
    
    // 平滑滚动到指定位置
    private fun smoothScrollTo(targetY: Float) {
        // 确保目标滚动位置在有效范围内
        val maxScrollY = getMaxScrollY()
        val safeTargetY = targetY.coerceIn(0f, maxScrollY)
        
        // 如果已经在目标位置或非常接近，不需要滚动
        if (Math.abs(safeTargetY - scrollY) < 1f) return
        
        // 取消之前的滚动动画
        scrollAnimator?.cancel()
        
        // 创建新的滚动动画，根据滚动距离调整动画时长
        val distance = Math.abs(safeTargetY - scrollY)
        val duration = (150 + distance * 0.5f).coerceAtMost(500f).toLong() // 根据距离调整时长，最短150ms，最长500ms
        
        scrollAnimator = ValueAnimator.ofFloat(scrollY, safeTargetY).apply {
            this.duration = duration
            interpolator = android.view.animation.DecelerateInterpolator() // 使用减速插值器使滚动更自然
            addUpdateListener { 
                scrollY = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }
    
    // 取消延迟恢复滚动
    private fun cancelDelayedResumeScroll() {
        resumeScrollRunnable?.let {
            removeCallbacks(it)
            resumeScrollRunnable = null
        }
    }
    
    // 恢复自动滚动
    private fun resumeAutoScroll() {
        if (currentLyricIndex < 0 || currentLyricIndex >= lyricItems.size) return
        
        // 取消之前的延迟恢复任务
        cancelDelayedResumeScroll()
        
        // 创建新的延迟恢复任务
        resumeScrollRunnable = Runnable {
            // 计算当前句子在整个文本中的起始位置
            updateScrollPosition()
            
            // 任务完成后清空引用
            resumeScrollRunnable = null
        }
        
        // 延迟执行
        postDelayed(resumeScrollRunnable, RESUME_SCROLL_DELAY)
    }
    
    // 更新滚动位置
    private fun updateScrollPosition() {
        // 如果没有正在播放，或者没有歌词，则不需要更新滚动位置
        if (isAnimationFinished || lyricItems.isEmpty() || currentLyricIndex < 0) {
            return
        }
        
        // 如果没有缓存布局，不需要更新滚动位置
        if (cachedLayout == null) {
            return
        }
        
        // 确保视图已经测量完成
        if (width == 0 || height == 0) {
            // 视图尚未完成测量，延迟更新
            post { updateScrollPosition() }
            return
        }
        
        if (maxWidth > 0 && textPaint.measureText(lyrics) > maxWidth) {
            // 多行显示情况下的滚动位置计算
            if (cachedLayout == null) return
            
            // 计算当前歌词在哪一行
            var currentLyricStart = 0
            for (i in 0 until currentLyricIndex) {
                currentLyricStart += lyricItems[i].text.length + 1 // +1 是空格
            }
            
            // 计算当前进度对应的文本位置
            val currentLyricLength = lyricItems[currentLyricIndex].text.length
            val progressPosition = currentLyricStart + (currentLyricLength * currentProgress).toInt()
            
            // 添加调试日志
            
            // 找到当前进度所在的行
            var currentLine = 0
            for (i in 0 until cachedLayout!!.lineCount) {
                val lineStart = cachedLayout!!.getLineStart(i)
                val lineEnd = cachedLayout!!.getLineEnd(i)
                
                
                if (progressPosition >= lineStart && progressPosition < lineEnd) {
                    currentLine = i
                    break
                }
            }
            
            // 计算目标行的Y坐标
            val targetLineY = cachedLayout!!.getLineTop(currentLine).toFloat()
            
            // 计算目标滚动位置，使当前行在视图中间偏上位置
            val visibleHeight = height - paddingTop - paddingBottom
            targetScrollY = (targetLineY - visibleHeight * 0.3f).coerceAtLeast(0f).coerceAtMost(getMaxScrollY())
            
            // 如果不是拖动状态，平滑滚动到目标位置
            if (!isDragging && autoScrollEnabled) {
                smoothScrollTo(targetScrollY)
            }
        } else {
            // 单行显示情况下的滚动位置计算
            if (lyricPositions.isEmpty() || currentLyricIndex >= lyricPositions.size) return
            
            // 获取当前歌词的位置
            val position = lyricPositions[currentLyricIndex]
            
            // 计算当前歌词的中心位置
            val lyricCenterX = position.startX + position.width / 2
            
            // 计算视图的中心位置
            val viewCenterX = width / 2f
            
            // 计算需要水平滚动的距离，使当前歌词居中显示
            val scrollOffset = lyricCenterX - viewCenterX
            
            // 如果歌词总宽度大于视图宽度，才需要滚动
            if (textPaint.measureText(lyrics) > width) {
                // 如果不是拖动状态，平滑滚动到目标位置
                if (!isDragging && autoScrollEnabled) {
                    // 创建水平滚动动画
                    scrollAnimator?.cancel()
                    
                    // 确保滚动偏移量在合理范围内
                    val maxScrollX = (textPaint.measureText(lyrics) - width).coerceAtLeast(0f)
                    val safeScrollOffset = scrollOffset.coerceIn(0f, maxScrollX)
                    
                    // 创建滚动动画
                    val duration = 300L // 滚动动画时长
                    scrollAnimator = ValueAnimator.ofFloat(scrollY, safeScrollOffset).apply {
                        this.duration = duration
                        interpolator = android.view.animation.DecelerateInterpolator()
                        addUpdateListener { 
                            scrollY = it.animatedValue as Float
                            invalidate()
                        }
                        start()
                    }
                }
            }
        }
    }
    
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        
        
        // 如果高度发生变化，需要重新计算滚动位置
        if (h != oldh && h > 0) {
            // 确保滚动位置在有效范围内
            scrollY = scrollY.coerceAtMost(getMaxScrollY())
            
            // 如果正在播放动画且未被拖动，更新滚动位置
            if (!isAnimationFinished && !isDragging && autoScrollEnabled) {
                updateScrollPosition()
            }
        }
    }
    
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // console.log("onTouchEvent ACTION_DOWN: 坐标(${event.x}, ${event.y})")
                // 记录初始触摸位置
                initialTouchY = event.y
                lastTouchY = event.y
                initialTouchX = event.x
                lastTouchX = event.x
                isScrollingVertically = false
                // console.log("onTouchEvent ACTION_DOWN: initialTouchY=$initialTouchY, touchSlop=$touchSlop")
                
                // 取消延迟恢复滚动
                cancelDelayedResumeScroll()
                
                // 请求所有父视图不要拦截触摸事件
                var parent: ViewParent? = parent
                while (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true)
                    parent = parent.parent
                }
                
                // 停止自动滚动
                scrollAnimator?.cancel()
                return true
            }
            
            MotionEvent.ACTION_MOVE -> {
                val deltaY = lastTouchY - event.y
                
                // 判断是否达到滑动阈值
                if (!isScrollingVertically && Math.abs(event.y - initialTouchY) > touchSlop) {
                    isScrollingVertically = true
                }
                
                if (isScrollingVertically) {
                    // 如果开始滚动，取消延迟恢复
                    cancelDelayedResumeScroll()
                    // 检查是否已经滚动到边界
                    val canScrollUp = scrollY > 0
                    val canScrollDown = scrollY < getMaxScrollY()
                    
                    // 如果无法继续滚动，让父视图处理事件
                    if ((deltaY > 0 && !canScrollDown) || (deltaY < 0 && !canScrollUp)) {
                        // 允许所有父视图拦截事件
                        var parent: ViewParent? = parent
                        while (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(false)
                            parent = parent.parent
                        }
                    } else {
                        // 否则自己处理滚动
                        isDragging = true
                        scrollY = (scrollY + deltaY).coerceIn(0f, getMaxScrollY())
                        invalidate()
                    }
                }
                
                lastTouchY = event.y
                return true
            }
            
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // 恢复父视图的事件拦截
                var parent: ViewParent? = parent
                while (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(false)
                    parent = parent.parent
                }
                
                if (isDragging) {
                    isDragging = false
                    // 如果自动滚动启用，延迟恢复到当前播放位置
                    if (!isAnimationFinished && autoScrollEnabled && 
                        lyricItems.isNotEmpty() && currentLyricIndex >= 0) {
                        resumeAutoScroll()
                    }
                } else {
                    // 如果没有拖拽，检查是否为点击事件
                    // console.log("ACTION_UP: 检查点击事件 - isScrollingVertically=$isScrollingVertically, deltaY=${Math.abs(event.y - initialTouchY)}, touchSlop=$touchSlop")
                    if (!isScrollingVertically && Math.abs(event.y - initialTouchY) <= touchSlop) {
                        // console.log("ACTION_UP: 确认为点击事件")
                        // 这是一个点击事件，检查点击的是哪个歌词
                        val clickedLyricIndex = findClickedLyric(event.x, event.y)
                        // console.log("ACTION_UP: findClickedLyric返回=$clickedLyricIndex, currentLyricIndex=$currentLyricIndex")
                        if (clickedLyricIndex != -1 && clickedLyricIndex != currentLyricIndex) {
                            console.log("ACTION_UP: 选中歌词，显示弹窗 - lyricIndex=$clickedLyricIndex")
                            // 选中歌词，显示弹窗
                            selectedLyricIndex = clickedLyricIndex
                            showStartButtonDialog()
                        } else {
                            // console.log("ACTION_UP: 点击其他地方或相同歌词，关闭弹窗")
                            // 点击其他地方，关闭弹窗
                            hideCustomPopup()
                            selectedLyricIndex = -1
                            invalidate()
                        }
                    } else {
                        console.log("ACTION_UP: 不是点击事件，可能是滚动")
                    }
                }
                return true
            }
        }
        
        return super.onTouchEvent(event)
    }
    
    // 设置是否启用自动滚动
    fun setAutoScrollEnabled(enabled: Boolean): KaraokeTextView {
        autoScrollEnabled = enabled
        return this
    }
    
    // 添加设置延迟时间的方法
    fun setDelayBetweenLyrics(delayMs: Long): KaraokeTextView {
        this.delayBetweenLyrics = delayMs.coerceAtLeast(0L) // 确保延迟时间不小于0
        return this
    }
    
    // 添加设置播放完成回调的方法
    fun setOnLyricPlayedCallback(callback: ((Int, String) -> Unit)?): KaraokeTextView {
        this.onLyricPlayedCallback = callback
        return this
    }
    
    // 添加设置播放状态变化回调的方法
    // 回调参数：isPlaying(是否正在播放), lyricIndex(当前歌词索引), lyricText(当前歌词文本), forceJump(是否强制跳转)
    fun setOnPlaybackStateChangedCallback(callback: ((Boolean, Int, String, Boolean) -> Unit)?): KaraokeTextView {
        this.onPlaybackStateChangedCallback = callback
        return this
    }
    
    // 添加设置播放结束回调的方法
    fun setOnPlayEndCallback(callback: (() -> Unit)?): KaraokeTextView {
        this.onPlayEndCallback = callback
        return this
    }
    
    // 跳转到指定歌词并开始播放（公共方法）
    fun jumpToLyric(lyricIndex: Int): KaraokeTextView {
        jumpToLyricInternal(lyricIndex)
        return this
    }
    
    // 添加设置是否预留评分空间的方法
    fun setShouldReserveScoreSpace(reserve: Boolean): KaraokeTextView {
        if (this.shouldReserveScoreSpace != reserve) {
            this.shouldReserveScoreSpace = reserve
            // 重置布局缓存
            cachedLayout = null
            lastAvailableWidth = 0
            lastTextWidth = 0f
            // 重新计算布局
            if (width > 0 && !lyrics.isEmpty()) {
                val availableWidth = if (maxWidth > 0) minOf(maxWidth, width) else width
                val textWidth = textPaint.measureText(lyrics)
                val x = when (textAlignment) {
                    TextAlignment.LEFT -> 0f
                    TextAlignment.RIGHT -> width - textWidth
                    else -> (width - textWidth) / 2 // CENTER
                }
                calculateLyricPositions(x)
            }
            invalidate()
        }
        return this
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        if (lyrics.isEmpty()) return
        
        val availableWidth = if (maxWidth > 0) minOf(maxWidth, width) else width
        val textWidth = textPaint.measureText(lyrics)
        
        console.log("[DEBUG] onDraw - lyrics.length: ${lyrics.length}")
        console.log("[DEBUG] onDraw - availableWidth: $availableWidth, textWidth: $textWidth")
        console.log("[DEBUG] onDraw - maxWidth: $maxWidth, view width: $width")
        console.log("[DEBUG] onDraw - isForceLineBreak: $isForceLineBreak")
        console.log("[DEBUG] onDraw - lyricItems.size: ${lyricItems.size}")
        
        if (maxWidth > 0 && textWidth > availableWidth) {
            // 文本超出最大宽度，需要折行显示
            // 使用缓存的布局对象，避免每次onDraw都创建新对象
            val layout = if (cachedLayout != null && lastAvailableWidth == availableWidth && lastTextWidth == textWidth) {
                cachedLayout
            } else {
                // 根据textAlignment设置对应的Layout.Alignment
                val alignment = when (textAlignment) {
                    TextAlignment.LEFT -> Layout.Alignment.ALIGN_NORMAL
                    TextAlignment.RIGHT -> Layout.Alignment.ALIGN_OPPOSITE
                    else -> Layout.Alignment.ALIGN_CENTER
                }
                
                // 只有在需要预留评分空间时才计算评分空间
                val adjustedWidth = if (shouldReserveScoreSpace) {
                    // 为评分预留空间，减少可用宽度
                    val spaceWidth = textPaint.measureText(" ")
                    val defaultScoreText = ""
                    val scoreBounds = Rect()
                    val originalTextSize = textPaint.textSize
                    val originalBold = textPaint.isFakeBoldText
                    
                    // 设置评分文本的样式以计算宽度
                    textPaint.textSize = originalTextSize * 0.8f
                    textPaint.isFakeBoldText = true
                    textPaint.getTextBounds(defaultScoreText, 0, defaultScoreText.length, scoreBounds)
                    
                    // 恢复原始文本画笔设置
                    textPaint.textSize = originalTextSize
                    textPaint.isFakeBoldText = originalBold
                    
                    // 计算评分需要的空间（8个空格宽度加默认评分文本宽度）
                    val scoreSpace = spaceWidth * 8 + scoreBounds.width().toFloat()
                    
                    
                    // 减少可用宽度，为评分预留空间
                    availableWidth - scoreSpace
                } else {
                    availableWidth
                }
                
                console.log("[DEBUG] 创建StaticLayout - adjustedWidth: $adjustedWidth")
                console.log("[DEBUG] 创建StaticLayout - alignment: $alignment")
                console.log("[DEBUG] 创建StaticLayout - lyrics: '${lyrics.take(100)}${if(lyrics.length > 100) "..." else ""}'")
                
                val newLayout = StaticLayout.Builder.obtain(lyrics, 0, lyrics.length, textPaint, adjustedWidth.toInt())
                    .setAlignment(alignment)
                    .setLineSpacing(0f, 1.0f)
                    .setIncludePad(true)
                    .build()
                
                console.log("[DEBUG] StaticLayout创建完成 - lineCount: ${newLayout.lineCount}")
                console.log("[DEBUG] StaticLayout - height: ${newLayout.height}")
                
                for (i in 0 until minOf(5, newLayout.lineCount)) {
                    val lineStart = newLayout.getLineStart(i)
                    val lineEnd = newLayout.getLineEnd(i)
                    val lineText = if (lineEnd > lineStart) lyrics.substring(lineStart, minOf(lineEnd, lyrics.length)) else ""
                    console.log("[DEBUG] Line[$i]: start=$lineStart, end=$lineEnd, text='${lineText.take(50)}${if(lineText.length > 50) "..." else ""}'")
                }
                
                // 更新缓存
                cachedLayout = newLayout
                lastAvailableWidth = availableWidth
                lastTextWidth = textWidth
                textHeight = newLayout.height.toFloat()
                
                newLayout
            }
            
            // 计算每个歌词项的位置（多行文本情况下也需要）
            val x = (width - availableWidth).toFloat() / 2f
            calculateLyricPositions(x)
                
            canvas.save()
            // 应用垂直滚动偏移
            val yOffset = if (lyricItems.size == 1) {
                paddingTop.toFloat()
            } else {
				
                -scrollY
            }
            console.log("[DEBUG] Y偏移计算: lyricItems.size=${lyricItems.size}, scrollY=$scrollY, yOffset=$yOffset")
            canvas.translate((width - availableWidth).toFloat() / 2f, yOffset)
            
            // 先绘制所有歌词为未播放颜色
            console.log("[DEBUG] 绘制未播放歌词，颜色: $unplayedColor")
            textPaint.color = unplayedColor
            layout!!.draw(canvas)
            
            // 绘制已播放完毕的歌词（整个歌词都用已播放颜色）
            if (currentLyricIndex > 0) {
                var previousLyricsEnd = 0
                
                // 计算已播放完毕的歌词的结束位置
                for (i in 0 until currentLyricIndex) {
                    val lyricLength = lyricItems[i].text.length
                    previousLyricsEnd += lyricLength + 1 // +1 是空格
                    
                    // 找到包含这个歌词的所有行，并用已播放颜色绘制
                    var startPos = if (i == 0) 0 else previousLyricsEnd - lyricLength - 1
                    var endPos = previousLyricsEnd - 1 // 不包括空格
                    
                    // 遍历所有行，查找包含已播放歌词的行
                    for (lineIdx in 0 until layout!!.lineCount) {
                        val lineStart = layout!!.getLineStart(lineIdx)
                        val lineEnd = layout!!.getLineEnd(lineIdx)
                        
                        // 检查这一行是否包含已播放歌词的任何部分
                        if (!(endPos < lineStart || startPos >= lineEnd)) {
                            // 计算这一行中已播放歌词的范围
                            val segmentStart = maxOf(startPos, lineStart)
                            val segmentEnd = minOf(endPos, lineEnd)
                            
                            if (segmentEnd > segmentStart) {
                                // 获取行边界
                                val lineLeft = layout!!.getLineLeft(lineIdx)
                                val lineTop = layout!!.getLineTop(lineIdx).toFloat()
                                val lineBottom = layout!!.getLineBottom(lineIdx).toFloat()
                                
                                // 计算文本位置
                                val startX = lineLeft + textPaint.measureText(lyrics.substring(lineStart, segmentStart))
                                val width = textPaint.measureText(lyrics.substring(segmentStart, segmentEnd))
                                
                                // 检查是否有自定义颜色
                                val hasCustomColors = lyricCharColors.any { it.key.first == i }
                                
                                if (hasCustomColors) {
                                    // 如果有自定义颜色，逐个字符绘制
                                    for (charPos in segmentStart until segmentEnd) {
                                        val charInLyric = charPos - startPos
                                        val charColor = lyricCharColors[Pair(i, charInLyric)] ?: playedColor
                                        
                                        val charStartX = startX + textPaint.measureText(lyrics.substring(segmentStart, charPos))
                                        val charWidth = textPaint.measureText(lyrics.substring(charPos, charPos + 1))
                                        
                                        canvas.save()
                                        canvas.clipRect(charStartX, lineTop, charStartX + charWidth, lineBottom)
                                        textPaint.color = charColor
                                        layout!!.draw(canvas)
                                        canvas.restore()
                                    }
                                } else {
                                    // 如果没有自定义颜色，使用已播放颜色绘制整个部分
                                    canvas.save()
                                    canvas.clipRect(startX, lineTop, startX + width, lineBottom)
                                    textPaint.color = playedColor
                                    layout!!.draw(canvas)
                                    canvas.restore()
                                }
                            }
                        }
                    }
                }
            }
            
            // 绘制当前播放中的歌词部分 - 处理可能跨行的情况
            if (currentProgress > 0 && currentLyricIndex < lyricItems.size) {
                // 计算当前歌词在文本中的起始位置和长度
                var currentLyricStart = 0
                for (i in 0 until currentLyricIndex) {
                    currentLyricStart += lyricItems[i].text.length + 1 // +1 是空格
                }
                val currentLyricLength = lyricItems[currentLyricIndex].text.length
                val currentLyricEnd = currentLyricStart + currentLyricLength
                
                // 计算当前进度对应的文本位置
                val progressPosition = currentLyricStart + (currentLyricLength * currentProgress).toInt()
                
                // 遍历所有行，查找包含当前歌词的行
                for (lineIdx in 0 until layout!!.lineCount) {
                    val lineStart = layout!!.getLineStart(lineIdx)
                    val lineEnd = layout!!.getLineEnd(lineIdx)
                    
                    // 检查这一行是否包含当前歌词的任何部分
                    if (!((progressPosition < lineStart) || (currentLyricStart >= lineEnd))) {
                        // 计算这一行中当前歌词的范围
                        val segmentStart = maxOf(currentLyricStart, lineStart)
                        val segmentEnd = minOf(progressPosition, lineEnd)
                        
                        if (segmentEnd >= segmentStart) {
                            // 获取行边界
                            val lineLeft = layout!!.getLineLeft(lineIdx)
                            val lineTop = layout!!.getLineTop(lineIdx).toFloat()
                            val lineBottom = layout!!.getLineBottom(lineIdx).toFloat()
                            console.log("[DEBUG] 绘制行[$lineIdx]: lineTop=$lineTop, lineBottom=$lineBottom, lineStart=$lineStart, lineEnd=$lineEnd")
                            
                            // 计算文本位置
                            val startX = lineLeft + textPaint.measureText(lyrics.substring(lineStart, segmentStart))
                            
                            // 获取当前行的文本
                            val lineText = lyrics.substring(lineStart, lineEnd)
                            
                            // 计算当前行中当前歌词的进度
                            val lineProgress = if (lineEnd >= progressPosition) {
                                // 如果当前行包含进度位置，计算该行的进度
                                (progressPosition - segmentStart).toFloat() / (lineEnd - segmentStart).toFloat()
                            } else {
                                // 如果当前行不包含进度位置，说明是完整的一行
                                1.0f
                            }
                            
                            // 计算当前行的宽度
                            val lineWidth = textPaint.measureText(lineText)
                            
                            // 计算当前进度对应的宽度
                            val progressWidth = lineWidth * lineProgress
                            
                            // 绘制当前行中已播放的歌词部分
                            canvas.save()
                            
                            // 计算最后一个字符的宽度
                            val lastCharWidth = if (progressPosition == currentLyricEnd) {
                                // 如果是当前句的最后一个字，使用实际字符宽度
                                textPaint.measureText(lyrics.substring(progressPosition - 1, progressPosition))
                            } else if (lineEnd == progressPosition) {
                                // 如果是行末的字，也使用实际字符宽度
                                textPaint.measureText(lyrics.substring(progressPosition - 1, progressPosition))
                            } else {
                                0f
                            }
                            
                            // 如果当前进度接近1.0，确保最后一个字完全变色
                            val finalWidth = if (currentProgress >= 0.99f) {
                                lineWidth + lastCharWidth
                            } else {
                                progressWidth + lastCharWidth
                            }
                            
                            // 确保裁剪区域不会超出当前行的范围
                            val clipWidth = minOf(finalWidth, lineWidth)
                            
                            canvas.clipRect(startX, lineTop, startX + clipWidth, lineBottom)
                            textPaint.color = playedColor
                            layout!!.draw(canvas)
                            canvas.restore()
                        }
                    }
                }
            }
            
            // 绘制评分（多行情况）
            if (lyricScores.isNotEmpty()) {
                // 保存当前文本画笔设置
                val originalTextSize = textPaint.textSize
                val originalColor = textPaint.color
                val originalBold = textPaint.isFakeBoldText
                
                // 设置评分文本的样式
                textPaint.textSize = originalTextSize * 0.8f // 稍微小一点的字体
                textPaint.isFakeBoldText = true // 加粗显示评分
                
                // 计算每个歌词项在整个文本中的起始位置和结束位置
                val lyricStartPositions = mutableListOf<Int>()
                val lyricEndPositions = mutableListOf<Int>()
                var position = 0
                
                for (i in lyricItems.indices) {
                    lyricStartPositions.add(position)
                    position += lyricItems[i].text.length
                    lyricEndPositions.add(position)
                    position += 1 // 空格或换行符
                }
                
                for (entry in lyricScores) {
                    val lyricIndex = entry.key
                    val scoreInfo = entry.value
                    
                    // 确保歌词索引有效
                    if (lyricIndex < 0 || lyricIndex >= lyricItems.size) continue
                    
                    // 找到包含这个歌词的最后一行
                    val lyricStart = lyricStartPositions[lyricIndex]
                    val lyricEnd = lyricEndPositions[lyricIndex]
                    var lastLineIdx = -1
                    
                    for (lineIdx in 0 until layout!!.lineCount) {
                        val lineStart = layout!!.getLineStart(lineIdx)
                        val lineEnd = layout!!.getLineEnd(lineIdx)
                        
                        // 检查这一行是否包含歌词的任何部分
                        if (!(lyricEnd < lineStart || lyricStart >= lineEnd)) {
                            lastLineIdx = lineIdx
                        }
                    }
                    
                    if (lastLineIdx >= 0) {
                        // 获取行边界
                        val lineEnd = layout!!.getLineEnd(lastLineIdx)
                        val lineLeft = layout!!.getLineLeft(lastLineIdx)
                        val lineTop = layout!!.getLineTop(lastLineIdx).toFloat()
                        val lineBottom = layout!!.getLineBottom(lastLineIdx).toFloat()
                        
                        // 计算评分文本的位置 - 在行尾添加
                        val scoreText = scoreInfo.score
                        textPaint.color = scoreInfo.color
                        
                        // 计算当前歌词项的实际结束位置，确保评分显示在歌词的最后
                        val lyricText = lyricItems[lyricIndex].text
                        val lyricWidth = textPaint.measureText(lyricText)
                        
                        // 找到歌词在当前行的起始位置
                        val lyricStartInLine = maxOf(lyricStart, layout!!.getLineStart(lastLineIdx))
                        val lyricEndInLine = minOf(lyricEnd, lineEnd)
                        val lyricTextInLine = lyrics.substring(lyricStartInLine, lyricEndInLine)
                        val lyricWidthInLine = textPaint.measureText(lyricTextInLine)
                        
                        // 计算歌词在当前行的结束位置
                        val lyricEndX = lineLeft + textPaint.measureText(lyrics.substring(layout!!.getLineStart(lastLineIdx), lyricEndInLine))
                        
                        // 设置评分X坐标为歌词结束位置加上更大的间距
                        // 增加间距，确保评分显示在歌词右侧足够远的位置
                        // 使用预留空间的位置来显示评分，而不是根据行尾位置计算
                        val spaceWidth = textPaint.measureText(" ")
                        val scoreWidth = textPaint.measureText(scoreText)
                        
                        // 计算评分的X坐标 - 使用预留空间的起始位置
                        // 预留空间起始位置 = adjustedWidth (即availableWidth - scoreSpace)
                        val adjustedWidth = availableWidth - (spaceWidth * 8 + scoreWidth)
                        val scoreX = adjustedWidth + spaceWidth * 2 // 在预留空间起始位置添加一些间距
                        
                        
                        // 计算文本高度和基线偏移，确保评分文本完全显示并垂直居中
                        val textHeight = textPaint.descent() - textPaint.ascent()
                        // 调整垂直位置，确保评分与当前行垂直居中对齐
                        val scoreY = lineTop + (lineBottom - lineTop) / 2 + textHeight / 4
                        
                        // 绘制一个背景矩形，使评分更加清晰
                        val bgPaint = Paint()
                        bgPaint.color = Color.argb(120, 255, 255, 255) // 半透明白色背景
                        // 使用评分文本的实际宽度来计算背景矩形的宽度，而不是使用整个歌词的宽度
                        val scoreTextWidth = textPaint.measureText(scoreText)
                        canvas.drawRect(scoreX - 2, scoreY - textPaint.textSize, scoreX + scoreTextWidth + 2, scoreY + textPaint.descent(), bgPaint)
                        
                        // 绘制评分文本
                        canvas.drawText(scoreText, scoreX, scoreY, textPaint)
                    }
                }
                
                // 恢复原始文本画笔设置
                textPaint.textSize = originalTextSize
                textPaint.color = originalColor
                textPaint.isFakeBoldText = originalBold
            }
            
            canvas.restore()
            return
        }
        
        // 单行显示逻辑 - 修复强制换行模式下的Y坐标计算
        val x = when (textAlignment) {
            TextAlignment.LEFT -> 0f
            TextAlignment.RIGHT -> width - textWidth
            else -> (width - textWidth) / 2 // CENTER
        }
        
        // 计算每个歌词项的位置
        calculateLyricPositions(x)
        
        console.log("[DEBUG] 单行显示模式: isForceLineBreak=$isForceLineBreak, lyricItems.size=${lyricItems.size}")
        
        // 绘制所有歌词，根据播放状态决定颜色
        for (i in lyricItems.indices) {
            val item = lyricItems[i]
            val position = lyricPositions[i]
            
            // 计算Y坐标 - 在强制换行模式下，每个歌词应该在不同的行
            val y = if (isForceLineBreak && lyricItems.size > 1) {
                // 强制换行模式：每个歌词在不同行
                val lineHeight = textPaint.textSize * 1.5f // 行高为字体大小的1.5倍
                val startY = paddingTop + textPaint.textSize
                startY + (i * lineHeight)
            } else if (lyricItems.size == 1) {
                // 单个歌词：居中显示
                paddingTop + textPaint.textSize
            } else {
                // 非强制换行模式：所有歌词在同一行
                height / 2f
            }
            
            console.log("[DEBUG] 歌词[$i]: text='${item.text}', startX=${position.startX}, y=$y")
            
            // 设置颜色：已播放完的歌词用已播放颜色，未播放的用未播放颜色
            if (i < currentLyricIndex) {
                // 已播放完毕的歌词，使用已播放颜色
                textPaint.color = playedColor
            } else if (i > currentLyricIndex) {
                // 未播放的歌词，使用未播放颜色
                textPaint.color = unplayedColor
            } else {
                // 当前播放的歌词，先用未播放颜色绘制完整歌词
                textPaint.color = unplayedColor
            }
            
            // 绘制歌词
            canvas.drawText(item.text, position.startX, y, textPaint)
            
            // 在强制换行模式下不绘制空格，因为每个歌词在不同行
            if (!isForceLineBreak && i < lyricItems.size - 1) {
                val spaceX = position.startX + position.width
                canvas.drawText(" ", spaceX, y, textPaint)
            }
        }
        
        // 绘制当前播放的歌词的已播放部分
        if (currentLyricIndex < lyricItems.size && currentProgress > 0) {
            val position = lyricPositions[currentLyricIndex]
            val playedWidth = position.width * currentProgress
            
            // 重新计算当前播放歌词的Y坐标，与上面的逻辑保持一致
            val currentY = if (isForceLineBreak && lyricItems.size > 1) {
                // 强制换行模式：每个歌词在不同行
                val lineHeight = textPaint.textSize * 1.5f
                val startY = paddingTop + textPaint.textSize
                startY + (currentLyricIndex * lineHeight)
            } else if (lyricItems.size == 1) {
                // 单个歌词：居中显示
                paddingTop + textPaint.textSize
            } else {
                // 非强制换行模式：所有歌词在同一行
                height / 2f
            }
            
            canvas.save()
            canvas.clipRect(position.startX, 0f, position.startX + playedWidth, height.toFloat())
            textPaint.color = playedColor
            canvas.drawText(lyricItems[currentLyricIndex].text, position.startX, currentY, textPaint)
            canvas.restore()
        }
        
        // 绘制评分（单行情况）
        if (lyricScores.isNotEmpty()) {
            // 保存当前文本画笔设置
            val originalTextSize = textPaint.textSize
            val originalColor = textPaint.color
            val originalBold = textPaint.isFakeBoldText
            
            // 设置评分文本的样式
            textPaint.textSize = originalTextSize * 0.8f // 稍微小一点的字体
            textPaint.isFakeBoldText = true // 加粗显示评分
            
            for (entry in lyricScores) {
                val lyricIndex = entry.key
                val scoreInfo = entry.value
                
                // 确保歌词索引有效且有位置信息
                if (lyricIndex < 0 || lyricIndex >= lyricItems.size || lyricIndex >= lyricPositions.size) continue
                
                val position = lyricPositions[lyricIndex]
                textPaint.color = scoreInfo.color
                
                // 计算评分文本的位置
                val scoreText = scoreInfo.score
                val scoreWidth = textPaint.measureText(scoreText)
                
                // 计算评分应该显示的位置 - 在歌词文本的右侧
                // 使用歌词的实际宽度(不含评分空间)，然后添加间距
                // 注意：这里使用position.width而不是重新计算actualLyricWidth
                // 因为position.width已经是歌词的准确宽度，与calculateLyricPositions中计算的一致
                
                // 使用Rect获取更准确的空格宽度
                val spaceText = " "
                val spaceBounds = Rect()
                textPaint.getTextBounds(spaceText, 0, spaceText.length, spaceBounds)
                val spaceWidth = maxOf(spaceBounds.width().toFloat(), textPaint.textSize * 0.25f) // 确保空格宽度至少为字体大小的25%
                
                // 使用固定的空格数量（20个空格宽度）作为间距，与calculateLyricPositions保持一致
                val scoreX = position.startX + position.width + spaceWidth * 20
                
                
                // 重新计算评分的Y坐标，与歌词的Y坐标保持一致
                val scoreY = if (isForceLineBreak && lyricItems.size > 1) {
                    // 强制换行模式：每个歌词在不同行
                    val lineHeight = textPaint.textSize * 1.5f
                    val startY = paddingTop + textPaint.textSize
                    startY + (lyricIndex * lineHeight)
                } else if (lyricItems.size == 1) {
                    // 单个歌词：居中显示
                    paddingTop + textPaint.textSize
                } else {
                    // 非强制换行模式：所有歌词在同一行
                    height / 2f
                }
                
                // 微调垂直位置，确保评分与歌词对齐
                val textHeight = textPaint.descent() - textPaint.ascent()
                val adjustedScoreY = scoreY - textHeight / 4
                
                // 绘制一个背景矩形，使评分更加清晰
                val bgPaint = Paint()
                bgPaint.color = Color.argb(120, 255, 255, 255) // 半透明白色背景
                canvas.drawRect(scoreX - 2, adjustedScoreY - textPaint.textSize, scoreX + scoreWidth + 2, adjustedScoreY + textPaint.descent(), bgPaint)
                
                // 绘制评分文本
                canvas.drawText(scoreText, scoreX, adjustedScoreY, textPaint)
            }
            
            // 恢复原始文本画笔设置
            textPaint.textSize = originalTextSize
            textPaint.color = originalColor
            textPaint.isFakeBoldText = originalBold
        }
        
        // 绘制自定义颜色的字符（单行情况）
        if (charColors.isNotEmpty()) {
            for (entry in charColors) {
                val charPos = entry.key
                val charColor = entry.value
                
                // 确保字符位置在有效范围内
                if (charPos < 0 || charPos >= lyrics.length) continue
                
                // 计算字符在哪个歌词项中
                var currentPos = 0
                var foundLyricIndex = -1
                var positionInLyric = -1
                
                for (i in lyricItems.indices) {
                    val lyricLength = lyricItems[i].text.length
                    if (charPos >= currentPos && charPos < currentPos + lyricLength) {
                        foundLyricIndex = i
                        positionInLyric = charPos - currentPos
                        break
                    }
                    currentPos += lyricLength + 1 // +1 是空格
                }
                
                if (foundLyricIndex != -1 && foundLyricIndex < lyricPositions.size) {
                    val position = lyricPositions[foundLyricIndex]
                    val charStartX = position.startX + textPaint.measureText(lyricItems[foundLyricIndex].text.substring(0, positionInLyric))
                    val charWidth = textPaint.measureText(lyricItems[foundLyricIndex].text.substring(positionInLyric, positionInLyric + 1))
                    
                    canvas.save()
                    canvas.clipRect(charStartX, 0f, charStartX + charWidth, height.toFloat())
                    textPaint.color = charColor
                    canvas.drawText(lyricItems[foundLyricIndex].text, position.startX, y, textPaint)
                    canvas.restore()
                }
            }
        }
        
        // 绘制基于歌词项的自定义颜色字符（单行情况）
        if (lyricCharColors.isNotEmpty()) {
            for (entry in lyricCharColors) {
                val lyricIndex = entry.key.first
                val charPosInLyric = entry.key.second
                val charColor = entry.value
                
                // 确保歌词项索引和字符位置在有效范围内
                if (lyricIndex < 0 || lyricIndex >= lyricItems.size) continue
                if (charPosInLyric < 0 || charPosInLyric >= lyricItems[lyricIndex].text.length) continue
                if (lyricIndex >= lyricPositions.size) continue
                
                val position = lyricPositions[lyricIndex]
                val charStartX = position.startX + textPaint.measureText(lyricItems[lyricIndex].text.substring(0, charPosInLyric))
                val charWidth = textPaint.measureText(lyricItems[lyricIndex].text.substring(charPosInLyric, charPosInLyric + 1))
                
                canvas.save()
                canvas.clipRect(charStartX, 0f, charStartX + charWidth, height.toFloat())
                textPaint.color = charColor
                canvas.drawText(lyricItems[lyricIndex].text, position.startX, y, textPaint)
                canvas.restore()
            }
        }
        
        // 绘制自定义颜色的字符（单行情况）
        if (charColors.isNotEmpty()) {
            // 计算文本在整个字符串中的起始位置
            var charStartPos = 0
            
            for (i in lyricItems.indices) {
                val item = lyricItems[i]
                val position = lyricPositions[i]
                val itemLength = item.text.length
                
                // 逐个字符检查是否有自定义颜色
                for (j in 0 until itemLength) {
                    val globalCharPos = charStartPos + j
                    
                    if (charColors.containsKey(globalCharPos)) {
                        // 计算字符位置
                        val charX = position.startX + textPaint.measureText(item.text.substring(0, j))
                        val charWidth = textPaint.measureText(item.text.substring(j, j + 1))
                        
                        // 绘制自定义颜色的字符
                        canvas.save()
                        canvas.clipRect(charX, 0f, charX + charWidth, height.toFloat())
                        textPaint.color = charColors[globalCharPos]!!
                        canvas.drawText(item.text, position.startX, y, textPaint)
                        canvas.restore()
                    }
                }
                
                // 更新下一个歌词项的起始位置
                charStartPos += itemLength + 1 // +1 是空格
            }
        }
        
        // 注意：现在使用弹窗代替按钮，不再在这里绘制按钮
    }

    // 根据评分设置文字颜色
    fun setLyricColorsByScore(analysisArray: JSONArray, fullText: String): KaraokeTextView {
        try {
            // 遍历分析数组
            for (i in 0 until analysisArray.length()) {
                val item = analysisArray.getJSONObject(i)
                val content = item.getString("content")
                val totalScore = item.getDouble("total_score")
                
                // 在完整文本中查找内容的位置
                val startIndex = fullText.indexOf(content)
                if (startIndex != -1) {
                    // 根据分数设置颜色
                    val color = when {
                        totalScore >= 90 -> "#5A9F32"
                        totalScore >= 80 -> "#6694DF"
                        totalScore >= 60 -> "#FA9600"
                        else -> "#E54E4E"
                    }
                    
                    // 设置文字颜色
                    setCharsColorInLyric(0, startIndex, startIndex + content.length, Color.parseColor(color))
                }
            }
        } catch (e: Exception) {
            console.error("设置歌词颜色失败: ${e.message}")
        }
        return this
    }
    
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // 停止所有动画
        lyricAnimator?.cancel()
        lyricAnimator = null
        
        // 释放音频资源
        releaseMediaPlayer()
        
        // 取消延迟恢复滚动
        cancelDelayedResumeScroll()
        
        // 停止滚动动画
        scrollAnimator?.cancel()
    }
    
    // 查找点击位置对应的歌词索引
    private fun findClickedLyric(x: Float, y: Float): Int {
        // console.log("findClickedLyric: 开始查找点击坐标(${x}, ${y})对应的歌词")
        // console.log("findClickedLyric: lyrics.length=${lyrics.length}, lyricItems.size=${lyricItems.size}, lyricPositions.size=${lyricPositions.size}")
        
        if (lyrics.isEmpty() || lyricItems.isEmpty() || lyricPositions.isEmpty()) {
            // console.log("findClickedLyric: 数据为空，返回-1")
            return -1
        }
        
        val availableWidth = if (maxWidth > 0) minOf(maxWidth, width) else width
        val textWidth = textPaint.measureText(lyrics)
        // console.log("findClickedLyric: availableWidth=$availableWidth, textWidth=$textWidth, maxWidth=$maxWidth, width=$width")
        
        // 如果文本需要折行显示
        if (maxWidth > 0 && textWidth > availableWidth) {
            // console.log("findClickedLyric: 使用多行布局检测")
            // 使用StaticLayout来处理折行文本的点击检测
            val layout = cachedLayout
            if (layout == null) {
                // console.log("findClickedLyric: cachedLayout为null，返回-1")
                return -1
            }
            
            // 调整点击坐标，考虑滚动偏移
            val adjustedX = x - (width - availableWidth) / 2f
            val adjustedY = y + scrollY - (if (lyricItems.size == 1) paddingTop.toFloat() else 0f)
            // console.log("findClickedLyric: 调整后坐标 adjustedX=$adjustedX, adjustedY=$adjustedY, scrollY=$scrollY")
            
            // 检查点击是否在文本区域内
            // console.log("findClickedLyric: 文本区域检查 layout.height=${layout.height}")
            if (adjustedX < 0 || adjustedX > availableWidth || adjustedY < 0 || adjustedY > layout.height) {
                // console.log("findClickedLyric: 点击位置超出文本区域，返回-1")
                return -1
            }
            
            // 获取点击位置对应的字符索引
            val line = layout.getLineForVertical(adjustedY.toInt())
            // console.log("findClickedLyric: 点击所在行=$line, 总行数=${layout.lineCount}")
            if (line < 0 || line >= layout.lineCount) {
                // console.log("findClickedLyric: 点击行超出范围，返回-1")
                return -1
            }
            
            val charIndex = layout.getOffsetForHorizontal(line, adjustedX)
            // console.log("findClickedLyric: 点击位置字符索引=$charIndex")
            
            // 根据字符索引找到对应的歌词项
            var currentCharIndex = 0
            for (i in lyricItems.indices) {
                val itemLength = lyricItems[i].text.length
                // console.log("findClickedLyric: 歌词$i 字符范围 [$currentCharIndex, ${currentCharIndex + itemLength}), 点击字符索引=$charIndex")
                if (charIndex >= currentCharIndex && charIndex < currentCharIndex + itemLength) {
                    // console.log("findClickedLyric: 在多行布局中找到匹配的歌词索引=$i")
                    return i
                }
                currentCharIndex += itemLength + 1 // +1 是分隔符（空格或换行）
            }
        } else {
            // console.log("findClickedLyric: 使用单行布局检测")
            // 单行文本的点击检测
            val baseX = when (textAlignment) {
                TextAlignment.LEFT -> 0f
                TextAlignment.RIGHT -> width - textWidth
                else -> (width - textWidth) / 2 // CENTER
            }
            // console.log("findClickedLyric: baseX=$baseX, textAlignment=$textAlignment")
            
            // 检查点击是否在文本的垂直范围内
            val textTop = paddingTop.toFloat()
            val textBottom = textTop + textPaint.textSize
            // console.log("findClickedLyric: 垂直范围检查 textTop=$textTop, textBottom=$textBottom, clickY=$y")
            
            if (y < textTop || y > textBottom) {
                // console.log("findClickedLyric: 点击位置超出垂直范围，返回-1")
                return -1
            }
            
            // 检查点击是否在某个歌词项的水平范围内
            // console.log("findClickedLyric: 开始检查水平范围，lyricPositions.size=${lyricPositions.size}")
            for (i in lyricPositions.indices) {
                val position = lyricPositions[i]
                val lyricLeft = position.startX
                val lyricRight = position.startX + position.width
                // console.log("findClickedLyric: 歌词$i 范围 left=$lyricLeft, right=$lyricRight, clickX=$x")
                
                if (x >= lyricLeft && x <= lyricRight) {
                    // console.log("findClickedLyric: 找到匹配的歌词索引=$i")
                    return i
                }
            }
        }
        
        // console.log("findClickedLyric: 没有找到匹配的歌词，返回-1")
        return -1
    }
    
    // 获取当前是否处于暂停状态
    fun getIsPaused(): Boolean {
        return isPaused
    }
    
    // 跳转到指定歌词并开始播放
    private fun jumpToLyricInternal(lyricIndex: Int) {
        if (lyricIndex < 0 || lyricIndex >= lyricItems.size) {
            return
        }
        
        // console.log("jumpToLyric: 跳转到歌词索引 $lyricIndex")
        
        // 停止当前动画和音频
        lyricAnimator?.cancel()
        lyricAnimator = null
        releaseMediaPlayer()
        
        // 重置状态
        isPaused = false
        isAnimationFinished = true
        
        // 设置新的歌词索引和进度
        currentLyricIndex = lyricIndex
        currentProgress = 0f
        
        // 更新滚动位置到新的歌词
        updateScrollPosition()
        
        // 强制重绘
        invalidate()
        
        // 通知播放状态变化：从指定歌词开始播放
        if (lyricIndex >= 0 && lyricIndex < lyricItems.size) {
            onPlaybackStateChangedCallback?.invoke(true, lyricIndex, lyricItems[lyricIndex].text, true)
        }
        
        // 开始播放新的歌词
        startLyricAnimation(true)
    }
}