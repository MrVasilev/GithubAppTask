package com.emerchantpay.githubapptask.di

import com.emerchantpay.githubapptask.BuildConfig
import com.emerchantpay.githubapptask.common.Constants
import com.emerchantpay.githubapptask.data.network.GitHubApi
import com.emerchantpay.githubapptask.data.network.interceptor.AuthenticationInterceptor
import com.emerchantpay.githubapptask.data.network.interceptor.MandatoryHeaderInterceptor
import com.emerchantpay.githubapptask.data.security.TokenProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val READ_TIMEOUT_SECONDS = 30L
private const val CONNECT_TIMEOUT_SECONDS = 30L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGitHubApi(retrofit: Retrofit): GitHubApi = retrofit.create(GitHubApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        mandatoryHeaderInterceptor: MandatoryHeaderInterceptor,
        authInterceptor: AuthenticationInterceptor
    ): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(mandatoryHeaderInterceptor)
            .addInterceptor(authInterceptor)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }.let {
                okHttpBuilder.addInterceptor(it)
            }
        }

        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideMandatoryHeaderInterceptor(): MandatoryHeaderInterceptor =
        MandatoryHeaderInterceptor()

    @Provides
    @Singleton
    fun provideAuthenticationInterceptor(tokenProvider: TokenProvider): AuthenticationInterceptor =
        AuthenticationInterceptor(tokenProvider)
}