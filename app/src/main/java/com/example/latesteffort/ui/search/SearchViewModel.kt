package com.example.latesteffort.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.BookmarkUseCase
import com.example.domain.SearchUseCase
import com.example.domain.model.SearchResultImage
import com.example.domain.model.SearchResultVideo
import com.example.domain.model.result.Result
import com.example.domain.model.result.ServiceError
import com.example.latesteffort.model.ItemSearch
import com.example.latesteffort.util.getSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
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

    private val _errorOccurred = MutableLiveData<Result.Failure>()
    val errorOccurred
        get() = _errorOccurred

    val searchResultFlow: StateFlow<SearchUiState> =
        combine(searchImageState, searchVideoState) { images, videos ->
            if (images is Result.Success && videos is Result.Success) {
                val imageResult = mutableListOf<ItemSearch>()

                images.data.result.forEach {
                    if (lastImagePage != it.page) {
                        imageResult.add(ItemSearch.SearchPage(it.page))
                    }

                    imageResult.add(
                        ItemSearch.SearchResult(
                            thumbnailUrl = it.thumbnailUrl,
                            dateTime = it.dateTime,
                            isBookmarked = it.bookmarked,
                        )
                    )

                    _lastImagePage = it.page
                }

                val videoResult = mutableListOf<ItemSearch>()

                videos.data.result.forEach {
                    if (lastVideoPage != it.page) {
                        videoResult.add(ItemSearch.SearchPage(it.page))
                    }

                    videoResult.add(
                        ItemSearch.SearchResult(
                            thumbnailUrl = it.thumbnailUrl,
                            dateTime = it.dateTime,
                            isBookmarked = it.bookmarked,
                        )
                    )

                    _lastVideoPage = it.page
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

    val noResults = searchResultFlow.map {
        it.searchResults.isEmpty()
    }.asLiveData(Dispatchers.Main)

    init {
        viewModelScope.launch {
            queryText.asFlow()
                .debounce(500L)
                .collect {
                    searchImage()
                    searchVideo()
                }
        }
    }

    private fun searchImage(page: Int = 1, size: Int = 10) = viewModelScope.launch {
        searchQuery?.let { query ->
            searchUseCase.searchImage(query = query, page = page, size = size).catch {
                _errorOccurred.value = Result.Failure(ServiceError.SearchFail)
            }.collect { result ->
                searchImageState.value = result
            }
        }
    }

    private fun searchVideo(page: Int = 1, size: Int = 10) = viewModelScope.launch {
        searchQuery?.let { query ->
            searchUseCase.searchVideo(query = query, page = page, size = size).catch {
                _errorOccurred.value = Result.Failure(ServiceError.SearchFail)
            }.collect { result ->
                searchVideoState.value = result
            }
        }
    }

    fun searchImageMore(position: Int, size: Int = 10) = viewModelScope.launch {
        if (imageIsPageable(position)) {
            val pageToQuery = searchResultFlow.value.imageCurrentPage + 1

            imagePaging = true

            searchQuery?.let { query ->
                searchUseCase.searchImage(query = query, page = pageToQuery, size = size)
                    .catch {
                        _errorOccurred.value = Result.Failure(ServiceError.SearchFail)
                    }
                    .collect { pagingResult ->
                        searchImageState.update { currentState ->
                            val pagedItems = pagingResult.getSuccess()?.data?.result
                                ?: emptyList()

                            val mergedItems = currentState.getSuccess()?.data?.result
                                ?.plus(pagedItems) ?: emptyList()

                            val updatedPage = pagingResult.getSuccess()?.data?.currentPage ?: 0

                            val updatedData = currentState.getSuccess()?.data?.copy(
                                result = mergedItems,
                                currentPage = updatedPage
                            ) ?: return@update Result.Failure(ServiceError.PagingFail)

                            Result.Success(updatedData)
                        }

                        imagePaging = false
                    }
            }
        }
    }

    fun searchVideoMore(position: Int, size: Int = 10) = viewModelScope.launch {
        if (videoIsPageable(position)) {
            val pageToQuery = searchResultFlow.value.videoCurrentPage + 1

            videoPaging = true

            searchQuery?.let { query ->
                searchUseCase.searchVideo(query = query, page = pageToQuery, size = size)
                    .catch {
                        _errorOccurred.value = Result.Failure(ServiceError.SearchFail)
                    }
                    .collect { pagingResult ->
                        searchVideoState.update { currentState ->
                            val pagedItems = pagingResult.getSuccess()?.data?.result
                                ?: emptyList()

                            val mergedItems = currentState.getSuccess()?.data?.result
                                ?.plus(pagedItems) ?: emptyList()

                            val updatedPage = pagingResult.getSuccess()?.data?.currentPage ?: 0

                            val updatedData = currentState.getSuccess()?.data?.copy(
                                result = mergedItems,
                                currentPage = updatedPage
                            ) ?: return@update Result.Failure(ServiceError.PagingFail)

                            Result.Success(updatedData)
                        }

                        videoPaging = false
                    }
            }
        }
    }

    private fun imageIsPageable(position: Int): Boolean {
        return position + 2 >= searchResultFlow.value.searchResults.size && !imagePaging
                && searchResultFlow.value.imagePageable
    }

    private fun videoIsPageable(position: Int): Boolean {
        return position + 2 >= searchResultFlow.value.searchResults.size && !videoPaging
                && searchResultFlow.value.videoPageable
    }

    fun onBookmarkClick(item: ItemSearch.SearchResult) {
        if (item.isBookmarked) {
            bookmarkUseCase.removeBookmark(item.thumbnailUrl)
        } else {
            bookmarkUseCase.addBookmark(item.thumbnailUrl)
        }

        updateImageBookmark(url = item.thumbnailUrl, bookmarked = !item.isBookmarked)
        updateVideoBookmark(url = item.thumbnailUrl, bookmarked = !item.isBookmarked)
    }

    fun updateVideoBookmark(url: String, bookmarked: Boolean) {
        searchVideoState.update { currentState ->
            currentState.getSuccess()?.let { asSuccess ->
                val currentData = asSuccess.data.result
                val index = currentData.indexOfFirst { it.thumbnailUrl == url }

                if (index != -1) {
                    val prevItem = currentData[index]

                    val updated = currentData.toMutableList().apply {
                        set(index, prevItem.copy(bookmarked = bookmarked))
                    }.toList()

                    Result.Success(asSuccess.data.copy(result = updated))
                } else {
                    currentState
                }
            } ?: currentState
        }
    }

    fun updateImageBookmark(url: String, bookmarked: Boolean) {
        searchImageState.update { currentState ->
            currentState.getSuccess()?.let { asSuccess ->
                val currentData = asSuccess.data.result
                val index = currentData.indexOfFirst { it.thumbnailUrl == url }

                if (index != -1) {
                    val prevItem = currentData[index]

                    val updated = currentData.toMutableList().apply {
                        set(index, prevItem.copy(bookmarked = bookmarked))
                    }.toList()

                    Result.Success(asSuccess.data.copy(result = updated))
                } else {
                    currentState
                }
            } ?: currentState
        }
    }
}
