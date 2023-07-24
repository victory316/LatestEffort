package com.choidev.vibration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choidev.core.actions.VibrationAction
import com.choidev.vibration.state.VibrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class VibrationViewModel @Inject constructor() : ViewModel() {

    private val _vibrationState = MutableStateFlow(VibrationState())
    val vibrationState = _vibrationState.stateIn(
        scope = viewModelScope,
        initialValue = VibrationState(),
        started = SharingStarted.WhileSubscribed(5_000)
    )

    private var availableAmplitude: Int by Delegates.vetoable(0) { _, _, newValue ->
        newValue in 0..255
    }

    fun switchVibration(activate: Boolean) {
        _vibrationState.update {
            it.copy(activated = activate)
        }
    }

    fun repeatVibration(repeat: Boolean) {
        _vibrationState.update {
            it.copy(repeat = repeat)
        }
    }

    fun vibrationDuration(duration: Long) {
        _vibrationState.update {
            it.copy(duration = duration)
        }
    }

    fun vibrationAmplitude(amplitude: Int) {
        Log.d("LOGGING", "amp :$amplitude: ")
        availableAmplitude = amplitude
        Log.d("LOGGING", "availableAmplitude :$availableAmplitude: ")
        _vibrationState.update {
            it.copy(amplitude = availableAmplitude)
        }
    }

    fun selectVibrationEffect(effect: VibrationAction.VibrationEffect) {
        _vibrationState.update {
            it.copy(effect = effect)
        }
    }
}
