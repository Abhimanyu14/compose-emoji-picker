package com.makeappssimple.abhimanyu.composeemojipicker.app.utils

internal fun String.capitalizeWords(): String {
    return this
        .split(' ')
        .joinToString(" ") { word ->
            word.lowercase().replaceFirstChar {
                it.uppercaseChar()
            }
        }
}
