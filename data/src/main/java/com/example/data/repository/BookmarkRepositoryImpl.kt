package com.example.data.repository

import android.content.SharedPreferences
import com.example.data.repository.di.bookmarkIds
import com.example.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : BookmarkRepository {

    override fun loadBookmarks(): List<Int> {
        val currentIds = sharedPreferences.getStringSet(bookmarkIds, setOf())

        return currentIds?.map { it.toInt() } ?: emptyList()
    }

    override fun addBookmark(id: Int) {
        val currentIds = sharedPreferences.getStringSet(bookmarkIds, setOf())

        currentIds?.toMutableSet()?.apply {
            add(id.toString())
        }.also {
            sharedPreferences.edit().apply {
                putStringSet(bookmarkIds, it)
                apply()
            }
        }
    }

    override fun removeBookmark(id: Int) {
        val currentIds = sharedPreferences.getStringSet(bookmarkIds, setOf())

        currentIds?.toMutableSet()?.apply {
            remove(id.toString())
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
