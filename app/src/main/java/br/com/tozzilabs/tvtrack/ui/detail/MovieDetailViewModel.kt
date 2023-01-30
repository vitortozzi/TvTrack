package br.com.tozzilabs.tvtrack.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tozzilabs.tvtrack.data.MovieRepository
import br.com.tozzilabs.tvtrack.data.model.ApiError
import br.com.tozzilabs.tvtrack.data.model.ApiException
import br.com.tozzilabs.tvtrack.data.model.ApiSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    val detailLiveData = MutableLiveData<DetailViewState>()

    fun fetchDetails(id: Long) {
        viewModelScope.launch {
            detailLiveData.value = DetailViewState.Loading
            repository.getDetails(id).collect {
                detailLiveData.value = when (it) {
                    is ApiError -> DetailViewState.Error
                    is ApiException -> DetailViewState.Error
                    is ApiSuccess -> DetailViewState.DetailLoaded(it.data)
                }
            }
        }
    }
}