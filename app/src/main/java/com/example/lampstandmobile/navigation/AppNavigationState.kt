package com.example.lampstandmobile.navigation

enum class AppDestination {
    AUTH,
    ONBOARDING_PROFILE,
    ONBOARDING,
    HOME,
    PATHS,
    PATH_DETAIL,
    JOURNEY, // kept as alias for JOURNEY_LIST for BottomNav tab mapping
    JOURNEY_LIST,
    JOURNEY_DETAIL,
    JOURNEY_SESSION,
    JOURNEY_FEEDBACK,
    JOURNEY_COMPLETED,
    JOURNEY_LOOKING_FORWARD,
    JOURNEY_SUPPORT,
    PROFILE,
    PROFILE_PERSONAL_INFO
}

data class AppNavigationState(
    val currentDestination: AppDestination = AppDestination.AUTH
)
