package com.example.kakaobankhomework.model

data class SearchResult(
    val id: Int,
    val thumbnailUrl: String,
    val type: Type
) {

    enum class Type {
        IMAGE,
        VIDEO
    }
}
