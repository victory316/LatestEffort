package com.example.testing

import com.example.domain.BookmarkUseCase

class TestBookmarkUseCase : BookmarkUseCase {
    override fun loadBookmarks(): List<String> {
        return emptyList()
    }

    override fun addBookmark(id: String) {
    }

    override fun removeBookmark(id: String) {
    }

    override fun clearBookmark() {
    }
}