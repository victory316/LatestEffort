package com.choidev.latesteffort.core.util.vibration

import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class VibrationManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private var vibrator: VibrationAdapter = when (Build.VERSION.SDK_INT) {
        in 26..30 -> {
            VibratorBelowQ(context)
        }

        else -> {
            VibratorFromQ(context)
        }
    }

    fun vibrate(duration: Long = 1000L, repeat: Int = 0) {
        vibrator.vibrate()
    }

    fun vibrateWithPattern(timing: List<Long>, amplitudes: List<Int>, repeat: Int = 0) {
        vibrator.vibrate()
    }
}
