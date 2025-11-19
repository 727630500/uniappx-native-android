package uts.sdk.modules.wjSelectText;

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.PopupWindow
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.widget.PopupWindowCompat
import io.dcloud.uts.console
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.max
import kotlin.math.min
import android.util.Log
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import io.dcloud.uts.UTSAndroid

/**
 * 自定义小说阅读文本选择控件
 * 基于WebView实现，支持长按选择文本，弹出操作菜单
 */
class SelectableTextView : WebView {
    // 选择状态
    private var isSelecting = false
    private var selectionStart = -1
    private var selectionEnd = -1
    private var ttfPath = UTSAndroid.convert2AbsFullPath("static/ttf/TimesNewRoman.ttf")
    // 添加回调函数变量
    private var onTextSelectedCallback: ((String) -> Unit)? = null
    // 通知内容更新但不调整WebView高度
    private var onContentUpdatedCallback: (() -> Unit)? = null
    // 恢复高度回调
    private var onTextHeightCallback: ((Int) -> Unit)? = null
    private var onScrollPositionCallback: ((Int) -> Unit)? = null
    
    // 控制是否显示"加入生词本"功能
    private var isWordBookEnabled = true
    
    // 添加设置回调的方法
    fun onTextSelected(callback: (String) -> Unit) {
        onTextSelectedCallback = callback
    }
    
    fun onContentUpdated(callback: () -> Unit) {
        onContentUpdatedCallback = callback
    }
    
    // 恢复高度回调方法
    fun onTextHeight(callback: (Int) -> Unit) {
        onTextHeightCallback = callback
    }
    
    fun onScrollPosition(callback: (Int) -> Unit) {
        onScrollPositionCallback = callback
    }
    
    /**
     * 设置是否启用生词本功能
     * @param enabled true启用，false禁用
     */
    fun setWordBookEnabled(enabled: Boolean) {
        isWordBookEnabled = enabled
        
        // 动态控制文本选择能力
        if (isWebViewReady) {
            updateTextSelectionState()
        }
    }
    
    /**
     * 更新文本选择状态
     */
    private fun updateTextSelectionState() {
        val script = if (isWordBookEnabled) {
            """
                // 启用文本选择
                document.body.style.webkitUserSelect = 'text';
                document.body.style.userSelect = 'text';
                document.body.style.webkitTouchCallout = 'default';
            """.trimIndent()
        } else {
            """
                // 禁用文本选择
                document.body.style.webkitUserSelect = 'none';
                document.body.style.userSelect = 'none';
                document.body.style.webkitTouchCallout = 'none';
                // 清除当前选择
                if (window.getSelection) {
                    window.getSelection().removeAllRanges();
                }
            """.trimIndent()
        }
        
        evaluateJavascript(script, null)
    }
    
    // 添加选中文本缓存
    private var selectedTextCache: String = ""
    
    // 添加滚动位置缓存
    private var lastScrollPosition: Float = 0f
    
    // 触摸相关变量
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var touchStartTime = 0L
    private var isLongPressed = false
    
    // 弹出菜单
    private lateinit var popupWindow: PopupWindow
    private var isPopupShowing = false
    
    // 文本大小相关
    private var defaultTextSize = 24f // 默认文本大小，单位px
    
    // 文本颜色相关
    private var defaultTextColor = Color.BLACK // 默认文本颜色
    
    // 文本行高
    private var lineHeight = 1.5f
    
    // 内容内边距
    private var contentPadding = 16
    
    // 文本对齐方式
    private var textAlign = "left"
    
    // 添加协程作用域
    private val imageLoadScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // 添加Handler用于延迟操作
    private val mainHandler = Handler(Looper.getMainLooper())
    
    // 标记加载状态
    private var isPageLoaded = false
    private var pendingText: String = ""
    
    private var viewHeightChanged: ((Int) -> Unit)? = null
    private var textSelectionCallback: ((String) -> Unit)? = null
    private var scrollCallback: ((Float) -> Unit)? = null
    
    private var lastHeight = 0
    
    // 添加文本选择监听器接口
    interface OnTextSelectListener {
        fun onTextSelected(text: String, x: Int, y: Int, width: Int, height: Int)
    }
    
    // 文本选择监听器实例
    private var textSelectListener: OnTextSelectListener? = null
    
    private var onTextSelectionClearedCallback: (() -> Unit)? = null
    private var isWebViewReady = false
    private var pendingContent: String? = null
    
    // 添加存储当前选中文本的变量
    private var currentSelectedText: String = ""
    
