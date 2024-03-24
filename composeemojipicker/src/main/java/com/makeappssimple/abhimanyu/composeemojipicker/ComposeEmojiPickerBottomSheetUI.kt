package com.makeappssimple.abhimanyu.composeemojipicker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.makeappssimple.abhimanyu.composeemojipicker.utils.NavigationBarPadding
import com.makeappssimple.abhimanyu.composeemojipicker.utils.StatusBarPadding
import com.makeappssimple.abhimanyu.composeemojipicker.utils.defaultEmojiFontSize
import com.makeappssimple.abhimanyu.composeemojipicker.utils.defaultEmojiPadding
import com.makeappssimple.abhimanyu.composeemojipicker.utils.isEmojiCharacterRenderable
import emoji.core.datasource.EmojiDataSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.ceil
import kotlin.math.floor

private object ComposeEmojiPickerBottomSheetConstants {
    const val CACHE_FILE_NAME = "http_cache"
    val defaultMinHeight = 100.dp
}

/**
 * Mandatory parameters - Only [onEmojiClick] is mandatory parameter.
 *
 * @param onEmojiClick Click handler
 *
 * @param backgroundColor Whether the incoming min constraints should be passed to content.
 * @param onEmojiLongClick Whether the incoming min constraints should be passed to content.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
public fun ComposeEmojiPickerBottomSheetUI(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    searchBarColor: Color = MaterialTheme.colorScheme.primaryContainer,

    groupTitleTextColor: Color = MaterialTheme.colorScheme.onBackground,
    groupTitleTextStyle: TextStyle = MaterialTheme.typography.headlineMedium,

    emojiFontSize: TextUnit = defaultEmojiFontSize,
    searchText: String = "",
    onEmojiClick: (emoji: Emoji) -> Unit,
    onEmojiLongClick: ((emoji: Emoji) -> Unit)? = null,
    updateSearchText: ((updatedSearchText: String) -> Unit)? = null,
) {
    val context = LocalContext.current
    var emojisResult: MyResult<List<Emoji>> by remember {
        mutableStateOf(MyResult.Loading)
    }
    val emojiGroups: Map<String, List<Emoji>> = remember(
        key1 = emojisResult,
        key2 = searchText,
    ) {
        when (emojisResult) {
            is MyResult.Success -> {
                (emojisResult as MyResult.Success<List<Emoji>>).data.filter { emoji ->
                    if (searchText.isBlank()) {
                        true
                    } else {
                        emoji.unicodeName.contains(
                            other = searchText,
                        )
                    }
                }.groupBy { emoji ->
                    emoji.group
                }.filter { (_, emojis) ->
                    emojis.isNotEmpty()
                }
            }

            else -> {
                emptyMap()
            }
        }
    }
    val firstEmoji: String? = remember(emojiGroups) {
        if (emojiGroups.isNotEmpty()) {
            emojiGroups.values.firstOrNull()?.firstOrNull()?.character
        } else {
            null
        }
    }
    val emojiWidth = rememberEmojiWidth(
        firstEmoji = firstEmoji,
        emojiFontSize = emojiFontSize,
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        withContext(
            context = Dispatchers.IO,
        ) {
            withContext(
                context = Dispatchers.Main,
            ) {
                emojisResult = try {
                    MyResult.Success(
                        data = EmojiDataSourceImpl().getAllEmojis(
                            cacheFile = File(
                                context.cacheDir,
                                ComposeEmojiPickerBottomSheetConstants.CACHE_FILE_NAME,
                            ),
                        ).map { networkEmoji ->
                            Emoji(
                                networkEmoji = networkEmoji,
                            )
                        }.filter {
                            isEmojiCharacterRenderable(
                                emojiCharacter = it.character,
                            )
                        },
                    )
                } catch (
                    ioException: Exception,
                ) {
                    MyResult.Error(
                        exception = ioException,
                    )
                } catch (
                    exception: Exception,
                ) {
                    MyResult.Error(
                        exception = exception,
                    )
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = backgroundColor,
            )
            .defaultMinSize(
                minHeight = ComposeEmojiPickerBottomSheetConstants.defaultMinHeight,
            ),
    ) {
        StatusBarPadding()
        ComposeEmojiPickerSearchBar(
            backgroundColor = backgroundColor,
            searchBarColor = searchBarColor,
            text = searchText,
            onValueChange = updateSearchText ?: {},
        )
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                when (emojisResult) {
                    is MyResult.Error -> item {
                        ComposeEmojiPickerErrorUI()
                    }

                    MyResult.Loading -> item {
                        ComposeEmojiPickerLoadingUI()
                    }

                    is MyResult.Success -> {
                        if (firstEmoji == null || emojiWidth == null) {
                            item {
                                ComposeEmojiPickerEmptyUI()
                            }
                        } else {
                            val (columnCount, itemPadding) = getColumnData(
                                maxColumnWidth = maxWidth,
                                emojiWidth = emojiWidth,
                            )
                            emojiGroups.map { (group, emojis) ->
                                stickyHeader {
                                    ComposeEmojiPickerGroupTitle(
                                        backgroundColor = backgroundColor,
                                        titleTextColor = groupTitleTextColor,
                                        titleText = "$group (${emojis.size})",
                                        titleTextStyle = groupTitleTextStyle,
                                    )
                                }
                                emojis.chunked(
                                    size = columnCount,
                                ).map { emojiList ->
                                    item {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            emojiList.map { emoji ->
                                                ComposeEmojiPickerEmojiUI(
                                                    modifier = Modifier
                                                        .padding(
                                                            horizontal = itemPadding,
                                                        ),
                                                    emojiCharacter = emoji.character,
                                                    onClick = {
                                                        onEmojiClick(emoji)
                                                    },
                                                    onLongClick = {
                                                        onEmojiLongClick?.invoke(emoji)
                                                    },
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    NavigationBarPadding()
                }
            }
        }
    }
}

private fun getColumnData(
    maxColumnWidth: Dp,
    emojiWidth: Dp,
): Pair<Int, Dp> {
    val emojiWidthWithPadding = emojiWidth + (defaultEmojiPadding * 2)
    val columnCount = (maxColumnWidth / (emojiWidthWithPadding)).toInt()
    val ceilEmojiWidth = ceil(emojiWidthWithPadding.value).dp
    val itemPadding = floor(
        x = max(
            a = 0.dp,
            b = (maxColumnWidth - (ceilEmojiWidth * columnCount)) / (2 * columnCount),
        ).value,
    ).dp
    return Pair(columnCount, itemPadding)
}

@Composable
private fun rememberEmojiWidth(
    firstEmoji: String?,
    emojiFontSize: TextUnit,
): Dp? {
    if (firstEmoji == null) {
        return null
    }
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    return remember(
        firstEmoji,
        emojiFontSize,
    ) {
        with(density) {
            textMeasurer.measure(
                text = firstEmoji,
                style = TextStyle(
                    fontSize = emojiFontSize,
                ),
            ).size.width.toDp()
        }
    }
}
