package com.example.kakaobankhomework.ui.search

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

    private val searchImageState = MutableStateFlow<Result<SearchResultImage>>(Result.Loading)
    private val searchVideoState = MutableStateFlow<Result<SearchResultVideo>>(Result.Loading)

    val searchResultFlow: StateFlow<SearchUiState> =
        combine(searchImageState, searchVideoState) { images, videos ->
            if (images is Result.Success && videos is Result.Success) {
                val imageResult = images.data.result.map {
                    SearchItem.SearchResult(
                        id = 0,
                        thumbnailUrl = it.thumbnailUrl,
                        type = SearchItem.SearchResult.Type.IMAGE,
                        isBookmarked = it.bookmarked,
                    )
                }
                val videoResult = videos.data.result.map {
                    SearchItem.SearchResult(
                        id = 0,
                        thumbnailUrl = it.thumbnailUrl,
                        type = SearchItem.SearchResult.Type.VIDEO,
                        isBookmarked = it.bookmarked,
                    )
                }
                SearchUiState(
                    imageCurrentPage = images.data.currentPage,
                    videoCurrentPage = videos.data.currentPage,
                    searchResults = imageResult + videoResult,
                    imagePageable = false,
                    videoPageable = false,
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

    fun searchImage(page: Int = 10) = viewModelScope.launch {
        searchQuery?.let { query ->
            searchUseCase.searchImage(query = query, count = page).collect { result ->
                queryText.value = ""
                searchImageState.value = result
            }
        }
    }

    fun searchVideo(page: Int = 10) = viewModelScope.launch {
        searchQuery?.let { query ->
            searchUseCase.searchVideo(query = query, count = page).collect { result ->
                searchVideoState.value = result
            }
        }
    }

    fun onBookmarkClick(item: SearchItem.SearchResult) {
        if (item.isBookmarked) {
            bookmarkUseCase.removeBookmark(item.thumbnailUrl)
        } else {
            bookmarkUseCase.addBookmark(item.thumbnailUrl)
        }

        updateItems(item, !item.isBookmarked)
    }

    fun updateItems(item: SearchItem.SearchResult, bookmarked: Boolean) {
    }
}
