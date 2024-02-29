package io.moura.test.challenge.ctw.domain.repository

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import io.moura.test.challenge.ctw.data.source.network.NewsDataSource
import io.moura.test.challenge.ctw.data.source.network.model.DataResponse
import io.moura.test.challenge.ctw.data.source.network.model.toDate
import io.moura.test.challenge.ctw.domain.exceptions.BaseExceptions
import io.moura.test.challenge.ctw.domain.exceptions.ExceptionMapper
import io.moura.test.challenge.ctw.factory.generateRandomNetworkHeadLineResponse
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetHeadLinesRepositoryImplTest {

    private lateinit var sut: GetHeadLinesRepository
    private lateinit var newsDataSource: NewsDataSource

    @Before
    fun setUp() {
        newsDataSource = mockk()
        sut = GetHeadLinesRepositoryImpl(newsDataSource)
    }

    @Test
    fun `When fetch data from data source, should return a valida data`() = runTest {
        val networkData = generateRandomNetworkHeadLineResponse(5)
        val nextPage = 1
        coEvery { newsDataSource.loadHeadLines(nextPage) } returns flow {
            emit(DataResponse.Success(networkData))
        }

        sut.getHeadLines(nextPage).test {
            val response = awaitItem()
            response.data?.forEach { headLine ->
                networkData.articles.first { it.content == headLine.content }.let { actual ->
                    assertEquals(headLine.author, actual.author)
                    assertEquals(headLine.content, actual.content)
                    assertEquals(headLine.description, actual.description)
                    assertEquals(headLine.publishedAt, actual.publishedAt.toDate())
                    assertEquals(headLine.source.id, actual.source?.id)
                    assertEquals(headLine.source.name, actual.source?.name)
                    assertEquals(headLine.title, actual.title)
                    assertEquals(headLine.url, actual.url)
                    assertEquals(headLine.urlToImage, actual.urlToImage)
                }
            }

            awaitComplete()
        }
    }

    @Test
    fun `When fetch data from data source, ensure that headlines are organized by date asc`() =
        runTest {
            val nextPage = 1
            val networkData = generateRandomNetworkHeadLineResponse(5)
            coEvery { newsDataSource.loadHeadLines(nextPage) } returns flow {
                emit(DataResponse.Success(networkData))
            }

            sut.getHeadLines(nextPage).test {
                val response = awaitItem()
                val dates = response.data?.mapNotNull { it.publishedAt } ?: emptyList()
                assertTrue(dates.zipWithNext { a, b -> a <= b }.all { it })
                awaitComplete()
            }
        }

    @Test
    fun `when fetch data and an error happen, should return an generic exception`() = runTest {
        val error = Exception("error")
        val code = 404
        val nextPage = 1
        coEvery { newsDataSource.loadHeadLines(nextPage) } returns flow {
            emit(
                DataResponse.Error(
                    throwable = error,
                    code = code
                )
            )
        }

        sut.getHeadLines(nextPage).test {
            val response = awaitItem()
            assertEquals(response.exception, ExceptionMapper.mapException(error, code))
            assertTrue(response.exception is BaseExceptions.GenericError)
            awaitComplete()
        }
    }

    @Test
    fun `when fetch data throws a exception, catch block should be called and handle the exception`() =
        runTest {
            val nextPage = 1
            val error = Exception("error")
            coEvery { newsDataSource.loadHeadLines(nextPage) } throws error

            sut.getHeadLines(nextPage).test {
                val response = awaitItem()
                assertEquals(response.exception, ExceptionMapper.mapException(error))
                assertTrue(response.exception is BaseExceptions.GenericError)
                awaitComplete()
            }
        }
}
