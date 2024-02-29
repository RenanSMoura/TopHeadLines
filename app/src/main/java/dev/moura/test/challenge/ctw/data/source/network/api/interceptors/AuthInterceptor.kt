package dev.moura.test.challenge.ctw.data.source.network.api.interceptors

import dev.moura.test.challenge.ctw.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Api-Key", BuildConfig.API_KEY)
            .build()
        return chain.proceed(request)
    }
}
