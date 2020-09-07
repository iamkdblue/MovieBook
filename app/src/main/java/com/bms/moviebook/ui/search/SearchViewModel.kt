package com.bms.moviebook.ui.search

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
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SearchViewModel  @ViewModelInject constructor(
    private val dataRepository: DataRepository
) : ViewModel()  {

    val videoResponse =
        MutableLiveData<Resource<MutableList<MovieResponse.Result>>>()

    var searchString = MutableLiveData<String>()

    init {
        getNowPlayingVideo()
    }

    private fun getNowPlayingVideo() {
        videoResponse.postValue(Resource.loading(null))

        viewModelScope.launch {
            dataRepository.getNowPlayingVideo(Global.API_KEY)
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
                    videoResponse.postValue(Resource.success(videoList.results))
                }
        }
    }

    fun afterTextChanged(charSequence: CharSequence) {
        searchString.value = charSequence.toString()
    }

}