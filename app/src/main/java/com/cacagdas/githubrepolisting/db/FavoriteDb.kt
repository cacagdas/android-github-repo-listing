package com.cacagdas.githubrepolisting.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cacagdas.githubrepolisting.vo.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDb: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile private var instance: FavoriteDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FavoriteDb::class.java,
            "favoriterepos"
        ).build()
    }
}