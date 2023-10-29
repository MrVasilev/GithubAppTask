package com.emerchantpay.githubapptask.data.network

import com.emerchantpay.githubapptask.data.network.model.RepositoryResponse
import com.emerchantpay.githubapptask.data.network.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {

    @GET("user")
    suspend fun getUser(): UserResponse

    @GET("users/{name}")
    suspend fun getUserByName(@Path("name") name: String): UserResponse

    @GET("user/repos")
    suspend fun getUserRepos(): List<RepositoryResponse>

    @GET("user/starred")
    suspend fun getUserStarredRepos(): List<RepositoryResponse>

    @GET("user/following")
    suspend fun getFollowingUsers(): List<UserResponse>

    @GET("user/followers")
    suspend fun getFollowerUsers(): List<UserResponse>

    @GET("repos/{user}/{repo}/contributors")
    suspend fun getRepoContributors(
        @Path("user") user: String,
        @Path("repo") repo: String,
    ): List<UserResponse>
}