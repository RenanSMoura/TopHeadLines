package io.moura.test.challenge.ctw.data.source.network

import io.moura.test.challenge.ctw.data.source.network.api.ApiResponse
import io.moura.test.challenge.ctw.data.source.network.api.NewsApi
import io.moura.test.challenge.ctw.data.source.network.api.apiRequestFlow
import io.moura.test.challenge.ctw.data.source.network.model.DataResponse
import io.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLineResponse
import io.moura.test.challenge.ctw.data.utils.LocaleUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RetrofitNewsNetwork @Inject constructor(
    private val newsApi: NewsApi,
    private val localeUtils: LocaleUtils
) : NewsDataSource {
    override suspend fun loadHeadLines(
        nextPage: Int,
        pageSize: Int
    ): Flow<DataResponse<NetworkHeadLineResponse>> = flow {
        apiRequestFlow {
            newsApi.getHeadLinesFromCountry(
                countryId = localeUtils.getUserLocale() ?: localeUtils.DEFAULT_LOCALE,
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
