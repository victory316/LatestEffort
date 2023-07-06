package com.example.domain.repository

interface BookmarkRepository {

    fun loadBookmarks(): List<String>
    fun addBookmark(id: String)

    fun removeBookmark(id: String)

    fun clearAllBookmark()
}
