package com.example.testing

import com.example.domain.SearchUseCase
import com.example.domain.model.SearchResultImage
import com.example.domain.model.SearchResultVideo
import com.example.domain.model.result.Result
import kotlinx.coroutines.flow.Flow

class TestSearchUseCase: SearchUseCase {
    override suspend fun searchImage(
        query: String,
        page: Int,
        size: Int
    ): Flow<Result<SearchResultImage>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchVideo(
        query: String,
        page: Int,
        size: Int
    ): Flow<Result<SearchResultVideo>> {
        TODO("Not yet implemented")
    }
}