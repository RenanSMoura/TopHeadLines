package dev.moura.test.challenge.ctw.data.source.network.api

import dev.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLineResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getHeadLinesFromSource(
        @Query("sources") sources: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): Response<NetworkHeadLineResponse>
}
