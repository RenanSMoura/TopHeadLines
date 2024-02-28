package io.moura.test.challenge.ctw.data.source.network.api.interceptors

import io.moura.test.challenge.ctw.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Api-Key", BuildConfig.BUILD_TYPE)
            .build()
        return chain.proceed(request)
    }
}
