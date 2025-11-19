package cz.quanti.spacex.features.rockets.data.repository

import cz.quanti.spacex.features.rockets.domain.model.AccelerometerData
import cz.quanti.spacex.features.rockets.domain.repository.AccelerometerRepository
import cz.quanti.spacex.features.rockets.domain.sensors.AccelerometerSensor
import kotlinx.coroutines.flow.Flow

class DefaultAccelerometerRepository (
    private val sensor: AccelerometerSensor
): AccelerometerRepository {
    private val flow by lazy {
        sensor.startListening()
    }

    override fun observe(): Flow<AccelerometerData> = flow
    override fun stop() = sensor.stopListening()
}