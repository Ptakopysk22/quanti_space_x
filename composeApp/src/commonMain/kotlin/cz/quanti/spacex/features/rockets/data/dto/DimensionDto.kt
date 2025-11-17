package cz.quanti.spacex.features.rockets.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DimensionDto(
    @SerialName("meters")
    val meters: Double? = null,

    @SerialName("feet")
    val feet: Double? = null,
)
