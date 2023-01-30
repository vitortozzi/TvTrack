package br.com.tozzilabs.tvtrack.data

import br.com.tozzilabs.tvtrack.data.model.ApiResult
import br.com.tozzilabs.tvtrack.data.model.handleApi
import br.com.tozzilabs.tvtrack.model.DiscoveryResponse
import br.com.tozzilabs.tvtrack.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MovieRepository {
    suspend fun getTrendMovies() : Flow<ApiResult<DiscoveryResponse>>
    suspend fun getTopRatedMovies() : Flow<ApiResult<DiscoveryResponse>>
    suspend fun getDetails(id: Long): Flow<ApiResult<Movie>>
}

class MovieRepositoryImplToTest @Inject constructor(
    private val apiService: MovieApiService
) : MovieRepository {

    override suspend fun getTrendMovies(): Flow<ApiResult<DiscoveryResponse>> = flow {
        emit(handleApi { apiService.getTrendMovies() })
    }

    override suspend fun getTopRatedMovies(): Flow<ApiResult<DiscoveryResponse>> = flow {
        emit(handleApi { apiService.getTopRatedMovies()})
    }

    override suspend fun getDetails(id: Long): Flow<ApiResult<Movie>> = flow {
        emit(handleApi { apiService.getDetails(id) })
    }
}