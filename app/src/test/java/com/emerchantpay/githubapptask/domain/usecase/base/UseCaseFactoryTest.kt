package com.emerchantpay.githubapptask.domain.usecase.base

import com.emerchantpay.githubapptask.data.repository.UserRepository
import com.emerchantpay.githubapptask.domain.model.UserType
import com.emerchantpay.githubapptask.domain.usecase.GetFollowerUsersUseCase
import com.emerchantpay.githubapptask.domain.usecase.GetFollowingUsersUseCase
import com.emerchantpay.githubapptask.domain.usecase.GetRepoContributorsUseCase
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.kotlin.mock

@RunWith(Parameterized::class)
class UseCaseFactoryTest(
    private val userType: UserType,
    private val expected: Class<BaseGetUsersUseCase>,
) {

    private val userRepository = mock<UserRepository>()

    private lateinit var tested: UseCaseFactory

    @Before
    fun setUp() {
        tested = UseCaseFactory(userRepository)
    }

    @Test
    fun getUserUseCaseByType() {
        // when
        val actual = tested.getUserUseCaseByType(userType)

        // then
        assertTrue(actual::class.java == expected)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: userType={0} -> useCase={1}")
        fun provideData(): Collection<Array<Any>> = listOf(
            arrayOf(UserType.FOLLOWING, GetFollowingUsersUseCase::class.java),
            arrayOf(UserType.FOLLOWER, GetFollowerUsersUseCase::class.java),
            arrayOf(UserType.REPO_CONTRIBUTORS, GetRepoContributorsUseCase::class.java)
        )
    }
}