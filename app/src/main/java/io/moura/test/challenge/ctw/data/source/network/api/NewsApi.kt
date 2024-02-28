package io.moura.test.challenge.ctw.data.source.network.api

import io.moura.test.challenge.ctw.data.source.network.model.NetworkHeadLineResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApi {
    @GET("top-headlines?country={id}")
    suspend fun getHeadLinesFromCountry(
        @Path("id") countryId: String,
    ): Response<NetworkHeadLineResponse>
}
