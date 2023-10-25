package com.emerchantpay.githubapptask.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emerchantpay.githubapptask.data.db.model.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Query("SELECT * FROM users WHERE is_owner = 1 LIMIT 1")
    fun getOwnerUser(): UserEntity?

    @Query("SELECT * FROM users WHERE _id = :id LIMIT 1")
    fun getUser(id: Long): UserEntity
}