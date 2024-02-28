package io.moura.test.challenge.ctw.domain.exceptions

sealed class ProjectExceptions(message: String) : Throwable(message) {

    data object GenericError : ProjectExceptions("Generic error. Try again latter.")
    data object BadRequest : ProjectExceptions("Invalid request.")
    data object Unauthorized : ProjectExceptions("Unauthorized.")
    data object Forbidden : ProjectExceptions("Forbidden.")
    data object NotFound : ProjectExceptions("Not found.")
    data object InternalError : ProjectExceptions("Internal error.")
    data object TimeOut : ProjectExceptions("Time out. Try again.")
}
