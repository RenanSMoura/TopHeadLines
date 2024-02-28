package io.moura.test.challenge.ctw.domain.model

import io.moura.test.challenge.ctw.domain.exceptions.ProjectExceptions


data class DomainData<T>(val data: T?, val exception: ProjectExceptions?)
