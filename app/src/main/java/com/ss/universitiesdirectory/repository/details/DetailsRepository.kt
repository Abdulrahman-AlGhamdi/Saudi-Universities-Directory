package com.ss.universitiesdirectory.repository.details

sealed interface DetailsRepository {

    fun openApp(stringUri: String)
}