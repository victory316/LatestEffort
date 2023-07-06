package com.example.domain.repository

interface BookmarkRepository {

    fun addBookmark(id: Int)

    fun removeBookmark(id: Int)

    fun clearAllBookmark()
}
