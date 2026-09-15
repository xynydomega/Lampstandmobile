package com.example.lampstandmobile.ui.dashboard

import com.example.lampstandmobile.data.convex.DiscoveryDataFlow
import com.example.lampstandmobile.data.convex.PathsDataFlow
import com.example.lampstandmobile.data.convex.UsersDataFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class DashboardData(
    val me: Map<String, Any?>?,
    val homeState: Map<String, Any?>?,
    val discoveryPaths: List<Map<String, Any?>>
)

class DashboardDataFlow(
    private val usersDataFlow: UsersDataFlow = UsersDataFlow(),
    private val pathsDataFlow: PathsDataFlow = PathsDataFlow(),
    private val discoveryDataFlow: DiscoveryDataFlow = DiscoveryDataFlow()
) {

    suspend fun getDashboardData(): Result<DashboardData> =
        withContext(Dispatchers.IO) {
            runCatching {
                val me = usersDataFlow.getMe().getOrThrow()
                val homeState = pathsDataFlow.getHomeState().getOrThrow()
                val discoveryPaths =
                    discoveryDataFlow.getPathsDiscoveryScreen().getOrThrow()

                DashboardData(
                    me = me,
                    homeState = homeState,
                    discoveryPaths = discoveryPaths
                )
            }
        }
}
