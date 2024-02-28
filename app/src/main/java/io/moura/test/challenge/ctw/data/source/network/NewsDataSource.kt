package io.moura.test.challenge.ctw.data.source.network

import io.moura.test.challenge.ctw.data.source.network.model.DataResponse
import io.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLine
import kotlinx.coroutines.flow.Flow

interface NewsDataSource {
    fun loadHeadLines(): Flow<DataResponse<List<NetworkHeadLine>>>
}
