package com.makeappssimple.abhimanyu.composeemojipicker

internal sealed interface MyResult<out T> {
    data object Loading : MyResult<Nothing>

    data class Error(
        val exception: Throwable? = null,
    ) : MyResult<Nothing>

    data class Success<T>(
        val data: T,
    ) : MyResult<T>
}
