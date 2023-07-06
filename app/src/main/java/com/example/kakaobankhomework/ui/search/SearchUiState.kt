package com.example.kakaobankhomework.ui.search

import com.example.kakaobankhomework.model.SearchItem

data class SearchUiState(
    val imageCurrentPage: Int = 0,
    val videoCurrentPage: Int = 0,
    val searchResults: List<SearchItem> = emptyList(),
    val imagePageable: Boolean = false,
    val videoPageable: Boolean = false,
    val isLoading: Boolean = false
)
