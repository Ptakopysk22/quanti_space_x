package repository

import cz.quanti.spacex.core.domain.DataError
import cz.quanti.spacex.core.domain.Result
import cz.quanti.spacex.features.rockets.domain.model.Rocket
import cz.quanti.spacex.features.rockets.domain.model.RocketDetail
import cz.quanti.spacex.features.rockets.domain.repository.RocketRepository

class FakeRocketRepository : RocketRepository {

    var rocketsResult: Result<List<Rocket>, DataError.Remote>? = null

    var rocketResult: Result<RocketDetail, DataError.Remote>? = null

    override suspend fun getAllRockets(): Result<List<Rocket>, DataError.Remote> {
        return rocketsResult ?: error("rocketsResult not set")
    }

    override suspend fun getRocketById(id: String): Result<RocketDetail, DataError.Remote> {
       return rocketResult ?: error("rocketResult not set")
    }
}