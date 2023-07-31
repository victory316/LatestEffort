package com.choidev.latesteffort.core.util.motion

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import javax.inject.Inject

class MotionManagerImpl @Inject constructor(
    context: Context
) : MotionManager {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val gravitySensor: Sensor?
    private val accelerationSensor: Sensor?
    private val significantMotionSensor: Sensor?
    private val stepCounterSensor: Sensor?
    private val stepDetectorSensor: Sensor?

    init {
        with(sensorManager) {
            gravitySensor = getDefaultSensor(Sensor.TYPE_GRAVITY)
            accelerationSensor = getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            significantMotionSensor = getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION)
            stepCounterSensor = getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            stepDetectorSensor = getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        }
    }

    override fun observeTriggerEvent(event: () -> Unit) {
        val triggerEventListener = object : TriggerEventListener() {
            override fun onTrigger(event: TriggerEvent?) {

            }
        }

        significantMotionSensor?.let { sensor ->
            sensorManager.requestTriggerSensor(triggerEventListener, sensor)
        }
    }

    override fun observeGyroscope() {


        TODO("Not yet implemented")
    }

    override fun observeAccelerometer() {
        TODO("Not yet implemented")
    }

    override fun observeStep() {
        TODO("Not yet implemented")
    }
}
