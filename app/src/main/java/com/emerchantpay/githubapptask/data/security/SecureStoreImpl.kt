package com.emerchantpay.githubapptask.data.security

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

private const val FILE_NAME = "secure_store"

internal class SecureStoreImpl(context: Context) : SecureStore {

    private val sharedPreferences: SharedPreferences by lazy {
        createEncryptedSharedPrefs(context)
    }

    override fun putString(key: String, value: String?): Unit =
        sharedPreferences.edit().putString(key, value).apply()

    override fun getString(key: String, defaultValue: String?): String? =
        sharedPreferences.getString(key, defaultValue)

    override fun remove(key: String): Unit = sharedPreferences.edit().remove(key).apply()

    @Synchronized
    private fun createEncryptedSharedPrefs(context: Context): SharedPreferences =
        EncryptedSharedPreferences.create(
            FILE_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context.applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

}