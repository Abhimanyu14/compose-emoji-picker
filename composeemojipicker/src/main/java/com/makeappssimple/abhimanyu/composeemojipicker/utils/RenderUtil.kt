package com.makeappssimple.abhimanyu.composeemojipicker.utils

import android.graphics.Paint
import android.text.TextPaint

private val emojiGlyphPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

internal fun isEmojiCharacterRenderable(
    emojiCharacter: String,
): Boolean {
    return emojiGlyphPaint.hasGlyph(emojiCharacter)
}
