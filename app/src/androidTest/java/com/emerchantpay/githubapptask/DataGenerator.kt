package com.emerchantpay.githubapptask

import com.emerchantpay.githubapptask.data.db.model.RepositoryEntity
import com.emerchantpay.githubapptask.data.db.model.UserEntity

private const val ID = 123L
private const val LOGIN = "LOGIN"
private const val NAME = "NAME"
private const val AVATAR_URL = "AVATAR_URL"
private const val FOLLOWERS = 10
private const val REPO_ID = 456L
private const val REPO_URL = "https://REPO_URL"

fun generateUserDb(
    id: Long = ID,
    isOwner: Boolean = false,
    isFollowing: Boolean = false,
    isFollower: Boolean = false,
): UserEntity = UserEntity(
    id = id,
    login = LOGIN,
    name = NAME,
    avatarUrl = AVATAR_URL,
    followers = FOLLOWERS,
    following = FOLLOWERS,
    isOwner = isOwner,
    isFollowing = isFollowing,
    isFollower = isFollower
)

fun generateRepositoryDb(id: Long = REPO_ID, isStarred: Boolean = false): RepositoryEntity =
    RepositoryEntity(
        id = id,
        name = NAME,
        url = REPO_URL,
        ownerId = ID,
        ownerLogin = LOGIN,
        isStarred = isStarred
    )