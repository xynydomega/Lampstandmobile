package com.example.lampstandmobile.data.convex

import android.content.Context
import dev.convex.android.ConvexClientWithAuth
import dev.convex.android.MobileConvexClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

object ConvexClientProvider {

    private const val CLIENT_ID = "lampstand-android"

    private val authScope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO
    )

    private var applicationContext: Context? = null

    lateinit var authProvider: ConvexAuthProvider
        private set

    lateinit var tokenStorage: ConvexTokenStorage
        private set

    lateinit var client: ConvexClientWithAuth<String>
        private set

    val context: Context
        get() = applicationContext ?: error("ConvexClientProvider not initialized")

    fun initialize(context: Context) {
        if (::client.isInitialized) return

        applicationContext = context.applicationContext

        tokenStorage = ConvexTokenStorage(applicationContext!!)
        authProvider = ConvexAuthProvider()

        tokenStorage.getToken()?.let { token ->
            authProvider.setIdToken(token)
        }

        client = ConvexClientWithAuth(
            ConvexConfig.DEPLOYMENT_URL,
            authProvider,
            authScope
        ) { deploymentUrl, _, webSocketStateSubscriber ->
            MobileConvexClient(
                deploymentUrl,
                CLIENT_ID,
                webSocketStateSubscriber
            )
        }

        // Restore authentication state from cache if a token exists
        if (authProvider.getIdToken() != null) {
            authScope.launch {
                client.loginFromCache()
            }
        }
    }
}
