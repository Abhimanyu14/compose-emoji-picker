package com.makeappssimple.abhimanyu.composeemojipicker.utils

import androidx.emoji2.text.EmojiCompat
import com.makeappssimple.abhimanyu.composeemojipicker.Emoji

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

@Deprecated(
    message = "Use isEmojiCharacterRenderable()",
    replaceWith = ReplaceWith(
        expression = "isEmojiCharacterRenderable(emoji.character)",
    ),
)
internal fun isEmojiRenderable(
    emoji: Emoji,
): Boolean {
    return EmojiCompat.isConfigured() &&
            EmojiCompat.get().loadState == EmojiCompat.LOAD_STATE_SUCCEEDED &&
            EmojiCompat.get().getEmojiMatch(
                emoji.character,
                Int.MAX_VALUE
            ) == EmojiCompat.EMOJI_SUPPORTED
}
