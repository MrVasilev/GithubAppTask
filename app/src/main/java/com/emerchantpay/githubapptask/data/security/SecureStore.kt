package com.emerchantpay.githubapptask.data.security

import android.content.Context

interface SecureStore {

    fun putString(key: String, value: String?)

    fun getString(key: String, defaultValue: String?): String?

    fun remove(key: String)

    companion object {
        fun newInstance(context: Context): SecureStore = SecureStoreImpl(context)
    }
}