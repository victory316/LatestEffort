package com.example.domain.model

class SearchResultVideo(
    val result: List<Video>,
    val currentPage: Int,
    val isPageable: Boolean
)