package cz.quanti.spacex.features.rockets.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import cz.quanti.spacex.app.Graph
import kotlinx.serialization.Serializable

private interface Rockets {
    @Serializable
    data object RocketsList

    @Serializable
    data class RocketDetails(val rocketId: String)

    @Serializable
    data class FlightSimulator(val rocketId: String)
}




fun NavGraphBuilder.rockets(
    navController: NavController,
) {
    navigation<Graph.Rockets>(
        startDestination = Rockets.RocketsList,
    ) {
        composable<Rockets.RocketsList> {

        }
    }
}