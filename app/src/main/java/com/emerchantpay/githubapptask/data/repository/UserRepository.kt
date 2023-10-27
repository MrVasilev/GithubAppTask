package com.emerchantpay.githubapptask.data.repository

import com.emerchantpay.githubapptask.common.Constants.UNKNOWN_ERROR_MESSAGE
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.common.mapToDbModel
import com.emerchantpay.githubapptask.data.common.mapToDomainModel
import com.emerchantpay.githubapptask.data.db.dao.UserDao
import com.emerchantpay.githubapptask.data.network.GitHubApi
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
    private val userDao: UserDao
) {

    suspend fun getUser(): Flow<Resource<User>> = flow {
        emit(Resource.Loading)

        val user = userDao.getOwnerUser()?.mapToDomainModel() ?: fetchUserDataRemote()

        emit(Resource.Success(user))
    }.catch { error ->
        emit(Resource.Error(error.message ?: UNKNOWN_ERROR_MESSAGE))
    }.flowOn(Dispatchers.IO)

    private suspend fun fetchUserDataRemote(): User =
        gitHubApi.getUser()
            .run {
                val userDb = mapToDbModel()
                userDao.insertUsers(listOf(userDb))
                userDb.mapToDomainModel()
            }
}