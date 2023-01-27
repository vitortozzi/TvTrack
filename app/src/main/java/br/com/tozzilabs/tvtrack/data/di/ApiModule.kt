package br.com.tozzilabs.tvtrack.data.di

import br.com.tozzilabs.tvtrack.data.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): MovieApiService = retrofit.create(MovieApiService::class.java)

    @Singleton
    @Provides
    fun provideRepository(apiService: MovieApiService): MovieRepository = MovieRepositoryImpl(apiService)

}

//@Module
//@InstallIn(SingletonComponent::class)
//object MovieRepositoryModule {
//
//    @Singleton
//    @Provides
//    fun provideMovieRepository(
//        apiService: MovieApiService
//    ) : MovieRepository {
//        return MovieRepositoryImpl(apiService)
//    }
//}