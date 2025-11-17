package cz.quanti.spacex.features.rockets.data.repository

import cz.quanti.spacex.core.domain.DataError
import cz.quanti.spacex.core.domain.Result
import cz.quanti.spacex.core.domain.map
import cz.quanti.spacex.features.rockets.data.mappers.toRocket
import cz.quanti.spacex.features.rockets.data.mappers.toRocketDetail
import cz.quanti.spacex.features.rockets.data.network.RemoteRocketDataSource
import cz.quanti.spacex.features.rockets.domain.model.Rocket
import cz.quanti.spacex.features.rockets.domain.model.RocketDetail
import cz.quanti.spacex.features.rockets.domain.repository.RocketRepository

class DefaultRocketRepository(
    private val remoteRocketDataSource: RemoteRocketDataSource
) : RocketRepository {
    override suspend fun getAllRockets(): Result<List<Rocket>, DataError.Remote> {
        return remoteRocketDataSource.getAllRockets().map { dto -> dto.map { it.toRocket() } }
    }

    override suspend fun getRocketById(id: String): Result<RocketDetail, DataError.Remote> {
        return remoteRocketDataSource.getRocketById(id = id).map { dto -> dto.toRocketDetail() }
    }

}