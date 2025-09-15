package com.capstone.surfingthegangwon.data.sessionreading.di

import com.capstone.surfingthegangwon.core.retrofit.RetrofitModule
import com.capstone.surfingthegangwon.data.sessionreading.api.SessionReadingRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionReadingRetrofitModule {

    @Provides
    @Singleton
    fun provideServerGatheringRetrofitService(
        @RetrofitModule.ServiceApi retrofit: Retrofit
    ): SessionReadingRetrofitService {
        return retrofit.create(SessionReadingRetrofitService::class.java)
    }
}