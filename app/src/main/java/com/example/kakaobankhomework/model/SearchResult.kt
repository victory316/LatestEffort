package com.example.kakaobankhomework.model

import androidx.annotation.LayoutRes
import com.example.kakaobankhomework.R
import com.example.kakaobankhomework.binding.SimpleItemDiffCallback

sealed class SearchItem(
    @LayoutRes val layoutResId: Int
) : SimpleItemDiffCallback.DiffCallback {

    data class SearchResult(
        val id: Int,
        val thumbnailUrl: String,
        val type: Type,
        var isBookmarked: Boolean = false
    ) : SearchItem(R.layout.item_search_result) {
        enum class Type {
            IMAGE,
            VIDEO
        }
    }
}


