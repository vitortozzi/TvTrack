package br.com.tozzilabs.tvtrack.data.di

import br.com.tozzilabs.tvtrack.model.Movie
import br.com.tozzilabs.tvtrack.model.TrendResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {
    @GET("trending/movie/day")
    suspend fun getTrendMovies(): Response<TrendResponse>
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Response<TrendResponse>

    @GET("movie/{id}")
    suspend fun getDetails(@Path("id") id: Long): Response<Movie>
}