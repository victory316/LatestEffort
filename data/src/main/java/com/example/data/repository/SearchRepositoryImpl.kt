package com.example.data.repository

import com.example.domain.model.Image
import com.example.domain.model.Video
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
    override suspend fun searchImage(query: String, size: Int?): Flow<List<Image>> = flow {
        emit(
            networkDataSource.getImages(query = query, pages = size).body()?.images!!.map {
                Image(
                    thumbnailUrl = it.thumbnailUrl,
                    bookmarked = false
                )
            }
        )
    }.flowOn(ioDispatcher)

    override suspend fun searchVideo(query: String, size: Int?): Flow<List<Video>> = flow {
        emit(
            networkDataSource.getVideos(query = query, pages = size).body()?.videos!!.map {
                Video(
                    thumbnailUrl = it.url,
                    bookmarked = false
                )
            }
        )
    }.flowOn(ioDispatcher)
}
