package com.choidev.latesteffort.feature.search_media.ui.search

import com.choidev.latesteffort.feature.search_media.model.ItemSearch

data class SearchUiState(
    val imageCurrentPage: Int = 0,
    val videoCurrentPage: Int = 0,
    val searchResults: List<ItemSearch> = emptyList(),
    val imagePageable: Boolean = false,
    val videoPageable: Boolean = false
)
