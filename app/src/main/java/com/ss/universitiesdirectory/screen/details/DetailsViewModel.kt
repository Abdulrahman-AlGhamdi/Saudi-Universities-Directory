package com.ss.universitiesdirectory.screen.details

import androidx.lifecycle.ViewModel
import com.ss.universitiesdirectory.repository.details.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
) : ViewModel() {

    fun openApp(stringUri: String) = detailsRepository.openApp(stringUri)
}