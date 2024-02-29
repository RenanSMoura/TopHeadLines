package io.moura.test.challenge.ctw.domain.exceptions

import io.moura.test.challenge.ctw.data.source.network.api.ApiException
import io.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.BadRequest
import io.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.Forbidden
import io.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.GenericError
import io.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.InternalError
import io.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.NotFound
import io.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.TimeOut
import io.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.Unauthorized

object ExceptionMapper {

    fun mapException(throwable: Throwable?, code: Int? = null): BaseExceptions {
        return when (throwable) {
            is ApiException -> mapApiException(code)
            else -> GenericError
        }
    }

    private fun mapApiException(code: Int?): BaseExceptions {
        return when (code) {
            400 -> BadRequest
            401 -> Unauthorized
            403 -> Forbidden
            404 -> NotFound
            500 -> InternalError
            504 -> TimeOut
            else -> GenericError
        }
    }
}
