package io.moura.test.challenge.ctw.data.source.network.model

import com.google.gson.annotations.SerializedName
import io.moura.test.challenge.ctw.domain.model.HeadLine
import io.moura.test.challenge.ctw.domain.model.Source
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val EMPTY = ""

data class NetworkHeadLine(
    val author: String?,
    val content: String?,
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("source")
    val source: NetworkSource?,
    val title: String?,
    val url: String?,
    val urlToImage: String,
) {
    fun toModel() = HeadLine(
        author = author ?: EMPTY,
        content = content ?: EMPTY,
        description = description ?: EMPTY,
        publishedAt = publishedAt?.toDate(),
        source = Source(
            id = source?.id ?: EMPTY,
            name = source?.name ?: EMPTY
        ),
        title = title ?: EMPTY,
        url = url ?: EMPTY,
        urlToImage = urlToImage
    )
}

fun String?.toDate(): Date? {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    return format.parse(this)
}
