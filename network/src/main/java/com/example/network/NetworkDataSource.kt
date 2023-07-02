package com.example.network

import com.example.network.model.ImageResult
import com.example.network.model.VideoResult

interface NetworkDataSource {

    suspend fun getImages(query: String, pages: Int?): ImageResult

    suspend fun getVideos(query: String, pages: Int?): VideoResult
}