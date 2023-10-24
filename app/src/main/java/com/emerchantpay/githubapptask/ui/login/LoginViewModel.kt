package com.emerchantpay.githubapptask.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.security.TokenProvider
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.domain.usecase.GetUserUseCase
import com.emerchantpay.githubapptask.ui.common.UIState
import com.emerchantpay.githubapptask.ui.profile.mapper.UserUiStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val tokenProvider: TokenProvider,
    private val uiMapper: UserUiStateMapper
) : ViewModel() {

    private val _userUiState = MutableLiveData<UIState<User>>()
    val userUiState: LiveData<UIState<User>> = _userUiState

    init {
        tokenProvider.getAccessToken()?.let {
            _userUiState.postValue(UIState.Loading)
            getUserData()
        }
    }

    fun onLoginButtonClicked(token: String) {
        if (token.isNotBlank()) {
            loginWithAccessToken(token)
        }
    }

    private fun loginWithAccessToken(token: String) {
        storeAccessToken(token)
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            getUserUseCase().collect { response ->
                if (response is Resource.Error) {
                    removeAccessToken()
                }
                _userUiState.postValue(uiMapper.mapToUiState(response))
            }
        }
    }

    private fun storeAccessToken(token: String) {
        if (token.isNotEmpty()) tokenProvider.setAccessToken(token)
    }

    private fun removeAccessToken(): Unit = tokenProvider.removeAccessToken()
}