package br.com.tozzilabs.tvtrack.data

import br.com.tozzilabs.tvtrack.data.model.Resource
import br.com.tozzilabs.tvtrack.data.model.handleApi
import br.com.tozzilabs.tvtrack.data.model.DiscoveryResponse
import br.com.tozzilabs.tvtrack.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MovieRepository {
    suspend fun getTrendMovies() : Flow<Resource<DiscoveryResponse>>
    suspend fun getTopRatedMovies() : Flow<Resource<DiscoveryResponse>>
    suspend fun getDetails(id: Long): Flow<Resource<Movie>>
}

class MovieRepositoryImplToTest @Inject constructor(
    private val apiService: MovieApiService
) : MovieRepository {

    override suspend fun getTrendMovies(): Flow<Resource<DiscoveryResponse>> = flow {
       emit(handleApi { apiService.getTrendMovies() })
    }


    override suspend fun getTopRatedMovies(): Flow<Resource<DiscoveryResponse>> = flow {
         emit(handleApi { apiService.getTopRatedMovies() })
    }

    override suspend fun getDetails(id: Long): Flow<Resource<Movie>> = flow {
        emit(handleApi { apiService.getDetails(id) })
    }

}