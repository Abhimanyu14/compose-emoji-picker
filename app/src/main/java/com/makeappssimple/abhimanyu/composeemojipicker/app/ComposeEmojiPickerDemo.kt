package com.makeappssimple.abhimanyu.composeemojipicker.app

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.composeemojipicker.ComposeEmojiPickerBottomSheetUI
import com.makeappssimple.abhimanyu.composeemojipicker.ComposeEmojiPickerEmojiUI
import com.makeappssimple.abhimanyu.composeemojipicker.app.utils.capitalizeWords

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeEmojiPickerDemo() {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    var isModalBottomSheetVisible by remember {
        mutableStateOf(false)
    }
    var selectedEmoji by remember {
        mutableStateOf("😃")
    }
    var searchText by remember {
        mutableStateOf("")
    }

    if (isModalBottomSheetVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            shape = RectangleShape,
            tonalElevation = 0.dp,
            onDismissRequest = {
                isModalBottomSheetVisible = false
                searchText = ""
            },
            dragHandle = null,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                ComposeEmojiPickerBottomSheetUI(
                    onEmojiClick = { emoji ->
                        isModalBottomSheetVisible = false
                        selectedEmoji = emoji.character
                    },
                    onEmojiLongClick = { emoji ->
                        Toast.makeText(
                            context,
                            emoji.unicodeName.capitalizeWords(),
                            Toast.LENGTH_SHORT,
                        ).show()
                    },
                    searchText = searchText,
                    updateSearchText = { updatedSearchText ->
                        searchText = updatedSearchText
                    },
                )
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        ComposeEmojiPickerEmojiUI(
            emojiCharacter = selectedEmoji,
            onClick = {
                isModalBottomSheetVisible = true
            },
            fontSize = 56.sp,
        )
    }
}
