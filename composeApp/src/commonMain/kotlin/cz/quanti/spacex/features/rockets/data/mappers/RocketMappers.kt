package cz.quanti.spacex.features.rockets.data.mappers

import cz.quanti.spacex.features.rockets.data.dto.RocketDto
import cz.quanti.spacex.features.rockets.data.dto.RocketStageDto
import cz.quanti.spacex.features.rockets.domain.model.Rocket
import cz.quanti.spacex.features.rockets.domain.model.RocketDetail
import cz.quanti.spacex.features.rockets.domain.model.RocketStage
import cz.quanti.spacex.features.rockets.domain.model.RocketStageType
import kotlinx.datetime.LocalDate

fun RocketDto.toRocket(): Rocket {
    return Rocket(
        id = id,
        name = rocketName,
        firstFlight = LocalDate.parse(firstFlight)
    )
}

fun RocketDto.toRocketDetail(): RocketDetail {
    return RocketDetail(
        id = id,
        name = rocketName,
        description = description,
        heightMeters = height.meters,
        diameterMeters = diameter.meters,
        massKg = mass.kg,
        stages = listOf(
            firstStage.toRocketStage(RocketStageType.FIRST),
            secondStage.toRocketStage(RocketStageType.SECOND)
        ),
        images = images
    )
}


fun RocketStageDto.toRocketStage(type: RocketStageType): RocketStage {
    return RocketStage(
        type = type,
        reusable = reusable,
        engines = engines,
        fuelCapacity = fuelAmountTons,
        burnTime = burnTimeSec
    )
}