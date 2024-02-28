package io.moura.test.challenge.ctw.data.source.network

sealed interface Response<out T> {

    data class Success<T>(val data: T) : Response<T>

    data class Error(
        val throwable: Throwable? = null,
        val code: Int? = null
    ) : Response<Nothing>
}
