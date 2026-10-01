package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.NewsArticle

@Entity(tableName = "news_articles")
data class NewsArticleEntity(
    @PrimaryKey
    val id: String,
    val titleFa: String,
    val titleOriginal: String,
    val source: String,
    val category: String,
    val categoryFa: String,
    val summaryFa: String,
    val executiveSummaryFa: String,
    val contentFa: String,
    val originalContent: String,
    val keyPointsRaw: String,
    val credibilityScore: Int,
    val credibilityBadge: String,
    val publishedAt: String,
    val timestamp: Long,
    val isBookmarked: Boolean,
    val aiAnalysis: String,
    val readTimeMinutes: Int
) {
    fun toDomain(): NewsArticle {
        val points = if (keyPointsRaw.isBlank()) emptyList() else keyPointsRaw.split("|||")
        return NewsArticle(
            id = id,
            titleFa = titleFa,
            titleOriginal = titleOriginal,
            source = source,
            category = category,
            categoryFa = categoryFa,
            summaryFa = summaryFa,
            executiveSummaryFa = executiveSummaryFa,
            contentFa = contentFa,
            originalContent = originalContent,
            keyPoints = points,
            credibilityScore = credibilityScore,
            credibilityBadge = credibilityBadge,
            publishedAt = publishedAt,
            timestamp = timestamp,
            isBookmarked = isBookmarked,
            aiAnalysis = aiAnalysis,
            readTimeMinutes = readTimeMinutes
        )
    }

    companion object {
        fun fromDomain(article: NewsArticle): NewsArticleEntity {
            return NewsArticleEntity(
                id = article.id,
                titleFa = article.titleFa,
                titleOriginal = article.titleOriginal,
                source = article.source,
                category = article.category,
                categoryFa = article.categoryFa,
                summaryFa = article.summaryFa,
                executiveSummaryFa = article.executiveSummaryFa.ifBlank { article.summaryFa },
                contentFa = article.contentFa,
                originalContent = article.originalContent,
                keyPointsRaw = article.keyPoints.joinToString("|||"),
                credibilityScore = article.credibilityScore,
                credibilityBadge = article.credibilityBadge,
                publishedAt = article.publishedAt,
                timestamp = article.timestamp,
                isBookmarked = article.isBookmarked,
                aiAnalysis = article.aiAnalysis,
                readTimeMinutes = article.readTimeMinutes
            )
        }
    }
}
