package com.example.kakaobankhomework.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.SearchUseCase
import com.example.kakaobankhomework.ui.model.UiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val searchImageState = MutableStateFlow<UiResult>(UiResult.Loading)
    private val searchVideoState = MutableStateFlow<UiResult>(UiResult.Loading)

    val searchResultFlow: StateFlow<SearchUiState> =
        combine(searchImageState, searchVideoState) { images, videos ->
            SearchUiState(
                imageCurrentPage = 0,
                videoCurrentPage = 0,
                searchResults = emptyList(),
                imagePageable = false,
                videoPageable = false
            )
        }.stateIn(
            scope = viewModelScope,
            initialValue = SearchUiState(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    val queryText = MutableLiveData<String?>(null)
    private val searchQuery
        get() = queryText.value

    fun searchImage(page: Int = 10) = viewModelScope.launch {
        searchQuery?.let { query ->
            searchUseCase.searchImage(query = query, count = page).collect { result ->
                Log.d("LOGGING", "$result: ")
                queryText.value = ""
            }
        }
    }

    fun searchVideo(page: Int = 10) = viewModelScope.launch {
        searchQuery?.let { query ->
            searchUseCase.searchVideo(query = query, count = page).collect {
                searchVideoState.value = UiResult.Success
            }
        }
    }
}
