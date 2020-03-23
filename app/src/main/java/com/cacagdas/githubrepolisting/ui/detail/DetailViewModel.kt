package com.cacagdas.githubrepolisting.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cacagdas.githubrepolisting.db.FavoriteDb
import com.cacagdas.githubrepolisting.vo.Repo
import com.cacagdas.githubrepolisting.db.RepoDb
import com.cacagdas.githubrepolisting.viewmodel.BaseViewModel
import com.cacagdas.githubrepolisting.vo.Favorite
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailViewModel(application: Application): BaseViewModel(application) {

    val repoLiveData = MutableLiveData<Repo>()
    private val dao = FavoriteDb(getApplication()).favoriteDao()
    val isFavorite = MutableLiveData<Boolean>()

    fun fetch(uuid: Int) {
        launch {
            val repo = RepoDb(getApplication()).repoDao().getRepo(uuid)
            repoLiveData.value = repo
        }
    }

    fun addRepoToFavorites(id: Int) {
        launch {
            val favoriteRepo = Favorite(id)
            val favorites: List<Favorite> = dao.getAllRepos()
            if (!checkIsRepoFaved(favoriteRepo, favorites)) {
                dao.insertFavorite(favoriteRepo)
                isFavorite.value = true
            }
            else {
                dao.deleteFavorite(favoriteRepo)
                isFavorite.value = false
            }
        }
    }

    private fun checkIsRepoFaved(repo: Favorite, favorites: List<Favorite>): Boolean {
        for (favorite in favorites) {
            if (repo.id == favorite.id)
                return true
        }
        return false
    }

    private fun checkIsRepoFaved(repo: Favorite) {
        launch {
            val favorites: List<Favorite> = dao.getAllRepos()
            isFavorite.value = checkIsRepoFaved(repo, favorites)
        }
    }

    fun checkIsRepoFaved(id: Int) {
        val repo = Favorite(id)
        checkIsRepoFaved(repo)
    }
}