package com.choidev.latesteffort.core.util.motion

data class CachedAccelerometerData(
    val accelerationX: List<Float> = listOf(0f),
    val accelerationY: List<Float> = listOf(0f),
    val accelerationZ: List<Float> = listOf(0f)
)
