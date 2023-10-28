package com.emerchantpay.githubapptask.ui.profile

import com.emerchantpay.githubapptask.common.Constants
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.repository.RepoRepository
import com.emerchantpay.githubapptask.domain.model.Repository
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.domain.usecase.GetUserUseCase
import com.emerchantpay.githubapptask.generateRepository
import com.emerchantpay.githubapptask.generateUser
import com.emerchantpay.githubapptask.ui.common.UIState
import com.emerchantpay.githubapptask.ui.profile.mapper.UiStateMapper
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
class UserProfileViewModelTest {

    private val getUserUseCase = mock<GetUserUseCase>()
    private val repoRepository = mock<RepoRepository>()
    private val uiStateMapper = mock<UiStateMapper>()

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
            val userResponse = mockGetUserSuccess()
            val ownedRepoList = mockGetOwnedReposSuccess()
            val starredRepoList = mockGetStarredReposSuccess()
            val reposResponse = Resource.Success(ownedRepoList + starredRepoList)
            val reposUiState = UIState.Success(ownedRepoList + starredRepoList)

            whenever(uiStateMapper.mapToUiState(reposResponse)).thenReturn(reposUiState)

            // when
            tested = UserProfileViewModel(getUserUseCase, repoRepository, uiStateMapper)

            // then
            inOrder(getUserUseCase, repoRepository, uiStateMapper) {
                verify(getUserUseCase).invoke()
                verify(uiStateMapper).mapToUiState(userResponse)
                verify(repoRepository).getUserOwnedRepos()
                verify(repoRepository).getUserStarredRepos()
                verify(uiStateMapper).mapToUiState(reposResponse)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `init() with get user call error should show user error ui state`() =
        runTest {
            // given
            val userResponse = Resource.Error(Constants.UNKNOWN_ERROR_MESSAGE)
            val uiState = UIState.Error(Constants.UNKNOWN_ERROR_MESSAGE)
            val ownedRepoList = mockGetOwnedReposSuccess()
            val starredRepoList = mockGetStarredReposSuccess()
            val reposResponse = Resource.Success(ownedRepoList + starredRepoList)
            val reposUiState = UIState.Success(ownedRepoList + starredRepoList)

            whenever(uiStateMapper.mapToUiState(reposResponse)).thenReturn(reposUiState)
            whenever(getUserUseCase.invoke()).thenReturn(flowOf(userResponse))
            whenever(uiStateMapper.mapToUiState(userResponse)).thenReturn(uiState)

            // when
            tested = UserProfileViewModel(getUserUseCase, repoRepository, uiStateMapper)

            // then
            inOrder(getUserUseCase, repoRepository, uiStateMapper) {
                verify(getUserUseCase).invoke()
                verify(uiStateMapper).mapToUiState(userResponse)
                verify(repoRepository).getUserOwnedRepos()
                verify(repoRepository).getUserStarredRepos()
                verify(uiStateMapper).mapToUiState(reposResponse)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `init() with get owned repos call error should only starred repos success ui state`() =
        runTest {
            // given
            val response = Resource.Error(Constants.UNKNOWN_ERROR_MESSAGE)
            val userResponse = mockGetUserSuccess()
            val starredRepoList = mockGetStarredReposSuccess()
            val reposResponse = Resource.Success(starredRepoList)
            val reposUiState = UIState.Success(starredRepoList)

            whenever(repoRepository.getUserOwnedRepos()).thenReturn(flowOf(response))
            whenever(uiStateMapper.mapToUiState(reposResponse)).thenReturn(reposUiState)

            // when
            tested = UserProfileViewModel(getUserUseCase, repoRepository, uiStateMapper)

            // then
            inOrder(getUserUseCase, repoRepository, uiStateMapper) {
                verify(getUserUseCase).invoke()
                verify(uiStateMapper).mapToUiState(userResponse)
                verify(repoRepository).getUserOwnedRepos()
                verify(repoRepository).getUserStarredRepos()
                verify(uiStateMapper).mapToUiState(reposResponse)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `init() with get starred repos call error should only show owned repos success ui state`() =
        runTest {
            // given
            val response = Resource.Error(Constants.UNKNOWN_ERROR_MESSAGE)
            val userResponse = mockGetUserSuccess()
            val ownedRepoList = mockGetOwnedReposSuccess()
            val reposResponse = Resource.Success(ownedRepoList)
            val reposUiState = UIState.Success(ownedRepoList)

            whenever(repoRepository.getUserStarredRepos()).thenReturn(flowOf(response))
            whenever(uiStateMapper.mapToUiState(reposResponse)).thenReturn(reposUiState)

            // when
            tested = UserProfileViewModel(getUserUseCase, repoRepository, uiStateMapper)

            // then
            inOrder(getUserUseCase, repoRepository, uiStateMapper) {
                verify(getUserUseCase).invoke()
                verify(uiStateMapper).mapToUiState(userResponse)
                verify(repoRepository).getUserOwnedRepos()
                verify(repoRepository).getUserStarredRepos()
                verify(uiStateMapper).mapToUiState(reposResponse)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `init() with both repos calls error should only show error ui state`() =
        runTest {
            // given
            val response = Resource.Error(Constants.UNKNOWN_ERROR_MESSAGE)
            val uiState = UIState.Error(Constants.UNKNOWN_ERROR_MESSAGE)
            val userResponse = mockGetUserSuccess()

            whenever(getUserUseCase.invoke()).thenReturn(flowOf(userResponse))
            whenever(repoRepository.getUserOwnedRepos()).thenReturn(flowOf(response))
            whenever(repoRepository.getUserStarredRepos()).thenReturn(flowOf(response))
            whenever(uiStateMapper.mapToUiState(response)).thenReturn(uiState)

            // when
            tested = UserProfileViewModel(getUserUseCase, repoRepository, uiStateMapper)

            // then
            inOrder(getUserUseCase, repoRepository, uiStateMapper) {
                verify(getUserUseCase).invoke()
                verify(uiStateMapper).mapToUiState(userResponse)
                verify(repoRepository).getUserOwnedRepos()
                verify(repoRepository).getUserStarredRepos()
                verify(uiStateMapper).mapToUiState(response)
                verifyNoMoreInteractions()
            }
        }

    @Test
    fun `init() while loading should show loading ui state`() =
        runTest {
            // given
            val response = mockGetUserLoading()
            val ownedReposResponse = mockGetOwnedReposLoading()
            val starredReposResponse = mockGetStarredReposLoading()
            val uiState = UIState.Loading

            whenever(getUserUseCase.invoke()).thenReturn(flowOf(response))
            whenever(uiStateMapper.mapToUiState(response)).thenReturn(uiState)
            whenever(uiStateMapper.mapToUiState(ownedReposResponse)).thenReturn(uiState)
            whenever(uiStateMapper.mapToUiState(starredReposResponse)).thenReturn(uiState)

            // when
            tested = UserProfileViewModel(getUserUseCase, repoRepository, uiStateMapper)

            // then
            inOrder(getUserUseCase, repoRepository, uiStateMapper) {
                verify(getUserUseCase).invoke()
                verify(uiStateMapper).mapToUiState(response)
                verify(repoRepository).getUserOwnedRepos()
                verify(repoRepository).getUserStarredRepos()
                verify(uiStateMapper).mapToUiState(ownedReposResponse)
                verifyNoMoreInteractions()
            }
        }

    private suspend fun mockGetUserSuccess(): Resource.Success<User> {
        val user = generateUser()
        val userResponse = Resource.Success(user)
        val userUiState = UIState.Success(user)

        whenever(getUserUseCase.invoke()).thenReturn(flowOf(userResponse))
        whenever(uiStateMapper.mapToUiState(userResponse)).thenReturn(userUiState)

        return userResponse
    }

    private suspend fun mockGetUserLoading(): Resource.Loading {
        val userResponse = Resource.Loading
        val userUiState = UIState.Loading

        whenever(getUserUseCase.invoke()).thenReturn(flowOf(userResponse))
        whenever(uiStateMapper.mapToUiState(userResponse)).thenReturn(userUiState)

        return userResponse
    }

    private suspend fun mockGetOwnedReposSuccess(): List<Repository> {
        val ownedRepoList = listOf(generateRepository(), generateRepository(isStarred = true))
        val ownedReposResponse = Resource.Success(ownedRepoList)

        whenever(repoRepository.getUserOwnedRepos()).thenReturn(flowOf(ownedReposResponse))

        return ownedRepoList
    }

    private suspend fun mockGetOwnedReposLoading(): Resource.Loading {
        val ownedReposResponse = Resource.Loading

        whenever(repoRepository.getUserOwnedRepos()).thenReturn(flowOf(ownedReposResponse))

        return ownedReposResponse
    }

    private suspend fun mockGetStarredReposSuccess(): List<Repository> {
        val starredRepoList = listOf(generateRepository(isStarred = true), generateRepository())
        val starredReposResponse = Resource.Success(starredRepoList)

        whenever(repoRepository.getUserStarredRepos()).thenReturn(flowOf(starredReposResponse))

        return starredRepoList
    }

    private suspend fun mockGetStarredReposLoading(): Resource.Loading {
        val ownedReposResponse = Resource.Loading

        whenever(repoRepository.getUserStarredRepos()).thenReturn(flowOf(ownedReposResponse))

        return ownedReposResponse
    }
}