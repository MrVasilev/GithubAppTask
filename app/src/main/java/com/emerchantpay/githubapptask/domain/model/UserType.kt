package com.emerchantpay.githubapptask.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class UserType : Parcelable {
    data object Following : UserType()
    data object Follower : UserType()
    data class RepoContributions(val user: String, val repo: String) : UserType()
}