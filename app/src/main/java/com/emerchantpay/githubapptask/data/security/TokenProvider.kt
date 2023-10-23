package com.emerchantpay.githubapptask.data.security

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenProvider @Inject constructor(private val secureStore: SecureStore) {

    fun getAccessToken(): String? = secureStore.getString(KEY_ACCESS_TOKEN, null)

    fun setAccessToken(value: String) = secureStore.putString(KEY_ACCESS_TOKEN, value)

    fun removeAccessToken() = secureStore.remove(KEY_ACCESS_TOKEN)

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
    }
}