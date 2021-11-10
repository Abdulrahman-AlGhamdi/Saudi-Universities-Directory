package com.ss.universitiesdirectory.repository.universities

import com.ss.universitiesdirectory.model.UniversityModel
import com.ss.universitiesdirectory.repository.common.ApiService
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class UniversitiesRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getAllUniversities(region: String) = flow {
        this.emit(UniversitiesState.Loading)
        val language = Locale.getDefault().language
        val response = apiService.getAllUniversities(language, region)

        if (!response.isSuccessful) this.emit(UniversitiesState.Failed(response.message()))
        else response.body()?.let { this.emit(UniversitiesState.Successful(it)) }
    }

    sealed class UniversitiesState {
        object Loading                                                : UniversitiesState()
        data class Successful(val universities: List<UniversityModel>) : UniversitiesState()
        data class Failed(val message: String)                        : UniversitiesState()
    }
}