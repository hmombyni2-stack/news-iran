package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tts.AudioPlaybackState

@Composable
fun AudioPlayerBar(
    audioState: AudioPlaybackState,
    onPlayPause: () -> Unit,
    onStop: () -> Unit,
    onSpeedChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val isVisible = audioState.currentArticleTitle != null

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .testTag("audio_player_bar"),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.secondary,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                // Progress bar if multiple paragraphs
                if (audioState.totalParagraphs > 1) {
                    val progress = (audioState.currentParagraphIndex + 1).toFloat() / audioState.totalParagraphs.toFloat()
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .clip(CircleShape),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left (or Right in RTL): Audio Wave + Info
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Animated wave equalizer
                        AudioWaveVisualizer(isPlaying = audioState.isPlaying)

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = audioState.currentArticleTitle ?: "در حال خوانش خبر...",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = audioState.currentSource ?: "هوش مصنوعی فارسی",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                )
                                if (audioState.totalParagraphs > 0) {
                                    Text(
                                        text = " • بخش ${audioState.currentParagraphIndex + 1} از ${audioState.totalParagraphs}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                }
                            }
                        }
                    }

                    // Playback Controls
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Speed Switcher
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White.copy(alpha = 0.15f),
                            modifier = Modifier
                                .clickable {
                                    val nextSpeed = when (audioState.speechRate) {
                                        1.0f -> 1.25f
                                        1.25f -> 1.5f
                                        1.5f -> 0.85f
                                        else -> 1.0f
                                    }
                                    onSpeedChange(nextSpeed)
                                }
                                .testTag("audio_speed_btn")
                        ) {
                            Text(
                                text = "${audioState.speechRate}x",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                            )
                        }

                        // Play/Pause Button
                        FilledIconButton(
                            onClick = onPlayPause,
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .size(40.dp)
                                .testTag("audio_play_pause_btn")
                        ) {
                            Icon(
                                imageVector = if (audioState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (audioState.isPlaying) "توقف خوانش" else "ادامه خوانش",
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Close/Stop Button
                        IconButton(
                            onClick = onStop,
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("audio_stop_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "بستن خوانشگر",
                                tint = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AudioWaveVisualizer(isPlaying: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")

    val h1 by infiniteTransition.animateFloat(
        initialValue = 4.dp.value,
        targetValue = 20.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(450, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "h1"
    )
    val h2 by infiniteTransition.animateFloat(
        initialValue = 18.dp.value,
        targetValue = 6.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(380, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "h2"
    )
    val h3 by infiniteTransition.animateFloat(
        initialValue = 8.dp.value,
        targetValue = 22.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(520, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "h3"
    )

    Row(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.15f))
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val barColor = if (isPlaying) MaterialTheme.colorScheme.primary else Color.White.copy(alpha = 0.4f)
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(if (isPlaying) h1.dp else 6.dp)
                .clip(CircleShape)
                .background(barColor)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(if (isPlaying) h2.dp else 12.dp)
                .clip(CircleShape)
                .background(barColor)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(if (isPlaying) h3.dp else 8.dp)
                .clip(CircleShape)
                .background(barColor)
        )
    }
}
