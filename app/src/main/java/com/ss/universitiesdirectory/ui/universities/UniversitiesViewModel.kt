package com.ss.universitiesdirectory.ui.universities

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ss.universitiesdirectory.data.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class UniversitiesViewModel @Inject constructor(
    universitiesRepository: UniversitiesRepository
) : ViewModel() {

    val universitiesState = universitiesRepository.universitiesState

    val snackBarHost = SnackbarHostState()
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    var isSearching by mutableStateOf(false)
    var searchText by mutableStateOf("")
    var universities by mutableStateOf(listOf<UniversityModel>())
    var listOfUniversities by mutableStateOf(universities)

    fun searchList(query: String = "") {
        listOfUniversities = universities.filter {
            it.name.lowercase().contains(query.lowercase())
        }
        Log.d("TAG", "searchList: $listOfUniversities")
    }
}