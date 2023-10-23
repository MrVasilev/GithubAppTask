package com.emerchantpay.githubapptask.ui.mapper

import com.emerchantpay.githubapptask.common.Constants
import com.emerchantpay.githubapptask.common.Resource
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.ui.model.UserUiState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserUiStateMapper @Inject constructor() {
    fun mapToUiState(response: Resource<User>): UserUiState = when (response) {
        is Resource.Loading -> UserUiState.Loading
        is Resource.Success -> response.data?.let { UserUiState.Success(it) }
            ?: UserUiState.Error(Constants.UNKNOWN_ERROR_MESSAGE)

        is Resource.Error -> UserUiState.Error(response.message.orEmpty())
    }
}