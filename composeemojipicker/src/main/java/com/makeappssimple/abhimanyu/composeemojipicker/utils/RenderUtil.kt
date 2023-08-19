package com.makeappssimple.abhimanyu.composeemojipicker.utils

import androidx.emoji2.text.EmojiCompat
import emoji.core.model.NetworkEmoji

fun isEmojiRenderable(
    emoji: NetworkEmoji,
): Boolean {
    return EmojiCompat.isConfigured() &&
            EmojiCompat.get().loadState == EmojiCompat.LOAD_STATE_SUCCEEDED &&
            EmojiCompat.get().getEmojiMatch(
                emoji.character,
                Int.MAX_VALUE
            ) == EmojiCompat.EMOJI_SUPPORTED
}
