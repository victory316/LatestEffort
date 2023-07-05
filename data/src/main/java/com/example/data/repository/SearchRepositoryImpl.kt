package com.example.data.repository

import com.example.data.repository.ext.mapResult
import com.example.domain.model.Image
import com.example.domain.model.SearchResultImage
import com.example.domain.model.SearchResultVideo
import com.example.domain.model.Video
import com.example.domain.model.result.Result
import com.example.domain.repository.SearchRepository
import com.example.network.Dispatcher
import com.example.network.Dispatchers
import com.example.network.NetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : SearchRepository {
    override suspend fun searchImage(query: String, size: Int?): Flow<Result<SearchResultImage>> =
        flow {
            val result = networkDataSource.getImages(query = query, pages = size).mapResult {
                SearchResultImage(
                    result = it.documents?.map {
                        Image(
                            thumbnailUrl = it.imageUrl ?: "",
                            bookmarked = false
                        )
                    } ?: emptyList(),
                    currentPage = it.meta.pageableCount,
                    isPageable = !it.meta.isEnd
                )
            }

            emit(result)
        }.flowOn(ioDispatcher)

    override suspend fun searchVideo(query: String, size: Int?): Flow<Result<SearchResultVideo>> =
        flow {
            val result = networkDataSource.getVideos(query = query, pages = size).mapResult {
                SearchResultVideo(
                    result = it.documents?.map {
                        Video(
                            thumbnailUrl = it.url,
                            bookmarked = false
                        )
                    } ?: emptyList(),
                    currentPage = it.meta.pageableCount,
                    isPageable = !it.meta.isEnd
                )
            }

            emit(result)
        }.flowOn(ioDispatcher)
}
