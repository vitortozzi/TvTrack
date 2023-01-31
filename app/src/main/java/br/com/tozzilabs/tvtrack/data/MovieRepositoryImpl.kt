package br.com.tozzilabs.tvtrack.data

import br.com.tozzilabs.tvtrack.data.model.Resource
import br.com.tozzilabs.tvtrack.data.model.handleApi
import br.com.tozzilabs.tvtrack.data.model.DiscoveryResponse
import br.com.tozzilabs.tvtrack.data.model.Movie
import javax.inject.Inject

interface MovieRepository {
    suspend fun getTrendMovies() : Resource<DiscoveryResponse>
    suspend fun getTopRatedMovies() : Resource<DiscoveryResponse>
    suspend fun getDetails(id: Long): Resource<Movie>
}

class MovieRepositoryImplToTest @Inject constructor(
    private val apiService: MovieApiService
) : MovieRepository {

    override suspend fun getTrendMovies(): Resource<DiscoveryResponse> {
        return (handleApi { apiService.getTrendMovies() })
    }

    override suspend fun getTopRatedMovies(): Resource<DiscoveryResponse> {
        return handleApi { apiService.getTopRatedMovies() }
    }

    override suspend fun getDetails(id: Long): Resource<Movie> {
        return handleApi { apiService.getDetails(id) }
    }

}