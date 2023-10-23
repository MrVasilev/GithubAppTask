package com.emerchantpay.githubapptask.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class MandatoryHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(HEADER_ACCEPT, HEADER_ACCEPT_VALUE)
            .build()

        return chain.proceed(request)
    }

    companion object {
        private const val HEADER_ACCEPT = "Accept"
        private const val HEADER_ACCEPT_VALUE = "application/vnd.github+json"
    }
}