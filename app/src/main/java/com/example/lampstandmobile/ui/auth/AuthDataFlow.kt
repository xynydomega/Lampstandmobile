package com.example.lampstandmobile.ui.auth

import com.example.lampstandmobile.data.convex.ConvexAuthService
import com.example.lampstandmobile.data.convex.ConvexClientProvider

class AuthDataFlow(
    private val authService: ConvexAuthService = ConvexAuthService(
        ConvexClientProvider.authProvider,
        ConvexClientProvider.tokenStorage
    )
) {

    suspend fun sendOtp(email: String): Result<Unit> {
        return authService.sendOtp(email)
    }

    suspend fun verifyOtp(
        email: String,
        code: String
    ): Result<Unit> {
        return authService.verifyOtp(email, code)
    }
}
