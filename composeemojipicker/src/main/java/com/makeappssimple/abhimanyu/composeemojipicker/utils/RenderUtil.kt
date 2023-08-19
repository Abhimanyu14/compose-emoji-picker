package com.makeappssimple.abhimanyu.composeemojipicker.utils

import androidx.emoji2.text.EmojiCompat
import com.makeappssimple.abhimanyu.composeemojipicker.Emoji

fun isEmojiRenderable(
    emoji: Emoji,
): Boolean {
    return EmojiCompat.isConfigured() &&
            EmojiCompat.get().loadState == EmojiCompat.LOAD_STATE_SUCCEEDED &&
            EmojiCompat.get().getEmojiMatch(
                emoji.character,
                Int.MAX_VALUE
            ) == EmojiCompat.EMOJI_SUPPORTED
}
