package com.emerchantpay.githubapptask.data.network.utils

import com.emerchantpay.githubapptask.data.common.mapToDbModel
import com.emerchantpay.githubapptask.data.common.mapToDomainModel
import com.emerchantpay.githubapptask.data.db.dao.UserDao
import com.emerchantpay.githubapptask.data.network.GitHubApi
import com.emerchantpay.githubapptask.generateUser
import com.emerchantpay.githubapptask.generateUserDb
import com.emerchantpay.githubapptask.generateUserResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class NetworkExtensionsTest {

    private val gitHubApi = mock<GitHubApi>()
    private val userDao = mock<UserDao>()

    @Test
    fun `fetchRemoteDataAndInsertInDb() with user api call should return response and insert data in db`() =
        runTest {
            // given
            val userResponse = generateUserResponse()
            val userDb = generateUserDb(isOwner = true)
            val expected = listOf(generateUser())

            whenever(gitHubApi.getUser()).thenReturn(userResponse)

            // when
            val actual = fetchRemoteDataAndInsertInDb(
                fetchRemoteData = { gitHubApi.getUser().run { listOf(this) } },
                mapToDbModel = { it.mapToDbModel(isOwner = true) },
                insertDbData = { userDao.insertUsers(it) },
                mapToDomainModel = { it.mapToDomainModel() }
            )

            // then
            inOrder(gitHubApi, userDao) {
                verify(gitHubApi).getUser()
                verify(userDao).insertUsers(listOf(userDb))
                verifyNoMoreInteractions()
            }

            assertEquals(expected, actual)
        }

    @Test
    fun `fetchRemoteDataAndInsertInDb() with following users api call should return response and insert data in db`() =
        runTest {
            // given
            val userResponse = listOf(generateUserResponse())
            val userDb = listOf(generateUserDb(isFollowing = true))
            val expected = listOf(generateUser())

            whenever(gitHubApi.getFollowingUsers()).thenReturn(userResponse)

            // when
            val actual = fetchRemoteDataAndInsertInDb(
                fetchRemoteData = { gitHubApi.getFollowingUsers() },
                mapToDbModel = { it.mapToDbModel(isFollowing = true) },
                insertDbData = { userDao.insertUsers(it) },
                mapToDomainModel = { it.mapToDomainModel() }
            )

            // then
            inOrder(gitHubApi, userDao) {
                verify(gitHubApi).getFollowingUsers()
                verify(userDao).insertUsers(userDb)
                verifyNoMoreInteractions()
            }

            assertEquals(expected, actual)
        }
}