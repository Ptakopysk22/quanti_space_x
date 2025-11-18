package viewModel

import cz.quanti.spacex.core.domain.DataError
import cz.quanti.spacex.core.domain.Result
import cz.quanti.spacex.features.rockets.domain.model.RocketDetail
import cz.quanti.spacex.features.rockets.presentation.rocketDetail.RocketDetailState
import cz.quanti.spacex.features.rockets.presentation.rocketDetail.RocketDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import repository.FakeRocketRepository
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@OptIn(ExperimentalCoroutinesApi::class)
class RocketDetailViewModelTest {

    private lateinit var fakeRepository: FakeRocketRepository
    private lateinit var viewModel: RocketDetailViewModel

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeRocketRepository()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init load rocket detail and emits Success state on success`() = runTest {
        // Given
        val rocketId = "1"
        val rocket = RocketDetail(
            id = rocketId,
            name = "Falcon 9",
            description = "description",
            heightMeters = 48.0,
            diameterMeters = 22.0,
            massKg = 53053,
            stages = emptyList(),
            images = emptyList()
        )

        fakeRepository.rocketResult = Result.Success(rocket)

        // When
        viewModel = RocketDetailViewModel(
            fakeRepository,
            rocketId = rocketId
        )

        // allow the init { launch } to run
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertTrue(state is RocketDetailState.Success)
        assertEquals("Falcon 9", state.rocket.name)
        assertEquals(53053, state.rocket.massKg)
        assertEquals(emptyList(), state.rocket.stages)
    }

    @Test
    fun `init emits Error state on failure`() = runTest {
        // Given
        fakeRepository.rocketResult =
            Result.Error(DataError.Remote.NO_INTERNET)

        // When
        viewModel = RocketDetailViewModel(
            rocketRepository = fakeRepository,
            rocketId = "1"
        )

        advanceUntilIdle()

        // Then
        val state = viewModel.state.value

        assertTrue(state is RocketDetailState.Error)
        val message = state.errorMessage.asPlainString()
        assertEquals("error_no_internet", message)
    }
}