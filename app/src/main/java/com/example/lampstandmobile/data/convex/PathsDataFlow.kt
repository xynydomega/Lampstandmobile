package com.example.lampstandmobile.data.convex

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class PathsDataFlow {
    private val client
        get() = ConvexClientProvider.client

    /**
     * Fetches the home state for the current user.
     * Returns a map containing the state and relevant path/session data.
     */
    suspend fun getHomeState(): Result<Map<String, Any?>> =
        withContext(Dispatchers.IO) {
            runCatching {
                @Suppress("UNCHECKED_CAST")
                client.subscribe<Map<String, Any?>>(
                    "paths:getHomeState",
                    emptyMap()
                ).first().getOrThrow()
            }
        }
}
