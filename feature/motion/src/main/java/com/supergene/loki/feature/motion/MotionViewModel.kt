package com.supergene.loki.feature.motion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choidev.latesteffort.core.util.motion.AccelerometerData
import com.choidev.latesteffort.core.util.motion.MotionManager
import com.choidev.latesteffort.core.util.motion.SensorRate
import com.choidev.latesteffort.core.util.vibration.VibrationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.roundToInt

@OptIn(FlowPreview::class)
@HiltViewModel
class MotionViewModel @Inject constructor(
    private val motionManager: MotionManager,
    private val vibrationManager: VibrationManager
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

        viewModelScope.launch {
            accelerometerData.map {
                (it.accelerationX.absoluteValue
                        + it.accelerationY.absoluteValue
                        + it.accelerationZ.absoluteValue
                        ) / 3
            }.filter {
                it >= shakeThreshold.value
            }.collect {
                Log.d("TEST", "float : $it")
                vibrationManager.vibrate()
            }
        }
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
