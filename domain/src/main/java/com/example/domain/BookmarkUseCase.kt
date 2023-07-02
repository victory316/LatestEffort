package com.example.domain

interface BookmarkUseCase {

    fun loadBookmarks()

    fun addBookmark()

    fun removeBookmark()

    fun clearBookmark()
}