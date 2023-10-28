package com.emerchantpay.githubapptask.data.repository

import com.emerchantpay.githubapptask.common.Constants
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.common.mapToDbModel
import com.emerchantpay.githubapptask.data.common.mapToDomainModel
import com.emerchantpay.githubapptask.data.db.dao.RepositoryDao
import com.emerchantpay.githubapptask.data.network.GitHubApi
import com.emerchantpay.githubapptask.data.network.utils.fetchRemoteDataAndInsertInDb
import com.emerchantpay.githubapptask.domain.model.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
    private val gitHubApi: GitHubApi,
    private val repositoryDao: RepositoryDao,
) {

    suspend fun getUserOwnedRepos(): Flow<Resource<List<Repository>>> = flow {
        emit(Resource.Loading)

        val repos = repositoryDao.getAllOwnedRepositories().run {
            if (isNullOrEmpty()) {
                fetchUserRepositoriesDataRemote()
            } else {
                map { it.mapToDomainModel() }
            }
        }

        emit(Resource.Success(repos))
    }.catch { error ->
        emit(Resource.Error(error.message ?: Constants.UNKNOWN_ERROR_MESSAGE))
    }.flowOn(Dispatchers.IO)

    suspend fun getUserStarredRepos(): Flow<Resource<List<Repository>>> = flow {
        emit(Resource.Loading)

        val repos = repositoryDao.getAllStarredRepositories().run {
            if (isNullOrEmpty()) {
                fetchUserStarredRepositoriesDataRemote()
            } else {
                map { it.mapToDomainModel() }
            }
        }

        emit(Resource.Success(repos))
    }.catch { error ->
        emit(Resource.Error(error.message ?: Constants.UNKNOWN_ERROR_MESSAGE))
    }.flowOn(Dispatchers.IO)

    private suspend fun fetchUserRepositoriesDataRemote(): List<Repository> =
        fetchRemoteDataAndInsertInDb(
            fetchRemoteData = { gitHubApi.getUserRepos() },
            mapToDbModel = { it.mapToDbModel() },
            insertDbData = { repositoryDao.insertRepos(it) },
            mapToDomainModel = { it.mapToDomainModel() }
        )

    private suspend fun fetchUserStarredRepositoriesDataRemote(): List<Repository> =
        fetchRemoteDataAndInsertInDb(
            fetchRemoteData = { gitHubApi.getUserStarredRepos() },
            mapToDbModel = { it.mapToDbModel(isStarred = true) },
            insertDbData = { repositoryDao.insertRepos(it) },
            mapToDomainModel = { it.mapToDomainModel() }
        )

}