package com.emerchantpay.githubapptask.ui.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emerchantpay.githubapptask.data.repository.RepoRepository
import com.emerchantpay.githubapptask.data.repository.UserRepository
import com.emerchantpay.githubapptask.domain.model.Repository
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.ui.common.UIState
import com.emerchantpay.githubapptask.ui.common.mapper.UiStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
    private val userRepository: UserRepository,
    private val uiStateMapper: UiStateMapper,
) : ViewModel() {

    private val _repoUiState = MutableLiveData<UIState<Repository>>()
    val repoUiState: LiveData<UIState<Repository>> = _repoUiState

    private val _userUiState = MutableLiveData<UIState<User>>()
    val userUiState: LiveData<UIState<User>> = _userUiState

    fun init(owner: String, repoId: Long) {
        getRepositoryById(repoId)
        getRepoOwnerData(owner)
    }

    private fun getRepositoryById(id: Long) {
        viewModelScope.launch {
            repoRepository.getRepoById(id).collect { repoResponse ->
                _repoUiState.postValue(uiStateMapper.mapToUiState(repoResponse))
            }
        }
    }

    private fun getRepoOwnerData(id: String) {
        viewModelScope.launch {
            userRepository.getUserByName(id).collect { response ->
                _userUiState.postValue(uiStateMapper.mapToUiState(response))
            }
        }
    }
}