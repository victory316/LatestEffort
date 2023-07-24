package com.choidev.vibration.state

import com.choidev.core.actions.VibrationAction

data class VibrationState(
    val activated: Boolean = false,
    val duration: Long = 0L,
    val amplitude: Int = 0,
    val effect: VibrationAction.VibrationEffect = VibrationAction.VibrationEffect.EFFECT_CLICK,
    val repeat: Boolean = false
)
