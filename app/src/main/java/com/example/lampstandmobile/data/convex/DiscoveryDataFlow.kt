package com.example.lampstandmobile.data.convex

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class DiscoveryDataFlow {

    private val client
        get() = ConvexClientProvider.client

    suspend fun getPathsDiscoveryScreen(): Result<List<Map<String, Any?>>> =
        withContext(Dispatchers.IO) {
            runCatching {
                client.subscribe<List<Map<String, Any?>>>(
                    "discovery:getPathsDiscoveryScreen",
                    emptyMap()
                ).first().getOrThrow()
            }
        }
}
