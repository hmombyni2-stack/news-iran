package com.example.data.repository

import com.example.data.local.NewsDao
import com.example.data.local.NewsArticleEntity
import com.example.data.model.NewsArticle
import com.example.data.remote.GeminiNewsService
import com.example.data.remote.SampleNewsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class NewsRepository(
    private val newsDao: NewsDao,
    private val geminiService: GeminiNewsService = GeminiNewsService()
) {

    val allArticles: Flow<List<NewsArticle>> = newsDao.getAllArticles().map { list ->
        list.map { it.toDomain() }
    }

    val bookmarkedArticles: Flow<List<NewsArticle>> = newsDao.getBookmarkedArticles().map { list ->
        list.map { it.toDomain() }
    }

    suspend fun initializeIfEmpty() {
        val current = newsDao.getAllArticles().first()
        if (current.isEmpty()) {
            val entities = SampleNewsData.initialArticles.map { NewsArticleEntity.fromDomain(it) }
            newsDao.insertArticles(entities)
        }
    }

    suspend fun refreshNews(
        query: String = "",
        category: String = "all",
        source: String = "all",
        timeRange: String = "all"
    ): Result<List<NewsArticle>> {
        val result = geminiService.searchAndTranslateNews(query, category, source, timeRange)
        if (result.isSuccess) {
            val fetched = result.getOrNull() ?: emptyList()
            if (fetched.isNotEmpty()) {
                val currentBookmarkedIds = newsDao.getBookmarkedArticles().first().map { it.id }.toSet()
                val entitiesToInsert = fetched.map { article ->
                    val isBookmarked = currentBookmarkedIds.contains(article.id)
                    NewsArticleEntity.fromDomain(article.copy(isBookmarked = isBookmarked))
                }
                newsDao.insertArticles(entitiesToInsert)
            }
        }
        return result
    }

    suspend fun toggleBookmark(article: NewsArticle) {
        val newStatus = !article.isBookmarked
        newsDao.updateBookmark(article.id, newStatus)
    }

    suspend fun translateForeignText(textOrUrl: String): Result<NewsArticle> {
        val result = geminiService.translateAndAnalyzeForeignText(textOrUrl)
        if (result.isSuccess) {
            val article = result.getOrNull()
            if (article != null) {
                newsDao.insertArticle(NewsArticleEntity.fromDomain(article))
            }
        }
        return result
    }

    suspend fun getArticleById(id: String): NewsArticle? {
        return newsDao.getArticleById(id)?.toDomain()
    }

    suspend fun saveArticle(article: NewsArticle) {
        newsDao.insertArticle(NewsArticleEntity.fromDomain(article))
    }
}
