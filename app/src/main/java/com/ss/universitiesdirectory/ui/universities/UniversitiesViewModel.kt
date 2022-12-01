package com.ss.universitiesdirectory.ui.universities

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.universitiesdirectory.data.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniversitiesViewModel @Inject constructor(
    private val universitiesRepository: UniversitiesRepository
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            universitiesRepository.getAllUniversities()
        }
    }

    val universitiesState = universitiesRepository.universitiesState

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