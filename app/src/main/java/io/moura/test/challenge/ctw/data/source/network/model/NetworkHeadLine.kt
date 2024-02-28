package io.moura.test.challenge.ctw.data.source.network.model

import com.google.gson.annotations.SerializedName

data class NetworkHeadLine(
    val author: String?,
    val content: String?,
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("source")
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String,
)
