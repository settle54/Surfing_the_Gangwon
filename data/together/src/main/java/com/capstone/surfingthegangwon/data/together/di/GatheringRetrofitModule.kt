package com.capstone.surfingthegangwon.data.together.di

import com.capstone.surfingthegangwon.core.retrofit.RetrofitModule
import com.capstone.surfingthegangwon.data.together.api.GatheringRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GatheringRetrofitModule {

    @Provides
    @Singleton
    fun provideServerGatheringRetrofitService(
        @RetrofitModule.ServiceApi retrofit: Retrofit
    ): GatheringRetrofitService {
        return retrofit.create(GatheringRetrofitService::class.java)
    }
}