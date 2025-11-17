package cz.quanti.spacex.features.rockets.data.network

import cz.quanti.spacex.core.domain.DataError
import cz.quanti.spacex.core.domain.Result
import cz.quanti.spacex.features.rockets.data.dto.RocketDto

interface RemoteRocketDataSource {

    suspend fun getAllRockets(): Result<List<RocketDto>, DataError.Remote>

    suspend fun getRocketById(id: String): Result<RocketDto, DataError.Remote>
}