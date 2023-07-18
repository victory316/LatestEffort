package com.example.latesteffort.ui.search

import com.example.latesteffort.model.ItemSearch

data class SearchUiState(
    val imageCurrentPage: Int = 0,
    val videoCurrentPage: Int = 0,
    val searchResults: List<ItemSearch> = emptyList(),
    val imagePageable: Boolean = false,
    val videoPageable: Boolean = false
)
