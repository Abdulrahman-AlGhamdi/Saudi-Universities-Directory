package com.ss.universitiesdirectory.screen.details

import androidx.lifecycle.ViewModel
import com.ss.universitiesdirectory.repository.details.DetailsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsManager: DetailsManager
) : ViewModel() {

    fun openApp(stringUri: String) = detailsManager.openApp(stringUri)
}