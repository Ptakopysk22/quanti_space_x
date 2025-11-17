package cz.quanti.spacex.features.rockets.domain.repository

import cz.quanti.spacex.core.domain.DataError
import cz.quanti.spacex.core.domain.Result
import cz.quanti.spacex.features.rockets.domain.model.Rocket
import cz.quanti.spacex.features.rockets.domain.model.RocketDetail

interface RocketRepository {

    suspend fun getAllRockets(): Result<List<Rocket>, DataError.Remote>

    suspend fun getRocketById(id: String): Result<RocketDetail, DataError.Remote>

}