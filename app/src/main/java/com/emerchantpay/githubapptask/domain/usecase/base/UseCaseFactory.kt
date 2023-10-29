package com.emerchantpay.githubapptask.domain.usecase.base

import com.emerchantpay.githubapptask.data.repository.UserRepository
import com.emerchantpay.githubapptask.domain.model.UserType
import com.emerchantpay.githubapptask.domain.usecase.GetFollowerUsersUseCase
import com.emerchantpay.githubapptask.domain.usecase.GetFollowingUsersUseCase
import com.emerchantpay.githubapptask.domain.usecase.GetRepoContributorsUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UseCaseFactory @Inject constructor(private val userRepository: UserRepository) {

    fun getUserUseCaseByType(userType: UserType): BaseGetUsersUseCase =
        when (userType) {
            UserType.FOLLOWING -> GetFollowingUsersUseCase(userRepository)
            UserType.FOLLOWER -> GetFollowerUsersUseCase(userRepository)
            UserType.REPO_CONTRIBUTORS -> GetRepoContributorsUseCase(userRepository)
        }
}