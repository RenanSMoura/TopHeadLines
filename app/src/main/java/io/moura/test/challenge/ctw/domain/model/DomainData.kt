package io.moura.test.challenge.ctw.domain.model

import io.moura.test.challenge.ctw.domain.exceptions.BaseExceptions

data class DomainData<T>(val data: T?, val exception: BaseExceptions? = null)
