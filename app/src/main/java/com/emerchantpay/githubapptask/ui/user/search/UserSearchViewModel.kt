package com.emerchantpay.githubapptask.ui.user.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emerchantpay.githubapptask.data.repository.UserRepository
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.ui.common.UIState
import com.emerchantpay.githubapptask.ui.common.mapper.UiStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val uiStateMapper: UiStateMapper,
) : ViewModel() {

    private val _usersUiState = MutableLiveData<UIState<List<User>>>()
    val usersUiState: LiveData<UIState<List<User>>> = _usersUiState

    fun init() {
        getFollowingUsers()
    }

    private fun getFollowingUsers() {
        viewModelScope.launch {
            userRepository.getFollowingUsers().collect { usersResponse ->
                _usersUiState.postValue(uiStateMapper.mapToUiState(usersResponse))
            }
        }
    }
}