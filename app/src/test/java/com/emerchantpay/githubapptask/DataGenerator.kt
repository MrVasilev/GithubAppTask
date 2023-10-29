package com.emerchantpay.githubapptask

import com.emerchantpay.githubapptask.data.db.model.RepositoryEntity
import com.emerchantpay.githubapptask.data.db.model.UserEntity
import com.emerchantpay.githubapptask.data.network.model.RepositoryResponse
import com.emerchantpay.githubapptask.data.network.model.UserResponse
import com.emerchantpay.githubapptask.domain.model.Repository
import com.emerchantpay.githubapptask.domain.model.User

private const val ID = 123L
private const val LOGIN = "LOGIN"
private const val NAME = "NAME"
private const val AVATAR_URL = "AVATAR_URL"
private const val FOLLOWERS = 10
private const val REPO_ID = 456L
private const val REPO_URL = "https://REPO_URL"

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

fun generateRepositoryResponse(): RepositoryResponse = RepositoryResponse(
    id = REPO_ID,
    name = NAME,
    url = REPO_URL,
    owner = generateUserResponse()
)

fun generateRepositoryResponseWithNulls(): RepositoryResponse = RepositoryResponse(
    id = REPO_ID,
    name = null,
    url = null,
    owner = generateUserResponseWithNulls()
)

fun generateRepositoryDb(isStarred: Boolean = false): RepositoryEntity = RepositoryEntity(
    id = REPO_ID,
    name = NAME,
    url = REPO_URL,
    ownerId = ID,
    ownerLogin = LOGIN,
    isStarred = isStarred
)

fun generateRepositoryDbEmpty(): RepositoryEntity = RepositoryEntity(
    id = REPO_ID,
    name = "",
    url = "",
    ownerId = ID,
    ownerLogin = "",
    isStarred = false
)

fun generateRepository(isStarred: Boolean = false): Repository = Repository(
    id = REPO_ID,
    name = NAME,
    url = REPO_URL,
    ownerId = ID,
    ownerLogin = LOGIN,
    isStarred = isStarred
)

fun generateRepositoryEmpty(): Repository = Repository(
    id = REPO_ID,
    name = "",
    url = "",
    ownerId = ID,
    ownerLogin = "",
    isStarred = false
)