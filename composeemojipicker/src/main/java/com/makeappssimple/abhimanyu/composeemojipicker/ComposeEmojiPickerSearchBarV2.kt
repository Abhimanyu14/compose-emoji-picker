package com.makeappssimple.abhimanyu.composeemojipicker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.composeemojipicker.utils.defaultPlaceholderText

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun ComposeEmojiPickerSearchBarV2(
    modifier: Modifier = Modifier,

    isAutoFocusEnabled: Boolean = true,
    backgroundColor: Color,
    searchBarColor: Color,

    searchTextFieldState: TextFieldState,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,

    placeholderText: String = defaultPlaceholderText,
    placeholderTextColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    placeholderTextStyle: TextStyle = MaterialTheme.typography.headlineLarge,

    cursorBrush: Brush = SolidColor(
        value = MaterialTheme.colorScheme.primary,
    ),
    onSearch: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val keyboardController: SoftwareKeyboardController? =
        LocalSoftwareKeyboardController.current
    val focusRequester: FocusRequester = remember {
        FocusRequester()
    }

    if (isAutoFocusEnabled) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
            )
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .requiredHeight(
                    height = 40.dp,
                )
                .clip(
                    shape = CircleShape,
                )
                .background(
                    color = searchBarColor,
                ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        end = 8.dp,
                    )
                    .fillMaxWidth(),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(
                            horizontal = 14.dp,
                        )
                        .size(
                            size = 20.dp,
                        ),
                )
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .height(
                            height = 40.dp,
                        )
                ) {
                    if (searchTextFieldState.text.isEmpty()) {
                        Text(
                            text = placeholderText,
                            style = placeholderTextStyle
                                .copy(
                                    color = placeholderTextColor,
                                ),
                        )
                    }
                    BasicTextField(
                        state = searchTextFieldState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(
                                focusRequester = focusRequester,
                            ),
                        textStyle = textStyle.copy(
                            color = textColor,
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search,
                        ),
                        onKeyboardAction = {
                            onSearch()
                        },
                        lineLimits = TextFieldLineLimits.SingleLine,
                        cursorBrush = cursorBrush,
                    )
                }
                if (searchTextFieldState.text.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(
                                shape = CircleShape,
                            )
                            .clickable {
                                focusManager.clearFocus()
                                searchTextFieldState.clearText()
                            }
                            .padding(
                                all = 6.dp,
                            )
                            .size(
                                size = 20.dp,
                            ),
                    )
                }
            }
        }
    }
}
