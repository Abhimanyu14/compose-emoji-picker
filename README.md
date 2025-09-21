<a href="https://opensource.org/licenses/Apache-2.0" target="_blank"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
<a href="https://jitpack.io/#Abhimanyu14/compose-emoji-picker" target="_blank"><img alt="License" src="https://jitpack.io/v/Abhimanyu14/compose-emoji-picker.svg"/></a>
<a href="https://github.com/Abhimanyu14/compose-emoji-picker/stargazers" target="_blank"><img alt="License" src="https://img.shields.io/github/stars/Abhimanyu14/compose-emoji-picker?style=social"/></a>
<a href="https://github.com/Abhimanyu14/compose-emoji-picker/fork" target="_blank"><img alt="License" src="https://img.shields.io/github/forks/Abhimanyu14/compose-emoji-picker?logo=github&style=social"/></a>

# Compose Emoji Picker

Compose Emoji Picker uses [Emoji Core](https://github.com/Abhimanyu14/emoji-core) to fetch all the latest emojis and display them.

# Features

1. Added checks to skip emojis that can not be rendered instead of showing them as tofu (􏿿).
2. Searching and Filtering
3. All emoji from Unicode emoji version 17.0 (latest) are available. (as supported by device)
4. Sticky header for the group name

# Setup

The latest version can be found here </br>
[![](https://jitpack.io/v/Abhimanyu14/compose-emoji-picker.svg)](https://jitpack.io/#Abhimanyu14/compose-emoji-picker)

1. Add it in your root `build.gradle` at the end of repositories:

```kotlin
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

2. Add dependency to module `build.gradle`

```kotlin
dependencies {
    implementation "com.github.Abhimanyu14:compose-emoji-picker:$latest_version"
}
```

# Demo

https://github.com/Abhimanyu14/compose-emoji-picker/assets/29737108/88491846-bbba-4543-b79e-4166d7ddb6c0

# Usage

<details>

<summary>Using State based TextField (TextFieldState)</summary>

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeEmojiPickerDemo() {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    var isModalBottomSheetVisible by remember {
        mutableStateOf(
            value = false,
        )
    }
    var selectedEmoji by remember {
        mutableStateOf(
            value = "😃",
        )
    }
    val searchTextFieldState = remember {
        TextFieldState()
    }

    if (isModalBottomSheetVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            shape = RectangleShape,
            tonalElevation = 0.dp,
            onDismissRequest = {
                isModalBottomSheetVisible = false
                searchTextFieldState.clearText()
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
                    searchTextFieldState = searchTextFieldState,
                )
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                all = 16.dp,
            ),
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
```

</details>

<details>

<summary>Using Value based TextField</summary>

```kotlin
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
            windowInsets = WindowInsets(0),
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
```

</details>

# Dependencies

- Jetpack Compose dependencies
- emoji2

# Issues

Please do create new issues if there are any bugs or feature requests. </br>
You can also directly reach out to me via [LinkedIn](https://www.linkedin.com/in/abhimanyu-n/)

## Find this repository useful? ♥️

Support it by joining **[stargazers](https://github.com/Abhimanyu14/compose-emoji-picker/stargazers)** for this repository. 🌟  
Also **[follow](https://github.com/Abhimanyu14)** me for my next creations! 🤗

# License

```
Copyright 2023 Abhimanyu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
