package com.emerchantpay.githubapptask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emerchantpay.githubapptask.data.db.dao.RepositoryDao
import com.emerchantpay.githubapptask.data.db.dao.UserDao
import com.emerchantpay.githubapptask.data.db.model.RepositoryEntity
import com.emerchantpay.githubapptask.data.db.model.UserEntity

@Database(
    entities = [UserEntity::class, RepositoryEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun repositoryDao(): RepositoryDao
}