package cz.quanti.spacex.features.rockets.presentation.rocketList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.quanti.spacex.core.domain.onError
import cz.quanti.spacex.core.domain.onSuccess
import cz.quanti.spacex.core.presentation.UiText
import cz.quanti.spacex.core.presentation.toUiText
import cz.quanti.spacex.features.rockets.domain.model.Rocket
import cz.quanti.spacex.features.rockets.domain.repository.RocketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RocketListViewModel(
    private val rocketRepository: RocketRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<RocketListState>(RocketListState.Loading)
    val state: StateFlow<RocketListState> = _state


    init {
        viewModelScope.launch {
            loadRockets()
        }
    }

    private suspend fun loadRockets() {
        rocketRepository.getAllRockets().onSuccess { rockets ->
            _state.update { RocketListState.Success(rockets) }
        }.onError { error ->
            _state.update { RocketListState.Error(error.toUiText()) }
        }
    }

}

sealed interface RocketListState {
    data object Loading : RocketListState

    data class Error(val errorMessage: UiText) : RocketListState

    data class Success(val rockets: List<Rocket>) : RocketListState
}