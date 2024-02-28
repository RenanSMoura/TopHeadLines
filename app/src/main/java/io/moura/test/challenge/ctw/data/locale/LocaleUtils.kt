package io.moura.test.challenge.ctw.data.locale

import java.util.Locale

object LocaleUtils {
    fun getUserLocale(): String? = Locale.getDefault().language
}

