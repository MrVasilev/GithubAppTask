package com.emerchantpay.githubapptask.ui.profile

import com.emerchantpay.githubapptask.common.Constants
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.domain.usecase.GetUserUseCase
import com.emerchantpay.githubapptask.generateUser
import com.emerchantpay.githubapptask.ui.common.UIState
import com.emerchantpay.githubapptask.ui.profile.mapper.UserUiStateMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserProfileViewModelTest {

    private val getUserUseCase = mock<GetUserUseCase>()
    private val userUiStateMapper = mock<UserUiStateMapper>()

    private lateinit var tested: UserProfileViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `init() with success should show success ui state`() =
        runTest {
            // given
            val user = generateUser()
            val response = Resource.Success(user)
            val uiState = UIState.Success(user)

            whenever(getUserUseCase.invoke()).thenReturn(flowOf(response))
            whenever(userUiStateMapper.mapToUiState(response)).thenReturn(uiState)

            // when
            tested = UserProfileViewModel(getUserUseCase, userUiStateMapper)

            // then
            inOrder(getUserUseCase, userUiStateMapper) {
                verify(getUserUseCase).invoke()
                verify(userUiStateMapper).mapToUiState(response)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `init() with error should show error ui state`() =
        runTest {
            // given
            val response = Resource.Error(Constants.UNKNOWN_ERROR_MESSAGE)
            val uiState = UIState.Error(Constants.UNKNOWN_ERROR_MESSAGE)

            whenever(getUserUseCase.invoke()).thenReturn(flowOf(response))
            whenever(userUiStateMapper.mapToUiState(response)).thenReturn(uiState)

            // when
            tested = UserProfileViewModel(getUserUseCase, userUiStateMapper)

            // then
            inOrder(getUserUseCase, userUiStateMapper) {
                verify(getUserUseCase).invoke()
                verify(userUiStateMapper).mapToUiState(response)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `init() while loading should show loading ui state`() =
        runTest {
            // given
            val response = Resource.Loading
            val uiState = UIState.Loading

            whenever(getUserUseCase.invoke()).thenReturn(flowOf(response))
            whenever(userUiStateMapper.mapToUiState(response)).thenReturn(uiState)

            // when
            tested = UserProfileViewModel(getUserUseCase, userUiStateMapper)

            // then
            inOrder(getUserUseCase, userUiStateMapper) {
                verify(getUserUseCase).invoke()
                verify(userUiStateMapper).mapToUiState(response)
                verifyNoMoreInteractions()
            }
        }
}