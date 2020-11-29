package com.android.breakingbad.common

sealed class TaskResult<out R> {
    data class Success<out T>(val data: T) : TaskResult<T>()
    data class Error(val exception: Exception) : TaskResult<Nothing>()
}
