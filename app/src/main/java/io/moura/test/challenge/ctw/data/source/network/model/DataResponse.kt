package io.moura.test.challenge.ctw.data.source.network.model

sealed interface DataResponse<out T> {

    data class Success<T>(val data: T) : DataResponse<T>

    data class Error(
        val throwable: Throwable? = null,
        val code: Int? = null
    ) : DataResponse<Nothing>
}
