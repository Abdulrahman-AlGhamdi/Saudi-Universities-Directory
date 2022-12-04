package com.ss.universitiesdirectory.ui.universities

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    private fun getUniversities() {
        viewModelScope.launch {
            universitiesRepository.getUniversities().collect {
                _universitiesState.value = it
            }
        }
    }

    val snackBarHost = SnackbarHostState()
    var isSearching by mutableStateOf(false)
    var searchText by mutableStateOf("")

    var universities by mutableStateOf(listOf<UniversityModel>())
    var universitiesSearchedList by mutableStateOf(universities)

    fun searchList(query: String = "") {
        universitiesSearchedList = if (query.isNotBlank()) universities.filter {
            if (!it.province) it.name.lowercase().contains(query.lowercase()) else false
        } else universities
    }
}