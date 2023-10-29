package com.emerchantpay.githubapptask.ui.user.search

import com.emerchantpay.githubapptask.common.Constants
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.repository.UserRepository
import com.emerchantpay.githubapptask.domain.model.UserType
import com.emerchantpay.githubapptask.domain.usecase.GetFollowerUsersUseCase
import com.emerchantpay.githubapptask.domain.usecase.GetFollowingUsersUseCase
import com.emerchantpay.githubapptask.domain.usecase.base.UseCaseFactory
import com.emerchantpay.githubapptask.generateUser
import com.emerchantpay.githubapptask.ui.common.UIState
import com.emerchantpay.githubapptask.ui.common.mapper.UiStateMapper
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
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class UserSearchViewModelTest {

    private val useCaseFactory = mock<UseCaseFactory>()
    private val userRepository = mock<UserRepository>()
    private val uiStateMapper = mock<UiStateMapper>()

    private lateinit var tested: UserSearchViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        tested = UserSearchViewModel(useCaseFactory, uiStateMapper)
    }

    @Test
    fun `init with getFollowingUsers() and success response should show success ui state`() =
        runTest {
            // given
            val followingUsers = listOf(generateUser())
            val response = Resource.Success(followingUsers)
            val uiState = UIState.Success(followingUsers)
            val getUsersUseCase = GetFollowingUsersUseCase(userRepository)
            val userType = UserType.Following

            whenever(useCaseFactory.getUserUseCaseByType(userType)).thenReturn(getUsersUseCase)
            whenever(getUsersUseCase.invoke()).thenReturn(flowOf(response))
            whenever(uiStateMapper.mapToUiState(response)).thenReturn(uiState)

            // when
            tested.init(userType)

            inOrder(useCaseFactory, userRepository, uiStateMapper) {
                verify(useCaseFactory).getUserUseCaseByType(userType)
                verify(userRepository).getFollowingUsers()
                verify(uiStateMapper).mapToUiState(response)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `init with getFollowingUsers() call error should show user error ui state`() =
        runTest {
            // given
            val response = Resource.Error(Constants.UNKNOWN_ERROR_MESSAGE)
            val uiState = UIState.Error(Constants.UNKNOWN_ERROR_MESSAGE)
            val getUsersUseCase = GetFollowingUsersUseCase(userRepository)
            val userType = UserType.Following

            whenever(useCaseFactory.getUserUseCaseByType(userType)).thenReturn(getUsersUseCase)
            whenever(getUsersUseCase.invoke()).thenReturn(flowOf(response))
            whenever(uiStateMapper.mapToUiState(response)).thenReturn(uiState)

            // when
            tested.init(userType)

            inOrder(useCaseFactory, userRepository, uiStateMapper) {
                verify(useCaseFactory).getUserUseCaseByType(userType)
                verify(userRepository).getFollowingUsers()
                verify(uiStateMapper).mapToUiState(response)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `init with getFollowingUsers() while loading should show loading ui state`() =
        runTest {
            // given
            val response = Resource.Loading
            val uiState = UIState.Loading
            val getUsersUseCase = GetFollowingUsersUseCase(userRepository)
            val userType = UserType.Following

            whenever(useCaseFactory.getUserUseCaseByType(userType)).thenReturn(getUsersUseCase)
            whenever(getUsersUseCase.invoke()).thenReturn(flowOf(response))
            whenever(uiStateMapper.mapToUiState(response)).thenReturn(uiState)

            // when
            tested.init(userType)

            inOrder(useCaseFactory, userRepository, uiStateMapper) {
                verify(useCaseFactory).getUserUseCaseByType(userType)
                verify(userRepository).getFollowingUsers()
                verify(uiStateMapper).mapToUiState(response)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `init with getFollowerUsers() and success response should show success ui state`() =
        runTest {
            // given
            val followingUsers = listOf(generateUser())
            val response = Resource.Success(followingUsers)
            val uiState = UIState.Success(followingUsers)
            val getUsersUseCase = GetFollowerUsersUseCase(userRepository)
            val userType = UserType.Follower

            whenever(useCaseFactory.getUserUseCaseByType(userType)).thenReturn(getUsersUseCase)
            whenever(getUsersUseCase.invoke()).thenReturn(flowOf(response))
            whenever(uiStateMapper.mapToUiState(response)).thenReturn(uiState)

            // when
            tested.init(userType)

            inOrder(useCaseFactory, userRepository, uiStateMapper) {
                verify(useCaseFactory).getUserUseCaseByType(userType)
                verify(userRepository).getFollowerUsers()
                verify(uiStateMapper).mapToUiState(response)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `init with getFollowerUsers() call error should show user error ui state`() =
        runTest {
            // given
            val response = Resource.Error(Constants.UNKNOWN_ERROR_MESSAGE)
            val uiState = UIState.Error(Constants.UNKNOWN_ERROR_MESSAGE)
            val getUsersUseCase = GetFollowerUsersUseCase(userRepository)
            val userType = UserType.Follower

            whenever(useCaseFactory.getUserUseCaseByType(userType)).thenReturn(getUsersUseCase)
            whenever(getUsersUseCase.invoke()).thenReturn(flowOf(response))
            whenever(uiStateMapper.mapToUiState(response)).thenReturn(uiState)

            // when
            tested.init(userType)

            inOrder(useCaseFactory, userRepository, uiStateMapper) {
                verify(useCaseFactory).getUserUseCaseByType(userType)
                verify(userRepository).getFollowerUsers()
                verify(uiStateMapper).mapToUiState(response)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `init with getFollowerUsers() while loading should show loading ui state`() =
        runTest {
            // given
            val response = Resource.Loading
            val uiState = UIState.Loading
            val getUsersUseCase = GetFollowerUsersUseCase(userRepository)
            val userType = UserType.Follower

            whenever(useCaseFactory.getUserUseCaseByType(userType)).thenReturn(getUsersUseCase)
            whenever(getUsersUseCase.invoke()).thenReturn(flowOf(response))
            whenever(uiStateMapper.mapToUiState(response)).thenReturn(uiState)

            // when
            tested.init(userType)

            inOrder(useCaseFactory, userRepository, uiStateMapper) {
                verify(useCaseFactory).getUserUseCaseByType(userType)
                verify(userRepository).getFollowerUsers()
                verify(uiStateMapper).mapToUiState(response)
                verifyNoMoreInteractions()
            }
        }
}