package com.emerchantpay.githubapptask.domain.model

data class Repository(
    val id: Long,
    val name: String,
    val url: String,
    val ownerId: Long,
    val ownerLogin: String,
    val isStarred: Boolean,
)
