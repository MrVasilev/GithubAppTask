package com.emerchantpay.githubapptask

import com.emerchantpay.githubapptask.data.db.model.UserEntity

private const val ID = 123L
private const val LOGIN = "LOGIN"
private const val NAME = "NAME"
private const val AVATAR_URL = "AVATAR_URL"
private const val FOLLOWERS = 10

fun generateUserDb(isOwner: Boolean = false): UserEntity = UserEntity(
    id = ID,
    login = LOGIN,
    name = NAME,
    avatarUrl = AVATAR_URL,
    followers = FOLLOWERS,
    following = FOLLOWERS,
    isOwner = isOwner
)