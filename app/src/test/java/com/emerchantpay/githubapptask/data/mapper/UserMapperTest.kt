package com.emerchantpay.githubapptask.data.mapper

import com.emerchantpay.githubapptask.data.model.UserResponse
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.generateUser
import com.emerchantpay.githubapptask.generateUserEmpty
import com.emerchantpay.githubapptask.generateUserResponse
import com.emerchantpay.githubapptask.generateUserResponseWithNulls
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserMapperTest {

    private lateinit var tested: UserMapper

    @Before
    fun setUp() {
        tested = UserMapper()
    }

    @Test
    fun `mapToDomainModel() with data should return correct data`() {
        // given
        val response: UserResponse = generateUserResponse()
        val expected: User = generateUser()

        // when
        val actual = tested.mapToDomainModel(response)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `mapToDomainModel() with null data should return empty data`() {
        // given
        val response: UserResponse = generateUserResponseWithNulls()
        val expected: User = generateUserEmpty()

        // when
        val actual = tested.mapToDomainModel(response)

        // then
        assertEquals(expected, actual)
    }

}