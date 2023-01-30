package br.com.tozzilabs.tvtrack.data

import br.com.tozzilabs.tvtrack.model.DiscoveryResponse
import br.com.tozzilabs.tvtrack.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {
    @GET("trending/movie/day")
    suspend fun getTrendMovies(): Response<DiscoveryResponse>
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<DiscoveryResponse>

    @GET("movie/{id}")
    suspend fun getDetails(@Path("id") id: Long): Response<Movie>
}