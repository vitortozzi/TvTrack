package br.com.tozzilabs.tvtrack.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tozzilabs.tvtrack.data.MovieRepository
import br.com.tozzilabs.tvtrack.data.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailViewState>(DetailViewState.Loading)
    val uiState: StateFlow<DetailViewState> = _uiState

    fun fetchDetails(id: Long) {
        viewModelScope.launch {
            repository.getDetails(id).collect {
                _uiState.value = when (it) {
                    is Resource.Error -> DetailViewState.Error
                    is Resource.Success -> DetailViewState.DetailLoaded(it.getContent())
                }
            }
        }
    }
}