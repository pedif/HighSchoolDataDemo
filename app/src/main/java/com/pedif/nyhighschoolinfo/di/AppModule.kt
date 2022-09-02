package com.pedif.nyhighschoolinfo.di

import com.pedif.nyhighschoolinfo.common.Constants
import com.pedif.nyhighschoolinfo.data.network.HighSchoolApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHighSchoolApi(): HighSchoolApi{
        return  Retrofit.Builder()
            .baseUrl(Constants.HIGH_SCHOOL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HighSchoolApi::class.java)
    }


}