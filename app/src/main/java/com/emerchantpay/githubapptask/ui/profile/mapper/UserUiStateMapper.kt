package com.emerchantpay.githubapptask.ui.profile.mapper

import com.emerchantpay.githubapptask.common.Constants
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.ui.common.UIState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserUiStateMapper @Inject constructor() {

    fun mapToUiState(response: Resource<User>): UIState<User> =
        when (response) {
            is Resource.Loading -> UIState.Loading
            is Resource.Success -> {
                response.data?.let { UIState.Success(data = response.data) }
                    ?: UIState.Error(message = Constants.UNKNOWN_ERROR_MESSAGE)
            }

            is Resource.Error -> UIState.Error(
                message = response.message ?: Constants.UNKNOWN_ERROR_MESSAGE
            )
        }
}