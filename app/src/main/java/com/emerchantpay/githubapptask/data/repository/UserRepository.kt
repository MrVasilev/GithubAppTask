package com.emerchantpay.githubapptask.data.repository

import com.emerchantpay.githubapptask.common.Constants.UNKNOWN_ERROR_MESSAGE
import com.emerchantpay.githubapptask.common.Resource
import com.emerchantpay.githubapptask.data.mapper.UserMapper
import com.emerchantpay.githubapptask.data.network.GitHubApi
import com.emerchantpay.githubapptask.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val gitHubApi: GitHubApi,
    private val userMapper: UserMapper
) {

    suspend fun getUser(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        val user = gitHubApi.getUser()
            .let { userMapper.mapToDomainModel(it) }
        emit(Resource.Success(user))
    }.catch { error ->
        emit(Resource.Error(error.message ?: UNKNOWN_ERROR_MESSAGE))
    }
}