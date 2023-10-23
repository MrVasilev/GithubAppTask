package com.emerchantpay.githubapptask.ui.model

import com.emerchantpay.githubapptask.domain.model.User

sealed interface UserUiState {
    data object Loading : UserUiState
    data class Success(val user: User) : UserUiState
    data class Error(val message: String) : UserUiState
}