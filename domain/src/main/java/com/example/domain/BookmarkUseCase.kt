package com.example.domain

interface BookmarkUseCase {

    fun loadBookmarks()

    fun addBookmark(id: Int)

    fun removeBookmark(id: Int)

    fun clearBookmark()
}