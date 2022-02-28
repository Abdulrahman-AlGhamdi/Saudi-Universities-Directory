package com.ss.universitiesdirectory.repository.universities

import com.ss.universitiesdirectory.model.UniversityModel
import com.ss.universitiesdirectory.repository.common.ApiService
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository.UniversitiesState.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import javax.inject.Inject

class UniversitiesRepository @Inject constructor(private val apiService: ApiService) {

    private var _universitiesState = MutableStateFlow<UniversitiesState>(Idle)
    val universitiesState = _universitiesState.asStateFlow()

    suspend fun getAllUniversities(region: String = "") {
        _universitiesState.value = Loading
        val language = Locale.getDefault().language
        val response = apiService.getAllUniversities(language, region)

        if (!response.isSuccessful) _universitiesState.value = Failed(response.message())
        else response.body()?.let { _universitiesState.value = Successful(it) }
    }

    sealed class UniversitiesState {
        object Idle                                                    : UniversitiesState()
        object Loading                                                 : UniversitiesState()
        data class Failed(val message: String)                         : UniversitiesState()
        data class Successful(val universities: List<UniversityModel>) : UniversitiesState()
    }
}