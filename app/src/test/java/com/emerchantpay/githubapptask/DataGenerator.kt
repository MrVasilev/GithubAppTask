package com.emerchantpay.githubapptask

import com.emerchantpay.githubapptask.data.db.model.UserEntity
import com.emerchantpay.githubapptask.data.network.model.UserResponse
import com.emerchantpay.githubapptask.domain.model.User

private const val ID = 123L
private const val LOGIN = "LOGIN"
private const val NAME = "NAME"
private const val AVATAR_URL = "AVATAR_URL"
private const val FOLLOWERS = 10

fun generateUserResponse(): UserResponse = UserResponse(
    id = ID,
    login = LOGIN,
    name = NAME,
    avatarUrl = AVATAR_URL,
    followers = FOLLOWERS,
    following = FOLLOWERS
)

fun generateUserResponseWithNulls(): UserResponse = UserResponse(
    id = ID,
    login = null,
    name = null,
    avatarUrl = null,
    followers = null,
    following = null
)

fun generateUserDb(): UserEntity = UserEntity(
    id = ID,
    login = LOGIN,
    name = NAME,
    avatarUrl = AVATAR_URL,
    followers = FOLLOWERS,
    following = FOLLOWERS,
    isOwner = false
)

fun generateUserDbEmpty(): UserEntity = UserEntity(
    id = ID,
    login = "",
    name = "",
    avatarUrl = "",
    followers = 0,
    following = 0,
    isOwner = false
)

fun generateUser(): User = User(
    id = ID,
    login = LOGIN,
    name = NAME,
    avatarUrl = AVATAR_URL,
    followers = FOLLOWERS,
    following = FOLLOWERS
)

fun generateUserEmpty(): User = User(
    id = ID,
    login = "",
    name = "",
    avatarUrl = "",
    followers = 0,
    following = 0
)