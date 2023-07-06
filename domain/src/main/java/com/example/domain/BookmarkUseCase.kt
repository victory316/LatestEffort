package com.example.domain

interface BookmarkUseCase {

    fun loadBookmarks(): List<String>

    fun addBookmark(id: String)

    fun removeBookmark(id: String)

    fun clearBookmark()
}