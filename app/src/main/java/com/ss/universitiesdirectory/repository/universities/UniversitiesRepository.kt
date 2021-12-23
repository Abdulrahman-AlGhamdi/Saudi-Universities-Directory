package com.ss.universitiesdirectory.repository.universities

import com.ss.universitiesdirectory.model.UniversityModel
import com.ss.universitiesdirectory.repository.common.ApiService
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository.UniversitiesState.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class UniversitiesRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getAllUniversities(region: String) = flow {
        this.emit(Loading)
        val language = Locale.getDefault().language
        val response = apiService.getAllUniversities(language, region)

        if (!response.isSuccessful) this.emit(Failed(response.message()))
        else response.body()?.let { this.emit(Successful(it)) }
    }.flowOn(Dispatchers.IO)

    sealed class UniversitiesState {
        object Idle                                                    : UniversitiesState()
        object Loading                                                 : UniversitiesState()
        data class Failed(val message: String)                         : UniversitiesState()
        data class Successful(val universities: List<UniversityModel>) : UniversitiesState()
    }
}