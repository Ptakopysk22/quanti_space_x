package cz.quanti.spacex.features.rockets.presentation.rocketList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.quanti.spacex.core.presentation.ErrorBox
import cz.quanti.spacex.core.presentation.ProgressIndicator
import cz.quanti.spacex.features.rockets.domain.model.Rocket
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import quanti_space_x.composeapp.generated.resources.Res
import quanti_space_x.composeapp.generated.resources.rockets

@Composable
fun RocketListRoute(
    onRocketClick: (Rocket) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RocketListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RocketListScreen(
        state = state,
        modifier = modifier,
        onRocketClick = onRocketClick,
    )
}

@Composable
private fun RocketListScreen(
    state: RocketListState,
    modifier: Modifier = Modifier,
    onRocketClick: (Rocket) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->

        when (state) {

            is RocketListState.Loading -> {
                ProgressIndicator()
            }

            is RocketListState.Error -> {
                ErrorBox(error = state.errorMessage)
            }

            is RocketListState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = stringResource(Res.string.rockets),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    RocketList(
                        rockets = state.rockets,
                        onRocketClick = onRocketClick,
                    )
                }
            }
        }
    }
}