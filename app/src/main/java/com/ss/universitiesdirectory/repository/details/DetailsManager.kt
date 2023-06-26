package com.ss.universitiesdirectory.repository.details

sealed interface DetailsManager {

    fun openApp(stringUri: String)
}