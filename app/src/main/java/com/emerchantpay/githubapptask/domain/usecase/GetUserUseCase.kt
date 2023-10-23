package com.emerchantpay.githubapptask.domain.usecase

import com.emerchantpay.githubapptask.common.Resource
import com.emerchantpay.githubapptask.data.repository.UserRepository
import com.emerchantpay.githubapptask.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Flow<Resource<User>> = userRepository.getUser()

}