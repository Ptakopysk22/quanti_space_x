package cz.quanti.spacex.features.rockets.domain.model

sealed interface AccelerometerData {
    data class Data(val x: Float, val y: Float, val z: Float) : AccelerometerData
    data class Error(val message: String) : AccelerometerData
}

