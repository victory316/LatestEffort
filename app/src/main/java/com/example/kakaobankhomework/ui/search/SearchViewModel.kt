package com.example.kakaobankhomework.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.SearchUseCase
import com.example.domain.model.SearchResultImage
import com.example.domain.model.SearchResultVideo
import com.example.kakaobankhomework.ui.model.UiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.domain.model.result.Result
import com.example.kakaobankhomework.model.SearchResult

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val searchImageState = MutableStateFlow<Result<SearchResultImage>>(Result.Loading)
    private val searchVideoState = MutableStateFlow<Result<SearchResultVideo>>(Result.Loading)

    val searchResultFlow: StateFlow<SearchUiState> =
        combine(searchImageState, searchVideoState) { images, videos ->
            if (images is Result.Success && videos is Result.Success) {
                val imageResult = images.data.result.map {
                    SearchResult(
                        id = 0,
                        thumbnailUrl = it.thumbnailUrl,
                        type = SearchResult.Type.IMAGE
                    )
                }
                val videoResult = videos.data.result.map {
                    SearchResult(
                        id = 0,
                        thumbnailUrl = it.thumbnailUrl,
                        type = SearchResult.Type.VIDEO
                    )
                }
                SearchUiState(
                    imageCurrentPage = images.data.currentPage,
                    videoCurrentPage = videos.data.currentPage,
                    searchResults = imageResult + videoResult,
                    imagePageable = false,
                    videoPageable = false
                )
            } else {
                SearchUiState(searchResults = emptyList())
            }
        }.stateIn(
            scope = viewModelScope,
            initialValue = SearchUiState(),
            started = SharingStarted.WhileSubscribed(5_000)
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
}
