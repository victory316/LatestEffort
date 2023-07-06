package com.example.kakaobankhomework.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.BookmarkUseCase
import com.example.domain.SearchUseCase
import com.example.kakaobankhomework.model.SearchItem
import com.example.kakaobankhomework.ui.model.UiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkUseCase: BookmarkUseCase
) : ViewModel() {

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
