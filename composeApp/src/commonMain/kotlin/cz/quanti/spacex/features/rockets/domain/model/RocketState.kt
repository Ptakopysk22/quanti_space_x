package cz.quanti.spacex.features.rockets.domain.model

enum class RocketState {
    ON_GROUND,
    STARTING_ENGINE,
    LIFTING,
    FLYING,
    LANDING,
    STOPPING_ENGINE
}