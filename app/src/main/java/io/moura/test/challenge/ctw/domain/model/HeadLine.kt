package io.moura.test.challenge.ctw.domain.model

import java.util.Date

data class HeadLine(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: Date?,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)
