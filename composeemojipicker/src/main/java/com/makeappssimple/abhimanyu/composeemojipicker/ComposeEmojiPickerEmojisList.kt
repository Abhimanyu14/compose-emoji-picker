package com.makeappssimple.abhimanyu.composeemojipicker

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.makeappssimple.abhimanyu.composeemojipicker.utils.CACHE_FILE_NAME
import com.makeappssimple.abhimanyu.composeemojipicker.utils.NavigationBarPadding
import com.makeappssimple.abhimanyu.composeemojipicker.utils.defaultEmojiFontSize
import com.makeappssimple.abhimanyu.composeemojipicker.utils.defaultEmojiPadding
import com.makeappssimple.abhimanyu.composeemojipicker.utils.isEmojiCharacterRenderable
import emoji.core.datasource.EmojiDataSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.ceil
import kotlin.math.floor

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
internal fun ComposeEmojiPickerEmojisList(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,

    groupTitleTextColor: Color = MaterialTheme.colorScheme.onBackground,
    groupTitleTextStyle: TextStyle = MaterialTheme.typography.headlineMedium,

    emojiFontSize: TextUnit = defaultEmojiFontSize,
    searchText: String = "",
    onEmojiClick: (emoji: Emoji) -> Unit,
    onEmojiLongClick: ((emoji: Emoji) -> Unit)? = null,
) {
    val context = LocalContext.current
    var emojisResult: MyResult<List<Emoji>> by remember {
        mutableStateOf(
            value = MyResult.Loading,
        )
    }
    var columnCount by remember {
        mutableIntStateOf(
            value = 0,
        )
    }
    var itemPadding by remember {
        mutableStateOf(
            value = 0.dp,
        )
    }

    val firstEmoji: String? = remember(
        key1 = emojisResult,
    ) {
        (emojisResult as? MyResult.Success<List<Emoji>>)
            ?.data
            ?.firstOrNull()
            ?.character
    }
    val emojiWidth = rememberEmojiWidth(
        firstEmoji = firstEmoji,
        emojiFontSize = emojiFontSize,
    )
    val filteredEmojis = remember(
        key1 = emojisResult,
        key2 = searchText,
    ) {
        when (emojisResult) {
            is MyResult.Success -> {
                (emojisResult as MyResult.Success<List<Emoji>>).data
                    .filter { emoji ->
                        if (searchText.isBlank()) {
                            true
                        } else {
                            emoji.unicodeName.contains(
                                other = searchText,
                            )
                        }
                    }
            }

            else -> {
                emptyList()
            }
        }
    }
    val uiItems: List<ComposeEmojiPickerBottomSheetUIItem> = remember(
        key1 = filteredEmojis,
        key2 = searchText,
        key3 = columnCount,
    ) {
        if (columnCount == 0) {
            emptyList()
        } else {
            filteredEmojis
                .groupBy { emoji ->
                    emoji.group
                }
                .filter { (_, emojis) ->
                    emojis.isNotEmpty()
                }
                .flatMap { (groupName, emojisInGroup) ->
                    val items =
                        mutableListOf<ComposeEmojiPickerBottomSheetUIItem>()
                    items.add(
                        element = ComposeEmojiPickerBottomSheetUIItem.EmojiGroupHeader(
                            title = groupName,
                        ),
                    )
                    emojisInGroup
                        .chunked(
                            size = columnCount,
                        )
                        .forEach { emojis ->
                            items.add(
                                element = ComposeEmojiPickerBottomSheetUIItem.EmojiGroupItems(
                                    emojis = emojis,
                                ),
                            )
                        }
                    items
                }
        }
    }

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
                        data = EmojiDataSourceImpl()
                            .getAllEmojis(
                                cacheFile = File(
                                    context.cacheDir,
                                    CACHE_FILE_NAME,
                                ),
                            )
                            .filter {
                                isEmojiCharacterRenderable(
                                    emojiCharacter = it.character,
                                )
                            }
                            .map { networkEmoji ->
                                Emoji(
                                    networkEmoji = networkEmoji,
                                )
                            },
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

    when (emojisResult) {
        is MyResult.Error -> {
            ComposeEmojiPickerErrorUI()
            NavigationBarPadding()
        }

        is MyResult.Loading -> {
            ComposeEmojiPickerLoadingUI()
            NavigationBarPadding()
        }

        is MyResult.Success -> {
            if (emojiWidth == null) {
                ComposeEmojiPickerEmptyUI()
                NavigationBarPadding()
            } else {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    LaunchedEffect(
                        key1 = maxWidth,
                        key2 = emojiWidth,
                    ) {
                        val (calculatedColumnCount, calculatedItemPadding) = getColumnData(
                            maxColumnWidth = maxWidth,
                            emojiWidth = emojiWidth,
                        )
                        columnCount = calculatedColumnCount
                        itemPadding = calculatedItemPadding
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        uiItems.forEach {
                            when (it) {
                                is ComposeEmojiPickerBottomSheetUIItem.EmojiGroupHeader -> {
                                    stickyHeader {
                                        ComposeEmojiPickerGroupTitle(
                                            backgroundColor = backgroundColor,
                                            titleTextColor = groupTitleTextColor,
                                            titleText = it.title,
                                            titleTextStyle = groupTitleTextStyle,
                                        )
                                    }
                                }

                                is ComposeEmojiPickerBottomSheetUIItem.EmojiGroupItems -> {
                                    item {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            it.emojis.forEach { emoji ->
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
                                                        onEmojiLongClick?.invoke(
                                                            emoji
                                                        )
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
    }
}

private fun getColumnData(
    maxColumnWidth: Dp,
    emojiWidth: Dp,
): Pair<Int, Dp> {
    val emojiWidthPlusPadding = emojiWidth + (defaultEmojiPadding * 2)
    val columnCount = (maxColumnWidth / (emojiWidthPlusPadding)).toInt()
    val ceilEmojiWidth = ceil(emojiWidthPlusPadding.value).dp
    val itemPadding = floor(
        x = max(
            a = 0.dp,
            b = (maxColumnWidth - (ceilEmojiWidth * columnCount)) / (2 * columnCount),
        ).value,
    ).dp
    return Pair(
        first = columnCount,
        second = itemPadding,
    )
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
        key1 = firstEmoji,
        key2 = emojiFontSize,
    ) {
        with(
            receiver = density,
        ) {
            textMeasurer
                .measure(
                    text = firstEmoji,
                    style = TextStyle(
                        fontSize = emojiFontSize,
                    ),
                )
                .size
                .width
                .toDp()
        }
    }
}
