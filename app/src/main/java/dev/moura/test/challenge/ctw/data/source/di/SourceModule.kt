package dev.moura.test.challenge.ctw.data.source.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.moura.test.challenge.ctw.data.source.network.NewsDataSource
import dev.moura.test.challenge.ctw.data.source.network.RetrofitNewsNetwork

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    abstract fun bindNewsDataSource(
        retrofitNewsNetwork: RetrofitNewsNetwork
    ): NewsDataSource
}
