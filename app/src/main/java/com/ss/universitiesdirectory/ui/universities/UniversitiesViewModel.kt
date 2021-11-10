package com.ss.universitiesdirectory.ui.universities

import androidx.lifecycle.ViewModel
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UniversitiesViewModel @Inject constructor(
    private val universitiesRepository: UniversitiesRepository
) : ViewModel() {

    fun getAllUniversities(region: String = "") =
        universitiesRepository.getAllUniversities(region)
}