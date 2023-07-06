package com.example.domain.model

data class SearchResultImage(
    val result: List<Image>,
    val currentPage: Int,
    val isPageable: Boolean
)
