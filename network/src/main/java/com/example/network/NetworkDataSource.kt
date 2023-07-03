package com.example.network

import com.example.network.model.ImageResultDto
import com.example.network.model.VideoResult

interface NetworkDataSource {

    suspend fun getImages(query: String, pages: Int?): ImageResultDto

    suspend fun getVideos(query: String, pages: Int?): VideoResult
}