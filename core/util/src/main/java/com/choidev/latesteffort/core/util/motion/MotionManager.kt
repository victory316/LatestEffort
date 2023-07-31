package com.choidev.latesteffort.core.util.motion

interface MotionManager {

    fun observeTriggerEvent(event: () -> Unit)

    fun observeGyroscope()

    fun observeAccelerometer(
        sensorRate: SensorRate = SensorRate.NORMAL,
        accelerometerEvent: (data: AccelerometerData) -> Unit
    )

    fun observeStep()

    fun unregisterObservers()
}
