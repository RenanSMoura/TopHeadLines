package io.moura.test.challenge.ctw.data.source.network.model

import com.google.gson.annotations.SerializedName

data class NetworkHeadLineResponse(
    @SerializedName("totalResults")
    val totalResults : Int,
    val articles: List<NetworkHeadLine>
)
