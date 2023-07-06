package com.example.domain

import com.example.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkUseCaseImpl @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : BookmarkUseCase {

    override fun loadBookmarks(): List<String> {
        return bookmarkRepository.loadBookmarks()
    }

    override fun addBookmark(id: String) {
        bookmarkRepository.addBookmark(id)
    }

    override fun clearBookmark() {
        bookmarkRepository.clearAllBookmark()
    }

    override fun removeBookmark(id: String) {
        bookmarkRepository.removeBookmark(id)
    }
}
