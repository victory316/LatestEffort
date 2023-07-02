package com.example.data.repository

import com.example.network.Dispatcher
import com.example.network.NetworkDataSource
import com.example.network.Dispatchers
import com.example.network.model.ImageResult
import com.example.network.model.VideoResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : SearchRepository {
    override suspend fun searchImage(query: String, size: Int?): Flow<ImageResult> = flow {
        emit(networkDataSource.getImages(query = query, pages = size))
    }.flowOn(ioDispatcher)

    override suspend fun searchVideo(query: String, size: Int?): Flow<VideoResult> = flow<VideoResult> {
        emit(networkDataSource.getVideos(query = query, pages = size))
    }.flowOn(ioDispatcher)
}
