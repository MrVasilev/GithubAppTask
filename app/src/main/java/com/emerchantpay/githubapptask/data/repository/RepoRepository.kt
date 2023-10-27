package com.emerchantpay.githubapptask.data.repository

import com.emerchantpay.githubapptask.common.Constants
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.common.mapToDbModel
import com.emerchantpay.githubapptask.data.common.mapToDomainModel
import com.emerchantpay.githubapptask.data.db.dao.RepositoryDao
import com.emerchantpay.githubapptask.data.db.model.RepositoryEntity
import com.emerchantpay.githubapptask.data.network.GitHubApi
import com.emerchantpay.githubapptask.data.network.model.RepositoryResponse
import com.emerchantpay.githubapptask.domain.model.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
    private val gitHubApi: GitHubApi,
    private val repositoryDao: RepositoryDao
) {

    suspend fun getUserRepos(): Flow<Resource<List<Repository>>> = flow {
        emit(Resource.Loading)

        val repos = repositoryDao.getAllRepositories()?.map { it.mapToDomainModel() }
            ?: fetchUserRepositoriesDataRemote()

        emit(Resource.Success(repos))
    }.catch { error ->
        emit(Resource.Error(error.message ?: Constants.UNKNOWN_ERROR_MESSAGE))
    }

    suspend fun getUserStarredRepos(): Flow<Resource<List<Repository>>> = flow {
        emit(Resource.Loading)

        val repos = repositoryDao.getAllRepositories()?.map { it.mapToDomainModel() }
            ?: fetchUserStarredRepositoriesDataRemote()

        emit(Resource.Success(repos))
    }.catch { error ->
        emit(Resource.Error(error.message ?: Constants.UNKNOWN_ERROR_MESSAGE))
    }

    private suspend fun fetchUserRepositoriesDataRemote(): List<Repository> =
        fetchAndInsertRepositories(
            fetchRemoteRepos = { gitHubApi.getUserRepos() },
            mapToDbModel = { it.mapToDbModel() },
            insertRepos = { repositoryDao.insertRepos(it) },
            mapToDomainModel = { it.mapToDomainModel() }
        )

    private suspend fun fetchUserStarredRepositoriesDataRemote(): List<Repository> =
        fetchAndInsertRepositories(
            fetchRemoteRepos = { gitHubApi.getUserStarredRepos() },
            mapToDbModel = { it.mapToDbModel() },
            insertRepos = { repositoryDao.insertRepos(it) },
            mapToDomainModel = { it.mapToDomainModel() }
        )

    private suspend fun fetchAndInsertRepositories(
        fetchRemoteRepos: suspend () -> List<RepositoryResponse>,
        mapToDbModel: (RepositoryResponse) -> RepositoryEntity,
        insertRepos: suspend (List<RepositoryEntity>) -> Unit,
        mapToDomainModel: (RepositoryEntity) -> Repository
    ): List<Repository> {
        val reposDb = fetchRemoteRepos().map { mapToDbModel(it) }
        insertRepos(reposDb)
        return reposDb.map { mapToDomainModel(it) }
    }

}