package uts.sdk.modules.wjKdxf;

import io.dcloud.uts.setInterval
import io.dcloud.uts.clearInterval
import io.dcloud.uts.console

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Handler
import android.os.HandlerThread

class realTimeAudioRecorder {
    private val sampleRate = 16000 // 采样率
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private var audioRecord: AudioRecord? = null
    @Volatile private var isRecording = false
    private var handlerThread: HandlerThread? = null
    private var handler: Handler? = null
    private val interval = 20L // 20ms

    fun startRecording(onAudioDataReceived: (ByteArray) -> Unit) {
        val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
        audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioFormat, bufferSize)

        audioRecord?.startRecording()
        isRecording = true

        handlerThread = HandlerThread("AudioHandlerThread").apply { start() }
        handler = Handler(handlerThread!!.looper)

        val buffer = ByteArray(bufferSize)
        val audioRunnable = object : Runnable {
            override fun run() {
                if (isRecording) {
                    val read = audioRecord?.read(buffer, 0, buffer.size) ?: 0
                    if (read > 0) {
                        // 将实时音频数据传递给回调函数
                        onAudioDataReceived(buffer.copyOf(read))
                    }
                    handler?.postDelayed(this, interval)
                }
            }
        }
        handler?.post(audioRunnable)
    }

    fun stopRecording() {
        isRecording = false
        audioRecord?.let {
            it.stop()
            it.release()
            audioRecord = null
        }
        handler?.removeCallbacksAndMessages(null)
        handlerThread?.quitSafely()
        handlerThread = null
        handler = null
    }
}