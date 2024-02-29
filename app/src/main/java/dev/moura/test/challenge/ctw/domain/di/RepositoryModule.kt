package dev.moura.test.challenge.ctw.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.moura.test.challenge.ctw.domain.repository.GetHeadLinesRepository
import dev.moura.test.challenge.ctw.domain.repository.GetHeadLinesRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindGetHeadLineRepository(
        getHeadLinesRepositoryImpl: GetHeadLinesRepositoryImpl
    ): GetHeadLinesRepository
}
