package com.emerchantpay.githubapptask.domain.usecase.base

import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.domain.model.User
import kotlinx.coroutines.flow.Flow

interface BaseGetUsersUseCase {

    suspend operator fun invoke(): Flow<Resource<List<User>>>

}