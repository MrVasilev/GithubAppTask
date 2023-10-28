package com.emerchantpay.githubapptask.data.network.model

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
    val owner: UserResponse,
)
