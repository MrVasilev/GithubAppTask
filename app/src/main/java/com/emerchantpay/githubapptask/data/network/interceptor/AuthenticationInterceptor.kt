package com.emerchantpay.githubapptask.data.network.interceptor

import com.emerchantpay.githubapptask.data.security.TokenProvider
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthenticationInterceptor(private val tokenProvider: TokenProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(chain.request().addAuthenticationHeader(tokenProvider.getAccessToken()))

    private fun Request.addAuthenticationHeader(token: String?): Request =
        token?.let {
            newBuilder()
                .addHeader(HEADER_AUTHENTICATION, "$BEARER ${tokenProvider.getAccessToken()}")
                .build()
        } ?: this

    companion object {
        private const val HEADER_AUTHENTICATION = "Authorization"
        private const val BEARER = "Bearer"
    }
}