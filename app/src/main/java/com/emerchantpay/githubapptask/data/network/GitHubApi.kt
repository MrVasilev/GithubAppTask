package com.emerchantpay.githubapptask.data.network

import com.emerchantpay.githubapptask.data.network.model.RepositoryResponse
import com.emerchantpay.githubapptask.data.network.model.UserResponse
import retrofit2.http.GET

interface GitHubApi {

    @GET("user")
    suspend fun getUser(): UserResponse

    @GET("user/repos")
    suspend fun getUserRepos(): List<RepositoryResponse>

    @GET("user/starred")
    suspend fun getUserStarredRepos(): List<RepositoryResponse>

    @GET("user/following")
    suspend fun getFollowingUsers(): List<UserResponse>
}