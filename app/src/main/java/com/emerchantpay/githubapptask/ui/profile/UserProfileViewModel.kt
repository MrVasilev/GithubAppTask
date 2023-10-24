package com.emerchantpay.githubapptask.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.domain.usecase.GetUserUseCase
import com.emerchantpay.githubapptask.ui.common.UIState
import com.emerchantpay.githubapptask.ui.profile.mapper.UserUiStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val userUiStateMapper: UserUiStateMapper
) : ViewModel() {

    private val _userUiState = MutableLiveData<UIState<User>>()
    val userUiState: LiveData<UIState<User>> = _userUiState

    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            getUserUseCase().collect { response ->
                _userUiState.postValue(userUiStateMapper.mapToUiState(response))
            }
        }
    }

}