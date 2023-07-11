package com.example.testing

import com.example.domain.BookmarkUseCase

class TestBookmarkUseCase : BookmarkUseCase {

    private var mockData: MutableList<String>? = null
    fun setMockData(data: List<String>) {
        mockData = data.toMutableList()
    }

    override fun loadBookmarks(): List<String> {
        return mockData ?: emptyList()
    }

    override fun addBookmark(id: String) {
        mockData?.add(id)
    }

    override fun removeBookmark(id: String) {
        mockData?.remove(id)
    }

    override fun clearBookmark() {
        mockData?.clear()
    }
}