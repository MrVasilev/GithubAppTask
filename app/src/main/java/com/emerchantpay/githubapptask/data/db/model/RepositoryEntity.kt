package com.emerchantpay.githubapptask.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "repo_id")
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "html_url")
    val url: String,

    @ColumnInfo(name = "owner_id")
    val ownerId: Long,

    @ColumnInfo(name = "is_starred")
    val isStarred: Boolean
)
