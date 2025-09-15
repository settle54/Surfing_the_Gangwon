package com.capstone.surfingthegangwon.data.user.di

import com.capstone.surfingthegangwon.core.retrofit.RetrofitModule
import com.capstone.surfingthegangwon.data.user.api.UserRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserRetrofitModule {

    @Provides
    @Singleton
    fun provideServerUserRetrofitService(
        @RetrofitModule.ServiceApi retrofit: Retrofit
    ): UserRetrofitService {
        return retrofit.create(UserRetrofitService::class.java)
    }
}