package com.example.lampstandmobile.data.convex

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class ConvexTokenStorage(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val preferences = EncryptedSharedPreferences.create(
        context,
        "convex_auth",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveToken(token: String) {
        preferences.edit()
            .putString(KEY_ID_TOKEN, token)
            .apply()
    }

    fun getToken(): String? {
        return preferences.getString(KEY_ID_TOKEN, null)
    }

    fun clearToken() {
        preferences.edit()
            .remove(KEY_ID_TOKEN)
            .apply()
    }

    companion object {
        private const val KEY_ID_TOKEN = "id_token"
    }
}
