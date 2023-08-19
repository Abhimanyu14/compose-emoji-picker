package com.makeappssimple.abhimanyu.composeemojipicker.utils

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.areStatusBarsVisible
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@Composable
fun NavigationBarPadding() {
    Spacer(
        modifier = Modifier.navigationBarPadding(),
    )
}

@Composable
fun StatusBarPadding() {
    Spacer(
        modifier = Modifier.statusBarPadding(),
    )
}

@OptIn(ExperimentalLayoutApi::class)
private fun Modifier.statusBarPadding(): Modifier {
    return composed {
        if (WindowInsets.areStatusBarsVisible) {
            this.padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
            )
        } else {
            this
        }
    }
}

private fun Modifier.navigationBarPadding(): Modifier {
    return composed {
        this.windowInsetsPadding(
            insets = WindowInsets.navigationBars,
        )
    }
}
