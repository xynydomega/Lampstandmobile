package com.example.lampstandmobile.ui.journey.list

sealed interface MyJourneyListAction {
    data object Refresh : MyJourneyListAction
    data class PathClicked(val id: String) : MyJourneyListAction
    data object GoToPaths : MyJourneyListAction
}
