package com.example.testing

import com.example.domain.SearchUseCase
import com.example.domain.model.SearchResultImage
import com.example.domain.model.SearchResultVideo
import com.example.domain.model.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestSearchUseCase : SearchUseCase {

    private var exceptionOnImage: Exception? = null
    private var exceptionOnVideo: Exception? = null

    lateinit var searchImageMockResult: Result<SearchResultImage>
    lateinit var searchVideoMockResult: Result<SearchResultVideo>
    fun setImageMockResult(mock: Result<SearchResultImage>) {
        searchImageMockResult = mock
    }

    fun setImageVideoResult(mock: Result<SearchResultVideo>) {
        searchVideoMockResult = mock
    }

    fun simulateExceptionOnImage(exception: Exception) {
        exceptionOnImage = exception
    }

    fun simulateExceptionOnVideo(exception: Exception) {
        exceptionOnVideo = exception
    }

    override suspend fun searchImage(
        query: String,
        page: Int,
        size: Int
    ): Flow<Result<SearchResultImage>> = flow {
        emit(searchImageMockResult)
        exceptionOnImage?.let { throw it }
    }

    override suspend fun searchVideo(
        query: String,
        page: Int,
        size: Int
    ): Flow<Result<SearchResultVideo>> = flow {
        emit(searchVideoMockResult)
        exceptionOnVideo?.let { throw it }
    }
}