package br.com.tozzilabs.tvtrack.data.di

import br.com.tozzilabs.tvtrack.data.model.ApiResult
import br.com.tozzilabs.tvtrack.data.model.handleApi
import br.com.tozzilabs.tvtrack.model.Movie
import br.com.tozzilabs.tvtrack.model.TrendResponse
import javax.inject.Inject

interface MovieRepository {
    suspend fun getTrendMovies() : ApiResult<TrendResponse>
    suspend fun getUpcomingMovies() : ApiResult<TrendResponse>
    suspend fun getDetails(id: Long): ApiResult<Movie>
}

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieRepository {

    override suspend fun getTrendMovies(): ApiResult<TrendResponse> {
        return handleApi { apiService.getTrendMovies() }
    }

    override suspend fun getUpcomingMovies(): ApiResult<TrendResponse> {
        return handleApi { apiService.getUpcomingMovies() }
    }

    override suspend fun getDetails(id: Long): ApiResult<Movie> {
        return handleApi { apiService.getDetails(id) }
    }
}