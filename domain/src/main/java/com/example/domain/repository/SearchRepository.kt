package com.example.domain.repository

import com.example.domain.model.SearchResultImage
import com.example.domain.model.SearchResultVideo
import com.example.domain.model.result.Result
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchImage(query: String, page: Int?, size: Int?): Flow<Result<SearchResultImage>>

    suspend fun searchVideo(query: String, page: Int?, size: Int?): Flow<Result<SearchResultVideo>>
}
