package com.example.domain

import com.example.network.model.ImageResult
import com.example.network.model.VideoResult
import kotlinx.coroutines.flow.Flow

interface SearchUseCase {

    suspend fun searchImage(query: String, count: Int): Flow<ImageResult>

    suspend fun searchVideo(query: String, count: Int): Flow<VideoResult>
}