    constructor(context: Context) : super(context) {
        init()
    }
    
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
    
    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    private fun init() {


        // 启用JavaScript
        settings.javaScriptEnabled = true
        
        // WebView基础设置
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.allowContentAccess = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.setSupportZoom(false)  // 禁用缩放
        settings.builtInZoomControls = false
        
        // 启用缓存
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK // 优先使用缓存，没有缓存时才从网络加载
        // 启用文件和数据库缓存
        settings.databaseEnabled = true
        
        // 启用长按文本选择 - 重要！
        isLongClickable = true
        
        // 使用JavaScript注入来处理文本选择
        // 不再直接设置长按监听器，让原生的文本选择发生
        setOnLongClickListener(null)
        
        // 设置WebViewClient以处理控制台消息
        webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                consoleMessage?.let { 
                    // console.log("WebView Console: ${it.message()}")
                }
                return true
            }
        }
        
        // 监听WebView加载完成事件
        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // 页面加载完成后，应用当前文本内容
                isPageLoaded = true
                isWebViewReady = true
                
                // 注入更强大的选择监听脚本
                injectSelectionScript()
                
                // 应用文本选择状态
                updateTextSelectionState()
                
                // 应用待处理的内容
                if (pendingText.isNotEmpty()) {
                    applyContent()
                }
            }
        }
        
        // 允许混合内容（HTTP和HTTPS）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        
        // 优化渲染性能
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            setWebContentsDebuggingEnabled(true)
        } else {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        
        // 增强滚动性能的设置
        overScrollMode = View.OVER_SCROLL_ALWAYS  // 允许过度滚动效果
        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY  // 滚动条覆盖在内容上，不占用内容空间
        isVerticalScrollBarEnabled = true  // 显示垂直滚动条
        isFocusable = true
        isFocusableInTouchMode = true
        isScrollContainer = true  // 标记为滚动容器
        isHorizontalScrollBarEnabled = false  // 禁用水平滚动条
        
        // 确保可以滚动
        setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            false // 返回false以允许WebView正常处理触摸事件
        }
        
        // 设置默认字体大小
        settings.defaultFontSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            defaultTextSize,
            resources.displayMetrics
        ).toInt()
        
        // 添加JavaScript接口
        addJavascriptInterface(JavascriptInterface(), "Android")
        
        // 设置WebView背景为透明
        setBackgroundColor(Color.TRANSPARENT)
        
        // 加载HTML基础结构
        loadHtmlContent()
    }
    
    // 注入文本选择脚本
    private fun injectSelectionScript() {
        val script = """
            (function() {
                console.log("注入选择监听脚本开始");
                
                // 根据生词本功能状态设置文本选择能力
                ${if (isWordBookEnabled) {
                    """
                    document.body.style.webkitUserSelect = 'text';
                    document.body.style.userSelect = 'text';
                    document.body.style.webkitTouchCallout = 'default';
                    """.trimIndent()
                } else {
                    """
                    document.body.style.webkitUserSelect = 'none';
                    document.body.style.userSelect = 'none';
                    document.body.style.webkitTouchCallout = 'none';
                    """.trimIndent()
                }}
                
                // 文本选择改变时触发
                document.addEventListener('selectionchange', function() {
                    const selection = window.getSelection();
                    if (!selection.isCollapsed && selection.rangeCount > 0) {
                        const selectedText = selection.toString();
                        if (selectedText && selectedText.trim().length > 0) {
                            console.log("已选择文本: " + selectedText);
                            try {
                                const range = selection.getRangeAt(0);
                                const rect = range.getBoundingClientRect();
                                const scrollTop = window.scrollY || document.documentElement.scrollTop;
                                
                                // 通知Android选择了文本
                                Android.onTextSelected(
                                    selectedText,
                                    rect.left,
                                    rect.top + scrollTop,
                                    rect.width,
                                    rect.height
                                );
                            } catch(e) {
                                console.error('通知选择失败: ' + e.message);
                            }
                        }
                    }
                });
                
                // 处理长按事件 - 确保文本选择被初始化
                document.addEventListener('contextmenu', function(e) {
                    console.log("触发上下文菜单");
                    e.preventDefault(); // 阻止默认上下文菜单
                    return false;
                });
                
                console.log("注入选择监听脚本完成");
            })();
        """.trimIndent()
        
        evaluateJavascript(script, null)
    }
    
    /**
     * JavaScriptInterface类，用于处理JavaScript与Android之间的通信
     */
    @SuppressLint("JavascriptInterface")
    inner class JavascriptInterface {
        @android.webkit.JavascriptInterface
        fun onTextSelected(text: String, x: Float, y: Float, width: Float, height: Float) {
            mainHandler.post {
                // 如果生词本功能被禁用，直接返回不处理
                if (!isWordBookEnabled) {
                    return@post
                }
                
                // console.log("收到选中文本回调: $text, x=$x, y=$y")
                
                // 更新当前选中文本
                currentSelectedText = text
                
                // 设置弹窗位置
                lastTouchX = x + width/2
                lastTouchY = y
                
                // 如果弹窗已显示，更新内容；否则创建新弹窗
                if (isPopupShowing && activeDialog != null) {
                    updatePopupContent(text)
                } else {
                    showCustomPopupMenu(text)
                }
                
            }
        }

        @android.webkit.JavascriptInterface
        fun onContentHeightChanged(height: Int) {
            mainHandler.post {
                // console.log("收到高度变化通知: $height")
                
                // 确保最小高度
                val minHeight = 30
                val finalHeight = if (height < minHeight) minHeight else height
                
                // 设置自身高度
                // layoutParams.height = finalHeight
                // requestLayout()
                
                
                // 通知高度回调
                onTextHeightCallback?.invoke(finalHeight)
            }
        }

        @android.webkit.JavascriptInterface
        fun onPageScroll(scrollY: Int) {
            mainHandler.post {
                scrollCallback?.invoke(scrollY.toFloat())
            }
        }

        @android.webkit.JavascriptInterface
        fun onTextSelectionCleared() {
            mainHandler.post {
                onTextSelectionClearedCallback?.invoke()
            }
        }

        @android.webkit.JavascriptInterface
        fun onWebViewReady() {
            mainHandler.post {
                isWebViewReady = true
                if (pendingText.isNotEmpty()) {
                    applyContent()
                }
            }
        }

        @android.webkit.JavascriptInterface
        fun onInputChange(id: String, value: String) {
            mainHandler.post {
                // 调用对应ID的回调函数
                inputCallbacks[id]?.invoke(value)
            }
        }

        @android.webkit.JavascriptInterface
        fun onInputFieldClicked(id: String, currentValue: String) {
            mainHandler.post {
                // 当输入框被点击时，显示自定义输入对话框
                showCustomInputDialog(id, currentValue)
            }
        }
    }
    
    // 滚动监听接口
    interface OnScrollListener {
        fun onScroll(scrollY: Float)
    }
    
    // 高度变化监听接口
    interface OnHeightChangeListener {
        fun onHeightChanged(height: Int)
    }
    
    // 监听接口实例
    private var scrollListener: OnScrollListener? = null
    private var heightChangeListener: OnHeightChangeListener? = null
    
    /**
     * 设置滚动监听
     */
    fun setOnScrollListener(listener: OnScrollListener) {
        this.scrollListener = listener
    }
    
    /**
     * 设置高度变化监听
     */
    fun setOnHeightChangeListener(listener: OnHeightChangeListener) {
        this.heightChangeListener = listener
    }
    
    private fun loadContentDirectly(content: String) {
        // console.log(content)
        // 确保内容被p标签包裹
        val wrappedContent = if (!content.trim().startsWith("<p") && !content.trim().startsWith("<div")) {
            "<p>$content</p>"
        } else {
            content
        }
        
        val safeContent = wrappedContent.replace("\"", "\\\"").replace("\n", "\\n")
        val script = "document.querySelector('.ql-editor').innerHTML = \"$safeContent\";"
        evaluateJavascript(script, null)
        
        // 刷新自定义滚动
        mainHandler.postDelayed({
            refreshScroll()
            // 通知内容已更新
            onContentUpdatedCallback?.invoke()
            
            // 获取并传递内容高度
            getContentHeight { height ->
                onTextHeightCallback?.invoke(height)
            }
        }, 50)
    }
 
    
    private fun dismissPopup() {
        if (::popupWindow.isInitialized && popupWindow.isShowing) {
            popupWindow.dismiss()
            isPopupShowing = false
        }
    }
    
    private fun clearSelection() {
        isSelecting = false
        evaluateJavascript("window.getSelection().removeAllRanges();", null)
        invalidate()
    }
    
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.x
                lastTouchY = event.y
            }
        }
        return super.onTouchEvent(event)
    }
    
    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
    
    private fun colorToHex(color: Int): String {
        return String.format("#%06X", (0xFFFFFF and color))
    }
    
    /**
     * 设置文本大小
     * @param textSize 文本大小，单位px
     */
    fun setCustomTextSize(textSize: Float) {
        defaultTextSize = textSize
        val script = "document.body.style.fontSize = '" + textSize + "px';"
        evaluateJavascript(script, null)
    }
    
    /**
     * 增大文本大小
     * @param increment 增量大小，单位px
     */
    fun increaseTextSize(increment: Float = 2f) {
        defaultTextSize += increment
        val script = "document.body.style.fontSize = '" + defaultTextSize + "px';"
        evaluateJavascript(script, null)
    }
    
    /**
     * 减小文本大小
     * @param decrement 减小大小，单位px
     */
    fun decreaseTextSize(decrement: Float = 2f) {
        if (defaultTextSize - decrement > 16f) { // 设置最小字体大小限制
            defaultTextSize -= decrement
            val script = "document.body.style.fontSize = '" + defaultTextSize + "px';"
            evaluateJavascript(script, null)
        }
    }
    
    /**
     * 设置文本颜色
     * @param color 文本颜色
     */
    fun setCustomTextColor(color: Int) {
        defaultTextColor = color
        val script = "document.body.style.color = '" + colorToHex(color) + "';"
        evaluateJavascript(script, null)
    }
    
    /**
     * 设置HTML文本
     * @param html HTML内容
     */
    fun setHtmlText(html: String) {
        // 确保HTML内容被p标签包裹
        val wrappedHtml = if (!html.trim().startsWith("<p") && !html.trim().startsWith("<div")) {
            "<p>$html</p>"
        } else {
            html
        }
        
        if (isPageLoaded) {
            loadContentDirectly(wrappedHtml)
        } else {
            pendingText = wrappedHtml
        }
    }
    
    /**
     * 设置普通文本
     * @param text 文本内容
     */
    fun setText(text: String) {
        if (isPageLoaded) {
            // JavaScript转义字符串
            val escapedText = text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "</p><p>") // 将换行符转换为新段落
                .replace("\r", "")
            
            // 确保文本被p标签包裹
            val wrappedText = "<p>$escapedText</p>"
            
            // 执行JavaScript将文本设置到WebView
            evaluateJavascript("setText(\"$wrappedText\")", null)
        } else {
            pendingText = "<p>" + text.replace("\n", "</p><p>") + "</p>"
        }
    }
    
    /**
     * 设置JSON格式文本
     * @param jsonArrayStr JSON数组字符串
     */
    fun setJsonText(jsonArrayStr: String) {
        try {
            val jsonArray = JSONArray(jsonArrayStr)
            val htmlBuilder = StringBuilder()
            
            // 添加开始的p标签，包含默认字体大小
            htmlBuilder.append("<p style=\"font-size: ${defaultTextSize}px;\">")
            
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val text = item.optString("text", "")
                val color = item.optString("color", null)
                val bold = item.optBoolean("bold", false)
                val italic = item.optBoolean("italic", false)
                val underline = item.optBoolean("underline", false)
                
                // 构建样式
                val styleBuilder = StringBuilder()
                if (color != null) {
                    styleBuilder.append("color: $color;")
                }
                if (bold) {
                    styleBuilder.append("font-weight: bold;")
                }
                if (italic) {
                    styleBuilder.append("font-style: italic;")
                }
                if (underline) {
                    // 使用border-bottom替代text-decoration，并添加适当的padding确保下划线不会被截断
                    styleBuilder.append("border-bottom: 1px solid; padding-bottom: 1px; text-decoration: none;margin-right: 8px;")
                }
                
                // 添加包含样式的文本
                val style = if (styleBuilder.isNotEmpty()) 
                    " style=\"${styleBuilder}\"" else ""
                
                // 处理文本中的换行符，将其转换为结束当前p标签并开始新p标签
                val paragraphs = text.split("\n")
                for (j in paragraphs.indices) {
                    val paragraph = paragraphs[j]
                    
                    // 添加当前段落文本
                    htmlBuilder.append("<span$style>$paragraph</span>")
                    
                    // 如果不是最后一个段落，添加新段落标签
                    if (j < paragraphs.size - 1) {
                        htmlBuilder.append("</p><p style=\"font-size: ${defaultTextSize}px;\">")
                    }
                }
            }
            
            // 添加结束的p标签
            htmlBuilder.append("</p>")
            
            setHtmlText(htmlBuilder.toString())
            
            // 添加日志，输出生成的HTML内容
            // console.log("生成的HTML内容: ${htmlBuilder.toString()}")
        } catch (e: Exception) {
            // console.error("SelectableTextView", "JSON解析错误: ${e.message}")
			// console.log(jsonArrayStr)
            // 直接设置文本并自动判断是否需要HTML解析
            if (jsonArrayStr.contains("<") && jsonArrayStr.contains(">")) {
                // 如果包含HTML标签，直接设置HTML
                // 检查是否包含字体大小设置
                val hasFontSize = jsonArrayStr.contains("font-size", ignoreCase = true)
                val wrappedHtml = if (!jsonArrayStr.trim().startsWith("<p>")) {
                    // 如果没有p标签包裹，添加p标签和字体大小
                    "<p style='font-size:${defaultTextSize}px'>$jsonArrayStr</p>"
                } else {
                   jsonArrayStr.replace("<p", "<p style='font-size:${defaultTextSize}px'")
                }
				// console.log(wrappedHtml)
                setHtmlText(wrappedHtml)
            } else {
                // 否则当作普通文本处理，并用p标签包裹
                setText("<div style='font-size:${defaultTextSize}px'>$jsonArrayStr</div>")
            }
        }
    }
    
    /**
     * 滚动到特定位置
     * @param yPosition 垂直滚动位置
     */
    fun scrollTo(yPosition: Int) {
        super.scrollTo(0, yPosition)
    }
    
    /**
     * 滚动到顶部
     */
    fun scrollToTop() {
        scrollTo(0)
    }
    
    /**
     * 滚动到底部
     */
    fun scrollToBottom() {
        getContentHeight { height ->
            scrollTo(height)
        }
    }
    
    /**
     * 获取当前滚动位置
     * @param callback 回调函数，返回当前的滚动位置
     */
    fun getCurrentScrollPosition(callback: (Int) -> Unit) {
        callback(scrollY)
    }
    
    /**
     * 获取最大滚动位置
     * @param callback 回调函数，返回最大的滚动位置
     */
    fun getMaxScrollPosition(callback: (Int) -> Unit) {
        getContentHeight { contentHeight ->
            val maxScroll = Math.max(0, contentHeight - height)
            callback(maxScroll)
        }
    }
    
    /**
     * 刷新滚动区域
     * 当内容变化后调用，更新滚动区域大小
     */
    fun refreshScroll() {
        invalidate()
    }
    
    /**
     * 恢复上次滚动位置
     */
    fun restoreScrollPosition() {
        scrollTo(lastScrollPosition.toInt())
    }
    
    /**
     * 设置HTML文本并恢复滚动位置
     * @param html HTML内容
     * @param restoreScroll 是否恢复滚动位置
     */
    fun setHtmlTextAndScroll(html: String, restoreScroll: Boolean = false) {
        val savedPosition = if (restoreScroll) lastScrollPosition else 0f
        
        if (isPageLoaded) {
            loadContentDirectly(html)
            if (restoreScroll) {
                // 延迟一会儿再滚动，确保内容已加载
                mainHandler.postDelayed({
                    scrollTo(savedPosition.toInt())
                }, 100)
            }
        } else {
            pendingText = html
            // 页面加载完成后会自动加载内容并恢复位置
        }
    }
    
    // 重写onDetachedFromWindow以清理资源
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        imageLoadScope.cancel() // 取消所有协程
    }
    
    /**
     * 获取内容高度
     * @param callback 回调函数，返回内容高度
     */
    fun getContentHeight(callback: (Int) -> Unit) {
        val script = """
            (function() {
                // 直接获取ql-container的高度
                const container = document.querySelector('.ql-container');
                if (!container) return 0;
                
                // 确保能精确计算行高
                const editor2 = document.querySelector('.ql-editor');
                const computedStyle = window.getComputedStyle(editor2);
                const fontSize = parseFloat(computedStyle.fontSize);
                
                // 获取行高 - 确保正确计算行高
                let lineHeight;
                if (computedStyle.lineHeight === 'normal') {
                    // 浏览器默认的normal约为1.2
                    lineHeight = fontSize * 1.2;
                } else if (computedStyle.lineHeight.endsWith('px')) {
                    lineHeight = parseFloat(computedStyle.lineHeight);
                } else {
                    // 如果是倍数形式，乘以字体大小
                    lineHeight = parseFloat(computedStyle.lineHeight) * fontSize;
                }
                
                // 计算段落数量
                const paragraphs = editor2.querySelectorAll('p, div:not(.ql-editor)');
                const paragraphCount = paragraphs.length || 1; // 至少有一个段落
                
                // 测量内容的确切高度，考虑行高和段落数
                const editorRect = editor2.getBoundingClientRect();
                const containerRect = container.getBoundingClientRect();
                
                // 计算最精确的高度
                const editorHeight = Math.ceil(editorRect.height);
                const containerHeight = Math.ceil(containerRect.height);
                const contentHeight = Math.max(editorHeight, containerHeight);
                
                // 检测是否需要额外空间来保障行高显示
                const paddingTop = parseInt(computedStyle.paddingTop) || 0;
                const paddingBottom = parseInt(computedStyle.paddingBottom) || 0;
                
                // 最终高度 = 内容高度 + 额外空间
                // 添加额外的空间确保所有行高都能正确显示
                const finalHeight = contentHeight + paddingTop + paddingBottom;
                
                console.log('行高计算: fontSize=' + fontSize + 'px, lineHeight=' + lineHeight + 'px');
                console.log('段落数量: ' + paragraphCount);
                console.log('高度计算: editor=' + editorHeight + 'px, container=' + containerHeight + 'px, final=' + finalHeight + 'px');
                
                return finalHeight;
            })()
        """.trimIndent()
        
        evaluateJavascript(script) { heightStr ->
            try {
                // 处理可能的小数点
                val heightValue = heightStr.toDoubleOrNull() ?: 0.0
                val height = heightValue.toInt()
                
                // 确保最小高度
                val minHeight = 30
                val finalHeight = if (height < minHeight) minHeight else height
                
                // console.log("转换后的高度: $height, 最终高度: $finalHeight")
                callback(finalHeight)
            } catch (e: Exception) {
                console.error("获取高度失败: ${e.message}")
                // 返回默认最小高度以避免高度为0
                callback(50)
            }
        }
    }
    
    /**
     * 设置滚动回调
     */
    fun setScrollCallback(callback: (Float) -> Unit) {
        scrollCallback = callback
    }
    
    // 添加文本选择监听器
    private fun addTextSelectionListener() {
        // 已在HTML中添加了监听器
    }
    
    // 显示选择视图
    private fun onShowSelectionView(text: String, x: Float, y: Float, width: Float, height: Float) {
        // 实现选择视图的显示逻辑
    }
    
    /**
     * 设置滚动位置
     */
    fun setScrollY(y: Float) {
        if (isPageLoaded) {
            evaluateJavascript("scrollToPosition($y)", null)
        }
    }
    
    private fun loadHtmlContent() {
        val html = """
            <!DOCTYPE html>
            <html lang="zh-CN">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
                <title>SelectableText</title>
                <!-- 引入外部CSS (添加版本号参数强制缓存) -->
                <link rel="stylesheet" href="http://oss-jiaopei.hongchengzhilian.com/default/css/quill.core.css?v=1.0.0">
                <link rel="stylesheet" href="http://oss-jiaopei.hongchengzhilian.com/default/css/quill.bubble.css?v=1.0.0">
                <link rel="stylesheet" href="http://oss-jiaopei.hongchengzhilian.com/default/css/quill.snow.css?v=1.0.0">
                <style>
                    @font-face {
                        font-family: 'TimesNewRoman';
                        src: url('file://$ttfPath') format('truetype');
                        font-weight: normal;
                        font-style: normal;
                    }
                    
                    * {
                        margin: 0;
                        padding: 0;
                        box-sizing: border-box;
                        -webkit-tap-highlight-color: transparent;
                    }
                    
                    html, body {
                        width: 100%;
                        height: 100%;
                        overflow-x: hidden;
                        overflow-y: auto !important;
                        background-color: transparent;
                        font-family: 'TimesNewRoman', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
                        -webkit-user-select: text;
                        user-select: text;
                        overscroll-behavior: none;
                        touch-action: auto;
                        margin: 0 !important;
                        padding: 0 !important;
                        border: 0 !important;
                    }
                    
                    #content-wrapper {
                        width: 100%;
                        min-height: 100%;
                        padding: 0;
                        overflow-x: hidden;
                        overflow-y: visible;
                        margin: 0 !important;
                        position: relative;
                    }
                    
                    #content {
                        width: 100%;
                    }

                    .ql-editor {
                        box-sizing: border-box;
                        cursor: text;
                        outline: none;
                        overflow-y: visible;
                        padding: 0 !important;
                        margin: 0 !important;
                        tab-size: 4;
                        -moz-tab-size: 4;
                        text-align: left;
                        white-space: pre-wrap;
                        word-wrap: break-word;
                        font-family: 'TimesNewRoman', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
                    }
                    
                    /* 覆盖所有可能导致顶部空白的样式 */
                    .ql-container.ql-snow {
                        border: none !important;
                    }
                    
                    ::selection {
                        background-color: rgba(25, 118, 210, 0.3);
                    }
                </style>
            </head>
            <body ontouchstart="">
                <div id="content-wrapper">
                    <div id="content" class="ql-container ql-snow" style="scroll:auto">
                        <div class="ql-editor"></div>
                    </div>
                </div>
                
                <script>
                    // 处理输入框点击，不唤醒系统键盘
                    function handleInputClick(inputId) {
                        try {
                            var input = document.getElementById(inputId);
                            if (input) {
                                // 通知Android处理自定义输入
                                Android.onInputFieldClicked(inputId, input.value);
                            }
                        } catch(e) {
                            console.error('处理输入框点击失败: ' + e.message);
                        }
                    }
                    
                    // 移除禁用上下文菜单的代码，允许原生长按文本选择
                    
                    // 页面加载后立即移除可能的空白
                    function removeUnwantedSpaces() {
                        // 移除空标签和空白文本节点
                        const editor = document.querySelector('.ql-editor');
                        let walker = document.createTreeWalker(
                            editor,
                            NodeFilter.SHOW_TEXT | NodeFilter.SHOW_ELEMENT,
                            null,
                            false
                        );
                        
                        let node;
                        let nodesToRemove = [];
                        
                        // 收集需要删除的节点
                        while(node = walker.nextNode()) {
                            if (node.nodeType === Node.TEXT_NODE && node.nodeValue.trim() === '') {
                                nodesToRemove.push(node);
                            } else if (node.nodeType === Node.ELEMENT_NODE && 
                                      !node.hasChildNodes() && 
                                      !/^(IMG|BR|HR)$/i.test(node.tagName)) {
                                nodesToRemove.push(node);
                            }
                        }
                        
                        // 删除收集的节点
                        nodesToRemove.forEach(node => {
                            node.parentNode.removeChild(node);
                        });
                    }
                    
                    // 全局变量
                    let lastScrollTop = 0;
                    
                    // DOM元素引用
                    const contentWrapper = document.getElementById('content-wrapper');
                    const content = document.getElementById('content');
                    const editor = document.querySelector('.ql-editor');
                    
                    // 设置文本内容
                    function setText(text) {
                        editor.innerHTML = text;
                        removeUnwantedSpaces();
                        notifyContentHeight();
                    }
                    
                    // 设置HTML内容
                    function setHtmlContent(htmlContent) {
                        editor.innerHTML = htmlContent;
                        removeUnwantedSpaces();
                        notifyContentHeight();
                    }
                    
                    // 通知内容高度变化
                    function notifyContentHeight() {
                        removeUnwantedSpaces();
                        
                        // 获取ql-container的高度
                        const container = document.querySelector('.ql-container');
                        // 确保容器已被正确渲染
                        if (!container || container.offsetHeight === 0) {
                            console.log('容器尚未完全渲染，高度为0');
                            // 延迟重试
                            setTimeout(notifyContentHeight, 1000);
                            return;
                        }
                        
                        const containerRect = container.getBoundingClientRect();
                        // 向上取整确保高度足够
                        const containerHeight = Math.ceil(containerRect.height);
                        
                        console.log('高度计算: ql-container=' + containerHeight + 
                                   ', content=' + content.scrollHeight + 
                                   ', editor=' + editor.scrollHeight);
                        
                        try {
                            // 通知Android高度变化，使用整数值
                            Android.onContentHeightChanged(Math.ceil(containerHeight));
                        } catch(e) {
                            console.error('通知高度变化失败: ' + e.message);
                        }
                    }
                    
                    // 滚动处理
                    window.addEventListener('scroll', function(event) {
                        const currentScrollTop = window.scrollY || document.documentElement.scrollTop;
                        
                        // 避免连续多次触发
                        if (Math.abs(currentScrollTop - lastScrollTop) < 5) {
                            return;
                        }
                        
                        try {
                            Android.onPageScroll(currentScrollTop);
                        } catch(e) {
                            console.error('Failed to notify scroll', e);
                        }
                        
                        lastScrollTop = currentScrollTop;
                    }, { passive: true });
                    
                    // 滚动到指定位置
                    function scrollToPosition(y) {
                        window.scrollTo({
                            top: y,
                            behavior: 'auto'
                        });
                    }
                    
                    // 文本选择处理通过selectionchange事件自动处理
                    // 不再需要自定义的长按选择实现
                    
                    // 监听DOM变化，自动更新高度
                    const observer = new MutationObserver(function(mutations) {
                        // 延迟执行，等待渲染完成
                        setTimeout(function() {
                            notifyContentHeight();
                        }, 50);
                    });
                    
                    // 在页面加载后启动观察
                    window.addEventListener('load', function() {
                        try {
                            // 设置初始内容，确保至少有一个空段落
                            if (!editor.innerHTML.trim()) {
                                editor.innerHTML = '<p>&nbsp;</p>';
                            }
                            
                            // 监听编辑器内容变化
                            observer.observe(editor, {
                                childList: true,
                                subtree: true,
                                characterData: true
                            });
                            
                            // 延迟通知高度，等待完全渲染
                            setTimeout(function() {
                                notifyContentHeight();
                                // 通知WebView已准备好
                                Android.onWebViewReady();
                            }, 100);
                        } catch(e) {
                            console.error('初始化观察者失败: ' + e.message);
                        }
                    });
                </script>
            </body>
            </html>
        """.trimIndent()
        
        // 使用有效的baseUrl以允许加载外部资源
        loadDataWithBaseURL("http://oss-jiaopei.hongchengzhilian.com/", html, "text/html", "UTF-8", null)
    }

    private fun applyContent() {
        if (isPageLoaded && pendingText.isNotEmpty()) {
            if (pendingText.startsWith("<") && pendingText.contains("</")) {
                // 如果看起来像HTML内容
                loadContentDirectly(pendingText)
            } else {
                // 作为纯文本处理
                val escapedText = pendingText.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "<br>")
                    .replace("\r", "")
                
                evaluateJavascript("setText(\"$escapedText\")", null)
            }
            
            pendingText = "" // 清空待处理内容
            
            // 通知内容已更新
            onContentUpdatedCallback?.invoke()
        }
    }

 
    // 覆盖此方法返回false，不再阻止默认上下文菜单
    override fun onCreateContextMenu(menu: android.view.ContextMenu) {
        // 清除默认菜单
        menu.clear()
        
        // 不调用父类方法，阻止默认菜单显示
        //super.onCreateContextMenu(menu)
    }

    // 添加更新弹窗内容的方法
    private fun updatePopupContent(selectedText: String) {
        try {
            // console.log("更新弹窗内容: $selectedText")
            
            // 如果当前有活动对话框，更新其标题
            activeDialog?.setTitle("文本操作: $selectedText")
            
            // 更新弹窗中的按钮操作
            // 由于按钮已经创建并设置了点击事件，这里不需要重新创建
            // 点击事件将使用最新的selectedText值
            
        } catch (e: Exception) {
            console.error("更新弹窗内容失败: ${e.message}")
        }
    }

    // 显示自定义弹出菜单
    private fun showCustomPopupMenu(selectedText: String) {
        showCustomPopupWindow(selectedText)
    }

    // 显示自定义弹出窗口
    private fun showCustomPopupWindow(selectedText: String) {
        try {
            // console.log("显示自定义弹出窗口，选中文本: $selectedText")
            
            // 如果已经有弹窗显示，更新内容后直接返回
            if (isPopupShowing && activeDialog != null) {
                updatePopupContent(selectedText)
                return
            }
            
            // 标记弹窗正在显示
            isPopupShowing = true
            
            // 使用AlertDialog代替PopupWindow
            val builder = android.app.AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar)
            
            // 创建菜单布局
            val menuLayout = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(8))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                gravity = Gravity.CENTER
            }
            
            // 定义自定义数据类用于菜单项
            data class MenuItem(val text: String, val color: String, val action: () -> Unit)
            
            // 先创建对话框实例，这样按钮可以引用它
            activeDialog = builder.setView(menuLayout)
                .setCancelable(true)
                .setTitle("文本操作: $selectedText")
                .create()
                
            // 定义菜单选项 - 使用当前的选中文本变量而不是传入的参数
            val menuItems = mutableListOf<MenuItem>().apply {
                // 复制功能始终可用
                add(MenuItem("复制", "#4285F4") { copyText(currentSelectedText) })
                
                // 根据设置决定是否添加生词本功能
                if (isWordBookEnabled) {
                    add(MenuItem("加入生词本", "#0F9D58") { addToWordBook(currentSelectedText) })
                }
            }.toTypedArray()
            
            // 添加菜单按钮
            for (item in menuItems) {
                val button = AppCompatButton(context).apply {
                    setText(item.text)
                    setTextColor(Color.WHITE)
                    setBackgroundColor(Color.parseColor(item.color))
                    setPadding(dpToPx(12), dpToPx(8), dpToPx(12), dpToPx(8))
                    
                    // 设置点击事件
                    setOnClickListener {
                        item.action()
                        // 关闭对话框
                        activeDialog?.dismiss()
                    }
                    
                    // 设置布局参数
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        dpToPx(40)
                    ).apply {
                        setMargins(dpToPx(4), 0, dpToPx(4), 0)
                        weight = 1f
                    }
                }
                menuLayout.addView(button)
            }
            
            // 获取设备屏幕尺寸
            val displayMetrics = context.resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels
            
            // 设置对话框关闭监听器
            activeDialog?.setOnDismissListener {
                // 重置弹窗显示状态
                isPopupShowing = false
                activeDialog = null
            }
            
            // 显示对话框
            activeDialog?.show()
            
            // 设置对话框宽度为屏幕宽度的50%
            val lp = android.view.WindowManager.LayoutParams()
            lp.copyFrom(activeDialog?.window?.attributes)
            lp.width = (screenWidth * 0.5).toInt()
            activeDialog?.window?.attributes = lp
            
        } catch (e: Exception) {
            // 重置弹窗状态，以防错误导致状态不一致
            isPopupShowing = false
            activeDialog = null
            // console.error("显示弹出窗口失败: ${e.message}")
            // 降级处理 - 使用Toast提示
            Toast.makeText(context, "选中文本: $selectedText", Toast.LENGTH_SHORT).show()
        }
    }

    // 添加成员变量来跟踪当前活动的对话框
    private var activeDialog: android.app.AlertDialog? = null

    // 复制文本
    private fun copyText(text: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Selected Text", text)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, "已复制文本", Toast.LENGTH_SHORT).show()
    }

    // 添加到生词本
    private fun addToWordBook(text: String) {
        // 调用回调函数将文本传递给应用
        onTextSelectedCallback?.invoke(text)
    }

    /**
     * 插入输入框
     * @param id 输入框ID
     * @param placeholder 占位符文本
     * @param initialValue 初始值
     * @param width 宽度，如"100px"或"80%"
     * @param callback 输入变化回调
     */
    fun insertInputField(id: String, placeholder: String, initialValue: String = "", width: String = "100%", callback: ((String) -> Unit)? = null) {
        // 保存回调函数
        inputCallbacks[id] = callback
        
        // 构建input HTML
        val inputHtml = """
            <input type="text" 
                   id="$id" 
                   style="width: $width; padding: 8px; border: 1px solid #ccc; border-radius: 4px; font-size: ${defaultTextSize}px;" 
                   placeholder="$placeholder" 
                   value="$initialValue"
                   oninput="notifyInputChange('$id', this.value)">
        """.trimIndent()
        
        // 注入JavaScript处理函数(如果尚未注入)
        val script = """
            if (!window.notifyInputChange) {
                window.notifyInputChange = function(id, value) {
                    try {
                        Android.onInputChange(id, value);
                    } catch(e) {
                        console.error('通知输入变化失败: ' + e.message);
                    }
                }
            }
        """.trimIndent()
        
        evaluateJavascript(script, null)
        
        // 插入输入框到光标位置或末尾
        val insertScript = """
            (function() {
                // 先尝试在选中位置插入
                var sel = window.getSelection();
                if (sel && sel.rangeCount > 0) {
                    var range = sel.getRangeAt(0);
                    var div = document.createElement('div');
                    div.innerHTML = `$inputHtml`;
                    range.deleteContents();
                    range.insertNode(div);
                } else {
                    // 否则插入到编辑器末尾
                    var editor = document.querySelector('.ql-editor');
                    if (editor) {
                        var div = document.createElement('div');
                        div.innerHTML = `$inputHtml`;
                        editor.appendChild(div);
                    }
                }
            })();
        """.trimIndent()
        
        evaluateJavascript(insertScript, null)
    }
    
    // 显示自定义输入对话框
    private fun showCustomInputDialog(inputId: String, currentValue: String) {
        try {
            // 创建EditText用于输入
            val editText = android.widget.EditText(context).apply {
                setText(currentValue)
                setTextColor(android.graphics.Color.BLACK)
                setPadding(dpToPx(16), dpToPx(8), dpToPx(16), dpToPx(8))
                setBackgroundResource(android.R.drawable.edit_text)
                isSingleLine = true
            }
            
            // 创建对话框
            val builder = android.app.AlertDialog.Builder(context)
                .setTitle("请输入内容")
                .setView(editText)
                .setPositiveButton("确定") { dialog, _ ->
                    val newValue = editText.text.toString()
                    // 更新WebView中的输入框值
                    updateInputFieldValue(inputId, newValue)
                    // 触发回调
                    inputCallbacks[inputId]?.invoke(newValue)
                    dialog.dismiss()
                }
                .setNegativeButton("取消") { dialog, _ ->
                    dialog.cancel()
                }
            
            // 显示对话框
            builder.show()
        } catch (e: Exception) {
            console.error("显示输入对话框失败: ${e.message}")
        }
    }
    
    // 更新WebView中输入框的值
    private fun updateInputFieldValue(inputId: String, value: String) {
        val script = """
            (function() {
                var input = document.getElementById('$inputId');
                if (input) {
                    input.value = "$value";
                    // 触发change事件以便其他脚本能够感知变化
                    var event = new Event('change', { bubbles: true });
                    input.dispatchEvent(event);
                }
            })();
        """.trimIndent()
        
        evaluateJavascript(script, null)
    }
    
    /**
     * 插入带有自定义输入的输入框，不会唤醒系统键盘
     */
    fun insertCustomInputField(id: String, placeholder: String, initialValue: String = "", width: String = "100%", callback: ((String) -> Unit)? = null) {
        // 保存回调函数
        inputCallbacks[id] = callback
        
        // 构建带有readonly属性的input HTML
        val inputHtml = """
            <input type="text" 
                   id="$id" 
                   readonly="readonly"
                   style="width: $width; padding: 8px; border: 1px solid #ccc; border-radius: 4px; font-size: ${defaultTextSize}px;" 
                   placeholder="$placeholder" 
                   value="$initialValue"
                   onclick="handleInputClick('$id')">
        """.trimIndent()
        
        // 注入JavaScript处理函数(如果尚未注入)
        val script = """
            if (!window.handleInputClick) {
                window.handleInputClick = function(id) {
                    try {
                        var input = document.getElementById(id);
                        Android.onInputFieldClicked(id, input.value);
                    } catch(e) {
                        console.error('处理输入框点击失败: ' + e.message);
                    }
                }
            }
        """.trimIndent()
        
        evaluateJavascript(script, null)
        
        // 插入输入框到光标位置或末尾
        val insertScript = """
            (function() {
                // 先尝试在选中位置插入
                var sel = window.getSelection();
                if (sel && sel.rangeCount > 0) {
                    var range = sel.getRangeAt(0);
                    var div = document.createElement('div');
                    div.innerHTML = `$inputHtml`;
                    range.deleteContents();
                    range.insertNode(div);
                } else {
                    // 否则插入到编辑器末尾
                    var editor = document.querySelector('.ql-editor');
                    if (editor) {
                        var div = document.createElement('div');
                        div.innerHTML = `$inputHtml`;
                        editor.appendChild(div);
                    }
                }
            })();
        """.trimIndent()
        
        evaluateJavascript(insertScript, null)
    }
    
    // 存储输入框回调函数的映射
    private val inputCallbacks = mutableMapOf<String, ((String) -> Unit)?>()
}
