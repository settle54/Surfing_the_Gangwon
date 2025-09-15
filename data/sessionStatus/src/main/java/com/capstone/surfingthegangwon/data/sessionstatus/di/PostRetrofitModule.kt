package com.capstone.surfingthegangwon.data.sessionstatus.di

import com.capstone.surfingthegangwon.core.retrofit.RetrofitModule
import com.capstone.surfingthegangwon.data.sessionstatus.api.PostRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostRetrofitModule {

    @Provides
    @Singleton
    fun provideServerPostRetrofitService(
        @RetrofitModule.ServiceApi retrofit: Retrofit
    ): PostRetrofitService {
        return retrofit.create(PostRetrofitService::class.java)
    }
}