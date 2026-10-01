package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_articles ORDER BY timestamp DESC")
    fun getAllArticles(): Flow<List<NewsArticleEntity>>

    @Query("SELECT * FROM news_articles WHERE isBookmarked = 1 ORDER BY timestamp DESC")
    fun getBookmarkedArticles(): Flow<List<NewsArticleEntity>>

    @Query("SELECT * FROM news_articles WHERE category = :category ORDER BY timestamp DESC")
    fun getArticlesByCategory(category: String): Flow<List<NewsArticleEntity>>

    @Query("SELECT * FROM news_articles WHERE id = :id LIMIT 1")
    suspend fun getArticleById(id: String): NewsArticleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<NewsArticleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: NewsArticleEntity)

    @Query("UPDATE news_articles SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmark(id: String, isBookmarked: Boolean)

    @Query("DELETE FROM news_articles WHERE isBookmarked = 0")
    suspend fun clearUnsavedArticles()

    @Query("DELETE FROM news_articles WHERE id = :id")
    suspend fun deleteArticleById(id: String)
}
