package uts.sdk.modules.wjAudioPlayer

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import android.view.animation.DecelerateInterpolator
import java.io.IOException
import io.dcloud.uts.console

class AudioPlayerView : View {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler(Looper.getMainLooper())
    private var progressUpdateRunnable: Runnable? = null
    
    // 保存当前音频源URL，用于reset方法
    private var currentAudioUrl: String? = null

    // 进度条样式属性
    private var progressColor = Color.parseColor("#4e6ef2")
    private var progressBackgroundColor = Color.parseColor("#e0e0e0")
    private var progressHeight = 8f
    private var progressRadius = 4f

    // 音频信息
    private var currentTime = 0
    private var duration = 0
    private var progress = 0f
    
    // 性能优化相关变量
    private var lastUpdateTime = 0L
    private var lastProgress = -1f
    private val UPDATE_THRESHOLD = 0.002f // 进度变化阈值，优化性能
    
    // 动画相关变量
    private var progressAnimator: ValueAnimator? = null
    private var animatedProgress = 0f // 当前动画进度值
    private var targetProgress = 0f // 目标进度值
    private var isAnimating = false // 动画状态标记
    
    // 拖动相关变量
    private var isSeekEnabled = true // 是否启用拖动功能
    private var isDragging = false // 是否正在拖动
    private var wasPlayingBeforeDrag = false // 拖动前是否在播放
    
    // 播放完成后进度条归零控制
    private var resetProgressOnComplete = true // 播放完成后是否自动归零进度条
    
    // 播放完成状态跟踪
    private var isPlaybackCompleted = false // 标记音频是否已播放完毕

    // 回调函数
    private var onPlaybackStateChangedCallback: ((Boolean) -> Unit)? = null
    private var onProgressChangedCallback: ((Int, Int, Float) -> Unit)? = null
    private var onPlaybackCompletedCallback: (() -> Unit)? = null
    private var onErrorCallback: ((String) -> Unit)? = null

    // 画笔
    private val backgroundPaint = Paint().apply { flags = Paint.ANTI_ALIAS_FLAG }
    private val progressPaint = Paint().apply { flags = Paint.ANTI_ALIAS_FLAG }

    private fun init() {
        setupPaints()
        setupProgressUpdateRunnable()
        
        // 初始化动画状态
        animatedProgress = 0f
        targetProgress = 0f
        isAnimating = false
    }

    private fun setupPaints() {
        backgroundPaint.color = progressBackgroundColor
        backgroundPaint.style = Paint.Style.FILL

        progressPaint.color = progressColor
        progressPaint.style = Paint.Style.FILL
    }

    private fun setupProgressUpdateRunnable() {
        progressUpdateRunnable = object : Runnable {
            override fun run() {
                if (mediaPlayer?.isPlaying == true) {
                    updateProgress()
                    handler.postDelayed(this, 200) // 优化：每200ms更新一次，减少CPU占用
                }
            }
        }
    }

    private fun updateProgress() {
        mediaPlayer?.let { player: MediaPlayer ->
            val currentTimeMs = player.currentPosition
            val durationMs = player.duration
            val newProgress = if (durationMs > 0) currentTimeMs.toFloat() / durationMs.toFloat() else 0f
            
            // 性能优化：只在进度有明显变化时才更新UI
            val progressDiff = kotlin.math.abs(newProgress - lastProgress)
            val currentTime = System.currentTimeMillis()
            
            if (progressDiff > UPDATE_THRESHOLD || currentTime - lastUpdateTime > 1000) {
                this@AudioPlayerView.currentTime = currentTimeMs
                this@AudioPlayerView.duration = durationMs
                this@AudioPlayerView.progress = newProgress
                
                // 动态修正时长显示，确保与实际音频长度精确匹配
                val originalDurationSeconds = durationMs / 1000
                // 使用向上取整，确保显示时长不少于实际时长
                val correctedDurationSeconds = kotlin.math.max(kotlin.math.ceil(durationMs / 1000.0).toInt(), 1)
                // 按比例修正当前时间，保持播放进度的准确性
                val correctedCurrentSeconds = kotlin.math.min(
                    kotlin.math.round(currentTimeMs.toDouble() / durationMs * correctedDurationSeconds).toInt(),
                    correctedDurationSeconds
                ) // 确保当前时间不超过总时长
                
                // 触发进度回调，使用修正后的时长
                onProgressChangedCallback?.invoke(correctedCurrentSeconds, correctedDurationSeconds, newProgress)
                
                // 使用补间动画更新进度条，而不是直接重绘
                if (!isAnimating || kotlin.math.abs(newProgress - targetProgress) > 0.01f) {
                    // 正常播放时使用较短的动画时间，保持流畅性
                    val animationDuration = if (kotlin.math.abs(newProgress - animatedProgress) > 0.05f) 120L else 80L
                    startProgressAnimation(animatedProgress, newProgress, animationDuration)
                }
                
                lastProgress = newProgress
                lastUpdateTime = currentTime
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawProgressBar(canvas)
    }
    
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isSeekEnabled) {
            return super.onTouchEvent(event)
        }
        
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 检查触摸点是否在进度条区域内
                val touchY = event.y
                val progressBarTop = (height - progressHeight) / 2
                val progressBarBottom = progressBarTop + progressHeight
                
                if (touchY >= progressBarTop && touchY <= progressBarBottom) {
                    isDragging = true
                    wasPlayingBeforeDrag = mediaPlayer?.isPlaying ?: false
                    
                    // 暂停播放和进度更新
                    if (wasPlayingBeforeDrag) {
                        mediaPlayer?.pause()
                        stopProgressUpdates()
                    }
                    
                    // 立即更新进度到触摸位置
                    updateProgressFromTouch(event.x)
                    return true
                }
            }
            
