package io.moura.test.challenge.ctw.data.source.network

import io.moura.test.challenge.ctw.data.source.network.model.DataResponse
import io.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLine
import io.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLineResponse
import kotlinx.coroutines.flow.Flow

interface NewsDataSource {
    suspend fun loadHeadLines(
        nextPage: Int,
        pageSize: Int = 20
    ): Flow<DataResponse<NetworkHeadLineResponse>>
}
