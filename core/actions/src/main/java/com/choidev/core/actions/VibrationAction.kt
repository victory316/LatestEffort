package com.choidev.core.actions

import android.os.Build
import android.os.VibrationEffect

sealed interface VibrationAction : Action {
    data class Vibrate(
        val activate: Boolean,
        val duration: Long,
        val effect: VibrationEffect,
        val amplitude: Int
    ) : VibrationAction

    data class VibrateEffect(
        val effect: VibrationEffect
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

fun VibrationAction.VibrationEffect.mapToId(): Int {
    return when (Build.VERSION.SDK_INT) {
        in 0..28 -> {
            when (this) {
                VibrationAction.VibrationEffect.EFFECT_CLICK -> -1
                VibrationAction.VibrationEffect.EFFECT_DOUBLE_CLICK -> -1
                VibrationAction.VibrationEffect.EFFECT_TICK -> -1
            }
        }

        else -> {
            when (this) {
                VibrationAction.VibrationEffect.EFFECT_CLICK -> VibrationEffect.EFFECT_CLICK
                VibrationAction.VibrationEffect.EFFECT_DOUBLE_CLICK -> VibrationEffect.EFFECT_DOUBLE_CLICK
                VibrationAction.VibrationEffect.EFFECT_TICK -> VibrationEffect.EFFECT_TICK
            }
        }
    }
}
