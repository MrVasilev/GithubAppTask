package com.emerchantpay.githubapptask.domain.model

data class User(
    val id: Long,
    val login: String,
    val name: String,
    val avatarUrl: String,
    val followers: Int,
    val following: Int
)
