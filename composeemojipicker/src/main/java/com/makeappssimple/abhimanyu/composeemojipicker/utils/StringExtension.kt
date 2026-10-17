package com.makeappssimple.abhimanyu.composeemojipicker.utils

internal fun String.capitalizeWords(): String {
    return this
        .split(' ')
        .joinToString(" ") { word ->
            word.lowercase().replaceFirstChar { char ->
                char.uppercaseChar()
            }
        }
}
