package com.example.domain

import android.os.Parcel
import android.os.Parcelable
import com.example.data.repository.SearchRepository
import com.example.network.model.ImageResult
import com.example.network.model.VideoResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton
import javax.inject.Inject


@Singleton
class SearchUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) : SearchUseCase {

    override fun searchImage(query: String, count: Int): Flow<ImageResult> =
        searchRepository.searchImage(query = query, size = count)

    override fun searchVideo(query: String, count: Int): Flow<VideoResult> =
        searchRepository.searchVideo(query = query, size = count)
}