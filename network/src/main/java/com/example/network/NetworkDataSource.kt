package com.example.network

import com.example.network.model.ImageResultDto
import com.example.network.model.VideoResultDto
import retrofit2.Response

interface NetworkDataSource {

    suspend fun getImages(query: String, sort: String?, page: Int?, size:Int?): Response<ImageResultDto>

    suspend fun getVideos(query: String, sort: String?, page: Int?, size:Int?): Response<VideoResultDto>
}