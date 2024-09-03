package com.choidev.latesteffort.feature.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NestedScrollViewModel @Inject constructor() : ViewModel() {

    private val _dummyItems: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val dummyItems = _dummyItems
        .stateIn(
            scope = viewModelScope,
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(5000L)
        )

    init {
        _dummyItems.update {
            listOf(
                "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
            )
        }
    }
}
