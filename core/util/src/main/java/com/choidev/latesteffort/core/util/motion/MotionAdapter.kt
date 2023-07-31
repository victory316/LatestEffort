package com.choidev.latesteffort.core.util.motion

interface MotionAdapter {

    fun observeTriggerEvent(event: () -> Unit)

    fun observeGyroscope()

    fun observeAccelerometer()

    fun observeStep()
}
