package com.emerchantpay.githubapptask.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: Long,

    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @ColumnInfo(name = "followers")
    val followers: Int,

    @ColumnInfo(name = "following")
    val following: Int,

    @ColumnInfo(name = "is_owner")
    val isOwner: Boolean = false,

    @Relation(
        parentColumn = "id",
        entityColumn = "repository_id",
        associateBy = Junction(UserRepoCrossRef::class)
    )
    val repositories: List<RepositoryEntity>
)
