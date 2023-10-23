package com.emerchantpay.githubapptask.data.network.interceptor

import com.emerchantpay.githubapptask.data.security.TokenProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.only
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class AuthenticationInterceptorTest : BaseInterceptorTest() {

    private val tokenProvider = mock<TokenProvider>()

    private lateinit var tested: AuthenticationInterceptor

    @Before
    override fun setUp() {
        super.setUp()

        tested = AuthenticationInterceptor(tokenProvider)
    }

    @Test
    fun `intercept() with endpoint should add auth header`() {
        // given
        whenever(tokenProvider.getAccessToken()).thenReturn(ACCESS_TOKEN)

        // when
        val actual = tested.intercept(interceptorChain)

        // then
        verify(requestBuilder, only()).addHeader(HEADER_AUTHENTICATION, HEADER_AUTHENTICATION_VALUE)
        assertEquals(interceptorChain.proceed(request), actual)
    }

    @Test
    fun `intercept() with endpoint should not add auth header when access token is null`() {
        // given
        whenever(tokenProvider.getAccessToken()).thenReturn(null)

        // when
        val actual = tested.intercept(interceptorChain)

        // then
        verify(requestBuilder, never()).addHeader(eq(HEADER_AUTHENTICATION), any())
        assertEquals(interceptorChain.proceed(request), actual)
    }

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val HEADER_AUTHENTICATION = "Authorization"
        private const val HEADER_AUTHENTICATION_VALUE = "Bearer $ACCESS_TOKEN"
    }
}