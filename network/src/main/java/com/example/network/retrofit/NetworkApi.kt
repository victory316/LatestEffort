package com.example.network.retrofit

import androidx.annotation.Size
import com.example.network.NetworkDataSource
import com.example.network.model.ImageResult
import com.example.network.model.VideoResult
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

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

private const val networkBaseUrl = BuildConfig.BACKEND_URL

@Singleton
class Network @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : NetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(networkBaseUrl)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(NetworkApi::class.java)

    override fun getImages(query: String, pages: Int) =
        networkApi.getImages(query = query, pages = pages)

    override fun getVideos(query: String, pages: Int) =
        networkApi.getVideos(query = query, pages = pages)
}
