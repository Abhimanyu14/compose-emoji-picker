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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.composeemojipicker.utils.NavigationBarPadding
import com.makeappssimple.abhimanyu.composeemojipicker.utils.StatusBarPadding
import com.makeappssimple.abhimanyu.composeemojipicker.utils.defaultEmojiFontSize
import com.makeappssimple.abhimanyu.composeemojipicker.utils.defaultEmojiPadding
import com.makeappssimple.abhimanyu.composeemojipicker.utils.isEmojiRenderable
import emoji.core.datasource.EmojiDataSource
import emoji.core.datasource.EmojiDataSourceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale.filter
import kotlin.math.ceil
import kotlin.math.floor

val black = Color(0xFF1C2020)
val white = Color(0xFFF2F7F7)
val gray = Color(0xFF848686)

/*
*/
/**
 * Mandatory parameters - Only [onEmojiClick] is mandatory parameter.
 *
 * @param onEmojiClick Click handler
 *
 * @param backgroundColor Whether the incoming min constraints should be passed to content.
 * @param onEmojiLongClick Whether the incoming min constraints should be passed to content.
 *//*

@Composable
fun ComposeEmojiPickerBottomSheetUI(
    modifier: Modifier = Modifier,

    addNavigationBarPadding: Boolean = true,
    addStatusBarPadding: Boolean = true,
    backgroundColor: Color = defaultBackgroundColor,
    emojiGroups: Map<String, List<NetworkEmoji>>,

    groupHeader: @Composable ((group: String, emojis: List<NetworkEmoji>) -> Unit)? = null,
    searchBar: @Composable (() -> Unit)? = null,
    emptyUI: @Composable (() -> Unit)? = null,
    emojiUI: @Composable (() -> Unit)? = null,
) {
    val firstEmoji: String? = emojiGroups.values.firstOrNull()?.firstOrNull()?.character

    Column(
        modifier = modifier
            .background(
                color = backgroundColor,
            )
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = 100.dp,
            ),
    ) {
        if (addStatusBarPadding) {
            StatusBarPadding()
        }
        searchBar?.invoke()
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (firstEmoji == null) {
                    item {
                        emptyUI?.invoke()
                    }
                } else {
                    composeEmojiPickerEmojiGrid(
                        firstEmoji = firstEmoji,
                        maxWidth = maxWidth,
                        emojiGroups = emojiGroups,
                        groupHeader = groupHeader,
                        emojiUI = emojiUI
                    )
                }
                item {
                    if (addNavigationBarPadding) {
                        NavigationBarPadding()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.composeEmojiPickerEmojiGrid(
    firstEmoji: String,
    maxWidth: Dp,
    emojiGroups: Map<String, List<NetworkEmoji>>,
    groupHeader: @Composable ((group: String, emojis: List<NetworkEmoji>) -> Unit)?,
    emojiUI: @Composable (() -> Unit)?,
) {
    val emojiWidth = rememberEmojiWidth(
        firstEmoji = firstEmoji,
        emojiFontSize = 28.sp,
    )
    val (columnCount, itemPadding) = getColumnData(
        maxColumnWidth = maxWidth,
        emojiWidth = emojiWidth,
    )
    emojiGroups.map { (group, emojis) ->
        stickyHeader {
            groupHeader?.invoke(group, emojis)
        }
        emojis.chunked(
            size = columnCount,
        ).map {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    it.forEach { emoji ->
                        emojiUI?.invoke()
                    }
                }
            }
        }
    }
}
*/

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComposeEmojiPickerBottomSheetUI(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    groupTitleTextColor: Color = MaterialTheme.colorScheme.onBackground,
    searchBarColor: Color = MaterialTheme.colorScheme.primaryContainer,
    groupTitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    emojiFontSize: TextUnit = defaultEmojiFontSize,
    searchText: String = "",
    onEmojiClick: (emoji: Emoji) -> Unit,
    onEmojiLongClick: ((emoji: Emoji) -> Unit)? = null,
    updateSearchText: ((updatedSearchText: String) -> Unit)? = null,
) {
    var isLoading by remember {
        mutableStateOf(false)
    }
    var emojis by remember {
        mutableStateOf(emptyList<Emoji>())
    }
    val emojiGroups by remember(
        key1 = emojis,
        key2 = searchText,
    ) {
        mutableStateOf(emojis.filter { emoji ->
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
        })
    }

    val firstEmoji = emojiGroups.values.firstOrNull()?.firstOrNull()?.character
    val emojiWidth = rememberEmojiWidth(
        firstEmoji = firstEmoji,
        emojiFontSize = emojiFontSize,
    )



    LaunchedEffect(
        key1 = Unit,
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            val emojiDataSource: EmojiDataSource = EmojiDataSourceImpl()
            withContext(Dispatchers.Main) {
                isLoading = true
                emojis = emojiDataSource.getAllEmojis().map {
                    Emoji(it)
                }.filter {
                    isEmojiRenderable(it)
                }
                isLoading = false
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
                minHeight = 100.dp,
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
                if (isLoading) {
                    item {
                        ComposeEmojiPickerLoadingUI()
                    }
                } else if (firstEmoji == null || emojiWidth == null) {
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
                        ).map {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    it.forEach { emoji ->
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
    val itemPadding =
        floor(((maxColumnWidth - (ceilEmojiWidth * columnCount)) / (2 * columnCount)).value).dp
    return Pair(columnCount, itemPadding)
}

@Composable
private fun rememberEmojiWidth(
    firstEmoji: String?,
    emojiFontSize: TextUnit
): Dp? {
    if (firstEmoji == null) {
        return null
    }
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    return remember {
        with(density) {
            firstEmoji.run {
                textMeasurer.measure(
                    text = firstEmoji,
                    style = TextStyle(
                        fontSize = emojiFontSize,
                    ),
                ).size.width.toDp()
            }
        }
    }
}
