package io.moura.test.challenge.ctw.domain.exceptions

import io.moura.test.challenge.ctw.data.source.network.api.ApiException
import org.junit.Assert.assertEquals
import org.junit.Test

class ExceptionMapperTest {

    @Test
    fun `when code is 400, should return BadRequest error`() {
        assertEquals(
            ExceptionMapper.mapException(
                ApiException(""),
                400
            ),
            ProjectExceptions.BadRequest
        )
    }

    @Test
    fun `when code is 401, should return Unauthorized error`() {
        assertEquals(
            ExceptionMapper.mapException(
                ApiException(""),
                401
            ),
            ProjectExceptions.Unauthorized
        )
    }

    @Test
    fun `when code is 404, should return Forbidden error`() {
        assertEquals(
            ExceptionMapper.mapException(
                ApiException(""),
                403
            ),
            ProjectExceptions.Forbidden
        )
    }

    @Test
    fun `when code is 500, should return InternalError error`() {
        assertEquals(
            ExceptionMapper.mapException(
                ApiException(""),
                500
            ),
            ProjectExceptions.InternalError
        )
    }

    @Test
    fun `when code is 504, should return TimeOut error`() {
        assertEquals(
            ExceptionMapper.mapException(
                ApiException(""),
                504
            ),
            ProjectExceptions.TimeOut
        )
    }

    @Test
    fun `when code is unknown, should return GenericError error`() {
        assertEquals(
            ExceptionMapper.mapException(
                ApiException(""),
                null
            ),
            ProjectExceptions.GenericError
        )
    }

    @Test
    fun `when exception is null, should return GenericError error`() {
        assertEquals(
            ExceptionMapper.mapException(
                null,
                400
            ),
            ProjectExceptions.GenericError
        )
    }
}
