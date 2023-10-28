package com.emerchantpay.githubapptask.data.db

import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.emerchantpay.githubapptask.data.db.dao.RepositoryDao
import com.emerchantpay.githubapptask.generateRepositoryDb
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class RepositoryDaoTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var tested: RepositoryDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        tested = appDatabase.repositoryDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun insertReposShouldStoreItInDb() {
        // given
        val expected =
            listOf(generateRepositoryDb(id = 111, isStarred = true), generateRepositoryDb())

        // when
        tested.insertRepos(expected)
        val actual = tested.getAllRepositories()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun getAllStarredRepositoriesShouldReturnOnlyStarredRepos() {
        // given
        val expected = generateRepositoryDb(id = 111, isStarred = true)
        val repos = listOf(generateRepositoryDb(), expected)

        // when
        tested.insertRepos(repos)
        val actual = tested.getAllStarredRepositories()

        // then
        assertEquals(listOf(expected), actual)
    }

    @Test
    fun getAllStarredRepositoriesWithNoStarredRepoShouldReturnEmptyList() {
        // given
        val repos = listOf(generateRepositoryDb(), generateRepositoryDb(id = 111))

        // when
        tested.insertRepos(repos)
        val actual = tested.getAllStarredRepositories()

        // then
        assertTrue(actual?.isEmpty() == true)
    }

    @Test
    fun getAllOwnedRepositoriesShouldReturnOnlyNOTStarredRepos() {
        // given
        val expected = generateRepositoryDb()
        val repos = listOf(expected, generateRepositoryDb(id = 111, isStarred = true))

        // when
        tested.insertRepos(repos)
        val actual = tested.getAllOwnedRepositories()

        // then
        assertEquals(listOf(expected), actual)
    }

    @Test
    fun getAllOwnedRepositoriesWithNoOwnedRepoShouldReturnEmptyList() {
        // given
        val repos = listOf(
            generateRepositoryDb(isStarred = true),
            generateRepositoryDb(id = 111, isStarred = true)
        )

        // when
        tested.insertRepos(repos)
        val actual = tested.getAllOwnedRepositories()

        // then
        assertTrue(actual?.isEmpty() == true)
    }

    @Test
    fun getRepoByIdNotInDbShouldReturnNull() {
        // when
        val actual = tested.getRepoById(12L)

        // then
        assertNull(actual)
    }
}