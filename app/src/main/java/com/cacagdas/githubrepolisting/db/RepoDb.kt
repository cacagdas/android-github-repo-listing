package com.cacagdas.githubrepolisting.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cacagdas.githubrepolisting.vo.Repo

@Database(entities = [Repo::class], version = 1)
abstract class RepoDb: RoomDatabase() {
    abstract fun repoDao(): RepoDao

    companion object {
        @Volatile private var instance: RepoDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RepoDb::class.java,
            "repodatabase"
        ).build()
    }
}