package com.choidev.latesteffort.core.util.vibration

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class VibrationManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val vibrator: VibrationAdapter = when (Build.VERSION.SDK_INT) {
        in 28..30 -> {
            VibratorBelowQ(context)
        }

        else -> {
            VibratorFromQ(context)
        }
    }

    fun vibrate(
        duration: Long = 1000L,
        repeat: Int = -1,
        amplitude: Int = VibrationEffect.DEFAULT_AMPLITUDE
    ) {
        vibrator.vibrate(
            duration = duration,
            repeat = repeat,
            amplitude = amplitude
        )
    }

    fun vibrateWithPattern(
        timing: LongArray,
        amplitudes: IntArray,
        repeat: Int = VibrationEffect.DEFAULT_AMPLITUDE
    ) {
        vibrator.vibrateWithPattern(
            timing = timing,
            amplitudes = amplitudes,
            repeat = repeat
        )
    }
}
