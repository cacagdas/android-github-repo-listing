package com.cacagdas.githubrepolisting.di

import com.cacagdas.githubrepolisting.api.RepoService
import com.cacagdas.githubrepolisting.ui.main.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: RepoService)

    fun inject(viewModel: ListViewModel)
}