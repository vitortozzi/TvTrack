package br.com.tozzilabs.tvtrack.interactor

import br.com.tozzilabs.tvtrack.data.MovieRepository
import br.com.tozzilabs.tvtrack.data.model.Resource
import br.com.tozzilabs.tvtrack.data.model.isSuccess
import br.com.tozzilabs.tvtrack.interactor.model.MovieDiscoveryVO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetDiscoverMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun invoke(): Flow<Resource<MovieDiscoveryVO>> = flow {
        coroutineScope {
            val trendResponse = repository.getTrendMovies()
            val topRatedResponse = repository.getTopRatedMovies()
            trendResponse.combine(topRatedResponse) { trend, topRated ->
                if (trend.isSuccess() && topRated.isSuccess()) {
                    Resource.Success(
                        MovieDiscoveryVO(
                            trend.data?.results ?: listOf(),
                            topRated.data?.results ?: listOf()
                        )
                    )
                } else {
                    Resource.Error(null)
                }
            }.collect {
                emit(it)
            }
        }
    }
}
