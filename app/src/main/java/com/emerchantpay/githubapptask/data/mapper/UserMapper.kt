package com.emerchantpay.githubapptask.data.mapper

import com.emerchantpay.githubapptask.data.model.UserResponse
import com.emerchantpay.githubapptask.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserMapper @Inject constructor() {

    fun mapToDomainModel(response: UserResponse) = User(
        id = response.id,
        login = response.login.orEmpty(),
        name = response.name.orEmpty(),
        avatarUrl = response.avatarUrl.orEmpty(),
        followers = response.followers ?: 0,
        following = response.following ?: 0
    )
}