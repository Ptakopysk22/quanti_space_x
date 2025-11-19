package cz.quanti.spacex.features.rockets.presentation.flightSimulator

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.quanti.spacex.features.rockets.domain.model.AccelerometerData
import cz.quanti.spacex.features.rockets.domain.model.RocketState
import cz.quanti.spacex.features.rockets.domain.repository.AccelerometerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.atan2
/**
 * ViewModel responsible for handling accelerometer monitoring and deriving rocket state transitions.
 */
class FlightSimulatorViewModel(
    private val accelerometerRepository: AccelerometerRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val rocketName: String
) : ViewModel() {

    // Backing flow for exposing immutable UI state
    private val _state = MutableStateFlow<FlightSimulatorState>(
        FlightSimulatorState.Running(
            rocketState = RocketState.ON_GROUND,
            rocketName = rocketName
        )
    )
    val state: StateFlow<FlightSimulatorState> = _state

    private val pitchThreshold = 30f

    /**
     * Ensures sensors are stopped when ViewModel is destroyed by the system.
     */
    override fun onCleared() {
        super.onCleared()
        stopSensorMonitoring()
    }

    /**
     * Starts collecting accelerometer updates in background.
     * The caller (Compose UI) controls when this should start.
     */
    fun startSensorMonitoring() {
        viewModelScope.launch(dispatcher) {
            accelerometerRepository.observe().collectLatest { data ->
                when (data) {
                    is AccelerometerData.Data -> processSensorData(data)
                    is AccelerometerData.Error -> handleSensorError(data.message)
                }
            }
        }
    }

    /**
     * Stops accelerometer monitoring manually.
     * Called by UI and by onCleared() as a safety fallback.
     */
    fun stopSensorMonitoring() {
        accelerometerRepository.stop()
    }

    /**
     * Handles valid accelerometer data and performs rocket state transitions
     * based on current pitch angle.
     */
    private fun processSensorData(data: AccelerometerData.Data) {

        // Only process data when we are in Running state
        val current = _state.value
        if (current !is FlightSimulatorState.Running) return

        val pitch = computePitchDegreesYZ(data)

        val newState = when {
            pitch >= pitchThreshold && current.rocketState == RocketState.ON_GROUND ->
                RocketState.STARTING_ENGINE

            pitch < pitchThreshold && current.rocketState == RocketState.FLYING ->
                RocketState.LANDING

            else -> null
        }

        newState?.let { state ->
            _state.update {
                FlightSimulatorState.Running(
                    rocketState = state,
                    rocketName = current.rocketName
                )
            }
        }
    }

    /**
     * Handles sensor hardware errors and transitions the ViewModel
     * into an error state, stopping all monitoring.
     */
    private fun handleSensorError(message: String) {
        _state.update { current ->
            FlightSimulatorState.Error(
                message = message,
                rocketName = current.rocketName
            )
        }

        stopSensorMonitoring()
    }

    /**
     * Calculates pitch angle based on Y/Z accelerometer axes.
     * Extracted for testing.
     */
    @VisibleForTesting
    internal fun computePitchDegreesYZ(data: AccelerometerData.Data): Float {
        return atan2(data.y, data.z) * 180f / PI.toFloat()
    }

    /**
     * Manual UI action that performs deterministic rocket state transitions.
     */
    fun onAction(action: FlightSimulatorAction) {
        when (action) {
            FlightSimulatorAction.OnUpdateRocketState -> updateRocketState()
        }
    }

    private fun updateRocketState() {
        val current = _state.value
        if (current !is FlightSimulatorState.Running) return

        val newState = when (current.rocketState) {
            RocketState.ON_GROUND -> RocketState.STARTING_ENGINE
            RocketState.STARTING_ENGINE -> RocketState.LIFTING
            RocketState.LIFTING -> RocketState.FLYING
            RocketState.FLYING -> RocketState.LANDING
            RocketState.LANDING -> RocketState.STOPPING_ENGINE
            RocketState.STOPPING_ENGINE -> RocketState.ON_GROUND
        }

        _state.value = FlightSimulatorState.Running(
            rocketState = newState,
            rocketName = current.rocketName
        )
    }
}


/** User-driven events */
sealed interface FlightSimulatorAction {
    data object OnUpdateRocketState : FlightSimulatorAction
}

/**
 * UI state split into Running and Error variants.
 * Ensures that error cannot coexist with active rocket data.
 */
sealed interface FlightSimulatorState {
    val rocketName: String

    data class Running(
        override val rocketName: String,
        val rocketState: RocketState
    ) : FlightSimulatorState

    data class Error(
        override val rocketName: String,
        val message: String
    ) : FlightSimulatorState
}