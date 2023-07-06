package com.example.kakaobankhomework.ui.bookmark

import androidx.lifecycle.ViewModel
import com.example.domain.BookmarkUseCase
import com.example.kakaobankhomework.model.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkUseCase: BookmarkUseCase,
) : ViewModel() {

    fun getBookmarks() {
        bookmarkUseCase.loadBookmarks()
    }

    fun onBookmarkClick(item: SearchItem.SearchResult) {
        if (item.isBookmarked) {
            bookmarkUseCase.removeBookmark(item.thumbnailUrl.hashCode())
        } else {
            bookmarkUseCase.addBookmark(item.thumbnailUrl.hashCode())
        }
    }

    fun clearBookmark() {
        bookmarkUseCase.clearBookmark()
    }
}
