package io.moura.test.challenge.ctw.domain.repository

import io.moura.test.challenge.ctw.domain.model.DomainData
import kotlinx.coroutines.flow.Flow

interface GetHeadLinesRepository<T> {
    suspend fun getHeadLines(): Flow<DomainData<T>>
}
