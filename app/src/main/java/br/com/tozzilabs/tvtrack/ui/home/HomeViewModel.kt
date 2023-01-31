package br.com.tozzilabs.tvtrack.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tozzilabs.tvtrack.data.MovieRepository
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

    val trendLiveDate = MutableLiveData<HomeViewState>()
    val topRatedLiveData = MutableLiveData<HomeViewState>()

    init {
        loadDiscoverMovies()
    }

    private fun loadDiscoverMovies() {
        viewModelScope.launch {

            trendLiveDate.value = HomeViewState.Loading

            repository.getTopRatedMovies().collect {
                topRatedLiveData.postValue(
                    when (it) {
                        is ApiSuccess -> HomeViewState.TopRatedLoaded(it.data.results)
                        is ApiException -> HomeViewState.Error
                        is ApiError -> HomeViewState.Error
                    }
                )
            }

            repository.getTrendMovies().collect {
                trendLiveDate.postValue(
                    when (it) {
                        is ApiSuccess -> HomeViewState.TrendLoaded(it.data.results)
                        is ApiException -> HomeViewState.Error
                        is ApiError -> HomeViewState.Error
                    }
                )
            }
        }
    }
}

sealed class HomeViewState {
    data class TrendLoaded(val movies: List<Movie>): HomeViewState()
    data class TopRatedLoaded(val movies: List<Movie>): HomeViewState()
    object Loading: HomeViewState()
    object Error: HomeViewState()
}