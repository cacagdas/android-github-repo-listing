package com.cacagdas.githubrepolisting.di

import com.cacagdas.githubrepolisting.api.RepoApi
import com.cacagdas.githubrepolisting.api.RepoService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    private val BASE_URL = "https://api.github.com"

    @Provides
    fun provideRepoApi(): RepoApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RepoApi::class.java)
    }

    @Provides
    fun provideRepoService(): RepoService {
        return RepoService()
    }
}