package com.choidev.latesteffort.core.util.motion

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MotionManagerImpl @Inject constructor(
    @ApplicationContext context: Context
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

    override fun observeAccelerometer(accelerometerEvent: (data: AccelerometerData) -> Unit) {
        val listener = object : SensorEventListener {
            var gravity = mutableListOf(0f, 0f, 0f)
            var linear_acceleration = mutableListOf(0f, 0f, 0f)

            override fun onSensorChanged(event: SensorEvent?) {
                event?.run {
                    // In this example, alpha is calculated as t / (t + dT),
                    // where t is the low-pass filter's time-constant and
                    // dT is the event delivery rate.

                    val alpha = 0.8f

                    // Isolate the force of gravity with the low-pass filter.
                    gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0]
                    gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1]
                    gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2]

                    // Remove the gravity contribution with the high-pass filter.
                    linear_acceleration[0] = event.values[0] - gravity[0]
                    linear_acceleration[1] = event.values[1] - gravity[1]
                    linear_acceleration[2] = event.values[2] - gravity[2]
                }

                accelerometerEvent.invoke(
                    AccelerometerData(
                        gravityX = gravity[0],
                        gravityY = gravity[1],
                        gravityZ = gravity[2],
                        accelerationX = linear_acceleration[0],
                        accelerationY = linear_acceleration[1],
                        accelerationZ = linear_acceleration[2]
                    )
                )
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                /* no-op */
            }
        }

        sensorManager.registerListener(
            listener,
            accelerationSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun observeStep() {
        TODO("Not yet implemented")
    }
}
