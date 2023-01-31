package br.com.tozzilabs.tvtrack.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tozzilabs.tvtrack.data.model.*
import br.com.tozzilabs.tvtrack.interactor.GetDiscoverMoviesUseCase
import br.com.tozzilabs.tvtrack.interactor.model.MovieDiscoveryVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val discoverMoviesUseCase: GetDiscoverMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val uiState : StateFlow<HomeViewState> = _uiState

    init {
        loadDiscoverMovies()
    }

    private fun loadDiscoverMovies() {
        viewModelScope.launch {
            discoverMoviesUseCase.invoke().collect {
                _uiState.value = when(it) {
                    is Resource.Error -> HomeViewState.Error
                    is Resource.Success -> HomeViewState.DiscoverItemsLoaded(it.getContent())
                }
            }
        }
    }
}

sealed class HomeViewState {
    data class DiscoverItemsLoaded(val discover: MovieDiscoveryVO): HomeViewState()
    object Loading: HomeViewState()
    object Error: HomeViewState()
}