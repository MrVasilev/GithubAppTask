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
    isOwner = isOwner,
    repositories = emptyList()
)

fun UserEntity.mapToDomainModel(): User = User(
    id = id,
    login = login,
    name = name,
    avatarUrl = avatarUrl,
    followers = followers,
    following = following
)

fun RepositoryResponse.mapToDbModel(): RepositoryEntity = RepositoryEntity(
    id = id,
    name = name.orEmpty(),
    url = url.orEmpty(),
    owner = owner.mapToDbModel(),
    contributors = contributors?.map { it.mapToDbModel() }.orEmpty()
)

fun RepositoryEntity.mapToDomainModel(): Repository = Repository(
    id = id,
    name = name.orEmpty(),
    url = url.orEmpty(),
    owner = owner.mapToDomainModel(),
    contributors = contributors.map { it.mapToDomainModel() }.orEmpty()
)