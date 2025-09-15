package com.capstone.surfingthegangwon.data.city.di

import com.capstone.surfingthegangwon.core.retrofit.RetrofitModule
import com.capstone.surfingthegangwon.data.city.api.CityRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CityRetrofitModule {

    @Provides
    @Singleton
    fun provideServerCityRetrofitService(
        @RetrofitModule.ServiceApi retrofit: Retrofit
    ): CityRetrofitService {
        return retrofit.create(CityRetrofitService::class.java)
    }
}