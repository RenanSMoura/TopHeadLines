package io.moura.test.challenge.ctw.domain.exceptions

import io.moura.test.challenge.ctw.data.source.network.api.ApiException
import io.moura.test.challenge.ctw.domain.exceptions.ProjectExceptions.BadRequest
import io.moura.test.challenge.ctw.domain.exceptions.ProjectExceptions.Forbidden
import io.moura.test.challenge.ctw.domain.exceptions.ProjectExceptions.GenericError
import io.moura.test.challenge.ctw.domain.exceptions.ProjectExceptions.InternalError
import io.moura.test.challenge.ctw.domain.exceptions.ProjectExceptions.NotFound
import io.moura.test.challenge.ctw.domain.exceptions.ProjectExceptions.TimeOut
import io.moura.test.challenge.ctw.domain.exceptions.ProjectExceptions.Unauthorized

object ExceptionMapper {

    fun mapException(throwable: Throwable?, code: Int? = null): ProjectExceptions {
        return when (throwable) {
            is ApiException -> mapApiException(code)
            else -> GenericError
        }
    }


    private fun mapApiException(code: Int?): ProjectExceptions {
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
