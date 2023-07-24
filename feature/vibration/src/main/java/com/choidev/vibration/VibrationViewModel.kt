package com.choidev.vibration

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

    fun repeatVibration(repeat: Boolean) {
        _vibrationState.update {
            it.copy(repeat = repeat)
        }
    }

    fun selectVibrationEffect(effect: VibrationAction.VibrationEffect) {
        _vibrationState.update {
            it.copy(effect = effect)
        }
    }

    fun addVibrationPattern(patternPair: Pair<Int, Int>) {
        _vibrationState.update {
            it.copy(
                patterns = it.patterns.toMutableList().apply {
                    add(patternPair)
                }.toList()
            )
        }
    }

    fun removeVibrationPattern(patternPair: Pair<Int, Int>) {
        _vibrationState.update {
            it.copy(
                patterns = it.patterns.toMutableList().apply {
                    remove(patternPair)
                }.toList()
            )
        }
    }
}
