package com.cacagdas.githubrepolisting.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cacagdas.githubrepolisting.vo.Repo

@Dao
interface RepoDao {
    @Insert
    suspend fun insertAll(vararg repos: Repo): List<Long>

    @Query("SELECT * FROM repo")
    suspend fun getAllRepos(): List<Repo>

    @Query("SELECT * FROM repo WHERE repo_id = :repoId")
    suspend fun getRepo(repoId: Int): Repo

    @Query("DELETE FROM repo")
    suspend fun deleteAllRepos()
}