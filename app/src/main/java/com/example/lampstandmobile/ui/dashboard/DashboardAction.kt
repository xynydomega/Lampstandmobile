package com.example.lampstandmobile.ui.dashboard

sealed interface DashboardAction {
    data object Refresh : DashboardAction
    data class ItemClicked(val id: String) : DashboardAction
}
