package com.pixabay.challenge.data.datastore.remote

import retrofit2.http.GET
import retrofit2.http.Query
import com.pixabay.challenge.data.BuildConfig
import com.pixabay.challenge.data.model.ImagesResponse
import java.util.Locale

interface ImageApiService {
    @GET("api/")
    suspend fun fetchImages(
        @Query("key") apiKey: String = BuildConfig.API_KEY,
        @Query("q") query: String,
        @Query("lang") language: String = Locale.getDefault().language
    ): ImagesResponse
}
