package com.example.network.retrofit

import com.example.network.NetworkDataSource
import com.example.network.model.ImageResultDto
import com.example.network.model.VideoResultDto
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
    ): ImageResultDto

    @GET(value = "v2/search/vclip")
    suspend fun getVideos(
        @Query("query") query: String,
        @Query("size") size: Int?
    ): VideoResultDto
}

private const val networkBaseUrl = "https://dapi.kakao.com"

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

    override suspend fun getImages(query: String, pages: Int?): ImageResultDto =
        networkApi.getImages(query = query, size = pages)

    override suspend fun getVideos(query: String, pages: Int?): VideoResultDto =
        networkApi.getVideos(query = query, size = pages)
}
