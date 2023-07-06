package com.example.data.repository

import android.content.SharedPreferences
import com.example.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : BookmarkRepository {
    private val bookmarkIds = "bookmark_ids"

    override fun addBookmark(id: Int) {
        val currentIds = sharedPreferences.getStringSet(bookmarkIds, setOf())

        currentIds?.apply {
            add(id.toString())
        }.also {
            sharedPreferences.edit().putStringSet(bookmarkIds, it)
        }
    }

    override fun removeBookmark(id: Int) {
        val currentIds = sharedPreferences.getStringSet(bookmarkIds, setOf())

        currentIds?.apply {
            remove(id.toString())
        }.also {
            sharedPreferences.edit().putStringSet(bookmarkIds, it)
        }
    }

    override fun clearAllBookmark() {
        sharedPreferences.edit().clear()
    }
}