package com.emerchantpay.githubapptask.di

import android.app.Application
import com.emerchantpay.githubapptask.data.security.SecureStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {

    @Provides
    @Singleton
    fun provideSecureStore(app: Application): SecureStore =
        SecureStore.newInstance(app.applicationContext)
}