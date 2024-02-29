package dev.moura.test.challenge.ctw.data.source.network

import dev.moura.test.challenge.ctw.data.source.network.api.ApiResponse
import dev.moura.test.challenge.ctw.data.source.network.api.NewsApi
import dev.moura.test.challenge.ctw.data.source.network.api.apiRequestFlow
import dev.moura.test.challenge.ctw.data.source.network.model.DataResponse
import dev.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLineResponse
import dev.moura.test.challenge.ctw.data.utils.SourceUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RetrofitNewsNetwork @Inject constructor(
    private val newsApi: NewsApi
) : NewsDataSource {
    override suspend fun loadHeadLines(
        nextPage: Int,
        pageSize: Int
    ): Flow<DataResponse<NetworkHeadLineResponse>> = flow {
        apiRequestFlow {
            newsApi.getHeadLinesFromSource(
                sources = SourceUtils.getSource(),
                pageSize = pageSize,
                page = nextPage
            )
        }.collect { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Failure -> emit(
                    DataResponse.Error(
                        throwable = apiResponse.throwable,
                        code = apiResponse.code
                    )
                )

                is ApiResponse.Success -> {
                    emit(
                        DataResponse.Success(
                            data = apiResponse.data
                        )
                    )
                }
            }
        }
    }
}
