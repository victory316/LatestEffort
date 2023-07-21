package com.choidev.latesteffort.core.util.vibration

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator

@Suppress("deprecation")
class VibratorBelowQ(
    context: Context
) : VibrationAdapter {

    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    override fun vibrate(duration: Long, repeat: Int, amplitude: Int) {
        VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE).also { effect ->
            vibrator.vibrate(effect)
        }
    }

    override fun vibrateWithPattern(timing: LongArray, amplitudes: IntArray, effect: Int) {
        VibrationEffect.createWaveform(timing, amplitudes, effect).also { effect ->
            vibrator.vibrate(effect)
        }
    }
}
