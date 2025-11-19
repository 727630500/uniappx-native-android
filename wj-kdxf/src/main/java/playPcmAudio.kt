package uts.sdk.modules.wjKdxf;


import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class pcmPlayer {

    private var audioTrack: AudioTrack? = null
    private var playbackJob: Job? = null

    fun playPcmAudio(filePath: String) {
        // 如果有正在播放的音频，先停止
        stop()

        val sampleRate = 16000 // 采样率
        val channelConfig = AudioFormat.CHANNEL_OUT_MONO // 单声道
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT // PCM 16位
        val bufferSize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat)

        audioTrack = AudioTrack(
            AudioManager.STREAM_MUSIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize,
            AudioTrack.MODE_STREAM
        )

        playbackJob = CoroutineScope(Dispatchers.IO).launch {
            val file = File(filePath)
            val audioData = ByteArray(bufferSize)
            var inputStream: FileInputStream? = null

            try {
                inputStream = FileInputStream(file)
                audioTrack?.play()

                var read: Int = 0 // 初始化read变量
                while (isActive && inputStream.read(audioData).also { read = it } != -1) {
                    if (audioTrack?.state == AudioTrack.STATE_INITIALIZED) {
                        audioTrack?.write(audioData, 0, read)
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                inputStream?.close()
                stop() // 确保在协程结束时停止播放
            }
        }
    }

    fun stop() {
        playbackJob?.cancel() // 取消协程任务
        playbackJob = null

        audioTrack?.let {
            if (it.state == AudioTrack.STATE_INITIALIZED && it.playState == AudioTrack.PLAYSTATE_PLAYING) {
				it.stop()
			}
            it.release()
            audioTrack = null
        }
    }
}