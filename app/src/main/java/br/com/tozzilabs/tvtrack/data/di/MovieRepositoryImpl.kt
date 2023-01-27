package br.com.tozzilabs.tvtrack.data.di

import br.com.tozzilabs.tvtrack.data.model.ApiResult
import br.com.tozzilabs.tvtrack.data.model.handleApi
import br.com.tozzilabs.tvtrack.model.TrendResponse
import javax.inject.Inject

interface MovieRepository {
    suspend fun getTrendMovies() : ApiResult<TrendResponse>
    suspend fun getUpcomingMovies() : ApiResult<TrendResponse>
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
}