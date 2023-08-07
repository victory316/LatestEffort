package com.example.data.repository

import android.content.SharedPreferences
import com.example.data.di.bookmarkIds
import com.example.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : BookmarkRepository {

    override fun loadBookmarks(): List<String> {
        val currentIds = sharedPreferences.getStringSet(bookmarkIds, setOf())

        val sorted = currentIds?.map {
            it.split("][")
        }?.let {
            it.sortedBy { it[0] }
        }?.map {
            it[1]
        }?.toList()

        return sorted ?: emptyList()
    }

    override fun addBookmark(id: String) {
        val currentIds = sharedPreferences.getStringSet(bookmarkIds, setOf())

        currentIds?.toMutableSet()?.apply {
            add("${this.size}][$id")
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
            removeIf { id in it }
        }.also {
            sharedPreferences.edit().apply {
                putStringSet(bookmarkIds, it)
                apply()
            }
        }
    }

    override fun clearAllBookmark() {
        sharedPreferences.edit().clear().apply()
    }
}
