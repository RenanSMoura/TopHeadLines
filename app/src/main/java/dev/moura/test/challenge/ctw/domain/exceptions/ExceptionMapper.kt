package dev.moura.test.challenge.ctw.domain.exceptions

import dev.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.BadRequest
import dev.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.Forbidden
import dev.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.GenericError
import dev.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.InternalError
import dev.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.NotFound
import dev.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.TimeOut
import dev.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.TooManyRequests
import dev.moura.test.challenge.ctw.domain.exceptions.BaseExceptions.Unauthorized

object ExceptionMapper {

    fun mapException(throwable: Throwable?, code: Int? = null): BaseExceptions {
        return when (throwable) {
            is dev.moura.test.challenge.ctw.data.source.network.api.ApiException -> mapApiException(code)
            else -> GenericError
        }
    }

    private fun mapApiException(code: Int?): BaseExceptions {
        return when (code) {
            400 -> BadRequest
            401 -> Unauthorized
            403 -> Forbidden
            404 -> NotFound
            429 -> TooManyRequests
            500 -> InternalError
            504 -> TimeOut
            else -> GenericError
        }
    }
}
