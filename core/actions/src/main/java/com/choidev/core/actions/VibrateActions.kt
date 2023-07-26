package com.choidev.core.actions

import android.os.Build
import android.os.VibrationEffect

sealed interface VibrateAction : Action {
    data class Vibrate(
        val activate: Boolean,
        val duration: Long,
        val effect: VibrationEffect,
        val amplitude: Int
    ) : VibrateAction

    data class VibrateEffect(
        val effect: VibrationEffect
    ) : VibrateAction

    data class VibratePattern(
        val repeat: Boolean,
        val patterns: List<Pair<Int, Int>>
    ) : VibrateAction

    object StopVibration : VibrateAction

    enum class VibrationEffect {
        EFFECT_CLICK,
        EFFECT_DOUBLE_CLICK,
        EFFECT_TICK
    }
}

fun VibrateAction.VibrationEffect.mapToId(): Int {
    return when (Build.VERSION.SDK_INT) {
        in 0..28 -> {
            when (this) {
                VibrateAction.VibrationEffect.EFFECT_CLICK -> -1
                VibrateAction.VibrationEffect.EFFECT_DOUBLE_CLICK -> -1
                VibrateAction.VibrationEffect.EFFECT_TICK -> -1
            }
        }

        else -> {
            when (this) {
                VibrateAction.VibrationEffect.EFFECT_CLICK -> VibrationEffect.EFFECT_CLICK
                VibrateAction.VibrationEffect.EFFECT_DOUBLE_CLICK -> VibrationEffect.EFFECT_DOUBLE_CLICK
                VibrateAction.VibrationEffect.EFFECT_TICK -> VibrationEffect.EFFECT_TICK
            }
        }
    }
}
