package io.moura.test.challenge.ctw.data.source.network

import io.moura.test.challenge.ctw.data.source.network.model.DataResponse
import io.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLine
import io.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLineResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeRetrofitNewsNetwork : NewsDataSource {
    private val headLinesFlow = MutableSharedFlow<DataResponse<List<NetworkHeadLine>>>()
    suspend fun emit(response: DataResponse<List<NetworkHeadLine>>) = headLinesFlow.emit(response)
    override suspend fun loadHeadLines(
        nextPage: Int,
        pageSize: Int
    ): Flow<DataResponse<NetworkHeadLineResponse>> {
        TODO("Not yet implemented")
    }

}
