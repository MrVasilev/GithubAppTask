package com.emerchantpay.githubapptask.data.network.interceptor

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.only
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class MandatoryHeaderInterceptorTest : BaseInterceptorTest() {

    private lateinit var tested: MandatoryHeaderInterceptor

    @Before
    override fun setUp() {
        super.setUp()

        tested = MandatoryHeaderInterceptor()
    }

    @Test
    fun `intercept() with endpoint should add access header`() {
        // when
        val actual = tested.intercept(interceptorChain)

        // then
        verify(requestBuilder, only()).addHeader(HEADER_ACCEPT, HEADER_ACCEPT_VALUE)
        assertEquals(interceptorChain.proceed(request), actual)
    }

    companion object {
        private const val HEADER_ACCEPT = "Accept"
        private const val HEADER_ACCEPT_VALUE = "application/vnd.github+json"
    }

}