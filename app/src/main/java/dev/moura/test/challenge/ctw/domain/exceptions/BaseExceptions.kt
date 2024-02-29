package dev.moura.test.challenge.ctw.domain.exceptions

sealed class BaseExceptions(message: String) : Throwable(message) {

    data object GenericError : BaseExceptions("Generic error. Try again latter.")
    data object BadRequest : BaseExceptions("Invalid request.")
    data object Unauthorized : BaseExceptions("Unauthorized.")
    data object Forbidden : BaseExceptions("Forbidden.")
    data object NotFound : BaseExceptions("Not found.")
    data object InternalError : BaseExceptions("Internal error.")

    data object TooManyRequests : BaseExceptions("Too many requests. Try again later.")
    data object TimeOut : BaseExceptions("Time out. Try again.")
}
