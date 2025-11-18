package cz.quanti.spacex.features.rockets.presentation.rocketDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.quanti.spacex.core.presentation.ErrorBox
import cz.quanti.spacex.core.presentation.ProgressIndicator
import cz.quanti.spacex.features.rockets.domain.model.Rocket
import cz.quanti.spacex.features.rockets.presentation.components.RocketImage
import cz.quanti.spacex.features.rockets.presentation.components.RocketTopBar
import cz.quanti.spacex.features.rockets.presentation.components.SubtitleRow
import org.jetbrains.compose.resources.stringResource
import quanti_space_x.composeapp.generated.resources.Res
import quanti_space_x.composeapp.generated.resources.diameter
import quanti_space_x.composeapp.generated.resources.height
import quanti_space_x.composeapp.generated.resources.mass
import quanti_space_x.composeapp.generated.resources.overview
import quanti_space_x.composeapp.generated.resources.parameters
import quanti_space_x.composeapp.generated.resources.photos
import quanti_space_x.composeapp.generated.resources.unknown_rocket
import kotlin.math.roundToInt

@Composable
fun RocketDetailRoute(
    onLaunchClick: (Rocket) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RocketDetailViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RocketDetailScreen(
        state = state,
        modifier = modifier,
        onLaunchClick = onLaunchClick,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RocketDetailScreen(
    state: RocketDetailState,
    modifier: Modifier = Modifier,
    onLaunchClick: (Rocket) -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            RocketTopBar(
                onBackClick = onBackClick,
                onLaunchClick = {},
                title = if (state is RocketDetailState.Success) {
                    state.rocket.name
                } else {
                    stringResource(Res.string.unknown_rocket)
                }
            )
        }
    ) { innerPadding ->

        when (state) {

            is RocketDetailState.Loading -> {
                ProgressIndicator()
            }

            is RocketDetailState.Error -> {
                ErrorBox(error = state.errorMessage)
            }

            is RocketDetailState.Success -> {
                val rocket = state.rocket
                val massT = rocket.massKg?.let { it / 1000 }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    item {
                        SubtitleRow(subtitle = stringResource(Res.string.overview))
                    }

                    item {
                        Text(text = rocket.description)
                    }

                    item {
                        SubtitleRow(subtitle = stringResource(Res.string.parameters))
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ParameterBox(
                                value = rocket.heightMeters?.roundToInt().toString(),
                                unit = "m",
                                description = stringResource(Res.string.height),
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            ParameterBox(
                                value = rocket.diameterMeters.toString(),
                                unit = "m",
                                description = stringResource(Res.string.diameter),
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            ParameterBox(
                                value = massT.toString(),
                                unit = "t",
                                description = stringResource(Res.string.mass),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    item {
                        StageBox(stage = rocket.stages[0])
                    }

                    item {
                        StageBox(stage = rocket.stages[1])
                    }

                    item {
                        SubtitleRow(subtitle = stringResource(Res.string.photos))
                    }
                    items(rocket.images) { url ->
                        RocketImage(
                            url = url,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                    }
                }

            }
        }
    }
}