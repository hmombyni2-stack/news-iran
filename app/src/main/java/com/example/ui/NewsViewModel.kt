package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.NewsArticle
import com.example.data.model.NewsCategory
import com.example.data.model.TimeRangeFilter
import com.example.data.repository.NewsRepository
import com.example.tts.AudioPlaybackState
import com.example.tts.NewsAudioReader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SearchFilterState(
    val query: String = "",
    val category: NewsCategory = NewsCategory.ALL,
    val source: String = "all", // "all", "Reuters", "Associated Press", "BBC", "Bloomberg", "Euronews", "AFP"
    val timeRange: TimeRangeFilter = TimeRangeFilter.ALL,
    val minCredibility: Int = 0 // 0, 90, 95
)

data class InternalUiState(
    val selectedTab: Int = 0,
    val filters: SearchFilterState = SearchFilterState(),
    val isRefreshing: Boolean = false,
    val isTranslating: Boolean = false,
    val translationInput: String = "",
    val translatedArticleResult: NewsArticle? = null,
    val selectedArticle: NewsArticle? = null,
    val statusMessage: String? = null,
    val errorMessage: String? = null
)

data class NewsUiState(
    val selectedTab: Int = 0,
    val filters: SearchFilterState = SearchFilterState(),
    val isRefreshing: Boolean = false,
    val isTranslating: Boolean = false,
    val translationInput: String = "",
    val translatedArticleResult: NewsArticle? = null,
    val selectedArticle: NewsArticle? = null,
    val statusMessage: String? = null,
    val errorMessage: String? = null,
    val allArticles: List<NewsArticle> = emptyList(),
    val filteredArticles: List<NewsArticle> = emptyList(),
    val bookmarkedArticles: List<NewsArticle> = emptyList(),
    val audioState: AudioPlaybackState = AudioPlaybackState()
)

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NewsRepository
    val audioReader: NewsAudioReader = NewsAudioReader(application)

    private val _internalState = MutableStateFlow(InternalUiState())

    init {
        val db = AppDatabase.getInstance(application)
        repository = NewsRepository(db.newsDao())

        viewModelScope.launch {
            repository.initializeIfEmpty()
        }
    }

    val uiState: StateFlow<NewsUiState> = combine(
        repository.allArticles,
        repository.bookmarkedArticles,
        audioReader.state,
        _internalState
    ) { allArticles, bookmarked, audio, internalState ->

        val filtered = allArticles.filter { article ->
            val filters = internalState.filters

            // Category filter
            val matchesCategory = if (filters.category == NewsCategory.ALL) true else {
                article.category.equals(filters.category.id, ignoreCase = true)
            }

            // Keyword query filter (title, summary, executive summary, content, key points, source)
            val matchesQuery = if (filters.query.isBlank()) true else {
                val q = filters.query.trim()
                article.titleFa.contains(q, ignoreCase = true) ||
                article.summaryFa.contains(q, ignoreCase = true) ||
                article.executiveSummaryFa.contains(q, ignoreCase = true) ||
                article.contentFa.contains(q, ignoreCase = true) ||
                article.source.contains(q, ignoreCase = true) ||
                article.titleOriginal.contains(q, ignoreCase = true) ||
                article.keyPoints.any { it.contains(q, ignoreCase = true) }
            }

            // Source filter
            val matchesSource = if (filters.source == "all" || filters.source.isBlank()) true else {
                article.source.contains(filters.source, ignoreCase = true)
            }

            // Time range filter
            val now = System.currentTimeMillis()
            val matchesTime = when (filters.timeRange) {
                TimeRangeFilter.ALL -> true
                TimeRangeFilter.TODAY -> article.timestamp >= (now - 86400000L)
                TimeRangeFilter.WEEK -> article.timestamp >= (now - 7 * 86400000L)
            }

            // Credibility score filter
            val matchesCredibility = article.credibilityScore >= filters.minCredibility

            matchesCategory && matchesQuery && matchesSource && matchesTime && matchesCredibility
        }

        NewsUiState(
            selectedTab = internalState.selectedTab,
            filters = internalState.filters,
            isRefreshing = internalState.isRefreshing,
            isTranslating = internalState.isTranslating,
            translationInput = internalState.translationInput,
            translatedArticleResult = internalState.translatedArticleResult,
            selectedArticle = internalState.selectedArticle,
            statusMessage = internalState.statusMessage,
            errorMessage = internalState.errorMessage,
            allArticles = allArticles,
            filteredArticles = filtered,
            bookmarkedArticles = bookmarked,
            audioState = audio
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NewsUiState()
    )

    fun selectTab(tab: Int) {
        _internalState.value = _internalState.value.copy(selectedTab = tab)
    }

    fun selectCategory(category: NewsCategory) {
        _internalState.value = _internalState.value.copy(
            filters = _internalState.value.filters.copy(category = category)
        )
    }

    fun onSearchQueryChange(query: String) {
        _internalState.value = _internalState.value.copy(
            filters = _internalState.value.filters.copy(query = query)
        )
    }

    fun setSourceFilter(source: String) {
        _internalState.value = _internalState.value.copy(
            filters = _internalState.value.filters.copy(source = source)
        )
    }

    fun setTimeRangeFilter(timeRange: TimeRangeFilter) {
        _internalState.value = _internalState.value.copy(
            filters = _internalState.value.filters.copy(timeRange = timeRange)
        )
    }

    fun setMinCredibility(score: Int) {
        _internalState.value = _internalState.value.copy(
            filters = _internalState.value.filters.copy(minCredibility = score)
        )
    }

    fun resetFilters() {
        _internalState.value = _internalState.value.copy(
            filters = SearchFilterState()
        )
    }

    fun selectArticle(article: NewsArticle?) {
        _internalState.value = _internalState.value.copy(selectedArticle = article)
    }

    fun toggleBookmark(article: NewsArticle) {
        viewModelScope.launch {
            repository.toggleBookmark(article)
            if (_internalState.value.selectedArticle?.id == article.id) {
                _internalState.value = _internalState.value.copy(
                    selectedArticle = _internalState.value.selectedArticle?.copy(isBookmarked = !article.isBookmarked)
                )
            }
        }
    }

    fun refreshNews(query: String = "") {
        viewModelScope.launch {
            _internalState.value = _internalState.value.copy(
                isRefreshing = true,
                statusMessage = "در حال رصد و دسته‌بندی هوشمند آخرین اخبار با هوش مصنوعی...",
                errorMessage = null
            )
            try {
                val currentFilters = _internalState.value.filters
                val effectiveQuery = query.ifBlank { currentFilters.query }
                val catId = currentFilters.category.id
                val source = currentFilters.source
                val timeRange = currentFilters.timeRange.id

                val result = repository.refreshNews(effectiveQuery, catId, source, timeRange)
                if (result.isFailure) {
                    _internalState.value = _internalState.value.copy(
                        errorMessage = "خطا در برقراری ارتباط، نمایش اخبار معتبر پیش‌فرض"
                    )
                } else {
                    _internalState.value = _internalState.value.copy(
                        statusMessage = "اخبار با موفقیت دسته‌بندی و خلاصه‌سازی شد"
                    )
                }
            } catch (e: Exception) {
                _internalState.value = _internalState.value.copy(
                    errorMessage = e.message
                )
            } finally {
                _internalState.value = _internalState.value.copy(isRefreshing = false)
            }
        }
    }

    fun performAiSearch(topic: String) {
        onSearchQueryChange(topic)
        refreshNews(topic)
    }

    fun setTranslationInput(text: String) {
        _internalState.value = _internalState.value.copy(translationInput = text)
    }

    fun translateCustomArticle() {
        val input = _internalState.value.translationInput.trim()
        if (input.isBlank()) return

        viewModelScope.launch {
            _internalState.value = _internalState.value.copy(
                isTranslating = true,
                errorMessage = null,
                statusMessage = "در حال ترجمه، خلاصه‌سازی هوشمند و دسته‌بندی با هوش مصنوعی..."
            )
            try {
                val result = repository.translateForeignText(input)
                if (result.isSuccess) {
                    val article = result.getOrNull()
                    _internalState.value = _internalState.value.copy(
                        translatedArticleResult = article,
                        statusMessage = "ترجمه، خلاصه‌سازی و تحلیل هوشمند با موفقیت تکمیل شد!"
                    )
                } else {
                    _internalState.value = _internalState.value.copy(
                        errorMessage = "خطا در ترجمه: ${result.exceptionOrNull()?.message}"
                    )
                }
            } catch (e: Exception) {
                _internalState.value = _internalState.value.copy(
                    errorMessage = "خطا: ${e.message}"
                )
            } finally {
                _internalState.value = _internalState.value.copy(isTranslating = false)
            }
        }
    }

    fun playArticleAudio(article: NewsArticle) {
        audioReader.playArticle(article)
    }

    fun toggleAudioPlayback(article: NewsArticle) {
        audioReader.togglePlayPause(article)
    }

    fun pauseAudio() {
        audioReader.pause()
    }

    fun resumeAudio() {
        audioReader.resume()
    }

    fun stopAudio() {
        audioReader.stop()
    }

    fun setAudioSpeed(rate: Float) {
        audioReader.setSpeechRate(rate)
    }

    fun clearStatus() {
        _internalState.value = _internalState.value.copy(
            statusMessage = null,
            errorMessage = null
        )
    }

    override fun onCleared() {
        super.onCleared()
        audioReader.shutdown()
    }
}
