package cz.quanti.spacex.features.rockets.presentation.rocketDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.quanti.spacex.core.domain.onError
import cz.quanti.spacex.core.domain.onSuccess
import cz.quanti.spacex.core.presentation.UiText
import cz.quanti.spacex.core.presentation.toUiText
import cz.quanti.spacex.features.rockets.domain.model.RocketDetail
import cz.quanti.spacex.features.rockets.domain.repository.RocketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RocketDetailViewModel(
    private val rocketRepository: RocketRepository,
    private val rocketId: String
) : ViewModel() {

    private val _state = MutableStateFlow<RocketDetailState>(RocketDetailState.Loading)
    val state: StateFlow<RocketDetailState> = _state


    init {
        viewModelScope.launch {
            loadRocket()
        }
    }

    private suspend fun loadRocket() {
        rocketRepository.getRocketById(rocketId).onSuccess { rocket ->
            _state.update { RocketDetailState.Success(rocket) }
        }.onError { error ->
            _state.update { RocketDetailState.Error(error.toUiText()) }
        }
    }

}

sealed interface RocketDetailState {
    data object Loading : RocketDetailState

    data class Error(val errorMessage: UiText) : RocketDetailState

    data class Success(val rocket: RocketDetail) : RocketDetailState
}