package com.ss.universitiesdirectory.ui.details

import androidx.lifecycle.ViewModel
import com.ss.universitiesdirectory.manager.details.DetailsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsManager: DetailsManager
) : ViewModel() {

    fun openApp(stringUri: String) = detailsManager.openApp(stringUri)
}