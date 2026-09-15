package com.example.lampstandmobile.ui.components.shared

data class BottomNavState(
    val activeTab: BottomNavTab = BottomNavTab.HOME
)

enum class BottomNavTab {
    HOME,
    PATHS,
    JOURNEY,
    PROFILE
}
