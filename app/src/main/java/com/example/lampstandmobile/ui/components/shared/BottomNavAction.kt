package com.example.lampstandmobile.ui.components.shared

sealed interface BottomNavAction {
    data class SelectTab(
        val tab: BottomNavTab
    ) : BottomNavAction
}
