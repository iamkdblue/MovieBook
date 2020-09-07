package com.bms.moviebook.di

import com.bms.moviebook.BuildConfig
import com.bms.moviebook.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object ApiModule {

    private val BASE_API_URL = "https://api.themoviedb.org/3/movie/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(): ApiService = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(provideOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

}