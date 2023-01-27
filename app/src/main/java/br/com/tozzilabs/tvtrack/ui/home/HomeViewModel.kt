package br.com.tozzilabs.tvtrack.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tozzilabs.tvtrack.data.di.MovieRepository
import br.com.tozzilabs.tvtrack.data.model.ApiError
import br.com.tozzilabs.tvtrack.data.model.ApiException
import br.com.tozzilabs.tvtrack.data.model.ApiSuccess
import br.com.tozzilabs.tvtrack.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val viewState = MutableLiveData<HomeViewState>()

    init {
        loadTrends()
        loadComingSoon()
    }

    private fun loadComingSoon() {
        viewModelScope.launch {
            viewState.value = HomeViewState.Loading
            viewState.value = when (val response = repository.getUpcomingMovies()) {
                is ApiSuccess -> HomeViewState.UpcomingLoaded(response.data.results)
                is ApiException -> HomeViewState.Error
                is ApiError -> HomeViewState.Error
            }
        }
    }

    private fun loadTrends() {
        viewModelScope.launch {
            viewState.value = HomeViewState.Loading
            viewState.value = when (val response = repository.getTrendMovies()) {
                is ApiSuccess -> HomeViewState.TrendLoaded(response.data.results)
                is ApiException -> HomeViewState.Error
                is ApiError -> HomeViewState.Error
            }
        }
    }
}

sealed class HomeViewState {
    data class TrendLoaded(val movies: List<Movie>): HomeViewState()
    data class UpcomingLoaded(val movies: List<Movie>): HomeViewState()
    object Loading: HomeViewState()
    object Error: HomeViewState()
}