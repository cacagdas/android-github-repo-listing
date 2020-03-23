package com.cacagdas.githubrepolisting.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cacagdas.githubrepolisting.vo.Favorite

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    suspend fun getAllRepos(): List<Favorite>

    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE fav_id = :fav_id")
    suspend fun deleteFavoriteById(fav_id: Int)

}
