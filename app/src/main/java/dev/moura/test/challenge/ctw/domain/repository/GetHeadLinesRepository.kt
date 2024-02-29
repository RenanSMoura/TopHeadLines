package dev.moura.test.challenge.ctw.domain.repository

import dev.moura.test.challenge.ctw.domain.model.DomainData
import dev.moura.test.challenge.ctw.domain.model.HeadLine
import kotlinx.coroutines.flow.Flow

interface GetHeadLinesRepository {
    suspend fun getHeadLines(nextPage: Int): Flow<DomainData<List<HeadLine>>>
}
