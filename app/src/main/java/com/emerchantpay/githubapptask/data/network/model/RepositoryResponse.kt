package com.emerchantpay.githubapptask.data.model

import com.emerchantpay.githubapptask.domain.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositoryResponse(
    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "html_url")
    val url: String? = null,

    @Json(name = "owner")
    val owner: User,

    @Json(name = "contributors_url")
    val contributors: List<User>? = null
)
