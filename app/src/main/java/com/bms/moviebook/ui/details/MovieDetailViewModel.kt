package com.bms.moviebook.ui.details

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.kalaa.data.repository.DataRepository
import com.bms.moviebook.model.cast.CastResponse
import com.bms.moviebook.model.popular.MovieResponse
import com.bms.moviebook.util.Global
import com.bms.moviebook.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MovieDetailViewModel @ViewModelInject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    val castResponse = MutableLiveData<Resource<MutableList<CastResponse.Cast>>>()
    val similarMovieResponse = MutableLiveData<Resource<MutableList<MovieResponse.Result>>>()
    var isSimilarMovieEmpty = ObservableBoolean(false)

    fun getMovieCredit(movieId: Int) {
        castResponse.postValue(Resource.loading(null))
        viewModelScope.launch {
            dataRepository.getMovieCredit(Global.API_KEY, movieId)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    if (e is IOException) {
                        castResponse.postValue(
                            Resource.error(
                                "Check Internet Connection !",
                                null
                            )
                        )
                    } else if (e is HttpException) {
                        castResponse.postValue(Resource.error(e.toString(), null))
                    }
                }
                .collect { videoList ->
                    castResponse.postValue(Resource.success(videoList.cast))
                }
        }
    }

    fun getSimilarMovie(movieId: Int) {

        similarMovieResponse.postValue(Resource.loading(null))
        viewModelScope.launch {
            dataRepository.getSimilarMovie(Global.API_KEY, movieId)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    if (e is IOException) {
                        similarMovieResponse.postValue(
                            Resource.error(
                                "Check Internet Connection !",
                                null
                            )
                        )
                    } else if (e is HttpException) {
                        similarMovieResponse.postValue(Resource.error(e.toString(), null))
                    }
                }
                .collect { videoList ->
                    isSimilarMovieEmpty.set(videoList.results.isEmpty())
                    similarMovieResponse.postValue(Resource.success(videoList.results))
                }
        }
    }

}