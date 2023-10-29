package com.emerchantpay.githubapptask.domain.usecase

import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.repository.UserRepository
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.domain.usecase.base.BaseGetUsersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFollowingUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseGetUsersUseCase {

    override suspend fun invoke(): Flow<Resource<List<User>>> = userRepository.getFollowingUsers()

}