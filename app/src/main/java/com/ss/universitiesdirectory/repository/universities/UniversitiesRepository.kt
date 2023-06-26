package com.ss.universitiesdirectory.repository.universities

import com.ss.universitiesdirectory.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.utils.ResponseState
import kotlinx.coroutines.flow.Flow

sealed interface UniversitiesRepository {

    suspend fun getUniversities(): Flow<ResponseState<List<UniversityModel>>>

    fun getSearchList(query: String): Flow<ResponseState<List<UniversityModel>>>
}