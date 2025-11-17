package cz.quanti.spacex.features.rockets.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketDto(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val rocketName: String,

    @SerialName("first_flight")
    val firstFlight: String,

    @SerialName("description")
    val description: String,

    @SerialName("height")
    val height: DimensionDto,

    @SerialName("diameter")
    val diameter: DimensionDto,

    @SerialName("mass")
    val mass: MassDto,

    @SerialName("first_stage")
    val firstStage: RocketStageDto,

    @SerialName("second_stage")
    val secondStage: RocketStageDto,

    @SerialName("flickr_images")
    val images: List<String>,
)