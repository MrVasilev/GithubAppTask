package com.emerchantpay.githubapptask.data.db.model

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "user_repo_cross_ref")
data class UserRepoCrossRef(
    @Embedded val user: UserEntity,
    @Embedded val repository: RepositoryEntity
)
