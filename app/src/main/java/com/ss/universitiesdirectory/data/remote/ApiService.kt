package com.ss.universitiesdirectory.data.remote

import com.ss.universitiesdirectory.data.model.univeristy.UniversityModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/v1/universities/{language}")
    suspend fun getAllUniversities(
        @Path("language") language: String,
        @Query("region") region: String
    ): Response<List<UniversityModel>>

    companion object { const val BASE_URL = "https://saudiuniversitiesdirectory.herokuapp.com/" }
}