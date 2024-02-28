package io.moura.test.challenge.ctw.data.utils

import java.util.Locale

object LocaleUtils {
    fun getUserLocale(): String? = Locale.getDefault().language

    const val DEFAULT_LOCALE = "us"
}
