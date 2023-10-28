@file:JvmName(name = "ModelExtensions")

package com.emerchantpay.githubapptask.data.common

import com.emerchantpay.githubapptask.data.db.model.RepositoryEntity
import com.emerchantpay.githubapptask.data.db.model.UserEntity
import com.emerchantpay.githubapptask.data.network.model.RepositoryResponse
import com.emerchantpay.githubapptask.data.network.model.UserResponse
import com.emerchantpay.githubapptask.domain.model.Repository
import com.emerchantpay.githubapptask.domain.model.User

fun UserResponse.mapToDbModel(isOwner: Boolean = false): UserEntity = UserEntity(
    id = id,
    login = login.orEmpty(),
    name = name.orEmpty(),
    avatarUrl = avatarUrl.orEmpty(),
    followers = followers ?: 0,
    following = following ?: 0,
    isOwner = isOwner
)

fun UserEntity.mapToDomainModel(): User = User(
    id = id,
    login = login,
    name = name,
    avatarUrl = avatarUrl,
    followers = followers,
    following = following
)

fun RepositoryResponse.mapToDbModel(isStarred: Boolean = false): RepositoryEntity =
    RepositoryEntity(
        id = id,
        name = name.orEmpty(),
        url = url.orEmpty(),
        ownerId = owner.id,
        isStarred = isStarred
    )

fun RepositoryEntity.mapToDomainModel(): Repository = Repository(
    id = id,
    name = name,
    url = url,
    ownerId = ownerId,
    isStarred = isStarred
)