package com.emerchantpay.githubapptask.data.db

import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.emerchantpay.githubapptask.data.db.dao.UserDao
import com.emerchantpay.githubapptask.generateUserDb
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class UserDaoTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var tested: UserDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        tested = appDatabase.userDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun insertUserShouldStoreItInDb() {
        // given
        val expected = generateUserDb()

        // when
        tested.insertUsers(expected)
        val actual = tested.getUserById(expected.id)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun getOwnerUserShouldReturnLoggedUser() {
        // given
        val expected = generateUserDb(isOwner = true)

        // when
        tested.insertUsers(expected)
        val actual = tested.getOwnerUser()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun getOwnerUserNotInDbShouldReturnNull() {
        // given
        val user = generateUserDb(isOwner = false)

        // when
        tested.insertUsers(user)
        val actual = tested.getOwnerUser()

        // then
        assertNull(actual)
    }

    @Test
    fun getUserNotInDbShouldReturnNull() {
        // when
        val actual = tested.getUserById(12)

        // then
        assertNull(actual)
    }
}