            MotionEvent.ACTION_MOVE -> {
                if (isDragging) {
                    updateProgressFromTouch(event.x)
                    return true
                }
            }
            
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (isDragging) {
                    // 检查是否为点击（没有移动）
                    val touchY = event.y
                    val progressBarTop = (height - progressHeight) / 2
                    val progressBarBottom = progressBarTop + progressHeight
                    
                    if (touchY >= progressBarTop && touchY <= progressBarBottom) {
                        // 点击功能：直接跳转到点击位置
                        updateProgressFromTouch(event.x)
                    }
                    
                    isDragging = false
                    
                    // 恢复播放状态
                    if (wasPlayingBeforeDrag) {
                        mediaPlayer?.start()
                        startProgressUpdates()
                    }
                    
                    return true
                }
            }
        }
        
        return super.onTouchEvent(event)
    }
    
    /**
     * 根据触摸位置更新播放进度
     */
    private fun updateProgressFromTouch(touchX: Float) {
        val newProgress = (touchX / width).coerceIn(0f, 1f)
        val newPosition = (newProgress * (mediaPlayer?.duration ?: 0)).toInt()
        
        // 更新MediaPlayer位置
        mediaPlayer?.seekTo(newPosition)
        
        // 重置播放完成状态，因为用户拖动进度条表示要继续播放
        isPlaybackCompleted = false
        
        // 更新进度显示
        this.progress = newProgress
        this.currentTime = newPosition
        
        // 使用快速动画更新进度条显示
        startProgressAnimation(animatedProgress, newProgress, 50)
        
        // 触发进度回调
        val durationMs = mediaPlayer?.duration ?: 0
        val correctedDurationSeconds = kotlin.math.max(kotlin.math.ceil(durationMs / 1000.0).toInt(), 1)
        val correctedCurrentSeconds = kotlin.math.min(
            kotlin.math.round(newPosition.toDouble() / durationMs * correctedDurationSeconds).toInt(),
            correctedDurationSeconds
        )
        
        onProgressChangedCallback?.invoke(correctedCurrentSeconds, correctedDurationSeconds, newProgress)
    }
    
    /**
     * 启动进度条补间动画
     * @param fromProgress 起始进度值
     * @param toProgress 目标进度值
     * @param duration 动画持续时间（毫秒）
     */
    private fun startProgressAnimation(fromProgress: Float, toProgress: Float, duration: Long = 150) {
        // 取消之前的动画
        progressAnimator?.cancel()
        
        // 如果进度变化很小，直接设置不使用动画
        if (kotlin.math.abs(toProgress - fromProgress) < 0.005f) {
            animatedProgress = toProgress
            targetProgress = toProgress
            isAnimating = false
            invalidateProgressBar()
            return
        }
        
        // 创建新的动画
        progressAnimator = ValueAnimator.ofFloat(fromProgress, toProgress).apply {
            this.duration = duration
            
            // 根据动画类型选择合适的插值器
            interpolator = if (toProgress >= 1.0f) {
                // 播放完成时使用减速插值器，提供更自然的结束效果
                DecelerateInterpolator(1.5f)
            } else if (kotlin.math.abs(toProgress - fromProgress) > 0.1f) {
                // 大幅度进度变化时使用减速插值器
                DecelerateInterpolator(1.2f)
            } else {
                // 小幅度进度变化时使用线性插值器
                LinearInterpolator()
            }
            
            addUpdateListener { animator ->
                animatedProgress = animator.animatedValue as Float
                invalidateProgressBar()
            }
            
            addListener(object : android.animation.Animator.AnimatorListener {
                override fun onAnimationStart(animation: android.animation.Animator) {
                    isAnimating = true
                }
                
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    isAnimating = false
                    animatedProgress = toProgress
                    targetProgress = toProgress
                }
                
                override fun onAnimationCancel(animation: android.animation.Animator) {
                    isAnimating = false
                    // 动画被取消时，确保进度值正确设置
                    animatedProgress = targetProgress
                }
                
                override fun onAnimationRepeat(animation: android.animation.Animator) {}
            })
            
            start()
        }
        
        targetProgress = toProgress
    }
    
    /**
     * 只重绘进度条区域
     */
    private fun invalidateProgressBar() {
        val progressBarTop = (height - progressHeight) / 2
        val progressBarBottom = progressBarTop + progressHeight
        invalidate(0, progressBarTop.toInt(), width, progressBarBottom.toInt())
    }

    // 缓存绘制相关的计算结果
    private var cachedWidth = 0f
    private var cachedHeight = 0f
    private var cachedProgressBarTop = 0f
    private var cachedProgressBarBottom = 0f
    private var backgroundRect: RectF? = null
    
    private fun drawProgressBar(canvas: Canvas) {
        val currentWidth = width.toFloat()
        val currentHeight = height.toFloat()
        
        // 只在尺寸变化时重新计算
        if (currentWidth != cachedWidth || currentHeight != cachedHeight) {
            cachedWidth = currentWidth
            cachedHeight = currentHeight
            cachedProgressBarTop = (currentHeight - progressHeight) / 2
            cachedProgressBarBottom = cachedProgressBarTop + progressHeight
            backgroundRect = RectF(0f, cachedProgressBarTop, currentWidth, cachedProgressBarBottom)
        }
        
        // 绘制背景
        backgroundRect?.let { rect ->
            canvas.drawRoundRect(rect, progressRadius, progressRadius, backgroundPaint)
        }
        
        // 绘制进度 - 使用动画进度值而不是直接的progress值
        val currentProgress = if (isAnimating) animatedProgress else progress
        if (currentProgress > 0) {
            val progressWidth = currentWidth * currentProgress
            val progressRect = RectF(0f, cachedProgressBarTop, progressWidth, cachedProgressBarBottom)
            canvas.drawRoundRect(progressRect, progressRadius, progressRadius, progressPaint)
        }
    }

    // 设置音频源
    fun setAudioSource(url: String) {
        try {
            // 保存当前音频URL
            currentAudioUrl = url
            releaseMediaPlayer()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(context, Uri.parse(url))
                prepareAsync()
                setOnPreparedListener { mp: MediaPlayer ->
                    this@AudioPlayerView.duration = mp.duration
                    
                    // 重置播放完成状态
                    isPlaybackCompleted = false
                    
                    // 重置所有进度相关状态，确保每次播放都从0开始
                    this@AudioPlayerView.currentTime = 0
                    this@AudioPlayerView.progress = 0f
                    
                    // 取消任何正在进行的进度条动画
                    progressAnimator?.cancel()
                    
                    // 重置动画相关状态
                    animatedProgress = 0f
                    targetProgress = 0f
                    isAnimating = false
                    
                    // 重置缓存值
                    lastProgress = 0f
                    lastUpdateTime = System.currentTimeMillis()
                    
                    // 立即更新进度条显示
                    invalidateProgressBar()
                    
                    
                    // 触发进度回调，确保UI显示正确的初始状态
                    onProgressChangedCallback?.invoke(0, this@AudioPlayerView.duration / 1000, 0f)
                }
                setOnCompletionListener { _: MediaPlayer ->
                    stopProgressUpdates()
                    
                    // 标记播放已完成
                    isPlaybackCompleted = true
                    
                    // 先确保进度条走满100%，然后在动画完成后执行后续操作
                    this@AudioPlayerView.currentTime = this@AudioPlayerView.duration
                    this@AudioPlayerView.progress = 1.0f
                    
                    
                    // 创建进度条到100%的动画，动画完成后执行后续操作
                    progressAnimator?.cancel()
                    progressAnimator = ValueAnimator.ofFloat(animatedProgress, 1.0f).apply {
                        duration = 200L // 较短的动画时间，确保快速到达100%
                        interpolator = LinearInterpolator()
                        
                        addUpdateListener { animator ->
                            animatedProgress = animator.animatedValue as Float
                            invalidateProgressBar()
                        }
                        
                        addListener(object : android.animation.Animator.AnimatorListener {
                            override fun onAnimationStart(animation: android.animation.Animator) {
                                isAnimating = true
                            }
                            
                            override fun onAnimationEnd(animation: android.animation.Animator) {
                                isAnimating = false
                                animatedProgress = 1.0f
                                targetProgress = 1.0f
                                
                                
                                // 更新缓存值
                                lastProgress = 1.0f
                                lastUpdateTime = System.currentTimeMillis()
                                
                                // 先触发播放状态变化和完成回调
                                onPlaybackStateChangedCallback?.invoke(false)
                                onPlaybackCompletedCallback?.invoke()
                                
                                // 根据设置决定是否需要归零进度条
                                if (resetProgressOnComplete) {
                                    
                                    // 延迟一小段时间后开始归零动画，让用户看到100%的状态
                                    handler.postDelayed({
                                        this@AudioPlayerView.currentTime = 0
                                        this@AudioPlayerView.progress = 0f
                                        
                                        // 使用动画过渡到0%进度
                                        startProgressAnimation(1.0f, 0f, 300)
                                        
                                        // 更新缓存值
                                        lastProgress = 0f
                                        lastUpdateTime = System.currentTimeMillis()
                                    }, 500) // 延迟500ms让用户看到100%状态
                                } else {
                                }
                            }
                            
                            override fun onAnimationCancel(animation: android.animation.Animator) {
                                isAnimating = false
                            }
                            
                            override fun onAnimationRepeat(animation: android.animation.Animator) {}
                        })
                        
                        start()
                    }
                }
                setOnErrorListener { _, what: Int, extra: Int ->
                    onErrorCallback?.invoke("MediaPlayer error: what=$what, extra=$extra")
                    true
                }
            }
        } catch (e: IOException) {
            onErrorCallback?.invoke("Failed to set audio source: ${e.message}")
        }
    }

    // 播放音频
    fun play() {
        mediaPlayer?.let { player: MediaPlayer ->
            if (!player.isPlaying) {
                // 检查播放完成状态，决定播放行为
                if (isPlaybackCompleted) {
                    // 播放已完成，从头开始重新播放
                    console.log("AudioPlayer", "播放已完成，从头开始重新播放")
                    
                    // 重置MediaPlayer位置到0
                    player.seekTo(0)
                    
                    // 重置播放完成状态
                    isPlaybackCompleted = false
                    
                    // 重置所有进度相关状态
                    this@AudioPlayerView.currentTime = 0
                    this@AudioPlayerView.progress = 0f
                    
                    // 取消任何正在进行的动画
                    progressAnimator?.cancel()
                    
                    // 重置动画状态
                    animatedProgress = 0f
                    targetProgress = 0f
                    isAnimating = false
                    
                    // 重置缓存值
                    lastProgress = 0f
                    lastUpdateTime = System.currentTimeMillis()
                    
                    // 立即更新进度条显示
                    invalidateProgressBar()
                    
                    // 触发进度回调，确保UI显示正确的状态
                    onProgressChangedCallback?.invoke(0, duration / 1000, 0f)
                } else {
                    // 播放未完成，继续从当前进度播放
                    val currentPosition = player.currentPosition
                    console.log("AudioPlayer", "继续从当前进度播放，位置: ${currentPosition}ms")
                    
                    // 不重置位置，保持当前进度
                    // 确保进度显示与MediaPlayer位置同步
                    val currentProgress = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f
                    this@AudioPlayerView.currentTime = currentPosition
                    this@AudioPlayerView.progress = currentProgress
                    
                    // 更新动画进度值以保持同步
                    animatedProgress = currentProgress
                    targetProgress = currentProgress
                    
                    // 更新缓存值
                    lastProgress = currentProgress
                    lastUpdateTime = System.currentTimeMillis()
                    
                    // 立即更新进度条显示
                    invalidateProgressBar()
                    
                    // 触发进度回调，确保UI显示正确的状态
                    val correctedDurationSeconds = kotlin.math.max(kotlin.math.ceil(duration / 1000.0).toInt(), 1)
                    val correctedCurrentSeconds = kotlin.math.min(
                        kotlin.math.round(currentPosition.toDouble() / duration * correctedDurationSeconds).toInt(),
                        correctedDurationSeconds
                    )
                    onProgressChangedCallback?.invoke(correctedCurrentSeconds, correctedDurationSeconds, currentProgress)
                }
                
                // 开始播放
                player.start()
                startProgressUpdates()
                onPlaybackStateChangedCallback?.invoke(true)
            }
        }
    }

    // 暂停音频
    fun pause() {
        mediaPlayer?.let { player: MediaPlayer ->
            if (player.isPlaying) {
                player.pause()
                stopProgressUpdates()
                onPlaybackStateChangedCallback?.invoke(false)
            }
        }
    }

    // 停止音频
    fun stop() {
        mediaPlayer?.let { player: MediaPlayer ->
            if (player.isPlaying) {
                player.stop()
            }
            stopProgressUpdates()
            this@AudioPlayerView.currentTime = 0
            this@AudioPlayerView.progress = 0f
            
            // 重置播放完成状态
            isPlaybackCompleted = false
            
            // 使用动画过渡到0进度
            startProgressAnimation(animatedProgress, 0f, 200)
            
            // 更新缓存值
            lastProgress = 0f
            lastUpdateTime = System.currentTimeMillis()
            
            onPlaybackStateChangedCallback?.invoke(false)
        }
    }

    // 重置音频播放器，使其在stop()后能够重新播放
    fun reset() {
        
        // 停止当前播放
        mediaPlayer?.let { player ->
            if (player.isPlaying) {
                player.stop()
            }
        }
        
        // 停止进度更新
        stopProgressUpdates()
        
        // 取消所有动画
        progressAnimator?.cancel()
        progressAnimator = null
        isAnimating = false
        
        // 重置所有进度相关状态
        currentTime = 0
        progress = 0f
        animatedProgress = 0f
        targetProgress = 0f
        lastProgress = -1f
        lastUpdateTime = 0L
        
        // 重置缓存值
        cachedWidth = 0f
        cachedHeight = 0f
        cachedProgressBarTop = 0f
        cachedProgressBarBottom = 0f
        backgroundRect = null
        
        // 重置拖动状态
        isDragging = false
        wasPlayingBeforeDrag = false
        
        // 重置播放完成状态
        isPlaybackCompleted = false
        
        Log.d("AudioPlayerView", "reset() - 重置所有状态变量")
        
        // 立即更新进度条显示
        invalidateProgressBar()
        
        // 触发进度变化回调，通知外部进度已重置
        onProgressChangedCallback?.invoke(currentTime, duration, progress)
        
        // 重新设置音频源（如果有的话）
        currentAudioUrl?.let { url ->
            Log.d("AudioPlayerView", "reset() - 重新设置音频源: $url")
            setAudioSource(url)
        }
        
        Log.d("AudioPlayerView", "reset() - 重置完成")
    }

    // 跳转到指定时间位置（秒）
    fun seekTo(position: Int) {
        mediaPlayer?.let { player: MediaPlayer ->
            player.seekTo(position * 1000)
            // 重置播放完成状态，因为用户拖动进度条表示要继续播放
            isPlaybackCompleted = false
            // 强制立即更新进度，确保拖拽时的响应性
            forceUpdateProgress()
        }
    }
    
    // 强制更新进度，用于拖拽等需要立即响应的场景
    private fun forceUpdateProgress() {
        mediaPlayer?.let { player: MediaPlayer ->
            val currentTimeMs = player.currentPosition
            val durationMs = player.duration
            val newProgress = if (durationMs > 0) currentTimeMs.toFloat() / durationMs.toFloat() else 0f
            
            this@AudioPlayerView.currentTime = currentTimeMs
            this@AudioPlayerView.duration = durationMs
            this@AudioPlayerView.progress = newProgress
            
            // 动态修正时长显示，确保与实际音频长度精确匹配
        val originalDurationSeconds = durationMs / 1000
        // 使用向上取整，确保显示时长不少于实际时长
        val correctedDurationSeconds = kotlin.math.max(kotlin.math.ceil(durationMs / 1000.0).toInt(), 1)
        // 按比例修正当前时间，保持播放进度的准确性
        val correctedCurrentSeconds = kotlin.math.min(
            kotlin.math.round(currentTimeMs.toDouble() / durationMs * correctedDurationSeconds).toInt(),
            correctedDurationSeconds
        ) // 确保当前时间不超过总时长
            
            
            // 触发进度回调，使用修正后的时长
            onProgressChangedCallback?.invoke(correctedCurrentSeconds, correctedDurationSeconds, newProgress)
            
            // 拖拽时使用快速动画过渡，提供即时反馈
            startProgressAnimation(animatedProgress, newProgress, 50)
            
            // 更新缓存值
            lastProgress = newProgress
            lastUpdateTime = System.currentTimeMillis()
        }
    }

    // 设置音量（0.0 - 1.0）
    fun setVolume(volume: Float) {
        mediaPlayer?.setVolume(volume, volume)
    }

    // 设置进度条颜色
    fun setProgressColor(color: String) {
        progressColor = Color.parseColor(color)
        progressPaint.color = progressColor
        invalidate()
    }

    // 设置进度条背景颜色
    fun setProgressBackgroundColor(color: String) {
        progressBackgroundColor = Color.parseColor(color)
        backgroundPaint.color = progressBackgroundColor
        invalidate()
    }

    // 设置进度条高度
    fun setProgressHeight(height: Int) {
        progressHeight = height.toFloat()
        invalidate()
    }

    // 设置进度条圆角半径
    fun setProgressRadius(radius: Int) {
        progressRadius = radius.toFloat()
        invalidate()
    }
    
    // 设置是否启用拖动功能
    fun setSeekEnabled(enabled: Boolean) {
        isSeekEnabled = enabled
        // 如果禁用拖动时正在拖动，则停止拖动
        if (!enabled && isDragging) {
            isDragging = false
            // 恢复播放状态
            if (wasPlayingBeforeDrag) {
                mediaPlayer?.start()
                startProgressUpdates()
            }
        }
    }
    
    // 获取当前拖动功能状态
    fun isSeekEnabled(): Boolean {
        return isSeekEnabled
    }
    
    // 设置播放完成后是否自动归零进度条
    fun setResetProgressOnComplete(reset: Boolean) {
        resetProgressOnComplete = reset
    }
    
    // 获取播放完成后是否自动归零进度条的设置
    fun getResetProgressOnComplete(): Boolean {
        return resetProgressOnComplete
    }

    // 设置回调函数
    fun setOnPlaybackStateChangedCallback(callback: (Boolean) -> Unit) {
        onPlaybackStateChangedCallback = callback
    }

    fun setOnProgressChangedCallback(callback: (Int, Int, Float) -> Unit) {
        onProgressChangedCallback = callback
    }

    fun setOnPlaybackCompletedCallback(callback: () -> Unit) {
        onPlaybackCompletedCallback = callback
    }

    fun setOnErrorCallback(callback: (String) -> Unit) {
        onErrorCallback = callback
    }

    // 获取当前播放时间（秒）
    fun getCurrentTime(): Int {
        return (mediaPlayer?.currentPosition ?: 0) / 1000
    }

    // 获取音频总时长（秒）
    fun getDuration(): Int {
        return (mediaPlayer?.duration ?: 0) / 1000
    }

    // 获取当前播放状态
    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    private fun startProgressUpdates() {
        progressUpdateRunnable?.let { runnable: Runnable ->
            handler.removeCallbacks(runnable)
            handler.post(runnable)
        }
    }

    private fun stopProgressUpdates() {
        progressUpdateRunnable?.let { runnable: Runnable ->
            handler.removeCallbacks(runnable)
        }
    }

    private fun releaseMediaPlayer() {
        mediaPlayer?.let { player: MediaPlayer ->
            if (player.isPlaying) {
                player.stop()
            }
            player.release()
        }
        mediaPlayer = null
        stopProgressUpdates()
    }

    // 销毁资源
    fun destroy() {
        stopProgressUpdates()
        releaseMediaPlayer()
        
        // 清理动画
        progressAnimator?.cancel()
        progressAnimator = null
        isAnimating = false
        
        // 清空回调
        onPlaybackStateChangedCallback = null
        onProgressChangedCallback = null
        onPlaybackCompletedCallback = null
        onErrorCallback = null
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        destroy()
    }
}