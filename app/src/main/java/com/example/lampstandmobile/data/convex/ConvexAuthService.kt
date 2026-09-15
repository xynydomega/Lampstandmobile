package com.example.lampstandmobile.data.convex

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class ConvexAuthService(
    private val authProvider: ConvexAuthProvider,
    private val tokenStorage: ConvexTokenStorage,
    private val httpClient: OkHttpClient = OkHttpClient()
) {

    suspend fun sendOtp(email: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val body = FormBody.Builder()
                    .add("provider", "resend-otp")
                    .add("email", email)
                    .build()

                val request = Request.Builder()
                    .url("${ConvexConfig.SITE_URL}/auth")
                    .post(body)
                    .build()

                httpClient.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        error("Failed to send OTP: HTTP ${response.code}")
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
                val body = FormBody.Builder()
                    .add("provider", "resend-otp")
                    .add("email", email)
                    .add("code", code)
                    .build()

                val request = Request.Builder()
                    .url("${ConvexConfig.SITE_URL}/auth")
                    .post(body)
                    .build()

                httpClient.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        error("Invalid or expired code: HTTP ${response.code}")
                    }

                    val responseBody = response.body?.string()
                        ?: error("Empty authentication response.")

                    val json = Json.parseToJsonElement(responseBody).jsonObject

                    val token = json["tokens"]
                        ?.jsonObject
                        ?.get("token")
                        ?.jsonPrimitive
                        ?.content
                        ?: error("Authentication response did not contain a token.")

                    authProvider.setIdToken(token)
                    tokenStorage.saveToken(token)
                }
            }
        }
}
