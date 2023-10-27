package com.emerchantpay.githubapptask.data.common

import com.emerchantpay.githubapptask.data.db.model.UserEntity
import com.emerchantpay.githubapptask.data.network.model.UserResponse
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.generateUser
import com.emerchantpay.githubapptask.generateUserDb
import com.emerchantpay.githubapptask.generateUserDbEmpty
import com.emerchantpay.githubapptask.generateUserEmpty
import com.emerchantpay.githubapptask.generateUserResponse
import com.emerchantpay.githubapptask.generateUserResponseWithNulls
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ModelExtensionTest {

    @Test
    fun `mapToDbModel() with data should return correct data`() {
        // given
        val response: UserResponse = generateUserResponse()
        val expected: UserEntity = generateUserDb()

        // when
        val actual = response.mapToDbModel()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `mapToDbModel() with null data should return empty data`() {
        // given
        val response: UserResponse = generateUserResponseWithNulls()
        val expected: UserEntity = generateUserDbEmpty()

        // when
        val actual = response.mapToDbModel()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `mapToDomainModel() with data should return correct data`() {
        // given
        val userEntity: UserEntity = generateUserDb()
        val expected: User = generateUser()

        // when
        val actual = userEntity.mapToDomainModel()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `mapToDomainModel() with null data should return empty data`() {
        // given
        val userEntity: UserEntity = generateUserDbEmpty()
        val expected: User = generateUserEmpty()

        // when
        val actual = userEntity.mapToDomainModel()

        // then
        assertEquals(expected, actual)
    }

}