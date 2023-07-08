package com.example.domain.model

data class SearchResultVideo(
    val result: List<Video>,
    val currentPage: Int,
    val isPageable: Boolean
)