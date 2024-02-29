package dev.moura.test.challenge.ctw.data.source.network

import app.cash.turbine.test
import dev.moura.test.challenge.ctw.data.source.network.api.ApiException
import dev.moura.test.challenge.ctw.data.source.network.api.NewsApi
import dev.moura.test.challenge.ctw.data.source.network.model.DataResponse
import dev.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLineResponse
import dev.moura.test.challenge.ctw.factory.createRetrofitErrorResponse
import dev.moura.test.challenge.ctw.factory.generateRandomNetworkHeadLineResponse
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RetrofitNewsNetworkTest {

    private lateinit var sut: NewsDataSource
    private lateinit var newsApi: NewsApi

    @Before
    fun setUp() {
        newsApi = mockk<NewsApi>()
        sut = RetrofitNewsNetwork(
            newsApi = newsApi
        )
    }

    @Test
    fun `when api return success, check if flow was emitted with correct data`() = runTest {
        val articleResponse = generateRandomNetworkHeadLineResponse(5)
        val nextPage = 1
        val pageSize = articleResponse.articles.size
        coEvery {
            newsApi.getHeadLinesFromSource(
                sources = any(),
                page = nextPage,
                pageSize = pageSize
            )
        } returns Response.success(articleResponse)

        sut.loadHeadLines(nextPage, pageSize).test {
            val response = awaitItem() as DataResponse.Success
            response.data.articles.forEach { responseArticle ->
                articleResponse.articles.first { it.description == responseArticle.description }
                    .let { actual ->
                        assertEquals(responseArticle.author, actual.author)
                        assertEquals(responseArticle.content, actual.content)
                        assertEquals(responseArticle.description, actual.description)
                        assertEquals(responseArticle.publishedAt, actual.publishedAt)
                        assertEquals(responseArticle.source?.id, actual.source?.id)
                        assertEquals(responseArticle.source?.name, actual.source?.name)
                        assertEquals(responseArticle.title, actual.title)
                        assertEquals(responseArticle.url, actual.url)
                        assertEquals(responseArticle.urlToImage, actual.urlToImage)
                    }
            }
            awaitComplete()
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when api returns error, check if exception`() = runTest {
        val code = 404
        val errorMessage = "Not Found"
        val nextPage = 1
        val pageSize = 5
        val errorResponse = createRetrofitErrorResponse<NetworkHeadLineResponse>(code, errorMessage)
        coEvery {
            newsApi.getHeadLinesFromSource(
                sources = any(),
                pageSize = pageSize,
                page = nextPage
            )
        } returns errorResponse

        sut.loadHeadLines(nextPage, pageSize).test {
            val response = awaitItem() as DataResponse.Error
            assertEquals(response.code, code)
            assert(response.throwable is ApiException)

            awaitComplete()
            ensureAllEventsConsumed()
        }
    }

    @Before
    fun tearDown() {
        unmockkAll()
    }
}
