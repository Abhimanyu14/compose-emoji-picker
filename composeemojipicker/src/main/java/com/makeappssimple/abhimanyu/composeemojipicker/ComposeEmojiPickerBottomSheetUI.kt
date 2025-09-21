package com.makeappssimple.abhimanyu.composeemojipicker

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.composeemojipicker.utils.StatusBarPadding
import com.makeappssimple.abhimanyu.composeemojipicker.utils.defaultEmojiFontSize

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
@SuppressLint("UnusedBoxWithConstraintsScope")
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
        ComposeEmojiPickerEmojisList(
            backgroundColor = backgroundColor,
            groupTitleTextColor = groupTitleTextColor,
            groupTitleTextStyle = groupTitleTextStyle,
            emojiFontSize = emojiFontSize,
            searchText = searchText,
            onEmojiClick = onEmojiClick,
            onEmojiLongClick = onEmojiLongClick,
        )
    }
}
