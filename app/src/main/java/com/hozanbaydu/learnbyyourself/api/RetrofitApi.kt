package com.hozanbaydu.learnbyyourself.api

import com.hozanbaydu.learnbyyourself.model.Images
import com.hozanbaydu.learnbyyourself.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String=API_KEY
    ): Response<Images>
}