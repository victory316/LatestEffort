package com.example.kakaobankhomework.model

import androidx.annotation.LayoutRes
import com.example.kakaobankhomework.R
import java.util.Date

sealed class ItemSearch(
    @LayoutRes val layoutResId: Int
) {

    data class SearchResult(
        val thumbnailUrl: String,
        val dateTime: Date,
        var isBookmarked: Boolean = false
    ) : ItemSearch(R.layout.item_search_result) {
        enum class Type {
            IMAGE,
            VIDEO
        }
    }

    data class SearchPage(
        val searchPageIndex: Int
    ) : ItemSearch(R.layout.item_search_page_header)
}


