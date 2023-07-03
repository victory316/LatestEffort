package com.example.kakaobankhomework.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.BookmarkUseCase
import com.example.domain.SearchUseCase
import com.example.kakaobankhomework.ui.model.UiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkUseCase: BookmarkUseCase,
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val searchImageState = MutableStateFlow<UiResult>(UiResult.Loading)
    private val searchVideoState = MutableStateFlow<UiResult>(UiResult.Loading)

    fun searchImage(query: String, page: Int) = viewModelScope.launch {
        searchUseCase.searchImage(query = query, count = page)
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
