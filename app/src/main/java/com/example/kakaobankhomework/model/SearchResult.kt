package com.example.kakaobankhomework.model

import com.example.kakaobankhomework.binding.SimpleItemDiffCallback

data class SearchResult(
    val id: Int,
    val thumbnailUrl: String,
    val type: Type
) : SimpleItemDiffCallback.DiffCallback {

    enum class Type {
        IMAGE,
        VIDEO
    }
}
