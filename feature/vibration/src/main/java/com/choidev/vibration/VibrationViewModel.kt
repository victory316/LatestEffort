package com.choidev.vibration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choidev.core.actions.VibrateAction
import com.choidev.vibration.state.VibrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VibrationViewModel @Inject constructor() : ViewModel() {

    private val _vibrationState = MutableStateFlow(VibrationState())
    val vibrationState = _vibrationState.stateIn(
        scope = viewModelScope,
        initialValue = VibrationState(),
        started = SharingStarted.WhileSubscribed(5_000)
    )

    fun repeatVibration(repeat: Boolean) {
        _vibrationState.update {
            it.copy(repeat = repeat)
        }
    }

    fun toggleVibrationPattern() {
        _vibrationState.update {
            it.copy(activated = !it.activated)
        }

        if (!vibrationState.value.repeat) {
            viewModelScope.launch {
                val totalTime = vibrationState.value.patterns
                    .map { it.first }
                    .reduce { acc, i -> acc + i }

                delay(totalTime.toLong())

                _vibrationState.update {
                    it.copy(activated = false)
                }
            }
        }
    }

    fun selectVibrationEffect(effect: VibrateAction.VibrationEffect) {
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
