package cz.quanti.spacex.features.rockets.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketStageDto(
    @SerialName("reusable")
    val reusable: Boolean,

    @SerialName("engines")
    val engines: Int,

    @SerialName("fuel_amount_tons")
    val fuelAmountTons: Double? = null,

    @SerialName("burn_time_sec")
    val burnTimeSec: Int? = null,
)
