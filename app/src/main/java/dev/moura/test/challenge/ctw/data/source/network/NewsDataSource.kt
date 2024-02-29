package dev.moura.test.challenge.ctw.data.source.network

import dev.moura.test.challenge.ctw.data.source.network.model.DataResponse
import dev.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLineResponse
import kotlinx.coroutines.flow.Flow

interface NewsDataSource {
    suspend fun loadHeadLines(
        nextPage: Int,
        pageSize: Int = 20
    ): Flow<DataResponse<NetworkHeadLineResponse>>
}
