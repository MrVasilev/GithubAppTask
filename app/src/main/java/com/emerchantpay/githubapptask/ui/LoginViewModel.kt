package com.emerchantpay.githubapptask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emerchantpay.githubapptask.common.Resource
import com.emerchantpay.githubapptask.data.security.TokenProvider
import com.emerchantpay.githubapptask.domain.usecase.GetUserUseCase
import com.emerchantpay.githubapptask.ui.mapper.UserUiStateMapper
import com.emerchantpay.githubapptask.ui.model.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val tokenProvider: TokenProvider,
    private val userUiStateMapper: UserUiStateMapper
) : ViewModel() {

    private val _userUiState = MutableLiveData<UserUiState>()
    val userUiState: LiveData<UserUiState> = _userUiState

    init {
        tokenProvider.getAccessToken()?.let {
            _userUiState.postValue(UserUiState.Loading)
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

                _userUiState.postValue(userUiStateMapper.mapToUiState(response))
            }
        }
    }

    private fun storeAccessToken(token: String) {
        if (token.isNotEmpty()) tokenProvider.setAccessToken(token)
    }

    private fun removeAccessToken(): Unit = tokenProvider.removeAccessToken()
}