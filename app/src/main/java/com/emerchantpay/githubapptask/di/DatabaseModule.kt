package com.emerchantpay.githubapptask.di

import android.app.Application
import androidx.room.Room
import com.emerchantpay.githubapptask.data.db.AppDatabase
import com.emerchantpay.githubapptask.data.db.dao.RepositoryDao
import com.emerchantpay.githubapptask.data.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "github_app_database"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(
            app.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideRepositoryDao(db: AppDatabase): RepositoryDao = db.repositoryDao()
}