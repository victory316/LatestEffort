package com.example.kakaobankhomework.ui.bookmark

import androidx.lifecycle.ViewModel
import com.example.domain.BookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkUseCase: BookmarkUseCase
) : ViewModel() {

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
