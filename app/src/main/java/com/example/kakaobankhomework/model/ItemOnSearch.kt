package com.example.kakaobankhomework.model

import androidx.annotation.LayoutRes
import com.example.kakaobankhomework.R
import java.util.Date

sealed class ItemOnSearch(
    @LayoutRes val layoutResId: Int
) {

    data class SearchResult(
        val id: Int,
        val thumbnailUrl: String,
        val type: Type,
        val dateTime: Date,
        var isBookmarked: Boolean = false
    ) : ItemOnSearch(R.layout.item_search_result) {
        enum class Type {
            IMAGE,
            VIDEO
        }
    }

    data class SearchPage(
        val searchPageIndex: Int
    ) : ItemOnSearch(R.layout.item_search_page_header)
}


