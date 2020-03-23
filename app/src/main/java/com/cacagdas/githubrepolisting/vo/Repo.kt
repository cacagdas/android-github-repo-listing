package com.cacagdas.githubrepolisting.vo

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Repo(

    @PrimaryKey
    @ColumnInfo(name = "repo_id")
    @SerializedName("id")
    var id: Int,

    @ColumnInfo(name = "repo_name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "repo_description")
    @SerializedName("description")
    val description: String?,

    @Embedded(prefix = "owner_")
    @SerializedName("owner")
    val owner: Owner,

    @ColumnInfo(name = "repo_star_count")
    @SerializedName("stargazers_count")
    val stars: Int,

    @ColumnInfo(name = "repo_issue_count")
    @SerializedName("open_issues_count")
    val openIssues: Int
) {
    data class Owner(
        @ColumnInfo(name = "name")
        @SerializedName("login")
        val ownerName: String?,

        @ColumnInfo(name = "avatar_url")
        @SerializedName("avatar_url")
        val avatarUrl: String?
    )
}