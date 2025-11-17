package cz.quanti.spacex.features.rockets.domain.model

import kotlinx.datetime.LocalDate

data class Rocket(
    val id: String,
    val name: String,
    val firstFlight: LocalDate,
)
