package com.cacagdas.githubrepolisting.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cacagdas.githubrepolisting.api.DataHolder
import com.cacagdas.githubrepolisting.vo.Repo
import com.cacagdas.githubrepolisting.db.RepoDb
import com.cacagdas.githubrepolisting.api.RepoService
import com.cacagdas.githubrepolisting.db.FavoriteDb
import com.cacagdas.githubrepolisting.di.DaggerApiComponent
import com.cacagdas.githubrepolisting.viewmodel.BaseViewModel
import com.cacagdas.githubrepolisting.vo.Favorite
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

class ListViewModel(application: Application): BaseViewModel(application) {

    @Inject
    lateinit var repoService: RepoService
    init {
        DaggerApiComponent.create().inject(this)
    }
    private val disposable = CompositeDisposable()

    private val dao = FavoriteDb(getApplication()).favoriteDao()

    val favedRepos = MutableLiveData<List<Favorite>>()

    fun refresh(username: String) {
        fetchFromRemote(username)
    }

    private fun fetchFromRemote(username: String) {
        processTraceLiveData.value = DataHolder.Loading
        disposable.add(
            repoService.getRepos(username)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Repo>>() {
                    override fun onSuccess(repoList: List<Repo>) {
                        storeReposLocally(repoList)
                        getAllFavedRepos()
                    }

                    override fun onError(e: Throwable) {
                        processTraceLiveData.value = DataHolder.Fail(Error(e))
                    }

                })
        )
    }

    private fun reposRetrieved(repoList: List<Repo>) {
        processTraceLiveData.value = DataHolder.Success(repoList)
    }

    private fun storeReposLocally(list: List<Repo>) {
        launch {
            val dao = RepoDb(getApplication()).repoDao()
            dao.deleteAllRepos()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].id = result[i].toInt()
                ++i
            }
            reposRetrieved(list)
        }
    }

    fun getAllFavedRepos() {
        launch {
            val favorites: List<Favorite> = dao.getAllRepos()
            Log.d("favorites: ", favorites.size.toString())
            favedRepos.value = favorites
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}