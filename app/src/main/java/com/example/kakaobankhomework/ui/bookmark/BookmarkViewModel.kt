package com.example.kakaobankhomework.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.BookmarkUseCase
import com.example.kakaobankhomework.model.ItemBookmarked
import com.example.kakaobankhomework.model.SearchItem
import com.example.kakaobankhomework.ui.search.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkUseCase: BookmarkUseCase,
) : ViewModel() {

    private val _bookmarks = MutableStateFlow<List<String>>(emptyList())
    val bookmakrs: StateFlow<List<ItemBookmarked>?> = _bookmarks.map { list ->
        list.map {
            ItemBookmarked(it)
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(5_000),
    )

    fun loadBookmarks() {
        _bookmarks.value = bookmarkUseCase.loadBookmarks()
    }

    fun onBookmarkClick(item: ItemBookmarked) {
        bookmarkUseCase.removeBookmark(item.thumbnailUrl)
        loadBookmarks()
    }
}
