package io.moura.test.challenge.ctw.data.source.network.api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.moura.test.challenge.ctw.BuildConfig
import io.moura.test.challenge.ctw.data.source.network.api.NewsApi
import io.moura.test.challenge.ctw.data.source.network.api.interceptors.AuthInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = ""

    @Singleton
    @Provides
    fun providesHttpLoginInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    private fun provideAuthInterceptor(): Interceptor = AuthInterceptor()

    @Singleton
    @Provides
    fun providesHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor = provideAuthInterceptor()
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): NewsApi =
        retrofit.create(NewsApi::class.java)
}
