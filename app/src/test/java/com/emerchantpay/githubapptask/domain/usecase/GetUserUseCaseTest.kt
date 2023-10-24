package com.emerchantpay.githubapptask.domain.usecase

import app.cash.turbine.test
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.repository.UserRepository
import com.emerchantpay.githubapptask.generateUser
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.only
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetUserUseCaseTest {

    private val userRepository = mock<UserRepository>()

    private lateinit var tested: GetUserUseCase

    @Before
    fun setUp() {
        tested = GetUserUseCase(userRepository)
    }

    @Test
    fun `invoke() loading should return loading response`() = runTest {
        // given
        whenever(userRepository.getUser()).thenReturn(flowOf(Resource.Loading))

        // when
        tested.invoke().test {
            assertTrue(awaitItem() is Resource.Loading)
            awaitComplete()
        }

        verify(userRepository, only()).getUser()
    }

    @Test
    fun `invoke() success should return success response`() = runTest {
        // given
        val user = generateUser()

        whenever(userRepository.getUser()).thenReturn(flowOf(Resource.Success(user)))

        // when
        tested.invoke().test {
            assertTrue(awaitItem() is Resource.Success)
            awaitComplete()
        }

        verify(userRepository, only()).getUser()
    }

    @Test
    fun `invoke() error should return error response`() = runTest {
        // given
        whenever(userRepository.getUser()).thenReturn(flowOf(Resource.Error(ERROR_MESSAGE)))

        // when
        tested.invoke().test {
            assertTrue(awaitItem() is Resource.Error)
            awaitComplete()
        }

        verify(userRepository, only()).getUser()
    }

    companion object {
        private const val ERROR_MESSAGE = "ERROR_MESSAGE"
    }
}