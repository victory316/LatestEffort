package com.choidev.latesteffort.core.util.vibration

interface VibrationAdapter {

    fun vibrate(duration: Long, repeat: Int, amplitude: Int)

    fun vibrateWithPattern(timing: LongArray, amplitudes: IntArray, effect: Int)
}
