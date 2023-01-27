package br.com.tozzilabs.tvtrack.data.di

import br.com.tozzilabs.tvtrack.model.TrendResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieApiService {
    @GET("trending/movie/day")
    suspend fun getTrendMovies(): Response<TrendResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Response<TrendResponse>
}