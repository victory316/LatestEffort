package com.example.kakaobankhomework.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.BookmarkUseCase
import com.example.domain.SearchUseCase
import com.example.domain.model.SearchResultImage
import com.example.domain.model.SearchResultVideo
import com.example.domain.model.result.Result
import com.example.kakaobankhomework.model.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bookmarkUseCase: BookmarkUseCase,
    private val searchUseCase: SearchUseCase,
) : ViewModel() {

    private var imagePaging = false
    private var videoPaging = false

    private var _lastImagePage = 0
    private val lastImagePage
        get() = _lastImagePage

    private var _lastVideoPage = 0
    private val lastVideoPage
        get() = _lastVideoPage

    private val searchImageState = MutableStateFlow<Result<SearchResultImage>>(Result.Loading)
    private val searchVideoState = MutableStateFlow<Result<SearchResultVideo>>(Result.Loading)

    val searchResultFlow: StateFlow<SearchUiState> =
        combine(searchImageState, searchVideoState) { images, videos ->
            if (images is Result.Success && videos is Result.Success) {
                val imageResult = images.data.result.map {
                    if (lastImagePage != it.page) {
                        _lastImagePage = it.page
                        SearchItem.SearchPage(it.page)
                    } else {
                        SearchItem.SearchResult(
                            id = 0,
                            thumbnailUrl = it.thumbnailUrl,
                            type = SearchItem.SearchResult.Type.IMAGE,
                            dateTime = it.dateTime,
                            isBookmarked = it.bookmarked,
                        )
                    }
                }

                val videoResult = videos.data.result.map {
                    if (lastVideoPage != it.page) {
                        _lastVideoPage = it.page
                        SearchItem.SearchPage(it.page)
                    } else {
                        SearchItem.SearchResult(
                            id = 0,
                            thumbnailUrl = it.thumbnailUrl,
                            type = SearchItem.SearchResult.Type.VIDEO,
                            dateTime = it.dateTime,
                            isBookmarked = it.bookmarked,
                        )
                    }
                }

                SearchUiState(
                    imageCurrentPage = images.data.currentPage,
                    videoCurrentPage = videos.data.currentPage,
                    searchResults = imageResult + videoResult,
                    imagePageable = images.data.isPageable,
                    videoPageable = videos.data.isPageable,
                )
            } else {
                SearchUiState(searchResults = emptyList())
            }
        }.stateIn(
            scope = viewModelScope,
            initialValue = SearchUiState(),
            started = SharingStarted.WhileSubscribed(5_000),
        )

    val queryText = MutableLiveData<String?>(null)
    private val searchQuery
        get() = queryText.value

    fun searchImage(page: Int = 1, size: Int = 10) = viewModelScope.launch {
        searchQuery?.let { query ->
            searchUseCase.searchImage(query = query, page = page, size = size).collect { result ->
                searchImageState.value = result
            }
        }
    }

    fun searchVideo(page: Int = 1, size: Int = 10) = viewModelScope.launch {
        searchQuery?.let { query ->
            searchUseCase.searchVideo(query = query, page = page, size = size).collect { result ->
                searchVideoState.value = result
            }
        }
    }

    fun searchImageMore(position: Int, size: Int = 10) = viewModelScope.launch {
        if (position + 2 >= searchResultFlow.value.searchResults.size && !imagePaging &&
            searchResultFlow.value.searchResults.size != 0
        ) {
            val pageToQuery = searchResultFlow.value.imageCurrentPage + 1
            imagePaging = true

            searchQuery?.let { query ->
                searchUseCase.searchImage(query = query, page = pageToQuery, size = size)
                    .collect { result ->
                        searchImageState.update { currentState ->
                            (currentState as? Result.Success)?.let { asSuccess ->
                                val pagedItems =
                                    (result as? Result.Success)?.data?.result ?: emptyList()

                                val mergedItems =
                                    asSuccess.data.result + pagedItems
                                val updatedPage =
                                    (result as? Result.Success)?.data?.currentPage ?: 0

                                Result.Success(
                                    asSuccess.data.copy(
                                        result = mergedItems,
                                        currentPage = updatedPage
                                    )
                                )
                            } ?: currentState
                        }

                        imagePaging = false
                    }
            }
        }
    }

    fun searchVideoMore(position: Int, size: Int = 10) = viewModelScope.launch {
        if (position + 2 >= searchResultFlow.value.searchResults.size && !videoPaging &&
            searchResultFlow.value.searchResults.size != 0
        ) {
            val pageToQuery = searchResultFlow.value.videoCurrentPage + 1
            videoPaging = true

            searchQuery?.let { query ->
                searchUseCase.searchVideo(query = query, page = pageToQuery, size = size)
                    .collect { result ->
                        searchVideoState.update { currentState ->
                            (currentState as? Result.Success)?.let { asSuccess ->
                                val pagedItems =
                                    (result as? Result.Success)?.data?.result ?: emptyList()
                                val mergedItems = asSuccess.data.result + pagedItems
                                val updatedPage =
                                    (result as? Result.Success)?.data?.currentPage ?: 0

                                Result.Success(
                                    asSuccess.data.copy(
                                        result = mergedItems,
                                        currentPage = updatedPage
                                    )
                                )
                            } ?: currentState
                        }

                        videoPaging = false
                    }
            }
        }
    }

    fun onBookmarkClick(item: SearchItem.SearchResult) {
        if (item.isBookmarked) {
            bookmarkUseCase.removeBookmark(item.thumbnailUrl)
        } else {
            bookmarkUseCase.addBookmark(item.thumbnailUrl)
        }
    }
}
