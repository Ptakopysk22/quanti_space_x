package dataSource

import cz.quanti.spacex.core.domain.DataError
import cz.quanti.spacex.core.domain.Result
import cz.quanti.spacex.features.rockets.data.dto.RocketDto
import cz.quanti.spacex.features.rockets.data.network.RemoteRocketDataSource

class FakeRemoteRocketDataSource : RemoteRocketDataSource {

    var rocketsResult: Result<List<RocketDto>, DataError.Remote>? = null
    var rocketDetailResult: Result<RocketDto, DataError.Remote>? = null

    override suspend fun getAllRockets(): Result<List<RocketDto>, DataError.Remote> {
        return rocketsResult ?: error("rocketsResult not set")
    }

    override suspend fun getRocketById(id: String): Result<RocketDto, DataError.Remote> {
        return rocketDetailResult ?: error("rocketDetailResult not set")
    }
}