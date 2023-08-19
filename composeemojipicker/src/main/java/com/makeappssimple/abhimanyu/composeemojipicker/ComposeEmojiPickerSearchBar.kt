package com.makeappssimple.abhimanyu.composeemojipicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.composeemojipicker.utils.defaultPlaceholderText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeEmojiPickerSearchBar(
    modifier: Modifier = Modifier,

    isAutoFocusEnabled: Boolean = true,
    backgroundColor: Color,
    searchBarColor: Color,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    placeholderText: String = defaultPlaceholderText,
    text: String,
    onSearch: () -> Unit = {},
    onValueChange: (updatedSearchText: String) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
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
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .height(
                    height = 40.dp,
                )
                .focusRequester(
                    focusRequester = focusRequester,
                ),
            textStyle = MaterialTheme.typography.bodyMedium
                .copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                },
            ),
            singleLine = true,
            cursorBrush = SolidColor(
                value = MaterialTheme.colorScheme.primary,
            ),
            decorationBox = {
                TextFieldDefaults.DecorationBox(
                    value = text,
                    innerTextField = it,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    placeholder = {
                        Text(
                            text = placeholderText,
                            style = MaterialTheme.typography.bodyMedium
                                .copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                ),
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(
                                    all = 0.dp,
                                )
                                .size(
                                    size = 20.dp,
                                ),
                        )
                    },
                    trailingIcon = if (text.isNotBlank()) {
                        {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(
                                        shape = CircleShape,
                                    )
                                    .clickable {
                                        focusManager.clearFocus()
                                        onValueChange("")
                                    }
                                    .padding(
                                        all = 6.dp,
                                    )
                                    .size(
                                        size = 20.dp,
                                    ),
                            )
                        }
                    } else {
                        null
                    },
                    shape = CircleShape,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = searchBarColor,
                        unfocusedContainerColor = searchBarColor,
                        disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedIndicatorColor = Transparent,
                        unfocusedIndicatorColor = Transparent,
                    ),
                    contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                        start = 0.dp,
                        top = 0.dp,
                        end = 0.dp,
                        bottom = 0.dp,
                    ),
                    container = {
                        Box(
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
                        )
                    },
                )
            },
        )
    }
}
