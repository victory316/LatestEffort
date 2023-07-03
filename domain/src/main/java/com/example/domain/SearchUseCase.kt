package com.example.domain

import com.example.domain.model.Image
import com.example.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface SearchUseCase {

    suspend fun searchImage(query: String, count: Int): Flow<List<Image>>

    suspend fun searchVideo(query: String, count: Int): Flow<List<Video>>
}