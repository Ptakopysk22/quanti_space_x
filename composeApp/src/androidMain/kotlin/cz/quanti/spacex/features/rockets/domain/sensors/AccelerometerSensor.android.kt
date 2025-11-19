package cz.quanti.spacex.features.rockets.domain.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import cz.quanti.spacex.features.rockets.domain.model.AccelerometerData
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


actual class AccelerometerSensor (private val context: Context)
{
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null
    private var listener: SensorEventListener? = null

    // Holds the active Flow instance so we don't recreate it
    private var flow: Flow<AccelerometerData>? = null

    // Reference to the internal Flow channel so we can close it manually if needed
    private var localChannel: SendChannel<AccelerometerData>? = null

    actual fun startListening(): Flow<AccelerometerData> {
        // Reuse an existing flow instead of creating a new one
        if (flow != null) return flow!!

        flow = callbackFlow {

            // Acquire the SensorManager (may be unavailable on some devices)
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
            if (sensorManager == null) {
                trySend(AccelerometerData.Error("SensorManager not available"))
                close()
                return@callbackFlow
            }

            // Try to access the accelerometer sensor (may not exist on the device)
            sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            if (sensor == null) {
                trySend(AccelerometerData.Error("Accelerometer not available"))
                close()
                return@callbackFlow
            }

            // Store reference to the channel so we can close it from stopListening()
            localChannel = this@callbackFlow.channel

            // Sensor listener that pushes updates into the Flow
            listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent) {
                    trySend(
                        AccelerometerData.Data(
                            x = event.values[0],
                            y = event.values[1],
                            z = event.values[2]
                        )
                    )
                }
                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            // Register the listener (may fail on some devices)
            val registered = sensorManager!!.registerListener(
                listener,
                sensor,
                SensorManager.SENSOR_DELAY_GAME
            )
            if (!registered) {
                trySend(AccelerometerData.Error("Failed to register sensor listener"))
                close()
                return@callbackFlow
            }

            // Called when the coroutine Flow collector is cancelled
            awaitClose {
                sensorManager?.unregisterListener(listener)
                localChannel = null
            }
        }

        return flow!!
    }

    actual fun stopListening() {
        // Manually stop updates outside of the Flow coroutine
        sensorManager?.unregisterListener(listener)
        listener = null
        flow = null
        localChannel?.close()
    }
}