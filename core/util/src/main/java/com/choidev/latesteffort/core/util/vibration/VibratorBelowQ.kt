package com.choidev.latesteffort.core.util.vibration

import android.content.Context
import android.os.Vibrator

@Suppress("deprecation")
class VibratorBelowQ(
    context: Context
) : VibrationAdapter {

    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    override fun vibrate() {
        vibrator.vibrate(100L)
    }
}
