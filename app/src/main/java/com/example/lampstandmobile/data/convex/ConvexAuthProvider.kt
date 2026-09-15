package com.example.lampstandmobile.data.convex

import android.content.Context
import dev.convex.android.AuthProvider
import java.util.concurrent.atomic.AtomicReference

class ConvexAuthProvider : AuthProvider<String> {

    private val idToken = AtomicReference<String?>(null)

    override suspend fun login(
        context: Context,
        onIdToken: (String?) -> Unit
    ): Result<String> {
        val token = idToken.get()

        return if (token != null) {
            onIdToken(token)
            Result.success(token)
        } else {
            Result.failure(
                IllegalStateException(
                    "No Convex authentication token is available."
                )
            )
        }
    }

    override suspend fun loginFromCache(
        onIdToken: (String?) -> Unit
    ): Result<String> {
        val token = idToken.get()

        return if (token != null) {
            onIdToken(token)
            Result.success(token)
        } else {
            Result.failure(
                IllegalStateException(
                    "No cached Convex authentication token is available."
                )
            )
        }
    }

    override suspend fun logout(
        context: Context
    ): Result<Void?> {
        idToken.set(null)
        return Result.success(null)
    }

    override fun extractIdToken(result: String): String {
        return result
    }

    fun setIdToken(token: String) {
        idToken.set(token)
    }

    fun clearIdToken() {
        idToken.set(null)
    }

    fun getIdToken(): String? {
        return idToken.get()
    }
}
