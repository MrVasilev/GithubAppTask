package com.emerchantpay.githubapptask.data.network

import com.emerchantpay.githubapptask.data.model.UserResponse
import retrofit2.http.GET

interface GitHubApi {

    @GET("user")
    suspend fun getUser(): UserResponse
}