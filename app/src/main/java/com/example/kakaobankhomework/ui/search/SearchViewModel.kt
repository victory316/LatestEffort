package com.example.kakaobankhomework.ui.search

import android.app.appsearch.SearchResult
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.SearchUseCase
import com.example.kakaobankhomework.model.SearchUiState
import com.example.kakaobankhomework.model.UiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val searchUiState = MutableStateFlow(SearchUiState())

    private val searchImageState = MutableStateFlow<UiResult<List<SearchResult>>>(UiResult.Loading)
    private val searchVideoState = MutableStateFlow<UiResult<List<SearchResult>>>(UiResult.Loading)

    val queryText = MutableLiveData<String?>(null)
    val searchQuery
        get() = queryText.value

    init {
        combine(searchImageState, searchVideoState) { images, videos ->
            images
        }
    }

    fun searchImage(page: Int = 10) = viewModelScope.launch {
        searchQuery?.let { query ->
            searchUseCase.searchImage(query = query, count = page).collect {
                Log.d("LOGGING", "$it: ")
                queryText.value = ""
            }
        }
    }

    fun searchVideo(page: Int = 10) = viewModelScope.launch {
        searchQuery?.let { query ->
            searchUseCase.searchVideo(query = query, count = page).collect {

            }
        }
    }
}
