package com.example.data.model

data class NewsArticle(
    val id: String,
    val titleFa: String,
    val titleOriginal: String,
    val source: String,
    val category: String, // politics, economy, sports, culture, society, tech, energy
    val categoryFa: String,
    val summaryFa: String, // خلاصه کوتاه و مفید از مهمترین نکات
    val executiveSummaryFa: String = "", // خلاصه راهبردی و هوشمند
    val contentFa: String,
    val originalContent: String,
    val keyPoints: List<String>,
    val credibilityScore: Int, // e.g. 96
    val credibilityBadge: String,
    val publishedAt: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isBookmarked: Boolean = false,
    val aiAnalysis: String = "",
    val readTimeMinutes: Int = 2
)

enum class NewsCategory(val id: String, val titleFa: String, val iconName: String) {
    ALL("all", "همه موضوعات", "ViewList"),
    POLITICS("politics", "سیاست و دیپلماسی", "Gavel"),
    ECONOMY("economy", "اقتصاد و بازار", "TrendingUp"),
    SPORTS("sports", "ورزش و مسابقات", "SportsSoccer"),
    CULTURE("culture", "فرهنگ و هنر", "Palette"),
    SOCIETY("society", "جامعه و شهروندی", "People"),
    TECH("tech", "علم و فناوری", "Memory"),
    ENERGY("energy", "انرژی و محیط‌زیست", "Eco");

    companion object {
        fun fromId(id: String): NewsCategory {
            return entries.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: ALL
        }
    }
}

enum class TimeRangeFilter(val id: String, val titleFa: String) {
    ALL("all", "همه زمان‌ها"),
    TODAY("today", "امروز (۲۴ ساعت گذشته)"),
    WEEK("week", "یک هفته اخیر")
}
