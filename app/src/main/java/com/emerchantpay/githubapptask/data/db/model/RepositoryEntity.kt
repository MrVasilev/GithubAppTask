package com.emerchantpay.githubapptask.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "html_url")
    val url: String,

    @ColumnInfo(name = "owner")
    val owner: UserEntity,

    @Relation(
        parentColumn = "repository_id",
        entityColumn = "user_id",
        associateBy = Junction(UserRepoCrossRef::class)
    )
    val contributors: List<UserEntity>
)
