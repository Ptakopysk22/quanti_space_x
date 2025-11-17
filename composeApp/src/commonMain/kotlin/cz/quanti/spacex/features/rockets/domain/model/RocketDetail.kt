package cz.quanti.spacex.features.rockets.domain.model

data class RocketDetail(
    val id: String,
    val name: String,
    val description: String,
    val heightMeters: Double?,
    val diameterMeters: Double?,
    val massKg: Int?,
    val stages: List<RocketStage>,
    val images: List<String>
)

data class RocketStage(
    val type: RocketStageType,
    val reusable: Boolean,
    val engines: Int,
    val fuelCapacity: Double?,
    val burnTime: Int?,
)

enum class RocketStageType {
    FIRST, SECOND
}


