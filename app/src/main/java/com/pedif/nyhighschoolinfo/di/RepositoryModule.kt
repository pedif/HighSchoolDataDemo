package com.pedif.nyhighschoolinfo.di

import com.pedif.nyhighschoolinfo.data.repository.HighSchoolRepository
import com.pedif.nyhighschoolinfo.data.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHighSchoolRepository(
        highSchoolRepository: HighSchoolRepository
    ):Repository
}