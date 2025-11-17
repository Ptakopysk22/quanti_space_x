package cz.quanti.spacex.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cz.quanti.spacex.features.rockets.presentation.rockets
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController(),
) {
        MaterialTheme(
            //colorScheme = lightColorSchema,
        ) {
            NavHost(
                navController = navController,
                startDestination = Graph.Rockets
            ) {
                rockets(
                    navController = navController,
                )
            }
        }
}
