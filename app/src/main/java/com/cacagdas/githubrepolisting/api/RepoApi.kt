package com.cacagdas.githubrepolisting.api

import com.cacagdas.githubrepolisting.vo.Repo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoApi {

    @GET("users/{user}/repos")
    fun getRepos(@Path("user") user: String): Single<List<Repo>>
}