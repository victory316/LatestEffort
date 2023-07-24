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

    override fun vibrateWithEffect(effectId: Int) {
        VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE).also { effect ->
            vibrator.vibrate(effect)
        }
    }

    override fun vibrateWithPattern(timing: LongArray, amplitudes: IntArray, repeat: Int) {
        VibrationEffect.createWaveform(timing, amplitudes, repeat).also { effect ->
            vibrator.vibrate(effect)
        }
    }
}
