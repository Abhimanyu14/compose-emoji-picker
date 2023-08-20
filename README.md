# Compose Emoji Picker

<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>

Compose Emoji Picker uses [Emoji Core](https://github.com/Abhimanyu14/emoji-core) to fetch all latest emojis and display them in a grid.

# Features

1. Search and Filter
2. Grouping
3. Sticky Group Names

# Setup

Latest version can be found here </br>
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

# Dependencies

* Jetpack Compose dependencies
* emoji2

# Issues

Please do create new issues if there are any bugs or feature requests. </br>
You can also directly reach out to me via [LinkedIn](https://www.linkedin.com/in/abhimanyu-n/)

## Find this repository useful? ♥️

Support it by joining __[stargazers](https://github.com/Abhimanyu14/compose-emoji-picker/stargazers)__ for this repository. 🌟  
Also __[follow](https://github.com/Abhimanyu14)__ me for my next creations! 🤗

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
