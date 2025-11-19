package repository

import cz.quanti.spacex.features.rockets.domain.model.AccelerometerData
import cz.quanti.spacex.features.rockets.domain.repository.AccelerometerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeAccelerometerRepository : AccelerometerRepository {

    private val flow = MutableSharedFlow<AccelerometerData>()

    override fun observe(): Flow<AccelerometerData> = flow

    override fun stop() {
        // test-only: no-op
    }

    suspend fun emit(data: AccelerometerData) {
        flow.emit(data)
    }
}