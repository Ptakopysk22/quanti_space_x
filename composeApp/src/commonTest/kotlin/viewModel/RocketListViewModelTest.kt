package viewModel

import cz.quanti.spacex.core.domain.DataError
import cz.quanti.spacex.core.domain.Result
import cz.quanti.spacex.features.rockets.domain.model.Rocket
import cz.quanti.spacex.features.rockets.presentation.rocketList.RocketListState
import cz.quanti.spacex.features.rockets.presentation.rocketList.RocketListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import kotlinx.datetime.LocalDate
import repository.FakeRocketRepository
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class RocketListViewModelTest {

    private lateinit var fakeRepository: FakeRocketRepository
    private lateinit var viewModel: RocketListViewModel

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
    fun `init loads rockets and emits Success state on success`() = runTest {
        // Given
        val rockets = listOf(
            Rocket(id = "1", name = "Falcon 9", firstFlight = LocalDate.parse("2010-06-04")),
            Rocket(id = "2", name = "Falcon Heavy", firstFlight = LocalDate.parse("2018-02-06"))
        )

        fakeRepository.rocketsResult = Result.Success(rockets)

        // When
        viewModel = RocketListViewModel(fakeRepository)

        // allow the init { launch } to run
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertTrue(state is RocketListState.Success)
        assertEquals(2, state.rockets.size)
        assertEquals("Falcon 9", state.rockets[0].name)
    }

    @Test
    fun `init emits Error state on failure`() = runTest {
        // Given
        fakeRepository.rocketsResult = Result.Error(DataError.Remote.NO_INTERNET)

        // When
        viewModel = RocketListViewModel(fakeRepository)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertTrue(state is RocketListState.Error)
        val message = state.errorMessage.asPlainString()
        assertEquals("error_no_internet", message)
    }
}