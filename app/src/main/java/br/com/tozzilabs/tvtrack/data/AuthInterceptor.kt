package br.com.tozzilabs.tvtrack.data

import br.com.tozzilabs.tvtrack.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val httpUrl = original.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(httpUrl)

        return chain.proceed(requestBuilder.build())
    }
}