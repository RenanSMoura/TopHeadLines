package io.moura.test.challenge.ctw.data.source.network

import io.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLine
import io.moura.test.challenge.ctw.data.source.network.model.DataResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeRetrofitNewsNetwork : NewsDataSource {
    private val headLinesFlow = MutableSharedFlow<DataResponse<List<NetworkHeadLine>>>()
    override fun loadHeadLines(): Flow<DataResponse<List<NetworkHeadLine>>> = headLinesFlow
    suspend fun emit(response: DataResponse<List<NetworkHeadLine>>) = headLinesFlow.tryEmit(response)
}
