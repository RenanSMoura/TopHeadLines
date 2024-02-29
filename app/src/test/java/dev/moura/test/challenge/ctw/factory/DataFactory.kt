package dev.moura.test.challenge.ctw.factory

import dev.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLine
import dev.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLineResponse
import dev.moura.test.challenge.ctw.data.source.network.model.NetworkSource
import dev.moura.test.challenge.ctw.domain.model.HeadLine
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.util.UUID

fun generateRandomHeadLines(numArticles: Int): List<HeadLine> {
    val articles = mutableListOf<HeadLine>()
    repeat(numArticles) {
        articles.add(generateRandomNetworkHeadLine().toModel())
    }
    return articles
}

fun generateRandomNetworkHeadLineResponse(numArticles: Int): NetworkHeadLineResponse {
    val articles = mutableListOf<NetworkHeadLine>()
    repeat(numArticles) {
        articles.add(generateRandomNetworkHeadLine())
    }
    return NetworkHeadLineResponse(numArticles, articles)
}

fun generateRandomNetworkHeadLine() = NetworkHeadLine(
    author = UUID.randomUUID().toString(),
    content = UUID.randomUUID().toString(),
    description = UUID.randomUUID().toString(),
    publishedAt = UUID.randomUUID().toString(),
    source = generateRandomNetworkSource(),
    title = UUID.randomUUID().toString(),
    url = UUID.randomUUID().toString(),
    urlToImage = UUID.randomUUID().toString()
)

fun generateRandomNetworkSource() = NetworkSource(
    id = UUID.randomUUID().toString(),
    name = UUID.randomUUID().toString()
)

fun <T> createRetrofitErrorResponse(code: Int, message: String): Response<T> {
    return Response.error(
        message.toResponseBody(),
        okhttp3.Response.Builder().request(Request.Builder().url("http://localhost/").build())
            .code(404).message(message).protocol(Protocol.HTTP_1_1).build()
    )
}
