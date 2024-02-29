package dev.moura.test.challenge.ctw.domain.repository

import dev.moura.test.challenge.ctw.domain.model.DomainData
import dev.moura.test.challenge.ctw.domain.model.HeadLine
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeGetHeadLinesRepository :
    GetHeadLinesRepository {

    private val headLinesFlow = MutableSharedFlow<DomainData<List<HeadLine>>>()

    override suspend fun getHeadLines(nextPage: Int) = headLinesFlow
    suspend fun emit(response: DomainData<List<HeadLine>>) = headLinesFlow.emit(response)
}
