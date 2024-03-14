package com.makeappssimple.abhimanyu.composeemojipicker

sealed interface MyResult<out T> {
    object Loading : MyResult<Nothing>

    data class Error(
        val exception: Throwable? = null,
    ) : MyResult<Nothing>

    data class Success<T>(
        val data: T,
    ) : MyResult<T>
}
