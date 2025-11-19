package viewModel

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import repository.FakeAccelerometerRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import cz.quanti.spacex.features.rockets.domain.model.AccelerometerData
import cz.quanti.spacex.features.rockets.domain.model.RocketState
import cz.quanti.spacex.features.rockets.presentation.flightSimulator.FlightSimulatorAction
import cz.quanti.spacex.features.rockets.presentation.flightSimulator.FlightSimulatorState
import cz.quanti.spacex.features.rockets.presentation.flightSimulator.FlightSimulatorViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

class FlightSimulatorViewModelTest {

    @Test
    fun computePitch_isCorrect() = runTest {
        val fakeRepo = FakeAccelerometerRepository()
        val vm = FlightSimulatorViewModel(
            fakeRepo,
            rocketName = "Falcon 9"
        )

        val data = AccelerometerData.Data(0f, 10f, 0f) // y=10, z=0 → 90°

        val result = vm.computePitchDegreesYZ(data)

        assertEquals(90f, result, 1f)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenPitchAboveThreshold_rocketStartsEngine() = runTest {
        val fakeRepo = FakeAccelerometerRepository()
        val dispatcher = StandardTestDispatcher(testScheduler)
        val vm = FlightSimulatorViewModel(fakeRepo, dispatcher, rocketName = "Falcon 9")

        val collected = mutableListOf<FlightSimulatorState>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            vm.state.toList(collected)
        }

        vm.startSensorMonitoring()
        advanceUntilIdle()

        // Emit data with high pitch → ON_GROUND -> STARTING_ENGINE
        fakeRepo.emit(AccelerometerData.Data(0f, 20f, 10f))
        advanceUntilIdle()

        val last = collected.last() as FlightSimulatorState.Running
        assertEquals(RocketState.STARTING_ENGINE, last.rocketState)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun flyingPitchBelowThreshold_lands() = runTest {
        val fakeRepo = FakeAccelerometerRepository()
        val dispatcher = StandardTestDispatcher(testScheduler)
        val vm = FlightSimulatorViewModel(
            fakeRepo, dispatcher,
            rocketName = "Falcon 9"
        )

        // advance through states: ON_GROUND -> STARTING -> LIFTING -> FLYING
        vm.onAction(FlightSimulatorAction.OnUpdateRocketState) // STARTING_ENGINE
        vm.onAction(FlightSimulatorAction.OnUpdateRocketState) // LIFTING
        vm.onAction(FlightSimulatorAction.OnUpdateRocketState) // FLYING


        val initial = vm.state.value as FlightSimulatorState.Running
        assertEquals(RocketState.FLYING, initial.rocketState)

        val collected = mutableListOf<FlightSimulatorState>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            vm.state.toList(collected)
        }

        vm.startSensorMonitoring()
        advanceUntilIdle()

        // pitch < threshold → FLYING → LANDING
        fakeRepo.emit(AccelerometerData.Data(0f, 0f, 10f))
        advanceUntilIdle()

        val last = collected.last() as FlightSimulatorState.Running
        assertEquals(RocketState.LANDING, last.rocketState)
    }
}