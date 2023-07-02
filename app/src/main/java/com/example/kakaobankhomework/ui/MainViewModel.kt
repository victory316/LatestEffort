package com.example.kakaobankhomework.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.Result
import com.example.core.ServiceError
import com.example.core.asResult
import com.example.domain.BookmarkUseCase
import com.example.domain.SearchUseCase
import com.example.kakaobankhomework.ui.model.UiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val bookmarkUseCase: BookmarkUseCase,
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val searchImageState = MutableStateFlow<UiResult>(UiResult.Loading)
    private val searchVideoState = MutableStateFlow<UiResult>(UiResult.Loading)
    private val bookmarkState = MutableStateFlow<UiResult>(UiResult.Loading)
    val state = bookmarkUseCase.loadBookmarks()

    fun searchImage(query: String, page: Int) = viewModelScope.launch {
        searchUseCase.searchImage(query = query, count = page).asResult().map { result ->
            when (result) {
                is Result.Success -> UiResult.Success
                is Result.Error -> UiResult.Failure
                Result.Loading -> UiResult.Loading
            }
        }
    }

    fun searchVideo(query: String, page: Int) = viewModelScope.launch {
        searchUseCase.searchImage(query = query, count = page)
    }

    fun addBookmark(id: String) {
        bookmarkUseCase.addBookmark()
    }

    fun removeBookmark(id: String) {
        bookmarkUseCase.removeBookmark()
    }

    fun clearBookmark() {
        bookmarkUseCase.clearBookmark()
    }
}
