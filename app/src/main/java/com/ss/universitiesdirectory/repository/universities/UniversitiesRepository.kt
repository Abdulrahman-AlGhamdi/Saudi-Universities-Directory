package com.ss.universitiesdirectory.repository.universities

import com.ss.universitiesdirectory.data.remote.ApiService
import com.ss.universitiesdirectory.data.remote.ResponseStatus
import com.ss.universitiesdirectory.data.remote.ResponseStatus.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import javax.inject.Inject

class UniversitiesRepository @Inject constructor(private val apiService: ApiService) {

    private var _universitiesState = MutableStateFlow<ResponseStatus>(Idle)
    val universitiesState = _universitiesState.asStateFlow()

    suspend fun getAllUniversities(region: String = "") {
        _universitiesState.value = Progress
        val language = Locale.getDefault().language
        val response = apiService.getAllUniversities(language, region)

        if (!response.isSuccessful) _universitiesState.value = Failed(response.message())
        else response.body()?.let { _universitiesState.value = Success(it) }
    }
}