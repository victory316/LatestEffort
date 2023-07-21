package com.choidev.core.actions

sealed interface VibrationAction : Action {
    data class Vibrate(val activate: Boolean, val duration: Long) : VibrationAction
}