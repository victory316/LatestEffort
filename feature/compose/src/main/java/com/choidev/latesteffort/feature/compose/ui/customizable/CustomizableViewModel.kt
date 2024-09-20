package com.choidev.latesteffort.feature.compose.ui.customizable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choidev.latesteffort.feature.compose.ui.customizable.state.BoxInfoState
import com.choidev.latesteffort.feature.compose.ui.customizable.state.CustomizableScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CustomizableViewModel @Inject constructor() : ViewModel() {

    private val _screenState = MutableStateFlow(CustomizableScreenState())
    val screenState =
        _screenState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CustomizableScreenState()
        )

    init {
        _screenState.update {
            it.copy(boxes = listOf(BoxInfoState(2, 3)))
        }
    }
}