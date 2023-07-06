package com.example.domain.repository

interface BookmarkRepository {

    fun loadBookmarks(): List<Int>
    fun addBookmark(id: Int)

    fun removeBookmark(id: Int)

    fun clearAllBookmark()
}
