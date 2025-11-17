package cz.quanti.spacex.app

import kotlinx.serialization.Serializable

interface Graph {
    @Serializable
    data object Rockets : Graph

}