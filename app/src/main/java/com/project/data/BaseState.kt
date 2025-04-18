package com.project.data

sealed class BaseState<T> {
    class Loading<T> : BaseState<T>()
    data class Success<T>(val response: T) : BaseState<T>()
    data class Error<T>(val message: String) : BaseState<T>()
}