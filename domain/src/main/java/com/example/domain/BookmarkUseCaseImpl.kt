package com.example.domain

import com.example.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkUseCaseImpl @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : BookmarkUseCase {

    override fun loadBookmarks(): List<Int> {
        return bookmarkRepository.loadBookmarks()
    }

    override fun addBookmark(id: Int) {
        bookmarkRepository.addBookmark(id)
    }

    override fun clearBookmark() {
        bookmarkRepository.clearAllBookmark()
    }

    override fun removeBookmark(id: Int) {
        bookmarkRepository.removeBookmark(id)
    }
}
