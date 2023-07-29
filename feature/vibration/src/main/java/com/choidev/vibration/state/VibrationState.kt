package com.choidev.vibration.state

import com.choidev.core.actions.VibrateAction

data class VibrationState(
    val activated: Boolean = false,
    val duration: Long = 0L,
    val amplitude: Int = 0,
    val patterns: List<Pair<Int, Int>> = emptyList(),
    val effect: VibrateAction.VibrationEffect = VibrateAction.VibrationEffect.EFFECT_CLICK,
    val repeat: Boolean = false
)
