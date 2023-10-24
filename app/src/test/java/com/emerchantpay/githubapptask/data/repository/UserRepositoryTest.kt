package com.emerchantpay.githubapptask.data.repository

import app.cash.turbine.test
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.mapper.UserMapper
import com.emerchantpay.githubapptask.data.network.GitHubApi
import com.emerchantpay.githubapptask.generateUser
import com.emerchantpay.githubapptask.generateUserResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
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
class UserRepositoryTest {

    private val gitHubApi = mock<GitHubApi>()
    private val userMapper = mock<UserMapper>()

    private lateinit var tested: UserRepository

    @Before
    fun setUp() {
        tested = UserRepository(gitHubApi, userMapper)
    }

    @Test
    fun `getUser() success should emit loading, success and return response`() = runTest {
        // given
        val response = generateUserResponse()
        val user = generateUser()

        whenever(gitHubApi.getUser()).thenReturn(response)
        whenever(userMapper.mapToDomainModel(response)).thenReturn(user)

        // when
        tested.getUser().test {
            assertTrue(awaitItem() is Resource.Loading)
            assertTrue(awaitItem() is Resource.Success)
            awaitComplete()
        }

        inOrder(gitHubApi, userMapper) {
            verify(gitHubApi).getUser()
            verify(userMapper).mapToDomainModel(response)
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `getUser() error should emit loading, error and return exception`() = runTest {
        // given
        whenever(gitHubApi.getUser()).thenAnswer { throw Exception() }

        // when
        tested.getUser().test {
            assertTrue(awaitItem() is Resource.Loading)
            assertTrue(awaitItem() is Resource.Error)
            awaitComplete()
        }

        verify(gitHubApi, only()).getUser()
    }
}