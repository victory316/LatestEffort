package com.supergene.loki.feature.motion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choidev.latesteffort.core.util.motion.AccelerometerData
import com.choidev.latesteffort.core.util.motion.MotionManager
import com.choidev.latesteffort.core.util.motion.SensorRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.roundToInt

@HiltViewModel
class MotionViewModel @Inject constructor(
    private val motionManager: MotionManager
) : ViewModel() {

    val currentRate = MutableStateFlow(SensorRate.NORMAL)

    val shakeThreshold = MutableStateFlow(0f)

    private val _accelerometerData = MutableStateFlow(AccelerometerData())
    val accelerometerData = _accelerometerData.stateIn(
        scope = viewModelScope,
        initialValue = AccelerometerData(),
        started = SharingStarted.WhileSubscribed(5_000),
    )

    init {
        observeAccelerometer()
    }

    fun observeAccelerometer(rate: SensorRate = SensorRate.NORMAL) {
        currentRate.value = rate

        motionManager.observeAccelerometer(rate) {
            _accelerometerData.value = it.copy(
                gravityX = it.gravityX.roundTo(2),
                gravityY = it.gravityY.roundTo(2),
                gravityZ = it.gravityZ.roundTo(2),
                accelerationX = it.accelerationX.roundTo(2),
                accelerationY = it.accelerationY.roundTo(2),
                accelerationZ = it.accelerationZ.roundTo(2)
            )
        }
    }

    private fun Float.roundTo(numFractionDigits: Int): Float {
        val factor = 10.0.pow(numFractionDigits.toDouble())
        return ((this * factor).roundToInt() / factor).toFloat()
    }

    override fun onCleared() {
        super.onCleared()

        motionManager.unregisterObservers()
    }
}
