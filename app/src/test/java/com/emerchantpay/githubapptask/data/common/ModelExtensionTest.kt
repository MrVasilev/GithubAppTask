package com.emerchantpay.githubapptask.data.common

import com.emerchantpay.githubapptask.data.db.model.RepositoryEntity
import com.emerchantpay.githubapptask.data.db.model.UserEntity
import com.emerchantpay.githubapptask.data.network.model.RepositoryResponse
import com.emerchantpay.githubapptask.data.network.model.UserResponse
import com.emerchantpay.githubapptask.domain.model.Repository
import com.emerchantpay.githubapptask.domain.model.User
import com.emerchantpay.githubapptask.generateRepository
import com.emerchantpay.githubapptask.generateRepositoryDb
import com.emerchantpay.githubapptask.generateRepositoryDbEmpty
import com.emerchantpay.githubapptask.generateRepositoryEmpty
import com.emerchantpay.githubapptask.generateRepositoryResponse
import com.emerchantpay.githubapptask.generateRepositoryResponseWithNulls
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
    fun `mapToDbModel() with user data should return correct user data`() {
        // given
        val response: UserResponse = generateUserResponse()
        val expected: UserEntity = generateUserDb()

        // when
        val actual = response.mapToDbModel()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `mapToDbModel() with null user data should return empty user data`() {
        // given
        val response: UserResponse = generateUserResponseWithNulls()
        val expected: UserEntity = generateUserDbEmpty()

        // when
        val actual = response.mapToDbModel()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `mapToDomainModel() with user data should return correct user data`() {
        // given
        val userEntity: UserEntity = generateUserDb()
        val expected: User = generateUser()

        // when
        val actual = userEntity.mapToDomainModel()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `mapToDomainModel() with null user data should return empty user data`() {
        // given
        val userEntity: UserEntity = generateUserDbEmpty()
        val expected: User = generateUserEmpty()

        // when
        val actual = userEntity.mapToDomainModel()

        // then
        assertEquals(expected, actual)
    }


    @Test
    fun `mapToDbModel() with repo data should return correct repo data`() {
        // given
        val response: RepositoryResponse = generateRepositoryResponse()
        val expected: RepositoryEntity = generateRepositoryDb()

        // when
        val actual = response.mapToDbModel()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `mapToDbModel() with null repo data should return empty repo data`() {
        // given
        val response: RepositoryResponse = generateRepositoryResponseWithNulls()
        val expected: RepositoryEntity = generateRepositoryDbEmpty()

        // when
        val actual = response.mapToDbModel()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `mapToDomainModel() with repo data should return correct repo data`() {
        // given
        val userEntity: RepositoryEntity = generateRepositoryDb()
        val expected: Repository = generateRepository()

        // when
        val actual = userEntity.mapToDomainModel()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `mapToDomainModel() with null repo data should return empty repo data`() {
        // given
        val userEntity: RepositoryEntity = generateRepositoryDbEmpty()
        val expected: Repository = generateRepositoryEmpty()

        // when
        val actual = userEntity.mapToDomainModel()

        // then
        assertEquals(expected, actual)
    }

}