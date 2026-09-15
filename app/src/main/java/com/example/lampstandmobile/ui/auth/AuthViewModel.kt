package com.example.lampstandmobile.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lampstandmobile.data.convex.ConvexClientProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authDataFlow: AuthDataFlow = AuthDataFlow()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthState(email = "demo@lampstand.test", currentStep = AuthStep.OTP, code = listOf("1", "2", "", "")))
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    private var cooldownJob: Job? = null

    fun onAction(action: AuthAction) {
        when (action) {
            is AuthAction.EmailChanged -> {
                _uiState.update {
                    it.copy(
                        email = action.email,
                        errorMessage = null
                    )
                }
            }

            is AuthAction.CodeChanged -> handleCodeChanged(
                action.index,
                action.value
            )

            AuthAction.SendOtp -> sendOtp()
            AuthAction.VerifyOtp -> verifyOtp()
            AuthAction.ResendOtp -> resendOtp()
            AuthAction.DevLogin -> devLogin()
            AuthAction.Logout -> logout()
        }
    }

    private fun handleCodeChanged(index: Int, value: String) {
        if (value.any { !it.isDigit() }) return

        val digit = value.takeLast(1)

        _uiState.update { state ->
            state.copy(
                code = state.code.toMutableList().also {
                    it[index] = digit
                },
                errorMessage = null
            )
        }

        if (_uiState.value.code.all { it.isNotEmpty() }) {
            viewModelScope.launch {
                delay(100)
                verifyOtp()
            }
        }
    }

    private fun sendOtp() {
        val email = _uiState.value.email.trim()

        if (email.isBlank()) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            var sent = false
            var lastError: Throwable? = null
            for (attempt in 1..3) {
                val result = authDataFlow.sendOtp(email)
                if (result.isSuccess) {
                    sent = true
                    break
                } else {
                    lastError = result.exceptionOrNull()
                    if (attempt < 3) delay(600)
                }
            }

            if (sent) {
                _uiState.update {
                    it.copy(
                        currentStep = AuthStep.OTP,
                        isLoading = false
                    )
                }
                startCooldown()
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage =
                            lastError?.message
                                ?: "Failed to send code. Please try again."
                    )
                }
            }
        }
    }

    private fun verifyOtp() {
        val state = _uiState.value

        if (state.code.any { it.isEmpty() }) return

        val email = state.email.trim()
        val code = state.code.joinToString("")

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            authDataFlow.verifyOtp(email, code)
                .onSuccess {
                    viewModelScope.launch {
                        ConvexClientProvider.client.login(
                            ConvexClientProvider.context
                        )
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage =
                                error.message
                                    ?: "Invalid or expired code. Please try again."
                        )
                    }
                }
        }
    }

    private fun resendOtp() {
        if (_uiState.value.resendCooldown > 0) return
        sendOtp()
    }

    private fun devLogin() {
        val email = _uiState.value.email.trim()
        if (email.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Enter your email address first, then click Dev Login.") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            // Step 1: send OTP with 3x retry like web handleDevLogin page.tsx:98
            var sent = false
            for (attempt in 1..3) {
                val r = authDataFlow.sendOtp(email)
                if (r.isSuccess) { sent = true; break } else if (attempt < 3) delay(600)
            }
            if (!sent) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Dev login failed (OTP send after 3 attempts). Make sure AUTH_DEV_BYPASS_CODE 0000 is set.") }
                return@launch
            }
            delay(600)
            // Step 2: verify with DEV_BYPASS_CODE 0000 like web page.tsx:131
            authDataFlow.verifyOtp(email, "0000")
                .onSuccess {
                    ConvexClientProvider.client.login(ConvexClientProvider.context)
                    _uiState.update { it.copy(isLoading = false, errorMessage = null) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.message ?: "Dev login step 2 failed (OTP verify). Check AUTH_DEV_BYPASS_CODE=0000 is set.") }
                }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            ConvexClientProvider.client.logout(
                ConvexClientProvider.context
            )
            ConvexClientProvider.tokenStorage.clearToken()
            _uiState.update { AuthState() }
        }
    }

    private fun startCooldown() {
        cooldownJob?.cancel()

        cooldownJob = viewModelScope.launch {
            for (seconds in 60 downTo 1) {
                _uiState.update {
                    it.copy(resendCooldown = seconds)
                }
                delay(1_000)
            }

            _uiState.update {
                it.copy(resendCooldown = 0)
            }
        }
    }

    override fun onCleared() {
        cooldownJob?.cancel()
        super.onCleared()
    }
}

