package com.example.data.repository

import com.example.network.model.ImageResult
import com.example.network.model.VideoResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchImage(query: String, size: Int?): Flow<ImageResult>

    suspend fun searchVideo(query: String, size: Int?): Flow<VideoResult>
}
