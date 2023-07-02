package com.example.kakaobankhomework.ui.model

sealed interface SearchResult {

    data class ImageResult(
        val id: Int,
        val thumbnailUrl: String
    ) : SearchResult

    data class VideoResult(
        val id: Int,
        val thumbnailUrl: String
    ) : SearchResult
}
