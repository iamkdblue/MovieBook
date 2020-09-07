package com.bms.moviebook.api

import com.bms.moviebook.model.cast.CastResponse
import com.bms.moviebook.model.popular.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("popular")
    suspend fun getPopularVideo(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET("top_rated")
    suspend fun getTopRatedVideo(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET("{movie_id}/credits")
    suspend fun getMovieCredit(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): CastResponse

    @GET("{movie_id}/similar")
    suspend fun getSimilarMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieResponse

}