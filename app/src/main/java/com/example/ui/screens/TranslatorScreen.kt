package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.NewsArticle
import com.example.tts.AudioPlaybackState
import com.example.ui.theme.VerifiedGreen
import com.example.ui.theme.VerifiedGreenBg

@Composable
fun TranslatorScreen(
    inputText: String,
    isTranslating: Boolean,
    translatedArticle: NewsArticle?,
    audioState: AudioPlaybackState,
    onInputTextChange: (String) -> Unit,
    onTranslateClick: () -> Unit,
    onAudioToggle: (NewsArticle) -> Unit,
    onBookmarkToggle: (NewsArticle) -> Unit,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val isPlayingThis = translatedArticle != null && audioState.currentArticleId == translatedArticle.id && audioState.isPlaying

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("translator_screen")
    ) {
        // Header
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "مترجم و تحلیل‌گر صوتی اخبار",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "ترجمه روان به فارسی، استخراج نکات و گویندگی صوتی با هوش مصنوعی",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input Field for Foreign News
        OutlinedTextField(
            value = inputText,
            onValueChange = onInputTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .testTag("translator_input_field"),
            placeholder = {
                Text("متن خبر، گزارش انگلیسی یا لینک خبری در مورد ایران را اینجا جای‌گذاری (Paste) کنید...")
            },
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                if (inputText.isNotEmpty()) {
                    IconButton(onClick = { onInputTextChange("") }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "پاک کردن")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Quick Paste & Sample Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {
                    clipboardManager.getText()?.let { clipText ->
                        onInputTextChange(clipText.text)
                    }
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.testTag("paste_clipboard_btn")
            ) {
                Icon(imageVector = Icons.Default.ContentPaste, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("چسباندن متن از حافظه", style = MaterialTheme.typography.labelSmall)
            }

            FilledTonalButton(
                onClick = {
                    onInputTextChange(
                        "International Atomic Energy Agency reports constructive technical consultations regarding monitoring protocols and verification equipment installations, marking positive momentum in regional compliance discussions."
                    )
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.testTag("sample_text_btn")
            ) {
                Text("بارگذاری نمونه خبر بین‌المللی", style = MaterialTheme.typography.labelSmall)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Submit Button
        Button(
            onClick = onTranslateClick,
            enabled = inputText.isNotBlank() && !isTranslating,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("submit_translate_btn"),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (isTranslating) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("در حال پردازش، ترجمه و ساخت فایل صوتی با هوش مصنوعی...")
            } else {
                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("ترجمه هوشمند به فارسی و تولید صوت", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Result Card
        AnimatedVisibility(visible = translatedArticle != null) {
            translatedArticle?.let { article ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("translated_result_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = VerifiedGreenBg
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = null,
                                        tint = VerifiedGreen,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${article.credibilityScore}٪ شاخص اعتماد خبر",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = VerifiedGreen,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = article.categoryFa,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Translated Title
                        Text(
                            text = article.titleFa,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                lineHeight = 26.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Audio Action Button
                        Button(
                            onClick = { onAudioToggle(article) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("listen_translated_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isPlayingThis) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(
                                imageVector = if (isPlayingThis) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isPlayingThis) "توقف خوانش گزارش صوتی" else "گوش دادن به گزارش با صدای هوش مصنوعی (TTS)",
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Key Points
                        if (article.keyPoints.isNotEmpty()) {
                            Text(
                                text = "نکات کلیدی استخراج‌شده:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            article.keyPoints.forEach { point ->
                                Row(
                                    modifier = Modifier.padding(vertical = 3.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = point,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        // Full Translated Body
                        Text(
                            text = "متن کامل ترجمه فارسی:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = article.contentFa,
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 24.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}
