package com.cacagdas.githubrepolisting.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Favorite (

    @PrimaryKey
    @ColumnInfo(name = "fav_id")
    var id: Int
)