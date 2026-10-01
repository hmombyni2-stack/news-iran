package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.NewsViewModel
import com.example.ui.components.ArticleDetailView
import com.example.ui.components.AudioPlayerBar
import com.example.ui.screens.BookmarksScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.TranslatorScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppScreen(viewModel: NewsViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.clearStatus()
        }
    }

    LaunchedEffect(uiState.statusMessage) {
        uiState.statusMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearStatus()
        }
    }

    // If an article is selected, display its full detail view
    if (uiState.selectedArticle != null) {
        ArticleDetailView(
            article = uiState.selectedArticle!!,
            audioState = uiState.audioState,
            onBack = { viewModel.selectArticle(null) },
            onToggleAudio = { viewModel.toggleAudioPlayback(uiState.selectedArticle!!) },
            onBookmarkToggle = { viewModel.toggleBookmark(uiState.selectedArticle!!) },
            onSpeedChange = { viewModel.setAudioSpeed(it) }
        )
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Column {
                // Persistent audio player bar appears above navigation bar whenever TTS is active
                AudioPlayerBar(
                    audioState = uiState.audioState,
                    onPlayPause = {
                        if (uiState.audioState.isPlaying) {
                            viewModel.pauseAudio()
                        } else {
                            viewModel.resumeAudio()
                        }
                    },
                    onStop = { viewModel.stopAudio() },
                    onSpeedChange = { viewModel.setAudioSpeed(it) }
                )

                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    val navItems = listOf(
                        Triple(0, "اخبار روز", Icons.Default.Newspaper),
                        Triple(1, "جستجوی پیشرفته", Icons.Default.Search),
                        Triple(2, "مترجم هوشمند", Icons.Default.Translate),
                        Triple(3, "نشان‌شده‌ها", Icons.Default.Bookmark)
                    )

                    navItems.forEach { (index, title, icon) ->
                        val isSelected = uiState.selectedTab == index
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.selectTab(index) },
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = title
                                )
                            },
                            label = {
                                Text(
                                    text = title,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            modifier = Modifier.testTag("nav_tab_$index")
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.selectedTab) {
                0 -> HomeScreen(
                    articles = uiState.filteredArticles,
                    selectedCategory = uiState.filters.category,
                    isRefreshing = uiState.isRefreshing,
                    statusMessage = uiState.statusMessage,
                    audioState = uiState.audioState,
                    onCategorySelected = { viewModel.selectCategory(it) },
                    onRefresh = { viewModel.refreshNews() },
                    onArticleClick = { viewModel.selectArticle(it) },
                    onAudioToggle = { viewModel.toggleAudioPlayback(it) },
                    onBookmarkToggle = { viewModel.toggleBookmark(it) }
                )
                1 -> SearchScreen(
                    filters = uiState.filters,
                    filteredArticles = uiState.filteredArticles,
                    isSearching = uiState.isRefreshing,
                    audioState = uiState.audioState,
                    onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                    onCategoryChange = { viewModel.selectCategory(it) },
                    onSourceChange = { viewModel.setSourceFilter(it) },
                    onTimeRangeChange = { viewModel.setTimeRangeFilter(it) },
                    onMinCredibilityChange = { viewModel.setMinCredibility(it) },
                    onResetFilters = { viewModel.resetFilters() },
                    onSearchExecute = { viewModel.performAiSearch(it) },
                    onArticleClick = { viewModel.selectArticle(it) },
                    onAudioToggle = { viewModel.toggleAudioPlayback(it) },
                    onBookmarkToggle = { viewModel.toggleBookmark(it) }
                )
                2 -> TranslatorScreen(
                    inputText = uiState.translationInput,
                    isTranslating = uiState.isTranslating,
                    translatedArticle = uiState.translatedArticleResult,
                    audioState = uiState.audioState,
                    onInputTextChange = { viewModel.setTranslationInput(it) },
                    onTranslateClick = { viewModel.translateCustomArticle() },
                    onAudioToggle = { viewModel.toggleAudioPlayback(it) },
                    onBookmarkToggle = { viewModel.toggleBookmark(it) }
                )
                3 -> BookmarksScreen(
                    bookmarkedArticles = uiState.bookmarkedArticles,
                    audioState = uiState.audioState,
                    onArticleClick = { viewModel.selectArticle(it) },
                    onAudioToggle = { viewModel.toggleAudioPlayback(it) },
                    onBookmarkToggle = { viewModel.toggleBookmark(it) },
                    onPlayAll = {
                        if (uiState.bookmarkedArticles.isNotEmpty()) {
                            viewModel.playArticleAudio(uiState.bookmarkedArticles.first())
                        }
                    }
                )
            }
        }
    }
}
