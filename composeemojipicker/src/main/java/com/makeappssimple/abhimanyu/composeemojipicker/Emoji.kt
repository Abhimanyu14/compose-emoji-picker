package com.makeappssimple.abhimanyu.composeemojipicker

import emoji.core.model.NetworkEmoji

data class Emoji(
    val character: String,
    val codePoint: String,
    val group: String,
    val subgroup: String,
    val unicodeName: String,
) {
    constructor(networkEmoji: NetworkEmoji) : this(
        character = networkEmoji.character,
        codePoint = networkEmoji.codePoint,
        group = networkEmoji.group,
        subgroup = networkEmoji.subgroup,
        unicodeName = networkEmoji.unicodeName,
    )
}

fun Emoji.asExternalModel(): NetworkEmoji {
    return NetworkEmoji(
        character = this.character,
        codePoint = this.codePoint,
        group = this.group,
        subgroup = this.subgroup,
        unicodeName = this.unicodeName,
    )
}
