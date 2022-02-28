package com.ss.universitiesdirectory.ui.universities

import androidx.lifecycle.ViewModel
import com.ss.universitiesdirectory.data.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UniversitiesViewModel @Inject constructor(
    universitiesRepository: UniversitiesRepository
) : ViewModel() {

    var universities = mutableListOf<UniversityModel>()

    val universitiesState  = universitiesRepository.universitiesState
}