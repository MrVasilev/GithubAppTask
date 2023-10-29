package com.emerchantpay.githubapptask.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "id")
    val id: Long,

    @Json(name = "login")
    val login: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "avatar_url")
    val avatarUrl: String? = null,

    @Json(name = "followers")
    val followers: Int? = null,

    @Json(name = "following")
    val following: Int? = null,
)
