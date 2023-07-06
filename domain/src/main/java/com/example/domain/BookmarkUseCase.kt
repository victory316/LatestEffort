package com.example.domain

interface BookmarkUseCase {

    fun loadBookmarks(): List<Int>

    fun addBookmark(id: Int)

    fun removeBookmark(id: Int)

    fun clearBookmark()
}