package com.emerchantpay.githubapptask.data.repository

import app.cash.turbine.test
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.db.dao.RepositoryDao
import com.emerchantpay.githubapptask.data.network.GitHubApi
import com.emerchantpay.githubapptask.generateRepositoryDb
import com.emerchantpay.githubapptask.generateRepositoryResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.only
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class RepoRepositoryTest {

    private val gitHubApi = mock<GitHubApi>()
    private val repositoryDao = mock<RepositoryDao>()

    private lateinit var tested: RepoRepository

    @Before
    fun setUp() {
        tested = RepoRepository(gitHubApi, repositoryDao)
    }

    @Test
    fun `getUserOwnedRepos() with empty db and success network call should return success`() =
        runTest {
            // given
            val response = listOf(generateRepositoryResponse())
            val repositoryList = listOf(generateRepositoryDb())

            whenever(gitHubApi.getUserRepos()).thenReturn(response)
            whenever(repositoryDao.getAllOwnedRepositories()).thenReturn(null)

            // when
            tested.getUserOwnedRepos().test {
                Assert.assertTrue(awaitItem() is Resource.Loading)
                Assert.assertTrue(awaitItem() is Resource.Success)
                awaitComplete()
            }

            inOrder(gitHubApi, repositoryDao) {
                verify(repositoryDao).getAllOwnedRepositories()
                verify(gitHubApi).getUserRepos()
                verify(repositoryDao).insertRepos(repositoryList)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `getUserOwnedRepos() with repos in db should return success and not make network call`() =
        runTest {
            // given
            val repositoryList = listOf(generateRepositoryDb())

            whenever(repositoryDao.getAllOwnedRepositories()).thenReturn(repositoryList)

            // when
            tested.getUserOwnedRepos().test {
                Assert.assertTrue(awaitItem() is Resource.Loading)
                Assert.assertTrue(awaitItem() is Resource.Success)
                awaitComplete()
            }

            verify(repositoryDao, only()).getAllOwnedRepositories()
        }

    @Test
    fun `getUserStarredRepos() with repos in db should return success and not make network call`() =
        runTest {
            // given
            val repositoryList = listOf(generateRepositoryDb())

            whenever(repositoryDao.getAllStarredRepositories()).thenReturn(repositoryList)

            // when
            tested.getUserStarredRepos().test {
                Assert.assertTrue(awaitItem() is Resource.Loading)
                Assert.assertTrue(awaitItem() is Resource.Success)
                awaitComplete()
            }

            verify(repositoryDao, only()).getAllStarredRepositories()
        }

    @Test
    fun `getUserOwnedRepos() with empty db and network exception should return error`() =
        runTest {
            // given
            whenever(repositoryDao.getAllOwnedRepositories()).thenReturn(null)
            whenever(gitHubApi.getUserRepos()).thenAnswer { throw Exception() }

            // when
            tested.getUserOwnedRepos().test {
                Assert.assertTrue(awaitItem() is Resource.Loading)
                Assert.assertTrue(awaitItem() is Resource.Error)
                awaitComplete()
            }

            inOrder(gitHubApi, repositoryDao) {
                verify(repositoryDao).getAllOwnedRepositories()
                verify(gitHubApi).getUserRepos()
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `getUserStarredRepos() with empty db and network exception should return error`() =
        runTest {
            // given
            whenever(repositoryDao.getAllStarredRepositories()).thenReturn(null)
            whenever(gitHubApi.getUserStarredRepos()).thenAnswer { throw Exception() }

            // when
            tested.getUserStarredRepos().test {
                Assert.assertTrue(awaitItem() is Resource.Loading)
                Assert.assertTrue(awaitItem() is Resource.Error)
                awaitComplete()
            }

            inOrder(gitHubApi, repositoryDao) {
                verify(repositoryDao).getAllStarredRepositories()
                verify(gitHubApi).getUserStarredRepos()
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `getUserOwnedRepos() with user in db and db exception should return error`() = runTest {
        // given
        whenever(repositoryDao.getAllOwnedRepositories()).thenAnswer { throw Exception() }

        // when
        tested.getUserOwnedRepos().test {
            Assert.assertTrue(awaitItem() is Resource.Loading)
            Assert.assertTrue(awaitItem() is Resource.Error)
            awaitComplete()
        }

        verify(repositoryDao, only()).getAllOwnedRepositories()
    }

    @Test
    fun `getUserStarredRepos() with user in db and db exception should return error`() = runTest {
        // given
        whenever(repositoryDao.getAllStarredRepositories()).thenAnswer { throw Exception() }

        // when
        tested.getUserStarredRepos().test {
            Assert.assertTrue(awaitItem() is Resource.Loading)
            Assert.assertTrue(awaitItem() is Resource.Error)
            awaitComplete()
        }

        verify(repositoryDao, only()).getAllStarredRepositories()
    }
}