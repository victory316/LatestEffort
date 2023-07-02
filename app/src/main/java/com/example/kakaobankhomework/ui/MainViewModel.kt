package com.example.kakaobankhomework.ui

import androidx.lifecycle.ViewModel
import com.example.domain.BookmarkUseCase
import com.example.domain.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val bookmarkUseCase: BookmarkUseCase,
    val searchUseCase: SearchUseCase
) : ViewModel() {

    fun searchImage(query: String, page: Int) {
        searchUseCase.searchImage(query = query, count = page)
    }

    fun searchVideo(query: String, page: Int) {
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
