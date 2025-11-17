package repository

import cz.quanti.spacex.core.domain.DataError
import cz.quanti.spacex.features.rockets.data.dto.DimensionDto
import cz.quanti.spacex.features.rockets.data.dto.MassDto
import cz.quanti.spacex.features.rockets.data.dto.RocketDto
import cz.quanti.spacex.features.rockets.data.dto.RocketStageDto
import cz.quanti.spacex.features.rockets.data.repository.DefaultRocketRepository
import cz.quanti.spacex.features.rockets.domain.model.RocketStageType
import dataSource.FakeRemoteRocketDataSource
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import cz.quanti.spacex.core.domain.Result

class DefaultRocketRepositoryTest {

    private lateinit var fakeDataSource: FakeRemoteRocketDataSource
    private lateinit var repository: DefaultRocketRepository

    @BeforeTest
    fun setup() {
        fakeDataSource = FakeRemoteRocketDataSource()
        repository = DefaultRocketRepository(fakeDataSource)
    }

    @Test
    fun `getAllRockets returns mapped rockets on success`() = runTest {
        // Given DTO input
        val dto = RocketDto(
            id = "falcon9",
            rocketName = "Falcon 9",
            firstFlight = "2010-06-04",
            description = "desc",
            height = DimensionDto(70.0, 200.0),
            diameter = DimensionDto(3.7, 12.0),
            mass = MassDto(549054, 1207920),
            firstStage = RocketStageDto(false, 9, 385.0, 162),
            secondStage = RocketStageDto(false, 1, 90.0, 397),
            images = listOf("img1", "img2")
        )

        fakeDataSource.rocketsResult = Result.Success(listOf(dto))

        // When
        val result = repository.getAllRockets()

        // Then
        assertTrue(result is Result.Success)
        val rockets = result.data
        assertEquals(1, rockets.size)
        assertEquals("falcon9", rockets[0].id)
        assertEquals("Falcon 9", rockets[0].name)
        assertEquals(LocalDate.parse("2010-06-04"), rockets[0].firstFlight)
    }

    @Test
    fun `getAllRockets returns error when datasource returns error`() = runTest {
        fakeDataSource.rocketsResult = Result.Error(DataError.Remote.SERVER)

        val result = repository.getAllRockets()

        assertTrue(result is Result.Error)
        assertEquals(DataError.Remote.SERVER, result.error)
    }
    @Test
    fun `getRocketById returns mapped RocketDetail on success`() = runTest {
        val dto = RocketDto(
            id = "f9",
            rocketName = "Falcon 9",
            firstFlight = "2010-06-04",
            description = "desc",
            height = DimensionDto(50.0, null),
            diameter = DimensionDto(3.7, null),
            mass = MassDto(549054, 1207920),
            firstStage = RocketStageDto(false, 9, 385.0, 162),
            secondStage = RocketStageDto(false, 1, 90.0, 397),
            images = listOf("img")
        )

        fakeDataSource.rocketDetailResult = Result.Success(dto)

        val result = repository.getRocketById("f9")

        assertTrue(result is Result.Success)
        val rocket = result.data

        assertEquals("f9", rocket.id)
        assertEquals("Falcon 9", rocket.name)
        assertEquals("desc", rocket.description)

        // check stages mapping
        assertEquals(2, rocket.stages.size)
        assertEquals(RocketStageType.FIRST, rocket.stages[0].type)
        assertEquals(9, rocket.stages[0].engines)
        assertEquals(385.0, rocket.stages[0].fuelCapacity)
    }

    @Test
    fun `getRocketById returns error when datasource returns error`() = runTest {
        fakeDataSource.rocketDetailResult = Result.Error(DataError.Remote.NO_INTERNET)

        val result = repository.getRocketById("whatever")

        assertTrue(result is Result.Error)
        assertEquals(DataError.Remote.NO_INTERNET, result.error)
    }
}

