package com.eversadclown.borutoapplication.data.remote

import com.eversadclown.borutoapplication.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

//интерфейс обращения к удаленному серверу
interface BorutoApi {

    @GET("/boruto/heroes")
    suspend fun getAllHeroes(
        @Query("page") page: Int = 1
    ): ApiResponse

    @GET("/boruto/heroes/search")
    suspend fun searchHeroes(
        @Query("name") name: String = ""
    ): ApiResponse

}