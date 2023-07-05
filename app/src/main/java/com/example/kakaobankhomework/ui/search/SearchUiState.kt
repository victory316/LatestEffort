package com.example.kakaobankhomework.ui.search

import com.example.kakaobankhomework.model.SearchResult

data class SearchUiState(
    val imageCurrentPage: Int = 0,
    val videoCurrentPage: Int = 0,
    val searchResults: List<SearchResult> = emptyList(),
    val imagePageable: Boolean = false,
    val videoPageable: Boolean = false
)
