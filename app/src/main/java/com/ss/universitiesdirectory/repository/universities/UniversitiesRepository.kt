package com.ss.universitiesdirectory.repository.universities

import com.ss.universitiesdirectory.data.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.data.remote.ResponseState
import kotlinx.coroutines.flow.StateFlow

interface UniversitiesRepository {

    val universitiesState: StateFlow<ResponseState<List<UniversityModel>>>

    suspend fun getAllUniversities(region: String = "")
}