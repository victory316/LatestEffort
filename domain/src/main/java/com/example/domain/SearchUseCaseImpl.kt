package com.example.domain

import com.example.domain.model.SearchResultImage
import com.example.domain.model.SearchResultVideo
import com.example.domain.model.result.Result
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository,
) : SearchUseCase {

    override suspend fun searchImage(
        query: String,
        page: Int,
        size: Int
    ): Flow<Result<SearchResultImage>> =
        searchRepository.searchImage(query = query, page = page, size = size)

    override suspend fun searchVideo(
        query: String,
        page: Int,
        size: Int
    ): Flow<Result<SearchResultVideo>> =
        searchRepository.searchVideo(query = query, page = page, size = size)
}
