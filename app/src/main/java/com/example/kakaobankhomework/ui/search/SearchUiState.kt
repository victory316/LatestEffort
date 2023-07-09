package com.example.kakaobankhomework.ui.search

import com.example.kakaobankhomework.model.ItemSearch

data class SearchUiState(
    val imageCurrentPage: Int = 0,
    val videoCurrentPage: Int = 0,
    val searchResults: List<ItemSearch> = emptyList(),
    val imagePageable: Boolean = false,
    val videoPageable: Boolean = false,
    val isLoading: Boolean = false
)
