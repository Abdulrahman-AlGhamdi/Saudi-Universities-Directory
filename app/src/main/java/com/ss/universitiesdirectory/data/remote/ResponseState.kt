package com.ss.universitiesdirectory.data.remote

sealed class ResponseState<T>(val data: T? = null, val message: String? = null) {
    class Idle<T> : ResponseState<T>()
    class Progress<T> : ResponseState<T>()
    class Success<T>(data: T?): ResponseState<T>(data = data)
    class Error<T>(message: String): ResponseState<T>(message = message)
}