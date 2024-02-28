package io.moura.test.challenge.ctw.data.source.network

import io.moura.test.challenge.ctw.data.source.network.api.ApiResponse
import io.moura.test.challenge.ctw.data.source.network.api.NewsApi
import io.moura.test.challenge.ctw.data.source.network.api.apiRequestFlow
import io.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLine
import io.moura.test.challenge.ctw.data.utils.LocaleUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RetrofitNewsNetwork @Inject constructor(
    private val newsApi: NewsApi,
    private val localeUtils: LocaleUtils
) : NewsDataSource {
    override fun loadHeadLines(): Flow<Response<List<NetworkHeadLine>>> = flow {
        apiRequestFlow {
            newsApi.getHeadLinesFromCountry(
                localeUtils.getUserLocale() ?: localeUtils.DEFAULT_LOCALE
            )
        }.collect { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Failure -> emit(
                    Response.Error(
                        throwable = apiResponse.throwable,
                        code = apiResponse.code
                    )
                )

                is ApiResponse.Success -> {
                    Response.Success(
                        data = apiResponse.data
                    )
                }
            }
        }
    }
}
