package cz.quanti.spacex.features.rockets.presentation.flightSimulator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.quanti.spacex.core.presentation.ErrorBox
import cz.quanti.spacex.features.rockets.domain.model.RocketState
import cz.quanti.spacex.features.rockets.presentation.components.RocketTopBar
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import quanti_space_x.composeapp.generated.resources.Res
import quanti_space_x.composeapp.generated.resources.launch
import quanti_space_x.composeapp.generated.resources.launch_before_start_description
import quanti_space_x.composeapp.generated.resources.launch_successful

@Composable
fun FlightSimulatorRoute(
    modifier: Modifier = Modifier,
    viewModel: FlightSimulatorViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.startSensorMonitoring() }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopSensorMonitoring()
        }
    }

    FlightSimulatorScreen(
        state = state,
        modifier = modifier,
        onAction = viewModel::onAction,
        onBackClick = onBackClick
    )
}

@Composable
fun FlightSimulatorScreen(
    state: FlightSimulatorState,
    onAction: (FlightSimulatorAction) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    // UI constants (visual only)
    val offsetGround = 0f
    val offsetSky = -500f
    //correction of flying rocket image
    val offsetCorrection = 88f
    val imageModifier = Modifier
        .fillMaxHeight(0.45f)
        .aspectRatio(0.45f)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            RocketTopBar(
                onBackClick = onBackClick,
                backClickText = state.rocketName,
                onLaunchClick = {},
                title = stringResource(Res.string.launch),
                isLaunchVisible = false
            )
        }
    ) { innerPadding ->

        when (state) {

            is FlightSimulatorState.Error -> {
                ErrorBox(errorText = state.message)
            }

            is FlightSimulatorState.Running -> {

                Column(
                    Modifier.fillMaxSize().padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    when (state.rocketState) {
                        RocketState.ON_GROUND, RocketState.FLYING -> {
                            ConstantRocketImage(
                                rocketState = state.rocketState,
                                offsetSky = offsetSky,
                                offsetGround = offsetGround,
                                offsetCorrection = offsetCorrection,
                                modifier = imageModifier
                            )
                        }

                        RocketState.STARTING_ENGINE, RocketState.STOPPING_ENGINE -> {
                            StartStopEngineOperation(
                                modifier = imageModifier,
                                isLiftingOff = (state.rocketState == RocketState.STARTING_ENGINE),
                                offsetGround = offsetGround,
                                offsetCorrection = offsetCorrection,
                                onOperationFinished = { onAction(FlightSimulatorAction.OnUpdateRocketState) }
                            )
                        }

                        RocketState.LIFTING, RocketState.LANDING -> {
                            LiftingLandingOperation(
                                modifier = imageModifier,
                                onOperationFinished = { onAction(FlightSimulatorAction.OnUpdateRocketState) },
                                startAnimationPosition = if (state.rocketState == RocketState.LIFTING) offsetGround else offsetSky,
                                endAnimationPosition = if (state.rocketState == RocketState.LIFTING) offsetSky else offsetGround,
                                offsetCorrection = offsetCorrection
                            )
                        }
                    }
                    Text(
                        text = stringResource(
                            if (state.rocketState == RocketState.FLYING)
                                Res.string.launch_successful
                            else
                                Res.string.launch_before_start_description
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(30.dp)
                    )
                }
            }
        }
    }
}