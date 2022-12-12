package com.ss.universitiesdirectory.manager.details

sealed interface DetailsManager {

    fun openApp(stringUri: String)
}