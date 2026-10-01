package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.model.NewsCategory

@Composable
fun CategorySelector(
    selectedCategory: NewsCategory,
    onCategorySelected: (NewsCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .testTag("category_selector"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(NewsCategory.entries.toTypedArray()) { category ->
            val isSelected = category == selectedCategory
            FilterChip(
                selected = isSelected,
                onClick = { onCategorySelected(category) },
                label = {
                    Text(
                        text = category.titleFa,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                leadingIcon = {
                    val icon: ImageVector = when (category) {
                        NewsCategory.ALL -> Icons.AutoMirrored.Filled.List
                        NewsCategory.POLITICS -> Icons.Default.Gavel
                        NewsCategory.ECONOMY -> Icons.AutoMirrored.Filled.TrendingUp
                        NewsCategory.SPORTS -> Icons.Default.SportsSoccer
                        NewsCategory.CULTURE -> Icons.Default.AutoStories
                        NewsCategory.SOCIETY -> Icons.Default.People
                        NewsCategory.TECH -> Icons.Default.Memory
                        NewsCategory.ENERGY -> Icons.Default.Eco
                    }
                    Icon(imageVector = icon, contentDescription = null)
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.testTag("chip_${category.id}")
            )
        }
    }
}
