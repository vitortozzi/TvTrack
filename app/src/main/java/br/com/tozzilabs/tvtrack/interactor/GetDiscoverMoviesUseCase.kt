package br.com.tozzilabs.tvtrack.interactor

import br.com.tozzilabs.tvtrack.data.MovieRepository
import br.com.tozzilabs.tvtrack.data.model.Resource
import br.com.tozzilabs.tvtrack.data.model.isSuccess
import br.com.tozzilabs.tvtrack.interactor.model.MovieDiscoveryVO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDiscoverMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun invoke(): Flow<Resource<MovieDiscoveryVO>> = flow {
        coroutineScope {
            val trends = (async { repository.getTrendMovies() }).await()
            val topRated = (async { repository.getTopRatedMovies() }).await()
            if (trends.isSuccess() && topRated.isSuccess()) {
                emit(
                    Resource.Success(
                        MovieDiscoveryVO(
                            trends.data?.results ?: listOf(),
                            topRated.data?.results ?: listOf()
                        )
                    )
                )
            } else {
                emit(Resource.Error(null))
            }
        }
    }
}