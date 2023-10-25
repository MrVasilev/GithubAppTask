package com.emerchantpay.githubapptask.data.repository

import com.emerchantpay.githubapptask.common.Constants
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.mapper.RepositoryMapper
import com.emerchantpay.githubapptask.data.network.GitHubApi
import com.emerchantpay.githubapptask.domain.model.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
    private val gitHubApi: GitHubApi,
    private val repositoryMapper: RepositoryMapper
) {

    suspend fun getUserRepos(): Flow<Resource<List<Repository>>> = flow {
        emit(Resource.Loading)
        val repos = gitHubApi.getUserRepos()
            .map { repositoryMapper.mapToDomainModel(it) }
        emit(Resource.Success(repos))
    }.catch { error ->
        emit(Resource.Error(error.message ?: Constants.UNKNOWN_ERROR_MESSAGE))
    }

    suspend fun getUserStarredRepos(): Flow<Resource<List<Repository>>> = flow {
        emit(Resource.Loading)
        val repos = gitHubApi.getUserStarredRepos()
            .map { repositoryMapper.mapToDomainModel(it) }
        emit(Resource.Success(repos))
    }.catch { error ->
        emit(Resource.Error(error.message ?: Constants.UNKNOWN_ERROR_MESSAGE))
    }

}