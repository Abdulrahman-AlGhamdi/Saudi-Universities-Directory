package com.ss.universitiesdirectory.repository.common

import com.ss.universitiesdirectory.model.UniversityModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/v1/universities")
    suspend fun getAllUniversities(
        @Query("language") language: String,
        @Query("region") region: String
    ): Response<List<UniversityModel>>

    companion object { const val BASE_URL = "https://saudiuniversitiesdirectory.herokuapp.com/" }
}