package com.emerchantpay.githubapptask.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import org.junit.Before
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

open class BaseInterceptorTest {

    protected val interceptorChain = mock<Interceptor.Chain>()
    protected val requestBuilder = mock<Request.Builder>()
    protected val request = mock<Request>()

    @Before
    open fun setUp() {
        mockRequest()
    }

    private fun mockRequest() {
        whenever(interceptorChain.request()).thenReturn(request)
        whenever(request.newBuilder()).thenReturn(requestBuilder)
        whenever(requestBuilder.addHeader(any(), any())).thenReturn(mock())
    }
}