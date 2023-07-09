package com.example.data.repository

import android.content.SharedPreferences
import com.example.data.repository.di.bookmarkIds
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
import com.example.network.retrofit.SortBy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val sharedPreferences: SharedPreferences,
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : SearchRepository {
    override suspend fun searchImage(
        query: String,
        page: Int?,
        size: Int?
    ): Flow<Result<SearchResultImage>> =
        flow {
            val currentIds = sharedPreferences.getStringSet(bookmarkIds, setOf())

            val result = networkDataSource.getImages(
                query = query,
                sort = SortBy.RECENCY.stringKey,
                page = page,
                size = size
            ).mapResult { dto ->
                SearchResultImage(
                    result = dto.documents?.map {
                        val isBookmarked =
                            currentIds?.contains(it.thumbnailUrl)

                        Image(
                            thumbnailUrl = it.thumbnailUrl,
                            bookmarked = isBookmarked ?: false,
                            page = page ?: 0 + 1,
                            dateTime = it.datetime
                        )
                    }?.sortedBy { it.dateTime } ?: emptyList(),
                    currentPage = page ?: 0 + 1,
                    isPageable = !dto.meta.isEnd,
                )
            }

            emit(result)
        }.flowOn(ioDispatcher)

    override suspend fun searchVideo(
        query: String,
        page: Int?,
        size: Int?
    ): Flow<Result<SearchResultVideo>> =
        flow {
            val currentIds = sharedPreferences.getStringSet(bookmarkIds, setOf())

            val result = networkDataSource.getVideos(
                query = query,
                sort = SortBy.RECENCY.stringKey,
                page = page,
                size = size
            ).mapResult { dto ->
                SearchResultVideo(
                    result = dto.documents?.map {
                        val isBookmarked =
                            currentIds?.contains(it.thumbnail)

                        Video(
                            thumbnailUrl = it.thumbnail,
                            bookmarked = isBookmarked ?: false,
                            page = page ?: 0 + 1,
                            dateTime = it.datetime
                        )
                    }?.sortedBy { it.dateTime } ?: emptyList(),
                    currentPage = page ?: 0 + 1,
                    isPageable = !dto.meta.isEnd,
                )
            }

            emit(result)
        }.flowOn(ioDispatcher)
}
