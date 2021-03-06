package com.android.kalaa.data.repository

import com.bms.moviebook.api.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getPopularVideo(apiKey: String, page: Int) =
        flow { emit(apiService.getPopularVideo(apiKey, page)) }

    suspend fun getTopRatedVideo(apiKey: String, page: Int) =
        flow { emit(apiService.getTopRatedVideo(apiKey, page)) }

    suspend fun getMovieCredit(apiKey: String, movieId: Int) =
        flow { emit(apiService.getMovieCredit(movieId, apiKey)) }

    suspend fun getSimilarMovie(apiKey: String, movieId: Int) =
        flow { emit(apiService.getSimilarMovie(movieId, apiKey)) }

    suspend fun getNowPlayingVideo(apiKey: String) = flow {
        emit(apiService.getNowPlayingVideo(apiKey))
    }


}