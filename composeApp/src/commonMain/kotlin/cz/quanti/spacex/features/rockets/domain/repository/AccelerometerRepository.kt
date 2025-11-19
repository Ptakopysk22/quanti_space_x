package cz.quanti.spacex.features.rockets.domain.repository

import cz.quanti.spacex.features.rockets.domain.model.AccelerometerData
import kotlinx.coroutines.flow.Flow

interface AccelerometerRepository {
    fun observe(): Flow<AccelerometerData>
    fun stop()
}