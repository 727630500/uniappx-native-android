package uts.sdk.modules.wjKdxf;


import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.*
import java.io.FileOutputStream
import java.io.IOException

class audioRecorder {

    private var audioRecord: AudioRecord? = null
    private var isRecording = false
    private val sampleRate = 16000 // 采样率
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO // 单声道
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT // PCM 16位
    private val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
    private var outputFilePath: String? = null
    private var recordingJob: Job? = null

    fun init(outputFilePath: String) {
        this.outputFilePath = outputFilePath
    }

    fun startRecording() {
        if (outputFilePath == null) {
            throw IllegalStateException("Output file path must be set before recording.")
        }

        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize
        )

        recordingJob = CoroutineScope(Dispatchers.IO).launch {
            val audioData = ByteArray(bufferSize)
            var outputStream: FileOutputStream? = null

            try {
                outputStream = FileOutputStream(outputFilePath)
                audioRecord?.startRecording()
                isRecording = true

                while (isRecording) {
                    val read = audioRecord?.read(audioData, 0, bufferSize) ?: 0
                    if (read > 0) {
                        outputStream.write(audioData, 0, read)
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                outputStream?.close()
            }
        }
    }

    fun stopRecording() {
        if (isRecording) {
            isRecording = false
            recordingJob?.cancel()
            audioRecord?.stop()
            audioRecord?.release()
            audioRecord = null
        }
    }
}