package com.example.domain.repository

import com.example.domain.model.Image
import com.example.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchImage(query: String, size: Int?): Flow<List<Image>>

    suspend fun searchVideo(query: String, size: Int?): Flow<List<Video>>
}
