package com.example.kakaobankhomework.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.BookmarkUseCase
import com.example.domain.SearchUseCase
import com.example.kakaobankhomework.ui.model.UiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val searchImageState = MutableStateFlow<UiResult>(UiResult.Loading)
    private val searchVideoState = MutableStateFlow<UiResult>(UiResult.Loading)

    fun searchImage(query: String, page: Int) = viewModelScope.launch {
        searchUseCase.searchImage(query = query, count = page)
    }

    fun searchVideo(query: String, page: Int) = viewModelScope.launch {
        searchUseCase.searchImage(query = query, count = page)
    }
}
