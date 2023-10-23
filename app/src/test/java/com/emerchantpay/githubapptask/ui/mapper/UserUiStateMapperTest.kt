package com.emerchantpay.githubapptask.ui.mapper

import com.emerchantpay.githubapptask.common.Constants.UNKNOWN_ERROR_MESSAGE
import com.emerchantpay.githubapptask.common.Resource
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.generateUser
import com.emerchantpay.githubapptask.ui.model.UserUiState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class UserUiStateMapperTest(
    private val response: Resource<User>,
    private val uiState: UserUiState
) {

    private lateinit var tested: UserUiStateMapper

    @Before
    fun setUp() {
        tested = UserUiStateMapper()
    }

    @Test
    fun `mapToUiState() with loading should return loading ui state`() {
        // when
        val actual = tested.mapToUiState(response)

        // then
        assertEquals(uiState, actual)
    }

    companion object {
        private val USER = generateUser()

        @JvmStatic
        @Parameterized.Parameters(name = "{index}: response={0} -> uiState={1}")
        fun provideData(): Collection<Array<Any>> = listOf(
            arrayOf(Resource.Loading<User>(), UserUiState.Loading),
            arrayOf(Resource.Success(USER), UserUiState.Success(USER)),
            arrayOf(Resource.Success(null), UserUiState.Error(UNKNOWN_ERROR_MESSAGE)),
            arrayOf(Resource.Error<User>(UNKNOWN_ERROR_MESSAGE), UserUiState.Error(UNKNOWN_ERROR_MESSAGE)),
            arrayOf(Resource.Error<User>(""), UserUiState.Error(""))
        )
    }
}