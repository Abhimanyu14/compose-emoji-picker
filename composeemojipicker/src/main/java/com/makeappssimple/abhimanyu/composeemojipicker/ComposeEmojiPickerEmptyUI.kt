package com.makeappssimple.abhimanyu.composeemojipicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ComposeEmojiPickerEmptyUI() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = 32.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "No Emoji Found 😕",
        )
    }
}
