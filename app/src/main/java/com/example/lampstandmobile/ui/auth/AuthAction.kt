package com.example.lampstandmobile.ui.auth

sealed interface AuthAction {
    data class EmailChanged(val email: String) : AuthAction

    data class CodeChanged(
        val index: Int,
        val value: String
    ) : AuthAction

    data object SendOtp : AuthAction

    data object VerifyOtp : AuthAction

    data object ResendOtp : AuthAction

    data object DevLogin : AuthAction

    data object Logout : AuthAction
}
