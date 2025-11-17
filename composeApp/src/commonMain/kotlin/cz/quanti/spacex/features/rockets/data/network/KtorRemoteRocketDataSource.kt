package cz.quanti.spacex.features.rockets.data.network

import cz.quanti.spacex.core.data.safeCall
import cz.quanti.spacex.core.domain.DataError
import cz.quanti.spacex.core.domain.Result
import cz.quanti.spacex.features.rockets.data.dto.RocketDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorRemoteRocketDataSource(
    private val client: HttpClient,
) : RemoteRocketDataSource {
    override suspend fun getAllRockets(): Result<List<RocketDto>, DataError.Remote> {
        return safeCall<List<RocketDto>> {
            client.get("rockets")
        }
    }

    override suspend fun getRocketById(id: String): Result<RocketDto, DataError.Remote> {
        return safeCall<RocketDto> {
            client.get(
                urlString = "rockets/$id"
            )
        }
    }

}