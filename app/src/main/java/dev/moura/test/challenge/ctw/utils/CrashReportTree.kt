package dev.moura.test.challenge.ctw.utils

import timber.log.Timber

// Not a real implementation.
class CrashReportTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        return
    }
}
