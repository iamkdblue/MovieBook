package com.bms.moviebook.api

import com.bms.moviebook.model.popular.PopularVideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("popular")
    suspend fun getPopularVideo(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
    ): PopularVideoResponse

    @GET("top_rated")
    suspend fun getTopRatedVideo(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
    ): PopularVideoResponse

}