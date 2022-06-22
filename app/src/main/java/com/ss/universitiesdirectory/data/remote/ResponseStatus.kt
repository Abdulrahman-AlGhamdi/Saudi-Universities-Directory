package com.ss.universitiesdirectory.data.remote

sealed class ResponseStatus {
    object Idle : ResponseStatus()
    object Progress : ResponseStatus()
    data class Success<T>(val data: T) : ResponseStatus()
    data class Failed(val message: String) : ResponseStatus()
}