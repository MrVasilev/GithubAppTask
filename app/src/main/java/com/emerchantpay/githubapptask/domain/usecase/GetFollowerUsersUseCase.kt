package com.emerchantpay.githubapptask.domain.usecase

import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.data.repository.UserRepository
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.domain.usecase.base.BaseGetUsersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFollowerUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : BaseGetUsersUseCase {

    override suspend fun invoke(user: String, repo: String): Flow<Resource<List<User>>> = userRepository.getFollowerUsers()
}