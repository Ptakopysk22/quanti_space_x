package cz.quanti.spacex.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cz.quanti.spacex.core.presentation.materialTheme.lightColorSchema
import cz.quanti.spacex.features.rockets.presentation.rockets
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController(),
) {
    remember {
        Napier.base(DebugAntilog())
        null
    }
    
        MaterialTheme(
            colorScheme = lightColorSchema,
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
