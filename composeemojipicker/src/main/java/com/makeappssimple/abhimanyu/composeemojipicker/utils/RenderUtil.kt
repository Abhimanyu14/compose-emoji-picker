package com.makeappssimple.abhimanyu.composeemojipicker.utils

import androidx.emoji2.text.EmojiCompat

internal fun isEmojiCharacterRenderable(
    emojiCharacter: String,
): Boolean {
    return EmojiCompat.isConfigured() &&
            EmojiCompat.get().loadState == EmojiCompat.LOAD_STATE_SUCCEEDED &&
            EmojiCompat.get().getEmojiMatch(
                emojiCharacter,
                Int.MAX_VALUE
            ) == EmojiCompat.EMOJI_SUPPORTED
}
