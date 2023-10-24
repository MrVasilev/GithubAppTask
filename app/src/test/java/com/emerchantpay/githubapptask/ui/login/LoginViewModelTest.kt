package com.emerchantpay.githubapptask.ui.login

import com.emerchantpay.githubapptask.common.Constants.UNKNOWN_ERROR_MESSAGE
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.security.TokenProvider
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
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.only
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    private val getUserUseCase = mock<GetUserUseCase>()
    private val tokenProvider = mock<TokenProvider>()
    private val userUiStateMapper = mock<UserUiStateMapper>()

    private lateinit var tested: LoginViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        tested = LoginViewModel(getUserUseCase, tokenProvider, userUiStateMapper)
    }

    @Test
    fun `onLoginButtonClicked() with correct access token should show success ui state`() =
        runTest {
            // given
            val user = generateUser()
            val response = Resource.Success(user)
            val uiState = UIState.Success(user)

            whenever(getUserUseCase.invoke()).thenReturn(flowOf(response))
            whenever(userUiStateMapper.mapToUiState(response)).thenReturn(uiState)

            // when
            tested.onLoginButtonClicked(ACCESS_TOKEN)

            // then
            inOrder(getUserUseCase, userUiStateMapper, tokenProvider) {
                verify(tokenProvider).setAccessToken(ACCESS_TOKEN)
                verify(getUserUseCase).invoke()
                verify(userUiStateMapper).mapToUiState(response)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `onLoginButtonClicked() with wrong access token should show error ui state`() =
        runTest {
            // given
            val response = Resource.Error(UNKNOWN_ERROR_MESSAGE)
            val uiState = UIState.Error(UNKNOWN_ERROR_MESSAGE)

            whenever(getUserUseCase.invoke()).thenReturn(flowOf(response))
            whenever(userUiStateMapper.mapToUiState(response)).thenReturn(uiState)

            // when
            tested.onLoginButtonClicked(ACCESS_TOKEN)

            // then
            inOrder(getUserUseCase, userUiStateMapper, tokenProvider) {
                verify(tokenProvider).setAccessToken(ACCESS_TOKEN)
                verify(getUserUseCase).invoke()
                verify(userUiStateMapper).mapToUiState(response)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `onLoginButtonClicked() while loading should show loading ui state`() =
        runTest {
            // given
            val response = Resource.Loading
            val uiState = UIState.Loading

            whenever(getUserUseCase.invoke()).thenReturn(flowOf(response))
            whenever(userUiStateMapper.mapToUiState(response)).thenReturn(uiState)

            // when
            tested.onLoginButtonClicked(ACCESS_TOKEN)

            // then
            inOrder(getUserUseCase, userUiStateMapper, tokenProvider) {
                verify(tokenProvider).setAccessToken(ACCESS_TOKEN)
                verify(getUserUseCase).invoke()
                verify(userUiStateMapper).mapToUiState(response)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `onLoginButtonClicked() with empty access token should do nothing`() {
        // when
        tested.onLoginButtonClicked("")

        // then
        verify(tokenProvider, only()).getAccessToken()
    }

    companion object {
        private const val ACCESS_TOKEN = "access_token"
    }
}