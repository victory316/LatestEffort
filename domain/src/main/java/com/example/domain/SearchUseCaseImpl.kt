package com.example.domain

import com.example.domain.model.Image
import com.example.domain.model.Video
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton
import javax.inject.Inject


@Singleton
class SearchUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) : SearchUseCase {

    override suspend fun searchImage(query: String, count: Int): Flow<List<Image>> =
        searchRepository.searchImage(query = query, size = count)

    override suspend fun searchVideo(query: String, count: Int): Flow<List<Video>> =
        searchRepository.searchVideo(query = query, size = count)
}