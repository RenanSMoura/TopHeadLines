package dev.moura.test.challenge.ctw.data.utils

import dev.moura.test.challenge.ctw.BuildConfig

object SourceUtils {
    fun getSource(): String = BuildConfig.SOURCE_TYPE
}
