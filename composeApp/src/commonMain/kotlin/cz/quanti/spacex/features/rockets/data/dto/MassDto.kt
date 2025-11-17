package cz.quanti.spacex.features.rockets.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MassDto(
    @SerialName("kg")
    val kg: Int,

    @SerialName("lb")
    val lb: Int,
)
