package com.capstone.surfingthegangwon.data.login.di

import com.capstone.surfingthegangwon.core.retrofit.RetrofitModule
import com.capstone.surfingthegangwon.data.login.api.AuthRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRetrofitModule {

    @Provides
    @Singleton
    fun provideServerAuthRetrofitService(
        @RetrofitModule.ServiceApi retrofit: Retrofit): AuthRetrofitService {
        return retrofit.create(AuthRetrofitService::class.java)
    }
}