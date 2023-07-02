package com.example.domain

import com.example.network.model.ImageResult
import com.example.network.model.VideoResult
import kotlinx.coroutines.flow.Flow

interface SearchUseCase {

    fun searchImage(query: String, count: Int): Flow<ImageResult>

    fun searchVideo(query: String, count: Int): Flow<VideoResult>
}