package com.emerchantpay.githubapptask.data.db.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithRepos(
    @Embedded val user: UserEntity,

    @Relation(
        parentColumn = "user_id",
        entityColumn = "owner_id"
    )
    val repositories: List<RepositoryEntity> = emptyList(),
)
