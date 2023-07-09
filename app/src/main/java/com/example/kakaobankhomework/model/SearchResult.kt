package com.example.kakaobankhomework.model

import androidx.annotation.LayoutRes
import com.example.kakaobankhomework.R
import com.example.kakaobankhomework.binding.SimpleItemDiffCallback
import java.util.Date

sealed class SearchItem(
    @LayoutRes val layoutResId: Int
) : SimpleItemDiffCallback.DiffCallback {

    data class SearchResult(
        val id: Int,
        val thumbnailUrl: String,
        val type: Type,
        val dateTime: Date,
        var isBookmarked: Boolean = false
    ) : SearchItem(R.layout.item_search_result) {
        enum class Type {
            IMAGE,
            VIDEO
        }
    }

    data class SearchPage(
        val searchPageIndex: Int
    ) : SearchItem(R.layout.item_search_page_header)
}


