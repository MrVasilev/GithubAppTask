package com.emerchantpay.githubapptask.data.security

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.only
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class TokenProviderTest {

    private val secureStore = mock<SecureStore>()

    private lateinit var tested: TokenProvider

    @Before
    fun setUp() {
        tested = TokenProvider(secureStore)
    }

    @Test
    fun `getAccessToken() should return correct access token`() {
        // when
        whenever(secureStore.getString(KEY_ACCESS_TOKEN, null)).thenReturn(ACCESS_TOKEN)

        // when
        val actual = tested.getAccessToken()

        // then
        verify(secureStore, only()).getString(KEY_ACCESS_TOKEN, null)
        assertEquals(ACCESS_TOKEN, actual)
    }

    @Test
    fun `getAccessToken() should return null when no access token stored`() {
        // when
        whenever(secureStore.getString(KEY_ACCESS_TOKEN, null)).thenReturn(null)

        // when
        val actual = tested.getAccessToken()

        // then
        verify(secureStore, only()).getString(KEY_ACCESS_TOKEN, null)
        assertNull(actual)
    }

    @Test
    fun `setAccessToken() should update access token`() {
        // when
        whenever(secureStore.getString(KEY_ACCESS_TOKEN, null)).thenReturn(ACCESS_TOKEN)

        // when
        tested.setAccessToken(ACCESS_TOKEN)

        // then
        verify(secureStore, only()).putString(KEY_ACCESS_TOKEN, ACCESS_TOKEN)
        assertEquals(ACCESS_TOKEN, tested.getAccessToken())
    }

    @Test
    fun `removeAccessToken() should clear access token`() {
        // when
        whenever(secureStore.getString(KEY_ACCESS_TOKEN, null)).thenReturn(null)

        // when
        tested.removeAccessToken()

        // then
        verify(secureStore, only()).remove(KEY_ACCESS_TOKEN)
        assertEquals(null, tested.getAccessToken())
    }

    companion object {
        private const val ACCESS_TOKEN = "access_token_value"
        private const val KEY_ACCESS_TOKEN = "access_token"
    }
}