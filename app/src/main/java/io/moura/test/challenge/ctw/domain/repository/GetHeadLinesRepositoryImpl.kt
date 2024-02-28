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
) : GetHeadLinesRepository<List<HeadLine>> {
    override suspend fun getHeadLines() = flow<DomainData<List<HeadLine>>> {
        newsDataSource.loadHeadLines().collect { dataResponse ->
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
                            data = dataResponse.data.map {
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
