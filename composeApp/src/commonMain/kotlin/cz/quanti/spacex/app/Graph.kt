package cz.quanti.spacex.app

import kotlinx.serialization.Serializable

sealed interface Graph {
    @Serializable
    data object Rockets : Graph

}