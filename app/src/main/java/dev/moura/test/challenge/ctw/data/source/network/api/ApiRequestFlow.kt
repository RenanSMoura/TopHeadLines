package dev.moura.test.challenge.ctw.data.source.network.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> apiRequestFlow(
    call: suspend () -> Response<T>
): Flow<ApiResponse<T>> = flow {
    val response = call()
    try {
        if (response.isSuccessful) {
            response.body()?.let { data ->
                emit(ApiResponse.Success(data))
            }
        } else {
            response.errorBody()?.let { error ->
                emit(
                    ApiResponse.Failure(
                        throwable = ApiException(
                            error.charStream().toString()
                        ),
                        code = response.code()
                    )
                )
            }
        }
    } catch (e: Exception) {
        emit(
            ApiResponse.Failure(
                throwable = e,
                code = 500
            )
        )
    }
}
