@file:JvmName(name = "ModelExtensions")

package com.emerchantpay.githubapptask.data.common

import com.emerchantpay.githubapptask.data.db.model.UserEntity
import com.emerchantpay.githubapptask.data.network.model.UserResponse
import com.emerchantpay.githubapptask.domain.model.User

fun UserEntity.mapToDomainModel(): User = User(
    id = id,
    login = login,
    name = name,
    avatarUrl = avatarUrl,
    followers = followers,
    following = following
)

fun UserResponse.mapToDbModel(isOwner: Boolean = false) = UserEntity(
    id = id,
    login = login.orEmpty(),
    name = name.orEmpty(),
    avatarUrl = avatarUrl.orEmpty(),
    followers = followers ?: 0,
    following = following ?: 0,
    isOwner = isOwner
)