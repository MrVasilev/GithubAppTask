package com.emerchantpay.githubapptask.data.repository

import app.cash.turbine.test
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.db.dao.UserDao
import com.emerchantpay.githubapptask.data.network.GitHubApi
import com.emerchantpay.githubapptask.generateUserDb
import com.emerchantpay.githubapptask.generateUserResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    private val gitHubApi = mock<GitHubApi>()
    private val userDao = mock<UserDao>()

    private lateinit var tested: UserRepository

    @Before
    fun setUp() {
        tested = UserRepository(gitHubApi, userDao)
    }

    @Test
    fun `getUser() with empty db and success network call should return success`() = runTest {
        // given
        val response = generateUserResponse()
        val userDb = generateUserDb()


        whenever(gitHubApi.getUser()).thenReturn(response)
        whenever(userDao.getOwnerUser()).thenReturn(null)

        // when
        tested.getUser().test {
            assertTrue(awaitItem() is Resource.Loading)
            assertTrue(awaitItem() is Resource.Success)
            awaitComplete()
        }

        inOrder(gitHubApi, userDao) {
            verify(userDao).getOwnerUser()
            verify(gitHubApi).getUser()
            verify(userDao).insertUsers(userDb)
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `getUser() with user in db should return success and not make network call`() = runTest {
        // given
        val userDb = generateUserDb()

        whenever(userDao.getOwnerUser()).thenReturn(userDb)

        // when
        tested.getUser().test {
            assertTrue(awaitItem() is Resource.Loading)
            assertTrue(awaitItem() is Resource.Success)
            awaitComplete()
        }

        inOrder(userDao) {
            verify(userDao).getOwnerUser()
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `getUser() with empty db and network exception should return error`() = runTest {
        // given
        whenever(userDao.getOwnerUser()).thenReturn(null)
        whenever(gitHubApi.getUser()).thenAnswer { throw Exception() }

        // when
        tested.getUser().test {
            assertTrue(awaitItem() is Resource.Loading)
            assertTrue(awaitItem() is Resource.Error)
            awaitComplete()
        }

        inOrder(gitHubApi, userDao) {
            verify(userDao).getOwnerUser()
            verify(gitHubApi).getUser()
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `getUser() with user in db and db exception should return error`() = runTest {
        // given
        whenever(userDao.getOwnerUser()).thenAnswer { throw Exception() }

        // when
        tested.getUser().test {
            assertTrue(awaitItem() is Resource.Loading)
            assertTrue(awaitItem() is Resource.Error)
            awaitComplete()
        }

        inOrder(gitHubApi, userDao) {
            verify(userDao).getOwnerUser()
            verifyNoMoreInteractions()
        }
    }
}