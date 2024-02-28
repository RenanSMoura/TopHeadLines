package io.moura.test.challenge.ctw.data.source.network.api

sealed class ApiResponse<out T> {

    data class Success<out T>(
        val data: T
    ) : ApiResponse<T>()

    data class Failure(
        val throwable: Throwable,
        val code: Int
    ) : ApiResponse<Nothing>()
}
