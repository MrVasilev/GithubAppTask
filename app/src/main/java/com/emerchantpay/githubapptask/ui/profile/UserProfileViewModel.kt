package com.emerchantpay.githubapptask.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.repository.RepoRepository
import com.emerchantpay.githubapptask.domain.model.Repository
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.domain.usecase.GetUserUseCase
import com.emerchantpay.githubapptask.ui.common.UIState
import com.emerchantpay.githubapptask.ui.profile.mapper.UiStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val repoRepository: RepoRepository,
    private val uiStateMapper: UiStateMapper,
) : ViewModel() {

    private val _userUiState = MutableLiveData<UIState<User>>()
    val userUiState: LiveData<UIState<User>> = _userUiState

    private val _reposUiState = MutableLiveData<UIState<List<Repository>>>()
    val reposUiState: LiveData<UIState<List<Repository>>> = _reposUiState

    init {
        getUserData()
        getUserOwnedAndStarredRepos()
    }

    private fun getUserData() {
        viewModelScope.launch {
            getUserUseCase().collect { response ->
                _userUiState.postValue(uiStateMapper.mapToUiState(response))
            }
        }
    }

    private fun getUserOwnedAndStarredRepos() {
        viewModelScope.launch {
            _reposUiState.postValue(UIState.Loading)

            repoRepository.getUserOwnedRepos().collect { ownedRepos ->
                val starredRepos = repoRepository.getUserStarredRepos().last()
                val allRepos = mergeRepositoryResults(ownedRepos, starredRepos)

                _reposUiState.postValue(uiStateMapper.mapToUiState(allRepos))
            }
        }
    }

    private fun mergeRepositoryResults(
        ownedReposResponse: Resource<List<Repository>>,
        starredReposResponse: Resource<List<Repository>>,
    ): Resource<List<Repository>> =
        when {
            ownedReposResponse is Resource.Success && starredReposResponse is Resource.Success -> {
                val ownerRepos: List<Repository> = ownedReposResponse.data.orEmpty()
                val starredRepos: List<Repository> = starredReposResponse.data.orEmpty()
                val allRepos: List<Repository> = ownerRepos.toMutableList().plus(starredRepos)

                Resource.Success(data = allRepos)
            }

            ownedReposResponse is Resource.Error && starredReposResponse is Resource.Success ->
                Resource.Success(data = starredReposResponse.data)

            starredReposResponse is Resource.Error && ownedReposResponse is Resource.Success ->
                Resource.Success(data = ownedReposResponse.data)

            ownedReposResponse is Resource.Error && starredReposResponse is Resource.Error -> ownedReposResponse

            else -> Resource.Loading
        }

}