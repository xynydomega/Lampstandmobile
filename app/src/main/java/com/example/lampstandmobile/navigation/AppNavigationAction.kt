package com.example.lampstandmobile.navigation

sealed interface AppNavigationAction {
    data class SelectDestination(
        val destination: AppDestination
    ) : AppNavigationAction
}
