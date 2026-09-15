package com.example.lampstandmobile.ui.journey.list

data class JourneyPathUi(
    val id: String,
    val title: String,
    val description: String,
    val durationDays: Int,
    val thumbnailRes: Int? = null
)

data class MyJourneyListState(
    val isLoading: Boolean = false,
    val paths: List<JourneyPathUi> = emptyList(),
    val errorMessage: String? = null
)
