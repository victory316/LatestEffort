package com.example.data.repository

import android.content.SharedPreferences
import com.example.data.repository.di.bookmarkIds
import com.example.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : BookmarkRepository {

    override fun loadBookmarks(): List<String> {
        val currentIds = sharedPreferences.getStringSet(bookmarkIds, setOf())

        return currentIds?.toList() ?: emptyList()
    }

    override fun addBookmark(id: String) {
        val currentIds = sharedPreferences.getStringSet(bookmarkIds, setOf())

        currentIds?.toMutableSet()?.apply {
            add(id)
        }.also {
            sharedPreferences.edit().apply {
                putStringSet(bookmarkIds, it)
                apply()
            }
        }
    }

    override fun removeBookmark(id: String) {
        val currentIds = sharedPreferences.getStringSet(bookmarkIds, setOf())

        currentIds?.toMutableSet()?.apply {
            remove(id)
        }.also {
            sharedPreferences.edit().apply {
                putStringSet(bookmarkIds, it)
                apply()
            }
        }
    }

    override fun clearAllBookmark() {
        sharedPreferences.edit().clear()
    }
}
