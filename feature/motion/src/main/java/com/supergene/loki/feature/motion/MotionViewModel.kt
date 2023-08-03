package com.supergene.loki.feature.motion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choidev.core.actions.VibrateAction
import com.choidev.core.actions.mapToId
import com.choidev.latesteffort.core.util.motion.AccelerometerData
import com.choidev.latesteffort.core.util.motion.CachedAccelerometerData
import com.choidev.latesteffort.core.util.motion.MotionManager
import com.choidev.latesteffort.core.util.motion.SensorRate
import com.choidev.latesteffort.core.util.vibration.VibrationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.roundToInt

private const val DEFAULT_THRESHOLD = 10f
private const val DEFAULT_DIGIT = 1

@HiltViewModel
class MotionViewModel @Inject constructor(
    private val motionManager: MotionManager,
    private val vibrationManager: VibrationManager
) : ViewModel() {

    private val digitSet = listOf(1, 2, 3, 4, 5, 6, 7, 8)
    private var fractionDigitIndex = DEFAULT_DIGIT

    val currentRate = MutableStateFlow(SensorRate.NORMAL)
    val shakeThreshold = MutableStateFlow(DEFAULT_THRESHOLD)
    val fractionDigit = MutableStateFlow(digitSet[fractionDigitIndex])

    private val _accelerometerData = MutableStateFlow(AccelerometerData())
    val accelerometerData = _accelerometerData.stateIn(
        scope = viewModelScope,
        initialValue = AccelerometerData(),
        started = SharingStarted.WhileSubscribed(5_000),
    )

    private val _cachedAccelerometerData = MutableStateFlow(CachedAccelerometerData())
    val cachedAccelerometerData = _cachedAccelerometerData.asStateFlow()

    init {
        observeAccelerometer()
        observeThreshold()
        determineShake()
    }

    fun observeAccelerometer(rate: SensorRate = SensorRate.NORMAL) {
        currentRate.value = rate

        motionManager.observeAccelerometer(rate) { data ->
            _accelerometerData.value = data.copy(
                gravityX = data.gravityX.roundTo(fractionDigit.value),
                gravityY = data.gravityY.roundTo(fractionDigit.value),
                gravityZ = data.gravityZ.roundTo(fractionDigit.value),
                accelerationX = data.accelerationX.roundTo(fractionDigit.value),
                accelerationY = data.accelerationY.roundTo(fractionDigit.value),
                accelerationZ = data.accelerationZ.roundTo(fractionDigit.value)
            )

            _cachedAccelerometerData.update {
                it.copy(
                    accelerationX = it.accelerationX.toMutableList().apply {
                        add(data.accelerationX)
                    },
                    accelerationY = it.accelerationY.toMutableList().apply {
                        add(data.accelerationY)
                    },
                    accelerationZ = it.accelerationZ.toMutableList().apply {
                        add(data.accelerationZ)
                    }
                )
            }
        }
    }

    fun incrementFractionDigit() {
        if ((fractionDigitIndex + 1) <= digitSet.lastIndex) {
            fractionDigitIndex++
        } else {
            fractionDigitIndex = 0
        }

        fractionDigit.value = digitSet[fractionDigitIndex]
    }

    private fun determineShake() = viewModelScope.launch {
        var shakeFlag = false
        var accSum: Float

        accelerometerData.collect { data ->
            accSum = data.accelerationX + data.accelerationY + data.accelerationZ

            if (shakeFlag && !accSum.isNegative()) {
                vibrationManager.vibrate()
                shakeFlag = false

                return@collect
            }

            if (accSum.isNegative() && accSum.absoluteValue > shakeThreshold.value) {
                shakeFlag = true
            }
        }
    }

    private fun observeThreshold() = viewModelScope.launch {
        shakeThreshold.collect {
            vibrationManager.vibrateEffect(VibrateAction.VibrationEffect.EFFECT_TICK.mapToId())
        }
    }

    override fun onCleared() {
        super.onCleared()

        motionManager.unregisterObservers()
    }

    private fun Float.roundTo(numFractionDigits: Int): Float {
        val factor = 10.0.pow(numFractionDigits.toDouble())
        return ((this * factor).roundToInt() / factor).toFloat()
    }

    private fun Float.isNegative(): Boolean {
        return this < 0.0
    }
}
