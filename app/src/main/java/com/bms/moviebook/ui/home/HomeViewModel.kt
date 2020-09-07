package com.bms.moviebook.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.kalaa.data.repository.DataRepository
import com.bms.moviebook.model.popular.MovieResponse
import com.bms.moviebook.util.Global
import com.bms.moviebook.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel @ViewModelInject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    val videoResponse =
        MutableLiveData<Resource<MutableList<MutableList<MovieResponse.Result>>>>()

    init {
        getPopularVideoAndTopRatedVideo()
    }

    private fun getPopularVideoAndTopRatedVideo() {

        videoResponse.postValue(Resource.loading(null))

        /*its runs on the main thread*/
        viewModelScope.launch {
            dataRepository.getPopularVideo(Global.API_KEY, 1)
                .zip(dataRepository.getTopRatedVideo(Global.API_KEY, 1))
                { poplarVideo, topRatedVideo ->
                    //val videosList = ArrayList<PopularVideoResponse.Result>()
                    val videosList = mutableListOf<MutableList<MovieResponse.Result>>()
                    videosList.add(poplarVideo.results.toMutableList())
                    videosList.add(topRatedVideo.results.toMutableList())
                    return@zip videosList
                }
                //here i am running on IO thread for IO operation
                // operator below flowOn Runs on Mainthread
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    if (e is IOException) {
                        videoResponse.postValue(
                            Resource.error(
                                "Check Internet Connection !",
                                null
                            )
                        )
                    } else if (e is HttpException) {
                        videoResponse.postValue(Resource.error(e.toString(), null))
                    }
                }
                .collect { videoList ->
                    videoResponse.postValue(Resource.success(videoList))
                }

        }
    }

}