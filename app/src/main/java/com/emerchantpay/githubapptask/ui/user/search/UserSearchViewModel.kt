package com.emerchantpay.githubapptask.ui.user.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.domain.model.UserType
import com.emerchantpay.githubapptask.domain.usecase.base.BaseGetUsersUseCase
import com.emerchantpay.githubapptask.domain.usecase.base.UseCaseFactory
import com.emerchantpay.githubapptask.ui.common.UIState
import com.emerchantpay.githubapptask.ui.common.mapper.UiStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val useCaseFactory: UseCaseFactory,
    private val uiStateMapper: UiStateMapper,
) : ViewModel() {

    private val _usersUiState = MutableLiveData<UIState<List<User>>>()
    val usersUiState: LiveData<UIState<List<User>>> = _usersUiState

    private lateinit var getUsersUseCase: BaseGetUsersUseCase

    fun init(userType: UserType) {
        getAllUsersByType(userType)
    }

    private fun getAllUsersByType(userType: UserType) {
        getUsersUseCase = useCaseFactory.getUserUseCaseByType(userType)
        val user = if (userType is UserType.RepoContributions) userType.user else ""
        val repo = if (userType is UserType.RepoContributions) userType.repo else ""

        viewModelScope.launch {
            getUsersUseCase.invoke(user, repo).collect { usersResponse ->
                _usersUiState.postValue(uiStateMapper.mapToUiState(usersResponse))
            }
        }
    }
}