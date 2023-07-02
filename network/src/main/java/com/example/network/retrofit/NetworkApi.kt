package com.example.network.retrofit

import androidx.annotation.Size
import com.example.network.model.ImageResult
import com.example.network.model.VideoResult
import retrofit2.http.GET
import retrofit2.http.Query

private interface NetworkApi {

    @GET(value = "v2/search/image")
    suspend fun getImages(
        @Query("query") query: String,
        @Query("size") size: Int?
    ): ImageResult

    @GET(value = "v2/search/vclip")
    suspend fun getVideos(
        @Query("query") query: String,
        @Query("size") size: Int?
    ): VideoResult
}
