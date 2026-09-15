package com.example.lampstandmobile.ui.journey.list

import com.example.lampstandmobile.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * DataFlow for My Journey LIST — mirrors web `app/my-journey/page.tsx` mockPaths
 * + future `myJourney:get` Convex query. For now returns mock until backend wired.
 */
class MyJourneyListDataFlow {

    suspend fun getMyJourneyPaths(): Result<List<JourneyPathUi>> =
        withContext(Dispatchers.IO) {
            runCatching {
                // TODO: replace with Convex subscribe when backend exposes myJourney query
                // client.subscribe<List<Map<String, Any?>>>("myJourney:get", emptyMap()).first().getOrThrow()
                //   .map { mapToUi(it) }
                mockPaths()
            }
        }

    private fun mockPaths(): List<JourneyPathUi> = listOf(
        JourneyPathUi(
            id = "1",
            title = "Trust in Uncertainty",
            description = "Grow in understanding God's character through these curated paths",
            durationDays = 7,
            thumbnailRes = R.drawable.sailboat
        )
    )
}
