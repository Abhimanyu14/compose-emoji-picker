package com.makeappssimple.abhimanyu.composeemojipicker

internal sealed class ComposeEmojiPickerBottomSheetUIItem {
    data class EmojiGroupHeader(
        val title: String,
    ) : ComposeEmojiPickerBottomSheetUIItem()

    data class EmojiGroupItems(
        val emojis: List<Emoji>,
    ) : ComposeEmojiPickerBottomSheetUIItem()
}
