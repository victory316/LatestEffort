package com.example.latesteffort.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.BookmarkUseCase
import com.example.latesteffort.util.RxBus
import com.example.latesteffort.model.ItemBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    val bookmarks: StateFlow<List<ItemBookmark>?> = _bookmarks.map { list ->
        list.map {
            ItemBookmark(it)
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(5_000),
    )

    val noBookmarks = bookmarks.map {
        it?.isEmpty() == true
    }.asLiveData(Dispatchers.Main)

    fun loadBookmarks() {
        _bookmarks.value = bookmarkUseCase.loadBookmarks()
    }

    fun onBookmarkClick(item: ItemBookmark) {
        bookmarkUseCase.removeBookmark(item.thumbnailUrl)
        RxBus.onBookmarkRemoved.onNext(item.thumbnailUrl)
        loadBookmarks()
    }
}
