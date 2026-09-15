package com.example.lampstandmobile.ui.auth

data class AuthState(
    val email: String = "",
    val currentStep: AuthStep = AuthStep.EMAIL,
    val code: List<String> = listOf("", "", "", ""),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val resendCooldown: Int = 0,
)

enum class AuthStep {
    EMAIL,
    OTP
}
