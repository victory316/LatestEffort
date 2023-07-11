package com.example.kakaobankhomework.model

import android.app.appsearch.SearchResult

data class SearchUiState(
    var imagePageable: Boolean = false,
    var videoPageable: Boolean = false,
    var currentState: UiResult<List<SearchResult>> = UiResult.Loading,
)
