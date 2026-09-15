package com.example.lampstandmobile.ui.dashboard

data class DashboardState(
    val isLoading: Boolean = false,
    val homeState: Map<String, Any?>? = null,
    val errorMessage: String? = null
)
