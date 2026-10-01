package com.example.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.data.model.NewsArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

data class AudioPlaybackState(
    val isPlaying: Boolean = false,
    val isPaused: Boolean = false,
    val currentArticleId: String? = null,
    val currentArticleTitle: String? = null,
    val currentSource: String? = null,
    val speechRate: Float = 1.0f,
    val currentParagraphIndex: Int = 0,
    val totalParagraphs: Int = 0,
    val isLanguageSupported: Boolean = true
)

class NewsAudioReader(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false

    private val _state = MutableStateFlow(AudioPlaybackState())
    val state: StateFlow<AudioPlaybackState> = _state.asStateFlow()

    private var currentTextSegments: List<String> = emptyList()
    private var currentSegmentIndex = 0
    private var currentArticle: NewsArticle? = null

    init {
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val faLocale = Locale.forLanguageTag("fa-IR")
            val langResult = tts?.setLanguage(faLocale)
            val isSupported = langResult != TextToSpeech.LANG_MISSING_DATA && langResult != TextToSpeech.LANG_NOT_SUPPORTED
            if (!isSupported) {
                // Try fallback to Arabic or default if Persian specific pack isn't downloaded yet
                val altLocale = Locale.forLanguageTag("ar")
                tts?.setLanguage(altLocale)
            }
            _state.value = _state.value.copy(isLanguageSupported = isSupported)
            tts?.setSpeechRate(_state.value.speechRate)
            isInitialized = true

            setupProgressListener()
        } else {
            Log.e("NewsAudioReader", "TTS initialization failed: $status")
        }
    }

    private fun setupProgressListener() {
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                _state.value = _state.value.copy(isPlaying = true, isPaused = false)
            }

            override fun onDone(utteranceId: String?) {
                currentSegmentIndex++
                if (currentSegmentIndex < currentTextSegments.size) {
                    _state.value = _state.value.copy(
                        currentParagraphIndex = currentSegmentIndex
                    )
                    speakNextSegment()
                } else {
                    _state.value = _state.value.copy(
                        isPlaying = false,
                        isPaused = false,
                        currentParagraphIndex = 0
                    )
                }
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                Log.e("NewsAudioReader", "TTS utterance error on $utteranceId")
                _state.value = _state.value.copy(isPlaying = false, isPaused = false)
            }
        })
    }

    fun playArticle(article: NewsArticle) {
        if (!isInitialized) return
        stop()

        currentArticle = article
        val segments = mutableListOf<String>()

        // Prepare structured spoken script in Persian
        segments.add("عنوان خبر: ${article.titleFa}")
        segments.add("منبع گزارش: ${article.source}")
        if (article.executiveSummaryFa.isNotBlank()) {
            segments.add("خلاصه هوشمند و پیام کلیدی: ${article.executiveSummaryFa}")
        } else if (article.summaryFa.isNotBlank()) {
            segments.add("خلاصه گزارش: ${article.summaryFa}")
        }
        if (article.keyPoints.isNotEmpty()) {
            segments.add("مهم‌ترین نکات خبر:")
            article.keyPoints.forEachIndexed { idx, point ->
                segments.add("نکته ${idx + 1}: $point")
            }
        }
        if (article.contentFa.isNotBlank()) {
            segments.add("مشروح گزارش:")
            // Split content into sentences for natural flow
            val sentences = article.contentFa.split(Regex("[.!?،\\n]+"))
                .map { it.trim() }
                .filter { it.isNotBlank() }
            segments.addAll(sentences)
        }

        currentTextSegments = segments
        currentSegmentIndex = 0

        _state.value = _state.value.copy(
            isPlaying = true,
            isPaused = false,
            currentArticleId = article.id,
            currentArticleTitle = article.titleFa,
            currentSource = article.source,
            currentParagraphIndex = 0,
            totalParagraphs = segments.size
        )

        speakNextSegment()
    }

    private fun speakNextSegment() {
        if (currentSegmentIndex < currentTextSegments.size) {
            val text = currentTextSegments[currentSegmentIndex]
            val utteranceId = "seg_${currentSegmentIndex}_${System.currentTimeMillis()}"
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
        }
    }

    fun pause() {
        tts?.stop()
        _state.value = _state.value.copy(isPlaying = false, isPaused = true)
    }

    fun resume() {
        if (_state.value.isPaused && currentTextSegments.isNotEmpty()) {
            _state.value = _state.value.copy(isPlaying = true, isPaused = false)
            speakNextSegment()
        } else if (currentArticle != null) {
            playArticle(currentArticle!!)
        }
    }

    fun stop() {
        tts?.stop()
        currentTextSegments = emptyList()
        currentSegmentIndex = 0
        _state.value = _state.value.copy(
            isPlaying = false,
            isPaused = false,
            currentArticleId = null,
            currentArticleTitle = null,
            currentSource = null,
            currentParagraphIndex = 0,
            totalParagraphs = 0
        )
    }

    fun togglePlayPause(article: NewsArticle) {
        if (_state.value.currentArticleId == article.id) {
            if (_state.value.isPlaying) {
                pause()
            } else {
                resume()
            }
        } else {
            playArticle(article)
        }
    }

    fun setSpeechRate(rate: Float) {
        tts?.setSpeechRate(rate)
        _state.value = _state.value.copy(speechRate = rate)
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
    }
}
