package cz.quanti.spacex.features.rockets.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import cz.quanti.spacex.app.Graph
import cz.quanti.spacex.features.rockets.presentation.flightSimulator.FlightSimulatorRoute
import cz.quanti.spacex.features.rockets.presentation.rocketDetail.RocketDetailRoute
import cz.quanti.spacex.features.rockets.presentation.rocketList.RocketListRoute
import io.github.aakira.napier.Napier
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

private sealed interface Rockets {
    @Serializable
    data object RocketsList

    @Serializable
    data class RocketDetail(val rocketId: String)

    @Serializable
    data class FlightSimulator(val rocketName: String)
}


fun NavGraphBuilder.rockets(
    navController: NavController,
) {
    navigation<Graph.Rockets>(
        startDestination = Rockets.RocketsList,
    ) {
        composable<Rockets.RocketsList> {
            RocketListRoute(
                onRocketClick = {
                    navController.navigate(Rockets.RocketDetail(it.id))
                },
            )
        }
        composable<Rockets.RocketDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<Rockets.RocketDetail>()
            val rocketId = args.rocketId

            RocketDetailRoute(
                onLaunchClick = { navController.navigate(Rockets.FlightSimulator(it)) },
                viewModel = koinViewModel(parameters = { parametersOf(rocketId) }),
                onBackClick = { navController.popBackStack() },
            )
        }
        composable<Rockets.FlightSimulator> { backStackEntry ->
            val args = backStackEntry.toRoute<Rockets.FlightSimulator>()
            val rocketName = args.rocketName

            Napier.e { rocketName }

            FlightSimulatorRoute (
                viewModel = koinViewModel(parameters = { parametersOf(rocketName) }),
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}