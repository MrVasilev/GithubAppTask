package com.emerchantpay.githubapptask.data.repository

import com.emerchantpay.githubapptask.common.Constants.UNKNOWN_ERROR_MESSAGE
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.common.mapToDbModel
import com.emerchantpay.githubapptask.data.common.mapToDomainModel
import com.emerchantpay.githubapptask.data.db.dao.UserDao
import com.emerchantpay.githubapptask.data.network.GitHubApi
import com.emerchantpay.githubapptask.data.network.utils.fetchRemoteDataAndInsertInDb
import com.emerchantpay.githubapptask.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val gitHubApi: GitHubApi,
    private val userDao: UserDao,
) {

    suspend fun getUser(): Flow<Resource<User>> = flow {
        emit(Resource.Loading)

        val user = userDao.getOwnerUser()?.mapToDomainModel() ?: fetchUserDataRemoteAndInsertInDb()

        emit(Resource.Success(user))
    }.catch { error ->
        emit(Resource.Error(error.message ?: UNKNOWN_ERROR_MESSAGE))
    }.flowOn(Dispatchers.IO)

    suspend fun getFollowingUsers(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading)

        val followingUsers = userDao.getFollowingUsers().run {
            if (isNullOrEmpty()) {
                fetchFollowingUsersDataRemote()
            } else {
                map { it.mapToDomainModel() }
            }
        }

        emit(Resource.Success(followingUsers))
    }.catch { error ->
        emit(Resource.Error(error.message ?: UNKNOWN_ERROR_MESSAGE))
    }.flowOn(Dispatchers.IO)

    suspend fun getFollowerUsers(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading)

        val followerUsers = userDao.getFollowerUsers().run {
            if (isNullOrEmpty()) {
                fetchFollowerUsersDataRemote()
            } else {
                map { it.mapToDomainModel() }
            }
        }

        emit(Resource.Success(followerUsers))
    }.catch { error ->
        emit(Resource.Error(error.message ?: UNKNOWN_ERROR_MESSAGE))
    }.flowOn(Dispatchers.IO)

    private suspend fun fetchUserDataRemoteAndInsertInDb(): User = fetchRemoteDataAndInsertInDb(
        fetchRemoteData = { gitHubApi.getUser().run { listOf(this) } },
        mapToDbModel = { it.mapToDbModel(isOwner = true) },
        insertDbData = { userDao.insertUsers(it) },
        mapToDomainModel = { it.mapToDomainModel() }
    ).first()

    private suspend fun fetchFollowingUsersDataRemote(): List<User> = fetchRemoteDataAndInsertInDb(
        fetchRemoteData = { gitHubApi.getFollowingUsers() },
        mapToDbModel = { it.mapToDbModel(isFollowing = true) },
        insertDbData = { userDao.insertUsers(it) },
        mapToDomainModel = { it.mapToDomainModel() }
    )

    private suspend fun fetchFollowerUsersDataRemote(): List<User> = fetchRemoteDataAndInsertInDb(
        fetchRemoteData = { gitHubApi.getFollowerUsers() },
        mapToDbModel = { it.mapToDbModel(isFollower = true) },
        insertDbData = { userDao.insertUsers(it) },
        mapToDomainModel = { it.mapToDomainModel() }
    )

}