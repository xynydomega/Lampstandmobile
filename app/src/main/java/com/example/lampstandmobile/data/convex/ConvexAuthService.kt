package com.example.lampstandmobile.data.convex

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class ConvexAuthService {

    private val client = OkHttpClient()

    private suspend fun cleanupDevBypassCodes() {
        // Mirrors web app: app/auth/page.tsx calls api.users.cleanupDevBypassCodes before dev login
        // Prevents "unique() query returned more than one result from table authVerificationCodes" when AUTH_DEV_BYPASS_CODE=0000
        runCatching {
            val body = JSONObject()
                .put("path", "users:cleanupDevBypassCodes")
                .put("args", JSONObject())
                .put("format", "json")
                .toString()
            val request = Request.Builder()
                .url("${ConvexConfig.DEPLOYMENT_URL}/api/mutation")
                .post(body.toRequestBody("application/json".toMediaType()))
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    Log.w("ConvexAuthService", "cleanupDevBypassCodes failed: ${response.code} ${response.body?.string()}")
                }
            }
        }.onFailure { Log.w("ConvexAuthService", "cleanupDevBypassCodes error", it) }
    }

    suspend fun sendOtp(email: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                // Clean duplicates first so verifyCodeAndSignIn unique() doesn't crash with 0000 bypass
                cleanupDevBypassCodes()
                val body = JSONObject()
                    .put("path", "auth:signIn")
                    .put(
                        "args",
                        JSONObject()
                            .put("provider", "resend-otp")
                            .put(
                                "params",
                                JSONObject().put("email", email)
                            )
                    )
                    .put("format", "json")
                    .toString()

                val request = Request.Builder()
                    .url("${ConvexConfig.DEPLOYMENT_URL}/api/action")
                    .post(
                        body.toRequestBody(
                            "application/json".toMediaType()
                        )
                    )
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        throw Exception(
                            "OTP request failed: ${response.code} ${response.body?.string()}"
                        )
                    }
                }
            }
        }

    suspend fun verifyOtp(
        email: String,
        code: String
    ): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val body = JSONObject()
                    .put("path", "auth:signIn")
                    .put(
                        "args",
                        JSONObject()
                            .put("provider", "resend-otp")
                            .put(
                                "params",
                                JSONObject()
                                    .put("email", email)
                                    .put("code", code)
                            )
                    )
                    .put("format", "json")
                    .toString()

                val request = Request.Builder()
                    .url("${ConvexConfig.DEPLOYMENT_URL}/api/action")
                    .post(
                        body.toRequestBody(
                            "application/json".toMediaType()
                        )
                    )
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        throw Exception(
                            "OTP verification failed: ${response.code}"
                        )
                    }

                    val responseBody = response.body?.string()
                        ?: throw Exception("Empty auth response")

                    val json = JSONObject(responseBody)

                    val token = json
                        .optJSONObject("value")
                        ?.optJSONObject("tokens")
                        ?.optString("token")
                        ?.takeIf { it.isNotBlank() }
                        ?: throw Exception("No auth token returned")

                    ConvexClientProvider.setAuthToken(token)
                    ConvexClientProvider.setAuthenticated(true)

                    Log.d(
                        "ConvexAuthService",
                        "Authentication successful"
                    )

                    Unit
                }
            }
        }

    suspend fun logout(): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                ConvexClientProvider.clearAuthToken()
                ConvexClientProvider.clearAuthentication()
                Unit
            }
        }
}



