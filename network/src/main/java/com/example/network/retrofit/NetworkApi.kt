package com.example.network.retrofit

import com.example.network.NetworkDataSource
import com.example.network.model.ImageResultDto
import com.example.network.model.VideoResultDto
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface NetworkApi {

    @Headers("Authorization: KakaoAK $networkHeader")
    @GET(value = "v2/search/image")
    suspend fun getImages(
        @Query("query") query: String,
        @Query("sort") sort: String? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): Response<ImageResultDto>

    @Headers("Authorization: KakaoAK $networkHeader")
    @GET(value = "v2/search/vclip")
    suspend fun getVideos(
        @Query("query") query: String,
        @Query("sort") sort: String? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): Response<VideoResultDto>
}

private const val networkHeader = "8883f58d6338fb1f3ed1c038a12c1ca3"
private const val networkBaseUrl = "https://dapi.kakao.com"

enum class SortBy(val stringKey: String) {
    ACCURACY("accuracy"),
    RECENCY("recency")
}

@Singleton
class Network @Inject constructor(
    okhttpCallFactory: Call.Factory,
) : NetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(networkBaseUrl)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setFieldNamingPolicy(
                    FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES,
                ).create(),
            ),
        )
        .build()
        .create(NetworkApi::class.java)

    override suspend fun getImages(
        query: String,
        sort: String?,
        page: Int?,
        size: Int?
    ): Response<ImageResultDto> =
        networkApi.getImages(query = query, sort = sort, page = page, size = size)

    override suspend fun getVideos(
        query: String,
        sort: String?,
        page: Int?,
        size: Int?
    ): Response<VideoResultDto> =
        networkApi.getVideos(query = query, sort = sort, page = page, size = size)
}
