package com.emerchantpay.githubapptask.domain.model

data class User(
    val id: Int,
    val login: String,
    val name: String,
    val avatarUrl: String,
    val followers: Int,
    val following: Int
)
