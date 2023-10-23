package com.emerchantpay.githubapptask

import com.emerchantpay.githubapptask.data.model.UserResponse
import com.emerchantpay.githubapptask.domain.model.User

private const val ID = 123
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