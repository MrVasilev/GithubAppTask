package com.emerchantpay.githubapptask.domain.model

data class Repository(
    val id: Long,
    val name: String,
    val url: String,
    val owner: User,
    val contributors: List<User>
)
