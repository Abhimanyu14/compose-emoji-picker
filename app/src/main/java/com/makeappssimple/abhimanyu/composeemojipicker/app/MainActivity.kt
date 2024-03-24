package com.makeappssimple.abhimanyu.composeemojipicker.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.makeappssimple.abhimanyu.composeemojipicker.app.theme.ComposeEmojiPickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ComposeEmojiPickerTheme {
                ComposeEmojiPickerDemo()
            }
        }
    }
}
