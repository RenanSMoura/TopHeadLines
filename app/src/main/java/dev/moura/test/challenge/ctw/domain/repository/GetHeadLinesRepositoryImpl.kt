package dev.moura.test.challenge.ctw.domain.repository

import dev.moura.test.challenge.ctw.data.source.network.model.DataResponse
import dev.moura.test.challenge.ctw.domain.exceptions.ExceptionMapper
import dev.moura.test.challenge.ctw.domain.model.DomainData
import dev.moura.test.challenge.ctw.domain.model.HeadLine
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHeadLinesRepositoryImpl @Inject constructor(
    private val newsDataSource: dev.moura.test.challenge.ctw.data.source.network.NewsDataSource
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
                            }.sortedByDescending { it.publishedAt },
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
