package io.moura.test.challenge.ctw.domain.repository

import io.moura.test.challenge.ctw.data.source.network.NewsDataSource
import io.moura.test.challenge.ctw.data.source.network.model.DataResponse
import io.moura.test.challenge.ctw.domain.exceptions.ExceptionMapper
import io.moura.test.challenge.ctw.domain.model.DomainData
import io.moura.test.challenge.ctw.domain.model.HeadLine
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHeadLinesRepositoryImpl @Inject constructor(
    private val newsDataSource: NewsDataSource
) : GetHeadLinesRepository {
    override suspend fun getHeadLines(nextPage: Int) = flow<DomainData<List<HeadLine>>> {
        newsDataSource.loadHeadLines(nextPage).collect { dataResponse ->
            when (dataResponse) {
                is DataResponse.Error -> {
                    emit(
                        DomainData(
                            data = null,
                            exception = ExceptionMapper.mapException(

                                throwable = dataResponse.throwable,
                                code = dataResponse.code,
                            )
                        )
                    )
                }

                is DataResponse.Success -> {
                    emit(
                        DomainData(
                            data = dataResponse.data.articles.map {
                                it.toModel()
                            }.sortedBy { it.publishedAt },
                            exception = null
                        )
                    )
                }
            }
        }
    }.catch {
        emit(
            DomainData(
                data = null,
                exception = ExceptionMapper.mapException(it)
            )
        )
    }
}
