package com.example.kakaobankhomework.model

import androidx.annotation.LayoutRes
import com.example.kakaobankhomework.R
import com.example.kakaobankhomework.binding.SimpleItemDiffCallback

sealed class SearchResultItem(@LayoutRes val layoutResId: Int) :
    SimpleItemDiffCallback.DiffCallback {

    data class ResultItem(
        val id: Int,
        val thumbnailUrl: String,
        val type: Type
    ) : SearchResultItem(R.layout.item_search_result) {

        enum class Type {
            IMAGE,
            VIDEO
        }
    }

    data class PageHeader(val pageCount: Int) : SearchResultItem(R.layout.item_search_page_header)
}
