package com.choidev.latesteffort.core.util.vibration

import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.VibratorManager
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.S)
class VibratorFromQ(
    context: Context
) : VibrationAdapter {

    private val vibrator =
        context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager

    override fun vibrate(duration: Long, repeat: Int, amplitude: Int) {
        val effect = VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
        val combined = CombinedVibration.createParallel(effect)

        vibrator.vibrate(combined)
    }

    override fun vibrateWithPattern(timing: LongArray, amplitudes: IntArray, effect: Int) {
        val effect =
            VibrationEffect.createWaveform(timing, amplitudes, VibrationEffect.DEFAULT_AMPLITUDE)
        val combined = CombinedVibration.createParallel(effect)

        vibrator.vibrate(combined)
    }
}
