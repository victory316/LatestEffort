package com.choidev.vibration.state

data class VibrationState(
    val activated: Boolean = false,
    val duration: Long = 0L,
    val repeat: Boolean = false
)
