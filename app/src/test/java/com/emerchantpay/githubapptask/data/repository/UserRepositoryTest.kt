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
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.only
import org.mockito.kotlin.verify
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
        val userDb = generateUserDb(isOwner = true)

        whenever(userDao.getOwnerUser()).thenReturn(null)
        whenever(gitHubApi.getUser()).thenReturn(response)
        doNothing().`when`(userDao).insertUsers(listOf(userDb))

        // when
        tested.getUser().test {
            assertTrue(awaitItem() is Resource.Loading)
            assertTrue(awaitItem() is Resource.Success)
            awaitComplete()
        }

        inOrder(gitHubApi, userDao) {
            verify(userDao).getOwnerUser()
            verify(gitHubApi).getUser()
            verify(userDao).insertUsers(listOf(userDb))
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

        verify(userDao, only()).getOwnerUser()
    }

    @Test
    fun `getFollowingUsers() with empty db and success network call should return success`() =
        runTest {
            // given
            val response = listOf(generateUserResponse())
            val usersDb = listOf(generateUserDb(isFollowing = true))

            whenever(userDao.getFollowingUsers()).thenReturn(null)
            whenever(gitHubApi.getFollowingUsers()).thenReturn(response)
            doNothing().`when`(userDao).insertUsers(usersDb)

            // when
            tested.getFollowingUsers().test {
                assertTrue(awaitItem() is Resource.Loading)
                assertTrue(awaitItem() is Resource.Success)
                awaitComplete()
            }

            inOrder(gitHubApi, userDao) {
                verify(userDao).getFollowingUsers()
                verify(gitHubApi).getFollowingUsers()
                verify(userDao).insertUsers(usersDb)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `getFollowingUsers() with user in db should return success and not make network call`() =
        runTest {
            // given
            val usersDb = listOf(generateUserDb(isFollowing = true))

            whenever(userDao.getFollowingUsers()).thenReturn(usersDb)

            // when
            tested.getFollowingUsers().test {
                assertTrue(awaitItem() is Resource.Loading)
                assertTrue(awaitItem() is Resource.Success)
                awaitComplete()
            }

            inOrder(userDao) {
                verify(userDao).getFollowingUsers()
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `getFollowingUsers() with empty db and network exception should return error`() = runTest {
        // given
        whenever(userDao.getFollowingUsers()).thenReturn(null)
        whenever(gitHubApi.getFollowingUsers()).thenAnswer { throw Exception() }

        // when
        tested.getFollowingUsers().test {
            assertTrue(awaitItem() is Resource.Loading)
            assertTrue(awaitItem() is Resource.Error)
            awaitComplete()
        }

        inOrder(gitHubApi, userDao) {
            verify(userDao).getFollowingUsers()
            verify(gitHubApi).getFollowingUsers()
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `getFollowingUsers() with user in db and db exception should return error`() = runTest {
        // given
        whenever(userDao.getFollowingUsers()).thenAnswer { throw Exception() }

        // when
        tested.getFollowingUsers().test {
            assertTrue(awaitItem() is Resource.Loading)
            assertTrue(awaitItem() is Resource.Error)
            awaitComplete()
        }

        verify(userDao, only()).getFollowingUsers()
    }

    @Test
    fun `getFollowerUsers() with empty db and success network call should return success`() =
        runTest {
            // given
            val response = listOf(generateUserResponse())
            val usersDb = listOf(generateUserDb(isFollower = true))

            whenever(userDao.getFollowerUsers()).thenReturn(null)
            whenever(gitHubApi.getFollowerUsers()).thenReturn(response)
            doNothing().`when`(userDao).insertUsers(usersDb)

            // when
            tested.getFollowerUsers().test {
                assertTrue(awaitItem() is Resource.Loading)
                assertTrue(awaitItem() is Resource.Success)
                awaitComplete()
            }

            inOrder(gitHubApi, userDao) {
                verify(userDao).getFollowerUsers()
                verify(gitHubApi).getFollowerUsers()
                verify(userDao).insertUsers(usersDb)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `getFollowerUsers() with user in db should return success and not make network call`() =
        runTest {
            // given
            val usersDb = listOf(generateUserDb(isFollower = true))

            whenever(userDao.getFollowerUsers()).thenReturn(usersDb)

            // when
            tested.getFollowerUsers().test {
                assertTrue(awaitItem() is Resource.Loading)
                assertTrue(awaitItem() is Resource.Success)
                awaitComplete()
            }

            inOrder(userDao) {
                verify(userDao).getFollowerUsers()
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `getFollowerUsers() with empty db and network exception should return error`() = runTest {
        // given
        whenever(userDao.getFollowerUsers()).thenReturn(null)
        whenever(gitHubApi.getFollowerUsers()).thenAnswer { throw Exception() }

        // when
        tested.getFollowerUsers().test {
            assertTrue(awaitItem() is Resource.Loading)
            assertTrue(awaitItem() is Resource.Error)
            awaitComplete()
        }

        inOrder(gitHubApi, userDao) {
            verify(userDao).getFollowerUsers()
            verify(gitHubApi).getFollowerUsers()
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `getFollowerUsers() with user in db and db exception should return error`() = runTest {
        // given
        whenever(userDao.getFollowerUsers()).thenAnswer { throw Exception() }

        // when
        tested.getFollowerUsers().test {
            assertTrue(awaitItem() is Resource.Loading)
            assertTrue(awaitItem() is Resource.Error)
            awaitComplete()
        }

        verify(userDao, only()).getFollowerUsers()
    }
}