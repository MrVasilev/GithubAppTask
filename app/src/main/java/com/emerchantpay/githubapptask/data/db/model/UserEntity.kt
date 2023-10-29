package com.emerchantpay.githubapptask.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
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

    @ColumnInfo(name = "is_following")
    val isFollowing: Boolean = false,

    @ColumnInfo(name = "is_follower")
    val isFollower: Boolean = false,
)
