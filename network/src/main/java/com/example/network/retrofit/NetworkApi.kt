package com.example.network.retrofit

import com.example.network.NetworkDataSource
import com.example.network.model.ImageResultDto
import com.example.network.model.VideoResultDto
import com.google.gson.internal.GsonBuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface NetworkApi {

    @Headers("Authorization: KakaoAK $networkHeader")
    @GET(value = "v2/search/image")
    suspend fun getImages(
        @Query("query") query: String,
        @Query("size") size: Int?
    ): Response<ImageResultDto>

    @Headers("Authorization: KakaoAK $networkHeader")
    @GET(value = "v2/search/vclip")
    suspend fun getVideos(
        @Query("query") query: String,
        @Query("size") size: Int?
    ): Response<VideoResultDto>
}

private const val networkHeader = "8883f58d6338fb1f3ed1c038a12c1ca3"
private const val networkBaseUrl = "https://dapi.kakao.com"

@Singleton
class Network @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : NetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(networkBaseUrl)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NetworkApi::class.java)

    override suspend fun getImages(query: String, pages: Int?): Response<ImageResultDto> =
        networkApi.getImages(query = query, size = pages)

    override suspend fun getVideos(query: String, pages: Int?): Response<VideoResultDto> =
        networkApi.getVideos(query = query, size = pages)
}
