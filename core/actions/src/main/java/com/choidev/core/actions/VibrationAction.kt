package com.choidev.core.actions

sealed interface VibrationAction : Action {
    data class Vibrate(
        val activate: Boolean,
        val duration: Long,
        val effect: VibrationEffect,
        val amplitude: Int
    ) : VibrationAction

    data class RepeatVibrate(
        val activate: Boolean,
        val duration: Long,
        val repeat: Boolean,
        val effect: VibrationEffect,
        val amplitude: Int
    ) : VibrationAction

    enum class VibrationEffect {
        EFFECT_CLICK,
        EFFECT_DOUBLE_CLICK,
        EFFECT_TICK
    }
}
