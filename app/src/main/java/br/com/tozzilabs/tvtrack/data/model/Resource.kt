package br.com.tozzilabs.tvtrack.data.model

import retrofit2.HttpException
import retrofit2.Response

sealed class Resource<T> (val data: T? = null, val message: String? = null) {

    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String? = null, data: T? = null): Resource<T>(data, message)
}

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): Resource<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Resource.Success(body)
        } else {
            Resource.Error(message = response.message())
        }
    } catch (e: HttpException) {
        Resource.Error(message = e.message())
    } catch (e: Throwable) {
        Resource.Error(message = e.message)
    }
}

fun <T> Resource<T>.isSuccess(): Boolean {
    return this is Resource.Success && this.data != null
}

fun <T> Resource.Success<T>.getContent(): T {
    return this.data!!
}