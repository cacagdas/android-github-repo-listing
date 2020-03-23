package com.cacagdas.githubrepolisting.api

import com.cacagdas.githubrepolisting.di.ApiComponent
import com.cacagdas.githubrepolisting.di.DaggerApiComponent
import com.cacagdas.githubrepolisting.vo.Repo
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RepoService {

    @Inject
    lateinit var api: RepoApi
    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getRepos(username: String): Single<List<Repo>> {
        return api.getRepos(username)
    }
}