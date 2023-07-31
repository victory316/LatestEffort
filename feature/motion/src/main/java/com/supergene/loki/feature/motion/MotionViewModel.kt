package com.supergene.loki.feature.motion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choidev.latesteffort.core.util.motion.AccelerometerData
import com.choidev.latesteffort.core.util.motion.MotionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MotionViewModel @Inject constructor(
    private val motionManager: MotionManager
) : ViewModel() {

    private val _accelerometerData = MutableStateFlow<AccelerometerData>(AccelerometerData())
    val accelerometerData = _accelerometerData.stateIn(
        scope = viewModelScope,
        initialValue = AccelerometerData(),
        started = SharingStarted.WhileSubscribed(5_000),
    )

    init {
        motionManager.observeAccelerometer {
            _accelerometerData.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()

        motionManager.unregisterObservers()
    }
}
