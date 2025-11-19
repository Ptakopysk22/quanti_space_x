package cz.quanti.spacex.features.rockets.domain.sensors

import cz.quanti.spacex.features.rockets.domain.model.AccelerometerData
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import platform.CoreMotion.CMAccelerometerData
import platform.CoreMotion.CMMotionManager
import platform.Foundation.NSOperationQueue

actual class AccelerometerSensor {

    // CoreMotion manager responsible for accelerometer updates
    private val motionManager = CMMotionManager()

    // Holds the active Flow instance
    private var flow: Flow<AccelerometerData>? = null

    // Reference to the internal Flow channel
    private var localChannel: SendChannel<AccelerometerData>? = null

    @OptIn(ExperimentalForeignApi::class)
    actual fun startListening(): Flow<AccelerometerData> {
        // Reuse the existing Flow if already created
        if (flow != null) return flow!!

        flow = callbackFlow {

            // Store the Flow's channel for manual closing
            localChannel = this@callbackFlow.channel

            // Check whether accelerometer hardware is available
            if (!motionManager.isAccelerometerAvailable()) {
                trySend(AccelerometerData.Error("Accelerometer not available"))
                close()
                return@callbackFlow
            }

            // Set update frequency (60 Hz)
            motionManager.accelerometerUpdateInterval = 1.0 / 60.0

            // Start receiving accelerometer data
            motionManager.startAccelerometerUpdatesToQueue(
                NSOperationQueue.mainQueue
            ) { data: CMAccelerometerData?, error ->

                // Report any CoreMotion error as Flow error
                if (error != null) {
                    trySend(AccelerometerData.Error(error.localizedDescription))
                    return@startAccelerometerUpdatesToQueue
                }

                // Handle null or missing sensor values
                val acc = data?.acceleration
                if (acc == null) {
                    trySend(AccelerometerData.Error("Accelerometer returned null data"))
                    return@startAccelerometerUpdatesToQueue
                }

                // Align values with Android output using a correction factor
                val correctionFactor = -10f
                acc.useContents {
                    trySend(
                        AccelerometerData.Data(
                            x = x.toFloat() * correctionFactor,
                            y = y.toFloat() * correctionFactor,
                            z = z.toFloat() * correctionFactor
                        )
                    )
                }
            }

            // Clean up when Flow collection is cancelled
            awaitClose {
                motionManager.stopAccelerometerUpdates()
                localChannel = null
            }
        }

        return flow!!
    }

    actual fun stopListening() {
        // Stop accelerometer updates outside of Flow coroutine
        motionManager.stopAccelerometerUpdates()
        flow = null
        localChannel?.close()
    }
}