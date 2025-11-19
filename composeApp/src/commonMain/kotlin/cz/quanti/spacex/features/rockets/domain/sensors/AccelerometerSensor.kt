package cz.quanti.spacex.features.rockets.domain.sensors

import cz.quanti.spacex.features.rockets.domain.model.AccelerometerData
import kotlinx.coroutines.flow.Flow

expect class AccelerometerSensor
{
    fun startListening(): Flow<AccelerometerData>
    fun stopListening()
}