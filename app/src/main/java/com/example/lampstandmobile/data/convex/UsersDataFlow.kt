package com.example.lampstandmobile.data.convex

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class UsersDataFlow {

    private val client
        get() = ConvexClientProvider.client

    suspend fun getMe(): Result<Map<String, Any?>> =
        withContext(Dispatchers.IO) {
            runCatching {
                client.subscribe<Map<String, Any?>>(
                    "users:me",
                    emptyMap()
                ).first().getOrThrow()
            }
        }
}
