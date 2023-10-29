package com.emerchantpay.githubapptask.ui.common.mapper

import com.emerchantpay.githubapptask.common.Constants.UNKNOWN_ERROR_MESSAGE
import com.emerchantpay.githubapptask.data.common.Resource
import com.emerchantpay.githubapptask.domain.model.Repository
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.generateRepository
import com.emerchantpay.githubapptask.generateUser
import com.emerchantpay.githubapptask.ui.common.UIState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class UiStateMapperTest {

    @RunWith(Parameterized::class)
    class UserUiStateMapperTest(
        private val response: Resource<User>,
        private val uiState: UIState<User>,
    ) {

        private lateinit var tested: UiStateMapper

        @Before
        fun setUp() {
            tested = UiStateMapper()
        }

        @Test
        fun `mapToUiState() with user data should return correct ui state`() {
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
                arrayOf(Resource.Loading, UIState.Loading),
                arrayOf(Resource.Success(USER), UIState.Success(USER)),
                arrayOf(Resource.Success(null), UIState.Error(UNKNOWN_ERROR_MESSAGE)),
                arrayOf(
                    Resource.Error(UNKNOWN_ERROR_MESSAGE),
                    UIState.Error(UNKNOWN_ERROR_MESSAGE)
                ),
                arrayOf(Resource.Error(""), UIState.Error(""))
            )
        }
    }

    @RunWith(Parameterized::class)
    class ReposUiStateMapperTest(
        private val response: Resource<List<Repository>>,
        private val uiState: UIState<List<Repository>>,
    ) {

        private lateinit var tested: UiStateMapper

        @Before
        fun setUp() {
            tested = UiStateMapper()
        }

        @Test
        fun `mapToUiState() with repos data should return correct ui state`() {
            // when
            val actual = tested.mapToUiState(response)

            // then
            assertEquals(uiState, actual)
        }

        companion object {
            private val repositories =
                listOf(generateRepository(), generateRepository(isStarred = true))

            @JvmStatic
            @Parameterized.Parameters(name = "{index}: response={0} -> uiState={1}")
            fun provideData(): Collection<Array<Any>> = listOf(
                arrayOf(Resource.Loading, UIState.Loading),
                arrayOf(Resource.Success(repositories), UIState.Success(repositories)),
                arrayOf(Resource.Success(null), UIState.Error(UNKNOWN_ERROR_MESSAGE)),
                arrayOf(
                    Resource.Error(UNKNOWN_ERROR_MESSAGE),
                    UIState.Error(UNKNOWN_ERROR_MESSAGE)
                ),
                arrayOf(Resource.Error(""), UIState.Error(""))
            )
        }
    }
}
