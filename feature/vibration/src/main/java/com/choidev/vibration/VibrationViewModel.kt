package com.choidev.vibration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choidev.vibration.state.VibrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class VibrationViewModel @Inject constructor() : ViewModel() {

    private val _vibrationState =
        MutableStateFlow<VibrationState>(VibrationState(activated = false, 0L))
    val vibrationState = _vibrationState.stateIn(
        scope = viewModelScope,
        initialValue = VibrationState(activated = false, 0L),
        started = SharingStarted.WhileSubscribed(5_000)
    )

    fun switchVibration(activate: Boolean) {
        _vibrationState.update {
            it.copy(activated = activate)
        }
    }

    fun vibrationDuration(duration: Long) {
        _vibrationState.update {
            it.copy(duration = duration)
        }
    }
}
