package com.ss.universitiesdirectory.screen.universities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.universitiesdirectory.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository
import com.ss.universitiesdirectory.utils.ResponseState
import com.ss.universitiesdirectory.utils.ResponseState.Idle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniversitiesViewModel @Inject constructor(
    private val universitiesRepository: UniversitiesRepository
) : ViewModel() {

    private var _universitiesState = MutableStateFlow<ResponseState<List<UniversityModel>>>(Idle())
    val universitiesState = _universitiesState.asStateFlow()

    init {
        getUniversities()
    }

    private fun getUniversities() = viewModelScope.launch {
        universitiesRepository.getUniversities().collect {
            _universitiesState.value = it
        }
    }

    fun getSearchList(query: String) = viewModelScope.launch {
        universitiesRepository.getSearchList(query).collect {
            _universitiesState.value = it
        }
    }
}