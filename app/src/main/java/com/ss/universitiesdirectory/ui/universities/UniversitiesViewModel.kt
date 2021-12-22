package com.ss.universitiesdirectory.ui.universities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.universitiesdirectory.model.UniversityModel
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository.UniversitiesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniversitiesViewModel @Inject constructor(
    private val universitiesRepository: UniversitiesRepository
) : ViewModel() {

    var universities = mutableListOf<UniversityModel>()
    private var _universitiesState = MutableStateFlow<UniversitiesState>(UniversitiesState.Idle)
    val universitiesState = _universitiesState.asStateFlow()

    fun getAllUniversities(region: String = "") = viewModelScope.launch {
        universitiesRepository.getAllUniversities(region).collect {
            _universitiesState.value = it
        }
    }
}