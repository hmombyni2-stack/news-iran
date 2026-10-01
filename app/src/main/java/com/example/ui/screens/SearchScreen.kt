package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.NewsArticle
import com.example.data.model.NewsCategory
import com.example.data.model.TimeRangeFilter
import com.example.tts.AudioPlaybackState
import com.example.ui.SearchFilterState
import com.example.ui.components.NewsCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    filters: SearchFilterState,
    filteredArticles: List<NewsArticle>,
    isSearching: Boolean,
    audioState: AudioPlaybackState,
    onSearchQueryChange: (String) -> Unit,
    onCategoryChange: (NewsCategory) -> Unit,
    onSourceChange: (String) -> Unit,
    onTimeRangeChange: (TimeRangeFilter) -> Unit,
    onMinCredibilityChange: (Int) -> Unit,
    onResetFilters: () -> Unit,
    onSearchExecute: (String) -> Unit,
    onArticleClick: (NewsArticle) -> Unit,
    onAudioToggle: (NewsArticle) -> Unit,
    onBookmarkToggle: (NewsArticle) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAdvancedFilters by remember { mutableStateOf(false) }

    val sources = listOf(
        "all" to "همه منابع",
        "Reuters" to "رویترز",
        "Associated Press" to "آسوشیتدپرس",
        "BBC" to "بی‌بی‌سی",
        "Bloomberg" to "بلومبرگ",
        "Euronews" to "یورونیوز",
        "AFP" to "خبرگزاری فرانسه"
    )

    val trendingTopics = listOf(
        "کریدور ترانزیت شمال جنوب",
        "مذاکرات دیپلماتیک",
        "مزارع انرژی خورشیدی",
        "هوش مصنوعی و دانشگاه‌ها",
        "کشتی آزاد و قهرمانی جهان",
        "صنایع دستی و بی‌ینال میلان"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("search_screen")
    ) {
        // Search Header
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "جستجوی پیشرفته و رصد اخبار ایران",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "فیلتر بر اساس کلمات کلیدی، منبع، بازه زمانی و دسته‌بندی",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = { showAdvancedFilters = !showAdvancedFilters },
                        modifier = Modifier.testTag("toggle_filters_btn")
                    ) {
                        Icon(
                            imageVector = if (showAdvancedFilters) Icons.Default.ExpandLess else Icons.Default.FilterList,
                            contentDescription = "فیلترهای پیشرفته",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Keyword Search Input
                OutlinedTextField(
                    value = filters.query,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_input_field"),
                    placeholder = { Text("کلمه کلیدی یا موضوع خبر را جستجو کنید...") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "جستجو")
                    },
                    trailingIcon = {
                        if (filters.query.isNotEmpty()) {
                            IconButton(onClick = { onSearchQueryChange("") }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "پاک کردن")
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                // Advanced Filters Section (Expandable)
                AnimatedVisibility(visible = showAdvancedFilters) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            // Category Filter
                            Text(
                                text = "موضوع و دسته‌بندی خبر:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(NewsCategory.entries.toTypedArray()) { cat ->
                                    val isSelected = filters.category == cat
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { onCategoryChange(cat) },
                                        label = { Text(cat.titleFa, style = MaterialTheme.typography.labelSmall) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Source Filter
                            Text(
                                text = "منبع خبرگزاری:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(sources) { (key, label) ->
                                    val isSelected = filters.source == key
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { onSourceChange(key) },
                                        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Time Range Filter
                            Text(
                                text = "بازه زمانی انتشار:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                TimeRangeFilter.entries.forEach { timeRange ->
                                    val isSelected = filters.timeRange == timeRange
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { onTimeRangeChange(timeRange) },
                                        label = { Text(timeRange.titleFa, style = MaterialTheme.typography.labelSmall) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Reset Filters Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                OutlinedButton(
                                    onClick = onResetFilters,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.testTag("reset_filters_btn")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.RestartAlt,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("بازنشانی فیلترها", style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // AI Deep Search Button
                Button(
                    onClick = { onSearchExecute(filters.query) },
                    enabled = !isSearching,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("ai_search_submit_btn")
                ) {
                    if (isSearching) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("در حال جستجو و ترجمه با Gemini...")
                    } else {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (filters.query.isBlank()) "رصد هوشمند تازه‌ترین اخبار با هوش مصنوعی" else "جستجو و فیلتر پیشرفته در رسانه‌ها",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Trending Topics
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "موضوعات داغ و پرتکرار:",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                trendingTopics.forEach { topic ->
                    SuggestionChip(
                        onClick = {
                            onSearchQueryChange(topic)
                            onSearchExecute(topic)
                        },
                        label = { Text(topic, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
        }

        // Results Count Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "نتایج فیلتر شده: ${filteredArticles.size} خبر معتبر",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (filters.category != NewsCategory.ALL || filters.source != "all" || filters.timeRange != TimeRangeFilter.ALL) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "فیلتر فعال",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }

        if (filteredArticles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "هیچ خبری با معیارهای انتخابی پیدا نشد. می‌توانید فیلترها را بازنشانی کنید یا با فشردن دکمه هوش مصنوعی جستجوی عمیق‌تری انجام دهید.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("search_results_list"),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(filteredArticles, key = { it.id }) { article ->
                    val isCurrentInAudio = audioState.currentArticleId == article.id
                    NewsCard(
                        article = article,
                        isPlaying = audioState.isPlaying,
                        isCurrentInAudio = isCurrentInAudio,
                        onCardClick = { onArticleClick(article) },
                        onAudioToggle = { onAudioToggle(article) },
                        onBookmarkToggle = { onBookmarkToggle(article) }
                    )
                }
            }
        }
    }
}
